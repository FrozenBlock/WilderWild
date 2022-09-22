package net.frozenblock.wilderwild.world.gen;

import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.biome.api.parameters.*;
import net.minecraft.world.level.biome.Climate;

import java.util.ArrayList;
import java.util.List;

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
        public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.WET, Humidity.HUMID);
        public static final Climate.Parameter CONTINENTALNESS = Continentalness.MUSHROOM_FIELDS;
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
