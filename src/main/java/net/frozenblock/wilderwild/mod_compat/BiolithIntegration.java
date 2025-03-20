/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mod_compat;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.BiomeParameterTargets;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import com.terraformersmc.biolith.api.biome.sub.RatioTargets;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.worldgen.biome.api.parameters.FrozenBiomeParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.worldgen.biome.CypressWetlands;
import net.frozenblock.wilderwild.worldgen.biome.DyingForest;
import net.frozenblock.wilderwild.worldgen.biome.DyingMixedForest;
import net.frozenblock.wilderwild.worldgen.biome.FlowerField;
import net.frozenblock.wilderwild.worldgen.biome.FrozenCaves;
import net.frozenblock.wilderwild.worldgen.biome.MagmaticCaves;
import net.frozenblock.wilderwild.worldgen.biome.MapleForest;
import net.frozenblock.wilderwild.worldgen.biome.MesogleaCaves;
import net.frozenblock.wilderwild.worldgen.biome.MixedForest;
import net.frozenblock.wilderwild.worldgen.biome.Oasis;
import net.frozenblock.wilderwild.worldgen.biome.OldGrowthDarkForest;
import net.frozenblock.wilderwild.worldgen.biome.SnowyDyingForest;
import net.frozenblock.wilderwild.worldgen.biome.SnowyDyingMixedForest;
import net.frozenblock.wilderwild.worldgen.biome.TemperateRainforest;
import net.frozenblock.wilderwild.worldgen.biome.Tundra;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import java.util.List;
import static com.terraformersmc.biolith.api.biome.sub.CriterionBuilder.allOf;
import static com.terraformersmc.biolith.api.biome.sub.CriterionBuilder.anyOf;

public class BiolithIntegration extends ModIntegration {

	public BiolithIntegration() {
		super("biolith");
	}

	@Override
	public void init() {
		WWModIntegrations.biolithLoaded = true;

		// Transition Biomes

		if (WWWorldgenConfig.get().biomeGeneration.generateAridForest) {
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.ARID_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.1F),CriterionBuilder.neighbor(Biomes.FOREST)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateAridSavanna) {
			BiomePlacement.addSubOverworld(Biomes.SAVANNA, WWBiomes.ARID_SAVANNA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.1F),CriterionBuilder.neighbor(Biomes.DESERT)))));
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.ARID_SAVANNA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.FOREST)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateBirchJungle) {
			BiomePlacement.addSubOverworld(Biomes.JUNGLE, WWBiomes.BIRCH_JUNGLE, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.BIRCH_FOREST))),(allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.OLD_GROWTH_BIRCH_FOREST)))));
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.BIRCH_JUNGLE, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.JUNGLE)))));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.BIRCH_JUNGLE, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.JUNGLE)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateBirchTaiga) {
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.BIRCH_TAIGA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.BIRCH_FOREST))),(allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.OLD_GROWTH_BIRCH_FOREST)))));
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.BIRCH_TAIGA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.TAIGA)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateDarkBirchForest) {
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.DARK_BIRCH_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.DARK_FOREST)))));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.DARK_BIRCH_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.DARK_FOREST)))));
			BiomePlacement.addSubOverworld(Biomes.DARK_FOREST, WWBiomes.DARK_BIRCH_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.BIRCH_FOREST))),(allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.OLD_GROWTH_BIRCH_FOREST)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateDarkTaiga) {
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_PINE_TAIGA, WWBiomes.DARK_TAIGA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.DARK_FOREST)))));
			BiomePlacement.addSubOverworld(Biomes.DARK_FOREST, WWBiomes.DARK_TAIGA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.OLD_GROWTH_PINE_TAIGA)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateOldGrowthBirchTaiga) {
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.TAIGA)))));
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.OLD_GROWTH_BIRCH_FOREST)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateParchedForest) {
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.PARCHED_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.SAVANNA)))));
			BiomePlacement.addSubOverworld(Biomes.SAVANNA, WWBiomes.PARCHED_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.FOREST)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateRainforest) {
			/*BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.RAINFOREST, anyOf((allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.15F, 0.55F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.1F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.11F, 1F), CriterionBuilder.value(BiomeParameterTargets.EROSION, -1F, 0.05F), CriterionBuilder.value(BiomeParameterTargets.WEIRDNESS, -1F, 0.05F)))));
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FOREST)) {
				if (point.temperature().equals(Temperature.WARM)) {
					BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.RAINFOREST, anyOf((allOf(CriterionBuilder.neighbor(Biomes.JUNGLE), CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.075F, 0.225F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.1F)))));
				}
				if (point.temperature().equals(Temperature.NEUTRAL)) {
					BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.RAINFOREST, anyOf((allOf(CriterionBuilder.neighbor(Biomes.JUNGLE), CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.175F, 0.25F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.075F, 0.225F)))));
				}
			}*/
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.RAINFOREST, anyOf((allOf(CriterionBuilder.neighbor(Biomes.JUNGLE))),(allOf(CriterionBuilder.neighbor(Biomes.BAMBOO_JUNGLE)))));
			BiomePlacement.addSubOverworld(Biomes.JUNGLE, WWBiomes.RAINFOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.1F),CriterionBuilder.neighbor(Biomes.FOREST)))));
			BiomePlacement.addSubOverworld(Biomes.BAMBOO_JUNGLE, WWBiomes.RAINFOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.1F),CriterionBuilder.neighbor(Biomes.FOREST)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateSemiBirchForest) {
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.SEMI_BIRCH_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.BIRCH_FOREST)))));
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.SEMI_BIRCH_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.FOREST)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateSparseBirchJungle) {
			BiomePlacement.addSubOverworld(Biomes.SPARSE_JUNGLE, WWBiomes.SPARSE_BIRCH_JUNGLE, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.OLD_GROWTH_BIRCH_FOREST)))));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.SPARSE_BIRCH_JUNGLE, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.3F),CriterionBuilder.neighbor(Biomes.SPARSE_JUNGLE)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateSparseForest) {
			if (WWWorldgenConfig.get().biomeGeneration.generateDyingForest) {
				BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.SPARSE_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F), CriterionBuilder.neighbor(Biomes.PLAINS), CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.3F, 0F)))));
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.SPARSE_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F), CriterionBuilder.neighbor(Biomes.FOREST), CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.3F, 0F)))));
			}
			else {
				BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.SPARSE_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F), CriterionBuilder.neighbor(Biomes.PLAINS), CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, 0F)))));
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.SPARSE_FOREST, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F), CriterionBuilder.neighbor(Biomes.FOREST), CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, 0F)))));
			}
		}
/*		if (WWWorldgenConfig.get().biomeGeneration.generateWarmBeach) {
			BiomePlacement.addSubOverworld(Biomes.BEACH, WWBiomes.WARM_BEACH, allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F)));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateWarmRiver) {
			BiomePlacement.addSubOverworld(Biomes.RIVER, WWBiomes.WARM_RIVER, allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0F)));
			if (WWWorldgenConfig.get().biomePlacement.modifyJunglePlacement) {
				BiomePlacement.addSubOverworld(Biomes.RIVER, WWBiomes.WARM_RIVER, allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.55F, 1F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0F, 1F)));
			}
			else {
				BiomePlacement.addSubOverworld(Biomes.RIVER, WWBiomes.WARM_RIVER, allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.55F, 1F)));
			}
		}*/

		// Variant Biomes

		if (WWWorldgenConfig.get().biomeGeneration.generateDyingForest) {
/*			if (WWWorldgenConfig.get().biomeGeneration.generateTundra) {
				BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.DYING_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.300F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.050F),CriterionBuilder.value(BiomeParameterTargets.EROSION, 0.05F, 0.45F))));
			}
			else {
				BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.DYING_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.465F, -0.300F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.050F),CriterionBuilder.value(BiomeParameterTargets.EROSION, 0.05F, 0.45F))));
			}*/
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.DYING_FOREST, anyOf(allOf(anyOf(CriterionBuilder.neighbor(Biomes.SNOWY_PLAINS),CriterionBuilder.neighbor(Biomes.SNOWY_TAIGA),CriterionBuilder.neighbor(Biomes.FROZEN_RIVER),CriterionBuilder.neighbor(Biomes.GROVE)), CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, 0.15F))));
/*			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FOREST)) {
				boolean generateTundra = WWWorldgenConfig.get().biomeGeneration.generateTundra;
				BiomePlacement.addOverworld(WWBiomes.DYING_FOREST,
					new Climate.ParameterPoint(
						generateTundra ? DyingForest.TEMPERATURE_TUNDRA : DyingForest.TEMPERATURE,
						DyingForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.DYING_FOREST,
					new Climate.ParameterPoint(
						generateTundra ? DyingForest.TEMPERATURE_TUNDRA : DyingForest.TEMPERATURE,
						DyingForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateDyingMixedForest) {
			if (WWWorldgenConfig.get().biomeGeneration.generateTundra) {
				BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.300F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.050F))));
			}
			else {
				BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.465F, -0.300F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.050F))));
			}
			/*for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				boolean generateTundra = WWWorldgenConfig.get().biomeGeneration.generateTundra;
				BiomePlacement.addOverworld(WWBiomes.DYING_MIXED_FOREST,
					new Climate.ParameterPoint(
						generateTundra ? DyingForest.TEMPERATURE_TUNDRA : DyingMixedForest.TEMPERATURE,
						DyingMixedForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.DYING_MIXED_FOREST,
					new Climate.ParameterPoint(
						generateTundra ? DyingForest.TEMPERATURE_TUNDRA : DyingMixedForest.TEMPERATURE,
						DyingMixedForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateFlowerField) {
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.200F, -0.075F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1.0F, -0.35F))));
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.200F, -0.075F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1.0F, -0.35F))));
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.15F, 0.2F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.400F, -0.300F))));
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.15F, 0.2F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.400F, -0.300F))));
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.200F, -0.075F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.3675F, -0.3125F))));
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.200F, -0.075F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.3675F, -0.3125F))));
			/*for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FLOWER_FOREST)) {
				BiomePlacement.addOverworld(WWBiomes.FLOWER_FIELD,
					new Climate.ParameterPoint(
						FlowerField.TEMPERATURE_A,
						FlowerField.HUMIDITY_A,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.FLOWER_FIELD,
					new Climate.ParameterPoint(
						FlowerField.TEMPERATURE_A,
						FlowerField.HUMIDITY_A,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.FLOWER_FIELD,
					new Climate.ParameterPoint(
						FlowerField.TEMPERATURE_B,
						FlowerField.HUMIDITY_B,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.FLOWER_FIELD,
					new Climate.ParameterPoint(
						FlowerField.TEMPERATURE_B,
						FlowerField.HUMIDITY_B,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.FLOWER_FIELD,
					new Climate.ParameterPoint(
						FlowerField.TEMPERATURE_A,
						FlowerField.HUMIDITY_AB,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.FLOWER_FIELD,
					new Climate.ParameterPoint(
						FlowerField.TEMPERATURE_A,
						FlowerField.HUMIDITY_AB,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateMixedForest) {
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, -0.140F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.050F, 0.150F))));
			/*for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
				BiomePlacement.addOverworld(WWBiomes.MIXED_FOREST,
					new Climate.ParameterPoint(
						MixedForest.TEMPERATURE,
						MixedForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.MIXED_FOREST,
					new Climate.ParameterPoint(
						MixedForest.TEMPERATURE,
						MixedForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateOasis) {
/*			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.MANGROVE_SWAMP)) {
				BiomePlacement.addOverworld(WWBiomes.OASIS,
					new Climate.ParameterPoint(
						Oasis.TEMPERATURE,
						Oasis.HUMIDITY,
						Oasis.CONTINENTALNESS,
						Oasis.EROSION,
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.OASIS,
					new Climate.ParameterPoint(
						Oasis.TEMPERATURE,
						Oasis.HUMIDITY,
						Oasis.CONTINENTALNESS,
						Oasis.EROSION,
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.OASIS, anyOf((allOf(CriterionBuilder.neighbor(BiomeTags.IS_RIVER), CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -1.0F, 0.2F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.1F, 0.3F)))));
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.OASIS, anyOf((allOf(anyOf(CriterionBuilder.neighbor(Biomes.JUNGLE),CriterionBuilder.neighbor(Biomes.BAMBOO_JUNGLE),CriterionBuilder.neighbor(Biomes.SPARSE_JUNGLE)),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.1F, 0.3F)))));
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.OASIS, anyOf((allOf(anyOf(CriterionBuilder.neighbor(Biomes.JUNGLE),CriterionBuilder.neighbor(Biomes.BAMBOO_JUNGLE),CriterionBuilder.neighbor(Biomes.SPARSE_JUNGLE)),CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.3F)))));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateOldGrowthDarkForest) {
			BiomePlacement.replaceOverworld(Biomes.DARK_FOREST, WWBiomes.OLD_GROWTH_DARK_FOREST, 0.5);
			BiomePlacement.addSubOverworld(Biomes.DARK_FOREST, WWBiomes.OLD_GROWTH_DARK_FOREST, CriterionBuilder.NEAR_BORDER);
			BiomePlacement.addSubOverworld(WWBiomes.OLD_GROWTH_DARK_FOREST, Biomes.DARK_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -1, -0.2F))));
			BiomePlacement.addSubOverworld(WWBiomes.OLD_GROWTH_DARK_FOREST, Biomes.DARK_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 1F))));
			BiomePlacement.addSubOverworld(WWBiomes.OLD_GROWTH_DARK_FOREST, Biomes.DARK_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, 0.35F))));
			BiomePlacement.addSubOverworld(WWBiomes.OLD_GROWTH_DARK_FOREST, Biomes.DARK_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, 0.35F))));
			/*for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST)) {
				BiomePlacement.addOverworld(WWBiomes.OLD_GROWTH_DARK_FOREST,
					new Climate.ParameterPoint(
						OldGrowthDarkForest.TEMPERATURE,
						OldGrowthDarkForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.OLD_GROWTH_DARK_FOREST,
					new Climate.ParameterPoint(
						OldGrowthDarkForest.TEMPERATURE,
						OldGrowthDarkForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateSnowyDyingForest) {
			if (WWWorldgenConfig.get().biomeGeneration.generateTundra) {
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.SNOWY_DYING_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.505F, -0.495F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.050F))));
			}
			else {
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.SNOWY_DYING_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.485F, -0.465F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.050F))));
			}
			/*for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FOREST)) {
				boolean generateTundra = WWWorldgenConfig.get().biomeGeneration.generateTundra;
				BiomePlacement.addOverworld(WWBiomes.SNOWY_DYING_FOREST,
					new Climate.ParameterPoint(
						generateTundra ? SnowyDyingForest.TEMPERATURE_TUNDRA : SnowyDyingForest.TEMPERATURE,
						SnowyDyingForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.SNOWY_DYING_FOREST,
					new Climate.ParameterPoint(
						generateTundra ? SnowyDyingForest.TEMPERATURE_TUNDRA : SnowyDyingForest.TEMPERATURE,
						SnowyDyingForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateSnowyDyingMixedForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				boolean weird = FrozenBiomeParameters.isWeird(point);
				if (WWWorldgenConfig.get().biomeGeneration.generateTundra) {
					if (weird) {
						BiomePlacement.addSubOverworld(Biomes.SNOWY_TAIGA, WWBiomes.SNOWY_DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.505F, -0.495F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.155F))));
						BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.SNOWY_DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.505F, -0.495F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.155F))));
					}
					else {
						BiomePlacement.addSubOverworld(Biomes.SNOWY_TAIGA, WWBiomes.SNOWY_DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.505F, -0.495F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.050F, 0.155F))));
						BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.SNOWY_DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.505F, -0.495F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.050F, 0.155F))));
					}
				}
				else {
					if (weird) {
						BiomePlacement.addSubOverworld(Biomes.SNOWY_TAIGA, WWBiomes.SNOWY_DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.485F, -0.465F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.155F))));
						BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.SNOWY_DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.485F, -0.465F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.155F))));
					}
					else {
						BiomePlacement.addSubOverworld(Biomes.SNOWY_TAIGA, WWBiomes.SNOWY_DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.485F, -0.465F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.050F, 0.155F))));
						BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.SNOWY_DYING_MIXED_FOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.485F, -0.465F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.050F, 0.155F))));
					}
				}
			}
			/*for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				boolean generateTundra = WWWorldgenConfig.get().biomeGeneration.generateTundra;
				boolean weird = FrozenBiomeParameters.isWeird(point);
				BiomePlacement.addOverworld(WWBiomes.SNOWY_DYING_MIXED_FOREST,
					new Climate.ParameterPoint(
						generateTundra ? SnowyDyingMixedForest.TEMPERATURE_TUNDRA : SnowyDyingMixedForest.TEMPERATURE,
						weird ? SnowyDyingMixedForest.HUMIDITY_WEIRD : SnowyDyingMixedForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.SNOWY_DYING_MIXED_FOREST,
					new Climate.ParameterPoint(
						generateTundra ? SnowyDyingMixedForest.TEMPERATURE_TUNDRA : SnowyDyingMixedForest.TEMPERATURE,
						weird ? SnowyDyingMixedForest.HUMIDITY_WEIRD : SnowyDyingMixedForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateOldGrowthSnowyTaiga) {
/*			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				BiomePlacement.addOverworld(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA,
					new Climate.ParameterPoint(
						SnowyOldGrowthPineTaiga.TEMPERATURE,
						SnowyOldGrowthPineTaiga.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA,
					new Climate.ParameterPoint(
						SnowyOldGrowthPineTaiga.TEMPERATURE,
						SnowyOldGrowthPineTaiga.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -1F, -0.45F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 1F)));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateTemperateRainforest) {
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.TEMPERATE_RAINFOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.250F, -0.050F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.250F, 1.0F),CriterionBuilder.value(BiomeParameterTargets.EROSION, -1F, 0.05F))));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_SPRUCE_TAIGA, WWBiomes.TEMPERATE_RAINFOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.250F, -0.050F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.250F, 1.0F),CriterionBuilder.value(BiomeParameterTargets.EROSION, -1F, 0.05F))));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_PINE_TAIGA, WWBiomes.TEMPERATE_RAINFOREST, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.250F, -0.050F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.250F, 1.0F),CriterionBuilder.value(BiomeParameterTargets.EROSION, -1F, 0.05F))));
			/*for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
				BiomePlacement.addOverworld(WWBiomes.TEMPERATE_RAINFOREST,
					new Climate.ParameterPoint(
						TemperateRainforest.TEMPERATURE,
						TemperateRainforest.HUMIDITY,
						point.continentalness(),
						TemperateRainforest.EROSION,
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TEMPERATE_RAINFOREST,
					new Climate.ParameterPoint(
						TemperateRainforest.TEMPERATURE,
						TemperateRainforest.HUMIDITY,
						point.continentalness(),
						TemperateRainforest.EROSION,
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
			}*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateTundra) {

			if (!WWWorldgenConfig.get().biomeGeneration.generateMapleForest) {
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.295F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.2F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F), CriterionBuilder.value(BiomeParameterTargets.EROSION, -2.233F, 0.450F))));
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.295F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.2F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F), CriterionBuilder.value(BiomeParameterTargets.EROSION, -2.233F, 0.450F))));
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.295F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.2F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F), CriterionBuilder.value(BiomeParameterTargets.EROSION, -2.233F, 0.450F))));
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.295F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.2F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F), CriterionBuilder.value(BiomeParameterTargets.EROSION, -2.233F, 0.450F))));
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -1.000F, -0.450F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.300F, 0.700F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F), CriterionBuilder.value(BiomeParameterTargets.EROSION, 0.050F, 0.450F))));
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -1.000F, -0.450F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.300F, 0.700F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F), CriterionBuilder.value(BiomeParameterTargets.EROSION, 0.050F, 0.450F))));
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.245F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1.0F, -0.100F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, 0.030F, 0.800F), CriterionBuilder.value(BiomeParameterTargets.EROSION, -0.375F, 0.450F))));
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.245F), CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1.0F, -0.100F), CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, 0.030F, 0.800F), CriterionBuilder.value(BiomeParameterTargets.EROSION, -0.375F, 0.450F))));
			}
			/*for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.PLAINS)) {
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE,
						Tundra.HUMIDITY,
						Tundra.CONTINENTALNESS,
						Tundra.EROSION_A,
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE,
						Tundra.HUMIDITY,
						Tundra.CONTINENTALNESS,
						Tundra.EROSION_A,
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE,
						Tundra.HUMIDITY,
						Tundra.CONTINENTALNESS,
						Tundra.EROSION_A,
						Climate.Parameter.point(0F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE,
						Tundra.HUMIDITY,
						Tundra.CONTINENTALNESS,
						Tundra.EROSION_A,
						Climate.Parameter.point(1F),
						point.weirdness(),
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_B,
						Tundra.HUMIDITY_C,
						Tundra.CONTINENTALNESS_B,
						Tundra.EROSION_B,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_C,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_B,
						Tundra.HUMIDITY_C,
						Tundra.CONTINENTALNESS_B,
						Tundra.EROSION_B,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_C,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_C,
						Tundra.HUMIDITY_D,
						Tundra.CONTINENTALNESS_C,
						Tundra.EROSION_C,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_D,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_C,
						Tundra.HUMIDITY_D,
						Tundra.CONTINENTALNESS_C,
						Tundra.EROSION_C,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_D,
						0L
					));
			}*/
			if (WWWorldgenConfig.get().biomePlacement.modifyTundraPlacement) {
				/*List<Climate.ParameterPoint> plainsSnowySlopesBorders = FrozenBiomeParameters.findBorderParameters(
					OverworldBiomeBuilderParameters.points(Biomes.PLAINS),
					OverworldBiomeBuilderParameters.points(Biomes.SNOWY_SLOPES),
					0.15F
				);
				plainsSnowySlopesBorders.forEach(parameterPoint -> {
					BiomePlacement.addOverworld(WWBiomes.TUNDRA,
						new Climate.ParameterPoint(
							parameterPoint.temperature(),
							parameterPoint.humidity(),
							parameterPoint.continentalness(),
							parameterPoint.erosion(),
							Climate.Parameter.point(0F),
							Tundra.WEIRDNESS_SLOPE_A,
							0L
						));
					BiomePlacement.addOverworld(WWBiomes.TUNDRA,
						new Climate.ParameterPoint(
							parameterPoint.temperature(),
							parameterPoint.humidity(),
							parameterPoint.continentalness(),
							parameterPoint.erosion(),
							Climate.Parameter.point(1F),
							Tundra.WEIRDNESS_SLOPE_A,
							0L
						));
					BiomePlacement.addOverworld(WWBiomes.TUNDRA,
						new Climate.ParameterPoint(
							parameterPoint.temperature(),
							parameterPoint.humidity(),
							parameterPoint.continentalness(),
							parameterPoint.erosion(),
							Climate.Parameter.point(0F),
							Tundra.WEIRDNESS_SLOPE_B,
							0L
						));
					BiomePlacement.addOverworld(WWBiomes.TUNDRA,
						new Climate.ParameterPoint(
							parameterPoint.temperature(),
							parameterPoint.humidity(),
							parameterPoint.continentalness(),
							parameterPoint.erosion(),
							Climate.Parameter.point(1F),
							Tundra.WEIRDNESS_SLOPE_B,
							0L
						));
					BiomePlacement.addOverworld(WWBiomes.TUNDRA,
						new Climate.ParameterPoint(
							parameterPoint.temperature(),
							parameterPoint.humidity(),
							parameterPoint.continentalness(),
							parameterPoint.erosion(),
							Climate.Parameter.point(0F),
							Tundra.WEIRDNESS_SLOPE_C,
							0L
						));
					BiomePlacement.addOverworld(WWBiomes.TUNDRA,
						new Climate.ParameterPoint(
							parameterPoint.temperature(),
							parameterPoint.humidity(),
							parameterPoint.continentalness(),
							parameterPoint.erosion(),
							Climate.Parameter.point(1F),
							Tundra.WEIRDNESS_SLOPE_C,
							0L
						));
					BiomePlacement.addOverworld(WWBiomes.TUNDRA,
						new Climate.ParameterPoint(
							parameterPoint.temperature(),
							parameterPoint.humidity(),
							parameterPoint.continentalness(),
							parameterPoint.erosion(),
							Climate.Parameter.point(0F),
							Tundra.WEIRDNESS_SLOPE_D,
							0L
						));
					BiomePlacement.addOverworld(WWBiomes.TUNDRA,
						new Climate.ParameterPoint(
							parameterPoint.temperature(),
							parameterPoint.humidity(),
							parameterPoint.continentalness(),
							parameterPoint.erosion(),
							Climate.Parameter.point(1F),
							Tundra.WEIRDNESS_SLOPE_D,
							0L
						));
				});*/
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.2F),CriterionBuilder.neighbor(Biomes.SNOWY_SLOPES)))));
				BiomePlacement.addSubOverworld(Biomes.SNOWY_SLOPES, WWBiomes.TUNDRA, anyOf((allOf(CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.1F),CriterionBuilder.neighbor(Biomes.PLAINS)))));
			}
			if (WWWorldgenConfig.get().biomeGeneration.generateMapleForest) {
/*				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_A_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_A_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_B_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_B_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE_PEAK,
						Tundra.EROSION_MAPLE_PEAK,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_MAPLE_PEAK,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE_PEAK,
						Tundra.EROSION_MAPLE_PEAK,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_MAPLE_PEAK,
						0L
					));
				// A
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_A_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_A_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_A_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_A_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_A_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_A_MAPLE,
						0L
					));
				// B
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER_CENTER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_MAPLE_BORDER_CENTER,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER_CENTER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_MAPLE_BORDER_CENTER,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER_CENTER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_MAPLE_BORDER_CENTER,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER_CENTER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_MAPLE_BORDER_CENTER,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER_CENTER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_MAPLE_BORDER_CENTER,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER_CENTER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_MAPLE_BORDER_CENTER,
						0L
					));
				// C
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_B_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_B_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_B_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_B_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(0F),
						Tundra.WEIRDNESS_B_MAPLE,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.TUNDRA,
					new Climate.ParameterPoint(
						Tundra.TEMPERATURE_MAPLE_BORDER,
						Tundra.HUMIDITY_MAPLE_BORDER,
						Tundra.CONTINENTALNESS_MAPLE,
						Tundra.EROSION_MAPLE_BORDER,
						Climate.Parameter.point(1F),
						Tundra.WEIRDNESS_B_MAPLE,
						0L
					));
				*/
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, anyOf((allOf(CriterionBuilder.neighbor(WWBiomes.MAPLE_FOREST)))));
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, anyOf((allOf(CriterionBuilder.neighbor(WWBiomes.MAPLE_FOREST)))));
				BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.TUNDRA, anyOf((allOf(CriterionBuilder.neighbor(WWBiomes.MAPLE_FOREST)))));
			}
		}

		// Regular Biomes

		if (WWWorldgenConfig.get().biomeGeneration.generateCypressWetlands) {
			BiomePlacement.addSubOverworld(Biomes.SWAMP, WWBiomes.CYPRESS_WETLANDS, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.3F),CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -0.6F, 0.2F))));
			BiomePlacement.addSubOverworld(Biomes.SWAMP, WWBiomes.CYPRESS_WETLANDS, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.3F),CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -0.6F, 0.2F))));
			BiomePlacement.addSubOverworld(Biomes.SWAMP, WWBiomes.CYPRESS_WETLANDS, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.3F),CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -0.6F, 0.2F))));
			BiomePlacement.addSubOverworld(Biomes.SWAMP, WWBiomes.CYPRESS_WETLANDS, anyOf(allOf(CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.1F, 0.3F),CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -0.6F, 0.2F))));
			/*BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
				new Climate.ParameterPoint(
					CypressWetlands.TEMPERATURE,
					CypressWetlands.HUMIDITY,
					CypressWetlands.CONTINENTALNESS,
					CypressWetlands.EROSION,
					Climate.Parameter.point(0F),
					CypressWetlands.WEIRDNESS_A,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
				new Climate.ParameterPoint(
					CypressWetlands.TEMPERATURE,
					CypressWetlands.HUMIDITY,
					CypressWetlands.CONTINENTALNESS,
					CypressWetlands.EROSION,
					Climate.Parameter.point(1F),
					CypressWetlands.WEIRDNESS_A,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
				new Climate.ParameterPoint(
					CypressWetlands.TEMPERATURE,
					CypressWetlands.HUMIDITY,
					CypressWetlands.CONTINENTALNESS,
					CypressWetlands.EROSION,
					Climate.Parameter.point(0F),
					CypressWetlands.WEIRDNESS_B,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
				new Climate.ParameterPoint(
					CypressWetlands.TEMPERATURE,
					CypressWetlands.HUMIDITY,
					CypressWetlands.CONTINENTALNESS,
					CypressWetlands.EROSION,
					Climate.Parameter.point(1F),
					CypressWetlands.WEIRDNESS_B,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
				new Climate.ParameterPoint(
					CypressWetlands.TEMPERATURE,
					CypressWetlands.HUMIDITY,
					CypressWetlands.CONTINENTALNESS,
					CypressWetlands.EROSION,
					Climate.Parameter.point(0F),
					CypressWetlands.WEIRDNESS_C,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
				new Climate.ParameterPoint(
					CypressWetlands.TEMPERATURE,
					CypressWetlands.HUMIDITY,
					CypressWetlands.CONTINENTALNESS,
					CypressWetlands.EROSION,
					Climate.Parameter.point(1F),
					CypressWetlands.WEIRDNESS_C,
					0L
				));*/

			/*List<Climate.ParameterPoint> swampJungleBorders = FrozenBiomeParameters.findBorderParameters(
				OverworldBiomeBuilderParameters.points(Biomes.SWAMP),
				OverworldBiomeBuilderParameters.points(Biomes.JUNGLE),
				0.35F
			);
			swampJungleBorders.forEach(parameterPoint -> {
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(0F),
						CypressWetlands.WEIRDNESS_A,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(1F),
						CypressWetlands.WEIRDNESS_A,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(0F),
						CypressWetlands.WEIRDNESS_B,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(1F),
						CypressWetlands.WEIRDNESS_B,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(0F),
						CypressWetlands.WEIRDNESS_C,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(1F),
						CypressWetlands.WEIRDNESS_C,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(0F),
						CypressWetlands.WEIRDNESS_D,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(1F),
						CypressWetlands.WEIRDNESS_D,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(0F),
						CypressWetlands.WEIRDNESS_E,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(1F),
						CypressWetlands.WEIRDNESS_E,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(0F),
						CypressWetlands.WEIRDNESS_F,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(1F),
						CypressWetlands.WEIRDNESS_F,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(0F),
						CypressWetlands.WEIRDNESS_G,
						0L
					));
				BiomePlacement.addOverworld(WWBiomes.CYPRESS_WETLANDS,
					new Climate.ParameterPoint(
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Climate.Parameter.point(1F),
						CypressWetlands.WEIRDNESS_G,
						0L
					));
				});*/
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateFrozenCaves) {
			for (float depth : FrozenCaves.DEPTHS) {
				BiomePlacement.addOverworld(WWBiomes.FROZEN_CAVES,
					new Climate.ParameterPoint(
						FrozenCaves.TEMPERATURE,
						FrozenCaves.HUMIDITY,
						FrozenCaves.CONTINENTALNESS,
						FrozenCaves.EROSION_PEAK,
						Climate.Parameter.point(depth),
						Climate.Parameter.span(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_DESCENDING),
						0L
					));
			}
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateMagmaticCaves) {
			BiomePlacement.addOverworld(WWBiomes.MAGMATIC_CAVES,
				new Climate.ParameterPoint(
					MagmaticCaves.TEMPERATURE,
					MagmaticCaves.HUMIDITY,
					MagmaticCaves.CONTINENTALNESS,
					MagmaticCaves.EROSION,
					Climate.Parameter.point(1.1F),
					MagmaticCaves.WEIRDNESS,
					0L
				));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateMapleForest) {
			BiomePlacement.addOverworld(WWBiomes.MAPLE_FOREST,
				new Climate.ParameterPoint(
					MapleForest.TEMPERATURE,
					MapleForest.HUMIDITY,
					MapleForest.CONTINENTALNESS,
					MapleForest.EROSION,
					Climate.Parameter.point(0F),
					MapleForest.WEIRDNESS_A,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.MAPLE_FOREST,
				new Climate.ParameterPoint(
					MapleForest.TEMPERATURE,
					MapleForest.HUMIDITY,
					MapleForest.CONTINENTALNESS,
					MapleForest.EROSION,
					Climate.Parameter.point(1F),
					MapleForest.WEIRDNESS_A,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.MAPLE_FOREST,
				new Climate.ParameterPoint(
					MapleForest.TEMPERATURE,
					MapleForest.HUMIDITY,
					MapleForest.CONTINENTALNESS,
					MapleForest.EROSION,
					Climate.Parameter.point(0F),
					MapleForest.WEIRDNESS_B,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.MAPLE_FOREST,
				new Climate.ParameterPoint(
					MapleForest.TEMPERATURE,
					MapleForest.HUMIDITY,
					MapleForest.CONTINENTALNESS,
					MapleForest.EROSION,
					Climate.Parameter.point(1F),
					MapleForest.WEIRDNESS_B,
					0L
				));
		}
		if (WWWorldgenConfig.get().biomeGeneration.generateMesogleaCaves) {
			BiomePlacement.addOverworld(WWBiomes.MESOGLEA_CAVES,
				new Climate.ParameterPoint(
					MesogleaCaves.TEMPERATURE,
					MesogleaCaves.HUMIDITY,
					MesogleaCaves.CONTINENTALNESS,
					MesogleaCaves.EROSION,
					Climate.Parameter.span(0.4F, 1F),
					MesogleaCaves.WEIRDNESS,
					0L
				));
			BiomePlacement.addOverworld(WWBiomes.MESOGLEA_CAVES,
				new Climate.ParameterPoint(
					MesogleaCaves.TEMPERATURE,
					MesogleaCaves.HUMIDITY,
					MesogleaCaves.CONTINENTALNESS,
					MesogleaCaves.EROSION,
					Climate.Parameter.span(0.2F, 0.9F),
					MesogleaCaves.WEIRDNESS,
					0L
				));
		}

	}
}
