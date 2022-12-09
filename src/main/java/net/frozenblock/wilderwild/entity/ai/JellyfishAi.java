package net.frozenblock.wilderwild.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.frozenblock.lib.entity.behavior.api.FrozenBehaviorUtils;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

public class JellyfishAi {

    public static final ImmutableList<SensorType<? extends Sensor<? super Jellyfish>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS
    );
    public static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.NEAREST_ATTACKABLE,
			MemoryModuleType.IS_PANICKING
    );

    public static Brain<Jellyfish> makeBrain(Jellyfish jellyfish, Brain<Jellyfish> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(jellyfish, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
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
				10,
                ImmutableList.of(
						StartAttacking.create(JellyfishAi::findNearestValidAttackTarget),
						TryFindWater.create(6, 0.15F),
						new RunOne<>(
								ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
								ImmutableList.of(
										Pair.of(BehaviorBuilder.triggerIf(jellyfish -> jellyfish.getTarget() == null && jellyfish.canRandomSwim(), FrozenBehaviorUtils.getOneShot(RandomStroll.swim(1.0F))), 2),
										Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 1),
										Pair.of(BehaviorBuilder.triggerIf(Entity::isOnGround), 1)
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
                        StopAttackingIfTargetInvalid.create(
                                livingEntity -> !jellyfish.canTargetEntity(livingEntity), JellyfishAi::onTargetInvalid, false
                        ),
                        SetEntityLookTarget.create(livingEntity -> isTarget(jellyfish, livingEntity), (float) jellyfish.getAttributeValue(Attributes.FOLLOW_RANGE)),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(JellyfishAi::getSpeedModifierChasing)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean isTarget(Jellyfish jellyfish, LivingEntity livingEntity) {
        return jellyfish.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
    }

    public static void updateActivity(Jellyfish jellyfish) {
        Brain<Jellyfish> brain = jellyfish.getBrain();
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    private static float getSpeedModifierChasing(LivingEntity livingEntity) {
		return 2F;
        //return livingEntity.isInWaterOrBubble() ? 0.6F : 0.15F;
    }

    private static void onTargetInvalid(Jellyfish jellyfish, LivingEntity target) {
        if (jellyfish.getTarget() == target) {
            jellyfish.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Jellyfish jellyfish) {
        return jellyfish.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }
}
