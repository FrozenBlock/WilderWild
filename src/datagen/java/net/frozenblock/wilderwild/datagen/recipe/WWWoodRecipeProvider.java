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
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import java.util.function.Consumer;

public class WWWoodRecipeProvider {

	static void buildRecipes(Consumer<FinishedRecipe> exporter) {
		RecipeProvider.planksFromLogs(exporter, RegisterBlocks.BAOBAB_PLANKS, WilderItemTags.BAOBAB_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.BAOBAB_WOOD, RegisterBlocks.BAOBAB_LOG);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.STRIPPED_BAOBAB_WOOD, RegisterBlocks.STRIPPED_BAOBAB_LOG);
		RecipeProvider.woodenBoat(exporter, RegisterItems.BAOBAB_BOAT, RegisterBlocks.BAOBAB_PLANKS);
		RecipeProvider.chestBoat(exporter, RegisterItems.BAOBAB_CHEST_BOAT, RegisterItems.BAOBAB_BOAT);
		FrozenRecipeProvider.woodenButton(exporter, RegisterBlocks.BAOBAB_BUTTON, RegisterBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenDoor(exporter, RegisterBlocks.BAOBAB_DOOR, RegisterBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenFence(exporter, RegisterBlocks.BAOBAB_FENCE, RegisterBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(exporter, RegisterBlocks.BAOBAB_FENCE_GATE, RegisterBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(exporter, RegisterBlocks.BAOBAB_PRESSURE_PLATE, RegisterBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenSlab(exporter, RegisterBlocks.BAOBAB_SLAB, RegisterBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenStairs(exporter, RegisterBlocks.BAOBAB_STAIRS, RegisterBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(exporter, RegisterBlocks.BAOBAB_TRAPDOOR, RegisterBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenSign(exporter, RegisterItems.BAOBAB_SIGN, RegisterBlocks.BAOBAB_PLANKS);
		RecipeProvider.hangingSign(exporter, RegisterItems.BAOBAB_HANGING_SIGN, RegisterBlocks.STRIPPED_BAOBAB_LOG);

		RecipeProvider.planksFromLogs(exporter, RegisterBlocks.CYPRESS_PLANKS, WilderItemTags.CYPRESS_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.CYPRESS_WOOD, RegisterBlocks.CYPRESS_LOG);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.STRIPPED_CYPRESS_WOOD, RegisterBlocks.STRIPPED_CYPRESS_LOG);
		RecipeProvider.woodenBoat(exporter, RegisterItems.CYPRESS_BOAT, RegisterBlocks.CYPRESS_PLANKS);
		RecipeProvider.chestBoat(exporter, RegisterItems.CYPRESS_CHEST_BOAT, RegisterItems.CYPRESS_BOAT);
		FrozenRecipeProvider.woodenButton(exporter, RegisterBlocks.CYPRESS_BUTTON, RegisterBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenDoor(exporter, RegisterBlocks.CYPRESS_DOOR, RegisterBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenFence(exporter, RegisterBlocks.CYPRESS_FENCE, RegisterBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(exporter, RegisterBlocks.CYPRESS_FENCE_GATE, RegisterBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(exporter, RegisterBlocks.CYPRESS_PRESSURE_PLATE, RegisterBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenSlab(exporter, RegisterBlocks.CYPRESS_SLAB, RegisterBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenStairs(exporter, RegisterBlocks.CYPRESS_STAIRS, RegisterBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(exporter, RegisterBlocks.CYPRESS_TRAPDOOR, RegisterBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenSign(exporter, RegisterItems.CYPRESS_SIGN, RegisterBlocks.CYPRESS_PLANKS);
		RecipeProvider.hangingSign(exporter, RegisterItems.CYPRESS_HANGING_SIGN, RegisterBlocks.STRIPPED_CYPRESS_LOG);

		RecipeProvider.planksFromLogs(exporter, RegisterBlocks.PALM_PLANKS, WilderItemTags.PALM_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.PALM_WOOD, RegisterBlocks.PALM_LOG);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.STRIPPED_PALM_WOOD, RegisterBlocks.STRIPPED_PALM_LOG);
		RecipeProvider.woodenBoat(exporter, RegisterItems.PALM_BOAT, RegisterBlocks.PALM_PLANKS);
		RecipeProvider.chestBoat(exporter, RegisterItems.PALM_CHEST_BOAT, RegisterItems.PALM_BOAT);
		FrozenRecipeProvider.woodenButton(exporter, RegisterBlocks.PALM_BUTTON, RegisterBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenDoor(exporter, RegisterBlocks.PALM_DOOR, RegisterBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenFence(exporter, RegisterBlocks.PALM_FENCE, RegisterBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(exporter, RegisterBlocks.PALM_FENCE_GATE, RegisterBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(exporter, RegisterBlocks.PALM_PRESSURE_PLATE, RegisterBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenSlab(exporter, RegisterBlocks.PALM_SLAB, RegisterBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenStairs(exporter, RegisterBlocks.PALM_STAIRS, RegisterBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(exporter, RegisterBlocks.PALM_TRAPDOOR, RegisterBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenSign(exporter, RegisterItems.PALM_SIGN, RegisterBlocks.PALM_PLANKS);
		RecipeProvider.hangingSign(exporter, RegisterItems.PALM_HANGING_SIGN, RegisterBlocks.STRIPPED_PALM_LOG);
	}

}
