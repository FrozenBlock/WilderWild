/*
 * Copyright 2023-2024 FrozenBlock
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
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.tooltip;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WorldgenConfigGui {
	private WorldgenConfigGui() {
		throw new UnsupportedOperationException("WorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = WorldgenConfig.get(true);
		var modifiedConfig = WorldgenConfig.getWithSync();
		Class<? extends WorldgenConfig> clazz = config.getClass();
		Config<? extends WorldgenConfig> configInstance = WorldgenConfig.INSTANCE;

		var defaultConfig = WorldgenConfig.INSTANCE.defaultInstance();
		var biomePlacement = config.biomePlacement;
		var modifiedBiomePlacement = modifiedConfig.biomePlacement;
		var biomes = config.biomeGeneration;
		var modifiedBiomes = modifiedConfig.biomeGeneration;
		category.setBackground(WilderSharedConstants.id("textures/config/worldgen.png"));

		var betaBeaches = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("beta_beaches"), modifiedConfig.betaBeaches)
					.setDefaultValue(defaultConfig.betaBeaches)
					.setSaveConsumer(newValue -> config.betaBeaches = newValue)
					.setTooltip(tooltip("beta_beaches"))
					.build(),
				clazz,
				"betaBeaches",
				configInstance
			)
		);

		var snowUnderMountains = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("snow_under_mountains"), modifiedConfig.snowUnderMountains)
					.setDefaultValue(defaultConfig.snowUnderMountains)
					.setSaveConsumer(newValue -> config.snowUnderMountains = newValue)
					.setTooltip(tooltip("snow_under_mountains"))
					.build(),
				clazz,
				"snowUnderMountains",
				configInstance
			)
		);

		var cypressWetlands = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_cypress_wetlands"), modifiedBiomes.generateCypressWetlands)
				.setDefaultValue(defaultConfig.biomeGeneration.generateCypressWetlands)
				.setSaveConsumer(newValue -> biomes.generateCypressWetlands = newValue)
				.setTooltip(tooltip("generate_cypress_wetlands"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateCypressWetlands",
			configInstance
		);

		var jellyfishCaves = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_jellyfish_caves"), modifiedBiomes.generateJellyfishCaves)
				.setDefaultValue(defaultConfig.biomeGeneration.generateJellyfishCaves)
				.setSaveConsumer(newValue -> biomes.generateJellyfishCaves = newValue)
				.setTooltip(tooltip("generate_jellyfish_caves"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateJellyfishCaves",
			configInstance
		);
		var mixedForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_mixed_forest"), modifiedBiomes.generateMixedForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateMixedForest)
				.setSaveConsumer(newValue -> biomes.generateMixedForest = newValue)
				.setTooltip(tooltip("generate_mixed_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateMixedForest",
			configInstance
		);
		var oasis = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_oasis"), modifiedBiomes.generateOasis)
				.setDefaultValue(defaultConfig.biomeGeneration.generateOasis)
				.setSaveConsumer(newValue -> biomes.generateOasis = newValue)
				.setTooltip(tooltip("generate_oasis"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateOasis",
			configInstance
		);
		var warmRiver = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_warm_river"), modifiedBiomes.generateWarmRiver)
				.setDefaultValue(defaultConfig.biomeGeneration.generateWarmRiver)
				.setSaveConsumer(newValue -> biomes.generateWarmRiver = newValue)
				.setTooltip(tooltip("generate_warm_river"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateWarmRiver",
			configInstance
		);
		var warmBeach = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_warm_beach"), modifiedBiomes.generateWarmBeach)
				.setDefaultValue(defaultConfig.biomeGeneration.generateWarmBeach)
				.setSaveConsumer(newValue -> biomes.generateWarmBeach = newValue)
				.setTooltip(tooltip("generate_warm_beach"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateWarmBeach",
			configInstance
		);
		var birchTaiga = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_birch_taiga"), modifiedBiomes.generateBirchTaiga)
				.setDefaultValue(defaultConfig.biomeGeneration.generateBirchTaiga)
				.setSaveConsumer(newValue -> biomes.generateBirchTaiga = newValue)
				.setTooltip(tooltip("generate_birch_taiga"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateBirchTaiga",
			configInstance
		);
		var oldGrowthBirchTaiga = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_old_growth_birch_taiga"), modifiedBiomes.generateOldGrowthBirchTaiga)
				.setDefaultValue(defaultConfig.biomeGeneration.generateOldGrowthBirchTaiga)
				.setSaveConsumer(newValue -> biomes.generateOldGrowthBirchTaiga = newValue)
				.setTooltip(tooltip("generate_old_growth_birch_taiga"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateOldGrowthBirchTaiga",
			configInstance
		);
		var flowerField = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_flower_field"), modifiedBiomes.generateFlowerField)
				.setDefaultValue(defaultConfig.biomeGeneration.generateFlowerField)
				.setSaveConsumer(newValue -> biomes.generateFlowerField = newValue)
				.setTooltip(tooltip("generate_flower_field"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateFlowerField",
			configInstance
		);
		var aridSavanna = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_arid_savanna"), modifiedBiomes.generateAridSavanna)
				.setDefaultValue(defaultConfig.biomeGeneration.generateAridSavanna)
				.setSaveConsumer(newValue -> biomes.generateAridSavanna = newValue)
				.setTooltip(tooltip("generate_arid_savanna"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateAridSavanna",
			configInstance
		);
		var parchedForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_parched_forest"), modifiedBiomes.generateParchedForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateParchedForest)
				.setSaveConsumer(newValue -> biomes.generateParchedForest = newValue)
				.setTooltip(tooltip("generate_parched_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateParchedForest",
			configInstance
		);
		var aridForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_arid_forest"), modifiedBiomes.generateAridForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateAridForest)
				.setSaveConsumer(newValue -> biomes.generateAridForest = newValue)
				.setTooltip(tooltip("generate_arid_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateAridForest",
			configInstance
		);
		var oldGrowthSnowyTaiga = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_snowy_old_growth_pine_taiga"), modifiedBiomes.generateOldGrowthSnowyTaiga)
				.setDefaultValue(defaultConfig.biomeGeneration.generateOldGrowthSnowyTaiga)
				.setSaveConsumer(newValue -> biomes.generateOldGrowthSnowyTaiga = newValue)
				.setTooltip(tooltip("generate_snowy_old_growth_pine_taiga"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateOldGrowthSnowyTaiga",
			configInstance
		);
		var birchJungle = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_birch_jungle"), modifiedBiomes.generateBirchJungle)
				.setDefaultValue(defaultConfig.biomeGeneration.generateBirchJungle)
				.setSaveConsumer(newValue -> biomes.generateBirchJungle = newValue)
				.setTooltip(tooltip("generate_birch_jungle"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateBirchJungle",
			configInstance
		);
		var sparseBirchJungle = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_sparse_birch_jungle"), modifiedBiomes.generateSparseBirchJungle)
				.setDefaultValue(defaultConfig.biomeGeneration.generateSparseBirchJungle)
				.setSaveConsumer(newValue -> biomes.generateSparseBirchJungle = newValue)
				.setTooltip(tooltip("generate_sparse_birch_jungle"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateSparseBirchJungle",
			configInstance
		);
		var oldGrowthDarkForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_old_growth_dark_forest"), modifiedBiomes.generateOldGrowthDarkForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateOldGrowthDarkForest)
				.setSaveConsumer(newValue -> biomes.generateOldGrowthDarkForest = newValue)
				.setTooltip(tooltip("generate_old_growth_dark_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateOldGrowthDarkForest",
			configInstance
		);
		var darkBirchForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_dark_birch_forest"), modifiedBiomes.generateDarkBirchForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateDarkBirchForest)
				.setSaveConsumer(newValue -> biomes.generateDarkBirchForest = newValue)
				.setTooltip(tooltip("generate_dark_birch_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateDarkBirchForest",
			configInstance
		);
		var semiBirchForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_semi_birch_forest"), modifiedBiomes.generateSemiBirchForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateSemiBirchForest)
				.setSaveConsumer(newValue -> biomes.generateSemiBirchForest = newValue)
				.setTooltip(tooltip("generate_semi_birch_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateSemiBirchForest",
			configInstance
		);
		var temperateRainforest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_temperate_rainforest"), modifiedBiomes.generateTemperateRainforest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateTemperateRainforest)
				.setSaveConsumer(newValue -> biomes.generateTemperateRainforest = newValue)
				.setTooltip(tooltip("generate_temperate_rainforest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateTemperateRainforest",
			configInstance
		);
		var rainforest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_rainforest"), modifiedBiomes.generateRainforest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateRainforest)
				.setSaveConsumer(newValue -> biomes.generateRainforest = newValue)
				.setTooltip(tooltip("generate_rainforest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateRainforest",
			configInstance
		);
		var darkTaiga = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_dark_taiga"), modifiedBiomes.generateDarkTaiga)
				.setDefaultValue(defaultConfig.biomeGeneration.generateDarkTaiga)
				.setSaveConsumer(newValue -> biomes.generateDarkTaiga = newValue)
				.setTooltip(tooltip("generate_dark_taiga"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateDarkTaiga",
			configInstance
		);
		var dyingForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_dying_forest"), modifiedBiomes.generateDyingForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateDyingForest)
				.setSaveConsumer(newValue -> biomes.generateDyingForest = newValue)
				.setTooltip(tooltip("generate_dying_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateDyingForest",
			configInstance
		);
		var snowyDyingForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_snowy_dying_forest"), modifiedBiomes.generateSnowyDyingForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateSnowyDyingForest)
				.setSaveConsumer(newValue -> biomes.generateSnowyDyingForest = newValue)
				.setTooltip(tooltip("generate_snowy_dying_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateSnowyDyingForest",
			configInstance
		);
		var dyingMixedForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_dying_mixed_forest"), modifiedBiomes.generateDyingMixedForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateDyingMixedForest)
				.setSaveConsumer(newValue -> biomes.generateDyingMixedForest = newValue)
				.setTooltip(tooltip("generate_dying_mixed_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateDyingMixedForest",
			configInstance
		);
		var snowyDyingMixedForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_snowy_dying_mixed_forest"), modifiedBiomes.generateSnowyDyingMixedForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateSnowyDyingMixedForest)
				.setSaveConsumer(newValue -> biomes.generateSnowyDyingMixedForest = newValue)
				.setTooltip(tooltip("generate_snowy_dying_mixed_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateSnowyDyingMixedForest",
			configInstance
		);
		var magmaticCaves = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_magmatic_caves"), modifiedBiomes.generateMagmaticCaves)
				.setDefaultValue(defaultConfig.biomeGeneration.generateMagmaticCaves)
				.setSaveConsumer(newValue -> biomes.generateMagmaticCaves = newValue)
				.setTooltip(tooltip("generate_magmatic_caves"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateMagmaticCaves",
			configInstance
		);
		var frozenCaves = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_frozen_caves"), modifiedBiomes.generateFrozenCaves)
				.setDefaultValue(defaultConfig.biomeGeneration.generateFrozenCaves)
				.setSaveConsumer(newValue -> biomes.generateFrozenCaves = newValue)
				.setTooltip(tooltip("generate_frozen_caves"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateFrozenCaves",
			configInstance
		);

		var biomeGenerationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_generation"),
			false,
			tooltip("biome_generation"),
			aridForest, aridSavanna, birchJungle, birchTaiga, cypressWetlands, darkBirchForest, darkTaiga, dyingForest, dyingMixedForest, flowerField, frozenCaves,
			jellyfishCaves, magmaticCaves, mixedForest, oasis, oldGrowthBirchTaiga, oldGrowthDarkForest, oldGrowthSnowyTaiga, parchedForest, rainforest, semiBirchForest,
			snowyDyingForest, snowyDyingMixedForest, sparseBirchJungle, temperateRainforest, warmBeach, warmRiver
		);

		var cherryGrove = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("modify_cherry_grove_placement"), modifiedBiomePlacement.modifyCherryGrovePlacement)
				.setDefaultValue(defaultConfig.biomePlacement.modifyCherryGrovePlacement)
				.setSaveConsumer(newValue -> biomePlacement.modifyCherryGrovePlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_cherry_grove_placement"))
				.requireRestart()
				.build(),
			biomePlacement.getClass(),
			"modifyCherryGrovePlacement",
			configInstance
		);
		var jungle = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("modify_jungle_placement"), modifiedBiomePlacement.modifyJunglePlacement)
				.setDefaultValue(defaultConfig.biomePlacement.modifyJunglePlacement)
				.setSaveConsumer(newValue -> biomePlacement.modifyJunglePlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_jungle_placement"))
				.requireRestart()
				.build(),
			biomePlacement.getClass(),
			"modifyJunglePlacement",
			configInstance
		);
		var mangroveSwamp = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("modify_mangrove_swamp_placement"), modifiedBiomePlacement.modifyMangroveSwampPlacement)
				.setDefaultValue(defaultConfig.biomePlacement.modifyMangroveSwampPlacement)
				.setSaveConsumer(newValue -> biomePlacement.modifyMangroveSwampPlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_mangrove_swamp_placement"))
				.requireRestart()
				.build(),
			biomePlacement.getClass(),
			"modifyMangroveSwampPlacement",
			configInstance
		);
		var stonyShore = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("modify_stony_shore_placement"), modifiedBiomePlacement.modifyStonyShorePlacement)
				.setDefaultValue(defaultConfig.biomePlacement.modifyStonyShorePlacement)
				.setSaveConsumer(newValue -> biomePlacement.modifyStonyShorePlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_stony_shore_placement"))
				.requireRestart()
				.build(),
			biomePlacement.getClass(),
			"modifyStonyShorePlacement",
			configInstance
		);
		var swamp = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("modify_swamp_placement"), modifiedBiomePlacement.modifySwampPlacement)
				.setDefaultValue(defaultConfig.biomePlacement.modifySwampPlacement)
				.setSaveConsumer(newValue -> biomePlacement.modifySwampPlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_swamp_placement"))
				.requireRestart()
				.build(),
			biomePlacement.getClass(),
			"modifySwampPlacement",
			configInstance
		);
		var windsweptSavanna = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("modify_windswept_savanna_placement"), modifiedBiomePlacement.modifyWindsweptSavannaPlacement)
				.setDefaultValue(defaultConfig.biomePlacement.modifyWindsweptSavannaPlacement)
				.setSaveConsumer(newValue -> biomePlacement.modifyWindsweptSavannaPlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement." + bool))
				.setTooltip(tooltip("modify_windswept_savanna_placement"))
				.requireRestart()
				.build(),
			biomePlacement.getClass(),
			"modifyWindsweptSavannaPlacement",
			configInstance
		);

		var biomePlacementCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_placement"),
			false,
			tooltip("biome_placement"),
			cherryGrove, jungle, mangroveSwamp, stonyShore, swamp, windsweptSavanna
		);

		var fallenTrees = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("fallen_trees"), modifiedConfig.fallenTrees)
					.setDefaultValue(defaultConfig.fallenTrees)
					.setSaveConsumer(newValue -> config.fallenTrees = newValue)
					.setTooltip(tooltip("fallen_trees"))
					.requireRestart()
					.build(),
				clazz,
				"fallenTrees",
				configInstance
			)
		);
		var snappedTrees = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("snapped_trees"), modifiedConfig.snappedTrees)
					.setDefaultValue(defaultConfig.snappedTrees)
					.setSaveConsumer(newValue -> config.snappedTrees = newValue)
					.setTooltip(tooltip("snapped_trees"))
					.requireRestart()
					.build(),
				clazz,
				"snappedTrees",
				configInstance
			));

		var bushGeneration = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("bush_generation"), modifiedConfig.bushGeneration)
					.setDefaultValue(defaultConfig.bushGeneration)
					.setSaveConsumer(newValue -> config.bushGeneration = newValue)
					.setTooltip(tooltip("bush_generation"))
					.requireRestart()
					.build(),
				clazz,
				"bushGeneration",
				configInstance
			)
		);
		var cactusGeneration = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("cactus_generation"), modifiedConfig.cactusGeneration)
					.setDefaultValue(defaultConfig.cactusGeneration)
					.setSaveConsumer(newValue -> config.cactusGeneration = newValue)
					.setTooltip(tooltip("cactus_generation"))
					.requireRestart()
					.build(),
				clazz,
				"cactusGeneration",
				configInstance
			)
		);
		var flowerGeneration = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("flower_generation"), modifiedConfig.flowerGeneration)
					.setDefaultValue(defaultConfig.flowerGeneration)
					.setSaveConsumer(newValue -> config.flowerGeneration = newValue)
					.setTooltip(tooltip("flower_generation"))
					.requireRestart()
					.build(),
				clazz,
				"flowerGeneration",
				configInstance
			)
		);
		var grassGeneration = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("grass_generation"), modifiedConfig.grassGeneration)
					.setDefaultValue(defaultConfig.grassGeneration)
					.setSaveConsumer(newValue -> config.grassGeneration = newValue)
					.setTooltip(tooltip("grass_generation"))
					.requireRestart()
					.build(),
				clazz,
				"grassGeneration",
				configInstance
			)
		);
		var mushroomGeneration = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("mushroom_generation"), modifiedConfig.mushroomGeneration)
					.setDefaultValue(defaultConfig.mushroomGeneration)
					.setSaveConsumer(newValue -> config.mushroomGeneration = newValue)
					.setTooltip(tooltip("mushroom_generation"))
					.requireRestart()
					.build(),
				clazz,
				"mushroomGeneration",
				configInstance
			)
		);
		var treeGeneration = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("tree_generation"), modifiedConfig.treeGeneration)
					.setDefaultValue(defaultConfig.treeGeneration)
					.setSaveConsumer(newValue -> config.treeGeneration = newValue)
					.setTooltip(tooltip("tree_generation"))
					.requireRestart()
					.build(),
				clazz,
				"treeGeneration",
				configInstance
			)
		);

		var algae = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("algae_generation"), modifiedConfig.algae)
					.setDefaultValue(defaultConfig.algae)
					.setSaveConsumer(newValue -> config.algae = newValue)
					.setTooltip(tooltip("algae_generation"))
					.requireRestart()
					.build(),
				clazz,
				"algae",
				configInstance
			)
		);
		var tumbleweed = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("tumbleweed_generation"), modifiedConfig.tumbleweed)
					.setDefaultValue(defaultConfig.tumbleweed)
					.setSaveConsumer(newValue -> config.tumbleweed = newValue)
					.setTooltip(tooltip("tumbleweed_generation"))
					.requireRestart()
					.build(),
				clazz,
				"tumbleweed",
				configInstance
			)
		);
		var termite = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("termite_generation"), modifiedConfig.termiteGen)
					.setDefaultValue(defaultConfig.termiteGen)
					.setSaveConsumer(newValue -> config.termiteGen = newValue)
					.setTooltip(tooltip("termite_generation"))
					.requireRestart()
					.build(),
				clazz,
				"termiteGen",
				configInstance
			)
		);
		var netherGeyserGen = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("nether_geyser_generation"), modifiedConfig.netherGeyserGen)
					.setDefaultValue(defaultConfig.netherGeyserGen)
					.setSaveConsumer(newValue -> config.netherGeyserGen = newValue)
					.setTooltip(tooltip("nether_geyser_generation"))
					.requireRestart()
					.build(),
				clazz,
				"netherGeyserGen",
				configInstance
			)
		);
		var surfaceDecoration = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("surface_decoration"), modifiedConfig.surfaceDecoration)
					.setDefaultValue(defaultConfig.surfaceDecoration)
					.setSaveConsumer(newValue -> config.surfaceDecoration = newValue)
					.setTooltip(tooltip("surface_decoration"))
					.requireRestart()
					.build(),
				clazz,
				"surfaceDecoration",
				configInstance
			)
		);
		var snowBelowTrees = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("snow_below_trees"), modifiedConfig.snowBelowTrees)
					.setDefaultValue(defaultConfig.snowBelowTrees)
					.setSaveConsumer(newValue -> config.snowBelowTrees = newValue)
					.setTooltip(tooltip("snow_below_trees"))
					.requireRestart()
					.build(),
				clazz,
				"snowBelowTrees",
				configInstance
			)
		);
		var surfaceTransitions = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("surface_transitions"), modifiedConfig.surfaceTransitions)
					.setDefaultValue(defaultConfig.surfaceTransitions)
					.setSaveConsumer(newValue -> config.surfaceTransitions = newValue)
					.setTooltip(tooltip("surface_transitions"))
					.requireRestart()
					.build(),
				clazz,
				"surfaceTransitions",
				configInstance
			)
		);
		var newWitchHuts = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("new_witch_huts"), modifiedConfig.newWitchHuts)
					.setDefaultValue(defaultConfig.newWitchHuts)
					.setSaveConsumer(newValue -> config.newWitchHuts = newValue)
					.setTooltip(tooltip("new_witch_huts"))
					.build(),
				clazz,
				"newWitchHuts",
				configInstance
			)
		);
	}
}
