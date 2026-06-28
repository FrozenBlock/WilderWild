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
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public record ShelfFungiFeature(
	Block placeBlock,
	int searchRange,
	boolean placeOnFloor,
	boolean placeOnCeiling,
	boolean placeOnWalls,
	HolderSet<Block> canPlaceOn,
	ObjectArrayList<Direction> directions
) implements Feature {
	public static final MapCodec<ShelfFungiFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BuiltInRegistries.BLOCK.byNameCodec().validate(ShelfFungiFeature::validateBlock).fieldOf("block").forGetter(ShelfFungiFeature::placeBlock),
		Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter(ShelfFungiFeature::searchRange),
		Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter(ShelfFungiFeature::placeOnFloor),
		Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter(ShelfFungiFeature::placeOnCeiling),
		Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter(ShelfFungiFeature::placeOnWalls),
		RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(ShelfFungiFeature::canPlaceOn)
	).apply(instance, ShelfFungiFeature::new));

	private static DataResult<Block> validateBlock(final Block block) {
		return block instanceof ShelfFungiBlock shelfFungiBlock
			? DataResult.success(shelfFungiBlock)
			: DataResult.error(() -> "Growth block should be a shelf fungi block");
	}

	@Override
	public MapCodec<? extends Feature> codec() {
		return CODEC;
	}

	public ShelfFungiFeature(Block placeBlock, int searchRange, boolean placeOnFloor, boolean placeOnCeiling, boolean placeOnWalls, HolderSet<Block> canPlaceOn) {
		this(placeBlock, searchRange, placeOnFloor, placeOnCeiling, placeOnWalls, canPlaceOn, new ObjectArrayList<>(6));
		if (placeOnCeiling) this.directions.add(Direction.UP);
		if (placeOnFloor) this.directions.add(Direction.DOWN);
		if (placeOnWalls) {
			for (Direction direction : Direction.Plane.HORIZONTAL) this.directions.add(direction);
		}
	}

	public boolean generate(WorldGenLevel level, BlockPos pos, RandomSource random) {
		final MutableBlockPos mutable = pos.mutable();

		Direction placementDirection = null;
		for (Direction direction : Direction.values()) {
			final BlockState state = level.getBlockState(mutable.setWithOffset(pos, direction));
			if (!state.is(this.canPlaceOn)) continue;
			placementDirection = direction.getAxis() == Direction.Axis.Y ? Direction.Plane.HORIZONTAL.getRandomDirection(random) : direction.getOpposite();
			break;
		}

		if (placementDirection == null) return false;

		level.setBlockAndUpdate(
			pos,
			this.placeBlock.defaultBlockState()
				.setValue(ShelfFungiBlock.FACING, placementDirection)
				.setValue(ShelfFungiBlock.FACE, ShelfFungiBlock.getFace(placementDirection.getOpposite()))
				.setValue(ShelfFungiBlock.STAGE, random.nextInt(1, ShelfFungiBlock.MAX_STAGE))
		);
		level.getChunk(pos).markPosForPostProcessing(pos);
		return true;
	}

	private static boolean isAirOrWater(BlockState state) {
		return state.isAir() || state.is(Blocks.WATER);
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		origin = origin.above(random.nextInt(0, 4));

		if (!isAirOrWater(level.getBlockState(origin))) return false;

		final List<Direction> list = Util.shuffledCopy(this.directions, random);
		if (this.generate(level, origin, random)) return true;

		final MutableBlockPos mutable = origin.mutable();
		for (Direction direction : list) {
			for (int i = 0; i < this.searchRange; ++i) {
				mutable.setWithOffset(origin, direction);
				final BlockState state = level.getBlockState(mutable);
				if (!isAirOrWater(state) && !state.is(this.placeBlock)) break;
				if (this.generate(level, mutable, random)) return true;
			}
		}
		return false;
	}
}
