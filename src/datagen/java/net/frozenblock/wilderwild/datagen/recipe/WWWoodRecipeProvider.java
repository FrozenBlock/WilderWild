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

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;

public class WWWoodRecipeProvider {

	static void buildRecipes(RecipeOutput exporter) {
		RecipeProvider.planksFromLogs(exporter, RegisterBlocks.BAOBAB_PLANKS, WilderItemTags.BAOBAB_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.BAOBAB_WOOD, RegisterBlocks.BAOBAB_LOG);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.STRIPPED_BAOBAB_WOOD, RegisterBlocks.STRIPPED_BAOBAB_LOG);
		RecipeProvider.woodenBoat(exporter, RegisterItems.BAOBAB_BOAT, RegisterBlocks.BAOBAB_PLANKS);
		RecipeProvider.chestBoat(exporter, RegisterItems.BAOBAB_CHEST_BOAT, RegisterItems.BAOBAB_BOAT);
		RecipeProvider.buttonBuilder(RegisterBlocks.BAOBAB_BUTTON, Ingredient.of(RegisterBlocks.BAOBAB_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.BAOBAB_PLANKS)).save(exporter);
		RecipeProvider.doorBuilder(RegisterBlocks.BAOBAB_DOOR, Ingredient.of(RegisterBlocks.BAOBAB_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.BAOBAB_PLANKS)).save(exporter);
		RecipeProvider.fenceBuilder(RegisterBlocks.BAOBAB_FENCE, Ingredient.of(RegisterBlocks.BAOBAB_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.BAOBAB_PLANKS)).save(exporter);
		RecipeProvider.fenceGateBuilder(RegisterBlocks.BAOBAB_FENCE_GATE, Ingredient.of(RegisterBlocks.BAOBAB_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.BAOBAB_PLANKS)).save(exporter);
		RecipeProvider.pressurePlate(exporter, RegisterBlocks.BAOBAB_PRESSURE_PLATE, RegisterBlocks.BAOBAB_PLANKS);
		RecipeProvider.slab(exporter, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.BAOBAB_SLAB, RegisterBlocks.BAOBAB_PLANKS);
		RecipeProvider.stairBuilder(RegisterBlocks.BAOBAB_STAIRS, Ingredient.of(RegisterBlocks.BAOBAB_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.BAOBAB_PLANKS)).save(exporter);
		RecipeProvider.trapdoorBuilder(RegisterBlocks.BAOBAB_TRAPDOOR, Ingredient.of(RegisterBlocks.BAOBAB_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.BAOBAB_PLANKS)).save(exporter);
		RecipeProvider.signBuilder(RegisterItems.BAOBAB_SIGN, Ingredient.of(RegisterBlocks.BAOBAB_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.BAOBAB_PLANKS)).save(exporter);
		RecipeProvider.hangingSign(exporter, RegisterItems.BAOBAB_HANGING_SIGN, RegisterBlocks.BAOBAB_PLANKS);

		RecipeProvider.planksFromLogs(exporter, RegisterBlocks.CYPRESS_PLANKS, WilderItemTags.CYPRESS_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.CYPRESS_WOOD, RegisterBlocks.CYPRESS_LOG);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.STRIPPED_CYPRESS_WOOD, RegisterBlocks.STRIPPED_CYPRESS_LOG);
		RecipeProvider.woodenBoat(exporter, RegisterItems.CYPRESS_BOAT, RegisterBlocks.CYPRESS_PLANKS);
		RecipeProvider.chestBoat(exporter, RegisterItems.CYPRESS_CHEST_BOAT, RegisterItems.CYPRESS_BOAT);
		RecipeProvider.buttonBuilder(RegisterBlocks.CYPRESS_BUTTON, Ingredient.of(RegisterBlocks.CYPRESS_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.CYPRESS_PLANKS)).save(exporter);
		RecipeProvider.doorBuilder(RegisterBlocks.CYPRESS_DOOR, Ingredient.of(RegisterBlocks.CYPRESS_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.CYPRESS_PLANKS)).save(exporter);
		RecipeProvider.fenceBuilder(RegisterBlocks.CYPRESS_FENCE, Ingredient.of(RegisterBlocks.CYPRESS_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.CYPRESS_PLANKS)).save(exporter);
		RecipeProvider.fenceGateBuilder(RegisterBlocks.CYPRESS_FENCE_GATE, Ingredient.of(RegisterBlocks.CYPRESS_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.CYPRESS_PLANKS)).save(exporter);
		RecipeProvider.pressurePlate(exporter, RegisterBlocks.CYPRESS_PRESSURE_PLATE, RegisterBlocks.CYPRESS_PLANKS);
		RecipeProvider.slab(exporter, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CYPRESS_SLAB, RegisterBlocks.CYPRESS_PLANKS);
		RecipeProvider.stairBuilder(RegisterBlocks.CYPRESS_STAIRS, Ingredient.of(RegisterBlocks.CYPRESS_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.CYPRESS_PLANKS)).save(exporter);
		RecipeProvider.trapdoorBuilder(RegisterBlocks.CYPRESS_TRAPDOOR, Ingredient.of(RegisterBlocks.CYPRESS_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.CYPRESS_PLANKS)).save(exporter);
		RecipeProvider.signBuilder(RegisterItems.CYPRESS_SIGN, Ingredient.of(RegisterBlocks.CYPRESS_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.CYPRESS_PLANKS)).save(exporter);
		RecipeProvider.hangingSign(exporter, RegisterItems.CYPRESS_HANGING_SIGN, RegisterBlocks.CYPRESS_PLANKS);

		RecipeProvider.planksFromLogs(exporter, RegisterBlocks.PALM_PLANKS, WilderItemTags.PALM_LOGS, 4);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.PALM_WOOD, RegisterBlocks.PALM_LOG);
		RecipeProvider.woodFromLogs(exporter, RegisterBlocks.STRIPPED_PALM_WOOD, RegisterBlocks.STRIPPED_PALM_LOG);
		RecipeProvider.woodenBoat(exporter, RegisterItems.PALM_BOAT, RegisterBlocks.PALM_PLANKS);
		RecipeProvider.chestBoat(exporter, RegisterItems.PALM_CHEST_BOAT, RegisterItems.PALM_BOAT);
		RecipeProvider.buttonBuilder(RegisterBlocks.PALM_BUTTON, Ingredient.of(RegisterBlocks.PALM_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.PALM_PLANKS)).save(exporter);
		RecipeProvider.doorBuilder(RegisterBlocks.PALM_DOOR, Ingredient.of(RegisterBlocks.PALM_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.PALM_PLANKS)).save(exporter);
		RecipeProvider.fenceBuilder(RegisterBlocks.PALM_FENCE, Ingredient.of(RegisterBlocks.PALM_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.PALM_PLANKS)).save(exporter);
		RecipeProvider.fenceGateBuilder(RegisterBlocks.PALM_FENCE_GATE, Ingredient.of(RegisterBlocks.PALM_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.PALM_PLANKS)).save(exporter);
		RecipeProvider.pressurePlate(exporter, RegisterBlocks.PALM_PRESSURE_PLATE, RegisterBlocks.PALM_PLANKS);
		RecipeProvider.slab(exporter, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.PALM_SLAB, RegisterBlocks.PALM_PLANKS);
		RecipeProvider.stairBuilder(RegisterBlocks.PALM_STAIRS, Ingredient.of(RegisterBlocks.PALM_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.PALM_PLANKS)).save(exporter);
		RecipeProvider.trapdoorBuilder(RegisterBlocks.PALM_TRAPDOOR, Ingredient.of(RegisterBlocks.PALM_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.PALM_PLANKS)).save(exporter);
		RecipeProvider.signBuilder(RegisterItems.PALM_SIGN, Ingredient.of(RegisterBlocks.PALM_PLANKS))
			.unlockedBy("has_planks", RecipeProvider.has(RegisterBlocks.PALM_PLANKS)).save(exporter);
		RecipeProvider.hangingSign(exporter, RegisterItems.PALM_HANGING_SIGN, RegisterBlocks.PALM_PLANKS);
	}

}
