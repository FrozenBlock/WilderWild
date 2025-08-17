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
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public final class WWBlockRenderLayers {

	public static void init() {
		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;

		renderLayerRegistry.putBlock(WWBlocks.CARNATION, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.SEEDING_DANDELION, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CARNATION, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SEEDING_DANDELION, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BAOBAB_NUT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_WILLOW_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CYPRESS_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_COCONUT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_MAPLE_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BIG_DRIPLEAF, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SMALL_DRIPLEAF, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SHORT_GRASS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_TUMBLEWEED_PLANT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_TUMBLEWEED, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SHRUB, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PRICKLY_PEAR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PINK_PETALS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BUSH, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_FIREFLY_BUSH, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SHORT_DRY_GRASS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_TALL_DRY_GRASS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CACTUS_FLOWER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_WHITE_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_RED_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_YELLOW_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PINK_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PURPLE_HIBISCUS, RenderType.cutout());

		renderLayerRegistry.putBlock(WWBlocks.FROZEN_SHORT_GRASS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_FROZEN_SHORT_GRASS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.FROZEN_TALL_GRASS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.FROZEN_FERN, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_FROZEN_FERN, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.FROZEN_LARGE_FERN, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.FROZEN_BUSH, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_FROZEN_BUSH, RenderType.cutout());

		renderLayerRegistry.putBlock(WWBlocks.AUBURN_CREEPING_MOSS, RenderType.cutout());

		renderLayerRegistry.putBlock(WWBlocks.DATURA, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CATTAIL, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.ALGAE, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PLANKTON, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BARNACLES, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.SEA_ANEMONE, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.SEA_WHIP, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.TUBE_WORMS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MILKWEED, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MARIGOLD, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_MARIGOLD, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_WILDFLOWERS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PASQUEFLOWER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PASQUEFLOWER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PHLOX, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PHLOX, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.LANTANAS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_LANTANAS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POLLEN, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PALE_MUSHROOM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PALE_MUSHROOM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CLOVERS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CLOVERS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MYCELIUM_GROWTH, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.ECHO_GLASS, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.HANGING_TENDRIL, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.FLOWERING_LILY_PAD, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BROWN_SHELF_FUNGI, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.RED_SHELF_FUNGI, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PALE_SHELF_FUNGI, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CRIMSON_SHELF_FUNGI, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.WARPED_SHELF_FUNGI, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PALM_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PALM_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_NUT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.COCONUT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.RED_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.WHITE_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PINK_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PURPLE_HIBISCUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.SHRUB, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PRICKLY_PEAR_CACTUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.TERMITE_MOUND, RenderType.solid());
		renderLayerRegistry.putBlock(WWBlocks.DISPLAY_LANTERN, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_ACACIA_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_BAOBAB_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_BIRCH_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CHERRY_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_WILLOW_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CYPRESS_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_MAPLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_DARK_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_JUNGLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_MANGROVE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_SPRUCE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_PALM_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CRIMSON_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_WARPED_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_PEARLESCENT_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.LIME_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.PINK_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.RED_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.LIME_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.PINK_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.RED_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.TUMBLEWEED_PLANT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.TUMBLEWEED, RenderType.cutout());

		renderLayerRegistry.putBlock(WWBlocks.GEYSER, RenderType.translucent());

		renderLayerRegistry.putBlock(WWBlocks.FRAGILE_ICE, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.ICICLE, RenderType.cutout());

		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_LEAVES, RenderType.cutoutMipped());
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_LEAVES, RenderType.cutoutMipped());
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_LEAVES, RenderType.cutoutMipped());
		renderLayerRegistry.putBlock(WWBlocks.PALM_FRONDS, RenderType.cutoutMipped());
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_MAPLE_LEAVES, RenderType.cutoutMipped());
		renderLayerRegistry.putBlock(WWBlocks.ORANGE_MAPLE_LEAVES, RenderType.cutoutMipped());
		renderLayerRegistry.putBlock(WWBlocks.RED_MAPLE_LEAVES, RenderType.cutoutMipped());

		renderLayerRegistry.putBlock(WWBlocks.ACACIA_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.AZALEA_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BIRCH_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CHERRY_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.DARK_OAK_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.JUNGLE_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MANGROVE_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PALE_OAK_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PALM_FROND_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.SPRUCE_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.WILLOW_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_MAPLE_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.ORANGE_MAPLE_LEAF_LITTER, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.RED_MAPLE_LEAF_LITTER, RenderType.cutout());
	}
}
