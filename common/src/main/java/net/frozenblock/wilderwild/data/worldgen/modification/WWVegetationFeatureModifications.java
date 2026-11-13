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
import lombok.experimental.UtilityClass;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import static net.frozenblock.lib.levelgen.biome.api.modifications.data.FeatureModification.*;
import net.frozenblock.wilderwild.data.worldgen.placement.WWVegetationPlacements;
import net.frozenblock.wilderwild.registry.WWBiomeSelectors;
import net.frozenblock.wilderwild.tag.WWVegetationFeatureBiomeTags;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWVegetationFeatureModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		// LEAF LITTER
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.LEAF_LITTER,
			WWBiomeSelectors.GENERATE_LEAF_LITTER_OAK,
			List.of(VegetationPlacements.PATCH_LEAF_LITTER),
			List.of(WWVegetationPlacements.PATCH_LEAF_LITTER)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.LEAF_LITTER_UNCOMMON,
			WWBiomeSelectors.GENERATE_LEAF_LITTER_OAK,
			List.of(VegetationPlacements.PATCH_LEAF_LITTER),
			List.of(WWVegetationPlacements.PATCH_LEAF_LITTER_UNCOMMON)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.DARK_OAK_LEAF_LITTER,
			WWBiomeSelectors.GENERATE_LEAF_LITTER_DARK_OAK,
			List.of(VegetationPlacements.PATCH_LEAF_LITTER),
			List.of(WWVegetationPlacements.PATCH_DARK_OAK_LEAF_LITTER)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.PALE_OAK_LEAF_LITTER,
			WWBiomeSelectors.GENERATE_LEAF_LITTER_PALE_OAK,
			List.of(VegetationPlacements.PATCH_LEAF_LITTER),
			List.of(WWVegetationPlacements.PATCH_PALE_OAK_LEAF_LITTER)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.SPRUCE_LEAF_LITTER,
			WWBiomeSelectors.GENERATE_LEAF_LITTER_SPRUCE,
			List.of(VegetationPlacements.PATCH_LEAF_LITTER),
			List.of(WWVegetationPlacements.PATCH_SPRUCE_LEAF_LITTER)
		);

		// GRASS
		// REPLACEMENTS
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.GRASS_FOREST,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_GRASS_FOREST),
			List.of(WWVegetationPlacements.PATCH_FERN_AND_GRASS, WWVegetationPlacements.TALL_GRASS)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.GRASS_FOREST_LIGHT,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_GRASS_FOREST),
			List.of(WWVegetationPlacements.PATCH_FERN_AND_GRASS_LIGHT, WWVegetationPlacements.TALL_GRASS_LIGHT)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.GRASS_BIRCH_FOREST,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_GRASS_FOREST),
			List.of(WWVegetationPlacements.PATCH_GRASS_BIRCH, WWVegetationPlacements.TALL_GRASS)
		);
		// ADDITIONS
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.GRASS_RARE, WWVegetationPlacements.PATCH_GRASS_RARE);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.TALL_GRASS_DENSE, WWVegetationPlacements.PATCH_TALL_GRASS_DENSE);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.TALL_GRASS_FLOWER_FIELD, WWVegetationPlacements.PATCH_TALL_GRASS_FLOWER_FIELD);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.TALL_GRASS_SWAMP, WWVegetationPlacements.PATCH_TALL_GRASS_SWAMP);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.GRASS_WATER, WWVegetationPlacements.PATCH_GRASS_WATER);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.LARGE_FERN_AND_GRASS, WWVegetationPlacements.PATCH_LARGE_FERN_AND_GRASS);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.LARGE_FERN_AND_GRASS_RARE, WWVegetationPlacements.PATCH_LARGE_FERN_AND_GRASS_RARE);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.FERN_DENSE, WWVegetationPlacements.PATCH_FERN_DENSE);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.FERN_SWAMP, WWVegetationPlacements.PATCH_FERN_SWAMP);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.CLOVERS, WWVegetationPlacements.PATCH_CLOVERS);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.CLOVERS_SPARSE, WWVegetationPlacements.PATCH_CLOVERS_SPARSE);

		// DRY GRASS
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.DRY_GRASS_DESERT,
			WWBiomeSelectors.GENERATE_DRY_GRASS,
			List.of(VegetationPlacements.PATCH_DRY_GRASS_DESERT),
			List.of(WWVegetationPlacements.PATCH_DRY_GRASS_DESERT)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.DRY_GRASS_BADLANDS,
			WWBiomeSelectors.GENERATE_DRY_GRASS,
			List.of(VegetationPlacements.PATCH_DRY_GRASS_BADLANDS),
			List.of(WWVegetationPlacements.PATCH_DRY_GRASS_BADLANDS)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.DRY_GRASS_BEACH,
			WWBiomeSelectors.GENERATE_DRY_GRASS,
			List.of(),
			List.of(WWVegetationPlacements.PATCH_DRY_GRASS_BEACH)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.DRY_GRASS_BETA_BEACH,
			WWBiomeSelectors.GENERATE_DRY_GRASS,
			List.of(VegetationPlacements.PATCH_DRY_GRASS_DESERT),
			List.of(WWVegetationPlacements.PATCH_DRY_GRASS_BETA_BEACH)
		);

		// FROZEN GRASS
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FROZEN_GRASS_PLAIN,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_GRASS_BADLANDS, VegetationPlacements.PATCH_GRASS_PLAIN),
			List.of(WWVegetationPlacements.PATCH_FROZEN_GRASS_PLAIN)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FROZEN_GRASS_FOREST,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_GRASS_FOREST),
			List.of(WWVegetationPlacements.PATCH_FROZEN_GRASS_FOREST)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FROZEN_GRASS_NORMAL,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_GRASS_NORMAL),
			List.of(WWVegetationPlacements.PATCH_FROZEN_GRASS_NORMAL)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FROZEN_GRASS_TAIGA,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_GRASS_TAIGA),
			List.of(WWVegetationPlacements.PATCH_FROZEN_GRASS_TAIGA)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FROZEN_GRASS_TAIGA_2,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_GRASS_TAIGA_2),
			List.of(WWVegetationPlacements.PATCH_FROZEN_GRASS_TAIGA_2)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FROZEN_TALL_GRASS_PLAIN,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_TALL_GRASS_2),
			List.of(WWVegetationPlacements.PATCH_FROZEN_TALL_GRASS_2)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FROZEN_BUSH,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_BUSH),
			List.of(WWVegetationPlacements.PATCH_FROZEN_BUSH)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FROZEN_LARGE_FERN,
			WWBiomeSelectors.GENERATE_GRASS,
			List.of(VegetationPlacements.PATCH_LARGE_FERN),
			List.of(WWVegetationPlacements.PATCH_FROZEN_LARGE_FERN)
		);

		// FIREFLY BUSH
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FIREFLY_BUSH,
			WWBiomeSelectors.GENERATE_FIREFLY_BUSH,
			List.of(
				VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER,
				VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP,
				VegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP
			),
			List.of(WWVegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER)
		);
		registerVegetalReplacement(
			context,
			WWVegetationFeatureBiomeTags.FIREFLY_BUSH_SWAMP,
			WWBiomeSelectors.GENERATE_FIREFLY_BUSH,
			List.of(
				VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER,
				VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP,
				VegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP
			),
			List.of(WWVegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP, WWVegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP)
		);

		// SHRUB
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB, WWVegetationPlacements.PATCH_SHRUB);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_SPARSE, WWVegetationPlacements.PATCH_SHRUB_SPARSE);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_FLOWER_FIELD, WWVegetationPlacements.PATCH_SHRUB_FLOWER_FIELD);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_JUNGLE, WWVegetationPlacements.PATCH_SHRUB_JUNGLE);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_OASIS, WWVegetationPlacements.PATCH_SHRUB_OASIS);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_DESERT, WWVegetationPlacements.PATCH_SHRUB_DESERT);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_ARID, WWVegetationPlacements.PATCH_SHRUB_ARID);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_BADLANDS_SAND, WWVegetationPlacements.PATCH_SHRUB_BADLANDS_SAND);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_BADLANDS_SAND_RARE, WWVegetationPlacements.PATCH_SHRUB_BADLANDS_SAND_RARE);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.SHRUB_BADLANDS_TERRACOTTA, WWVegetationPlacements.PATCH_SHRUB_BADLANDS_TERRACOTTA);
		registerVegetalAddition(
			context,
			WWVegetationFeatureBiomeTags.SHRUB_WOODED_BADLANDS,
			WWVegetationPlacements.PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA,
			WWVegetationPlacements.PATCH_SHRUB_WOODED_BADLANDS_DIRT
		);

		// MUSHROOM
		registerTopLayerAddition(context, WWVegetationFeatureBiomeTags.BROWN_MUSHROOM_COMMON, WWVegetationPlacements.BROWN_MUSHROOM);
		registerTopLayerAddition(context, WWVegetationFeatureBiomeTags.RED_MUSHROOM_COMMON, WWVegetationPlacements.RED_MUSHROOM);
		registerTopLayerAddition(context, WWVegetationFeatureBiomeTags.PALE_MUSHROOM, WWVegetationPlacements.PALE_MUSHROOM);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.HUGE_PALE_MUSHROOM, WWVegetationPlacements.HUGE_PALE_MUSHROOM);
		registerTopLayerAddition(context, WWVegetationFeatureBiomeTags.PATCH_MUSHROOM_DARK_FOREST, WWVegetationPlacements.PATCH_MUSHROOM_DARK_FOREST);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.HUGE_BROWN_MUSHROOM, WWVegetationPlacements.HUGE_BROWN_MUSHROOM);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.HUGE_RED_MUSHROOM, WWVegetationPlacements.HUGE_RED_MUSHROOM);
		registerTopLayerAddition(context, WWVegetationFeatureBiomeTags.HUGE_MUSHROOM, WWVegetationPlacements.HUGE_MUSHROOM);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.HUGE_MUSHROOM_SWAMP, WWVegetationPlacements.HUGE_MUSHROOM_SWAMP);
		registerTopLayerAddition(context, WWVegetationFeatureBiomeTags.HUGE_MUSHROOM_RARE, WWVegetationPlacements.HUGE_MUSHROOM_RARE);
		registerTopLayerAddition(context, WWVegetationFeatureBiomeTags.HUGE_MUSHROOM_MIXED_FOREST, WWVegetationPlacements.HUGE_MUSHROOM_MIXED_FOREST);
		registerTopLayerAddition(context, WWVegetationFeatureBiomeTags.HUGE_MUSHROOM_RAINFOREST, WWVegetationPlacements.HUGE_MUSHROOM_RAINFOREST);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.MYCELIUM_GROWTH, WWVegetationPlacements.PATCH_MYCELIUM_GROWTH);

		// CACTUS
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.CACTUS_TALL, WWVegetationPlacements.PATCH_CACTUS_TALL);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.CACTUS_TALL_BADLANDS, WWVegetationPlacements.PATCH_CACTUS_TALL_BADLANDS);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.PRICKLY_PEAR, WWVegetationPlacements.PATCH_PRICKLY_PEAR);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.PRICKLY_PEAR_RARE, WWVegetationPlacements.PATCH_PRICKLY_PEAR_RARE);

		// TUMBLEWEED
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.TUMBLEWEED, WWVegetationPlacements.PATCH_TUMBLEWEED);

		// POLLEN
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.POLLEN, WWVegetationPlacements.POLLEN);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.POLLEN_SPARSE, WWVegetationPlacements.POLLEN_SPARSE);

		// MOSS
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.MOSS_CARPET, WWVegetationPlacements.MOSS_CARPET);

		// AUBURN MOSS
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.AUBURN_MOSS, WWVegetationPlacements.AUBURN_MOSS);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.AUBURN_CREEPING_MOSS, WWVegetationPlacements.AUBURN_CREEPING_MOSS);

		// SWEET BERRY BUSH
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.BERRY_FOREST, WWVegetationPlacements.PATCH_BERRY_FOREST);

		// PUMPKIN
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.PUMPKIN_COMMON, WWVegetationPlacements.PATCH_PUMPKIN_COMMON);
		registerVegetalAddition(context, WWVegetationFeatureBiomeTags.PUMPKIN_UNCOMMON, WWVegetationPlacements.PATCH_PUMPKIN_UNCOMMON);

		// MISC
		registerOreAddition(context, WWVegetationFeatureBiomeTags.TERMITE_MOUND, WWVegetationPlacements.TERMITE_MOUND);
	}
}
