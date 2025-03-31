/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.features.placed;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.worldgen.features.WWPlacementUtils;
import static net.frozenblock.wilderwild.worldgen.features.WWPlacementUtils.register;
import net.frozenblock.wilderwild.worldgen.features.configured.WWAquaticConfigured;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public final class WWAquaticPlaced {
	public static final FrozenLibPlacedFeature PATCH_CATTAIL = register("cattail");
	public static final FrozenLibPlacedFeature PATCH_CATTAIL_UNCOMMON = register("cattail_uncommon");
	public static final FrozenLibPlacedFeature PATCH_CATTAIL_COMMON = register("cattail_common");
	public static final FrozenLibPlacedFeature PATCH_CATTAIL_MUD = register("cattail_mud");
	public static final FrozenLibPlacedFeature BARNACLES_COMMON = register("barnacles_common");
	public static final FrozenLibPlacedFeature BARNACLES_STRUCTURE = register("barnacles_structure");
	public static final FrozenLibPlacedFeature BARNACLES = register("barnacles");
	public static final FrozenLibPlacedFeature BARNACLES_SPARSE = register("barnacles_sparse");
	public static final FrozenLibPlacedFeature BARNACLES_RARE = register("barnacles_rare");
	public static final FrozenLibPlacedFeature PATCH_FLOWERING_WATERLILY = register("patch_flowering_waterlily");
	public static final FrozenLibPlacedFeature PATCH_ALGAE = register("patch_algae");
	public static final FrozenLibPlacedFeature PATCH_ALGAE_SMALL = register("patch_algae_small");
	public static final FrozenLibPlacedFeature PATCH_PLANKTON = register("patch_plankton");
	public static final FrozenLibPlacedFeature SEAGRASS_MEADOW = register("seagrass_meadow");
	public static final FrozenLibPlacedFeature SPONGE_BUDS = register("sponge_buds");
	public static final FrozenLibPlacedFeature SPONGE_BUDS_RARE = register("sponge_buds_rare");
	public static final FrozenLibPlacedFeature PATCH_SEA_ANEMONE = register("patch_sea_anemone");
	public static final FrozenLibPlacedFeature PATCH_SEA_ANEMONE_SPARSE = register("patch_sea_anemone_sparse");
	public static final FrozenLibPlacedFeature PATCH_SEA_ANEMONE_RARE = register("patch_sea_anemone_rare");
	public static final FrozenLibPlacedFeature PATCH_SEA_WHIP = register("patch_sea_whip");
	public static final FrozenLibPlacedFeature PATCH_SEA_WHIP_SPARSE = register("patch_sea_whip_sparse");
	public static final FrozenLibPlacedFeature PATCH_SEA_WHIP_RARE = register("patch_sea_whip_rare");
	public static final FrozenLibPlacedFeature PATCH_TUBE_WORMS = register("patch_tube_worms");
	public static final FrozenLibPlacedFeature PATCH_TUBE_WORMS_RARE = register("patch_tube_worms_rare");

	public static final FrozenLibPlacedFeature HYDROTHERMAL_VENT = register("hydrothermal_vent");
	public static final FrozenLibPlacedFeature HYDROTHERMAL_VENT_RARE = register("hydrothermal_vent_rare");
	public static final FrozenLibPlacedFeature HYDROTHERMAL_VENT_TUBE_WORMS = register("hydrothermal_vent_tube_worms");
	public static final FrozenLibPlacedFeature HYDROTHERMAL_VENT_TUBE_WORMS_RARE = register("hydrothermal_vent_tube_worms_rare");
	public static final FrozenLibPlacedFeature OCEAN_MOSS = WWPlacementUtils.register("ocean_moss");
	public static final FrozenLibPlacedFeature AUBURN_MOSS_UNDERWATER = WWPlacementUtils.register("auburn_moss_underwater");
	public static final FrozenLibPlacedFeature AUBURN_MOSS_UNDERWATER_RARE = WWPlacementUtils.register("auburn_moss_underwater_rare");
	public static final FrozenLibPlacedFeature AUBURN_CREEPING_MOSS_UNDERWATER = WWPlacementUtils.register("auburn_creeping_moss_underwater");

	private WWAquaticPlaced() {
		throw new UnsupportedOperationException("WWAquaticPlaced contains only static declarations.");
	}

	public static void registerAquaticPlaced(@NotNull BootstrapContext<PlacedFeature> entries) {
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		WWConstants.logWithModId("Registering WWAquaticPlaced for", true);

		PATCH_CATTAIL.makeAndSetHolder(WWAquaticConfigured.CATTAIL.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		PATCH_CATTAIL_UNCOMMON.makeAndSetHolder(WWAquaticConfigured.CATTAIL_SMALL.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		PATCH_CATTAIL_COMMON.makeAndSetHolder(WWAquaticConfigured.CATTAIL_SMALL.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		PATCH_CATTAIL_MUD.makeAndSetHolder(WWAquaticConfigured.CATTAIL_MUD.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		BARNACLES_COMMON.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES_DENSE.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		BARNACLES_STRUCTURE.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES_STRUCTURE.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		BARNACLES.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		BARNACLES_SPARSE.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES.getHolder(),
			RarityFilter.onAverageOnceEvery(18),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		BARNACLES_RARE.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES.getHolder(),
			RarityFilter.onAverageOnceEvery(24),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_FLOWERING_WATERLILY.makeAndSetHolder(WWAquaticConfigured.PATCH_FLOWERING_WATERLILY.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_ALGAE.makeAndSetHolder(WWAquaticConfigured.PATCH_ALGAE.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_ALGAE_SMALL.makeAndSetHolder(WWAquaticConfigured.PATCH_ALGAE_SMALL.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_PLANKTON.makeAndSetHolder(WWAquaticConfigured.PATCH_PLANKTON.getHolder(),
			RarityFilter.onAverageOnceEvery(30),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SEAGRASS_MEADOW.makeAndSetHolder(WWAquaticConfigured.SEAGRASS_MEADOW.getHolder(),
			CountPlacement.of(98),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		SPONGE_BUDS.makeAndSetHolder(WWAquaticConfigured.SPONGE_BUD.getHolder(),
			CountPlacement.of(UniformInt.of(0, 3)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		SPONGE_BUDS_RARE.makeAndSetHolder(WWAquaticConfigured.SPONGE_BUD.getHolder(),
			CountPlacement.of(UniformInt.of(0, 1)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_SEA_ANEMONE.makeAndSetHolder(WWAquaticConfigured.PATCH_SEA_ANEMONE.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_SEA_ANEMONE_SPARSE.makeAndSetHolder(WWAquaticConfigured.PATCH_SEA_ANEMONE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_SEA_ANEMONE_RARE.makeAndSetHolder(WWAquaticConfigured.PATCH_SEA_ANEMONE.getHolder(),
			RarityFilter.onAverageOnceEvery(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_SEA_WHIP.makeAndSetHolder(WWAquaticConfigured.PATCH_SEA_WHIP.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_SEA_WHIP_SPARSE.makeAndSetHolder(WWAquaticConfigured.PATCH_SEA_WHIP_SPARSE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_SEA_WHIP_RARE.makeAndSetHolder(WWAquaticConfigured.PATCH_SEA_WHIP_SPARSE.getHolder(),
			RarityFilter.onAverageOnceEvery(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_TUBE_WORMS.makeAndSetHolder(WWAquaticConfigured.PATCH_TUBE_WORMS.getHolder(),
			RarityFilter.onAverageOnceEvery(22),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_TUBE_WORMS_RARE.makeAndSetHolder(WWAquaticConfigured.PATCH_TUBE_WORMS.getHolder(),
			RarityFilter.onAverageOnceEvery(42),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		HYDROTHERMAL_VENT.makeAndSetHolder(WWAquaticConfigured.HYDROTHERMAL_VENT.getHolder(),
			RarityFilter.onAverageOnceEvery(48),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -256, -9),
			BiomeFilter.biome()
		);

		HYDROTHERMAL_VENT_RARE.makeAndSetHolder(WWAquaticConfigured.HYDROTHERMAL_VENT.getHolder(),
			RarityFilter.onAverageOnceEvery(72),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -256, -9),
			BiomeFilter.biome()
		);

		HYDROTHERMAL_VENT_TUBE_WORMS.makeAndSetHolder(WWAquaticConfigured.HYDROTHERMAL_VENT_TUBE_WORMS.getHolder(),
			RarityFilter.onAverageOnceEvery(40),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -256, -9),
			BiomeFilter.biome()
		);

		HYDROTHERMAL_VENT_TUBE_WORMS_RARE.makeAndSetHolder(WWAquaticConfigured.HYDROTHERMAL_VENT_TUBE_WORMS.getHolder(),
			RarityFilter.onAverageOnceEvery(65),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -256, -9),
			BiomeFilter.biome()
		);

		OCEAN_MOSS.makeAndSetHolder(WWAquaticConfigured.OCEAN_MOSS.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		AUBURN_MOSS_UNDERWATER.makeAndSetHolder(WWAquaticConfigured.AUBURN_MOSS_UNDERWATER.getHolder(),
			RarityFilter.onAverageOnceEvery(22),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		AUBURN_MOSS_UNDERWATER_RARE.makeAndSetHolder(WWAquaticConfigured.AUBURN_MOSS_UNDERWATER.getHolder(),
			RarityFilter.onAverageOnceEvery(52),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		AUBURN_CREEPING_MOSS_UNDERWATER.makeAndSetHolder(WWAquaticConfigured.AUBURN_CREEPING_MOSS_PATCH_UNDERWATER.getHolder(),
			RarityFilter.onAverageOnceEvery(14),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);
	}

	@Contract("_, _ -> new")
	private static @Unmodifiable List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
		return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
	}

	@Contract("_, _ -> new")
	private static @Unmodifiable List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
		return modifiers(CountPlacement.of(count), heightModifier);
	}

}
