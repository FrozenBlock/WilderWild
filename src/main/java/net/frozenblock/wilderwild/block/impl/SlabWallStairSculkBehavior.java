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

package net.frozenblock.wilderwild.block.impl;

import java.util.Collection;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class SlabWallStairSculkBehavior implements SculkBehaviour {

	public static void clearSculkVeins(LevelAccessor level, BlockPos pos) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		BlockState stateReplace;
		Direction oppositeDirection;
		for (Direction direction : BlockBehaviour.UPDATE_SHAPE_ORDER) {
			stateReplace = level.getBlockState(mutable.move(direction));
			oppositeDirection = direction.getOpposite();
			if (stateReplace.is(Blocks.SCULK_VEIN)) {
				stateReplace = stateReplace.setValue(MultifaceBlock.getFaceProperty(oppositeDirection), false);
				if (MultifaceBlock.availableFaces(stateReplace).isEmpty()) {
					stateReplace = stateReplace.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
				}
				level.setBlock(mutable, stateReplace, Block.UPDATE_ALL);
			}
			mutable.move(oppositeDirection);
		}
	}

	@Override
	public int attemptUseCharge(
		SculkSpreader.ChargeCursor cursor,
		LevelAccessor level,
		BlockPos catalystPos,
		RandomSource random,
		SculkSpreader spreader,
		boolean shouldConvertToBlock
	) {
		final BlockPos pos = cursor.getPos();
		final int charge = cursor.getCharge();
		if (charge != 0 && random.nextInt(spreader.chargeDecayRate()) == 0) {
			final boolean isTooClose = pos.closerThan(catalystPos, spreader.noGrowthRadius());
			BlockState placeState = switchBlockStates(level.getBlockState(pos));
			if (!isTooClose && placeState != null) {
				level.setBlock(pos, placeState, Block.UPDATE_ALL);
				clearSculkVeins(level, pos);
			}
			return random.nextInt(spreader.additionalDecayRate()) != 0 ? charge : charge - (isTooClose ? 1 : SculkBlock.getDecayPenalty(spreader, pos, catalystPos, charge));
		}
		return charge;
	}

	@Override
	public boolean attemptSpreadVein(LevelAccessor level, BlockPos pos, BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
		final BlockState placeState = switchBlockStates(level.getBlockState(pos));
		if (placeState != null) {
			level.setBlock(pos, placeState, Block.UPDATE_ALL);
			clearSculkVeins(level, pos);
			if (markForPostProcessing) level.getChunk(pos).markPosForPostprocessing(pos);
		}
		return true;
	}

	@Nullable
	private BlockState switchBlockStates(BlockState state) {
		if (state.is(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_STAIR_REPLACEABLE)) return WWBlocks.SCULK_STAIRS.withPropertiesOf(state);
		if (state.is(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_WALL_REPLACEABLE)) return WWBlocks.SCULK_WALL.withPropertiesOf(state);
		if (state.is(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_SLAB_REPLACEABLE)) return WWBlocks.SCULK_SLAB.withPropertiesOf(state);
		return null;
	}

}
