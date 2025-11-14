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
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.frozenblock.lib.block.client.api.TintRegistryHelper;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.DryFoliageColor;
import net.minecraft.world.level.GrassColor;

@Environment(EnvType.CLIENT)
public final class WWTints {

	public static void applyTints() {
		ColorProviderRegistry.BLOCK.register(
			((state, level, pos, tintIndex) -> level == null || pos == null ? 7455580 : 2129968),
			WWBlocks.FLOWERING_LILY_PAD
		);

		final BlockColor flowerBedProvider = (blockState, blockAndTintGetter, blockPos, i) -> {
			if (i != 0) return blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos) : GrassColor.getDefaultColor();
			return -1;
		};
		final BlockColor grassTintProvider = (blockState, blockAndTintGetter, blockPos, i) ->
			blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos) : GrassColor.getDefaultColor();

		final BlockColor dryFoliageProvider = (blockState, blockAndTintGetter, blockPos, i) ->
			blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageDryFoliageColor(blockAndTintGetter, blockPos) : DryFoliageColor.FOLIAGE_DRY_DEFAULT;

		ColorProviderRegistry.BLOCK.register(flowerBedProvider, WWBlocks.POTTED_PINK_PETALS);
		ColorProviderRegistry.BLOCK.register(flowerBedProvider, WWBlocks.POTTED_WILDFLOWERS);
		ColorProviderRegistry.BLOCK.register(grassTintProvider, WWBlocks.POTTED_BUSH);
		ColorProviderRegistry.BLOCK.register(flowerBedProvider, WWBlocks.PHLOX);
		ColorProviderRegistry.BLOCK.register(flowerBedProvider, WWBlocks.POTTED_PHLOX);
		ColorProviderRegistry.BLOCK.register(flowerBedProvider, WWBlocks.LANTANAS);
		ColorProviderRegistry.BLOCK.register(flowerBedProvider, WWBlocks.POTTED_LANTANAS);
		ColorProviderRegistry.BLOCK.register(grassTintProvider, WWBlocks.CLOVERS);
		ColorProviderRegistry.BLOCK.register(grassTintProvider, WWBlocks.POTTED_CLOVERS);

		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.BAOBAB_LEAVES);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.WILLOW_LEAVES);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.CYPRESS_LEAVES);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.PALM_FRONDS);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.POTTED_SHORT_GRASS);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.SHRUB);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.POTTED_SHRUB);

		ColorProviderRegistry.BLOCK.register(dryFoliageProvider, WWBlocks.ACACIA_LEAF_LITTER);
		ColorProviderRegistry.BLOCK.register(dryFoliageProvider, WWBlocks.BAOBAB_LEAF_LITTER);
		ColorProviderRegistry.BLOCK.register(dryFoliageProvider, WWBlocks.CYPRESS_LEAF_LITTER);
		ColorProviderRegistry.BLOCK.register(dryFoliageProvider, WWBlocks.DARK_OAK_LEAF_LITTER);
		ColorProviderRegistry.BLOCK.register(dryFoliageProvider, WWBlocks.JUNGLE_LEAF_LITTER);
		ColorProviderRegistry.BLOCK.register(dryFoliageProvider, WWBlocks.MANGROVE_LEAF_LITTER);
		ColorProviderRegistry.BLOCK.register(dryFoliageProvider, WWBlocks.PALM_FROND_LITTER);
		ColorProviderRegistry.BLOCK.register(dryFoliageProvider, WWBlocks.WILLOW_LEAF_LITTER);
	}
}
