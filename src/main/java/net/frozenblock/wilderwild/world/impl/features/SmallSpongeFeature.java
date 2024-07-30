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

package net.frozenblock.wilderwild.world.impl.features;

import com.mojang.serialization.Codec;
import java.util.List;
import net.frozenblock.wilderwild.block.SmallSpongeBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.impl.features.config.SmallSpongeFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmallSpongeFeature extends Feature<SmallSpongeFeatureConfig> {

	public SmallSpongeFeature(@NotNull Codec<SmallSpongeFeatureConfig> codec) {
		super(codec);
	}

	public static boolean generate(@NotNull WorldGenLevel level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull SmallSpongeFeatureConfig config, @NotNull List<Direction> directions) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for (Direction direction : directions) {
			BlockState blockState = level.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
			if (blockState.is(config.canPlaceOn)) {
				BlockState blockState2 = getStateForPlacement(level.getRandom(), state, level, pos, direction);
				if (blockState2 == null) {
					return false;
				}

				if (blockState2.getValue(SmallSpongeBlock.WATERLOGGED)) {
					level.setBlock(pos, blockState2, 3);
					level.getChunk(pos).markPosForPostprocessing(pos);
					return true;
				}
			}
		}
		return false;
	}

	@Nullable
	private static BlockState getStateForPlacement(RandomSource random, @NotNull BlockState currentState, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction lookingDirection) {
		Block sponge = RegisterBlocks.SMALL_SPONGE;
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
				blockState = blockState.setValue(SmallSpongeBlock.FACE, lookingDirection == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(SmallSpongeBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
			} else {
				blockState = blockState.setValue(SmallSpongeBlock.FACE, AttachFace.WALL).setValue(SmallSpongeBlock.FACING, lookingDirection.getOpposite());
			}

			return blockState.setValue(SmallSpongeBlock.AGE, random.nextInt(SmallSpongeBlock.MAX_AGE));
		}
	}

	private static boolean isValidStateForPlacement(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
		BlockPos blockPos = pos.relative(direction);
		return canAttachTo(level, direction, blockPos, level.getBlockState(blockPos));
	}

	private static boolean canAttachTo(@NotNull BlockGetter level, @NotNull Direction direction, @NotNull BlockPos pos, @NotNull BlockState state) {
		return Block.isFaceFull(state.getBlockSupportShape(level, pos), direction.getOpposite()) || Block.isFaceFull(state.getCollisionShape(level, pos), direction.getOpposite());
	}

	public static boolean isAirOrWater(@NotNull BlockState state) {
		return state.isAir() || state.is(Blocks.WATER);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<SmallSpongeFeatureConfig> context) {
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		SmallSpongeFeatureConfig config = context.config();
		if (!isAirOrWater(worldGenLevel.getBlockState(blockPos))) {
			return false;
		} else {
			List<Direction> list = config.shuffleDirections(randomSource);
			if (generate(worldGenLevel, blockPos, worldGenLevel.getBlockState(blockPos), config, list)) {
				return true;
			} else {
				BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

				for (Direction direction : list) {
					mutableBlockPos.set(blockPos);
					List<Direction> list2 = config.shuffleDirections(randomSource, direction.getOpposite());

					for (int i = 0; i < config.searchRange; ++i) {
						mutableBlockPos.setWithOffset(blockPos, direction);
						BlockState blockState = worldGenLevel.getBlockState(mutableBlockPos);
						if (!isAirOrWater(blockState) && !blockState.is(RegisterBlocks.SMALL_SPONGE)) {
							break;
						}

						if (generate(worldGenLevel, mutableBlockPos, blockState, config, list2)) {
							return true;
						}
					}
				}
				return false;
			}
		}
	}
}

