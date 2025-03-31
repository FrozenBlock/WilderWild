/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.ai.firefly;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
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
import net.minecraft.world.entity.LivingEntity;
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
import org.jetbrains.annotations.NotNull;

public class FireflyAi {
	protected static final List<SensorType<? extends Sensor<? super Firefly>>> SENSOR_TYPES = List.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		WWSensorTypes.FIREFLY_SPECIFIC_SENSOR,
		WWSensorTypes.FIREFLY_LEADER_SENSOR
	);
	protected static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
		MemoryModuleType.PATH,
		MemoryModuleType.NEAREST_LIVING_ENTITIES,
		MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
		MemoryModuleType.WALK_TARGET,
		MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.HOME,
		WWMemoryModuleTypes.NEARBY_FIREFLIES,
		WWMemoryModuleTypes.NATURAL,
		WWMemoryModuleTypes.HOME_VALIDATE_COOLDOWN,
		WWMemoryModuleTypes.IS_SWARM_LEADER,
		WWMemoryModuleTypes.SWARM_LEADER_TRACKER
	);

	private FireflyAi() {
	}

	public static void setNatural(@NotNull Firefly firefly) {
		firefly.getBrain().setMemory(WWMemoryModuleTypes.NATURAL, true);
	}

	public static void setSwarmLeader(@NotNull Firefly firefly) {
		firefly.getBrain().setMemory(WWMemoryModuleTypes.IS_SWARM_LEADER, true);
	}

	@NotNull
	public static Brain.Provider<Firefly> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
	}

	@NotNull
	public static Brain<?> makeBrain(@NotNull Firefly firefly, @NotNull Brain<Firefly> brain) {
		addCoreActivities(brain);
		addIdleActivities(firefly, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void addCoreActivities(@NotNull Brain<Firefly> brain) {
		brain.addActivity(
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

	private static void addIdleActivities(@NotNull Firefly firefly, @NotNull Brain<Firefly> brain) {
		brain.addActivity(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(1, new FireflyHide(firefly, 1.75F, 10, 8)),
				Pair.of(2, new FireflyMoveToBush(firefly, 1.25F, 10, 8, 5)),
				Pair.of(3, StayCloseToTarget.create(FireflyAi::getHomeTarget, entity -> true, 7, 16, 1.25F)),
				Pair.of(4, StayCloseToTarget.create(FireflyAi::getSwarmLeaderTarget, entity -> WWEntityConfig.FIREFLY_SWARMS, 2, 3, 1.75F)),
				Pair.of(5, new RunOne<>(
					ImmutableList.of(
						Pair.of(RandomStroll.fly(1.25F), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1.25F, 3), 2),
						Pair.of(new DoNothing(30, 60), 1)
					)
				))
			)
		);
	}

	public static void updateActivities(@NotNull Firefly firefly) {
		firefly.getBrain().setActiveActivityToFirstValid(List.of(Activity.IDLE));
	}

	public static void rememberHome(@NotNull LivingEntity firefly, @NotNull BlockPos pos) {
		Brain<?> brain = firefly.getBrain();
		GlobalPos globalPos = GlobalPos.of(firefly.level().dimension(), pos);
		brain.setMemory(MemoryModuleType.HOME, globalPos);
	}

	private static boolean shouldGoTowardsHome(@NotNull LivingEntity firefly, @NotNull GlobalPos pos) {
		Level level = firefly.level();
		return ((Firefly) firefly).hasHome() && level.dimension() == pos.dimension() && !((Firefly) firefly).shouldHide();
	}

	@NotNull
	private static Optional<PositionTracker> getSwarmLeaderTarget(@NotNull LivingEntity firefly) {
		return !((Firefly)firefly).hasHome() ? firefly.getBrain().getMemory(WWMemoryModuleTypes.SWARM_LEADER_TRACKER) : Optional.empty();
	}

	@NotNull
	private static Optional<PositionTracker> getHomeTarget(@NotNull LivingEntity firefly) {
		Brain<?> brain = firefly.getBrain();
		Optional<GlobalPos> home = brain.getMemory(MemoryModuleType.HOME);
		if (home.isPresent()) {
			GlobalPos globalPos = home.get();
			if (shouldGoTowardsHome(firefly, globalPos)) {
				return Optional.of(new BlockPosTracker(randomPosAround(globalPos.pos(), firefly.level())));
			}
		}

		return Optional.empty();
	}

	@NotNull
	private static BlockPos randomPosAround(@NotNull BlockPos pos, @NotNull Level level) {
		return pos.offset(level.random.nextIntBetweenInclusive(-7, 7), level.random.nextIntBetweenInclusive(-7, 7), level.random.nextIntBetweenInclusive(-7, 7));
	}

	public static List<Firefly> getNearbyFirefliesInRank(@NotNull Firefly firefly, boolean searchingForLeader) {
		return new ArrayList<>(firefly.getBrain().getMemory(WWMemoryModuleTypes.NEARBY_FIREFLIES).orElse(ImmutableList.of()))
			.stream()
			.filter(otherFirefly -> otherFirefly.isSwarmLeader() == searchingForLeader)
			.sorted(Comparator.comparingDouble(firefly::distanceToSqr))
			.toList();
	}

	public static void transferLeadershipToRandomFirefly(@NotNull Firefly firefly) {
		Brain<Firefly> brain = firefly.getBrain();
		List<Firefly> nonLeaderFireflies = getNearbyFirefliesInRank(firefly, false);

		if (!nonLeaderFireflies.isEmpty()) {
			transferLeadershipTo(firefly, nonLeaderFireflies.getFirst());
			return;
		}

		brain.eraseMemory(WWMemoryModuleTypes.IS_SWARM_LEADER);
	}

	private static void transferLeadershipTo(@NotNull Firefly firefly, Firefly newLeader) {
		FireflyAi.setSwarmLeader(newLeader);
		firefly.getBrain().eraseMemory(WWMemoryModuleTypes.IS_SWARM_LEADER);
	}
}
