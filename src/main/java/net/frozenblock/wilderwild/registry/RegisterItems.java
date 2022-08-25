package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.frozenblock.lib.item.PrickOnUseItem;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.item.AncientHorn;
import net.frozenblock.wilderwild.item.CopperHorn;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.item.MilkweedPod;
import net.frozenblock.wilderwild.misc.WilderBoats;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;

public final class RegisterItems {

    public static final MilkweedPod MILKWEED_POD = new MilkweedPod(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(64));
    public static final RecordItem MUSIC_DISC_BENEATH = new RecordItem(15, RegisterSounds.MUSIC_DISC_BENEATH, new FabricItemSettings().stacksTo(1).rarity(Rarity.RARE), 169);
    public static final RecordItem MUSIC_DISC_GOAT_HORN_SYMPHONY = new RecordItem(15, RegisterSounds.MUSIC_DISC_GOATHORN_SYMPHONY, new FabricItemSettings().stacksTo(1).rarity(Rarity.RARE), 144);
    public static final RecordItem MUSIC_DISC_THE_OTHER_SIDE = new RecordItem(15, RegisterSounds.MUSIC_DISC_THE_OTHER_SIDE, new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.RARE), 76);
    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(RegisterEntities.FIREFLY, Integer.parseInt("2A2E2B", 16), Integer.parseInt("AAF644", 16), new FabricItemSettings().tab(CreativeModeTab.TAB_MISC));

    public static final Item BAOBAB_BOAT_ITEM = new BoatItem(false, WilderBoats.BAOBAB, new FabricItemSettings().stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION));
    public static final Item BAOBAB_CHEST_BOAT_ITEM = new BoatItem(true, WilderBoats.BAOBAB, new FabricItemSettings().stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION));
    public static final Item BAOBAB_SIGN = new SignItem(new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS).stacksTo(16),
            RegisterBlocks.BAOBAB_SIGN_BLOCK, RegisterBlocks.BAOBAB_WALL_SIGN);
    public static final Item CYPRESS_BOAT_ITEM = new BoatItem(false, WilderBoats.CYPRESS, new FabricItemSettings().stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION));
    public static final Item CYPRESS_CHEST_BOAT_ITEM = new BoatItem(true, WilderBoats.CYPRESS, new FabricItemSettings().stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION));
    public static final Item CYPRESS_SIGN = new SignItem(new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS).stacksTo(16),
            RegisterBlocks.CYPRESS_SIGN_BLOCK, RegisterBlocks.CYPRESS_WALL_SIGN);

    public static final Item BAOBAB_NUT = new BlockItem(RegisterBlocks.BAOBAB_NUT, new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS).food(RegisterFood.BAOBAB_NUT));
    public static final Item PRICKLY_PEAR = new PrickOnUseItem(new FabricItemSettings().tab(CreativeModeTab.TAB_FOOD).food(RegisterFood.PRICKLY_PEAR), 2F, RegisterSounds.HURT_PRICKLY_PEAR, "prick");
    public static final Item PEELED_PRICKLY_PEAR = new Item(new FabricItemSettings().tab(CreativeModeTab.TAB_FOOD).food(Foods.APPLE));

    public static final Item FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "on");

    public static final Item BLACK_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "black");
    public static final Item RED_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "red");
    public static final Item GREEN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "green");
    public static final Item BROWN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "brown");
    public static final Item BLUE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "blue");
    public static final Item PURPLE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "purple");
    public static final Item CYAN_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "cyan");
    public static final Item LIGHT_GRAY_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "light_gray");
    public static final Item GRAY_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "gray");
    public static final Item PINK_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "pink");
    public static final Item LIME_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "lime");
    public static final Item YELLOW_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "yellow");
    public static final Item LIGHT_BLUE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "light_blue");
    public static final Item MAGENTA_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "magenta");
    public static final Item ORANGE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "orange");
    public static final Item WHITE_FIREFLY_BOTTLE = new FireflyBottle(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(32), "white");

    public static final Item POLLEN = new BlockItem(RegisterBlocks.POLLEN_BLOCK, new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final Item ANCIENT_FRAGMENT = new Item(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(64));


    public static final AncientHorn ANCIENT_HORN = new AncientHorn(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.EPIC), WilderWild.ANCIENT_HORNS);
    public static final ResourceKey<Instrument> ANCIENT_HORN_INSTRUMENT = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("ancient_horn"));
    public static final CopperHorn COPPER_HORN = new CopperHorn(new FabricItemSettings().tab(CreativeModeTab.TAB_MISC).stacksTo(1), WilderWild.COPPER_HORNS, 0);
    public static final ResourceKey<Instrument> SAX_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("sax_copper_horn"));
    public static final ResourceKey<Instrument> TUBA_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("tuba_copper_horn"));
    public static final ResourceKey<Instrument> FLUTE_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("flute_copper_horn"));
    public static final ResourceKey<Instrument> OBOE_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("oboe_copper_horn"));
    public static final ResourceKey<Instrument> CLARINET_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("clarinet_copper_horn"));
    public static final ResourceKey<Instrument> TRUMPET_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("trumpet_copper_horn"));
    public static final ResourceKey<Instrument> TROMBONE_COPPER_HORN = ResourceKey.create(Registry.INSTRUMENT_REGISTRY, WilderWild.id("trombone_copper_horn"));

    public static void registerItems() {
        WilderWild.logWild("Registering Items for", WilderWild.UNSTABLE_LOGGING);
        Registry.register(Registry.ITEM, WilderWild.id("baobab_boat"), BAOBAB_BOAT_ITEM);
        Registry.register(Registry.ITEM, WilderWild.id("baobab_chest_boat"), BAOBAB_CHEST_BOAT_ITEM);
        Registry.register(Registry.ITEM, WilderWild.id("cypress_boat"), CYPRESS_BOAT_ITEM);
        Registry.register(Registry.ITEM, WilderWild.id("cypress_chest_boat"), CYPRESS_CHEST_BOAT_ITEM);
        Registry.register(Registry.ITEM, WilderWild.id("ancient_horn"), ANCIENT_HORN);
        Registry.register(Registry.ITEM, WilderWild.id("copper_horn"), COPPER_HORN);
        Registry.register(Registry.ITEM, WilderWild.id("milkweed_pod"), MILKWEED_POD);
        Registry.register(Registry.ITEM, WilderWild.id("music_disc_beneath"), MUSIC_DISC_BENEATH);
        Registry.register(Registry.ITEM, WilderWild.id("music_disc_goathorn_symphony"), MUSIC_DISC_GOAT_HORN_SYMPHONY);
        Registry.register(Registry.ITEM, WilderWild.id("music_disc_the_other_side"), MUSIC_DISC_THE_OTHER_SIDE);
        Registry.register(Registry.ITEM, WilderWild.id("firefly_spawn_egg"), FIREFLY_SPAWN_EGG);

        Registry.register(Registry.INSTRUMENT, ANCIENT_HORN_INSTRUMENT, new Instrument(RegisterSounds.ANCIENT_HORN_CALL, 300, 256.0F));
        Registry.register(Registry.INSTRUMENT, SAX_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_SAX_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, TUBA_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TUBA_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, FLUTE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_FLUTE_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, OBOE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_OBOE_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, CLARINET_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_CLARINET_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, TRUMPET_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TRUMPET_LOOP, 32767, 64.0F));
        Registry.register(Registry.INSTRUMENT, TROMBONE_COPPER_HORN, new Instrument(RegisterSounds.ITEM_COPPER_HORN_TROMBONE_LOOP, 32767, 64.0F));

        Registry.register(Registry.ITEM, WilderWild.id("baobab_nut"), BAOBAB_NUT);
        Registry.register(Registry.ITEM, WilderWild.id("prickly_pear"), PRICKLY_PEAR);
        Registry.register(Registry.ITEM, WilderWild.id("peeled_prickly_pear"), PEELED_PRICKLY_PEAR);

        Registry.register(Registry.ITEM, WilderWild.id("firefly_bottle"), FIREFLY_BOTTLE);

        Registry.register(Registry.ITEM, WilderWild.id("magenta_firefly_bottle"), MAGENTA_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("purple_firefly_bottle"), PURPLE_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("blue_firefly_bottle"), BLUE_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("black_firefly_bottle"), BLACK_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("red_firefly_bottle"), RED_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("green_firefly_bottle"), GREEN_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("brown_firefly_bottle"), BROWN_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("cyan_firefly_bottle"), CYAN_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("light_gray_firefly_bottle"), LIGHT_GRAY_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("gray_firefly_bottle"), GRAY_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("pink_firefly_bottle"), PINK_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("lime_firefly_bottle"), LIME_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("yellow_firefly_bottle"), YELLOW_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("light_blue_firefly_bottle"), LIGHT_BLUE_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("orange_firefly_bottle"), ORANGE_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, WilderWild.id("white_firefly_bottle"), WHITE_FIREFLY_BOTTLE);

        Registry.register(Registry.ITEM, WilderWild.id("pollen"), POLLEN);

        Registry.register(Registry.ITEM, WilderWild.id("ancient_fragment"), ANCIENT_FRAGMENT);

        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
            factories.add(new VillagerTrades.ItemsForEmeralds(RegisterItems.BAOBAB_NUT.asItem(), 5, 1, 8, 1));
            factories.add(new VillagerTrades.ItemsForEmeralds(RegisterBlocks.CYPRESS_SAPLING.asItem(), 5, 1, 8, 1));
        });
    }
}
