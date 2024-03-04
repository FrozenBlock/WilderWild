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

import com.google.common.collect.ImmutableList;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.lib.recipe.api.ShapedRecipeUtil;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterDataComponents;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class WWRecipeProvider extends FabricRecipeProvider {
	public WWRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void buildRecipes(RecipeOutput exporter) {
		HollowedLogRecipeProvider.buildRecipes(exporter);
		WWWoodRecipeProvider.buildRecipes(exporter);
		MesogleaRecipeProvider.buildRecipes(exporter);
		FireflyBottleRecipeProvider.buildRecipes(exporter);
		CopperHornRecipeProvider.buildRecipes(exporter);
		WWCookRecipeProvider.buildRecipes(exporter);

		ShapedRecipeUtil.withResultPatch(
			ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegisterItems.ANCIENT_HORN)
				.group("wilderwild_ancient_horn")
				.define('#', Ingredient.of(RegisterItems.ANCIENT_HORN_FRAGMENT))
				.define('I', Ingredient.of(Items.ECHO_SHARD))
				.define('G', Ingredient.of(Items.GOLD_INGOT))
				.pattern("#G#")
				.pattern("#I#")
				.pattern("I#I")
				.unlockedBy("has_fragment", RecipeProvider.has(RegisterItems.ANCIENT_HORN_FRAGMENT)),
			DataComponentPatch.builder()
				.set(DataComponents.INSTRUMENT, BuiltInRegistries.INSTRUMENT.getHolderOrThrow(RegisterItems.ANCIENT_HORN_INSTRUMENT))
				.build()
		).save(exporter);

		ShapedRecipeUtil.withResultPatch(
			ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, RegisterBlocks.DISPLAY_LANTERN)
				.define('X', Ingredient.of(Items.IRON_NUGGET))
				.define('#', Ingredient.of(Items.GLASS_PANE))
				.pattern("XXX")
				.pattern("X#X")
				.pattern("XXX")
				.unlockedBy("has_iron_ingot", RecipeProvider.has(Items.IRON_INGOT))
				.unlockedBy("has_iron_nugget", RecipeProvider.has(Items.IRON_NUGGET)),
			DataComponentPatch.builder()
				.set(RegisterDataComponents.FIREFLIES, ImmutableList.of())
				.build()
		).save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.STONE_CHEST)
			.group("stone_chest")
			.define('_', Ingredient.of(Items.COBBLED_DEEPSLATE_SLAB))
			.define('#', Ingredient.of(Items.COBBLED_DEEPSLATE))
			.pattern("___")
			.pattern("# #")
			.pattern("###")
			.unlockedBy("has_stone", RecipeProvider.has(Items.COBBLED_DEEPSLATE))
		.save(exporter);
	}

}
