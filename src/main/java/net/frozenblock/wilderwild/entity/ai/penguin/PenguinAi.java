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

package net.frozenblock.wilderwild.entity.ai.penguin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWActivities;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.TryFindLand;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PenguinAi {
	private static final float SPEED_MULTIPLIER_WHEN_ATTACKING = 1.75F;
	private static final UniformInt TIME_BETWEEN_LONG_JUMPS = UniformInt.of(20, 40);

	public static final int LAY_DOWN_DURATION = 13;
	public static final int STAND_UP_DURATION = 48;

	private static final ImmutableList<SensorType<? extends Sensor<? super Penguin>>> SENSOR_TYPES = ImmutableList.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.HURT_BY,
		WWSensorTypes.OSTRICH_TEMPTATIONS,
		SensorType.NEAREST_ADULT,
		SensorType.NEAREST_PLAYERS,
		WWSensorTypes.PENGUIN_SPECIFIC_SENSOR,
		WWSensorTypes.PENGUIN_TEMPTATIONS,
		WWSensorTypes.PENGUIN_ATTACKABLES,
		SensorType.IS_IN_WATER,
		WWSensorTypes.LAND_POS_SENSOR
	);
	private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
		MemoryModuleType.IS_PANICKING,
		MemoryModuleType.HURT_BY,
		MemoryModuleType.HURT_BY_ENTITY,
		MemoryModuleType.WALK_TARGET,
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
		MemoryModuleType.PATH,
		MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
		MemoryModuleType.TEMPTING_PLAYER,
		MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
		MemoryModuleType.IS_TEMPTED,
		MemoryModuleType.BREED_TARGET,
		MemoryModuleType.NEAREST_VISIBLE_ADULT,
		MemoryModuleType.IS_PREGNANT,
		MemoryModuleType.ANGRY_AT,
		MemoryModuleType.UNIVERSAL_ANGER,
		MemoryModuleType.ATTACK_TARGET,
		MemoryModuleType.NEAREST_ATTACKABLE,
		MemoryModuleType.NEAREST_LIVING_ENTITIES,
		MemoryModuleType.NEAREST_PLAYERS,
		MemoryModuleType.NEAREST_VISIBLE_PLAYER,
		MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
		WWMemoryModuleTypes.NEARBY_PENGUINS,
		MemoryModuleType.ATTACK_COOLING_DOWN,
		MemoryModuleType.HOME,
		MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS,
		MemoryModuleType.LONG_JUMP_MID_JUMP,
		MemoryModuleType.IS_IN_WATER,
		WWMemoryModuleTypes.IDLE_TIME,
		WWMemoryModuleTypes.DIVE_TICKS,
		MemoryModuleType.HAS_HUNTING_COOLDOWN,
		WWMemoryModuleTypes.LAYING_DOWN,
		WWMemoryModuleTypes.SEARCHING_FOR_WATER,
		WWMemoryModuleTypes.WANTS_TO_LAUNCH,
		WWMemoryModuleTypes.LAND_POS,
		WWMemoryModuleTypes.WATER_POS,
		WWMemoryModuleTypes.STANDING_UP
	);

	@NotNull
	public static Brain.Provider<Penguin> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
	}

	@NotNull
	public static Brain<?> makeBrain(@NotNull Penguin penguin, @NotNull Brain<Penguin> brain) {
		initCoreActivity(brain);
		initStandUpActivity(brain);
		initIdleActivity(brain);
		initSearchActivity(brain);
		initSwimActivity(brain);
		initEscapeActivity(brain);
		initJumpActivity(brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void initCoreActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivity(
			Activity.CORE,
			0,
			ImmutableList.of(
				new AnimalPanic<>(2F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS),
				new CountDownCooldownTicks(WWMemoryModuleTypes.IDLE_TIME),
				new CountDownCooldownTicks(WWMemoryModuleTypes.DIVE_TICKS)
			)
		);
	}

	private static void initStandUpActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			WWActivities.STAND_UP,
			5,
			ImmutableList.of(
				new PenguinStandUp<>(STAND_UP_DURATION)
			),
			WWMemoryModuleTypes.STANDING_UP
		);
	}

	private static void initIdleActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityWithConditions(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6F, UniformInt.of(30, 60))),
				Pair.of(0, new AnimalMakeLove(WWEntityTypes.PENGUIN)),
				Pair.of(1, new FollowTemptation(livingEntity -> 1.25F)),
				//Pair.of(2, StartAttacking.create(PenguinAi::canAttack, frog -> frog.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
				Pair.of(3, TryFindLand.create(6, 1.0F)),
				Pair.of(
					4,
					new RunOne<>(
						ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
						ImmutableList.of(
							Pair.of(RandomStroll.stroll(1F), 1),
							Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 1),
							Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 2)
						)
					)
				)
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.SEARCHING_FOR_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IDLE_TIME, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.LAYING_DOWN, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void initSearchActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityAndRemoveMemoriesWhenStopped(
			WWActivities.SEARCH,
			ImmutableList.of(
				Pair.of(0, new PenguinSearchingForWater()),
				Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6F, UniformInt.of(30, 60))),
				Pair.of(1, TryFindWater.create(8, 0.8F)),
				Pair.of(2, PenguinReturnToWater.create(0.8F)),
				Pair.of(
					3,
					new RunOne<>(
						ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
						ImmutableList.of(
							Pair.of(RandomStroll.stroll(1F), 1),
							Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 1),
							Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 2)
						)
					)
				)
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IDLE_TIME, MemoryStatus.VALUE_ABSENT)
			),
			ImmutableSet.of(
				WWMemoryModuleTypes.SEARCHING_FOR_WATER,
				WWMemoryModuleTypes.LAYING_DOWN
			)
		);
	}

	private static void initSwimActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityWithConditions(
			Activity.SWIM,
			ImmutableList.of(
				Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
				Pair.of(1, new FollowTemptation(livingEntity -> 1.25F)),
				//Pair.of(2, StartAttacking.create(PenguinAi::canAttack, frog -> frog.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
				//Pair.of(3, TryFindLand.create(8, 1.5F)),
				Pair.of(
					5,
					new GateBehavior<>(
						ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
						ImmutableSet.of(),
						GateBehavior.OrderPolicy.ORDERED,
						GateBehavior.RunningPolicy.TRY_ALL,
						ImmutableList.of(
							Pair.of(RandomStroll.swim(1.5F), 1),
							Pair.of(RandomStroll.stroll(1F, true), 1),
							Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 1),
							Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 5)
						)
					)
				)
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static void initEscapeActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityWithConditions(
			WWActivities.ESCAPE,
			ImmutableList.of(
				Pair.of(0, PenguinFollowReturnPos.create(2F)),
				Pair.of(0, PenguinFindEscapePos.create(10, 2F))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.DIVE_TICKS, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void initJumpActivity(@NotNull Brain<Penguin> brain) {
		// TODO: [Treetrain1] Remake the DolphinJump thing as a behavior.
		/*
		brain.addActivityWithConditions(
			Activity.LONG_JUMP,
			ImmutableList.of(
				Pair.of(0, new LongJumpMidJump(TIME_BETWEEN_LONG_JUMPS, SoundEvents.FROG_STEP)),
				Pair.of(1, new PenguinLongJump<>(TIME_BETWEEN_LONG_JUMPS, 5, 16, 5F, penguin -> SoundEvents.GOAT_LONG_JUMP))
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.DIVE_TICKS, MemoryStatus.VALUE_ABSENT)
			)
		);
		 */
	}

	public static void updateActivity(@NotNull Penguin penguin) {
		penguin.getBrain().setActiveActivityToFirstValid(
			ImmutableList.of(
				WWActivities.STAND_UP,
				Activity.FIGHT,
				Activity.LONG_JUMP,
				WWActivities.ESCAPE,
				Activity.SWIM,
				WWActivities.SEARCH,
				Activity.IDLE
			)
		);
	}

	@NotNull
	private static Optional<List<Penguin>> getNearbyPenguins(@NotNull Penguin penguin) {
		return penguin.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_PENGUINS);
	}

	public static void removeAttackAndAngerTarget(@NotNull Penguin penguin) {
		Brain<Penguin> brain = penguin.getBrain();
		brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
		brain.eraseMemory(MemoryModuleType.ANGRY_AT);
		brain.eraseMemory(MemoryModuleType.UNIVERSAL_ANGER);
	}

	private static float getSpeedModifierChasing(@Nullable LivingEntity livingEntity) {
		return SPEED_MULTIPLIER_WHEN_ATTACKING;
	}

	@NotNull
	public static Ingredient getTemptations() {
		return Ingredient.of(WWItemTags.PENGUIN_FOOD);
	}

}
