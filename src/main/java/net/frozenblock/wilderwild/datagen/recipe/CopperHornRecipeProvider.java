/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datagen.recipe;

import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.lib.recipe.api.ShapedRecipeBuilderExtension;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public final class CopperHornRecipeProvider {

	static void buildRecipes(RecipeProvider provider, HolderLookup.Provider lookup, RecipeOutput exporter) {
		copperHorn(provider, lookup, exporter, "recorder", Instruments.YEARN_GOAT_HORN, WWItems.RECORDER_COPPER_HORN);
		copperHorn(provider, lookup, exporter, "clarinet", Instruments.DREAM_GOAT_HORN, WWItems.CLARINET_COPPER_HORN);
		copperHorn(provider, lookup, exporter, "flute", Instruments.CALL_GOAT_HORN, WWItems.FLUTE_COPPER_HORN);
		copperHorn(provider, lookup, exporter, "oboe", Instruments.SING_GOAT_HORN, WWItems.OBOE_COPPER_HORN);
		copperHorn(provider, lookup, exporter, "sax", Instruments.PONDER_GOAT_HORN, WWItems.SAX_COPPER_HORN);
		copperHorn(provider, lookup, exporter, "trombone", Instruments.SEEK_GOAT_HORN, WWItems.TROMBONE_COPPER_HORN);
		copperHorn(provider, lookup, exporter, "trumpet", Instruments.ADMIRE_GOAT_HORN, WWItems.TRUMPET_COPPER_HORN);
		copperHorn(provider, lookup, exporter, "tuba", Instruments.FEEL_GOAT_HORN, WWItems.TUBA_COPPER_HORN);
	}

	private static void copperHorn(RecipeProvider provider, HolderLookup.Provider lookup, RecipeOutput exporter, String name, ResourceKey<Instrument> goatHornInstrument, ResourceKey<Instrument> copperHornInstrument) {
		((ShapedRecipeBuilderExtension) provider.shaped(RecipeCategory.TOOLS, WWItems.COPPER_HORN)
			.group("wilderwild_copper_horn")
			.define('C', Ingredient.of(Items.COPPER_INGOT))
			.define('G', DefaultCustomIngredients.components(Ingredient.of(Items.GOAT_HORN),
				DataComponentPatch.builder()
					.set(DataComponents.INSTRUMENT, lookup.lookupOrThrow(Registries.INSTRUMENT).getOrThrow(goatHornInstrument))
					.build()
			))
			.pattern("CGC")
			.pattern(" C ")
			.unlockedBy("has_horn", provider.has(Items.GOAT_HORN))
		).frozenLib$patch(DataComponentPatch.builder().set(DataComponents.INSTRUMENT, lookup.lookupOrThrow(Registries.INSTRUMENT).getOrThrow(copperHornInstrument)).build())
			.save(exporter, WWConstants.string(name + "_copper_horn"));
	}

}
