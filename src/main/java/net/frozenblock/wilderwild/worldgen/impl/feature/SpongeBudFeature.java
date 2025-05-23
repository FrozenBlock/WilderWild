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

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.SpongeBudFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpongeBudFeature extends Feature<SpongeBudFeatureConfig> {

	public SpongeBudFeature(@NotNull Codec<SpongeBudFeatureConfig> codec) {
		super(codec);
	}

	public static boolean generate(
		@NotNull WorldGenLevel level,
		@NotNull BlockPos pos,
		@NotNull BlockState state,
		@NotNull SpongeBudFeatureConfig config,
		@NotNull List<Direction> directions
	) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for (Direction direction : directions) {
			BlockState blockState = level.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
			if (blockState.is(config.canPlaceOn)) {
				BlockState blockState2 = getStateForPlacement(level.getRandom(), state, level, pos, direction);
				if (blockState2 == null) return false;

				if (blockState2.getValue(SpongeBudBlock.WATERLOGGED)) {
					level.setBlock(pos, blockState2, Block.UPDATE_ALL);
					level.getChunk(pos).markPosForPostprocessing(pos);
					return true;
				}
			}
		}
		return false;
	}

	@Nullable
	private static BlockState getStateForPlacement(
		RandomSource random,
		@NotNull BlockState currentState,
		@NotNull BlockGetter level,
		@NotNull BlockPos pos,
		@NotNull Direction lookingDirection
	) {
		Block sponge = WWBlocks.SPONGE_BUD;
		if (!isValidStateForPlacement(level, pos, lookingDirection)) {
			return null;
		} else {
			BlockState blockState;
			if (currentState.is(sponge)) {
				blockState = currentState;
			} else if (currentState.getFluidState().isSourceOfType(Fluids.WATER)) {
				blockState = sponge.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
			} else {
				blockState = sponge.defaultBlockState();
			}

			if (lookingDirection.getAxis() == Direction.Axis.Y) {
				blockState = blockState
					.setValue(SpongeBudBlock.FACE, lookingDirection == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)
					.setValue(SpongeBudBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
			} else {
				blockState = blockState.setValue(SpongeBudBlock.FACE, AttachFace.WALL)
					.setValue(SpongeBudBlock.FACING, lookingDirection.getOpposite());
			}

			return blockState.setValue(SpongeBudBlock.AGE, random.nextInt(SpongeBudBlock.MAX_AGE));
		}
	}

	private static boolean isValidStateForPlacement(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
		BlockPos blockPos = pos.relative(direction);
		return canAttachTo(level, direction, blockPos, level.getBlockState(blockPos));
	}

	private static boolean canAttachTo(@NotNull BlockGetter level, @NotNull Direction direction, @NotNull BlockPos pos, @NotNull BlockState state) {
		return Block.isFaceFull(state.getBlockSupportShape(level, pos), direction.getOpposite())
			|| Block.isFaceFull(state.getCollisionShape(level, pos), direction.getOpposite());
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<SpongeBudFeatureConfig> context) {
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		SpongeBudFeatureConfig config = context.config();

		if (!BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE.test(worldGenLevel, blockPos)) return false;
		List<Direction> directions = config.shuffleDirections(randomSource);
		if (generate(worldGenLevel, blockPos, worldGenLevel.getBlockState(blockPos), config, directions)) return true;

		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		for (Direction direction : directions) {
			mutableBlockPos.set(blockPos);
			List<Direction> directions2 = config.shuffleDirections(randomSource, direction.getOpposite());
			for (int i = 0; i < config.searchRange; ++i) {
				mutableBlockPos.setWithOffset(blockPos, direction);
				BlockState blockState = worldGenLevel.getBlockState(mutableBlockPos);
				if (!BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE.test(worldGenLevel, mutableBlockPos) && !blockState.is(WWBlocks.SPONGE_BUD)) break;
				if (generate(worldGenLevel, mutableBlockPos, blockState, config, directions2)) return true;
			}
		}
		return false;
	}

}
