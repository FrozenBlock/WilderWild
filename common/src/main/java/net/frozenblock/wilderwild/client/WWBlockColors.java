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

package net.frozenblock.wilderwild.client;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.block.client.api.BlockColorRegistry;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockTintSources;

@Environment(EnvType.CLIENT)
public final class WWBlockColors {

	public static void init() {
		BlockColorRegistry.registerTints(
			List.of(BlockTintSources.constant(BlockColors.LILY_PAD_DEFAULT, BlockColors.LILY_PAD_IN_WORLD)),
			WWBlocks.FLOWERING_LILY_PAD
		);

		BlockColorRegistry.registerTints(
			List.of(BlockColors.BLANK_LAYER, BlockTintSources.grass()),
			WWBlocks.POTTED_PINK_PETALS,
			WWBlocks.POTTED_WILDFLOWERS,
			WWBlocks.PHLOX,
			WWBlocks.POTTED_PHLOX,
			WWBlocks.LANTANAS,
			WWBlocks.POTTED_LANTANAS
		);

		BlockColorRegistry.registerTints(
			BlockTintSources.grass(),
			WWBlocks.POTTED_BUSH,
			WWBlocks.CLOVERS,
			WWBlocks.POTTED_CLOVERS
		);

		BlockColorRegistry.registerAverageFoliageTint(
			WWBlocks.BAOBAB_LEAVES,
			WWBlocks.WILLOW_LEAVES,
			WWBlocks.CYPRESS_LEAVES,
			WWBlocks.PALM_FRONDS,
			WWBlocks.POTTED_SHORT_GRASS,
			WWBlocks.SHRUB,
			WWBlocks.POTTED_SHRUB
		);

		BlockColorRegistry.registerTints(
			BlockTintSources.dryFoliage(),
			WWBlocks.ACACIA_LEAF_LITTER,
			WWBlocks.BAOBAB_LEAF_LITTER,
			WWBlocks.DARK_OAK_LEAF_LITTER,
			WWBlocks.JUNGLE_LEAF_LITTER,
			WWBlocks.MANGROVE_LEAF_LITTER,
			WWBlocks.PALM_FROND_LITTER,
			WWBlocks.WILLOW_LEAF_LITTER
		);
	}
}
