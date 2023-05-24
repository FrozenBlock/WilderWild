/*
 * Copyright 2022-2023 FrozenBlock
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

import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OsseousSculkBlock extends RotatedPillarBlock implements SculkBehaviour {
	public static final IntegerProperty HEIGHT_LEFT = RegisterProperties.PILLAR_HEIGHT_LEFT;
	public static final BooleanProperty UPSIDEDOWN = RegisterProperties.UPSIDE_DOWN;
	public static final IntegerProperty TOTAL_HEIGHT = RegisterProperties.TOTAL_HEIGHT;
	private static final ConstantInt EXPERIENCE = ConstantInt.of(3);

	public OsseousSculkBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(HEIGHT_LEFT, 0).setValue(AXIS, Direction.Axis.Y).setValue(UPSIDEDOWN, false).setValue(TOTAL_HEIGHT, 0));
	}

	public static Direction getDir(@NotNull Direction.Axis axis, boolean upsideDown, @NotNull RandomSource random) {
		switch (axis) {
			case X -> {
				return random.nextBoolean() ? Direction.EAST : Direction.WEST;
			}
			case Y -> {
				return upsideDown ? Direction.DOWN : Direction.UP;
			}
			default -> {
				return random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
			}
		}
	}

	@NotNull
	public static Direction.Axis getAxis(@NotNull RandomSource random) {
		return random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
	}

	@NotNull
	public static Direction.Axis getAxis(@NotNull BlockPos pos) {
		return EasyNoiseSampler.sample(EasyNoiseSampler.perlinLocal, pos, 0.7, false, false) > 0 ? Direction.Axis.X : Direction.Axis.Z;
	}

	public static boolean isSafeToReplace(@NotNull BlockState state) {
		return state.is(Blocks.SCULK_VEIN) || state.isAir() || state.is(Blocks.WATER);
	}

	@Override
	public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
		super.spawnAfterBreak(state, level, pos, stack, dropExperience);
		if (dropExperience) {
			this.tryDropExperience(level, pos, stack, EXPERIENCE);
		}
	}

	public void convertToSculk(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.is(this)) {
			Direction.Axis axis = state.getValue(AXIS);
			Direction dir = getDir(axis, state.getValue(UPSIDEDOWN), level.getRandom());
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable().move(dir);
			if (level.getBlockState(mutableBlockPos).is(this)) {
				BlockState stateReplace;
				Direction oppositeDirection;
				for (Direction direction : UPDATE_SHAPE_ORDER) {
					stateReplace = level.getBlockState(mutableBlockPos.move(direction));
					oppositeDirection = direction.getOpposite();
					BlockState stateSetTo = null;
					if (stateReplace.is(Blocks.SCULK_VEIN)) {
						stateSetTo = stateReplace.setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true);
					}
					if (stateReplace.isAir()) {
						stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true);
					}
					if (stateReplace.getBlock() == Blocks.WATER) {
						stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true).setValue(BlockStateProperties.WATERLOGGED, true);
					}
					if (stateSetTo != null) {
						level.setBlock(mutableBlockPos, stateSetTo, 3);
					}
					mutableBlockPos.move(direction, -1);
				}
				level.setBlock(pos, Blocks.SCULK.defaultBlockState(), 3);
			}
		}
	}

	@Override
	public int attemptUseCharge(SculkSpreader.@NotNull ChargeCursor cursor, @NotNull LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreadManager, boolean shouldConvertToBlock) {
		if (spreadManager.isWorldGeneration()) {
			worldGenSpread(cursor.getPos(), level, random);
			return cursor.getCharge();
		}
		int i = cursor.getCharge();
		int j = 1;
		if (i != 0 && random.nextInt(2) == 0) {
			BlockPos blockPos = cursor.getPos();
			boolean bl = blockPos.closerThan(catalystPos, spreadManager.noGrowthRadius());
			if (!bl) {
				int pillarHeight = level.getBlockState(blockPos).getValue(OsseousSculkBlock.HEIGHT_LEFT);
				BlockPos topPos = getTop(level, blockPos, pillarHeight);
				if (topPos != null) {
					BlockPos.MutableBlockPos mutableBlockPos = topPos.mutable();
					RandomSource randomSource = level.getRandom();
					BlockState state = level.getBlockState(topPos);
					pillarHeight = state.getValue(OsseousSculkBlock.HEIGHT_LEFT);
					Direction direction = getDir(state.getValue(AXIS), state.getValue(UPSIDEDOWN), randomSource);
					BlockState offsetState = level.getBlockState(mutableBlockPos.set(topPos).move(direction));
					if (offsetState.isAir() || offsetState.getBlock() == Blocks.SCULK_VEIN) {
						mutableBlockPos.set(topPos);
						BlockState blockState = this.defaultBlockState().setValue(HEIGHT_LEFT, Math.max(0, pillarHeight - 1));
						if (pillarHeight == 1 && !state.getValue(UPSIDEDOWN) && state.getValue(TOTAL_HEIGHT) > 0) {
							if (EasyNoiseSampler.localRandom.nextInt(Math.max(1, state.getValue(TOTAL_HEIGHT) / 2)) <= 1) {
								if (random.nextInt(11) == 0) {
									blockState = Blocks.SCULK_CATALYST.defaultBlockState();
								}
							}
						}
						if (blockState.getBlock() == this) {
							blockState = blockState.setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT));
							if (state.getValue(UPSIDEDOWN)) {
								blockState = blockState.setValue(UPSIDEDOWN, true);
								if (direction == Direction.DOWN && random.nextDouble() > 0.8) {
									Direction ribCageDirection = getDir(getAxis(randomSource), false, randomSource);
									if (isSafeToReplace(level.getBlockState(mutableBlockPos.move(ribCageDirection)))) {
										level.setBlock(mutableBlockPos, this.defaultBlockState().setValue(AXIS, getAxis(randomSource)).setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT)).setValue(HEIGHT_LEFT, 0), 3);
										if (isSafeToReplace(level.getBlockState(mutableBlockPos.move(Direction.DOWN)))) {
											if (random.nextDouble() > 0.7) {
												level.setBlock(mutableBlockPos, RegisterBlocks.HANGING_TENDRIL.defaultBlockState(), 3);
											}
											mutableBlockPos.move(Direction.UP);
										}
									}
									mutableBlockPos.move(ribCageDirection, -1);
								}
							}
						}
						level.setBlock(mutableBlockPos.move(direction), blockState, 3);
						SoundType placedSoundType = blockState.getSoundType();
						level.playSound(null, blockPos, placedSoundType.getPlaceSound(), SoundSource.BLOCKS, placedSoundType.getVolume(), placedSoundType.getPitch());
						if (spreadManager.isWorldGeneration() && random.nextDouble() > 0.2) {
							j = 0;
						}
						BlockPos bottom = getBottom(level, mutableBlockPos, state.getValue(TOTAL_HEIGHT));
						if (bottom != null) {
							BlockState bottomState = level.getBlockState(bottom);
							if (bottomState.is(this)) {
								int piece = bottomState.getValue(HEIGHT_LEFT);
								int total = bottomState.getValue(TOTAL_HEIGHT);
								if ((total) - piece <= total / 3) {
									convertToSculk(level, bottom);
								}
							}
						}
						return Math.max(0, i - j);
					}
				}
			}
		}
		return i;
	}

	public void worldGenSpread(@NotNull BlockPos blockPos, @NotNull LevelAccessor level, @NotNull RandomSource random) {
		if (level.getBlockState(blockPos).is(this)) {
			int pillarHeight = level.getBlockState(blockPos).getValue(HEIGHT_LEFT);
			BlockPos topPos = getTop(level, blockPos, pillarHeight);
			if (topPos != null) {
				BlockPos.MutableBlockPos mutableBlockPos = topPos.mutable();
				BlockState state = level.getBlockState(mutableBlockPos);
				RandomSource randomSource = level.getRandom();
				pillarHeight = state.getValue(HEIGHT_LEFT);
				Direction direction = getDir(state.getValue(AXIS), state.getValue(UPSIDEDOWN), randomSource);
				BlockState offsetState = level.getBlockState(mutableBlockPos.move(direction));
				if (offsetState.isAir() || offsetState.getBlock() == Blocks.SCULK_VEIN) {
					mutableBlockPos.set(topPos);
					BlockState blockState = this.defaultBlockState().setValue(HEIGHT_LEFT, Math.max(0, pillarHeight - 1));
					if (pillarHeight == 1 && !state.getValue(UPSIDEDOWN) && state.getValue(TOTAL_HEIGHT) > 0) {
						if (EasyNoiseSampler.localRandom.nextInt(Math.max(1, state.getValue(TOTAL_HEIGHT) / 2)) <= 1) {
							if (random.nextInt(11) == 0) {
								blockState = Blocks.SCULK_CATALYST.defaultBlockState();
							}
						}
					}
					if (blockState.getBlock() == this) {
						blockState = blockState.setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT));
						if (state.getValue(UPSIDEDOWN)) {
							blockState = blockState.setValue(UPSIDEDOWN, true);
							if (direction == Direction.DOWN && random.nextDouble() > 0.9) {
								Direction ribCage = getDir(getAxis(topPos), false, randomSource);
								if (isSafeToReplace(level.getBlockState(mutableBlockPos.move(ribCage)))) {
									level.setBlock(mutableBlockPos, this.defaultBlockState().setValue(AXIS, getAxis(topPos)).setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT)).setValue(HEIGHT_LEFT, 0), 3);
									if (isSafeToReplace(level.getBlockState(mutableBlockPos.move(Direction.DOWN)))) {
										if (random.nextDouble() > 0.66) {
											level.setBlock(mutableBlockPos, RegisterBlocks.HANGING_TENDRIL.defaultBlockState(), 3);
										}
									}
								}
							}
						}
					}
					mutableBlockPos.set(topPos).move(direction);
					level.setBlock(mutableBlockPos, blockState, 3);
					SoundType placedSoundType = blockState.getSoundType();
					level.playSound(null, blockPos, placedSoundType.getPlaceSound(), SoundSource.BLOCKS, placedSoundType.getVolume(), placedSoundType.getPitch());
					if (blockState.getBlock() == Blocks.SCULK_CATALYST || (blockState.getBlock() == this && blockState.getValue(HEIGHT_LEFT) == 0)) {
						for (int i = 0; i < 4; i++) {
							BlockPos bottom = getBottom(level, topPos, state.getValue(TOTAL_HEIGHT));
							if (bottom != null) {
								BlockState bottomState = level.getBlockState(bottom);
								if (bottomState.is(this)) {
									int piece = bottomState.getValue(HEIGHT_LEFT);
									int total = bottomState.getValue(TOTAL_HEIGHT);
									if ((total) - piece <= total / 3) {
										this.convertToSculk(level, bottom);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Nullable
	public BlockPos getTop(@NotNull LevelAccessor level, @NotNull BlockPos pos, int max) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		RandomSource randomSource = level.getRandom();
		for (int i = 0; i < max; i++) {
			BlockState blockState = level.getBlockState(mutableBlockPos);
			if (blockState.getBlock() != this) {
				return null;
			}
			BlockState offsetState = level.getBlockState(mutableBlockPos.move(getDir(blockState.getValue(AXIS), blockState.getValue(UPSIDEDOWN), randomSource)));
			if (offsetState.isAir() || offsetState.getBlock() == Blocks.SCULK_VEIN) {
				return pos.immutable();
			}
		}
		return null;
	}

	@Nullable
	public BlockPos getBottom(@NotNull LevelAccessor level, @NotNull BlockPos pos, int max) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		RandomSource randomSource = level.getRandom();
		for (int i = 0; i < max; i++) {
			BlockState blockState = level.getBlockState(mutableBlockPos);
			if (blockState.getBlock() != this) {
				return null;
			}
			if (level.getBlockState(mutableBlockPos.move(getDir(blockState.getValue(AXIS), blockState.getValue(UPSIDEDOWN), randomSource), -1)).is(Blocks.SCULK)) {
				return pos.immutable();
			}
		}
		return null;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HEIGHT_LEFT).add(BlockStateProperties.AXIS).add(UPSIDEDOWN).add(TOTAL_HEIGHT);
	}
}
