package net.frozenblock.wilderwild.world.gen;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.biome.api.parameters.*;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.gen.noise.WilderNoise;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.lib.worldgen.surface.FrozenSurfaceRules.*;

public final class SharedWorldgen {

    public static final class CypressWetlands {
        public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.WARM);
        public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.NEUTRAL, Humidity.HUMID);
        public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-0.2F, 0.5F);
        public static final Climate.Parameter EROSION = Climate.Parameter.span(0.50F, 1.0F);
        public static final float OFFSET = 0.0F;
        public static final ArrayList<Climate.Parameter> WEIRDNESS = new ArrayList<>() {{
            add(Weirdness.MID_SLICE_NORMAL_ASCENDING);
            add(Weirdness.MID_SLICE_NORMAL_DESCENDING);
            add(Weirdness.LOW_SLICE_NORMAL_DESCENDING);
            add(Weirdness.VALLEY);
            add(Weirdness.LOW_SLICE_VARIANT_ASCENDING);
            add(Weirdness.MID_SLICE_VARIANT_ASCENDING);
            add(Weirdness.MID_SLICE_VARIANT_DESCENDING);
        }};

        public static List<Climate.ParameterPoint> parameters() {
            ImmutableList.Builder<Climate.ParameterPoint> builder = new ImmutableList.Builder<>();
            WEIRDNESS.forEach(weirdness -> {
                builder.add(surfaceParameters(TEMPERATURE, HUMIDITY, CONTINENTALNESS, EROSION, weirdness, OFFSET));
            });
            return builder.build();
        }
    }

    public static final class MixedForest {
        public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.NEUTRAL);
        public static final Climate.Parameter HUMIDITY = Humidity.FULL_RANGE;
        public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.INLAND, Continentalness.FAR_INLAND);
        public static final Climate.Parameter LOW_EROSION = Erosion.EROSION_2;
        public static final Climate.Parameter MID_EROSION = Erosion.EROSION_1;
        public static final float OFFSET = 0.0F;
    }

    public static final class JellyfishCaves {
        public static final Climate.Parameter TEMPERATURE = Temperature.FULL_RANGE;
        public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.DRY, Humidity.HUMID);
        public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-1.2F, -0.749F);
        public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_4, Erosion.EROSION_6);
        public static final Climate.Parameter WEIRDNESS = Weirdness.FULL_RANGE;
        public static final float OFFSET = 0.0F;
    }

    public static final class Swamp {

        public static final Climate.Parameter SWAMP_HUMIDITY = Climate.Parameter.span(Humidity.NEUTRAL, Humidity.HUMID);

        public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.WARM);

        public static final class MangroveSwamp {

            public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.NEUTRAL, Temperature.HOT);
        }
    }

    // SURFACE RULES

    public static SurfaceRules.RuleSource cypressSurfaceRules() {
        return SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(RegisterWorldgen.CYPRESS_WETLANDS),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
                                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER)
                                        )
                                )
                        )
                )
        );
    }
    public static SurfaceRules.RuleSource betaBeachSurfaceRules() {
        var gravel = SurfaceRules.ifTrue(
                SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.TAIGA, Biomes.FROZEN_RIVER, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA, RegisterWorldgen.MIXED_FOREST),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
                                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoise.GRAVEL_BEACH_KEY, 0.12, 1.7976931348623157E308), GRAVEL)
                                        )
                                )
                        )
                )
        );

        var sand = SurfaceRules.sequence(SurfaceRules.ifTrue(
                SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.DARK_FOREST),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
                                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308), SAND)
                                        )
                                )
                        )
                )
        ), SurfaceRules.sequence(SurfaceRules.ifTrue(
                SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SAVANNA),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
                                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308), SAND)
                                        )
                                )
                        )
                )
        )));

        var betaBeaches = SurfaceRules.sequence(gravel, sand);

        //SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.JUNGLE),
        // SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
        // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65),0),
        // SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.steep()),
        // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH, 0.12, 1.7976931348623157E308),
        // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
        // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(3, false, CaveSurface.CEILING),SANDSTONE), SAND})),
        // SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1),
        // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
        // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.ON_CEILING,
        // SANDSTONE), SAND})), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SANDSTONE)}))}))})))})))}));
        return betaBeaches;
    }

    public static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static Climate.ParameterPoint bottomParameters(
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset
    ) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.1F), weirdness, offset);
    }

    public static Climate.ParameterPoint deepParameters(
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset
    ) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.65F, 1.1F), weirdness, offset);
    }

    public static Climate.ParameterPoint semiDeepParameters(
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset
    ) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.4F, 1.0F), weirdness, offset);
    }

    public static Climate.ParameterPoint surfaceParameters(
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset
    ) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.0F, 1.0F), weirdness, offset);
    }
}
