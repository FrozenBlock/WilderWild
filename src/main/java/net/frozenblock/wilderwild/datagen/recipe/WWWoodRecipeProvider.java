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

package net.frozenblock.wilderwild.datagen.recipe;

import net.frozenblock.lib.recipe.api.FrozenRecipeProvider;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public final class WWWoodRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput output) {
		provider.planksFromLogs(WWItems.BAOBAB_PLANKS, WWBlockItemTags.BAOBAB_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.BAOBAB_WOOD, WWItems.BAOBAB_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_BAOBAB_WOOD, WWItems.STRIPPED_BAOBAB_LOG);
		provider.woodenBoat(WWItems.BAOBAB_BOAT, WWItems.BAOBAB_PLANKS);
		provider.chestBoat(WWItems.BAOBAB_CHEST_BOAT, WWItems.BAOBAB_BOAT);
		FrozenRecipeProvider.woodenButton(provider, output, WWItems.BAOBAB_BUTTON, WWItems.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, output, WWItems.BAOBAB_DOOR, WWItems.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, output, WWItems.BAOBAB_FENCE, WWItems.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, output, WWItems.BAOBAB_FENCE_GATE, WWItems.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, output, WWItems.BAOBAB_PRESSURE_PLATE, WWItems.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, output, WWItems.BAOBAB_SLAB, WWItems.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, output, WWItems.BAOBAB_STAIRS, WWItems.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, output, WWItems.BAOBAB_TRAPDOOR, WWItems.BAOBAB_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, output, WWItems.BAOBAB_SIGN, WWItems.BAOBAB_PLANKS);
		provider.hangingSign(WWItems.BAOBAB_HANGING_SIGN, WWItems.STRIPPED_BAOBAB_LOG);
		provider.shelf(WWItems.BAOBAB_SHELF, WWItems.STRIPPED_BAOBAB_LOG);

		provider.planksFromLogs(WWItems.WILLOW_PLANKS, WWBlockItemTags.WILLOW_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.WILLOW_WOOD, WWItems.WILLOW_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_WILLOW_WOOD, WWItems.STRIPPED_WILLOW_LOG);
		provider.woodenBoat(WWItems.WILLOW_BOAT, WWItems.WILLOW_PLANKS);
		provider.chestBoat(WWItems.WILLOW_CHEST_BOAT, WWItems.WILLOW_BOAT);
		FrozenRecipeProvider.woodenButton(provider, output, WWItems.WILLOW_BUTTON, WWItems.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, output, WWItems.WILLOW_DOOR, WWItems.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, output, WWItems.WILLOW_FENCE, WWItems.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, output, WWItems.WILLOW_FENCE_GATE, WWItems.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, output, WWItems.WILLOW_PRESSURE_PLATE, WWItems.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, output, WWItems.WILLOW_SLAB, WWItems.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, output, WWItems.WILLOW_STAIRS, WWItems.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, output, WWItems.WILLOW_TRAPDOOR, WWItems.WILLOW_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, output, WWItems.WILLOW_SIGN, WWItems.WILLOW_PLANKS);
		provider.hangingSign(WWItems.WILLOW_HANGING_SIGN, WWItems.STRIPPED_WILLOW_LOG);
		provider.shelf(WWItems.WILLOW_SHELF, WWItems.STRIPPED_WILLOW_LOG);

		provider.planksFromLogs(WWItems.CYPRESS_PLANKS, WWBlockItemTags.CYPRESS_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.CYPRESS_WOOD, WWItems.CYPRESS_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_CYPRESS_WOOD, WWItems.STRIPPED_CYPRESS_LOG);
		provider.woodenBoat(WWItems.CYPRESS_BOAT, WWItems.CYPRESS_PLANKS);
		provider.chestBoat(WWItems.CYPRESS_CHEST_BOAT, WWItems.CYPRESS_BOAT);
		FrozenRecipeProvider.woodenButton(provider, output, WWItems.CYPRESS_BUTTON, WWItems.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, output, WWItems.CYPRESS_DOOR, WWItems.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, output, WWItems.CYPRESS_FENCE, WWItems.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, output, WWItems.CYPRESS_FENCE_GATE, WWItems.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, output, WWItems.CYPRESS_PRESSURE_PLATE, WWItems.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, output, WWItems.CYPRESS_SLAB, WWItems.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, output, WWItems.CYPRESS_STAIRS, WWItems.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, output, WWItems.CYPRESS_TRAPDOOR, WWItems.CYPRESS_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, output, WWItems.CYPRESS_SIGN, WWItems.CYPRESS_PLANKS);
		provider.hangingSign(WWItems.CYPRESS_HANGING_SIGN, WWItems.STRIPPED_CYPRESS_LOG);
		provider.shelf(WWItems.CYPRESS_SHELF, WWItems.STRIPPED_CYPRESS_LOG);

		provider.planksFromLogs(WWItems.PALM_PLANKS, WWBlockItemTags.PALM_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.PALM_WOOD, WWItems.PALM_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_PALM_WOOD, WWItems.STRIPPED_PALM_LOG);
		provider.woodenBoat(WWItems.PALM_BOAT, WWItems.PALM_PLANKS);
		provider.chestBoat(WWItems.PALM_CHEST_BOAT, WWItems.PALM_BOAT);
		FrozenRecipeProvider.woodenButton(provider, output, WWItems.PALM_BUTTON, WWItems.PALM_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, output, WWItems.PALM_DOOR, WWItems.PALM_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, output, WWItems.PALM_FENCE, WWItems.PALM_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, output, WWItems.PALM_FENCE_GATE, WWItems.PALM_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, output, WWItems.PALM_PRESSURE_PLATE, WWItems.PALM_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, output, WWItems.PALM_SLAB, WWItems.PALM_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, output, WWItems.PALM_STAIRS, WWItems.PALM_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, output, WWItems.PALM_TRAPDOOR, WWItems.PALM_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, output, WWItems.PALM_SIGN, WWItems.PALM_PLANKS);
		provider.hangingSign(WWItems.PALM_HANGING_SIGN, WWItems.STRIPPED_PALM_LOG);
		provider.shelf(WWItems.PALM_SHELF, WWItems.STRIPPED_PALM_LOG);

		provider.planksFromLogs(WWItems.MAPLE_PLANKS, WWBlockItemTags.MAPLE_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.MAPLE_WOOD, WWItems.MAPLE_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_MAPLE_WOOD, WWItems.STRIPPED_MAPLE_LOG);
		provider.woodenBoat(WWItems.MAPLE_BOAT, WWItems.MAPLE_PLANKS);
		provider.chestBoat(WWItems.MAPLE_CHEST_BOAT, WWItems.MAPLE_BOAT);
		FrozenRecipeProvider.woodenButton(provider, output, WWItems.MAPLE_BUTTON, WWItems.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenDoor(provider, output, WWItems.MAPLE_DOOR, WWItems.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenFence(provider, output, WWItems.MAPLE_FENCE, WWItems.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenFenceGate(provider, output, WWItems.MAPLE_FENCE_GATE, WWItems.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenPressurePlace(provider, output, WWItems.MAPLE_PRESSURE_PLATE, WWItems.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenSlab(provider, output, WWItems.MAPLE_SLAB, WWItems.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenStairs(provider, output, WWItems.MAPLE_STAIRS, WWItems.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenTrapdoor(provider, output, WWItems.MAPLE_TRAPDOOR, WWItems.MAPLE_PLANKS);
		FrozenRecipeProvider.woodenSign(provider, output, WWItems.MAPLE_SIGN, WWItems.MAPLE_PLANKS);
		provider.hangingSign(WWItems.MAPLE_HANGING_SIGN, WWItems.STRIPPED_MAPLE_LOG);
		provider.shelf(WWItems.MAPLE_SHELF, WWItems.STRIPPED_MAPLE_LOG);
	}

}
