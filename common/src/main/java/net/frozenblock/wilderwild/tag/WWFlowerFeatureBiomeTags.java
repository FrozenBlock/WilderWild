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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class WWFlowerFeatureBiomeTags {
	// PLAINS
	public static final TagKey<Biome> PLAINS_FLOWERS = bind("plains_flowers");
	public static final TagKey<Biome> SNOWY_PLAINS_FLOWERS = bind("snowy_plains_flowers");
	public static final TagKey<Biome> SUNFLOWER_PLAINS_FLOWERS = bind("sunflower_plains_flowers");
	public static final TagKey<Biome> FLOWER_FIELD_FLOWERS = bind("flower_field_flowers");

	// FOREST
	public static final TagKey<Biome> FOREST_FLOWERS = bind("forest_flowers");
	public static final TagKey<Biome> SPARSE_FOREST_FLOWERS = bind("sparse_forest_flowers");
	public static final TagKey<Biome> MIXED_FOREST_FLOWERS = bind("mixed_forest_flowers");
	public static final TagKey<Biome> FLOWER_FOREST_FLOWERS = bind("flower_forest_flowers");
	public static final TagKey<Biome> DYING_FOREST_FLOWERS = bind("dying_forest_flowers");
	public static final TagKey<Biome> DYING_MIXED_FOREST_FLOWERS = bind("dying_mixed_forest_flowers");
	public static final TagKey<Biome> SNOWY_DYING_FOREST_FLOWERS = bind("snowy_dying_forest_flowers");

	// BIRCH FOREST
	public static final TagKey<Biome> WILDFLOWERS_BIRCH_FOREST = bind("wildflowers_birch_forest");
	public static final TagKey<Biome> BIRCH_FOREST_FLOWERS = bind("birch_forest_flowers");
	public static final TagKey<Biome> OLD_GROWTH_BIRCH_FOREST_FLOWERS = bind("old_growth_birch_forest_flowers");
	public static final TagKey<Biome> WILDFLOWERS_SEMI_BIRCH_FOREST = bind("wildflowers_semi_birch_forest");
	public static final TagKey<Biome> SEMI_BIRCH_FOREST_FLOWERS = bind("semi_birch_forest_flowers");
	public static final TagKey<Biome> BIRCH_JUNGLE_FLOWERS = bind("birch_jungle_flowers");
	public static final TagKey<Biome> SPARSE_BIRCH_JUNGLE_FLOWERS = bind("sparse_birch_jungle_flowers");
	public static final TagKey<Biome> BIRCH_TAIGA_FLOWERS = bind("birch_taiga_flowers");

	// DARK FOREST
	public static final TagKey<Biome> DARK_FOREST_FLOWERS = bind("dark_forest_flowers");
	public static final TagKey<Biome> DARK_BIRCH_FOREST_FLOWERS = bind("dark_birch_forest_flowers");
	public static final TagKey<Biome> DARK_TAIGA_FLOWERS = bind("dark_taiga_flowers");
	public static final TagKey<Biome> PALE_GARDEN_FLOWERS = bind("pale_garden_flowers");

	// JUNGLE
	public static final TagKey<Biome> JUNGLE_FLOWERS = bind("jungle_flowers");
	public static final TagKey<Biome> SPARSE_JUNGLE_FLOWERS = bind("sparse_jungle_flowers");

	// SAVANNA
	public static final TagKey<Biome> SAVANNA_FLOWERS = bind("savanna_flowers");

	// SWAMP
	public static final TagKey<Biome> MILKWEED_SWAMP = bind("milkweed_swamp");
	public static final TagKey<Biome> SWAMP_FLOWERS = bind("swamp_flowers");
	public static final TagKey<Biome> MANGROVE_SWAMP_FLOWERS = bind("mangrove_swamp_flowers");

	// CHERRY GROVE
	public static final TagKey<Biome> CHERRY_GROVE_FLOWERS = bind("cherry_grove_flowers");

	// AUTUMNAL
	public static final TagKey<Biome> MAPLE_FOREST_FLOWERS = bind("maple_forest_flowers");
	public static final TagKey<Biome> TUNDRA_FLOWERS = bind("tundra_flowers");
	public static final TagKey<Biome> DAPPLED_FOREST_FLOWERS = bind("dappled_forest_flowers");

	// MEADOW
	public static final TagKey<Biome> MEADOW_FLOWERS = bind("meadow_flowers");

	// TAIGA
	public static final TagKey<Biome> WINDSWEPT_FLOWERS = bind("windswept_flowers");

	private static TagKey<Biome> bind(String name) {
		return TagKey.create(Registries.BIOME, WWConstants.id("feature/flower/" + name));
	}

	private WWFlowerFeatureBiomeTags() {}
}
