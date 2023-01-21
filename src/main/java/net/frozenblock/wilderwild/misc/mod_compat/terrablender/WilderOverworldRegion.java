package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.lib_compat.terrablender.FrozenTerraBlenderCompat;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
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

			List<Climate.ParameterPoint> oldGrowthBirchForestPoints = FrozenTerraBlenderCompat.points(Biomes.OLD_GROWTH_BIRCH_FOREST);
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

			List<Climate.ParameterPoint> taigaPoints = FrozenTerraBlenderCompat.points(Biomes.TAIGA);
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

			List<Climate.ParameterPoint> flowerForestPoints = FrozenTerraBlenderCompat.points(Biomes.FLOWER_FOREST);
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

			List<Climate.ParameterPoint> swampPointsCypress = FrozenTerraBlenderCompat.points(Biomes.SWAMP);
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

			List<Climate.ParameterPoint> mangroveSwampPointsCypress = FrozenTerraBlenderCompat.points(Biomes.MANGROVE_SWAMP);
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

			List<Climate.ParameterPoint> dripstoneCavesPoints = FrozenTerraBlenderCompat.points(Biomes.DRIPSTONE_CAVES);
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

			List<Climate.ParameterPoint> desertPoints = FrozenTerraBlenderCompat.points(Biomes.DESERT);
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

			List<Climate.ParameterPoint> riverPoints = FrozenTerraBlenderCompat.points(Biomes.RIVER);
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
				List<Climate.ParameterPoint> mangroveSwampPoints = FrozenTerraBlenderCompat.points(Biomes.MANGROVE_SWAMP);
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
				List<Climate.ParameterPoint> swampPoints = FrozenTerraBlenderCompat.points(Biomes.SWAMP);

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
