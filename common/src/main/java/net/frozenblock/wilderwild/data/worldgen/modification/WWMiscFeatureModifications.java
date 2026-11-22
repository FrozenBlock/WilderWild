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

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.levelgen.biome.api.modifications.ModificationPhase;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import static net.frozenblock.lib.levelgen.biome.api.modifications.data.FeatureModification.*;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.selector.ContainsPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.placement.WWMiscPlacements;
import net.frozenblock.wilderwild.tag.WWMiscFeatureBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWMiscFeatureModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		// TERMITE MOUND
		registerOreAddition(context, WWMiscFeatureBiomeTags.TERMITE_MOUND, WWMiscPlacements.TERMITE_MOUND);

		// COARSE DIRT
		registerOreAddition(context, WWMiscFeatureBiomeTags.COARSE_DIRT_BLOB_AND_PATCH, WWMiscPlacements.COARSE_DIRT_BLOB_AND_PATCH);
		registerOreAddition(context, WWMiscFeatureBiomeTags.COARSE_DIRT_BLOB_AND_PATCH_RARE, WWMiscPlacements.COARSE_DIRT_BLOB_AND_PATCH_RARE);

		// PODZOL
		registerLakeAddition(context, WWMiscFeatureBiomeTags.PODZOL_BASIN, WWMiscPlacements.PODZOL_BASIN);

		// MUD
		registerLakeAddition(context, WWMiscFeatureBiomeTags.MUD_BASIN, WWMiscPlacements.MUD_BASIN);
		registerLakeAddition(context, WWMiscFeatureBiomeTags.MUD_LAKE, WWMiscPlacements.MUD_LAKE);
		registerOreAddition(context, WWMiscFeatureBiomeTags.MUD_TRANSITION, WWMiscPlacements.MUD_TRANSITION);

		// MOSS
		registerVegetalAddition(context, WWMiscFeatureBiomeTags.MOSS_BLOB, WWMiscPlacements.MOSS_BLOB);
		registerLakeAddition(context, WWMiscFeatureBiomeTags.MOSS_BASIN, WWMiscPlacements.MOSS_BASIN);
		registerLakeAddition(context, WWMiscFeatureBiomeTags.MOSS_LAKE, WWMiscPlacements.MOSS_LAKE);
		registerLakeAddition(context, WWMiscFeatureBiomeTags.MOSS_LAKE_RARE, WWMiscPlacements.MOSS_LAKE_RARE);

		// PALE MOSS
		registerVegetalAddition(context, WWMiscFeatureBiomeTags.PALE_MOSS_BLOB, WWMiscPlacements.PALE_MOSS_BLOB);

		// GRAVEL
		registerOreAddition(context, WWMiscFeatureBiomeTags.GRAVEL_TRANSITION, WWMiscPlacements.GRAVEL_TRANSITION);
		registerOreAddition(context, WWMiscFeatureBiomeTags.BETA_BEACH_GRAVEL_TRANSITION, WWMiscPlacements.BETA_BEACH_GRAVEL_TRANSITION);

		// STONE
		registerTopLayerAddition(context, WWMiscFeatureBiomeTags.TAIGA_FOREST_ROCK_SPARSE, WWMiscPlacements.FOREST_ROCK_TAIGA_SPARSE);
		registerOreAddition(context, WWMiscFeatureBiomeTags.STONE_TRANSITION, WWMiscPlacements.STONE_TRANSITION);

		// SAND
		registerOreAddition(context, WWMiscFeatureBiomeTags.SAND_TRANSITION, WWMiscPlacements.SAND_TRANSITION);
		registerOreAddition(context, WWMiscFeatureBiomeTags.SMALL_SAND_TRANSITION, WWMiscPlacements.SMALL_SAND_TRANSITION);
		registerOreAddition(context, WWMiscFeatureBiomeTags.BETA_BEACH_SAND_TRANSITION, WWMiscPlacements.BETA_BEACH_SAND_TRANSITION);

		// RED SAND
		registerOreAddition(context, WWMiscFeatureBiomeTags.RED_SAND_TRANSITION, WWMiscPlacements.RED_SAND_TRANSITION);

		// SNOW
		registerVegetalAddition(context, WWMiscFeatureBiomeTags.SNOW_BLOB, WWMiscPlacements.SNOW_BLOB);
		context.register(
			createKey(WWConstants.id("misc/snow_blanket")),
			forAddition(
				Holder.direct(new ContainsPlacedFeature(MiscOverworldPlacements.FREEZE_TOP_LAYER)),
				ModificationPhase.POST_PROCESSING,
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
				WWMiscPlacements.SNOW_BLANKET.asHolderSet()
			)
		);
		context.register(
			createKey(WWConstants.id("misc/snow_and_ice_transition")),
			forAddition(
				Holder.direct(new ContainsPlacedFeature(MiscOverworldPlacements.FREEZE_TOP_LAYER)),
				ModificationPhase.POST_PROCESSING,
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
				WWMiscPlacements.SNOW_AND_ICE_TRANSITION.asHolderSet()
			)
		);

		// ICE
		registerTopLayerAddition(context, WWMiscFeatureBiomeTags.SURFACE_BRITTLE_ICE, WWMiscPlacements.BRITTLE_ICE_DISK_SURFACE);
		registerVegetalAddition(
			context,
			WWMiscFeatureBiomeTags.SURFACE_ICICLE,
			WWMiscPlacements.DECORATIVE_ICICLE_CLUSTER_SURFACE_WG,
			WWMiscPlacements.DECORATIVE_ICICLE_CLUSTER_SURFACE
		);

		// GEOTHERMAL VENT
		registerUndergroundAddition(context, WWMiscFeatureBiomeTags.NETHER_GEOTHERMAL_VENT, WWMiscPlacements.NETHER_GEOTHERMAL_VENT);
		registerUndergroundAddition(context, WWMiscFeatureBiomeTags.NETHER_LAVA_GEOTHERMAL_VENT, WWMiscPlacements.NETHER_LAVA_GEOTHERMAL_VENT);
	}
}
