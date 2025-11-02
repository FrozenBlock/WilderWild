/*
 * Copyright 2025 FrozenBlock
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
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.frozenblock.lib.item.api.DamageOnUseBlockItem;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.impl.WWBoatTypes;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.item.CoconutItem;
import net.frozenblock.wilderwild.item.CopperHornItem;
import net.frozenblock.wilderwild.item.CrabClawItem;
import net.frozenblock.wilderwild.item.MilkweedPodItem;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.tag.WWInstrumentTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public final class WWItems {
	// BLOCK ITEMS
	public static final BlockItem BAOBAB_NUT = new BlockItem(WWBlocks.BAOBAB_NUT, new Item.Properties().food(WWFood.BAOBAB_NUT));
	public static final SignItem BAOBAB_SIGN = new SignItem(new Item.Properties().stacksTo(16),
		WWBlocks.BAOBAB_SIGN, WWBlocks.BAOBAB_WALL_SIGN
	);
	public static final HangingSignItem BAOBAB_HANGING_SIGN = new HangingSignItem(WWBlocks.BAOBAB_HANGING_SIGN, WWBlocks.BAOBAB_WALL_HANGING_SIGN,
		new Item.Properties().stacksTo(16)
	);
	public static final SignItem WILLOW_SIGN = new SignItem(new Item.Properties().stacksTo(16),
		WWBlocks.WILLOW_SIGN, WWBlocks.WILLOW_WALL_SIGN
	);
	public static final HangingSignItem WILLOW_HANGING_SIGN = new HangingSignItem(WWBlocks.WILLOW_HANGING_SIGN, WWBlocks.WILLOW_WALL_HANGING_SIGN,
		new Item.Properties().stacksTo(16)
	);
	public static final SignItem CYPRESS_SIGN = new SignItem(new Item.Properties().stacksTo(16),
		WWBlocks.CYPRESS_SIGN, WWBlocks.CYPRESS_WALL_SIGN
	);
	public static final HangingSignItem CYPRESS_HANGING_SIGN = new HangingSignItem(WWBlocks.CYPRESS_HANGING_SIGN, WWBlocks.CYPRESS_WALL_HANGING_SIGN,
		new Item.Properties().stacksTo(16)
	);
	public static final SignItem PALM_SIGN = new SignItem(new Item.Properties().stacksTo(16),
		WWBlocks.PALM_SIGN, WWBlocks.PALM_WALL_SIGN
	);
	public static final HangingSignItem PALM_HANGING_SIGN = new HangingSignItem(WWBlocks.PALM_HANGING_SIGN, WWBlocks.PALM_WALL_HANGING_SIGN,
		new Item.Properties().stacksTo(16)
	);
	public static final SignItem MAPLE_SIGN = new SignItem(new Item.Properties().stacksTo(16),
		WWBlocks.MAPLE_SIGN, WWBlocks.MAPLE_WALL_SIGN
	);
	public static final HangingSignItem MAPLE_HANGING_SIGN = new HangingSignItem(WWBlocks.MAPLE_HANGING_SIGN, WWBlocks.MAPLE_WALL_HANGING_SIGN,
		new Item.Properties().stacksTo(16)
	);
	public static final CoconutItem COCONUT = new CoconutItem(WWBlocks.COCONUT, new Item.Properties());
	public static final BlockItem SCORCHED_SAND = new BlockItem(WWBlocks.SCORCHED_SAND, new Item.Properties());
	public static final BlockItem SCORCHED_RED_SAND = new BlockItem(WWBlocks.SCORCHED_RED_SAND, new Item.Properties());
	public static final BlockItem ECHO_GLASS = new BlockItem(WWBlocks.ECHO_GLASS, new Item.Properties());
	public static final BlockItem DISPLAY_LANTERN = new BlockItem(WWBlocks.DISPLAY_LANTERN, new Item.Properties().component(WWDataComponents.FIREFLIES, ImmutableList.of()));
	// ITEMS
	public static final MilkweedPodItem MILKWEED_POD = new MilkweedPodItem(new Item.Properties().stacksTo(64));
	public static final Item SPLIT_COCONUT = new Item(new Item.Properties().food(WWFood.SPLIT_COCONUT));
	public static final BoatItem BAOBAB_BOAT = new BoatItem(false, WWBoatTypes.BAOBAB, new Item.Properties().stacksTo(1));
	public static final BoatItem BAOBAB_CHEST_BOAT = new BoatItem(true, WWBoatTypes.BAOBAB, new Item.Properties().stacksTo(1));
	public static final BoatItem WILLOW_BOAT = new BoatItem(false, WWBoatTypes.WILLOW, new Item.Properties().stacksTo(1));
	public static final BoatItem WILLOW_CHEST_BOAT = new BoatItem(true, WWBoatTypes.WILLOW, new Item.Properties().stacksTo(1));
	public static final BoatItem CYPRESS_BOAT = new BoatItem(false, WWBoatTypes.CYPRESS, new Item.Properties().stacksTo(1));
	public static final BoatItem CYPRESS_CHEST_BOAT = new BoatItem(true, WWBoatTypes.CYPRESS, new Item.Properties().stacksTo(1));
	public static final BoatItem PALM_BOAT = new BoatItem(false, WWBoatTypes.PALM, new Item.Properties().stacksTo(1));
	public static final BoatItem PALM_CHEST_BOAT = new BoatItem(true, WWBoatTypes.PALM, new Item.Properties().stacksTo(1));
	public static final BoatItem MAPLE_BOAT = new BoatItem(false, WWBoatTypes.MAPLE, new Item.Properties().stacksTo(1));
	public static final BoatItem MAPLE_CHEST_BOAT = new BoatItem(true, WWBoatTypes.MAPLE, new Item.Properties().stacksTo(1));

	public static final MobBottleItem FIREFLY_BOTTLE = new MobBottleItem(
		WWEntityTypes.FIREFLY,
		WWSounds.ITEM_BOTTLE_RELEASE_FIREFLY,
		new Item.Properties()
			.stacksTo(16)
			.component(
				WWDataComponents.BOTTLE_ENTITY_DATA,
				CustomData.EMPTY.update(compoundTag -> compoundTag.putString("FireflyBottleVariantTag", FireflyColors.DEFAULT.location().toString()))
			)
	);
	public static final MobBottleItem BUTTERFLY_BOTTLE = new MobBottleItem(
		WWEntityTypes.BUTTERFLY,
		WWSounds.ITEM_BOTTLE_RELEASE_BUTTERFLY,
		new Item.Properties().stacksTo(1).component(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.EMPTY)
	);

	public static final DamageOnUseBlockItem PRICKLY_PEAR = new DamageOnUseBlockItem(WWBlocks.PRICKLY_PEAR_CACTUS, new Item.Properties().food(WWFood.PRICKLY_PEAR), 2F, WWSounds.PLAYER_HURT_CACTUS, WWDamageTypes.PRICKLY_PEAR);
	public static final Item PEELED_PRICKLY_PEAR = new Item(new Item.Properties().food(Foods.APPLE));
	public static final MobBucketItem CRAB_BUCKET = new MobBucketItem(WWEntityTypes.CRAB, Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_CRAB, new Item.Properties().stacksTo(1));
	public static final Item CRAB_CLAW = new CrabClawItem(new Item.Properties().food(WWFood.CRAB_CLAW));
	public static final Item COOKED_CRAB_CLAW = new Item(new Item.Properties().food(WWFood.COOKED_CRAB_CLAW));
	public static final Item SCORCHED_EYE = new Item(new Item.Properties().food(WWFood.SCORCHED_EYE));
	public static final Item FERMENTED_SCORCHED_EYE = new Item(new Item.Properties());
	// SPAWN EGGS
	public static final SpawnEggItem FIREFLY_SPAWN_EGG = new SpawnEggItem(WWEntityTypes.FIREFLY, Integer.parseInt("2A2E2B", 16), Integer.parseInt("AAF644", 16), new Item.Properties());
	public static final SpawnEggItem JELLYFISH_SPAWN_EGG = new SpawnEggItem(WWEntityTypes.JELLYFISH, Integer.parseInt("E484E4", 16), Integer.parseInt("DF71DC", 16), new Item.Properties());
	public static final MobBucketItem JELLYFISH_BUCKET = new MobBucketItem(WWEntityTypes.JELLYFISH, Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_JELLYFISH, new Item.Properties().stacksTo(1));
	public static final SpawnEggItem CRAB_SPAWN_EGG = new SpawnEggItem(WWEntityTypes.CRAB, Integer.parseInt("F98334", 16), Integer.parseInt("F9C366", 16), new Item.Properties());
	public static final SpawnEggItem OSTRICH_SPAWN_EGG = new SpawnEggItem(WWEntityTypes.OSTRICH, Integer.parseInt("FAE0D0", 16), Integer.parseInt("5B4024", 16), new Item.Properties());
	public static final SpawnEggItem SCORCHED_SPAWN_EGG = new SpawnEggItem(WWEntityTypes.SCORCHED, Integer.parseInt("4C2516", 16), Integer.parseInt("FFB800", 16), new Item.Properties());
	public static final SpawnEggItem BUTTERFLY_SPAWN_EGG = new SpawnEggItem(WWEntityTypes.BUTTERFLY, Integer.parseInt("542003", 16), Integer.parseInt("FFCF60", 16), new Item.Properties());
	public static final SpawnEggItem MOOBLOOM_SPAWN_EGG = new SpawnEggItem(WWEntityTypes.MOOBLOOM, Integer.parseInt("FED639", 16), Integer.parseInt("F7EDC1", 16), new Item.Properties());
	public static final SpawnEggItem PENGUIN_SPAWN_EGG = new SpawnEggItem(WWEntityTypes.PENGUIN, Integer.parseInt("2E2C40", 16), Integer.parseInt("E0B635", 16), new Item.Properties());
	// INSTRUMENT
	public static final CopperHornItem COPPER_HORN = new CopperHornItem(new Item.Properties().stacksTo(1), WWInstrumentTags.COPPER_HORNS);
	public static final ResourceKey<Instrument> SAX_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("sax_copper_horn"));
	public static final ResourceKey<Instrument> TUBA_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("tuba_copper_horn"));
	public static final ResourceKey<Instrument> RECORDER_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("recorder_copper_horn"));
	public static final ResourceKey<Instrument> FLUTE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("flute_copper_horn"));
	public static final ResourceKey<Instrument> OBOE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("oboe_copper_horn"));
	public static final ResourceKey<Instrument> CLARINET_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("clarinet_copper_horn"));
	public static final ResourceKey<Instrument> TRUMPET_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("trumpet_copper_horn"));
	public static final ResourceKey<Instrument> TROMBONE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("trombone_copper_horn"));

	private WWItems() {
		throw new UnsupportedOperationException("WWItems contains only static declarations.");
	}

	public static void registerBlockItems() {
		WWConstants.logWithModId("Registering Block Items for", WWConstants.UNSTABLE_LOGGING);
		registerItemAfter(Items.GLOW_BERRIES, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.FOOD_AND_DRINKS);

		registerItemAfter(Items.MANGROVE_HANGING_SIGN, BAOBAB_SIGN, "baobab_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(BAOBAB_SIGN, BAOBAB_HANGING_SIGN, "baobab_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(BAOBAB_HANGING_SIGN, WILLOW_SIGN, "willow_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(WILLOW_SIGN, WILLOW_HANGING_SIGN, "willow_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(WILLOW_HANGING_SIGN, CYPRESS_SIGN, "cypress_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(CYPRESS_SIGN, CYPRESS_HANGING_SIGN, "cypress_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(CYPRESS_HANGING_SIGN, PALM_SIGN, "palm_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(PALM_SIGN, PALM_HANGING_SIGN, "palm_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(Blocks.CHERRY_HANGING_SIGN, MAPLE_SIGN, "maple_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(MAPLE_SIGN, MAPLE_HANGING_SIGN, "maple_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);

		registerItemAfter(Items.CACTUS, PRICKLY_PEAR, "prickly_pear", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.TINTED_GLASS, ECHO_GLASS, "echo_glass", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(Items.SAND, SCORCHED_SAND, "scorched_sand", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.RED_SAND, SCORCHED_RED_SAND, "scorched_red_sand", CreativeModeTabs.NATURAL_BLOCKS);

		registerItemAfter(Items.SOUL_LANTERN, DISPLAY_LANTERN, "display_lantern", CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	public static void registerItems() {
		WWConstants.logWithModId("Registering Items for", WWConstants.UNSTABLE_LOGGING);
		//BOATS
		registerItemAfter(Items.MANGROVE_CHEST_BOAT, BAOBAB_BOAT, "baobab_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(BAOBAB_BOAT, BAOBAB_CHEST_BOAT, "baobab_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(BAOBAB_CHEST_BOAT, WILLOW_BOAT, "willow_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(WILLOW_BOAT, WILLOW_CHEST_BOAT, "willow_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(WILLOW_CHEST_BOAT, CYPRESS_BOAT, "cypress_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(CYPRESS_BOAT, CYPRESS_CHEST_BOAT, "cypress_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(CYPRESS_CHEST_BOAT, PALM_BOAT, "palm_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(PALM_BOAT, PALM_CHEST_BOAT, "palm_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.CHERRY_CHEST_BOAT, MAPLE_BOAT, "maple_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(MAPLE_BOAT, MAPLE_CHEST_BOAT, "maple_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);

		registerItemBefore(Items.INK_SAC, MILKWEED_POD, "milkweed_pod", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.EVOKER_SPAWN_EGG, FIREFLY_SPAWN_EGG, "firefly_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.HUSK_SPAWN_EGG, JELLYFISH_SPAWN_EGG, "jellyfish_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.AXOLOTL_BUCKET, JELLYFISH_BUCKET, "jellyfish_bucket", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemBefore(Items.CREEPER_SPAWN_EGG, CRAB_SPAWN_EGG, "crab_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.OCELOT_SPAWN_EGG, OSTRICH_SPAWN_EGG, "ostrich_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.SALMON_SPAWN_EGG, SCORCHED_SPAWN_EGG, "scorched_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.BREEZE_SPAWN_EGG, BUTTERFLY_SPAWN_EGG, "butterfly_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemBefore(Items.MOOSHROOM_SPAWN_EGG, MOOBLOOM_SPAWN_EGG, "moobloom_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemBefore(Items.PHANTOM_SPAWN_EGG, PENGUIN_SPAWN_EGG, "penguin_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(JELLYFISH_BUCKET, CRAB_BUCKET, "crab_bucket", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.ENCHANTED_GOLDEN_APPLE, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(Items.MANGROVE_PROPAGULE, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.EGG, COCONUT, "coconut", CreativeModeTabs.COMBAT);
		registerItemAfter(WWBlocks.CYPRESS_SAPLING.asItem(), COCONUT, "coconut", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(BAOBAB_NUT, SPLIT_COCONUT, "split_coconut", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(Items.SWEET_BERRIES, PRICKLY_PEAR, "prickly_pear", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(PRICKLY_PEAR, PEELED_PRICKLY_PEAR, "peeled_prickly_pear", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemBefore(Items.COD, CRAB_CLAW, "crab_claw", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(CRAB_CLAW, COOKED_CRAB_CLAW, "cooked_crab_claw", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(Items.SPIDER_EYE, SCORCHED_EYE, "scorched_eye", CreativeModeTabs.FOOD_AND_DRINKS, CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.FERMENTED_SPIDER_EYE, FERMENTED_SCORCHED_EYE, "fermented_scorched_eye", CreativeModeTabs.INGREDIENTS);

		Registry.register(BuiltInRegistries.INSTRUMENT, SAX_COPPER_HORN, new Instrument(WWSounds.ITEM_COPPER_HORN_SAX, 32767, 64F));
		Registry.register(BuiltInRegistries.INSTRUMENT, TUBA_COPPER_HORN, new Instrument(WWSounds.ITEM_COPPER_HORN_TUBA, 32767, 64F));
		Registry.register(BuiltInRegistries.INSTRUMENT, RECORDER_COPPER_HORN, new Instrument(WWSounds.ITEM_COPPER_HORN_RECORDER, 32767, 64F));
		Registry.register(BuiltInRegistries.INSTRUMENT, FLUTE_COPPER_HORN, new Instrument(WWSounds.ITEM_COPPER_HORN_FLUTE, 32767, 64F));
		Registry.register(BuiltInRegistries.INSTRUMENT, OBOE_COPPER_HORN, new Instrument(WWSounds.ITEM_COPPER_HORN_OBOE, 32767, 64F));
		Registry.register(BuiltInRegistries.INSTRUMENT, CLARINET_COPPER_HORN, new Instrument(WWSounds.ITEM_COPPER_HORN_CLARINET, 32767, 64F));
		Registry.register(BuiltInRegistries.INSTRUMENT, TRUMPET_COPPER_HORN, new Instrument(WWSounds.ITEM_COPPER_HORN_TRUMPET, 32767, 64F));
		Registry.register(BuiltInRegistries.INSTRUMENT, TROMBONE_COPPER_HORN, new Instrument(WWSounds.ITEM_COPPER_HORN_TROMBONE, 32767, 64F));

		registerInstrumentBefore(Items.MUSIC_DISC_13, COPPER_HORN, "copper_horn", WWInstrumentTags.COPPER_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.TOOLS_AND_UTILITIES);

		registerItemBefore(Items.BUCKET, FIREFLY_BOTTLE, "firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(FIREFLY_BOTTLE, BUTTERFLY_BOTTLE, "butterfly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);

		registerItemBefore(Items.LILY_PAD, new PlaceOnWaterBlockItem(WWBlocks.ALGAE, new Item.Properties()), "algae", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(WWBlocks.ALGAE, new PlaceOnWaterBlockItem(WWBlocks.PLANKTON, new Item.Properties()), "plankton", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.LILY_PAD, new PlaceOnWaterBlockItem(WWBlocks.FLOWERING_LILY_PAD, new Item.Properties()), "flowering_lily_pad", CreativeModeTabs.NATURAL_BLOCKS);

		CompostingChanceRegistry.INSTANCE.add(BAOBAB_NUT, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(MILKWEED_POD, 0.25F);
		CompostingChanceRegistry.INSTANCE.add(SPLIT_COCONUT, 0.15F);
		CompostingChanceRegistry.INSTANCE.add(COCONUT, 0.3F);
	}

	@SafeVarargs
	private static void registerInstrumentBefore(@NotNull Item comparedItem, @NotNull Item instrument, @NotNull String path, @NotNull TagKey<Instrument> tagKey, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegister(instrument, path);
		FrozenCreativeTabs.addInstrumentBefore(comparedItem, instrument, tagKey, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerItem(@NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.add(item, tabs);
	}

	@SafeVarargs
	private static void registerItemBefore(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		registerItemBefore(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerItemBefore(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.addBefore(comparedItem, item, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerItemAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		registerItemAfter(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
	private static void registerItemAfter(@NotNull ItemLike comparedItem, @NotNull Item item, @NotNull String path, @NotNull CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.addAfter(comparedItem, item, tabVisibility, tabs);
	}

	private static void actualRegister(@NotNull Item item, @NotNull String path) {
		if (BuiltInRegistries.ITEM.getOptional(WWConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, WWConstants.id(path), item);
		}
	}
}
