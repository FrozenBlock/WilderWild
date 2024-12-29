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

package net.frozenblock.wilderwild.entity.ai.firefly;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
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
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireflyAi {

	private FireflyAi() {
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
				new Swim(0.8F),
				new LookAtTargetSink(45, 90),
				new MoveToTargetSink()
			)
		);
	}

	private static void addIdleActivities(@NotNull Firefly firefly, @NotNull Brain<Firefly> brain) {
		brain.addActivity(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(1, new FireflyHide(firefly, 2.0D, 10, 8)),
				Pair.of(2, StayCloseToTarget.create(FireflyAi::getLookTarget, entity -> true, 7, 16, 1F)),
				Pair.of(4, new RunOne<>(
					ImmutableList.of(
						Pair.of(RandomStroll.fly(1.0F), 2),
						Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
						Pair.of(new DoNothing(30, 60), 1)
					)
				))
			)
		);
	}

	public static void updateActivities(@NotNull Firefly firefly) {
		firefly.getBrain().setActiveActivityToFirstValid(List.of(Activity.IDLE));
	}

	@Nullable
	public static BlockPos getHome(@NotNull Firefly firefly) {
		Optional<GlobalPos> optional = firefly.getBrain().getMemory(MemoryModuleType.HOME);
		return optional.map(GlobalPos::pos).orElse(null);
	}

	public static boolean isInHomeDimension(@NotNull Firefly firefly) {
		Optional<GlobalPos> optional = firefly.getBrain().getMemory(MemoryModuleType.HOME);
		return optional.filter(globalPos -> globalPos.dimension() == firefly.level().dimension()).isPresent();
	}

	public static void rememberHome(@NotNull LivingEntity firefly, @NotNull BlockPos pos) {
		Brain<?> brain = firefly.getBrain();
		GlobalPos globalPos = GlobalPos.of(firefly.level().dimension(), pos);
		brain.setMemory(MemoryModuleType.HOME, globalPos);
	}

	private static boolean shouldGoTowardsHome(@NotNull LivingEntity firefly, @NotNull GlobalPos pos) {
		Level level = firefly.level();
		return ((Firefly) firefly).hasHome && level.dimension() == pos.dimension() && !((Firefly) firefly).shouldHide();
	}

	@NotNull
	private static Optional<PositionTracker> getLookTarget(@NotNull LivingEntity firefly) {
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
}
