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

import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import static net.frozenblock.lib.levelgen.biome.api.modifications.data.FeatureModification.*;
import net.frozenblock.wilderwild.data.worldgen.placement.WWAquaticPlacements;
import net.frozenblock.wilderwild.tag.WWAquaticFeatureBiomeTags;
import net.minecraft.data.worldgen.BootstrapContext;

public final class WWAquaticFeatureModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		// SURFACE
		// FLOWERING WATER LILY
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.FLOWERING_WATER_LILY, WWAquaticPlacements.PATCH_FLOWERING_WATER_LILY);
		// CATTAIL
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.CATTAIL, WWAquaticPlacements.PATCH_CATTAIL);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.CATTAIL_COMMON, WWAquaticPlacements.PATCH_CATTAIL_COMMON);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.CATTAIL_UNCOMMON, WWAquaticPlacements.PATCH_CATTAIL_UNCOMMON);
		// ALGAE
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.ALGAE, WWAquaticPlacements.PATCH_ALGAE);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.ALGAE_SMALL, WWAquaticPlacements.PATCH_ALGAE_SMALL);
		// PLANKTON
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.PLANKTON, WWAquaticPlacements.PATCH_PLANKTON);
		// POOL
		registerLakeAddition(context, WWAquaticFeatureBiomeTags.WATER_POOLS, WWAquaticPlacements.RIVER_POOL, WWAquaticPlacements.SMALL_RIVER_POOL);

		// VEGETATION
		// SEAGRASS
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SEAGRASS_MEADOW, WWAquaticPlacements.SEAGRASS_MEADOW);
		// BARNACLES
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.BARNACLES, WWAquaticPlacements.PATCH_BARNACLES);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.BARNACLES_COMMON, WWAquaticPlacements.PATCH_BARNACLES_COMMON);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.BARNACLES_SPARSE, WWAquaticPlacements.PATCH_BARNACLES_SPARSE);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.BARNACLES_RARE, WWAquaticPlacements.PATCH_BARNACLES_RARE);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.BARNACLES_STRUCTURE, WWAquaticPlacements.PATCH_BARNACLES_STRUCTURE);
		// SEA ANEMONE
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SEA_ANEMONE, WWAquaticPlacements.PATCH_SEA_ANEMONE);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SEA_ANEMONE_SPARSE, WWAquaticPlacements.PATCH_SEA_ANEMONE_SPARSE);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SEA_ANEMONE_RARE, WWAquaticPlacements.PATCH_SEA_ANEMONE_RARE);
		// SEA WHIP
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SEA_WHIP, WWAquaticPlacements.PATCH_SEA_WHIP);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SEA_WHIP_SPARSE, WWAquaticPlacements.PATCH_SEA_WHIP_SPARSE);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SEA_WHIP_RARE, WWAquaticPlacements.PATCH_SEA_WHIP_RARE);
		// SPONGE BUD
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SPONGE_BUD, WWAquaticPlacements.SPONGE_BUDS);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.SPONGE_BUD_RARE, WWAquaticPlacements.SPONGE_BUDS_RARE);
		// TUBE WORMS
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.TUBE_WORMS, WWAquaticPlacements.PATCH_TUBE_WORMS);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.TUBE_WORMS_RARE, WWAquaticPlacements.PATCH_TUBE_WORMS_RARE);
		// MOSS
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.AUBURN_CREEPING_MOSS_UNDERWATER, WWAquaticPlacements.AUBURN_CREEPING_MOSS_UNDERWATER);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.AUBURN_MOSS_UNDERWATER, WWAquaticPlacements.AUBURN_MOSS_UNDERWATER);
		registerVegetalAddition(context, WWAquaticFeatureBiomeTags.AUBURN_MOSS_UNDERWATER_RARE, WWAquaticPlacements.AUBURN_MOSS_UNDERWATER_RARE);

		// MISC
		registerFluidSpringAddition(context, WWAquaticFeatureBiomeTags.HYDROTHERMAL_VENT, WWAquaticPlacements.HYDROTHERMAL_VENT);
		registerFluidSpringAddition(context, WWAquaticFeatureBiomeTags.HYDROTHERMAL_VENT_RARE, WWAquaticPlacements.HYDROTHERMAL_VENT_RARE);
	}

	private WWAquaticFeatureModifications() {}
}
