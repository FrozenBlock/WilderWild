package net.frozenblock.wilderwild.world.generation;

import java.util.ArrayList;
import java.util.List;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Depth;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import static net.frozenblock.lib.worldgen.surface.FrozenSurfaceRules.*;
import net.frozenblock.lib.worldgen.surface.impl.BiomeTagConditionSource;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.generation.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.world.generation.noise.WilderNoise;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

/**
 * Contains Wilder Wild's worldgen data.
 */
public final class WilderSharedWorldgen {

	// DEPTHS
	public static final Climate.Parameter BOTTOM_DEPTH = Climate.Parameter.point(1.1F);
	public static final Climate.Parameter DEEP_DEPTH = Climate.Parameter.span(0.65F, 1.1F);
	public static final Climate.Parameter SEMI_DEEP_DEPTH = Climate.Parameter.span(0.4F, 1.0F);
	public static final Climate.Parameter SURFACE_DEPTH = Climate.Parameter.span(0.0F, 1.0F);

	// MODDED BIOME PARAMETERS

    public static final class CypressWetlands {
        public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.WARM);
        public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.NEUTRAL, Humidity.HUMID);
        public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-0.2F, 0.5F);
        public static final Climate.Parameter EROSION = Climate.Parameter.span(0.50F, 1.0F);
		public static final Climate.Parameter DEPTH = Depth.SURFACE;
		public static final List<Climate.Parameter> WEIRDNESS = new ArrayList<>() {{
			add(Weirdness.MID_SLICE_NORMAL_ASCENDING);
			add(Weirdness.MID_SLICE_NORMAL_DESCENDING);
			add(Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			add(Weirdness.VALLEY);
			add(Weirdness.LOW_SLICE_VARIANT_ASCENDING);
			add(Weirdness.MID_SLICE_VARIANT_ASCENDING);
			add(Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}};
        public static final float OFFSET = 0.0F;
    }

    public static final class MixedForest {
        public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.NEUTRAL);
        public static final Climate.Parameter HUMIDITY = Humidity.FULL_RANGE;
        public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.INLAND, Continentalness.FAR_INLAND);
        public static final Climate.Parameter LOW_EROSION = Erosion.EROSION_2;
        public static final Climate.Parameter MID_EROSION = Erosion.EROSION_1;
		public static final List<Climate.Parameter> LOW_WEIRDNESS = new ArrayList<>() {{
			add(Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			add(Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}};
		public static final List<Climate.Parameter> MID_WEIRDNESS = new ArrayList<>() {{
			add(Weirdness.MID_SLICE_NORMAL_ASCENDING);
			add(Weirdness.MID_SLICE_NORMAL_DESCENDING);
			add(Weirdness.MID_SLICE_VARIANT_ASCENDING);
			add(Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}};
		public static final List<Climate.Parameter> WEIRDNESS = new ArrayList<>() {{
			addAll(LOW_WEIRDNESS);
			addAll(MID_WEIRDNESS);
		}};
		public static final Climate.Parameter DEPTH = Depth.SURFACE;
        public static final float OFFSET = 0.0F;
    }

	public static final class BirchTaiga {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.NEUTRAL);
		public static final Climate.Parameter HUMIDITY = Humidity.WET;
		public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.INLAND, Continentalness.FAR_INLAND);
		public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_2, Erosion.EROSION_5);
		public static final List<Climate.Parameter> LOW_WEIRDNESS = new ArrayList<>() {{
			add(Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			add(Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}};
		public static final List<Climate.Parameter> MID_WEIRDNESS = new ArrayList<>() {{
			add(Weirdness.MID_SLICE_NORMAL_ASCENDING);
			add(Weirdness.MID_SLICE_NORMAL_DESCENDING);
			add(Weirdness.MID_SLICE_VARIANT_ASCENDING);
			add(Weirdness.MID_SLICE_VARIANT_DESCENDING);
		}};
		public static final List<Climate.Parameter> WEIRDNESS = new ArrayList<>() {{
			addAll(LOW_WEIRDNESS);
			addAll(MID_WEIRDNESS);
		}};
		public static final Climate.Parameter DEPTH = Depth.SURFACE;
		public static final float OFFSET = 0.0F;
	}

    public static final class JellyfishCaves {
        public static final Climate.Parameter TEMPERATURE = Temperature.FULL_RANGE;
        public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.DRY, Humidity.HUMID);
        public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-1.2F, -0.749F);
        public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_4, Erosion.EROSION_6);
		public static final Climate.Parameter DEPTH = SEMI_DEEP_DEPTH;
		public static final Climate.Parameter WEIRDNESS = Weirdness.FULL_RANGE;
        public static final List<Climate.Parameter> WEIRDNESS_LIST = List.of(WEIRDNESS);
        public static final float OFFSET = 0.0F;
    }

	public static final class WarmRiver {
		public static final Climate.Parameter WARM_RANGE = Climate.Parameter.span(0.55F, 1.0F);
		public static final Climate.Parameter UNFROZEN_NOT_WARM_RANGE = Climate.Parameter.span(0.0F, 0.2F);
		public static final Climate.Parameter HUMIDITY_TO_TWO = Climate.Parameter.span(-0.1F, 0.0F);
		public static final Climate.Parameter HUMIDITY_TO_THREE = Climate.Parameter.span(0F, 0.1F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-0.1F, 0.1F);
		public static final Climate.Parameter WEIRDNESS = Weirdness.VALLEY;
	}

	public static final class Oasis {
		public static final Climate.Parameter WARM_RANGE = Temperature.HOT;
		public static final Climate.Parameter HUMIDITY_DRY = Humidity.DRY;
		public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.COAST, Continentalness.FAR_INLAND);
		public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_3, Erosion.EROSION_4);
		public static final Climate.Parameter DEPTH = Depth.SURFACE;
		public static final List<Climate.Parameter> WEIRDNESS = new ArrayList<>() {{
			add(Weirdness.LOW_SLICE_NORMAL_DESCENDING);
			add(Weirdness.VALLEY);
			add(Weirdness.LOW_SLICE_VARIANT_ASCENDING);
		}};
		public static final float OFFSET = 0.0F;
	}

    public static final class Swamp {
        public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Climate.Parameter.span(-0.2F, 0.1F), Humidity.WET);
        public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.WARM);

	}

	public static final class MangroveSwamp {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.NEUTRAL, Temperature.HOT);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Climate.Parameter.span(0.05F, 0.1F), Humidity.HUMID);
	}

    // SURFACE RULES

    public static SurfaceRules.RuleSource cypressSurfaceRules() {
        return SurfaceRules.ifTrue(
				SurfaceRules.isBiome(RegisterWorldgen.CYPRESS_WETLANDS),
				SurfaceRules.ifTrue(
						SurfaceRules.ON_FLOOR,
						SurfaceRules.ifTrue(
								SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0),
								SurfaceRules.ifTrue(
										SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
										SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER)
								)
						)
				)
        );
    }

	public static SurfaceRules.RuleSource warmRiverRules() {
		return SurfaceRules.ifTrue(
				SurfaceRules.isBiome(RegisterWorldgen.WARM_RIVER),
				SurfaceRules.ifTrue(
						SurfaceRules.yBlockCheck(VerticalAnchor.absolute(32), 0),
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(
										SurfaceRules.DEEP_UNDER_FLOOR,
										SAND
								),
								SANDSTONE
						)
				)
		);
	}

	public static SurfaceRules.RuleSource oasisRules() {
		return SurfaceRules.ifTrue(
				SurfaceRules.isBiome(RegisterWorldgen.OASIS),
				SurfaceRules.sequence(
						SurfaceRules.ifTrue(
								SurfaceRules.ON_FLOOR,
								SurfaceRules.ifTrue(
										SurfaceRules.waterBlockCheck(-1, 0),
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(
														SurfaceRules.ON_CEILING,
														SANDSTONE
												),
												SAND
										)
								)
						),
						SurfaceRules.ifTrue(
								SurfaceRules.waterStartCheck(-6, -1),
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.UNDER_FLOOR,
												SurfaceRules.sequence(
														SurfaceRules.ifTrue(
																SurfaceRules.ON_CEILING,
																SANDSTONE
														),
														SAND
												)
										),
										SurfaceRules.ifTrue(
												SurfaceRules.VERY_DEEP_UNDER_FLOOR,
												SANDSTONE
										)
								)
						)
				)
		);
	}

	public static SurfaceRules.RuleSource betaBeaches() {
		return SurfaceRules.ifTrue(
				BetaBeachConditionSource.betaBeachConditionSource(1),
				SurfaceRules.sequence(
						gravelBetaBeaches(),
						sandBetaBeaches(),
						multilayerSandBetaBeaches()
				)
		);
	}

	public static SurfaceRules.RuleSource gravelBetaBeaches() {
		return SurfaceRules.ifTrue(
				BiomeTagConditionSource.isBiomeTag(WilderBiomeTags.GRAVEL_BEACH),
				SurfaceRules.ifTrue(
						SurfaceRules.UNDER_FLOOR,
						SurfaceRules.ifTrue(
								SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
								SurfaceRules.ifTrue(
										SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(WilderNoise.GRAVEL_BEACH_KEY, 0.12, 1.7976931348623157E308),
												GRAVEL
										)
								)
						)
				)
		);
	}

	public static SurfaceRules.RuleSource sandBetaBeaches() {
		return SurfaceRules.ifTrue(
				BiomeTagConditionSource.isBiomeTag(WilderBiomeTags.SAND_BEACHES),
				SurfaceRules.ifTrue(
						SurfaceRules.DEEP_UNDER_FLOOR,
						SurfaceRules.ifTrue(
								SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
								SurfaceRules.ifTrue(
										SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
												SAND
										)
								)
						)
				)
		);
	}

	public static SurfaceRules.RuleSource multilayerSandBetaBeaches() {
		return SurfaceRules.ifTrue(
				BiomeTagConditionSource.isBiomeTag(WilderBiomeTags.MULTI_LAYER_SAND_BEACHES),
				SurfaceRules.ifTrue(
						SurfaceRules.DEEP_UNDER_FLOOR,
						SurfaceRules.ifTrue(
								SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
								SurfaceRules.ifTrue(
										SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
												SAND
										)
								)
						)
				)
		);
	}

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

    public static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

	// PARAMETER POINTS

    public static Climate.ParameterPoint bottomParameters(
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset
    ) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, BOTTOM_DEPTH, weirdness, offset);
    }

    public static Climate.ParameterPoint deepParameters(
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset
    ) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, DEEP_DEPTH, weirdness, offset);
    }

    public static Climate.ParameterPoint semiDeepParameters(
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset
    ) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, SEMI_DEEP_DEPTH, weirdness, offset);
    }

    public static Climate.ParameterPoint surfaceParameters(
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset
    ) {
        return Climate.parameters(temperature, humidity, continentalness, erosion, SURFACE_DEPTH, weirdness, offset);
    }
}
