package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "worldgen")
public class WorldgenConfig implements ConfigData {
    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);

    public boolean betaBeaches = true;
    public boolean modifyDesertPlacement = true;
    public boolean modifyBadlandsPlacement = true;
    public boolean modifyWindsweptSavannaPlacement = true;
    public boolean modifyJunglePlacement = true;
    public boolean modifySwampPlacement = true;
    public boolean modifyMangroveSwampPlacement = true;
    public boolean fallenLogs = true;
    public boolean wilderWildTreeGen = true;
    public boolean wilderWildGrassGen = true;

    @Environment(EnvType.CLIENT)
    public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().worldgen;
        category.addEntry(entryBuilder.startBooleanToggle(text("beta_beaches"), config.betaBeaches)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.betaBeaches = newValue)
                .setTooltip(tooltip("beta_beaches"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_desert_placement"), config.modifyDesertPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyDesertPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_desert_placement"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_badlands_placement"), config.modifyBadlandsPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyBadlandsPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_badlands_placement"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_windswept_savanna_placement"), config.modifyWindsweptSavannaPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyWindsweptSavannaPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_windswept_savanna_placement"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_jungle_placement"), config.modifyJunglePlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyJunglePlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_jungle_placement"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_swamp_placement"), config.modifySwampPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifySwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_swamp_placement"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("modify_mangrove_swamp_placement"), config.modifyMangroveSwampPlacement)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.modifyMangroveSwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_mangrove_swamp_placement"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("fallen_logs"), config.fallenLogs)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.fallenLogs = newValue)
                .setTooltip(tooltip("fallen_logs"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_trees"), config.wilderWildTreeGen)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.wilderWildTreeGen = newValue)
                .setTooltip(tooltip("wilder_wild_trees"))
                .requireRestart()
                .build());
        category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_grass"), config.wilderWildGrassGen)
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
