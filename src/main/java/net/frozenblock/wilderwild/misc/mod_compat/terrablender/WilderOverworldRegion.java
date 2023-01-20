package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Depth;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class WilderOverworldRegion extends Region {
	public WilderOverworldRegion(ResourceLocation name, int weight) {
		super(name, RegionType.OVERWORLD, weight);
	}

	@Override
	public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
		this.addModifiedVanillaOverworldBiomes(mapper, builder -> {

			OverworldBiomeBuilderParameters.BiomeParameters oldGrowthBirchForest = OverworldBiomeBuilderParameters.getParameters(Biomes.OLD_GROWTH_BIRCH_FOREST.location());
			List<Climate.ParameterPoint> oldGrowthBirchForestPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(oldGrowthBirchForest.temperatures.toArray(new Climate.Parameter[0]))
					.humidity(oldGrowthBirchForest.humidities.toArray(new Climate.Parameter[0]))
					.continentalness(oldGrowthBirchForest.continentalnesses.toArray(new Climate.Parameter[0]))
					.erosion(oldGrowthBirchForest.erosions.toArray(new Climate.Parameter[0]))
					.depth(oldGrowthBirchForest.depths.toArray(new Climate.Parameter[0]))
					.weirdness(oldGrowthBirchForest.weirdnesses.toArray(new Climate.Parameter[0]))
					.offset(oldGrowthBirchForest.offsets.toArray(new Float[0]))
					.build();

			if (ClothConfigInteractionHandler.generateMixedForest()) {
				oldGrowthBirchForestPoints.forEach(point -> {
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

			OverworldBiomeBuilderParameters.BiomeParameters taiga = OverworldBiomeBuilderParameters.getParameters(Biomes.TAIGA.location());
			List<Climate.ParameterPoint> taigaPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(taiga.temperatures.toArray(new Climate.Parameter[0]))
					.humidity(taiga.humidities.toArray(new Climate.Parameter[0]))
					.continentalness(taiga.continentalnesses.toArray(new Climate.Parameter[0]))
					.erosion(taiga.erosions.toArray(new Climate.Parameter[0]))
					.depth(taiga.depths.toArray(new Climate.Parameter[0]))
					.weirdness(taiga.weirdnesses.toArray(new Climate.Parameter[0]))
					.offset(taiga.offsets.toArray(new Float[0]))
					.build();

			if (ClothConfigInteractionHandler.generateBirchTaiga()) {
				taigaPoints.forEach(point -> {
					builder.replaceParameter(point,
							Climate.parameters(
									WilderSharedWorldgen.BirchTaiga.TEMPERATURE,
									WilderSharedWorldgen.BirchTaiga.HUMIDITY,
									WilderSharedWorldgen.BirchTaiga.CONTINENTALNESS,
									WilderSharedWorldgen.BirchTaiga.EROSION,
									point.depth(),
									point.weirdness(),
									WilderSharedWorldgen.BirchTaiga.OFFSET
							)
					);

					builder.replaceBiome(point, RegisterWorldgen.BIRCH_TAIGA);
				});
			}

			OverworldBiomeBuilderParameters.BiomeParameters flowerForest = OverworldBiomeBuilderParameters.getParameters(Biomes.FLOWER_FOREST.location());
			List<Climate.ParameterPoint> flowerForestPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(flowerForest.temperatures.toArray(new Climate.Parameter[0]))
					.humidity(flowerForest.humidities.toArray(new Climate.Parameter[0]))
					.continentalness(flowerForest.continentalnesses.toArray(new Climate.Parameter[0]))
					.erosion(flowerForest.erosions.toArray(new Climate.Parameter[0]))
					.depth(flowerForest.depths.toArray(new Climate.Parameter[0]))
					.weirdness(flowerForest.weirdnesses.toArray(new Climate.Parameter[0]))
					.offset(flowerForest.offsets.toArray(new Float[0]))
					.build();

			if (ClothConfigInteractionHandler.generateFlowerField()) {
				flowerForestPoints.forEach(point -> {
					builder.replaceParameter(point,
							Climate.parameters(
									WilderSharedWorldgen.FlowerField.TEMPERATURE,
									WilderSharedWorldgen.FlowerField.HUMIDITY_A,
									point.continentalness(),
									point.erosion(),
									point.depth(),
									point.weirdness(),
									WilderSharedWorldgen.FlowerField.OFFSET
							)
					);

					builder.replaceBiome(point, RegisterWorldgen.FLOWER_FIELD);
				});
				flowerForestPoints.forEach(point -> {
					builder.replaceParameter(point,
							Climate.parameters(
									WilderSharedWorldgen.FlowerField.TEMPERATURE,
									WilderSharedWorldgen.FlowerField.HUMIDITY_B,
									point.continentalness(),
									point.erosion(),
									point.depth(),
									point.weirdness(),
									WilderSharedWorldgen.FlowerField.OFFSET
							)
					);

					builder.replaceBiome(point, RegisterWorldgen.FLOWER_FIELD);
				});
			}

			OverworldBiomeBuilderParameters.BiomeParameters swamp = OverworldBiomeBuilderParameters.getParameters(Biomes.SWAMP.location());
			List<Climate.ParameterPoint> swampPointsCypress = new ParameterUtils.ParameterPointListBuilder()
					.temperature(swamp.temperatures.toArray(new Climate.Parameter[0]))
					.humidity(swamp.humidities.toArray(new Climate.Parameter[0]))
					.continentalness(swamp.continentalnesses.toArray(new Climate.Parameter[0]))
					.erosion(swamp.erosions.toArray(new Climate.Parameter[0]))
					.depth(swamp.depths.toArray(new Climate.Parameter[0]))
					.weirdness(swamp.weirdnesses.toArray(new Climate.Parameter[0]))
					.offset(swamp.offsets.toArray(new Float[0]))
					.build();

			if (ClothConfigInteractionHandler.generateCypressWetlands()) {
				swampPointsCypress.forEach(point -> {
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

			OverworldBiomeBuilderParameters.BiomeParameters mangroveSwamp = OverworldBiomeBuilderParameters.getParameters(Biomes.MANGROVE_SWAMP.location());
			List<Climate.ParameterPoint> mangroveSwampPointsCypress = new ParameterUtils.ParameterPointListBuilder()
					.temperature(mangroveSwamp.temperatures.toArray(new Climate.Parameter[0]))
					.humidity(mangroveSwamp.humidities.toArray(new Climate.Parameter[0]))
					.continentalness(mangroveSwamp.continentalnesses.toArray(new Climate.Parameter[0]))
					.erosion(mangroveSwamp.erosions.toArray(new Climate.Parameter[0]))
					.depth(mangroveSwamp.depths.toArray(new Climate.Parameter[0]))
					.weirdness(mangroveSwamp.weirdnesses.toArray(new Climate.Parameter[0]))
					.offset(mangroveSwamp.offsets.toArray(new Float[0]))
					.build();

			if (ClothConfigInteractionHandler.generateCypressWetlands()) {
				mangroveSwampPointsCypress.forEach(point -> {
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

			OverworldBiomeBuilderParameters.BiomeParameters dripstoneCaves = OverworldBiomeBuilderParameters.getParameters(Biomes.DRIPSTONE_CAVES.location());
			List<Climate.ParameterPoint> dripstoneCavesPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(dripstoneCaves.temperatures.toArray(new Climate.Parameter[0]))
					.humidity(dripstoneCaves.humidities.toArray(new Climate.Parameter[0]))
					.continentalness(dripstoneCaves.continentalnesses.toArray(new Climate.Parameter[0]))
					.erosion(dripstoneCaves.erosions.toArray(new Climate.Parameter[0]))
					.depth(dripstoneCaves.depths.toArray(new Climate.Parameter[0]))
					.weirdness(dripstoneCaves.weirdnesses.toArray(new Climate.Parameter[0]))
					.offset(dripstoneCaves.offsets.toArray(new Float[0]))
					.build();

			if (ClothConfigInteractionHandler.generateJellyfishCaves()) {
				dripstoneCavesPoints.forEach(point -> {
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

			OverworldBiomeBuilderParameters.BiomeParameters desert = OverworldBiomeBuilderParameters.getParameters(Biomes.DESERT.location());
			List<Climate.ParameterPoint> desertPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(desert.temperatures.toArray(new Climate.Parameter[0]))
					.humidity(desert.humidities.toArray(new Climate.Parameter[0]))
					.continentalness(desert.continentalnesses.toArray(new Climate.Parameter[0]))
					.erosion(desert.erosions.toArray(new Climate.Parameter[0]))
					.depth(desert.depths.toArray(new Climate.Parameter[0]))
					.weirdness(desert.weirdnesses.toArray(new Climate.Parameter[0]))
					.offset(desert.offsets.toArray(new Float[0]))
					.build();

			if (ClothConfigInteractionHandler.generateOasis()) {
				desertPoints.forEach(point -> {
					builder.replaceParameter(point,
							Climate.parameters(
									WilderSharedWorldgen.Oasis.WARM_RANGE,
									WilderSharedWorldgen.Oasis.HUMIDITY_DRY,
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

			OverworldBiomeBuilderParameters.BiomeParameters river = OverworldBiomeBuilderParameters.getParameters(Biomes.RIVER.location());
			List<Climate.ParameterPoint> riverPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(river.temperatures.toArray(new Climate.Parameter[0]))
					.humidity(river.humidities.toArray(new Climate.Parameter[0]))
					.continentalness(river.continentalnesses.toArray(new Climate.Parameter[0]))
					.erosion(river.erosions.toArray(new Climate.Parameter[0]))
					.depth(river.depths.toArray(new Climate.Parameter[0]))
					.weirdness(river.weirdnesses.toArray(new Climate.Parameter[0]))
					.offset(river.offsets.toArray(new Float[0]))
					.build();

			if (ClothConfigInteractionHandler.generateWarmRiver()) {
				riverPoints.forEach(point -> {
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

			if (ClothConfigInteractionHandler.modifyMangroveSwampPlacement()) {
				List<Climate.ParameterPoint> mangroveSwampPoints = new ParameterUtils.ParameterPointListBuilder()
						.temperature(mangroveSwamp.temperatures.toArray(new Climate.Parameter[0]))
						.humidity(mangroveSwamp.humidities.toArray(new Climate.Parameter[0]))
						.continentalness(mangroveSwamp.continentalnesses.toArray(new Climate.Parameter[0]))
						.erosion(mangroveSwamp.erosions.toArray(new Climate.Parameter[0]))
						.depth(mangroveSwamp.depths.toArray(new Climate.Parameter[0]))
						.weirdness(mangroveSwamp.weirdnesses.toArray(new Climate.Parameter[0]))
						.offset(mangroveSwamp.offsets.toArray(new Float[0]))
						.build();

				mangroveSwampPoints.forEach(point ->
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

			if (ClothConfigInteractionHandler.modifySwampPlacement()) {
				List<Climate.ParameterPoint> swampPoints = new ParameterUtils.ParameterPointListBuilder()
						.temperature(swamp.temperatures.toArray(new Climate.Parameter[0]))
						.humidity(swamp.humidities.toArray(new Climate.Parameter[0]))
						.continentalness(swamp.continentalnesses.toArray(new Climate.Parameter[0]))
						.erosion(swamp.erosions.toArray(new Climate.Parameter[0]))
						.depth(swamp.depths.toArray(new Climate.Parameter[0]))
						.weirdness(swamp.weirdnesses.toArray(new Climate.Parameter[0]))
						.offset(swamp.offsets.toArray(new Float[0]))
						.build();

				swampPoints.forEach(point ->
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
