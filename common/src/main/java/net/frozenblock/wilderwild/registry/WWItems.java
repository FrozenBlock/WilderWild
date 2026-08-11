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

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.item.api.DamageOnUseBlockItem;
import net.frozenblock.lib.item.api.component.ItemTooltipAdditionAPI;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredItem;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.item.CoconutItem;
import net.frozenblock.wilderwild.item.CrabClawItem;
import net.frozenblock.wilderwild.item.MilkweedPodItem;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.references.WWItemIds;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.ChatFormatting;

public final class WWItems {
	private static final FrozenDeferredRegister.Items REGISTER = FrozenDeferredRegister.createItems(WWConstants.MOD_ID);

	// BLOCK ITEMS
	// MUD
	public static final FrozenDeferredItem<BlockItem> CHISELED_MUD_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CHISELED_MUD_BRICKS, WWBlocks.CHISELED_MUD_BRICKS);
	public static final FrozenDeferredItem<BlockItem> CRACKED_MUD_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CRACKED_MUD_BRICKS, WWBlocks.CRACKED_MUD_BRICKS);
	public static final FrozenDeferredItem<BlockItem> MOSSY_MUD_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_MUD_BRICKS, WWBlocks.MOSSY_MUD_BRICKS);
	public static final FrozenDeferredItem<BlockItem> MOSSY_MUD_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS, WWBlocks.MOSSY_MUD_BRICK_STAIRS);
	public static final FrozenDeferredItem<BlockItem> MOSSY_MUD_BRICK_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_MUD_BRICK_SLAB, WWBlocks.MOSSY_MUD_BRICK_SLAB);
	public static final FrozenDeferredItem<BlockItem> MOSSY_MUD_BRICK_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_MUD_BRICK_WALL, WWBlocks.MOSSY_MUD_BRICK_WALL);

	// SAND
	public static final FrozenDeferredItem<BlockItem> SCORCHED_SAND = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCORCHED_SAND, WWBlocks.SCORCHED_SAND);
	public static final FrozenDeferredItem<BlockItem> SCORCHED_RED_SAND = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCORCHED_RED_SAND, WWBlocks.SCORCHED_RED_SAND);

	// SAPLINGS
	public static final FrozenDeferredItem<BlockItem> BAOBAB_NUT = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_NUT, WWBlocks.BAOBAB_NUT,
		() -> new Item.Properties().food(WWFoods.BAOBAB_NUT).compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_SAPLING = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_SAPLING, WWBlocks.WILLOW_SAPLING,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_SAPLING = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_SAPLING, WWBlocks.CYPRESS_SAPLING,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<CoconutItem> COCONUT = REGISTER.registerItem(WWBlockItemIds.COCONUT.item(),
		properties -> new CoconutItem(WWBlocks.COCONUT.get(), properties),
		() -> new Item.Properties().useBlockDescriptionPrefix().compostable(NumberProviders.COMPOSTABLE_MEDIUM).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final MapleCollection<FrozenDeferredItem<BlockItem>> MAPLE_SAPLING = MapleCollection.zipMap(WWBlockItemIds.MAPLE_SAPLING, WWBlocks.MAPLE_SAPLING,
		(id, block) -> REGISTER.registerSimpleBlockItem(id, block,
			() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
		)
	);

	// LEAVES
	public static final FrozenDeferredItem<BlockItem> BAOBAB_LEAVES = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_LEAVES, WWBlocks.BAOBAB_LEAVES,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_LEAVES = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_LEAVES, WWBlocks.WILLOW_LEAVES,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_LEAVES = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_LEAVES, WWBlocks.CYPRESS_LEAVES,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_FRONDS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_FRONDS, WWBlocks.PALM_FRONDS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final MapleCollection<FrozenDeferredItem<BlockItem>> MAPLE_LEAVES = MapleCollection.zipMap(WWBlockItemIds.MAPLE_LEAVES, WWBlocks.MAPLE_LEAVES,
		(id, block) -> REGISTER.registerSimpleBlockItem(id, block,
			() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
		)
	);

	// HOLLOWED LOGS
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_OAK_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_SPRUCE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_BIRCH_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_JUNGLE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_ACACIA_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_DARK_OAK_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_MANGROVE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_CHERRY_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_PALE_OAK_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_POPLAR_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_POPLAR_LOG, WWBlocks.HOLLOWED_POPLAR_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_CRIMSON_STEM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_WARPED_STEM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_BAOBAB_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_BAOBAB_LOG, WWBlocks.HOLLOWED_BAOBAB_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_WILLOW_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_WILLOW_LOG, WWBlocks.HOLLOWED_WILLOW_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_CYPRESS_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_CYPRESS_LOG, WWBlocks.HOLLOWED_CYPRESS_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_PALM_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_PALM_LOG, WWBlocks.HOLLOWED_PALM_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> HOLLOWED_MAPLE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_MAPLE_LOG, WWBlocks.HOLLOWED_MAPLE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);

	// STRIPPED HOLLOWED LOGS
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_OAK_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_SPRUCE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_BIRCH_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_JUNGLE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_ACACIA_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_DARK_OAK_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_MANGROVE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_CHERRY_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_PALE_OAK_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_POPLAR_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_POPLAR_LOG, WWBlocks.STRIPPED_HOLLOWED_POPLAR_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_CRIMSON_STEM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_WARPED_STEM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

	// LEAF LITTER
	public static final FrozenDeferredItem<BlockItem> ACACIA_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.ACACIA_LEAF_LITTER, WWBlocks.ACACIA_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> AZALEA_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.AZALEA_LEAF_LITTER, WWBlocks.AZALEA_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_LEAF_LITTER, WWBlocks.BAOBAB_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> BIRCH_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BIRCH_LEAF_LITTER, WWBlocks.BIRCH_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> CHERRY_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CHERRY_LEAF_LITTER, WWBlocks.CHERRY_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_LEAF_LITTER, WWBlocks.CYPRESS_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> DARK_OAK_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.DARK_OAK_LEAF_LITTER, WWBlocks.DARK_OAK_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> JUNGLE_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.JUNGLE_LEAF_LITTER, WWBlocks.JUNGLE_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> MANGROVE_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MANGROVE_LEAF_LITTER, WWBlocks.MANGROVE_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> PALE_OAK_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALE_OAK_LEAF_LITTER, WWBlocks.PALE_OAK_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_FROND_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_FROND_LITTER, WWBlocks.PALM_FROND_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> SPRUCE_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SPRUCE_LEAF_LITTER, WWBlocks.SPRUCE_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_LEAF_LITTER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_LEAF_LITTER, WWBlocks.WILLOW_LEAF_LITTER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final MapleCollection<FrozenDeferredItem<BlockItem>> MAPLE_LEAF_LITTER = MapleCollection.zipMap(WWBlockItemIds.MAPLE_LEAF_LITTER, WWBlocks.MAPLE_LEAF_LITTER,
		(id, block) -> REGISTER.registerSimpleBlockItem(id, block,
			() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
		)
	);

	// SCULK
	public static final FrozenDeferredItem<BlockItem> SCULK_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCULK_STAIRS, WWBlocks.SCULK_STAIRS);
	public static final FrozenDeferredItem<BlockItem> SCULK_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCULK_SLAB, WWBlocks.SCULK_SLAB);
	public static final FrozenDeferredItem<BlockItem> SCULK_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCULK_WALL, WWBlocks.SCULK_WALL);
	public static final FrozenDeferredItem<BlockItem> OSSEOUS_SCULK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.OSSEOUS_SCULK, WWBlocks.OSSEOUS_SCULK);
	public static final FrozenDeferredItem<BlockItem> HANGING_TENDRIL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HANGING_TENDRIL, WWBlocks.HANGING_TENDRIL);
	public static final FrozenDeferredItem<BlockItem> ECHO_GLASS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.ECHO_GLASS, WWBlocks.ECHO_GLASS);

	// MESOGLEA
	public static final FrozenDeferredItem<BlockItem> PEARLESCENT_BLUE_MESOGLEA = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PEARLESCENT_BLUE_MESOGLEA, WWBlocks.PEARLESCENT_BLUE_MESOGLEA);
	public static final FrozenDeferredItem<BlockItem> PEARLESCENT_PURPLE_MESOGLEA = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PEARLESCENT_PURPLE_MESOGLEA, WWBlocks.PEARLESCENT_PURPLE_MESOGLEA);
	public static final FrozenDeferredItem<BlockItem> YELLOW_MESOGLEA = REGISTER.registerSimpleBlockItem(WWBlockItemIds.YELLOW_MESOGLEA, WWBlocks.YELLOW_MESOGLEA);
	public static final FrozenDeferredItem<BlockItem> BLUE_MESOGLEA = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BLUE_MESOGLEA, WWBlocks.BLUE_MESOGLEA);
	public static final FrozenDeferredItem<BlockItem> LIME_MESOGLEA = REGISTER.registerSimpleBlockItem(WWBlockItemIds.LIME_MESOGLEA, WWBlocks.LIME_MESOGLEA);
	public static final FrozenDeferredItem<BlockItem> RED_MESOGLEA = REGISTER.registerSimpleBlockItem(WWBlockItemIds.RED_MESOGLEA, WWBlocks.RED_MESOGLEA);
	public static final FrozenDeferredItem<BlockItem> PINK_MESOGLEA = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PINK_MESOGLEA, WWBlocks.PINK_MESOGLEA);

	// NEMATOCYST
	public static final FrozenDeferredItem<BlockItem> PEARLESCENT_BLUE_NEMATOCYST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST, WWBlocks.PEARLESCENT_BLUE_NEMATOCYST);
	public static final FrozenDeferredItem<BlockItem> PEARLESCENT_PURPLE_NEMATOCYST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST, WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST);
	public static final FrozenDeferredItem<BlockItem> YELLOW_NEMATOCYST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.YELLOW_NEMATOCYST, WWBlocks.YELLOW_NEMATOCYST);
	public static final FrozenDeferredItem<BlockItem> BLUE_NEMATOCYST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BLUE_NEMATOCYST, WWBlocks.BLUE_NEMATOCYST);
	public static final FrozenDeferredItem<BlockItem> LIME_NEMATOCYST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.LIME_NEMATOCYST, WWBlocks.LIME_NEMATOCYST);
	public static final FrozenDeferredItem<BlockItem> RED_NEMATOCYST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.RED_NEMATOCYST, WWBlocks.RED_NEMATOCYST);
	public static final FrozenDeferredItem<BlockItem> PINK_NEMATOCYST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PINK_NEMATOCYST, WWBlocks.PINK_NEMATOCYST);

	// MISC
	public static final FrozenDeferredItem<BlockItem> TERMITE_MOUND = REGISTER.registerSimpleBlockItem(WWBlockItemIds.TERMITE_MOUND, WWBlocks.TERMITE_MOUND);
	public static final FrozenDeferredItem<BlockItem> STONE_CHEST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STONE_CHEST, WWBlocks.STONE_CHEST);
	public static final FrozenDeferredItem<BlockItem> NULL_BLOCK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.NULL_BLOCK, WWBlocks.NULL_BLOCK);
	public static final FrozenDeferredItem<BlockItem> DISPLAY_LANTERN = REGISTER.registerSimpleBlockItem(WWBlockItemIds.DISPLAY_LANTERN, WWBlocks.DISPLAY_LANTERN,
		properties -> properties.component(WWDataComponents.FIREFLIES.get(), List.of())
	);

	// FLOWERS
	public static final FrozenDeferredItem<BlockItem> SEEDING_DANDELION = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SEEDING_DANDELION, WWBlocks.SEEDING_DANDELION,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> CARNATION = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CARNATION, WWBlocks.CARNATION,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> MARIGOLD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MARIGOLD, WWBlocks.MARIGOLD,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> PASQUEFLOWER = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PASQUEFLOWER, WWBlocks.PASQUEFLOWER,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> RED_HIBISCUS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.RED_HIBISCUS, WWBlocks.RED_HIBISCUS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> YELLOW_HIBISCUS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.YELLOW_HIBISCUS, WWBlocks.YELLOW_HIBISCUS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> WHITE_HIBISCUS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WHITE_HIBISCUS, WWBlocks.WHITE_HIBISCUS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> PINK_HIBISCUS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PINK_HIBISCUS, WWBlocks.PINK_HIBISCUS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> PURPLE_HIBISCUS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PURPLE_HIBISCUS, WWBlocks.PURPLE_HIBISCUS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);

	// FLOWERBEDS
	public static final FrozenDeferredItem<BlockItem> PHLOX = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PHLOX, WWBlocks.PHLOX,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> LANTANAS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.LANTANAS, WWBlocks.LANTANAS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> CLOVERS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CLOVERS, WWBlocks.CLOVERS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);

	// TALL FLOWERS
	public static final FrozenDeferredItem<DoubleHighBlockItem> DATURA = registerBlockItem(WWBlockItemIds.DATURA, WWBlocks.DATURA,
		DoubleHighBlockItem::new,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<DoubleHighBlockItem> MILKWEED = registerBlockItem(WWBlockItemIds.MILKWEED, WWBlocks.MILKWEED,
		DoubleHighBlockItem::new,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);

	// VEGETATION
	public static final FrozenDeferredItem<BlockItem> POLLEN = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLLEN, WWBlocks.POLLEN,
		() -> new Item.Properties().compostable(WWNumberProviders.COMPOSTABLE_POLLEN)
	);
	public static final FrozenDeferredItem<DamageOnUseBlockItem> PRICKLY_PEAR = registerBlockItem(WWBlockItemIds.PRICKLY_PEAR, WWBlocks.PRICKLY_PEAR,
		(block, properties) -> new DamageOnUseBlockItem(block, properties, 2F, WWSounds.PLAYER_HURT_CACTUS.get(), WWDamageTypes.PRICKLY_PEAR),
		() -> new Item.Properties().food(WWFoods.PRICKLY_PEAR).compostable(NumberProviders.COMPOSTABLE_LOW_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> SHRUB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SHRUB, WWBlocks.SHRUB,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> TUMBLEWEED_PLANT = REGISTER.registerSimpleBlockItem(WWBlockItemIds.TUMBLEWEED_PLANT, WWBlocks.TUMBLEWEED_PLANT,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW_MEDIUM)
	);
	public static final FrozenDeferredItem<SpawnEggItem> TUMBLEWEED = REGISTER.registerItem(
		WWBlockItemIds.TUMBLEWEED.item(),
		SpawnEggItem::new,
		() -> new Item.Properties()
			.useBlockDescriptionPrefix()
			.requiredFeatures(WWBlocks.TUMBLEWEED.get().requiredFeatures())
			.spawnEgg(WWEntityTypes.TUMBLEWEED.get())
			.compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> FROZEN_SHORT_GRASS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_SHORT_GRASS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<DoubleHighBlockItem> FROZEN_TALL_GRASS = registerBlockItem(WWBlockItemIds.FROZEN_TALL_GRASS, WWBlocks.FROZEN_TALL_GRASS,
		DoubleHighBlockItem::new,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> FROZEN_FERN = REGISTER.registerSimpleBlockItem(WWBlockItemIds.FROZEN_FERN, WWBlocks.FROZEN_FERN,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<DoubleHighBlockItem> FROZEN_LARGE_FERN = registerBlockItem(WWBlockItemIds.FROZEN_LARGE_FERN, WWBlocks.FROZEN_LARGE_FERN,
		DoubleHighBlockItem::new,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> FROZEN_BUSH = REGISTER.registerSimpleBlockItem(WWBlockItemIds.FROZEN_BUSH, WWBlocks.FROZEN_BUSH,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> MYCELIUM_GROWTH = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MYCELIUM_GROWTH, WWBlocks.MYCELIUM_GROWTH,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);

	// MUSHROOMS
	public static final FrozenDeferredItem<BlockItem> BROWN_SHELF_FUNGI = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BROWN_SHELF_FUNGI, WWBlocks.BROWN_SHELF_FUNGI,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> RED_SHELF_FUNGI = REGISTER.registerSimpleBlockItem(WWBlockItemIds.RED_SHELF_FUNGI, WWBlocks.RED_SHELF_FUNGI,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> CRIMSON_SHELF_FUNGI = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CRIMSON_SHELF_FUNGI, WWBlocks.CRIMSON_SHELF_FUNGI,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> WARPED_SHELF_FUNGI = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WARPED_SHELF_FUNGI, WWBlocks.WARPED_SHELF_FUNGI,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> PALE_MUSHROOM_BLOCK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALE_MUSHROOM_BLOCK, WWBlocks.PALE_MUSHROOM_BLOCK,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> PALE_MUSHROOM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALE_MUSHROOM, WWBlocks.PALE_MUSHROOM,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> PALE_SHELF_FUNGI = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALE_SHELF_FUNGI, WWBlocks.PALE_SHELF_FUNGI,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);

	// MOSS
	public static final FrozenDeferredItem<BlockItem> AUBURN_MOSS_BLOCK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.AUBURN_MOSS_BLOCK, WWBlocks.AUBURN_MOSS_BLOCK,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<BlockItem> AUBURN_MOSS_CARPET = REGISTER.registerSimpleBlockItem(WWBlockItemIds.AUBURN_MOSS_CARPET, WWBlocks.AUBURN_MOSS_CARPET,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> AUBURN_CREEPING_MOSS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.AUBURN_CREEPING_MOSS, WWBlocks.AUBURN_CREEPING_MOSS,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);

	// AQUATIC
	public static final FrozenDeferredItem<DoubleHighBlockItem> CATTAIL = registerBlockItem(WWBlockItemIds.CATTAIL, WWBlocks.CATTAIL,
		DoubleHighBlockItem::new,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<PlaceOnWaterBlockItem> FLOWERING_LILY_PAD = registerBlockItem(WWBlockItemIds.FLOWERING_LILY_PAD, WWBlocks.FLOWERING_LILY_PAD,
		PlaceOnWaterBlockItem::new,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_MEDIUM)
	);
	public static final FrozenDeferredItem<PlaceOnWaterBlockItem> ALGAE = registerBlockItem(WWBlockItemIds.ALGAE, WWBlocks.ALGAE,
		PlaceOnWaterBlockItem::new,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<PlaceOnWaterBlockItem> PLANKTON = registerBlockItem(WWBlockItemIds.PLANKTON, WWBlocks.PLANKTON,
		PlaceOnWaterBlockItem::new,
		() -> new Item.Properties().compostable(NumberProviders.COMPOSTABLE_LOW)
	);
	public static final FrozenDeferredItem<BlockItem> SPONGE_BUD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SPONGE_BUD, WWBlocks.SPONGE_BUD);
	public static final FrozenDeferredItem<BlockItem> BARNACLES = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BARNACLES, WWBlocks.BARNACLES);
	public static final FrozenDeferredItem<BlockItem> SEA_ANEMONE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SEA_ANEMONE, WWBlocks.SEA_ANEMONE);
	public static final FrozenDeferredItem<BlockItem> SEA_WHIP = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SEA_WHIP, WWBlocks.SEA_WHIP);
	public static final FrozenDeferredItem<BlockItem> TUBE_WORMS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.TUBE_WORMS, WWBlocks.TUBE_WORMS);

	// EGGS
	public static final FrozenDeferredItem<BlockItem> OSTRICH_EGG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.OSTRICH_EGG, WWBlocks.OSTRICH_EGG);
	public static final FrozenDeferredItem<BlockItem> PENGUIN_EGG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PENGUIN_EGG, WWBlocks.PENGUIN_EGG);

	// GABBRO
	public static final FrozenDeferredItem<BlockItem> GABBRO = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO, WWBlocks.GABBRO);
	public static final FrozenDeferredItem<BlockItem> GABBRO_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_STAIRS, WWBlocks.GABBRO_STAIRS);
	public static final FrozenDeferredItem<BlockItem> GABBRO_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_SLAB, WWBlocks.GABBRO_SLAB);
	public static final FrozenDeferredItem<BlockItem> GABBRO_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_WALL, WWBlocks.GABBRO_WALL);
	public static final FrozenDeferredItem<BlockItem> GEOTHERMAL_VENT = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GEOTHERMAL_VENT, WWBlocks.GEOTHERMAL_VENT);

	public static final FrozenDeferredItem<BlockItem> POLISHED_GABBRO = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLISHED_GABBRO, WWBlocks.POLISHED_GABBRO);
	public static final FrozenDeferredItem<BlockItem> POLISHED_GABBRO_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO_STAIRS);
	public static final FrozenDeferredItem<BlockItem> POLISHED_GABBRO_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO_SLAB);
	public static final FrozenDeferredItem<BlockItem> POLISHED_GABBRO_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLISHED_GABBRO_WALL, WWBlocks.POLISHED_GABBRO_WALL);

	public static final FrozenDeferredItem<BlockItem> GABBRO_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_BRICKS, WWBlocks.GABBRO_BRICKS);
	public static final FrozenDeferredItem<BlockItem> GABBRO_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICK_STAIRS);
	public static final FrozenDeferredItem<BlockItem> GABBRO_BRICK_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICK_SLAB);
	public static final FrozenDeferredItem<BlockItem> GABBRO_BRICK_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_BRICK_WALL, WWBlocks.GABBRO_BRICK_WALL);
	public static final FrozenDeferredItem<BlockItem> CRACKED_GABBRO_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CRACKED_GABBRO_BRICKS, WWBlocks.CRACKED_GABBRO_BRICKS);
	public static final FrozenDeferredItem<BlockItem> CHISELED_GABBRO_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CHISELED_GABBRO_BRICKS, WWBlocks.CHISELED_GABBRO_BRICKS);

	public static final FrozenDeferredItem<BlockItem> MOSSY_GABBRO_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICKS);
	public static final FrozenDeferredItem<BlockItem> MOSSY_GABBRO_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICK_STAIRS);
	public static final FrozenDeferredItem<BlockItem> MOSSY_GABBRO_BRICK_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICK_SLAB);
	public static final FrozenDeferredItem<BlockItem> MOSSY_GABBRO_BRICK_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL, WWBlocks.MOSSY_GABBRO_BRICK_WALL);

	// BAOBAB
	public static final FrozenDeferredItem<BlockItem> BAOBAB_PLANKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_PLANKS, WWBlocks.BAOBAB_PLANKS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_STAIRS, WWBlocks.BAOBAB_STAIRS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_FENCE_GATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_FENCE_GATE, WWBlocks.BAOBAB_FENCE_GATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_SLAB, WWBlocks.BAOBAB_SLAB,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_SLABS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_PRESSURE_PLATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_PRESSURE_PLATE, WWBlocks.BAOBAB_PRESSURE_PLATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_BUTTON = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_BUTTON, WWBlocks.BAOBAB_BUTTON,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL)
	);
	public static final FrozenDeferredItem<DoubleHighBlockItem> BAOBAB_DOOR = registerBlockItem(WWBlockItemIds.BAOBAB_DOOR, WWBlocks.BAOBAB_DOOR,
		DoubleHighBlockItem::new,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_TRAPDOOR = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_TRAPDOOR, WWBlocks.BAOBAB_TRAPDOOR,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_FENCE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_FENCE, WWBlocks.BAOBAB_FENCE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_LOG, WWBlocks.BAOBAB_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_BAOBAB_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_BAOBAB_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_WOOD, WWBlocks.BAOBAB_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_BAOBAB_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<StandingAndWallBlockItem> BAOBAB_SIGN = registerBlockItem(WWBlockItemIds.BAOBAB_SIGN, WWBlocks.BAOBAB_SIGN,
		(block, properties) -> new StandingAndWallBlockItem(block, WWBlocks.BAOBAB_WALL_SIGN.get(), Direction.DOWN, properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<HangingSignItem> BAOBAB_HANGING_SIGN = registerBlockItem(WWBlockItemIds.BAOBAB_HANGING_SIGN, WWBlocks.BAOBAB_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.BAOBAB_WALL_HANGING_SIGN.get(), properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_HANGING_SIGNS).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<BlockItem> BAOBAB_SHELF = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BAOBAB_SHELF, WWBlocks.BAOBAB_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);

	// WILLOW
	public static final FrozenDeferredItem<BlockItem> WILLOW_PLANKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_PLANKS, WWBlocks.WILLOW_PLANKS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_STAIRS, WWBlocks.WILLOW_STAIRS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_FENCE_GATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_FENCE_GATE, WWBlocks.WILLOW_FENCE_GATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_SLAB, WWBlocks.WILLOW_SLAB,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_SLABS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_PRESSURE_PLATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_PRESSURE_PLATE, WWBlocks.WILLOW_PRESSURE_PLATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_BUTTON = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_BUTTON, WWBlocks.WILLOW_BUTTON,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL)
	);
	public static final FrozenDeferredItem<DoubleHighBlockItem> WILLOW_DOOR = registerBlockItem(WWBlockItemIds.WILLOW_DOOR, WWBlocks.WILLOW_DOOR,
		DoubleHighBlockItem::new,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_TRAPDOOR = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_TRAPDOOR, WWBlocks.WILLOW_TRAPDOOR,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_FENCE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_FENCE, WWBlocks.WILLOW_FENCE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_LOG, WWBlocks.WILLOW_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_WILLOW_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_WILLOW_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_WOOD, WWBlocks.WILLOW_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_WILLOW_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<StandingAndWallBlockItem> WILLOW_SIGN = registerBlockItem(WWBlockItemIds.WILLOW_SIGN, WWBlocks.WILLOW_SIGN,
		(block, properties) -> new StandingAndWallBlockItem(block, WWBlocks.WILLOW_WALL_SIGN.get(), Direction.DOWN, properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<HangingSignItem> WILLOW_HANGING_SIGN = registerBlockItem(WWBlockItemIds.WILLOW_HANGING_SIGN, WWBlocks.WILLOW_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.WILLOW_WALL_HANGING_SIGN.get(), properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_HANGING_SIGNS).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<BlockItem> WILLOW_SHELF = REGISTER.registerSimpleBlockItem(WWBlockItemIds.WILLOW_SHELF, WWBlocks.WILLOW_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);

	// CYPRESS
	public static final FrozenDeferredItem<BlockItem> CYPRESS_PLANKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_PLANKS, WWBlocks.CYPRESS_PLANKS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_STAIRS, WWBlocks.CYPRESS_STAIRS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_FENCE_GATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_FENCE_GATE, WWBlocks.CYPRESS_FENCE_GATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_SLAB, WWBlocks.CYPRESS_SLAB,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_SLABS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_PRESSURE_PLATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_PRESSURE_PLATE, WWBlocks.CYPRESS_PRESSURE_PLATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_BUTTON = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_BUTTON, WWBlocks.CYPRESS_BUTTON,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL)
	);
	public static final FrozenDeferredItem<DoubleHighBlockItem> CYPRESS_DOOR = registerBlockItem(WWBlockItemIds.CYPRESS_DOOR, WWBlocks.CYPRESS_DOOR,
		DoubleHighBlockItem::new,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_TRAPDOOR = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_TRAPDOOR, WWBlocks.CYPRESS_TRAPDOOR,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_FENCE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_FENCE, WWBlocks.CYPRESS_FENCE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_LOG, WWBlocks.CYPRESS_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_CYPRESS_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_CYPRESS_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_WOOD, WWBlocks.CYPRESS_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_CYPRESS_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<StandingAndWallBlockItem> CYPRESS_SIGN = registerBlockItem(WWBlockItemIds.CYPRESS_SIGN, WWBlocks.CYPRESS_SIGN,
		(block, properties) -> new StandingAndWallBlockItem(block, WWBlocks.CYPRESS_WALL_SIGN.get(), Direction.DOWN, properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<HangingSignItem> CYPRESS_HANGING_SIGN = registerBlockItem(WWBlockItemIds.CYPRESS_HANGING_SIGN, WWBlocks.CYPRESS_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.CYPRESS_WALL_HANGING_SIGN.get(), properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_HANGING_SIGNS).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<BlockItem> CYPRESS_SHELF = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CYPRESS_SHELF, WWBlocks.CYPRESS_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);

	// PALM
	public static final FrozenDeferredItem<BlockItem> PALM_PLANKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_PLANKS, WWBlocks.PALM_PLANKS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_STAIRS, WWBlocks.PALM_STAIRS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_FENCE_GATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_FENCE_GATE, WWBlocks.PALM_FENCE_GATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_SLAB, WWBlocks.PALM_SLAB,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_SLABS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_PRESSURE_PLATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_PRESSURE_PLATE, WWBlocks.PALM_PRESSURE_PLATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_BUTTON = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_BUTTON, WWBlocks.PALM_BUTTON,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL)
	);
	public static final FrozenDeferredItem<DoubleHighBlockItem> PALM_DOOR = registerBlockItem(WWBlockItemIds.PALM_DOOR, WWBlocks.PALM_DOOR,
		DoubleHighBlockItem::new,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_TRAPDOOR = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_TRAPDOOR, WWBlocks.PALM_TRAPDOOR,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_FENCE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_FENCE, WWBlocks.PALM_FENCE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_LOG, WWBlocks.PALM_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_PALM_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_PALM_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_PALM_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> PALM_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_WOOD, WWBlocks.PALM_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_PALM_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_PALM_WOOD, WWBlocks.STRIPPED_PALM_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<StandingAndWallBlockItem> PALM_SIGN = registerBlockItem(WWBlockItemIds.PALM_SIGN, WWBlocks.PALM_SIGN,
		(block, properties) -> new StandingAndWallBlockItem(block, WWBlocks.PALM_WALL_SIGN.get(), Direction.DOWN, properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<HangingSignItem> PALM_HANGING_SIGN = registerBlockItem(WWBlockItemIds.PALM_HANGING_SIGN, WWBlocks.PALM_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.PALM_WALL_HANGING_SIGN.get(), properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_HANGING_SIGNS).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<BlockItem> PALM_SHELF = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALM_SHELF, WWBlocks.PALM_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);

	// MAPLE
	public static final FrozenDeferredItem<BlockItem> MAPLE_PLANKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_PLANKS, WWBlocks.MAPLE_PLANKS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_STAIRS, WWBlocks.MAPLE_STAIRS,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_FENCE_GATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_FENCE_GATE, WWBlocks.MAPLE_FENCE_GATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_SLAB, WWBlocks.MAPLE_SLAB,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_SLABS)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_PRESSURE_PLATE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_PRESSURE_PLATE, WWBlocks.MAPLE_PRESSURE_PLATE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_BUTTON = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_BUTTON, WWBlocks.MAPLE_BUTTON,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL)
	);
	public static final FrozenDeferredItem<DoubleHighBlockItem> MAPLE_DOOR = registerBlockItem(WWBlockItemIds.MAPLE_DOOR, WWBlocks.MAPLE_DOOR,
		DoubleHighBlockItem::new,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_TRAPDOOR = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_TRAPDOOR, WWBlocks.MAPLE_TRAPDOOR,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_FENCE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_FENCE, WWBlocks.MAPLE_FENCE,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_LOG, WWBlocks.MAPLE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_MAPLE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_HOLLOWED_MAPLE_LOG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_WOOD, WWBlocks.MAPLE_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<BlockItem> STRIPPED_MAPLE_WOOD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_WOOD,
		properties -> properties.cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);
	public static final FrozenDeferredItem<StandingAndWallBlockItem> MAPLE_SIGN = registerBlockItem(WWBlockItemIds.MAPLE_SIGN, WWBlocks.MAPLE_SIGN,
		(block, properties) -> new StandingAndWallBlockItem(block, WWBlocks.MAPLE_WALL_SIGN.get(), Direction.DOWN, properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_WOOD_ITEMS_LARGE).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<HangingSignItem> MAPLE_HANGING_SIGN = registerBlockItem(WWBlockItemIds.MAPLE_HANGING_SIGN, WWBlocks.MAPLE_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.MAPLE_WALL_HANGING_SIGN.get(), properties),
		() -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_HANGING_SIGNS).stacksTo(16).signText()
	);
	public static final FrozenDeferredItem<BlockItem> MAPLE_SHELF = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MAPLE_SHELF, WWBlocks.MAPLE_SHELF,
		properties -> properties.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).cookingFuel(NumberProviders.COOKING_TIME_WOOD_BLOCKS)
	);

	// ICE
	public static final FrozenDeferredItem<BlockItem> FRAGILE_ICE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.FRAGILE_ICE, WWBlocks.FRAGILE_ICE);
	public static final FrozenDeferredItem<BlockItem> ICICLE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.ICICLE, WWBlocks.ICICLE);

	// FROGLIGHT GOOP
	public static final FrozenDeferredItem<BlockItem> OCHRE_FROGLIGHT_GOOP = REGISTER.registerSimpleBlockItem(WWBlockItemIds.OCHRE_FROGLIGHT_GOOP, WWBlocks.OCHRE_FROGLIGHT_GOOP);
	public static final FrozenDeferredItem<BlockItem> VERDANT_FROGLIGHT_GOOP = REGISTER.registerSimpleBlockItem(WWBlockItemIds.VERDANT_FROGLIGHT_GOOP, WWBlocks.VERDANT_FROGLIGHT_GOOP);
	public static final FrozenDeferredItem<BlockItem> PEARLESCENT_FROGLIGHT_GOOP = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PEARLESCENT_FROGLIGHT_GOOP, WWBlocks.PEARLESCENT_FROGLIGHT_GOOP);

	// ITEMS

	// BOATS
	public static final FrozenDeferredItem<BoatItem> BAOBAB_BOAT = registerBoatItem(WWItemIds.BAOBAB_BOAT, WWEntityTypes.BAOBAB_BOAT);
	public static final FrozenDeferredItem<BoatItem> BAOBAB_CHEST_BOAT = registerBoatItem(WWItemIds.BAOBAB_CHEST_BOAT, WWEntityTypes.BAOBAB_CHEST_BOAT);
	public static final FrozenDeferredItem<BoatItem> WILLOW_BOAT = registerBoatItem(WWItemIds.WILLOW_BOAT, WWEntityTypes.WILLOW_BOAT);
	public static final FrozenDeferredItem<BoatItem> WILLOW_CHEST_BOAT = registerBoatItem(WWItemIds.WILLOW_CHEST_BOAT, WWEntityTypes.WILLOW_CHEST_BOAT);
	public static final FrozenDeferredItem<BoatItem> CYPRESS_BOAT = registerBoatItem(WWItemIds.CYPRESS_BOAT, WWEntityTypes.CYPRESS_BOAT);
	public static final FrozenDeferredItem<BoatItem> CYPRESS_CHEST_BOAT = registerBoatItem(WWItemIds.CYPRESS_CHEST_BOAT, WWEntityTypes.CYPRESS_CHEST_BOAT);
	public static final FrozenDeferredItem<BoatItem> PALM_BOAT = registerBoatItem(WWItemIds.PALM_BOAT, WWEntityTypes.PALM_BOAT);
	public static final FrozenDeferredItem<BoatItem> PALM_CHEST_BOAT = registerBoatItem(WWItemIds.PALM_CHEST_BOAT, WWEntityTypes.PALM_CHEST_BOAT);
	public static final FrozenDeferredItem<BoatItem> MAPLE_BOAT = registerBoatItem(WWItemIds.MAPLE_BOAT, WWEntityTypes.MAPLE_BOAT);
	public static final FrozenDeferredItem<BoatItem> MAPLE_CHEST_BOAT = registerBoatItem(WWItemIds.MAPLE_CHEST_BOAT, WWEntityTypes.MAPLE_CHEST_BOAT);

	public static final FrozenDeferredItem<MilkweedPodItem> MILKWEED_POD = REGISTER.registerItem(WWItemIds.MILKWEED_POD,
		MilkweedPodItem::new,
		() -> new Item.Properties().compostable(WWNumberProviders.COMPOSTABLE_MILKWEED_POD)
	);
	public static final FrozenDeferredItem<MobBottleItem> FIREFLY_BOTTLE = REGISTER.registerItem(WWItemIds.FIREFLY_BOTTLE,
		properties -> new MobBottleItem(
			WWEntityTypes.FIREFLY.get(),
			WWSounds.ITEM_BOTTLE_RELEASE_FIREFLY.get(),
			properties
		),
		() -> new Item.Properties()
			.stacksTo(16)
			.component(WWDataComponents.BOTTLE_ENTITY_DATA.get(), CustomData.EMPTY)
			.delayedComponent(
				WWDataComponents.FIREFLY_COLOR.get(),
				context -> context.lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).get(FireflyColors.DEFAULT).orElse(null)
			)
	);
	public static final FrozenDeferredItem<MobBottleItem> BUTTERFLY_BOTTLE = REGISTER.registerItem(WWItemIds.BUTTERFLY_BOTTLE,
		properties -> new MobBottleItem(
			WWEntityTypes.BUTTERFLY.get(),
			WWSounds.ITEM_BOTTLE_RELEASE_BUTTERFLY.get(),
			properties
		),
		() -> new Item.Properties()
			.stacksTo(1)
			.component(WWDataComponents.BOTTLE_ENTITY_DATA.get(), CustomData.EMPTY)
	);

	// FOOD
	public static final FrozenDeferredItem<Item> PEELED_PRICKLY_PEAR = REGISTER.registerSimpleItem(WWItemIds.PEELED_PRICKLY_PEAR,
		() -> new Item.Properties().food(Foods.APPLE).compostable(NumberProviders.COMPOSTABLE_LOW_MEDIUM)
	);
	public static final FrozenDeferredItem<Item> SPLIT_COCONUT = REGISTER.registerSimpleItem(WWItemIds.SPLIT_COCONUT,
		() -> new Item.Properties().food(WWFoods.SPLIT_COCONUT).compostable(NumberProviders.COMPOSTABLE_MEDIUM).cookingFuel(NumberProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final FrozenDeferredItem<CrabClawItem> CRAB_CLAW = REGISTER.registerItem(WWItemIds.CRAB_CLAW,
		CrabClawItem::new,
		() -> new Item.Properties().food(WWFoods.CRAB_CLAW));
	public static final FrozenDeferredItem<Item> COOKED_CRAB_CLAW = REGISTER.registerSimpleItem(WWItemIds.COOKED_CRAB_CLAW,
		() -> new Item.Properties().food(WWFoods.COOKED_CRAB_CLAW)
	);
	public static final FrozenDeferredItem<Item> SCORCHED_EYE = REGISTER.registerSimpleItem(WWItemIds.SCORCHED_EYE,
		() -> new Item.Properties().food(WWFoods.SCORCHED_EYE, WWFoods.SCORCHED_EYE_CONSUMABLE)
	);
	public static final FrozenDeferredItem<Item> FERMENTED_SCORCHED_EYE = REGISTER.registerSimpleItem(WWItemIds.FERMENTED_SCORCHED_EYE);

	// SPAWN EGGS & BUCKETS
	public static final FrozenDeferredItem<MobBucketItem> JELLYFISH_BUCKET = REGISTER.registerItem(
		WWItemIds.JELLYFISH_BUCKET,
		properties -> new MobBucketItem(WWEntityTypes.JELLYFISH.get(), Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_JELLYFISH.get(), properties),
		() -> new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
	);
	public static final FrozenDeferredItem<MobBucketItem> CRAB_BUCKET = REGISTER.registerItem(
		WWItemIds.CRAB_BUCKET,
		properties -> new MobBucketItem(WWEntityTypes.CRAB.get(), Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_CRAB.get(), properties),
		() -> new Item.Properties().stacksTo(1).component(DataComponents.FOOD, WWFoods.CRAB_CLAW)
	);

	public static final FrozenDeferredItem<SpawnEggItem> FIREFLY_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.FIREFLY_SPAWN_EGG, WWEntityTypes.FIREFLY::get);
	public static final FrozenDeferredItem<SpawnEggItem> JELLYFISH_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.JELLYFISH_SPAWN_EGG, WWEntityTypes.JELLYFISH::get);
	public static final FrozenDeferredItem<SpawnEggItem> CRAB_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.CRAB_SPAWN_EGG, WWEntityTypes.CRAB::get);
	public static final FrozenDeferredItem<SpawnEggItem> OSTRICH_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.OSTRICH_SPAWN_EGG, WWEntityTypes.OSTRICH::get);
	public static final FrozenDeferredItem<SpawnEggItem> ZOMBIE_OSTRICH_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.ZOMBIE_OSTRICH_SPAWN_EGG, WWEntityTypes.ZOMBIE_OSTRICH::get);
	public static final FrozenDeferredItem<SpawnEggItem> SCORCHED_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.SCORCHED_SPAWN_EGG, WWEntityTypes.SCORCHED::get);
	public static final FrozenDeferredItem<SpawnEggItem> BUTTERFLY_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.BUTTERFLY_SPAWN_EGG, WWEntityTypes.BUTTERFLY::get);
	public static final FrozenDeferredItem<SpawnEggItem> MOOBLOOM_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.MOOBLOOM_SPAWN_EGG, WWEntityTypes.MOOBLOOM::get);
	public static final FrozenDeferredItem<SpawnEggItem> PENGUIN_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.PENGUIN_SPAWN_EGG, WWEntityTypes.PENGUIN::get);

	public static void init() {}

	/**
	 * Called separately on Fabric and NeoForge. Registries on NeoForge MUST be populated before running this.
	 */
	public static void setup() {
		ItemTooltipAdditionAPI.addTooltip(
			Component.translatable("item.disabled.trailiertales").withStyle(ChatFormatting.RED),
			stack -> !FrozenBools.HAS_TRAILIERTALES && stack.getItem().requiredFeatures().contains(net.frozenblock.wilderwild.WWFeatureFlags.TRAILIER_TALES_COMPAT)
		);

		Item.BY_BLOCK.put(WWBlocks.TUMBLEWEED.get(), TUMBLEWEED.get());

		DispenserBlock.registerBehavior(BAOBAB_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.BAOBAB_BOAT.get()));
		DispenserBlock.registerBehavior(BAOBAB_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.BAOBAB_CHEST_BOAT.get()));
		DispenserBlock.registerBehavior(WILLOW_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.WILLOW_BOAT.get()));
		DispenserBlock.registerBehavior(WILLOW_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.WILLOW_CHEST_BOAT.get()));
		DispenserBlock.registerBehavior(CYPRESS_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.CYPRESS_BOAT.get()));
		DispenserBlock.registerBehavior(CYPRESS_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.CYPRESS_CHEST_BOAT.get()));
		DispenserBlock.registerBehavior(PALM_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.PALM_BOAT.get()));
		DispenserBlock.registerBehavior(PALM_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.PALM_CHEST_BOAT.get()));
		DispenserBlock.registerBehavior(MAPLE_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.MAPLE_BOAT.get()));
		DispenserBlock.registerBehavior(MAPLE_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWEntityTypes.MAPLE_CHEST_BOAT.get()));
	}

	private static <I extends Item> FrozenDeferredItem<I> registerBlockItem(BlockItemId id, Supplier<? extends Block> block, BiFunction<Block, Item.Properties, ? extends I> itemFactory) {
		return REGISTER.registerItem(id.item(), properties -> itemFactory.apply(block.get(), properties), () -> new Item.Properties().useBlockDescriptionPrefix().setId(id.item()));
	}

	private static <I extends Item> FrozenDeferredItem<I> registerBlockItem(
		BlockItemId id, Supplier<? extends Block> block, BiFunction<Block, Item.Properties, ? extends I> itemFactory, UnaryOperator<Item.Properties> propertiesModifier
	) {
		return REGISTER.registerItem(id.item(), properties -> itemFactory.apply(block.get(), properties), () -> propertiesModifier.apply(new Item.Properties().useBlockDescriptionPrefix().setId(id.item())));
	}

	private static <I extends Item> FrozenDeferredItem<I> registerBlockItem(
		BlockItemId id, Supplier<? extends Block> block, BiFunction<Block, Item.Properties, ? extends I> itemFactory, Supplier<Item.Properties> propertiesSupplier
	) {
		return REGISTER.registerItem(id.item(), properties -> itemFactory.apply(block.get(), properties), propertiesSupplier);
	}

	private static <T extends AbstractBoat> FrozenDeferredItem<BoatItem> registerBoatItem(ResourceKey<Item> id, FrozenHolder<EntityType<?>, EntityType<T>> boat) {
		return REGISTER.registerItem(id, properties -> new BoatItem(boat.get(), properties), () -> new Item.Properties().cookingFuel(NumberProviders.COOKING_TIME_BOATS).stacksTo(1));
	}

	static {
		REGISTER.register();
	}
}
