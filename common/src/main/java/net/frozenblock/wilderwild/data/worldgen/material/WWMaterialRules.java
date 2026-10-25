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

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.material.api.FrozenLibMaterialRules;
import net.frozenblock.lib.levelgen.material.api.MaterialRuleAdditions;
import net.frozenblock.lib.levelgen.material.impl.MaterialRuleAddition;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.lib.tag.api.FrozenLibDimensionTypeTags;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.noise.WWNoises;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
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
	public static final ResourceKey<MaterialRule> COARSE_DIRT_PATH_BASE = createKey("coarse_dirt_path_base");
	public static final ResourceKey<MaterialRule> COARSE_DIRT_PATH = createKey("coarse_dirt_path");
	public static final ResourceKey<MaterialRule> COARSE_DIRT_PATH_PATCHY = createKey("coarse_dirt_path_patchy");
	public static final ResourceKey<MaterialRule> SANDSTONE_PATH = createKey("sandstone_path");

	public static MaterialRule sandAndSandstoneAquaticBiomeRule(HolderGetter<Biome> biomes, HolderGetter<MaterialRule> materialRules, ResourceKey<Biome>... biomeKeys) {
		return MaterialRules.ifTrue(
			MaterialRules.isBiome(biomes, biomeKeys),
			MaterialRules.getRule(materialRules, SAND_AND_SANDSTONE_AQUATIC)
		);
	}

	public static MaterialRule betaBeachRule(
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

	private static MaterialRule aboveSurface(MaterialRule original) {
		return MaterialRules.ifTrue(
			MaterialRules.abovePreliminarySurface(),
			original
		);
	}

	@SafeVarargs
	private static MaterialRule aboveSurfaceAndInBiomes(HolderGetter<Biome> biomes, MaterialRule original, ResourceKey<Biome>... biomeKeys) {
		return aboveSurface(
			MaterialRules.ifTrue(
				MaterialRules.isBiome(biomes, biomeKeys),
				original
			)
		);
	}

	private static MaterialRule aboveSurfaceAndInBiomes(
		HolderGetter<Biome> biomes,
		TagKey<Biome> biomeTag,
		HolderGetter<ConfigPredicate> configPredicates,
		ResourceKey<ConfigPredicate> configPredicate,
		MaterialRule original
	) {
		return aboveSurface(
			MaterialRules.ifTrue(
				ConfigPredicate.materialCondition(configPredicates.getOrThrow(configPredicate)),
				MaterialRules.ifTrue(
					FrozenLibMaterialRules.isBiomeTag(biomes, biomeTag),
					original
				)
			)
		);
	}

	public static void bootstrapAdditions(BootstrapContext<MaterialRuleAddition> context) {
		final HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<MaterialRule> materialRules = context.lookup(Registries.MATERIAL_RULE);
		final HolderGetter<MaterialCondition> materialConditions = context.lookup(Registries.MATERIAL_CONDITION);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);

		final HolderSet<DimensionType> overworld = dimensionTypes.getOrThrow(FrozenLibDimensionTypeTags.OVERWORLD);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("snow_under_mountains"),
			overworld,
			MaterialRules.ifTrue(
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
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("beta_beach"),
			overworld,
			aboveSurface(
				MaterialRules.ifTrue(
					WWWorldgenConfig.BETA_BEACHES.equalTo(true).asMaterialCondition(),
					MaterialRules.sequence(
						betaBeachRule(
							biomes,
							WWBiomeTags.BETA_BEACH_GRAVEL,
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
							65,
							MaterialRules.noiseCondition2d(WWNoises.GRAVEL_BEACH, -1.7976931348623157E308D, -0.12),
							MaterialRules.getRule(materialRules, WWMaterialRules.GRAVEL_AND_STONE_BETA_BEACH)
						),
						betaBeachRule(
							biomes,
							WWBiomeTags.BETA_BEACH_SAND,
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.UNDER_FLOOR),
							65,
							MaterialRules.noiseCondition2d(WWNoises.SAND_BEACH, 0.12D, 1.7976931348623157E308D),
							MaterialRules.getRule(materialRules, WWMaterialRules.SAND_AND_SANDSTONE_BETA_BEACH)
						),
						betaBeachRule(
							biomes,
							WWBiomeTags.BETA_BEACH_SAND_DEEP,
							MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.DEEP_UNDER_FLOOR),
							64,
							MaterialRules.noiseCondition2d(WWNoises.SAND_BEACH, 0.12D, 1.7976931348623157E308D),
							MaterialRules.getRule(materialRules, WWMaterialRules.SAND_AND_SANDSTONE_BETA_BEACH)
						)
					)
				)
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("cypress_wetlands"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.yBlockCheck(VerticalAnchor.absolute(60), 0),
						MaterialRules.ifTrue(
							MaterialRules.not(MaterialRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
							MaterialRules.ifTrue(
								MaterialRules.noiseCondition2d(Noises.SWAMP, 0D),
								FrozenLibMaterialRules.WATER
							)
						)
					)
				),
				WWBiomes.CYPRESS_WETLANDS
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("warm_river_and_warm_beach"),
			overworld,
			aboveSurface(sandAndSandstoneAquaticBiomeRule(biomes, materialRules, WWBiomes.WARM_RIVER, WWBiomes.WARM_BEACH))
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("oasis"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.sequence(
					MaterialRules.ifTrue(
						MaterialRules.noiseCondition2d(WWNoises.OASIS_GRASS, -2D, -0.66D),
						MaterialRules.getRule(materialRules, GRASS_AND_DIRT)
					),
					MaterialRules.getRule(materialRules, SAND_AND_VERY_DEEP_SANDSTONE)
				),
				WWBiomes.OASIS
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("arid_savanna_and_arid_forest"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.sequence(
					MaterialRules.ifTrue(
						MaterialRules.noiseCondition2d(Noises.SURFACE, 0.155D, 0.3666D),
						MaterialRules.sequence(
							MaterialRules.ifTrue(
								ConfigPredicate.materialCondition(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_COARSE_DIRT)),
								MaterialRules.getRule(materialRules, COARSE_DIRT_PATH)
							),
							MaterialRules.getRule(materialRules, GRASS_AND_DIRT)
						)
					),
					MaterialRules.getRule(materialRules, SAND_AND_VERY_DEEP_SANDSTONE)
				),
				WWBiomes.ARID_SAVANNA, WWBiomes.ARID_FOREST
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("moss_path_jungle"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				WWBiomeTags.MOSS_PATH_JUNGLE,
				configPredicates,
				WWConfigPredicates.GENERATE_MOSS,
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(WWNoises.JUNGLE_MOSS_PATH, 0.1D, 0.55D),
							MaterialRules.ifTrue(
								MaterialRules.noiseCondition2d(WWNoises.JUNGLE_MOSS_PATH_PATCH, 0.85D, 2D),
								FrozenLibMaterialRules.makeStateRule(Blocks.MOSS_BLOCK)
							)
						)
					)
				)
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("moss_ocean"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				WWBiomeTags.MOSS_PATH_OCEAN,
				configPredicates,
				WWConfigPredicates.GENERATE_OCEAN_MOSS,
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.not(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER)),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(WWNoises.OCEAN_MOSS, -0.65D, -0.25D),
							FrozenLibMaterialRules.makeStateRule(Blocks.MOSS_BLOCK)
						)
					)
				)
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("coarse_dirt_path"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				WWBiomeTags.COARSE_DIRT_PATH,
				configPredicates,
				WWConfigPredicates.GENERATE_COARSE_DIRT,
				MaterialRules.getRule(materialRules, COARSE_DIRT_PATH)
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("coarse_dirt_path_patchy"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				WWBiomeTags.COARSE_DIRT_PATH_PATCHY,
				configPredicates,
				WWConfigPredicates.GENERATE_COARSE_DIRT,
				MaterialRules.getRule(materialRules, COARSE_DIRT_PATH_PATCHY)
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("old_growth_snowy_pine_taiga"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
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
				),
				WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("old_growth_dark_forest"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.SURFACE, -0.0667, 0.04),
							FrozenLibMaterialRules.PODZOL
						)
					)
				),
				WWBiomes.OLD_GROWTH_DARK_FOREST
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("temperate_rainforest"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
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
				),
				WWBiomes.TEMPERATE_RAINFOREST
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("rainforest"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.ifTrue(MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenLibMaterialRules.makeStateRule(Blocks.MOSS_BLOCK)
						)
					)
				),
				WWBiomes.RAINFOREST
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("dying_forest_and_dying_mixed_forest"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
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
				),
				WWBiomes.DYING_FOREST, WWBiomes.DYING_MIXED_FOREST
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("maple_forest"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
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
				),
				WWBiomes.MAPLE_FOREST
			)
		);

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("tundra"),
			overworld,
			aboveSurfaceAndInBiomes(
				biomes,
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
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
				),
				WWBiomes.TUNDRA
			)
		);

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

		MaterialRuleAdditions.register(
			context,
			WWConstants.id("magmatic_caves"),
			overworld,
			MaterialRules.ifTrue(
				MaterialRules.isBiome(biomes, WWBiomes.MAGMATIC_CAVES),
				FrozenLibMaterialRules.avoidBedrockFloorAndRoof(
					materialConditions,
					MaterialRules.sequence(
						MaterialRules.ifTrue(MaterialRules.noiseCondition3d(WWNoises.MAGMATIC_CAVE_GRADIENT, -0.45F, -0.245F), gabbro),
						MaterialRules.ifTrue(MaterialRules.noiseCondition3d(WWNoises.MAGMATIC_CAVE_GRADIENT, -0.245F, -0.07F), magmaOrGabbroUnderWater),
						MaterialRules.ifTrue(MaterialRules.noiseCondition3d(WWNoises.MAGMATIC_CAVE_GRADIENT, -0.07F, 0.3F), gabbro),

						MaterialRules.ifTrue(MaterialRules.noiseCondition3d(WWNoises.MAGMATIC_CAVE_GRADIENT, 0.655, 1F), gabbro)
					)
				)
			)
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

		context.register(
			COARSE_DIRT_PATH_BASE,
			MaterialRules.ifTrue(
				MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
					MaterialRules.ifTrue(
						MaterialRules.noiseCondition2d(WWNoises.COARSE_DIRT_PATH, 0.26D, 0.55D),
						FrozenLibMaterialRules.makeStateRule(Blocks.COARSE_DIRT)
					)
				)
			)
		);

		context.register(
			COARSE_DIRT_PATH,
			MaterialRules.ifTrue(
				MaterialRules.noiseCondition2d(WWNoises.COARSE_DIRT_PATH_PATCH, -0.25D, 0.15D),
				MaterialRules.getRule(materialRules, COARSE_DIRT_PATH_BASE)
			)
		);

		context.register(
			COARSE_DIRT_PATH_PATCHY,
			MaterialRules.ifTrue(
				MaterialRules.not(MaterialRules.noiseCondition2d(WWNoises.COARSE_DIRT_PATH_PATCH_PATCHY, -0.1D, 0.07D)),
				MaterialRules.getRule(materialRules, COARSE_DIRT_PATH_BASE)
			)
		);

		context.register(
			SANDSTONE_PATH,
			MaterialRules.ifTrue(
				MaterialRules.noiseCondition2d(WWNoises.COARSE_DIRT_PATH_PATCH, -0.25D, 0.15D),
				MaterialRules.ifTrue(
					MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.ON_FLOOR),
					MaterialRules.ifTrue(
						MaterialRules.getCondition(materialConditions, VanillaMaterialConditions.NOT_UNDERWATER),
						MaterialRules.ifTrue(
							MaterialRules.noiseCondition2d(WWNoises.COARSE_DIRT_PATH, 0.26D, 0.55D),
							FrozenLibMaterialRules.makeStateRule(Blocks.SANDSTONE)
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
