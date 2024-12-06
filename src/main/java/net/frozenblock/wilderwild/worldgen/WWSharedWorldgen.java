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

import net.frozenblock.lib.worldgen.biome.api.parameters.BiomeParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Depth;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.TWO, Humidity.FOUR);
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
		public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.THREE, Humidity.FIVE);
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

	// TODO: Remove this and use FrozenLib's once multiloader.
	/**
	 * Returns parameters for a border between two consecutive {@link Climate.Parameter}s, with the amount of space taken up per-parameter dictated by its span.
	 * <p>
	 * The first parameter's max point must be equal to the second parameter's min point.
	 * @param firstParameter The first {@link Climate.Parameter}.
	 * @param secondParameter The second {@link Climate.Parameter}.
	 * @param percentagePerSlot The percentage of space taken up per-parameter based on its span.
	 * @return a border between two consecutive {@link Climate.Parameter}s, with the amount of space taken up per-parameter dictated by its span.
	 */
	@Contract(value = "_, _, _ -> new", pure = true)
	public static Climate.@NotNull Parameter makeParameterBorder(
		Climate.@NotNull Parameter firstParameter, Climate.@NotNull Parameter secondParameter, float percentagePerSlot
	) {
		long border = firstParameter.max();
		long secondMin = secondParameter.min();
		if (border != secondMin)
			throw new IllegalArgumentException("FrozenLib: Cannot run makeParameterBorder when firstParameter's max is not equal to secondParameter's min!");

		long firstWidth = firstParameter.max() - firstParameter.min();
		long secondWidth = secondParameter.max() - secondParameter.min();
		return new Climate.Parameter((long) (border - (firstWidth * percentagePerSlot)), (long) (border + (secondWidth * percentagePerSlot)));
	}

	/**
	 * Finds borders between two {@link net.minecraft.world.level.biome.Climate.ParameterPoint}s, ignoring {@link Depth} and {@link Weirdness}.
	 * @param point1 The first {@link net.minecraft.world.level.biome.Climate.ParameterPoint} to find borders between.
	 * @param point2 The second {@link net.minecraft.world.level.biome.Climate.ParameterPoint} to find borders between.
	 * @param percentagePerSlot The percentage per "slot" of worldgen noise the border should take up.
	 * @return A {@link List} of found borders, ignoring {@link Depth} and {@link Weirdness}.
	 */
	public static @NotNull List<Climate.ParameterPoint> findBorderParameters(
		Climate.@NotNull ParameterPoint point1,
		Climate.@NotNull ParameterPoint point2,
		float percentagePerSlot
	) {
		List<Climate.ParameterPoint> borders = new ArrayList<>();

		List<Climate.Parameter> temperatures = findBorderParameters(point1.temperature(), point2.temperature(), percentagePerSlot);
		if (!temperatures.isEmpty()) {
			List<Climate.Parameter> humidities = findBorderParameters(point1.humidity(), point2.humidity(), percentagePerSlot);
			if (!humidities.isEmpty()) {
				List<Climate.Parameter> continentalnesses = findBorderParameters(point1.continentalness(), point2.continentalness(), percentagePerSlot);
				if (!continentalnesses.isEmpty()) {
					List<Climate.Parameter> erosions = findBorderParameters(point1.erosion(), point2.erosion(), percentagePerSlot);
					if (!erosions.isEmpty()) {
						long offset = (long) ((point1.offset() + point2.offset()) * 0.5D);
						temperatures.forEach(temperature ->
							humidities.forEach(humidity ->
								continentalnesses.forEach(continentalness ->
									erosions.forEach(erosion ->
										borders.add(
											new Climate.ParameterPoint(
												temperature,
												humidity,
												continentalness,
												erosion,
												OverworldBiomeBuilderParameters.FULL_RANGE,
												OverworldBiomeBuilderParameters.FULL_RANGE,
												offset
											)
										)
									)
								)
							)
						);
					}
				}
			}
		}
		return borders;
	}

	/**
	 * Finds borders between two {@link List}s of {@link net.minecraft.world.level.biome.Climate.ParameterPoint}s, ignoring {@link Depth} and {@link Weirdness}.
	 * <p>
	 * This is best used alongside {@link OverworldBiomeBuilderParameters#getParameters(net.minecraft.resources.ResourceKey)}
	 * @param pointList1 The first {@link List} of {@link net.minecraft.world.level.biome.Climate.ParameterPoint}s to find borders between.
	 * @param pointList2 The second {@link List} of {@link net.minecraft.world.level.biome.Climate.ParameterPoint}s to find borders between.
	 * @param percentagePerSlot The percentage per "slot" of worldgen noise the border should take up.
	 * @return A {@link List} of found borders, ignoring {@link Depth} and {@link Weirdness}.
	 */
	public static @NotNull List<Climate.ParameterPoint> findBorderParameters(
		@NotNull List<Climate.ParameterPoint> pointList1,
		@NotNull List<Climate.ParameterPoint> pointList2,
		float percentagePerSlot
	) {
		List<Climate.ParameterPoint> borders = new ArrayList<>();
		pointList1.forEach(point1 -> pointList2.forEach(point2 -> borders.addAll(findBorderParameters(point1, point2, percentagePerSlot))));
		return borders;
	}

	/**
	 * Finds borders between two {@link net.minecraft.world.level.biome.Climate.Parameter}s.
	 * <p>
	 * Do note that this keeps intersections between {@link net.minecraft.world.level.biome.Climate.Parameter}s intact.
	 * @param firstParameter The first {@link net.minecraft.world.level.biome.Climate.Parameter} to find a border between.
	 * @param secondParameter The second {@link net.minecraft.world.level.biome.Climate.Parameter} to find a border between.
	 * @param percentagePerSlot The percentage per "slot" of worldgen noise the border should take up.
	 * @return A {@link List} of found borders and intersections.
	 */
	public static @NotNull List<Climate.Parameter> findBorderParameters(
		Climate.@NotNull Parameter firstParameter, Climate.@NotNull Parameter secondParameter, float percentagePerSlot
	) {
		List<Climate.Parameter> borders = new ArrayList<>();
		if (firstParameter.equals(secondParameter)) return List.of(firstParameter);

		List<Climate.Parameter> splitFirstParam = splitParameter(firstParameter, secondParameter);
		List<Climate.Parameter> splitSecondParam = splitParameter(secondParameter, firstParameter);

		splitFirstParam.forEach(parameter1 ->
			splitSecondParam.forEach(parameter2 -> {
				if (parameter1.equals(parameter2) && !borders.contains(parameter1)) {
					borders.add(parameter1);
				} else {
					try {
						Climate.Parameter borderParameter = makeParameterBorder(parameter1, parameter2, percentagePerSlot);
						if (!borders.contains(borderParameter)) borders.add(borderParameter);
					} catch (IllegalArgumentException ignored) {}
					try {
						Climate.Parameter borderParameter = makeParameterBorder(parameter2, parameter1, percentagePerSlot);
						if (!borders.contains(borderParameter)) borders.add(borderParameter);
					} catch (IllegalArgumentException ignored) {}
				}
			})
		);

		return borders;
	}
	/**
	 * Splits a {@link net.minecraft.world.level.biome.Climate.Parameter} into multiple {@link net.minecraft.world.level.biome.Climate.Parameter}s, according to a reference.
	 * <p>
	 * Do note that this keeps intersections between {@link net.minecraft.world.level.biome.Climate.Parameter}s intact.
	 * @param parameter The first {@link net.minecraft.world.level.biome.Climate.Parameter}.
	 * @param referenceParameter The {@link net.minecraft.world.level.biome.Climate.Parameter} to reference and create borders with.
	 * @return A {@link List} of split-up {@link net.minecraft.world.level.biome.Climate.Parameter}s based on the given reference {@link net.minecraft.world.level.biome.Climate.Parameter}.
	 */
	@Contract(pure = true)
	public static @NotNull List<Climate.Parameter> splitParameter(Climate.@NotNull Parameter parameter, Climate.@NotNull Parameter referenceParameter) {
		List<Climate.Parameter> splitParameters = new ArrayList<>();

		long min = parameter.min();
		long max = parameter.max();

		long refMin = referenceParameter.min();
		long refMax = referenceParameter.max();

		if (min < refMin) {
			splitParameters.add(
				new Climate.Parameter(min, refMin)
			);
		}

		if (max > refMax) {
			splitParameters.add(
				new Climate.Parameter(refMax, max)
			);
		}

		if (min <= refMin && max >= refMax) {
			splitParameters.add(
				new Climate.Parameter(refMin, refMax)
			);
		}

		return splitParameters;
	}
}
