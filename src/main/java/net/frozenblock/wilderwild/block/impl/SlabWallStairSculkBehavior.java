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

package net.frozenblock.wilderwild.block.impl;

import java.util.Collection;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlabWallStairSculkBehavior implements SculkBehaviour {

	public static void clearSculkVeins(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockState stateReplace;
		Direction oppositeDirection;
		for (Direction direction : BlockBehaviour.UPDATE_SHAPE_ORDER) {
			stateReplace = level.getBlockState(mutableBlockPos.move(direction));
			oppositeDirection = direction.getOpposite();
			if (stateReplace.is(Blocks.SCULK_VEIN)) {
				stateReplace = stateReplace.setValue(MultifaceBlock.getFaceProperty(oppositeDirection), false);
				if (MultifaceBlock.availableFaces(stateReplace).isEmpty()) {
					stateReplace = stateReplace.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
				}
				level.setBlock(mutableBlockPos, stateReplace, 3);
			}
			mutableBlockPos.move(oppositeDirection);
		}
	}

	@Override
	public int attemptUseCharge(@NotNull SculkSpreader.ChargeCursor cursor, @NotNull LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreader, boolean shouldConvertToBlock) {
		BlockPos cursorPos = cursor.getPos();
		int i = cursor.getCharge();
		if (i != 0 && random.nextInt(spreader.chargeDecayRate()) == 0) {
			boolean bl = cursorPos.closerThan(catalystPos, spreader.noGrowthRadius());
			BlockState placeState = switchBlockStates(level.getBlockState(cursorPos));
			if (!bl && placeState != null) {
				level.setBlock(cursorPos, placeState, 3);
				clearSculkVeins(level, cursorPos);
			}
			return random.nextInt(spreader.additionalDecayRate()) != 0 ? i : i - (bl ? 1 : SculkBlock.getDecayPenalty(spreader, cursorPos, catalystPos, i));
		}
		return i;
	}

	@Override
	public boolean attemptSpreadVein(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
		BlockState placeState = switchBlockStates(level.getBlockState(pos));
		if (placeState != null) {
			level.setBlock(pos, placeState, 3);
			clearSculkVeins(level, pos);
		}
		return true;
	}

	@Nullable
	private BlockState switchBlockStates(@NotNull BlockState blockState) {
		if (blockState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
			return WWBlocks.SCULK_STAIRS.withPropertiesOf(blockState);
		} else if (blockState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
			return WWBlocks.SCULK_WALL.withPropertiesOf(blockState);
		} else if (blockState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
			return WWBlocks.SCULK_SLAB.withPropertiesOf(blockState);
		}
		return null;
	}

}
