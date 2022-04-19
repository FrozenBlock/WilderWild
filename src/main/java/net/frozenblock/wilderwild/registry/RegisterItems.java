package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.mixin.MusicDiscItemInvoker;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class RegisterItems {
    public static final MusicDiscItem MUSIC_DISC_BENEATH = MusicDiscItemInvoker.invokeConstructor(15, RegisterSounds.MUSIC_DISC_BENEATH, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final MusicDiscItem MUSIC_DISC_GOATHORN_SYMPHONY = MusicDiscItemInvoker.invokeConstructor(15, RegisterSounds.MUSIC_DISC_GOATHORN_SYMPHONY, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));

    public static void RegisterItems() {
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "music_disc_beneath"), MUSIC_DISC_BENEATH);
        Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "music_disc_goathorn_symphony"), MUSIC_DISC_GOATHORN_SYMPHONY);
    }
}
