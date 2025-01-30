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

package net.frozenblock.wilderwild.entity.ai.firefly;

import net.frozenblock.lib.entity.api.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class FireflyMoveToBush extends MoveToBlockBehavior<Firefly> {
	private final int returnDistance;

	public FireflyMoveToBush(@NotNull Firefly firefly, double speedModifier, int searchRange, int verticalSearchRange, int returnDistance) {
		super(firefly, speedModifier, searchRange, verticalSearchRange);
		this.returnDistance = returnDistance;
	}

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull Firefly firefly) {
		return !firefly.hasHome() && super.checkExtraStartConditions(level, firefly) && !this.blockPos.closerThan(firefly.blockPosition(), this.returnDistance);
	}

	@Override
	public boolean isValidTarget(@NotNull LevelReader level, @NotNull BlockPos pos) {
		return level.getBlockState(pos).is(Blocks.FIREFLY_BUSH);
	}

	@Override
	public double acceptedDistance() {
		return 4D;
	}

	@Override
	protected void moveMobToBlock() {
		RandomSource randomSource = this.mob.getRandom();

		this.mob
			.getNavigation()
			.moveTo(
				randomSource.nextInt(-3, 3) + this.blockPos.getX() + 0.5D,
				randomSource.nextInt(-1, 3) + this.blockPos.getY() + 0.5D,
				randomSource.nextInt(-3, 3) + this.blockPos.getZ() + 0.5D,
				this.speedModifier
			);
	}

	@Override
	@NotNull
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}
}
