/*
 * Copyright 2025-2026 FrozenBlock
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
import net.frozenblock.wilderwild.tag.WWFluidTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
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
import net.minecraft.world.entity.ai.behavior.TryFindLiquid;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class PenguinAi {
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
		WWSensorTypes.PENGUIN_SPECIFIC_SENSOR.get(),
		WWSensorTypes.PENGUIN_TEMPTATIONS.get(),
		WWSensorTypes.PENGUIN_ATTACKABLES.get(),
		SensorType.IS_IN_WATER,
		WWSensorTypes.LAND_POS_SENSOR.get(),
		WWSensorTypes.TRACKED_BOAT_SENSOR.get()
	);
	private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
		MemoryModuleType.ATTACK_COOLING_DOWN,
		MemoryModuleType.HOME,
		WWMemoryModuleTypes.IDLE_TIME.get()
	);

	private static final BehaviorControl<Penguin> HUNTING_COOLDOWN_SETTER = BehaviorBuilder.create(
		instance -> instance.group(instance.registered(MemoryModuleType.HAS_HUNTING_COOLDOWN))
		.apply(instance, hasHuntingCOoldown -> (level, penguin, l) -> {
			hasHuntingCOoldown.setWithExpiry(true, 600);
			return true;
		})
	);

	public static Brain.Provider<Penguin> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES, PenguinAi::getActivities);
	}

	public static List<ActivityData<Penguin>> getActivities(final Penguin body) {
		return List.of(
			initCoreActivity(),
			initStandUpActivity(),
			initChaseActivity(),
			initCallActivity(),
			initMeetActivity(),
			initFightActivity(body),
			initIdleActivity(),
			initPreSearchActivity(),
			initSearchActivity(),
			initSwimActivity(),
			initEscapeActivity(),
			initPostEscapeActivity()
		);
	}

	private static ActivityData<Penguin> initCoreActivity() {
		return ActivityData.create(
			Activity.CORE,
			0,
			ImmutableList.of(
				new AnimalPanic<>(2F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				new PenguinLayEgg(WWBlocks.PENGUIN_EGG.get()),
				new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new CountDownCooldownTicks(WWMemoryModuleTypes.IDLE_TIME.get()),
				new CountDownCooldownTicks(WWMemoryModuleTypes.DIVE_TICKS.get()),
				new CountDownCooldownTicks(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS.get())
			)
		);
	}

	private static ActivityData<Penguin> initStandUpActivity() {
		return ActivityData.create(
			WWActivities.STAND_UP.get(),
			10,
			ImmutableList.of(new PenguinStandUp<>(STAND_UP_DURATION)),
			WWMemoryModuleTypes.STANDING_UP.get()
		);
	}

	private static ActivityData<Penguin> initChaseActivity() {
		return ActivityData.create(
			WWActivities.CHASE.get(),
			ImmutableList.of(
				Pair.of(0, SetTrackedBoatLookTarget.create()),
				Pair.of(0, PenguinBoostBoat.create()),
				Pair.of(0, SetWalkTargetFromLookTarget.create(
					entity -> true,
					entity -> 2F,
					2
				)),
				Pair.of(0, EraseMemoryIf.create(BehaviorUtils::isBreeding, WWMemoryModuleTypes.TRACKED_BOAT.get()))
			),
			ImmutableSet.of(
				Pair.of(WWMemoryModuleTypes.TRACKED_BOAT.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static ActivityData<Penguin> initCallActivity() {
		return ActivityData.create(
			WWActivities.CALL.get(),
			ImmutableList.of(
				Pair.of(20, new PenguinCall<>(CALL_DURATION))
			),
			ImmutableSet.of(
				Pair.of(WWMemoryModuleTypes.WANTS_TO_CALL.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.NEARBY_PENGUINS.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT)
			),
			ImmutableSet.of(
				WWMemoryModuleTypes.WANTS_TO_CALL.get(),
				WWMemoryModuleTypes.CALLING.get()
			)
		);
	}

	private static ActivityData<Penguin> initMeetActivity() {
		return ActivityData.create(
			Activity.MEET,
			ImmutableList.of(
				Pair.of(0, PenguinMeetCaller.create()),
				Pair.of(0, SetWalkTargetFromLookTarget.create(
					entity -> true,
					entity -> 1.25F,
					2
				)),
				Pair.of(0, EraseMemoryIf.create(BehaviorUtils::isBreeding, WWMemoryModuleTypes.CALLER.get()))
			),
			ImmutableSet.of(
				Pair.of(WWMemoryModuleTypes.CALLER.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static ActivityData<Penguin> initFightActivity(final Penguin body) {
		return ActivityData.create(
			Activity.FIGHT,
			10,
			ImmutableList.of(
				HUNTING_COOLDOWN_SETTER,
				StopAttackingIfTargetInvalid.create(
					(level, entity) -> !body.canTargetEntity(entity), PenguinAi::onTargetInvalid, true
				),
				SetEntityLookTarget.create(entity -> isTarget(body, entity), (float) body.getAttributeValue(Attributes.FOLLOW_RANGE)),
				SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(PenguinAi::getSpeedModifierChasing),
				MeleeAttack.create(30),
				EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
			),
			MemoryModuleType.ATTACK_TARGET
		);
	}

	private static ActivityData<Penguin> initIdleActivity() {
		return ActivityData.create(
			Activity.IDLE,
			0,
			ImmutableList.of(
				SetEntityLookTargetSometimes.create(8F, UniformInt.of(30, 60)),
				BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F),
				new AnimalMakeLove(WWEntityTypes.PENGUIN.get(), SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2),
				new FollowTemptation(entity -> 1.25F),
				TryFindLand.create(6, 1F),
				new RunOne<>(
					ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
					ImmutableList.of(
						Pair.of(RandomStroll.stroll(1F), 1),
						Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 1),
						Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 2)
					)
				)
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.SEARCHING_FOR_WATER.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IDLE_TIME.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.LAYING_DOWN.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static ActivityData<Penguin> initPreSearchActivity() {
		return ActivityData.create(
			WWActivities.PRE_SEARCH.get(),
			0,
			ImmutableList.of(
				new PenguinPreSearch<>()
			),
			ImmutableSet.of(
				Pair.of(WWMemoryModuleTypes.NEARBY_PENGUINS.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.IDLE_TIME.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.WANTS_TO_CALL.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.ESCAPING.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.STARTING_SEARCH.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.SEARCHING_FOR_WATER.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static ActivityData<Penguin> initSearchActivity() {
		return ActivityData.create(
			WWActivities.SEARCH.get(),
			ImmutableList.of(
				Pair.of(0, new PenguinLayDown<>()),
				Pair.of(0, SetEntityLookTargetSometimes.create(8F, UniformInt.of(30, 60))),
				Pair.of(1, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F)),
				Pair.of(2, new AnimalMakeLove(WWEntityTypes.PENGUIN.get(), SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)),
				Pair.of(3, new FollowTemptation(entity -> 1.25F)),
				Pair.of(4, TryFindLiquid.create(8, 0.8F, WWFluidTags.PENGUIN_TRIES_TO_FIND)),
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
				Pair.of(WWMemoryModuleTypes.IDLE_TIME.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.ESCAPING.get(), MemoryStatus.VALUE_ABSENT)
			),
			ImmutableSet.of(
				WWMemoryModuleTypes.SEARCHING_FOR_WATER.get(),
				WWMemoryModuleTypes.LAYING_DOWN.get()
			)
		);
	}

	private static ActivityData<Penguin> initSwimActivity() {
		return ActivityData.create(
			Activity.SWIM,
			1,
			ImmutableList.of(
				BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F),
				new AnimalMakeLove(WWEntityTypes.PENGUIN.get(), SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2),
				new FollowTemptation(entity -> 1.25F),
				StartAttacking.create(PenguinAi::canAttack, (level, penguin) -> penguin.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE)),
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
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.DIVE_TICKS.get(), MemoryStatus.VALUE_PRESENT)
			)
		);
	}

	private static ActivityData<Penguin> initEscapeActivity() {
		return ActivityData.create(
			WWActivities.ESCAPE.get(),
			ImmutableList.of(
				Pair.of(0, new PenguinMarkAsEscaping<>()),
				Pair.of(0, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 0.6F)),
				Pair.of(1, PenguinFollowReturnPos.create(1.5F)),
				Pair.of(1, PenguinFindEscapePos.create(10, 1.5F)),
				Pair.of(2, new AnimalMakeLove(WWEntityTypes.PENGUIN.get(), SPEED_MULTIPLIER_WHEN_MAKING_LOVE, 2)),
				Pair.of(3, new FollowTemptation(entity -> 1.25F)),
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
				Pair.of(WWMemoryModuleTypes.DIVE_TICKS.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.STARTING_SEARCH.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	private static ActivityData<Penguin> initPostEscapeActivity() {
		return ActivityData.create(
			WWActivities.POST_ESCAPE.get(),
			0,
			ImmutableList.of(
				new PenguinPostEscape<>()
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT),
				Pair.of(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.DIVE_TICKS.get(), MemoryStatus.VALUE_ABSENT),
				Pair.of(WWMemoryModuleTypes.ESCAPING.get(), MemoryStatus.VALUE_PRESENT),
				Pair.of(WWMemoryModuleTypes.STARTING_SEARCH.get(), MemoryStatus.VALUE_ABSENT)
			)
		);
	}

	public static void updateActivity(final Penguin body) {
		if (!body.isBaby()) {
			body.getBrain().setActiveActivityToFirstValid(
				ImmutableList.of(
					WWActivities.STAND_UP.get(),
					WWActivities.CHASE.get(),
					WWActivities.CALL.get(),
					Activity.MEET,
					Activity.FIGHT,
					WWActivities.PRE_SEARCH.get(),
					WWActivities.ESCAPE.get(),
					WWActivities.POST_ESCAPE.get(),
					WWActivities.SEARCH.get(),
					Activity.SWIM,
					Activity.IDLE
				)
			);
		} else {
			body.getBrain().setActiveActivityToFirstValid(
				ImmutableList.of(
					WWActivities.STAND_UP.get(),
					WWActivities.CHASE.get(),
					Activity.MEET,
					WWActivities.ESCAPE.get(),
					WWActivities.POST_ESCAPE.get(),
					WWActivities.SEARCH.get(),
					Activity.SWIM,
					Activity.IDLE
				)
			);
		}
	}

	public static Optional<List<Penguin>> getNearbyPenguins(Penguin body) {
		return body.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_PENGUINS.get());
	}

	private static boolean isTarget(Penguin body, LivingEntity entity) {
		return body.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(possibleEntity -> possibleEntity == entity).isPresent();
	}

	private static float getSpeedModifierChasing(@Nullable LivingEntity body) {
		return SPEED_MULTIPLIER_WHEN_ATTACKING;
	}

	private static void onTargetInvalid(ServerLevel level, Penguin body, LivingEntity target) {
		if (body.getTarget() == target) body.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		body.getNavigation().stop();
	}

	private static boolean canAttack(ServerLevel level, Penguin body) {
		return !body.isBaby() && !BehaviorUtils.isBreeding(body) && body.getBrain().checkMemory(MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryStatus.VALUE_ABSENT);
	}

	public static void addCallMemoryIfPenguinsClose(Penguin body) {
		if (!hasNearbyPenguins(body)) return;
		final Brain<Penguin> brain = body.getBrain();
		if (brain.checkMemory(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS.get(), MemoryStatus.VALUE_ABSENT)) {
			brain.setMemoryWithExpiry(WWMemoryModuleTypes.WANTS_TO_CALL.get(), Unit.INSTANCE, 12000L);
		}
	}

	public static boolean hasNearbyPenguins(Penguin body) {
		return !getNearbyPenguins(body).orElse(List.of()).isEmpty();
	}

	public static void addCallerMemoryToNearbyPenguins(Penguin body) {
		final UUID callerUUID = body.getUUID();
		final List<Penguin> penguins = PenguinAi.getNearbyPenguins(body).orElse(List.of());
		final Brain<Penguin> callerBrain = body.getBrain();
		final Optional<Integer> idleTime = callerBrain.getMemory(WWMemoryModuleTypes.IDLE_TIME.get());

		penguins.forEach(penguin -> {
			if (penguin == body) return;
			final Brain<Penguin> brain = penguin.getBrain();
			brain.setMemoryWithExpiry(WWMemoryModuleTypes.CALLER.get(), callerUUID, 400L);
			brain.setMemory(WWMemoryModuleTypes.CALL_COOLDOWN_TICKS.get(), 400);
			brain.eraseMemory(WWMemoryModuleTypes.WANTS_TO_CALL.get());
			idleTime.ifPresentOrElse(
				time -> brain.setMemory(WWMemoryModuleTypes.IDLE_TIME.get(), Math.max(time + penguin.getRandom().nextInt(0, 100), 0)),
				() -> brain.eraseMemory(WWMemoryModuleTypes.IDLE_TIME.get())
			);
		});
	}

	public static Optional<LivingEntity> getCaller(LivingEntity body, UUID callerID) {
		final Optional<List<Penguin>> penguins = body.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_PENGUINS.get());
		if (penguins.isPresent()) {
			final List<Penguin> penguinList = penguins.get();
			for (Penguin penguin : penguinList) {
				if (penguin.getUUID().equals(callerID)) return Optional.of(penguin);
			}
		}
		return Optional.empty();
	}

	public static Predicate<ItemStack> getTemptations() {
		return itemStack -> itemStack.is(WWItemTags.PENGUIN_FOOD);
	}
}
