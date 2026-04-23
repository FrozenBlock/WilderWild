/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.google.common.collect.ImmutableList;
import net.frozenblock.lib.worldgen.surface.api.FrozenSurfaceRules;
import net.frozenblock.lib.worldgen.surface.api.SurfaceRuleEvents;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.worldgen.impl.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.worldgen.impl.conditionsource.SnowUnderMountainConditionSource;
import net.frozenblock.wilderwild.worldgen.impl.conditionsource.SulfurCavesCalciteConditionSource;
import net.frozenblock.wilderwild.worldgen.impl.noise.WWNoise;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public final class WWSurfaceRules implements SurfaceRuleEvents.OverworldSurfaceRuleCallback, SurfaceRuleEvents.OverworldSurfaceRuleNoPrelimSurfaceCallback {

	public static SurfaceRules.RuleSource cypressSurfaceRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.CYPRESS_WETLANDS),
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

	public static SurfaceRules.RuleSource warmRiverRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.WARM_RIVER),
			SurfaceRules.sequence(
			desertAndBeachRules(),
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, FrozenSurfaceRules.SANDSTONE),
						FrozenSurfaceRules.SAND
					)
				)
			)
		);
	}

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

	public static SurfaceRules.RuleSource warmBeachRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.WARM_BEACH),
			desertAndBeachRules()
		);
	}

	public static SurfaceRules.RuleSource oasisRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.OASIS),
			desertAndBeachRules()
		);
	}

	public static SurfaceRules.RuleSource aridGrass(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
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

	public static SurfaceRules.RuleSource aridRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
			desertAndBeachRules()
		);
	}

	public static SurfaceRules.RuleSource oldGrowthSnowyTaigaRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA),
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

	public static SurfaceRules.RuleSource oldGrowthDarkForestRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.OLD_GROWTH_DARK_FOREST),
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

	public static SurfaceRules.RuleSource temperateRainforestRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.TEMPERATE_RAINFOREST),
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

	public static SurfaceRules.RuleSource rainforestRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.RAINFOREST),
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

	public static SurfaceRules.RuleSource dyingForestRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
				SurfaceRules.isBiome(biomes, WWBiomes.DYING_FOREST, WWBiomes.DYING_MIXED_FOREST),
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

	public static SurfaceRules.RuleSource mapleForestRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.MAPLE_FOREST),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 0.023, 0.095),
							FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, 0.3667, 0.4),
							FrozenSurfaceRules.makeStateRule(Blocks.ROOTED_DIRT)
							//Deviation 0.0333
							//Middle 0.18335
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, 0.34005, 0.42665),
							FrozenSurfaceRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource tundraRules(HolderLookup<Biome> biomes) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.TUNDRA),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 0.525D, 0.725D),
							FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, 0.3667D, 0.4D),
							FrozenSurfaceRules.makeStateRule(Blocks.ROOTED_DIRT)
							//Deviation 0.0333
							//Middle 0.18335
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, 0.34005D, 0.42665D),
							FrozenSurfaceRules.COARSE_DIRT
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, -0.7250D, -0.525D),
							FrozenSurfaceRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource gravelBetaBeaches() {
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiomeTagOptimized(WWBiomeTags.BETA_BEACH_GRAVEL),
			SurfaceRules.ifTrue(
				SurfaceRules.UNDER_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(WWNoise.GRAVEL_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(Blocks.GRAVEL, Blocks.STONE)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource sandBetaBeaches() {
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiomeTagOptimized(WWBiomeTags.BETA_BEACH_SAND),
			SurfaceRules.ifTrue(
				SurfaceRules.DEEP_UNDER_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource multiLayerSandBetaBeaches() {
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiomeTagOptimized(WWBiomeTags.BETA_BEACH_MULTI_LAYER_SAND),
			SurfaceRules.ifTrue(
				SurfaceRules.DEEP_UNDER_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

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
	public void addOverworldSurfaceRules(HolderLookup<Biome> biomes, List<SurfaceRules.RuleSource> context) {
		context.add(
			SurfaceRules.sequence(
				betaBeaches(),
				cypressSurfaceRules(biomes),
				warmRiverRules(biomes),
				warmBeachRules(biomes),
				oasisRules(biomes),
				aridGrass(biomes),
				aridRules(biomes),
				oldGrowthSnowyTaigaRules(biomes),
				oldGrowthDarkForestRules(biomes),
				temperateRainforestRules(biomes),
				rainforestRules(biomes),
				dyingForestRules(biomes),
				mapleForestRules(biomes),
				tundraRules(biomes)
			)
		);
	}

	public static SurfaceRules.RuleSource snowUnderMountains() {
		return SurfaceRules.ifTrue(
			SnowUnderMountainConditionSource.snowUnderMountainConditionSource(),
			SurfaceRules.ifTrue(
				FrozenSurfaceRules.isBiomeTagOptimized(WWBiomeTags.BELOW_SURFACE_SNOW),
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

	private static SurfaceRules.RuleSource frozenCavesIcePath(Block base, Block border, Block center) {
		return SurfaceRules.noiseGradient(
			Noises.SULFUR_CAVE_GRADIENT,
			List.of(
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.of(base.defaultBlockState()),
				Optional.of(base.defaultBlockState()),
				Optional.of(base.defaultBlockState()),
				Optional.of(border.defaultBlockState()),
				Optional.of(center.defaultBlockState()),
				Optional.of(border.defaultBlockState()),
				Optional.of(base.defaultBlockState()),
				Optional.of(base.defaultBlockState()),
				Optional.of(base.defaultBlockState())
			)
		);
	}

	public static SurfaceRules.RuleSource frozenCavesSurfaceRules(HolderLookup<Biome> biomes) {
		final SurfaceRules.RuleSource iceNoiseRule = frozenCavesIcePath(Blocks.PACKED_ICE, Blocks.BLUE_ICE, WWBlocks.FRAGILE_ICE);
		final SurfaceRules.RuleSource iceNoiseRuleOnlyFragileIce = frozenCavesIcePath(WWBlocks.FRAGILE_ICE, WWBlocks.FRAGILE_ICE, WWBlocks.FRAGILE_ICE);
		final SurfaceRules.RuleSource iceNoiseRuleNoFragileIce = frozenCavesIcePath(Blocks.PACKED_ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE);

		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.FROZEN_CAVES),
			SurfaceRules.sequence(
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.ON_CEILING,
							iceNoiseRuleOnlyFragileIce
						),
						iceNoiseRule
					)
				),
				SurfaceRules.ifTrue(
					SurfaceRules.UNDER_FLOOR,
					iceNoiseRule
				),
				SurfaceRules.ifTrue(
					SurfaceRules.DEEP_UNDER_FLOOR,
					iceNoiseRuleNoFragileIce
				),
				SurfaceRules.ifTrue(
					SurfaceRules.ON_CEILING,
					iceNoiseRule
				),
				SurfaceRules.ifTrue(
					SurfaceRules.UNDER_CEILING,
					iceNoiseRule
				)
			)
		);
	}

	public static SurfaceRules.RuleSource sulfurCavesCalcite(HolderLookup<Biome> biomes) {
		// It's half and half!
		final ArrayList<Optional<BlockState>> states = new ArrayList<>();
		for (int i = 0; i < 40; i++) states.add(Optional.empty());
		for (int i = 0; i < 2; i++) states.add(Optional.of(Blocks.TUFF.defaultBlockState()));
		for (int i = 0; i < 9; i++) states.add(Optional.of(Blocks.CINNABAR.defaultBlockState()));
		for (int i = 0; i < 3; i++) states.add(Optional.of(Blocks.GRANITE.defaultBlockState()));
		for (int i = 0; i < 2; i++) states.add(Optional.of(Blocks.CALCITE.defaultBlockState()));
		for (int i = 0; i < 24; i++) states.add(Optional.of(Blocks.SULFUR.defaultBlockState()));

		return SurfaceRules.ifTrue(
			SulfurCavesCalciteConditionSource.sulfurCavesCalciteConditionSource(),
			SurfaceRules.ifTrue(
				SurfaceRules.isBiome(biomes, Biomes.SULFUR_CAVES),
				SurfaceRules.noiseGradient(
					Noises.SULFUR_CAVE_GRADIENT,
					ImmutableList.copyOf(states)
				)
			)
		);
	}

	@Override
	public void addOverworldNoPrelimSurfaceRules(HolderLookup<Biome> biomes, List<SurfaceRules.RuleSource> context) {
		context.add(
			SurfaceRules.sequence(
				snowUnderMountains(),
				frozenCavesSurfaceRules(biomes),
				sulfurCavesCalcite(biomes)
			)
		);
	}
}
