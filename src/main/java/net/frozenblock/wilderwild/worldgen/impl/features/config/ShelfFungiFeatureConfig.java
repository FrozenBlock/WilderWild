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

package net.frozenblock.wilderwild.worldgen.impl.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Objects;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ShelfFungiFeatureConfig implements FeatureConfiguration {
	public static final Codec<ShelfFungiFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block")
				.flatXmap(ShelfFungiFeatureConfig::validateBlock, DataResult::success)
				.orElse(WWBlocks.BROWN_SHELF_FUNGI)
				.forGetter(config -> config.fungus),
			Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter(
				config -> config.searchRange
			),
			Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter(
				config -> config.placeOnFloor
			),
			Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter(
				config -> config.placeOnCeiling
			),
			Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter(
				config -> config.placeOnWalls
			),
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(
				config -> config.canPlaceOn
			)
		).apply(instance, ShelfFungiFeatureConfig::new)
	);

	public final ShelfFungiBlock fungus;
	public final int searchRange;
	public final boolean placeOnFloor;
	public final boolean placeOnCeiling;
	public final boolean placeOnWalls;
	public final HolderSet<Block> canPlaceOn;
	private final ObjectArrayList<Direction> directions;

	public ShelfFungiFeatureConfig(ShelfFungiBlock fungus, int searchRange, boolean placeOnFloor, boolean placeOnCeiling, boolean placeOnWalls, HolderSet<Block> canPlaceOn) {
		this.fungus = fungus;
		this.searchRange = searchRange;
		this.placeOnFloor = placeOnFloor;
		this.placeOnCeiling = placeOnCeiling;
		this.placeOnWalls = placeOnWalls;
		this.canPlaceOn = canPlaceOn;
		this.directions = new ObjectArrayList<>(6);
		if (placeOnCeiling) {
			this.directions.add(Direction.UP);
		}

		if (placeOnFloor) {
			this.directions.add(Direction.DOWN);
		}

		if (placeOnWalls) {
			Direction.Plane var10000 = Direction.Plane.HORIZONTAL;
			ObjectArrayList<Direction> var10001 = this.directions;
			Objects.requireNonNull(var10001);
			var10000.forEach(var10001::add);
		}

	}

	private static DataResult<ShelfFungiBlock> validateBlock(Block block) {
		DataResult<ShelfFungiBlock> var10000;
		if (block instanceof ShelfFungiBlock shelfFungusBlock) {
			var10000 = DataResult.success(shelfFungusBlock);
		} else {
			var10000 = DataResult.error(() -> "Growth block should be a shelf fungus block bruh bruh bruh bruh bruh");
		}

		return var10000;
	}

	public List<Direction> shuffleDirections(RandomSource random, Direction excluded) {
		return Util.toShuffledList(this.directions.stream().filter((direction) -> direction != excluded), random);
	}

	public List<Direction> shuffleDirections(RandomSource random) {
		return Util.shuffledCopy(this.directions, random);
	}
}
