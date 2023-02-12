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

package net.frozenblock.wilderwild.world.additions.feature;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public final class WilderMiscConfigured {
	private WilderMiscConfigured() {
		throw new UnsupportedOperationException("WilderMiscConfigured contains only static declarations.");
	}

	public static final RuleTest PACKED_MUD_REPLACEABLE = new TagMatchTest(WilderBlockTags.PACKED_MUD_REPLACEABLE);
	public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);

	public static final ResourceKey<ConfiguredFeature<?, ?>> BLANK_SHUT_UP = key("blank_shut_up");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_COARSE_DIRT = key("disk_coarse_dirt");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_MUD = key("disk_mud");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MUD_PATH = key("mud_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_PATH = key("coarse_dirt_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_PATH = key("moss_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SANDSTONE_PATH = key("sandstone_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PACKED_MUD_PATH = key("packed_mud_path");
	//CYPRESS WETLANDS
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_SAND_PATH = key("under_water_sand_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_GRAVEL_PATH = key("under_water_gravel_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_CLAY_PATH = key("under_water_clay_path");
	//BEACH & RIVERS
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_CLAY_PATH_BEACH = key("under_water_clay_path_beach");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDER_WATER_GRAVEL_PATH_RIVER = key("under_water_gravel_path_river");
	//I FORGOT
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PACKED_MUD = key("ore_packed_mud");
	// BADLANDS
	public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_DIRT_PATH_SMALL = key("coarse_dirt_path_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PACKED_MUD_PATH_BADLANDS = key("packed_mud_path_badlands");
	//JELLYFISH CAVES
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CALCITE = key("ore_calcite");

	public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_POOL = key("deepslate_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_POOL = key("stone_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPWARDS_MESOGLEA_PILLAR = key("blue_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA_PILLAR = key("purple_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DOWNWARDS_MESOGLEA_PILLAR = key("downwards_blue_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DOWNWARDS_PURPLE_MESOGLEA_PILLAR = key("downwards_purple_mesoglea_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA_PATH = key("blue_mesoglea_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA_PATH = key("purple_mesoglea_path");
	// OASIS
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAND_POOL = key("sand_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MESSY_SAND_POOL = key("messy_sand_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATH = key("grass_path");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_PATH_OASIS = key("moss_path_oasis");
	//ARID SAVANNA
	public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_COARSE_PATH = key("arid_coarse_dirt_path");

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, WilderSharedConstants.id(path));
	}
}
