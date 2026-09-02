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
import net.frozenblock.lib.levelgen.material.api.MaterialRuleAdditions;
import net.frozenblock.lib.levelgen.material.impl.MaterialRuleAddition;
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
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;

public final class WWMaterialRules {

	public static MaterialRule cypressMaterialRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.CYPRESS_WETLANDS),
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.yBlockCheck(
						VerticalAnchor.absolute(60),
						0
					),
					MaterialRules.ifTrue(
						MaterialRules.not(
							MaterialRules.yBlockCheck(
								VerticalAnchor.absolute(63),
								0
							)
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SWAMP, 0D),
							FrozenLibMaterialRules.WATER
						)
					)
				)
			)
		);
	}

	public static MaterialRule fallingBlockAndSafeBlockRules(HolderGetter<MaterialCondition> materialConditions, Block fallingBlock, Block safeBlock) {
		final MaterialRule fallingBlockRule = FrozenLibMaterialRules.makeStateRule(fallingBlock);
		final MaterialRule safeBlockRule = FrozenLibMaterialRules.makeStateRule(safeBlock);
		return MaterialRules.sequence(
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.waterBlockCheck(-1, 0),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							safeBlockRule
						),
						fallingBlockRule
					)
				)
			),
			MaterialRules.ifTrue(
				MaterialRules.waterStartCheck(-6, -1),
				MaterialRules.sequence(
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							safeBlockRule
						),
						fallingBlockRule
					)
				)
			)
		);
	}

	public static MaterialRule warmRiverRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.WARM_RIVER),
			MaterialRules.sequence(
			desertAndBeachRules(biomes, materialConditions),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							FrozenLibMaterialRules.SANDSTONE
						),
						FrozenLibMaterialRules.SAND
					)
				)
			)
		);
	}

	public static MaterialRule desertAndBeachRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.sequence(
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.waterBlockCheck(-1, 0),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							FrozenLibMaterialRules.SANDSTONE
						),
						FrozenLibMaterialRules.SAND
					)
				)
			),
			MaterialRules.ifTrue(
				MaterialRules.waterStartCheck(-6, -1),
				MaterialRules.sequence(
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
						MaterialRules.sequence(
							MaterialRules.ifTrue(
								MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
								FrozenLibMaterialRules.SANDSTONE
							),
							FrozenLibMaterialRules.SAND
						)
					),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.VERY_DEEP_UNDER_FLOOR),
						FrozenLibMaterialRules.SANDSTONE
					)
				)
			)
		);
	}

	public static MaterialRule warmBeachRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.WARM_BEACH),
			desertAndBeachRules(biomes, materialConditions)
		);
	}

	public static MaterialRule oasisRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.OASIS),
			desertAndBeachRules(biomes, materialConditions)
		);
	}

	public static MaterialRule aridGrass(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
			MaterialRules.sequence(
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.noiseCondition2d(Noises.SURFACE, 0.155, 0.3666),
						MaterialRules.sequence(
							MaterialRules.ifTrue(
								MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
								FrozenLibMaterialRules.DIRT
							),
							FrozenLibMaterialRules.GRASS_BLOCK
						)
					)
				),
				MaterialRules.ifTrue(
					MaterialRules.noiseCondition2d(Noises.SURFACE, 0.155, 0.3666),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
							MaterialRules.sequence(
								MaterialRules.ifTrue(
									MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
									FrozenLibMaterialRules.DIRT
								),
								FrozenLibMaterialRules.DIRT
							)
						),
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
							FrozenLibMaterialRules.DIRT
						)
					)
				)
			)
		);
	}

	public static MaterialRule aridRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
			desertAndBeachRules(biomes, materialConditions)
		);
	}

	public static MaterialRule oldGrowthSnowyPineTaigaRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA),
			MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.waterBlockCheck(-1, 0),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, 1.75D / 8.25D, Double.MAX_VALUE),
							FrozenLibMaterialRules.COARSE_DIRT
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, -0.95D / 8.25D, Double.MAX_VALUE),
							FrozenLibMaterialRules.PODZOL
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, 0.0222, 0.055),
							FrozenLibMaterialRules.POWDER_SNOW
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, 0.065, 0.12),
							FrozenLibMaterialRules.SNOW_BLOCK
						)
					)
				)
			)
		);
	}

	public static MaterialRule oldGrowthDarkForestRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.OLD_GROWTH_DARK_FOREST),
			MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.waterBlockCheck(-1, 0),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, -0.0667, 0.04),
							FrozenLibMaterialRules.PODZOL
						)
					)
				)
			)
		);
	}

	public static MaterialRule temperateRainforestRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.TEMPERATE_RAINFOREST),
			MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.waterBlockCheck(-1, 0),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, 0.095, 0.2),
							FrozenLibMaterialRules.makeStateRule(Blocks.PODZOL)
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenLibMaterialRules.makeStateRule(Blocks.MOSS_BLOCK)
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.0667, 0.4),
							FrozenLibMaterialRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static MaterialRule rainforestRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.RAINFOREST),
			MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.waterBlockCheck(-1, 0),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenLibMaterialRules.makeStateRule(Blocks.MOSS_BLOCK)
						)
					)
				)
			)
		);
	}

	public static MaterialRule dyingForestRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
				MaterialRules.isBiome(biomes, WWBiomes.DYING_FOREST, WWBiomes.DYING_MIXED_FOREST),
				MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
						MaterialRules.ifTrue(
								MaterialRules.waterBlockCheck(-1, 0),
								MaterialRules.sequence(
										MaterialRules.ifTrue(
												MaterialRules.noiseCondition2d(Noises.SURFACE, 0.033, 0.095),
												FrozenLibMaterialRules.makeStateRule(Blocks.PODZOL)
										),
										MaterialRules.ifTrue(
												MaterialRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.0667, 0.1),
												FrozenLibMaterialRules.COARSE_DIRT
										)
								)
						)
				)
		);
	}

	public static MaterialRule mapleForestRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.MAPLE_FOREST),
			MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.waterBlockCheck(-1, 0),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, 0.023, 0.095),
							FrozenLibMaterialRules.makeStateRule(Blocks.PODZOL)
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.3667, 0.4),
							FrozenLibMaterialRules.makeStateRule(Blocks.ROOTED_DIRT)
							//Deviation 0.0333
							//Middle 0.18335
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.34005, 0.42665),
							FrozenLibMaterialRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static MaterialRule tundraRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.TUNDRA),
			MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.waterBlockCheck(-1, 0),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, 0.525D, 0.725D),
							FrozenLibMaterialRules.makeStateRule(Blocks.PODZOL)
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.3667D, 0.4D),
							FrozenLibMaterialRules.makeStateRule(Blocks.ROOTED_DIRT)
							//Deviation 0.0333
							//Middle 0.18335
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE_SECONDARY, 0.34005D, 0.42665D),
							FrozenLibMaterialRules.COARSE_DIRT
						),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE_SECONDARY, -0.7250D, -0.525D),
							FrozenLibMaterialRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static MaterialRule gravelBetaBeaches(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(biomes, WWBiomeTags.BETA_BEACH_GRAVEL),
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					MaterialRules.ifTrue(
						MaterialRules.not(MaterialRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(WWNoise.GRAVEL_BEACH_KEY, -1.7976931348623157E308, -0.12),
							fallingBlockAndSafeBlockRules(materialConditions, Blocks.GRAVEL, Blocks.STONE)
						)
					)
				)
			)
		);
	}

	public static MaterialRule sandBetaBeaches(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(biomes, WWBiomeTags.BETA_BEACH_SAND),
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					MaterialRules.ifTrue(
						MaterialRules.not(MaterialRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(materialConditions, Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	public static MaterialRule multiLayerSandBetaBeaches(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(biomes, WWBiomeTags.BETA_BEACH_MULTI_LAYER_SAND),
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					MaterialRules.ifTrue(
						MaterialRules.not(MaterialRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(WWNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
							fallingBlockAndSafeBlockRules(materialConditions, Blocks.SAND, Blocks.SANDSTONE)
						)
					)
				)
			)
		);
	}

	public static MaterialRule betaBeaches(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			WWWorldgenConfig.BETA_BEACHES.equalTo(true).asMaterialCondition(),
			MaterialRules.sequence(
				gravelBetaBeaches(biomes, materialConditions),
				sandBetaBeaches(biomes, materialConditions),
				multiLayerSandBetaBeaches(biomes, materialConditions)
			)
		);
	}

	public static MaterialRule snowUnderMountains(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			WWWorldgenConfig.SNOW_UNDER_MOUNTAINS.equalTo(true).asMaterialCondition(),
			MaterialRules.ifTrue(
				FrozenLibMaterialRules.isBiomeTag(biomes, WWBiomeTags.BELOW_SURFACE_SNOW),
				MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.not(MaterialRules.verticalGradient("snow_gradient", VerticalAnchor.absolute(64), VerticalAnchor.absolute(72))),
						MaterialRules.ifTrue(
							MaterialRules.waterBlockCheck(0, 0),
							FrozenLibMaterialRules.makeStateRule(Blocks.SNOW_BLOCK)
						)
					)
				)
			)
		);
	}

	private static MaterialRule frozenCavesIcePath(MaterialRule base, MaterialRule border, MaterialRule center) {
		return MaterialRules.sequence(
			MaterialRules.ifTrue(MaterialRules.noiseCondition3d(Noises.SULFUR_CAVE_GRADIENT, -0.384615385F, 0.0769230769F), base),
			MaterialRules.ifTrue(MaterialRules.noiseCondition3d(Noises.SULFUR_CAVE_GRADIENT, 0.0769230769F, 0.538461538F), border),
			MaterialRules.ifTrue(MaterialRules.noiseCondition3d(Noises.SULFUR_CAVE_GRADIENT, 0.538461538F, 1F), center)
		);
	}

	public static MaterialRule frozenCavesRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		final MaterialRule packedIce = MaterialRules.state(Blocks.PACKED_ICE.defaultBlockState());
		final MaterialRule blueIce = MaterialRules.state(Blocks.BLUE_ICE.defaultBlockState());
		final MaterialRule fragileIce = MaterialRules.state(WWBlocks.FRAGILE_ICE.get().defaultBlockState());

		final MaterialRule iceNoiseRule = frozenCavesIcePath(packedIce, blueIce, fragileIce);
		final MaterialRule iceNoiseRuleOnlyFragileIce = frozenCavesIcePath(fragileIce, fragileIce, fragileIce);
		final MaterialRule iceNoiseRuleNoFragileIce = frozenCavesIcePath(packedIce, packedIce, blueIce);

		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.FROZEN_CAVES),
			MaterialRules.sequence(
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
							iceNoiseRuleOnlyFragileIce
						),
						iceNoiseRule
					)
				),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
					iceNoiseRule
				),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
					iceNoiseRuleNoFragileIce
				),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_CEILING),
					iceNoiseRule
				),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_CEILING),
					iceNoiseRule
				)
			)
		);
	}

	public static void bootstrap(BootstrapContext<MaterialRuleAddition> context) {
		final HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<MaterialCondition> materialConditions = context.lookup(Registries.MATERIAL_CONDITION);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("beta_beach"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			betaBeaches(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("cypress_wetlands"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			cypressMaterialRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("warm_river"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			warmRiverRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("warm_beach"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			warmBeachRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("oasis"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			oasisRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("arid_savanna_and_arid_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			MaterialRules.sequence(
				aridGrass(biomes, materialConditions),
				aridRules(biomes, materialConditions)
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("old_growth_snowy_pine_taiga"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			oldGrowthSnowyPineTaigaRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("old_growth_dark_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			oldGrowthDarkForestRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("temperate_rainforest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			temperateRainforestRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("rainforest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			rainforestRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("dying_forest_and_dying_mixed_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			dyingForestRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("maple_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			mapleForestRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("tundra"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			tundraRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("frozen_caves"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			frozenCavesRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("snow_under_mountains"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			snowUnderMountains(biomes, materialConditions)
		);
	}
}
