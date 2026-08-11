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

import net.frozenblock.lib.levelgen.material.api.FrozenLibMaterialRules;
import net.frozenblock.lib.levelgen.material.api.RuleSourceAdditions;
import net.frozenblock.lib.levelgen.material.impl.RuleSourceAddition;
import net.frozenblock.lib.tag.api.FrozenLibDimensionTypeTags;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.noise.WWNoise;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.material.VanillaMaterialConditions;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public final class WWMaterialRules {

	public static SurfaceRules.RuleSource cypressSurfaceRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.CYPRESS_WETLANDS),
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
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
							FrozenLibMaterialRules.WATER
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource fallingBlockAndSafeBlockRules(HolderGetter<SurfaceRules.ConditionSource> materialConditions, Block fallingBlock, Block safeBlock) {
		final SurfaceRules.RuleSource fallingBlockSource = FrozenLibMaterialRules.makeStateRule(fallingBlock);
		final SurfaceRules.RuleSource safeBlockSource = FrozenLibMaterialRules.makeStateRule(safeBlock);
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
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
							SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							safeBlockSource
						),
						fallingBlockSource
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource warmRiverRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.WARM_RIVER),
			SurfaceRules.sequence(
			desertAndBeachRules(biomes, materialConditions),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							FrozenLibMaterialRules.SANDSTONE
						),
						FrozenLibMaterialRules.SAND
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource desertAndBeachRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							FrozenLibMaterialRules.SANDSTONE
						),
						FrozenLibMaterialRules.SAND
					)
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.waterStartCheck(-6, -1),
				SurfaceRules.sequence(
					SurfaceRules.ifTrue(
						SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(
								SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
								FrozenLibMaterialRules.SANDSTONE
							),
							FrozenLibMaterialRules.SAND
						)
					),
					SurfaceRules.ifTrue(
						SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.VERY_DEEP_UNDER_FLOOR),
						FrozenLibMaterialRules.SANDSTONE
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource warmBeachRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.WARM_BEACH),
			desertAndBeachRules(biomes, materialConditions)
		);
	}

	public static SurfaceRules.RuleSource oasisRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.OASIS),
			desertAndBeachRules(biomes, materialConditions)
		);
	}

	public static SurfaceRules.RuleSource aridGrass(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
			SurfaceRules.sequence(
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.155, 0.3666),
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(
								SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
								FrozenLibMaterialRules.DIRT
							),
							FrozenLibMaterialRules.GRASS_BLOCK
						)
					)
				),
				SurfaceRules.ifTrue(
					SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.155, 0.3666),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
							SurfaceRules.sequence(
								SurfaceRules.ifTrue(
									SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
									FrozenLibMaterialRules.DIRT
								),
								FrozenLibMaterialRules.DIRT
							)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
							FrozenLibMaterialRules.DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource aridRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
			desertAndBeachRules(biomes, materialConditions)
		);
	}

	public static SurfaceRules.RuleSource oldGrowthSnowyPineTaigaRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 1.75D / 8.25D, Double.MAX_VALUE),
							FrozenLibMaterialRules.COARSE_DIRT
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, -0.95D / 8.25D, Double.MAX_VALUE),
							FrozenLibMaterialRules.PODZOL
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.0222, 0.055),
							FrozenLibMaterialRules.POWDER_SNOW
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.065, 0.12),
							FrozenLibMaterialRules.SNOW_BLOCK
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource oldGrowthDarkForestRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.OLD_GROWTH_DARK_FOREST),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, -0.0667, 0.04),
							FrozenLibMaterialRules.PODZOL
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource temperateRainforestRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.TEMPERATE_RAINFOREST),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.095, 0.2),
							FrozenLibMaterialRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenLibMaterialRules.makeStateRule(Blocks.MOSS_BLOCK)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.0667, 0.4),
							FrozenLibMaterialRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource rainforestRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.RAINFOREST),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenLibMaterialRules.makeStateRule(Blocks.MOSS_BLOCK)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource dyingForestRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
				SurfaceRules.isBiome(biomes, WWBiomes.DYING_FOREST, WWBiomes.DYING_MIXED_FOREST),
				SurfaceRules.ifTrue(SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
						SurfaceRules.ifTrue(
								SurfaceRules.waterBlockCheck(-1, 0),
								SurfaceRules.sequence(
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.033, 0.095),
												FrozenLibMaterialRules.makeStateRule(Blocks.PODZOL)
										),
										SurfaceRules.ifTrue(
												SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.0667, 0.1),
												FrozenLibMaterialRules.COARSE_DIRT
										)
								)
						)
				)
		);
	}

	public static SurfaceRules.RuleSource mapleForestRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.MAPLE_FOREST),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.023, 0.095),
							FrozenLibMaterialRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.3667, 0.4),
							FrozenLibMaterialRules.makeStateRule(Blocks.ROOTED_DIRT)
							//Deviation 0.0333
							//Middle 0.18335
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.34005, 0.42665),
							FrozenLibMaterialRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource tundraRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.TUNDRA),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.525D, 0.725D),
							FrozenLibMaterialRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.3667D, 0.4D),
							FrozenLibMaterialRules.makeStateRule(Blocks.ROOTED_DIRT)
							//Deviation 0.0333
							//Middle 0.18335
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.34005D, 0.42665D),
							FrozenLibMaterialRules.COARSE_DIRT
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(Noises.SURFACE_SECONDARY, -0.7250D, -0.525D),
							FrozenLibMaterialRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource gravelBetaBeaches(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(biomes, WWBiomeTags.BETA_BEACH_GRAVEL),
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(WWNoise.GRAVEL_BEACH_KEY, -1.7976931348623157E308, -0.12),
							fallingBlockAndSafeBlockRules(materialConditions, Blocks.GRAVEL, Blocks.STONE)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource sandBetaBeaches(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(biomes, WWBiomeTags.BETA_BEACH_SAND),
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(materialConditions, Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource multiLayerSandBetaBeaches(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(biomes, WWBiomeTags.BETA_BEACH_MULTI_LAYER_SAND),
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(materialConditions, Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource betaBeaches(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			WWWorldgenConfig.BETA_BEACHES.equalTo(true).asConditionSource(),
			SurfaceRules.sequence(
				gravelBetaBeaches(biomes, materialConditions),
				sandBetaBeaches(biomes, materialConditions),
				multiLayerSandBetaBeaches(biomes, materialConditions)
			)
		);
	}

	public static SurfaceRules.RuleSource snowUnderMountains(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		return SurfaceRules.ifTrue(
			WWWorldgenConfig.SNOW_UNDER_MOUNTAINS.equalTo(true).asConditionSource(),
			SurfaceRules.ifTrue(
				FrozenLibMaterialRules.isBiomeTag(biomes, WWBiomeTags.BELOW_SURFACE_SNOW),
				SurfaceRules.ifTrue(SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.verticalGradient("snow_gradient", VerticalAnchor.absolute(64), VerticalAnchor.absolute(72))),
						SurfaceRules.ifTrue(
							SurfaceRules.waterBlockCheck(0, 0),
							FrozenLibMaterialRules.makeStateRule(Blocks.SNOW_BLOCK)
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

	public static SurfaceRules.RuleSource frozenCavesRules(HolderGetter<Biome> biomes, HolderGetter<SurfaceRules.ConditionSource> materialConditions) {
		final SurfaceRules.RuleSource packedIce = SurfaceRules.state(Blocks.PACKED_ICE.defaultBlockState());
		final SurfaceRules.RuleSource blueIce = SurfaceRules.state(Blocks.BLUE_ICE.defaultBlockState());
		final SurfaceRules.RuleSource fragileIce = SurfaceRules.state(WWBlocks.FRAGILE_ICE.get().defaultBlockState());

		final SurfaceRules.RuleSource iceNoiseRule = frozenCavesIcePath(packedIce, blueIce, fragileIce);
		final SurfaceRules.RuleSource iceNoiseRuleOnlyFragileIce = frozenCavesIcePath(fragileIce, fragileIce, fragileIce);
		final SurfaceRules.RuleSource iceNoiseRuleNoFragileIce = frozenCavesIcePath(packedIce, packedIce, blueIce);

		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biomes, WWBiomes.FROZEN_CAVES),
			SurfaceRules.sequence(
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							iceNoiseRuleOnlyFragileIce
						),
						iceNoiseRule
					)
				),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
					iceNoiseRule
				),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
					iceNoiseRuleNoFragileIce
				),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
					iceNoiseRule
				),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_CEILING),
					iceNoiseRule
				)
			)
		);
	}

	public static void bootstrap(BootstrapContext<RuleSourceAddition> context) {
		final HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<SurfaceRules.ConditionSource> materialConditions = context.lookup(Registries.MATERIAL_CONDITION);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("beta_beach"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			betaBeaches(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("cypress_wetlands"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			cypressSurfaceRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("warm_river"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			warmRiverRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("warm_beach"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			warmBeachRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("oasis"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			oasisRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("arid_savanna_and_arid_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			SurfaceRules.sequence(
				aridGrass(biomes, materialConditions),
				aridRules(biomes, materialConditions)
			)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("old_growth_snowy_pine_taiga"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			oldGrowthSnowyPineTaigaRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("old_growth_dark_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			oldGrowthDarkForestRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("temperate_rainforest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			temperateRainforestRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("rainforest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			rainforestRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("dying_forest_and_dying_mixed_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			dyingForestRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("maple_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			mapleForestRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("tundra"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			tundraRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("frozen_caves"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			frozenCavesRules(biomes, materialConditions)
		);

		RuleSourceAdditions.register(
			context,
			WWConstants.id("snow_under_mountains"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			snowUnderMountains(biomes, materialConditions)
		);
	}
}
