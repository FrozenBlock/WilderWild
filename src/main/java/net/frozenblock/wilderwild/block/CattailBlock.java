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

package net.frozenblock.wilderwild.block;

import net.frozenblock.lib.block.api.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.impl.BlockAmbienceUtil;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CattailBlock extends WaterloggableTallFlowerBlock {

	public CattailBlock(@NotNull Properties settings) {
		super(settings);
	}

	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState blockState = super.getStateForPlacement(context);
		return blockState != null ?
			SnowloggingUtils.getSnowPlacementState(blockState, context) : null;
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(WWBlockTags.CATTAIL_FEATURE_PLACEABLE) || blockState.is(WWBlockTags.CATTAIL_FEATURE_MUD_PLACEABLE);
	}

	@Override
	public void animateTick(BlockState blockState, Level level, BlockPos blockPos, @NotNull RandomSource randomSource) {
		if (randomSource.nextFloat() < BlockAmbienceUtil.horizontalWindStrength(level, blockPos) * 0.045F) {
			if (blockState.getValue(WATERLOGGED)) {
				BlockState aboveState = level.getBlockState(blockPos.above());
				if (aboveState.is(this) && !aboveState.getValue(WATERLOGGED)) {
					if (BlockAmbienceUtil.isBrightEnoughForWind(level, blockPos)) {
						level.playLocalSound(
							blockPos.getX() + 0.5D,
							blockPos.getY() + 0.5D,
							blockPos.getZ() + 0.5D,
							WWSounds.AMBIENT_OVERWORLD_WIND_CATTAIL,
							SoundSource.AMBIENT,
							0.05F,
							0.85F + (randomSource.nextFloat() * 0.25F),
							false
						);
					}
				}
			}
		}
	}
}
