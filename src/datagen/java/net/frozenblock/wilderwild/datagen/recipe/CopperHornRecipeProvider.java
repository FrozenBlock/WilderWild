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

import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.lib.recipe.api.ShapedRecipeBuilderExtension;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
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

public class CopperHornRecipeProvider {

	static void buildRecipes(RecipeOutput exporter) {
		copperHorn(exporter, "clarinet", Instruments.DREAM_GOAT_HORN, RegisterItems.CLARINET_COPPER_HORN);
		copperHorn(exporter, "flute", Instruments.CALL_GOAT_HORN, RegisterItems.FLUTE_COPPER_HORN);
		copperHorn(exporter, "oboe", Instruments.SING_GOAT_HORN, RegisterItems.OBOE_COPPER_HORN);
		copperHorn(exporter, "sax", Instruments.PONDER_GOAT_HORN, RegisterItems.SAX_COPPER_HORN);
		copperHorn(exporter, "trombone", Instruments.SEEK_GOAT_HORN, RegisterItems.TROMBONE_COPPER_HORN);
		copperHorn(exporter, "trumpet", Instruments.ADMIRE_GOAT_HORN, RegisterItems.TRUMPET_COPPER_HORN);
		copperHorn(exporter, "tuba", Instruments.FEEL_GOAT_HORN, RegisterItems.TUBA_COPPER_HORN);
	}

	private static void copperHorn(RecipeOutput exporter, String name, ResourceKey<Instrument> goatHornInstrument, ResourceKey<Instrument> copperHornInstrument) {
		((ShapedRecipeBuilderExtension)ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, RegisterItems.COPPER_HORN)
			.group("wilderwild_copper_horn")
			.define('C', Ingredient.of(Items.COPPER_INGOT))
			.define('G', DefaultCustomIngredients.nbt(Ingredient.of(Items.GOAT_HORN), new CompoundTag() {{
				put("instrument", StringTag.valueOf(goatHornInstrument.location().toString()));
			}}, true))
			.pattern("CGC")
			.pattern(" C ")
			.unlockedBy("has_horn", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOAT_HORN))
		).frozenLib$tag(new CompoundTag() {{ put("instrument", StringTag.valueOf(copperHornInstrument.location().toString())); }})
			.save(exporter, WilderConstants.id(name + "_copper_horn"));
	}

}
