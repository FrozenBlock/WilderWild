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

package net.frozenblock.wilderwild.datagen.recipe;

import net.frozenblock.lib.recipe.api.FrozenRecipeProvider;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public final class WWWoodRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput exporter) {
		provider.planksFromLogs(WWBlocks.BAOBAB_PLANKS, WWItemTags.BAOBAB_LOGS, 4);
		provider.woodFromLogs(WWBlocks.BAOBAB_WOOD, WWBlocks.BAOBAB_LOG);
		provider.woodFromLogs(WWBlocks.STRIPPED_BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_LOG);
		provider.woodenBoat(WWItems.BAOBAB_BOAT, WWBlocks.BAOBAB_PLANKS);
		provider.chestBoat(WWItems.BAOBAB_CHEST_BOAT, WWItems.BAOBAB_BOAT);
		FrozenRecipeProvider.woodenButton(provider, exporter, WWBlocks.BAOBAB_BUTTON, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, exporter, WWBlocks.BAOBAB_DOOR, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, exporter, WWBlocks.BAOBAB_FENCE, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, exporter, WWBlocks.BAOBAB_FENCE_GATE, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, exporter, WWBlocks.BAOBAB_PRESSURE_PLATE, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, exporter, WWBlocks.BAOBAB_SLAB, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, exporter, WWBlocks.BAOBAB_STAIRS, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, exporter, WWBlocks.BAOBAB_TRAPDOOR, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, exporter, WWItems.BAOBAB_SIGN, WWBlocks.BAOBAB_PLANKS);
		provider.hangingSign(WWItems.BAOBAB_HANGING_SIGN, WWBlocks.STRIPPED_BAOBAB_LOG);

		provider.planksFromLogs(WWBlocks.CYPRESS_PLANKS, WWItemTags.CYPRESS_LOGS, 4);
		provider.woodFromLogs(WWBlocks.CYPRESS_WOOD, WWBlocks.CYPRESS_LOG);
		provider.woodFromLogs(WWBlocks.STRIPPED_CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_LOG);
		provider.woodenBoat(WWItems.CYPRESS_BOAT, WWBlocks.CYPRESS_PLANKS);
		provider.chestBoat(WWItems.CYPRESS_CHEST_BOAT, WWItems.CYPRESS_BOAT);
		FrozenRecipeProvider.woodenButton(provider, exporter, WWBlocks.CYPRESS_BUTTON, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, exporter, WWBlocks.CYPRESS_DOOR, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, exporter, WWBlocks.CYPRESS_FENCE, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, exporter, WWBlocks.CYPRESS_FENCE_GATE, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, exporter, WWBlocks.CYPRESS_PRESSURE_PLATE, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, exporter, WWBlocks.CYPRESS_SLAB, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, exporter, WWBlocks.CYPRESS_STAIRS, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, exporter, WWBlocks.CYPRESS_TRAPDOOR, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, exporter, WWItems.CYPRESS_SIGN, WWBlocks.CYPRESS_PLANKS);
		provider.hangingSign(WWItems.CYPRESS_HANGING_SIGN, WWBlocks.STRIPPED_CYPRESS_LOG);

		provider.planksFromLogs(WWBlocks.PALM_PLANKS, WWItemTags.PALM_LOGS, 4);
		provider.woodFromLogs(WWBlocks.PALM_WOOD, WWBlocks.PALM_LOG);
		provider.woodFromLogs(WWBlocks.STRIPPED_PALM_WOOD, WWBlocks.STRIPPED_PALM_LOG);
		provider.woodenBoat(WWItems.PALM_BOAT, WWBlocks.PALM_PLANKS);
		provider.chestBoat(WWItems.PALM_CHEST_BOAT, WWItems.PALM_BOAT);
		FrozenRecipeProvider.woodenButton(provider, exporter, WWBlocks.PALM_BUTTON, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, exporter, WWBlocks.PALM_DOOR, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, exporter, WWBlocks.PALM_FENCE, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, exporter, WWBlocks.PALM_FENCE_GATE, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, exporter, WWBlocks.PALM_PRESSURE_PLATE, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, exporter, WWBlocks.PALM_SLAB, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, exporter, WWBlocks.PALM_STAIRS, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, exporter, WWBlocks.PALM_TRAPDOOR, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, exporter, WWItems.PALM_SIGN, WWBlocks.PALM_PLANKS);
		provider.hangingSign(WWItems.PALM_HANGING_SIGN, WWBlocks.STRIPPED_PALM_LOG);

		provider.planksFromLogs(WWBlocks.MAPLE_PLANKS, WWItemTags.MAPLE_LOGS, 4);
		provider.woodFromLogs(WWBlocks.MAPLE_WOOD, WWBlocks.MAPLE_LOG);
		provider.woodFromLogs(WWBlocks.STRIPPED_MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_LOG);
		provider.woodenBoat(WWItems.MAPLE_BOAT, WWBlocks.MAPLE_PLANKS);
		provider.chestBoat(WWItems.MAPLE_CHEST_BOAT, WWItems.MAPLE_BOAT);
		FrozenRecipeProvider.woodenButton(provider, exporter, WWBlocks.MAPLE_BUTTON, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, exporter, WWBlocks.MAPLE_DOOR, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, exporter, WWBlocks.MAPLE_FENCE, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, exporter, WWBlocks.MAPLE_FENCE_GATE, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, exporter, WWBlocks.MAPLE_PRESSURE_PLATE, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, exporter, WWBlocks.MAPLE_SLAB, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, exporter, WWBlocks.MAPLE_STAIRS, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, exporter, WWBlocks.MAPLE_TRAPDOOR, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, exporter, WWItems.MAPLE_SIGN, WWBlocks.MAPLE_PLANKS);
		provider.hangingSign(WWItems.MAPLE_HANGING_SIGN, WWBlocks.STRIPPED_MAPLE_LOG);
	}

}
