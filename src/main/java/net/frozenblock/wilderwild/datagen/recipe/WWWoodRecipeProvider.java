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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.datagen.recipe;

import net.frozenblock.lib.recipe.api.FrozenRecipeProvider;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public final class WWWoodRecipeProvider {

	static void buildRecipes(RecipeOutput exporter) {
		RecipeProvider.planksFromLogs(exporter, WWBlocks.BAOBAB_PLANKS, WWItemTags.BAOBAB_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.BAOBAB_WOOD, WWBlocks.BAOBAB_LOG);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.STRIPPED_BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_LOG);
		RecipeProvider.woodenBoat(exporter, WWItems.BAOBAB_BOAT, WWBlocks.BAOBAB_PLANKS);
		RecipeProvider.chestBoat(exporter, WWItems.BAOBAB_CHEST_BOAT, WWItems.BAOBAB_BOAT);
		FrozenRecipeProvider.woodenButton(exporter, WWBlocks.BAOBAB_BUTTON, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenDoor(exporter, WWBlocks.BAOBAB_DOOR, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenFence(exporter, WWBlocks.BAOBAB_FENCE, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(exporter, WWBlocks.BAOBAB_FENCE_GATE, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(exporter, WWBlocks.BAOBAB_PRESSURE_PLATE, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenSlab(exporter, WWBlocks.BAOBAB_SLAB, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenStairs(exporter, WWBlocks.BAOBAB_STAIRS, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(exporter, WWBlocks.BAOBAB_TRAPDOOR, WWBlocks.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenSign(exporter, WWItems.BAOBAB_SIGN, WWBlocks.BAOBAB_PLANKS);
		RecipeProvider.hangingSign(exporter, WWItems.BAOBAB_HANGING_SIGN, WWBlocks.STRIPPED_BAOBAB_LOG);

		RecipeProvider.planksFromLogs(exporter, WWBlocks.WILLOW_PLANKS, WWItemTags.WILLOW_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.WILLOW_WOOD, WWBlocks.WILLOW_LOG);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.STRIPPED_WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_LOG);
		RecipeProvider.woodenBoat(exporter, WWItems.WILLOW_BOAT, WWBlocks.WILLOW_PLANKS);
		RecipeProvider.chestBoat(exporter, WWItems.WILLOW_CHEST_BOAT, WWItems.WILLOW_BOAT);
		FrozenRecipeProvider.woodenButton(exporter, WWBlocks.WILLOW_BUTTON, WWBlocks.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenDoor(exporter, WWBlocks.WILLOW_DOOR, WWBlocks.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenFence(exporter, WWBlocks.WILLOW_FENCE, WWBlocks.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(exporter, WWBlocks.WILLOW_FENCE_GATE, WWBlocks.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(exporter, WWBlocks.WILLOW_PRESSURE_PLATE, WWBlocks.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenSlab(exporter, WWBlocks.WILLOW_SLAB, WWBlocks.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenStairs(exporter, WWBlocks.WILLOW_STAIRS, WWBlocks.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(exporter, WWBlocks.WILLOW_TRAPDOOR, WWBlocks.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenSign(exporter, WWItems.WILLOW_SIGN, WWBlocks.WILLOW_PLANKS);
		RecipeProvider.hangingSign(exporter, WWItems.WILLOW_HANGING_SIGN, WWBlocks.STRIPPED_WILLOW_LOG);

		RecipeProvider.planksFromLogs(exporter, WWBlocks.CYPRESS_PLANKS, WWItemTags.CYPRESS_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.CYPRESS_WOOD, WWBlocks.CYPRESS_LOG);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.STRIPPED_CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_LOG);
		RecipeProvider.woodenBoat(exporter, WWItems.CYPRESS_BOAT, WWBlocks.CYPRESS_PLANKS);
		RecipeProvider.chestBoat(exporter, WWItems.CYPRESS_CHEST_BOAT, WWItems.CYPRESS_BOAT);
		FrozenRecipeProvider.woodenButton(exporter, WWBlocks.CYPRESS_BUTTON, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenDoor(exporter, WWBlocks.CYPRESS_DOOR, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenFence(exporter, WWBlocks.CYPRESS_FENCE, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(exporter, WWBlocks.CYPRESS_FENCE_GATE, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(exporter, WWBlocks.CYPRESS_PRESSURE_PLATE, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenSlab(exporter, WWBlocks.CYPRESS_SLAB, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenStairs(exporter, WWBlocks.CYPRESS_STAIRS, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(exporter, WWBlocks.CYPRESS_TRAPDOOR, WWBlocks.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenSign(exporter, WWItems.CYPRESS_SIGN, WWBlocks.CYPRESS_PLANKS);
		RecipeProvider.hangingSign(exporter, WWItems.CYPRESS_HANGING_SIGN, WWBlocks.STRIPPED_CYPRESS_LOG);

		RecipeProvider.planksFromLogs(exporter, WWBlocks.PALM_PLANKS, WWItemTags.PALM_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.PALM_WOOD, WWBlocks.PALM_LOG);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.STRIPPED_PALM_WOOD, WWBlocks.STRIPPED_PALM_LOG);
		RecipeProvider.woodenBoat(exporter, WWItems.PALM_BOAT, WWBlocks.PALM_PLANKS);
		RecipeProvider.chestBoat(exporter, WWItems.PALM_CHEST_BOAT, WWItems.PALM_BOAT);
		FrozenRecipeProvider.woodenButton(exporter, WWBlocks.PALM_BUTTON, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenDoor(exporter, WWBlocks.PALM_DOOR, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenFence(exporter, WWBlocks.PALM_FENCE, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(exporter, WWBlocks.PALM_FENCE_GATE, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(exporter, WWBlocks.PALM_PRESSURE_PLATE, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenSlab(exporter, WWBlocks.PALM_SLAB, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenStairs(exporter, WWBlocks.PALM_STAIRS, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(exporter, WWBlocks.PALM_TRAPDOOR, WWBlocks.PALM_PLANKS);
		FrozenRecipeProvider.woodenSign(exporter, WWItems.PALM_SIGN, WWBlocks.PALM_PLANKS);
		RecipeProvider.hangingSign(exporter, WWItems.PALM_HANGING_SIGN, WWBlocks.STRIPPED_PALM_LOG);

		RecipeProvider.planksFromLogs(exporter, WWBlocks.MAPLE_PLANKS, WWItemTags.MAPLE_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.MAPLE_WOOD, WWBlocks.MAPLE_LOG);
		RecipeProvider.woodFromLogs(exporter, WWBlocks.STRIPPED_MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_LOG);
		RecipeProvider.woodenBoat(exporter, WWItems.MAPLE_BOAT, WWBlocks.MAPLE_PLANKS);
		RecipeProvider.chestBoat(exporter, WWItems.MAPLE_CHEST_BOAT, WWItems.MAPLE_BOAT);
		FrozenRecipeProvider.woodenButton(exporter, WWBlocks.MAPLE_BUTTON, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenDoor(exporter, WWBlocks.MAPLE_DOOR, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenFence(exporter, WWBlocks.MAPLE_FENCE, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(exporter, WWBlocks.MAPLE_FENCE_GATE, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(exporter, WWBlocks.MAPLE_PRESSURE_PLATE, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenSlab(exporter, WWBlocks.MAPLE_SLAB, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenStairs(exporter, WWBlocks.MAPLE_STAIRS, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(exporter, WWBlocks.MAPLE_TRAPDOOR, WWBlocks.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenSign(exporter, WWItems.MAPLE_SIGN, WWBlocks.MAPLE_PLANKS);
		RecipeProvider.hangingSign(exporter, WWItems.MAPLE_HANGING_SIGN, WWBlocks.STRIPPED_MAPLE_LOG);
	}

}
