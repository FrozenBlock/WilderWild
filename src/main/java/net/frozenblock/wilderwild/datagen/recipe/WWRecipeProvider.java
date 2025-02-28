/*
 * Copyright 2024-2025 FrozenBlock
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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public final class WWRecipeProvider extends FabricRecipeProvider {
	public static boolean GENERATING_WW_RECIPES = false;

	public WWRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void buildRecipes(RecipeOutput exporter) {
		GENERATING_WW_RECIPES = true;
		HollowedLogRecipeProvider.buildRecipes(exporter);
		WWWoodRecipeProvider.buildRecipes(exporter);
		MesogleaRecipeProvider.buildRecipes(exporter);
		FireflyBottleRecipeProvider.buildRecipes(exporter);
		CopperHornRecipeProvider.buildRecipes(exporter);
		WWNaturalRecipeProvider.buildRecipes(exporter);
		WWCookRecipeProvider.buildRecipes(exporter);

		ShapedRecipeUtil.withResultPatch(
			ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, WWBlocks.DISPLAY_LANTERN)
				.define('X', Ingredient.of(Items.IRON_NUGGET))
				.define('#', Ingredient.of(Items.GLASS_PANE))
				.pattern("XXX")
				.pattern("X#X")
				.pattern("XXX")
				.unlockedBy(RecipeProvider.getHasName(Items.IRON_INGOT), RecipeProvider.has(Items.IRON_INGOT))
				.unlockedBy(RecipeProvider.getHasName(Items.IRON_NUGGET), RecipeProvider.has(Items.IRON_NUGGET)),
			DataComponentPatch.builder()
				.set(WWDataComponents.FIREFLIES, ImmutableList.of())
				.build()
		).save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WWBlocks.STONE_CHEST)
			.group("stone_chest")
			.define('_', Ingredient.of(Items.COBBLED_DEEPSLATE_SLAB))
			.define('#', Ingredient.of(Items.COBBLED_DEEPSLATE))
			.pattern("___")
			.pattern("# #")
			.pattern("###")
			.unlockedBy(RecipeProvider.getHasName(Items.COBBLED_DEEPSLATE), RecipeProvider.has(Items.COBBLED_DEEPSLATE))
			.save(exporter);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WWItems.ECHO_GLASS)
			.requires(Items.ECHO_SHARD, 2)
			.requires(Items.TINTED_GLASS)
			.unlockedBy(RecipeProvider.getHasName(Items.ECHO_SHARD), RecipeProvider.has(Items.ECHO_SHARD))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.SANDSTONE, 2)
			.group("sandstone")
			.define('#', Ingredient.of(Items.SAND))
			.define('X', Ingredient.of(WWItems.SCORCHED_SAND))
			.pattern("#X")
			.pattern("X#")
			.unlockedBy(RecipeProvider.getHasName(Items.SAND), RecipeProvider.has(Items.SAND))
			.save(exporter, WWConstants.id(RecipeProvider.getConversionRecipeName(Items.SANDSTONE, WWItems.SCORCHED_SAND)));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.RED_SANDSTONE, 2)
			.group("red_sandstone")
			.define('#', Ingredient.of(Items.RED_SAND))
			.define('X', Ingredient.of(WWItems.SCORCHED_RED_SAND))
			.pattern("#X")
			.pattern("X#")
			.unlockedBy(RecipeProvider.getHasName(Items.RED_SAND), RecipeProvider.has(Items.RED_SAND))
			.save(exporter, WWConstants.id(RecipeProvider.getConversionRecipeName(Items.RED_SANDSTONE, WWItems.SCORCHED_RED_SAND)));

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WWBlocks.NULL_BLOCK, 2)
			.define('#', Ingredient.of(Items.BLACK_CONCRETE))
			.define('X', Ingredient.of(Items.MAGENTA_CONCRETE))
			.pattern("#X")
			.pattern("X#")
			.unlockedBy(RecipeProvider.getHasName(Items.BLACK_CONCRETE), RecipeProvider.has(Items.BLACK_CONCRETE))
			.unlockedBy(RecipeProvider.getHasName(Items.MAGENTA_CONCRETE), RecipeProvider.has(Items.MAGENTA_CONCRETE))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, WWBlocks.GEYSER, 2)
			.define('#', Items.MAGMA_BLOCK)
			.define('X', Items.BASALT)
			.define('U', Items.LAVA_BUCKET)
			.pattern("#X#")
			.pattern("XUX")
			.pattern("#X#")
			.unlockedBy(getHasName(Items.MAGMA_BLOCK), has(Items.MAGMA_BLOCK))
			.save(exporter);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WWItems.FERMENTED_SCORCHED_EYE)
			.requires(WWItems.SCORCHED_EYE)
			.requires(Items.BROWN_MUSHROOM)
			.requires(Items.SUGAR)
			.unlockedBy(RecipeProvider.getHasName(WWItems.SCORCHED_EYE), RecipeProvider.has(WWItems.SCORCHED_EYE))
			.save(exporter);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WWItems.SCORCHED_EYE)
			.requires(Items.SPIDER_EYE)
			.requires(Items.BLAZE_POWDER)
			.unlockedBy(RecipeProvider.getHasName(Items.BLAZE_POWDER), RecipeProvider.has(Items.BLAZE_POWDER))
			.unlockedBy(RecipeProvider.getHasName(WWItems.SCORCHED_EYE), RecipeProvider.has(WWItems.SCORCHED_EYE))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SPONGE)
			.define('#', WWBlocks.SPONGE_BUD)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.group("sponge")
			.unlockedBy(getHasName(WWBlocks.SPONGE_BUD), has(WWBlocks.SPONGE_BUD))
			.save(exporter, WWConstants.id(getConversionRecipeName(Items.SPONGE, WWBlocks.SPONGE_BUD)));

		// ICE

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.ICE), RecipeCategory.DECORATIONS, WWBlocks.FRAGILE_ICE.asItem(), 0.05F, 100)
			.unlockedBy("has_ice", has(Blocks.ICE))
			.save(exporter);

		// MUD BRICKS

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.MUD_BRICKS), RecipeCategory.BUILDING_BLOCKS, WWBlocks.CRACKED_MUD_BRICKS.asItem(), 0.1F, 200)
			.unlockedBy("has_mud_bricks", has(Blocks.MUD_BRICKS))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WWBlocks.CHISELED_MUD_BRICKS)
			.define('#', Ingredient.of(Items.MUD_BRICK_SLAB))
			.pattern("#")
			.pattern("#")
			.unlockedBy(RecipeProvider.getHasName(Items.MUD_BRICKS), RecipeProvider.has(Items.MUD_BRICKS))
			.unlockedBy(RecipeProvider.getHasName(Items.MUD_BRICK_SLAB), RecipeProvider.has(Items.MUD_BRICK_SLAB))
			.unlockedBy(RecipeProvider.getHasName(WWBlocks.CHISELED_MUD_BRICKS), RecipeProvider.has(WWBlocks.CHISELED_MUD_BRICKS))
			.save(exporter);

		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.CHISELED_MUD_BRICKS, Blocks.PACKED_MUD);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.CHISELED_MUD_BRICKS, Blocks.MUD_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICKS, Blocks.PACKED_MUD);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_SLAB, Blocks.MUD_BRICKS, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_SLAB, Blocks.PACKED_MUD, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_STAIRS, Blocks.MUD_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_STAIRS, Blocks.PACKED_MUD);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_WALL, Blocks.MUD_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.MUD_BRICK_WALL, Blocks.PACKED_MUD);

		// MOSSY MUD BRICKS

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_MUD_BRICKS)
			.requires(Blocks.MUD_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_mud_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(exporter, getConversionRecipeName(WWBlocks.MOSSY_MUD_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_MUD_BRICKS)
			.requires(Blocks.MUD_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_mud_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(exporter, getConversionRecipeName(WWBlocks.MOSSY_MUD_BRICKS, Blocks.MOSS_BLOCK));

		stairBuilder(
			WWBlocks.MOSSY_MUD_BRICK_STAIRS,
			Ingredient.of(WWBlocks.MOSSY_MUD_BRICKS)
		).unlockedBy(getHasName(WWBlocks.MOSSY_MUD_BRICKS), has(WWBlocks.MOSSY_MUD_BRICKS)).save(exporter);

		slab(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_MUD_BRICK_SLAB, WWBlocks.MOSSY_MUD_BRICKS);

		wall(exporter, RecipeCategory.MISC, WWBlocks.MOSSY_MUD_BRICK_WALL, WWBlocks.MOSSY_MUD_BRICKS);

		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_MUD_BRICK_SLAB, WWBlocks.MOSSY_MUD_BRICKS, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_MUD_BRICK_STAIRS, WWBlocks.MOSSY_MUD_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, WWBlocks.MOSSY_MUD_BRICK_WALL, WWBlocks.MOSSY_MUD_BRICKS);

		// GABBRO

		RecipeProvider.generateRecipes(exporter, WWBlocks.FAMILY_POLISHED_GABBRO, FeatureFlags.VANILLA_SET);
		RecipeProvider.generateRecipes(exporter, WWBlocks.FAMILY_GABBRO_BRICK, FeatureFlags.VANILLA_SET);

		RecipeProvider.generateRecipes(exporter, WWBlocks.FAMILY_GABBRO, WWFeatureFlags.TRAILIER_TALES_COMPAT_FLAG_SET);
		RecipeProvider.generateRecipes(exporter, WWBlocks.FAMILY_MOSSY_GABBRO_BRICK, WWFeatureFlags.TRAILIER_TALES_COMPAT_FLAG_SET);

		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_SLAB, WWBlocks.GABBRO, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_STAIRS, WWBlocks.GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_WALL, WWBlocks.GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.POLISHED_GABBRO, WWBlocks.GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.POLISHED_GABBRO_SLAB, WWBlocks.GABBRO, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.POLISHED_GABBRO_STAIRS, WWBlocks.GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.POLISHED_GABBRO_WALL, WWBlocks.GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.POLISHED_GABBRO_WALL, WWBlocks.POLISHED_GABBRO);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICKS, 4)
			.define('#', WWBlocks.GABBRO)
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_gabbro", has(WWBlocks.GABBRO))
			.save(exporter);

		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.CHISELED_GABBRO_BRICKS, WWBlocks.GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.CHISELED_GABBRO_BRICKS, WWBlocks.POLISHED_GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.CHISELED_GABBRO_BRICKS, WWBlocks.GABBRO_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICKS, WWBlocks.GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICKS, WWBlocks.POLISHED_GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICKS, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_SLAB, WWBlocks.POLISHED_GABBRO, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_SLAB, WWBlocks.GABBRO, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_STAIRS, WWBlocks.POLISHED_GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_WALL, WWBlocks.GABBRO_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_WALL, WWBlocks.POLISHED_GABBRO);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.GABBRO_BRICK_WALL, WWBlocks.GABBRO);

		// MOSSY GABBRO

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_GABBRO_BRICKS)
			.requires(WWBlocks.GABBRO_BRICKS)
			.requires(Blocks.VINE)
			.group("mossy_gabbro_bricks")
			.unlockedBy("has_vine", has(Blocks.VINE))
			.save(exporter, getConversionRecipeName(WWBlocks.MOSSY_GABBRO_BRICKS, Blocks.VINE));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_GABBRO_BRICKS)
			.requires(WWBlocks.GABBRO_BRICKS)
			.requires(Blocks.MOSS_BLOCK)
			.group("mossy_gabbro_bricks")
			.unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
			.save(exporter, getConversionRecipeName(WWBlocks.MOSSY_GABBRO_BRICKS, Blocks.MOSS_BLOCK));

		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICKS, 2);
		stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, WWBlocks.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICKS);
		stonecutterResultFromBase(exporter, RecipeCategory.DECORATIONS, WWBlocks.MOSSY_GABBRO_BRICK_WALL, WWBlocks.MOSSY_GABBRO_BRICKS);

		GENERATING_WW_RECIPES = false;
	}

	public static void stonecutterResultFromBase(RecipeOutput exporter, RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
			.unlockedBy(getHasName(material), has(material))
			.save(exporter, WWConstants.id(getConversionRecipeName(result, material) + "_stonecutting"));
	}

}
