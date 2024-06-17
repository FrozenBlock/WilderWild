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

package net.frozenblock.wilderwild.mod_compat.terrablender;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class WilderOverworldRegion extends Region {
	public WilderOverworldRegion(ResourceLocation name, int weight) {
		super(name, RegionType.OVERWORLD, weight);
	}

	@Override
	public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
		this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
/*
			if (WilderSharedConstants.config().generateDarkTaiga()) {
				OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.DarkTaiga.TEMPERATURE,
							WilderSharedWorldgen.DarkTaiga.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.DARK_TAIGA);
				});
				OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.DarkTaiga.TEMPERATURE,
							WilderSharedWorldgen.DarkTaiga.HUMIDITY_B,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.DARK_TAIGA);
				});
			}
			if (WilderSharedConstants.config().generateMixedForest()) {
				OverworldBiomeBuilderParameters.points(Biomes.TAIGA).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.MixedForest.TEMPERATURE,
							WilderSharedWorldgen.MixedForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.MIXED_FOREST);
				});
			}
			if (WilderSharedConstants.config().generateTemperateRainforest()) {
				OverworldBiomeBuilderParameters.points(Biomes.TAIGA).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.TemperateRainforest.TEMPERATURE,
							WilderSharedWorldgen.TemperateRainforest.HUMIDITY,
							point.continentalness(),
							WilderSharedWorldgen.TemperateRainforest.EROSION,
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.TEMPERATE_RAINFOREST);
				});
			}

			if (WilderSharedConstants.config().generateRainforest()) {
				OverworldBiomeBuilderParameters.points(Biomes.FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.Rainforest.TEMPERATURE_A,
							WilderSharedWorldgen.Rainforest.HUMIDITY_A,
							WilderSharedWorldgen.Rainforest.CONTINENTALNESS_A,
							WilderSharedWorldgen.Rainforest.EROSION_A,
							point.depth(),
							WilderSharedWorldgen.Rainforest.WEIRDNESS_A,
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.RAINFOREST);
				});

				OverworldBiomeBuilderParameters.points(Biomes.FOREST).forEach(point -> {
					if (point.temperature().equals(Temperature.FOUR)) {
						builder.replaceParameter(point,
							Climate.parameters(
								WilderSharedWorldgen.Rainforest.TEMPERATURE_B,
								WilderSharedWorldgen.Rainforest.HUMIDITY_B,
								point.continentalness(),
								point.erosion(),
								point.depth(),
								point.weirdness(),
								point.offset()
							)
						);
						builder.replaceBiome(point, RegisterWorldgen.RAINFOREST);
					} else if (point.temperature().equals(Temperature.THREE)) {
						builder.replaceParameter(point,
							Climate.parameters(
								WilderSharedWorldgen.Rainforest.TEMPERATURE_C,
								WilderSharedWorldgen.Rainforest.HUMIDITY_C,
								point.continentalness(),
								point.erosion(),
								point.depth(),
								point.weirdness(),
								point.offset()
							)
						);
						builder.replaceBiome(point, RegisterWorldgen.RAINFOREST);
					}
				});
			}

			if (WilderSharedConstants.config().generateBirchTaiga()) {
				OverworldBiomeBuilderParameters.points(Biomes.TAIGA).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.BirchTaiga.TEMPERATURE,
							WilderSharedWorldgen.BirchTaiga.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.BIRCH_TAIGA);
				});
				OverworldBiomeBuilderParameters.points(Biomes.TAIGA).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.BirchTaiga.TEMPERATURE,
							WilderSharedWorldgen.BirchTaiga.HUMIDITY_B,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.BIRCH_TAIGA);
				});
			}

			if (WilderSharedConstants.config().generateOldGrowthBirchTaiga()) {
				OverworldBiomeBuilderParameters.points(Biomes.OLD_GROWTH_BIRCH_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.BirchTaiga.TEMPERATURE,
							WilderSharedWorldgen.BirchTaiga.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA);
				});
				OverworldBiomeBuilderParameters.points(Biomes.OLD_GROWTH_BIRCH_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.BirchTaiga.TEMPERATURE,
							WilderSharedWorldgen.BirchTaiga.HUMIDITY_B,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA);
				});
			}

			if (WilderSharedConstants.config().generateBirchJungle()) {
				OverworldBiomeBuilderParameters.points(Biomes.JUNGLE).forEach(point -> {
					if (point.humidity().equals(Humidity.FOUR)) {
						builder.replaceParameter(point,
							Climate.parameters(
								WilderSharedWorldgen.BirchJungle.TEMPERATURE,
								WilderSharedWorldgen.BirchJungle.HUMIDITY_A,
								point.continentalness(),
								point.erosion(),
								point.depth(),
								point.weirdness(),
								point.offset()
							)
						);
						builder.replaceBiome(point, RegisterWorldgen.BIRCH_JUNGLE);
					} else {
						builder.replaceParameter(point,
							Climate.parameters(
								WilderSharedWorldgen.BirchJungle.TEMPERATURE,
								WilderSharedWorldgen.BirchJungle.HUMIDITY_B,
								point.continentalness(),
								point.erosion(),
								point.depth(),
								point.weirdness(),
								point.offset()
							)
						);
						builder.replaceBiome(point, RegisterWorldgen.BIRCH_JUNGLE);
					}
				});
			}

			if (WilderSharedConstants.config().generateSparseBirchJungle()) {
				OverworldBiomeBuilderParameters.points(Biomes.SPARSE_JUNGLE).forEach(point -> {
					if (!FrozenBiomeParameters.isWeird(point)) {
						builder.replaceParameter(point,
							Climate.parameters(
								WilderSharedWorldgen.BirchJungle.TEMPERATURE,
								WilderSharedWorldgen.BirchJungle.HUMIDITY_A,
								point.continentalness(),
								point.erosion(),
								point.depth(),
								point.weirdness(),
								point.offset()
							)
						);
						builder.replaceBiome(point, RegisterWorldgen.SPARSE_BIRCH_JUNGLE);
					}
				});
			}

			if (WilderSharedConstants.config().generateFlowerField()) {
				OverworldBiomeBuilderParameters.points(Biomes.FLOWER_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.FlowerField.TEMPERATURE_A,
							WilderSharedWorldgen.FlowerField.HUMIDITY_A,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.FLOWER_FIELD);
				});
				OverworldBiomeBuilderParameters.points(Biomes.FLOWER_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.FlowerField.TEMPERATURE_B,
							WilderSharedWorldgen.FlowerField.HUMIDITY_B,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.FLOWER_FIELD);
				});
				OverworldBiomeBuilderParameters.points(Biomes.FLOWER_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.FlowerField.TEMPERATURE_A,
							WilderSharedWorldgen.FlowerField.HUMIDITY_AB,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.FLOWER_FIELD);
				});
			}

			if (WilderSharedConstants.config().generateAridSavanna()) {
				OverworldBiomeBuilderParameters.points(Biomes.SAVANNA).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.AridSavanna.TEMPERATURE,
							WilderSharedWorldgen.AridSavanna.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.ARID_SAVANNA);
				});
			}

			if (WilderSharedConstants.config().generateParchedForest()) {
				OverworldBiomeBuilderParameters.points(Biomes.FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.ParchedForest.TEMPERATURE_A,
							WilderSharedWorldgen.ParchedForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.PARCHED_FOREST);
				});
				OverworldBiomeBuilderParameters.points(Biomes.FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.ParchedForest.TEMPERATURE_B,
							WilderSharedWorldgen.ParchedForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.PARCHED_FOREST);
				});
			}

			if (WilderSharedConstants.config().generateAridForest()) {
				OverworldBiomeBuilderParameters.points(Biomes.FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.AridForest.TEMPERATURE,
							WilderSharedWorldgen.AridForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.ARID_FOREST);
				});
			}

			if (WilderSharedConstants.config().generateOldGrowthSnowyTaiga()) {
				OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.TEMPERATURE,
							WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);
				});
			}

			if (WilderSharedConstants.config().generateOldGrowthDarkForest()) {
				OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST).forEach(point -> {
					if (point.weirdness().max() < 0L) {
						builder.replaceParameter(point,
							Climate.parameters(
								WilderSharedWorldgen.OldGrowthDarkForest.TEMPERATURE,
								WilderSharedWorldgen.OldGrowthDarkForest.HUMIDITY,
								point.continentalness(),
								point.erosion(),
								point.depth(),
								point.weirdness(),
								point.offset()
							)
						);
						builder.replaceBiome(point, RegisterWorldgen.OLD_GROWTH_DARK_FOREST);
					}
				});
			}

			if (WilderSharedConstants.config().generateDarkBirchForest()) {
				OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.DarkBirchForest.TEMPERATURE,
							WilderSharedWorldgen.DarkBirchForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.DARK_BIRCH_FOREST);
				});
			}

			if (WilderSharedConstants.config().generateSemiBirchForest()) {
				OverworldBiomeBuilderParameters.points(Biomes.BIRCH_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.SemiBirchForest.TEMPERATURE_A,
							WilderSharedWorldgen.SemiBirchForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.SEMI_BIRCH_FOREST);
				});
				OverworldBiomeBuilderParameters.points(Biomes.BIRCH_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.SemiBirchForest.TEMPERATURE_B,
							WilderSharedWorldgen.SemiBirchForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.SEMI_BIRCH_FOREST);
				});
				OverworldBiomeBuilderParameters.points(Biomes.OLD_GROWTH_BIRCH_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.SemiBirchForest.TEMPERATURE_A,
							WilderSharedWorldgen.SemiBirchForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.SEMI_BIRCH_FOREST);
				});
				OverworldBiomeBuilderParameters.points(Biomes.OLD_GROWTH_BIRCH_FOREST).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.SemiBirchForest.TEMPERATURE_B,
							WilderSharedWorldgen.SemiBirchForest.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.SEMI_BIRCH_FOREST);
				});
			}

			if (WilderSharedConstants.config().generateCypressWetlands()) {
				OverworldBiomeBuilderParameters.points(Biomes.SWAMP).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.CypressWetlands.TEMPERATURE,
							WilderSharedWorldgen.CypressWetlands.HUMIDITY,
							WilderSharedWorldgen.CypressWetlands.CONTINENTALNESS,
							WilderSharedWorldgen.CypressWetlands.EROSION,
							point.depth(),
							point.weirdness(),
							WilderSharedWorldgen.CypressWetlands.OFFSET
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.CYPRESS_WETLANDS);
				});
			}

			if (WilderSharedConstants.config().generateCypressWetlands()) {
				OverworldBiomeBuilderParameters.points(Biomes.MANGROVE_SWAMP).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.CypressWetlands.TEMPERATURE,
							WilderSharedWorldgen.CypressWetlands.HUMIDITY,
							WilderSharedWorldgen.CypressWetlands.CONTINENTALNESS,
							WilderSharedWorldgen.CypressWetlands.EROSION,
							point.depth(),
							point.weirdness(),
							WilderSharedWorldgen.CypressWetlands.OFFSET
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.CYPRESS_WETLANDS);
				});
			}

			if (WilderSharedConstants.config().generateJellyfishCaves()) {
				OverworldBiomeBuilderParameters.points(Biomes.DRIPSTONE_CAVES).forEach(point -> {
					builder.replaceParameter(point,
						WilderSharedWorldgen.semiDeepParameters(
							WilderSharedWorldgen.JellyfishCaves.TEMPERATURE,
							WilderSharedWorldgen.JellyfishCaves.HUMIDITY,
							WilderSharedWorldgen.JellyfishCaves.CONTINENTALNESS,
							WilderSharedWorldgen.JellyfishCaves.EROSION,
							WilderSharedWorldgen.JellyfishCaves.WEIRDNESS,
							WilderSharedWorldgen.JellyfishCaves.OFFSET
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.JELLYFISH_CAVES);
				});
			}

			if (WilderSharedConstants.config().generateOasis()) {
				OverworldBiomeBuilderParameters.points(Biomes.DESERT).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.Oasis.TEMPERATURE,
							WilderSharedWorldgen.Oasis.HUMIDITY,
							WilderSharedWorldgen.Oasis.CONTINENTALNESS,
							WilderSharedWorldgen.Oasis.EROSION,
							point.depth(),
							point.weirdness(),
							WilderSharedWorldgen.Oasis.OFFSET
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.OASIS);
				});
			}

			if (WilderSharedConstants.config().generateWarmRiver()) {
				OverworldBiomeBuilderParameters.points(Biomes.RIVER).forEach(point -> {
					builder.replaceParameter(point,
						Climate.parameters(
							WilderSharedWorldgen.WarmRiver.WARM_RANGE,
							WilderSharedWorldgen.WarmRiver.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					);
					builder.replaceBiome(point, RegisterWorldgen.WARM_RIVER);
				});
			}

			if (WilderSharedConstants.config().modifyMangroveSwampPlacement()) {
				OverworldBiomeBuilderParameters.points(Biomes.MANGROVE_SWAMP).forEach(point ->
					builder.replaceParameter(point,
						new Climate.ParameterPoint(
							WilderSharedWorldgen.MangroveSwamp.TEMPERATURE,
							WilderSharedWorldgen.MangroveSwamp.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					)
				);
			}

			if (WilderSharedConstants.config().modifySwampPlacement()) {
				OverworldBiomeBuilderParameters.points(Biomes.SWAMP).forEach(point ->
					builder.replaceParameter(point,
						new Climate.ParameterPoint(
							WilderSharedWorldgen.Swamp.TEMPERATURE,
							WilderSharedWorldgen.Swamp.HUMIDITY,
							point.continentalness(),
							point.erosion(),
							point.depth(),
							point.weirdness(),
							point.offset()
						)
					)
				);
			}*/
		});
	}
}
