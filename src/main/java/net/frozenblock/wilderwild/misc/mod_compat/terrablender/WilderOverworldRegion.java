package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import com.mojang.datafixers.util.Pair;
import net.frozenblock.lib.worldgen.biome.api.parameters.FrozenHumidity;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.List;
import java.util.function.Consumer;

public class WilderOverworldRegion extends Region {
    public WilderOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {

            // how do you get the exact parameters of these?
            builder.replaceBiome(Biomes.FOREST, RegisterWorldgen.MIXED_FOREST);
            builder.replaceBiome(Biomes.DARK_FOREST, RegisterWorldgen.MIXED_FOREST);
            builder.replaceBiome(Biomes.FLOWER_FOREST, RegisterWorldgen.MIXED_FOREST);


            // DON'T CHANGE THIS GO DOWN LIKE 10 LINES IF YOU WANT TO CHANGE BIOME PARAMETERS
            List<Climate.ParameterPoint> swampPoints = new ParameterUtils.ParameterPointListBuilder()
                    .temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.COOL, ParameterUtils.Temperature.NEUTRAL))
                    .humidity(ParameterUtils.Humidity.FULL_RANGE)
                    .continentalness(ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.NEAR_INLAND, ParameterUtils.Continentalness.FAR_INLAND))
                    .erosion(ParameterUtils.Erosion.EROSION_6)
                    .depth(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR)
                    .weirdness(ParameterUtils.Weirdness.VALLEY, ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_DESCENDING)
                    .build();

            // REPLACE BIOME PARAMETERS HERE
            swampPoints.forEach(point -> {
                builder.replaceParameter(point,
                        new Climate.ParameterPoint(
                                ParameterUtils.Temperature.span(ParameterUtils.Temperature.COOL, ParameterUtils.Temperature.WARM),
                                ParameterUtils.Humidity.span(ParameterUtils.Humidity.NEUTRAL, ParameterUtils.Humidity.HUMID),
                                Climate.Parameter.span(-0.2F, 0.5F),
                                Climate.Parameter.span(0.50F, 1.0F),
                                point.depth(),
                                point.weirdness(),
                                Climate.quantizeCoord(0.0F)));

                builder.replaceBiome(point, RegisterWorldgen.CYPRESS_WETLANDS);
            });

            // DON'T CHANGE THIS GO DOWN LIKE 10 LINES IF YOU WANT TO CHANGE BIOME PARAMETERS
            List<Climate.ParameterPoint> mangroveSwampPoints = new ParameterUtils.ParameterPointListBuilder()
                    .temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.WARM, ParameterUtils.Temperature.HOT))
                    .humidity(ParameterUtils.Humidity.FULL_RANGE)
                    .continentalness(ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.NEAR_INLAND, ParameterUtils.Continentalness.FAR_INLAND))
                    .erosion(ParameterUtils.Erosion.EROSION_6)
                    .depth(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR)
                    .weirdness(ParameterUtils.Weirdness.VALLEY, ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_DESCENDING)
                    .build();


            // REPLACE BIOME PARAMETERS HERE
            mangroveSwampPoints.forEach(point -> {

                builder.replaceParameter(point,
                        new Climate.ParameterPoint(
                                ParameterUtils.Temperature.span(ParameterUtils.Temperature.COOL, ParameterUtils.Temperature.WARM),
                                ParameterUtils.Humidity.span(ParameterUtils.Humidity.NEUTRAL, ParameterUtils.Humidity.HUMID),
                                Climate.Parameter.span(-0.2F, 0.5F),
                                Climate.Parameter.span(0.50F, 1.0F),
                                point.depth(),
                                point.weirdness(),
                                Climate.quantizeCoord(0.0F)
                        )
                );

                builder.replaceBiome(point, RegisterWorldgen.CYPRESS_WETLANDS);
            });

            List<Climate.ParameterPoint> dripstoneCavesPoints = new ParameterUtils.ParameterPointListBuilder()
                    .temperature(ParameterUtils.Temperature.FULL_RANGE)
                    .humidity(ParameterUtils.Humidity.FULL_RANGE)
                    .continentalness(Climate.Parameter.span(0.8F, 1.0F))
                    .erosion(ParameterUtils.Erosion.FULL_RANGE)
                    .depth(Climate.Parameter.span(0.2F, 0.9F))
                    .weirdness(ParameterUtils.Weirdness.FULL_RANGE)
                    .offset(0.0F)
                    .build();

            dripstoneCavesPoints.forEach(point -> {

                builder.replaceParameter(point,
                        new Climate.ParameterPoint(
                                ParameterUtils.Temperature.FULL_RANGE.parameter(),
                                ParameterUtils.Humidity.span(ParameterUtils.Humidity.NEUTRAL, ParameterUtils.Humidity.HUMID),
                                ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.DEEP_OCEAN, ParameterUtils.Continentalness.OCEAN),
                                ParameterUtils.Erosion.span(ParameterUtils.Erosion.EROSION_3, ParameterUtils.Erosion.EROSION_6),
                                Climate.Parameter.span(0.4F, 1.05F),
                                ParameterUtils.Weirdness.FULL_RANGE.parameter(),
                                Climate.quantizeCoord(0.0F)
                        )
                );

                builder.replaceBiome(point, RegisterWorldgen.JELLYFISH_CAVES);
            });

            if (ClothConfigInteractionHandler.modifyMangroveSwampPlacement()) {
                List<Climate.ParameterPoint> mangroveSwampPoints2 = new ParameterUtils.ParameterPointListBuilder()
                        .temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.WARM, ParameterUtils.Temperature.HOT))
                        .humidity(ParameterUtils.Humidity.FULL_RANGE)
                        .continentalness(ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.NEAR_INLAND, ParameterUtils.Continentalness.FAR_INLAND))
                        .erosion(ParameterUtils.Erosion.EROSION_6)
                        .depth(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR)
                        .weirdness(ParameterUtils.Weirdness.VALLEY, ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_DESCENDING)
                        .build();

                mangroveSwampPoints2.forEach(point -> {

                    builder.replaceParameter(point,
                            new Climate.ParameterPoint(
                                    ParameterUtils.Temperature.span(ParameterUtils.Temperature.NEUTRAL, ParameterUtils.Temperature.HOT),
                                    Climate.Parameter.span(FrozenHumidity.humidities[RegisterWorldgen.SWAMP_HUMIDITY], ParameterUtils.Humidity.HUMID.parameter()),
                                    point.continentalness(),
                                    point.erosion(),
                                    ParameterUtils.Depth.span(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR),
                                    point.weirdness(),
                                    point.offset()
                            )
                    );
                });
            }

            if (ClothConfigInteractionHandler.modifySwampPlacement()) {
                List<Climate.ParameterPoint> swampPoints2 = new ParameterUtils.ParameterPointListBuilder()
                        .temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.COOL, ParameterUtils.Temperature.NEUTRAL))
                        .humidity(ParameterUtils.Humidity.FULL_RANGE)
                        .continentalness(ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.NEAR_INLAND, ParameterUtils.Continentalness.FAR_INLAND))
                        .erosion(ParameterUtils.Erosion.EROSION_6)
                        .depth(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR)
                        .weirdness(ParameterUtils.Weirdness.VALLEY, ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_VARIANT_DESCENDING)
                        .build();

                swampPoints2.forEach(point -> {
                    builder.replaceParameter(point,
                            new Climate.ParameterPoint(
                                    ParameterUtils.Temperature.span(ParameterUtils.Temperature.COOL, ParameterUtils.Temperature.WARM),
                                    Climate.Parameter.span(FrozenHumidity.humidities[RegisterWorldgen.SWAMP_HUMIDITY], ParameterUtils.Humidity.HUMID.parameter()),
                                    point.continentalness(),
                                    point.erosion(),
                                    ParameterUtils.Depth.span(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR),
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
