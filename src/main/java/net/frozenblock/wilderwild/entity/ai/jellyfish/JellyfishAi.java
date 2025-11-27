/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.entity.ai.jellyfish;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.frozenblock.lib.entity.api.behavior.FrozenBehaviorUtils;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.server.level.ServerLevel;
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
	public static final List<SensorType<? extends Sensor<? super Jellyfish>>> SENSOR_TYPES = List.of(
		SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS
	);
	public static final List<? extends MemoryModuleType<?>> MEMORY_TYPES = List.of(
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
		initIdleActivity(jellyfish, brain);
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
				new AnimalPanic<>(2F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink()
			)
		);
	}

	private static void initIdleActivity(Jellyfish jellyfish, Brain<Jellyfish> brain) {
		brain.addActivity(
			Activity.IDLE,
			10,
			ImmutableList.of(
				StartAttacking.create(JellyfishAi::findNearestValidAttackTarget),
				TryFindWater.create(6, 0.15F),
				new RunOne<>(
					ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
					ImmutableList.of(
						Pair.of(
							BehaviorBuilder.triggerIf(jellyfish1 -> jellyfish1.getTarget() == null && jellyfish1.canRandomSwim(), FrozenBehaviorUtils.getOneShot(RandomStroll.swim(1F))),
							2
						),
						Pair.of(BehaviorBuilder.triggerIf(Entity::isInWater), 1),
						Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 1)
					)
				),
				new JellyfishHide(jellyfish, 1.25D, 8, 3)
			)
		);
	}

	private static void initFightActivity(Jellyfish jellyfish, Brain<Jellyfish> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				StopAttackingIfTargetInvalid.create(
					(level, livingEntity) -> !jellyfish.canTargetEntity(livingEntity, level), JellyfishAi::onTargetInvalid, false
				),
				SetEntityLookTarget.create(livingEntity -> isTarget(jellyfish, livingEntity), (float) jellyfish.getAttributeValue(Attributes.FOLLOW_RANGE)),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(JellyfishAi::getSpeedModifierChasing)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static boolean isTarget(Jellyfish jellyfish, LivingEntity entity) {
		return jellyfish.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(possibleEntity -> possibleEntity == entity).isPresent();
	}

	public static void updateActivity(Jellyfish jellyfish) {
		Brain<Jellyfish> brain = jellyfish.getBrain();
		brain.setActiveActivityToFirstValid(List.of(Activity.FIGHT, Activity.IDLE));
	}

	private static float getSpeedModifierChasing(LivingEntity entity) {
		return 1.5F;
	}

	private static void onTargetInvalid(ServerLevel level, Jellyfish jellyfish, LivingEntity target) {
		if (jellyfish.getTarget() == target) jellyfish.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
	}

	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, Jellyfish jellyfish) {
		return jellyfish.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}
}
