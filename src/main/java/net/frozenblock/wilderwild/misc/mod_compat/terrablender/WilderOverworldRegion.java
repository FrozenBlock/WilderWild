package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateMixedForest()) {
				OverworldBiomeBuilderParameters.points(Biomes.TAIGA).forEach(point -> {
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
					builder.replaceBiome(point, RegisterWorldgen.MIXED_FOREST);
				});
			}

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateBirchTaiga()) {
				OverworldBiomeBuilderParameters.points(Biomes.BIRCH_FOREST).forEach(point -> {
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
			}

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateOldGrowthBirchTaiga()) {
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
			}

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateFlowerField()) {
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
			}

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateAridSavanna()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateParchedForest()) {
				OverworldBiomeBuilderParameters.points(Biomes.FOREST).forEach(point -> {
					builder.replaceParameter(point,
							Climate.parameters(
									WilderSharedWorldgen.ParchedForest.TEMPERATURE,
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateAridForest()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateOldGrowthSnowyTaiga()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateCypressWetlands()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateCypressWetlands()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateJellyfishCaves()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateOasis()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().generateWarmRiver()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().modifyMangroveSwampPlacement()) {
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

			if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().modifySwampPlacement()) {
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
			}
		});
	}
}
