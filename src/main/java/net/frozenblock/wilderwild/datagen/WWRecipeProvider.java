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

package net.frozenblock.wilderwild.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.lib.recipe.api.ShapedRecipeBuilderExtension;
import net.frozenblock.lib.recipe.api.ShapedRecipeUtil;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class WWRecipeProvider extends FabricRecipeProvider {
	public WWRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	public void buildRecipes(RecipeOutput exporter) {
		ShapedRecipeUtil.withResultPatch(
			ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegisterItems.ANCIENT_HORN)
				.group("wilderwild_ancient_horn")
				.define('#', Ingredient.of(RegisterItems.ANCIENT_HORN_FRAGMENT))
				.define('I', Ingredient.of(Items.ECHO_SHARD))
				.define('G', Ingredient.of(Items.GOLD_INGOT))
				.pattern("#G#")
				.pattern("#I#")
				.pattern("I#I")
				.unlockedBy("has_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.ANCIENT_HORN_FRAGMENT)),
			DataComponentPatch.builder()
				.set(DataComponents.INSTRUMENT, BuiltInRegistries.INSTRUMENT.getHolderOrThrow(RegisterItems.ANCIENT_HORN_INSTRUMENT))
				.build()
		).save(exporter, WilderSharedConstants.id("ancient_horn"));

		copperHorn(exporter, "clarinet", Instruments.DREAM_GOAT_HORN, RegisterItems.CLARINET_COPPER_HORN);
		copperHorn(exporter, "flute", Instruments.CALL_GOAT_HORN, RegisterItems.FLUTE_COPPER_HORN);
		copperHorn(exporter, "oboe", Instruments.SING_GOAT_HORN, RegisterItems.OBOE_COPPER_HORN);
		copperHorn(exporter, "sax", Instruments.PONDER_GOAT_HORN, RegisterItems.SAX_COPPER_HORN);
		copperHorn(exporter, "trombone", Instruments.SEEK_GOAT_HORN, RegisterItems.TROMBONE_COPPER_HORN);
		copperHorn(exporter, "trumpet", Instruments.ADMIRE_GOAT_HORN, RegisterItems.TRUMPET_COPPER_HORN);
		copperHorn(exporter, "tuba", Instruments.FEEL_GOAT_HORN, RegisterItems.TUBA_COPPER_HORN);
	}

	private static void copperHorn(RecipeOutput exporter, String name, ResourceKey<Instrument> goatHornInstrument, ResourceKey<Instrument> copperHornInstrument) {
		((ShapedRecipeBuilderExtension) ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, RegisterItems.COPPER_HORN)
			.group("wilderwild_copper_horn")
			.define('C', Ingredient.of(Items.COPPER_INGOT))
			.define('G', DefaultCustomIngredients.components(Ingredient.of(Items.GOAT_HORN),
				DataComponentPatch.builder()
					.set(DataComponents.INSTRUMENT, BuiltInRegistries.INSTRUMENT.getHolderOrThrow(goatHornInstrument))
					.build()
			))
			.pattern("CGC")
			.pattern(" C ")
			.unlockedBy("has_horn", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOAT_HORN))
		).frozenLib$patch(DataComponentPatch.builder().set(DataComponents.INSTRUMENT, BuiltInRegistries.INSTRUMENT.getHolderOrThrow(copperHornInstrument)).build())
			.save(exporter, WilderSharedConstants.id(name + "_copper_horn"));
	}
}
