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
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import java.util.function.Consumer;

public class MesogleaRecipeProvider {

	static void buildRecipes(Consumer<FinishedRecipe> exporter) {
		mesoglea(RegisterBlocks.BLUE_NEMATOCYST, RegisterBlocks.BLUE_MESOGLEA, exporter);
		mesoglea(RegisterBlocks.LIME_NEMATOCYST, RegisterBlocks.LIME_MESOGLEA, exporter);
		mesoglea(RegisterBlocks.PINK_NEMATOCYST, RegisterBlocks.PINK_MESOGLEA, exporter);
		mesoglea(RegisterBlocks.YELLOW_NEMATOCYST, RegisterBlocks.YELLOW_MESOGLEA, exporter);
		mesoglea(RegisterBlocks.RED_NEMATOCYST, RegisterBlocks.RED_MESOGLEA, exporter);
		mesoglea(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST, RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA, exporter);
		mesoglea(RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST, RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA, exporter);
	}

	public static void mesoglea(ItemLike nematocyst, ItemLike mesoglea, Consumer<FinishedRecipe> exporter) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, mesoglea, 1)
			.group("mesoglea")
			.define('#', Ingredient.of(nematocyst))
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_nematocyst", RecipeProvider.has(nematocyst))
		.save(exporter);
	}
}
