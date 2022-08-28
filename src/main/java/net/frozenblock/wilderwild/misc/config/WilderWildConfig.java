package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWild;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.network.chat.Component;

public class WilderWildConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("block")
    @ConfigEntry.Gui.TransitiveObject
    public BlockConfig block = new BlockConfig();

    public boolean mcLiveSensorTendrils = true;
    public boolean shriekerGargling = true;

    @ConfigEntry.Category("entity")
    @ConfigEntry.Gui.TransitiveObject
    public EntityConfig entity = new EntityConfig();

    public boolean wardenEmergesFromEgg = true;
    public boolean customWardenTendrils = true;
    public boolean wardenSwimAnimation = true;

    @ConfigEntry.Category("worldgen")
    @ConfigEntry.Gui.TransitiveObject
    public WorldgenConfig worldgen = new WorldgenConfig();

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
}
/* public class WilderWildConfig {

    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
    public boolean betaBeaches = true;
    public boolean modifyDesertPlacement = true;
    public boolean modifyBadlandsPlacement = true;
    public boolean modifyWindsweptSavannaPlacement = true;
    public boolean modifyJunglePlacement = true;
    public boolean modifySwampPlacement = true;
    public boolean modifyMangroveSwampPlacement = true;
    public boolean mcLiveSensorTendrils = false;
    public boolean wardenEmergesFromEgg = true;
    public boolean customWardenTendrils = true;
    public boolean wardenSwimAnimation = true;
    public boolean shriekerGargling = true;
    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());

} */
