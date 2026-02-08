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

package net.frozenblock.wilderwild.entity.ai.firefly;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.ai.ValidateOrSetHome;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;

public class FireflyAi {
	protected static final List<SensorType<? extends Sensor<? super Firefly>>> SENSOR_TYPES = List.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		WWSensorTypes.FIREFLY_SPECIFIC_SENSOR,
		WWSensorTypes.FIREFLY_LEADER_SENSOR
	);
	protected static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
		MemoryModuleType.HOME,
		WWMemoryModuleTypes.NEARBY_FIREFLIES,
		WWMemoryModuleTypes.NATURAL,
		WWMemoryModuleTypes.HOME_VALIDATE_COOLDOWN
	);

	private FireflyAi() {
	}

	public static void setNatural(Firefly firefly) {
		firefly.getBrain().setMemory(WWMemoryModuleTypes.NATURAL, Unit.INSTANCE);
	}

	public static void setSwarmLeader(Firefly firefly) {
		firefly.getBrain().setMemory(WWMemoryModuleTypes.IS_SWARM_LEADER, true);
	}

	public static Brain.Provider<Firefly> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES, body -> getActivities());
	}

	protected static List<ActivityData<Firefly>> getActivities() {
		return List.of(initCoreActivity(), initIdleActivity());
	}

	private static ActivityData<Firefly> initCoreActivity() {
		return ActivityData.create(
			Activity.CORE,
			0,
			ImmutableList.of(
				new Swim<>(0.8F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink(),
				ValidateOrSetHome.create(),
				new CountDownCooldownTicks(WWMemoryModuleTypes.HOME_VALIDATE_COOLDOWN)
			)
		);
	}

	private static ActivityData<Firefly> initIdleActivity() {
		return ActivityData.create(
			Activity.CORE,
			1,
			ImmutableList.of(
				new FireflyHide(1.75F, 10, 8),
				new FireflyMoveToBush(1.25F, 10, 8, 5),
				StayCloseToTarget.create(FireflyAi::getHomeTarget, entity -> true, 7, 16, 1.25F),
				StayCloseToTarget.create(FireflyAi::getSwarmLeaderTarget, entity -> WWEntityConfig.FIREFLY_SWARM.get(), 2, 3, 1.75F),
				new RunOne<>(
					ImmutableList.of(
						Pair.of(RandomStroll.fly(1.25F), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1.25F, 3), 2),
						Pair.of(new DoNothing(30, 60), 1)
					)
				)
			)
		);
	}

	public static void updateActivity(Firefly firefly) {
		firefly.getBrain().setActiveActivityToFirstValid(List.of(Activity.IDLE));
	}

	public static void rememberHome(LivingEntity firefly, BlockPos pos) {
		firefly.getBrain().setMemory(MemoryModuleType.HOME, GlobalPos.of(firefly.level().dimension(), pos));
	}

	private static boolean shouldGoTowardsHome(LivingEntity firefly, GlobalPos pos) {
		return ((Firefly) firefly).hasHome() && firefly.level().dimension() == pos.dimension() && !((Firefly) firefly).shouldHide();
	}

	private static Optional<PositionTracker> getSwarmLeaderTarget(LivingEntity firefly) {
		return !((Firefly)firefly).hasHome() ? firefly.getBrain().getMemory(WWMemoryModuleTypes.SWARM_LEADER_TRACKER) : Optional.empty();
	}

	private static Optional<PositionTracker> getHomeTarget(LivingEntity firefly) {
		final Optional<GlobalPos> home = firefly.getBrain().getMemory(MemoryModuleType.HOME);
		if (home.isPresent()) {
			final GlobalPos globalPos = home.get();
			if (shouldGoTowardsHome(firefly, globalPos)) return Optional.of(new BlockPosTracker(randomPosAround(globalPos.pos(), firefly.level())));
		}

		return Optional.empty();
	}

	private static BlockPos randomPosAround(BlockPos pos, Level level) {
		return pos.offset(level.getRandom().nextIntBetweenInclusive(-7, 7), level.getRandom().nextIntBetweenInclusive(-7, 7), level.getRandom().nextIntBetweenInclusive(-7, 7));
	}

	public static List<Firefly> getNearbyFirefliesInRank(Firefly firefly, boolean searchingForLeader) {
		return new ArrayList<>(firefly.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_FIREFLIES).orElse(ImmutableList.of()))
			.stream()
			.filter(otherFirefly -> otherFirefly.isSwarmLeader() == searchingForLeader)
			.sorted(Comparator.comparingDouble(firefly::distanceToSqr))
			.toList();
	}

	public static void transferLeadershipToRandomFirefly(Firefly firefly) {
		final Brain<Firefly> brain = firefly.getBrain();
		final List<Firefly> nonLeaderFireflies = getNearbyFirefliesInRank(firefly, false);

		if (!nonLeaderFireflies.isEmpty()) {
			transferLeadershipTo(firefly, nonLeaderFireflies.getFirst());
			return;
		}

		brain.eraseMemory(WWMemoryModuleTypes.IS_SWARM_LEADER);
	}

	private static void transferLeadershipTo(Firefly firefly, Firefly newLeader) {
		FireflyAi.setSwarmLeader(newLeader);
		firefly.getBrain().eraseMemory(WWMemoryModuleTypes.IS_SWARM_LEADER);
	}
}
