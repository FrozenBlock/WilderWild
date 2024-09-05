/*
 * Copyright 2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client.rendering;

import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

@Environment(EnvType.CLIENT)
public class WWTints {

	public static void initItems() {
		registerDefaultFoliageColorForItem(WWBlocks.BAOBAB_LEAVES);
		registerDefaultFoliageColorForItem(WWBlocks.CYPRESS_LEAVES);
		registerDefaultFoliageColorForItem(WWBlocks.PALM_FRONDS);

		registerDefaultFoliageColorForItem(WWBlocks.OAK_LEAF_LITTER);
	}

	public static void initBlocks() {
		ColorProviderRegistry.BLOCK.register(
			((state, level, pos, tintIndex) -> level == null || pos == null ? 7455580 : 2129968),
			WWBlocks.FLOWERING_LILY_PAD
		);

		registerAverageFoliageColorForBlock(WWBlocks.BAOBAB_LEAVES);
		registerAverageFoliageColorForBlock(WWBlocks.CYPRESS_LEAVES);
		registerAverageFoliageColorForBlock(WWBlocks.PALM_FRONDS);
		registerAverageFoliageColorForBlock(WWBlocks.POTTED_SHORT_GRASS);
		registerAverageFoliageColorForBlock(WWBlocks.BUSH);
		registerAverageFoliageColorForBlock(WWBlocks.POTTED_BUSH);

		registerAverageFoliageColorForBlock(WWBlocks.OAK_LEAF_LITTER);
	}

	public static void registerDefaultFoliageColorForItem(ItemLike itemLike) {
		ColorProviderRegistry.ITEM.register(
			((stack, tintIndex) -> FoliageColor.getDefaultColor()),
			itemLike
		);
	}

	public static void registerAverageFoliageColorForBlock(Block block) {
		ColorProviderRegistry.BLOCK.register(
			(state, level, pos, tintIndex) -> BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos)),
			block
		);
	}
}
