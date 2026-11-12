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

package net.frozenblock.wilderwild.data.worldgen.modification;

import java.util.List;
import net.frozenblock.lib.levelgen.biome.api.modifications.ModificationPhase;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.FeatureModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.selector.BiomeSelector;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.placement.WWTreePlacements;
import net.frozenblock.wilderwild.data.worldgen.placement.WWTreeVegetationPlacements;
import net.frozenblock.wilderwild.registry.WWBiomeSelectors;
import net.frozenblock.wilderwild.tag.WWTreeFeatureBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import static net.frozenblock.lib.levelgen.biome.api.modifications.data.FeatureModification.*;

public final class WWTreeFeatureModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		// PALM
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.PALM, WWTreeVegetationPlacements.PALM);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.PALM_JUNGLE, WWTreeVegetationPlacements.PALM_JUNGLE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.PALM_RARE, WWTreeVegetationPlacements.PALM_RARE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.PALM_WARM_BEACH, WWTreeVegetationPlacements.PALMS_WARM_BEACH);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.PALM_OASIS, WWTreeVegetationPlacements.PALMS_OASIS);

		// SAVANNA
		registerSavannaReplacement(context, "trees_savanna", true, false, WWTreeVegetationPlacements.TREE_SAVANNA);
		registerSavannaReplacement(context, "trees_savanna_baobab", true, true, WWTreeVegetationPlacements.TREE_SAVANNA_BAOBAB);
		registerSavannaReplacement(context, "trees_savanna_baobab_vanilla", false, true, WWTreeVegetationPlacements.TREE_SAVANNA_BAOBAB_VANILLA);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_WINDSWEPT_SAVANNA,
			List.of(VegetationPlacements.TREES_WINDSWEPT_SAVANNA),
			List.of(WWTreeVegetationPlacements.TREE_WINDSWEPT_SAVANNA)
		);

		// SWAMP
		registerSwampReplacement(context, "trees_swamp", true, false, List.of(WWTreeVegetationPlacements.TREE_SWAMP));
		registerSwampReplacement(
			context,
			"trees_swamp_willow",
			true,
			true,
			List.of(
				WWTreeVegetationPlacements.TREE_SWAMP_SURFACE_WILLOW,
				WWTreeVegetationPlacements.TREE_SWAMP_WATER_SHALLOW,
				WWTreeVegetationPlacements.TREE_SWAMP_WATER
			)
		);

		// REPLACEMENTS
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_PLAINS,
			List.of(VegetationPlacements.TREES_PLAINS),
			List.of(WWTreeVegetationPlacements.TREE_PLAINS)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_SNOWY,
			List.of(VegetationPlacements.TREES_SNOWY),
			List.of(WWTreeVegetationPlacements.TREE_SNOWY)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_FOREST,
			List.of(VegetationPlacements.TREES_BIRCH_AND_OAK_LEAF_LITTER),
			List.of(WWTreeVegetationPlacements.BIRCH_AND_OAK, WWTreeVegetationPlacements.BIRCH_AND_OAK_ORIGINAL)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_FLOWER_FOREST,
			List.of(VegetationPlacements.TREES_BIRCH_AND_OAK_LEAF_LITTER, VegetationPlacements.TREES_FLOWER_FOREST),
			List.of(WWTreeVegetationPlacements.TREE_FLOWER_FOREST)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_BIRCH_FOREST,
			List.of(VegetationPlacements.TREES_BIRCH),
			List.of(WWTreeVegetationPlacements.TREE_BIRCH)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_OLD_GROWTH_BIRCH_FOREST,
			List.of(VegetationPlacements.BIRCH_TALL),
			List.of(WWTreeVegetationPlacements.TREE_BIRCH_TALL)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_TAIGA,
			List.of(VegetationPlacements.TREES_TAIGA),
			List.of(WWTreeVegetationPlacements.TREE_TAIGA)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_TAIGA_SNOWY,
			List.of(VegetationPlacements.TREES_TAIGA),
			List.of(WWTreeVegetationPlacements.TREE_TAIGA_NO_LITTER)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_TALL_PINE_TAIGA,
			List.of(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA),
			List.of(WWTreeVegetationPlacements.TREE_OLD_GROWTH_PINE_TAIGA)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_TALL_SPRUCE_TAIGA,
			List.of(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA),
			List.of(WWTreeVegetationPlacements.TREE_OLD_GROWTH_SPRUCE_TAIGA)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_JUNGLE,
			List.of(VegetationPlacements.TREES_JUNGLE),
			List.of(WWTreeVegetationPlacements.TREE_JUNGLE)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_SPARSE_JUNGLE,
			List.of(VegetationPlacements.TREES_SPARSE_JUNGLE),
			List.of(WWTreeVegetationPlacements.TREE_SPARSE_JUNGLE)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_BAMBOO_JUNGLE,
			List.of(VegetationPlacements.BAMBOO_VEGETATION),
			List.of(WWTreeVegetationPlacements.TREE_BAMBOO_JUNGLE)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_DARK_FOREST,
			List.of(VegetationPlacements.DARK_FOREST_VEGETATION),
			List.of(WWTreeVegetationPlacements.DARK_FOREST_VEGETATION)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_PALE_GARDEN,
			List.of(VegetationPlacements.PALE_GARDEN_VEGETATION),
			List.of(WWTreeVegetationPlacements.TREE_PALE_GARDEN)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_WOODED_BADLANDS,
			List.of(VegetationPlacements.TREES_BADLANDS),
			List.of(WWTreeVegetationPlacements.TREE_WOODED_BADLANDS)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_MANGROVE_SWAMP,
			List.of(VegetationPlacements.TREES_MANGROVE),
			List.of(WWTreeVegetationPlacements.MANGROVE)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_CHERRY_GROVE,
			List.of(VegetationPlacements.TREES_CHERRY),
			List.of(WWTreeVegetationPlacements.CHERRY)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_DAPPLED_FOREST,
			List.of(VegetationPlacements.TREES_DAPPLED_FOREST),
			List.of(WWTreeVegetationPlacements.POPLAR)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_MEADOW,
			List.of(VegetationPlacements.TREES_MEADOW),
			List.of(WWTreeVegetationPlacements.TREE_MEADOW)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_GROVE,
			List.of(VegetationPlacements.TREES_GROVE),
			List.of(WWTreeVegetationPlacements.TREE_GROVE)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_WINDSWEPT_FOREST,
			List.of(VegetationPlacements.TREES_WINDSWEPT_FOREST),
			List.of(WWTreeVegetationPlacements.TREE_WINDSWEPT_FOREST)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_WINDSWEPT_HILLS,
			List.of(VegetationPlacements.TREES_WINDSWEPT_HILLS),
			List.of(WWTreeVegetationPlacements.TREE_WINDSWEPT_HILLS)
		);
		registerReplacement(
			context,
			WWTreeFeatureBiomeTags.TREE_WATER,
			List.of(VegetationPlacements.TREES_WATER),
			List.of(WWTreeVegetationPlacements.BIG_BUSH_WATER)
		);

		// ADDITIONS
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SHORT_SPRUCE, WWTreeVegetationPlacements.SHORT_SPRUCE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SHORT_SPRUCE_SPARSE_LEAF_LITTER, WWTreeVegetationPlacements.SHORT_SPRUCE_SPARSE_LEAF_LITTER);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SHORT_MEGA_SPRUCE, WWTreeVegetationPlacements.SHORT_MEGA_SPRUCE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SHORT_MEGA_SPRUCE_ON_SNOW, WWTreeVegetationPlacements.SHORT_MEGA_SPRUCE_ON_SNOW);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.BIG_BUSH_FOREST, WWTreeVegetationPlacements.BIG_BUSH_FOREST);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.BIG_BUSH_FOREST_COMMON, WWTreeVegetationPlacements.BIG_BUSH_FOREST_COMMON);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.BIG_BUSH, WWTreeVegetationPlacements.BIG_BUSH);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.BIG_BUSH_ON_SAND, WWTreeVegetationPlacements.BIG_BUSH_ON_SAND);

		// FALLEN
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_OAK_AND_BIRCH_MOSSY, WWTreeVegetationPlacements.FALLEN_OAK_AND_BIRCH_MOSSY);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_OAK_AND_BIRCH_DARK_FOREST, WWTreeVegetationPlacements.FALLEN_OAK_AND_BIRCH_DARK_FOREST);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_BIRCH, WWTreeVegetationPlacements.FALLEN_BIRCH);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_BIRCH_AND_OAK, WWTreeVegetationPlacements.FALLEN_BIRCH_AND_OAK);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_BIRCH_AND_OAK_SPARSE, WWTreeVegetationPlacements.FALLEN_BIRCH_AND_OAK_SPARSE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_BIRCH_AND_SPRUCE, WWTreeVegetationPlacements.FALLEN_BIRCH_AND_SPRUCE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_OAK_AND_BIRCH_AND_SPRUCE, WWTreeVegetationPlacements.FALLEN_OAK_AND_BIRCH_AND_SPRUCE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_OAK_AND_BIRCH_AND_SPRUCE_MOSSY, WWTreeVegetationPlacements.FALLEN_OAK_AND_BIRCH_AND_SPRUCE_MOSSY);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_SPRUCE_AND_OAK, WWTreeVegetationPlacements.FALLEN_SPRUCE_AND_OAK);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_SPRUCE, WWTreeVegetationPlacements.FALLEN_SPRUCE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_SPRUCE_CLEAN, WWTreeVegetationPlacements.FALLEN_SPRUCE_CLEAN);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.LARGE_FALLEN_SPRUCE, WWTreeVegetationPlacements.LARGE_FALLEN_SPRUCE);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.LARGE_FALLEN_SPRUCE_COMMON, WWTreeVegetationPlacements.LARGE_FALLEN_SPRUCE_COMMON);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.LARGE_FALLEN_SPRUCE_CLEAN, WWTreeVegetationPlacements.LARGE_FALLEN_SPRUCE_CLEAN);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.LARGE_FALLEN_SPRUCE_CLEAN_COMMON, WWTreeVegetationPlacements.LARGE_FALLEN_SPRUCE_CLEAN_COMMON);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.FALLEN_DARK_OAK, WWTreeVegetationPlacements.FALLEN_DARK_OAK);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.FALLEN_DARK_OAK_COMMON, WWTreeVegetationPlacements.FALLEN_DARK_OAK_COMMON);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.FALLEN_PALE_OAK, WWTreeVegetationPlacements.FALLEN_PALE_OAK);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.LARGE_FALLEN_JUNGLE, WWTreeVegetationPlacements.LARGE_FALLEN_JUNGLE);
		registerLocalAddition(context, WWTreeFeatureBiomeTags.LARGE_FALLEN_JUNGLE_COMMON, WWTreeVegetationPlacements.LARGE_FALLEN_JUNGLE_COMMON);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_ACACIA_AND_OAK, WWTreeVegetationPlacements.FALLEN_ACACIA_AND_OAK);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_SWAMP, WWTreeVegetationPlacements.FALLEN_WILLOW_AND_OAK);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_MANGROVE, WWTreeVegetationPlacements.FALLEN_MANGROVE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_CYPRESS_AND_OAK, WWTreeVegetationPlacements.FALLEN_CYPRESS_AND_OAK);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_PALM, WWTreeVegetationPlacements.FALLEN_PALM);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_PALM_DESERT, WWTreeVegetationPlacements.FALLEN_PALM_DESERT);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_PALM_RARE, WWTreeVegetationPlacements.FALLEN_PALM_RARE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_PALM_AND_JUNGLE_AND_OAK, WWTreeVegetationPlacements.FALLEN_PALM_AND_JUNGLE_AND_OAK);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_CHERRY, WWTreeVegetationPlacements.FALLEN_CHERRY);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_MAPLE, WWTreeVegetationPlacements.FALLEN_MAPLE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.FALLEN_POPLAR, WWTreeVegetationPlacements.FALLEN_POPLAR);
		registerFluidSpringAddition(context, WWTreeFeatureBiomeTags.FALLEN_CRIMSON_FUNGI, WWTreePlacements.FALLEN_CRIMSON);
		registerFluidSpringAddition(context, WWTreeFeatureBiomeTags.FALLEN_WARPED_FUNGI, WWTreePlacements.FALLEN_WARPED);

		// SNAPPED
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_OAK,
			WWTreeVegetationPlacements.SNAPPED_OAK,
			WWTreeVegetationPlacements.SNAPPED_OAK_CLEARING
		);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_BIRCH,
			WWTreeVegetationPlacements.SNAPPED_BIRCH,
			WWTreeVegetationPlacements.SNAPPED_BIRCH_CLEARING
		);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_BIRCH_AND_OAK,
			WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_OAK,
			WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_OAK_CLEARING
		);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_BIRCH_AND_SPRUCE,
			WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_SPRUCE,
			WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_SPRUCE_CLEARING
		);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE,
			WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE,
			WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING
		);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_BIRCH_AND_JUNGLE, WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_JUNGLE);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_SPRUCE,
			WWTreeVegetationPlacements.SNAPPED_SPRUCE,
			WWTreeVegetationPlacements.SNAPPED_SPRUCE_CLEARING
		);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_SPRUCE_ON_SNOW,
			WWTreeVegetationPlacements.SNAPPED_SPRUCE_ON_SNOW,
			WWTreeVegetationPlacements.SNAPPED_SPRUCE_ON_SNOW_CLEARING
		);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_LARGE_SPRUCE, WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_LARGE_SPRUCE_COMMON,
			WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE_COMMON,
			WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE_CLEARING
		);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_LARGE_SPRUCE_ON_SNOW,
			WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE_ON_SNOW,
			WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING
		);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_DARK_OAK,
			WWTreeVegetationPlacements.SNAPPED_DARK_OAK,
			WWTreeVegetationPlacements.SNAPPED_DARK_OAK_CLEARING
		);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_PALE_OAK, WWTreeVegetationPlacements.SNAPPED_PALE_OAK);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_JUNGLE, WWTreeVegetationPlacements.SNAPPED_JUNGLE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_LARGE_JUNGLE, WWTreeVegetationPlacements.SNAPPED_LARGE_JUNGLE);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_ACACIA, WWTreeVegetationPlacements.SNAPPED_ACACIA);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_ACACIA_AND_OAK, WWTreeVegetationPlacements.SNAPPED_ACACIA_AND_OAK);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_CYPRESS, WWTreeVegetationPlacements.SNAPPED_CYPRESS);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_CHERRY, WWTreeVegetationPlacements.SNAPPED_CHERRY);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_MAPLE,
			WWTreeVegetationPlacements.SNAPPED_MAPLE,
			WWTreeVegetationPlacements.SNAPPED_MAPLE_CLEARING
		);
		registerVegetalAddition(
			context,
			WWTreeFeatureBiomeTags.SNAPPED_POPLAR,
			WWTreeVegetationPlacements.SNAPPED_POPLAR,
			WWTreeVegetationPlacements.SNAPPED_POPLAR_CLEARING
		);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_CRIMSON_FUNGI, WWTreePlacements.SNAPPED_CRIMSON);
		registerVegetalAddition(context, WWTreeFeatureBiomeTags.SNAPPED_WARPED_FUNGI, WWTreePlacements.SNAPPED_WARPED);
	}

	private static void registerReplacement(
		BootstrapContext<BiomeModification> context,
		TagKey<Biome> biomes,
		List<ResourceKey<PlacedFeature>> removals,
		List<FrozenLibPlacedFeature> additions
	) {
		registerVegetalReplacement(context, biomes, WWBiomeSelectors.GENERATE_TREE, removals, additions);
	}

	private static void registerSavannaReplacement(
		BootstrapContext<BiomeModification> context,
		String name,
		boolean generateTree,
		boolean generateBaobab,
		FrozenLibPlacedFeature feature
	) {
		final HolderGetter<BiomeSelector> biomeSelectors = context.lookup(FrozenLibRegistries.BIOME_SELECTOR);

		context.register(
			createKey(name),
			FeatureModification.forReplacement(
				Holder.direct(
					BiomeSelector.allOf(
						HolderSet.direct(
							biomeSelectors.getOrThrow(generateTree ? WWBiomeSelectors.GENERATE_TREE : WWBiomeSelectors.CANNOT_GENERATE_TREE),
							biomeSelectors.getOrThrow(generateBaobab ? WWBiomeSelectors.GENERATE_BAOBAB : WWBiomeSelectors.CANNOT_GENERATE_BAOBAB),
							biomeSelectors.getOrThrow(WWBiomeSelectors.IN_SAVANNA_TREE_TAG)
						)
					)
				),
				ModificationPhase.REPLACEMENTS,
				HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(VegetationPlacements.TREES_SAVANNA)),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				feature.asHolderSet()
			)
		);
	}

	private static void registerSwampReplacement(
		BootstrapContext<BiomeModification> context,
		String name,
		boolean generateTree,
		boolean generateWillow,
		List<FrozenLibPlacedFeature> features
	) {
		final HolderGetter<BiomeSelector> biomeSelectors = context.lookup(FrozenLibRegistries.BIOME_SELECTOR);

		context.register(
			createKey(name),
			FeatureModification.forReplacement(
				Holder.direct(
					BiomeSelector.allOf(
						HolderSet.direct(
							biomeSelectors.getOrThrow(generateTree ? WWBiomeSelectors.GENERATE_TREE : WWBiomeSelectors.CANNOT_GENERATE_TREE),
							biomeSelectors.getOrThrow(generateWillow ? WWBiomeSelectors.GENERATE_WILLOW : WWBiomeSelectors.CANNOT_GENERATE_WILLOW),
							biomeSelectors.getOrThrow(WWBiomeSelectors.IN_SWAMP_TREE_TAG)
						)
					)
				),
				ModificationPhase.REPLACEMENTS,
				HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(VegetationPlacements.TREES_SWAMP)),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				HolderSet.direct(features.stream().map(FrozenLibPlacedFeature::getHolder).toList())
			)
		);
	}

	private static ResourceKey<BiomeModification> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.BIOME_MODIFICATION, WWConstants.id("feature/tree/" + name));
	}

	private WWTreeFeatureModifications() {}
}
