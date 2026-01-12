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
import net.fabricmc.fabric.api.client.rendering.v1.ChunkSectionLayerMap;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

@Environment(EnvType.CLIENT)
public final class WWBlockRenderLayers {

	public static void init() {
		ChunkSectionLayerMap.putBlock(WWBlocks.CARNATION, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.SEEDING_DANDELION, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_CARNATION, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_SEEDING_DANDELION, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_BAOBAB_NUT, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_WILLOW_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_CYPRESS_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_COCONUT, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_YELLOW_MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_ORANGE_MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_RED_MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_BIG_DRIPLEAF, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_SMALL_DRIPLEAF, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_TUMBLEWEED_PLANT, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_TUMBLEWEED, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_SHRUB, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_PRICKLY_PEAR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_PINK_PETALS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_BUSH, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_FIREFLY_BUSH, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_SHORT_DRY_GRASS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_TALL_DRY_GRASS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_CACTUS_FLOWER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_WHITE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_RED_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_YELLOW_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_PINK_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_PURPLE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.FROZEN_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_FROZEN_SHORT_GRASS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.FROZEN_TALL_GRASS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.FROZEN_FERN, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_FROZEN_FERN, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.FROZEN_LARGE_FERN, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.FROZEN_BUSH, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_FROZEN_BUSH, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(WWBlocks.AUBURN_CREEPING_MOSS, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(WWBlocks.DATURA, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CATTAIL, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.ALGAE, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PLANKTON, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BARNACLES, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.SEA_ANEMONE, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.SEA_WHIP, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.TUBE_WORMS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.MILKWEED, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.MARIGOLD, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_MARIGOLD, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_WILDFLOWERS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PASQUEFLOWER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_PASQUEFLOWER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PHLOX, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_PHLOX, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.LANTANAS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_LANTANAS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POLLEN, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PALE_MUSHROOM, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_PALE_MUSHROOM, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CLOVERS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.POTTED_CLOVERS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.MYCELIUM_GROWTH, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.ECHO_GLASS, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.HANGING_TENDRIL, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.FLOWERING_LILY_PAD, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BROWN_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.RED_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PALE_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CRIMSON_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.WARPED_SHELF_FUNGI, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BAOBAB_DOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.WILLOW_DOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CYPRESS_DOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PALM_DOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.MAPLE_DOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BAOBAB_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.WILLOW_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CYPRESS_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PALM_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.MAPLE_TRAPDOOR, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BAOBAB_NUT, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.WILLOW_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CYPRESS_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.COCONUT, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.YELLOW_MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.ORANGE_MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.RED_MAPLE_SAPLING, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.RED_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.WHITE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.YELLOW_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PINK_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PURPLE_HIBISCUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.SHRUB, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PRICKLY_PEAR_CACTUS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.TERMITE_MOUND, ChunkSectionLayer.SOLID);
		ChunkSectionLayerMap.putBlock(WWBlocks.DISPLAY_LANTERN, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PEARLESCENT_BLUE_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BLUE_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.LIME_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PINK_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.RED_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.YELLOW_MESOGLEA, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PEARLESCENT_BLUE_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BLUE_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.LIME_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PINK_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.RED_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.YELLOW_NEMATOCYST, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.TUMBLEWEED_PLANT, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.TUMBLEWEED, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(WWBlocks.GEYSER, ChunkSectionLayer.TRANSLUCENT);

		ChunkSectionLayerMap.putBlock(WWBlocks.FRAGILE_ICE, ChunkSectionLayer.TRANSLUCENT);
		ChunkSectionLayerMap.putBlock(WWBlocks.ICICLE, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(WWBlocks.BAOBAB_LEAVES, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.WILLOW_LEAVES, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CYPRESS_LEAVES, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PALM_FRONDS, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.YELLOW_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.ORANGE_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.RED_MAPLE_LEAVES, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(WWBlocks.ACACIA_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.AZALEA_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BAOBAB_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.BIRCH_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CHERRY_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.CYPRESS_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.DARK_OAK_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.JUNGLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.MANGROVE_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PALE_OAK_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PALM_FROND_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.SPRUCE_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.WILLOW_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.YELLOW_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.ORANGE_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.RED_MAPLE_LEAF_LITTER, ChunkSectionLayer.CUTOUT);

		ChunkSectionLayerMap.putBlock(WWBlocks.PEARLESCENT_FROGLIGHT_GOOP_BODY, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.PEARLESCENT_FROGLIGHT_GOOP, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.VERDANT_FROGLIGHT_GOOP_BODY, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.VERDANT_FROGLIGHT_GOOP, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.OCHRE_FROGLIGHT_GOOP, ChunkSectionLayer.CUTOUT);
		ChunkSectionLayerMap.putBlock(WWBlocks.OCHRE_FROGLIGHT_GOOP_BODY, ChunkSectionLayer.CUTOUT);
	}
}
