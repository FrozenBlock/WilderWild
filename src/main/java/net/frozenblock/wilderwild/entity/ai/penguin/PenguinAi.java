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
import java.util.UUID;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWActivities;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.TryFindLand;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PenguinAi {
	private static final float SPEED_MULTIPLIER_WHEN_ATTACKING = 1.15F;
	private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 1.15F;
	private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(3, 16);
	public static final UniformInt IDLE_TIME = UniformInt.of(1200, 2400);
	public static final UniformInt DIVE_TIME = UniformInt.of(400, 1200);

	public static final int STAND_UP_DURATION = 48;
	public static final int CALL_DURATION = 60;

	private static final ImmutableList<SensorType<? extends Sensor<? super Penguin>>> SENSOR_TYPES = ImmutableList.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.NEAREST_ADULT,
		SensorType.NEAREST_PLAYERS,
		WWSensorTypes.PENGUIN_SPECIFIC_SENSOR,
		WWSensorTypes.PENGUIN_TEMPTATIONS,
		WWSensorTypes.PENGUIN_ATTACKABLES,
		SensorType.IS_IN_WATER,
		WWSensorTypes.LAND_POS_SENSOR,
		WWSensorTypes.TRACKED_BOAT_SENSOR
	);
	private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
		MemoryModuleType.IS_PANICKING,
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
		MemoryModuleType.ATTACK_TARGET,
		MemoryModuleType.NEAREST_ATTACKABLE,
		MemoryModuleType.NEAREST_LIVING_ENTITIES,
		MemoryModuleType.NEAREST_PLAYERS,
		MemoryModuleType.NEAREST_VISIBLE_PLAYER,
		MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
		WWMemoryModuleTypes.NEARBY_PENGUINS,
		WWMemoryModuleTypes.CLOSE_PENGUINS,
		MemoryModuleType.ATTACK_COOLING_DOWN,
		MemoryModuleType.HOME,
		MemoryModuleType.IS_IN_WATER,
		WWMemoryModuleTypes.IDLE_TIME,
		WWMemoryModuleTypes.DIVE_TICKS,
		MemoryModuleType.HAS_HUNTING_COOLDOWN,
		WWMemoryModuleTypes.LAYING_DOWN,
		WWMemoryModuleTypes.STARTING_SEARCH,
		WWMemoryModuleTypes.SEARCHING_FOR_WATER,
		WWMemoryModuleTypes.ESCAPING,
		WWMemoryModuleTypes.LAND_POS,
		WWMemoryModuleTypes.WATER_POS,
		WWMemoryModuleTypes.STANDING_UP,
		WWMemoryModuleTypes.TRACKED_BOAT,
		WWMemoryModuleTypes.WANTS_TO_CALL,
		WWMemoryModuleTypes.CALL_COOLDOWN_TICKS,
		WWMemoryModuleTypes.CALLING,
		WWMemoryModuleTypes.CALLER
	);

	private static final BehaviorControl<Penguin> HUNTING_COOLDOWN_SETTER = BehaviorBuilder.create(
		instance -> instance.group(instance.registered(MemoryModuleType.HAS_HUNTING_COOLDOWN))
			.apply(instance, memoryAccessor -> (world, penguin, l) -> {
				memoryAccessor.setWithExpiry(true, 600);
				return true;
			})
	);

	@NotNull
	public static Brain.Provider<Penguin> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
	}

	@NotNull
	public static Brain<?> makeBrain(@NotNull Penguin penguin, @NotNull Brain<Penguin> brain) {
		initCoreActivity(brain);
		initStandUpActivity(brain);
		initChaseActivity(brain);
		initCallActivity(brain);
		initMeetActivity(brain, penguin);
		initFightActivity(brain, penguin);
		initIdleActivity(brain);
		initPreSearchActivity(brain);
		initSearchActivity(brain);
		initSwimActivity(brain);
		initEscapeActivity(brain);
		initPostEscapeActivity(brain);
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
				new PenguinLayEgg(WWBlocks.PENGUIN_EGG),
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new CountDownCooldownTicks(WWMemoryModuleTypes.IDLE_TIME),
				new CountDownCooldownTicks(WWMemoryModuleTypes.DIVE_TICKS),
				new CountDownCooldownTicks(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS)
			)
		);
	}

	private static void initStandUpActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			WWActivities.STAND_UP,
			10,
			ImmutableList.of(new PenguinStandUp<>(STAND_UP_DURATION)),
			WWMemoryModuleTypes.STANDING_UP
		);
	}

	private static void initChaseActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityWithConditions(
			WWActivities.CHASE,
			ImmutableList.of(
				Pair.of(0, SetTrackedBoatLookTarget.create()),
				Pair.of(0, PenguinBoostBoat.create()),
				Pair.of(0, SetWalkTargetFromLookTarget.create(
					entity -> true,
					entity -> 2F,
					2
				)),
				Pair.of(0, EraseMemoryIf.create(BehaviorUtils::isBreeding, WWMemoryModuleTypes.TRACKED_BOAT))
			),
			ImmutableSet.of(
				Pair.of(WWMemoryModuleTypes.TRACKED_BOAT, MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static void initCallActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityAndRemoveMemoriesWhenStopped(
			WWActivities.CALL,
			ImmutableList.of(
				Pair.of(20, new PenguinCall<>(CALL_DURATION))
			),
			ImmutableSet.of(
				Pair.of(WWMemoryModuleTypes.WANTS_TO_CALL, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.NEARBY_PENGUINS, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_TEMPTED, MemoryStatus.VALUE_ABSENT)
			),
			ImmutableSet.of(
				WWMemoryModuleTypes.WANTS_TO_CALL,
				WWMemoryModuleTypes.CALLING
			)
		);
	}

	private static void initMeetActivity(@NotNull Brain<Penguin> brain, Penguin penguin) {
		brain.addActivityWithConditions(
			Activity.MEET,
			ImmutableList.of(
				Pair.of(0, PenguinMeetCaller.create()),
				Pair.of(0, SetWalkTargetFromLookTarget.create(
					entity -> true,
					entity -> 1.25F,
					2
				)),
				Pair.of(0, EraseMemoryIf.create(BehaviorUtils::isBreeding, WWMemoryModuleTypes.CALLER))
			),
			ImmutableSet.of(
				Pair.of(WWMemoryModuleTypes.CALLER, MemoryStatus.VALUE_PRESENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_TEMPTED, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void initFightActivity(@NotNull Brain<Penguin> brain, @NotNull Penguin penguin) {
		brain.addActivityAndRemoveMemoryWhenStopped(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				HUNTING_COOLDOWN_SETTER,
				StopAttackingIfTargetInvalid.create(
					(serverLevel, livingEntity) -> !penguin.canTargetEntity(livingEntity), PenguinAi::onTargetInvalid, true
				),
				SetEntityLookTarget.create(livingEntity -> isTarget(penguin, livingEntity), (float) penguin.getAttributeValue(Attributes.FOLLOW_RANGE)),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(PenguinAi::getSpeedModifierChasing),
				MeleeAttack.create(30),
				EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static void initIdleActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityWithConditions(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, SetEntityLookTargetSometimes.create(8F, UniformInt.of(30, 60))),
				Pair.of(1, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F)),
				Pair.of(2, new AnimalMakeLove(WWEntityTypes.PENGUIN, SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)),
				Pair.of(3, new FollowTemptation(livingEntity -> 1.25F)),
				Pair.of(4, TryFindLand.create(6, 1F)),
				Pair.of(
					5,
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
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.SEARCHING_FOR_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IDLE_TIME, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.LAYING_DOWN, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void initPreSearchActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityWithConditions(
			WWActivities.PRE_SEARCH,
			ImmutableList.of(
				Pair.of(0, new PenguinPreSearch<>())
			),
			ImmutableSet.of(
				Pair.of(WWMemoryModuleTypes.NEARBY_PENGUINS, MemoryStatus.VALUE_PRESENT),
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IDLE_TIME, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.WANTS_TO_CALL, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.ESCAPING, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.STARTING_SEARCH, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.SEARCHING_FOR_WATER, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void initSearchActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityAndRemoveMemoriesWhenStopped(
			WWActivities.SEARCH,
			ImmutableList.of(
				Pair.of(0, new PenguinLayDown<>()),
				Pair.of(0, SetEntityLookTargetSometimes.create(8F, UniformInt.of(30, 60))),
				Pair.of(1, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F)),
				Pair.of(2, new AnimalMakeLove(WWEntityTypes.PENGUIN, SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)),
				Pair.of(3, new FollowTemptation(livingEntity -> 1.25F)),
				Pair.of(4, TryFindWater.create(8, 0.8F)),
				Pair.of(5, PenguinReturnToWater.create(0.8F)),
				Pair.of(
					6,
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
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IDLE_TIME, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.ESCAPING, MemoryStatus.VALUE_ABSENT)
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
				Pair.of(1, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F)),
				Pair.of(2, new AnimalMakeLove(WWEntityTypes.PENGUIN, SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)),
				Pair.of(3, new FollowTemptation(livingEntity -> 1.25F)),
				Pair.of(4, StartAttacking.create(PenguinAi::canAttack, (serverLevel, penguin) -> penguin.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
				Pair.of(
					5,
					new GateBehavior<>(
						ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
						ImmutableSet.of(),
						GateBehavior.OrderPolicy.ORDERED,
						GateBehavior.RunningPolicy.TRY_ALL,
						ImmutableList.of(
							Pair.of(RandomStroll.swim(1F), 2),
							Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 3),
							Pair.of(BehaviorBuilder.triggerIf(Entity::isInWater), 5)
						)
					)
				)
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.DIVE_TICKS, MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static void initEscapeActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityWithConditions(
			WWActivities.ESCAPE,
			ImmutableList.of(
				Pair.of(0, new PenguinMarkAsEscaping<>()),
				Pair.of(0, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F)),
				Pair.of(1, PenguinFollowReturnPos.create(1.5F)),
				Pair.of(1, PenguinFindEscapePos.create(10, 1.5F)),
				Pair.of(2, new AnimalMakeLove(WWEntityTypes.PENGUIN, SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)),
				Pair.of(3, new FollowTemptation(livingEntity -> 1.25F)),
				Pair.of(
					4,
					new GateBehavior<>(
						ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
						ImmutableSet.of(),
						GateBehavior.OrderPolicy.ORDERED,
						GateBehavior.RunningPolicy.TRY_ALL,
						ImmutableList.of(
							Pair.of(RandomStroll.swim(1F), 1),
							Pair.of(RandomStroll.stroll(1F, true), 1),
							Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 1),
							Pair.of(BehaviorBuilder.triggerIf(Entity::isInWater), 5)
						)
					)
				)
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.DIVE_TICKS, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.STARTING_SEARCH, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static void initPostEscapeActivity(@NotNull Brain<Penguin> brain) {
		brain.addActivityWithConditions(
			WWActivities.POST_ESCAPE,
			ImmutableList.of(
				Pair.of(0, new PenguinPostEscape<>())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.DIVE_TICKS, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.ESCAPING, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.STARTING_SEARCH, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	public static void updateActivity(@NotNull Penguin penguin) {
		if (!penguin.isBaby()) {
			penguin.getBrain().setActiveActivityToFirstValid(
				ImmutableList.of(
					WWActivities.STAND_UP,
					WWActivities.CHASE,
					WWActivities.CALL,
					Activity.MEET,
					Activity.FIGHT,
					WWActivities.PRE_SEARCH,
					WWActivities.ESCAPE,
					WWActivities.POST_ESCAPE,
					WWActivities.SEARCH,
					Activity.SWIM,
					Activity.IDLE
				)
			);
		} else {
			penguin.getBrain().setActiveActivityToFirstValid(
				ImmutableList.of(
					WWActivities.STAND_UP,
					WWActivities.CHASE,
					Activity.MEET,
					WWActivities.ESCAPE,
					WWActivities.POST_ESCAPE,
					WWActivities.SEARCH,
					Activity.SWIM,
					Activity.IDLE
				)
			);
		}
	}

	@NotNull
	public static Optional<List<Penguin>> getNearbyPenguins(@NotNull Penguin penguin) {
		return penguin.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_PENGUINS);
	}

	@NotNull
	public static Optional<List<Penguin>> getClosePenguins(@NotNull Penguin penguin) {
		return penguin.getBrain().getMemory(WWMemoryModuleTypes.CLOSE_PENGUINS);
	}

	private static boolean isTarget(@NotNull Penguin penguin, @NotNull LivingEntity livingEntity) {
		return penguin.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity2 -> livingEntity2 == livingEntity).isPresent();
	}

	private static boolean isCaller(@NotNull Penguin penguin, @NotNull LivingEntity livingEntity) {
		return penguin.getBrain().getMemory(WWMemoryModuleTypes.CALLER).filter(uuid -> livingEntity.getUUID().equals(uuid)).isPresent();
	}

	private static float getSpeedModifierChasing(@Nullable LivingEntity livingEntity) {
		return SPEED_MULTIPLIER_WHEN_ATTACKING;
	}

	private static void onTargetInvalid(ServerLevel serverLevel, @NotNull Penguin penguin, @NotNull LivingEntity target) {
		if (penguin.getTarget() == target) {
			penguin.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		}
		penguin.getNavigation().stop();
	}

	private static boolean canAttack(ServerLevel serverLevel, @NotNull Penguin penguin) {
		return !penguin.isBaby() && !BehaviorUtils.isBreeding(penguin) && penguin.getBrain().checkMemory(MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryStatus.VALUE_ABSENT);
	}

	public static void addCallMemoryIfPenguinsClose(@NotNull Penguin penguin) {
		Brain<Penguin> brain = penguin.getBrain();
		if (hasNearbyPenguins(penguin)) {
			if (brain.checkMemory(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT)) {
				brain.setMemoryWithExpiry(WWMemoryModuleTypes.WANTS_TO_CALL, Unit.INSTANCE, 12000L);
			}
		}
	}

	public static boolean hasNearbyPenguins(@NotNull Penguin penguin) {
		return !getNearbyPenguins(penguin).orElse(List.of()).isEmpty();
	}

	public static void addCallerMemoryToNearbyPenguins(@NotNull Penguin caller) {
		UUID uuid = caller.getUUID();
		List<Penguin> penguins = PenguinAi.getNearbyPenguins(caller).orElse(List.of());
		Brain<Penguin> callerBrain = caller.getBrain();
		Optional<Integer> idleTime = callerBrain.getMemory(WWMemoryModuleTypes.IDLE_TIME);

		penguins.forEach(penguin -> {
			if (penguin != caller) {
				Brain<Penguin> brain = penguin.getBrain();
				brain.setMemoryWithExpiry(WWMemoryModuleTypes.CALLER, uuid, 400L);
				brain.setMemory(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS, 400);
				brain.eraseMemory(WWMemoryModuleTypes.WANTS_TO_CALL);
				idleTime.ifPresentOrElse(
					time -> brain.setMemory(WWMemoryModuleTypes.IDLE_TIME, Math.max(time + penguin.getRandom().nextInt(0, 100), 0)),
					() -> brain.eraseMemory(WWMemoryModuleTypes.IDLE_TIME)
				);
			}
		});
	}

	public static Optional<LivingEntity> getCaller(@NotNull LivingEntity entity, UUID callerID) {
		Optional<List<Penguin>> penguins = entity.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_PENGUINS);
		if (penguins.isPresent()) {
			List<Penguin> penguinList = penguins.get();
			for (Penguin penguin : penguinList) {
				if (penguin.getUUID().equals(callerID)) {
					return Optional.of(penguin);
				}
			}
		}
		return Optional.empty();
	}

	public static Predicate<ItemStack> getTemptations() {
		return itemStack -> itemStack.is(WWItemTags.PENGUIN_FOOD);
	}

}
