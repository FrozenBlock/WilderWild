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

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

@Environment(EnvType.CLIENT)
public final class WWBlockRenderLayers {

	public static void init() {
		BlockRenderLayerMap.putBlock(WWBlocks.CARNATION, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.SEEDING_DANDELION, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_CARNATION, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_SEEDING_DANDELION, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_BAOBAB_NUT, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_WILLOW_SAPLING, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_CYPRESS_SAPLING, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_COCONUT, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_BIG_DRIPLEAF, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_SMALL_DRIPLEAF, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_TUMBLEWEED_PLANT, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_TUMBLEWEED, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_SHRUB, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_PRICKLY_PEAR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_PINK_PETALS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_BUSH, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_FIREFLY_BUSH, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_SHORT_DRY_GRASS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_TALL_DRY_GRASS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_CACTUS_FLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_WHITE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_RED_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_YELLOW_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_PINK_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_PURPLE_HIBISCUS, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(WWBlocks.FROZEN_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_FROZEN_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.FROZEN_TALL_GRASS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.FROZEN_FERN, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_FROZEN_FERN, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.FROZEN_LARGE_FERN, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.FROZEN_BUSH, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_FROZEN_BUSH, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(WWBlocks.AUBURN_CREEPING_MOSS, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(WWBlocks.DATURA, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.CATTAIL, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.ALGAE, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PLANKTON, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.BARNACLES, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.SEA_ANEMONE, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.SEA_WHIP, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.TUBE_WORMS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.MILKWEED, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.MARIGOLD, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_MARIGOLD, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_WILDFLOWERS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PASQUEFLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_PASQUEFLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PHLOX, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_PHLOX, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.LANTANAS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_LANTANAS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POLLEN, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PALE_MUSHROOM, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_PALE_MUSHROOM, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.CLOVERS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.POTTED_CLOVERS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.MYCELIUM_GROWTH, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.ECHO_GLASS, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.HANGING_TENDRIL, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.FLOWERING_LILY_PAD, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.BROWN_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.RED_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PALE_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.CRIMSON_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.WARPED_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.BAOBAB_DOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.WILLOW_DOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.CYPRESS_DOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PALM_DOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.MAPLE_DOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.BAOBAB_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.WILLOW_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.CYPRESS_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PALM_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.MAPLE_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.BAOBAB_NUT, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.WILLOW_SAPLING, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.CYPRESS_SAPLING, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.COCONUT, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.RED_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.WHITE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.YELLOW_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PINK_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PURPLE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.SHRUB, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.PRICKLY_PEAR_CACTUS, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.TERMITE_MOUND, ChunkSectionLayer.SOLID);
		BlockRenderLayerMap.putBlock(WWBlocks.DISPLAY_LANTERN, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_ACACIA_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_BAOBAB_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_BIRCH_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_CHERRY_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_WILLOW_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_CYPRESS_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_MAPLE_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_DARK_OAK_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_JUNGLE_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_MANGROVE_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_OAK_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_SPRUCE_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_PALM_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_CRIMSON_STEM, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.HOLLOWED_WARPED_STEM, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.BLUE_PEARLESCENT_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.BLUE_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.LIME_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.PINK_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.RED_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.YELLOW_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.BLUE_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.LIME_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.PINK_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.RED_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.YELLOW_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.TUMBLEWEED_PLANT, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(WWBlocks.TUMBLEWEED, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(WWBlocks.FRAGILE_ICE, ChunkSectionLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(WWBlocks.ICICLE, ChunkSectionLayer.CUTOUT);

		BlockRenderLayerMap.putBlock(WWBlocks.BAOBAB_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.WILLOW_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.CYPRESS_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.PALM_FRONDS, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.YELLOW_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.ORANGE_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.RED_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.YELLOW_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.ORANGE_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT_MIPPED);
		BlockRenderLayerMap.putBlock(WWBlocks.RED_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT_MIPPED);
	}
}
