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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class WWConfigPredicates {
	public static final ResourceKey<ConfigPredicate> SOUND_OVERRIDE_GRASS = createKey("sound_override_grass");
	public static final ResourceKey<ConfigPredicate> SOUND_OVERRIDE_MUSHROOM = createKey("sound_override_mushroom");
	public static final ResourceKey<ConfigPredicate> SOUND_OVERRIDE_LEAF = createKey("sound_override_leaf");
	public static final ResourceKey<ConfigPredicate> SOUND_OVERRIDE_MELON = createKey("sound_override_melon");
	public static final ResourceKey<ConfigPredicate> SOUND_OVERRIDE_MOSS = createKey("sound_override_moss");
	public static final ResourceKey<ConfigPredicate> SOUND_OVERRIDE_PALE_OAK_LEAVES = createKey("sound_override_pale_oak_leaves");
	public static final ResourceKey<ConfigPredicate> SOUND_OVERRIDE_PALE_OAK_LEAVES_DEFAULT = createKey("sound_override_pale_oak_leaves_default");
	public static final ResourceKey<ConfigPredicate> SOUND_OVERRIDE_PALE_OAK = createKey("sound_override_pale_oak");

	public static final ResourceKey<ConfigPredicate> GENERATE_FLOWER = createKey("generate_flower");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_ACACIA = createKey("generate_shelf_mushroom_acacia");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_BIRCH = createKey("generate_shelf_mushroom_birch");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_CHERRY = createKey("generate_shelf_mushroom_cherry");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_CYPRESS = createKey("generate_shelf_mushroom_cypress");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_JUNGLE = createKey("generate_shelf_mushroom_jungle");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_MANGROVE = createKey("generate_shelf_mushroom_mangrove");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_OAK = createKey("generate_shelf_mushroom_oak");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_DARK_OAK = createKey("generate_shelf_mushroom_dark_oak");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_PALE_OAK = createKey("generate_shelf_mushroom_pale_oak");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_POPLAR = createKey("generate_shelf_mushroom_poplar");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_SPRUCE = createKey("generate_shelf_mushroom_spruce");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_WILLOW = createKey("generate_shelf_mushroom_willow");
	public static final ResourceKey<ConfigPredicate> GENERATE_SHELF_MUSHROOM_MAPLE = createKey("generate_shelf_mushroom_maple");

	public static final ResourceKey<ConfigPredicate> GENERATE_RIVER_POOL = createKey("generate_river_pool");
	public static final ResourceKey<ConfigPredicate> GENERATE_CATTAIL = createKey("generate_cattail");
	public static final ResourceKey<ConfigPredicate> GENERATE_BARNACLES = createKey("generate_barnacles");
	public static final ResourceKey<ConfigPredicate> GENERATE_ALGAE = createKey("generate_algae");
	public static final ResourceKey<ConfigPredicate> GENERATE_SPONGE_BUD = createKey("generate_sponge_bud");
	public static final ResourceKey<ConfigPredicate> GENERATE_SEA_ANEMONE = createKey("generate_sea_anemone");
	public static final ResourceKey<ConfigPredicate> GENERATE_SEA_WHIP = createKey("generate_sea_whip");
	public static final ResourceKey<ConfigPredicate> GENERATE_TUBE_WORMS = createKey("generate_tube_worms");
	public static final ResourceKey<ConfigPredicate> GENERATE_HYDROTHERMAL_VENT = createKey("generate_hydrothermal_vent");
	public static final ResourceKey<ConfigPredicate> GENERATE_OCEAN_AUBURN_MOSS = createKey("generate_ocean_auburn_moss");

	public static final ResourceKey<ConfigPredicate> SPAWN_WOLF_VARIANT = createKey("spawn_wolf_variant");

	public static void bootstrap(BootstrapContext<ConfigPredicate> context) {
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);

		context.register(
			SOUND_OVERRIDE_GRASS,
			WWBlockConfig.GRASS_SOUNDS.equalTo(true)
		);
		context.register(
			SOUND_OVERRIDE_MUSHROOM,
			WWBlockConfig.MUSHROOM_BLOCK_SOUNDS.equalTo(true)
		);
		context.register(
			SOUND_OVERRIDE_LEAF,
			WWBlockConfig.LEAF_SOUNDS.equalTo(true)
		);
		context.register(
			SOUND_OVERRIDE_MELON,
			WWBlockConfig.MELON_SOUNDS.equalTo(true)
		);
		context.register(
			SOUND_OVERRIDE_MOSS,
			WWBlockConfig.MOSS_SOUNDS.equalTo(true)
		);
		context.register(
			SOUND_OVERRIDE_PALE_OAK_LEAVES,
			ConfigPredicate.allOf(
				HolderSet.direct(
					configPredicates.getOrThrow(SOUND_OVERRIDE_PALE_OAK),
					configPredicates.getOrThrow(SOUND_OVERRIDE_LEAF)
				)
			)
		);
		context.register(
			SOUND_OVERRIDE_PALE_OAK_LEAVES_DEFAULT,
			ConfigPredicate.allOf(
				HolderSet.direct(
					ConfigPredicate.not(configPredicates.getOrThrow(SOUND_OVERRIDE_PALE_OAK)).asHolder(),
					configPredicates.getOrThrow(SOUND_OVERRIDE_LEAF)
				)
			)
		);
		context.register(
			SOUND_OVERRIDE_PALE_OAK,
			WWBlockConfig.PALE_OAK_SOUNDS.equalTo(true)
		);

		context.register(
			GENERATE_FLOWER,
			WWWorldgenConfig.FLOWER_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_ACACIA,
			WWWorldgenConfig.ACACIA_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_BIRCH,
			WWWorldgenConfig.BIRCH_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_CHERRY,
			WWWorldgenConfig.CHERRY_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_CYPRESS,
			WWWorldgenConfig.CYPRESS_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_JUNGLE,
			WWWorldgenConfig.JUNGLE_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_MANGROVE,
			WWWorldgenConfig.MANGROVE_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_OAK,
			WWWorldgenConfig.OAK_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_DARK_OAK,
			WWWorldgenConfig.DARK_OAK_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_PALE_OAK,
			WWWorldgenConfig.PALE_OAK_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_POPLAR,
			WWWorldgenConfig.POPLAR_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_SPRUCE,
			WWWorldgenConfig.SPRUCE_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_WILLOW,
			WWWorldgenConfig.WILLOW_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SHELF_MUSHROOM_MAPLE,
			WWWorldgenConfig.MAPLE_SHELF_MUSHROOM_GENERATION.equalTo(true)
		);

		context.register(
			GENERATE_RIVER_POOL,
			WWWorldgenConfig.RIVER_POOL_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_CATTAIL,
			WWWorldgenConfig.CATTAIL_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_BARNACLES,
			WWWorldgenConfig.BARNACLES_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_ALGAE,
			WWWorldgenConfig.ALGAE_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SPONGE_BUD,
			WWWorldgenConfig.SPONGE_BUD_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SEA_ANEMONE,
			WWWorldgenConfig.SEA_ANEMONE_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_SEA_WHIP,
			WWWorldgenConfig.SEA_WHIP_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_TUBE_WORMS,
			WWWorldgenConfig.TUBE_WORMS_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_HYDROTHERMAL_VENT,
			WWWorldgenConfig.HYDROTHERMAL_VENT_GENERATION.equalTo(true)
		);
		context.register(
			GENERATE_OCEAN_AUBURN_MOSS,
			WWWorldgenConfig.OCEAN_AUBURN_MOSS_GENERATION.equalTo(true)
		);

		context.register(
			SPAWN_WOLF_VARIANT,
			WWEntityConfig.SPAWN_WOLF_VARIANTS.equalTo(true)
		);
	}

	private static ResourceKey<ConfigPredicate> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER, WWConstants.id(name));
	}

	private WWConfigPredicates() {}
}
