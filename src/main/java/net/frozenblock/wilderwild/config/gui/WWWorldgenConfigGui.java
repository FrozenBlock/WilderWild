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
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WWWorldgenConfigGui {
	private WWWorldgenConfigGui() {
		throw new UnsupportedOperationException("WorldgenConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = WWWorldgenConfig.get(true);
		var modifiedConfig = WWWorldgenConfig.getWithSync();
		Class<? extends WWWorldgenConfig> clazz = config.getClass();
		Config<? extends WWWorldgenConfig> configInstance = WWWorldgenConfig.INSTANCE;

		var defaultConfig = WWWorldgenConfig.INSTANCE.defaultInstance();
		var biomePlacement = config.biomePlacement;
		var modifiedBiomePlacement = modifiedConfig.biomePlacement;
		var biomes = config.biomeGeneration;
		var modifiedBiomes = modifiedConfig.biomeGeneration;

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
		var mesogleaCaves = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_mesoglea_caves"), modifiedBiomes.generateMesogleaCaves)
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
		var sparseForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_sparse_forest"), modifiedBiomes.generateSparseForest)
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
		var mapleForest = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("generate_maple_forest"), modifiedBiomes.generateMapleForest)
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
			entryBuilder.startBooleanToggle(text("generate_tundra"), modifiedBiomes.generateTundra)
				.setDefaultValue(defaultConfig.biomeGeneration.generateTundra)
				.setSaveConsumer(newValue -> biomes.generateTundra = newValue)
				.setTooltip(tooltip("generate_tundra"))
				.requireRestart()
				.build(),
			biomes.getClass(),
			"generateTundra",
			configInstance
		);

		var biomeGenerationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("biome_generation"),
			false,
			tooltip("biome_generation"),
			aridForest, aridSavanna, birchJungle, birchTaiga, cypressWetlands, darkBirchForest, darkTaiga, dyingForest, dyingMixedForest, flowerField,
			frozenCaves, magmaticCaves, mapleForest, mesogleaCaves, mixedForest, oasis, oldGrowthBirchTaiga, oldGrowthDarkForest, oldGrowthSnowyTaiga, parchedForest,
			rainforest, semiBirchForest, snowyDyingForest, snowyDyingMixedForest, sparseBirchJungle, sparseForest, temperateRainforest, tundra, warmBeach, warmRiver
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
		var modifyTundraPlacement = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("modify_tundra_placement"), modifiedBiomePlacement.modifyTundraPlacement)
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
			cherryGrove, jungle, mangroveSwamp, stonyShore, swamp, windsweptSavanna, modifyTundraPlacement
		);

		var tree = config.treeGeneration;
		var modifiedTree = modifiedConfig.treeGeneration;
		var defaultTree = defaultConfig.treeGeneration;
		var treeClazz = tree.getClass();

		var treeGeneration = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("tree_generation"), modifiedTree.treeGeneration)
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
			entryBuilder.startBooleanToggle(text("fallen_trees"), modifiedTree.fallenTrees)
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
			entryBuilder.startBooleanToggle(text("hollowed_fallen_trees"), modifiedTree.hollowedFallenTrees)
				.setDefaultValue(defaultTree.hollowedFallenTrees)
				.setSaveConsumer(newValue -> tree.hollowedFallenTrees = newValue)
				.setTooltip(tooltip("hollowed_fallen_trees"))
				.build(),
			treeClazz,
			"hollowedFallenTrees",
			configInstance
		);
		var snappedTrees = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("snapped_trees"), modifiedTree.snappedTrees)
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
			entryBuilder.startBooleanToggle(text("baobab_generation"), modifiedTree.baobab)
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
			entryBuilder.startBooleanToggle(text("palm_generation"), modifiedTree.palm)
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
			entryBuilder.startBooleanToggle(text("willow_generation"), modifiedTree.willow)
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
			entryBuilder.startBooleanToggle(text("birch_branches"), modifiedTree.birchBranches)
				.setDefaultValue(defaultTree.birchBranches)
				.setSaveConsumer(newValue -> tree.birchBranches = newValue)
				.setTooltip(tooltip("birch_branches"))
				.build(),
			treeClazz,
			"birchBranches",
			configInstance
		);
		var oakBranches = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("oak_branches"), modifiedTree.oakBranches)
				.setDefaultValue(defaultTree.oakBranches)
				.setSaveConsumer(newValue -> tree.oakBranches = newValue)
				.setTooltip(tooltip("oak_branches"))
				.build(),
			treeClazz,
			"oakBranches",
			configInstance
		);
		var darkOakBranches = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("dark_oak_branches"), modifiedTree.darkOakBranches)
				.setDefaultValue(defaultTree.darkOakBranches)
				.setSaveConsumer(newValue -> tree.darkOakBranches = newValue)
				.setTooltip(tooltip("dark_oak_branches"))
				.build(),
			treeClazz,
			"darkOakBranches",
			configInstance
		);
		var paleOakBranches = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("pale_oak_branches"), modifiedTree.paleOakBranches)
				.setDefaultValue(defaultTree.paleOakBranches)
				.setSaveConsumer(newValue -> tree.paleOakBranches = newValue)
				.setTooltip(tooltip("pale_oak_branches"))
				.build(),
			treeClazz,
			"paleOakBranches",
			configInstance
		);

		var treeGenerationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("tree_generation_category"),
			false,
			tooltip("tree_generation_category"),
			treeGeneration, fallenTrees, hollowedFallenTrees, snappedTrees,
			baobab, palm, willow,
			birchBranches, oakBranches, darkOakBranches, paleOakBranches
		);

		var vegetation = config.vegetation;
		var modifiedVegetation = modifiedConfig.vegetation;
		var defaultVegetation = defaultConfig.vegetation;
		var vegetationClazz = vegetation.getClass();

		var bushGeneration = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("bush_generation"), modifiedVegetation.bushGeneration)
				.setDefaultValue(defaultVegetation.bushGeneration)
				.setSaveConsumer(newValue -> vegetation.bushGeneration = newValue)
				.setTooltip(tooltip("bush_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"bushGeneration",
			configInstance
		);
		var cactusGeneration = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("cactus_generation"), modifiedVegetation.cactusGeneration)
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
			entryBuilder.startBooleanToggle(text("flower_generation"), modifiedVegetation.flowerGeneration)
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
			entryBuilder.startBooleanToggle(text("grass_generation"), modifiedVegetation.grassGeneration)
				.setDefaultValue(defaultVegetation.grassGeneration)
				.setSaveConsumer(newValue -> vegetation.grassGeneration = newValue)
				.setTooltip(tooltip("grass_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"grassGeneration",
			configInstance
		);
		var shelfFungiGeneration = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("shelf_fungi_generation"), modifiedVegetation.shelfFungiGeneration)
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
			entryBuilder.startBooleanToggle(text("mushroom_generation"), modifiedVegetation.mushroomGeneration)
				.setDefaultValue(defaultVegetation.mushroomGeneration)
				.setSaveConsumer(newValue -> vegetation.mushroomGeneration = newValue)
				.setTooltip(tooltip("mushroom_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"mushroomGeneration",
			configInstance
		);
		var pollen = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("pollen_generation"), modifiedVegetation.pollen)
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
			entryBuilder.startBooleanToggle(text("tumbleweed_generation"), modifiedVegetation.tumbleweed)
				.setDefaultValue(defaultVegetation.tumbleweed)
				.setSaveConsumer(newValue -> vegetation.tumbleweed = newValue)
				.setTooltip(tooltip("tumbleweed_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"tumbleweed",
			configInstance
		);
		var pumpkin = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("pumpkin_generation"), modifiedVegetation.pumpkin)
				.setDefaultValue(defaultVegetation.pumpkin)
				.setSaveConsumer(newValue -> vegetation.pumpkin = newValue)
				.setTooltip(tooltip("pumpkin_generation"))
				.requireRestart()
				.build(),
			vegetationClazz,
			"pumpkin",
			configInstance
		);

		var vegetationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("vegetation"),
			false,
			tooltip("vegetation"),
			grassGeneration, flowerGeneration, bushGeneration, cactusGeneration, shelfFungiGeneration, mushroomGeneration, pollen, pumpkin, tumbleweed
		);

		var surfaceDecoration = config.surfaceDecoration;
		var modifiedSurfaceDecoration = modifiedConfig.surfaceDecoration;
		var defaultSurfaceDecoration = defaultConfig.surfaceDecoration;
		var surfaceDecorationClazz = surfaceDecoration.getClass();

		var coarseDecoration = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("coarse_decoration"), modifiedSurfaceDecoration.coarseDecoration)
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
			entryBuilder.startBooleanToggle(text("gravel_decoration"), modifiedSurfaceDecoration.gravelDecoration)
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
			entryBuilder.startBooleanToggle(text("mud_decoration"), modifiedSurfaceDecoration.mudDecoration)
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
			entryBuilder.startBooleanToggle(text("packed_mud_decoration"), modifiedSurfaceDecoration.packedMudDecoration)
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
			entryBuilder.startBooleanToggle(text("stone_decoration"), modifiedSurfaceDecoration.stoneDecoration)
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
			entryBuilder.startBooleanToggle(text("moss_decoration"), modifiedSurfaceDecoration.mossDecoration)
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
			entryBuilder.startBooleanToggle(text("auburn_moss_generation"), modifiedSurfaceDecoration.auburnMoss)
				.setDefaultValue(defaultSurfaceDecoration.auburnMoss)
				.setSaveConsumer(newValue -> surfaceDecoration.auburnMoss = newValue)
				.setTooltip(tooltip("auburn_moss_generation"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"auburnMoss",
			configInstance
		);
		var scorchedSandDecoration = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("scorched_sand_decoration"), modifiedSurfaceDecoration.scorchedSandDecoration)
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
			entryBuilder.startBooleanToggle(text("scorched_red_sand_decoration"), modifiedSurfaceDecoration.scorchedRedSandDecoration)
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
			entryBuilder.startBooleanToggle(text("sandstone_decoration"), modifiedSurfaceDecoration.sandstoneDecoration)
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
			entryBuilder.startBooleanToggle(text("clay_decoration"), modifiedSurfaceDecoration.clayDecoration)
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
			entryBuilder.startBooleanToggle(text("clearing_decoration"), modifiedSurfaceDecoration.clearingDecoration)
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
			entryBuilder.startBooleanToggle(text("snow_piles"), modifiedSurfaceDecoration.snowPiles)
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
			entryBuilder.startBooleanToggle(text("fragile_ice_decoration"), modifiedSurfaceDecoration.fragileIceDecoration)
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
			entryBuilder.startBooleanToggle(text("icicle_decoration"), modifiedSurfaceDecoration.icicleDecoration)
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
			entryBuilder.startBooleanToggle(text("taiga_boulders"), modifiedSurfaceDecoration.taigaBoulders)
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
			entryBuilder.startBooleanToggle(text("lake_generation"), modifiedSurfaceDecoration.lakes)
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
			entryBuilder.startBooleanToggle(text("basin_generation"), modifiedSurfaceDecoration.basins)
				.setDefaultValue(defaultSurfaceDecoration.basins)
				.setSaveConsumer(newValue -> surfaceDecoration.basins = newValue)
				.setTooltip(tooltip("basin_generation"))
				.requireRestart()
				.build(),
			surfaceDecorationClazz,
			"basins",
			configInstance
		);

		var surfaceDecorationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("surface_decoration"),
			false,
			tooltip("surface_decoration"),
			coarseDecoration, gravelDecoration, mudDecoration, packedMudDecoration, stoneDecoration, mossDecoration, auburnMoss,
			scorchedSandDecoration, scorchedRedSandDecoration, sandstoneDecoration, clayDecoration, clearingDecoration, taigaBoulders,
			snowPiles, fragileIceDecoration, icicleDecoration, lakes, basins
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

		var aquatic = config.aquaticGeneration;
		var modifiedAquatic = modifiedConfig.aquaticGeneration;
		var defaultAquatic = defaultConfig.aquaticGeneration;
		var aquaticClazz = aquatic.getClass();

		var riverPool = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("river_pool"), modifiedAquatic.riverPool)
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
			entryBuilder.startBooleanToggle(text("algae_generation"), modifiedAquatic.algae)
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
			entryBuilder.startBooleanToggle(text("plankton_generation"), modifiedAquatic.plankton)
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
			entryBuilder.startBooleanToggle(text("seagrass_generation"), modifiedAquatic.seagrass)
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
			entryBuilder.startBooleanToggle(text("sponge_bud_generation"), modifiedAquatic.spongeBud)
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
			entryBuilder.startBooleanToggle(text("barnacle_generation"), modifiedAquatic.barnacle)
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
			entryBuilder.startBooleanToggle(text("cattail_generation"), modifiedAquatic.cattail)
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
			entryBuilder.startBooleanToggle(text("sea_anemone_generation"), modifiedAquatic.seaAnemone)
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
			entryBuilder.startBooleanToggle(text("sea_whip_generation"), modifiedAquatic.seaWhip)
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
			entryBuilder.startBooleanToggle(text("tube_worm_generation"), modifiedAquatic.tubeWorm)
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
			entryBuilder.startBooleanToggle(text("hydrothermal_vent_generation"), modifiedAquatic.hydrothermalVent)
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
			entryBuilder.startBooleanToggle(text("ocean_moss_generation"), modifiedAquatic.oceanMossGeneration)
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
			entryBuilder.startBooleanToggle(text("ocean_auburn_moss_generation"), modifiedAquatic.oceanAuburnMossGeneration)
				.setDefaultValue(defaultAquatic.oceanAuburnMossGeneration)
				.setSaveConsumer(newValue -> aquatic.oceanAuburnMossGeneration = newValue)
				.setTooltip(tooltip("ocean_auburn_moss_generation"))
				.requireRestart()
				.build(),
			aquaticClazz,
			"oceanAuburnMossGeneration",
			configInstance
		);

		var aquaticGenerationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("aquatic_generation"),
			false,
			tooltip("aquatic_generation"),
			riverPool, algae, plankton, seagrass, spongeBud, barnacle, cattail, seaAnemone, seaWhip, tubeWorm, hydrothermalVent, oceanMossGeneration, oceanAuburnMossGeneration
		);

		var transition = config.transitionGeneration;
		var modifiedTransition = modifiedConfig.transitionGeneration;
		var defaultTransition = defaultConfig.transitionGeneration;
		var transitionClazz = transition.getClass();

		var sandTransitions = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("sand_transitions"), modifiedTransition.sandTransitions)
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
			entryBuilder.startBooleanToggle(text("red_sand_transitions"), modifiedTransition.redSandTransitions)
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
			entryBuilder.startBooleanToggle(text("coarse_dirt_transitions"), modifiedTransition.coarseTransitions)
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
			entryBuilder.startBooleanToggle(text("gravel_transitions"), modifiedTransition.gravelTransitions)
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
			entryBuilder.startBooleanToggle(text("mud_transitions"), modifiedTransition.mudTransitions)
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
			entryBuilder.startBooleanToggle(text("stone_transitions"), modifiedTransition.stoneTransitions)
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
			entryBuilder.startBooleanToggle(text("snow_transitions"), modifiedTransition.snowTransitions)
				.setDefaultValue(defaultTransition.snowTransitions)
				.setSaveConsumer(newValue -> transition.snowTransitions = newValue)
				.setTooltip(tooltip("snow_transitions"))
				.requireRestart()
				.build(),
			transitionClazz,
			"snowTransitions",
			configInstance
		);

		var transitionGenerationCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("transition_generation"),
			false,
			tooltip("transition_generation"),
			sandTransitions, redSandTransitions, coarseTransitions, gravelTransitions, mudTransitions, stoneTransitions, snowTransitions
		);

		var structure = config.structure;
		var modifiedStructure = modifiedConfig.structure;
		var defaultStructure = defaultConfig.structure;
		var structureClazz = structure.getClass();

		var decayTrailRuins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("decay_trail_ruins"), modifiedStructure.decayTrailRuins)
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
			entryBuilder.startBooleanToggle(text("new_desert_villages"), modifiedStructure.newDesertVillages)
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
			entryBuilder.startBooleanToggle(text("new_witch_huts"), modifiedStructure.newWitchHuts)
				.setDefaultValue(defaultStructure.newWitchHuts)
				.setSaveConsumer(newValue -> structure.newWitchHuts = newValue)
				.setTooltip(tooltip("new_witch_huts"))
				.build(),
			structureClazz,
			"newWitchHuts",
			configInstance
		);

		var structureCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("structure_generation"),
			false,
			tooltip("structure_generation"),
			decayTrailRuins, newDesertVillages, newWitchHuts
		);
	}
}
