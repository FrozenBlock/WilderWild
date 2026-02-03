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

package net.frozenblock.wilderwild.entity.ai.butterfly;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.entity.Butterfly;
import net.frozenblock.wilderwild.entity.FlowerCow;
import net.frozenblock.wilderwild.entity.ai.ValidateOrSetHome;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
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

public class ButterflyAi {
	protected static final List<SensorType<? extends Sensor<? super Butterfly>>> SENSOR_TYPES = List.of(
		SensorType.NEAREST_LIVING_ENTITIES
	);
	protected static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
		MemoryModuleType.HOME,
		WWMemoryModuleTypes.NATURAL,
		WWMemoryModuleTypes.HOME_VALIDATE_COOLDOWN
	);

	private ButterflyAi() {
	}

	public static Brain.Provider<Butterfly> brainProvider() {
		return Brain.provider(MEMORY_TYPES, SENSOR_TYPES, butterfly -> getActivities());
	}

	protected static List<ActivityData<Butterfly>> getActivities() {
		return List.of(initCoreActivity(), initIdleActivity());
	}

	public static void setNatural(Butterfly butterfly) {
		butterfly.getBrain().setMemory(WWMemoryModuleTypes.NATURAL, Unit.INSTANCE);
	}

	private static ActivityData<Butterfly> initCoreActivity() {
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

	private static ActivityData<Butterfly> initIdleActivity() {
		return ActivityData.create(
			Activity.IDLE,
			1,
			ImmutableList.of(
				StayCloseToTarget.create(ButterflyAi::getHomeTarget, entity -> true, 7, 16, 1F),
				StayCloseToTarget.create(ButterflyAi::getLookTarget, entity -> true, 5, 5, 1F),
				SetEntityLookTarget.create(
					entity -> entity.isAlive()
						&& !entity.isSpectator()
						&& entity instanceof FlowerCow flowerCow
						&& flowerCow.hasFlowersLeft()
						&& !flowerCow.isBaby(),
					8F
				),
				new RunOne<>(
					ImmutableList.of(
						Pair.of(RandomStroll.fly(1F), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1F, 3), 2)
					)
				)
			)
		);
	}

	public static void updateActivity(Butterfly butterfly) {
		butterfly.getBrain().setActiveActivityToFirstValid(List.of(Activity.IDLE));
	}

	public static void rememberHome(LivingEntity butterfly, BlockPos pos) {
		butterfly.getBrain().setMemory(MemoryModuleType.HOME, GlobalPos.of(butterfly.level().dimension(), pos));
	}

	private static boolean shouldGoTowardsHome(LivingEntity butterfly, GlobalPos pos) {
		return ((Butterfly) butterfly).hasHome() && butterfly.level().dimension() == pos.dimension();
	}

	private static Optional<PositionTracker> getHomeTarget(LivingEntity butterfly) {
		final Optional<GlobalPos> home = butterfly.getBrain().getMemory(MemoryModuleType.HOME);
		if (home.isPresent()) {
			final GlobalPos globalPos = home.get();
			if (shouldGoTowardsHome(butterfly, globalPos)) return Optional.of(new BlockPosTracker(randomPosAround(globalPos.pos(), butterfly.level())));
		}

		return Optional.empty();
	}

	private static Optional<PositionTracker> getLookTarget(LivingEntity butterfly) {
		return butterfly.getBrain().getMemory(MemoryModuleType.LOOK_TARGET);
	}

	private static BlockPos randomPosAround(BlockPos pos, Level level) {
		return pos.offset(level.getRandom().nextIntBetweenInclusive(-7, 7), level.getRandom().nextIntBetweenInclusive(-7, 7), level.getRandom().nextIntBetweenInclusive(-7, 7));
	}
}
