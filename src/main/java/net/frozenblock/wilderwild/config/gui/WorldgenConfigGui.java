package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.config.defaults.DefaultWorldgenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.tooltip;

@Environment(EnvType.CLIENT)
public final class WorldgenConfigGui {
	private WorldgenConfigGui() {
		throw new UnsupportedOperationException("WorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = WorldgenConfig.get();
		var biomePlacement = config.biomePlacement;
		var biomes = config.biomeGeneration;
		var waterColors = config.waterColors;
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
		var warmBeach = entryBuilder.startBooleanToggle(text("generate_warm_beach"), biomes.generateWarmBeach)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_WARM_BEACH)
			.setSaveConsumer(newValue -> biomes.generateWarmBeach = newValue)
			.setTooltip(tooltip("generate_warm_beach"))
			.requireRestart()
			.build();
		var birchTaiga = entryBuilder.startBooleanToggle(text("generate_birch_taiga"), biomes.generateBirchTaiga)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_TAIGA)
			.setSaveConsumer(newValue -> biomes.generateBirchTaiga = newValue)
			.setTooltip(tooltip("generate_birch_taiga"))
			.requireRestart()
			.build();
		var oldGrowthBirchTaiga = entryBuilder.startBooleanToggle(text("generate_old_growth_birch_taiga"), biomes.generateOldGrowthBirchTaiga)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_BIRCH_TAIGA)
			.setSaveConsumer(newValue -> biomes.generateOldGrowthBirchTaiga = newValue)
			.setTooltip(tooltip("generate_old_growth_birch_taiga"))
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
		var oldGrowthSnowyTaiga = entryBuilder.startBooleanToggle(text("generate_snowy_old_growth_pine_taiga"), biomes.generateOldGrowthSnowyTaiga)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_SNOWY_TAIGA)
			.setSaveConsumer(newValue -> biomes.generateOldGrowthSnowyTaiga = newValue)
			.setTooltip(tooltip("generate_snowy_old_growth_pine_taiga"))
			.requireRestart()
			.build();
		var birchJungle = entryBuilder.startBooleanToggle(text("generate_birch_jungle"), biomes.generateBirchJungle)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_JUNGLE)
			.setSaveConsumer(newValue -> biomes.generateBirchJungle = newValue)
			.setTooltip(tooltip("generate_birch_jungle"))
			.requireRestart()
			.build();
		var sparseBirchJungle = entryBuilder.startBooleanToggle(text("generate_sparse_birch_jungle"), biomes.generateSparseBirchJungle)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_SPARSE_BIRCH_JUNGLE)
			.setSaveConsumer(newValue -> biomes.generateSparseBirchJungle = newValue)
			.setTooltip(tooltip("generate_sparse_birch_jungle"))
			.requireRestart()
			.build();
		var oldGrowthDarkForest = entryBuilder.startBooleanToggle(text("generate_old_growth_dark_forest"), biomes.generateOldGrowthDarkForest)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_DARK_FOREST)
			.setSaveConsumer(newValue -> biomes.generateOldGrowthDarkForest = newValue)
			.setTooltip(tooltip("generate_old_growth_dark_forest"))
			.requireRestart()
			.build();
		var darkBirchForest = entryBuilder.startBooleanToggle(text("generate_dark_birch_forest"), biomes.generateDarkBirchForest)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_DARK_BIRCH_FOREST)
			.setSaveConsumer(newValue -> biomes.generateDarkBirchForest = newValue)
			.setTooltip(tooltip("generate_dark_birch_forest"))
			.requireRestart()
			.build();
		var semiBirchForest = entryBuilder.startBooleanToggle(text("generate_semi_birch_forest"), biomes.generateSemiBirchForest)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_SEMI_BIRCH_FOREST)
			.setSaveConsumer(newValue -> biomes.generateSemiBirchForest = newValue)
			.setTooltip(tooltip("generate_semi_birch_forest"))
			.requireRestart()
			.build();
		var temperateRainforest = entryBuilder.startBooleanToggle(text("generate_temperate_rainforest"), biomes.generateTemperateRainforest)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_TEMPERATE_RAINFOREST)
			.setSaveConsumer(newValue -> biomes.generateTemperateRainforest = newValue)
			.setTooltip(tooltip("generate_temperate_rainforest"))
			.requireRestart()
			.build();
		var rainforest = entryBuilder.startBooleanToggle(text("generate_rainforest"), biomes.generateRainforest)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_RAINFOREST)
			.setSaveConsumer(newValue -> biomes.generateRainforest = newValue)
			.setTooltip(tooltip("generate_rainforest"))
			.requireRestart()
			.build();
		var darkTaiga = entryBuilder.startBooleanToggle(text("generate_dark_taiga"), biomes.generateDarkTaiga)
			.setDefaultValue(DefaultWorldgenConfig.BiomeGeneration.GENERATE_DARK_TAIGA)
			.setSaveConsumer(newValue -> biomes.generateDarkTaiga = newValue)
			.setTooltip(tooltip("generate_dark_taiga"))
			.requireRestart()
			.build();

		var biomeGenerationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_generation"),
			false,
			tooltip("biome_generation"),
			aridForest, aridSavanna, birchJungle, birchTaiga, cypressWetlands, darkBirchForest, darkTaiga, flowerField, jellyfishCaves, mixedForest,
			oasis, oldGrowthBirchTaiga, oldGrowthDarkForest, oldGrowthSnowyTaiga, parchedForest, rainforest, semiBirchForest,
			sparseBirchJungle, temperateRainforest, warmBeach, warmRiver
		);

		var cherryGrove = entryBuilder.startBooleanToggle(text("modify_cherry_grove_placement"), biomePlacement.modifyCherryGrovePlacement)
			.setDefaultValue(DefaultWorldgenConfig.BiomePlacement.MODIFY_CHERRY_GROVE_PLACEMENT)
			.setSaveConsumer(newValue -> biomePlacement.modifyCherryGrovePlacement = newValue)
			.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
			.setTooltip(tooltip("modify_cherry_grove_placement"))
			.requireRestart()
			.build();
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
		var stonyShore = entryBuilder.startBooleanToggle(text("modify_stony_shore_placement"), biomePlacement.modifyStonyShorePlacement)
			.setDefaultValue(DefaultWorldgenConfig.BiomePlacement.MODIFY_STONY_SHORE_PLACEMENT)
			.setSaveConsumer(newValue -> biomePlacement.modifyStonyShorePlacement = newValue)
			.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
			.setTooltip(tooltip("modify_stony_shore_placement"))
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

		var biomePlacementCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_placement"),
			false,
			tooltip("biome_placement"),
			cherryGrove, jungle, mangroveSwamp, stonyShore, swamp, windsweptSavanna
		);

		var hotBiomes = entryBuilder.startBooleanToggle(text("hot_water"), waterColors.modifyHotWater)
			.setDefaultValue(DefaultWorldgenConfig.WaterColors.HOT_BIOMES)
			.setSaveConsumer(newValue -> waterColors.modifyHotWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("hot_water"))
			.requireRestart()
			.build();
		var lukewarmBiomes = entryBuilder.startBooleanToggle(text("lukewarm_water"), waterColors.modifyLukewarmWater)
			.setDefaultValue(DefaultWorldgenConfig.WaterColors.LUKEWARM_BIOMES)
			.setSaveConsumer(newValue -> waterColors.modifyLukewarmWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("lukewarm_water"))
			.requireRestart()
			.build();
		var snowyBiomes = entryBuilder.startBooleanToggle(text("snowy_water"), waterColors.modifySnowyWater)
			.setDefaultValue(DefaultWorldgenConfig.WaterColors.SNOWY_BIOMES)
			.setSaveConsumer(newValue -> waterColors.modifySnowyWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("snowy_water"))
			.requireRestart()
			.build();
		var frozenBiomes = entryBuilder.startBooleanToggle(text("frozen_water"), waterColors.modifyFrozenWater)
			.setDefaultValue(DefaultWorldgenConfig.WaterColors.FROZEN_BIOMES)
			.setSaveConsumer(newValue -> waterColors.modifyFrozenWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("frozen_water"))
			.requireRestart()
			.build();

		var waterColorCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("water_colors"),
			false,
			tooltip("water_colors"),
			hotBiomes, lukewarmBiomes, snowyBiomes, frozenBiomes
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
		var snappedLogs = category.addEntry(entryBuilder.startBooleanToggle(text("snapped_logs"), config.snappedLogs)
			.setDefaultValue(DefaultWorldgenConfig.SNAPPED_LOGS)
			.setSaveConsumer(newValue -> config.snappedLogs = newValue)
			.setTooltip(tooltip("snapped_logs"))
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
		var wilderWildFlowers = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_flowers"), config.wilderWildFlowerGen)
			.setDefaultValue(DefaultWorldgenConfig.WILDER_WILD_FLOWER_GEN)
			.setSaveConsumer(newValue -> config.wilderWildFlowerGen = newValue)
			.setTooltip(tooltip("wilder_wild_flowers"))
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
		var wilderWildBushes = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_bushes"), config.wilderWildBushGen)
			.setDefaultValue(DefaultWorldgenConfig.WILDER_WILD_BUSH_GEN)
			.setSaveConsumer(newValue -> config.wilderWildBushGen = newValue)
			.setTooltip(tooltip("wilder_wild_bushes"))
			.requireRestart()
			.build()
		);
		var wilderWildCacti = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_cacti"), config.wilderWildCactusGen)
			.setDefaultValue(DefaultWorldgenConfig.WILDER_WILD_CACTUS_GEN)
			.setSaveConsumer(newValue -> config.wilderWildCactusGen = newValue)
			.setTooltip(tooltip("wilder_wild_cacti"))
			.requireRestart()
			.build()
		);
		var wilderWildMushrooms = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_mushrooms"), config.wilderWildMushroomGen)
			.setDefaultValue(DefaultWorldgenConfig.WILDER_WILD_MUSHROOM_GEN)
			.setSaveConsumer(newValue -> config.wilderWildMushroomGen = newValue)
			.setTooltip(tooltip("wilder_wild_mushrooms"))
			.requireRestart()
			.build()
		);
		var tumbleweed = category.addEntry(entryBuilder.startBooleanToggle(text("tumbleweed_gen"), config.tumbleweed)
			.setDefaultValue(DefaultWorldgenConfig.TUMBLEWEED_GEN)
			.setSaveConsumer(newValue -> config.tumbleweed = newValue)
			.setTooltip(tooltip("tumbleweed_gen"))
			.requireRestart()
			.build()
		);
		var algae = category.addEntry(entryBuilder.startBooleanToggle(text("algae_gen"), config.algae)
			.setDefaultValue(DefaultWorldgenConfig.ALGAE_GEN)
			.setSaveConsumer(newValue -> config.algae = newValue)
			.setTooltip(tooltip("algae_gen"))
			.requireRestart()
			.build()
		);
		var termite = category.addEntry(entryBuilder.startBooleanToggle(text("termite_gen"), config.termiteGen)
			.setDefaultValue(DefaultWorldgenConfig.TERMITE_GEN)
			.setSaveConsumer(newValue -> config.termiteGen = newValue)
			.setTooltip(tooltip("termite_gen"))
			.requireRestart()
			.build()
		);
		var surfaceDecoration = category.addEntry(entryBuilder.startBooleanToggle(text("surface_decoration"), config.surfaceDecoration)
			.setDefaultValue(DefaultWorldgenConfig.SURFACE_DECORATION)
			.setSaveConsumer(newValue -> config.surfaceDecoration = newValue)
			.setTooltip(tooltip("surface_decoration"))
			.requireRestart()
			.build()
		);
		var snowBelowTrees = category.addEntry(entryBuilder.startBooleanToggle(text("snow_below_trees"), config.snowBelowTrees)
			.setDefaultValue(DefaultWorldgenConfig.SNOW_BELOW_TREES)
			.setSaveConsumer(newValue -> config.snowBelowTrees = newValue)
			.setTooltip(tooltip("snow_below_trees"))
			.requireRestart()
			.build()
		);
		var surfaceTransitions = category.addEntry(entryBuilder.startBooleanToggle(text("surface_transitions"), config.surfaceTransitions)
			.setDefaultValue(DefaultWorldgenConfig.SURFACE_TRANSITIONS)
			.setSaveConsumer(newValue -> config.surfaceTransitions = newValue)
			.setTooltip(tooltip("surface_transitions"))
			.requireRestart()
			.build()
		);
		var newWitchHuts = category.addEntry(entryBuilder.startBooleanToggle(text("new_witch_huts"), config.newWitchHuts)
			.setDefaultValue(DefaultWorldgenConfig.NEW_WITCH_HUTS)
			.setSaveConsumer(newValue -> config.newWitchHuts = newValue)
			.setTooltip(tooltip("new_witch_huts"))
			.requireRestart()
			.build()
		);
	}
}
