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

import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.lib.recipe.api.ShapedRecipeBuilderExtension;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public final class CopperHornRecipeProvider {

	static void buildRecipes(RecipeOutput exporter) {
		copperHorn(exporter, "clarinet", Instruments.DREAM_GOAT_HORN, WWItems.CLARINET_COPPER_HORN);
		copperHorn(exporter, "flute", Instruments.CALL_GOAT_HORN, WWItems.FLUTE_COPPER_HORN);
		copperHorn(exporter, "oboe", Instruments.SING_GOAT_HORN, WWItems.OBOE_COPPER_HORN);
		copperHorn(exporter, "sax", Instruments.PONDER_GOAT_HORN, WWItems.SAX_COPPER_HORN);
		copperHorn(exporter, "trombone", Instruments.SEEK_GOAT_HORN, WWItems.TROMBONE_COPPER_HORN);
		copperHorn(exporter, "trumpet", Instruments.ADMIRE_GOAT_HORN, WWItems.TRUMPET_COPPER_HORN);
		copperHorn(exporter, "tuba", Instruments.FEEL_GOAT_HORN, WWItems.TUBA_COPPER_HORN);
		copperHorn(exporter, "recorder", Instruments.YEARN_GOAT_HORN, WWItems.RECORDER_COPPER_HORN);
	}

	private static void copperHorn(RecipeOutput exporter, String name, ResourceKey<Instrument> goatHornInstrument, ResourceKey<Instrument> copperHornInstrument) {
		((ShapedRecipeBuilderExtension) ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, WWItems.COPPER_HORN)
			.group("wilderwild_copper_horn")
			.define('C', Ingredient.of(Items.COPPER_INGOT))
			.define('G', DefaultCustomIngredients.components(Ingredient.of(Items.GOAT_HORN),
				DataComponentPatch.builder()
					.set(DataComponents.INSTRUMENT, BuiltInRegistries.INSTRUMENT.getHolderOrThrow(goatHornInstrument))
					.build()
			))
			.pattern("CGC")
			.pattern(" C ")
			.unlockedBy("has_horn", RecipeProvider.has(Items.GOAT_HORN))
		).frozenLib$patch(DataComponentPatch.builder().set(DataComponents.INSTRUMENT, BuiltInRegistries.INSTRUMENT.getHolderOrThrow(copperHornInstrument)).build())
			.save(exporter, WWConstants.id(name + "_copper_horn"));
	}

}
