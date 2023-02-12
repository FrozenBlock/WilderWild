/*
 * Copyright 2022-2023 FrozenBlock
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

public class FireflyAi {

    private FireflyAi() {
    }

    public static Brain<?> makeBrain(Firefly firefly, Brain<Firefly> brain) {
        addCoreActivities(brain);
        addIdleActivities(firefly, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivities(Brain<Firefly> brain) {
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

    private static void addIdleActivities(Firefly firefly, Brain<Firefly> brain) {
        brain.addActivity(
				Activity.IDLE,
				ImmutableList.of(
						Pair.of(1, new FireflyHide(firefly, 2.0D, 10, 8)),
						Pair.of(2, StayCloseToTarget.create(FireflyAi::getLookTarget, 7, 16, 1.0F)),
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

    public static void updateActivities(Firefly firefly) {
        firefly.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }

    public static BlockPos getHome(Firefly firefly) {
        Optional<GlobalPos> optional = firefly.getBrain().getMemory(MemoryModuleType.HOME);
        return optional.map(GlobalPos::pos).orElse(null);
    }

    public static boolean isInHomeDimension(Firefly firefly) {
        Optional<GlobalPos> optional = firefly.getBrain().getMemory(MemoryModuleType.HOME);
        return optional.filter(globalPos -> globalPos.dimension() == firefly.level.dimension()).isPresent();
    }

    public static void rememberHome(LivingEntity firefly, BlockPos pos) {
        Brain<?> brain = firefly.getBrain();
        GlobalPos globalPos = GlobalPos.of(firefly.getLevel().dimension(), pos);
        brain.setMemory(MemoryModuleType.HOME, globalPos);
    }

    private static boolean shouldGoTowardsHome(LivingEntity firefly, GlobalPos pos) {
        Level level = firefly.getLevel();
        return ((Firefly) firefly).hasHome && level.dimension() == pos.dimension() && !((Firefly) firefly).shouldHide();
    }

    private static Optional<PositionTracker> getLookTarget(LivingEntity firefly) {
        Brain<?> brain = firefly.getBrain();
        Optional<GlobalPos> home = brain.getMemory(MemoryModuleType.HOME);
        if (home.isPresent()) {
            GlobalPos globalPos = home.get();
            if (shouldGoTowardsHome(firefly, globalPos)) {
                return Optional.of(new BlockPosTracker(randomPosAround(globalPos.pos(), firefly.level)));
            }
        }

        return Optional.empty();
    }

    private static BlockPos randomPosAround(BlockPos pos, Level level) {
        return pos.offset(level.random.nextIntBetweenInclusive(-7, 7), level.random.nextIntBetweenInclusive(-7, 7), level.random.nextIntBetweenInclusive(-7, 7));
    }
}
