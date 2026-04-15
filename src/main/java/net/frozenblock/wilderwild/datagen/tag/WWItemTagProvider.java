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
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class WWItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

	public WWItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
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
		this.builder(BlockItemTags.STAIRS.item())
			.add(WWBlockItemIds.SCULK_STAIRS)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS)
			.add(WWBlockItemIds.GABBRO_STAIRS)
			.add(WWBlockItemIds.POLISHED_GABBRO_STAIRS)
			.add(WWBlockItemIds.GABBRO_BRICK_STAIRS)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS);

		this.builder(BlockItemTags.SLABS.item())
			.add(WWBlockItemIds.SCULK_SLAB)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_SLAB)
			.add(WWBlockItemIds.GABBRO_SLAB)
			.add(WWBlockItemIds.POLISHED_GABBRO_SLAB)
			.add(WWBlockItemIds.GABBRO_BRICK_SLAB)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB);

		this.builder(ItemTags.WALLS)
			.add(WWBlockItemIds.SCULK_WALL)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_WALL)
			.add(WWBlockItemIds.GABBRO_WALL)
			.add(WWBlockItemIds.POLISHED_GABBRO_WALL)
			.add(WWBlockItemIds.GABBRO_BRICK_WALL)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL);

		this.builder(ItemTags.WOODEN_SHELVES)
			.add(WWBlockItemIds.BAOBAB_SHELF)
			.add(WWBlockItemIds.WILLOW_SHELF)
			.add(WWBlockItemIds.CYPRESS_SHELF)
			.add(WWBlockItemIds.PALM_SHELF)
			.add(WWBlockItemIds.MAPLE_SHELF);

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

		this.builder(WWItemTags.MESOGLEA)
			.add(WWBlockItemIds.BLUE_MESOGLEA)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_MESOGLEA)
			.add(WWBlockItemIds.LIME_MESOGLEA)
			.add(WWBlockItemIds.PINK_MESOGLEA)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_MESOGLEA)
			.add(WWBlockItemIds.RED_MESOGLEA)
			.add(WWBlockItemIds.YELLOW_MESOGLEA);

		this.builder(WWItemTags.NEMATOCYSTS)
			.add(WWBlockItemIds.BLUE_NEMATOCYST)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST)
			.add(WWBlockItemIds.LIME_NEMATOCYST)
			.add(WWBlockItemIds.PINK_NEMATOCYST)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST)
			.add(WWBlockItemIds.RED_NEMATOCYST)
			.add(WWBlockItemIds.YELLOW_NEMATOCYST);

		this.builder(WWItemTags.PEARLESCENT_NEMATOCYSTS)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST);

		this.builder(WWItemTags.FROGLIGHT_GOOP)
			.add(WWBlockItemIds.PEARLESCENT_FROGLIGHT_GOOP)
			.add(WWBlockItemIds.VERDANT_FROGLIGHT_GOOP)
			.add(WWBlockItemIds.OCHRE_FROGLIGHT_GOOP);

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

		this.builder(BlockItemTags.SMALL_FLOWERS.item())
			.add(WWBlockItemIds.CARNATION)
			.add(WWBlockItemIds.MARIGOLD)
			.add(WWBlockItemIds.SEEDING_DANDELION)
			.add(WWBlockItemIds.PASQUEFLOWER)
			.add(WWBlockItemIds.RED_HIBISCUS)
			.add(WWBlockItemIds.YELLOW_HIBISCUS)
			.add(WWBlockItemIds.WHITE_HIBISCUS)
			.add(WWBlockItemIds.PINK_HIBISCUS)
			.add(WWBlockItemIds.PURPLE_HIBISCUS)
			.add(WWBlockItemIds.FLOWERING_LILY_PAD);

		this.builder(ItemTags.BEE_FOOD)
			.add(WWBlockItemIds.CARNATION)
			.add(WWBlockItemIds.MARIGOLD)
			.add(WWBlockItemIds.SEEDING_DANDELION)
			.add(WWBlockItemIds.RED_HIBISCUS)
			.add(WWBlockItemIds.YELLOW_HIBISCUS)
			.add(WWBlockItemIds.WHITE_HIBISCUS)
			.add(WWBlockItemIds.PINK_HIBISCUS)
			.add(WWBlockItemIds.PURPLE_HIBISCUS)
			.add(WWBlockItemIds.FLOWERING_LILY_PAD)
			.add(WWBlockItemIds.DATURA)
			.add(WWBlockItemIds.CATTAIL)
			.add(WWBlockItemIds.MILKWEED)
			.add(WWBlockItemIds.POLLEN)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS);

		this.builder(ItemTags.MOSS_BLOCKS)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK);

		this.builder(WWItemTags.BAOBAB_LOGS)
			.add(WWBlockItemIds.BAOBAB_LOG, WWBlockItemIds.STRIPPED_BAOBAB_LOG)
			.add(WWBlockItemIds.BAOBAB_WOOD, WWBlockItemIds.STRIPPED_BAOBAB_WOOD);

		this.builder(WWItemTags.WILLOW_LOGS)
			.add(WWBlockItemIds.WILLOW_LOG, WWBlockItemIds.STRIPPED_WILLOW_LOG)
			.add(WWBlockItemIds.WILLOW_WOOD, WWBlockItemIds.STRIPPED_WILLOW_WOOD);

		this.builder(WWItemTags.CYPRESS_LOGS)
			.add(WWBlockItemIds.CYPRESS_LOG, WWBlockItemIds.STRIPPED_CYPRESS_LOG)
			.add(WWBlockItemIds.CYPRESS_WOOD, WWBlockItemIds.STRIPPED_CYPRESS_WOOD);

		this.builder(WWItemTags.PALM_LOGS)
			.add(WWBlockItemIds.PALM_LOG, WWBlockItemIds.STRIPPED_PALM_LOG)
			.add(WWBlockItemIds.PALM_WOOD, WWBlockItemIds.STRIPPED_PALM_WOOD);

		this.builder(WWItemTags.MAPLE_LOGS)
			.add(WWBlockItemIds.MAPLE_LOG, WWBlockItemIds.STRIPPED_MAPLE_LOG)
			.add(WWBlockItemIds.MAPLE_WOOD, WWBlockItemIds.STRIPPED_MAPLE_WOOD);

		this.builder(ItemTags.LEAVES)
			.add(WWBlockItemIds.BAOBAB_LEAVES)
			.add(WWBlockItemIds.WILLOW_LEAVES)
			.add(WWBlockItemIds.CYPRESS_LEAVES)
			.add(WWBlockItemIds.PALM_FRONDS)
			.add(WWBlockItemIds.YELLOW_MAPLE_LEAVES)
			.add(WWBlockItemIds.ORANGE_MAPLE_LEAVES)
			.add(WWBlockItemIds.RED_MAPLE_LEAVES);

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

		this.builder(ItemTags.LOGS)
			.addOptionalTag(WWItemTags.BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.WILLOW_LOGS)
			.addOptionalTag(WWItemTags.CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.PALM_LOGS)
			.addOptionalTag(WWItemTags.MAPLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS);

		this.builder(ItemTags.LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.WILLOW_LOGS)
			.addOptionalTag(WWItemTags.CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.PALM_LOGS)
			.addOptionalTag(WWItemTags.MAPLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_THAT_BURN);

		this.builder(ItemTags.NON_FLAMMABLE_WOOD)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_DONT_BURN);

		this.builder(ItemTags.PLANKS)
			.add(WWBlockItemIds.BAOBAB_PLANKS)
			.add(WWBlockItemIds.WILLOW_PLANKS)
			.add(WWBlockItemIds.CYPRESS_PLANKS)
			.add(WWBlockItemIds.PALM_PLANKS)
			.add(WWBlockItemIds.MAPLE_PLANKS);

		this.builder(BlockItemTags.SAPLINGS.item())
			.add(WWBlockItemIds.BAOBAB_NUT)
			.add(WWBlockItemIds.WILLOW_SAPLING)
			.add(WWBlockItemIds.CYPRESS_SAPLING)
			.add(WWBlockItemIds.COCONUT)
			.add(WWBlockItemIds.YELLOW_MAPLE_SAPLING, WWBlockItemIds.ORANGE_MAPLE_SAPLING, WWBlockItemIds.RED_MAPLE_SAPLING);

		this.builder(BlockItemTags.SIGNS.item())
			.add(WWBlockItemIds.BAOBAB_SIGN)
			.add(WWBlockItemIds.WILLOW_SIGN)
			.add(WWBlockItemIds.CYPRESS_SIGN)
			.add(WWBlockItemIds.PALM_SIGN)
			.add(WWBlockItemIds.MAPLE_SIGN);

		this.builder(BlockItemTags.HANGING_SIGNS.item())
			.add(WWBlockItemIds.BAOBAB_HANGING_SIGN)
			.add(WWBlockItemIds.WILLOW_HANGING_SIGN)
			.add(WWBlockItemIds.CYPRESS_HANGING_SIGN)
			.add(WWBlockItemIds.PALM_HANGING_SIGN)
			.add(WWBlockItemIds.MAPLE_HANGING_SIGN);

		this.builder(ItemTags.WOODEN_BUTTONS)
			.add(WWBlockItemIds.BAOBAB_BUTTON)
			.add(WWBlockItemIds.WILLOW_BUTTON)
			.add(WWBlockItemIds.CYPRESS_BUTTON)
			.add(WWBlockItemIds.PALM_BUTTON)
			.add(WWBlockItemIds.MAPLE_BUTTON);

		this.builder(ItemTags.WOODEN_DOORS)
			.add(WWBlockItemIds.BAOBAB_DOOR)
			.add(WWBlockItemIds.WILLOW_DOOR)
			.add(WWBlockItemIds.CYPRESS_DOOR)
			.add(WWBlockItemIds.PALM_DOOR)
			.add(WWBlockItemIds.MAPLE_DOOR);

		this.builder(ItemTags.WOODEN_FENCES)
			.add(WWBlockItemIds.BAOBAB_FENCE)
			.add(WWBlockItemIds.WILLOW_FENCE)
			.add(WWBlockItemIds.CYPRESS_FENCE)
			.add(WWBlockItemIds.PALM_FENCE)
			.add(WWBlockItemIds.MAPLE_FENCE);

		this.builder(ItemTags.FENCE_GATES)
			.add(WWBlockItemIds.BAOBAB_FENCE_GATE)
			.add(WWBlockItemIds.WILLOW_FENCE_GATE)
			.add(WWBlockItemIds.CYPRESS_FENCE_GATE)
			.add(WWBlockItemIds.PALM_FENCE_GATE)
			.add(WWBlockItemIds.MAPLE_FENCE_GATE);

		this.builder(ItemTags.WOODEN_PRESSURE_PLATES)
			.add(WWBlockItemIds.BAOBAB_PRESSURE_PLATE)
			.add(WWBlockItemIds.WILLOW_PRESSURE_PLATE)
			.add(WWBlockItemIds.CYPRESS_PRESSURE_PLATE)
			.add(WWBlockItemIds.PALM_PRESSURE_PLATE)
			.add(WWBlockItemIds.MAPLE_PRESSURE_PLATE);

		this.builder(ItemTags.WOODEN_SLABS)
			.add(WWBlockItemIds.BAOBAB_SLAB)
			.add(WWBlockItemIds.WILLOW_SLAB)
			.add(WWBlockItemIds.CYPRESS_SLAB)
			.add(WWBlockItemIds.PALM_SLAB)
			.add(WWBlockItemIds.MAPLE_SLAB);

		this.builder(ItemTags.WOODEN_STAIRS)
			.add(WWBlockItemIds.BAOBAB_STAIRS)
			.add(WWBlockItemIds.WILLOW_STAIRS)
			.add(WWBlockItemIds.CYPRESS_STAIRS)
			.add(WWBlockItemIds.PALM_STAIRS)
			.add(WWBlockItemIds.MAPLE_STAIRS);

		this.builder(ItemTags.WOODEN_TRAPDOORS)
			.add(WWBlockItemIds.BAOBAB_TRAPDOOR)
			.add(WWBlockItemIds.WILLOW_TRAPDOOR)
			.add(WWBlockItemIds.CYPRESS_TRAPDOOR)
			.add(WWBlockItemIds.PALM_TRAPDOOR)
			.add(WWBlockItemIds.MAPLE_TRAPDOOR);

		this.builder(WWItemTags.HOLLOWED_ACACIA_LOGS)
			.add(WWBlockItemIds.HOLLOWED_ACACIA_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG);

		this.builder(WWItemTags.HOLLOWED_BIRCH_LOGS)
			.add(WWBlockItemIds.HOLLOWED_BIRCH_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG);

		this.builder(WWItemTags.HOLLOWED_CHERRY_LOGS)
			.add(WWBlockItemIds.HOLLOWED_CHERRY_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG);

		this.builder(WWItemTags.HOLLOWED_CRIMSON_STEMS)
			.add(WWBlockItemIds.HOLLOWED_CRIMSON_STEM, WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM);

		this.builder(WWItemTags.HOLLOWED_DARK_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_DARK_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		this.builder(WWItemTags.HOLLOWED_JUNGLE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_JUNGLE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG);

		this.builder(WWItemTags.HOLLOWED_MANGROVE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_MANGROVE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG);

		this.builder(WWItemTags.HOLLOWED_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG);

		this.builder(WWItemTags.HOLLOWED_SPRUCE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_SPRUCE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG);

		this.builder(WWItemTags.HOLLOWED_WARPED_STEMS)
			.add(WWBlockItemIds.HOLLOWED_WARPED_STEM, WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM);

		this.builder(WWItemTags.HOLLOWED_BAOBAB_LOGS)
			.add(WWBlockItemIds.HOLLOWED_BAOBAB_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG);

		this.builder(WWItemTags.HOLLOWED_WILLOW_LOGS)
			.add(WWBlockItemIds.HOLLOWED_WILLOW_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG);

		this.builder(WWItemTags.HOLLOWED_CYPRESS_LOGS)
			.add(WWBlockItemIds.HOLLOWED_CYPRESS_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG);

		this.builder(WWItemTags.HOLLOWED_PALM_LOGS)
			.add(WWBlockItemIds.HOLLOWED_PALM_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG);

		this.builder(WWItemTags.HOLLOWED_PALE_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_PALE_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG);

		this.builder(WWItemTags.HOLLOWED_MAPLE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_MAPLE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.builder(WWItemTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_BIRCH_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CHERRY_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_DARK_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_JUNGLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_MANGROVE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_SPRUCE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_WILLOW_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_PALM_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_PALE_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_MAPLE_LOGS);

		this.builder(WWItemTags.HOLLOWED_LOGS_DONT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_WARPED_STEMS);

		this.builder(WWItemTags.HOLLOWED_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_DONT_BURN);

		this.builder(WWItemTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.builder(WWItemTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM);

		this.builder(WWItemTags.STRIPPED_HOLLOWED_LOGS)
			.addOptionalTag(WWItemTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN);

		// SULFUR CUBE
		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_BOUNCY)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS);

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
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS);

		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_FAST_SLIDING)
			.add(WWBlockItemIds.FRAGILE_ICE);

		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_SLIDING)
			.add(WWBlockItemIds.PALE_MUSHROOM_BLOCK);
	}
}
