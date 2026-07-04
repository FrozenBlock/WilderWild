package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

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

		// COCONUT
		insertAfterInCombat(Items.BLUE_EGG, WWFabricItems.COCONUT);
		insertAfterInNaturalBlocks(WWItems.CYPRESS_SAPLING, WWFabricItems.COCONUT);

		// MAPLE (NATURAL BLOCKS)
		insertAfterInNaturalBlocks(Blocks.CHERRY_LEAVES, WWFabricItems.YELLOW_MAPLE_LEAVES);
		insertAfterInNaturalBlocks(WWFabricItems.YELLOW_MAPLE_LEAVES, WWFabricItems.ORANGE_MAPLE_LEAVES);
		insertAfterInNaturalBlocks(WWFabricItems.ORANGE_MAPLE_LEAVES, WWFabricItems.RED_MAPLE_LEAVES);

		// MAPLE (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(Items.CHERRY_CHEST_BOAT, WWFabricItems.MAPLE_BOAT);
		insertAfterInToolsAndUtilities(WWFabricItems.MAPLE_BOAT, WWFabricItems.MAPLE_CHEST_BOAT);

		// LEAF LITTERS
		insertAfterInNaturalBlocks(WWItems.CHERRY_LEAF_LITTER, WWFabricItems.YELLOW_MAPLE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWFabricItems.YELLOW_MAPLE_LEAF_LITTER, WWFabricItems.ORANGE_MAPLE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWFabricItems.ORANGE_MAPLE_LEAF_LITTER, WWFabricItems.RED_MAPLE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWFabricItems.RED_MAPLE_LEAF_LITTER, WWItems.PALE_OAK_LEAF_LITTER);

		// SMALL FLOWERS
		insertAfterInNaturalBlocks(Items.DANDELION, WWFabricItems.SEEDING_DANDELION);

		// TALL FLOWERS
		insertBeforeInIngredients(Items.INK_SAC, WWFabricItems.MILKWEED_POD);

		// PLANTS
		insertBeforeInNaturalBlocks(Items.GLOW_LICHEN, WWFabricItems.POLLEN);
		insertAfterInNaturalBlocks(WWItems.PRICKLY_PEAR, WWFabricItems.TUMBLEWEED_PLANT);
		insertAfterInNaturalBlocks(WWFabricItems.TUMBLEWEED_PLANT, WWFabricItems.TUMBLEWEED);
		insertAfterInNaturalBlocks(WWFabricItems.TUMBLEWEED, WWItems.SHRUB);
		insertAfterInNaturalBlocks(WWItems.ALGAE, WWFabricItems.PLANKTON);

		// SHELF FUNGI
		insertAfterInNaturalBlocks(Items.WARPED_FUNGUS, WWFabricItems.BROWN_SHELF_FUNGI);
		insertAfterInNaturalBlocks(WWFabricItems.BROWN_SHELF_FUNGI, WWFabricItems.RED_SHELF_FUNGI);
		insertAfterInNaturalBlocks(WWFabricItems.RED_SHELF_FUNGI, WWFabricItems.CRIMSON_SHELF_FUNGI);
		insertAfterInNaturalBlocks(WWFabricItems.CRIMSON_SHELF_FUNGI, WWFabricItems.WARPED_SHELF_FUNGI);

		// PALE MUSHROOMS
		insertAfterInNaturalBlocks(Items.RED_MUSHROOM_BLOCK, WWFabricItems.PALE_MUSHROOM_BLOCK);
		insertAfterInNaturalBlocks(Items.RED_MUSHROOM, WWFabricItems.PALE_MUSHROOM);
		insertAfterInNaturalBlocks(WWFabricItems.RED_SHELF_FUNGI, WWFabricItems.PALE_SHELF_FUNGI);

		// EGGS
		insertBeforeInNaturalBlocks(Items.SNIFFER_EGG, WWFabricItems.OSTRICH_EGG);
		insertAfterInNaturalBlocks(WWFabricItems.OSTRICH_EGG, WWFabricItems.PENGUIN_EGG);

		// MESOGLEA
		insertBeforeInNaturalBlocks(Items.SPONGE, WWFabricItems.PEARLESCENT_BLUE_MESOGLEA);
		insertAfterInNaturalBlocks(WWFabricItems.PEARLESCENT_BLUE_MESOGLEA, WWFabricItems.PEARLESCENT_PURPLE_MESOGLEA);
		insertAfterInNaturalBlocks(WWFabricItems.PEARLESCENT_PURPLE_MESOGLEA, WWFabricItems.BLUE_MESOGLEA);
		insertAfterInNaturalBlocks(WWFabricItems.BLUE_MESOGLEA, WWFabricItems.PINK_MESOGLEA);
		insertAfterInNaturalBlocks(WWFabricItems.PINK_MESOGLEA, WWFabricItems.RED_MESOGLEA);
		insertAfterInNaturalBlocks(WWFabricItems.RED_MESOGLEA, WWFabricItems.YELLOW_MESOGLEA);
		insertAfterInNaturalBlocks(WWFabricItems.YELLOW_MESOGLEA, WWFabricItems.LIME_MESOGLEA);

		// ICE
		insertAfterInNaturalBlocks(Items.BLUE_ICE, WWFabricItems.ICICLE);

		// SCULK
		insertAfterInNaturalBlocks(Items.SCULK_SENSOR, WWFabricItems.HANGING_TENDRIL);

		// SCORCHED SAND
		insertAfterInNaturalBlocks(Items.SAND, WWFabricItems.SCORCHED_SAND);
		insertAfterInNaturalBlocks(Items.RED_SAND, WWFabricItems.SCORCHED_RED_SAND);

		// STORAGE
		insertAfterInFunctionalBlocks(Items.CHEST, WWFabricItems.STONE_CHEST);
		insertAfterInFunctionalBlocks(Items.SOUL_LANTERN, WWFabricItems.DISPLAY_LANTERN);

		// FUNCTIONAL BLOCK ENTITIES
		insertBeforeInNaturalBlocks(Items.BEE_NEST, WWFabricItems.TERMITE_MOUND);
		insertBeforeInRedstoneBlocks(Items.SCULK_SENSOR, WWFabricItems.GEOTHERMAL_VENT);

		// BUTTERFLY
		insertAfterInSpawnEggs(Items.BEE_SPAWN_EGG, WWFabricItems.BUTTERFLY_SPAWN_EGG);
		insertAfterInToolsAndUtilities(WWItems.FIREFLY_BOTTLE, WWFabricItems.BUTTERFLY_BOTTLE);

		// JELLYFISH
		insertAfterInSpawnEggs(Items.GLOW_SQUID_SPAWN_EGG, WWFabricItems.JELLYFISH_SPAWN_EGG);
		insertAfterInToolsAndUtilities(Items.AXOLOTL_BUCKET, WWFabricItems.JELLYFISH_BUCKET);

		// CRAB
		insertAfterInSpawnEggs(Items.COD_SPAWN_EGG, WWFabricItems.CRAB_SPAWN_EGG);
		insertAfterInToolsAndUtilities(WWFabricItems.JELLYFISH_BUCKET, WWFabricItems.CRAB_BUCKET);
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

		// GABBRO
		insertAfterInNaturalAndFunctionalBlocks(Items.MAGMA_BLOCK, WWFabricItems.GEOTHERMAL_VENT);
		insertBeforeInNaturalBlocks(WWFabricItems.GEOTHERMAL_VENT, WWItems.GABBRO);
	}

	private static void insertAfterInCombat(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.COMBAT);
	}

	private static void insertBeforeInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInNaturalAndFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void insertAfterInFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void insertBeforeInRedstoneBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void insertAfterInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertBeforeInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.INGREDIENTS);
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
