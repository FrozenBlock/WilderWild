/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import org.jetbrains.annotations.NotNull;

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

	@NotNull
	public static Brain<Jellyfish> makeBrain(@NotNull Jellyfish jellyfish, @NotNull Brain<Jellyfish> brain) {
		initCoreActivity(brain);
		initIdleActivity(jellyfish, brain);
		initFightActivity(jellyfish, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void initCoreActivity(@NotNull Brain<Jellyfish> brain) {
		brain.addActivity(
			Activity.CORE,
			0,
			ImmutableList.of(
				new AnimalPanic<>(2.0F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink()
			)
		);
	}

	private static void initIdleActivity(@NotNull Jellyfish jellyfish, @NotNull Brain<Jellyfish> brain) {
		brain.addActivity(
			Activity.IDLE,
			10,
			ImmutableList.of(
				StartAttacking.create(JellyfishAi::findNearestValidAttackTarget),
				TryFindWater.create(6, 0.15F),
				new RunOne<>(
					ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
					ImmutableList.of(
						Pair.of(BehaviorBuilder.triggerIf(jellyfish1 -> jellyfish1.getTarget() == null && jellyfish1.canRandomSwim(), FrozenBehaviorUtils.getOneShot(RandomStroll.swim(1.0F))), 2),
						Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 1),
						Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 1)
					)
				),
				new JellyfishHide(jellyfish, 1.25D, 8, 3)
			)
		);
	}

	private static void initFightActivity(@NotNull Jellyfish jellyfish, @NotNull Brain<Jellyfish> brain) {
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

	private static boolean isTarget(@NotNull Jellyfish jellyfish, @NotNull LivingEntity livingEntity) {
		return jellyfish.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
	}

	public static void updateActivity(@NotNull Jellyfish jellyfish) {
		Brain<Jellyfish> brain = jellyfish.getBrain();
		brain.setActiveActivityToFirstValid(List.of(Activity.FIGHT, Activity.IDLE));
	}

	private static float getSpeedModifierChasing(@NotNull LivingEntity livingEntity) {
		return 2F;
	}

	private static void onTargetInvalid(@NotNull Jellyfish jellyfish, @NotNull LivingEntity target) {
		if (jellyfish.getTarget() == target) {
			jellyfish.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		}
	}

	@NotNull
	private static Optional<? extends LivingEntity> findNearestValidAttackTarget(@NotNull Jellyfish jellyfish) {
		return jellyfish.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
	}
}
