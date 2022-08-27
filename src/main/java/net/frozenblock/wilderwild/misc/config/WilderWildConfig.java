package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWild;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "wilderwild")
public class WilderWildConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("block")
    @ConfigEntry.Gui.TransitiveObject
    public BlockConfig block = new BlockConfig();
        
    @ConfigEntry.Category("entity")
    @ConfigEntry.Gui.TransitiveObject
    public EntityConfig entity = new EntityConfig();
    
    @ConfigEntry.Category("worldgen")
    @ConfigEntry.Gui.TransitiveObject
    public WorldgenConfig worldgen = new WorldgenConfig();

    public static WilderWildConfig get() {
        if (!WilderWild.areConfigsInit) {
            AutoConfig.register(WilderWildConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
            WilderWild.areConfigsInit = true;
        }
        return AutoConfig.getConfigHolder(WilderWildConfig.class).getConfig();
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
