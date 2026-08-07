/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public record SpongeBudFeature(
	int searchRange,
	boolean placeOnFloor,
	boolean placeOnCeiling,
	boolean placeOnWalls,
	HolderSet<Block> canPlaceOn,
	ObjectArrayList<Direction> directions
) implements Feature {
	public static final MapCodec<SpongeBudFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter(SpongeBudFeature::searchRange),
		Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter(SpongeBudFeature::placeOnFloor),
		Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter(SpongeBudFeature::placeOnCeiling),
		Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter(SpongeBudFeature::placeOnWalls),
		RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(SpongeBudFeature::canPlaceOn)
	).apply(instance, SpongeBudFeature::new));

	@Override
	public MapCodec<? extends Feature> codec() {
		return CODEC;
	}

	public SpongeBudFeature(int searchRange, boolean placeOnFloor, boolean placeOnCeiling, boolean placeOnWalls, HolderSet<Block> canPlaceOn) {
		this(searchRange, placeOnFloor, placeOnCeiling, placeOnWalls, canPlaceOn, new ObjectArrayList<>(6));
		if (placeOnCeiling) this.directions.add(Direction.UP);
		if (placeOnFloor) this.directions.add(Direction.DOWN);

		if (placeOnWalls) {
			for (Direction direction : Direction.Plane.HORIZONTAL) this.directions.add(direction);
		}
	}

	public boolean generate(WorldGenLevel level, BlockPos pos, BlockState state, List<Direction> directions) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		for (Direction direction : directions) {
			BlockState offsetState = level.getBlockState(mutable.setWithOffset(pos, direction));
			if (!offsetState.is(this.canPlaceOn)) continue;

			final BlockState placementState = getStateForPlacement(level.getRandom(), state, level, pos, direction);
			if (placementState == null) return false;

			if (!placementState.getValue(SpongeBudBlock.WATERLOGGED)) continue;

			level.setBlockAndUpdate(pos, placementState);
			level.getChunk(pos).markPosForPostProcessing(pos);
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
		if (currentState.is(WWBlocks.SPONGE_BUD.get())) {
			state = currentState;
		} else if (currentState.getFluidState().isSourceOfType(Fluids.WATER)) {
			state = WWBlocks.SPONGE_BUD.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
		} else {
			state = WWBlocks.SPONGE_BUD.get().defaultBlockState();
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
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		if (!BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE.test(level, origin)) return false;

		final List<Direction> directions = Util.shuffledCopy(this.directions, random);
		if (this.generate(level, origin, level.getBlockState(origin), directions)) return true;

		final BlockPos.MutableBlockPos mutable = origin.mutable();
		for (Direction direction : directions) {
			mutable.set(origin);
			final List<Direction> directionWithoutCurrent = Util.toShuffledList(this.directions.stream().filter(d -> d != direction.getOpposite()), random);
			for (int i = 0; i < this.searchRange; ++i) {
				mutable.setWithOffset(origin, direction);
				BlockState state = level.getBlockState(mutable);
				if (!BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE.test(level, mutable) && !state.is(WWBlocks.SPONGE_BUD.get())) break;
				if (this.generate(level, mutable, state, directionWithoutCurrent)) return true;
			}
		}
		return false;
	}
}
