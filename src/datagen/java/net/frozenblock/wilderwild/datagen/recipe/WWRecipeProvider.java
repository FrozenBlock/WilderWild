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
import net.frozenblock.wilderwild.WilderConstants;
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
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

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
		WWNaturalRecipeProvider.buildRecipes(exporter);
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
				.unlockedBy(RecipeProvider.getHasName(RegisterItems.ANCIENT_HORN_FRAGMENT), RecipeProvider.has(RegisterItems.ANCIENT_HORN_FRAGMENT)),
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
				.unlockedBy(RecipeProvider.getHasName(Items.IRON_INGOT), RecipeProvider.has(Items.IRON_INGOT))
				.unlockedBy(RecipeProvider.getHasName(Items.IRON_NUGGET), RecipeProvider.has(Items.IRON_NUGGET)),
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

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegisterItems.SCORCHED_EYE)
			.requires(Items.SPIDER_EYE)
			.requires(Items.BLAZE_POWDER)
			.unlockedBy(RecipeProvider.getHasName(Items.BLAZE_POWDER), RecipeProvider.has(Items.BLAZE_POWDER))
			.unlockedBy(RecipeProvider.getHasName(RegisterItems.SCORCHED_EYE), RecipeProvider.has(RegisterItems.SCORCHED_EYE))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SPONGE)
			.define('#', RegisterBlocks.SPONGE_BUD)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.group("sponge")
			.unlockedBy(getHasName(RegisterBlocks.SPONGE_BUD), has(RegisterBlocks.SPONGE_BUD))
			.save(exporter, WilderConstants.id(getConversionRecipeName(Items.SPONGE, RegisterBlocks.SPONGE_BUD)));

		// MUD BRICKS

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.MUD_BRICKS), RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CRACKED_MUD_BRICKS.asItem(), 0.1F, 200)
			.unlockedBy("has_mud_bricks", has(Blocks.MUD_BRICKS))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_MUD_BRICKS)
			.define('#', Ingredient.of(Items.MUD_BRICK_SLAB))
			.pattern("#")
			.pattern("#")
			.unlockedBy(RecipeProvider.getHasName(Items.MUD_BRICKS), RecipeProvider.has(Items.MUD_BRICKS))
			.unlockedBy(RecipeProvider.getHasName(Items.MUD_BRICK_SLAB), RecipeProvider.has(Items.MUD_BRICK_SLAB))
			.unlockedBy(RecipeProvider.getHasName(RegisterBlocks.CHISELED_MUD_BRICKS), RecipeProvider.has(RegisterBlocks.CHISELED_MUD_BRICKS))
			.save(exporter);

		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_MUD_BRICKS, Blocks.PACKED_MUD);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_MUD_BRICKS, Blocks.MUD_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICKS, Blocks.PACKED_MUD);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_SLAB, Blocks.MUD_BRICKS, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_SLAB, Blocks.PACKED_MUD, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_STAIRS, Blocks.MUD_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_STAIRS, Blocks.PACKED_MUD);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_WALL, Blocks.MUD_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_WALL, Blocks.PACKED_MUD);

		// MOSSY MUD BRICKS

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_MUD_BRICKS)
			.requires(Blocks.MUD_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_mud_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(exporter, getConversionRecipeName(RegisterBlocks.MOSSY_MUD_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_MUD_BRICKS)
			.requires(Blocks.MUD_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_mud_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(exporter, getConversionRecipeName(RegisterBlocks.MOSSY_MUD_BRICKS, Blocks.MOSS_BLOCK));

		stairBuilder(
			RegisterBlocks.MOSSY_MUD_BRICK_STAIRS,
			Ingredient.of(RegisterBlocks.MOSSY_MUD_BRICKS)
		).unlockedBy(getHasName(RegisterBlocks.MOSSY_MUD_BRICKS), has(RegisterBlocks.MOSSY_MUD_BRICKS)).save(exporter);

		slab(exporter, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_MUD_BRICK_SLAB, RegisterBlocks.MOSSY_MUD_BRICKS);

		wall(exporter, RecipeCategory.MISC, RegisterBlocks.MOSSY_MUD_BRICK_WALL, RegisterBlocks.MOSSY_MUD_BRICKS);

		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_MUD_BRICK_SLAB, RegisterBlocks.MOSSY_MUD_BRICKS, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MOSSY_MUD_BRICK_STAIRS, RegisterBlocks.MOSSY_MUD_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, RegisterBlocks.MOSSY_MUD_BRICK_WALL, RegisterBlocks.MOSSY_MUD_BRICKS);
	}

	public static void stonecutterResultFromBase(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
			.unlockedBy(getHasName(material), has(material))
			.save(recipeOutput, WilderConstants.id(getConversionRecipeName(result, material) + "_stonecutting"));
	}

}
