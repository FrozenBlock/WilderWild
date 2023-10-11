package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.lib.entity.behavior.api.FrozenBehaviorUtils;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Optional;

public final class CrabAi {
    private CrabAi() {}

    @NotNull
    public static Brain<?> makeBrain(@NotNull Crab crab, @NotNull Brain<Crab> brain) {
        addCoreActivity(brain);
        addIdleActivity(brain);
        addFightActivity(brain);
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

    private static void addIdleActivity(@NotNull Crab crab, @NotNull Brain<Crab> brain) {
        brain.addActivity(
            Activity.IDLE,
            10,
            ImmutableList.of(
                StartAttacking.create(CrabAi::findNearestValidAttackTarget),
                new RunOne<>(
                    ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                    ImmutableList.of(
                        Pair.of(BehaviorBuilder.triggerIf(crab1 -> crab1.getTarget() == null && crab1.canRandomSwim(), FrozenBehaviorUtils.getOneShot(RandomStroll.swim(1.0F))), 2),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 1),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 1)
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
                StopAttackingIfTargetInvalid.create(
                    livingEntity -> !crab.canTargetEntity(livingEntity), CrabAi::onTargetInvalid, false
                ),
                SetEntityLookTarget.create(livingEntity -> isTarget(crab, livingEntity), (float) crab.getAttributeValue(Attributes.FOLLOW_RANGE)),
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(CrabAi::getSpeedModifierChasing)
            ),
            MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean isTarget(@NotNull Crab crab, @NotNull LivingEntity livingEntity) {
        return crab.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
    }

    public static void updateActivity(@NotNull Crab crab) {
        Brain<Crab> brain = crab.getBrain();
        brain.setActiveActivityToFirstValid(List.of(Activity.FIGHT, Activity.IDLE));
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
}
