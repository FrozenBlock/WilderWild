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
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.material.api.FrozenLibMaterialRules;
import net.frozenblock.lib.levelgen.material.api.MaterialRuleEvents;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.noise.WWNoise;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.material.VanillaMaterialConditions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public final class WWMaterialRules implements MaterialRuleEvents.OverworldMaterialRuleCallback, MaterialRuleEvents.OverworldMaterialRuleNoPrelimSurfaceCallback {

	public static SurfaceRules.RuleSource cypressSurfaceRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.CYPRESS_WETLANDS),
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource fallingBlockAndSafeBlockRules(RegistryAccess registries, Block fallingBlock, Block safeBlock) {
		final SurfaceRules.RuleSource fallingBlockSource = FrozenLibMaterialRules.makeStateRule(fallingBlock);
		final SurfaceRules.RuleSource safeBlockSource = FrozenLibMaterialRules.makeStateRule(safeBlock);
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
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
							SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
							safeBlockSource
						),
						fallingBlockSource
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource warmRiverRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.WARM_RIVER),
			SurfaceRules.sequence(
			desertAndBeachRules(registries),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
							FrozenLibMaterialRules.SANDSTONE
						),
						FrozenLibMaterialRules.SAND
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource desertAndBeachRules(RegistryAccess registries) {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
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
						SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.UNDER_FLOOR),
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(
								SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
								FrozenLibMaterialRules.SANDSTONE
							),
							FrozenLibMaterialRules.SAND
						)
					),
					SurfaceRules.ifTrue(
						SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.VERY_DEEP_UNDER_FLOOR),
						FrozenLibMaterialRules.SANDSTONE
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource warmBeachRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.WARM_BEACH),
			desertAndBeachRules(registries)
		);
	}

	public static SurfaceRules.RuleSource oasisRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.OASIS),
			desertAndBeachRules(registries)
		);
	}

	public static SurfaceRules.RuleSource aridGrass(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
			SurfaceRules.sequence(
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition2d(Noises.SURFACE, 0.155, 0.3666),
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(
								SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
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
							SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.UNDER_FLOOR),
							SurfaceRules.sequence(
								SurfaceRules.ifTrue(
									SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
									FrozenLibMaterialRules.DIRT
								),
								FrozenLibMaterialRules.DIRT
							)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.DEEP_UNDER_FLOOR),
							FrozenLibMaterialRules.DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource aridRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
			desertAndBeachRules(registries)
		);
	}

	public static SurfaceRules.RuleSource oldGrowthSnowyTaigaRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource oldGrowthDarkForestRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.OLD_GROWTH_DARK_FOREST),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource temperateRainforestRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.TEMPERATE_RAINFOREST),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource rainforestRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.RAINFOREST),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource dyingForestRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
				SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.DYING_FOREST, WWBiomes.DYING_MIXED_FOREST),
				SurfaceRules.ifTrue(SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource mapleForestRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.MAPLE_FOREST),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource tundraRules(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.TUNDRA),
			SurfaceRules.ifTrue(SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource gravelBetaBeaches(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(registries, WWBiomeTags.BETA_BEACH_GRAVEL),
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.UNDER_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(WWNoise.GRAVEL_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(registries, Blocks.GRAVEL, Blocks.STONE)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource sandBetaBeaches(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(registries, WWBiomeTags.BETA_BEACH_SAND),
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.DEEP_UNDER_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(registries, Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource multiLayerSandBetaBeaches(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(registries, WWBiomeTags.BETA_BEACH_MULTI_LAYER_SAND),
			SurfaceRules.ifTrue(
				SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.DEEP_UNDER_FLOOR),
				SurfaceRules.ifTrue(
					SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					SurfaceRules.ifTrue(
						SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition2d(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(registries, Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource betaBeaches(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			ConfigPredicate.equalTo(WWWorldgenConfig.BETA_BEACHES, true).asConditionSource(),
			SurfaceRules.sequence(
				gravelBetaBeaches(registries),
				sandBetaBeaches(registries),
				multiLayerSandBetaBeaches(registries)
			)
		);
	}

	@Override
	public void addOverworldMaterialRules(RegistryAccess registries, List<SurfaceRules.RuleSource> context) {
		context.add(
			SurfaceRules.sequence(
				betaBeaches(registries),
				cypressSurfaceRules(registries),
				warmRiverRules(registries),
				warmBeachRules(registries),
				oasisRules(registries),
				aridGrass(registries),
				aridRules(registries),
				oldGrowthSnowyTaigaRules(registries),
				oldGrowthDarkForestRules(registries),
				temperateRainforestRules(registries),
				rainforestRules(registries),
				dyingForestRules(registries),
				mapleForestRules(registries),
				tundraRules(registries)
			)
		);
	}

	public static SurfaceRules.RuleSource snowUnderMountains(RegistryAccess registries) {
		return SurfaceRules.ifTrue(
			ConfigPredicate.equalTo(WWWorldgenConfig.SNOW_UNDER_MOUNTAINS, true).asConditionSource(),
			SurfaceRules.ifTrue(
				FrozenLibMaterialRules.isBiomeTag(registries, WWBiomeTags.BELOW_SURFACE_SNOW),
				SurfaceRules.ifTrue(SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
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

	public static SurfaceRules.RuleSource frozenCavesSurfaceRules(RegistryAccess registries) {
		final SurfaceRules.RuleSource packedIce = SurfaceRules.state(Blocks.PACKED_ICE.defaultBlockState());
		final SurfaceRules.RuleSource blueIce = SurfaceRules.state(Blocks.BLUE_ICE.defaultBlockState());
		final SurfaceRules.RuleSource fragileIce = SurfaceRules.state(WWBlocks.FRAGILE_ICE.get().defaultBlockState());

		final SurfaceRules.RuleSource iceNoiseRule = frozenCavesIcePath(packedIce, blueIce, fragileIce);
		final SurfaceRules.RuleSource iceNoiseRuleOnlyFragileIce = frozenCavesIcePath(fragileIce, fragileIce, fragileIce);
		final SurfaceRules.RuleSource iceNoiseRuleNoFragileIce = frozenCavesIcePath(packedIce, packedIce, blueIce);

		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(registries.lookupOrThrow(Registries.BIOME), WWBiomes.FROZEN_CAVES),
			SurfaceRules.sequence(
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_FLOOR),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
							iceNoiseRuleOnlyFragileIce
						),
						iceNoiseRule
					)
				),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.UNDER_FLOOR),
					iceNoiseRule),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.DEEP_UNDER_FLOOR),
					iceNoiseRuleNoFragileIce),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.ON_CEILING),
					iceNoiseRule),
				SurfaceRules.ifTrue(
					SurfaceRules.getCondition(registries.lookupOrThrow(Registries.MATERIAL_CONDITION), VanillaMaterialConditions.UNDER_CEILING),
					iceNoiseRule)
			)
		);
	}

	@Override
	public void addOverworldNoPrelimMaterialRules(RegistryAccess registries, List<SurfaceRules.RuleSource> context) {
		context.add(
			SurfaceRules.sequence(
				snowUnderMountains(registries),
				frozenCavesSurfaceRules(registries)
			)
		);
	}
}
