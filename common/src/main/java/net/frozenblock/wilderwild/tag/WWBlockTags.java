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
import net.minecraft.world.level.block.Block;

public final class WWBlockTags {
	public static final TagKey<Block> SOUND_GRASS = bind("sound/grass");
	public static final TagKey<Block> SOUND_FROZEN_GRASS = bind("sound/frozen_grass");
	public static final TagKey<Block> SOUND_DRY_GRASS = bind("sound/dry_grass");
	public static final TagKey<Block> SOUND_FLOWER = bind("sound/flower");
	public static final TagKey<Block> SOUND_LEAVES = bind("sound/leaves");
	public static final TagKey<Block> SOUND_CONIFER_LEAVES = bind("sound/conifer_leaves");
	public static final TagKey<Block> SOUND_CONIFER_LEAF_LITTER = bind("sound/conifer_leaf_litter");
	public static final TagKey<Block> SOUND_SAPLING = bind("sound/sapling");
	public static final TagKey<Block> SOUND_CACTUS = bind("sound/cactus");
	public static final TagKey<Block> SOUND_COARSE_DIRT = bind("sound/coarse_dirt");
	public static final TagKey<Block> SOUND_ICE = bind("sound/ice");
	public static final TagKey<Block> SOUND_FROSTED_ICE = bind("sound/frosted_ice");
	public static final TagKey<Block> SOUND_ICICLE = bind("sound/icicle");
	public static final TagKey<Block> SOUND_ICE_CRYSTAL = bind("sound/ice_crystal");
	public static final TagKey<Block> SOUND_MUSHROOM = bind("sound/mushroom");
	public static final TagKey<Block> SOUND_MUSHROOM_BLOCK = bind("sound/mushroom_block");
	public static final TagKey<Block> SOUND_SANDSTONE = bind("sound/sandstone");
	public static final TagKey<Block> SOUND_LILY_PAD = bind("sound/lily_pad");
	public static final TagKey<Block> SOUND_MELON = bind("sound/melon");
	public static final TagKey<Block> SOUND_MELON_STEM = bind("sound/melon_stem");
	public static final TagKey<Block> SOUND_GRAVEL = bind("sound/gravel");
	public static final TagKey<Block> SOUND_CLAY = bind("sound/clay");
	public static final TagKey<Block> SOUND_DEAD_BUSH = bind("sound/dead_bush");
	public static final TagKey<Block> SOUND_PODZOL = bind("sound/podzol");
	public static final TagKey<Block> SOUND_REINFORCED_DEEPSLATE = bind("sound/reinforced_deepslate");
	public static final TagKey<Block> SOUND_SUGAR_CANE = bind("sound/sugar_cane");
	public static final TagKey<Block> SOUND_WITHER_ROSE = bind("sound/wither_rose");
	public static final TagKey<Block> SOUND_MAGMA_BLOCK = bind("sound/magma_block");
	public static final TagKey<Block> SOUND_COCONUT = bind("sound/coconut");
	public static final TagKey<Block> SOUND_AUBURN_MOSS = bind("sound/auburn_moss");
	public static final TagKey<Block> SOUND_AUBURN_MOSS_CARPET = bind("sound/auburn_moss_carpet");
	public static final TagKey<Block> SOUND_PALE_MOSS = bind("sound/pale_moss");
	public static final TagKey<Block> SOUND_PALE_MOSS_CARPET = bind("sound/pale_moss_carpet");
	public static final TagKey<Block> SOUND_PALE_OAK_LEAVES = bind("sound/pale_oak_leaves");
	public static final TagKey<Block> SOUND_PALE_OAK_LEAF_LITTER = bind("sound/pale_oak_leaf_litter");
	public static final TagKey<Block> SOUND_PALE_OAK_WOOD = bind("sound/pale_oak_wood");
	public static final TagKey<Block> SOUND_PALE_OAK_WOOD_HANGING_SIGN = bind("sound/pale_oak_wood_hanging_sign");
	public static final TagKey<Block> SOUND_HOLLOWED_PALE_OAK_WOOD = bind("sound/hollowed_pale_oak_wood");

	public static final TagKey<Block> SNOW_GENERATION_CAN_SEARCH_THROUGH = bind("snow_generation_can_search_through");

	public static final TagKey<Block> WILLOW_ROOTS_CAN_GROW_THROUGH = bind("willow_roots_can_grow_through");
	public static final TagKey<Block> FALLEN_TREE_STUMP_PLACEABLE_ON = bind("fallen_tree_stump_placeable_on");

	public static final TagKey<Block> KILLS_TERMITE = bind("kills_termite");
	public static final TagKey<Block> BLOCKS_TERMITE = bind("blocks_termite");
	public static final TagKey<Block> CANNOT_SUPPORT_UPWARDS_TERMITE_MOVEMENT = bind("cannot_support_upwards_termite_movement");

	public static final TagKey<Block> ICICLE_CAN_GROW_UNDER = bind("icicle_can_grow_under");

	public static final TagKey<Block> ANCIENT_CITY_BLOCKS = bind("ancient_city_blocks");
	public static final TagKey<Block> SCULK_STAIR_REPLACEABLE_WORLDGEN = bind("sculk_stair_replaceable_worldgen");
	public static final TagKey<Block> SCULK_SLAB_REPLACEABLE_WORLDGEN = bind("sculk_slab_replaceable_worldgen");
	public static final TagKey<Block> SCULK_WALL_REPLACEABLE_WORLDGEN = bind("sculk_wall_replaceable_worldgen");
	public static final TagKey<Block> SCULK_SLAB_REPLACEABLE = bind("sculk_slab_replaceable");
	public static final TagKey<Block> SCULK_STAIR_REPLACEABLE = bind("sculk_stair_replaceable");
	public static final TagKey<Block> SCULK_WALL_REPLACEABLE = bind("sculk_wall_replaceable");

	public static final TagKey<Block> SPLITS_COCONUT = bind("splits_coconut");
	public static final TagKey<Block> STOPS_TUMBLEWEED = bind("stops_tumbleweed");

	public static final TagKey<Block> NO_LIGHTNING_BLOCK_PARTICLES = bind("no_lightning_block_particles");
	public static final TagKey<Block> NO_LIGHTNING_SMOKE_PARTICLES = bind("no_lightning_smoke_particles");
	public static final TagKey<Block> CRAB_HIDEABLE = bind("crab_hideable");
	public static final TagKey<Block> OSTRICH_BEAK_BURYABLE = bind("ostrich_beak_buryable");
	public static final TagKey<Block> FIREFLY_HIDEABLE_BLOCKS = bind("firefly_hideable_blocks");
	public static final TagKey<Block> PENGUIN_IGNORE_FRICTION = bind("penguin_ignore_friction");
	public static final TagKey<Block> PENGUIN_SPAWNABLE_ON = bind("penguin_spawnable_on");
	public static final TagKey<Block> PENGUIN_IMMUNE_TO = bind("penguins_immune_to");

	public static final TagKey<Block> MYCELIUM_GROWTH_REPLACEABLE = bind("mycelium_growth_replaceable");

	public static final TagKey<Block> SUPPORTS_ALGAE = bind("supports_algae");
	public static final TagKey<Block> SUPPORTS_PLANKTON = bind("supports_plankton");
	public static final TagKey<Block> SUPPORTS_HANGING_BAOBAB_NUT = bind("supports_hanging_baobab_nut");
	public static final TagKey<Block> SUPPORTS_CATTAIL = bind("supports_cattail");
	public static final TagKey<Block> SUPPORTS_COCONUT = bind("supports_coconut");
	public static final TagKey<Block> SUPPORTS_HANGING_COCONUT = bind("supports_hanging_coconut");

	public static final TagKey<Block> CANNOT_SUPPORT_SEA_ANEMONE = bind("cannot_support_sea_anemone");
	public static final TagKey<Block> CANNOT_SUPPORT_SEA_WHIP = bind("cannot_support_sea_whip");
	public static final TagKey<Block> CANNOT_SUPPORT_TUBE_WORMS = bind("cannot_support_tube_worms");

	public static final TagKey<Block> SUPPORTS_FROZEN_VEGETATION = bind("supports_frozen_vegetation");
	public static final TagKey<Block> SUPPORTS_WATERLOGGABLE_SAPLING = bind("supports_waterloggable_sapling");
	public static final TagKey<Block> CANNOT_REPLACE_BELOW_TREE_TRUNK_WATERLOGGABLE = bind("cannot_replace_below_tree_trunk_waterloggable");
	public static final TagKey<Block> CANNOT_REPLACE_BELOW_TREE_TRUNK_DESERT = bind("cannot_replace_below_tree_trunk_desert");
	public static final TagKey<Block> SUPPORTS_SHRUB = bind("supports_shrub");

	public static final TagKey<Block> CYPRESS_GROWS_AS_JUNIPER_ON = bind("cypress_grows_as_juniper_on");

	private static TagKey<Block> bind(String name) {
		return TagKey.create(Registries.BLOCK, WWConstants.id(name));
	}

	private WWBlockTags() {}
}
