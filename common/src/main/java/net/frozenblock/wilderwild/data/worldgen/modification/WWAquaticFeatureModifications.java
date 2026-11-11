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

import java.util.Arrays;
import net.frozenblock.lib.levelgen.biome.api.modifications.ModificationPhase;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.FeatureModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.selector.BiomeSelector;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.placement.WWAquaticPlacements;
import net.frozenblock.wilderwild.tag.WWAquaticFeatureBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WWAquaticFeatureModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		// SURFACE
		// FLOWERING WATER LILY
		registerVegetalAddition(context, "flowering_water_lily", WWAquaticFeatureBiomeTags.FLOWERING_WATER_LILY, WWAquaticPlacements.PATCH_FLOWERING_WATER_LILY);
		// CATTAIL
		registerVegetalAddition(context, "cattail", WWAquaticFeatureBiomeTags.CATTAIL, WWAquaticPlacements.PATCH_CATTAIL);
		registerVegetalAddition(context, "cattail_common", WWAquaticFeatureBiomeTags.CATTAIL_COMMON, WWAquaticPlacements.PATCH_CATTAIL_COMMON);
		registerVegetalAddition(context, "cattail_uncommon", WWAquaticFeatureBiomeTags.CATTAIL_UNCOMMON, WWAquaticPlacements.PATCH_CATTAIL_UNCOMMON);
		// ALGAE
		registerVegetalAddition(context, "algae", WWAquaticFeatureBiomeTags.ALGAE, WWAquaticPlacements.PATCH_ALGAE);
		registerVegetalAddition(context, "algae_small", WWAquaticFeatureBiomeTags.ALGAE_SMALL, WWAquaticPlacements.PATCH_ALGAE_SMALL);
		// PLANKTON
		registerVegetalAddition(context, "plankton", WWAquaticFeatureBiomeTags.PLANKTON, WWAquaticPlacements.PATCH_PLANKTON);
		// POOL
		registerLakeAddition(context, "water_pool", WWAquaticFeatureBiomeTags.WATER_POOLS, WWAquaticPlacements.RIVER_POOL, WWAquaticPlacements.SMALL_RIVER_POOL);

		// VEGETATION
		// SEAGRASS
		registerVegetalAddition(context, "seagrass_meadow", WWAquaticFeatureBiomeTags.SEAGRASS_MEADOW, WWAquaticPlacements.SEAGRASS_MEADOW);
		// BARNACLES
		registerVegetalAddition(context, "barnacles", WWAquaticFeatureBiomeTags.BARNACLES, WWAquaticPlacements.PATCH_BARNACLES);
		registerVegetalAddition(context, "barnacles_common", WWAquaticFeatureBiomeTags.BARNACLES_COMMON, WWAquaticPlacements.PATCH_BARNACLES_COMMON);
		registerVegetalAddition(context, "barnacles_sparse", WWAquaticFeatureBiomeTags.BARNACLES_SPARSE, WWAquaticPlacements.PATCH_BARNACLES_SPARSE);
		registerVegetalAddition(context, "barnacles_rare", WWAquaticFeatureBiomeTags.BARNACLES_RARE, WWAquaticPlacements.PATCH_BARNACLES_RARE);
		registerVegetalAddition(context, "barnacles_structure", WWAquaticFeatureBiomeTags.BARNACLES_STRUCTURE, WWAquaticPlacements.PATCH_BARNACLES_STRUCTURE);
		// SEA ANEMONE
		registerVegetalAddition(context, "sea_anemone", WWAquaticFeatureBiomeTags.SEA_ANEMONE, WWAquaticPlacements.PATCH_SEA_ANEMONE);
		registerVegetalAddition(context, "sea_anemone_sparse", WWAquaticFeatureBiomeTags.SEA_ANEMONE_SPARSE, WWAquaticPlacements.PATCH_SEA_ANEMONE_SPARSE);
		registerVegetalAddition(context, "sea_anemone_rare", WWAquaticFeatureBiomeTags.SEA_ANEMONE_RARE, WWAquaticPlacements.PATCH_SEA_ANEMONE_RARE);

	}

	private static void registerVegetalAddition(
		BootstrapContext<BiomeModification> context,
		String name,
		TagKey<Biome> biomes,
		FrozenLibPlacedFeature... features
	) {
		context.register(
			createKey(name),
			FeatureModification.forAdditions(
				Holder.direct(
					BiomeSelector.inBiomeTag(context, biomes)
				),
				ModificationPhase.ADDITIONS,
				GenerationStep.Decoration.VEGETAL_DECORATION,
				Arrays.stream(features).map(FrozenLibPlacedFeature::getKey).toList()
			)
		);
	}

	private static void registerLakeAddition(
		BootstrapContext<BiomeModification> context,
		String name,
		TagKey<Biome> biomes,
		FrozenLibPlacedFeature... features
	) {
		context.register(
			createKey(name),
			FeatureModification.forAdditions(
				Holder.direct(
					BiomeSelector.inBiomeTag(context, biomes)
				),
				ModificationPhase.ADDITIONS,
				GenerationStep.Decoration.LAKES,
				Arrays.stream(features).map(FrozenLibPlacedFeature::getKey).toList()
			)
		);
	}

	private static ResourceKey<BiomeModification> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.BIOME_MODIFICATION, WWConstants.id("feature/aquatic/" + name));
	}

	private WWAquaticFeatureModifications() {}
}
