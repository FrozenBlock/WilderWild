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

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.lib.recipe.api.ShapedRecipeUtil;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import java.util.function.Consumer;

public class WWRecipeProvider extends FabricRecipeProvider {
	public WWRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> exporter) {
		HollowedLogRecipeProvider.buildRecipes(exporter);
		WWWoodRecipeProvider.buildRecipes(exporter);
		MesogleaRecipeProvider.buildRecipes(exporter);
		FireflyBottleRecipeProvider.buildRecipes(exporter);
		CopperHornRecipeProvider.buildRecipes(exporter);
		WWNaturalRecipeProvider.buildRecipes(exporter);
		WWCookRecipeProvider.buildRecipes(exporter);

		ShapedRecipeUtil.withResultTag(
			ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, RegisterItems.ANCIENT_HORN)
				.group("wilderwild_ancient_horn")
				.define('#', Ingredient.of(RegisterItems.ANCIENT_HORN_FRAGMENT))
				.define('I', Ingredient.of(Items.ECHO_SHARD))
				.define('G', Ingredient.of(Items.GOLD_INGOT))
				.pattern("#G#")
				.pattern("#I#")
				.pattern("I#I")
				.unlockedBy("has_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.ANCIENT_HORN_FRAGMENT)),
			new CompoundTag() {{ put("instrument", StringTag.valueOf(RegisterItems.ANCIENT_HORN_INSTRUMENT.location().toString())); }}
		).save(exporter, WilderSharedConstants.id("ancient_horn"));

			ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, RegisterBlocks.DISPLAY_LANTERN)
				.define('X', Ingredient.of(Items.IRON_NUGGET))
				.define('#', Ingredient.of(Items.GLASS_PANE))
				.pattern("XXX")
				.pattern("X#X")
				.pattern("XXX")
				.unlockedBy(RecipeProvider.getHasName(Items.IRON_INGOT), RecipeProvider.has(Items.IRON_INGOT))
				.unlockedBy(RecipeProvider.getHasName(Items.IRON_NUGGET), RecipeProvider.has(Items.IRON_NUGGET))
		.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.STONE_CHEST)
			.group("stone_chest")
			.define('_', Ingredient.of(Items.COBBLED_DEEPSLATE_SLAB))
			.define('#', Ingredient.of(Items.COBBLED_DEEPSLATE))
			.pattern("___")
			.pattern("# #")
			.pattern("###")
			.unlockedBy(RecipeProvider.getHasName(Items.COBBLED_DEEPSLATE), RecipeProvider.has(Items.COBBLED_DEEPSLATE))
			.save(exporter);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterItems.ECHO_GLASS)
			.requires(Items.ECHO_SHARD, 2)
			.requires(Items.TINTED_GLASS)
			.unlockedBy(RecipeProvider.getHasName(Items.ECHO_SHARD), RecipeProvider.has(Items.ECHO_SHARD))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.SANDSTONE, 2)
			.group("sandstone")
			.define('#', Ingredient.of(Items.SAND))
			.define('X', Ingredient.of(RegisterItems.SCORCHED_SAND))
			.pattern("#X")
			.pattern("X#")
			.unlockedBy(RecipeProvider.getHasName(Items.SAND), RecipeProvider.has(Items.SAND))
			.save(exporter, WilderConstants.id(RecipeProvider.getConversionRecipeName(Items.SANDSTONE, RegisterItems.SCORCHED_SAND)));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.RED_SANDSTONE, 2)
			.group("red_sandstone")
			.define('#', Ingredient.of(Items.RED_SAND))
			.define('X', Ingredient.of(RegisterItems.SCORCHED_RED_SAND))
			.pattern("#X")
			.pattern("X#")
			.unlockedBy(RecipeProvider.getHasName(Items.RED_SAND), RecipeProvider.has(Items.RED_SAND))
			.save(exporter, WilderConstants.id(RecipeProvider.getConversionRecipeName(Items.RED_SANDSTONE, RegisterItems.SCORCHED_RED_SAND)));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_MUD_BRICKS)
			.define('#', Ingredient.of(Items.MUD_BRICK_SLAB))
			.pattern("#")
			.pattern("#")
			.unlockedBy(RecipeProvider.getHasName(Items.MUD_BRICK_SLAB), RecipeProvider.has(Items.MUD_BRICK_SLAB))
			.save(exporter);

		VanillaRecipeProvider.stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Items.MUD_BRICKS, RegisterBlocks.CHISELED_MUD_BRICKS);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SPONGE)
			.define('#', RegisterBlocks.SMALL_SPONGE)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.group("sponge")
			.unlockedBy(getHasName(RegisterBlocks.SMALL_SPONGE), has(RegisterBlocks.SMALL_SPONGE))
			.save(exporter, WilderConstants.id(getConversionRecipeName(Items.SPONGE, RegisterBlocks.SMALL_SPONGE)));

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.NULL_BLOCK, 2)
			.define('#', Ingredient.of(Items.BLACK_CONCRETE))
			.define('X', Ingredient.of(Items.MAGENTA_CONCRETE))
			.pattern("#X")
			.pattern("X#")
			.unlockedBy(RecipeProvider.getHasName(Items.BLACK_CONCRETE), RecipeProvider.has(Items.BLACK_CONCRETE))
			.unlockedBy(RecipeProvider.getHasName(Items.MAGENTA_CONCRETE), RecipeProvider.has(Items.MAGENTA_CONCRETE))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlocks.GEYSER, 2)
			.define('#', Items.MAGMA_BLOCK)
			.define('X', Items.BASALT)
			.define('U', Items.LAVA_BUCKET)
			.pattern("#X#")
			.pattern("XUX")
			.pattern("#X#")
			.unlockedBy(getHasName(Items.MAGMA_BLOCK), has(Items.MAGMA_BLOCK))
			.save(exporter);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegisterItems.FERMENTED_SCORCHED_EYE)
			.requires(RegisterItems.SCORCHED_EYE)
			.requires(Items.BROWN_MUSHROOM)
			.requires(Items.SUGAR)
			.unlockedBy(RecipeProvider.getHasName(RegisterItems.SCORCHED_EYE), RecipeProvider.has(RegisterItems.SCORCHED_EYE))
			.save(exporter);
	}

	public static void stonecutterResultFromBase(Consumer<FinishedRecipe> recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
			.unlockedBy(getHasName(material), has(material))
			.save(recipeOutput, WilderConstants.id(getConversionRecipeName(result, material) + "_stonecutting"));
	}

}
