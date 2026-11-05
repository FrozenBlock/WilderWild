/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.data.wilder_aquatic;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.lib.item.api.recipe.RecipeExportNamespaceFix;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;

public final class WARecipeProvider extends FabricRecipeProvider {

	public WARecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {
			@Override
			public void buildRecipes() {
				RecipeExportNamespaceFix.setCurrentGeneratingModId(WWConstants.MOD_ID);

				this.generateForEnabledBlockFamilies(WWFeatureFlags.WILDER_AQUATIC_FLAG_SET);

				// PEARL
				this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, WWItems.PEARL_BLOCK, WWItems.PEARL);

				RecipeExportNamespaceFix.clearCurrentGeneratingModId();
			}
		};
	}

	@Override
	public String getName() {
		return "Wilder Aquatic Recipes";
	}
}
