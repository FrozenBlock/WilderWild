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

import net.frozenblock.lib.levelgen.biome.api.modifications.data.selector.BiomeSelector;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class WWBiomeSelectors {
	public static final ResourceKey<BiomeSelector> GENERATE_GRASS = createKey("generate_grass");
	public static final ResourceKey<BiomeSelector> GENERATE_DRY_GRASS = createKey("generate_dry_grass");
	public static final ResourceKey<BiomeSelector> GENERATE_FLOWER = createKey("generate_flower");
	public static final ResourceKey<BiomeSelector> GENERATE_FIREFLY_BUSH = createKey("generate_firefly_bush");

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

	public static void bootstrap(BootstrapContext<BiomeSelector> context) {
		context.register(GENERATE_DRY_GRASS, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_DRY_GRASS));
		context.register(GENERATE_GRASS, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_GRASS));
		context.register(GENERATE_FLOWER, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_FLOWER));
		context.register(GENERATE_FIREFLY_BUSH, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_FIREFLY_BUSH));

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
	}

	private static ResourceKey<BiomeSelector> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.BIOME_SELECTOR, WWConstants.id(name));
	}

	private WWBiomeSelectors() {}
}
