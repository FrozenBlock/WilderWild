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
import org.jetbrains.annotations.Nullable;

public class SpongeBudFeature extends Feature<SpongeBudFeatureConfig> {

	public SpongeBudFeature(Codec<SpongeBudFeatureConfig> codec) {
		super(codec);
	}

	public static boolean generate(
		WorldGenLevel level,
		BlockPos pos,
		BlockState state,
		SpongeBudFeatureConfig config,
		List<Direction> directions
	) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		for (Direction direction : directions) {
			BlockState offsetState = level.getBlockState(mutable.setWithOffset(pos, direction));
			if (!offsetState.is(config.canPlaceOn)) continue;
			final BlockState placementState = getStateForPlacement(level.getRandom(), state, level, pos, direction);
			if (placementState == null) return false;
			if (!placementState.getValue(SpongeBudBlock.WATERLOGGED)) continue;
			level.setBlock(pos, placementState, Block.UPDATE_ALL);
			level.getChunk(pos).markPosForPostprocessing(pos);
			return true;
		}
		return false;
	}

	@Nullable
	private static BlockState getStateForPlacement(
		RandomSource random,
		BlockState currentState,
		BlockGetter level,
		BlockPos pos,
		Direction lookingDirection
	) {
		if (!isValidStateForPlacement(level, pos, lookingDirection)) return null;

		BlockState state;
		if (currentState.is(WWBlocks.SPONGE_BUD)) {
			state = currentState;
		} else if (currentState.getFluidState().isSourceOfType(Fluids.WATER)) {
			state = WWBlocks.SPONGE_BUD.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
		} else {
			state = WWBlocks.SPONGE_BUD.defaultBlockState();
		}

		if (lookingDirection.getAxis() == Direction.Axis.Y) {
			state = state
				.setValue(SpongeBudBlock.FACE, lookingDirection == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)
				.setValue(SpongeBudBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
		} else {
			state = state.setValue(SpongeBudBlock.FACE, AttachFace.WALL)
				.setValue(SpongeBudBlock.FACING, lookingDirection.getOpposite());
		}

		return state.setValue(SpongeBudBlock.AGE, random.nextInt(SpongeBudBlock.MAX_AGE));
	}

	private static boolean isValidStateForPlacement(BlockGetter level, BlockPos pos, Direction direction) {
		final BlockPos offsetPos = pos.relative(direction);
		return canAttachTo(level, direction, offsetPos, level.getBlockState(offsetPos));
	}

	private static boolean canAttachTo(BlockGetter level, Direction direction, BlockPos pos, BlockState state) {
		return Block.isFaceFull(state.getBlockSupportShape(level, pos), direction.getOpposite())
			|| Block.isFaceFull(state.getCollisionShape(level, pos), direction.getOpposite());
	}

	@Override
	public boolean place(FeaturePlaceContext<SpongeBudFeatureConfig> context) {
		final WorldGenLevel level = context.level();
		final BlockPos origin = context.origin();
		final RandomSource random = context.random();
		final SpongeBudFeatureConfig config = context.config();

		if (!BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE.test(level, origin)) return false;
		final List<Direction> directions = config.shuffleDirections(random);
		if (generate(level, origin, level.getBlockState(origin), config, directions)) return true;

		final BlockPos.MutableBlockPos mutable = origin.mutable();
		for (Direction direction : directions) {
			mutable.set(origin);
			final List<Direction> directions2 = config.shuffleDirections(random, direction.getOpposite());
			for (int i = 0; i < config.searchRange; ++i) {
				mutable.setWithOffset(origin, direction);
				BlockState state = level.getBlockState(mutable);
				if (!BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE.test(level, mutable) && !state.is(WWBlocks.SPONGE_BUD)) break;
				if (generate(level, mutable, state, config, directions2)) return true;
			}
		}
		return false;
	}

}
