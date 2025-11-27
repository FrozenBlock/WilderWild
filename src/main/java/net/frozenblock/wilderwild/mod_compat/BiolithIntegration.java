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

package net.frozenblock.wilderwild.mod_compat;

import com.mojang.datafixers.util.Pair;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.BiomeParameterTargets;
import com.terraformersmc.biolith.api.biome.sub.Criterion;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import static com.terraformersmc.biolith.api.biome.sub.CriterionBuilder.allOf;
import static com.terraformersmc.biolith.api.biome.sub.CriterionBuilder.anyOf;
import com.terraformersmc.biolith.api.biome.sub.RatioTargets;
import java.util.ArrayList;
import java.util.List;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.worldgen.biome.api.parameters.FrozenBiomeParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.worldgen.biome.FrozenCaves;
import net.frozenblock.wilderwild.worldgen.biome.MagmaticCaves;
import net.frozenblock.wilderwild.worldgen.biome.MapleForest;
import net.frozenblock.wilderwild.worldgen.biome.MesogleaCaves;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.Contract;

public class BiolithIntegration extends ModIntegration {

	public BiolithIntegration() {
		super("biolith");
	}

	@Override
	public void init() {
		final WWWorldgenConfig worldgenConfig = WWWorldgenConfig.get();
		final WWWorldgenConfig.BiomeGeneration biomeGeneration = worldgenConfig.biomeGeneration;
		final WWWorldgenConfig.BiomePlacement biomePlacement = worldgenConfig.biomePlacement;

		// VANILLA BIOMES

		if (biomePlacement.modifyJunglePlacement) {
			BiomePlacement.addSubOverworld(
				Biomes.DESERT,
				Biomes.JUNGLE,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.55F, 1F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 1F)
				)
			);
		}

		if (biomePlacement.modifyCherryGrovePlacement) {
			BiomePlacement.addSubOverworld(
				Biomes.FOREST,
				Biomes.CHERRY_GROVE,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.15F, 0.2F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.35F, 0.3F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, -0.2225F, 0.05F),
					CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -1F, 0.2F)
				)
			);
			BiomePlacement.addSubOverworld(
				Biomes.BIRCH_FOREST,
				Biomes.CHERRY_GROVE,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.55F, 1F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 1F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, -0.2225F, 0.05F),
					CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -1F, 0.2F)
				)
			);
			BiomePlacement.addSubOverworld(
				Biomes.OLD_GROWTH_BIRCH_FOREST,
				Biomes.CHERRY_GROVE,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.55F, 1F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 1F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, -0.2225F, 0.05F),
					CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -1F, 0.2F)
				)
			);
		}

		if (biomePlacement.modifyStonyShorePlacement) {
			BiomePlacement.addSubOverworld(
				Biomes.BEACH,
				Biomes.STONY_SHORE,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, -0.15F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 1F),
					CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.19F, -0.11F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, 0.05F, 0.45F)
				)
			);
		}

		// TRANSITION BIOMES

		if (biomeGeneration.generateAridForest) {
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.ARID_FOREST, atEdgeOf(Biomes.FOREST, 0.1F));
		}

		if (biomeGeneration.generateAridSavanna) {
			BiomePlacement.addSubOverworld(Biomes.SAVANNA, WWBiomes.ARID_SAVANNA, atEdgeOf(Biomes.DESERT, 0.1F));
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.ARID_SAVANNA, atEdgeOf(Biomes.FOREST, 0.2F));
		}

		if (biomeGeneration.generateBirchJungle) {
			BiomePlacement.addSubOverworld(
				Biomes.JUNGLE,
				WWBiomes.BIRCH_JUNGLE,
				atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST)
			);

			Criterion jungleOrBambooJungleEdge = atEdgeOfAny(0.2F, Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE);
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.BIRCH_JUNGLE, jungleOrBambooJungleEdge);
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.BIRCH_JUNGLE, jungleOrBambooJungleEdge);
		}

		if (biomeGeneration.generateBirchTaiga) {
			BiomePlacement.addSubOverworld(
				Biomes.TAIGA,
				WWBiomes.BIRCH_TAIGA,
				atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST)
			);
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.BIRCH_TAIGA, atEdgeOf(Biomes.TAIGA, 0.2F));
		}

		if (biomeGeneration.generateDarkBirchForest) {
			Criterion darkForestEdge = atEdgeOf(Biomes.DARK_FOREST, 0.2F);
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.DARK_BIRCH_FOREST, darkForestEdge);
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.DARK_BIRCH_FOREST,darkForestEdge);
			BiomePlacement.addSubOverworld(
				Biomes.DARK_FOREST,
				WWBiomes.DARK_BIRCH_FOREST,
				atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST)
			);
		}

		if (biomeGeneration.generateDarkTaiga) {
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_PINE_TAIGA, WWBiomes.DARK_TAIGA, atEdgeOf(Biomes.DARK_FOREST, 0.2F));
			BiomePlacement.addSubOverworld(Biomes.DARK_FOREST, WWBiomes.DARK_TAIGA, atEdgeOf(Biomes.OLD_GROWTH_PINE_TAIGA, 0.2F));
		}

		if (biomeGeneration.generateOldGrowthBirchTaiga) {
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, atEdgeOf(Biomes.TAIGA, 0.2F));
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, atEdgeOf(Biomes.OLD_GROWTH_BIRCH_FOREST, 0.2F));
		}

		if (biomeGeneration.generateParchedForest) {
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.PARCHED_FOREST, atEdgeOf(Biomes.SAVANNA, 0.1F));
			BiomePlacement.addSubOverworld(Biomes.SAVANNA, WWBiomes.PARCHED_FOREST, atEdgeOf(Biomes.FOREST, 0.2F));
		}

		if (biomeGeneration.generateRainforest) {
			/*
			BiomePlacement.addSubOverworld(
				Biomes.FOREST,
				WWBiomes.RAINFOREST,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.15F, 0.55F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.1F),
					CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.11F, 1F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, -1F, 0.05F),
					CriterionBuilder.value(BiomeParameterTargets.WEIRDNESS, -1F, 0.05F)
				)
			);
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FOREST)) {
				if (point.temperature().equals(Temperature.WARM)) {
					BiomePlacement.addSubOverworld(
						Biomes.FOREST,
						WWBiomes.RAINFOREST,
						allOf(
							CriterionBuilder.neighbor(Biomes.JUNGLE),
							CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.075F, 0.225F),
							CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.1F)
						)
					);
				}
				if (point.temperature().equals(Temperature.NEUTRAL)) {
					BiomePlacement.addSubOverworld(
						Biomes.FOREST,
						WWBiomes.RAINFOREST,
						allOf(
							CriterionBuilder.neighbor(Biomes.JUNGLE),
							CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.175F, 0.25F),
							CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.075F, 0.225F)
						)
					);
				}
			}
			 */

			Criterion forestEdge = atEdgeOf(Biomes.FOREST, 0.1F);
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.RAINFOREST, neighboringAny(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE));
			BiomePlacement.addSubOverworld(Biomes.JUNGLE, WWBiomes.RAINFOREST, forestEdge);
			BiomePlacement.addSubOverworld(Biomes.BAMBOO_JUNGLE, WWBiomes.RAINFOREST, forestEdge);
		}

		if (biomeGeneration.generateSemiBirchForest) {
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.SEMI_BIRCH_FOREST, atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST));
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.SEMI_BIRCH_FOREST, atEdgeOf(Biomes.FOREST, 0.2F));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.SEMI_BIRCH_FOREST, atEdgeOf(Biomes.FOREST, 0.2F));
		}

		if (biomeGeneration.generateSparseBirchJungle) {
			BiomePlacement.addSubOverworld(Biomes.SPARSE_JUNGLE, WWBiomes.SPARSE_BIRCH_JUNGLE, atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST));
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.SPARSE_BIRCH_JUNGLE, atEdgeOf(Biomes.SPARSE_JUNGLE, 0.3F));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.SPARSE_BIRCH_JUNGLE, atEdgeOf(Biomes.SPARSE_JUNGLE, 0.3F));
		}

		if (biomeGeneration.generateSparseForest) {
			Criterion temperature = CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, biomeGeneration.generateDyingForest ? -0.3F : -0.45F, 0F);
			BiomePlacement.addSubOverworld(
				Biomes.FOREST,
				WWBiomes.SPARSE_FOREST,
				allOf(
					atEdgeOfAny(0.2F, Biomes.PLAINS),
					temperature
				)
			);
			BiomePlacement.addSubOverworld(
				Biomes.PLAINS,
				WWBiomes.SPARSE_FOREST,
				allOf(
					atEdgeOfAny(0.2F, Biomes.FOREST),
					temperature
				)
			);
		}

		if (biomeGeneration.generateWarmBeach) {
			BiomePlacement.addSubOverworld(
				Biomes.BEACH,
				WWBiomes.WARM_BEACH,
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F)
			);
		}

		if (biomeGeneration.generateWarmRiver) {
			BiomePlacement.addSubOverworld(
				Biomes.RIVER,
				WWBiomes.WARM_RIVER,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0F)
				)
			);

			Criterion temperature = CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.55F, 1F);
			if (biomePlacement.modifyJunglePlacement) {
				BiomePlacement.addSubOverworld(
					Biomes.RIVER,
					WWBiomes.WARM_RIVER,
					allOf(
						temperature,
						CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0F, 1F)
					)
				);
			} else {
				BiomePlacement.addSubOverworld(Biomes.RIVER, WWBiomes.WARM_RIVER, temperature);
			}
		}

		// VARIANT BIOMES

		if (biomeGeneration.generateDyingForest) {
			BiomePlacement.addSubOverworld(
				Biomes.FOREST,
				WWBiomes.DYING_FOREST,
				allOf(
					neighboringAny(Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.FROZEN_RIVER, Biomes.GROVE),
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, 0.15F)
				)
			);

			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FOREST)) {
				addSurfaceBiome(
					WWBiomes.DYING_FOREST,
					biomeGeneration.generateTundra ? DyingForest.TEMPERATURE_TUNDRA : DyingForest.TEMPERATURE,
					DyingForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
			}
			*/
		}

		if (biomeGeneration.generateDyingMixedForest) {
			BiomePlacement.addSubOverworld(
				Biomes.TAIGA,
				WWBiomes.DYING_MIXED_FOREST,
				allOf(
					CriterionBuilder.value(
						BiomeParameterTargets.TEMPERATURE,
						biomeGeneration.generateTundra ? -0.495F : -0.465F,
						-0.300F
					),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.050F)
				)
			);

			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				addSurfaceBiome(
					WWBiomes.DYING_MIXED_FOREST,
					biomeGeneration.generateTundra ? DyingForest.TEMPERATURE_TUNDRA : DyingMixedForest.TEMPERATURE,
					DyingMixedForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
			}
			*/
		}

		if (biomeGeneration.generateFlowerField) {
			Criterion criterionA = allOf(
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.2F, -0.075F),
				CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.35F)
			);
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, criterionA);
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, criterionA);

			Criterion criterionB = allOf(
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.15F, 0.2F),
				CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.4F, -0.3F)
			);
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, criterionB);
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, criterionB);

			Criterion criterionC = allOf(
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.2F, -0.075F),
				CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.3675F, -0.3125F)
			);
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, criterionC);
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, criterionC);

			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FLOWER_FOREST)) {
				addSurfaceBiome(
					WWBiomes.FLOWER_FIELD,
					FlowerField.TEMPERATURE_A,
					FlowerField.HUMIDITY_A,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
				addSurfaceBiome(
					WWBiomes.FLOWER_FIELD,
					FlowerField.TEMPERATURE_B,
					FlowerField.HUMIDITY_B,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
				addSurfaceBiome(
					WWBiomes.FLOWER_FIELD,
					FlowerField.TEMPERATURE_A,
					FlowerField.HUMIDITY_AB,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
			}
			 */
		}

		if (biomeGeneration.generateMixedForest) {
			BiomePlacement.addSubOverworld(
				Biomes.TAIGA,
				WWBiomes.MIXED_FOREST,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, -0.14F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.05F, 0.15F)
				)
			);

			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
				addSurfaceBiome(
					WWBiomes.MIXED_FOREST,
					MixedForest.TEMPERATURE,
					MixedForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
			}
			 */
		}

		if (biomeGeneration.generateOasis) {
			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.MANGROVE_SWAMP)) {
				addSurfaceBiome(
					WWBiomes.OASIS,
					Oasis.TEMPERATURE,
					Oasis.HUMIDITY,
					Oasis.CONTINENTALNESS,
					Oasis.EROSION,
					point.weirdness()
				);
			}
			 */

			BiomePlacement.addSubOverworld(
				Biomes.DESERT,
				WWBiomes.OASIS,
				allOf(
					CriterionBuilder.neighbor(BiomeTags.IS_RIVER),
					CriterionBuilder.value(BiomeParameterTargets.PEAKS_VALLEYS, -1F, 0.2F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.1F, 0.3F)
				)
			);

			Criterion neighboringVanillaJungles = neighboringAny(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SPARSE_JUNGLE);
			BiomePlacement.addSubOverworld(
				Biomes.DESERT,
				WWBiomes.OASIS,
				allOf(
					neighboringVanillaJungles,
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.1F, 0.3F)
				)
			);
			BiomePlacement.addSubOverworld(
				Biomes.DESERT,
				WWBiomes.OASIS,
				allOf(
					neighboringVanillaJungles,
					CriterionBuilder.ratioMax(RatioTargets.EDGE, 0.3F)
				)
			);
		}

		if (biomeGeneration.generateOldGrowthDarkForest) {
			BiomePlacement.replaceOverworld(Biomes.DARK_FOREST, WWBiomes.OLD_GROWTH_DARK_FOREST, 0.5F);
		}

		if (biomeGeneration.generateSnowyDyingForest) {
			BiomePlacement.addSubOverworld(
				Biomes.SNOWY_PLAINS,
				WWBiomes.SNOWY_DYING_FOREST,
				allOf(
					CriterionBuilder.value(
						BiomeParameterTargets.TEMPERATURE,
						biomeGeneration.generateTundra ? -0.505F : -0.485F,
						biomeGeneration.generateTundra ? -0.495F : 0.465F
					),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.05F)
				)
			);

			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FOREST)) {
				addSurfaceBiome(
					WWBiomes.SNOWY_DYING_FOREST,
					biomeGeneration.generateTundra ? SnowyDyingForest.TEMPERATURE_TUNDRA : SnowyDyingForest.TEMPERATURE, SnowyDyingForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
			}
			 */
		}

		if (biomeGeneration.generateSnowyDyingMixedForest) {
			Criterion temperature = CriterionBuilder.value(
				BiomeParameterTargets.TEMPERATURE,
				biomeGeneration.generateTundra ? -0.505F : -0.485F,
				biomeGeneration.generateTundra ? -0.495F : -0.465F
			);
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				Criterion criterion = allOf(
					temperature,
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, FrozenBiomeParameters.isWeird(point) ? -0.105F : 0.05F, 0.155F)
				);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_TAIGA, WWBiomes.SNOWY_DYING_MIXED_FOREST, criterion);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.SNOWY_DYING_MIXED_FOREST, criterion);
			}

			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				addSurfaceBiome(
					WWBiomes.SNOWY_DYING_MIXED_FOREST,
					biomeGeneration.generateTundra ? SnowyDyingMixedForest.TEMPERATURE_TUNDRA : SnowyDyingMixedForest.TEMPERATURE,
					FrozenBiomeParameters.isWeird(point) ? SnowyDyingMixedForest.HUMIDITY_WEIRD : SnowyDyingMixedForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
			}
			 */
		}

		if (biomeGeneration.generateOldGrowthSnowyTaiga) {
			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				addSurfaceBiome(
					WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA,
					SnowyOldGrowthPineTaiga.TEMPERATURE,
					SnowyOldGrowthPineTaiga.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness()
				);
			}
			 */

			BiomePlacement.addSubOverworld(
				Biomes.TAIGA,
				WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -1F, -0.45F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 1F)
				)
			);
		}

		if (biomeGeneration.generateTemperateRainforest) {
			Criterion criterion = allOf(
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.25F, -0.05F),
				CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.25F, 1F),
				CriterionBuilder.value(BiomeParameterTargets.EROSION, -1F, 0.05F)
			);
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.TEMPERATE_RAINFOREST, criterion);
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_SPRUCE_TAIGA, WWBiomes.TEMPERATE_RAINFOREST, criterion);
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_PINE_TAIGA, WWBiomes.TEMPERATE_RAINFOREST, criterion);

			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
				addSurfaceBiome(
					WWBiomes.TEMPERATE_RAINFOREST,
					TemperateRainforest.TEMPERATURE,
					TemperateRainforest.HUMIDITY,
					point.continentalness(),
					TemperateRainforest.EROSION,
					point.weirdness()
				);
			}
			 */
		}

		if (biomeGeneration.generateTundra) {
			if (!biomeGeneration.generateMapleForest) {
				Criterion criterionA = allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.295F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.2F),
					CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, -2.233F, 0.45F)
				);
				Criterion criterionB = allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -1F, -0.45F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 0.7F),
					CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, 0.050F, 0.45F)
				);
				Criterion criterionC = allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.245F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.1F),
					CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, 0.03F, 0.8F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, -0.375F, 0.45F)
				);

				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, criterionA);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, criterionA);
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, criterionA);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, criterionA);

				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, criterionB);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, criterionB);

				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, criterionC);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, criterionC);
			}

			/*
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.PLAINS)) {
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE,
					Tundra.HUMIDITY,
					Tundra.CONTINENTALNESS,
					Tundra.EROSION_A,
					point.weirdness()
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE,
					Tundra.HUMIDITY,
					Tundra.CONTINENTALNESS,
					Tundra.EROSION_A,
					point.weirdness()
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_B,
					Tundra.HUMIDITY_C,
					Tundra.CONTINENTALNESS_B,
					Tundra.EROSION_B,
					Tundra.WEIRDNESS_C
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_C,
					Tundra.HUMIDITY_D,
					Tundra.CONTINENTALNESS_C,
					Tundra.EROSION_C,
					Tundra.WEIRDNESS_D
				);
			}
			 */

			if (biomePlacement.modifyTundraPlacement) {
				/*
				List<Climate.ParameterPoint> plainsSnowySlopesBorders = FrozenBiomeParameters.findBorderParameters(
					OverworldBiomeBuilderParameters.points(Biomes.PLAINS),
					OverworldBiomeBuilderParameters.points(Biomes.SNOWY_SLOPES),
					0.15F
				);
				plainsSnowySlopesBorders.forEach(parameterPoint -> {
					addSurfaceBiome(
						WWBiomes.TUNDRA,
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Tundra.WEIRDNESS_SLOPE_A
					);
					addSurfaceBiome(
						WWBiomes.TUNDRA,
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Tundra.WEIRDNESS_SLOPE_B
					);
					addSurfaceBiome(
						WWBiomes.TUNDRA,
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Tundra.WEIRDNESS_SLOPE_C
					);
					addSurfaceBiome(
						WWBiomes.TUNDRA,
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						Tundra.WEIRDNESS_SLOPE_D
					);
				});
				 */

				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, atEdgeOf(Biomes.SNOWY_SLOPES, 0.4F));
				BiomePlacement.addSubOverworld(Biomes.SNOWY_SLOPES, WWBiomes.TUNDRA, atEdgeOf(Biomes.PLAINS, 0.2F));
			}

			if (biomeGeneration.generateMapleForest) {
				/*
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE,
					Tundra.HUMIDITY_MAPLE,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE,
					Tundra.WEIRDNESS_A_MAPLE
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE,
					Tundra.HUMIDITY_MAPLE,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE,
					Tundra.WEIRDNESS_B_MAPLE
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE,
					Tundra.HUMIDITY_MAPLE,
					Tundra.CONTINENTALNESS_MAPLE_PEAK,
					Tundra.EROSION_MAPLE_PEAK,
					Tundra.WEIRDNESS_MAPLE_PEAK
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE_BORDER,
					Tundra.HUMIDITY_MAPLE,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER,
					Tundra.WEIRDNESS_A_MAPLE
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE,
					Tundra.HUMIDITY_MAPLE_BORDER,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER,
					Tundra.WEIRDNESS_A_MAPLE
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE_BORDER,
					Tundra.HUMIDITY_MAPLE_BORDER,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER,
					Tundra.WEIRDNESS_A_MAPLE
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE_BORDER,
					Tundra.HUMIDITY_MAPLE,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER_CENTER,
					Tundra.WEIRDNESS_MAPLE_BORDER_CENTER
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE,
					Tundra.HUMIDITY_MAPLE_BORDER,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER_CENTER,
					Tundra.WEIRDNESS_MAPLE_BORDER_CENTER
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE_BORDER,
					Tundra.HUMIDITY_MAPLE_BORDER,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER_CENTER,
					Tundra.WEIRDNESS_MAPLE_BORDER_CENTER
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE_BORDER,
					Tundra.HUMIDITY_MAPLE,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER,
					Tundra.WEIRDNESS_B_MAPLE
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE,
					Tundra.HUMIDITY_MAPLE_BORDER,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER,
					Tundra.WEIRDNESS_B_MAPLE
				);
				addSurfaceBiome(
					WWBiomes.TUNDRA,
					Tundra.TEMPERATURE_MAPLE_BORDER,
					Tundra.HUMIDITY_MAPLE_BORDER,
					Tundra.CONTINENTALNESS_MAPLE,
					Tundra.EROSION_MAPLE_BORDER,
					Tundra.WEIRDNESS_B_MAPLE
				);
				 */

				Criterion neighboringMapleForest = CriterionBuilder.neighbor(WWBiomes.MAPLE_FOREST);
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, neighboringMapleForest);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, neighboringMapleForest);
				BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.TUNDRA, neighboringMapleForest);
			}
		}

		// Regular Biomes

		if (biomeGeneration.generateCypressWetlands) {
			BiomePlacement.addSubOverworld(
				Biomes.SWAMP,
				WWBiomes.CYPRESS_WETLANDS,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.3F)
				)
			);

			/*
			addSurfaceBiome(
				WWBiomes.CYPRESS_WETLANDS,
				CypressWetlands.TEMPERATURE,
				CypressWetlands.HUMIDITY,
				CypressWetlands.CONTINENTALNESS,
				CypressWetlands.EROSION,
				CypressWetlands.WEIRDNESS_A
			);
			addSurfaceBiome(
				WWBiomes.CYPRESS_WETLANDS,
				CypressWetlands.TEMPERATURE,
				CypressWetlands.HUMIDITY,
				CypressWetlands.CONTINENTALNESS,
				CypressWetlands.EROSION,
				CypressWetlands.WEIRDNESS_B
			);
			addSurfaceBiome(
				WWBiomes.CYPRESS_WETLANDS,
				CypressWetlands.TEMPERATURE,
				CypressWetlands.HUMIDITY,
				CypressWetlands.CONTINENTALNESS,
				CypressWetlands.EROSION,
				CypressWetlands.WEIRDNESS_C
			);
			 */

			/*
			List<Climate.ParameterPoint> swampJungleBorders = FrozenBiomeParameters.findBorderParameters(
				OverworldBiomeBuilderParameters.points(Biomes.SWAMP),
				OverworldBiomeBuilderParameters.points(Biomes.JUNGLE),
				0.35F
			);
			swampJungleBorders.forEach(parameterPoint -> {
				addSurfaceBiome(
					WWBiomes.CYPRESS_WETLANDS,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					CypressWetlands.WEIRDNESS_A
				);
				addSurfaceBiome(
					WWBiomes.CYPRESS_WETLANDS,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					CypressWetlands.WEIRDNESS_B
				);
				addSurfaceBiome(
					WWBiomes.CYPRESS_WETLANDS,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					CypressWetlands.WEIRDNESS_C
				);
				addSurfaceBiome(
					WWBiomes.CYPRESS_WETLANDS,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					CypressWetlands.WEIRDNESS_D
				);
				addSurfaceBiome(
					WWBiomes.CYPRESS_WETLANDS,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					CypressWetlands.WEIRDNESS_E
				);
				addSurfaceBiome(
					WWBiomes.CYPRESS_WETLANDS,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					CypressWetlands.WEIRDNESS_F
				);
				addSurfaceBiome(
					WWBiomes.CYPRESS_WETLANDS,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					CypressWetlands.WEIRDNESS_G
				);
			});
			 */
		}

		if (biomeGeneration.generateFrozenCaves) {
			for (float depth : FrozenCaves.DEPTHS) {
				Pair<Climate.ParameterPoint, Climate.ParameterPoint> biomeParameters = FrozenCaves.INSTANCE.makeParametersAt(depth);
				BiomePlacement.addOverworld(WWBiomes.FROZEN_CAVES, biomeParameters.getFirst());
				BiomePlacement.addOverworld(WWBiomes.FROZEN_CAVES, biomeParameters.getSecond());
			}
		}

		if (biomeGeneration.generateMagmaticCaves) {
			BiomePlacement.addOverworld(
				WWBiomes.MAGMATIC_CAVES,
				new Climate.ParameterPoint(
					MagmaticCaves.TEMPERATURE,
					MagmaticCaves.HUMIDITY,
					MagmaticCaves.CONTINENTALNESS,
					MagmaticCaves.EROSION,
					Climate.Parameter.point(1.1F),
					MagmaticCaves.WEIRDNESS,
					0L
				)
			);
		}

		if (biomeGeneration.generateMapleForest) {
			addSurfaceBiome(
				WWBiomes.MAPLE_FOREST,
				MapleForest.TEMPERATURE,
				MapleForest.HUMIDITY,
				MapleForest.CONTINENTALNESS,
				MapleForest.EROSION,
				MapleForest.WEIRDNESS_A
			);
			addSurfaceBiome(
				WWBiomes.MAPLE_FOREST,
				MapleForest.TEMPERATURE,
				MapleForest.HUMIDITY,
				MapleForest.CONTINENTALNESS,
				MapleForest.EROSION,
				MapleForest.WEIRDNESS_B
			);
		}

		if (biomeGeneration.generateMesogleaCaves) {
			BiomePlacement.addOverworld(
				WWBiomes.MESOGLEA_CAVES,
				new Climate.ParameterPoint(
					MesogleaCaves.TEMPERATURE,
					MesogleaCaves.HUMIDITY,
					MesogleaCaves.CONTINENTALNESS,
					MesogleaCaves.EROSION,
					Climate.Parameter.span(0.4F, 1F),
					MesogleaCaves.WEIRDNESS,
					0L
				)
			);
			BiomePlacement.addOverworld(
				WWBiomes.MESOGLEA_CAVES,
				new Climate.ParameterPoint(
					MesogleaCaves.TEMPERATURE,
					MesogleaCaves.HUMIDITY,
					MesogleaCaves.CONTINENTALNESS,
					MesogleaCaves.EROSION,
					Climate.Parameter.span(0.2F, 0.9F),
					MesogleaCaves.WEIRDNESS,
					0L
				)
			);
		}
	}

	@Contract("_, _ -> new")
	private static Criterion atEdgeOf(ResourceKey<Biome> biome, float max) {
		return allOf(
			CriterionBuilder.ratioMax(RatioTargets.EDGE, max),
			CriterionBuilder.neighbor(biome)
		);
	}

	@SafeVarargs
	private static Criterion atEdgeOfAny(float max, ResourceKey<Biome> ... biomes) {
		final List<Criterion> criterions = new ArrayList<>();
		for (ResourceKey<Biome> biome : biomes) criterions.add(atEdgeOf(biome, max));
		return anyOf(criterions);
	}

	@SafeVarargs
	private static Criterion neighboringAny(ResourceKey<Biome> ... biomes) {
		final List<Criterion> criterions = new ArrayList<>();
		for (ResourceKey<Biome> biome : biomes) criterions.add(CriterionBuilder.neighbor(biome));
		return anyOf(criterions);
	}

	private static void addSurfaceBiome(
		ResourceKey<Biome> biome,
		Climate.Parameter temperature,
		Climate.Parameter humidity,
		Climate.Parameter continentalness,
		Climate.Parameter erosion,
		Climate.Parameter weirdness
	) {
		BiomePlacement.addOverworld(
			biome,
			new Climate.ParameterPoint(
				temperature,
				humidity,
				continentalness,
				erosion,
				Climate.Parameter.point(0F),
				weirdness,
				0L
			)
		);
		BiomePlacement.addOverworld(
			biome,
			new Climate.ParameterPoint(
				temperature,
				humidity,
				continentalness,
				erosion,
				Climate.Parameter.point(1F),
				weirdness,
				0L
			)
		);
	}
}
