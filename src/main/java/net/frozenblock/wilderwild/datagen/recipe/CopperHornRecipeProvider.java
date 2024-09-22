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

import java.util.function.Consumer;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.lib.recipe.api.ShapedRecipeBuilderExtension;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public final class CopperHornRecipeProvider {

	static void buildRecipes(Consumer<FinishedRecipe> exporter) {
		copperHorn(exporter, "clarinet", Instruments.DREAM_GOAT_HORN, WWItems.CLARINET_COPPER_HORN);
		copperHorn(exporter, "flute", Instruments.CALL_GOAT_HORN, WWItems.FLUTE_COPPER_HORN);
		copperHorn(exporter, "oboe", Instruments.SING_GOAT_HORN,  WWItems.OBOE_COPPER_HORN);
		copperHorn(exporter, "sax", Instruments.PONDER_GOAT_HORN,  WWItems.SAX_COPPER_HORN);
		copperHorn(exporter, "trombone", Instruments.SEEK_GOAT_HORN,  WWItems.TROMBONE_COPPER_HORN);
		copperHorn(exporter, "trumpet", Instruments.ADMIRE_GOAT_HORN,  WWItems.TRUMPET_COPPER_HORN);
		copperHorn(exporter, "tuba", Instruments.FEEL_GOAT_HORN,  WWItems.TUBA_COPPER_HORN);
	}

	private static void copperHorn(Consumer<FinishedRecipe> exporter, String name, ResourceKey<Instrument> goatHornInstrument, ResourceKey<Instrument> copperHornInstrument) {
		((ShapedRecipeBuilderExtension)ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,  WWItems.COPPER_HORN)
			.group("wilderwild_copper_horn")
			.define('C', Ingredient.of(Items.COPPER_INGOT))
			.define('G', DefaultCustomIngredients.nbt(Ingredient.of(Items.GOAT_HORN), new CompoundTag() {{
				put("instrument", StringTag.valueOf(goatHornInstrument.location().toString()));
			}}, true))
			.pattern("CGC")
			.pattern(" C ")
			.unlockedBy("has_horn", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOAT_HORN))
		).frozenLib$tag(new CompoundTag() {{ put("instrument", StringTag.valueOf(copperHornInstrument.location().toString())); }})
			.save(exporter, WWConstants.id(name + "_copper_horn"));
	}

}
