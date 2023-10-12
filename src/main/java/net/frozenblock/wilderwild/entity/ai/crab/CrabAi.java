package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.frozenblock.lib.entity.behavior.api.FrozenBehaviorUtils;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.warden.ForceUnmount;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CrabAi {
	private static final int DIGGING_DURATION = Crab.DIG_LENGTH_IN_TICKS;
	private static final int EMERGE_DURATION = Crab.EMERGE_LENGTH_IN_TICKS;

	private static final BehaviorControl<Crab> DIG_COOLDOWN_SETTER = BehaviorBuilder.create(instance -> instance.group(instance.registered(MemoryModuleType.DIG_COOLDOWN)).apply(instance, memoryAccessor -> (world, crab, l) -> {
		if (instance.tryGet(memoryAccessor).isPresent()) {
			memoryAccessor.setWithExpiry(Unit.INSTANCE, getRandomDigCooldown(crab));
		}
		return true;
	}));
	private static final BehaviorControl<Crab> EMERGE_COOLDOWN_SETTER = BehaviorBuilder.create(instance -> instance.group(instance.registered(MemoryModuleType.DIG_COOLDOWN)).apply(instance, memoryAccessor -> (world, crab, l) -> {
		if (instance.tryGet(memoryAccessor).isPresent()) {
			memoryAccessor.setWithExpiry(Unit.INSTANCE, getRandomEmergeCooldown(crab));
		}
		return true;
	}));

	public static void updateActivity(@NotNull Crab crab) {
		crab.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.EMERGE, Activity.DIG, Activity.FIGHT, Activity.IDLE));
	}

    @NotNull
    public static Brain<?> makeBrain(@NotNull Crab crab, @NotNull Brain<Crab> brain) {
        addCoreActivity(brain);
		initEmergeActivity(brain);
		initDiggingActivity(brain);
        addIdleActivity(crab, brain);
        addFightActivity(crab, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivity(@NotNull Brain<Crab> brain) {
        brain.addActivity(
            Activity.CORE,
            0,
            ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
            )
        );
    }

	private static void initEmergeActivity(@NotNull Brain<Crab> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.EMERGE,
			5,
			ImmutableList.of(
				new CrabEmerge<>(EMERGE_DURATION)
			),
			MemoryModuleType.IS_EMERGING
		);
	}

	private static void initDiggingActivity(@NotNull Brain<Crab> brain) {
		brain.addActivityWithConditions(
			Activity.DIG,
			ImmutableList.of(
				Pair.of(0, new ForceUnmount()),
				Pair.of(1, new CrabDig<>(DIGGING_DURATION))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.DIG_COOLDOWN, MemoryStatus.VALUE_ABSENT),
				Pair.of(RegisterMemoryModuleTypes.UNDERGROUND, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

    private static void addIdleActivity(@NotNull Crab crab, @NotNull Brain<Crab> brain) {
        brain.addActivity(
            Activity.IDLE,
            10,
            ImmutableList.of(
				CrabTryToEmerge.create(),
                StartAttacking.create(CrabAi::findNearestValidAttackTarget),
                new RunOne<>(
                    ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                    ImmutableList.of(
						Pair.of(RandomStroll.stroll(1F), 2),
						Pair.of(new DoNothing(30, 60), 1)
                    )
                )
            )
        );
    }

    private static void addFightActivity(@NotNull Crab crab, @NotNull Brain<Crab> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
            Activity.FIGHT,
            10,
            ImmutableList.of(
				DIG_COOLDOWN_SETTER,
                StopAttackingIfTargetInvalid.create(
                    livingEntity -> !crab.canTargetEntity(livingEntity), CrabAi::onTargetInvalid, true
                ),
                SetEntityLookTarget.create(livingEntity -> isTarget(crab, livingEntity), (float) crab.getAttributeValue(Attributes.FOLLOW_RANGE)),
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(CrabAi::getSpeedModifierChasing),
				MeleeAttack.create(20),
				EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
            ),
            MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean isTarget(@NotNull Crab crab, @NotNull LivingEntity livingEntity) {
        return crab.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
    }

    private static float getSpeedModifierChasing(@Nullable LivingEntity livingEntity) {
        return 2F;
    }

    private static void onTargetInvalid(@NotNull Crab crab, @NotNull LivingEntity target) {
        if (crab.getTarget() == target) {
            crab.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }
    }

    @NotNull
    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(@NotNull Crab crab) {
        return crab.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

	public static void setDigCooldown(@NotNull Crab crab) {
		if (crab.getBrain().hasMemoryValue(MemoryModuleType.DIG_COOLDOWN)) {
			crab.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, getRandomDigCooldown(crab));
		}
	}

	public static void clearDigCooldown(@NotNull Crab crab) {
		crab.getBrain().eraseMemory(MemoryModuleType.DIG_COOLDOWN);
	}

	public static boolean isUnderground(@NotNull Crab crab) {
		return crab.getBrain().hasMemoryValue(RegisterMemoryModuleTypes.UNDERGROUND);
	}

	public static int getRandomDigCooldown(@NotNull LivingEntity entity) {
		return entity.getRandom().nextInt(800, 2400);
	}

	public static int getRandomEmergeCooldown(@NotNull LivingEntity entity) {
		return entity.getRandom().nextInt(800, 2400);
	}
}
