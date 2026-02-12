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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.frozenblock.lib.block.client.api.BlockColorHelper;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.DryFoliageColor;
import net.minecraft.world.level.GrassColor;

@Environment(EnvType.CLIENT)
public final class WWTints {

	public static void applyTints() {
		BlockColorRegistry.register(
			(state, level, pos, index) -> level == null || pos == null ? 7455580 : 2129968,
			WWBlocks.FLOWERING_LILY_PAD
		);

		final BlockColor flowerBedProvider = (state, level, pos, index) -> {
			if (index != 0) return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor();
			return -1;
		};
		final BlockColor grassTintProvider = (state, level, pos, index) ->
			level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor();

		final BlockColor dryFoliageProvider = (state, level, pos, index) ->
			level != null && pos != null ? BiomeColors.getAverageDryFoliageColor(level, pos) : DryFoliageColor.FOLIAGE_DRY_DEFAULT;

		BlockColorRegistry.register(
			flowerBedProvider,
			WWBlocks.POTTED_PINK_PETALS,
			WWBlocks.POTTED_WILDFLOWERS,
			WWBlocks.PHLOX,
			WWBlocks.POTTED_PHLOX,
			WWBlocks.LANTANAS,
			WWBlocks.POTTED_LANTANAS
		);

		BlockColorRegistry.register(
			grassTintProvider,
			WWBlocks.POTTED_BUSH,
			WWBlocks.CLOVERS,
			WWBlocks.POTTED_CLOVERS
		);

		BlockColorHelper.registerAverageFoliageColor(WWBlocks.BAOBAB_LEAVES);
		BlockColorHelper.registerAverageFoliageColor(WWBlocks.WILLOW_LEAVES);
		BlockColorHelper.registerAverageFoliageColor(WWBlocks.CYPRESS_LEAVES);
		BlockColorHelper.registerAverageFoliageColor(WWBlocks.PALM_FRONDS);
		BlockColorHelper.registerAverageFoliageColor(WWBlocks.POTTED_SHORT_GRASS);
		BlockColorHelper.registerAverageFoliageColor(WWBlocks.SHRUB);
		BlockColorHelper.registerAverageFoliageColor(WWBlocks.POTTED_SHRUB);

		BlockColorRegistry.register(
			dryFoliageProvider,
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
