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

package net.frozenblock.wilderwild.data.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.lib.item.api.recipe.RecipeExportNamespaceFix;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.ColorCollection;

public final class WWColoredRecipeProvider extends FabricRecipeProvider {

	public WWColoredRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {
			@Override
			public void buildRecipes() {
				RecipeExportNamespaceFix.setCurrentGeneratingModId(WWConstants.MOD_ID);

				// DYES
				this.oneToOneConversionRecipe(Items.DYE.orange(), WWItems.LANTANAS, "orange_dye");

				this.oneToOneConversionRecipe(Items.DYE.purple(), WWItems.PHLOX, "purple_dye");

				this.oneToOneConversionRecipe(Items.DYE.red(), WWItems.RED_HIBISCUS, "red_dye");
				this.oneToOneConversionRecipe(Items.DYE.yellow(), WWItems.YELLOW_HIBISCUS, "yellow_dye");
				this.oneToOneConversionRecipe(Items.DYE.white(), WWItems.WHITE_HIBISCUS, "white_dye");
				this.oneToOneConversionRecipe(Items.DYE.pink(), WWItems.PINK_HIBISCUS, "pink_dye");
				this.oneToOneConversionRecipe(Items.DYE.purple(), WWItems.PURPLE_HIBISCUS, "purple_dye");

				this.oneToOneConversionRecipe(Items.DYE.lightGray(), WWItems.DATURA, "light_gray_dye", 2);

				this.oneToOneConversionRecipe(Items.DYE.orange(), WWItems.MILKWEED, "orange_dye", 2);

				this.oneToOneConversionRecipe(Items.DYE.magenta(), WWItems.CARNATION, "magenta_dye");

				this.oneToOneConversionRecipe(Items.DYE.orange(), WWItems.MARIGOLD, "orange_dye");

				this.oneToOneConversionRecipe(Items.DYE.purple(), WWItems.PASQUEFLOWER, "purple_dye");

				this.oneToOneConversionRecipe(Items.DYE.white(), WWItems.SPLIT_COCONUT, "white_dye");
				this.shapeless(RecipeCategory.MISC, Items.BOWL, 2)
					.requires(WWItems.SPLIT_COCONUT, 2)
					.group("bowl")
					.unlockedBy(RecipeProvider.getHasName(WWItems.SPLIT_COCONUT), this.has(WWItems.SPLIT_COCONUT))
					.save(output, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.BOWL, WWItems.SPLIT_COCONUT)));

				// FIREFLY BOTTLE
				final List<Identifier> colorIds = ColorCollection.NAMES.map(WWConstants::id).asList();
				colorFireflyBottlesWithDyes(colorIds);

				final List<Item> dyes = Items.DYE.asList();
				for(int dyeIndex = 0; dyeIndex < dyes.size(); dyeIndex++) {
					final Item dye = dyes.get(dyeIndex);
					final Identifier outputColor = colorIds.get(dyeIndex);
					fireflyBottle(outputColor, dye);
				}

				RecipeExportNamespaceFix.clearCurrentGeneratingModId();
			}

			void colorFireflyBottlesWithDyes(List<Identifier> fireflyColors) {
				final List<Item> dyes = Items.DYE.asList();
				for(int dyeIndex = 0; dyeIndex < dyes.size(); dyeIndex++) {
					final Item dye = dyes.get(dyeIndex);
					final Identifier outputColor = fireflyColors.get(dyeIndex);

					final List<Ingredient> possibleIngredients = new ArrayList<>();
					for (Identifier fireflyColor : fireflyColors) {
						if (fireflyColor.equals(outputColor)) continue;

						possibleIngredients.add(DefaultCustomIngredients.components(
							Ingredient.of(WWItems.FIREFLY_BOTTLE),
							DataComponentPatch.builder()
								.set(WWDataComponents.FIREFLY_COLOR.get(), registries.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, fireflyColor)))
								.build()
						));
					}

					final Ingredient input = DefaultCustomIngredients.any(possibleIngredients.toArray(new Ingredient[0]));

					this.shapeless(
							RecipeCategory.MISC,
							new ItemStackTemplate(
								WWItems.FIREFLY_BOTTLE.get(),
								DataComponentPatch.builder()
									.set(WWDataComponents.FIREFLY_COLOR.get(), registries.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, outputColor)))
									.build()
							)
						)
						.requires(dye)
						.requires(input)
						.group("firefly_bottle")
						.unlockedBy("has_needed_dye", this.has(dye))
						.save(
							this.output,
							WWConstants.string("dye_" + outputColor.getPath() + "_firefly_bottle")
						);
				}
			}

			void fireflyBottle(Identifier fireflyColor, Item dye) {
				this.shapeless(
						RecipeCategory.MISC,
						new ItemStackTemplate(
							WWItems.FIREFLY_BOTTLE.get(),
							DataComponentPatch.builder()
								.set(WWDataComponents.FIREFLY_COLOR.get(), registries.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, fireflyColor)))
								.build()
						)
					)
					.group("firefly_bottle")
					.requires(dye)
					.requires(
						DefaultCustomIngredients.components(
							Ingredient.of(WWItems.FIREFLY_BOTTLE),
							DataComponentPatch.builder()
								.set(WWDataComponents.FIREFLY_COLOR.get(), registries.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, WWConstants.id("on"))))
								.build()
						)
					)
					.unlockedBy(getHasName(WWItems.FIREFLY_BOTTLE), this.has(WWItems.FIREFLY_BOTTLE))
					.save(this.output, WWConstants.string(fireflyColor.getPath() + "_firefly_bottle"));
			}
		};
	}

	@Override
	public String getName() {
		return "Wilder Wild Colored Recipes";
	}
}
