/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.Requirement;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;

@Environment(EnvType.CLIENT)
public final class WWWorldgenConfigGui {

	private WWWorldgenConfigGui() {
		throw new UnsupportedOperationException("WWWorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		final var config = WWWorldgenConfig.get(true);
		final var modifiedConfig = WWWorldgenConfig.getWithSync();
		final Class<? extends WWWorldgenConfig> clazz = config.getClass();
		final Config<? extends WWWorldgenConfig> configInstance = WWWorldgenConfig.INSTANCE;

		final var defaultConfig = WWWorldgenConfig.INSTANCE.defaultInstance();
		final var biomePlacement = config.biomePlacement;
		final var modifiedBiomePlacement = modifiedConfig.biomePlacement;
		final var biomes = config.biomeGeneration;
		final var modifiedBiomes = modifiedConfig.biomeGeneration;

		var betaBeaches = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("beta_beaches"), modifiedConfig.betaBeaches)
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
				builder.startBooleanToggle(text("snow_under_mountains"), modifiedConfig.snowUnderMountains)
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
			builder.startBooleanToggle(text("generate_cypress_wetlands"), modifiedBiomes.generateCypressWetlands)
				.setDefaultValue(defaultConfig.biomeGeneration.generateCypressWetlands)
				.setSaveConsumer(newValue -> biomes.generateCypressWetlands = newValue)
				.setTooltip(tooltip("generate_cypress_wetlands"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateCypressWetlands",
			configInstance
		);
		var mesogleaCaves = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("generate_mesoglea_caves"), modifiedBiomes.generateMesogleaCaves)
				.setDefaultValue(defaultConfig.biomeGeneration.generateMesogleaCaves)
				.setSaveConsumer(newValue -> biomes.generateMesogleaCaves = newValue)
				.setTooltip(tooltip("generate_mesoglea_caves"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateMesogleaCaves",
			configInstance
		);
		var mixedForest = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("generate_mixed_forest"), modifiedBiomes.generateMixedForest)
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
			builder.startBooleanToggle(text("generate_oasis"), modifiedBiomes.generateOasis)
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
			builder.startBooleanToggle(text("generate_warm_river"), modifiedBiomes.generateWarmRiver)
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
			builder.startBooleanToggle(text("generate_warm_beach"), modifiedBiomes.generateWarmBeach)
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
			builder.startBooleanToggle(text("generate_birch_taiga"), modifiedBiomes.generateBirchTaiga)
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
			builder.startBooleanToggle(text("generate_old_growth_birch_taiga"), modifiedBiomes.generateOldGrowthBirchTaiga)
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
			builder.startBooleanToggle(text("generate_flower_field"), modifiedBiomes.generateFlowerField)
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
			builder.startBooleanToggle(text("generate_arid_savanna"), modifiedBiomes.generateAridSavanna)
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
			builder.startBooleanToggle(text("generate_parched_forest"), modifiedBiomes.generateParchedForest)
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
			builder.startBooleanToggle(text("generate_arid_forest"), modifiedBiomes.generateAridForest)
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
			builder.startBooleanToggle(text("generate_snowy_old_growth_pine_taiga"), modifiedBiomes.generateOldGrowthSnowyTaiga)
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
			builder.startBooleanToggle(text("generate_birch_jungle"), modifiedBiomes.generateBirchJungle)
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
			builder.startBooleanToggle(text("generate_sparse_birch_jungle"), modifiedBiomes.generateSparseBirchJungle)
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
			builder.startBooleanToggle(text("generate_old_growth_dark_forest"), modifiedBiomes.generateOldGrowthDarkForest)
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
			builder.startBooleanToggle(text("generate_dark_birch_forest"), modifiedBiomes.generateDarkBirchForest)
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
			builder.startBooleanToggle(text("generate_semi_birch_forest"), modifiedBiomes.generateSemiBirchForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateSemiBirchForest)
				.setSaveConsumer(newValue -> biomes.generateSemiBirchForest = newValue)
				.setTooltip(tooltip("generate_semi_birch_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateSemiBirchForest",
			configInstance
		);
		var sparseForest = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("generate_sparse_forest"), modifiedBiomes.generateSparseForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateSparseForest)
				.setSaveConsumer(newValue -> biomes.generateSparseForest = newValue)
				.setTooltip(tooltip("generate_sparse_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateSparseForest",
			configInstance
		);
		var temperateRainforest = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("generate_temperate_rainforest"), modifiedBiomes.generateTemperateRainforest)
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
			builder.startBooleanToggle(text("generate_rainforest"), modifiedBiomes.generateRainforest)
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
			builder.startBooleanToggle(text("generate_dark_taiga"), modifiedBiomes.generateDarkTaiga)
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
			builder.startBooleanToggle(text("generate_dying_forest"), modifiedBiomes.generateDyingForest)
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
			builder.startBooleanToggle(text("generate_snowy_dying_forest"), modifiedBiomes.generateSnowyDyingForest)
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
			builder.startBooleanToggle(text("generate_dying_mixed_forest"), modifiedBiomes.generateDyingMixedForest)
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
			builder.startBooleanToggle(text("generate_snowy_dying_mixed_forest"), modifiedBiomes.generateSnowyDyingMixedForest)
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
			builder.startBooleanToggle(text("generate_magmatic_caves"), modifiedBiomes.generateMagmaticCaves)
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
			builder.startBooleanToggle(text("generate_frozen_caves"), modifiedBiomes.generateFrozenCaves)
				.setDefaultValue(defaultConfig.biomeGeneration.generateFrozenCaves)
				.setSaveConsumer(newValue -> biomes.generateFrozenCaves = newValue)
				.setTooltip(tooltip("generate_frozen_caves"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateFrozenCaves",
			configInstance
		);
		var mapleForest = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("generate_maple_forest"), modifiedBiomes.generateMapleForest)
				.setDefaultValue(defaultConfig.biomeGeneration.generateMapleForest)
				.setSaveConsumer(newValue -> biomes.generateMapleForest = newValue)
				.setTooltip(tooltip("generate_maple_forest"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateMapleForest",
			configInstance
		);
		var tundra = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("generate_tundra"), modifiedBiomes.generateTundra)
				.setDefaultValue(defaultConfig.biomeGeneration.generateTundra)
				.setSaveConsumer(newValue -> biomes.generateTundra = newValue)
				.setTooltip(tooltip("generate_tundra"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateTundra",
			configInstance
		);

		var biomeGenerationCategory = FrozenClothConfig.createSubCategory(builder, category, text("biome_generation"),
			false,
			tooltip("biome_generation"),
			aridForest, aridSavanna, birchJungle, birchTaiga, cypressWetlands, darkBirchForest, darkTaiga, dyingForest, dyingMixedForest, flowerField,
			frozenCaves, magmaticCaves, mapleForest, mesogleaCaves, mixedForest, oasis, oldGrowthBirchTaiga, oldGrowthDarkForest, oldGrowthSnowyTaiga, parchedForest,
			rainforest, semiBirchForest, snowyDyingForest, snowyDyingMixedForest, sparseBirchJungle, sparseForest, temperateRainforest, tundra, warmBeach, warmRiver
		);

		var cherryGrove = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("modify_cherry_grove_placement"), modifiedBiomePlacement.modifyCherryGrovePlacement)
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
			builder.startBooleanToggle(text("modify_jungle_placement"), modifiedBiomePlacement.modifyJunglePlacement)
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
			builder.startBooleanToggle(text("modify_mangrove_swamp_placement"), modifiedBiomePlacement.modifyMangroveSwampPlacement)
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
			builder.startBooleanToggle(text("modify_stony_shore_placement"), modifiedBiomePlacement.modifyStonyShorePlacement)
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
		var modifyTundraPlacement = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("modify_tundra_placement"), modifiedBiomePlacement.modifyTundraPlacement)
				.setDefaultValue(defaultConfig.biomePlacement.modifyTundraPlacement)
				.setSaveConsumer(newValue -> biomePlacement.modifyTundraPlacement = newValue)
				.setYesNoTextSupplier(bool -> text("biome_placement.tundra." + bool))
				.setTooltip(tooltip("modify_tundra_placement"))
				.setDisplayRequirement(Requirement.isTrue(() -> WWWorldgenConfig.get().biomeGeneration.generateTundra))
				.requireRestart()
				.build(),
			biomePlacement.getClass(),
			"modifyTundraPlacement",
			configInstance
		);
		var swamp = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("modify_swamp_placement"), modifiedBiomePlacement.modifySwampPlacement)
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
			builder.startBooleanToggle(text("modify_windswept_savanna_placement"), modifiedBiomePlacement.modifyWindsweptSavannaPlacement)
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

		var biomePlacementCategory = FrozenClothConfig.createSubCategory(builder, category, text("biome_placement"),
			false,
			tooltip("biome_placement"),
			cherryGrove, jungle, mangroveSwamp, stonyShore, swamp, windsweptSavanna, modifyTundraPlacement
		);

		var tree = config.treeGeneration;
		var modifiedTree = modifiedConfig.treeGeneration;
		var defaultTree = defaultConfig.treeGeneration;
		var treeClazz = tree.getClass();

		var treeGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("tree_generation"), modifiedTree.treeGeneration)
				.setDefaultValue(defaultTree.treeGeneration)
				.setSaveConsumer(newValue -> tree.treeGeneration = newValue)
				.setTooltip(tooltip("tree_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"treeGeneration",
			configInstance
		);
		var fallenTrees = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("fallen_trees"), modifiedTree.fallenTrees)
				.setDefaultValue(defaultTree.fallenTrees)
				.setSaveConsumer(newValue -> tree.fallenTrees = newValue)
				.setTooltip(tooltip("fallen_trees"))
				.requireRestart()
				.build(),
			treeClazz,
			"fallenTrees",
			configInstance
		);
		var hollowedFallenTrees = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("hollowed_fallen_trees"), modifiedTree.hollowedFallenTrees)
				.setDefaultValue(defaultTree.hollowedFallenTrees)
				.setSaveConsumer(newValue -> tree.hollowedFallenTrees = newValue)
				.setTooltip(tooltip("hollowed_fallen_trees"))
				.build(),
			treeClazz,
			"hollowedFallenTrees",
			configInstance
		);
		var snappedTrees = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("snapped_trees"), modifiedTree.snappedTrees)
				.setDefaultValue(defaultTree.snappedTrees)
				.setSaveConsumer(newValue -> tree.snappedTrees = newValue)
				.setTooltip(tooltip("snapped_trees"))
				.requireRestart()
				.build(),
			treeClazz,
			"snappedTrees",
			configInstance
		);
		var baobab = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("baobab_generation"), modifiedTree.baobab)
				.setDefaultValue(defaultTree.baobab)
				.setSaveConsumer(newValue -> tree.baobab = newValue)
				.setTooltip(tooltip("baobab_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"baobab",
			configInstance
		);
		var palm = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("palm_generation"), modifiedTree.palm)
				.setDefaultValue(defaultTree.palm)
				.setSaveConsumer(newValue -> tree.palm = newValue)
				.setTooltip(tooltip("palm_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"palm",
			configInstance
		);
		var willow = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("willow_generation"), modifiedTree.willow)
				.setDefaultValue(defaultTree.willow)
				.setSaveConsumer(newValue -> tree.willow = newValue)
				.setTooltip(tooltip("willow_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"willow",
			configInstance
		);
		var birchBranches = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("birch_branches"), modifiedTree.birchBranches)
				.setDefaultValue(defaultTree.birchBranches)
				.setSaveConsumer(newValue -> tree.birchBranches = newValue)
				.setTooltip(tooltip("birch_branches"))
				.build(),
			treeClazz,
			"birchBranches",
			configInstance
		);
		var oakBranches = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("oak_branches"), modifiedTree.oakBranches)
				.setDefaultValue(defaultTree.oakBranches)
				.setSaveConsumer(newValue -> tree.oakBranches = newValue)
				.setTooltip(tooltip("oak_branches"))
				.build(),
			treeClazz,
			"oakBranches",
			configInstance
		);
		var darkOakBranches = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("dark_oak_branches"), modifiedTree.darkOakBranches)
				.setDefaultValue(defaultTree.darkOakBranches)
				.setSaveConsumer(newValue -> tree.darkOakBranches = newValue)
				.setTooltip(tooltip("dark_oak_branches"))
				.build(),
			treeClazz,
			"darkOakBranches",
			configInstance
		);
		var paleOakBranches = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("pale_oak_branches"), modifiedTree.paleOakBranches)
				.setDefaultValue(defaultTree.paleOakBranches)
				.setSaveConsumer(newValue -> tree.paleOakBranches = newValue)
				.setTooltip(tooltip("pale_oak_branches"))
				.build(),
			treeClazz,
			"paleOakBranches",
			configInstance
		);
		var acaciaLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("acacia_leaf_litter_generation"), modifiedConfig.treeGeneration.acaciaLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.acaciaLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.acaciaLeafLitter = newValue)
				.setTooltip(tooltip("acacia_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"acaciaLeafLitter",
			configInstance
		);
		var azaleaLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("azalea_leaf_litter_generation"), modifiedConfig.treeGeneration.azaleaLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.azaleaLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.azaleaLeafLitter = newValue)
				.setTooltip(tooltip("azalea_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"azaleaLeafLitter",
			configInstance
		);
		var baobabLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("baobab_leaf_litter_generation"), modifiedConfig.treeGeneration.baobabLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.baobabLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.baobabLeafLitter = newValue)
				.setTooltip(tooltip("baobab_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"baobabLeafLitter",
			configInstance
		);
		var birchLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("birch_leaf_litter_generation"), modifiedConfig.treeGeneration.birchLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.birchLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.birchLeafLitter = newValue)
				.setTooltip(tooltip("birch_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"birchLeafLitter",
			configInstance
		);
		var cherryLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("cherry_leaf_litter_generation"), modifiedConfig.treeGeneration.cherryLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.cherryLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.cherryLeafLitter = newValue)
				.setTooltip(tooltip("cherry_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"cherryLeafLitter",
			configInstance
		);
		var cypressLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("cypress_leaf_litter_generation"), modifiedConfig.treeGeneration.cypressLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.cypressLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.cypressLeafLitter = newValue)
				.setTooltip(tooltip("cypress_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"cypressLeafLitter",
			configInstance
		);
		var darkOakLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("dark_oak_leaf_litter_generation"), modifiedConfig.treeGeneration.darkOakLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.darkOakLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.darkOakLeafLitter = newValue)
				.setTooltip(tooltip("dark_oak_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"darkOakLeafLitter",
			configInstance
		);
		var jungleLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("jungle_leaf_litter_generation"), modifiedConfig.treeGeneration.jungleLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.jungleLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.jungleLeafLitter = newValue)
				.setTooltip(tooltip("jungle_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"jungleLeafLitter",
			configInstance
		);
		var mangroveLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("mangrove_leaf_litter_generation"), modifiedConfig.treeGeneration.mangroveLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.mangroveLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.mangroveLeafLitter = newValue)
				.setTooltip(tooltip("mangrove_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"mangroveLeafLitter",
			configInstance
		);
		var oakLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("oak_leaf_litter_generation"), modifiedConfig.treeGeneration.oakLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.oakLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.oakLeafLitter = newValue)
				.setTooltip(tooltip("oak_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"oakLeafLitter",
			configInstance
		);
		var paleOakLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("pale_oak_leaf_litter_generation"), modifiedConfig.treeGeneration.paleOakLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.paleOakLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.paleOakLeafLitter = newValue)
				.setTooltip(tooltip("pale_oak_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"paleOakLeafLitter",
			configInstance
		);
		var palmFrondLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("palm_frond_litter_generation"), modifiedConfig.treeGeneration.palmFrondLitter)
				.setDefaultValue(defaultConfig.treeGeneration.palmFrondLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.palmFrondLitter = newValue)
				.setTooltip(tooltip("palm_frond_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"palmFrondLitter",
			configInstance
		);
		var spruceLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spruce_leaf_litter_generation"), modifiedConfig.treeGeneration.spruceLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.spruceLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.spruceLeafLitter = newValue)
				.setTooltip(tooltip("spruce_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"spruceLeafLitter",
			configInstance
		);
		var willowLeafLitter = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("willow_leaf_litter_generation"), modifiedConfig.treeGeneration.willowLeafLitter)
				.setDefaultValue(defaultConfig.treeGeneration.willowLeafLitter)
				.setSaveConsumer(newValue -> config.treeGeneration.willowLeafLitter = newValue)
				.setTooltip(tooltip("willow_leaf_litter_generation"))
				.requireRestart()
				.build(),
			treeClazz,
			"willowLeafLitter",
			configInstance
		);

		var treeGenerationCategory = FrozenClothConfig.createSubCategory(builder, category, text("tree_generation_category"),
			false,
			tooltip("tree_generation_category"),
			treeGeneration, fallenTrees, hollowedFallenTrees, snappedTrees,
			baobab, palm, willow,
			birchBranches, oakBranches, darkOakBranches, paleOakBranches, acaciaLeafLitter, azaleaLeafLitter, baobabLeafLitter, birchLeafLitter,
			cherryLeafLitter, cypressLeafLitter, darkOakLeafLitter, jungleLeafLitter, mangroveLeafLitter, oakLeafLitter, paleOakLeafLitter,
			palmFrondLitter, spruceLeafLitter, willowLeafLitter
		);

		var vegetation = config.vegetation;
		var modifiedVegetation = modifiedConfig.vegetation;
		var defaultVegetation = defaultConfig.vegetation;
		var vegetationClazz = vegetation.getClass();

		var shrubGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("shrub_generation"), modifiedVegetation.shrubGeneration)
				.setDefaultValue(defaultVegetation.shrubGeneration)
				.setSaveConsumer(newValue -> vegetation.shrubGeneration = newValue)
				.setTooltip(tooltip("shrub_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"shrubGeneration",
			configInstance
		);
		var cactusGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("cactus_generation"), modifiedVegetation.cactusGeneration)
				.setDefaultValue(defaultVegetation.cactusGeneration)
				.setSaveConsumer(newValue -> vegetation.cactusGeneration = newValue)
				.setTooltip(tooltip("cactus_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"cactusGeneration",
			configInstance
		);
		var flowerGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("flower_generation"), modifiedVegetation.flowerGeneration)
				.setDefaultValue(defaultVegetation.flowerGeneration)
				.setSaveConsumer(newValue -> vegetation.flowerGeneration = newValue)
				.setTooltip(tooltip("flower_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"flowerGeneration",
			configInstance
		);
		var grassGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("grass_generation"), modifiedVegetation.grassGeneration)
				.setDefaultValue(defaultVegetation.grassGeneration)
				.setSaveConsumer(newValue -> vegetation.grassGeneration = newValue)
				.setTooltip(tooltip("grass_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"grassGeneration",
			configInstance
		);
		var dryGrassGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("dry_grass_generation"), modifiedVegetation.dryGrassGeneration)
				.setDefaultValue(defaultVegetation.dryGrassGeneration)
				.setSaveConsumer(newValue -> vegetation.dryGrassGeneration = newValue)
				.setTooltip(tooltip("dry_grass_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"dryGrassGeneration",
			configInstance
		);
		var shelfFungiGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("shelf_fungi_generation"), modifiedVegetation.shelfFungiGeneration)
				.setDefaultValue(defaultVegetation.shelfFungiGeneration)
				.setSaveConsumer(newValue -> vegetation.shelfFungiGeneration = newValue)
				.setTooltip(tooltip("shelf_fungi_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"shelfFungiGeneration",
			configInstance
		);
		var mushroomGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("mushroom_generation"), modifiedVegetation.mushroomGeneration)
				.setDefaultValue(defaultVegetation.mushroomGeneration)
				.setSaveConsumer(newValue -> vegetation.mushroomGeneration = newValue)
				.setTooltip(tooltip("mushroom_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"mushroomGeneration",
			configInstance
		);
		var paleMushroomGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("pale_mushroom_generation"), modifiedVegetation.paleMushroomGeneration)
				.setDefaultValue(defaultVegetation.paleMushroomGeneration)
				.setSaveConsumer(newValue -> vegetation.paleMushroomGeneration = newValue)
				.setTooltip(tooltip("pale_mushroom_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"paleMushroomGeneration",
			configInstance
		);
		var pollen = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("pollen_generation"), modifiedVegetation.pollen)
				.setDefaultValue(defaultVegetation.pollen)
				.setSaveConsumer(newValue -> vegetation.pollen = newValue)
				.setTooltip(tooltip("pollen_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"pollen",
			configInstance
		);
		var tumbleweed = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("tumbleweed_generation"), modifiedVegetation.tumbleweed)
				.setDefaultValue(defaultVegetation.tumbleweed)
				.setSaveConsumer(newValue -> vegetation.tumbleweed = newValue)
				.setTooltip(tooltip("tumbleweed_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"tumbleweed",
			configInstance
		);
		var fireflyBushGen = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("firefly_bush_generation"), modifiedConfig.vegetation.fireflyBushGen)
				.setDefaultValue(defaultConfig.vegetation.fireflyBushGen)
				.setSaveConsumer(newValue -> config.vegetation.fireflyBushGen = newValue)
				.setTooltip(tooltip("firefly_bush_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"fireflyBushGen",
			configInstance
		);
		var pumpkin = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("pumpkin_generation"), modifiedVegetation.pumpkin)
				.setDefaultValue(defaultVegetation.pumpkin)
				.setSaveConsumer(newValue -> vegetation.pumpkin = newValue)
				.setTooltip(tooltip("pumpkin_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"pumpkin",
			configInstance
		);

		var vegetationCategory = FrozenClothConfig.createSubCategory(builder, category, text("vegetation"),
			false,
			tooltip("vegetation"),
			grassGeneration, dryGrassGeneration, flowerGeneration, shrubGeneration, cactusGeneration, shelfFungiGeneration, mushroomGeneration, paleMushroomGeneration,
			fireflyBushGen, pollen, pumpkin, tumbleweed
		);

		var surfaceDecoration = config.surfaceDecoration;
		var modifiedSurfaceDecoration = modifiedConfig.surfaceDecoration;
		var defaultSurfaceDecoration = defaultConfig.surfaceDecoration;
		var surfaceDecorationClazz = surfaceDecoration.getClass();

		var coarseDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("coarse_decoration"), modifiedSurfaceDecoration.coarseDecoration)
				.setDefaultValue(defaultSurfaceDecoration.coarseDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.coarseDecoration = newValue)
				.setTooltip(tooltip("coarse_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"coarseDecoration",
			configInstance
		);
		var gravelDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("gravel_decoration"), modifiedSurfaceDecoration.gravelDecoration)
				.setDefaultValue(defaultSurfaceDecoration.gravelDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.gravelDecoration = newValue)
				.setTooltip(tooltip("gravel_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"gravelDecoration",
			configInstance
		);
		var mudDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("mud_decoration"), modifiedSurfaceDecoration.mudDecoration)
				.setDefaultValue(defaultSurfaceDecoration.mudDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.mudDecoration = newValue)
				.setTooltip(tooltip("mud_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"mudDecoration",
			configInstance
		);
		var packedMudDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("packed_mud_decoration"), modifiedSurfaceDecoration.packedMudDecoration)
				.setDefaultValue(defaultSurfaceDecoration.packedMudDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.packedMudDecoration = newValue)
				.setTooltip(tooltip("packed_mud_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"packedMudDecoration",
			configInstance
		);
		var stoneDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("stone_decoration"), modifiedSurfaceDecoration.stoneDecoration)
				.setDefaultValue(defaultSurfaceDecoration.stoneDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.stoneDecoration = newValue)
				.setTooltip(tooltip("stone_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"stoneDecoration",
			configInstance
		);
		var mossDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("moss_decoration"), modifiedSurfaceDecoration.mossDecoration)
				.setDefaultValue(defaultSurfaceDecoration.mossDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.mossDecoration = newValue)
				.setTooltip(tooltip("moss_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"mossDecoration",
			configInstance
		);
		var auburnMoss = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("auburn_moss_generation"), modifiedSurfaceDecoration.auburnMoss)
				.setDefaultValue(defaultSurfaceDecoration.auburnMoss)
				.setSaveConsumer(newValue -> surfaceDecoration.auburnMoss = newValue)
				.setTooltip(tooltip("auburn_moss_generation"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"auburnMoss",
			configInstance
		);
		var paleMossDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("pale_moss_decoration"), modifiedSurfaceDecoration.paleMossDecoration)
				.setDefaultValue(defaultSurfaceDecoration.paleMossDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.paleMossDecoration = newValue)
				.setTooltip(tooltip("pale_moss_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"paleMossDecoration",
			configInstance
		);
		var scorchedSandDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("scorched_sand_decoration"), modifiedSurfaceDecoration.scorchedSandDecoration)
				.setDefaultValue(defaultSurfaceDecoration.scorchedSandDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.scorchedSandDecoration = newValue)
				.setTooltip(tooltip("scorched_sand_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"scorchedSandDecoration",
			configInstance
		);
		var scorchedRedSandDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("scorched_red_sand_decoration"), modifiedSurfaceDecoration.scorchedRedSandDecoration)
				.setDefaultValue(defaultSurfaceDecoration.scorchedRedSandDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.scorchedRedSandDecoration = newValue)
				.setTooltip(tooltip("scorched_red_sand_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"scorchedRedSandDecoration",
			configInstance
		);
		var sandstoneDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sandstone_decoration"), modifiedSurfaceDecoration.sandstoneDecoration)
				.setDefaultValue(defaultSurfaceDecoration.sandstoneDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.sandstoneDecoration = newValue)
				.setTooltip(tooltip("sandstone_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"sandstoneDecoration",
			configInstance
		);
		var clayDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("clay_decoration"), modifiedSurfaceDecoration.clayDecoration)
				.setDefaultValue(defaultSurfaceDecoration.clayDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.clayDecoration = newValue)
				.setTooltip(tooltip("clay_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"clayDecoration",
			configInstance
		);
		var clearingDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("clearing_decoration"), modifiedSurfaceDecoration.clearingDecoration)
				.setDefaultValue(defaultSurfaceDecoration.clearingDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.clearingDecoration = newValue)
				.setTooltip(tooltip("clearing_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"clearingDecoration",
			configInstance
		);
		var snowPiles = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("snow_piles"), modifiedSurfaceDecoration.snowPiles)
				.setDefaultValue(defaultSurfaceDecoration.snowPiles)
				.setSaveConsumer(newValue -> surfaceDecoration.snowPiles = newValue)
				.setTooltip(tooltip("snow_piles"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"snowPiles",
			configInstance
		);
		var fragileIceDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("fragile_ice_decoration"), modifiedSurfaceDecoration.fragileIceDecoration)
				.setDefaultValue(defaultSurfaceDecoration.fragileIceDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.fragileIceDecoration = newValue)
				.setTooltip(tooltip("fragile_ice_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"fragileIceDecoration",
			configInstance
		);
		var icicleDecoration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("icicle_decoration"), modifiedSurfaceDecoration.icicleDecoration)
				.setDefaultValue(defaultSurfaceDecoration.icicleDecoration)
				.setSaveConsumer(newValue -> surfaceDecoration.icicleDecoration = newValue)
				.setTooltip(tooltip("icicle_decoration"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"icicleDecoration",
			configInstance
		);
		var taigaBoulders = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("taiga_boulders"), modifiedSurfaceDecoration.taigaBoulders)
				.setDefaultValue(defaultSurfaceDecoration.taigaBoulders)
				.setSaveConsumer(newValue -> surfaceDecoration.taigaBoulders = newValue)
				.setTooltip(tooltip("taiga_boulders"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"taigaBoulders",
			configInstance
		);
		var lakes = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("lake_generation"), modifiedSurfaceDecoration.lakes)
				.setDefaultValue(defaultSurfaceDecoration.lakes)
				.setSaveConsumer(newValue -> surfaceDecoration.lakes = newValue)
				.setTooltip(tooltip("lake_generation"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"lakes",
			configInstance
		);
		var basins = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("basin_generation"), modifiedSurfaceDecoration.basins)
				.setDefaultValue(defaultSurfaceDecoration.basins)
				.setSaveConsumer(newValue -> surfaceDecoration.basins = newValue)
				.setTooltip(tooltip("basin_generation"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"basins",
			configInstance
		);

		var surfaceDecorationCategory = FrozenClothConfig.createSubCategory(builder, category, text("surface_decoration"),
			false,
			tooltip("surface_decoration"),
			coarseDecoration, gravelDecoration, mudDecoration, packedMudDecoration, stoneDecoration, mossDecoration, auburnMoss, paleMossDecoration,
			scorchedSandDecoration, scorchedRedSandDecoration, sandstoneDecoration, clayDecoration, clearingDecoration, taigaBoulders,
			snowPiles, fragileIceDecoration, icicleDecoration, lakes, basins
		);

		var termite = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("termite_generation"), modifiedConfig.termiteGen)
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
				builder.startBooleanToggle(text("nether_geyser_generation"), modifiedConfig.netherGeyserGen)
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
		var snowBelowTrees = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("snow_below_trees"), modifiedConfig.snowBelowTrees)
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

		var aquatic = config.aquaticGeneration;
		var modifiedAquatic = modifiedConfig.aquaticGeneration;
		var defaultAquatic = defaultConfig.aquaticGeneration;
		var aquaticClazz = aquatic.getClass();

		var riverPool = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("river_pool"), modifiedAquatic.riverPool)
				.setDefaultValue(defaultAquatic.riverPool)
				.setSaveConsumer(newValue -> aquatic.riverPool = newValue)
				.setTooltip(tooltip("river_pool"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"riverPool",
			configInstance
		);
		var algae = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("algae_generation"), modifiedAquatic.algae)
				.setDefaultValue(defaultAquatic.algae)
				.setSaveConsumer(newValue -> aquatic.algae = newValue)
				.setTooltip(tooltip("algae_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"algae",
			configInstance
		);
		var plankton = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("plankton_generation"), modifiedAquatic.plankton)
				.setDefaultValue(defaultAquatic.plankton)
				.setSaveConsumer(newValue -> aquatic.plankton = newValue)
				.setTooltip(tooltip("plankton_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"plankton",
			configInstance
		);
		var seagrass = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("seagrass_generation"), modifiedAquatic.seagrass)
				.setDefaultValue(defaultAquatic.seagrass)
				.setSaveConsumer(newValue -> aquatic.seagrass = newValue)
				.setTooltip(tooltip("seagrass_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"seagrass",
			configInstance
		);
		var spongeBud = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sponge_bud_generation"), modifiedAquatic.spongeBud)
				.setDefaultValue(defaultAquatic.spongeBud)
				.setSaveConsumer(newValue -> aquatic.spongeBud = newValue)
				.setTooltip(tooltip("sponge_bud_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"spongeBud",
			configInstance
		);
		var barnacle = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("barnacle_generation"), modifiedAquatic.barnacle)
				.setDefaultValue(defaultAquatic.barnacle)
				.setSaveConsumer(newValue -> aquatic.barnacle = newValue)
				.setTooltip(tooltip("barnacle_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"barnacle",
			configInstance
		);
		var cattail = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("cattail_generation"), modifiedAquatic.cattail)
				.setDefaultValue(defaultAquatic.cattail)
				.setSaveConsumer(newValue -> aquatic.cattail = newValue)
				.setTooltip(tooltip("cattail_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"cattail",
			configInstance
		);
		var seaAnemone = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sea_anemone_generation"), modifiedAquatic.seaAnemone)
				.setDefaultValue(defaultAquatic.seaAnemone)
				.setSaveConsumer(newValue -> aquatic.seaAnemone = newValue)
				.setTooltip(tooltip("sea_anemone_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"seaAnemone",
			configInstance
		);
		var seaWhip = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sea_whip_generation"), modifiedAquatic.seaWhip)
				.setDefaultValue(defaultAquatic.seaWhip)
				.setSaveConsumer(newValue -> aquatic.seaWhip = newValue)
				.setTooltip(tooltip("sea_whip_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"seaWhip",
			configInstance
		);
		var tubeWorm = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("tube_worm_generation"), modifiedAquatic.tubeWorm)
				.setDefaultValue(defaultAquatic.tubeWorm)
				.setSaveConsumer(newValue -> aquatic.tubeWorm = newValue)
				.setTooltip(tooltip("tube_worm_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"tubeWorm",
			configInstance
		);
		var hydrothermalVent = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("hydrothermal_vent_generation"), modifiedAquatic.hydrothermalVent)
				.setDefaultValue(defaultAquatic.hydrothermalVent)
				.setSaveConsumer(newValue -> aquatic.hydrothermalVent = newValue)
				.setTooltip(tooltip("hydrothermal_vent_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"hydrothermalVent",
			configInstance
		);
		var oceanMossGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("ocean_moss_generation"), modifiedAquatic.oceanMossGeneration)
				.setDefaultValue(defaultAquatic.oceanMossGeneration)
				.setSaveConsumer(newValue -> aquatic.oceanMossGeneration = newValue)
				.setTooltip(tooltip("ocean_moss_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"oceanMossGeneration",
			configInstance
		);
		var oceanAuburnMossGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("ocean_auburn_moss_generation"), modifiedAquatic.oceanAuburnMossGeneration)
				.setDefaultValue(defaultAquatic.oceanAuburnMossGeneration)
				.setSaveConsumer(newValue -> aquatic.oceanAuburnMossGeneration = newValue)
				.setTooltip(tooltip("ocean_auburn_moss_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"oceanAuburnMossGeneration",
			configInstance
		);

		var aquaticGenerationCategory = FrozenClothConfig.createSubCategory(builder, category, text("aquatic_generation"),
			false,
			tooltip("aquatic_generation"),
			riverPool, algae, plankton, seagrass, spongeBud, barnacle, cattail, seaAnemone, seaWhip, tubeWorm, hydrothermalVent, oceanMossGeneration, oceanAuburnMossGeneration
		);

		var transition = config.transitionGeneration;
		var modifiedTransition = modifiedConfig.transitionGeneration;
		var defaultTransition = defaultConfig.transitionGeneration;
		var transitionClazz = transition.getClass();

		var sandTransitions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sand_transitions"), modifiedTransition.sandTransitions)
				.setDefaultValue(defaultTransition.sandTransitions)
				.setSaveConsumer(newValue -> transition.sandTransitions = newValue)
				.setTooltip(tooltip("sand_transitions"))
				.requireRestart()
				.build(),
			transitionClazz,
			"sandTransitions",
			configInstance
		);
		var redSandTransitions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("red_sand_transitions"), modifiedTransition.redSandTransitions)
				.setDefaultValue(defaultTransition.redSandTransitions)
				.setSaveConsumer(newValue -> transition.redSandTransitions = newValue)
				.setTooltip(tooltip("red_sand_transitions"))
				.requireRestart()
				.build(),
			transitionClazz,
			"redSandTransitions",
			configInstance
		);
		var coarseTransitions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("coarse_dirt_transitions"), modifiedTransition.coarseTransitions)
				.setDefaultValue(defaultTransition.coarseTransitions)
				.setSaveConsumer(newValue -> transition.coarseTransitions = newValue)
				.setTooltip(tooltip("coarse_dirt_transitions"))
				.requireRestart()
				.build(),
			transitionClazz,
			"coarseTransitions",
			configInstance
		);
		var gravelTransitions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("gravel_transitions"), modifiedTransition.gravelTransitions)
				.setDefaultValue(defaultTransition.gravelTransitions)
				.setSaveConsumer(newValue -> transition.gravelTransitions = newValue)
				.setTooltip(tooltip("gravel_transitions"))
				.requireRestart()
				.build(),
			transitionClazz,
			"gravelTransitions",
			configInstance
		);
		var mudTransitions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("mud_transitions"), modifiedTransition.mudTransitions)
				.setDefaultValue(defaultTransition.mudTransitions)
				.setSaveConsumer(newValue -> transition.mudTransitions = newValue)
				.setTooltip(tooltip("mud_transitions"))
				.requireRestart()
				.build(),
			transitionClazz,
			"mudTransitions",
			configInstance
		);
		var stoneTransitions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("stone_transitions"), modifiedTransition.stoneTransitions)
				.setDefaultValue(defaultTransition.stoneTransitions)
				.setSaveConsumer(newValue -> transition.stoneTransitions = newValue)
				.setTooltip(tooltip("stone_transitions"))
				.requireRestart()
				.build(),
			transitionClazz,
			"stoneTransitions",
			configInstance
		);
		var snowTransitions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("snow_transitions"), modifiedTransition.snowTransitions)
				.setDefaultValue(defaultTransition.snowTransitions)
				.setSaveConsumer(newValue -> transition.snowTransitions = newValue)
				.setTooltip(tooltip("snow_transitions"))
				.requireRestart()
				.build(),
			transitionClazz,
			"snowTransitions",
			configInstance
		);

		var transitionGenerationCategory = FrozenClothConfig.createSubCategory(builder, category, text("transition_generation"),
			false,
			tooltip("transition_generation"),
			sandTransitions, redSandTransitions, coarseTransitions, gravelTransitions, mudTransitions, stoneTransitions, snowTransitions
		);

		var structure = config.structure;
		var modifiedStructure = modifiedConfig.structure;
		var defaultStructure = defaultConfig.structure;
		var structureClazz = structure.getClass();

		var decayTrailRuins = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("decay_trail_ruins"), modifiedStructure.decayTrailRuins)
				.setDefaultValue(defaultStructure.decayTrailRuins)
				.setSaveConsumer(newValue -> structure.decayTrailRuins = newValue)
				.setTooltip(tooltip("decay_trail_ruins"))
				.requireRestart()
				.build(),
			structureClazz,
			"decayTrailRuins",
			configInstance
		);
		var newDesertVillages = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("new_desert_villages"), modifiedStructure.newDesertVillages)
				.setDefaultValue(defaultStructure.newDesertVillages)
				.setSaveConsumer(newValue -> structure.newDesertVillages = newValue)
				.setTooltip(tooltip("new_desert_villages"))
				.requireRestart()
				.build(),
			structureClazz,
			"newDesertVillages",
			configInstance
		);
		var newWitchHuts = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("new_witch_huts"), modifiedStructure.newWitchHuts)
				.setDefaultValue(defaultStructure.newWitchHuts)
				.setSaveConsumer(newValue -> structure.newWitchHuts = newValue)
				.setTooltip(tooltip("new_witch_huts"))
				.build(),
			structureClazz,
			"newWitchHuts",
			configInstance
		);

		var structureCategory = FrozenClothConfig.createSubCategory(builder, category, text("structure_generation"),
			false,
			tooltip("structure_generation"),
			decayTrailRuins, newDesertVillages, newWitchHuts
		);
	}
}
