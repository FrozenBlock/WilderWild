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

import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OsseousSculkBlock extends Block implements SculkBehaviour {
	public static final int GROWTH_CHANCE = 2;
	public static final float HANGING_TENDRIL_CHANCE = 0.7F;
	public static final float HANGING_TENDRIL_WORLDGEN_CHANCE = 0.6F;
	public static final int CATALYST_GROWTH_CHANCE = 15;
	public static final int SCULK_CONVERSION_CHANCE = 15;
	public static final float RIB_CAGE_CHANCE = 0.8F;
	private static final ConstantInt EXPERIENCE = ConstantInt.of(3);
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	public static final MapCodec<OsseousSculkBlock> CODEC = simpleCodec(OsseousSculkBlock::new);

	public OsseousSculkBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
	}

	public static Direction getDir(@NotNull Direction.Axis axis, @NotNull RandomSource random) {
		if (axis == Direction.Axis.X) return random.nextBoolean() ? Direction.EAST : Direction.WEST;
		return random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
	}

	@NotNull
	public static Direction.Axis getRandomAxisForBranch(@NotNull RandomSource random) {
		return random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
	}

	public static boolean isSafeToReplace(@NotNull BlockState state) {
		return state.is(Blocks.SCULK_VEIN) || state.canBeReplaced();
	}

	public static void placeVeinsAround(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockState stateReplace;
		Direction oppositeDirection;
		for (Direction direction : UPDATE_SHAPE_ORDER) {
			stateReplace = level.getBlockState(mutableBlockPos.move(direction));
			oppositeDirection = direction.getOpposite();
			BlockState stateSetTo = null;
			if (stateReplace.is(Blocks.SCULK_VEIN)) {
				stateSetTo = stateReplace.setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true);
			} else if (stateReplace.isAir() && stateReplace.getFluidState().isEmpty()) {
				stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true);
			} else if (stateReplace.getBlock() == Blocks.WATER) {
				stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true)
					.setValue(BlockStateProperties.WATERLOGGED, true);
			}
			if (stateSetTo != null) {
				level.setBlock(mutableBlockPos, stateSetTo, UPDATE_ALL);
			}
			mutableBlockPos.move(oppositeDirection);
		}
	}

	@NotNull
	@Override
	protected MapCodec<? extends OsseousSculkBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
		return super.getStateForPlacement(blockPlaceContext).setValue(FACING, blockPlaceContext.getClickedFace());
	}

	@Override
	public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
		super.spawnAfterBreak(state, level, pos, stack, dropExperience);
		if (dropExperience) {
			this.tryDropExperience(level, pos, stack, EXPERIENCE);
		}
	}

	@Override
	public int attemptUseCharge(
		@NotNull SculkSpreader.ChargeCursor cursor,
		@NotNull LevelAccessor level,
		@NotNull BlockPos catalystPos,
		@NotNull RandomSource random,
		@NotNull SculkSpreader spreader,
		boolean shouldConvertBlocks
	) {
		boolean isWorldGeneration = spreader.isWorldGeneration();
		int cursorCharge = cursor.getCharge();
		int cost = 1;
		BlockPos blockPos = cursor.getPos();
		if (isWorldGeneration || (cursorCharge != 0 && random.nextInt(GROWTH_CHANCE) == 0)) {
			if (isWorldGeneration || !blockPos.closerThan(catalystPos, spreader.noGrowthRadius())) {
				Optional<BlockPos> possibleTopPos = getTop(level, blockPos);
				if (possibleTopPos.isPresent()) {
					BlockPos topPos = possibleTopPos.get();
					BlockPos.MutableBlockPos mutableBlockPos = topPos.mutable();
					BlockState state = level.getBlockState(topPos);
					Direction direction = state.getValue(FACING);

					if (Direction.Plane.VERTICAL.test(direction)) {
						BlockState offsetState = level.getBlockState(mutableBlockPos.move(direction));
						if (offsetState.canBeReplaced() || offsetState.getBlock() == Blocks.SCULK_VEIN) {
							BlockState blockState = getGrowthState(random, direction);

							if (blockState.getBlock() == this) {
								if (direction == Direction.DOWN && random.nextDouble() > RIB_CAGE_CHANCE) {
									Direction nextDirection = getDir(getRandomAxisForBranch(random), random);
									if (isSafeToReplace(level.getBlockState(mutableBlockPos.setWithOffset(topPos, nextDirection)))) {
										BlockState ribState = this.defaultBlockState().setValue(FACING, nextDirection);
										level.setBlock(mutableBlockPos, ribState, UPDATE_ALL);
										playPlaceSound(level, mutableBlockPos, ribState);
										if (WWBlockConfig.HANGING_TENDRIL_GENERATION
											&& isSafeToReplace(level.getBlockState(mutableBlockPos.move(Direction.DOWN)))
											&& random.nextFloat() > (isWorldGeneration ? HANGING_TENDRIL_WORLDGEN_CHANCE : HANGING_TENDRIL_CHANCE)
										) {
											BlockState tendrilState = WWBlocks.HANGING_TENDRIL.defaultBlockState();
											level.setBlock(mutableBlockPos, tendrilState, UPDATE_ALL);
										}
									}
								}
							}

							level.setBlock(mutableBlockPos.setWithOffset(topPos, direction), blockState, UPDATE_ALL);
							playPlaceSound(level, mutableBlockPos, blockState);
							workOnBottom(level, mutableBlockPos, random);
							if (!isWorldGeneration) {
								return Math.max(0, cursorCharge - cost);
							}
						}
					}
				}
			}
		}
		return cursorCharge;
	}

	private static void playPlaceSound(@NotNull LevelAccessor level, BlockPos pos, @NotNull BlockState state) {
		SoundType soundType = state.getSoundType();
		level.playSound(
			null,
			pos,
			soundType.getPlaceSound(),
			SoundSource.BLOCKS,
			soundType.getVolume(),
			soundType.getPitch() * 0.8F
		);
	}

	private BlockState getGrowthState(@NotNull RandomSource random, @NotNull Direction direction) {
		BlockState blockState = this.defaultBlockState().setValue(FACING, direction);
		if (direction == Direction.UP && random.nextInt(CATALYST_GROWTH_CHANCE) == 0) {
			blockState = Blocks.SCULK_CATALYST.defaultBlockState();
		}
		return blockState;
	}

	private void workOnBottom(@NotNull LevelAccessor level, @NotNull BlockPos topPos, RandomSource random) {
		Optional<BlockPos> possibleBottom = getBottom(level, topPos);
		if (possibleBottom.isPresent()) {
			BlockPos bottom = possibleBottom.get();
			BlockState bottomState = level.getBlockState(bottom);
			if (bottomState.is(this)) {
				if (random.nextInt(0, SCULK_CONVERSION_CHANCE) == 0) {
					this.convertToSculk(level, bottom);
				}
			}
		}
	}

	public void convertToSculk(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockState state = level.getBlockState(mutableBlockPos);
		if (state.is(this)) {
			BlockState stateReplace;
			Direction oppositeDirection;
			for (Direction direction : UPDATE_SHAPE_ORDER) {
				stateReplace = level.getBlockState(mutableBlockPos.move(direction));
				oppositeDirection = direction.getOpposite();
				if (stateReplace.is(Blocks.SCULK_VEIN)) {
					stateReplace = stateReplace.setValue(MultifaceBlock.getFaceProperty(oppositeDirection), false);
					if (MultifaceBlock.availableFaces(stateReplace).isEmpty()) {
						stateReplace = stateReplace.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
					}
					level.setBlock(mutableBlockPos, stateReplace, UPDATE_ALL);
				} else if (stateReplace.is(this) && stateReplace.hasProperty(FACING) && stateReplace.getValue(FACING) == direction) {
					placeVeinsAround(level, mutableBlockPos.mutable());
				}
				mutableBlockPos.move(oppositeDirection);
			}
			mutableBlockPos.move(state.getValue(FACING));
			placeVeinsAround(level, mutableBlockPos.mutable());
			level.setBlock(pos, Blocks.SCULK.defaultBlockState(), UPDATE_ALL);
		}
	}

	public Optional<BlockPos> getTop(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockPos.MutableBlockPos mutableBlockPos2 = pos.mutable();

		BlockState blockState = level.getBlockState(mutableBlockPos);
		if (blockState.getBlock() != this) return Optional.empty();

		for (int i = 0; i < 32; i++) {
			if (blockState.getBlock() == this) {
				BlockState offsetState = level.getBlockState(mutableBlockPos2.move(blockState.getValue(FACING)));
				if (offsetState.canBeReplaced() || offsetState.getBlock() == Blocks.SCULK_VEIN) {
					return Optional.of(mutableBlockPos.immutable());
				}
				mutableBlockPos.set(mutableBlockPos2);
				blockState = level.getBlockState(mutableBlockPos);
			} else {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	public Optional<BlockPos> getBottom(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockPos.MutableBlockPos mutableBlockPos2 = pos.mutable();

		BlockState blockState = level.getBlockState(mutableBlockPos);
		if (blockState.getBlock() != this) return Optional.empty();

		for (int i = 0; i < 32; i++) {
			if (blockState.getBlock() == this) {
				if (level.getBlockState(mutableBlockPos2.move(blockState.getValue(FACING), -1)).is(Blocks.SCULK)) {
					return Optional.of(mutableBlockPos.immutable());
				}
				mutableBlockPos.set(mutableBlockPos2);
				blockState = level.getBlockState(mutableBlockPos);
			} else {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	@NotNull
	@Override
	public BlockState rotate(@NotNull BlockState blockState, @NotNull Rotation rotation) {
		return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
