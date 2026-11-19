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
import java.util.Optional;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;
import net.frozenblock.lib.item.api.component.consume_effects.DamageConsumeEffect;
import net.frozenblock.lib.platform.api.registry.DeferredItem;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.block.impl.MesogleaCollection;
import net.frozenblock.wilderwild.block.impl.PoplarCollection;
import net.frozenblock.wilderwild.entity.ambient.firefly.FireflyVariants;
import net.frozenblock.wilderwild.item.CoconutItem;
import net.frozenblock.wilderwild.item.CrabClawItem;
import net.frozenblock.wilderwild.item.HarpoonItem;
import net.frozenblock.wilderwild.item.MilkweedPodItem;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.item.TumbleweedItem;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.references.WWDispenseBehaviorIds;
import net.frozenblock.wilderwild.references.WWItemIds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.DispenseBehaviorIds;
import net.minecraft.references.BlockItemIds;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.PlaceOnFluidBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
public final class WWItems {
	private static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(WWConstants.MOD_ID).requiredFeatures(WWFeatureFlags.FEATURE_FLAG);

	// BLOCK ITEMS
	// MUD
	public static final DeferredItem<BlockItem> CHISELED_MUD_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CHISELED_MUD_BRICKS, WWBlocks.CHISELED_MUD_BRICKS);
	public static final DeferredItem<BlockItem> CRACKED_MUD_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CRACKED_MUD_BRICKS, WWBlocks.CRACKED_MUD_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_MUD_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_MUD_BRICKS, WWBlocks.MOSSY_MUD_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_MUD_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS, WWBlocks.MOSSY_MUD_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_MUD_BRICK_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_MUD_BRICK_SLAB, WWBlocks.MOSSY_MUD_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_MUD_BRICK_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_MUD_BRICK_WALL, WWBlocks.MOSSY_MUD_BRICK_WALL);

	// SAND
	public static final DeferredItem<BlockItem> SCORCHED_SAND = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCORCHED_SAND, WWBlocks.SCORCHED_SAND);
	public static final DeferredItem<BlockItem> SCORCHED_RED_SAND = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCORCHED_RED_SAND, WWBlocks.SCORCHED_RED_SAND);

	// SAPLINGS
	public static final DeferredItem<BlockItem> BAOBAB_NUT = REGISTER.registerOverworldSapling(WWBlockItemIds.BAOBAB_NUT, WWBlocks.BAOBAB_NUT);
	public static final DeferredItem<BlockItem> WILLOW_SAPLING = REGISTER.registerOverworldSapling(WWBlockItemIds.WILLOW_SAPLING, WWBlocks.WILLOW_SAPLING);
	public static final DeferredItem<BlockItem> CYPRESS_SAPLING = REGISTER.registerOverworldSapling(WWBlockItemIds.CYPRESS_SAPLING, WWBlocks.CYPRESS_SAPLING);
	public static final DeferredItem<CoconutItem> COCONUT = REGISTER.registerItem(WWBlockItemIds.COCONUT.item(),
		properties -> new CoconutItem(WWBlocks.COCONUT.get(), properties),
		() -> new Item.Properties().useBlockDescriptionPrefix()
			.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)
			.cookingFuel(ContextIntProviders.COOKING_TIME_DRY_PLANTS)
			.dispensed(WWDispenseBehaviorIds.COCONUT)
	);
	public static final MapleCollection<DeferredItem<BlockItem>> MAPLE_SAPLING = MapleCollection.zipMap(WWBlockItemIds.MAPLE_SAPLING, WWBlocks.MAPLE_SAPLING, REGISTER::registerOverworldSapling);
	public static final PoplarCollection<Supplier<? extends Item>> POPLAR_SAPLING = PoplarCollection.zipMap(WWBlockItemIds.POPLAR_SAPLING, WWBlocks.POPLAR_SAPLING,
		(id, block) -> {
			if (id.equals(BlockItemIds.POPLAR_SAPLING)) return () -> Items.POPLAR_SAPLING;
			return REGISTER.registerOverworldSapling(id, block);
		}
	);

	// LEAVES
	public static final DeferredItem<BlockItem> BAOBAB_LEAVES = REGISTER.registerOverworldLeaves(WWBlockItemIds.BAOBAB_LEAVES, WWBlocks.BAOBAB_LEAVES);
	public static final DeferredItem<BlockItem> WILLOW_LEAVES = REGISTER.registerOverworldLeaves(WWBlockItemIds.WILLOW_LEAVES, WWBlocks.WILLOW_LEAVES);
	public static final DeferredItem<BlockItem> CYPRESS_LEAVES = REGISTER.registerOverworldLeaves(WWBlockItemIds.CYPRESS_LEAVES, WWBlocks.CYPRESS_LEAVES);
	public static final DeferredItem<BlockItem> PALM_FRONDS = REGISTER.registerOverworldLeaves(WWBlockItemIds.PALM_FRONDS, WWBlocks.PALM_FRONDS);
	public static final MapleCollection<DeferredItem<BlockItem>> MAPLE_LEAVES = MapleCollection.zipMap(WWBlockItemIds.MAPLE_LEAVES, WWBlocks.MAPLE_LEAVES, REGISTER::registerOverworldLeaves);

	// HOLLOWED LOGS
	public static final DeferredItem<BlockItem> HOLLOWED_OAK_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_SPRUCE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_BIRCH_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_JUNGLE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_ACACIA_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_DARK_OAK_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_MANGROVE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_CHERRY_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_PALE_OAK_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_POPLAR_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_POPLAR_LOG, WWBlocks.HOLLOWED_POPLAR_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_BAOBAB_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_BAOBAB_LOG, WWBlocks.HOLLOWED_BAOBAB_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_WILLOW_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_WILLOW_LOG, WWBlocks.HOLLOWED_WILLOW_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_CYPRESS_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_CYPRESS_LOG, WWBlocks.HOLLOWED_CYPRESS_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_PALM_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_PALM_LOG, WWBlocks.HOLLOWED_PALM_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_MAPLE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.HOLLOWED_MAPLE_LOG, WWBlocks.HOLLOWED_MAPLE_LOG);
	public static final DeferredItem<BlockItem> HOLLOWED_CRIMSON_STEM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM);
	public static final DeferredItem<BlockItem> HOLLOWED_WARPED_STEM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HOLLOWED_WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM);

	// STRIPPED HOLLOWED LOGS
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_OAK_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_SPRUCE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_BIRCH_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_JUNGLE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_ACACIA_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_DARK_OAK_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_MANGROVE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_CHERRY_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_PALE_OAK_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_POPLAR_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_POPLAR_LOG, WWBlocks.STRIPPED_HOLLOWED_POPLAR_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_CRIMSON_STEM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_WARPED_STEM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

	// LEAF LITTER
	public static final DeferredItem<BlockItem> ACACIA_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.ACACIA_LEAF_LITTER, WWBlocks.ACACIA_LEAF_LITTER);
	public static final DeferredItem<BlockItem> AZALEA_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.AZALEA_LEAF_LITTER, WWBlocks.AZALEA_LEAF_LITTER);
	public static final DeferredItem<BlockItem> BAOBAB_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.BAOBAB_LEAF_LITTER, WWBlocks.BAOBAB_LEAF_LITTER);
	public static final DeferredItem<BlockItem> BIRCH_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.BIRCH_LEAF_LITTER, WWBlocks.BIRCH_LEAF_LITTER);
	public static final DeferredItem<BlockItem> CHERRY_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.CHERRY_LEAF_LITTER, WWBlocks.CHERRY_LEAF_LITTER);
	public static final DeferredItem<BlockItem> CYPRESS_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.CYPRESS_LEAF_LITTER, WWBlocks.CYPRESS_LEAF_LITTER);
	public static final DeferredItem<BlockItem> DARK_OAK_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.DARK_OAK_LEAF_LITTER, WWBlocks.DARK_OAK_LEAF_LITTER);
	public static final DeferredItem<BlockItem> JUNGLE_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.JUNGLE_LEAF_LITTER, WWBlocks.JUNGLE_LEAF_LITTER);
	public static final DeferredItem<BlockItem> MANGROVE_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.MANGROVE_LEAF_LITTER, WWBlocks.MANGROVE_LEAF_LITTER);
	public static final DeferredItem<BlockItem> PALE_OAK_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.PALE_OAK_LEAF_LITTER, WWBlocks.PALE_OAK_LEAF_LITTER);
	public static final PoplarCollection<DeferredItem<BlockItem>> POPLAR_LEAF_LITTER = PoplarCollection.zipMap(WWBlockItemIds.POPLAR_LEAF_LITTER, WWBlocks.POPLAR_LEAF_LITTER, REGISTER::registerLeafLitter);
	public static final DeferredItem<BlockItem> PALM_FROND_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.PALM_FROND_LITTER, WWBlocks.PALM_FROND_LITTER);
	public static final DeferredItem<BlockItem> SPRUCE_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.SPRUCE_LEAF_LITTER, WWBlocks.SPRUCE_LEAF_LITTER);
	public static final DeferredItem<BlockItem> WILLOW_LEAF_LITTER = REGISTER.registerLeafLitter(WWBlockItemIds.WILLOW_LEAF_LITTER, WWBlocks.WILLOW_LEAF_LITTER);
	public static final MapleCollection<DeferredItem<BlockItem>> MAPLE_LEAF_LITTER = MapleCollection.zipMap(WWBlockItemIds.MAPLE_LEAF_LITTER, WWBlocks.MAPLE_LEAF_LITTER, REGISTER::registerLeafLitter);

	// SCULK
	public static final DeferredItem<BlockItem> SCULK_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCULK_STAIRS, WWBlocks.SCULK_STAIRS);
	public static final DeferredItem<BlockItem> SCULK_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCULK_SLAB, WWBlocks.SCULK_SLAB);
	public static final DeferredItem<BlockItem> SCULK_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SCULK_WALL, WWBlocks.SCULK_WALL);
	public static final DeferredItem<BlockItem> OSSEOUS_SCULK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.OSSEOUS_SCULK, WWBlocks.OSSEOUS_SCULK);
	public static final DeferredItem<BlockItem> HANGING_TENDRIL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.HANGING_TENDRIL, WWBlocks.HANGING_TENDRIL);
	public static final DeferredItem<BlockItem> ECHO_GLASS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.ECHO_GLASS, WWBlocks.ECHO_GLASS);

	// MESOGLEA
	public static final MesogleaCollection<DeferredItem<BlockItem>> MESOGLEA = MesogleaCollection.zipMap(WWBlockItemIds.MESOGLEA, WWBlocks.MESOGLEA, REGISTER::registerSimpleBlockItem);

	// NEMATOCYST
	public static final MesogleaCollection<DeferredItem<BlockItem>> NEMATOCYST = MesogleaCollection.zipMap(WWBlockItemIds.NEMATOCYST, WWBlocks.NEMATOCYST, REGISTER::registerSimpleBlockItem);

	// PEARL
	public static final DeferredItem<BlockItem> PEARL_BLOCK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PEARL_BLOCK,
		WWBlocks.PEARL_BLOCK,
		properties -> properties.requiredFeatures(WWFeatureFlags.WILDER_AQUATIC)
	);
	public static final DeferredItem<BlockItem> CUT_POLISHED_PEARL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CUT_POLISHED_PEARL,
		WWBlocks.CUT_POLISHED_PEARL,
		properties -> properties.requiredFeatures(WWFeatureFlags.WILDER_AQUATIC)
	);
	public static final DeferredItem<BlockItem> CUT_POLISHED_PEARL_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CUT_POLISHED_PEARL_STAIRS,
		WWBlocks.CUT_POLISHED_PEARL_STAIRS,
		properties -> properties.requiredFeatures(WWFeatureFlags.WILDER_AQUATIC)
	);
	public static final DeferredItem<BlockItem> CUT_POLISHED_PEARL_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CUT_POLISHED_PEARL_SLAB,
		WWBlocks.CUT_POLISHED_PEARL_SLAB,
		properties -> properties.requiredFeatures(WWFeatureFlags.WILDER_AQUATIC)
	);

	// MISC
	public static final DeferredItem<BlockItem> TERMITE_MOUND = REGISTER.registerSimpleBlockItem(WWBlockItemIds.TERMITE_MOUND, WWBlocks.TERMITE_MOUND);
	public static final DeferredItem<BlockItem> STONE_CHEST = REGISTER.registerSimpleBlockItem(WWBlockItemIds.STONE_CHEST, WWBlocks.STONE_CHEST);
	public static final DeferredItem<BlockItem> NULL_BLOCK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.NULL_BLOCK, WWBlocks.NULL_BLOCK);
	public static final DeferredItem<BlockItem> DISPLAY_LANTERN = REGISTER.registerSimpleBlockItem(WWBlockItemIds.DISPLAY_LANTERN, WWBlocks.DISPLAY_LANTERN,
		properties -> properties.component(WWDataComponents.FIREFLIES.get(), List.of())
	);

	// FLOWERS
	public static final DeferredItem<BlockItem> SEEDING_DANDELION = REGISTER.registerFlower(WWBlockItemIds.SEEDING_DANDELION, WWBlocks.SEEDING_DANDELION);
	public static final DeferredItem<BlockItem> CARNATION = REGISTER.registerFlower(WWBlockItemIds.CARNATION, WWBlocks.CARNATION);
	public static final DeferredItem<BlockItem> MARIGOLD = REGISTER.registerFlower(WWBlockItemIds.MARIGOLD, WWBlocks.MARIGOLD);
	public static final DeferredItem<BlockItem> PASQUEFLOWER = REGISTER.registerFlower(WWBlockItemIds.PASQUEFLOWER, WWBlocks.PASQUEFLOWER);
	public static final DeferredItem<BlockItem> RED_HIBISCUS = REGISTER.registerFlower(WWBlockItemIds.RED_HIBISCUS, WWBlocks.RED_HIBISCUS);
	public static final DeferredItem<BlockItem> YELLOW_HIBISCUS = REGISTER.registerFlower(WWBlockItemIds.YELLOW_HIBISCUS, WWBlocks.YELLOW_HIBISCUS);
	public static final DeferredItem<BlockItem> WHITE_HIBISCUS = REGISTER.registerFlower(WWBlockItemIds.WHITE_HIBISCUS, WWBlocks.WHITE_HIBISCUS);
	public static final DeferredItem<BlockItem> PINK_HIBISCUS = REGISTER.registerFlower(WWBlockItemIds.PINK_HIBISCUS, WWBlocks.PINK_HIBISCUS);
	public static final DeferredItem<BlockItem> PURPLE_HIBISCUS = REGISTER.registerFlower(WWBlockItemIds.PURPLE_HIBISCUS, WWBlocks.PURPLE_HIBISCUS);

	// FLOWERBEDS
	public static final DeferredItem<BlockItem> PHLOX = REGISTER.registerFlowerBed(WWBlockItemIds.PHLOX, WWBlocks.PHLOX);
	public static final DeferredItem<BlockItem> LANTANAS = REGISTER.registerFlowerBed(WWBlockItemIds.LANTANAS, WWBlocks.LANTANAS);
	public static final DeferredItem<BlockItem> CLOVERS = REGISTER.registerFlowerBed(WWBlockItemIds.CLOVERS, WWBlocks.CLOVERS);

	// TALL FLOWERS
	public static final DeferredItem<DoubleHighBlockItem> DATURA = REGISTER.registerDoubleTallFlower(WWBlockItemIds.DATURA, WWBlocks.DATURA);
	public static final DeferredItem<DoubleHighBlockItem> MILKWEED = REGISTER.registerDoubleTallFlower(WWBlockItemIds.MILKWEED, WWBlocks.MILKWEED);

	// VEGETATION
	public static final DeferredItem<BlockItem> POLLEN = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLLEN, WWBlocks.POLLEN,
		() -> new Item.Properties().compostable(WWContextIntProviders.COMPOSTABLE_POLLEN)
	);
	public static final DeferredItem<BlockItem> PRICKLY_PEAR = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PRICKLY_PEAR,
		WWBlocks.PRICKLY_PEAR,
		properties -> properties.food(WWFoods.PRICKLY_PEAR)
			.component(DataComponents.FOOD, WWFoods.PRICKLY_PEAR)
			.compostable(ContextIntProviders.COMPOSTABLE_LOW_MEDIUM)
			.delayedComponent(
				DataComponents.CONSUMABLE,
				provider -> Consumables.defaultFood()
					.onConsume(new DamageConsumeEffect(2F, Optional.empty(), provider.getOrThrow(WWDamageTypes.PRICKLY_PEAR)))
					.build()
			)
	);
	public static final DeferredItem<BlockItem> SHRUB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SHRUB, WWBlocks.SHRUB,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<BlockItem> TUMBLEWEED_PLANT = REGISTER.registerSimpleBlockItem(WWBlockItemIds.TUMBLEWEED_PLANT, WWBlocks.TUMBLEWEED_PLANT,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_LOW_MEDIUM)
	);
	public static final DeferredItem<TumbleweedItem> TUMBLEWEED = REGISTER.registerItem(WWBlockItemIds.TUMBLEWEED.item(),
		TumbleweedItem::new,
		() -> new Item.Properties()
			.useBlockDescriptionPrefix()
			.requiredFeatures(WWBlocks.TUMBLEWEED.get().requiredFeatures())
			.compostable(ContextIntProviders.COMPOSTABLE_LOW)
			.dispensed(WWDispenseBehaviorIds.TUMBLEWEED)
	);
	public static final DeferredItem<BlockItem> FROZEN_SHORT_GRASS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_SHORT_GRASS,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<DoubleHighBlockItem> FROZEN_TALL_GRASS = REGISTER.registerDoubleHighBlock(WWBlockItemIds.FROZEN_TALL_GRASS, WWBlocks.FROZEN_TALL_GRASS,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_LOW_MEDIUM)
	);
	public static final DeferredItem<BlockItem> FROZEN_FERN = REGISTER.registerSimpleBlockItem(WWBlockItemIds.FROZEN_FERN, WWBlocks.FROZEN_FERN,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)
	);
	public static final DeferredItem<DoubleHighBlockItem> FROZEN_LARGE_FERN = REGISTER.registerDoubleHighBlock(WWBlockItemIds.FROZEN_LARGE_FERN, WWBlocks.FROZEN_LARGE_FERN,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)
	);
	public static final DeferredItem<BlockItem> FROZEN_BUSH = REGISTER.registerSimpleBlockItem(WWBlockItemIds.FROZEN_BUSH, WWBlocks.FROZEN_BUSH,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<BlockItem> MYCELIUM_GROWTH = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MYCELIUM_GROWTH, WWBlocks.MYCELIUM_GROWTH,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);

	// MUSHROOMS
	public static final DeferredItem<BlockItem> PALE_MUSHROOM_BLOCK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALE_MUSHROOM_BLOCK, WWBlocks.PALE_MUSHROOM_BLOCK,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)
	);
	public static final DeferredItem<BlockItem> PALE_MUSHROOM = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALE_MUSHROOM, WWBlocks.PALE_MUSHROOM,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)
	);

	// MOSS
	public static final DeferredItem<BlockItem> AUBURN_MOSS_BLOCK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.AUBURN_MOSS_BLOCK, WWBlocks.AUBURN_MOSS_BLOCK,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)
	);
	public static final DeferredItem<BlockItem> AUBURN_MOSS_CARPET = REGISTER.registerSimpleBlockItem(WWBlockItemIds.AUBURN_MOSS_CARPET, WWBlocks.AUBURN_MOSS_CARPET,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<BlockItem> AUBURN_CREEPING_MOSS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.AUBURN_CREEPING_MOSS, WWBlocks.AUBURN_CREEPING_MOSS,
		() -> new Item.Properties().compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);

	// AQUATIC
	public static final DeferredItem<DoubleHighBlockItem> CATTAIL = REGISTER.registerDoubleHighBlock(WWBlockItemIds.CATTAIL, WWBlocks.CATTAIL);
	public static final DeferredItem<PlaceOnFluidBlockItem> FLOWERING_LILY_PAD = REGISTER.registerPlaceOnFluidBlock(WWBlockItemIds.FLOWERING_LILY_PAD, WWBlocks.FLOWERING_LILY_PAD,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)
	);
	public static final DeferredItem<PlaceOnFluidBlockItem> ALGAE = REGISTER.registerPlaceOnFluidBlock(WWBlockItemIds.ALGAE, WWBlocks.ALGAE,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_LOW)
	);
	public static final DeferredItem<PlaceOnFluidBlockItem> PLANKTON = REGISTER.registerPlaceOnFluidBlock(WWBlockItemIds.PLANKTON, WWBlocks.PLANKTON,
		properties -> properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)
	);
	public static final DeferredItem<BlockItem> SPONGE_BUD = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SPONGE_BUD, WWBlocks.SPONGE_BUD);
	public static final DeferredItem<BlockItem> BARNACLES = REGISTER.registerSimpleBlockItem(WWBlockItemIds.BARNACLES, WWBlocks.BARNACLES);
	public static final DeferredItem<BlockItem> SEA_ANEMONE = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SEA_ANEMONE, WWBlocks.SEA_ANEMONE);
	public static final DeferredItem<BlockItem> SEA_WHIP = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SEA_WHIP, WWBlocks.SEA_WHIP);
	public static final DeferredItem<BlockItem> TUBE_WORMS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.TUBE_WORMS, WWBlocks.TUBE_WORMS);

	// EGGS
	public static final DeferredItem<BlockItem> OSTRICH_EGG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.OSTRICH_EGG, WWBlocks.OSTRICH_EGG);
	public static final DeferredItem<BlockItem> PENGUIN_EGG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PENGUIN_EGG, WWBlocks.PENGUIN_EGG);

	// GABBRO
	public static final DeferredItem<BlockItem> GABBRO = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO, WWBlocks.GABBRO);
	public static final DeferredItem<BlockItem> GABBRO_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_STAIRS, WWBlocks.GABBRO_STAIRS);
	public static final DeferredItem<BlockItem> GABBRO_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_SLAB, WWBlocks.GABBRO_SLAB);
	public static final DeferredItem<BlockItem> GABBRO_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_WALL, WWBlocks.GABBRO_WALL);
	public static final DeferredItem<BlockItem> GEOTHERMAL_VENT = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GEOTHERMAL_VENT, WWBlocks.GEOTHERMAL_VENT);

	public static final DeferredItem<BlockItem> POLISHED_GABBRO = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLISHED_GABBRO, WWBlocks.POLISHED_GABBRO);
	public static final DeferredItem<BlockItem> POLISHED_GABBRO_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO_STAIRS);
	public static final DeferredItem<BlockItem> POLISHED_GABBRO_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO_SLAB);
	public static final DeferredItem<BlockItem> POLISHED_GABBRO_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.POLISHED_GABBRO_WALL, WWBlocks.POLISHED_GABBRO_WALL);

	public static final DeferredItem<BlockItem> GABBRO_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_BRICKS, WWBlocks.GABBRO_BRICKS);
	public static final DeferredItem<BlockItem> GABBRO_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> GABBRO_BRICK_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICK_SLAB);
	public static final DeferredItem<BlockItem> GABBRO_BRICK_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.GABBRO_BRICK_WALL, WWBlocks.GABBRO_BRICK_WALL);
	public static final DeferredItem<BlockItem> CRACKED_GABBRO_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CRACKED_GABBRO_BRICKS, WWBlocks.CRACKED_GABBRO_BRICKS);
	public static final DeferredItem<BlockItem> CHISELED_GABBRO_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.CHISELED_GABBRO_BRICKS, WWBlocks.CHISELED_GABBRO_BRICKS);

	public static final DeferredItem<BlockItem> MOSSY_GABBRO_BRICKS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICKS);
	public static final DeferredItem<BlockItem> MOSSY_GABBRO_BRICK_STAIRS = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICK_STAIRS);
	public static final DeferredItem<BlockItem> MOSSY_GABBRO_BRICK_SLAB = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICK_SLAB);
	public static final DeferredItem<BlockItem> MOSSY_GABBRO_BRICK_WALL = REGISTER.registerSimpleBlockItem(WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL, WWBlocks.MOSSY_GABBRO_BRICK_WALL);

	// BAOBAB
	public static final DeferredItem<BlockItem> BAOBAB_PLANKS = REGISTER.registerOverworldWood(WWBlockItemIds.BAOBAB_PLANKS, WWBlocks.BAOBAB_PLANKS);
	public static final DeferredItem<BlockItem> BAOBAB_STAIRS = REGISTER.registerOverworldWood(WWBlockItemIds.BAOBAB_STAIRS, WWBlocks.BAOBAB_STAIRS);
	public static final DeferredItem<BlockItem> BAOBAB_FENCE_GATE = REGISTER.registerCookableFenceGate(WWBlockItemIds.BAOBAB_FENCE_GATE, WWBlocks.BAOBAB_FENCE_GATE);
	public static final DeferredItem<BlockItem> BAOBAB_SLAB = REGISTER.registerOverworldWoodSlab(WWBlockItemIds.BAOBAB_SLAB, WWBlocks.BAOBAB_SLAB);
	public static final DeferredItem<BlockItem> BAOBAB_PRESSURE_PLATE = REGISTER.registerCookablePressurePlate(WWBlockItemIds.BAOBAB_PRESSURE_PLATE, WWBlocks.BAOBAB_PRESSURE_PLATE);
	public static final DeferredItem<BlockItem> BAOBAB_BUTTON = REGISTER.registerCookableButton(WWBlockItemIds.BAOBAB_BUTTON, WWBlocks.BAOBAB_BUTTON);
	public static final DeferredItem<DoubleHighBlockItem> BAOBAB_DOOR = REGISTER.registerCookableDoor(WWBlockItemIds.BAOBAB_DOOR, WWBlocks.BAOBAB_DOOR);
	public static final DeferredItem<BlockItem> BAOBAB_TRAPDOOR = REGISTER.registerCookableTrapdoor(WWBlockItemIds.BAOBAB_TRAPDOOR, WWBlocks.BAOBAB_TRAPDOOR);
	public static final DeferredItem<BlockItem> BAOBAB_FENCE = REGISTER.registerCookableFence(WWBlockItemIds.BAOBAB_FENCE, WWBlocks.BAOBAB_FENCE);
	public static final DeferredItem<BlockItem> BAOBAB_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.BAOBAB_LOG, WWBlocks.BAOBAB_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_BAOBAB_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_BAOBAB_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
	public static final DeferredItem<BlockItem> BAOBAB_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.BAOBAB_WOOD, WWBlocks.BAOBAB_WOOD);
	public static final DeferredItem<BlockItem> STRIPPED_BAOBAB_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_WOOD);
	public static final DeferredItem<StandingAndWallBlockItem> BAOBAB_SIGN = REGISTER.registerCookableSign(WWBlockItemIds.BAOBAB_SIGN, WWBlocks.BAOBAB_SIGN, WWBlocks.BAOBAB_WALL_SIGN);
	public static final DeferredItem<HangingSignItem> BAOBAB_HANGING_SIGN = REGISTER.registerCookableHangingSign(WWBlockItemIds.BAOBAB_HANGING_SIGN, WWBlocks.BAOBAB_HANGING_SIGN, WWBlocks.BAOBAB_WALL_HANGING_SIGN);
	public static final DeferredItem<BlockItem> BAOBAB_SHELF = REGISTER.registerCookableShelf(WWBlockItemIds.BAOBAB_SHELF, WWBlocks.BAOBAB_SHELF);

	// WILLOW
	public static final DeferredItem<BlockItem> WILLOW_PLANKS = REGISTER.registerOverworldWood(WWBlockItemIds.WILLOW_PLANKS, WWBlocks.WILLOW_PLANKS);
	public static final DeferredItem<BlockItem> WILLOW_STAIRS = REGISTER.registerOverworldWood(WWBlockItemIds.WILLOW_STAIRS, WWBlocks.WILLOW_STAIRS);
	public static final DeferredItem<BlockItem> WILLOW_FENCE_GATE = REGISTER.registerCookableFenceGate(WWBlockItemIds.WILLOW_FENCE_GATE, WWBlocks.WILLOW_FENCE_GATE);
	public static final DeferredItem<BlockItem> WILLOW_SLAB = REGISTER.registerOverworldWoodSlab(WWBlockItemIds.WILLOW_SLAB, WWBlocks.WILLOW_SLAB);
	public static final DeferredItem<BlockItem> WILLOW_PRESSURE_PLATE = REGISTER.registerCookablePressurePlate(WWBlockItemIds.WILLOW_PRESSURE_PLATE, WWBlocks.WILLOW_PRESSURE_PLATE);
	public static final DeferredItem<BlockItem> WILLOW_BUTTON = REGISTER.registerCookableButton(WWBlockItemIds.WILLOW_BUTTON, WWBlocks.WILLOW_BUTTON);
	public static final DeferredItem<DoubleHighBlockItem> WILLOW_DOOR = REGISTER.registerCookableDoor(WWBlockItemIds.WILLOW_DOOR, WWBlocks.WILLOW_DOOR);
	public static final DeferredItem<BlockItem> WILLOW_TRAPDOOR = REGISTER.registerCookableTrapdoor(WWBlockItemIds.WILLOW_TRAPDOOR, WWBlocks.WILLOW_TRAPDOOR);
	public static final DeferredItem<BlockItem> WILLOW_FENCE = REGISTER.registerCookableFence(WWBlockItemIds.WILLOW_FENCE, WWBlocks.WILLOW_FENCE);
	public static final DeferredItem<BlockItem> WILLOW_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.WILLOW_LOG, WWBlocks.WILLOW_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_WILLOW_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_WILLOW_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
	public static final DeferredItem<BlockItem> WILLOW_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.WILLOW_WOOD, WWBlocks.WILLOW_WOOD);
	public static final DeferredItem<BlockItem> STRIPPED_WILLOW_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_WOOD);
	public static final DeferredItem<StandingAndWallBlockItem> WILLOW_SIGN = REGISTER.registerCookableSign(WWBlockItemIds.WILLOW_SIGN, WWBlocks.WILLOW_SIGN, WWBlocks.WILLOW_WALL_SIGN);
	public static final DeferredItem<HangingSignItem> WILLOW_HANGING_SIGN = REGISTER.registerCookableHangingSign(WWBlockItemIds.WILLOW_HANGING_SIGN, WWBlocks.WILLOW_HANGING_SIGN, WWBlocks.WILLOW_WALL_HANGING_SIGN);
	public static final DeferredItem<BlockItem> WILLOW_SHELF = REGISTER.registerCookableShelf(WWBlockItemIds.WILLOW_SHELF, WWBlocks.WILLOW_SHELF);

	// CYPRESS
	public static final DeferredItem<BlockItem> CYPRESS_PLANKS = REGISTER.registerOverworldWood(WWBlockItemIds.CYPRESS_PLANKS, WWBlocks.CYPRESS_PLANKS);
	public static final DeferredItem<BlockItem> CYPRESS_STAIRS = REGISTER.registerOverworldWood(WWBlockItemIds.CYPRESS_STAIRS, WWBlocks.CYPRESS_STAIRS);
	public static final DeferredItem<BlockItem> CYPRESS_FENCE_GATE = REGISTER.registerCookableFenceGate(WWBlockItemIds.CYPRESS_FENCE_GATE, WWBlocks.CYPRESS_FENCE_GATE);
	public static final DeferredItem<BlockItem> CYPRESS_SLAB = REGISTER.registerOverworldWoodSlab(WWBlockItemIds.CYPRESS_SLAB, WWBlocks.CYPRESS_SLAB);
	public static final DeferredItem<BlockItem> CYPRESS_PRESSURE_PLATE = REGISTER.registerCookablePressurePlate(WWBlockItemIds.CYPRESS_PRESSURE_PLATE, WWBlocks.CYPRESS_PRESSURE_PLATE);
	public static final DeferredItem<BlockItem> CYPRESS_BUTTON = REGISTER.registerCookableButton(WWBlockItemIds.CYPRESS_BUTTON, WWBlocks.CYPRESS_BUTTON);
	public static final DeferredItem<DoubleHighBlockItem> CYPRESS_DOOR = REGISTER.registerCookableDoor(WWBlockItemIds.CYPRESS_DOOR, WWBlocks.CYPRESS_DOOR);
	public static final DeferredItem<BlockItem> CYPRESS_TRAPDOOR = REGISTER.registerCookableTrapdoor(WWBlockItemIds.CYPRESS_TRAPDOOR, WWBlocks.CYPRESS_TRAPDOOR);
	public static final DeferredItem<BlockItem> CYPRESS_FENCE = REGISTER.registerCookableFence(WWBlockItemIds.CYPRESS_FENCE, WWBlocks.CYPRESS_FENCE);
	public static final DeferredItem<BlockItem> CYPRESS_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.CYPRESS_LOG, WWBlocks.CYPRESS_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_CYPRESS_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_CYPRESS_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
	public static final DeferredItem<BlockItem> CYPRESS_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.CYPRESS_WOOD, WWBlocks.CYPRESS_WOOD);
	public static final DeferredItem<BlockItem> STRIPPED_CYPRESS_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_WOOD);
	public static final DeferredItem<StandingAndWallBlockItem> CYPRESS_SIGN = REGISTER.registerCookableSign(WWBlockItemIds.CYPRESS_SIGN, WWBlocks.CYPRESS_SIGN, WWBlocks.CYPRESS_WALL_SIGN);
	public static final DeferredItem<HangingSignItem> CYPRESS_HANGING_SIGN = REGISTER.registerCookableHangingSign(WWBlockItemIds.CYPRESS_HANGING_SIGN, WWBlocks.CYPRESS_HANGING_SIGN, WWBlocks.CYPRESS_WALL_HANGING_SIGN);
	public static final DeferredItem<BlockItem> CYPRESS_SHELF = REGISTER.registerCookableShelf(WWBlockItemIds.CYPRESS_SHELF, WWBlocks.CYPRESS_SHELF);

	// PALM
	public static final DeferredItem<BlockItem> PALM_PLANKS = REGISTER.registerOverworldWood(WWBlockItemIds.PALM_PLANKS, WWBlocks.PALM_PLANKS);
	public static final DeferredItem<BlockItem> PALM_STAIRS = REGISTER.registerOverworldWood(WWBlockItemIds.PALM_STAIRS, WWBlocks.PALM_STAIRS);
	public static final DeferredItem<BlockItem> PALM_FENCE_GATE = REGISTER.registerCookableFenceGate(WWBlockItemIds.PALM_FENCE_GATE, WWBlocks.PALM_FENCE_GATE);
	public static final DeferredItem<BlockItem> PALM_SLAB = REGISTER.registerOverworldWoodSlab(WWBlockItemIds.PALM_SLAB, WWBlocks.PALM_SLAB);
	public static final DeferredItem<BlockItem> PALM_PRESSURE_PLATE = REGISTER.registerCookablePressurePlate(WWBlockItemIds.PALM_PRESSURE_PLATE, WWBlocks.PALM_PRESSURE_PLATE);
	public static final DeferredItem<BlockItem> PALM_BUTTON = REGISTER.registerCookableButton(WWBlockItemIds.PALM_BUTTON, WWBlocks.PALM_BUTTON);
	public static final DeferredItem<DoubleHighBlockItem> PALM_DOOR = REGISTER.registerCookableDoor(WWBlockItemIds.PALM_DOOR, WWBlocks.PALM_DOOR);
	public static final DeferredItem<BlockItem> PALM_TRAPDOOR = REGISTER.registerCookableTrapdoor(WWBlockItemIds.PALM_TRAPDOOR, WWBlocks.PALM_TRAPDOOR);
	public static final DeferredItem<BlockItem> PALM_FENCE = REGISTER.registerCookableFence(WWBlockItemIds.PALM_FENCE, WWBlocks.PALM_FENCE);
	public static final DeferredItem<BlockItem> PALM_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.PALM_LOG, WWBlocks.PALM_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_PALM_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_PALM_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_PALM_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
	public static final DeferredItem<BlockItem> PALM_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.PALM_WOOD, WWBlocks.PALM_WOOD);
	public static final DeferredItem<BlockItem> STRIPPED_PALM_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_PALM_WOOD, WWBlocks.STRIPPED_PALM_WOOD);
	public static final DeferredItem<StandingAndWallBlockItem> PALM_SIGN = REGISTER.registerCookableSign(WWBlockItemIds.PALM_SIGN, WWBlocks.PALM_SIGN, WWBlocks.PALM_WALL_SIGN);
	public static final DeferredItem<HangingSignItem> PALM_HANGING_SIGN = REGISTER.registerCookableHangingSign(WWBlockItemIds.PALM_HANGING_SIGN, WWBlocks.PALM_HANGING_SIGN, WWBlocks.PALM_WALL_HANGING_SIGN);
	public static final DeferredItem<BlockItem> PALM_SHELF = REGISTER.registerCookableShelf(WWBlockItemIds.PALM_SHELF, WWBlocks.PALM_SHELF);

	// MAPLE
	public static final DeferredItem<BlockItem> MAPLE_PLANKS = REGISTER.registerOverworldWood(WWBlockItemIds.MAPLE_PLANKS, WWBlocks.MAPLE_PLANKS);
	public static final DeferredItem<BlockItem> MAPLE_STAIRS = REGISTER.registerOverworldWood(WWBlockItemIds.MAPLE_STAIRS, WWBlocks.MAPLE_STAIRS);
	public static final DeferredItem<BlockItem> MAPLE_FENCE_GATE = REGISTER.registerCookableFenceGate(WWBlockItemIds.MAPLE_FENCE_GATE, WWBlocks.MAPLE_FENCE_GATE);
	public static final DeferredItem<BlockItem> MAPLE_SLAB = REGISTER.registerOverworldWoodSlab(WWBlockItemIds.MAPLE_SLAB, WWBlocks.MAPLE_SLAB);
	public static final DeferredItem<BlockItem> MAPLE_PRESSURE_PLATE = REGISTER.registerCookablePressurePlate(WWBlockItemIds.MAPLE_PRESSURE_PLATE, WWBlocks.MAPLE_PRESSURE_PLATE);
	public static final DeferredItem<BlockItem> MAPLE_BUTTON = REGISTER.registerCookableButton(WWBlockItemIds.MAPLE_BUTTON, WWBlocks.MAPLE_BUTTON);
	public static final DeferredItem<DoubleHighBlockItem> MAPLE_DOOR = REGISTER.registerCookableDoor(WWBlockItemIds.MAPLE_DOOR, WWBlocks.MAPLE_DOOR);
	public static final DeferredItem<BlockItem> MAPLE_TRAPDOOR = REGISTER.registerCookableTrapdoor(WWBlockItemIds.MAPLE_TRAPDOOR, WWBlocks.MAPLE_TRAPDOOR);
	public static final DeferredItem<BlockItem> MAPLE_FENCE = REGISTER.registerCookableFence(WWBlockItemIds.MAPLE_FENCE, WWBlocks.MAPLE_FENCE);
	public static final DeferredItem<BlockItem> MAPLE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.MAPLE_LOG, WWBlocks.MAPLE_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_MAPLE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG);
	public static final DeferredItem<BlockItem> STRIPPED_HOLLOWED_MAPLE_LOG = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
	public static final DeferredItem<BlockItem> MAPLE_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.MAPLE_WOOD, WWBlocks.MAPLE_WOOD);
	public static final DeferredItem<BlockItem> STRIPPED_MAPLE_WOOD = REGISTER.registerOverworldWood(WWBlockItemIds.STRIPPED_MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_WOOD);
	public static final DeferredItem<StandingAndWallBlockItem> MAPLE_SIGN = REGISTER.registerCookableSign(WWBlockItemIds.MAPLE_SIGN, WWBlocks.MAPLE_SIGN, WWBlocks.MAPLE_WALL_SIGN);
	public static final DeferredItem<HangingSignItem> MAPLE_HANGING_SIGN = REGISTER.registerCookableHangingSign(WWBlockItemIds.MAPLE_HANGING_SIGN, WWBlocks.MAPLE_HANGING_SIGN, WWBlocks.MAPLE_WALL_HANGING_SIGN);
	public static final DeferredItem<BlockItem> MAPLE_SHELF = REGISTER.registerCookableShelf(WWBlockItemIds.MAPLE_SHELF, WWBlocks.MAPLE_SHELF);

	// FROGLIGHT GOOP
	public static final DeferredItem<BlockItem> OCHRE_FROGLIGHT_GOOP = REGISTER.registerSimpleBlockItem(WWBlockItemIds.OCHRE_FROGLIGHT_GOOP, WWBlocks.OCHRE_FROGLIGHT_GOOP);
	public static final DeferredItem<BlockItem> VERDANT_FROGLIGHT_GOOP = REGISTER.registerSimpleBlockItem(WWBlockItemIds.VERDANT_FROGLIGHT_GOOP, WWBlocks.VERDANT_FROGLIGHT_GOOP);
	public static final DeferredItem<BlockItem> PEARLESCENT_FROGLIGHT_GOOP = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PEARLESCENT_FROGLIGHT_GOOP, WWBlocks.PEARLESCENT_FROGLIGHT_GOOP);

	// ITEMS

	// BOATS
	public static final DeferredItem<BoatItem> BAOBAB_BOAT = REGISTER.registerBoat(WWItemIds.BAOBAB_BOAT, () -> WWEntityTypes.BAOBAB_BOAT.get());
	public static final DeferredItem<BoatItem> BAOBAB_CHEST_BOAT = REGISTER.registerBoat(WWItemIds.BAOBAB_CHEST_BOAT, () -> WWEntityTypes.BAOBAB_CHEST_BOAT.get());
	public static final DeferredItem<BoatItem> WILLOW_BOAT = REGISTER.registerBoat(WWItemIds.WILLOW_BOAT, () -> WWEntityTypes.WILLOW_BOAT.get());
	public static final DeferredItem<BoatItem> WILLOW_CHEST_BOAT = REGISTER.registerBoat(WWItemIds.WILLOW_CHEST_BOAT, () -> WWEntityTypes.WILLOW_CHEST_BOAT.get());
	public static final DeferredItem<BoatItem> CYPRESS_BOAT = REGISTER.registerBoat(WWItemIds.CYPRESS_BOAT, () -> WWEntityTypes.CYPRESS_BOAT.get());
	public static final DeferredItem<BoatItem> CYPRESS_CHEST_BOAT = REGISTER.registerBoat(WWItemIds.CYPRESS_CHEST_BOAT, () -> WWEntityTypes.CYPRESS_CHEST_BOAT.get());
	public static final DeferredItem<BoatItem> PALM_BOAT = REGISTER.registerBoat(WWItemIds.PALM_BOAT, () -> WWEntityTypes.PALM_BOAT.get());
	public static final DeferredItem<BoatItem> PALM_CHEST_BOAT = REGISTER.registerBoat(WWItemIds.PALM_CHEST_BOAT, () -> WWEntityTypes.PALM_CHEST_BOAT.get());
	public static final DeferredItem<BoatItem> MAPLE_BOAT = REGISTER.registerBoat(WWItemIds.MAPLE_BOAT, () -> WWEntityTypes.MAPLE_BOAT.get());
	public static final DeferredItem<BoatItem> MAPLE_CHEST_BOAT = REGISTER.registerBoat(WWItemIds.MAPLE_CHEST_BOAT, () -> WWEntityTypes.MAPLE_CHEST_BOAT.get());

	public static final DeferredItem<MilkweedPodItem> MILKWEED_POD = REGISTER.registerItem(WWItemIds.MILKWEED_POD,
		MilkweedPodItem::new,
		() -> new Item.Properties().compostable(WWContextIntProviders.COMPOSTABLE_MILKWEED_POD)
	);
	public static final DeferredItem<MobBottleItem> FIREFLY_BOTTLE = REGISTER.registerItem(WWItemIds.FIREFLY_BOTTLE,
		properties -> new MobBottleItem(
			WWEntityTypes.FIREFLY.get(),
			WWSounds.ITEM_BOTTLE_RELEASE_FIREFLY.get(),
			properties
		),
		() -> new Item.Properties()
			.stacksTo(16)
			.component(WWDataComponents.BOTTLE_ENTITY_DATA.get(), CustomData.EMPTY)
			.delayedComponent(
				WWDataComponents.FIREFLY_VARIANT.get(),
				context -> context.lookupOrThrow(WilderWildRegistries.FIREFLY_VARIANT).get(FireflyVariants.DEFAULT).orElse(null)
			)
			.dispensed(WWDispenseBehaviorIds.FILLED_MOB_BOTTLE)
	);
	public static final DeferredItem<MobBottleItem> BUTTERFLY_BOTTLE = REGISTER.registerItem(WWItemIds.BUTTERFLY_BOTTLE,
		properties -> new MobBottleItem(
			WWEntityTypes.BUTTERFLY.get(),
			WWSounds.ITEM_BOTTLE_RELEASE_BUTTERFLY.get(),
			properties
		),
		() -> new Item.Properties()
			.stacksTo(1)
			.component(WWDataComponents.BOTTLE_ENTITY_DATA.get(), CustomData.EMPTY)
			.dispensed(WWDispenseBehaviorIds.FILLED_MOB_BOTTLE)
	);
	public static final DeferredItem<Item> PEARL = REGISTER.registerSimpleItem(WWItemIds.PEARL,
		() -> new Item.Properties().trimMaterial(WWTrimMaterials.PEARL).requiredFeatures(WWFeatureFlags.WILDER_AQUATIC)
	);
	public static final DeferredItem<HarpoonItem> HARPOON = REGISTER.registerItem(WWItemIds.HARPOON,
		HarpoonItem::new,
		() -> new Item.Properties().requiredFeatures(WWFeatureFlags.WILDER_AQUATIC)
			.rarity(Rarity.UNCOMMON)
			.stacksTo(1)
			.durability(64)
			.enchantable(1)
	);

	// FOOD
	public static final DeferredItem<Item> PEELED_PRICKLY_PEAR = REGISTER.registerSimpleItem(WWItemIds.PEELED_PRICKLY_PEAR,
		() -> new Item.Properties().food(Foods.APPLE).compostable(ContextIntProviders.COMPOSTABLE_LOW_MEDIUM)
	);
	public static final DeferredItem<Item> SPLIT_COCONUT = REGISTER.registerSimpleItem(WWItemIds.SPLIT_COCONUT,
		() -> new Item.Properties().food(WWFoods.SPLIT_COCONUT).compostable(ContextIntProviders.COMPOSTABLE_MEDIUM).cookingFuel(ContextIntProviders.COOKING_TIME_DRY_PLANTS)
	);
	public static final DeferredItem<CrabClawItem> CRAB_CLAW = REGISTER.registerItem(WWItemIds.CRAB_CLAW,
		CrabClawItem::new,
		() -> new Item.Properties().food(WWFoods.CRAB_CLAW)
	);
	public static final DeferredItem<Item> COOKED_CRAB_CLAW = REGISTER.registerSimpleItem(WWItemIds.COOKED_CRAB_CLAW,
		() -> new Item.Properties().food(WWFoods.COOKED_CRAB_CLAW)
	);
	public static final DeferredItem<Item> SCORCHED_EYE = REGISTER.registerSimpleItem(WWItemIds.SCORCHED_EYE,
		() -> new Item.Properties().food(WWFoods.SCORCHED_EYE, WWFoods.SCORCHED_EYE_CONSUMABLE)
	);
	public static final DeferredItem<Item> FERMENTED_SCORCHED_EYE = REGISTER.registerSimpleItem(WWItemIds.FERMENTED_SCORCHED_EYE);

	// SPAWN EGGS & BUCKETS
	public static final DeferredItem<MobBucketItem> JELLYFISH_BUCKET = REGISTER.registerItem(
		WWItemIds.JELLYFISH_BUCKET,
		properties -> new MobBucketItem(WWEntityTypes.JELLYFISH.get(), Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_JELLYFISH.get(), properties),
		() -> new Item.Properties().stacksTo(1)
			.component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
			.dispensed(DispenseBehaviorIds.FILLED_BUCKET)
	);
	public static final DeferredItem<MobBucketItem> CRAB_BUCKET = REGISTER.registerItem(
		WWItemIds.CRAB_BUCKET,
		properties -> new MobBucketItem(WWEntityTypes.CRAB.get(), Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_CRAB.get(), properties),
		() -> new Item.Properties().stacksTo(1)
			.component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
			.component(DataComponents.FOOD, WWFoods.CRAB_CLAW)
			.dispensed(DispenseBehaviorIds.FILLED_BUCKET)
	);

	public static final DeferredItem<SpawnEggItem> FIREFLY_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.FIREFLY_SPAWN_EGG, WWEntityTypes.FIREFLY);
	public static final DeferredItem<SpawnEggItem> BUTTERFLY_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.BUTTERFLY_SPAWN_EGG, WWEntityTypes.BUTTERFLY);
	public static final DeferredItem<SpawnEggItem> JELLYFISH_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.JELLYFISH_SPAWN_EGG, WWEntityTypes.JELLYFISH);
	public static final DeferredItem<SpawnEggItem> CRAB_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.CRAB_SPAWN_EGG, WWEntityTypes.CRAB);
	public static final DeferredItem<SpawnEggItem> OSTRICH_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.OSTRICH_SPAWN_EGG, WWEntityTypes.OSTRICH);
	public static final DeferredItem<SpawnEggItem> ZOMBIE_OSTRICH_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.ZOMBIE_OSTRICH_SPAWN_EGG, WWEntityTypes.ZOMBIE_OSTRICH);
	public static final DeferredItem<SpawnEggItem> SCORCHED_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.SCORCHED_SPAWN_EGG, WWEntityTypes.SCORCHED);
	public static final DeferredItem<SpawnEggItem> SUNKEN_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.SUNKEN_SPAWN_EGG, WWEntityTypes.SUNKEN, properties -> properties.requiredFeatures(WWFeatureFlags.WILDER_AQUATIC));
	public static final DeferredItem<SpawnEggItem> MOOBLOOM_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.MOOBLOOM_SPAWN_EGG, WWEntityTypes.MOOBLOOM);
	public static final DeferredItem<SpawnEggItem> PENGUIN_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.PENGUIN_SPAWN_EGG, WWEntityTypes.PENGUIN);

	static {
		REGISTER.register();
	}

	@ApiStatus.Internal
	public static void init() {}

	@ApiStatus.Internal
	public static void setup() {
		Item.BY_BLOCK.put(WWBlocks.TUMBLEWEED.get(), TUMBLEWEED.get());
	}
}
