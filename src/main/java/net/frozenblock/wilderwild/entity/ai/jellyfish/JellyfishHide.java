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

package net.frozenblock.wilderwild.entity.ai.jellyfish;

import jdk.jfr.Experimental;
import net.frozenblock.lib.entity.api.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.block.NematocystBlock;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

@Experimental
public class JellyfishHide extends MoveToBlockBehavior<Jellyfish> {

	public JellyfishHide(@NotNull Jellyfish mob, double speedModifier, int searchRange, int verticalSearchRange) {
		super(mob, speedModifier, searchRange, verticalSearchRange);
	}

	@Override
	public void start(@NotNull ServerLevel level, @NotNull Jellyfish entity, long gameTime) {
		super.start(level, entity, gameTime);
	}

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull Jellyfish owner) {
		return owner.shouldHide() && super.checkExtraStartConditions(level, owner);
	}

	@Override
	protected void tick(@NotNull ServerLevel level, @NotNull Jellyfish owner, long gameTime) {
		super.tick(level, owner, gameTime);
		if (this.isReachedTarget() && !owner.vanishing) {
			level.broadcastEntityEvent(owner, (byte) 4);
			owner.vanishing = true;
		}
	}

	@Override
	public boolean isValidTarget(@NotNull LevelReader level, @NotNull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return (state.getBlock() instanceof MesogleaBlock || state.getBlock() instanceof NematocystBlock) && (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED));
	}

	@Override
	public double acceptedDistance() {
		return 0.5D;
	}

	@Override
	protected void moveMobToBlock() {
		this.mob
			.getNavigation()
			.moveTo(this.blockPos.getX() + 0.5, this.blockPos.getY() + 0.5, this.blockPos.getZ() + 0.5, this.speedModifier);
	}

	@Override
	@NotNull
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}
}
