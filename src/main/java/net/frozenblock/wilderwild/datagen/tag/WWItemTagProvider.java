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

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public final class WWItemTagProvider extends FabricTagProvider.ItemTagProvider {

	public WWItemTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@NotNull
	private TagKey<Item> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(ItemTags.STAIRS)
			.add(WWBlocks.SCULK_STAIRS.asItem())
			.add(WWBlocks.MOSSY_MUD_BRICK_STAIRS.asItem())
			.add(WWBlocks.GABBRO_STAIRS.asItem())
			.add(WWBlocks.POLISHED_GABBRO_STAIRS.asItem())
			.add(WWBlocks.GABBRO_BRICK_STAIRS.asItem())
			.add(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS.asItem());

		this.getOrCreateTagBuilder(ItemTags.SLABS)
			.add(WWBlocks.SCULK_SLAB.asItem())
			.add(WWBlocks.MOSSY_MUD_BRICK_SLAB.asItem())
			.add(WWBlocks.GABBRO_SLAB.asItem())
			.add(WWBlocks.POLISHED_GABBRO_SLAB.asItem())
			.add(WWBlocks.GABBRO_BRICK_SLAB.asItem())
			.add(WWBlocks.MOSSY_GABBRO_BRICK_SLAB.asItem());

		this.getOrCreateTagBuilder(ItemTags.WALLS)
			.add(WWBlocks.SCULK_WALL.asItem())
			.add(WWBlocks.MOSSY_MUD_BRICK_WALL.asItem())
			.add(WWBlocks.GABBRO_WALL.asItem())
			.add(WWBlocks.POLISHED_GABBRO_WALL.asItem())
			.add(WWBlocks.GABBRO_BRICK_WALL.asItem())
			.add(WWBlocks.MOSSY_GABBRO_BRICK_WALL.asItem());

		this.getOrCreateTagBuilder(ItemTags.ARMADILLO_FOOD)
			.add(WWItems.SCORCHED_EYE);

		this.getOrCreateTagBuilder(WWItemTags.BROWN_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.BROWN_MUSHROOM)
			.addOptional(WWConstants.id("brown_shelf_fungus"));

		this.getOrCreateTagBuilder(WWItemTags.RED_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.RED_MUSHROOM)
			.addOptional(WWConstants.id("red_shelf_fungus"));

		this.getOrCreateTagBuilder(WWItemTags.TUMBLEWEED_COMMON)
			.add(Items.ROTTEN_FLESH)
			.add(Items.BONE_MEAL)
			.add(Items.WHEAT_SEEDS)
			.add(Items.STICK)
			.add(Items.DEAD_BUSH);

		this.getOrCreateTagBuilder(WWItemTags.TUMBLEWEED_MEDIUM)
			.add(Items.BONE)
			.add(Items.GOLD_NUGGET)
			.add(Items.WHEAT_SEEDS)
			.add(Items.POTATO)
			.add(Items.CARROT)
			.add(Items.BEETROOT_SEEDS)
			.add(Items.STRING);

		this.getOrCreateTagBuilder(WWItemTags.TUMBLEWEED_RARE)
			.add(Items.IRON_NUGGET)
			.add(Items.POTATO)
			.add(Items.CARROT)
			.add(Items.MELON_SEEDS)
			.add(Items.PUMPKIN_SEEDS);

		this.getOrCreateTagBuilder(WWItemTags.MESOGLEA)
			.add(WWBlocks.BLUE_MESOGLEA.asItem())
			.add(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.asItem())
			.add(WWBlocks.LIME_MESOGLEA.asItem())
			.add(WWBlocks.PINK_MESOGLEA.asItem())
			.add(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.asItem())
			.add(WWBlocks.RED_MESOGLEA.asItem())
			.add(WWBlocks.YELLOW_MESOGLEA.asItem());

		this.getOrCreateTagBuilder(WWItemTags.NEMATOCYSTS)
			.add(WWBlocks.BLUE_NEMATOCYST.asItem())
			.add(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST.asItem())
			.add(WWBlocks.LIME_NEMATOCYST.asItem())
			.add(WWBlocks.PINK_NEMATOCYST.asItem())
			.add(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST.asItem())
			.add(WWBlocks.RED_NEMATOCYST.asItem())
			.add(WWBlocks.YELLOW_NEMATOCYST.asItem());

		this.getOrCreateTagBuilder(WWItemTags.PEARLESCENT_NEMATOCYSTS)
			.add(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST.asItem())
			.add(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST.asItem());

		this.getOrCreateTagBuilder(WWItemTags.JELLYFISH_FOOD)
			.add(Items.COD)
			.add(Items.SALMON);

		this.getOrCreateTagBuilder(WWItemTags.PEARLESCENT_JELLYFISH_FOOD)
			.add(Items.COD)
			.add(Items.SALMON);

		this.getOrCreateTagBuilder(WWItemTags.CRAB_FOOD)
			.add(Items.KELP);

		this.getOrCreateTagBuilder(WWItemTags.OSTRICH_FOOD)
			.add(WWBlocks.BUSH.asItem());

		this.getOrCreateTagBuilder(WWItemTags.PENGUIN_FOOD)
			.add(Items.INK_SAC)
			.add(Items.GLOW_INK_SAC);

		this.getOrCreateTagBuilder(ItemTags.BOATS)
			.add(WWItems.BAOBAB_BOAT)
			.add(WWItems.WILLOW_BOAT)
			.add(WWItems.CYPRESS_BOAT)
			.add(WWItems.PALM_BOAT)
			.add(WWItems.MAPLE_BOAT);

		this.getOrCreateTagBuilder(ItemTags.CHEST_BOATS)
			.add(WWItems.BAOBAB_CHEST_BOAT)
			.add(WWItems.WILLOW_CHEST_BOAT)
			.add(WWItems.CYPRESS_CHEST_BOAT)
			.add(WWItems.PALM_CHEST_BOAT)
			.add(WWItems.MAPLE_CHEST_BOAT);

		this.getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
			.add(WWBlocks.CARNATION.asItem())
			.add(WWBlocks.MARIGOLD.asItem())
			.add(WWBlocks.SEEDING_DANDELION.asItem())
			.add(WWBlocks.PASQUEFLOWER.asItem())
			.add(WWBlocks.RED_HIBISCUS.asItem())
			.add(WWBlocks.YELLOW_HIBISCUS.asItem())
			.add(WWBlocks.WHITE_HIBISCUS.asItem())
			.add(WWBlocks.PINK_HIBISCUS.asItem())
			.add(WWBlocks.PURPLE_HIBISCUS.asItem())
			.add(WWBlocks.FLOWERING_LILY_PAD.asItem());

		this.getOrCreateTagBuilder(ItemTags.BEE_FOOD)
			.add(WWBlocks.CARNATION.asItem())
			.add(WWBlocks.MARIGOLD.asItem())
			.add(WWBlocks.SEEDING_DANDELION.asItem())
			.add(WWBlocks.RED_HIBISCUS.asItem())
			.add(WWBlocks.YELLOW_HIBISCUS.asItem())
			.add(WWBlocks.WHITE_HIBISCUS.asItem())
			.add(WWBlocks.PINK_HIBISCUS.asItem())
			.add(WWBlocks.PURPLE_HIBISCUS.asItem())
			.add(WWBlocks.FLOWERING_LILY_PAD.asItem())
			.add(WWBlocks.DATURA.asItem())
			.add(WWBlocks.CATTAIL.asItem())
			.add(WWBlocks.MILKWEED.asItem())
			.add(WWBlocks.POLLEN.asItem())
			.add(WWBlocks.WILDFLOWERS.asItem())
			.add(WWBlocks.PHLOX.asItem())
			.add(WWBlocks.LANTANAS.asItem());

		this.getOrCreateTagBuilder(ItemTags.ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS);

		this.getOrCreateTagBuilder(ItemTags.BIRCH_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_BIRCH_LOGS);

		this.getOrCreateTagBuilder(ItemTags.CHERRY_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CHERRY_LOGS);

		this.getOrCreateTagBuilder(ItemTags.CRIMSON_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_CRIMSON_STEMS);

		this.getOrCreateTagBuilder(ItemTags.DARK_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_DARK_OAK_LOGS);

		this.getOrCreateTagBuilder(ItemTags.JUNGLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_JUNGLE_LOGS);

		this.getOrCreateTagBuilder(ItemTags.ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS);

		this.getOrCreateTagBuilder(ItemTags.MANGROVE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_MANGROVE_LOGS);

		this.getOrCreateTagBuilder(ItemTags.OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_OAK_LOGS);

		this.getOrCreateTagBuilder(ItemTags.ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS);

		this.getOrCreateTagBuilder(ItemTags.SPRUCE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_SPRUCE_LOGS);

		this.getOrCreateTagBuilder(ItemTags.WARPED_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_WARPED_STEMS);

		this.getOrCreateTagBuilder(WWItemTags.BAOBAB_LOGS)
			.add(WWBlocks.BAOBAB_LOG.asItem())
			.add(WWBlocks.STRIPPED_BAOBAB_LOG.asItem())
			.add(WWBlocks.BAOBAB_WOOD.asItem())
			.add(WWBlocks.STRIPPED_BAOBAB_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_BAOBAB_LOGS);

		this.getOrCreateTagBuilder(WWItemTags.WILLOW_LOGS)
			.add(WWBlocks.WILLOW_LOG.asItem())
			.add(WWBlocks.STRIPPED_WILLOW_LOG.asItem())
			.add(WWBlocks.WILLOW_WOOD.asItem())
			.add(WWBlocks.STRIPPED_WILLOW_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_WILLOW_LOGS);

		this.getOrCreateTagBuilder(WWItemTags.CYPRESS_LOGS)
			.add(WWBlocks.CYPRESS_LOG.asItem())
			.add(WWBlocks.STRIPPED_CYPRESS_LOG.asItem())
			.add(WWBlocks.CYPRESS_WOOD.asItem())
			.add(WWBlocks.STRIPPED_CYPRESS_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_CYPRESS_LOGS);

		this.getOrCreateTagBuilder(WWItemTags.PALM_LOGS)
			.add(WWBlocks.PALM_LOG.asItem())
			.add(WWBlocks.STRIPPED_PALM_LOG.asItem())
			.add(WWBlocks.PALM_WOOD.asItem())
			.add(WWBlocks.STRIPPED_PALM_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_PALM_LOGS);

		this.getOrCreateTagBuilder(WWItemTags.MAPLE_LOGS)
			.add(WWBlocks.MAPLE_LOG.asItem())
			.add(WWBlocks.STRIPPED_MAPLE_LOG.asItem())
			.add(WWBlocks.MAPLE_WOOD.asItem())
			.add(WWBlocks.STRIPPED_MAPLE_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_MAPLE_LOGS);

		this.getOrCreateTagBuilder(ItemTags.LEAVES)
			.add(WWBlocks.BAOBAB_LEAVES.asItem())
			.add(WWBlocks.WILLOW_LEAVES.asItem())
			.add(WWBlocks.CYPRESS_LEAVES.asItem())
			.add(WWBlocks.PALM_FRONDS.asItem())
			.add(WWBlocks.YELLOW_MAPLE_LEAVES.asItem())
			.add(WWBlocks.ORANGE_MAPLE_LEAVES.asItem())
			.add(WWBlocks.RED_MAPLE_LEAVES.asItem());

		this.getOrCreateTagBuilder(ConventionalItemTags.STRIPPED_LOGS)
			.add(WWBlocks.STRIPPED_BAOBAB_LOG.asItem())
			.add(WWBlocks.STRIPPED_WILLOW_LOG.asItem())
			.add(WWBlocks.STRIPPED_CYPRESS_LOG.asItem())
			.add(WWBlocks.STRIPPED_PALM_LOG.asItem())
			.add(WWBlocks.STRIPPED_MAPLE_LOG.asItem());

		this.getOrCreateTagBuilder(ConventionalItemTags.STRIPPED_WOODS)
			.add(WWBlocks.STRIPPED_BAOBAB_WOOD.asItem())
			.add(WWBlocks.STRIPPED_WILLOW_WOOD.asItem())
			.add(WWBlocks.STRIPPED_CYPRESS_WOOD.asItem())
			.add(WWBlocks.STRIPPED_PALM_WOOD.asItem())
			.add(WWBlocks.STRIPPED_MAPLE_WOOD.asItem());

		this.getOrCreateTagBuilder(ItemTags.LOGS)
			.addOptionalTag(WWItemTags.BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.WILLOW_LOGS)
			.addOptionalTag(WWItemTags.CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.PALM_LOGS)
			.addOptionalTag(WWItemTags.MAPLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS);

		this.getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.WILLOW_LOGS)
			.addOptionalTag(WWItemTags.CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.PALM_LOGS)
			.addOptionalTag(WWItemTags.MAPLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_THAT_BURN);

		this.getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_DONT_BURN);

		this.getOrCreateTagBuilder(ItemTags.PLANKS)
			.add(WWBlocks.BAOBAB_PLANKS.asItem())
			.add(WWBlocks.WILLOW_PLANKS.asItem())
			.add(WWBlocks.CYPRESS_PLANKS.asItem())
			.add(WWBlocks.PALM_PLANKS.asItem())
			.add(WWBlocks.MAPLE_PLANKS.asItem());

		this.getOrCreateTagBuilder(ItemTags.SAPLINGS)
			.add(WWItems.BAOBAB_NUT)
			.add(WWBlocks.WILLOW_SAPLING.asItem())
			.add(WWBlocks.CYPRESS_SAPLING.asItem())
			.add(WWItems.COCONUT)
			.add(WWBlocks.MAPLE_SAPLING.asItem());

		this.getOrCreateTagBuilder(ItemTags.SIGNS)
			.add(WWItems.BAOBAB_SIGN)
			.add(WWItems.WILLOW_SIGN)
			.add(WWItems.CYPRESS_SIGN)
			.add(WWItems.PALM_SIGN)
			.add(WWItems.MAPLE_SIGN);

		this.getOrCreateTagBuilder(ItemTags.HANGING_SIGNS)
			.add(WWItems.BAOBAB_HANGING_SIGN)
			.add(WWItems.WILLOW_HANGING_SIGN)
			.add(WWItems.CYPRESS_HANGING_SIGN)
			.add(WWItems.PALM_HANGING_SIGN)
			.add(WWItems.MAPLE_HANGING_SIGN);

		this.getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS)
			.add(WWBlocks.BAOBAB_BUTTON.asItem())
			.add(WWBlocks.WILLOW_BUTTON.asItem())
			.add(WWBlocks.CYPRESS_BUTTON.asItem())
			.add(WWBlocks.PALM_BUTTON.asItem())
			.add(WWBlocks.MAPLE_BUTTON.asItem());

		this.getOrCreateTagBuilder(ItemTags.WOODEN_DOORS)
			.add(WWBlocks.BAOBAB_DOOR.asItem())
			.add(WWBlocks.WILLOW_DOOR.asItem())
			.add(WWBlocks.CYPRESS_DOOR.asItem())
			.add(WWBlocks.PALM_DOOR.asItem())
			.add(WWBlocks.MAPLE_DOOR.asItem());

		this.getOrCreateTagBuilder(ItemTags.WOODEN_FENCES)
			.add(WWBlocks.BAOBAB_FENCE.asItem())
			.add(WWBlocks.WILLOW_FENCE.asItem())
			.add(WWBlocks.CYPRESS_FENCE.asItem())
			.add(WWBlocks.PALM_FENCE.asItem())
			.add(WWBlocks.MAPLE_FENCE.asItem());

		this.getOrCreateTagBuilder(ItemTags.FENCE_GATES)
			.add(WWBlocks.BAOBAB_FENCE_GATE.asItem())
			.add(WWBlocks.WILLOW_FENCE_GATE.asItem())
			.add(WWBlocks.CYPRESS_FENCE_GATE.asItem())
			.add(WWBlocks.PALM_FENCE_GATE.asItem())
			.add(WWBlocks.MAPLE_FENCE_GATE.asItem());

		this.getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES)
			.add(WWBlocks.BAOBAB_PRESSURE_PLATE.asItem())
			.add(WWBlocks.WILLOW_PRESSURE_PLATE.asItem())
			.add(WWBlocks.CYPRESS_PRESSURE_PLATE.asItem())
			.add(WWBlocks.PALM_PRESSURE_PLATE.asItem())
			.add(WWBlocks.MAPLE_PRESSURE_PLATE.asItem());

		this.getOrCreateTagBuilder(ItemTags.WOODEN_SLABS)
			.add(WWBlocks.BAOBAB_SLAB.asItem())
			.add(WWBlocks.WILLOW_SLAB.asItem())
			.add(WWBlocks.CYPRESS_SLAB.asItem())
			.add(WWBlocks.PALM_SLAB.asItem())
			.add(WWBlocks.MAPLE_SLAB.asItem());

		this.getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS)
			.add(WWBlocks.BAOBAB_STAIRS.asItem())
			.add(WWBlocks.WILLOW_STAIRS.asItem())
			.add(WWBlocks.CYPRESS_STAIRS.asItem())
			.add(WWBlocks.PALM_STAIRS.asItem())
			.add(WWBlocks.MAPLE_STAIRS.asItem());

		this.getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS)
			.add(WWBlocks.BAOBAB_TRAPDOOR.asItem())
			.add(WWBlocks.WILLOW_TRAPDOOR.asItem())
			.add(WWBlocks.CYPRESS_TRAPDOOR.asItem())
			.add(WWBlocks.PALM_TRAPDOOR.asItem())
			.add(WWBlocks.MAPLE_TRAPDOOR.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_ACACIA_LOGS)
			.add(WWBlocks.HOLLOWED_ACACIA_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_BIRCH_LOGS)
			.add(WWBlocks.HOLLOWED_BIRCH_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_CHERRY_LOGS)
			.add(WWBlocks.HOLLOWED_CHERRY_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_CRIMSON_STEMS)
			.add(WWBlocks.HOLLOWED_CRIMSON_STEM.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_DARK_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_DARK_OAK_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_JUNGLE_LOGS)
			.add(WWBlocks.HOLLOWED_JUNGLE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_MANGROVE_LOGS)
			.add(WWBlocks.HOLLOWED_MANGROVE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_OAK_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_SPRUCE_LOGS)
			.add(WWBlocks.HOLLOWED_SPRUCE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_WARPED_STEMS)
			.add(WWBlocks.HOLLOWED_WARPED_STEM.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_BAOBAB_LOGS)
			.add(WWBlocks.HOLLOWED_BAOBAB_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_WILLOW_LOGS)
			.add(WWBlocks.HOLLOWED_WILLOW_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_CYPRESS_LOGS)
			.add(WWBlocks.HOLLOWED_CYPRESS_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_PALM_LOGS)
			.add(WWBlocks.HOLLOWED_PALM_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_PALE_OAK_LOGS)
			.addOptional(WWConstants.id("hollowed_pale_oak_log"))
			.addOptional(WWConstants.id("stripped_hollowed_pale_oak_log"));

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_MAPLE_LOGS)
			.add(WWBlocks.HOLLOWED_MAPLE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_BIRCH_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CHERRY_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_DARK_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_JUNGLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_MANGROVE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_SPRUCE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_WARPED_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_WILLOW_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_PALM_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_PALE_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_MAPLE_LOGS);

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_LOGS_DONT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_WARPED_STEMS);

		this.getOrCreateTagBuilder(WWItemTags.HOLLOWED_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_DONT_BURN);

		this.getOrCreateTagBuilder(WWItemTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.add(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.asItem())
			.addOptional(WWConstants.id("stripped_hollowed_pale_oak_log"))
			.add(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.asItem());

		this.getOrCreateTagBuilder(WWItemTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN)
			.add(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.asItem());

		this.getOrCreateTagBuilder(WWItemTags.STRIPPED_HOLLOWED_LOGS)
			.addOptionalTag(WWItemTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN);
	}
}
