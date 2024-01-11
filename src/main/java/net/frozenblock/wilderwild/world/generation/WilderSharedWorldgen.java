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

package net.frozenblock.wilderwild.world.generation;

import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Contains Wilder Wild's worldgen data.
 */
public final class WilderSharedWorldgen {

	private WilderSharedWorldgen() {
		throw new UnsupportedOperationException("WilderSharedWorldgen contains only static declarations.");
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
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.465F, -0.300F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-0.105F, 0.050F);
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
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.485F, -0.465F);
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
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.465F, -0.255F);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.050F, 0.155F);
		public static final Climate.Parameter TEMPERATURE_WEIRD = Climate.Parameter.span(-0.465F, -0.425F);
		public static final Climate.Parameter HUMIDITY_WEIRD = Climate.Parameter.span(-0.105F, 0.100F);
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
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.485F, -0.465F);
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

	// PARAMETER POINTS

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

	public static void addBasicFeatures(@NotNull BiomeGenerationSettings.Builder builder, boolean commonSprings) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		if (commonSprings) {
			builder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
		} else {
			BiomeDefaultFeatures.addDefaultSprings(builder);
		}
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
	}
}
