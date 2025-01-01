/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai.butterfly;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.entity.Butterfly;
import net.frozenblock.wilderwild.entity.FlowerCow;
import net.frozenblock.wilderwild.entity.ai.ValidateOrSetHome;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ButterflyAi {
	protected static final List<SensorType<? extends Sensor<? super Butterfly>>> SENSOR_TYPES = List.of(
		SensorType.NEAREST_LIVING_ENTITIES
	);
	protected static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
		MemoryModuleType.PATH,
		MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
		MemoryModuleType.WALK_TARGET,
		MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.HOME,
		WWMemoryModuleTypes.NATURAL,
		WWMemoryModuleTypes.HOME_VALIDATE_COOLDOWN
	);

	private ButterflyAi() {
	}

	@NotNull
	public static Brain.Provider<Butterfly> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
	}

	public static void setNatural(@NotNull Butterfly butterfly) {
		butterfly.getBrain().setMemory(WWMemoryModuleTypes.NATURAL, true);
	}

	@NotNull
	public static Brain<?> makeBrain(@NotNull Butterfly butterfly, @NotNull Brain<Butterfly> brain) {
		addCoreActivities(brain);
		addIdleActivities(butterfly, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	private static void addCoreActivities(@NotNull Brain<Butterfly> brain) {
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

	private static void addIdleActivities(@NotNull Butterfly butterfly, @NotNull Brain<Butterfly> brain) {
		brain.addActivity(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(1, StayCloseToTarget.create(ButterflyAi::getHomeTarget, entity -> true, 7, 16, 1F)),
				Pair.of(2, StayCloseToTarget.create(ButterflyAi::getLookTarget, entity -> true, 5, 5, 1F)),
				Pair.of(3, SetEntityLookTarget.create(
					livingEntity -> livingEntity.isAlive()
						&& !livingEntity.isSpectator()
						&& livingEntity instanceof FlowerCow flowerCow
						&& flowerCow.hasFlowersLeft()
						&& !flowerCow.isBaby(),
					8F
				)),
				Pair.of(4, new RunOne<>(
					ImmutableList.of(
						Pair.of(RandomStroll.fly(1F), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 2)
					)
				))
			)
		);
	}

	public static void updateActivities(@NotNull Butterfly butterfly) {
		butterfly.getBrain().setActiveActivityToFirstValid(List.of(Activity.IDLE));
	}

	public static void rememberHome(@NotNull LivingEntity butterfly, @NotNull BlockPos pos) {
		GlobalPos globalPos = GlobalPos.of(butterfly.level().dimension(), pos);
		butterfly.getBrain().setMemory(MemoryModuleType.HOME, globalPos);
	}

	private static boolean shouldGoTowardsHome(@NotNull LivingEntity butterfly, @NotNull GlobalPos pos) {
		return ((Butterfly) butterfly).hasHome() && butterfly.level().dimension() == pos.dimension();
	}

	@NotNull
	private static Optional<PositionTracker> getHomeTarget(@NotNull LivingEntity butterfly) {
		Brain<?> brain = butterfly.getBrain();
		Optional<GlobalPos> home = brain.getMemory(MemoryModuleType.HOME);
		if (home.isPresent()) {
			GlobalPos globalPos = home.get();
			if (shouldGoTowardsHome(butterfly, globalPos)) {
				return Optional.of(new BlockPosTracker(randomPosAround(globalPos.pos(), butterfly.level())));
			}
		}

		return Optional.empty();
	}

	@NotNull
	private static Optional<PositionTracker> getLookTarget(@NotNull LivingEntity butterfly) {
		return butterfly.getBrain().getMemory(MemoryModuleType.LOOK_TARGET);
	}

	@NotNull
	private static BlockPos randomPosAround(@NotNull BlockPos pos, @NotNull Level level) {
		return pos.offset(level.random.nextIntBetweenInclusive(-7, 7), level.random.nextIntBetweenInclusive(-7, 7), level.random.nextIntBetweenInclusive(-7, 7));
	}
}
