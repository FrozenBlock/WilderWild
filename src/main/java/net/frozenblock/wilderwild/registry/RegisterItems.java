package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.WilderBoatEntity;
import net.frozenblock.wilderwild.item.AncientCityGoatHorn;
import net.frozenblock.wilderwild.item.FireflyBottleItem;
import net.frozenblock.wilderwild.item.MilkweedPodItem;
import net.frozenblock.wilderwild.item.WilderBoatItem;
import net.frozenblock.wilderwild.mixin.MusicDiscItemInvoker;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class RegisterItems {
    public static final AncientCityGoatHorn ANCIENT_HORN = new AncientCityGoatHorn(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.EPIC));
    public static final MilkweedPodItem MILKWEED_POD = new MilkweedPodItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(64));
    public static final MusicDiscItem MUSIC_DISC_BENEATH = MusicDiscItemInvoker.invokeConstructor(15, RegisterSounds.MUSIC_DISC_BENEATH, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final MusicDiscItem MUSIC_DISC_GOATHORN_SYMPHONY = MusicDiscItemInvoker.invokeConstructor(15, RegisterSounds.MUSIC_DISC_GOATHORN_SYMPHONY, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final MusicDiscItem MUSIC_DISC_THE_OTHER_SIDE = MusicDiscItemInvoker.invokeConstructor(15, RegisterSounds.MUSIC_DISC_THE_OTHER_SIDE, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(RegisterEntities.FIREFLY, Integer.parseInt("2A2E2B", 16), Integer.parseInt("AAF644", 16), new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item FIREFLY_BOTTLE = new FireflyBottleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(32));

    public static final Item BAOBAB_BOAT_ITEM = new WilderBoatItem(false, WilderBoatEntity.Type.BAOBAB, (new Item.Settings()).maxCount(1).group(ItemGroup.TRANSPORTATION));
    public static final Item BAOBAB_CHEST_BOAT_ITEM = new WilderBoatItem(true, WilderBoatEntity.Type.BAOBAB, (new Item.Settings()).maxCount(1).group(ItemGroup.TRANSPORTATION));


    public static void registerItems() {
        WilderWild.logWild("Registering Items for", WilderWild.UNSTABLE_LOGGING);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "baobab_boat"), BAOBAB_BOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "baobab_chest_boat"), BAOBAB_CHEST_BOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "ancient_horn"), ANCIENT_HORN);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "milkweed_pod"), MILKWEED_POD);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "music_disc_beneath"), MUSIC_DISC_BENEATH);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "music_disc_goathorn_symphony"), MUSIC_DISC_GOATHORN_SYMPHONY);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "music_disc_the_other_side"), MUSIC_DISC_THE_OTHER_SIDE);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "firefly_spawn_egg"), FIREFLY_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "firefly_bottle"), FIREFLY_BOTTLE);
    }
}
