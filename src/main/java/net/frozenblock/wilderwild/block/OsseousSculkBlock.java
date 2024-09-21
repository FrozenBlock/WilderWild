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

package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OsseousSculkBlock extends Block implements SculkBehaviour {
	public static final int GROWTH_CHANCE = 2;
	public static final double HANGING_TENDRIL_CHANCE = 0.7D;
	public static final double HANGING_TENDRIL_WORLDGEN_CHANCE = 0.6D;
	public static final int CATALYST_GROWTH_CHANCE = 11;
	public static final double SCULK_CONVERSION_HEIGHT_THRESHOLD = 3D;
	public static final double RIB_CAGE_CHANCE = 0.8D;
	private static final ConstantInt EXPERIENCE = ConstantInt.of(3);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final IntegerProperty HEIGHT_LEFT = WWBlockStateProperties.PILLAR_HEIGHT_LEFT;
	public static final IntegerProperty TOTAL_HEIGHT = WWBlockStateProperties.TOTAL_HEIGHT;

	public OsseousSculkBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(HEIGHT_LEFT, 0).setValue(TOTAL_HEIGHT, 0));
	}

	public static Direction getDir(@NotNull Direction.Axis axis, @NotNull RandomSource random) {
		if (axis == Direction.Axis.X) {
			return random.nextBoolean() ? Direction.EAST : Direction.WEST;
		}
		return random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
	}

	@NotNull
	public static Direction.Axis getAxis(@NotNull RandomSource random) {
		return random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
	}

	public static boolean isSafeToReplace(@NotNull BlockState state) {
		return state.is(Blocks.SCULK_VEIN) || state.isAir() || state.is(Blocks.WATER);
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
				stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true).setValue(BlockStateProperties.WATERLOGGED, true);
			}
			if (stateSetTo != null) {
				level.setBlock(mutableBlockPos, stateSetTo, UPDATE_ALL);
			}
			mutableBlockPos.move(oppositeDirection);
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
		return super.getStateForPlacement(blockPlaceContext).setValue(FACING, blockPlaceContext.getClickedFace().getOpposite());
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
		BlockState firstState = level.getBlockState(blockPos);
		if (firstState.is(this)) {
			if (isWorldGeneration || (cursorCharge != 0 && random.nextInt(GROWTH_CHANCE) == 0)) {
				if (isWorldGeneration || !blockPos.closerThan(catalystPos, spreader.noGrowthRadius())) {
					int pillarHeightLeft = level.getBlockState(blockPos).getValue(OsseousSculkBlock.HEIGHT_LEFT);
					if (pillarHeightLeft > 0) {
						BlockPos topPos = getTop(level, blockPos, pillarHeightLeft);
						if (topPos != null) {
							BlockPos.MutableBlockPos mutableBlockPos = topPos.mutable();
							BlockState state = level.getBlockState(topPos);
							pillarHeightLeft = state.getValue(HEIGHT_LEFT);
							Direction direction = state.getValue(FACING);
							BlockState offsetState = level.getBlockState(mutableBlockPos.move(direction));
							if (offsetState.isAir() || offsetState.getBlock() == Blocks.SCULK_VEIN) {
								BlockState blockState = getGrowthState(random, pillarHeightLeft, state, direction);
								if (blockState.getBlock() == this) {
									blockState = blockState.setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT)).setValue(FACING, direction);
									if (direction == Direction.DOWN && random.nextDouble() > RIB_CAGE_CHANCE) {
										Direction nextDirection = getDir(getAxis(random), random);
										if (isSafeToReplace(level.getBlockState(mutableBlockPos.setWithOffset(topPos, nextDirection)))) {
											BlockState ribState = this.defaultBlockState().setValue(FACING, nextDirection).setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT)).setValue(HEIGHT_LEFT, 0);
											level.setBlock(mutableBlockPos, ribState, UPDATE_ALL);
											SoundType placedSoundType = ribState.getSoundType();
											level.playSound(null, mutableBlockPos, placedSoundType.getPlaceSound(), SoundSource.BLOCKS, placedSoundType.getVolume(), placedSoundType.getPitch());
											if (isSafeToReplace(level.getBlockState(mutableBlockPos.move(Direction.DOWN))) && random.nextDouble() > (isWorldGeneration ? HANGING_TENDRIL_WORLDGEN_CHANCE : HANGING_TENDRIL_CHANCE)) {
												BlockState tendrilState = WWBlocks.HANGING_TENDRIL.defaultBlockState();
												level.setBlock(mutableBlockPos, tendrilState, UPDATE_ALL);
												SoundType tendrilSoundType = tendrilState.getSoundType();
												level.playSound(null, mutableBlockPos, tendrilSoundType.getPlaceSound(), SoundSource.BLOCKS, tendrilSoundType.getVolume(), tendrilSoundType.getPitch());
											}
										}
									}
								}
								level.setBlock(mutableBlockPos.setWithOffset(topPos, direction), blockState, UPDATE_ALL);
								SoundType placedSoundType = blockState.getSoundType();
								level.playSound(null, mutableBlockPos, placedSoundType.getPlaceSound(), SoundSource.BLOCKS, placedSoundType.getVolume(), placedSoundType.getPitch());
								workOnBottom(level, mutableBlockPos, state);
								cursor.pos = mutableBlockPos.immutable();
								if (!isWorldGeneration) {
									return Math.max(0, cursorCharge - cost);
								}
							}
						}
					}
				}
			}
		}
		return cursorCharge;
	}

	private BlockState getGrowthState(@NotNull RandomSource random, int pillarHeightLeft, @NotNull BlockState state, @NotNull Direction direction) {
		BlockState blockState = this.defaultBlockState().setValue(HEIGHT_LEFT, Math.max(0, pillarHeightLeft - 1));
		if (pillarHeightLeft == 1
			&& direction == Direction.UP
			&& state.getValue(TOTAL_HEIGHT) > 0
			&& random.nextInt(Math.max(1, state.getValue(TOTAL_HEIGHT) / 2)) <= 1
			&& random.nextInt(CATALYST_GROWTH_CHANCE) == 0
		) {
			blockState = Blocks.SCULK_CATALYST.defaultBlockState();
		}
		return blockState;
	}

	private void workOnBottom(@NotNull LevelAccessor level, @NotNull BlockPos topPos, @NotNull BlockState state) {
		BlockPos bottom = getBottom(level, topPos, state.getValue(TOTAL_HEIGHT));
		if (bottom != null) {
			BlockState bottomState = level.getBlockState(bottom);
			if (bottomState.is(this)) {
				int total = bottomState.getValue(TOTAL_HEIGHT);
				if ((total) - bottomState.getValue(HEIGHT_LEFT) <= total / SCULK_CONVERSION_HEIGHT_THRESHOLD) {
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

	@Nullable
	public BlockPos getTop(@NotNull LevelAccessor level, @NotNull BlockPos pos, int max) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockPos.MutableBlockPos mutableBlockPos2 = pos.mutable();
		for (int i = 0; i < max; i++) {
			BlockState blockState = level.getBlockState(mutableBlockPos);
			if (blockState.getBlock() != this) {
				return null;
			}
			BlockState offsetState = level.getBlockState(mutableBlockPos2.move(blockState.getValue(FACING)));
			if (offsetState.isAir() || offsetState.getBlock() == Blocks.SCULK_VEIN) {
				return mutableBlockPos.immutable();
			}
			mutableBlockPos.set(mutableBlockPos2);
		}
		return null;
	}

	@Nullable
	public BlockPos getBottom(@NotNull LevelAccessor level, @NotNull BlockPos pos, int max) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockPos.MutableBlockPos mutableBlockPos2 = pos.mutable();
		for (int i = 0; i < max; i++) {
			BlockState blockState = level.getBlockState(mutableBlockPos);
			if (blockState.getBlock() != this) {
				return null;
			}
			if (level.getBlockState(mutableBlockPos2.move(blockState.getValue(FACING), -1)).is(Blocks.SCULK)) {
				return mutableBlockPos.immutable();
			}
			mutableBlockPos.set(mutableBlockPos2);
		}
		return null;
	}

	@NotNull
	@Override
	public BlockState rotate(@NotNull BlockState blockState, @NotNull Rotation rotation) {
		return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HEIGHT_LEFT, TOTAL_HEIGHT);
	}
}
