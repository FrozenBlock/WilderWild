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
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

@Environment(EnvType.CLIENT)
public final class WWBlockRenderLayers {

	public static void init() {
		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;

		renderLayerRegistry.putBlock(WWBlocks.CARNATION, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.SEEDING_DANDELION, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CARNATION, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SEEDING_DANDELION, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BAOBAB_NUT, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_WILLOW_SAPLING, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CYPRESS_SAPLING, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_COCONUT, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BIG_DRIPLEAF, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SMALL_DRIPLEAF, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_TUMBLEWEED_PLANT, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_TUMBLEWEED, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SHRUB, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PRICKLY_PEAR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PINK_PETALS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BUSH, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_FIREFLY_BUSH, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SHORT_DRY_GRASS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_TALL_DRY_GRASS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CACTUS_FLOWER, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_WHITE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_RED_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_YELLOW_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PINK_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PURPLE_HIBISCUS, ChunkSectionLayer.CUTOUT);

		renderLayerRegistry.putBlock(WWBlocks.FROZEN_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_FROZEN_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.FROZEN_TALL_GRASS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.FROZEN_FERN, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_FROZEN_FERN, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.FROZEN_LARGE_FERN, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.FROZEN_BUSH, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_FROZEN_BUSH, ChunkSectionLayer.CUTOUT);

		renderLayerRegistry.putBlock(WWBlocks.AUBURN_CREEPING_MOSS, ChunkSectionLayer.CUTOUT);

		renderLayerRegistry.putBlock(WWBlocks.DATURA, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.CATTAIL, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.ALGAE, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PLANKTON, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.BARNACLES, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.SEA_ANEMONE, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.SEA_WHIP, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.TUBE_WORMS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.MILKWEED, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.MARIGOLD, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_MARIGOLD, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_WILDFLOWERS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PASQUEFLOWER, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PASQUEFLOWER, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PHLOX, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PHLOX, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.LANTANAS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_LANTANAS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POLLEN, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PALE_MUSHROOM, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PALE_MUSHROOM, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.CLOVERS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CLOVERS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.MYCELIUM_GROWTH, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.ECHO_GLASS, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.HANGING_TENDRIL, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.FLOWERING_LILY_PAD, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.BROWN_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.RED_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PALE_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.CRIMSON_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.WARPED_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_DOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_DOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_DOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PALM_DOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_DOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PALM_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_NUT, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_SAPLING, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_SAPLING, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.COCONUT, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.RED_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.WHITE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PINK_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PURPLE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.SHRUB, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.PRICKLY_PEAR_CACTUS, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.TERMITE_MOUND, ChunkSectionLayer.SOLID);
		renderLayerRegistry.putBlock(WWBlocks.DISPLAY_LANTERN, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_ACACIA_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_BAOBAB_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_BIRCH_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CHERRY_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_WILLOW_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CYPRESS_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_MAPLE_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_DARK_OAK_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_JUNGLE_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_MANGROVE_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_OAK_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_SPRUCE_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_PALM_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CRIMSON_STEM, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_WARPED_STEM, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.BLUE_PEARLESCENT_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.BLUE_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.LIME_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.PINK_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.RED_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.BLUE_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.LIME_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.PINK_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.RED_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.TUMBLEWEED_PLANT, ChunkSectionLayer.CUTOUT);
		renderLayerRegistry.putBlock(WWBlocks.TUMBLEWEED, ChunkSectionLayer.CUTOUT);

		renderLayerRegistry.putBlock(WWBlocks.FRAGILE_ICE, ChunkSectionLayer.TRANSLUCENT);
		renderLayerRegistry.putBlock(WWBlocks.ICICLE, ChunkSectionLayer.CUTOUT);

		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.PALM_FRONDS, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.ORANGE_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.RED_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.ORANGE_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT_MIPPED);
		renderLayerRegistry.putBlock(WWBlocks.RED_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT_MIPPED);
	}
}
