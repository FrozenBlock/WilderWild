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
    public static final Item FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32));

    public static final Item BAOBAB_BOAT_ITEM = new BoatItem(false, CustomBoatType.BAOBAB, new FabricItemSettings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item BAOBAB_CHEST_BOAT_ITEM = new BoatItem(true, CustomBoatType.BAOBAB, new FabricItemSettings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item BAOBAB_SIGN = new SignItem(new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(16),
            RegisterBlocks.BAOBAB_SIGN_BLOCK, RegisterBlocks.BAOBAB_WALL_SIGN);
    public static final Item CYPRESS_BOAT_ITEM = new BoatItem(false, CustomBoatType.CYPRESS, new FabricItemSettings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item CYPRESS_CHEST_BOAT_ITEM = new BoatItem(true, CustomBoatType.CYPRESS, new FabricItemSettings().maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item CYPRESS_SIGN = new SignItem(new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(16),
            RegisterBlocks.CYPRESS_SIGN_BLOCK, RegisterBlocks.CYPRESS_WALL_SIGN);


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
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "firefly_bottle"), FIREFLY_BOTTLE);

        Registry.register(Registry.INSTRUMENT, ANCIENT_HORN_INSTRUMENT, new Instrument(RegisterSounds.ANCIENT_HORN_CALL, 300, 256.0F));
    }
}
