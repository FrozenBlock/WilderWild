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

package net.frozenblock.wilderwild.datagen.recipe;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.lib.recipe.api.RecipeExportNamespaceFix;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.Contract;

public final class WWRecipeProvider extends FabricRecipeProvider {

	public WWRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Contract("_, _ -> new")
	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		return new RecipeProvider(registries, output) {
			@Override
			public void buildRecipes() {
				RecipeExportNamespaceFix.setCurrentGeneratingModId(WWConstants.MOD_ID);

				HollowedLogRecipeProvider.buildRecipes(this, this.output);
				WWWoodRecipeProvider.buildRecipes(this, this.output);
				MesogleaRecipeProvider.buildRecipes(this, this.output);
				FireflyBottleRecipeProvider.buildRecipes(this, this.output, this.registries.lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR));
				WWNaturalRecipeProvider.buildRecipes(this, this.output);
				WWCookRecipeProvider.buildRecipes(this, this.output);
				BuiltInRegistries.ITEM.stream().forEach(item -> {
					final SuspiciousEffectHolder effectHolder = SuspiciousEffectHolder.tryGet(item);
					if (effectHolder == null || !item.builtInRegistryHolder().key().identifier().getNamespace().equals(WWConstants.MOD_ID)) return;
					this.suspiciousStew(item, effectHolder);
				});

				this.shaped(RecipeCategory.DECORATIONS, WWItems.DISPLAY_LANTERN)
					.define('X', Ingredient.of(Items.IRON_NUGGET))
					.define('#', Ingredient.of(Items.GLASS_PANE))
					.pattern("XXX")
					.pattern("X#X")
					.pattern("XXX")
					.unlockedBy(RecipeProvider.getHasName(Items.IRON_INGOT), this.has(Items.IRON_INGOT))
					.unlockedBy(RecipeProvider.getHasName(Items.IRON_NUGGET), this.has(Items.IRON_NUGGET))
					.save(this.output);

				this.shaped(RecipeCategory.MISC, WWItems.STONE_CHEST)
					.group("stone_chest")
					.define('_', Ingredient.of(Items.COBBLED_DEEPSLATE_SLAB))
					.define('#', Ingredient.of(Items.COBBLED_DEEPSLATE))
					.pattern("___")
					.pattern("# #")
					.pattern("###")
					.unlockedBy(RecipeProvider.getHasName(Items.COBBLED_DEEPSLATE), this.has(Items.COBBLED_DEEPSLATE))
					.save(this.output);

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, WWItems.ECHO_GLASS)
					.requires(Items.ECHO_SHARD, 2)
					.requires(Items.TINTED_GLASS)
					.unlockedBy(RecipeProvider.getHasName(Items.ECHO_SHARD), this.has(Items.ECHO_SHARD))
					.save(this.output);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, Items.SANDSTONE, 2)
					.group("sandstone")
					.define('#', Ingredient.of(Items.SAND))
					.define('X', Ingredient.of(WWItems.SCORCHED_SAND))
					.pattern("#X")
					.pattern("X#")
					.unlockedBy(RecipeProvider.getHasName(Items.SAND), this.has(Items.SAND))
					.save(this.output, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.SANDSTONE, WWItems.SCORCHED_SAND)));

				this.shaped(RecipeCategory.BUILDING_BLOCKS, Items.RED_SANDSTONE, 2)
					.group("red_sandstone")
					.define('#', Ingredient.of(Items.RED_SAND))
					.define('X', Ingredient.of(WWItems.SCORCHED_RED_SAND))
					.pattern("#X")
					.pattern("X#")
					.unlockedBy(RecipeProvider.getHasName(Items.RED_SAND), this.has(Items.RED_SAND))
					.save(this.output, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.RED_SANDSTONE, WWItems.SCORCHED_RED_SAND)));

				this.shaped(RecipeCategory.MISC, WWItems.NULL_BLOCK, 2)
					.define('#', Ingredient.of(Items.CONCRETE.black()))
					.define('X', Ingredient.of(Items.CONCRETE.magenta()))
					.pattern("#X")
					.pattern("X#")
					.unlockedBy(RecipeProvider.getHasName(Items.CONCRETE.black()), this.has(Items.CONCRETE.black()))
					.unlockedBy(RecipeProvider.getHasName(Items.CONCRETE.magenta()), this.has(Items.CONCRETE.magenta()))
					.save(this.output);

				this.shaped(RecipeCategory.REDSTONE, WWItems.GEYSER, 2)
					.define('#', Items.MAGMA_BLOCK)
					.define('X', WWItems.GABBRO)
					.define('U', Items.LAVA_BUCKET)
					.pattern("#X#")
					.pattern("XUX")
					.pattern("#X#")
					.unlockedBy(getHasName(Items.MAGMA_BLOCK), this.has(Items.MAGMA_BLOCK))
					.unlockedBy(getHasName(WWItems.GABBRO), this.has(WWItems.GABBRO))
					.unlockedBy(getHasName(WWItems.GEYSER), this.has(WWItems.GEYSER))
					.save(this.output);

				this.shapeless(RecipeCategory.MISC, WWItems.FERMENTED_SCORCHED_EYE)
					.requires(WWItems.SCORCHED_EYE)
					.requires(Items.BROWN_MUSHROOM)
					.requires(Items.SUGAR)
					.unlockedBy(RecipeProvider.getHasName(WWItems.SCORCHED_EYE), this.has(WWItems.SCORCHED_EYE))
					.save(this.output);

				this.shapeless(RecipeCategory.MISC, WWItems.SCORCHED_EYE)
					.requires(Items.SPIDER_EYE)
					.requires(Items.BLAZE_POWDER)
					.unlockedBy(RecipeProvider.getHasName(Items.BLAZE_POWDER), this.has(Items.BLAZE_POWDER))
					.unlockedBy(RecipeProvider.getHasName(WWItems.SCORCHED_EYE), this.has(WWItems.SCORCHED_EYE))
					.save(this.output);

				this.shaped(RecipeCategory.MISC, Items.SPONGE)
					.define('#', WWItems.SPONGE_BUD)
					.pattern("###")
					.pattern("###")
					.pattern("###")
					.group("sponge")
					.unlockedBy(getHasName(WWItems.SPONGE_BUD), this.has(WWItems.SPONGE_BUD))
					.save(this.output, WWConstants.string(getConversionRecipeName(Items.SPONGE, WWItems.SPONGE_BUD)));

				// ICE

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.ICE), RecipeCategory.DECORATIONS, CookingBookCategory.BLOCKS, WWItems.FRAGILE_ICE.asItem(), 0.05F, 100)
					.unlockedBy("has_ice", this.has(Items.ICE))
					.save(this.output);

				// MUD BRICKS

				SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.MUD_BRICKS), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, WWItems.CRACKED_MUD_BRICKS.asItem(), 0.1F, 200)
					.unlockedBy("has_mud_bricks", this.has(Items.MUD_BRICKS))
					.save(this.output);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, WWItems.CHISELED_MUD_BRICKS)
					.define('#', Ingredient.of(Items.MUD_BRICK_SLAB))
					.pattern("#")
					.pattern("#")
					.unlockedBy(RecipeProvider.getHasName(Items.MUD_BRICKS), this.has(Items.MUD_BRICKS))
					.unlockedBy(RecipeProvider.getHasName(Items.MUD_BRICK_SLAB), this.has(Items.MUD_BRICK_SLAB))
					.unlockedBy(RecipeProvider.getHasName(WWItems.CHISELED_MUD_BRICKS), this.has(WWItems.CHISELED_MUD_BRICKS))
					.save(this.output);

				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.CHISELED_MUD_BRICKS, Items.MUD_BRICKS);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.CHISELED_MUD_BRICKS, Items.PACKED_MUD);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Items.MUD_BRICKS, Items.PACKED_MUD);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Items.MUD_BRICK_SLAB, Items.PACKED_MUD, 2);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Items.MUD_BRICK_STAIRS, Items.PACKED_MUD);
				this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Items.MUD_BRICK_WALL, Items.PACKED_MUD);

				// MOSSY MUD BRICKS

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_MUD_BRICKS)
					.requires(Items.MUD_BRICKS)
					.requires(Items.VINE)
					.group("mossy_mud_bricks")
					.unlockedBy("has_vine", this.has(Items.VINE))
					.save(this.output, getConversionRecipeName(WWItems.MOSSY_MUD_BRICKS, Items.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_MUD_BRICKS)
					.requires(Items.MUD_BRICKS)
					.requires(Items.MOSS_BLOCK)
					.group("mossy_mud_bricks")
					.unlockedBy("has_moss_block", this.has(Items.MOSS_BLOCK))
					.save(this.output, getConversionRecipeName(WWItems.MOSSY_MUD_BRICKS, Items.MOSS_BLOCK));

				stairBuilder(WWItems.MOSSY_MUD_BRICK_STAIRS, Ingredient.of(WWItems.MOSSY_MUD_BRICKS))
					.unlockedBy(getHasName(WWItems.MOSSY_MUD_BRICKS), this.has(WWItems.MOSSY_MUD_BRICKS))
					.save(this.output);

				slab(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_MUD_BRICK_SLAB, WWItems.MOSSY_MUD_BRICKS);

				wall(RecipeCategory.MISC, WWItems.MOSSY_MUD_BRICK_WALL, WWItems.MOSSY_MUD_BRICKS);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_MUD_BRICK_SLAB, WWItems.MOSSY_MUD_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_MUD_BRICK_STAIRS, WWItems.MOSSY_MUD_BRICKS);
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, WWItems.MOSSY_MUD_BRICK_WALL, WWItems.MOSSY_MUD_BRICKS);

				// GABBRO

				this.generateRecipes(WWBlocks.FAMILY_POLISHED_GABBRO, FeatureFlags.VANILLA_SET);
				this.generateRecipes(WWBlocks.FAMILY_GABBRO_BRICK, FeatureFlags.VANILLA_SET);

				this.generateRecipes(WWBlocks.FAMILY_GABBRO, WWFeatureFlags.TRAILIER_TALES_COMPAT_FLAG_SET);
				this.generateRecipes(WWBlocks.FAMILY_MOSSY_GABBRO_BRICK, WWFeatureFlags.TRAILIER_TALES_COMPAT_FLAG_SET);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_SLAB, WWItems.GABBRO, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_STAIRS, WWItems.GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_WALL, WWItems.GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.POLISHED_GABBRO, WWItems.GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.POLISHED_GABBRO_SLAB, WWItems.GABBRO, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.POLISHED_GABBRO_STAIRS, WWItems.GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.POLISHED_GABBRO_WALL, WWItems.GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.POLISHED_GABBRO_SLAB, WWItems.POLISHED_GABBRO, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.POLISHED_GABBRO_STAIRS, WWItems.POLISHED_GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.POLISHED_GABBRO_WALL, WWItems.POLISHED_GABBRO);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, WWItems.POLISHED_GABBRO, 4)
					.define('#', WWItems.GABBRO)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_gabbro", this.has(WWItems.GABBRO))
					.save(this.output);

				this.shaped(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICKS, 4)
					.define('#', WWItems.POLISHED_GABBRO)
					.pattern("##")
					.pattern("##")
					.unlockedBy("has_polished_gabbro", this.has(WWItems.POLISHED_GABBRO))
					.save(this.output);

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.CHISELED_GABBRO_BRICKS, WWItems.GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.CHISELED_GABBRO_BRICKS, WWItems.POLISHED_GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.CHISELED_GABBRO_BRICKS, WWItems.GABBRO_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICKS, WWItems.GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICKS, WWItems.POLISHED_GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_SLAB, WWItems.GABBRO_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_SLAB, WWItems.POLISHED_GABBRO, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_SLAB, WWItems.GABBRO, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_STAIRS, WWItems.GABBRO_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_STAIRS, WWItems.POLISHED_GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_STAIRS, WWItems.GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_WALL, WWItems.GABBRO_BRICKS);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_WALL, WWItems.POLISHED_GABBRO);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.GABBRO_BRICK_WALL, WWItems.GABBRO);

				// MOSSY GABBRO

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_GABBRO_BRICKS)
					.requires(WWItems.GABBRO_BRICKS)
					.requires(Items.VINE)
					.group("mossy_gabbro_bricks")
					.unlockedBy("has_vine", this.has(Items.VINE))
					.save(this.output, getConversionRecipeName(WWItems.MOSSY_GABBRO_BRICKS, Items.VINE));

				this.shapeless(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_GABBRO_BRICKS)
					.requires(WWItems.GABBRO_BRICKS)
					.requires(Items.MOSS_BLOCK)
					.group("mossy_gabbro_bricks")
					.unlockedBy("has_moss_block", this.has(Items.MOSS_BLOCK))
					.save(this.output, getConversionRecipeName(WWItems.MOSSY_GABBRO_BRICKS, Items.MOSS_BLOCK));

				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_GABBRO_BRICK_SLAB, WWItems.MOSSY_GABBRO_BRICKS, 2);
				stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, WWItems.MOSSY_GABBRO_BRICK_STAIRS, WWItems.MOSSY_GABBRO_BRICKS);
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, WWItems.MOSSY_GABBRO_BRICK_WALL, WWItems.MOSSY_GABBRO_BRICKS);

				RecipeExportNamespaceFix.clearCurrentGeneratingModId();
			}
		};
	}

	@Override
	public String getName() {
		return "Wilder Wild Recipes";
	}
}
