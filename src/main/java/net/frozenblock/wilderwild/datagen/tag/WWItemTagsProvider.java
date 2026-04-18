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

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.references.WWItemIds;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class WWItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {

	public WWItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	private TagKey<Item> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	private ResourceKey<Item> getKey(String namespace, String path) {
		return ResourceKey.create(this.registryKey, Identifier.fromNamespaceAndPath(namespace, path));
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		new WWBlockItemTagsProvider(tagId -> BlockItemTagsProvider.wrapForItems(this.tag(tagId.item()))).run();

		this.builder(ItemTags.ARMADILLO_FOOD)
			.add(WWItemIds.SCORCHED_EYE);

		this.builder(WWItemTags.BROWN_MUSHROOM_STEW_INGREDIENTS)
			.add(BlockItemIds.BROWN_MUSHROOM)
			.addOptional(WWBlockItemIds.BROWN_SHELF_FUNGI.item());

		this.builder(WWItemTags.RED_MUSHROOM_STEW_INGREDIENTS)
			.add(BlockItemIds.RED_MUSHROOM)
			.addOptional(WWBlockItemIds.RED_SHELF_FUNGI.item());

		this.builder(WWItemTags.TUMBLEWEED_COMMON)
			.add(ItemIds.ROTTEN_FLESH)
			.add(ItemIds.BONE_MEAL)
			.add(BlockItemIds.WHEAT_CROP)
			.add(ItemIds.STICK)
			.add(BlockItemIds.DEAD_BUSH);

		this.builder(WWItemTags.TUMBLEWEED_MEDIUM)
			.add(ItemIds.BONE)
			.add(ItemIds.GOLD_NUGGET)
			.add(BlockItemIds.WHEAT_CROP)
			.add(BlockItemIds.POTATO_CROP)
			.add(BlockItemIds.CARROT_CROP)
			.add(BlockItemIds.BEETROOT_CROP)
			.add(BlockItemIds.TRIPWIRE);

		this.builder(WWItemTags.TUMBLEWEED_RARE)
			.add(ItemIds.IRON_NUGGET)
			.add(BlockItemIds.POTATO_CROP)
			.add(BlockItemIds.CARROT_CROP)
			.add(BlockItemIds.MELON_CROP)
			.add(BlockItemIds.PUMPKIN_CROP);

		this.builder(WWItemTags.JELLYFISH_FOOD)
			.add(ItemIds.COD, ItemIds.SALMON);

		this.builder(WWItemTags.PEARLESCENT_JELLYFISH_FOOD)
			.add(ItemIds.COD, ItemIds.SALMON);

		this.builder(WWItemTags.CRAB_FOOD)
			.add(BlockItemIds.KELP);

		this.builder(WWItemTags.OSTRICH_FOOD)
			.add(WWBlockItemIds.SHRUB);

		this.builder(WWItemTags.ZOMBIE_OSTRICH_FOOD)
			.add(BlockItemIds.DEAD_BUSH);

		this.builder(WWItemTags.PENGUIN_FOOD)
			.add(ItemIds.INK_SAC, ItemIds.GLOW_INK_SAC);

		this.builder(ItemTags.BOATS)
			.add(WWItemIds.BAOBAB_BOAT)
			.add(WWItemIds.WILLOW_BOAT)
			.add(WWItemIds.CYPRESS_BOAT)
			.add(WWItemIds.PALM_BOAT)
			.add(WWItemIds.MAPLE_BOAT);

		this.builder(ItemTags.CHEST_BOATS)
			.add(WWItemIds.BAOBAB_CHEST_BOAT)
			.add(WWItemIds.WILLOW_CHEST_BOAT)
			.add(WWItemIds.CYPRESS_CHEST_BOAT)
			.add(WWItemIds.PALM_CHEST_BOAT)
			.add(WWItemIds.MAPLE_CHEST_BOAT);

		this.builder(ConventionalItemTags.STRIPPED_LOGS)
			.add(WWBlockItemIds.STRIPPED_BAOBAB_LOG)
			.add(WWBlockItemIds.STRIPPED_WILLOW_LOG)
			.add(WWBlockItemIds.STRIPPED_CYPRESS_LOG)
			.add(WWBlockItemIds.STRIPPED_PALM_LOG)
			.add(WWBlockItemIds.STRIPPED_MAPLE_LOG);

		this.builder(ConventionalItemTags.STRIPPED_WOODS)
			.add(WWBlockItemIds.STRIPPED_BAOBAB_WOOD)
			.add(WWBlockItemIds.STRIPPED_WILLOW_WOOD)
			.add(WWBlockItemIds.STRIPPED_CYPRESS_WOOD)
			.add(WWBlockItemIds.STRIPPED_PALM_WOOD)
			.add(WWBlockItemIds.STRIPPED_MAPLE_WOOD);

		this.builder(ItemTags.NON_FLAMMABLE_WOOD)
			.addOptionalTag(WWBlockItemTags.HOLLOWED_LOGS_DONT_BURN.item());

		// SULFUR CUBE
		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_BOUNCY)
			.addOptionalTag(WWBlockItemTags.HOLLOWED_LOGS.item());

		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_REGULAR)
			.add(
				WWBlockItemIds.CHISELED_MUD_BRICKS,
				WWBlockItemIds.MOSSY_MUD_BRICKS,
				WWBlockItemIds.CRACKED_MUD_BRICKS
			)
			.add(
				WWBlockItemIds.GABBRO,
				WWBlockItemIds.POLISHED_GABBRO,
				WWBlockItemIds.GABBRO_BRICKS,
				WWBlockItemIds.CRACKED_GABBRO_BRICKS,
				WWBlockItemIds.CHISELED_GABBRO_BRICKS,
				WWBlockItemIds.MOSSY_GABBRO_BRICKS
			)
			.add(WWBlockItemIds.SCORCHED_SAND, WWBlockItemIds.SCORCHED_RED_SAND)
			.add(WWBlockItemIds.NULL_BLOCK)
			.addOptionalTag(WWBlockItemTags.HOLLOWED_LOGS.item());

		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_FAST_SLIDING)
			.add(WWBlockItemIds.FRAGILE_ICE);

		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_SLIDING)
			.add(WWBlockItemIds.PALE_MUSHROOM_BLOCK);
	}
}
