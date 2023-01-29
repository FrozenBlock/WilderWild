package net.frozenblock.wilderwild.entity.ai.jellyfish;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
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
        initCoreActivity(jellyfish, brain);
        initIdleActivity(jellyfish, brain);
        initFightActivity(jellyfish, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Jellyfish jellyfish, Brain<Jellyfish> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new AnimalPanic(2.0F),
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink()
                )
        );
		brain.addActivity(
				Activity.CORE,
				1,
				ImmutableList.of(
						new JellyfishHide(jellyfish, 1.25D, 8, 3)
				)
		);
    }

    private static void initIdleActivity(Jellyfish jellyfish, Brain<Jellyfish> brain) {
        brain.addActivity(
                Activity.IDLE,
				10,
                ImmutableList.of(
						new StartAttacking<>(JellyfishAi::findNearestValidAttackTarget),
						new TryFindWater(6, 0.15F),
						new RunOne<>(
								ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
								ImmutableList.of(
										Pair.of(new JellyfishRandomSwim(1.0F), 2),
										Pair.of(new RunIf<>(Entity::isInWaterOrBubble, new DoNothing(30, 60)), 1),
										Pair.of(new RunIf<>(Entity::isOnGround, new DoNothing(200, 400)), 1)
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
