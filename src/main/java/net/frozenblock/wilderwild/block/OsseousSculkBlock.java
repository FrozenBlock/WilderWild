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
import java.util.function.Function;
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
import org.jetbrains.annotations.Nullable;

public class OsseousSculkBlock extends Block implements SculkBehaviour {
	private static final Function<BlockState, Boolean> TOP_SEARCH_CONDITION = OsseousSculkBlock::isSafeToReplace;
	private static final Function<BlockState, Boolean> BOTTOM_SEARCH_CONDITION = state -> state.is(Blocks.SCULK);
	private static final int GROWTH_COST = 1;
	public static final int GROWTH_CHANCE = 2;
	public static final float HANGING_TENDRIL_CHANCE = 0.3F;
	public static final float HANGING_TENDRIL_WORLDGEN_CHANCE = 0.4F;
	public static final int CATALYST_GROWTH_CHANCE = 15;
	public static final int SCULK_CONVERSION_CHANCE = 15;
	public static final float RIB_CAGE_CHANCE = 0.2F;
	private static final ConstantInt EXPERIENCE = ConstantInt.of(3);
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	public static final MapCodec<OsseousSculkBlock> CODEC = simpleCodec(OsseousSculkBlock::new);

	public OsseousSculkBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
	}

	@Override
	protected MapCodec<? extends OsseousSculkBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
	}

	@Override
	public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
		super.spawnAfterBreak(state, level, pos, stack, dropExperience);
		if (dropExperience) this.tryDropExperience(level, pos, stack, EXPERIENCE);
	}

	@Override
	public int attemptUseCharge(
		SculkSpreader.ChargeCursor cursor,
		LevelAccessor level,
		BlockPos catalystPos,
		RandomSource random,
		SculkSpreader spreader,
		boolean shouldConvertBlocks
	) {
		final boolean isWorldGeneration = spreader.isWorldGeneration();
		final int cursorCharge = cursor.getCharge();
		final BlockPos pos = cursor.getPos();
		if (!isWorldGeneration && !(cursorCharge != 0 && random.nextInt(GROWTH_CHANCE) == 0)) return cursorCharge;
		if (!isWorldGeneration && pos.closerThan(catalystPos, spreader.noGrowthRadius())) return cursorCharge;

		final Optional<BlockPos> possibleTopPos = getPillarEnd(level, pos, true);
		if (possibleTopPos.isEmpty()) return cursorCharge;
		final BlockPos topPos = possibleTopPos.get();
		final BlockPos.MutableBlockPos mutable = topPos.mutable();
		final BlockState topState = level.getBlockState(topPos);
		final Direction direction = topState.getValue(FACING);

		if (!Direction.Plane.VERTICAL.test(direction)) return cursorCharge;
		final BlockState offsetState = level.getBlockState(mutable.move(direction));
		if (!offsetState.canBeReplaced() && offsetState.getBlock() != Blocks.SCULK_VEIN) return cursorCharge;

		final BlockState growthState = getGrowthState(random, direction);
		placeRib: {
			if (growthState.getBlock() != this || direction != Direction.DOWN || random.nextFloat() > RIB_CAGE_CHANCE) break placeRib;

			final Direction nextDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
			if (!isSafeToReplace(level.getBlockState(mutable.setWithOffset(topPos, nextDirection)))) break placeRib;

			final BlockState ribState = this.defaultBlockState().setValue(FACING, nextDirection);
			level.setBlock(mutable, ribState, UPDATE_ALL);
			playPlaceSound(level, mutable, ribState);

			if (!WWBlockConfig.HANGING_TENDRIL_GENERATION || random.nextFloat() > (isWorldGeneration ? HANGING_TENDRIL_WORLDGEN_CHANCE : HANGING_TENDRIL_CHANCE)) break placeRib;
			if (!isSafeToReplace(level.getBlockState(mutable.move(Direction.DOWN)))) break placeRib;

			final BlockState tendrilState = WWBlocks.HANGING_TENDRIL.defaultBlockState();
			level.setBlock(mutable, tendrilState, UPDATE_ALL);
			playPlaceSound(level, mutable, tendrilState);
		}

		level.setBlock(mutable.setWithOffset(topPos, direction), growthState, UPDATE_ALL);
		playPlaceSound(level, mutable, growthState);
		convertBottomToSculk(level, mutable, random);
		if (!isWorldGeneration) return Math.max(0, cursorCharge - GROWTH_COST);
		return cursorCharge;
	}

	private static void playPlaceSound(LevelAccessor level, BlockPos pos, BlockState state) {
		final SoundType soundType = state.getSoundType();
		level.playSound(null, pos, soundType.getPlaceSound(), SoundSource.BLOCKS, soundType.getVolume(), soundType.getPitch() * 0.8F);
	}

	private BlockState getGrowthState(RandomSource random, Direction direction) {
		BlockState state = this.defaultBlockState().setValue(FACING, direction);
		if (direction == Direction.UP && random.nextInt(CATALYST_GROWTH_CHANCE) == 0) state = Blocks.SCULK_CATALYST.defaultBlockState();
		return state;
	}

	private void convertBottomToSculk(LevelAccessor level, BlockPos topPos, RandomSource random) {
		final Optional<BlockPos> possibleBottom = getPillarEnd(level, topPos, false);
		if (possibleBottom.isEmpty()) return;
		final BlockPos bottom = possibleBottom.get();
		final BlockState bottomState = level.getBlockState(bottom);
		if (bottomState.is(this) && random.nextInt(0, SCULK_CONVERSION_CHANCE) == 0) this.convertToSculk(level, bottom);
	}

	public void convertToSculk(LevelAccessor level, BlockPos pos) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		final BlockState state = level.getBlockState(mutable);
		if (!state.is(this)) return;

		BlockState stateReplace;
		Direction oppositeDirection;
		for (Direction direction : Direction.values()) {
			stateReplace = level.getBlockState(mutable.setWithOffset(pos, direction));
			oppositeDirection = direction.getOpposite();
			if (stateReplace.is(Blocks.SCULK_VEIN)) {
				stateReplace = stateReplace.setValue(MultifaceBlock.getFaceProperty(oppositeDirection), false);
				if (MultifaceBlock.availableFaces(stateReplace).isEmpty()) stateReplace = stateReplace.getFluidState().createLegacyBlock();
				level.setBlock(mutable, stateReplace, UPDATE_ALL);
			} else if (stateReplace.is(this) && stateReplace.hasProperty(FACING) && stateReplace.getValue(FACING) == direction) {
				placeVeinsAround(level, mutable.mutable());
			}
		}
		mutable.setWithOffset(pos, state.getValue(FACING));
		placeVeinsAround(level, mutable.mutable());
		level.setBlock(pos, Blocks.SCULK.defaultBlockState(), UPDATE_ALL);
	}

	public static void placeVeinsAround(LevelAccessor level, BlockPos pos) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.values()) {
			final BlockState replacingState = level.getBlockState(mutable.setWithOffset(pos, direction));
			final Direction oppositeDirection = direction.getOpposite();

			BlockState stateSetTo = null;
			if (replacingState.is(Blocks.SCULK_VEIN)) {
				stateSetTo = replacingState.setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true);
			} else if (replacingState.isAir() && replacingState.getFluidState().isEmpty()) {
				stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true);
			} else if (replacingState.getBlock() == Blocks.WATER) {
				stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(oppositeDirection), true)
					.setValue(BlockStateProperties.WATERLOGGED, true);
			}

			if (stateSetTo != null) level.setBlock(mutable, stateSetTo, UPDATE_ALL);
		}
	}

	public static boolean isSafeToReplace(BlockState state) {
		return state.canBeReplaced() || state.is(Blocks.SCULK_VEIN);
	}

	public Optional<BlockPos> getPillarEnd(LevelAccessor level, BlockPos pos, boolean top) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		final BlockPos.MutableBlockPos searchingMutable = pos.mutable();

		BlockState state = level.getBlockState(mutable);
		if (state.getBlock() != this) return Optional.empty();

		final int directionSteps = top ? 1 : -1;
		final Function<BlockState, Boolean> searchCondition = top ? TOP_SEARCH_CONDITION : BOTTOM_SEARCH_CONDITION;
		for (int i = 0; i < 32; i++) {
			if (state.getBlock() != this) return Optional.empty();
			final BlockState searchingState = level.getBlockState(searchingMutable.move(state.getValue(FACING), directionSteps));
			if (searchCondition.apply(searchingState)) return Optional.of(mutable.immutable());
			mutable.set(searchingMutable);
			state = searchingState;
		}
		return Optional.empty();
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
