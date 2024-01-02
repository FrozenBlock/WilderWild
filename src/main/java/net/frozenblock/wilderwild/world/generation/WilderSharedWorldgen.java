/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.generation;

import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Contains Wilder Wild's worldgen data.
 */
public final class WilderSharedWorldgen {
	// DEPTHS
	public static final Climate.Parameter BOTTOM_DEPTH = Climate.Parameter.point(1.1F);
	public static final Climate.Parameter DEEP_DEPTH = Climate.Parameter.span(0.65F, 1.1F);
	public static final Climate.Parameter SEMI_DEEP_DEPTH = Climate.Parameter.span(0.4F, 1.0F);
	public static final Climate.Parameter SURFACE_DEPTH = Climate.Parameter.span(0.0F, 1.0F);

	private WilderSharedWorldgen() {
		throw new UnsupportedOperationException("WilderSharedWorldgen contains only static declarations.");
	}

	// MODDED BIOME PARAMETERS

	@NotNull
	@Contract(value = "_, _, _, _, _, _ -> new", pure = true)
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

	@NotNull
	@Contract(value = "_, _, _, _, _, _ -> new", pure = true)
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

	@NotNull
	@Contract(value = "_, _, _, _, _, _ -> new", pure = true)
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

	@NotNull
	@Contract(value = "_, _, _, _, _, _ -> new", pure = true)
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

	public static final class CypressWetlands {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.WARM);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.NEUTRAL, Humidity.HUMID);
		public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-0.200F, 0.500F);
		public static final Climate.Parameter EROSION = Climate.Parameter.span(0.500F, 1.000F);
		public static final float OFFSET = 0.000F;
		public static final float TEMP = 0.6F;
		public static final float DOWNFALL = 0.7F;
		public static final int WATER_COLOR = 4552818;
		public static final int WATER_FOG_COLOR = 4552818;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(0.8F);
		public static final int FOLIAGE_COLOR = 5877296;
		public static final int GRASS_COLOR = 7979098;

		private CypressWetlands() {
			throw new UnsupportedOperationException("CypressWetlands contains only static declarations.");
		}
	}

	public static final class MixedForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.255F, -0.140F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.050F, 0.150F);
		public static final float TEMP = 0.5F;
		public static final float DOWNFALL = 0.7F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private MixedForest() {
			throw new UnsupportedOperationException("MixedForest contains only static declarations.");
		}
	}

	public static final class DyingForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.450F, -0.255F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-0.100F, 0.050F);
		public static final float TEMP = 0.35F;
		public static final float DOWNFALL = 0.65F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int GRASS_COLOR = 8955746;
		public static final int FOLIAGE_COLOR = 7703076;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private DyingForest() {
			throw new UnsupportedOperationException("DyingForest contains only static declarations.");
		}
	}

	public static final class SnowyDyingForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.485F, -0.450F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-0.105F, 0.050F);
		public static final float TEMP = 0.05F;
		public static final float DOWNFALL = 0.575F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int FOLIAGE_COLOR = 7703076;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private SnowyDyingForest() {
			throw new UnsupportedOperationException("SnowyDyingForest contains only static declarations.");
		}
	}

	public static final class DyingMixedForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.450F, -0.255F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.050F, 0.150F);
		public static final Climate.Parameter TEMPERATURE_WEIRD = Climate.Parameter.span(-0.450F, -0.425F);
		public static final Climate.Parameter HUMIDITY_WEIRD = Climate.Parameter.span(-0.100F, 0.100F);
		public static final float TEMP = 0.35F;
		public static final float DOWNFALL = 0.55F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int GRASS_COLOR = 8955746;
		public static final int FOLIAGE_COLOR = 7703076;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private DyingMixedForest() {
			throw new UnsupportedOperationException("DyingMixedForest contains only static declarations.");
		}
	}

	public static final class SnowyDyingMixedForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.485F, -0.450F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.050F, 0.155F);
		public static final Climate.Parameter HUMIDITY_WEIRD = Climate.Parameter.span(-0.105F, 0.155F);
		public static final float TEMP = -0.4F;
		public static final float DOWNFALL = 0.425F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int FOLIAGE_COLOR = 7703076;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private SnowyDyingMixedForest() {
			throw new UnsupportedOperationException("SnowyDyingMixedForest contains only static declarations.");
		}
	}

	public static final class BirchJungle {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(0.175F, 0.225F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.FOUR, Climate.Parameter.point(0.35F));
		public static final float TEMP = 0.825F;
		public static final float DOWNFALL = 0.85F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private BirchJungle() {
			throw new UnsupportedOperationException("BirchJungle contains only static declarations.");
		}
	}

	public static final class FlowerField {
		public static final Climate.Parameter TEMPERATURE_A = Climate.Parameter.span(-0.200F, -0.075F);
		public static final Climate.Parameter HUMIDITY_A = Humidity.ONE;
		public static final Climate.Parameter TEMPERATURE_B = Temperature.THREE;
		public static final Climate.Parameter HUMIDITY_B = Climate.Parameter.span(-0.400F, -0.300F);
		public static final Climate.Parameter HUMIDITY_AB = Climate.Parameter.span(-0.3675F, -0.3125F);
		public static final float TEMP = 0.8F;
		public static final float DOWNFALL = 0.5F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
		public static final int FOLIAGE_COLOR = 5877296;

		private FlowerField() {
			throw new UnsupportedOperationException("FlowerField contains only static declarations.");
		}
	}

	public static final class AridSavanna {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(0.525F, 0.575F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-1.000F, -0.125F);
		public static final float TEMP = 2.0F;
		public static final float DOWNFALL = 0.0F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private AridSavanna() {
			throw new UnsupportedOperationException("AridSavanna contains only static declarations.");
		}
	}

	public static final class ParchedForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Climate.Parameter.point(0.175F), Temperature.FOUR);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-0.150F, -0.050F);
		public static final float TEMP = 1.35F;
		public static final float DOWNFALL = 0.2F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private ParchedForest() {
			throw new UnsupportedOperationException("ParchedForest contains only static declarations.");
		}
	}

	public static final class AridForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(0.530F, 0.570F);
		public static final Climate.Parameter HUMIDITY = WorldgenConfig.get().biomePlacement.modifyJunglePlacement ? Climate.Parameter.span(-0.095F, 0.1F) : Climate.Parameter.span(-0.095F, 0.15F);
		public static final float TEMP = 1.75F;
		public static final float DOWNFALL = 0.05F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private AridForest() {
			throw new UnsupportedOperationException("AridForest contains only static declarations.");
		}
	}

	public static final class OldGrowthSnowySpruceTaiga {
		public static final Climate.Parameter TEMPERATURE = Temperature.ONE;
		public static final Climate.Parameter HUMIDITY = Humidity.HUMID;
		public static final float TEMP = -0.5F;
		public static final float DOWNFALL = 0.4F;
		public static final int WATER_COLOR = 4020182;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
		public static final int GRASS_COLOR = 8434839;
		public static final int FOLIAGE_COLOR = 6332795;

		private OldGrowthSnowySpruceTaiga() {
			throw new UnsupportedOperationException("OldGrowthSnowySpruceTaiga contains only static declarations.");
		}
	}

	public static final class BirchTaiga {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.175F, -0.125F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.100F, 0.325F);
		public static final float TEMP = 0.45F;
		public static final float DOWNFALL = 0.8F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private BirchTaiga() {
			throw new UnsupportedOperationException("BirchTaiga contains only static declarations.");
		}
	}

	public static final class DarkTaiga {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.175F, -0.125F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Climate.Parameter.point(0.275F), Humidity.HUMID);
		public static final float TEMP = 0.45F;
		public static final float DOWNFALL = 0.5F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private DarkTaiga() {
			throw new UnsupportedOperationException("DarkTaiga contains only static declarations.");
		}
	}

	public static final class DarkBirchForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.125F, 0.2F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.275F, 0.325F);
		public static final float TEMP = 0.65F;
		public static final float DOWNFALL = 0.7F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private DarkBirchForest() {
			throw new UnsupportedOperationException("DarkBirchForest contains only static declarations.");
		}
	}

	public static final class OldGrowthDarkForest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.200F, 0.200F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.350F, 1.000F);
		public static final float TEMP = 0.7F;
		public static final float DOWNFALL = 0.8F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private OldGrowthDarkForest() {
			throw new UnsupportedOperationException("OldGrowthDarkForest contains only static declarations.");
		}
	}

	public static final class SemiBirchForest {
		public static final Climate.Parameter TEMPERATURE_A = Temperature.THREE;
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.050F, 0.150F);
		public static final Climate.Parameter TEMPERATURE_B = Climate.Parameter.span(-0.140F, -0.100F);
		public static final float TEMP = 0.65F;
		public static final float DOWNFALL = 0.7F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private SemiBirchForest() {
			throw new UnsupportedOperationException("SemiBirchForest contains only static declarations.");
		}
	}

	public static final class TemperateRainforest {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.250F, -0.050F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.250F, 1.0F);
		public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_0, Erosion.EROSION_3);
		public static final float TEMP = 0.7F;
		public static final float DOWNFALL = 0.8F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
		public static final int FOLIAGE_COLOR = 4896834;

		private TemperateRainforest() {
			throw new UnsupportedOperationException("TemperateRainforest contains only static declarations.");
		}
	}

	public static final class Rainforest {
		public static final Climate.Parameter TEMPERATURE_A = Climate.Parameter.span(Temperature.NEUTRAL, Temperature.WARM);
		public static final Climate.Parameter HUMIDITY_A = Humidity.NEUTRAL;
		public static final Climate.Parameter CONTINENTALNESS_A = Climate.Parameter.span(Continentalness.INLAND, Continentalness.FAR_INLAND);
		public static final Climate.Parameter EROSION_A = Climate.Parameter.span(Erosion.EROSION_0, Erosion.EROSION_3);
		public static final Climate.Parameter WEIRDNESS_A = Climate.Parameter.span(-1.000F, 0.050F);
		public static final Climate.Parameter TEMPERATURE_B = Temperature.WARM;
		public static final Climate.Parameter HUMIDITY_B = Climate.Parameter.span(0.075F, 0.225F);
		public static final Climate.Parameter TEMPERATURE_C = Climate.Parameter.span(0.175F, 0.250F);
		public static final Climate.Parameter HUMIDITY_C = Climate.Parameter.span(0.075F, 0.225F);
		public static final float TEMP = 0.7F;
		public static final float DOWNFALL = 0.8F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
		public static final int FOLIAGE_COLOR = 4896834;

		private Rainforest() {
			throw new UnsupportedOperationException("Rainforest contains only static declarations.");
		}
	}

	public static final class JellyfishCaves {
		public static final Climate.Parameter TEMPERATURE = Temperature.FULL_RANGE;
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.DRY, Humidity.HUMID);
		public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-1.200F, -0.749F);
		public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_4, Erosion.EROSION_6);
		public static final Climate.Parameter WEIRDNESS = Weirdness.FULL_RANGE;
		public static final float OFFSET = 0.000F;
		public static final float TEMP = 0.4F;
		public static final float DOWNFALL = 0.4F;
		public static final int WATER_COLOR = 9817343;
		public static final int WATER_FOG_COLOR = 6069471;
		public static final int FOG_COLOR = 0;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(0.0F);

		private JellyfishCaves() {
			throw new UnsupportedOperationException("JellyfishCaves contains only static declarations.");
		}
	}

	// PARAMETER POINTS

	public static final class WarmRiver {
		public static final Climate.Parameter WARM_RANGE = Climate.Parameter.span(0.550F, 1.000F);
		public static final Climate.Parameter UNFROZEN_NOT_WARM_RANGE = Climate.Parameter.span(-0.450F, 0.200F);
		public static final Climate.Parameter HUMIDITY_TO_TWO = Climate.Parameter.span(-0.100F, 0.000F);
		public static final Climate.Parameter HUMIDITY_TO_THREE = Climate.Parameter.span(0.000F, 0.100F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-0.100F, 0.100F);
		public static final float TEMP = 1.5F;
		public static final float DOWNFALL = 0.15F;
		public static final int NEW_WATER_COLOR = 4566514;
		public static final int NEW_WATER_FOG_COLOR = 267827;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
		public static final int FOLIAGE_COLOR = 11445290;
		public static final int GRASS_COLOR = 12564309;

		private WarmRiver() {
			throw new UnsupportedOperationException("WarmRiver contains only static declarations.");
		}
	}

	public static final class WarmBeach {
		public static final float TEMP = 1.1F;
		public static final float DOWNFALL = 0.6F;
		public static final int WATER_COLOR = 4159204;
		public static final int WATER_FOG_COLOR = 329011;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);

		private WarmBeach() {
			throw new UnsupportedOperationException("WarmBeach contains only static declarations.");
		}
	}

	public static final class Oasis {
		public static final Climate.Parameter TEMPERATURE = Temperature.HOT;
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.THREE, Humidity.FIVE);
		public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.COAST, Continentalness.FAR_INLAND);
		public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_3, Erosion.EROSION_5);
		public static final float OFFSET = 0F;
		public static final float TEMP = 2.0F;
		public static final float DOWNFALL = 0.5F;
		public static final int WATER_COLOR = 3981763;
		public static final int WATER_FOG_COLOR = 270131;
		public static final int FOG_COLOR = 12638463;
		public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
		public static final int FOLIAGE_COLOR = 3193611;
		public static final int GRASS_COLOR = 8569413;

		private Oasis() {
			throw new UnsupportedOperationException("Oasis contains only static declarations.");
		}
	}

	public static final class Swamp {
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Climate.Parameter.span(-0.200F, 0.100F), Humidity.WET);
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.WARM);

		private Swamp() {
			throw new UnsupportedOperationException("Swamp contains only static declarations.");
		}

	}

	public static final class MangroveSwamp {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.NEUTRAL, Temperature.HOT);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Climate.Parameter.span(0.050F, 0.100F), Humidity.HUMID);

		private MangroveSwamp() {
			throw new UnsupportedOperationException("MangroveSwamp contains only static declarations.");
		}
	}

	public static final class CherryGrove {
		public static final Climate.Parameter TEMPERATURE = Temperature.NEUTRAL;
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.TWO, Humidity.FOUR);
		public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.INLAND, Continentalness.MID_INLAND);
		public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_4, Erosion.EROSION_5);
		public static final Climate.Parameter WEIRDNESS = Climate.Parameter.span(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		public static final float OFFSET = 0F;


		private CherryGrove() {
			throw new UnsupportedOperationException("Cherry Grove contains only static declarations.");
		}
	}

	public static final class StonyShoreTaiga {
		public static final Climate.Parameter TEMPERATURE = Temperature.COOL;
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.THREE, Humidity.FIVE);
		public static final Climate.Parameter CONTINENTALNESS = Continentalness.COAST;
		public static final Climate.Parameter EROSION = Erosion.EROSION_4;
		public static final float OFFSET = 0F;


		private StonyShoreTaiga() {
			throw new UnsupportedOperationException("Stony Shore Taiga contains only static declarations.");
		}
	}
}
