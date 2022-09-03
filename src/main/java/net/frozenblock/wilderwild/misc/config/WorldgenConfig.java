package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.gui.screens.Screen;

import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "worldgen")
@Config.Gui.Background("wilderwild:textures/block/chiseled_mud_bricks.png")
public class WorldgenConfig implements ConfigData {
    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public BiomePlacement biomePlacement = new BiomePlacement();

    @Config(name = "biomeplacement")
    public static class BiomePlacement {
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean modifyDesertPlacement = true;
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean modifyBadlandsPlacement = true;
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean modifyWindsweptSavannaPlacement = true;
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean modifyJunglePlacement = true;
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean modifySwampPlacement = true;
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean modifyMangroveSwampPlacement = true;
    }

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean betaBeaches = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean fallenLogs = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean wilderWildTreeGen = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean wilderWildGrassGen = true;

    @Environment(EnvType.CLIENT)
    protected static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().worldgen;
        var biomePlacement = config.biomePlacement;
        category.setBackground(WilderWild.id("textures/block/chiseled_mud_bricks.png"));
        var betaBeaches = category.addEntry(entryBuilder.startBooleanToggle(text("beta_beaches"), config.betaBeaches)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.betaBeaches = newValue)
                .setTooltip(tooltip("beta_beaches"))
                .requireRestart()
                .build());
        var badlands = category.addEntry(entryBuilder.startBooleanToggle(text("modify_badlands_placement"), biomePlacement.modifyBadlandsPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> biomePlacement.modifyBadlandsPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_badlands_placement"))
                .requireRestart()
                .build());
        var desert = category.addEntry(entryBuilder.startBooleanToggle(text("modify_desert_placement"), biomePlacement.modifyDesertPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> biomePlacement.modifyDesertPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_desert_placement"))
                .requireRestart()
                .build());
        var jungle = category.addEntry(entryBuilder.startBooleanToggle(text("modify_jungle_placement"), biomePlacement.modifyJunglePlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> biomePlacement.modifyJunglePlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_jungle_placement"))
                .requireRestart()
                .build());
        var mangroveSwamp = category.addEntry(entryBuilder.startBooleanToggle(text("modify_mangrove_swamp_placement"), biomePlacement.modifyMangroveSwampPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> biomePlacement.modifyMangroveSwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_mangrove_swamp_placement"))
                .requireRestart()
                .build());
        var swamp = category.addEntry(entryBuilder.startBooleanToggle(text("modify_swamp_placement"), biomePlacement.modifySwampPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> biomePlacement.modifySwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_swamp_placement"))
                .requireRestart()
                .build());
        var windsweptSavanna = category.addEntry(entryBuilder.startBooleanToggle(text("modify_windswept_savanna_placement"), biomePlacement.modifyWindsweptSavannaPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> biomePlacement.modifyWindsweptSavannaPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_windswept_savanna_placement"))
                .requireRestart()
                .build());
        var fallenLogs = category.addEntry(entryBuilder.startBooleanToggle(text("fallen_logs"), config.fallenLogs)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.fallenLogs = newValue)
                .setTooltip(tooltip("fallen_logs"))
                .requireRestart()
                .build());
        var wilderWildTrees = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_trees"), config.wilderWildTreeGen)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wilderWildTreeGen = newValue)
                .setTooltip(tooltip("wilder_wild_trees"))
                .requireRestart()
                .build());
        var wilderWildGrass = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_grass"), config.wilderWildGrassGen)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wilderWildGrassGen = newValue)
                .setTooltip(tooltip("wilder_wild_grass"))
                .requireRestart()
                .build());
    }

    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static WorldgenConfig get() {
        AutoConfig.register(WorldgenConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(WorldgenConfig.class).getConfig();
    }*/
}
