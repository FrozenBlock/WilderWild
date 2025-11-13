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
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		BlockState stateReplace;
		Direction oppositeDirection;
		for (Direction direction : UPDATE_SHAPE_ORDER) {
			stateReplace = level.getBlockState(mutable.setWithOffset(pos, direction));
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

			if (stateSetTo != null) level.setBlock(mutable, stateSetTo, UPDATE_ALL);
		}
	}

	@NotNull
	@Override
	protected MapCodec<? extends OsseousSculkBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
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
		final boolean isWorldGeneration = spreader.isWorldGeneration();
		final int cursorCharge = cursor.getCharge();
		final int cost = 1;
		final BlockPos pos = cursor.getPos();
		if (isWorldGeneration || (cursorCharge != 0 && random.nextInt(GROWTH_CHANCE) == 0)) {
			if (isWorldGeneration || !pos.closerThan(catalystPos, spreader.noGrowthRadius())) {
				final Optional<BlockPos> possibleTopPos = getTop(level, pos);
				if (possibleTopPos.isEmpty()) return cursorCharge;
				final BlockPos topPos = possibleTopPos.get();
				final BlockPos.MutableBlockPos mutable = topPos.mutable();
				final BlockState state = level.getBlockState(topPos);
				final Direction direction = state.getValue(FACING);

				if (!Direction.Plane.VERTICAL.test(direction)) return cursorCharge;
				BlockState offsetState = level.getBlockState(mutable.move(direction));
				if (offsetState.canBeReplaced() || offsetState.getBlock() == Blocks.SCULK_VEIN) {
					final BlockState growthState = getGrowthState(random, direction);

					if (growthState.getBlock() == this && direction == Direction.DOWN && random.nextDouble() > RIB_CAGE_CHANCE) {
						final Direction nextDirection = getDir(getRandomAxisForBranch(random), random);
						if (isSafeToReplace(level.getBlockState(mutable.setWithOffset(topPos, nextDirection)))) {
							BlockState ribState = this.defaultBlockState().setValue(FACING, nextDirection);
							level.setBlock(mutable, ribState, UPDATE_ALL);
							playPlaceSound(level, mutable, ribState);
							if (WWBlockConfig.HANGING_TENDRIL_GENERATION
								&& isSafeToReplace(level.getBlockState(mutable.move(Direction.DOWN)))
								&& random.nextFloat() > (isWorldGeneration ? HANGING_TENDRIL_WORLDGEN_CHANCE : HANGING_TENDRIL_CHANCE)
							) {
								final BlockState tendrilState = WWBlocks.HANGING_TENDRIL.defaultBlockState();
								level.setBlock(mutable, tendrilState, UPDATE_ALL);
							}
						}
					}

					level.setBlock(mutable.setWithOffset(topPos, direction), growthState, UPDATE_ALL);
					playPlaceSound(level, mutable, growthState);
					workOnBottom(level, mutable, random);
					if (!isWorldGeneration) return Math.max(0, cursorCharge - cost);
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
		BlockState state = this.defaultBlockState().setValue(FACING, direction);
		if (direction == Direction.UP && random.nextInt(CATALYST_GROWTH_CHANCE) == 0) state = Blocks.SCULK_CATALYST.defaultBlockState();
		return state;
	}

	private void workOnBottom(@NotNull LevelAccessor level, @NotNull BlockPos topPos, RandomSource random) {
		final Optional<BlockPos> possibleBottom = getBottom(level, topPos);
		if (possibleBottom.isEmpty()) return;
		final BlockPos bottom = possibleBottom.get();
		final BlockState bottomState = level.getBlockState(bottom);
		if (bottomState.is(this) && random.nextInt(0, SCULK_CONVERSION_CHANCE) == 0) this.convertToSculk(level, bottom);
	}


	public void convertToSculk(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		final BlockState state = level.getBlockState(mutable);
		if (!state.is(this)) return;
		BlockState stateReplace;
		Direction oppositeDirection;
		for (Direction direction : UPDATE_SHAPE_ORDER) {
			stateReplace = level.getBlockState(mutable.setWithOffset(pos, direction));
			oppositeDirection = direction.getOpposite();
			if (stateReplace.is(Blocks.SCULK_VEIN)) {
				stateReplace = stateReplace.setValue(MultifaceBlock.getFaceProperty(oppositeDirection), false);
				if (MultifaceBlock.availableFaces(stateReplace).isEmpty()) {
					stateReplace = stateReplace.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
				}
				level.setBlock(mutable, stateReplace, UPDATE_ALL);
			} else if (stateReplace.is(this) && stateReplace.hasProperty(FACING) && stateReplace.getValue(FACING) == direction) {
				placeVeinsAround(level, mutable.mutable());
			}
		}
		mutable.setWithOffset(pos, state.getValue(FACING));
		placeVeinsAround(level, mutable.mutable());
		level.setBlock(pos, Blocks.SCULK.defaultBlockState(), UPDATE_ALL);
	}

	public Optional<BlockPos> getTop(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockPos.MutableBlockPos mutableBlockPos2 = pos.mutable();

		BlockState blockState = level.getBlockState(mutableBlockPos);
		if (blockState.getBlock() != this) return Optional.empty();

		for (int i = 0; i < 32; i++) {
			if (blockState.getBlock() == this) {
				BlockState offsetState = level.getBlockState(mutableBlockPos2.move(blockState.getValue(FACING)));
				if (offsetState.canBeReplaced() || offsetState.getBlock() == Blocks.SCULK_VEIN) return Optional.of(mutableBlockPos.immutable());
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
				if (level.getBlockState(mutableBlockPos2.move(blockState.getValue(FACING), -1)).is(Blocks.SCULK)) return Optional.of(mutableBlockPos.immutable());
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
	public BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
