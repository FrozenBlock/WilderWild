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

package net.frozenblock.wilderwild.worldgen;

import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;

public final class WWSharedWorldgen {
	private WWSharedWorldgen() {
		throw new UnsupportedOperationException("WWSharedWorldgen contains only static declarations.");
	}

	public static final int STOCK_FOG_COLOR = 12638463;
	public static final int COLD_WATER_COLOR = 4020182;
	public static final int COLD_WATER_FOG_COLOR = 329011;
	public static final int STOCK_WATER_COLOR = 4159204;
	public static final int STOCK_WATER_FOG_COLOR = 329011;
	public static final int WARM_WATER_COLOR = 4566514;
	public static final int WARM_WATER_FOG_COLOR = 267827;

	public static final class Swamp {
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Climate.Parameter.span(-0.200F, 0.100F), Humidity.WET);
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.COOL, Temperature.WARM);

		private Swamp() {
			throw new UnsupportedOperationException("Swamp contains only static declarations.");
		}
	}

	public static final class MangroveSwamp {
		public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.NEUTRAL, Temperature.HOT);
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Climate.Parameter.point(0.050F), Humidity.HUMID);

		private MangroveSwamp() {
			throw new UnsupportedOperationException("MangroveSwamp contains only static declarations.");
		}
	}

	public static final class CherryGrove {
		public static final Climate.Parameter TEMPERATURE = Temperature.NEUTRAL;
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.DRY, Humidity.WET);
		public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.INLAND, Continentalness.MID_INLAND);
		public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_4, Erosion.EROSION_5);
		public static final Climate.Parameter WEIRDNESS = Climate.Parameter.span(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING);
		public static final float OFFSET = 0F;

		private CherryGrove() {
			throw new UnsupportedOperationException("CherryGrove contains only static declarations.");
		}
	}

	public static final class StonyShoreTaiga {
		public static final Climate.Parameter TEMPERATURE = Temperature.COOL;
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.NEUTRAL, Humidity.HUMID);
		public static final Climate.Parameter CONTINENTALNESS = Continentalness.COAST;
		public static final Climate.Parameter EROSION = Erosion.EROSION_4;

		private StonyShoreTaiga() {
			throw new UnsupportedOperationException("StonyShoreTaiga contains only static declarations.");
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
