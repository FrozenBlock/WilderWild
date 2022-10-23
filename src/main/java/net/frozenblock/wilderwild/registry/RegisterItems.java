package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.lib.sound.StartingSounds;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.item.AlgaeItem;
import net.frozenblock.wilderwild.item.AncientHorn;
import net.frozenblock.wilderwild.item.CopperHorn;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.item.FloweredLilyPadItem;
import net.frozenblock.wilderwild.item.MilkweedPod;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.WilderBoats;
import net.frozenblock.wilderwild.tag.WilderInstrumentTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

public final class RegisterItems {
	private RegisterItems() {
		throw new UnsupportedOperationException("RegisterItems contains only static declarations.");
	}

    public static final MilkweedPod MILKWEED_POD = new MilkweedPod(new FabricItemSettings().maxCount(64));
    public static final RecordItem MUSIC_DISC_BENEATH = new RecordItem(15, RegisterSounds.MUSIC_DISC_BENEATH, new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 169);
    public static final RecordItem MUSIC_DISC_GOAT_HORN_SYMPHONY = new RecordItem(15, RegisterSounds.MUSIC_DISC_GOATHORN_SYMPHONY, new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 144);
    public static final RecordItem MUSIC_DISC_BACK = new RecordItem(15, RegisterSounds.MUSIC_DISC_BACK, new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 76);
    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(RegisterEntities.FIREFLY, Integer.parseInt("2A2E2B", 16), Integer.parseInt("AAF644", 16), new FabricItemSettings());
    public static final Item JELLYFISH_SPAWN_EGG = new SpawnEggItem(RegisterEntities.JELLYFISH, Integer.parseInt("E484E4", 16), Integer.parseInt("DF71DC", 16), new FabricItemSettings());
    public static final Item JELLYFISH_BUCKET = new MobBucketItem(RegisterEntities.JELLYFISH, Fluids.WATER, RegisterSounds.ITEM_BUCKET_EMPTY_JELLYFISH, new FabricItemSettings().maxCount(1));


    public static final Item BAOBAB_BOAT_ITEM = new BoatItem(false, WilderBoats.BAOBAB, new FabricItemSettings().maxCount(1));
    public static final Item BAOBAB_CHEST_BOAT_ITEM = new BoatItem(true, WilderBoats.BAOBAB, new FabricItemSettings().maxCount(1));
    public static final Item BAOBAB_SIGN = new SignItem(new FabricItemSettings().maxCount(16),
            RegisterBlocks.BAOBAB_SIGN_BLOCK, RegisterBlocks.BAOBAB_WALL_SIGN);
    public static final Item CYPRESS_BOAT_ITEM = new BoatItem(false, WilderBoats.CYPRESS, new FabricItemSettings().maxCount(1));
    public static final Item CYPRESS_CHEST_BOAT_ITEM = new BoatItem(true, WilderBoats.CYPRESS, new FabricItemSettings().maxCount(1));
    public static final Item CYPRESS_SIGN = new SignItem(new FabricItemSettings().maxCount(16),
            RegisterBlocks.CYPRESS_SIGN_BLOCK, RegisterBlocks.CYPRESS_WALL_SIGN);

    public static final Item POLLEN = new BlockItem(RegisterBlocks.POLLEN_BLOCK, new FabricItemSettings());

    public static final Item BAOBAB_NUT = new BlockItem(RegisterBlocks.BAOBAB_NUT, new FabricItemSettings().food(RegisterFood.BAOBAB_NUT));
    //public static final Item PRICKLY_PEAR = new PrickOnUseBlockItem(RegisterBlocks.PRICKLY_PEAR_CACTUS, new FabricItemSettings().food(RegisterFood.PRICKLY_PEAR), 2F, RegisterSounds.PLAYER_HURT_CACTUS, "prickly_pear");
    //public static final Item PEELED_PRICKLY_PEAR = new Item(new FabricItemSettings().food(Foods.APPLE));

    public static final Item FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.ON);
    public static final Item BLACK_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.BLACK);
    public static final Item RED_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.RED);
    public static final Item GREEN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.GREEN);
    public static final Item BROWN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.BROWN);
    public static final Item BLUE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.BLUE);
    public static final Item PURPLE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.PURPLE);
    public static final Item CYAN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.CYAN);
    public static final Item LIGHT_GRAY_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.LIGHT_GRAY);
    public static final Item GRAY_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.GRAY);
    public static final Item PINK_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.PINK);
    public static final Item LIME_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.LIME);
    public static final Item YELLOW_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.YELLOW);
    public static final Item LIGHT_BLUE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.LIGHT_BLUE);
    public static final Item MAGENTA_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.MAGENTA);
    public static final Item ORANGE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.ORANGE);
    public static final Item WHITE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().maxCount(32), FireflyColor.WHITE);

    public static final Item ANCIENT_HORN_FRAGMENT = new Item(new FabricItemSettings().maxCount(64));

    public static final AncientHorn ANCIENT_HORN = new AncientHorn(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), WilderInstrumentTags.ANCIENT_HORNS);
    public static final ResourceKey<Instrument> ANCIENT_HORN_INSTRUMENT = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("ancient_horn"));
    public static final CopperHorn COPPER_HORN = new CopperHorn(new FabricItemSettings().maxCount(1), WilderInstrumentTags.COPPER_HORNS, 0);
    public static final ResourceKey<Instrument> SAX_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("sax_copper_horn"));
    public static final ResourceKey<Instrument> TUBA_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("tuba_copper_horn"));
    public static final ResourceKey<Instrument> FLUTE_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("flute_copper_horn"));
    public static final ResourceKey<Instrument> OBOE_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("oboe_copper_horn"));
    public static final ResourceKey<Instrument> CLARINET_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("clarinet_copper_horn"));
    public static final ResourceKey<Instrument> TRUMPET_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("trumpet_copper_horn"));
    public static final ResourceKey<Instrument> TROMBONE_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("trombone_copper_horn"));

    public static void registerItems() {
        WilderWild.logWild("Registering Items for", WilderWild.UNSTABLE_LOGGING);
		registerItemBefore(Items.BAMBOO_RAFT, BAOBAB_BOAT_ITEM, "baobab_boat", CreativeModeTabs.TAB_TOOLS);
		registerItemBefore(Items.BAMBOO_RAFT, BAOBAB_CHEST_BOAT_ITEM, "baobab_chest_boat", CreativeModeTabs.TAB_TOOLS);
		registerItemBefore(Items.BAMBOO_SIGN, BAOBAB_SIGN, "baobab_sign", CreativeModeTabs.TAB_FUNCTIONAL);
		registerItemBefore(Items.BAMBOO_RAFT, CYPRESS_BOAT_ITEM, "cypress_boat", CreativeModeTabs.TAB_TOOLS);
		registerItemBefore(Items.BAMBOO_RAFT, CYPRESS_CHEST_BOAT_ITEM, "cypress_chest_boat", CreativeModeTabs.TAB_TOOLS);
		registerItemBefore(Items.BAMBOO_SIGN, CYPRESS_SIGN, "cypress_sign", CreativeModeTabs.TAB_FUNCTIONAL);
		registerItemAfter(Items.BEETROOT_SEEDS, MILKWEED_POD, "milkweed_pod", CreativeModeTabs.TAB_NATURE);
		registerItemBefore(Items.MUSIC_DISC_5, MUSIC_DISC_BENEATH, "music_disc_beneath", CreativeModeTabs.TAB_TOOLS);
		registerItemBefore(Items.MUSIC_DISC_OTHERSIDE, MUSIC_DISC_GOAT_HORN_SYMPHONY, "music_disc_goathorn_symphony", CreativeModeTabs.TAB_TOOLS);
		registerItemAfter(Items.MUSIC_DISC_5, MUSIC_DISC_BACK, "music_disc_back", CreativeModeTabs.TAB_TOOLS);
		registerItemAfter(Items.EVOKER_SPAWN_EGG, FIREFLY_SPAWN_EGG, "firefly_spawn_egg", CreativeModeTabs.TAB_SPAWN_EGGS);
		registerItemAfter(Items.HUSK_SPAWN_EGG, JELLYFISH_SPAWN_EGG, "jellyfish_spawn_egg", CreativeModeTabs.TAB_SPAWN_EGGS);
		registerItemAfter(Items.AXOLOTL_BUCKET, JELLYFISH_BUCKET, "jellyfish_bucket", CreativeModeTabs.TAB_TOOLS);

        Registry.register(Registry.INSTRUMENT, ANCIENT_HORN_INSTRUMENT, new Instrument(RegisterSounds.ITEM_ANCIENT_HORN_CALL, 300, 256.0F));
        Registry.register(Registry.INSTRUMENT, SAX_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_SAX_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, TUBA_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TUBA_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, FLUTE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_FLUTE_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, OBOE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_OBOE_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, CLARINET_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_CLARINET_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, TRUMPET_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TRUMPET_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, TROMBONE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TROMBONE_LOOP, 32767, 64.0F));
        StartingSounds.startingSounds.put(SAX_COPPER_HORN, RegisterSounds.ITEM_COPPER_HORN_SAX_START);
        StartingSounds.startingSounds.put(TUBA_COPPER_HORN, RegisterSounds.ITEM_COPPER_HORN_TUBA_START);
        StartingSounds.startingSounds.put(FLUTE_COPPER_HORN, RegisterSounds.ITEM_COPPER_HORN_FLUTE_START);
        StartingSounds.startingSounds.put(OBOE_COPPER_HORN, RegisterSounds.ITEM_COPPER_HORN_OBOE_START);
        StartingSounds.startingSounds.put(CLARINET_COPPER_HORN, RegisterSounds.ITEM_COPPER_HORN_CLARINET_START);
        StartingSounds.startingSounds.put(TRUMPET_COPPER_HORN, RegisterSounds.ITEM_COPPER_HORN_TRUMPET_START);
        StartingSounds.startingSounds.put(TROMBONE_COPPER_HORN, RegisterSounds.ITEM_COPPER_HORN_TROMBONE_START);

		registerInstrumentBefore(Items.MUSIC_DISC_13, COPPER_HORN, "copper_horn", WilderInstrumentTags.COPPER_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.TAB_TOOLS);
		registerInstrumentBefore(Items.MUSIC_DISC_13, ANCIENT_HORN, "ancient_horn", WilderInstrumentTags.ANCIENT_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.TAB_TOOLS);
		registerInstrumentBefore(Items.BOW, ANCIENT_HORN, "ancient_horn", WilderInstrumentTags.ANCIENT_HORNS, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, CreativeModeTabs.TAB_COMBAT);

		registerItemAfter(Items.MANGROVE_PROPAGULE, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.TAB_NATURE);
		registerItemAfter(Items.GLOW_BERRIES, BAOBAB_NUT, "baobab_nut", CreativeModeTabs.TAB_CONSUMABLES);
		//registerItem(PRICKLY_PEAR, "prickly_pear", CreativeModeTabs.TAB_NATURE, CreativeModeTabs.TAB_CONSUMABLES);
		//registerItem(PEELED_PRICKLY_PEAR, "peeled_prickly_pear", CreativeModeTabs.TAB_NATURE, CreativeModeTabs.TAB_CONSUMABLES);

		registerItem(FIREFLY_BOTTLE, "firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(WHITE_FIREFLY_BOTTLE, "white_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(ORANGE_FIREFLY_BOTTLE, "orange_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(MAGENTA_FIREFLY_BOTTLE, "magenta_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(LIGHT_BLUE_FIREFLY_BOTTLE, "light_blue_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(YELLOW_FIREFLY_BOTTLE, "yellow_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(LIME_FIREFLY_BOTTLE, "lime_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(PINK_FIREFLY_BOTTLE, "pink_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(GRAY_FIREFLY_BOTTLE, "gray_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(LIGHT_GRAY_FIREFLY_BOTTLE, "light_gray_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(CYAN_FIREFLY_BOTTLE, "cyan_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(PURPLE_FIREFLY_BOTTLE, "purple_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(BLUE_FIREFLY_BOTTLE, "blue_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(BROWN_FIREFLY_BOTTLE, "brown_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(GREEN_FIREFLY_BOTTLE, "green_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(RED_FIREFLY_BOTTLE, "red_firefly_bottle", CreativeModeTabs.TAB_TOOLS);
		registerItem(BLACK_FIREFLY_BOTTLE, "black_firefly_bottle", CreativeModeTabs.TAB_TOOLS);

		registerItemAfter(Items.GLOW_LICHEN, POLLEN, "pollen", CreativeModeTabs.TAB_NATURE);
		registerItemBefore(Items.LILY_PAD, new AlgaeItem(RegisterBlocks.ALGAE, new FabricItemSettings()), "algae", CreativeModeTabs.TAB_NATURE);
		registerItemAfter(Items.LILY_PAD, new FloweredLilyPadItem(RegisterBlocks.FLOWERING_LILY_PAD, new FabricItemSettings()), "flowering_lily_pad", CreativeModeTabs.TAB_NATURE);

		registerItemAfter(Items.ECHO_SHARD, ANCIENT_HORN_FRAGMENT, "ancient_horn_fragment", CreativeModeTabs.TAB_CRAFTING);

        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
            factories.add(new VillagerTrades.ItemsForEmeralds(RegisterItems.BAOBAB_NUT, 5, 1, 8, 1));
            factories.add(new VillagerTrades.ItemsForEmeralds(RegisterBlocks.CYPRESS_SAPLING.asItem(), 5, 1, 8, 1));
        });
    }

	private static void registerInstrument(Item instrument, String path, TagKey<Instrument> tagKey, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		actualRegister(instrument, path);
		FrozenCreativeTabs.addInstrument(instrument, tagKey, tabVisibility, tabs);
	}

	private static void registerInstrumentBefore(Item comparedItem, Item instrument, String path, TagKey<Instrument> tagKey, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		actualRegister(instrument, path);
		FrozenCreativeTabs.addInstrumentBefore(comparedItem, instrument, tagKey, tabVisibility, tabs);
	}

	private static void registerItem(Item item, String path, CreativeModeTab... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.add(item, tabs);
	}

	private static void registerItemBefore(ItemLike comparedItem, Item item, String path, CreativeModeTab... tabs) {
		registerItemBefore(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	private static void registerItemBefore(ItemLike comparedItem, Item item, String path, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.addBefore(comparedItem, item, tabVisibility, tabs);
	}

	private static void registerItemAfter(ItemLike comparedItem, Item item, String path, CreativeModeTab... tabs) {
		registerItemAfter(comparedItem, item, path, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	private static void registerItemAfter(ItemLike comparedItem, Item item, String path, CreativeModeTab.TabVisibility tabVisibility, CreativeModeTab... tabs) {
		actualRegister(item, path);
		FrozenCreativeTabs.addAfter(comparedItem, item, tabVisibility, tabs);
	}

	private static void actualRegister(Item item, String path) {
		if (Registry.ITEM.getOptional(WilderWild.id(path)).isEmpty()) {
			Registry.register(Registry.ITEM, WilderWild.id(path), item);
		}
	}
}
