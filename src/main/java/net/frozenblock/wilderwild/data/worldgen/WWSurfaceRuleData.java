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

package net.frozenblock.wilderwild.data.worldgen;

import java.util.List;
import net.frozenblock.lib.worldgen.surface.api.FrozenSurfaceRules;
import net.frozenblock.lib.worldgen.surface.api.SurfaceRuleEvents;
import net.frozenblock.wilderwild.data.worldgen.noise.WWNoise;
import net.frozenblock.wilderwild.levelgen.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.levelgen.conditionsource.SnowUnderMountainConditionSource;
import net.frozenblock.wilderwild.levelgen.conditionsource.SulfurCavesCalciteConditionSource;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public final class WWSurfaceRuleData implements SurfaceRuleEvents.OverworldSurfaceRuleCallback, SurfaceRuleEvents.OverworldSurfaceRuleNoPrelimSurfaceCallback {

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
							SurfaceRules.noiseCondition2d(Noises.SWAMP, 0D),
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
						SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.155, 0.3666),
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
					SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.155, 0.3666),
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
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 1.75D / 8.25D, Double.MAX_VALUE),
							FrozenSurfaceRules.COARSE_DIRT
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, -0.95D / 8.25D, Double.MAX_VALUE),
							FrozenSurfaceRules.PODZOL
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.0222, 0.055),
							FrozenSurfaceRules.POWDER_SNOW
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.065, 0.12),
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
							SurfaceRules.noiseCondition2d(Noises.SURFACE, -0.0667, 0.04),
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
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.095, 0.2),
							FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenSurfaceRules.makeStateRule(Blocks.MOSS_BLOCK)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.0667, 0.4),
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
							SurfaceRules.noiseCondition2d(Noises.POWDER_SNOW, 0.065, 0.15),
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
												SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.033, 0.095),
												FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
										),
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.0667, 0.1),
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
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.023, 0.095),
							FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.3667, 0.4),
							FrozenSurfaceRules.makeStateRule(Blocks.ROOTED_DIRT)
							//Deviation 0.0333
							//Middle 0.18335
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.34005, 0.42665),
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
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.525D, 0.725D),
							FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.3667D, 0.4D),
							FrozenSurfaceRules.makeStateRule(Blocks.ROOTED_DIRT)
							//Deviation 0.0333
							//Middle 0.18335
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.34005D, 0.42665D),
							FrozenSurfaceRules.COARSE_DIRT
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, -0.7250D, -0.525D),
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
							SurfaceRules.noiseCondition2d(WWNoise.GRAVEL_BEACH_KEY, 0.12, 1.7976931348623157E308),
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
							SurfaceRules.noiseCondition2d(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
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
							SurfaceRules.noiseCondition2d(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
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

	private static SurfaceRules.RuleSource frozenCavesIcePath(SurfaceRules.RuleSource base, SurfaceRules.RuleSource border, SurfaceRules.RuleSource center) {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(SurfaceRules.noiseCondition3d(Noises.SULFUR_CAVE_GRADIENT, -0.384615385F, 0.0769230769F), base),
			SurfaceRules.ifTrue(SurfaceRules.noiseCondition3d(Noises.SULFUR_CAVE_GRADIENT, 0.0769230769F, 0.538461538F), border),
			SurfaceRules.ifTrue(SurfaceRules.noiseCondition3d(Noises.SULFUR_CAVE_GRADIENT, 0.538461538F, 1F), center)
		);
	}

	public static SurfaceRules.RuleSource frozenCavesSurfaceRules(HolderLookup<Biome> biomes) {
		final SurfaceRules.RuleSource packedIce = SurfaceRules.state(Blocks.PACKED_ICE.defaultBlockState());
		final SurfaceRules.RuleSource blueIce = SurfaceRules.state(Blocks.BLUE_ICE.defaultBlockState());
		final SurfaceRules.RuleSource fragileIce = SurfaceRules.state(WWBlocks.FRAGILE_ICE.defaultBlockState());

		final SurfaceRules.RuleSource iceNoiseRule = frozenCavesIcePath(packedIce, blueIce, fragileIce);
		final SurfaceRules.RuleSource iceNoiseRuleOnlyFragileIce = frozenCavesIcePath(fragileIce, fragileIce, fragileIce);
		final SurfaceRules.RuleSource iceNoiseRuleNoFragileIce = frozenCavesIcePath(packedIce, packedIce, blueIce);

		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.FROZEN_CAVES),
			SurfaceRules.sequence(
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, iceNoiseRuleOnlyFragileIce),
						iceNoiseRule
					)
				),
				SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, iceNoiseRule),
				SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, iceNoiseRuleNoFragileIce),
				SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, iceNoiseRule),
				SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, iceNoiseRule)
			)
		);
	}

	public static SurfaceRules.RuleSource sulfurCavesCalcite(HolderLookup<Biome> biomes) {
		// NOTE: Old gen before snapshot-6 reverted granite & tuff gen
		/*
		// It's half and half! (80 total.)
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
		 */

		return SurfaceRules.ifTrue(
			SulfurCavesCalciteConditionSource.sulfurCavesCalciteConditionSource(),
			SurfaceRules.ifTrue(
				SurfaceRules.isBiome(biomes, Biomes.SULFUR_CAVES),
				SurfaceRules.ifTrue(
					SurfaceRules.noiseCondition3d(Noises.SULFUR_CAVE_GRADIENT, -0.1F, 0F),
					SurfaceRules.state(Blocks.CALCITE.defaultBlockState())
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
