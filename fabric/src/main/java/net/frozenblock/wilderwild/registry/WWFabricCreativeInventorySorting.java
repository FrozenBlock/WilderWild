package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public final class WWFabricCreativeInventorySorting {

	public static void init() {
		// BAOBAB (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(Items.MANGROVE_CHEST_BOAT, WWFabricItems.BAOBAB_BOAT);
		insertAfterInToolsAndUtilities(WWFabricItems.BAOBAB_BOAT, WWFabricItems.BAOBAB_CHEST_BOAT);

		// WILLOW (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(WWFabricItems.BAOBAB_CHEST_BOAT, WWFabricItems.WILLOW_BOAT);
		insertAfterInToolsAndUtilities(WWFabricItems.WILLOW_BOAT, WWFabricItems.WILLOW_CHEST_BOAT);

		// CYPRESS (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(WWFabricItems.WILLOW_CHEST_BOAT, WWFabricItems.CYPRESS_BOAT);
		insertAfterInToolsAndUtilities(WWFabricItems.CYPRESS_BOAT, WWFabricItems.CYPRESS_CHEST_BOAT);

		// PALM (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(WWFabricItems.CYPRESS_CHEST_BOAT, WWFabricItems.PALM_BOAT);
		insertAfterInToolsAndUtilities(WWFabricItems.PALM_BOAT, WWFabricItems.PALM_CHEST_BOAT);

		// MAPLE (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(Items.CHERRY_CHEST_BOAT, WWFabricItems.MAPLE_BOAT);
		insertAfterInToolsAndUtilities(WWFabricItems.MAPLE_BOAT, WWFabricItems.MAPLE_CHEST_BOAT);

		// SMALL FLOWERS
		insertAfterInNaturalBlocks(Items.DANDELION, WWFabricItems.SEEDING_DANDELION);

		// PLANTS
		insertAfterInNaturalBlocks(WWItems.PRICKLY_PEAR, WWFabricItems.TUMBLEWEED_PLANT);
		insertAfterInNaturalBlocks(WWFabricItems.TUMBLEWEED_PLANT, WWFabricItems.TUMBLEWEED);
		insertAfterInNaturalBlocks(WWFabricItems.TUMBLEWEED, WWItems.SHRUB);

		// PALE MUSHROOMS
		insertAfterInNaturalBlocks(Items.RED_MUSHROOM_BLOCK, WWFabricItems.PALE_MUSHROOM_BLOCK);

		// EGGS
		insertBeforeInNaturalBlocks(Items.SNIFFER_EGG, WWFabricItems.OSTRICH_EGG);
		insertAfterInNaturalBlocks(WWFabricItems.OSTRICH_EGG, WWFabricItems.PENGUIN_EGG);

		// BUTTERFLY
		insertAfterInSpawnEggs(Items.BEE_SPAWN_EGG, WWFabricItems.BUTTERFLY_SPAWN_EGG);
		insertAfterInToolsAndUtilities(WWItems.FIREFLY_BOTTLE, WWFabricItems.BUTTERFLY_BOTTLE);

		// CRAB
		insertAfterInSpawnEggs(Items.COD_SPAWN_EGG, WWFabricItems.CRAB_SPAWN_EGG);
		insertAfterInToolsAndUtilities(WWItems.JELLYFISH_BUCKET, WWFabricItems.CRAB_BUCKET);
		insertBeforeInFoodAndDrinks(Items.COD, WWFabricItems.CRAB_CLAW);
		insertAfterInFoodAndDrinks(WWFabricItems.CRAB_CLAW, WWItems.COOKED_CRAB_CLAW);

		// OSTRICH
		insertAfterInSpawnEggs(Items.MULE_SPAWN_EGG, WWFabricItems.OSTRICH_SPAWN_EGG);

		// ZOMBIE OSTRICH
		insertAfterInSpawnEggs(Items.ZOMBIE_NAUTILUS_SPAWN_EGG, WWFabricItems.ZOMBIE_OSTRICH_SPAWN_EGG);

		// SCORCHED
		insertBeforeInSpawnEggs(Items.SPIDER_SPAWN_EGG, WWFabricItems.SCORCHED_SPAWN_EGG);

		// MOOBLOOM
		insertBeforeInSpawnEggs(Items.MOOSHROOM_SPAWN_EGG, WWFabricItems.MOOBLOOM_SPAWN_EGG);

		// PENGUIN
		insertAfterInSpawnEggs(Items.PANDA_SPAWN_EGG, WWFabricItems.PENGUIN_SPAWN_EGG);
	}

	private static void insertBeforeInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertBeforeInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void insertAfterInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void insertBeforeInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

	private static void insertAfterInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}
}
