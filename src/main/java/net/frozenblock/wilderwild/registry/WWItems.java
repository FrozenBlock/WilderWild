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

package net.frozenblock.wilderwild.registry;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.frozenblock.lib.item.api.DamageOnUseBlockItem;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.item.CoconutItem;
import net.frozenblock.wilderwild.item.CrabClawItem;
import net.frozenblock.wilderwild.item.MilkweedPodItem;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.references.WWItemIds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.Fluids;

public final class WWItems {
	// BLOCK ITEMS
	// MUD
	public static final Item CHISELED_MUD_BRICKS = Items.registerBlock(WWBlockItemIds.CHISELED_MUD_BRICKS, WWBlocks.CHISELED_MUD_BRICKS);
	public static final Item CRACKED_MUD_BRICKS = Items.registerBlock(WWBlockItemIds.CRACKED_MUD_BRICKS, WWBlocks.CRACKED_MUD_BRICKS);
	public static final Item MOSSY_MUD_BRICKS = Items.registerBlock(WWBlockItemIds.MOSSY_MUD_BRICKS, WWBlocks.MOSSY_MUD_BRICKS);
	public static final Item MOSSY_MUD_BRICK_STAIRS = Items.registerBlock(WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS, WWBlocks.MOSSY_MUD_BRICK_STAIRS);
	public static final Item MOSSY_MUD_BRICK_SLAB = Items.registerBlock(WWBlockItemIds.MOSSY_MUD_BRICK_SLAB, WWBlocks.MOSSY_MUD_BRICK_SLAB);
	public static final Item MOSSY_MUD_BRICK_WALL = Items.registerBlock(WWBlockItemIds.MOSSY_MUD_BRICK_WALL, WWBlocks.MOSSY_MUD_BRICK_WALL);

	// SAND
	public static final Item SCORCHED_SAND = Items.registerBlock(WWBlockItemIds.SCORCHED_SAND, WWBlocks.SCORCHED_SAND);
	public static final Item SCORCHED_RED_SAND = Items.registerBlock(WWBlockItemIds.SCORCHED_RED_SAND, WWBlocks.SCORCHED_RED_SAND);

	// SAPLINGS
	public static final Item BAOBAB_NUT = Items.registerBlock(WWBlockItemIds.BAOBAB_NUT, WWBlocks.BAOBAB_NUT,
		new Item.Properties().food(WWFoods.BAOBAB_NUT)
	);
	public static final Item WILLOW_SAPLING = Items.registerBlock(WWBlockItemIds.WILLOW_SAPLING, WWBlocks.WILLOW_SAPLING);
	public static final Item CYPRESS_SAPLING = Items.registerBlock(WWBlockItemIds.CYPRESS_SAPLING, WWBlocks.CYPRESS_SAPLING);
	public static final Item COCONUT = Items.registerBlock(WWBlockItemIds.COCONUT, WWBlocks.COCONUT, CoconutItem::new);
	public static final Item YELLOW_MAPLE_SAPLING = Items.registerBlock(WWBlockItemIds.YELLOW_MAPLE_SAPLING, WWBlocks.YELLOW_MAPLE_SAPLING);
	public static final Item ORANGE_MAPLE_SAPLING = Items.registerBlock(WWBlockItemIds.ORANGE_MAPLE_SAPLING, WWBlocks.ORANGE_MAPLE_SAPLING);
	public static final Item RED_MAPLE_SAPLING = Items.registerBlock(WWBlockItemIds.RED_MAPLE_SAPLING, WWBlocks.RED_MAPLE_SAPLING);

	// LEAVES
	public static final Item BAOBAB_LEAVES = Items.registerBlock(WWBlockItemIds.BAOBAB_LEAVES, WWBlocks.BAOBAB_LEAVES);
	public static final Item WILLOW_LEAVES = Items.registerBlock(WWBlockItemIds.WILLOW_LEAVES, WWBlocks.WILLOW_LEAVES);
	public static final Item CYPRESS_LEAVES = Items.registerBlock(WWBlockItemIds.CYPRESS_LEAVES, WWBlocks.CYPRESS_LEAVES);
	public static final Item PALM_FRONDS = Items.registerBlock(WWBlockItemIds.PALM_FRONDS, WWBlocks.PALM_FRONDS);
	public static final Item YELLOW_MAPLE_LEAVES = Items.registerBlock(WWBlockItemIds.YELLOW_MAPLE_LEAVES, WWBlocks.YELLOW_MAPLE_LEAVES);
	public static final Item ORANGE_MAPLE_LEAVES = Items.registerBlock(WWBlockItemIds.ORANGE_MAPLE_LEAVES, WWBlocks.ORANGE_MAPLE_LEAVES);
	public static final Item RED_MAPLE_LEAVES = Items.registerBlock(WWBlockItemIds.RED_MAPLE_LEAVES, WWBlocks.RED_MAPLE_LEAVES);

	// HOLLOWED LOGS
	public static final Item HOLLOWED_OAK_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG);
	public static final Item HOLLOWED_SPRUCE_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG);
	public static final Item HOLLOWED_BIRCH_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG);
	public static final Item HOLLOWED_JUNGLE_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG);
	public static final Item HOLLOWED_ACACIA_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG);
	public static final Item HOLLOWED_DARK_OAK_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG);
	public static final Item HOLLOWED_MANGROVE_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG);
	public static final Item HOLLOWED_CHERRY_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG);
	public static final Item HOLLOWED_PALE_OAK_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG);
	public static final Item HOLLOWED_CRIMSON_STEM = Items.registerBlock(WWBlockItemIds.HOLLOWED_CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM);
	public static final Item HOLLOWED_WARPED_STEM = Items.registerBlock(WWBlockItemIds.HOLLOWED_WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM);
	public static final Item HOLLOWED_BAOBAB_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_BAOBAB_LOG, WWBlocks.HOLLOWED_BAOBAB_LOG);
	public static final Item HOLLOWED_WILLOW_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_WILLOW_LOG, WWBlocks.HOLLOWED_WILLOW_LOG);
	public static final Item HOLLOWED_CYPRESS_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_CYPRESS_LOG, WWBlocks.HOLLOWED_CYPRESS_LOG);
	public static final Item HOLLOWED_PALM_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_PALM_LOG, WWBlocks.HOLLOWED_PALM_LOG);
	public static final Item HOLLOWED_MAPLE_LOG = Items.registerBlock(WWBlockItemIds.HOLLOWED_MAPLE_LOG, WWBlocks.HOLLOWED_MAPLE_LOG);

	// STRIPPED STRIPPED_HOLLOWED LOGS
	public static final Item STRIPPED_HOLLOWED_OAK_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);
	public static final Item STRIPPED_HOLLOWED_SPRUCE_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
	public static final Item STRIPPED_HOLLOWED_BIRCH_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
	public static final Item STRIPPED_HOLLOWED_JUNGLE_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
	public static final Item STRIPPED_HOLLOWED_ACACIA_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
	public static final Item STRIPPED_HOLLOWED_DARK_OAK_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
	public static final Item STRIPPED_HOLLOWED_MANGROVE_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
	public static final Item STRIPPED_HOLLOWED_CHERRY_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
	public static final Item STRIPPED_HOLLOWED_PALE_OAK_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);
	public static final Item STRIPPED_HOLLOWED_CRIMSON_STEM = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);
	public static final Item STRIPPED_HOLLOWED_WARPED_STEM = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

	// LEAF LITTER
	public static final Item ACACIA_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.ACACIA_LEAF_LITTER, WWBlocks.ACACIA_LEAF_LITTER);
	public static final Item AZALEA_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.AZALEA_LEAF_LITTER, WWBlocks.AZALEA_LEAF_LITTER);
	public static final Item BAOBAB_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.BAOBAB_LEAF_LITTER, WWBlocks.BAOBAB_LEAF_LITTER);
	public static final Item BIRCH_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.BIRCH_LEAF_LITTER, WWBlocks.BIRCH_LEAF_LITTER);
	public static final Item CHERRY_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.CHERRY_LEAF_LITTER, WWBlocks.CHERRY_LEAF_LITTER);
	public static final Item CYPRESS_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.CYPRESS_LEAF_LITTER, WWBlocks.CYPRESS_LEAF_LITTER);
	public static final Item DARK_OAK_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.DARK_OAK_LEAF_LITTER, WWBlocks.DARK_OAK_LEAF_LITTER);
	public static final Item JUNGLE_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.JUNGLE_LEAF_LITTER, WWBlocks.JUNGLE_LEAF_LITTER);
	public static final Item MANGROVE_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.MANGROVE_LEAF_LITTER, WWBlocks.MANGROVE_LEAF_LITTER);
	public static final Item PALE_OAK_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.PALE_OAK_LEAF_LITTER, WWBlocks.PALE_OAK_LEAF_LITTER);
	public static final Item PALM_FROND_LITTER = Items.registerBlock(WWBlockItemIds.PALM_FROND_LITTER, WWBlocks.PALM_FROND_LITTER);
	public static final Item SPRUCE_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.SPRUCE_LEAF_LITTER, WWBlocks.SPRUCE_LEAF_LITTER);
	public static final Item WILLOW_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.WILLOW_LEAF_LITTER, WWBlocks.WILLOW_LEAF_LITTER);
	public static final Item YELLOW_MAPLE_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.YELLOW_MAPLE_LEAF_LITTER, WWBlocks.YELLOW_MAPLE_LEAF_LITTER);
	public static final Item ORANGE_MAPLE_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.ORANGE_MAPLE_LEAF_LITTER, WWBlocks.ORANGE_MAPLE_LEAF_LITTER);
	public static final Item RED_MAPLE_LEAF_LITTER = Items.registerBlock(WWBlockItemIds.RED_MAPLE_LEAF_LITTER, WWBlocks.RED_MAPLE_LEAF_LITTER);

	// SCULK
	public static final Item SCULK_STAIRS = Items.registerBlock(WWBlockItemIds.SCULK_STAIRS, WWBlocks.SCULK_STAIRS);
	public static final Item SCULK_SLAB = Items.registerBlock(WWBlockItemIds.SCULK_SLAB, WWBlocks.SCULK_SLAB);
	public static final Item SCULK_WALL = Items.registerBlock(WWBlockItemIds.SCULK_WALL, WWBlocks.SCULK_WALL);
	public static final Item OSSEOUS_SCULK = Items.registerBlock(WWBlockItemIds.OSSEOUS_SCULK, WWBlocks.OSSEOUS_SCULK);
	public static final Item HANGING_TENDRIL = Items.registerBlock(WWBlockItemIds.HANGING_TENDRIL, WWBlocks.HANGING_TENDRIL);
	public static final Item ECHO_GLASS = Items.registerBlock(WWBlockItemIds.ECHO_GLASS, WWBlocks.ECHO_GLASS);

	// MESOGLEA
	public static final Item PEARLESCENT_BLUE_MESOGLEA = Items.registerBlock(WWBlockItemIds.PEARLESCENT_BLUE_MESOGLEA, WWBlocks.PEARLESCENT_BLUE_MESOGLEA);
	public static final Item PEARLESCENT_PURPLE_MESOGLEA = Items.registerBlock(WWBlockItemIds.PEARLESCENT_PURPLE_MESOGLEA, WWBlocks.PEARLESCENT_PURPLE_MESOGLEA);
	public static final Item YELLOW_MESOGLEA = Items.registerBlock(WWBlockItemIds.YELLOW_MESOGLEA, WWBlocks.YELLOW_MESOGLEA);
	public static final Item BLUE_MESOGLEA = Items.registerBlock(WWBlockItemIds.BLUE_MESOGLEA, WWBlocks.BLUE_MESOGLEA);
	public static final Item LIME_MESOGLEA = Items.registerBlock(WWBlockItemIds.LIME_MESOGLEA, WWBlocks.LIME_MESOGLEA);
	public static final Item RED_MESOGLEA = Items.registerBlock(WWBlockItemIds.RED_MESOGLEA, WWBlocks.RED_MESOGLEA);
	public static final Item PINK_MESOGLEA = Items.registerBlock(WWBlockItemIds.PINK_MESOGLEA, WWBlocks.PINK_MESOGLEA);

	// NEMATOCYST
	public static final Item PEARLESCENT_BLUE_NEMATOCYST = Items.registerBlock(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST, WWBlocks.PEARLESCENT_BLUE_NEMATOCYST);
	public static final Item PEARLESCENT_PURPLE_NEMATOCYST = Items.registerBlock(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST, WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST);
	public static final Item YELLOW_NEMATOCYST = Items.registerBlock(WWBlockItemIds.YELLOW_NEMATOCYST, WWBlocks.YELLOW_NEMATOCYST);
	public static final Item BLUE_NEMATOCYST = Items.registerBlock(WWBlockItemIds.BLUE_NEMATOCYST, WWBlocks.BLUE_NEMATOCYST);
	public static final Item LIME_NEMATOCYST = Items.registerBlock(WWBlockItemIds.LIME_NEMATOCYST, WWBlocks.LIME_NEMATOCYST);
	public static final Item RED_NEMATOCYST = Items.registerBlock(WWBlockItemIds.RED_NEMATOCYST, WWBlocks.RED_NEMATOCYST);
	public static final Item PINK_NEMATOCYST = Items.registerBlock(WWBlockItemIds.PINK_NEMATOCYST, WWBlocks.PINK_NEMATOCYST);

	// MISC
	public static final Item TERMITE_MOUND = Items.registerBlock(WWBlockItemIds.TERMITE_MOUND, WWBlocks.TERMITE_MOUND);
	public static final Item STONE_CHEST = Items.registerBlock(WWBlockItemIds.STONE_CHEST, WWBlocks.STONE_CHEST);
	public static final Item NULL_BLOCK = Items.registerBlock(WWBlockItemIds.NULL_BLOCK, WWBlocks.NULL_BLOCK);
	public static final Item DISPLAY_LANTERN = Items.registerBlock(WWBlockItemIds.DISPLAY_LANTERN, WWBlocks.DISPLAY_LANTERN,
		new Item.Properties().component(WWDataComponents.FIREFLIES, ImmutableList.of())
	);

	// FLOWERS
	public static final Item SEEDING_DANDELION = Items.registerBlock(WWBlockItemIds.SEEDING_DANDELION, WWBlocks.SEEDING_DANDELION);
	public static final Item CARNATION = Items.registerBlock(WWBlockItemIds.CARNATION, WWBlocks.CARNATION);
	public static final Item MARIGOLD = Items.registerBlock(WWBlockItemIds.MARIGOLD, WWBlocks.MARIGOLD);
	public static final Item PASQUEFLOWER = Items.registerBlock(WWBlockItemIds.PASQUEFLOWER, WWBlocks.PASQUEFLOWER);
	public static final Item RED_HIBISCUS = Items.registerBlock(WWBlockItemIds.RED_HIBISCUS, WWBlocks.RED_HIBISCUS);
	public static final Item YELLOW_HIBISCUS = Items.registerBlock(WWBlockItemIds.YELLOW_HIBISCUS, WWBlocks.YELLOW_HIBISCUS);
	public static final Item WHITE_HIBISCUS = Items.registerBlock(WWBlockItemIds.WHITE_HIBISCUS, WWBlocks.WHITE_HIBISCUS);
	public static final Item PINK_HIBISCUS = Items.registerBlock(WWBlockItemIds.PINK_HIBISCUS, WWBlocks.PINK_HIBISCUS);
	public static final Item PURPLE_HIBISCUS = Items.registerBlock(WWBlockItemIds.PURPLE_HIBISCUS, WWBlocks.PURPLE_HIBISCUS);

	// FLOWERBEDS
	public static final Item PHLOX = Items.registerBlock(WWBlockItemIds.PHLOX, WWBlocks.PHLOX);
	public static final Item LANTANAS = Items.registerBlock(WWBlockItemIds.LANTANAS, WWBlocks.LANTANAS);
	public static final Item CLOVERS = Items.registerBlock(WWBlockItemIds.CLOVERS, WWBlocks.CLOVERS);

	// TALL FLOWERS
	public static final Item DATURA = Items.registerBlock(WWBlockItemIds.DATURA, WWBlocks.DATURA, DoubleHighBlockItem::new);
	public static final Item MILKWEED = Items.registerBlock(WWBlockItemIds.MILKWEED, WWBlocks.MILKWEED, DoubleHighBlockItem::new);

	// VEGETATION
	public static final Item POLLEN = Items.registerBlock(WWBlockItemIds.POLLEN, WWBlocks.POLLEN);
	public static final Item PRICKLY_PEAR = Items.registerBlock(WWBlockItemIds.PRICKLY_PEAR, WWBlocks.PRICKLY_PEAR,
		(block, properties) -> new DamageOnUseBlockItem(block, properties, 2F, WWSounds.PLAYER_HURT_CACTUS, WWDamageTypes.PRICKLY_PEAR),
		new Item.Properties().food(WWFoods.PRICKLY_PEAR)
	);
	public static final Item SHRUB = Items.registerBlock(WWBlockItemIds.SHRUB, WWBlocks.SHRUB);
	public static final Item TUMBLEWEED_PLANT = Items.registerBlock(WWBlockItemIds.TUMBLEWEED_PLANT, WWBlocks.TUMBLEWEED_PLANT);
	public static final Item TUMBLEWEED = Items.registerBlock(WWBlockItemIds.TUMBLEWEED, WWBlocks.TUMBLEWEED);
	public static final Item FROZEN_SHORT_GRASS = Items.registerBlock(WWBlockItemIds.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_SHORT_GRASS);
	public static final Item FROZEN_TALL_GRASS = Items.registerBlock(WWBlockItemIds.FROZEN_TALL_GRASS, WWBlocks.FROZEN_TALL_GRASS, DoubleHighBlockItem::new);
	public static final Item FROZEN_FERN = Items.registerBlock(WWBlockItemIds.FROZEN_FERN, WWBlocks.FROZEN_FERN);
	public static final Item FROZEN_LARGE_FERN = Items.registerBlock(WWBlockItemIds.FROZEN_LARGE_FERN, WWBlocks.FROZEN_LARGE_FERN, DoubleHighBlockItem::new);
	public static final Item FROZEN_BUSH = Items.registerBlock(WWBlockItemIds.FROZEN_BUSH, WWBlocks.FROZEN_BUSH);
	public static final Item MYCELIUM_GROWTH = Items.registerBlock(WWBlockItemIds.MYCELIUM_GROWTH, WWBlocks.MYCELIUM_GROWTH);

	// MUSHROOMS
	public static final Item BROWN_SHELF_FUNGI = Items.registerBlock(WWBlockItemIds.BROWN_SHELF_FUNGI, WWBlocks.BROWN_SHELF_FUNGI);
	public static final Item RED_SHELF_FUNGI = Items.registerBlock(WWBlockItemIds.RED_SHELF_FUNGI, WWBlocks.RED_SHELF_FUNGI);
	public static final Item CRIMSON_SHELF_FUNGI = Items.registerBlock(WWBlockItemIds.CRIMSON_SHELF_FUNGI, WWBlocks.CRIMSON_SHELF_FUNGI);
	public static final Item WARPED_SHELF_FUNGI = Items.registerBlock(WWBlockItemIds.WARPED_SHELF_FUNGI, WWBlocks.WARPED_SHELF_FUNGI);

	public static final Item PALE_MUSHROOM_BLOCK = Items.registerBlock(WWBlockItemIds.PALE_MUSHROOM_BLOCK, WWBlocks.PALE_MUSHROOM_BLOCK);
	public static final Item PALE_MUSHROOM = Items.registerBlock(WWBlockItemIds.PALE_MUSHROOM, WWBlocks.PALE_MUSHROOM);
	public static final Item PALE_SHELF_FUNGI = Items.registerBlock(WWBlockItemIds.PALE_SHELF_FUNGI, WWBlocks.PALE_SHELF_FUNGI);

	// MOSS
	public static final Item AUBURN_MOSS_BLOCK = Items.registerBlock(WWBlockItemIds.AUBURN_MOSS_BLOCK, WWBlocks.AUBURN_MOSS_BLOCK);
	public static final Item AUBURN_MOSS_CARPET = Items.registerBlock(WWBlockItemIds.AUBURN_MOSS_CARPET, WWBlocks.AUBURN_MOSS_CARPET);
	public static final Item AUBURN_CREEPING_MOSS = Items.registerBlock(WWBlockItemIds.AUBURN_CREEPING_MOSS, WWBlocks.AUBURN_CREEPING_MOSS);

	// AQUATIC
	public static final Item CATTAIL = Items.registerBlock(WWBlockItemIds.CATTAIL, WWBlocks.CATTAIL, DoubleHighBlockItem::new);
	public static final Item FLOWERING_LILY_PAD = Items.registerBlock(WWBlockItemIds.FLOWERING_LILY_PAD, WWBlocks.FLOWERING_LILY_PAD, PlaceOnWaterBlockItem::new);
	public static final Item ALGAE = Items.registerBlock(WWBlockItemIds.ALGAE, WWBlocks.ALGAE, PlaceOnWaterBlockItem::new);
	public static final Item PLANKTON = Items.registerBlock(WWBlockItemIds.PLANKTON, WWBlocks.PLANKTON, PlaceOnWaterBlockItem::new);
	public static final Item SPONGE_BUD = Items.registerBlock(WWBlockItemIds.SPONGE_BUD, WWBlocks.SPONGE_BUD);
	public static final Item BARNACLES = Items.registerBlock(WWBlockItemIds.BARNACLES, WWBlocks.BARNACLES);
	public static final Item SEA_ANEMONE = Items.registerBlock(WWBlockItemIds.SEA_ANEMONE, WWBlocks.SEA_ANEMONE);
	public static final Item SEA_WHIP = Items.registerBlock(WWBlockItemIds.SEA_WHIP, WWBlocks.SEA_WHIP);
	public static final Item TUBE_WORMS = Items.registerBlock(WWBlockItemIds.TUBE_WORMS, WWBlocks.TUBE_WORMS);

	// EGGS
	public static final Item OSTRICH_EGG = Items.registerBlock(WWBlockItemIds.OSTRICH_EGG, WWBlocks.OSTRICH_EGG);
	public static final Item PENGUIN_EGG = Items.registerBlock(WWBlockItemIds.PENGUIN_EGG, WWBlocks.PENGUIN_EGG);

	// GABBRO
	public static final Item GABBRO = Items.registerBlock(WWBlockItemIds.GABBRO, WWBlocks.GABBRO);
	public static final Item GABBRO_STAIRS = Items.registerBlock(WWBlockItemIds.GABBRO_STAIRS, WWBlocks.GABBRO_STAIRS);
	public static final Item GABBRO_SLAB = Items.registerBlock(WWBlockItemIds.GABBRO_SLAB, WWBlocks.GABBRO_SLAB);
	public static final Item GABBRO_WALL = Items.registerBlock(WWBlockItemIds.GABBRO_WALL, WWBlocks.GABBRO_WALL);

	public static final Item GEYSER = Items.registerBlock(WWBlockItemIds.GEYSER, WWBlocks.GEYSER);

	public static final Item POLISHED_GABBRO = Items.registerBlock(WWBlockItemIds.POLISHED_GABBRO, WWBlocks.POLISHED_GABBRO);
	public static final Item POLISHED_GABBRO_STAIRS = Items.registerBlock(WWBlockItemIds.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO_STAIRS);
	public static final Item POLISHED_GABBRO_SLAB = Items.registerBlock(WWBlockItemIds.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO_SLAB);
	public static final Item POLISHED_GABBRO_WALL = Items.registerBlock(WWBlockItemIds.POLISHED_GABBRO_WALL, WWBlocks.POLISHED_GABBRO_WALL);

	public static final Item GABBRO_BRICKS = Items.registerBlock(WWBlockItemIds.GABBRO_BRICKS, WWBlocks.GABBRO_BRICKS);
	public static final Item GABBRO_BRICK_STAIRS = Items.registerBlock(WWBlockItemIds.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICK_STAIRS);
	public static final Item GABBRO_BRICK_SLAB = Items.registerBlock(WWBlockItemIds.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICK_SLAB);
	public static final Item GABBRO_BRICK_WALL = Items.registerBlock(WWBlockItemIds.GABBRO_BRICK_WALL, WWBlocks.GABBRO_BRICK_WALL);
	public static final Item CRACKED_GABBRO_BRICKS = Items.registerBlock(WWBlockItemIds.CRACKED_GABBRO_BRICKS, WWBlocks.CRACKED_GABBRO_BRICKS);
	public static final Item CHISELED_GABBRO_BRICKS = Items.registerBlock(WWBlockItemIds.CHISELED_GABBRO_BRICKS, WWBlocks.CHISELED_GABBRO_BRICKS);

	public static final Item MOSSY_GABBRO_BRICKS = Items.registerBlock(WWBlockItemIds.MOSSY_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICKS);
	public static final Item MOSSY_GABBRO_BRICK_STAIRS = Items.registerBlock(WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICK_STAIRS);
	public static final Item MOSSY_GABBRO_BRICK_SLAB = Items.registerBlock(WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICK_SLAB);
	public static final Item MOSSY_GABBRO_BRICK_WALL = Items.registerBlock(WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL, WWBlocks.MOSSY_GABBRO_BRICK_WALL);

	// BAOBAB
	public static final Item BAOBAB_PLANKS = Items.registerBlock(WWBlockItemIds.BAOBAB_PLANKS, WWBlocks.BAOBAB_PLANKS);
	public static final Item BAOBAB_STAIRS = Items.registerBlock(WWBlockItemIds.BAOBAB_STAIRS, WWBlocks.BAOBAB_STAIRS);
	public static final Item BAOBAB_FENCE_GATE = Items.registerBlock(WWBlockItemIds.BAOBAB_FENCE_GATE, WWBlocks.BAOBAB_FENCE_GATE);
	public static final Item BAOBAB_SLAB = Items.registerBlock(WWBlockItemIds.BAOBAB_SLAB, WWBlocks.BAOBAB_SLAB);
	public static final Item BAOBAB_PRESSURE_PLATE = Items.registerBlock(WWBlockItemIds.BAOBAB_PRESSURE_PLATE, WWBlocks.BAOBAB_PRESSURE_PLATE);
	public static final Item BAOBAB_BUTTON = Items.registerBlock(WWBlockItemIds.BAOBAB_BUTTON, WWBlocks.BAOBAB_BUTTON);
	public static final Item BAOBAB_DOOR = Items.registerBlock(WWBlockItemIds.BAOBAB_DOOR, WWBlocks.BAOBAB_DOOR, DoubleHighBlockItem::new);
	public static final Item BAOBAB_TRAPDOOR = Items.registerBlock(WWBlockItemIds.BAOBAB_TRAPDOOR, WWBlocks.BAOBAB_TRAPDOOR);
	public static final Item BAOBAB_FENCE = Items.registerBlock(WWBlockItemIds.BAOBAB_FENCE, WWBlocks.BAOBAB_FENCE);
	public static final Item BAOBAB_LOG = Items.registerBlock(WWBlockItemIds.BAOBAB_LOG, WWBlocks.BAOBAB_LOG);
	public static final Item STRIPPED_BAOBAB_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG);
	public static final Item STRIPPED_HOLLOWED_BAOBAB_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
	public static final Item BAOBAB_WOOD = Items.registerBlock(WWBlockItemIds.BAOBAB_WOOD, WWBlocks.BAOBAB_WOOD);
	public static final Item STRIPPED_BAOBAB_WOOD = Items.registerBlock(WWBlockItemIds.STRIPPED_BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_WOOD);
	public static final Item BAOBAB_SIGN = Items.registerBlock(WWBlockItemIds.BAOBAB_SIGN, WWBlocks.BAOBAB_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.BAOBAB_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item BAOBAB_HANGING_SIGN = Items.registerBlock(WWBlockItemIds.BAOBAB_HANGING_SIGN, WWBlocks.BAOBAB_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.BAOBAB_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item BAOBAB_SHELF = Items.registerBlock(WWBlockItemIds.BAOBAB_SHELF, WWBlocks.BAOBAB_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
	);

	// WILLOW
	public static final Item WILLOW_PLANKS = Items.registerBlock(WWBlockItemIds.WILLOW_PLANKS, WWBlocks.WILLOW_PLANKS);
	public static final Item WILLOW_STAIRS = Items.registerBlock(WWBlockItemIds.WILLOW_STAIRS, WWBlocks.WILLOW_STAIRS);
	public static final Item WILLOW_FENCE_GATE = Items.registerBlock(WWBlockItemIds.WILLOW_FENCE_GATE, WWBlocks.WILLOW_FENCE_GATE);
	public static final Item WILLOW_SLAB = Items.registerBlock(WWBlockItemIds.WILLOW_SLAB, WWBlocks.WILLOW_SLAB);
	public static final Item WILLOW_PRESSURE_PLATE = Items.registerBlock(WWBlockItemIds.WILLOW_PRESSURE_PLATE, WWBlocks.WILLOW_PRESSURE_PLATE);
	public static final Item WILLOW_BUTTON = Items.registerBlock(WWBlockItemIds.WILLOW_BUTTON, WWBlocks.WILLOW_BUTTON);
	public static final Item WILLOW_DOOR = Items.registerBlock(WWBlockItemIds.WILLOW_DOOR, WWBlocks.WILLOW_DOOR, DoubleHighBlockItem::new);
	public static final Item WILLOW_TRAPDOOR = Items.registerBlock(WWBlockItemIds.WILLOW_TRAPDOOR, WWBlocks.WILLOW_TRAPDOOR);
	public static final Item WILLOW_FENCE = Items.registerBlock(WWBlockItemIds.WILLOW_FENCE, WWBlocks.WILLOW_FENCE);
	public static final Item WILLOW_LOG = Items.registerBlock(WWBlockItemIds.WILLOW_LOG, WWBlocks.WILLOW_LOG);
	public static final Item STRIPPED_WILLOW_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG);
	public static final Item STRIPPED_HOLLOWED_WILLOW_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
	public static final Item WILLOW_WOOD = Items.registerBlock(WWBlockItemIds.WILLOW_WOOD, WWBlocks.WILLOW_WOOD);
	public static final Item STRIPPED_WILLOW_WOOD = Items.registerBlock(WWBlockItemIds.STRIPPED_WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_WOOD);
	public static final Item WILLOW_SIGN = Items.registerBlock(WWBlockItemIds.WILLOW_SIGN, WWBlocks.WILLOW_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.WILLOW_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item WILLOW_HANGING_SIGN = Items.registerBlock(WWBlockItemIds.WILLOW_HANGING_SIGN, WWBlocks.WILLOW_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.WILLOW_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item WILLOW_SHELF = Items.registerBlock(WWBlockItemIds.WILLOW_SHELF, WWBlocks.WILLOW_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
	);

	// CYPRESS
	public static final Item CYPRESS_PLANKS = Items.registerBlock(WWBlockItemIds.CYPRESS_PLANKS, WWBlocks.CYPRESS_PLANKS);
	public static final Item CYPRESS_STAIRS = Items.registerBlock(WWBlockItemIds.CYPRESS_STAIRS, WWBlocks.CYPRESS_STAIRS);
	public static final Item CYPRESS_FENCE_GATE = Items.registerBlock(WWBlockItemIds.CYPRESS_FENCE_GATE, WWBlocks.CYPRESS_FENCE_GATE);
	public static final Item CYPRESS_SLAB = Items.registerBlock(WWBlockItemIds.CYPRESS_SLAB, WWBlocks.CYPRESS_SLAB);
	public static final Item CYPRESS_PRESSURE_PLATE = Items.registerBlock(WWBlockItemIds.CYPRESS_PRESSURE_PLATE, WWBlocks.CYPRESS_PRESSURE_PLATE);
	public static final Item CYPRESS_BUTTON = Items.registerBlock(WWBlockItemIds.CYPRESS_BUTTON, WWBlocks.CYPRESS_BUTTON);
	public static final Item CYPRESS_DOOR = Items.registerBlock(WWBlockItemIds.CYPRESS_DOOR, WWBlocks.CYPRESS_DOOR, DoubleHighBlockItem::new);
	public static final Item CYPRESS_TRAPDOOR = Items.registerBlock(WWBlockItemIds.CYPRESS_TRAPDOOR, WWBlocks.CYPRESS_TRAPDOOR);
	public static final Item CYPRESS_FENCE = Items.registerBlock(WWBlockItemIds.CYPRESS_FENCE, WWBlocks.CYPRESS_FENCE);
	public static final Item CYPRESS_LOG = Items.registerBlock(WWBlockItemIds.CYPRESS_LOG, WWBlocks.CYPRESS_LOG);
	public static final Item STRIPPED_CYPRESS_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG);
	public static final Item STRIPPED_HOLLOWED_CYPRESS_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
	public static final Item CYPRESS_WOOD = Items.registerBlock(WWBlockItemIds.CYPRESS_WOOD, WWBlocks.CYPRESS_WOOD);
	public static final Item STRIPPED_CYPRESS_WOOD = Items.registerBlock(WWBlockItemIds.STRIPPED_CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_WOOD);
	public static final Item CYPRESS_SIGN = Items.registerBlock(WWBlockItemIds.CYPRESS_SIGN, WWBlocks.CYPRESS_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.CYPRESS_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item CYPRESS_HANGING_SIGN = Items.registerBlock(WWBlockItemIds.CYPRESS_HANGING_SIGN, WWBlocks.CYPRESS_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.CYPRESS_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item CYPRESS_SHELF = Items.registerBlock(WWBlockItemIds.CYPRESS_SHELF, WWBlocks.CYPRESS_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
	);

	// PALM
	public static final Item PALM_PLANKS = Items.registerBlock(WWBlockItemIds.PALM_PLANKS, WWBlocks.PALM_PLANKS);
	public static final Item PALM_STAIRS = Items.registerBlock(WWBlockItemIds.PALM_STAIRS, WWBlocks.PALM_STAIRS);
	public static final Item PALM_FENCE_GATE = Items.registerBlock(WWBlockItemIds.PALM_FENCE_GATE, WWBlocks.PALM_FENCE_GATE);
	public static final Item PALM_SLAB = Items.registerBlock(WWBlockItemIds.PALM_SLAB, WWBlocks.PALM_SLAB);
	public static final Item PALM_PRESSURE_PLATE = Items.registerBlock(WWBlockItemIds.PALM_PRESSURE_PLATE, WWBlocks.PALM_PRESSURE_PLATE);
	public static final Item PALM_BUTTON = Items.registerBlock(WWBlockItemIds.PALM_BUTTON, WWBlocks.PALM_BUTTON);
	public static final Item PALM_DOOR = Items.registerBlock(WWBlockItemIds.PALM_DOOR, WWBlocks.PALM_DOOR, DoubleHighBlockItem::new);
	public static final Item PALM_TRAPDOOR = Items.registerBlock(WWBlockItemIds.PALM_TRAPDOOR, WWBlocks.PALM_TRAPDOOR);
	public static final Item PALM_FENCE = Items.registerBlock(WWBlockItemIds.PALM_FENCE, WWBlocks.PALM_FENCE);
	public static final Item PALM_LOG = Items.registerBlock(WWBlockItemIds.PALM_LOG, WWBlocks.PALM_LOG);
	public static final Item STRIPPED_PALM_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_PALM_LOG);
	public static final Item STRIPPED_HOLLOWED_PALM_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
	public static final Item PALM_WOOD = Items.registerBlock(WWBlockItemIds.PALM_WOOD, WWBlocks.PALM_WOOD);
	public static final Item STRIPPED_PALM_WOOD = Items.registerBlock(WWBlockItemIds.STRIPPED_PALM_WOOD, WWBlocks.STRIPPED_PALM_WOOD);
	public static final Item PALM_SIGN = Items.registerBlock(WWBlockItemIds.PALM_SIGN, WWBlocks.PALM_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.PALM_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item PALM_HANGING_SIGN = Items.registerBlock(WWBlockItemIds.PALM_HANGING_SIGN, WWBlocks.PALM_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.PALM_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item PALM_SHELF = Items.registerBlock(WWBlockItemIds.PALM_SHELF, WWBlocks.PALM_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
	);

	// MAPLE
	public static final Item MAPLE_PLANKS = Items.registerBlock(WWBlockItemIds.MAPLE_PLANKS, WWBlocks.MAPLE_PLANKS);
	public static final Item MAPLE_STAIRS = Items.registerBlock(WWBlockItemIds.MAPLE_STAIRS, WWBlocks.MAPLE_STAIRS);
	public static final Item MAPLE_FENCE_GATE = Items.registerBlock(WWBlockItemIds.MAPLE_FENCE_GATE, WWBlocks.MAPLE_FENCE_GATE);
	public static final Item MAPLE_SLAB = Items.registerBlock(WWBlockItemIds.MAPLE_SLAB, WWBlocks.MAPLE_SLAB);
	public static final Item MAPLE_PRESSURE_PLATE = Items.registerBlock(WWBlockItemIds.MAPLE_PRESSURE_PLATE, WWBlocks.MAPLE_PRESSURE_PLATE);
	public static final Item MAPLE_BUTTON = Items.registerBlock(WWBlockItemIds.MAPLE_BUTTON, WWBlocks.MAPLE_BUTTON);
	public static final Item MAPLE_DOOR = Items.registerBlock(WWBlockItemIds.MAPLE_DOOR, WWBlocks.MAPLE_DOOR, DoubleHighBlockItem::new);
	public static final Item MAPLE_TRAPDOOR = Items.registerBlock(WWBlockItemIds.MAPLE_TRAPDOOR, WWBlocks.MAPLE_TRAPDOOR);
	public static final Item MAPLE_FENCE = Items.registerBlock(WWBlockItemIds.MAPLE_FENCE, WWBlocks.MAPLE_FENCE);
	public static final Item MAPLE_LOG = Items.registerBlock(WWBlockItemIds.MAPLE_LOG, WWBlocks.MAPLE_LOG);
	public static final Item STRIPPED_MAPLE_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG);
	public static final Item STRIPPED_HOLLOWED_MAPLE_LOG = Items.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
	public static final Item MAPLE_WOOD = Items.registerBlock(WWBlockItemIds.MAPLE_WOOD, WWBlocks.MAPLE_WOOD);
	public static final Item STRIPPED_MAPLE_WOOD = Items.registerBlock(WWBlockItemIds.STRIPPED_MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_WOOD);
	public static final Item MAPLE_SIGN = Items.registerBlock(WWBlockItemIds.MAPLE_SIGN, WWBlocks.MAPLE_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.MAPLE_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item MAPLE_HANGING_SIGN = Items.registerBlock(WWBlockItemIds.MAPLE_HANGING_SIGN, WWBlocks.MAPLE_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.MAPLE_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item MAPLE_SHELF = Items.registerBlock(WWBlockItemIds.MAPLE_SHELF, WWBlocks.MAPLE_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
	);

	// ICE
	public static final Item FRAGILE_ICE = Items.registerBlock(WWBlockItemIds.FRAGILE_ICE, WWBlocks.FRAGILE_ICE);
	public static final Item ICICLE = Items.registerBlock(WWBlockItemIds.ICICLE, WWBlocks.ICICLE);

	// FROGLIGHT GOOP
	public static final Item OCHRE_FROGLIGHT_GOOP = Items.registerBlock(WWBlockItemIds.OCHRE_FROGLIGHT_GOOP, WWBlocks.OCHRE_FROGLIGHT_GOOP);
	public static final Item VERDANT_FROGLIGHT_GOOP = Items.registerBlock(WWBlockItemIds.VERDANT_FROGLIGHT_GOOP, WWBlocks.VERDANT_FROGLIGHT_GOOP);
	public static final Item PEARLESCENT_FROGLIGHT_GOOP = Items.registerBlock(WWBlockItemIds.PEARLESCENT_FROGLIGHT_GOOP, WWBlocks.PEARLESCENT_FROGLIGHT_GOOP);

	// ITEMS
	// BOATS
	public static final Item BAOBAB_BOAT = registerBoatItem(WWItemIds.BAOBAB_BOAT, WWEntityTypes.BAOBAB_BOAT);
	public static final Item BAOBAB_CHEST_BOAT = registerBoatItem(WWItemIds.BAOBAB_CHEST_BOAT, WWEntityTypes.BAOBAB_CHEST_BOAT);
	public static final Item WILLOW_BOAT = registerBoatItem(WWItemIds.WILLOW_BOAT, WWEntityTypes.WILLOW_BOAT);
	public static final Item WILLOW_CHEST_BOAT = registerBoatItem(WWItemIds.WILLOW_CHEST_BOAT, WWEntityTypes.WILLOW_CHEST_BOAT);
	public static final Item CYPRESS_BOAT = registerBoatItem(WWItemIds.CYPRESS_BOAT, WWEntityTypes.CYPRESS_BOAT);
	public static final Item CYPRESS_CHEST_BOAT = registerBoatItem(WWItemIds.CYPRESS_CHEST_BOAT, WWEntityTypes.CYPRESS_CHEST_BOAT);
	public static final Item PALM_BOAT = registerBoatItem(WWItemIds.PALM_BOAT, WWEntityTypes.PALM_BOAT);
	public static final Item PALM_CHEST_BOAT = registerBoatItem(WWItemIds.PALM_CHEST_BOAT, WWEntityTypes.PALM_CHEST_BOAT);
	public static final Item MAPLE_BOAT = registerBoatItem(WWItemIds.MAPLE_BOAT, WWEntityTypes.MAPLE_BOAT);
	public static final Item MAPLE_CHEST_BOAT = registerBoatItem(WWItemIds.MAPLE_CHEST_BOAT, WWEntityTypes.MAPLE_CHEST_BOAT);

	// ITEMS
	public static final Item MILKWEED_POD = Items.registerItem(WWItemIds.MILKWEED_POD, MilkweedPodItem::new);
	public static final Item FIREFLY_BOTTLE = Items.registerItem(WWItemIds.FIREFLY_BOTTLE,
		properties -> new MobBottleItem(
			WWEntityTypes.FIREFLY,
			WWSounds.ITEM_BOTTLE_RELEASE_FIREFLY,
			properties
		),
		new Item.Properties()
			.stacksTo(16)
			.component(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.EMPTY)
			.delayedComponent(WWDataComponents.FIREFLY_COLOR, context -> context.lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).getOrThrow(FireflyColors.DEFAULT))
	);
	public static final Item BUTTERFLY_BOTTLE = Items.registerItem(WWItemIds.BUTTERFLY_BOTTLE,
		properties -> new MobBottleItem(
			WWEntityTypes.BUTTERFLY,
			WWSounds.ITEM_BOTTLE_RELEASE_BUTTERFLY,
			properties
		),
		new Item.Properties().stacksTo(1).component(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.EMPTY)
	);

	// FOOD
	public static final Item PEELED_PRICKLY_PEAR = Items.registerItem(WWItemIds.PEELED_PRICKLY_PEAR, new Item.Properties().food(Foods.APPLE));
	public static final Item SPLIT_COCONUT = Items.registerItem(WWItemIds.SPLIT_COCONUT, new Item.Properties().food(WWFoods.SPLIT_COCONUT));
	public static final Item CRAB_CLAW = Items.registerItem(WWItemIds.CRAB_CLAW, CrabClawItem::new, new Item.Properties().food(WWFoods.CRAB_CLAW));
	public static final Item COOKED_CRAB_CLAW = Items.registerItem(WWItemIds.COOKED_CRAB_CLAW, new Item.Properties().food(WWFoods.COOKED_CRAB_CLAW));
	public static final Item SCORCHED_EYE = Items.registerItem(WWItemIds.SCORCHED_EYE, new Item.Properties().food(WWFoods.SCORCHED_EYE, WWFoods.SCORCHED_EYE_CONSUMABLE));
	public static final Item FERMENTED_SCORCHED_EYE = Items.registerItem(WWItemIds.FERMENTED_SCORCHED_EYE);

	// SPAWN EGGS & BUCKETS
	public static final Item JELLYFISH_BUCKET = Items.registerItem(
		WWItemIds.JELLYFISH_BUCKET,
		properties -> new MobBucketItem(WWEntityTypes.JELLYFISH, Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_JELLYFISH, properties),
		new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
	);
	public static final Item CRAB_BUCKET = Items.registerItem(
		WWItemIds.CRAB_BUCKET,
		properties -> new MobBucketItem(WWEntityTypes.CRAB, Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_CRAB, properties),
		new Item.Properties().stacksTo(1).component(DataComponents.FOOD, WWFoods.CRAB_CLAW)
	);

	public static final Item FIREFLY_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.FIREFLY_SPAWN_EGG, WWEntityTypes.FIREFLY);
	public static final Item JELLYFISH_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.JELLYFISH_SPAWN_EGG, WWEntityTypes.JELLYFISH);
	public static final Item CRAB_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.CRAB_SPAWN_EGG, WWEntityTypes.CRAB);
	public static final Item OSTRICH_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.OSTRICH_SPAWN_EGG, WWEntityTypes.OSTRICH);
	public static final Item ZOMBIE_OSTRICH_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.ZOMBIE_OSTRICH_SPAWN_EGG, WWEntityTypes.ZOMBIE_OSTRICH);
	public static final Item SCORCHED_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.SCORCHED_SPAWN_EGG, WWEntityTypes.SCORCHED);
	public static final Item BUTTERFLY_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.BUTTERFLY_SPAWN_EGG, WWEntityTypes.BUTTERFLY);
	public static final Item MOOBLOOM_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.MOOBLOOM_SPAWN_EGG, WWEntityTypes.MOOBLOOM);
	public static final Item PENGUIN_SPAWN_EGG = Items.registerSpawnEgg(WWItemIds.PENGUIN_SPAWN_EGG, WWEntityTypes.PENGUIN);

	private WWItems() {
		throw new UnsupportedOperationException("WWItems contains only static declarations.");
	}

	public static void init() {
		CompostableRegistry.INSTANCE.add(BAOBAB_NUT, 0.3F);
		CompostableRegistry.INSTANCE.add(MILKWEED_POD, 0.25F);
		CompostableRegistry.INSTANCE.add(SPLIT_COCONUT, 0.15F);
		CompostableRegistry.INSTANCE.add(COCONUT, 0.3F);
		registerDispenses();
	}

	private static void registerDispenses() {
		DispenserBlock.registerBehavior(BAOBAB_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.BAOBAB_BOAT));
		DispenserBlock.registerBehavior(BAOBAB_CHEST_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.BAOBAB_CHEST_BOAT));
		DispenserBlock.registerBehavior(WILLOW_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.WILLOW_BOAT));
		DispenserBlock.registerBehavior(WILLOW_CHEST_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.WILLOW_CHEST_BOAT));
		DispenserBlock.registerBehavior(CYPRESS_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.CYPRESS_BOAT));
		DispenserBlock.registerBehavior(CYPRESS_CHEST_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.CYPRESS_CHEST_BOAT));
		DispenserBlock.registerBehavior(PALM_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.PALM_BOAT));
		DispenserBlock.registerBehavior(PALM_CHEST_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.PALM_CHEST_BOAT));
		DispenserBlock.registerBehavior(MAPLE_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.MAPLE_BOAT));
		DispenserBlock.registerBehavior(MAPLE_CHEST_BOAT, new BoatDispenseItemBehavior(WWEntityTypes.MAPLE_CHEST_BOAT));
	}

	private static Item registerBoatItem(ResourceKey<Item> id, EntityType<? extends AbstractBoat> boat) {
		return Items.registerItem(id, properties -> new BoatItem(boat, properties), new Item.Properties().stacksTo(1));
	}
}
