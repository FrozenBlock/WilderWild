package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.TransitiveObject;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.network.chat.Component;

public class WilderWildConfig extends PartitioningSerializer.GlobalData {
    @Category("block")
    @TransitiveObject
    public BlockConfig block = new BlockConfig();

    public boolean mcLiveSensorTendrils = true;
    public boolean shriekerGargling = true;

    @Category("entity")
    @TransitiveObject
    public EntityConfig entity = new EntityConfig();

    public boolean ancientHornShattersGlass = false;
    public boolean wardenEmergesFromEgg = true;
    public boolean customWardenTendrils = true;
    public boolean wardenSwimAnimation = true;

    @Category("worldgen")
    @TransitiveObject
    public WorldgenConfig worldgen = new WorldgenConfig();

    public boolean fallenLogs = true;
    public boolean wilderWildTreeGen = true;
    public boolean betaBeaches = true;
    public boolean modifyDesertPlacement = true;
    public boolean modifyBadlandsPlacement = true;
    public boolean modifyWindsweptSavannaPlacement = true;
    public boolean modifyJunglePlacement = true;
    public boolean modifySwampPlacement = true;
    public boolean modifyMangroveSwampPlacement = true;

    public static Component text(String key) {
        return Component.translatable("option." + WilderWild.MOD_ID + "." + key);
    }

    public static Component tooltip(String key) {
        return Component.translatable("tooltip." + WilderWild.MOD_ID + "." + key);
    }
}
