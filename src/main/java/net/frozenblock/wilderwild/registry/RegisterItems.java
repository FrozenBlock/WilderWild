package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.item.AncientCityGoatHorn;
import net.frozenblock.wilderwild.item.FireflyBottleItem;
import net.frozenblock.wilderwild.item.MilkweedPodItem;
import net.frozenblock.wilderwild.misc.CustomBoatType;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class RegisterItems {
    public static final AncientCityGoatHorn ANCIENT_HORN = new AncientCityGoatHorn(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.EPIC), WilderWild.WILD_HORNS);
    public static final RegistryKey<Instrument> ANCIENT_HORN_INSTRUMENT = RegistryKey.of(Registry.INSTRUMENT_KEY, new Identifier(WilderWild.MOD_ID, "ancient_horn"));
    public static final MilkweedPodItem MILKWEED_POD = new MilkweedPodItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(64));
    //public static final MusicDiscItem MUSIC_DISC_BENEATH = new MusicDiscItem(15, RegisterSounds.MUSIC_DISC_BENEATH, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    //public static final MusicDiscItem MUSIC_DISC_GOAT_HORN_SYMPHONY = new MusicDiscItem(15, RegisterSounds.MUSIC_DISC_GOATHORN_SYMPHONY, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final MusicDiscItem MUSIC_DISC_THE_OTHER_SIDE = new MusicDiscItem(15, RegisterSounds.MUSIC_DISC_THE_OTHER_SIDE, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(RegisterEntities.FIREFLY, Integer.parseInt("2A2E2B", 16), Integer.parseInt("AAF644", 16), new FabricItemSettings().group(ItemGroup.MISC));

    public static final Item BAOBAB_BOAT_ITEM = new BoatItem(false, CustomBoatType.BAOBAB, new FabricItemSettings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item BAOBAB_CHEST_BOAT_ITEM = new BoatItem(true, CustomBoatType.BAOBAB, new FabricItemSettings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item BAOBAB_SIGN = new SignItem(new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(16),
            RegisterBlocks.BAOBAB_SIGN_BLOCK, RegisterBlocks.BAOBAB_WALL_SIGN);
    public static final Item CYPRESS_BOAT_ITEM = new BoatItem(false, CustomBoatType.CYPRESS, new FabricItemSettings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item CYPRESS_CHEST_BOAT_ITEM = new BoatItem(true, CustomBoatType.CYPRESS, new FabricItemSettings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item CYPRESS_SIGN = new SignItem(new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(16),
            RegisterBlocks.CYPRESS_SIGN_BLOCK, RegisterBlocks.CYPRESS_WALL_SIGN);

    public static final Item FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "on");

    public static final Item BLACK_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "black");
    public static final Item RED_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "red");
    public static final Item GREEN_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "green");
    public static final Item BROWN_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "brown");
    public static final Item BLUE_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "blue");
    public static final Item PURPLE_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "purple");
    public static final Item CYAN_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "cyan");
    public static final Item LIGHT_GRAY_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "light_gray");
    public static final Item GRAY_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "gray");
    public static final Item PINK_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "pink");
    public static final Item LIME_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "lime");
    public static final Item YELLOW_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "yellow");
    public static final Item LIGHT_BLUE_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "light_blue");
    public static final Item MAGENTA_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "magenta");
    public static final Item ORANGE_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "orange");
    public static final Item WHITE_FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32), "white");

    public static final Item POLLEN = new BlockItem(RegisterBlocks.POLLEN_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item BLUE_GLORY_OF_THE_SNOW = new BlockItem(RegisterBlocks.BLUE_GLORY_OF_THE_SNOW, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item WHITE_GLORY_OF_THE_SNOW = new BlockItem(RegisterBlocks.WHITE_GLORY_OF_THE_SNOW, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item PINK_GLORY_OF_THE_SNOW = new BlockItem(RegisterBlocks.PINK_GLORY_OF_THE_SNOW, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item PURPLE_GLORY_OF_THE_SNOW = new BlockItem(RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW, new FabricItemSettings().group(ItemGroup.DECORATIONS));


    public static void registerItems() {
        WilderWild.logWild("Registering Items for", WilderWild.UNSTABLE_LOGGING);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "baobab_boat"), BAOBAB_BOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "baobab_sign"), BAOBAB_SIGN);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "baobab_chest_boat"), BAOBAB_CHEST_BOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "cypress_boat"), CYPRESS_BOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "cypress_sign"), CYPRESS_SIGN);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "cypress_chest_boat"), CYPRESS_CHEST_BOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "ancient_horn"), ANCIENT_HORN);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "milkweed_pod"), MILKWEED_POD);
        //Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "music_disc_beneath"), MUSIC_DISC_BENEATH);
        //Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "music_disc_goathorn_symphony"), MUSIC_DISC_GOAT_HORN_SYMPHONY);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "music_disc_the_other_side"), MUSIC_DISC_THE_OTHER_SIDE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "firefly_spawn_egg"), FIREFLY_SPAWN_EGG);

        Registry.register(Registry.INSTRUMENT, ANCIENT_HORN_INSTRUMENT, new Instrument(RegisterSounds.ANCIENT_HORN_CALL, 300, 256.0F));

        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "firefly_bottle"), FIREFLY_BOTTLE);

        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "magenta_firefly_bottle"), MAGENTA_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "purple_firefly_bottle"), PURPLE_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "blue_firefly_bottle"), BLUE_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "black_firefly_bottle"), BLACK_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "red_firefly_bottle"), RED_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "green_firefly_bottle"), GREEN_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "brown_firefly_bottle"), BROWN_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "cyan_firefly_bottle"), CYAN_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "light_gray_firefly_bottle"), LIGHT_GRAY_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "gray_firefly_bottle"), GRAY_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "pink_firefly_bottle"), PINK_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "lime_firefly_bottle"), LIME_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "yellow_firefly_bottle"), YELLOW_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "light_blue_firefly_bottle"), LIGHT_BLUE_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "orange_firefly_bottle"), ORANGE_FIREFLY_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "white_firefly_bottle"), WHITE_FIREFLY_BOTTLE);

        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "pollen"), POLLEN);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "blue_giant_glory_of_the_snow"), BLUE_GLORY_OF_THE_SNOW);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "pink_giant_glory_of_the_snow"), PINK_GLORY_OF_THE_SNOW);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "violet_beauty_glory_of_the_snow"), PURPLE_GLORY_OF_THE_SNOW);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "alba_glory_of_the_snow"), WHITE_GLORY_OF_THE_SNOW);
    }
}
