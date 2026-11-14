/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.selector.BiomeSelector;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.tag.WWTreeFeatureBiomeTags;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
public final class WWBiomeSelectors {
	public static final ResourceKey<BiomeSelector> GENERATE_GRASS = createKey("generate_grass");
	public static final ResourceKey<BiomeSelector> GENERATE_DRY_GRASS = createKey("generate_dry_grass");
	public static final ResourceKey<BiomeSelector> GENERATE_FLOWER = createKey("generate_flower");
	public static final ResourceKey<BiomeSelector> GENERATE_FIREFLY_BUSH = createKey("generate_firefly_bush");

	public static final ResourceKey<BiomeSelector> GENERATE_TREE = createKey("generate_tree");
	public static final ResourceKey<BiomeSelector> CANNOT_GENERATE_TREE = createKey("cannot_generate_tree");
	public static final ResourceKey<BiomeSelector> GENERATE_BAOBAB = createKey("generate_baobab");
	public static final ResourceKey<BiomeSelector> CANNOT_GENERATE_BAOBAB = createKey("cannot_generate_baobab");
	public static final ResourceKey<BiomeSelector> GENERATE_PALM = createKey("generate_palm");
	public static final ResourceKey<BiomeSelector> GENERATE_WILLOW = createKey("generate_willow");
	public static final ResourceKey<BiomeSelector> CANNOT_GENERATE_WILLOW = createKey("cannot_generate_willow");
	public static final ResourceKey<BiomeSelector> IN_SAVANNA_TREE_TAG = createKey("in_savanna_tree_tag");
	public static final ResourceKey<BiomeSelector> IN_SWAMP_TREE_TAG = createKey("in_swamp_tree_tag");

	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_ACACIA = createKey("generate_leaf_litter_acacia");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_AZALEA = createKey("generate_leaf_litter_azalea");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_BAOBAB = createKey("generate_leaf_litter_baobab");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_BIRCH = createKey("generate_leaf_litter_birch");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_CHERRY = createKey("generate_leaf_litter_cherry");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_CYPRESS = createKey("generate_leaf_litter_cypress");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_JUNGLE = createKey("generate_leaf_litter_jungle");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_MANGROVE = createKey("generate_leaf_litter_mangrove");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_OAK = createKey("generate_leaf_litter_oak");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_DARK_OAK = createKey("generate_leaf_litter_dark_oak");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_PALE_OAK = createKey("generate_leaf_litter_pale_oak");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_PALM = createKey("generate_leaf_litter_palm");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_POPLAR = createKey("generate_leaf_litter_poplar");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_SPRUCE = createKey("generate_leaf_litter_spruce");
	public static final ResourceKey<BiomeSelector> GENERATE_LEAF_LITTER_WILLOW = createKey("generate_leaf_litter_willow");

	public static final ResourceKey<BiomeSelector> HAS_BUTTERFLY = createKey("has_butterfly");
	public static final ResourceKey<BiomeSelector> BUTTERFLY_VERY_RARE_SPAWN = createKey("butterfly_very_rare_spawn");
	public static final ResourceKey<BiomeSelector> NOT_BUTTERFLY_VERY_RARE_SPAWN = createKey("not_butterfly_very_rare_spawn");
	public static final ResourceKey<BiomeSelector> BUTTERFLY_COMMON_SPAWN = createKey("butterfly_common_spawn");
	public static final ResourceKey<BiomeSelector> NOT_BUTTERFLY_COMMON_SPAWN = createKey("not_butterfly_common_spawn");

	public static final ResourceKey<BiomeSelector> HAS_JELLYFISH = createKey("has_jellyfish");
	public static final ResourceKey<BiomeSelector> JELLYFISH_COMMON_SPAWN = createKey("jellyfish_common_spawn");
	public static final ResourceKey<BiomeSelector> NOT_JELLYFISH_COMMON_SPAWN = createKey("not_jellyfish_common_spawn");

	public static final ResourceKey<BiomeSelector> WATER_COLOR_HOT = createKey("water_color_hot");
	public static final ResourceKey<BiomeSelector> WATER_COLOR_LUKEWARM = createKey("water_color_lukewarm");
	public static final ResourceKey<BiomeSelector> WATER_COLOR_SNOWY = createKey("water_color_snowy");
	public static final ResourceKey<BiomeSelector> WATER_COLOR_FROZEN = createKey("water_color_frozen");
	public static final ResourceKey<BiomeSelector> FOLIAGE_COLOR_BADLANDS = createKey("foliage_color_badlands");
	public static final ResourceKey<BiomeSelector> GRASS_COLOR_DAPPLED_FOREST = createKey("grass_color_dappled_forest");


	@ApiStatus.Internal
	public static void bootstrap(BootstrapContext<BiomeSelector> context) {
		context.register(GENERATE_DRY_GRASS, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_DRY_GRASS));
		context.register(GENERATE_GRASS, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_GRASS));
		context.register(GENERATE_FLOWER, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_FLOWER));
		context.register(GENERATE_FIREFLY_BUSH, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_FIREFLY_BUSH));

		context.register(GENERATE_TREE, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_TREE));
		context.register(CANNOT_GENERATE_TREE, BiomeSelector.not(context, GENERATE_TREE));
		context.register(GENERATE_BAOBAB, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_BAOBAB));
		context.register(CANNOT_GENERATE_BAOBAB, BiomeSelector.not(context, GENERATE_BAOBAB));
		context.register(GENERATE_PALM, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_PALM));
		context.register(GENERATE_WILLOW, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_WILLOW));
		context.register(CANNOT_GENERATE_WILLOW, BiomeSelector.not(context, GENERATE_WILLOW));
		context.register(IN_SAVANNA_TREE_TAG, BiomeSelector.inBiomeTag(context, WWTreeFeatureBiomeTags.TREE_SAVANNA));
		context.register(IN_SWAMP_TREE_TAG, BiomeSelector.inBiomeTag(context, WWTreeFeatureBiomeTags.TREE_SWAMP));

		context.register(GENERATE_LEAF_LITTER_ACACIA, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_ACACIA));
		context.register(GENERATE_LEAF_LITTER_AZALEA, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_AZALEA));
		context.register(GENERATE_LEAF_LITTER_BAOBAB, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_BAOBAB));
		context.register(GENERATE_LEAF_LITTER_BIRCH, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_BIRCH));
		context.register(GENERATE_LEAF_LITTER_CHERRY, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_CHERRY));
		context.register(GENERATE_LEAF_LITTER_CYPRESS, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_CYPRESS));
		context.register(GENERATE_LEAF_LITTER_JUNGLE, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_JUNGLE));
		context.register(GENERATE_LEAF_LITTER_MANGROVE, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_MANGROVE));
		context.register(GENERATE_LEAF_LITTER_OAK, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_OAK));
		context.register(GENERATE_LEAF_LITTER_DARK_OAK, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_DARK_OAK));
		context.register(GENERATE_LEAF_LITTER_PALE_OAK, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_PALE_OAK));
		context.register(GENERATE_LEAF_LITTER_PALM, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_PALM));
		context.register(GENERATE_LEAF_LITTER_POPLAR, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_POPLAR));
		context.register(GENERATE_LEAF_LITTER_SPRUCE, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_SPRUCE));
		context.register(GENERATE_LEAF_LITTER_WILLOW, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_LEAF_LITTER_WILLOW));

		context.register(HAS_BUTTERFLY, BiomeSelector.inBiomeTag(context, WWBiomeTags.HAS_BUTTERFLY));
		context.register(BUTTERFLY_VERY_RARE_SPAWN, BiomeSelector.inBiomeTag(context, WWBiomeTags.BUTTERFLY_VERY_RARE_SPAWN));
		context.register(NOT_BUTTERFLY_VERY_RARE_SPAWN, BiomeSelector.not(context, BUTTERFLY_VERY_RARE_SPAWN));
		context.register(BUTTERFLY_COMMON_SPAWN, BiomeSelector.inBiomeTag(context, WWBiomeTags.BUTTERFLY_COMMON_SPAWN));
		context.register(NOT_BUTTERFLY_COMMON_SPAWN, BiomeSelector.not(context, BUTTERFLY_COMMON_SPAWN));

		context.register(HAS_JELLYFISH, BiomeSelector.inBiomeTag(context, WWBiomeTags.HAS_JELLYFISH));
		context.register(JELLYFISH_COMMON_SPAWN, BiomeSelector.inBiomeTag(context, WWBiomeTags.JELLYFISH_COMMON_SPAWN));
		context.register(NOT_JELLYFISH_COMMON_SPAWN, BiomeSelector.not(context, JELLYFISH_COMMON_SPAWN));

		context.register(WATER_COLOR_HOT, BiomeSelector.configPredicate(context, WWConfigPredicates.WATER_COLOR_HOT));
		context.register(WATER_COLOR_LUKEWARM, BiomeSelector.configPredicate(context, WWConfigPredicates.WATER_COLOR_LUKEWARM));
		context.register(WATER_COLOR_SNOWY, BiomeSelector.configPredicate(context, WWConfigPredicates.WATER_COLOR_SNOWY));
		context.register(WATER_COLOR_FROZEN, BiomeSelector.configPredicate(context, WWConfigPredicates.WATER_COLOR_FROZEN));
		context.register(FOLIAGE_COLOR_BADLANDS, BiomeSelector.configPredicate(context, WWConfigPredicates.FOLIAGE_COLOR_BADLANDS));
		context.register(GRASS_COLOR_DAPPLED_FOREST, BiomeSelector.configPredicate(context, WWConfigPredicates.GRASS_COLOR_DAPPLED_FOREST));
	}

	private static ResourceKey<BiomeSelector> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.BIOME_SELECTOR, WWConstants.id(name));
	}
}
