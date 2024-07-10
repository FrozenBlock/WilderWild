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

package net.frozenblock.wilderwild.registry;

import java.util.List;
import net.frozenblock.lib.worldgen.surface.api.FrozenSurfaceRules;
import net.frozenblock.lib.worldgen.surface.api.SurfaceRuleEvents;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.impl.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.world.impl.conditionsource.SnowUnderMountainConditionSource;
import net.frozenblock.wilderwild.world.impl.noise.WilderNoise;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.jetbrains.annotations.NotNull;

public final class RegisterSurfaceRules implements SurfaceRuleEvents.OverworldSurfaceRuleCallback, SurfaceRuleEvents.OverworldSurfaceRuleNoPrelimSurfaceCallback {

	@NotNull
	public static SurfaceRules.RuleSource cypressSurfaceRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.CYPRESS_WETLANDS),
			SurfaceRules.ifTrue(
				SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.yBlockCheck(
						VerticalAnchor.absolute(60),
						0
					),
					SurfaceRules.ifTrue(
						SurfaceRules.not(
							SurfaceRules.yBlockCheck(
								VerticalAnchor.absolute(63),
								0
							)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SWAMP, 0.0),
							FrozenSurfaceRules.WATER
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource fallingBlockAndSafeBlockRules(Block fallingBlock, Block safeBlock) {
		SurfaceRules.RuleSource fallingBlockSource = FrozenSurfaceRules.makeStateRule(fallingBlock);
		SurfaceRules.RuleSource safeBlockSource = FrozenSurfaceRules.makeStateRule(safeBlock);
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.ON_CEILING,
							safeBlockSource
						),
						fallingBlockSource
					)
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.waterStartCheck(-6, -1),
				SurfaceRules.sequence(
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.ON_CEILING,
							safeBlockSource
						),
						fallingBlockSource
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource warmRiverRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.WARM_RIVER),
			SurfaceRules.ifTrue(
				SurfaceRules.yBlockCheck(VerticalAnchor.absolute(32), 0),
				fallingBlockAndSafeBlockRules(Blocks.SAND, Blocks.SANDSTONE)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource desertAndBeachRules() {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.ON_CEILING,
							FrozenSurfaceRules.SANDSTONE
						),
						FrozenSurfaceRules.SAND
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
								FrozenSurfaceRules.SANDSTONE
							),
							FrozenSurfaceRules.SAND
						)
					),
					SurfaceRules.ifTrue(
						SurfaceRules.VERY_DEEP_UNDER_FLOOR,
						FrozenSurfaceRules.SANDSTONE
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource warmBeachRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.WARM_BEACH),
			desertAndBeachRules()
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource oasisRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.OASIS),
			desertAndBeachRules()
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource aridGrass() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.ARID_SAVANNA, RegisterWorldgen.ARID_FOREST),
			SurfaceRules.sequence(
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(Noises.SURFACE, 0.155, 0.3666),
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(
								SurfaceRules.ON_CEILING,
								FrozenSurfaceRules.DIRT
							),
							FrozenSurfaceRules.GRASS_BLOCK
						)
					)
				),
				SurfaceRules.ifTrue(
					SurfaceRules.noiseCondition(Noises.SURFACE, 0.155, 0.3666),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.UNDER_FLOOR,
							SurfaceRules.sequence(
								SurfaceRules.ifTrue(
									SurfaceRules.ON_CEILING,
									FrozenSurfaceRules.DIRT
								),
								FrozenSurfaceRules.DIRT
							)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.DEEP_UNDER_FLOOR,
							FrozenSurfaceRules.DIRT
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource aridRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.ARID_SAVANNA, RegisterWorldgen.ARID_FOREST),
			desertAndBeachRules()
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource oldGrowthSnowyTaigaRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 1.75D / 8.25D, Double.MAX_VALUE),
							FrozenSurfaceRules.COARSE_DIRT
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, -0.95D / 8.25D, Double.MAX_VALUE),
							FrozenSurfaceRules.PODZOL
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 0.0222, 0.055),
							FrozenSurfaceRules.POWDER_SNOW
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 0.065, 0.12),
							FrozenSurfaceRules.SNOW_BLOCK
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource oldGrowthDarkForestRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.OLD_GROWTH_DARK_FOREST),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, -0.0667, 0.04),
							FrozenSurfaceRules.PODZOL
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource temperateRainforestRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.TEMPERATE_RAINFOREST),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 0.095, 0.2),
							FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenSurfaceRules.makeStateRule(Blocks.MOSS_BLOCK)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, 0.0667, 0.4),
							FrozenSurfaceRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource rainforestRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.RAINFOREST),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenSurfaceRules.makeStateRule(Blocks.MOSS_BLOCK)
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource dyingForestRules() {
		return SurfaceRules.ifTrue(
				SurfaceRules.isBiome(RegisterWorldgen.DYING_FOREST, RegisterWorldgen.DYING_MIXED_FOREST),
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
						SurfaceRules.ifTrue(
								SurfaceRules.waterBlockCheck(-1, 0),
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(Noises.SURFACE, 0.033, 0.095),
												FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
										),
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, 0.0667, 0.1),
												FrozenSurfaceRules.COARSE_DIRT
										)
								)
						)
				)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource gravelBetaBeaches() {
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiomeTagOptimized(WilderBiomeTags.GRAVEL_BEACH),
			SurfaceRules.ifTrue(
				SurfaceRules.UNDER_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(WilderNoise.GRAVEL_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(Blocks.GRAVEL, Blocks.STONE)
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource sandBetaBeaches() {
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiomeTagOptimized(WilderBiomeTags.SAND_BEACHES),
			SurfaceRules.ifTrue(
				SurfaceRules.DEEP_UNDER_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource multiLayerSandBetaBeaches() {
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiomeTagOptimized(WilderBiomeTags.MULTI_LAYER_SAND_BEACHES),
			SurfaceRules.ifTrue(
				SurfaceRules.DEEP_UNDER_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource betaBeaches() {
		return SurfaceRules.ifTrue(
			BetaBeachConditionSource.betaBeachConditionSource(),
			SurfaceRules.sequence(
				gravelBetaBeaches(),
				sandBetaBeaches(),
				multiLayerSandBetaBeaches()
			)
		);
	}

	@Override
	public void addOverworldSurfaceRules(@NotNull List<SurfaceRules.RuleSource> context) {
		context.add(
			SurfaceRules.sequence(
				betaBeaches(),
				cypressSurfaceRules(),
				warmRiverRules(),
				warmBeachRules(),
				oasisRules(),
				aridGrass(),
				aridRules(),
				oldGrowthSnowyTaigaRules(),
				oldGrowthDarkForestRules(),
				temperateRainforestRules(),
				rainforestRules(),
				dyingForestRules()
			)
		);
		WilderConstants.log("Wilder Wild's Overworld Surface Rules have been added!", true);
	}

	@NotNull
	public static SurfaceRules.RuleSource frozenCavesSnow() {
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiome(List.of(RegisterWorldgen.FROZEN_CAVES)),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.not(SurfaceRules.verticalGradient("snow_gradient", VerticalAnchor.absolute(64), VerticalAnchor.absolute(72))),
					SurfaceRules.ifTrue(
						SurfaceRules.waterBlockCheck(0, 0),
						FrozenSurfaceRules.makeStateRule(Blocks.SNOW_BLOCK)
					)
				)
			)
		);
	}

	@NotNull
	public static SurfaceRules.RuleSource snowUnderMountains() {
		return SurfaceRules.ifTrue(
			SnowUnderMountainConditionSource.snowUnderMountainConditionSource(),
			SurfaceRules.ifTrue(
				FrozenSurfaceRules.isBiomeTagOptimized(WilderBiomeTags.BELOW_SURFACE_SNOW),
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.verticalGradient("snow_gradient", VerticalAnchor.absolute(64), VerticalAnchor.absolute(72))),
						SurfaceRules.ifTrue(
							SurfaceRules.waterBlockCheck(0, 0),
							FrozenSurfaceRules.makeStateRule(Blocks.SNOW_BLOCK)
						)
					)
				)
			)
		);
	}

	@Override
	public void addOverworldNoPrelimSurfaceRules(@NotNull List<SurfaceRules.RuleSource> context) {
		context.add(
			SurfaceRules.sequence(
				frozenCavesSnow(),
				snowUnderMountains()
			)
		);
		WilderConstants.log("Wilder Wild's No Preliminary Surface Overworld Surface Rules have been added!", true);
	}
}
