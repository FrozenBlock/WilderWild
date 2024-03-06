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

package net.frozenblock.wilderwild.registry;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.lib.item.api.PrickOnUseBlockItem;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.item.AncientHorn;
import net.frozenblock.wilderwild.item.CoconutItem;
import net.frozenblock.wilderwild.item.CopperHorn;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.item.MilkweedPod;
import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderInstrumentTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public final class RegisterItems {

	// BLOCK ITEMS
	public static final BlockItem BAOBAB_NUT = new BlockItem(RegisterBlocks.BAOBAB_NUT, new Item.Properties().food(RegisterFood.BAOBAB_NUT));
	public static final SignItem BAOBAB_SIGN = new SignItem(new Item.Properties().stacksTo(16),
		RegisterBlocks.BAOBAB_SIGN, RegisterBlocks.BAOBAB_WALL_SIGN
	);
	public static final HangingSignItem BAOBAB_HANGING_SIGN = new HangingSignItem(RegisterBlocks.BAOBAB_HANGING_SIGN, RegisterBlocks.BAOBAB_WALL_HANGING_SIGN,
		new Item.Properties().stacksTo(16)
	);
	public static final SignItem CYPRESS_SIGN = new SignItem(new Item.Properties().stacksTo(16),
		RegisterBlocks.CYPRESS_SIGN, RegisterBlocks.CYPRESS_WALL_SIGN
	);
	public static final HangingSignItem CYPRESS_HANGING_SIGN = new HangingSignItem(RegisterBlocks.CYPRESS_HANGING_SIGN, RegisterBlocks.CYPRESS_WALL_HANGING_SIGN,
		new Item.Properties().stacksTo(16)
	);
	public static final SignItem PALM_SIGN = new SignItem(new Item.Properties().stacksTo(16),
		RegisterBlocks.PALM_SIGN, RegisterBlocks.PALM_WALL_SIGN
	);
	public static final HangingSignItem PALM_HANGING_SIGN = new HangingSignItem(RegisterBlocks.PALM_HANGING_SIGN, RegisterBlocks.PALM_WALL_HANGING_SIGN,
		new Item.Properties().stacksTo(16)
	);
	public static final CoconutItem COCONUT = new CoconutItem(RegisterBlocks.COCONUT, new Item.Properties());
	public static final BlockItem POLLEN = new BlockItem(RegisterBlocks.POLLEN_BLOCK, new Item.Properties());
	public static final BlockItem SCORCHED_SAND = new BlockItem(RegisterBlocks.SCORCHED_SAND, new Item.Properties());
	public static final BlockItem SCORCHED_RED_SAND = new BlockItem(RegisterBlocks.SCORCHED_RED_SAND, new Item.Properties());
	public static final BlockItem ECHO_GLASS = new BlockItem(RegisterBlocks.ECHO_GLASS, new Item.Properties());
	public static final BlockItem DISPLAY_LANTERN = new BlockItem(RegisterBlocks.DISPLAY_LANTERN, new Item.Properties());
	// ITEMS
	public static final MilkweedPod MILKWEED_POD = new MilkweedPod(new Item.Properties().stacksTo(64));
	public static final Item SPLIT_COCONUT = new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.4F).build()));
	public static final BoatItem BAOBAB_BOAT = new BoatItem(false, WilderEnumValues.BAOBAB, new Item.Properties().stacksTo(1));
	public static final BoatItem BAOBAB_CHEST_BOAT = new BoatItem(true, WilderEnumValues.BAOBAB, new Item.Properties().stacksTo(1));
	public static final BoatItem CYPRESS_BOAT = new BoatItem(false, WilderEnumValues.CYPRESS, new Item.Properties().stacksTo(1));
	public static final BoatItem CYPRESS_CHEST_BOAT = new BoatItem(true, WilderEnumValues.CYPRESS, new Item.Properties().stacksTo(1));
	public static final BoatItem PALM_BOAT = new BoatItem(false, WilderEnumValues.PALM, new Item.Properties().stacksTo(1));
	public static final BoatItem PALM_CHEST_BOAT = new BoatItem(true, WilderEnumValues.PALM, new Item.Properties().stacksTo(1));
	public static final FireflyBottle FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.ON);
	public static final FireflyBottle BLACK_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.BLACK);
	public static final FireflyBottle RED_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.RED);
	public static final FireflyBottle GREEN_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.GREEN);
	public static final FireflyBottle BROWN_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.BROWN);
	public static final FireflyBottle BLUE_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.BLUE);
	public static final FireflyBottle PURPLE_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.PURPLE);
	public static final FireflyBottle CYAN_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.CYAN);
	public static final FireflyBottle LIGHT_GRAY_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.LIGHT_GRAY);
	public static final FireflyBottle GRAY_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.GRAY);
	public static final FireflyBottle PINK_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.PINK);
	public static final FireflyBottle LIME_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.LIME);
	public static final FireflyBottle YELLOW_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.YELLOW);
	public static final FireflyBottle LIGHT_BLUE_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.LIGHT_BLUE);
	public static final FireflyBottle MAGENTA_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.MAGENTA);
	public static final FireflyBottle ORANGE_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.ORANGE);
	public static final FireflyBottle WHITE_FIREFLY_BOTTLE = new FireflyBottle(new Item.Properties().stacksTo(32), FireflyColor.WHITE);
	public static final PrickOnUseBlockItem PRICKLY_PEAR = new PrickOnUseBlockItem(RegisterBlocks.PRICKLY_PEAR_CACTUS, new Item.Properties().food(RegisterFood.PRICKLY_PEAR), 2F, RegisterSounds.PLAYER_HURT_CACTUS, RegisterDamageTypes.PRICKLY_PEAR);
	public static final Item PEELED_PRICKLY_PEAR = new Item(new Item.Properties().food(Foods.APPLE));
	public static final MobBucketItem CRAB_BUCKET = new MobBucketItem(RegisterEntities.CRAB, Fluids.WATER, RegisterSounds.ITEM_BUCKET_EMPTY_CRAB, new Item.Properties().stacksTo(1));
	public static final Item CRAB_CLAW = new Item(new Item.Properties().food(RegisterFood.CRAB_CLAW));
	public static final Item COOKED_CRAB_CLAW = new Item(new Item.Properties().food(RegisterFood.COOKED_CRAB_CLAW));
	public static final Item ANCIENT_HORN_FRAGMENT = new Item(new Item.Properties().stacksTo(64));
	// SPAWN EGGS
	public static final SpawnEggItem FIREFLY_SPAWN_EGG = new SpawnEggItem(RegisterEntities.FIREFLY, Integer.parseInt("2A2E2B", 16), Integer.parseInt("AAF644", 16), new Item.Properties());
	public static final SpawnEggItem JELLYFISH_SPAWN_EGG = new SpawnEggItem(RegisterEntities.JELLYFISH, Integer.parseInt("E484E4", 16), Integer.parseInt("DF71DC", 16), new Item.Properties());
	public static final MobBucketItem JELLYFISH_BUCKET = new MobBucketItem(RegisterEntities.JELLYFISH, Fluids.WATER, RegisterSounds.ITEM_BUCKET_EMPTY_JELLYFISH, new Item.Properties().stacksTo(1));
	public static final SpawnEggItem CRAB_SPAWN_EGG = new SpawnEggItem(RegisterEntities.CRAB, Integer.parseInt("F98334", 16), Integer.parseInt("F9C366", 16), new Item.Properties());
	public static final SpawnEggItem OSTRICH_SPAWN_EGG = new SpawnEggItem(RegisterEntities.OSTRICH, Integer.parseInt("FAE0D0", 16), Integer.parseInt("5B4024", 16), new Item.Properties());
	// INSTRUMENT
	public static final AncientHorn ANCIENT_HORN = new AncientHorn(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), WilderInstrumentTags.ANCIENT_HORNS);
	public static final ResourceKey<Instrument> ANCIENT_HORN_INSTRUMENT = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("ancient_horn"));
	public static final CopperHorn COPPER_HORN = new CopperHorn(new Item.Properties().stacksTo(1), WilderInstrumentTags.COPPER_HORNS);
	public static final ResourceKey<Instrument> SAX_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("sax_copper_horn"));
	public static final ResourceKey<Instrument> TUBA_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("tuba_copper_horn"));
	public static final ResourceKey<Instrument> FLUTE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("flute_copper_horn"));
	public static final ResourceKey<Instrument> OBOE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("oboe_copper_horn"));
	public static final ResourceKey<Instrument> CLARINET_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("clarinet_copper_horn"));
	public static final ResourceKey<Instrument> TRUMPET_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("trumpet_copper_horn"));
	public static final ResourceKey<Instrument> TROMBONE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WilderSharedConstants.id("trombone_copper_horn"));

	private RegisterItems() {
		throw new UnsupportedOperationException("RegisterItems contains only static declarations.");
	}

	public static void registerBlockItems() {
		WilderSharedConstants.logWithModId("Registering Block Items for", WilderSharedConstants.UNSTABLE_LOGGING);
		registerItemAfter(Items.GLOW_BERRIES, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.FOOD_AND_DRINKS);

		registerItemAfter(Items.MANGROVE_SIGN, BAOBAB_SIGN, "baobab_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(BAOBAB_SIGN, PALM_HANGING_SIGN, "palm_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(BAOBAB_SIGN, PALM_SIGN, "palm_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(BAOBAB_SIGN, CYPRESS_HANGING_SIGN, "cypress_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(BAOBAB_SIGN, CYPRESS_SIGN, "cypress_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(BAOBAB_SIGN, BAOBAB_HANGING_SIGN, "baobab_hanging_sign", CreativeModeTabs.FUNCTIONAL_BLOCKS);

		registerItemAfter(Items.GLOW_LICHEN, POLLEN, "pollen", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.CACTUS, PRICKLY_PEAR, "prickly_pear", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.TINTED_GLASS, ECHO_GLASS, "echo_glass", CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerItemAfter(Items.SAND, SCORCHED_SAND, "scorched_sand", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.RED_SAND, SCORCHED_RED_SAND, "scorched_red_sand", CreativeModeTabs.NATURAL_BLOCKS);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
			var second = new ItemStack(SCORCHED_SAND);
			ItemBlockStateTagUtils.setProperty(second, RegisterProperties.CRACKED, true);
			entries.addAfter(SCORCHED_SAND, second);

			var secondRed = new ItemStack(SCORCHED_RED_SAND);
			ItemBlockStateTagUtils.setProperty(secondRed, RegisterProperties.CRACKED, true);
			entries.addAfter(SCORCHED_RED_SAND, secondRed);
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
			var second = new ItemStack(SCORCHED_SAND);
			ItemBlockStateTagUtils.setProperty(second, RegisterProperties.CRACKED, true);
			entries.addAfter(SCORCHED_SAND, second);
		});

		registerItemAfter(Items.SOUL_LANTERN, DISPLAY_LANTERN, "display_lantern", CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	public static void registerItems() {
		WilderSharedConstants.logWithModId("Registering Items for", WilderSharedConstants.UNSTABLE_LOGGING);
		//BOATS
		registerItemAfter(Items.MANGROVE_CHEST_BOAT, BAOBAB_BOAT, "baobab_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(BAOBAB_BOAT, BAOBAB_CHEST_BOAT, "baobab_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(BAOBAB_CHEST_BOAT, CYPRESS_BOAT, "cypress_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(CYPRESS_BOAT, CYPRESS_CHEST_BOAT, "cypress_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(CYPRESS_CHEST_BOAT, PALM_BOAT, "palm_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(PALM_BOAT, PALM_CHEST_BOAT, "palm_chest_boat", CreativeModeTabs.TOOLS_AND_UTILITIES);

		registerItemBefore(Items.INK_SAC, MILKWEED_POD, "milkweed_pod", CreativeModeTabs.INGREDIENTS);
		registerItemAfter(Items.EVOKER_SPAWN_EGG, FIREFLY_SPAWN_EGG, "firefly_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.HUSK_SPAWN_EGG, JELLYFISH_SPAWN_EGG, "jellyfish_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.AXOLOTL_BUCKET, JELLYFISH_BUCKET, "jellyfish_bucket", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemBefore(Items.CREEPER_SPAWN_EGG, CRAB_SPAWN_EGG, "crab_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(Items.OCELOT_SPAWN_EGG, OSTRICH_SPAWN_EGG, "ostrich_spawn_egg", CreativeModeTabs.SPAWN_EGGS);
		registerItemAfter(JELLYFISH_BUCKET, CRAB_BUCKET, "crab_bucket", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItemAfter(Items.GLOW_BERRIES, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(Items.MANGROVE_PROPAGULE, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.EGG, COCONUT, "coconut", CreativeModeTabs.COMBAT);
		registerItemAfter(RegisterBlocks.CYPRESS_SAPLING.asItem(), COCONUT, "coconut", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(BAOBAB_NUT, SPLIT_COCONUT, "split_coconut", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(SPLIT_COCONUT, PRICKLY_PEAR, "prickly_pear", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(PRICKLY_PEAR, PEELED_PRICKLY_PEAR, "peeled_prickly_pear", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(PEELED_PRICKLY_PEAR, CRAB_CLAW, "crab_claw", CreativeModeTabs.FOOD_AND_DRINKS);
		registerItemAfter(CRAB_CLAW, COOKED_CRAB_CLAW, "cooked_crab_claw", CreativeModeTabs.FOOD_AND_DRINKS);

		Registry.register(BuiltInRegistries.INSTRUMENT, ANCIENT_HORN_INSTRUMENT, new Instrument(RegisterSounds.ITEM_ANCIENT_HORN_CALL, 300, 256.0F));
		Registry.register(BuiltInRegistries.INSTRUMENT, SAX_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_SAX_LOOP, 32767, 64.0F));
		Registry.register(BuiltInRegistries.INSTRUMENT, TUBA_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TUBA_LOOP, 32767, 64.0F));
		Registry.register(BuiltInRegistries.INSTRUMENT, FLUTE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_FLUTE_LOOP, 32767, 64.0F));
		Registry.register(BuiltInRegistries.INSTRUMENT, OBOE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_OBOE_LOOP, 32767, 64.0F));
		Registry.register(BuiltInRegistries.INSTRUMENT, CLARINET_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_CLARINET_LOOP, 32767, 64.0F));
		Registry.register(BuiltInRegistries.INSTRUMENT, TRUMPET_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TRUMPET_LOOP, 32767, 64.0F));
		Registry.register(BuiltInRegistries.INSTRUMENT, TROMBONE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TROMBONE_LOOP, 32767, 64.0F));

		registerInstrumentBefore(Items.MUSIC_DISC_13, COPPER_HORN, "copper_horn", WilderInstrumentTags.COPPER_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerInstrumentBefore(Items.MUSIC_DISC_13, ANCIENT_HORN, "ancient_horn", WilderInstrumentTags.ANCIENT_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerInstrumentBefore(Items.BOW, ANCIENT_HORN, "ancient_horn", WilderInstrumentTags.ANCIENT_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.COMBAT);

		registerItem(FIREFLY_BOTTLE, "firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(WHITE_FIREFLY_BOTTLE, "white_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(LIGHT_GRAY_FIREFLY_BOTTLE, "light_gray_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(GRAY_FIREFLY_BOTTLE, "gray_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(BLACK_FIREFLY_BOTTLE, "black_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(BROWN_FIREFLY_BOTTLE, "brown_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(RED_FIREFLY_BOTTLE, "red_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(ORANGE_FIREFLY_BOTTLE, "orange_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(YELLOW_FIREFLY_BOTTLE, "yellow_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(LIME_FIREFLY_BOTTLE, "lime_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(GREEN_FIREFLY_BOTTLE, "green_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(CYAN_FIREFLY_BOTTLE, "cyan_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(LIGHT_BLUE_FIREFLY_BOTTLE, "light_blue_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(BLUE_FIREFLY_BOTTLE, "blue_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(PURPLE_FIREFLY_BOTTLE, "purple_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(MAGENTA_FIREFLY_BOTTLE, "magenta_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);
		registerItem(PINK_FIREFLY_BOTTLE, "pink_firefly_bottle", CreativeModeTabs.TOOLS_AND_UTILITIES);

		registerItemBefore(Items.LILY_PAD, new PlaceOnWaterBlockItem(RegisterBlocks.ALGAE, new Item.Properties()), "algae", CreativeModeTabs.NATURAL_BLOCKS);
		registerItemAfter(Items.LILY_PAD, new PlaceOnWaterBlockItem(RegisterBlocks.FLOWERING_LILY_PAD, new Item.Properties()), "flowering_lily_pad", CreativeModeTabs.NATURAL_BLOCKS);

		registerItemAfter(Items.ECHO_SHARD, ANCIENT_HORN_FRAGMENT, "ancient_horn_fragment", CreativeModeTabs.INGREDIENTS);

		TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
			factories.add(new VillagerTrades.ItemsForEmeralds(RegisterItems.BAOBAB_NUT, 5, 1, 8, 1));
			factories.add(new VillagerTrades.ItemsForEmeralds(RegisterBlocks.CYPRESS_SAPLING.asItem(), 5, 1, 8, 1));
			factories.add(new VillagerTrades.ItemsForEmeralds(RegisterItems.COCONUT, 5, 1, 8, 1));
		});

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
		if (BuiltInRegistries.ITEM.getOptional(WilderSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, WilderSharedConstants.id(path), item);
		}
	}

	private static <S extends RecipeSerializer<T>, T extends Recipe<?>> @NotNull S registerSerializer(String key, S recipeSerializer) {
		return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, WilderSharedConstants.id(key), recipeSerializer);
	}
}
