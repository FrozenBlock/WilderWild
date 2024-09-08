/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class WWCreativeInventorySorting {

	public static void init() {
		// BAOBAB (BUILDING BLOCKS)
		addAfterInBuildingBlocks(Items.MANGROVE_BUTTON, WWBlocks.BAOBAB_LOG);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.BAOBAB_WOOD);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.BAOBAB_WOOD);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_WOOD);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_BAOBAB_WOOD, WWBlocks.BAOBAB_PLANKS);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_PLANKS, WWBlocks.BAOBAB_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_STAIRS, WWBlocks.BAOBAB_SLAB);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_SLAB, WWBlocks.BAOBAB_FENCE);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_FENCE, WWBlocks.BAOBAB_FENCE_GATE);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_FENCE_GATE, WWBlocks.BAOBAB_DOOR);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_DOOR, WWBlocks.BAOBAB_TRAPDOOR);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_TRAPDOOR, WWBlocks.BAOBAB_PRESSURE_PLATE);
		// BAOBAB (NATURE)
		addAfterInNaturalBlocks(Items.MANGROVE_LOG, WWBlocks.BAOBAB_LOG);
		addAfterInNaturalBlocks(Items.MANGROVE_LEAVES, WWBlocks.BAOBAB_LEAVES);
		addAfterInNaturalBlocks(WWBlocks.BAOBAB_LEAVES, WWBlocks.BAOBAB_LEAF_LITTER);
	}

	private static void addAfterInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void addAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}
}
