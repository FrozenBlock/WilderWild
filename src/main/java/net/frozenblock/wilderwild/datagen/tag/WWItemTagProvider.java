/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
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
			.add(WWBlocks.MOSSY_MUD_BRICK_STAIRS.asItem());

		this.getOrCreateTagBuilder(ItemTags.SLABS)
			.add(WWBlocks.SCULK_SLAB.asItem())
			.add(WWBlocks.MOSSY_MUD_BRICK_SLAB.asItem());

		this.getOrCreateTagBuilder(ItemTags.WALLS)
			.add(WWBlocks.SCULK_WALL.asItem())
			.add(WWBlocks.MOSSY_MUD_BRICK_WALL.asItem());

		this.getOrCreateTagBuilder(ItemTags.ARMADILLO_FOOD)
			.add(WWItems.SCORCHED_EYE);

		this.getOrCreateTagBuilder(getTag("c:stripped_logs"))
			.add(WWBlocks.STRIPPED_BAOBAB_LOG.asItem())
			.add(WWBlocks.STRIPPED_CYPRESS_LOG.asItem())
			.add(WWBlocks.STRIPPED_PALM_LOG.asItem());

		this.getOrCreateTagBuilder(getTag("c:stripped_wood"))
			.add(WWBlocks.STRIPPED_BAOBAB_WOOD.asItem())
			.add(WWBlocks.STRIPPED_CYPRESS_WOOD.asItem())
			.add(WWBlocks.STRIPPED_PALM_WOOD.asItem());

		this.getOrCreateTagBuilder(WWItemTags.BROWN_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.BROWN_MUSHROOM)
			.addOptional(WWConstants.id("brown_shelf_fungus"));

		this.getOrCreateTagBuilder(WWItemTags.RED_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.RED_MUSHROOM)
			.addOptional(WWConstants.id("red_shelf_fungus"));

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

		this.getOrCreateTagBuilder(ItemTags.LEAVES)
			.add(WWBlocks.BAOBAB_LEAVES.asItem())
			.add(WWBlocks.CYPRESS_LEAVES.asItem())
			.add(WWBlocks.PALM_FRONDS.asItem())
			.add(WWBlocks.YELLOW_MAPLE_LEAVES.asItem())
			.add(WWBlocks.ORANGE_MAPLE_LEAVES.asItem())
			.add(WWBlocks.RED_MAPLE_LEAVES.asItem());

		this.getOrCreateTagBuilder(ItemTags.BOATS)
			.add(WWItems.BAOBAB_BOAT)
			.add(WWItems.CYPRESS_BOAT)
			.add(WWItems.PALM_BOAT)
			.add(WWItems.MAPLE_BOAT);

		this.getOrCreateTagBuilder(ItemTags.CHEST_BOATS)
			.add(WWItems.BAOBAB_CHEST_BOAT)
			.add(WWItems.CYPRESS_CHEST_BOAT)
			.add(WWItems.PALM_CHEST_BOAT)
			.add(WWItems.MAPLE_CHEST_BOAT);

		this.getOrCreateTagBuilder(WWItemTags.SCORCHED_SAND)
			.add(WWBlocks.SINGED_SAND.asItem())
			.add(WWBlocks.SCORCHED_SAND.asItem());

		this.getOrCreateTagBuilder(WWItemTags.SCORCHED_RED_SAND)
			.add(WWBlocks.SINGED_RED_SAND.asItem())
			.add(WWBlocks.SCORCHED_RED_SAND.asItem());

		this.getOrCreateTagBuilder(ItemTags.FLOWERS)
			.add(WWBlocks.POLLEN.asItem());

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

		this.getOrCreateTagBuilder(ItemTags.PLANKS)
			.add(WWBlocks.BAOBAB_PLANKS.asItem())
			.add(WWBlocks.CYPRESS_PLANKS.asItem())
			.add(WWBlocks.PALM_PLANKS.asItem())
			.add(WWBlocks.MAPLE_PLANKS.asItem());

		this.getOrCreateTagBuilder(ItemTags.SAPLINGS)
			.add(WWItems.BAOBAB_NUT)
			.add(WWBlocks.CYPRESS_SAPLING.asItem())
			.add(WWItems.COCONUT)
			.add(WWBlocks.MAPLE_SAPLING.asItem());

		this.getOrCreateTagBuilder(getTag("sereneseasons:summer_crops"))
			.add(WWBlocks.BUSH.asItem())
			.add(WWBlocks.MILKWEED.asItem())
			.add(WWBlocks.DATURA.asItem())
			.add(WWBlocks.SEEDING_DANDELION.asItem())
			.add(WWBlocks.CYPRESS_SAPLING.asItem())
			.add(WWBlocks.BAOBAB_NUT.asItem())
			.add(WWBlocks.COCONUT.asItem())
			.add(WWBlocks.BROWN_SHELF_FUNGI.asItem())
			.add(WWBlocks.RED_SHELF_FUNGI.asItem())
			.add(WWBlocks.TUMBLEWEED_PLANT.asItem());

		this.getOrCreateTagBuilder(getTag("sereneseasons:spring_crops"))
			.add(WWBlocks.BUSH.asItem())
			.add(WWBlocks.MILKWEED.asItem())
			.add(WWBlocks.DATURA.asItem())
			.add(WWBlocks.SEEDING_DANDELION.asItem())
			.add(WWBlocks.CYPRESS_SAPLING.asItem())
			.add(WWBlocks.BAOBAB_NUT.asItem())
			.add(WWBlocks.COCONUT.asItem())
			.add(WWBlocks.BROWN_SHELF_FUNGI.asItem())
			.add(WWBlocks.RED_SHELF_FUNGI.asItem())
			.add(WWBlocks.TUMBLEWEED_PLANT.asItem());

		this.getOrCreateTagBuilder(getTag("sereneseasons:autumn_crops"))
			.add(WWBlocks.MILKWEED.asItem())
			.add(WWBlocks.GLORY_OF_THE_SNOW.asItem())
			.add(WWBlocks.SEEDING_DANDELION.asItem())
			.add(WWBlocks.CYPRESS_SAPLING.asItem())
			.add(WWBlocks.BROWN_SHELF_FUNGI.asItem())
			.add(WWBlocks.RED_SHELF_FUNGI.asItem())
			.add(WWBlocks.MARIGOLD.asItem());

		this.getOrCreateTagBuilder(getTag("sereneseasons:winter_crops"))
			.add(WWBlocks.GLORY_OF_THE_SNOW.asItem())
			.add(WWBlocks.SEEDING_DANDELION.asItem())
			.add(WWBlocks.BROWN_SHELF_FUNGI.asItem())
			.add(WWBlocks.RED_SHELF_FUNGI.asItem());
	}
}
