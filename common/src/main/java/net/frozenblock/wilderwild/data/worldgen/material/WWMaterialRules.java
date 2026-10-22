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

package net.frozenblock.wilderwild.data.worldgen.material;

import net.frozenblock.lib.levelgen.material.api.FrozenLibMaterialRules;
import net.frozenblock.lib.levelgen.material.api.MaterialRuleAdditions;
import net.frozenblock.lib.levelgen.material.impl.MaterialRuleAddition;
import net.frozenblock.lib.tag.api.FrozenLibDimensionTypeTags;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.noise.WWNoise;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWAquaticFeatureBiomeTags;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.material.OverworldMaterialRules;
import net.minecraft.data.worldgen.material.VanillaMaterialConditions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;

public final class WWMaterialRules {
	public static final ResourceKey<MaterialRule> GRASS_AND_DIRT = createKey("grass_and_dirt");
	public static final ResourceKey<MaterialRule> GRAVEL_AND_STONE_BETA_BEACH = createKey("gravel_and_stone_beta_beach");
	public static final ResourceKey<MaterialRule> SAND_AND_SANDSTONE_BETA_BEACH = createKey("sand_and_sandstone_beta_beach");
	public static final ResourceKey<MaterialRule> SAND_AND_SANDSTONE = createKey("sand_and_sandstone");
	public static final ResourceKey<MaterialRule> SAND_AND_VERY_DEEP_SANDSTONE = createKey("sand_and_very_deep_sandstone");
	public static final ResourceKey<MaterialRule> SAND_AND_SANDSTONE_AQUATIC = createKey("sand_and_sandstone_aquatic");

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

	public static MaterialRule sandAndSandstoneAquaticBiomeRule(HolderGetter<Biome> biomes, HolderGetter<MaterialRule> materialRules, ResourceKey<Biome>... biomeKeys) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, biomeKeys),
			MaterialRules.getRule(materialRules, SAND_AND_SANDSTONE_AQUATIC)
		);
	}

	public static MaterialRule oasisRules(HolderGetter<Biome> biomes, HolderGetter<MaterialRule> materialRules) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.OASIS),
			MaterialRules.sequence(
				MaterialRules.ifTrue(
					MaterialRules.noiseCondition2d(WWNoise.OASIS_GRASS, -2D, -0.66D),
					MaterialRules.getRule(materialRules, GRASS_AND_DIRT)
				),
				MaterialRules.getRule(materialRules, SAND_AND_VERY_DEEP_SANDSTONE)
			)
		);
	}

	public static MaterialRule aridRules(HolderGetter<Biome> biomes, HolderGetter<MaterialRule> materialRules) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST),
			MaterialRules.sequence(
				MaterialRules.ifTrue(
					MaterialRules.noiseCondition2d(Noises.SURFACE, 0.155D, 0.3666D),
					MaterialRules.getRule(materialRules, GRASS_AND_DIRT)
				),
				MaterialRules.getRule(materialRules, SAND_AND_VERY_DEEP_SANDSTONE)
			)
		);
	}

	public static MaterialRule oceanMossRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			WWWorldgenConfig.OCEAN_MOSS_GENERATION.equalTo(true).asMaterialCondition(),
			MaterialRules.ifTrue(
				FrozenLibMaterialRules.isBiomeTag(biomes, WWAquaticFeatureBiomeTags.OCEAN_MOSS),
				MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.not(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER)),
						MaterialRules.sequence(
							MaterialRules.ifTrue(
								MaterialRules.noiseCondition2d(WWNoise.OCEAN_MOSS, -0.65D, -0.25D),
								FrozenLibMaterialRules.makeStateRule(Blocks.MOSS_BLOCK)
							)
						)
					)
				)
			)
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

	public static MaterialRule magmaticCavesRules(HolderGetter<Biome> biomes, HolderGetter<MaterialCondition> materialConditions) {
		final MaterialRule gabbro = FrozenLibMaterialRules.makeStateRule(WWBlocks.GABBRO.get());
		final MaterialRule magmaOrGabbroUnderWater = MaterialRules.sequence(
			MaterialRules.ifTrue(
				MaterialRules.noiseCondition3d(Noises.PATCH, 0.05F),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
						FrozenLibMaterialRules.makeStateRule(Blocks.MAGMA_BLOCK)
					)
				)
			),
			gabbro
		);

		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, WWBiomes.MAGMATIC_CAVES),
			FrozenLibMaterialRules.avoidBedrockFloorAndRoof(
				materialConditions,
				MaterialRules.sequence(
					MaterialRules.ifTrue(MaterialRules.noiseCondition3d(WWNoise.MAGMATIC_CAVE_GRADIENT, -0.45F, -0.245F), gabbro),
					MaterialRules.ifTrue(MaterialRules.noiseCondition3d(WWNoise.MAGMATIC_CAVE_GRADIENT, -0.245F, -0.07F), magmaOrGabbroUnderWater),
					MaterialRules.ifTrue(MaterialRules.noiseCondition3d(WWNoise.MAGMATIC_CAVE_GRADIENT, -0.07F, 0.3F), gabbro),

					MaterialRules.ifTrue(MaterialRules.noiseCondition3d(WWNoise.MAGMATIC_CAVE_GRADIENT, 0.655, 1F), gabbro)
				)
			)
		);
	}

	public static MaterialRule betaBeach(
		HolderGetter<Biome> biomes,
		TagKey<Biome> biomeTag,
		MaterialCondition underFloorCondition,
		int maxYLevel,
		MaterialCondition noiseCondition,
		MaterialRule materialRule
	) {
		return MaterialRules.ifTrue(
			FrozenLibMaterialRules.isBiomeTag(biomes, biomeTag),
			MaterialRules.ifTrue(
				underFloorCondition,
				MaterialRules.ifTrue(
					MaterialRules.yStartCheck(VerticalAnchor.absolute(58), 0),
					MaterialRules.ifTrue(
						MaterialRules.not(MaterialRules.yStartCheck(VerticalAnchor.absolute(maxYLevel), 0)),
						MaterialRules.ifTrue(
							noiseCondition,
							materialRule
						)
					)
				)
			)
		);
	}

	public static MaterialRule betaBeaches(HolderGetter<Biome> biomes, HolderGetter<MaterialRule> materialRules, HolderGetter<MaterialCondition> materialConditions) {
		return MaterialRules.ifTrue(
			WWWorldgenConfig.BETA_BEACHES.equalTo(true).asMaterialCondition(),
			MaterialRules.sequence(
				betaBeach(
					biomes,
					WWBiomeTags.BETA_BEACH_GRAVEL,
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
					65,
					MaterialRules.noiseCondition2d(WWNoise.GRAVEL_BEACH, -1.7976931348623157E308D, -0.12),
					MaterialRules.getRule(materialRules, WWMaterialRules.GRAVEL_AND_STONE_BETA_BEACH)
				),
				betaBeach(
					biomes,
					WWBiomeTags.BETA_BEACH_SAND,
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
					65,
					MaterialRules.noiseCondition2d(WWNoise.SAND_BEACH, 0.12D, 1.7976931348623157E308D),
					MaterialRules.getRule(materialRules, WWMaterialRules.SAND_AND_SANDSTONE_BETA_BEACH)
				),
				betaBeach(
					biomes,
					WWBiomeTags.BETA_BEACH_MULTI_LAYER_SAND,
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
					64,
					MaterialRules.noiseCondition2d(WWNoise.SAND_BEACH, 0.12D, 1.7976931348623157E308D),
					MaterialRules.getRule(materialRules, WWMaterialRules.SAND_AND_SANDSTONE_BETA_BEACH)
				)
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

	private static MaterialRule aboveSurface(MaterialRule original) {
		return MaterialRules.ifTrue(
			MaterialRules.abovePreliminarySurface(),
			original
		);
	}

	public static void bootstrapAdditions(BootstrapContext<MaterialRuleAddition> context) {
		final HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<MaterialRule> materialRules = context.lookup(Registries.MATERIAL_RULE);
		final HolderGetter<MaterialCondition> materialConditions = context.lookup(Registries.MATERIAL_CONDITION);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("beta_beach"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(betaBeaches(biomes, materialRules, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("cypress_wetlands"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(cypressMaterialRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("warm_beach"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(sandAndSandstoneAquaticBiomeRule(biomes, materialRules, WWBiomes.WARM_BEACH))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("warm_river"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(sandAndSandstoneAquaticBiomeRule(biomes, materialRules, WWBiomes.WARM_RIVER))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("oasis"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(oasisRules(biomes, materialRules))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("arid_savanna_and_arid_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(aridRules(biomes, materialRules))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("ocean_moss"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(oceanMossRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("old_growth_snowy_pine_taiga"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(oldGrowthSnowyPineTaigaRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("old_growth_dark_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(oldGrowthDarkForestRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("temperate_rainforest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(temperateRainforestRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("rainforest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(rainforestRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("dying_forest_and_dying_mixed_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(dyingForestRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("maple_forest"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(mapleForestRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("tundra"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			aboveSurface(tundraRules(biomes, materialConditions))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("magmatic_caves"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			magmaticCavesRules(biomes, materialConditions)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("snow_under_mountains"),
			dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD),
			snowUnderMountains(biomes, materialConditions)
		);
	}

	public static MaterialRule baseSurfaceAndUnderSurfaceRule(
		HolderGetter<MaterialCondition> materialConditions,
		MaterialRule floorNotUnderWater,
		MaterialRule notUnderDeepWater
	) {
		return MaterialRules.sequence(
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
					floorNotUnderWater
				)
			),
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDER_DEEP_WATER),
				notUnderDeepWater
			)
		);
	}

	public static void bootstrapRules(BootstrapContext<MaterialRule> context) {
		final HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<MaterialRule> materialRules = context.lookup(Registries.MATERIAL_RULE);
		final HolderGetter<MaterialCondition> materialConditions = context.lookup(Registries.MATERIAL_CONDITION);


		context.register(
			GRASS_AND_DIRT,
			baseSurfaceAndUnderSurfaceRule(
				materialConditions,
				MaterialRules.getRule(materialRules, OverworldMaterialRules.DEFAULT_BIOME_SURFACE),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
					MaterialRules.getRule(materialRules, OverworldMaterialRules.DEFAULT_UNDER_BIOME_SURFACE)
				)
			)
		);

		context.register(
			GRAVEL_AND_STONE_BETA_BEACH,
			baseSurfaceAndUnderSurfaceRule(
				materialConditions,
				MaterialRules.getRule(materialRules, OverworldMaterialRules.GRAVEL_OR_STONE_IF_CEILING),
				MaterialRules.sequence(
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
						MaterialRules.getRule(materialRules, OverworldMaterialRules.GRAVEL_OR_STONE_IF_CEILING)
					),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
						FrozenLibMaterialRules.STONE
					)
				)
			)
		);

		final MaterialRule sandOrSandstoneIfCeiling = MaterialRules.getRule(materialRules, OverworldMaterialRules.SAND_OR_SANDSTONE_IF_CEILING);
		context.register(
			SAND_AND_SANDSTONE_BETA_BEACH,
			baseSurfaceAndUnderSurfaceRule(
				materialConditions,
				sandOrSandstoneIfCeiling,
				MaterialRules.sequence(
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
						sandOrSandstoneIfCeiling
					),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
						FrozenLibMaterialRules.SANDSTONE
					)
				)
			)
		);

		context.register(
			SAND_AND_SANDSTONE,
			baseSurfaceAndUnderSurfaceRule(
				materialConditions,
				sandOrSandstoneIfCeiling,
				MaterialRules.sequence(
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
						sandOrSandstoneIfCeiling
					),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
						FrozenLibMaterialRules.SANDSTONE
					)
				)
			)
		);

		context.register(
			SAND_AND_VERY_DEEP_SANDSTONE,
			baseSurfaceAndUnderSurfaceRule(
				materialConditions,
				sandOrSandstoneIfCeiling,
				MaterialRules.sequence(
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
						sandOrSandstoneIfCeiling
					),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
						FrozenLibMaterialRules.SANDSTONE
					),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.VERY_DEEP_UNDER_FLOOR),
						FrozenLibMaterialRules.SANDSTONE
					)
				)
			)
		);

		context.register(
			SAND_AND_SANDSTONE_AQUATIC,
			MaterialRules.sequence(
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					sandOrSandstoneIfCeiling
				),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDER_DEEP_WATER),
					MaterialRules.sequence(
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
							sandOrSandstoneIfCeiling
						),
						MaterialRules.ifTrue(
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
							FrozenLibMaterialRules.SANDSTONE
						)
					)
				)
			)
		);
	}

	private static ResourceKey<MaterialRule> createKey(String name) {
		return ResourceKey.create(Registries.MATERIAL_RULE, WWConstants.id(name));
	}

}
