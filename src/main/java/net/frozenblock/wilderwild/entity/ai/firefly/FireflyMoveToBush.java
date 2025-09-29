/*
 * Copyright 2025 FrozenBlock
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

import net.frozenblock.lib.entity.api.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.config.WWEntityConfig;
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
		return WWEntityConfig.FIREFLY_SWARMS_BUSH
			&& !firefly.hasHome()
			&& super.checkExtraStartConditions(level, firefly)
			&& !this.blockPos.closerThan(firefly.blockPosition(), this.returnDistance);
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
		final RandomSource random = this.mob.getRandom();
		this.mob
			.getNavigation()
			.moveTo(
				random.nextInt(-3, 3) + this.blockPos.getX() + 0.5D,
				random.nextInt(-1, 3) + this.blockPos.getY() + 0.5D,
				random.nextInt(-3, 3) + this.blockPos.getZ() + 0.5D,
				this.speedModifier
			);
	}

	@Override
	@NotNull
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}
}
