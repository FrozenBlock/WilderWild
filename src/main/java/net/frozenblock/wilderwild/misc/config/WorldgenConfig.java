package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultWorldgenConfig;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.*;

@Config(name = "worldgen")
public final class WorldgenConfig implements ConfigData {
    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);

    @ConfigEntry.Gui.CollapsibleObject
    public final BiomePlacement biomePlacement = new BiomePlacement();

    protected static class BiomePlacement {
        //public boolean modifyDesertPlacement = DefaultWorldgenConfig.BiomePlacement.modifyDesertPlacement;
        //public boolean modifyBadlandsPlacement = DefaultWorldgenConfig.BiomePlacement.modifyBadlandsPlacement;
        public boolean modifyWindsweptSavannaPlacement = DefaultWorldgenConfig.BiomePlacement.modifyWindsweptSavannaPlacement;
        public boolean modifyJunglePlacement = DefaultWorldgenConfig.BiomePlacement.modifyJunglePlacement;
        public boolean modifySwampPlacement = DefaultWorldgenConfig.BiomePlacement.modifySwampPlacement;
        public boolean modifyMangroveSwampPlacement = DefaultWorldgenConfig.BiomePlacement.modifyMangroveSwampPlacement;
    }

    public boolean betaBeaches = DefaultWorldgenConfig.betaBeaches;
    public boolean dyingTrees = DefaultWorldgenConfig.dyingTrees;
    public boolean fallenLogs = DefaultWorldgenConfig.fallenLogs;
    public boolean wilderWildTreeGen = DefaultWorldgenConfig.wilderWildTreeGen;
    public boolean wilderWildGrassGen = DefaultWorldgenConfig.wilderWildGrassGen;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().worldgen;
        var biomePlacement = config.biomePlacement;
        category.setBackground(WilderSharedConstants.id("textures/config/worldgen.png"));
        var betaBeaches = category.addEntry(entryBuilder.startBooleanToggle(text("beta_beaches"), config.betaBeaches)
                .setDefaultValue(DefaultWorldgenConfig.betaBeaches)
                .setSaveConsumer(newValue -> config.betaBeaches = newValue)
                .setTooltip(tooltip("beta_beaches"))
                .requireRestart()
                .build());
        /*
        var badlands = category.addEntry(entryBuilder.startBooleanToggle(text("modify_badlands_placement"), biomePlacement.modifyBadlandsPlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.modifyBadlandsPlacement)
                .setSaveConsumer(newValue -> biomePlacement.modifyBadlandsPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_badlands_placement"))
                .requireRestart()
                .build());
        var desert = category.addEntry(entryBuilder.startBooleanToggle(text("modify_desert_placement"), biomePlacement.modifyDesertPlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.modifyDesertPlacement)
                .setSaveConsumer(newValue -> biomePlacement.modifyDesertPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_desert_placement"))
                .requireRestart()
                .build());
         */
        var jungle = entryBuilder.startBooleanToggle(text("modify_jungle_placement"), biomePlacement.modifyJunglePlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.modifyJunglePlacement)
                .setSaveConsumer(newValue -> biomePlacement.modifyJunglePlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_jungle_placement"))
                .requireRestart()
                .build();
        var mangroveSwamp = entryBuilder.startBooleanToggle(text("modify_mangrove_swamp_placement"), biomePlacement.modifyMangroveSwampPlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.modifyMangroveSwampPlacement)
                .setSaveConsumer(newValue -> biomePlacement.modifyMangroveSwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_mangrove_swamp_placement"))
                .requireRestart()
                .build();
        var swamp = entryBuilder.startBooleanToggle(text("modify_swamp_placement"), biomePlacement.modifySwampPlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.modifySwampPlacement)
                .setSaveConsumer(newValue -> biomePlacement.modifySwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_swamp_placement"))
                .requireRestart()
                .build();
        var windsweptSavanna = entryBuilder.startBooleanToggle(text("modify_windswept_savanna_placement"), biomePlacement.modifyWindsweptSavannaPlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.modifyWindsweptSavannaPlacement)
                .setSaveConsumer(newValue -> biomePlacement.modifyWindsweptSavannaPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_windswept_savanna_placement"))
                .requireRestart()
                .build();

        var biomePlacementCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("biome_placement"),
                false,
                tooltip("biome_placement"),
                jungle, mangroveSwamp, swamp, windsweptSavanna
        );

        /*var dyingTrees = category.addEntry(entryBuilder.startBooleanToggle(text("dying_trees"), config.dyingTrees)
                .setDefaultValue(DefaultWorldgenConfig.dyingTrees)
                .setSaveConsumer(newValue -> config.dyingTrees = newValue)
                .setTooltip(tooltip("dying_trees"))
                .requireRestart()
                .build()
        );*/
        var fallenLogs = category.addEntry(entryBuilder.startBooleanToggle(text("fallen_logs"), config.fallenLogs)
                .setDefaultValue(DefaultWorldgenConfig.fallenLogs)
                .setSaveConsumer(newValue -> config.fallenLogs = newValue)
                .setTooltip(tooltip("fallen_logs"))
                .requireRestart()
                .build()
        );
        var wilderWildGrass = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_grass"), config.wilderWildGrassGen)
                .setDefaultValue(DefaultWorldgenConfig.wilderWildGrassGen)
                .setSaveConsumer(newValue -> config.wilderWildGrassGen = newValue)
                .setTooltip(tooltip("wilder_wild_grass"))
                .requireRestart()
                .build()
        );
        var wilderWildTrees = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_trees"), config.wilderWildTreeGen)
                .setDefaultValue(DefaultWorldgenConfig.wilderWildTreeGen)
                .setSaveConsumer(newValue -> config.wilderWildTreeGen = newValue)
                .setTooltip(tooltip("wilder_wild_trees"))
                .requireRestart()
                .build()
        );
    }

    //public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static WorldgenConfig get() {
        AutoConfig.register(WorldgenConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(WorldgenConfig.class).getConfig();
    }*/
}
