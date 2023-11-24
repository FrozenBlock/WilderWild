/*
 * Copyright 2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import org.jetbrains.annotations.NotNull;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.tooltip;

@Environment(EnvType.CLIENT)
public final class WorldgenConfigGui {
	private WorldgenConfigGui() {
		throw new UnsupportedOperationException("WorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = WorldgenConfig.get(true);
		var defaultConfig = WorldgenConfig.INSTANCE.defaultInstance();
		var biomePlacement = config.biomePlacement;
		var biomes = config.biomeGeneration;
		var waterColors = config.waterColors;
		category.setBackground(WilderSharedConstants.id("textures/config/worldgen.png"));

		var betaBeaches = category.addEntry(entryBuilder.startBooleanToggle(text("beta_beaches"), config.betaBeaches)
			.setDefaultValue(defaultConfig.betaBeaches)
			.setSaveConsumer(newValue -> config.betaBeaches = newValue)
			.setTooltip(tooltip("beta_beaches"))
			.build());

		var cypressWetlands = entryBuilder.startBooleanToggle(text("generate_cypress_wetlands"), biomes.generateCypressWetlands)
			.setDefaultValue(defaultConfig.biomeGeneration.generateCypressWetlands)
			.setSaveConsumer(newValue -> biomes.generateCypressWetlands = newValue)
			.setTooltip(tooltip("generate_cypress_wetlands"))
			.requireRestart()
			.build();
		var jellyfishCaves = entryBuilder.startBooleanToggle(text("generate_jellyfish_caves"), biomes.generateJellyfishCaves)
			.setDefaultValue(defaultConfig.biomeGeneration.generateJellyfishCaves)
			.setSaveConsumer(newValue -> biomes.generateJellyfishCaves = newValue)
			.setTooltip(tooltip("generate_jellyfish_caves"))
			.requireRestart()
			.build();
		var mixedForest = entryBuilder.startBooleanToggle(text("generate_mixed_forest"), biomes.generateMixedForest)
			.setDefaultValue(defaultConfig.biomeGeneration.generateMixedForest)
			.setSaveConsumer(newValue -> biomes.generateMixedForest = newValue)
			.setTooltip(tooltip("generate_mixed_forest"))
			.requireRestart()
			.build();
		var oasis = entryBuilder.startBooleanToggle(text("generate_oasis"), biomes.generateOasis)
			.setDefaultValue(defaultConfig.biomeGeneration.generateOasis)
			.setSaveConsumer(newValue -> biomes.generateOasis = newValue)
			.setTooltip(tooltip("generate_oasis"))
			.requireRestart()
			.build();
		var warmRiver = entryBuilder.startBooleanToggle(text("generate_warm_river"), biomes.generateWarmRiver)
			.setDefaultValue(defaultConfig.biomeGeneration.generateWarmRiver)
			.setSaveConsumer(newValue -> biomes.generateWarmRiver = newValue)
			.setTooltip(tooltip("generate_warm_river"))
			.requireRestart()
			.build();
		var warmBeach = entryBuilder.startBooleanToggle(text("generate_warm_beach"), biomes.generateWarmBeach)
			.setDefaultValue(defaultConfig.biomeGeneration.generateWarmBeach)
			.setSaveConsumer(newValue -> biomes.generateWarmBeach = newValue)
			.setTooltip(tooltip("generate_warm_beach"))
			.requireRestart()
			.build();
		var birchTaiga = entryBuilder.startBooleanToggle(text("generate_birch_taiga"), biomes.generateBirchTaiga)
			.setDefaultValue(defaultConfig.biomeGeneration.generateBirchTaiga)
			.setSaveConsumer(newValue -> biomes.generateBirchTaiga = newValue)
			.setTooltip(tooltip("generate_birch_taiga"))
			.requireRestart()
			.build();
		var oldGrowthBirchTaiga = entryBuilder.startBooleanToggle(text("generate_old_growth_birch_taiga"), biomes.generateOldGrowthBirchTaiga)
			.setDefaultValue(defaultConfig.biomeGeneration.generateOldGrowthBirchTaiga)
			.setSaveConsumer(newValue -> biomes.generateOldGrowthBirchTaiga = newValue)
			.setTooltip(tooltip("generate_old_growth_birch_taiga"))
			.requireRestart()
			.build();
		var flowerField = entryBuilder.startBooleanToggle(text("generate_flower_field"), biomes.generateFlowerField)
			.setDefaultValue(defaultConfig.biomeGeneration.generateFlowerField)
			.setSaveConsumer(newValue -> biomes.generateFlowerField = newValue)
			.setTooltip(tooltip("generate_flower_field"))
			.requireRestart()
			.build();
		var aridSavanna = entryBuilder.startBooleanToggle(text("generate_arid_savanna"), biomes.generateAridSavanna)
			.setDefaultValue(defaultConfig.biomeGeneration.generateAridSavanna)
			.setSaveConsumer(newValue -> biomes.generateAridSavanna = newValue)
			.setTooltip(tooltip("generate_arid_savanna"))
			.requireRestart()
			.build();
		var parchedForest = entryBuilder.startBooleanToggle(text("generate_parched_forest"), biomes.generateParchedForest)
			.setDefaultValue(defaultConfig.biomeGeneration.generateParchedForest)
			.setSaveConsumer(newValue -> biomes.generateParchedForest = newValue)
			.setTooltip(tooltip("generate_parched_forest"))
			.requireRestart()
			.build();
		var aridForest = entryBuilder.startBooleanToggle(text("generate_arid_forest"), biomes.generateAridForest)
			.setDefaultValue(defaultConfig.biomeGeneration.generateAridForest)
			.setSaveConsumer(newValue -> biomes.generateAridForest = newValue)
			.setTooltip(tooltip("generate_arid_forest"))
			.requireRestart()
			.build();
		var oldGrowthSnowyTaiga = entryBuilder.startBooleanToggle(text("generate_snowy_old_growth_pine_taiga"), biomes.generateOldGrowthSnowyTaiga)
			.setDefaultValue(defaultConfig.biomeGeneration.generateOldGrowthSnowyTaiga)
			.setSaveConsumer(newValue -> biomes.generateOldGrowthSnowyTaiga = newValue)
			.setTooltip(tooltip("generate_snowy_old_growth_pine_taiga"))
			.requireRestart()
			.build();
		var birchJungle = entryBuilder.startBooleanToggle(text("generate_birch_jungle"), biomes.generateBirchJungle)
			.setDefaultValue(defaultConfig.biomeGeneration.generateBirchJungle)
			.setSaveConsumer(newValue -> biomes.generateBirchJungle = newValue)
			.setTooltip(tooltip("generate_birch_jungle"))
			.requireRestart()
			.build();
		var sparseBirchJungle = entryBuilder.startBooleanToggle(text("generate_sparse_birch_jungle"), biomes.generateSparseBirchJungle)
			.setDefaultValue(defaultConfig.biomeGeneration.generateSparseBirchJungle)
			.setSaveConsumer(newValue -> biomes.generateSparseBirchJungle = newValue)
			.setTooltip(tooltip("generate_sparse_birch_jungle"))
			.requireRestart()
			.build();
		var oldGrowthDarkForest = entryBuilder.startBooleanToggle(text("generate_old_growth_dark_forest"), biomes.generateOldGrowthDarkForest)
			.setDefaultValue(defaultConfig.biomeGeneration.generateOldGrowthDarkForest)
			.setSaveConsumer(newValue -> biomes.generateOldGrowthDarkForest = newValue)
			.setTooltip(tooltip("generate_old_growth_dark_forest"))
			.requireRestart()
			.build();
		var darkBirchForest = entryBuilder.startBooleanToggle(text("generate_dark_birch_forest"), biomes.generateDarkBirchForest)
			.setDefaultValue(defaultConfig.biomeGeneration.generateDarkBirchForest)
			.setSaveConsumer(newValue -> biomes.generateDarkBirchForest = newValue)
			.setTooltip(tooltip("generate_dark_birch_forest"))
			.requireRestart()
			.build();
		var semiBirchForest = entryBuilder.startBooleanToggle(text("generate_semi_birch_forest"), biomes.generateSemiBirchForest)
			.setDefaultValue(defaultConfig.biomeGeneration.generateSemiBirchForest)
			.setSaveConsumer(newValue -> biomes.generateSemiBirchForest = newValue)
			.setTooltip(tooltip("generate_semi_birch_forest"))
			.requireRestart()
			.build();
		var temperateRainforest = entryBuilder.startBooleanToggle(text("generate_temperate_rainforest"), biomes.generateTemperateRainforest)
			.setDefaultValue(defaultConfig.biomeGeneration.generateTemperateRainforest)
			.setSaveConsumer(newValue -> biomes.generateTemperateRainforest = newValue)
			.setTooltip(tooltip("generate_temperate_rainforest"))
			.requireRestart()
			.build();
		var rainforest = entryBuilder.startBooleanToggle(text("generate_rainforest"), biomes.generateRainforest)
			.setDefaultValue(defaultConfig.biomeGeneration.generateRainforest)
			.setSaveConsumer(newValue -> biomes.generateRainforest = newValue)
			.setTooltip(tooltip("generate_rainforest"))
			.requireRestart()
			.build();
		var darkTaiga = entryBuilder.startBooleanToggle(text("generate_dark_taiga"), biomes.generateDarkTaiga)
			.setDefaultValue(defaultConfig.biomeGeneration.generateDarkTaiga)
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
			.setDefaultValue(defaultConfig.biomePlacement.modifyCherryGrovePlacement)
			.setSaveConsumer(newValue -> biomePlacement.modifyCherryGrovePlacement = newValue)
			.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
			.setTooltip(tooltip("modify_cherry_grove_placement"))
			.requireRestart()
			.build();
		var jungle = entryBuilder.startBooleanToggle(text("modify_jungle_placement"), biomePlacement.modifyJunglePlacement)
			.setDefaultValue(defaultConfig.biomePlacement.modifyJunglePlacement)
			.setSaveConsumer(newValue -> biomePlacement.modifyJunglePlacement = newValue)
			.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
			.setTooltip(tooltip("modify_jungle_placement"))
			.requireRestart()
			.build();
		var mangroveSwamp = entryBuilder.startBooleanToggle(text("modify_mangrove_swamp_placement"), biomePlacement.modifyMangroveSwampPlacement)
			.setDefaultValue(defaultConfig.biomePlacement.modifyMangroveSwampPlacement)
			.setSaveConsumer(newValue -> biomePlacement.modifyMangroveSwampPlacement = newValue)
			.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
			.setTooltip(tooltip("modify_mangrove_swamp_placement"))
			.requireRestart()
			.build();
		var stonyShore = entryBuilder.startBooleanToggle(text("modify_stony_shore_placement"), biomePlacement.modifyStonyShorePlacement)
			.setDefaultValue(defaultConfig.biomePlacement.modifyStonyShorePlacement)
			.setSaveConsumer(newValue -> biomePlacement.modifyStonyShorePlacement = newValue)
			.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
			.setTooltip(tooltip("modify_stony_shore_placement"))
			.requireRestart()
			.build();
		var swamp = entryBuilder.startBooleanToggle(text("modify_swamp_placement"), biomePlacement.modifySwampPlacement)
			.setDefaultValue(defaultConfig.biomePlacement.modifySwampPlacement)
			.setSaveConsumer(newValue -> biomePlacement.modifySwampPlacement = newValue)
			.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
			.setTooltip(tooltip("modify_swamp_placement"))
			.requireRestart()
			.build();
		var windsweptSavanna = entryBuilder.startBooleanToggle(text("modify_windswept_savanna_placement"), biomePlacement.modifyWindsweptSavannaPlacement)
			.setDefaultValue(defaultConfig.biomePlacement.modifyWindsweptSavannaPlacement)
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
			.setDefaultValue(defaultConfig.waterColors.modifyHotWater)
			.setSaveConsumer(newValue -> waterColors.modifyHotWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("hot_water"))
			.requireRestart()
			.build();
		var lukewarmBiomes = entryBuilder.startBooleanToggle(text("lukewarm_water"), waterColors.modifyLukewarmWater)
			.setDefaultValue(defaultConfig.waterColors.modifyLukewarmWater)
			.setSaveConsumer(newValue -> waterColors.modifyLukewarmWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("lukewarm_water"))
			.requireRestart()
			.build();
		var snowyBiomes = entryBuilder.startBooleanToggle(text("snowy_water"), waterColors.modifySnowyWater)
			.setDefaultValue(defaultConfig.waterColors.modifySnowyWater)
			.setSaveConsumer(newValue -> waterColors.modifySnowyWater = newValue)
			.setYesNoTextSupplier(bool -> text("water_colors." + bool))
			.setTooltip(tooltip("snowy_water"))
			.requireRestart()
			.build();
		var frozenBiomes = entryBuilder.startBooleanToggle(text("frozen_water"), waterColors.modifyFrozenWater)
			.setDefaultValue(defaultConfig.waterColors.modifyFrozenWater)
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
                .setDefaultValue(defaultConfig.dyingTrees)
                .setSaveConsumer(newValue -> config.dyingTrees = newValue)
                .setTooltip(tooltip("dying_trees"))
                .requireRestart()
                .build()
        );*/
		var fallenLogs = category.addEntry(entryBuilder.startBooleanToggle(text("fallen_logs"), config.fallenLogs)
			.setDefaultValue(defaultConfig.fallenLogs)
			.setSaveConsumer(newValue -> config.fallenLogs = newValue)
			.setTooltip(tooltip("fallen_logs"))
			.requireRestart()
			.build()
		);
		var snappedLogs = category.addEntry(entryBuilder.startBooleanToggle(text("snapped_logs"), config.snappedLogs)
			.setDefaultValue(defaultConfig.snappedLogs)
			.setSaveConsumer(newValue -> config.snappedLogs = newValue)
			.setTooltip(tooltip("snapped_logs"))
			.requireRestart()
			.build()
		);

		var wilderWildGrass = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_grass"), config.wilderWildGrassGen)
			.setDefaultValue(defaultConfig.wilderWildGrassGen)
			.setSaveConsumer(newValue -> config.wilderWildGrassGen = newValue)
			.setTooltip(tooltip("wilder_wild_grass"))
			.requireRestart()
			.build()
		);
		var wilderWildFlowers = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_flowers"), config.wilderWildFlowerGen)
			.setDefaultValue(defaultConfig.wilderWildFlowerGen)
			.setSaveConsumer(newValue -> config.wilderWildFlowerGen = newValue)
			.setTooltip(tooltip("wilder_wild_flowers"))
			.requireRestart()
			.build()
		);
		var wilderWildTrees = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_trees"), config.wilderWildTreeGen)
			.setDefaultValue(defaultConfig.wilderWildTreeGen)
			.setSaveConsumer(newValue -> config.wilderWildTreeGen = newValue)
			.setTooltip(tooltip("wilder_wild_trees"))
			.requireRestart()
			.build()
		);
		var wilderWildBushes = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_bushes"), config.wilderWildBushGen)
			.setDefaultValue(defaultConfig.wilderWildBushGen)
			.setSaveConsumer(newValue -> config.wilderWildBushGen = newValue)
			.setTooltip(tooltip("wilder_wild_bushes"))
			.requireRestart()
			.build()
		);
		var wilderWildCacti = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_cacti"), config.wilderWildCactusGen)
			.setDefaultValue(defaultConfig.wilderWildCactusGen)
			.setSaveConsumer(newValue -> config.wilderWildCactusGen = newValue)
			.setTooltip(tooltip("wilder_wild_cacti"))
			.requireRestart()
			.build()
		);
		var wilderWildMushrooms = category.addEntry(entryBuilder.startBooleanToggle(text("wilder_wild_mushrooms"), config.wilderWildMushroomGen)
			.setDefaultValue(defaultConfig.wilderWildMushroomGen)
			.setSaveConsumer(newValue -> config.wilderWildMushroomGen = newValue)
			.setTooltip(tooltip("wilder_wild_mushrooms"))
			.requireRestart()
			.build()
		);
		var tumbleweed = category.addEntry(entryBuilder.startBooleanToggle(text("tumbleweed_gen"), config.tumbleweed)
			.setDefaultValue(defaultConfig.tumbleweed)
			.setSaveConsumer(newValue -> config.tumbleweed = newValue)
			.setTooltip(tooltip("tumbleweed_gen"))
			.requireRestart()
			.build()
		);
		var algae = category.addEntry(entryBuilder.startBooleanToggle(text("algae_gen"), config.algae)
			.setDefaultValue(defaultConfig.algae)
			.setSaveConsumer(newValue -> config.algae = newValue)
			.setTooltip(tooltip("algae_gen"))
			.requireRestart()
			.build()
		);
		var termite = category.addEntry(entryBuilder.startBooleanToggle(text("termite_gen"), config.termiteGen)
			.setDefaultValue(defaultConfig.termiteGen)
			.setSaveConsumer(newValue -> config.termiteGen = newValue)
			.setTooltip(tooltip("termite_gen"))
			.requireRestart()
			.build()
		);
		var surfaceDecoration = category.addEntry(entryBuilder.startBooleanToggle(text("surface_decoration"), config.surfaceDecoration)
			.setDefaultValue(defaultConfig.surfaceDecoration)
			.setSaveConsumer(newValue -> config.surfaceDecoration = newValue)
			.setTooltip(tooltip("surface_decoration"))
			.requireRestart()
			.build()
		);
		var snowBelowTrees = category.addEntry(entryBuilder.startBooleanToggle(text("snow_below_trees"), config.snowBelowTrees)
			.setDefaultValue(defaultConfig.snowBelowTrees)
			.setSaveConsumer(newValue -> config.snowBelowTrees = newValue)
			.setTooltip(tooltip("snow_below_trees"))
			.requireRestart()
			.build()
		);
		var surfaceTransitions = category.addEntry(entryBuilder.startBooleanToggle(text("surface_transitions"), config.surfaceTransitions)
			.setDefaultValue(defaultConfig.surfaceTransitions)
			.setSaveConsumer(newValue -> config.surfaceTransitions = newValue)
			.setTooltip(tooltip("surface_transitions"))
			.requireRestart()
			.build()
		);
		var newWitchHuts = category.addEntry(entryBuilder.startBooleanToggle(text("new_witch_huts"), config.newWitchHuts)
			.setDefaultValue(defaultConfig.newWitchHuts)
			.setSaveConsumer(newValue -> config.newWitchHuts = newValue)
			.setTooltip(tooltip("new_witch_huts"))
			.requireRestart()
			.build()
		);
	}
}
