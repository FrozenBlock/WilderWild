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

import net.frozenblock.lib.math.api.AdvancedMath;
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

public class OsseousSculkBlock extends RotatedPillarBlock implements SculkBehaviour {
	private static final ConstantInt EXPERIENCE = ConstantInt.of(3);
	public static final IntegerProperty HEIGHT_LEFT = RegisterProperties.PILLAR_HEIGHT_LEFT;
	public static final BooleanProperty UPSIDEDOWN = RegisterProperties.UPSIDE_DOWN;
	public static final IntegerProperty TOTAL_HEIGHT = RegisterProperties.TOTAL_HEIGHT;

    public OsseousSculkBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(HEIGHT_LEFT, 0).setValue(AXIS, Direction.Axis.Y).setValue(UPSIDEDOWN, false).setValue(TOTAL_HEIGHT, 0));
    }

	@Override
    public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (dropExperience) {
            this.tryDropExperience(level, pos, stack, EXPERIENCE);
        }
    }

    public static Direction getDir(Direction.Axis axis, boolean upsideDown, RandomSource random) {
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

	public static Direction.Axis getAxis(RandomSource random) {
		return random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
	}

    public static Direction.Axis getAxis(BlockPos pos) {
        return EasyNoiseSampler.sample(EasyNoiseSampler.perlinLocal, pos, 0.7, false, false) > 0 ? Direction.Axis.X : Direction.Axis.Z;
    }

    public void convertToSculk(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.is(this)) {
            Direction.Axis axis = state.getValue(AXIS);
            Direction dir = getDir(axis, state.getValue(UPSIDEDOWN), level.getRandom());
            if (level.getBlockState(pos.relative(dir)).is(this)) {
                BlockPos newPos = pos.relative(dir);
				BlockPos.MutableBlockPos mutableBlockPos = newPos.mutable();
                for (Direction direction : UPDATE_SHAPE_ORDER) {
					mutableBlockPos.set(newPos);
                    BlockState stateReplace = level.getBlockState(mutableBlockPos.move(direction));
                    BlockState stateSetTo = null;
                    if (stateReplace.is(Blocks.SCULK_VEIN)) {
                        stateSetTo = stateReplace.setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true);
                    }
                    if (stateReplace.isAir()) {
                        stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true);
                    }
                    if (stateReplace == Blocks.WATER.defaultBlockState()) {
                        stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true).setValue(BlockStateProperties.WATERLOGGED, true);
                    }
                    if (stateSetTo != null) {
                        level.setBlock(mutableBlockPos, stateSetTo, 3);
                    }
                }
                level.setBlock(pos, Blocks.SCULK.defaultBlockState(), 3);
            }
        }
    }

    @Override
    public int attemptUseCharge(SculkSpreader.@NotNull ChargeCursor cursor, @NotNull LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock) {
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
                    BlockState state = level.getBlockState(topPos);
                    pillarHeight = state.getValue(OsseousSculkBlock.HEIGHT_LEFT);
                    Direction direction = getDir(state.getValue(AXIS), state.getValue(UPSIDEDOWN), level.getRandom());
                    if (level.getBlockState(topPos.relative(direction)).isAir() || level.getBlockState(topPos.relative(direction)).getBlock() == Blocks.SCULK_VEIN) {
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
                                    Direction ribCage = getDir(getAxis(level.getRandom()), false, level.getRandom());
                                    if (isSafeToReplace(level.getBlockState(topPos.relative(ribCage)))) {
                                        level.setBlock(topPos.relative(ribCage), this.defaultBlockState().setValue(AXIS, getAxis(level.getRandom())).setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT)).setValue(HEIGHT_LEFT, 0), 3);
                                        if (isSafeToReplace(level.getBlockState(topPos.relative(ribCage).below()))) {
                                            if (random.nextDouble() > 0.7) {
                                                level.setBlock(topPos.relative(ribCage).below(), RegisterBlocks.HANGING_TENDRIL.defaultBlockState(), 3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        level.setBlock(topPos.relative(direction), blockState, 3);
						SoundType placedSoundType = blockState.getSoundType();
                        level.playSound(null, blockPos, placedSoundType.getPlaceSound(), SoundSource.BLOCKS, placedSoundType.getVolume(), placedSoundType.getPitch());
                        if (spreadManager.isWorldGeneration() && random.nextDouble() > 0.2) {
                            j = 0;
                        }
                        BlockPos bottom = getBottom(level, topPos.relative(direction), state.getValue(TOTAL_HEIGHT));
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

    public static boolean isSafeToReplace(BlockState state) {
        return state.is(Blocks.SCULK_VEIN) || state.isAir() || state.is(Blocks.WATER);
    }

    public void worldGenSpread(BlockPos blockPos, LevelAccessor level, RandomSource random) {
        if (level.getBlockState(blockPos).is(this)) {
            int pillarHeight = level.getBlockState(blockPos).getValue(HEIGHT_LEFT);
            BlockPos topPos = getTop(level, blockPos, pillarHeight);
            if (topPos != null) {
                BlockState state = level.getBlockState(topPos);
                pillarHeight = state.getValue(HEIGHT_LEFT);
                Direction direction = getDir(state.getValue(AXIS), state.getValue(UPSIDEDOWN), level.getRandom());
                if (level.getBlockState(topPos.relative(direction)).isAir() || level.getBlockState(topPos.relative(direction)).getBlock() == Blocks.SCULK_VEIN) {
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
                                Direction ribCage = getDir(getAxis(topPos), false, level.getRandom());
                                if (isSafeToReplace(level.getBlockState(topPos.relative(ribCage)))) {
                                    level.setBlock(topPos.relative(ribCage), this.defaultBlockState().setValue(AXIS, getAxis(topPos)).setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT)).setValue(HEIGHT_LEFT, 0), 3);
                                    if (isSafeToReplace(level.getBlockState(topPos.relative(ribCage).below()))) {
                                        if (random.nextDouble() > 0.66) {
                                            level.setBlock(topPos.relative(ribCage).below(), RegisterBlocks.HANGING_TENDRIL.defaultBlockState(), 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    level.setBlock(topPos.relative(direction), blockState, 3);
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

    public BlockPos getTop(LevelAccessor level, BlockPos pos, int max) {
        for (int i = 0; i < max; i++) {
            Block block = level.getBlockState(pos).getBlock();
            if (block != this) {
                return null;
            }
            Direction direction = getDir(level.getBlockState(pos).getValue(AXIS), level.getBlockState(pos).getValue(UPSIDEDOWN), level.getRandom());
            if (level.getBlockState(pos.relative(direction)).isAir() || level.getBlockState(pos.relative(direction)).getBlock() == Blocks.SCULK_VEIN) {
                return pos;
            }
            pos = pos.relative(direction);
        }
        return null;
    }

	public BlockPos getBottom(LevelAccessor level, BlockPos pos, int max) {
		for (int i = 0; i < max; i++) {
			Block block = level.getBlockState(pos).getBlock();
			if (block != this) {
				return null;
			}
			BlockState state = level.getBlockState(pos);
			Direction direction = getDir(state.getValue(AXIS), state.getValue(UPSIDEDOWN), level.getRandom()).getOpposite();
			if (level.getBlockState(pos.relative(direction)).is(Blocks.SCULK)) {
				return pos;
			}
			pos = pos.relative(direction);
		}
		return null;
	}

	@Override
    public int updateDecayDelay(int oldDecay) {
        return 1;
    }

	@Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HEIGHT_LEFT).add(BlockStateProperties.AXIS).add(UPSIDEDOWN).add(TOTAL_HEIGHT);
    }
}
