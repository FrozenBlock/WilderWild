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

package net.frozenblock.wilderwild.datagen.recipe;

import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public final class MesogleaRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput output) {
		mesoglea(provider, WWBlocks.BLUE_NEMATOCYST, WWBlocks.BLUE_MESOGLEA, output);
		mesoglea(provider, WWBlocks.LIME_NEMATOCYST, WWBlocks.LIME_MESOGLEA, output);
		mesoglea(provider, WWBlocks.PINK_NEMATOCYST, WWBlocks.PINK_MESOGLEA, output);
		mesoglea(provider, WWBlocks.YELLOW_NEMATOCYST, WWBlocks.YELLOW_MESOGLEA, output);
		mesoglea(provider, WWBlocks.RED_NEMATOCYST, WWBlocks.RED_MESOGLEA, output);
		mesoglea(provider, WWBlocks.PEARLESCENT_BLUE_NEMATOCYST, WWBlocks.PEARLESCENT_BLUE_MESOGLEA, output);
		mesoglea(provider, WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST, WWBlocks.PEARLESCENT_PURPLE_MESOGLEA, output);
	}

	public static void mesoglea(RecipeProvider provider, ItemLike nematocyst, ItemLike mesoglea, RecipeOutput exporter) {
		provider.shaped(RecipeCategory.DECORATIONS, mesoglea, 1)
			.group("mesoglea")
			.define('#', Ingredient.of(nematocyst))
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_nematocyst", provider.has(nematocyst))
		.save(exporter);
	}
}
