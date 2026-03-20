/*
 * Copyright 2025-2026 FrozenBlock
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
		// VANILLA BIOMES

		if (WWWorldgenConfig.JUNGLE_MODIFIED_PLACEMENT.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.DESERT,
				Biomes.JUNGLE,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.55F, 1F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 1F)
				)
			);
		}

		if (WWWorldgenConfig.CHERRY_GROVE_MODIFIED_PLACEMENT.get()) {
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

		if (WWWorldgenConfig.STONY_SHORE_MODIFIED_PLACEMENT.get()) {
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

		if (WWWorldgenConfig.ARID_FOREST_GENERATION.get()) {
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.ARID_FOREST, atEdgeOf(Biomes.FOREST, 0.1F));
		}

		if (WWWorldgenConfig.ARID_SAVANNA_GENERATION.get()) {
			BiomePlacement.addSubOverworld(Biomes.SAVANNA, WWBiomes.ARID_SAVANNA, atEdgeOf(Biomes.DESERT, 0.1F));
			BiomePlacement.addSubOverworld(Biomes.DESERT, WWBiomes.ARID_SAVANNA, atEdgeOf(Biomes.FOREST, 0.2F));
		}

		if (WWWorldgenConfig.BIRCH_JUNGLE_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.JUNGLE,
				WWBiomes.BIRCH_JUNGLE,
				atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST)
			);

			Criterion jungleOrBambooJungleEdge = atEdgeOfAny(0.2F, Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE);
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.BIRCH_JUNGLE, jungleOrBambooJungleEdge);
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.BIRCH_JUNGLE, jungleOrBambooJungleEdge);
		}

		if (WWWorldgenConfig.BIRCH_TAIGA_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.TAIGA,
				WWBiomes.BIRCH_TAIGA,
				atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST)
			);
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.BIRCH_TAIGA, atEdgeOf(Biomes.TAIGA, 0.2F));
		}

		if (WWWorldgenConfig.DARK_BIRCH_FOREST_GENERATION.get()) {
			Criterion darkForestEdge = atEdgeOf(Biomes.DARK_FOREST, 0.2F);
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.DARK_BIRCH_FOREST, darkForestEdge);
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.DARK_BIRCH_FOREST,darkForestEdge);
			BiomePlacement.addSubOverworld(
				Biomes.DARK_FOREST,
				WWBiomes.DARK_BIRCH_FOREST,
				atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST)
			);
		}

		if (WWWorldgenConfig.DARK_TAIGA_GENERATION.get()) {
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_PINE_TAIGA, WWBiomes.DARK_TAIGA, atEdgeOf(Biomes.DARK_FOREST, 0.2F));
			BiomePlacement.addSubOverworld(Biomes.DARK_FOREST, WWBiomes.DARK_TAIGA, atEdgeOf(Biomes.OLD_GROWTH_PINE_TAIGA, 0.2F));
		}

		if (WWWorldgenConfig.OLD_GROWTH_BIRCH_TAIGA_GENERATION.get()) {
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, atEdgeOf(Biomes.TAIGA, 0.2F));
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, atEdgeOf(Biomes.OLD_GROWTH_BIRCH_FOREST, 0.2F));
		}

		if (WWWorldgenConfig.PARCHED_FOREST_GENERATION.get()) {
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.PARCHED_FOREST, atEdgeOf(Biomes.SAVANNA, 0.1F));
			BiomePlacement.addSubOverworld(Biomes.SAVANNA, WWBiomes.PARCHED_FOREST, atEdgeOf(Biomes.FOREST, 0.2F));
		}

		if (WWWorldgenConfig.RAINFOREST_GENERATION.get()) {
			final Criterion forestEdge = atEdgeOf(Biomes.FOREST, 0.1F);
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.RAINFOREST, neighboringAny(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE));
			BiomePlacement.addSubOverworld(Biomes.JUNGLE, WWBiomes.RAINFOREST, forestEdge);
			BiomePlacement.addSubOverworld(Biomes.BAMBOO_JUNGLE, WWBiomes.RAINFOREST, forestEdge);
		}

		if (WWWorldgenConfig.SEMI_BIRCH_FOREST_GENERATION.get()) {
			BiomePlacement.addSubOverworld(Biomes.FOREST, WWBiomes.SEMI_BIRCH_FOREST, atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST));
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.SEMI_BIRCH_FOREST, atEdgeOf(Biomes.FOREST, 0.2F));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.SEMI_BIRCH_FOREST, atEdgeOf(Biomes.FOREST, 0.2F));
		}

		if (WWWorldgenConfig.SPARSE_BIRCH_JUNGLE_GENERATION.get()) {
			BiomePlacement.addSubOverworld(Biomes.SPARSE_JUNGLE, WWBiomes.SPARSE_BIRCH_JUNGLE, atEdgeOfAny(0.2F, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST));
			BiomePlacement.addSubOverworld(Biomes.BIRCH_FOREST, WWBiomes.SPARSE_BIRCH_JUNGLE, atEdgeOf(Biomes.SPARSE_JUNGLE, 0.3F));
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_BIRCH_FOREST, WWBiomes.SPARSE_BIRCH_JUNGLE, atEdgeOf(Biomes.SPARSE_JUNGLE, 0.3F));
		}

		if (WWWorldgenConfig.SPARSE_FOREST_GENERATION.get()) {
			Criterion temperature = CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE,WWWorldgenConfig.DYING_FOREST_GENERATION.get() ? -0.3F : -0.45F, 0F);
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

		if (WWWorldgenConfig.WARM_BEACH_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.BEACH,
				WWBiomes.WARM_BEACH,
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F)
			);
		}

		if (WWWorldgenConfig.WARM_RIVER_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.RIVER,
				WWBiomes.WARM_RIVER,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0F)
				)
			);

			final Criterion temperature = CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.55F, 1F);
			if (WWWorldgenConfig.JUNGLE_MODIFIED_PLACEMENT.get()) {
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

		if (WWWorldgenConfig.DYING_FOREST_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.FOREST,
				WWBiomes.DYING_FOREST,
				allOf(
					neighboringAny(Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.FROZEN_RIVER),
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, 0.15F)
				)
			);
		}

		if (WWWorldgenConfig.DYING_MIXED_FOREST_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.TAIGA,
				WWBiomes.DYING_MIXED_FOREST,
				allOf(
					CriterionBuilder.value(
						BiomeParameterTargets.TEMPERATURE,
						WWWorldgenConfig.TUNDRA_GENERATION.get() ? -0.495F : -0.465F,
						-0.300F
					),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.050F)
				)
			);
		}

		if (WWWorldgenConfig.FLOWER_FIELD_GENERATION.get()) {
			final Criterion criterionA = allOf(
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.2F, -0.075F),
				CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.35F)
			);
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, criterionA);
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, criterionA);

			final Criterion criterionB = allOf(
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.15F, 0.2F),
				CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.4F, -0.3F)
			);
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, criterionB);
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, criterionB);

			final Criterion criterionC = allOf(
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.2F, -0.075F),
				CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.3675F, -0.3125F)
			);
			BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.FLOWER_FIELD, criterionC);
			BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.FLOWER_FIELD, criterionC);
		}

		if (WWWorldgenConfig.MIXED_FOREST_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.TAIGA,
				WWBiomes.MIXED_FOREST,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.45F, -0.14F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.05F, 0.15F)
				)
			);
		}

		if (WWWorldgenConfig.OASIS_GENERATION.get()) {
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

		if (WWWorldgenConfig.OLD_GROWTH_DARK_FOREST_GENERATION.get()) {
			BiomePlacement.replaceOverworld(Biomes.DARK_FOREST, WWBiomes.OLD_GROWTH_DARK_FOREST, 0.5F);
		}

		if (WWWorldgenConfig.SNOWY_DYING_FOREST_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.SNOWY_PLAINS,
				WWBiomes.SNOWY_DYING_FOREST,
				allOf(
					CriterionBuilder.value(
						BiomeParameterTargets.TEMPERATURE,
						WWWorldgenConfig.TUNDRA_GENERATION.get() ? -0.505F : -0.485F,
						WWWorldgenConfig.TUNDRA_GENERATION.get() ? -0.495F : 0.465F
					),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.105F, 0.05F)
				)
			);
		}

		if (WWWorldgenConfig.SNOWY_DYING_MIXED_FOREST_GENERATION.get()) {
			Criterion temperature = CriterionBuilder.value(
				BiomeParameterTargets.TEMPERATURE,
				WWWorldgenConfig.TUNDRA_GENERATION.get() ? -0.505F : -0.485F,
				WWWorldgenConfig.TUNDRA_GENERATION.get() ? -0.495F : -0.465F
			);
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				Criterion criterion = allOf(
					temperature,
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, FrozenBiomeParameters.isWeird(point) ? -0.105F : 0.05F, 0.155F)
				);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_TAIGA, WWBiomes.SNOWY_DYING_MIXED_FOREST, criterion);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.SNOWY_DYING_MIXED_FOREST, criterion);
			}
		}

		if (WWWorldgenConfig.SNOWY_OLD_GROWTH_PINE_TAIGA_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.TAIGA,
				WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -1F, -0.45F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 1F)
				)
			);
		}

		if (WWWorldgenConfig.TEMPERATE_RAINFOREST_GENERATION.get()) {
			final Criterion criterion = allOf(
				CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.25F, -0.05F),
				CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.25F, 1F),
				CriterionBuilder.value(BiomeParameterTargets.EROSION, -1F, 0.05F)
			);
			BiomePlacement.addSubOverworld(Biomes.TAIGA, WWBiomes.TEMPERATE_RAINFOREST, criterion);
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_SPRUCE_TAIGA, WWBiomes.TEMPERATE_RAINFOREST, criterion);
			BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_PINE_TAIGA, WWBiomes.TEMPERATE_RAINFOREST, criterion);
		}

		if (WWWorldgenConfig.TUNDRA_GENERATION.get()) {
			if (!WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) {
				final Criterion criterionA = allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -0.495F, -0.295F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -1F, -0.2F),
					CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, -2.233F, 0.45F)
				);
				final Criterion criterionB = allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, -1F, -0.45F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, 0.3F, 0.7F),
					CriterionBuilder.value(BiomeParameterTargets.CONTINENTALNESS, -0.110F, 0.030F),
					CriterionBuilder.value(BiomeParameterTargets.EROSION, 0.050F, 0.45F)
				);
				final Criterion criterionC = allOf(
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

			if (WWWorldgenConfig.TUNDRA_MODIFIED_PLACEMENT.get()) {
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, atEdgeOf(Biomes.SNOWY_SLOPES, 0.4F));
				BiomePlacement.addSubOverworld(Biomes.SNOWY_SLOPES, WWBiomes.TUNDRA, atEdgeOf(Biomes.PLAINS, 0.2F));
			}

			if (WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) {
				final Criterion neighboringMapleForest = CriterionBuilder.neighbor(WWBiomes.MAPLE_FOREST);
				BiomePlacement.addSubOverworld(Biomes.PLAINS, WWBiomes.TUNDRA, neighboringMapleForest);
				BiomePlacement.addSubOverworld(Biomes.SNOWY_PLAINS, WWBiomes.TUNDRA, neighboringMapleForest);
				BiomePlacement.addSubOverworld(Biomes.MEADOW, WWBiomes.TUNDRA, neighboringMapleForest);
			}
		}

		// Regular Biomes

		if (WWWorldgenConfig.CYPRESS_WETLANDS_GENERATION.get()) {
			BiomePlacement.addSubOverworld(
				Biomes.SWAMP,
				WWBiomes.CYPRESS_WETLANDS,
				allOf(
					CriterionBuilder.value(BiomeParameterTargets.TEMPERATURE, 0.2F, 0.55F),
					CriterionBuilder.value(BiomeParameterTargets.HUMIDITY, -0.1F, 0.3F)
				)
			);
		}

		if (WWWorldgenConfig.FROZEN_CAVES_GENERATION.get()) {
			for (float depth : FrozenCaves.DEPTHS) {
				Pair<Climate.ParameterPoint, Climate.ParameterPoint> biomeParameters = FrozenCaves.INSTANCE.makeParametersAt(depth);
				BiomePlacement.addOverworld(WWBiomes.FROZEN_CAVES, biomeParameters.getFirst());
				BiomePlacement.addOverworld(WWBiomes.FROZEN_CAVES, biomeParameters.getSecond());
			}
		}

		if (WWWorldgenConfig.MAGMATIC_CAVES_GENERATION.get()) {
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

		if (WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) {
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

		if (WWWorldgenConfig.MESOGLEA_CAVES_GENERATION.get()) {
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
