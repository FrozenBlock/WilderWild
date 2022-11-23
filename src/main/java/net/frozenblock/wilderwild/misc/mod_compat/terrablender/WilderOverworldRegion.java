package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Depth;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.generation.SharedWorldgen;
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
		/*List<Climate.ParameterPoint> cypressWetlands = new ParameterUtils.ParameterPointListBuilder()
				.temperature(SharedWorldgen.CypressWetlands.TEMPERATURE)
				.humidity(SharedWorldgen.CypressWetlands.HUMIDITY)
				.continentalness(SharedWorldgen.CypressWetlands.CONTINENTALNESS)
				.erosion(SharedWorldgen.CypressWetlands.EROSION)
				.depth(SharedWorldgen.CypressWetlands.DEPTH)
				.weirdness(SharedWorldgen.CypressWetlands.WEIRDNESS.toArray(new Climate.Parameter[0]))
				.offset(0.0F)
				.build();

		cypressWetlands.forEach(point -> {
			this.addBiome(
					mapper,
					point,
					RegisterWorldgen.CYPRESS_WETLANDS
			);
		});

		this.addBiome(
				mapper,
				SharedWorldgen.JellyfishCaves.TEMPERATURE,
				SharedWorldgen.JellyfishCaves.DEPTH,
				SharedWorldgen.JellyfishCaves.CONTINENTALNESS,
				SharedWorldgen.JellyfishCaves.EROSION,
				SharedWorldgen.JellyfishCaves.WEIRDNESS,
				SharedWorldgen.JellyfishCaves.DEPTH,
				0F,
				RegisterWorldgen.JELLYFISH_CAVES
		);

		List<Climate.ParameterPoint> lowMixedForest = new ParameterUtils.ParameterPointListBuilder()
				.temperature(SharedWorldgen.MixedForest.TEMPERATURE)
				.humidity(SharedWorldgen.MixedForest.HUMIDITY)
				.continentalness(SharedWorldgen.MixedForest.CONTINENTALNESS)
				.erosion(SharedWorldgen.MixedForest.LOW_EROSION)
				.weirdness(SharedWorldgen.MixedForest.LOW_WEIRDNESS.toArray(new Climate.Parameter[0]))
				.depth(SharedWorldgen.MixedForest.DEPTH)
				.offset(0.0F)
				.build();

		List<Climate.ParameterPoint> midMixedForest = new ParameterUtils.ParameterPointListBuilder()
				.temperature(SharedWorldgen.MixedForest.TEMPERATURE)
				.humidity(SharedWorldgen.MixedForest.HUMIDITY)
				.continentalness(SharedWorldgen.MixedForest.CONTINENTALNESS)
				.erosion(SharedWorldgen.MixedForest.MID_EROSION)
				.weirdness(SharedWorldgen.MixedForest.MID_WEIRDNESS.toArray(new Climate.Parameter[0]))
				.depth(SharedWorldgen.MixedForest.DEPTH)
				.offset(0.0F)
				.build();

		lowMixedForest.forEach(point -> {
			this.addBiome(
					mapper,
					point,
					RegisterWorldgen.MIXED_FOREST
			);
		});
		midMixedForest.forEach(point -> {
			this.addBiome(
					mapper,
					point,
					RegisterWorldgen.MIXED_FOREST
			);
		});*/
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {

			// This will not grant the exact parameters as defined in SharedWorldgen. Instead, it will replace a part of old growth birch forests with mixed forests.
			builder.replaceBiome(Biomes.OLD_GROWTH_BIRCH_FOREST, RegisterWorldgen.MIXED_FOREST);


			// DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF SWAMPS
			List<Climate.ParameterPoint> swampPointsCypress = new ParameterUtils.ParameterPointListBuilder()
					.temperature(Climate.Parameter.span(Temperature.COOL, Temperature.NEUTRAL))
					.humidity(Humidity.FULL_RANGE)
					.continentalness(Climate.Parameter.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
					.erosion(Erosion.EROSION_6)
					.depth(Depth.SURFACE, Depth.FLOOR)
					.weirdness(Weirdness.VALLEY, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
					.offset(0.0F)
					.build();

			swampPointsCypress.forEach(point -> {
				// CHANGE BIOME PARAMETERS HERE
				builder.replaceParameter(point,
						Climate.parameters(
								SharedWorldgen.CypressWetlands.TEMPERATURE,
								SharedWorldgen.CypressWetlands.HUMIDITY,
								SharedWorldgen.CypressWetlands.CONTINENTALNESS,
								SharedWorldgen.CypressWetlands.EROSION,
								point.depth(),
								point.weirdness(),
								SharedWorldgen.CypressWetlands.OFFSET
						)
				);

				builder.replaceBiome(point, RegisterWorldgen.CYPRESS_WETLANDS);
			});

			// DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF MANGROVE SWAMPS
			List<Climate.ParameterPoint> mangroveSwampPointsCypress = new ParameterUtils.ParameterPointListBuilder()
					.temperature(Climate.Parameter.span(Temperature.WARM, Temperature.HOT))
					.humidity(Humidity.FULL_RANGE)
					.continentalness(Climate.Parameter.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
					.erosion(Erosion.EROSION_6)
					.depth(Depth.SURFACE, Depth.FLOOR)
					.weirdness(Weirdness.VALLEY, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
					.offset(0.0F)
					.build();

			mangroveSwampPointsCypress.forEach(point -> {
				// REPLACE BIOME PARAMETERS HERE
				builder.replaceParameter(point,
						Climate.parameters(
								SharedWorldgen.CypressWetlands.TEMPERATURE,
								SharedWorldgen.CypressWetlands.HUMIDITY,
								SharedWorldgen.CypressWetlands.CONTINENTALNESS,
								SharedWorldgen.CypressWetlands.EROSION,
								point.depth(),
								point.weirdness(),
								SharedWorldgen.CypressWetlands.OFFSET
						)
				);

				builder.replaceBiome(point, RegisterWorldgen.CYPRESS_WETLANDS);
			});

			// DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF DRIPSTONE CAVES
			List<Climate.ParameterPoint> dripstoneCavesPoints = new ParameterUtils.ParameterPointListBuilder()
					.temperature(Temperature.FULL_RANGE)
					.humidity(Humidity.FULL_RANGE)
					.continentalness(Climate.Parameter.span(0.8F, 1.0F))
					.erosion(Erosion.FULL_RANGE)
					.depth(Climate.Parameter.span(0.2F, 0.9F))
					.weirdness(Weirdness.FULL_RANGE)
					.offset(0.0F)
					.build();

			dripstoneCavesPoints.forEach(point -> {
				// REPLACE BIOME PARAMETERS HERE
				builder.replaceParameter(point,
						SharedWorldgen.semiDeepParameters(
								SharedWorldgen.JellyfishCaves.TEMPERATURE,
								SharedWorldgen.JellyfishCaves.HUMIDITY,
								SharedWorldgen.JellyfishCaves.CONTINENTALNESS,
								SharedWorldgen.JellyfishCaves.EROSION,
								SharedWorldgen.JellyfishCaves.WEIRDNESS,
								SharedWorldgen.JellyfishCaves.OFFSET
						)
				);

				builder.replaceBiome(point, RegisterWorldgen.JELLYFISH_CAVES);
			});

            if (ClothConfigInteractionHandler.modifyMangroveSwampPlacement()) {
                // DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF MANGROVE SWAMPS
                List<Climate.ParameterPoint> mangroveSwampPoints = new ParameterUtils.ParameterPointListBuilder()
                        .temperature(Climate.Parameter.span(Temperature.WARM, Temperature.HOT))
                        .humidity(Humidity.FULL_RANGE)
                        .continentalness(Climate.Parameter.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                        .erosion(Erosion.EROSION_6)
                        .depth(Depth.SURFACE, Depth.FLOOR)
                        .weirdness(Weirdness.VALLEY, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
                        .offset(0.0F)
                        .build();

                mangroveSwampPoints.forEach(point -> {
                    // REPLACE BIOME PARAMETERS HERE
                    builder.replaceParameter(point,
                            new Climate.ParameterPoint(
                                    SharedWorldgen.MangroveSwamp.TEMPERATURE,
                                    SharedWorldgen.MangroveSwamp.HUMIDITY,
                                    point.continentalness(),
                                    point.erosion(),
                                    point.depth(),
                                    point.weirdness(),
                                    point.offset()
                            )
                    );
                });
            }

            if (ClothConfigInteractionHandler.modifySwampPlacement()) {
                // DON'T CHANGE THESE PARAMETERS. THESE ARE THE PARAMETERS OF SWAMPS
                List<Climate.ParameterPoint> swampPoints = new ParameterUtils.ParameterPointListBuilder()
                        .temperature(Climate.Parameter.span(Temperature.COOL, Temperature.NEUTRAL))
                        .humidity(Humidity.FULL_RANGE)
                        .continentalness(Climate.Parameter.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                        .erosion(Erosion.EROSION_6)
                        .depth(Depth.SURFACE, Depth.FLOOR)
                        .weirdness(Weirdness.VALLEY, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
                        .offset(0.0F)
                        .build();

                swampPoints.forEach(point -> {
                    // REPLACE BIOME PARAMETERS HERE
                    builder.replaceParameter(point,
                            new Climate.ParameterPoint(
                                    SharedWorldgen.Swamp.TEMPERATURE,
                                    SharedWorldgen.Swamp.HUMIDITY,
                                    point.continentalness(),
                                    point.erosion(),
                                    point.depth(),
                                    point.weirdness(),
                                    point.offset()
                            )
                    );
                });
            }

            /*List<Climate.ParameterPoint> forestPoints = new ParameterPointListBuilder()
                    .temperature(Temperature.COOL)
                    .humidity(Humidity.NEUTRAL)
                    .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.NEAR_INLAND))
                    .erosion(Erosion.span(Erosion.EROSION_2, Erosion.EROSION_3))
                    .depth(Depth.SURFACE, Depth.FLOOR)
                    .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING)
                    .offset(0.0F)
                    .build();

            forestPoints.forEach(point -> {
                builder.replaceBiome(point, RegisterWorldgen.MIXED_FOREST);
            });*/
        });
    }
}
