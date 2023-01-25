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
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultWorldgenConfig;

@Config(name = "worldgen")
public final class WorldgenConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public final BiomeGeneration biomeGeneration = new BiomeGeneration();

    @ConfigEntry.Gui.CollapsibleObject
    public final BiomePlacement biomePlacement = new BiomePlacement();

    protected static class BiomePlacement {
        public boolean modifyWindsweptSavannaPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT;
        public boolean modifyJunglePlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_JUNGLE_PLACEMENT;
        public boolean modifySwampPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_SWAMP_PLACEMENT;
        public boolean modifyMangroveSwampPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_MANGROVE_SWAMP_PLACEMENT;
    }

	protected static class BiomeGeneration {
		public boolean generateCypressWetlands = DefaultWorldgenConfig.BiomeGeneration.GENERATE_CYPRESS_WETLANDS;
		public boolean generateJellyfishCaves = DefaultWorldgenConfig.BiomeGeneration.GENERATE_JELLYFISH_CAVES;
		public boolean generateMixedForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_MIXED_FOREST;
		public boolean generateOasis = DefaultWorldgenConfig.BiomeGeneration.GENERATE_OASIS;
		public boolean generateWarmRiver = DefaultWorldgenConfig.BiomeGeneration.GENERATE_WARM_RIVER;
		public boolean generateBirchTaiga = DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_TAIGA;
		public boolean generateFlowerField = DefaultWorldgenConfig.BiomeGeneration.GENERATE_FLOWER_FIELD;
		public boolean generateAridSavanna = DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_SAVANNA;
		public boolean generateParchedForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_PARCHED_FOREST;
		public boolean generateAridForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_FOREST;
		public boolean generateOldGrowthSnowyTaiga = DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_SNOWY_TAIGA;
	}

    public boolean betaBeaches = DefaultWorldgenConfig.BETA_BEACHES;
    public boolean dyingTrees = DefaultWorldgenConfig.DYING_TREES;
    public boolean fallenLogs = DefaultWorldgenConfig.FALLEN_LOGS;
    public boolean wilderWildTreeGen = DefaultWorldgenConfig.WILDER_WILD_TREE_GEN;
    public boolean wilderWildGrassGen = DefaultWorldgenConfig.WILDER_WILD_GRASS_GEN;
	public boolean cypressWitchHuts = DefaultWorldgenConfig.CYPRESS_WITCH_HUTS;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().worldgen;
        var biomePlacement = config.biomePlacement;
		var biomes = config.biomeGeneration;
        category.setBackground(WilderSharedConstants.id("textures/config/worldgen.png"));
        var betaBeaches = category.addEntry(entryBuilder.startBooleanToggle(text("beta_beaches"), config.betaBeaches)
                .setDefaultValue(DefaultWorldgenConfig.BETA_BEACHES)
                .setSaveConsumer(newValue -> config.betaBeaches = newValue)
                .setTooltip(tooltip("beta_beaches"))
                .build());

		var cypressWetlands = entryBuilder.startBooleanToggle(text("generate_cypress_wetlands"), biomes.generateCypressWetlands)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_CYPRESS_WETLANDS)
				.setSaveConsumer(newValue -> biomes.generateCypressWetlands = newValue)
				.setTooltip(tooltip("generate_cypress_wetlands"))
				.requireRestart()
				.build();
		var jellyfishCaves = entryBuilder.startBooleanToggle(text("generate_jellyfish_caves"), biomes.generateJellyfishCaves)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_JELLYFISH_CAVES)
				.setSaveConsumer(newValue -> biomes.generateJellyfishCaves = newValue)
				.setTooltip(tooltip("generate_jellyfish_caves"))
				.requireRestart()
				.build();
		var mixedForest = entryBuilder.startBooleanToggle(text("generate_mixed_forest"), biomes.generateMixedForest)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_MIXED_FOREST)
				.setSaveConsumer(newValue -> biomes.generateMixedForest = newValue)
				.setTooltip(tooltip("generate_mixed_forest"))
				.requireRestart()
				.build();
		var oasis = entryBuilder.startBooleanToggle(text("generate_oasis"), biomes.generateOasis)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_OASIS)
				.setSaveConsumer(newValue -> biomes.generateOasis = newValue)
				.setTooltip(tooltip("generate_oasis"))
				.requireRestart()
				.build();
		var warmRiver = entryBuilder.startBooleanToggle(text("generate_warm_river"), biomes.generateWarmRiver)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_WARM_RIVER)
				.setSaveConsumer(newValue -> biomes.generateWarmRiver = newValue)
				.setTooltip(tooltip("generate_warm_river"))
				.requireRestart()
				.build();
		var birchTaiga = entryBuilder.startBooleanToggle(text("generate_birch_taiga"), biomes.generateBirchTaiga)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_TAIGA)
				.setSaveConsumer(newValue -> biomes.generateBirchTaiga = newValue)
				.setTooltip(tooltip("generate_birch_taiga"))
				.requireRestart()
				.build();
		var flowerField = entryBuilder.startBooleanToggle(text("generate_flower_field"), biomes.generateFlowerField)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_TAIGA)
				.setSaveConsumer(newValue -> biomes.generateFlowerField = newValue)
				.setTooltip(tooltip("generate_flower_field"))
				.requireRestart()
				.build();
		var aridSavanna = entryBuilder.startBooleanToggle(text("generate_arid_savanna"), biomes.generateAridSavanna)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_SAVANNA)
				.setSaveConsumer(newValue -> biomes.generateAridSavanna = newValue)
				.setTooltip(tooltip("generate_arid_savanna"))
				.requireRestart()
				.build();
		var parchedForest = entryBuilder.startBooleanToggle(text("generate_parched_forest"), biomes.generateParchedForest)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_PARCHED_FOREST)
				.setSaveConsumer(newValue -> biomes.generateParchedForest = newValue)
				.setTooltip(tooltip("generate_parched_forest"))
				.requireRestart()
				.build();
		var aridForest = entryBuilder.startBooleanToggle(text("generate_arid_forest"), biomes.generateAridForest)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_FOREST)
				.setSaveConsumer(newValue -> biomes.generateAridForest = newValue)
				.setTooltip(tooltip("generate_arid_forest"))
				.requireRestart()
				.build();
		var oldGrowthSnowyTaiga = entryBuilder.startBooleanToggle(text("generate_old_growth_snowy_pine_taiga"), biomes.generateOldGrowthSnowyTaiga)
				.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_SNOWY_TAIGA)
				.setSaveConsumer(newValue -> biomes.generateOldGrowthSnowyTaiga = newValue)
				.setTooltip(tooltip("generate_old_growth_snowy_pine_taiga"))
				.requireRestart()
				.build();

		var biomeGenerationCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("biome_generation"),
				false,
				tooltip("biome_generation"),
				cypressWetlands, jellyfishCaves, mixedForest, oasis, warmRiver, birchTaiga, flowerField, aridSavanna, parchedForest, aridForest, oldGrowthSnowyTaiga
		);

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
		var cypressWitchHuts = category.addEntry(entryBuilder.startBooleanToggle(text("cypress_witch_huts"), config.cypressWitchHuts)
				.setDefaultValue(DefaultWorldgenConfig.CYPRESS_WITCH_HUTS)
				.setSaveConsumer(newValue -> config.cypressWitchHuts = newValue)
				.setTooltip(tooltip("cypress_witch_huts"))
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
