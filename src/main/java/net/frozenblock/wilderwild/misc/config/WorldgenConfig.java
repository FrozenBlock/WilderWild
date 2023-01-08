package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultWorldgenConfig;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "worldgen")
public final class WorldgenConfig implements ConfigData {
    //public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);

    @ConfigEntry.Gui.CollapsibleObject
    public final BiomePlacement biomePlacement = new BiomePlacement();

	@ConfigEntry.Gui.CollapsibleObject
	public final Biomes biomes = new Biomes();

    protected static class BiomePlacement {
        //public boolean modifyDesertPlacement = DefaultWorldgenConfig.BiomePlacement.modifyDesertPlacement;
        //public boolean modifyBadlandsPlacement = DefaultWorldgenConfig.BiomePlacement.modifyBadlandsPlacement;
        public boolean modifyWindsweptSavannaPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT;
        public boolean modifyJunglePlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_JUNGLE_PLACEMENT;
        public boolean modifySwampPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_SWAMP_PLACEMENT;
        public boolean modifyMangroveSwampPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_MANGROVE_SWAMP_PLACEMENT;
    }

	protected static class Biomes {
		public boolean warmRiver = DefaultWorldgenConfig.Biomes.WARM_RIVER;
		public boolean oasis = DefaultWorldgenConfig.Biomes.OASIS;
		public boolean jellyfishCaves = DefaultWorldgenConfig.Biomes.JELLYFISH_CAVES;
		public boolean mixedForest = DefaultWorldgenConfig.Biomes.MIXED_FOREST;
		public boolean cypressWetlands = DefaultWorldgenConfig.Biomes.CYPRESS_WETLANDS;
	}

    public boolean betaBeaches = DefaultWorldgenConfig.BETA_BEACHES;
    public boolean dyingTrees = DefaultWorldgenConfig.DYING_TREES;
    public boolean fallenLogs = DefaultWorldgenConfig.FALLEN_LOGS;
    public boolean wilderWildTreeGen = DefaultWorldgenConfig.WILDER_WILD_TREE_GEN;
    public boolean wilderWildGrassGen = DefaultWorldgenConfig.WILDER_WILD_GRASS_GEN;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().worldgen;
        var biomePlacement = config.biomePlacement;
		var biomes = config.biomes;
        category.setBackground(WilderSharedConstants.id("textures/config/worldgen.png"));
        var betaBeaches = category.addEntry(entryBuilder.startBooleanToggle(text("beta_beaches"), config.betaBeaches)
                .setDefaultValue(DefaultWorldgenConfig.BETA_BEACHES)
                .setSaveConsumer(newValue -> config.betaBeaches = newValue)
                .setTooltip(tooltip("beta_beaches"))
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
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.MODIFY_JUNGLE_PLACEMENT)
                .setSaveConsumer(newValue -> biomePlacement.modifyJunglePlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_jungle_placement"))
                .requireRestart()
                .build();
        var mangroveSwamp = entryBuilder.startBooleanToggle(text("modify_mangrove_swamp_placement"), biomePlacement.modifyMangroveSwampPlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.MODIFY_MANGROVE_SWAMP_PLACEMENT)
                .setSaveConsumer(newValue -> biomePlacement.modifyMangroveSwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_mangrove_swamp_placement"))
                .requireRestart()
                .build();
        var swamp = entryBuilder.startBooleanToggle(text("modify_swamp_placement"), biomePlacement.modifySwampPlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.MODIFY_SWAMP_PLACEMENT)
                .setSaveConsumer(newValue -> biomePlacement.modifySwampPlacement = newValue)
                .setYesNoTextSupplier(bool -> text("biome_placement." + bool))
                .setTooltip(tooltip("modify_swamp_placement"))
                .requireRestart()
                .build();
        var windsweptSavanna = entryBuilder.startBooleanToggle(text("modify_windswept_savanna_placement"), biomePlacement.modifyWindsweptSavannaPlacement)
                .setDefaultValue(DefaultWorldgenConfig.BiomePlacement.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT)
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

		var cypressWetlands = entryBuilder.startBooleanToggle(text("cypress_wetlands"), biomes.cypressWetlands)
				.setDefaultValue(DefaultWorldgenConfig.Biomes.CYPRESS_WETLANDS)
				.setSaveConsumer(newValue -> biomes.cypressWetlands = newValue)
				.setYesNoTextSupplier(bool -> text("biome." + bool))
				.setTooltip(tooltip("cypress_wetlands"))
				.requireRestart()
				.build();
		var mixedForest = entryBuilder.startBooleanToggle(text("mixed_forest"), biomes.mixedForest)
				.setDefaultValue(DefaultWorldgenConfig.Biomes.MIXED_FOREST)
				.setSaveConsumer(newValue -> biomes.mixedForest = newValue)
				.setYesNoTextSupplier(bool -> text("biome." + bool))
				.setTooltip(tooltip("mixed_forest"))
				.requireRestart()
				.build();
		var jellyfishCaves = entryBuilder.startBooleanToggle(text("jellyfish_caves"), biomes.jellyfishCaves)
				.setDefaultValue(DefaultWorldgenConfig.Biomes.JELLYFISH_CAVES)
				.setSaveConsumer(newValue -> biomes.jellyfishCaves = newValue)
				.setYesNoTextSupplier(bool -> text("biome." + bool))
				.setTooltip(tooltip("jellyfish_caves"))
				.requireRestart()
				.build();
		var oasis = entryBuilder.startBooleanToggle(text("oasis"), biomes.oasis)
				.setDefaultValue(DefaultWorldgenConfig.Biomes.OASIS)
				.setSaveConsumer(newValue -> biomes.oasis = newValue)
				.setYesNoTextSupplier(bool -> text("biome." + bool))
				.setTooltip(tooltip("oasis"))
				.requireRestart()
				.build();
		var warmRiver = entryBuilder.startBooleanToggle(text("warm_river"), biomes.warmRiver)
				.setDefaultValue(DefaultWorldgenConfig.Biomes.WARM_RIVER)
				.setSaveConsumer(newValue -> biomes.warmRiver = newValue)
				.setYesNoTextSupplier(bool -> text("biome." + bool))
				.setTooltip(tooltip("warm_river"))
				.requireRestart()
				.build();

		var biomeCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("biomes"),
				false,
				tooltip("biomes"),
				cypressWetlands, mixedForest, jellyfishCaves, oasis, warmRiver
		);

        /*var dyingTrees = category.addEntry(entryBuilder.startBooleanToggle(text("dying_trees"), config.dyingTrees)
                .setDefaultValue(DefaultWorldgenConfig.dyingTrees)
                .setSaveConsumer(newValue -> config.dyingTrees = newValue)
                .setTooltip(tooltip("dying_trees"))
                .requireRestart()
                .build()
        );*/
        var fallenLogs = category.addEntry(entryBuilder.startBooleanToggle(text("fallen_logs"), config.fallenLogs)
                .setDefaultValue(DefaultWorldgenConfig.FALLEN_LOGS)
                .setSaveConsumer(newValue -> config.fallenLogs = newValue)
                .setTooltip(tooltip("fallen_logs"))
                .requireRestart()
                .build()
        );
        var wilderWildGrass = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_grass"), config.wilderWildGrassGen)
                .setDefaultValue(DefaultWorldgenConfig.WILDER_WILD_GRASS_GEN)
                .setSaveConsumer(newValue -> config.wilderWildGrassGen = newValue)
                .setTooltip(tooltip("wilder_wild_grass"))
                .requireRestart()
                .build()
        );
        var wilderWildTrees = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_trees"), config.wilderWildTreeGen)
                .setDefaultValue(DefaultWorldgenConfig.WILDER_WILD_TREE_GEN)
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
