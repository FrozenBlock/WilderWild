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
import net.frozenblock.lib.tag.api.FrozenItemTags;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.tag.WilderItemTags;
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
			.add(RegisterBlocks.SCULK_STAIRS.asItem())
			.add(RegisterBlocks.MOSSY_MUD_BRICK_STAIRS.asItem());

		this.getOrCreateTagBuilder(ItemTags.SLABS)
			.add(RegisterBlocks.SCULK_SLAB.asItem())
			.add(RegisterBlocks.MOSSY_MUD_BRICK_SLAB.asItem());

		this.getOrCreateTagBuilder(ItemTags.WALLS)
			.add(RegisterBlocks.SCULK_WALL.asItem())
			.add(RegisterBlocks.MOSSY_MUD_BRICK_WALL.asItem());

		this.getOrCreateTagBuilder(ItemTags.ARMADILLO_FOOD)
			.add(RegisterItems.SCORCHED_EYE);

		this.getOrCreateTagBuilder(getTag("c:stripped_logs"))
			.add(RegisterBlocks.STRIPPED_BAOBAB_LOG.asItem())
			.add(RegisterBlocks.STRIPPED_CYPRESS_LOG.asItem())
			.add(RegisterBlocks.STRIPPED_PALM_LOG.asItem());

		this.getOrCreateTagBuilder(getTag("c:stripped_wood"))
			.add(RegisterBlocks.STRIPPED_BAOBAB_WOOD.asItem())
			.add(RegisterBlocks.STRIPPED_CYPRESS_WOOD.asItem())
			.add(RegisterBlocks.STRIPPED_PALM_WOOD.asItem());

		this.getOrCreateTagBuilder(FrozenItemTags.ALWAYS_SAVE_COOLDOWNS)
			.add(RegisterItems.ANCIENT_HORN);

		this.getOrCreateTagBuilder(WilderItemTags.BROWN_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.BROWN_MUSHROOM)
			.addOptional(WilderConstants.id("brown_shelf_fungus"));

		this.getOrCreateTagBuilder(WilderItemTags.RED_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.RED_MUSHROOM)
			.addOptional(WilderConstants.id("red_shelf_fungus"));

		this.getOrCreateTagBuilder(WilderItemTags.MESOGLEA)
			.add(RegisterBlocks.BLUE_MESOGLEA.asItem())
			.add(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.asItem())
			.add(RegisterBlocks.LIME_MESOGLEA.asItem())
			.add(RegisterBlocks.PINK_MESOGLEA.asItem())
			.add(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.asItem())
			.add(RegisterBlocks.RED_MESOGLEA.asItem())
			.add(RegisterBlocks.YELLOW_MESOGLEA.asItem());

		this.getOrCreateTagBuilder(WilderItemTags.NEMATOCYSTS)
			.add(RegisterBlocks.BLUE_NEMATOCYST.asItem())
			.add(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.asItem())
			.add(RegisterBlocks.LIME_NEMATOCYST.asItem())
			.add(RegisterBlocks.PINK_NEMATOCYST.asItem())
			.add(RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.asItem())
			.add(RegisterBlocks.RED_NEMATOCYST.asItem())
			.add(RegisterBlocks.YELLOW_NEMATOCYST.asItem());

		this.getOrCreateTagBuilder(WilderItemTags.JELLYFISH_FOOD)
			.add(Items.COD)
			.add(Items.SALMON);

		this.getOrCreateTagBuilder(WilderItemTags.PEARLESCENT_JELLYFISH_FOOD)
			.add(Items.COD)
			.add(Items.SALMON);

		this.getOrCreateTagBuilder(WilderItemTags.CRAB_FOOD)
			.add(Items.KELP);

		this.getOrCreateTagBuilder(WilderItemTags.OSTRICH_FOOD)
			.add(RegisterBlocks.BUSH.asItem());

		this.getOrCreateTagBuilder(getTag("sereneseasons:summer_crops"))
			.add(RegisterBlocks.BUSH.asItem())
			.add(RegisterBlocks.MILKWEED.asItem())
			.add(RegisterBlocks.DATURA.asItem())
			.add(RegisterBlocks.SEEDING_DANDELION.asItem())
			.add(RegisterBlocks.CYPRESS_SAPLING.asItem())
			.add(RegisterBlocks.BAOBAB_NUT.asItem())
			.add(RegisterBlocks.COCONUT.asItem())
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS.asItem())
			.add(RegisterBlocks.RED_SHELF_FUNGUS.asItem())
			.add(RegisterBlocks.TUMBLEWEED_PLANT.asItem());

		this.getOrCreateTagBuilder(getTag("sereneseasons:spring_crops"))
			.add(RegisterBlocks.BUSH.asItem())
			.add(RegisterBlocks.MILKWEED.asItem())
			.add(RegisterBlocks.DATURA.asItem())
			.add(RegisterBlocks.SEEDING_DANDELION.asItem())
			.add(RegisterBlocks.CYPRESS_SAPLING.asItem())
			.add(RegisterBlocks.BAOBAB_NUT.asItem())
			.add(RegisterBlocks.COCONUT.asItem())
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS.asItem())
			.add(RegisterBlocks.RED_SHELF_FUNGUS.asItem())
			.add(RegisterBlocks.TUMBLEWEED_PLANT.asItem());

		this.getOrCreateTagBuilder(getTag("sereneseasons:autumn_crops"))
			.add(RegisterBlocks.MILKWEED.asItem())
			.add(RegisterBlocks.GLORY_OF_THE_SNOW.asItem())
			.add(RegisterBlocks.SEEDING_DANDELION.asItem())
			.add(RegisterBlocks.CYPRESS_SAPLING.asItem())
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS.asItem())
			.add(RegisterBlocks.RED_SHELF_FUNGUS.asItem());

		this.getOrCreateTagBuilder(getTag("sereneseasons:winter_crops"))
			.add(RegisterBlocks.GLORY_OF_THE_SNOW.asItem())
			.add(RegisterBlocks.SEEDING_DANDELION.asItem())
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS.asItem())
			.add(RegisterBlocks.RED_SHELF_FUNGUS.asItem());
	}
}
