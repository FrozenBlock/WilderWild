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
import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import static net.frozenblock.wilderwild.data.worldgen.modification.WWFeatureModificationUtils.registerVegetalAddition;
import static net.frozenblock.wilderwild.data.worldgen.modification.WWFeatureModificationUtils.registerVegetalReplacement;
import net.frozenblock.wilderwild.data.worldgen.placement.WWFlowerPlacements;
import net.frozenblock.wilderwild.registry.WWBiomeSelectors;
import net.frozenblock.wilderwild.tag.WWFlowerFeatureBiomeTags;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WWFlowerFeatureModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		// REPLACEMENTS
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.PLAINS_FLOWERS,
			List.of(VegetationPlacements.FLOWER_PLAINS),
			List.of(WWFlowerPlacements.FLOWER_DEFAULT_PLAINS, WWFlowerPlacements.FLOWER_PLAINS)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.SNOWY_PLAINS_FLOWERS,
			List.of(VegetationPlacements.FLOWER_PLAINS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.FLOWER_DEFAULT_SNOWY_PLAINS)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.SUNFLOWER_PLAINS_FLOWERS,
			List.of(VegetationPlacements.FLOWER_PLAINS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.WILDFLOWERS_SUNFLOWER_PLAINS, WWFlowerPlacements.PATCH_FLOWER_SUNFLOWER_PLAINS)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.FLOWER_FIELD_FLOWERS,
			List.of(
				VegetationPlacements.FLOWER_FOREST_FLOWERS,
				VegetationPlacements.FLOWER_FLOWER_FOREST,
				VegetationPlacements.FOREST_FLOWERS,
				VegetationPlacements.FLOWER_DEFAULT
			),
			List.of(WWFlowerPlacements.FLOWER_FLOWER_FIELD, WWFlowerPlacements.PATCH_FLOWER_FLOWER_FIELD)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.FLOWER_FOREST, WWFlowerPlacements.PATCH_FLOWER_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.SPARSE_FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.FLOWER_SPARSE_FOREST, WWFlowerPlacements.PATCH_FLOWER_SPARSE_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.FLOWER_FOREST_FLOWERS,
			List.of(VegetationPlacements.FLOWER_FOREST_FLOWERS, VegetationPlacements.FLOWER_FLOWER_FOREST, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.FLOWER_FLOWER_FOREST_1, WWFlowerPlacements.FLOWER_FLOWER_FOREST_2, WWFlowerPlacements.PATCH_FLOWER_FLOWER_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.WILDFLOWERS_BIRCH_FOREST,
			List.of(VegetationPlacements.WILDFLOWERS_BIRCH_FOREST),
			List.of(WWFlowerPlacements.WILDFLOWERS_BIRCH_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.WILDFLOWERS_SEMI_BIRCH_FOREST,
			List.of(VegetationPlacements.WILDFLOWERS_BIRCH_FOREST),
			List.of(WWFlowerPlacements.WILDFLOWERS_SEMI_BIRCH_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.BIRCH_FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.PATCH_FLOWER_BIRCH_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.OLD_GROWTH_BIRCH_FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.PATCH_FLOWER_OLD_GROWTH_BIRCH_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.SEMI_BIRCH_FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.PATCH_FLOWER_SEMI_BIRCH_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.BIRCH_TAIGA_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.PATCH_FLOWER_BIRCH_TAIGA)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.DARK_BIRCH_FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.FLOWER_FOREST, WWFlowerPlacements.PATCH_FLOWER_DARK_BIRCH_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.BIRCH_JUNGLE_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT, VegetationPlacements.FLOWER_WARM),
			List.of(WWFlowerPlacements.PATCH_FLOWER_BIRCH_JUNGLE)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.SPARSE_BIRCH_JUNGLE_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT, VegetationPlacements.FLOWER_WARM),
			List.of(WWFlowerPlacements.PATCH_FLOWER_SPARSE_BIRCH_JUNGLE)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.DARK_FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.PATCH_PHLOX_AND_LANTANAS_DARK_FOREST, WWFlowerPlacements.FLOWER_DARK_FOREST, WWFlowerPlacements.PATCH_FLOWER_DARK_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.DARK_TAIGA_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.PHLOX_DARK_TAIGA, WWFlowerPlacements.FLOWER_DARK_TAIGA, WWFlowerPlacements.PATCH_FLOWER_DARK_TAIGA)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.PALE_GARDEN_FLOWERS,
			// FLOWER_PALE_GARDEN is left untouched as it's literally just a rare single Eyeblossom
			List.of(VegetationPlacements.PALE_GARDEN_FLOWERS),
			List.of(WWFlowerPlacements.PATCH_FLOWER_PALE_GARDEN)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.MAPLE_FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.FLOWER_MAPLE_FOREST, WWFlowerPlacements.PATCH_FLOWER_MAPLE_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.TUNDRA_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.PATCH_FLOWER_TUNDRA)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.DAPPLED_FOREST_FLOWERS,
			List.of(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT),
			List.of(WWFlowerPlacements.PATCH_FLOWER_DAPPLED_FOREST)
		);
		registerReplacement(
			context,
			WWFlowerFeatureBiomeTags.MEADOW_FLOWERS,
			List.of(VegetationPlacements.WILDFLOWERS_MEADOW, VegetationPlacements.WILDFLOWERS_BIRCH_FOREST, VegetationPlacements.FLOWER_MEADOW),
			List.of(WWFlowerPlacements.PATCH_WILDFLOWERS_AND_PHLOX_MEADOW, WWFlowerPlacements.FLOWER_MEADOW)
		);

		// ADDITIONS
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.MIXED_FOREST_FLOWERS, WWFlowerPlacements.SEEDING_DANDELION_MIXED_FOREST);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.DYING_FOREST_FLOWERS, WWFlowerPlacements.PATCH_FLOWER_DYING_FOREST);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.DYING_MIXED_FOREST_FLOWERS, WWFlowerPlacements.PATCH_FLOWER_DYING_MIXED_FOREST);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.SNOWY_DYING_FOREST_FLOWERS, WWFlowerPlacements.SEEDING_DANDELION_SNOWY_DYING_FOREST);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.JUNGLE_FLOWERS, WWFlowerPlacements.FLOWER_JUNGLE, WWFlowerPlacements.PATCH_FLOWER_JUNGLE);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.SPARSE_JUNGLE_FLOWERS, WWFlowerPlacements.FLOWER_SPARSE_JUNGLE, WWFlowerPlacements.PATCH_FLOWER_SPARSE_JUNGLE);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.SAVANNA_FLOWERS, WWFlowerPlacements.LANTANAS_SAVANNA);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.MILKWEED_SWAMP, WWFlowerPlacements.MILKWEED_SWAMP);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.SWAMP_FLOWERS, WWFlowerPlacements.PHLOX_SWAMP);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.MANGROVE_SWAMP_FLOWERS, WWFlowerPlacements.PATCH_FLOWER_MANGROVE_SWAMP);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.CHERRY_GROVE_FLOWERS, WWFlowerPlacements.PATCH_FLOWER_CHERRY_GROVE);
		registerVegetalAddition(context, WWFlowerFeatureBiomeTags.WINDSWEPT_FLOWERS, WWFlowerPlacements.SEEDING_DANDELION_WINDSWEPT);
 	}

	private static void registerReplacement(
		BootstrapContext<BiomeModification> context,
		TagKey<Biome> biomes,
		List<ResourceKey<PlacedFeature>> removals,
		List<FrozenLibPlacedFeature> additions
	) {
		registerVegetalReplacement(context, biomes, WWBiomeSelectors.GENERATE_FLOWER, removals, additions);
	}

	private WWFlowerFeatureModifications() {}
}
