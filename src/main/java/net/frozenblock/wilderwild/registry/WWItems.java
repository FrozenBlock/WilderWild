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
import java.util.function.Function;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.frozenblock.lib.item.api.DamageOnUseBlockItem;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.impl.WWBoatTypes;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.item.CoconutItem;
import net.frozenblock.wilderwild.item.CopperHorn;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.item.MilkweedPod;
import net.frozenblock.wilderwild.tag.WWInstrumentTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import static net.minecraft.world.item.Items.registerBlock;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public final class WWItems {

	// BOATS
	public static final BoatItem BAOBAB_BOAT = registerItem("baobab_boat",
		properties -> new BoatItem(false, WWBoatTypes.BAOBAB, properties),
		new Item.Properties()
			.stacksTo(1)
	);
	public static final BoatItem BAOBAB_CHEST_BOAT = registerItem("baobab_chest_boat",
		properties -> new BoatItem(true, WWBoatTypes.BAOBAB, properties),
		new Item.Properties()
			.stacksTo(1)
	);
	public static final BoatItem CYPRESS_BOAT = registerItem("cypress_boat",
		properties -> new BoatItem(false, WWBoatTypes.CYPRESS, properties),
		new Item.Properties()
			.stacksTo(1)
	);
	public static final BoatItem CYPRESS_CHEST_BOAT = registerItem("cypress_chest_boat",
		properties -> new BoatItem(true, WWBoatTypes.CYPRESS, properties),
		new Item.Properties()
			.stacksTo(1)
	);
	public static final BoatItem PALM_BOAT = registerItem("palm_boat",
		properties -> new BoatItem(false, WWBoatTypes.PALM, properties),
		new Item.Properties()
			.stacksTo(1)
	);
	public static final BoatItem PALM_CHEST_BOAT = registerItem("palm_chest_boat",
		properties -> new BoatItem(true, WWBoatTypes.PALM, properties),
		new Item.Properties()
			.stacksTo(1)
	);
	public static final BoatItem MAPLE_BOAT = registerItem("maple_boat",
		properties -> new BoatItem(false, WWBoatTypes.MAPLE, properties),
		new Item.Properties()
			.stacksTo(1)
	);
	public static final BoatItem MAPLE_CHEST_BOAT = registerItem("maple_chest_boat",
		properties -> new BoatItem(false, WWBoatTypes.MAPLE, properties),
		new Item.Properties()
			.stacksTo(1)
	);

	// BLOCK ITEMS
	public static final Item BAOBAB_NUT = registerBlock(WWBlocks.BAOBAB_NUT,
		new Item.Properties()
			.food(WWFood.BAOBAB_NUT)
	);
	public static final Item BAOBAB_SIGN = registerBlock(WWBlocks.BAOBAB_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.BAOBAB_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item BAOBAB_HANGING_SIGN = registerBlock(WWBlocks.BAOBAB_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.BAOBAB_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item CYPRESS_SIGN = registerBlock(WWBlocks.CYPRESS_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.CYPRESS_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item CYPRESS_HANGING_SIGN = registerBlock(WWBlocks.CYPRESS_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.CYPRESS_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item PALM_SIGN = registerBlock(WWBlocks.PALM_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.PALM_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item PALM_HANGING_SIGN = registerBlock(WWBlocks.PALM_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.PALM_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item MAPLE_SIGN = registerBlock(WWBlocks.MAPLE_SIGN,
		(block, properties) -> new SignItem(block, WWBlocks.MAPLE_WALL_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item MAPLE_HANGING_SIGN = registerBlock(WWBlocks.MAPLE_HANGING_SIGN,
		(block, properties) -> new HangingSignItem(block, WWBlocks.MAPLE_WALL_HANGING_SIGN, properties),
		new Item.Properties().stacksTo(16)
	);
	public static final Item COCONUT = registerBlock(WWBlocks.COCONUT, CoconutItem::new, new Item.Properties());
	public static final Item POLLEN = registerBlock(WWBlocks.POLLEN, BlockItem::new, new Item.Properties());
	public static final Item ALGAE = registerBlock(WWBlocks.ALGAE, PlaceOnWaterBlockItem::new, new Item.Properties());
	public static final Item FLOWERING_LILY_PAD = registerBlock(WWBlocks.FLOWERING_LILY_PAD, PlaceOnWaterBlockItem::new, new Item.Properties());
	public static final Item SCORCHED_SAND = registerBlock(WWBlocks.SCORCHED_SAND, BlockItem::new, new Item.Properties());
	public static final Item SCORCHED_RED_SAND = registerBlock(WWBlocks.SCORCHED_RED_SAND, BlockItem::new, new Item.Properties());
	public static final Item ECHO_GLASS = registerBlock(WWBlocks.ECHO_GLASS, BlockItem::new, new Item.Properties());
	public static final Item DISPLAY_LANTERN = registerBlock(WWBlocks.DISPLAY_LANTERN, BlockItem::new, new Item.Properties().component(WWDataComponents.FIREFLIES, ImmutableList.of()));

	// ITEMS
	public static final MilkweedPod MILKWEED_POD = registerItem("milkweed_pod", MilkweedPod::new, new Item.Properties());
	public static final Item SPLIT_COCONUT = registerItem("split_coconut", Item::new, new Item.Properties().food(WWFood.SPLIT_COCONUT));
	public static final FireflyBottle FIREFLY_BOTTLE = registerItem("firefly_bottle", properties -> new FireflyBottle(FireflyColor.ON, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle BLACK_FIREFLY_BOTTLE = registerItem("black_firefly_bottle", properties -> new FireflyBottle(FireflyColor.BLACK, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle RED_FIREFLY_BOTTLE = registerItem("red_firefly_bottle", properties -> new FireflyBottle(FireflyColor.RED, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle GREEN_FIREFLY_BOTTLE = registerItem("green_firefly_bottle", properties -> new FireflyBottle(FireflyColor.GREEN, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle BROWN_FIREFLY_BOTTLE = registerItem("brown_firefly_bottle", properties -> new FireflyBottle(FireflyColor.BROWN, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle BLUE_FIREFLY_BOTTLE = registerItem("blue_firefly_bottle", properties -> new FireflyBottle(FireflyColor.BLUE, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle PURPLE_FIREFLY_BOTTLE = registerItem("purple_firefly_bottle", properties -> new FireflyBottle(FireflyColor.PURPLE, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle CYAN_FIREFLY_BOTTLE = registerItem("cyan_firefly_bottle", properties -> new FireflyBottle(FireflyColor.CYAN, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle LIGHT_GRAY_FIREFLY_BOTTLE = registerItem("light_gray_firefly_bottle", properties -> new FireflyBottle(FireflyColor.LIGHT_GRAY, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle GRAY_FIREFLY_BOTTLE = registerItem("gray_firefly_bottle", properties -> new FireflyBottle(FireflyColor.GRAY, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle PINK_FIREFLY_BOTTLE = registerItem("pink_firefly_bottle", properties -> new FireflyBottle(FireflyColor.PINK, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle LIME_FIREFLY_BOTTLE = registerItem("lime_firefly_bottle", properties -> new FireflyBottle(FireflyColor.LIME, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle YELLOW_FIREFLY_BOTTLE = registerItem("yellow_firefly_bottle", properties -> new FireflyBottle(FireflyColor.YELLOW, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle LIGHT_BLUE_FIREFLY_BOTTLE = registerItem("light_blue_firefly_bottle", properties -> new FireflyBottle(FireflyColor.LIGHT_BLUE, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle MAGENTA_FIREFLY_BOTTLE = registerItem("magenta_firefly_bottle", properties -> new FireflyBottle(FireflyColor.MAGENTA, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle ORANGE_FIREFLY_BOTTLE = registerItem("orange_firefly_bottle", properties -> new FireflyBottle(FireflyColor.ORANGE, properties), new Item.Properties().stacksTo(32));
	public static final FireflyBottle WHITE_FIREFLY_BOTTLE = registerItem("white_firefly_bottle", properties -> new FireflyBottle(FireflyColor.WHITE, properties), new Item.Properties().stacksTo(32));

	// FOOD
	public static final Item PRICKLY_PEAR = registerBlock(WWBlocks.PRICKLY_PEAR_CACTUS, ((block, properties) -> new DamageOnUseBlockItem(block, properties, 2F, WWSounds.PLAYER_HURT_CACTUS, WWDamageTypes.PRICKLY_PEAR)), new Item.Properties().food(WWFood.PRICKLY_PEAR));
	public static final Item PEELED_PRICKLY_PEAR = registerItem("peeled_prickly_pear", Item::new, new Item.Properties().food(Foods.APPLE));
	public static final Item CRAB_CLAW = registerItem("crab_claw", Item::new, new Item.Properties().food(WWFood.CRAB_CLAW));
	public static final Item COOKED_CRAB_CLAW = registerItem("cooked_crab_claw", Item::new, new Item.Properties().food(WWFood.COOKED_CRAB_CLAW));
	public static final Item SCORCHED_EYE = registerItem("scorched_eye", Item::new, new Item.Properties().food(WWFood.SCORCHED_EYE, WWFood.SCORCHED_EYE_CONSUMABLE));
	public static final Item FERMENTED_SCORCHED_EYE = registerItem("fermented_scorched_eye", Item::new, new Item.Properties());

	// SPAWN EGGS & BUCKETS
	public static final SpawnEggItem FIREFLY_SPAWN_EGG = registerItem("firefly_spawn_egg", properties -> new SpawnEggItem(WWEntityTypes.FIREFLY, Integer.parseInt("2A2E2B", 16), Integer.parseInt("AAF644", 16), properties), new Item.Properties());
	public static final SpawnEggItem JELLYFISH_SPAWN_EGG = registerItem("jellyfish_spawn_egg", properties -> new SpawnEggItem(WWEntityTypes.JELLYFISH, Integer.parseInt("E484E4", 16), Integer.parseInt("DF71DC", 16), properties), new Item.Properties());
	public static final MobBucketItem JELLYFISH_BUCKET = registerItem("jellyfish_bucket", properties -> new MobBucketItem(WWEntityTypes.JELLYFISH, Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_JELLYFISH, properties), new Item.Properties().stacksTo(1));
	public static final SpawnEggItem CRAB_SPAWN_EGG = registerItem("crab_spawn_egg", properties -> new SpawnEggItem(WWEntityTypes.CRAB, Integer.parseInt("F98334", 16), Integer.parseInt("F9C366", 16), properties), new Item.Properties());
	public static final MobBucketItem CRAB_BUCKET = registerItem("crab_bucket", properties -> new MobBucketItem(WWEntityTypes.CRAB, Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_CRAB, properties), new Item.Properties().stacksTo(1));
	public static final SpawnEggItem OSTRICH_SPAWN_EGG = registerItem("ostrich_spawn_egg", properties -> new SpawnEggItem(WWEntityTypes.OSTRICH, Integer.parseInt("FAE0D0", 16), Integer.parseInt("5B4024", 16), properties), new Item.Properties());
	public static final SpawnEggItem SCORCHED_SPAWN_EGG = registerItem("scorched_spawn_egg", properties -> new SpawnEggItem(WWEntityTypes.SCORCHED, Integer.parseInt("4C2516", 16), Integer.parseInt("FFB800", 16), properties), new Item.Properties());

	// INSTRUMENT
	public static final CopperHorn COPPER_HORN = registerItem("copper_horn",
		properties -> new CopperHorn(WWInstrumentTags.COPPER_HORNS, properties),
		new Item.Properties().stacksTo(1)
	);

	public static final ResourceKey<Instrument> SAX_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("sax_copper_horn"));
	public static final ResourceKey<Instrument> TUBA_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("tuba_copper_horn"));
	public static final ResourceKey<Instrument> FLUTE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("flute_copper_horn"));
	public static final ResourceKey<Instrument> OBOE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("oboe_copper_horn"));
	public static final ResourceKey<Instrument> CLARINET_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("clarinet_copper_horn"));
	public static final ResourceKey<Instrument> TRUMPET_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("trumpet_copper_horn"));
	public static final ResourceKey<Instrument> TROMBONE_COPPER_HORN = ResourceKey.create(Registries.INSTRUMENT, WWConstants.id("trombone_copper_horn"));

	private WWItems() {
		throw new UnsupportedOperationException("RegisterItems contains only static declarations.");
	}

	public static void registerItems() {
		WWConstants.logWithModId("Registering Items for", WWConstants.UNSTABLE_LOGGING);
		CompostingChanceRegistry.INSTANCE.add(BAOBAB_NUT, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(MILKWEED_POD, 0.25F);
		CompostingChanceRegistry.INSTANCE.add(SPLIT_COCONUT, 0.15F);
		CompostingChanceRegistry.INSTANCE.add(COCONUT, 0.3F);
	}

	private static @NotNull <T extends Item> T registerItem(String name, @NotNull Function<Item.Properties, Item> function, Item.@NotNull Properties properties) {
		return (T) Items.registerItem(ResourceKey.create(Registries.ITEM, WWConstants.id(name)), function, properties);
	}
}
