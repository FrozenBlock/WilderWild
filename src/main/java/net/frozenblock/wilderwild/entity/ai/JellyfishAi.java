package net.frozenblock.wilderwild.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Optional;

public class JellyfishAi {

    private static final ImmutableList<SensorType<? extends Sensor<? super Jellyfish>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY
    );
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.HAS_HUNTING_COOLDOWN
    );

    public static Brain<Jellyfish> makeBrain(Jellyfish jellyfish, Dynamic<?> dynamic) {
        Brain.Provider<Jellyfish> provider = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
        Brain<Jellyfish> brain = provider.makeBrain(dynamic);
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(jellyfish, brain);
        return brain;
    }

    private static void initCoreActivity(Brain<Jellyfish> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new AnimalPanic(2.0F),
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink()
                )
        );
    }

    private static void initIdleActivity(Brain<Jellyfish> brain) {
        brain.addActivity(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(3, new StartAttacking<>(JellyfishAi::findNearestValidAttackTarget)),
                        Pair.of(3, new TryFindWater(6, 0.15F)),
                        Pair.of(
                                4,
                                new GateBehavior<>(
                                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                                        ImmutableSet.of(),
                                        GateBehavior.OrderPolicy.ORDERED,
                                        GateBehavior.RunningPolicy.TRY_ALL,
                                        ImmutableList.of(
                                                Pair.of(new RandomSwim(0.5F), 2),
                                                Pair.of(new RandomStroll(0.15F, false), 2),
                                                Pair.of(new RunIf<>(Entity::isInWaterOrBubble, new DoNothing(30, 60)), 5),
                                                Pair.of(new RunIf<>(Entity::isOnGround, new DoNothing(200, 400)), 5)
                                        )
                                )
                        )
                )
        );
    }

    private static void initFightActivity(Jellyfish jellyfish, Brain<Jellyfish> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        new StopAttackingIfTargetInvalid<>(
                                livingEntity -> !jellyfish.canTargetEntity(livingEntity), JellyfishAi::onTargetInvalid, false
                        ),
                        new SetEntityLookTarget(livingEntity -> isTarget(jellyfish, livingEntity), (float) jellyfish.getAttributeValue(Attributes.FOLLOW_RANGE)),
                        new SetWalkTargetFromAttackTargetIfTargetOutOfReach(JellyfishAi::getSpeedModifierChasing)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean isTarget(Jellyfish jellyfish, LivingEntity livingEntity) {
        return jellyfish.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
    }

    public static void updateActivity(Jellyfish jellyfish) {
        Brain<Jellyfish> brain = jellyfish.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        if (activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }

    }

    private static float getSpeedModifierChasing(LivingEntity livingEntity) {
        return livingEntity.isInWaterOrBubble() ? 0.6F : 0.15F;
    }

    private static void onTargetInvalid(Jellyfish jellyfish, LivingEntity livingEntity) {
        if (jellyfish.getTarget() == livingEntity) {
            jellyfish.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Jellyfish jellyfish) {
        return jellyfish.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }
}
