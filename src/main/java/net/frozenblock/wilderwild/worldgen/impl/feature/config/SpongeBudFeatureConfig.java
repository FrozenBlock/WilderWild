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

package net.frozenblock.wilderwild.worldgen.impl.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class SpongeBudFeatureConfig implements FeatureConfiguration {
	public static final Codec<SpongeBudFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
		instance.group(
			Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter(config -> config.searchRange),
			Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter(config -> config.placeOnFloor),
			Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter(config -> config.placeOnCeiling),
			Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter(config -> config.placeOnWalls),
			TagKey.codec(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(config -> config.canPlaceOn)
		).apply(instance, SpongeBudFeatureConfig::new)
	);

	public final int searchRange;
	public final boolean placeOnFloor;
	public final boolean placeOnCeiling;
	public final boolean placeOnWalls;
	public final TagKey<Block> canPlaceOn;
	private final ObjectArrayList<Direction> directions;

	public SpongeBudFeatureConfig(int searchRange, boolean placeOnFloor, boolean placeOnCeiling, boolean placeOnWalls, TagKey<Block> canPlaceOn) {
		this.searchRange = searchRange;
		this.placeOnFloor = placeOnFloor;
		this.placeOnCeiling = placeOnCeiling;
		this.placeOnWalls = placeOnWalls;
		this.canPlaceOn = canPlaceOn;
		this.directions = new ObjectArrayList<>(6);
		if (placeOnCeiling) this.directions.add(Direction.UP);
		if (placeOnFloor) this.directions.add(Direction.DOWN);

		if (placeOnWalls) {
			for (Direction direction : Direction.Plane.HORIZONTAL) this.directions.add(direction);
		}
	}

	public List<Direction> shuffleDirections(RandomSource random, Direction excluded) {
		return Util.toShuffledList(this.directions.stream().filter((direction) -> direction != excluded), random);
	}

	public List<Direction> shuffleDirections(RandomSource random) {
		return Util.shuffledCopy(this.directions, random);
	}
}
