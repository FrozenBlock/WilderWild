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

package net.frozenblock.wilderwild.data.worldgen.placement;

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.NoiseThresholdBlockPredicate;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.data.worldgen.noise.WWNoises;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWPlacementUtils {
	public static final double CLEARING_THRESHOLD = 0.595D;
	public static final double CLEARING_EDGE_WIDTH = 0.06D;

	public static PlacementFilter outsideClearingFilter(BootstrapContext<?> context, boolean includeEdges) {
		return BlockPredicateFilter.forPredicate(
			BlockPredicate.anyOf(
				BlockPredicate.not(ConfigPredicate.blockPredicate(context.lookup(FrozenLibRegistries.CONFIG_PREDICATE).getOrThrow(WWConfigPredicates.GENERATE_CLEARING))),
				NoiseThresholdBlockPredicate.ofMaxThreshold(
					WWNoises.CLEARING,
					CLEARING_THRESHOLD + (includeEdges ? CLEARING_EDGE_WIDTH : 0D),
					false
				)
			)
		);
	}

	public static PlacementFilter outsideClearingFilter(BootstrapContext<?> context) {
		return outsideClearingFilter(context, false);
	}

	public static PlacementFilter insideClearingFilter(BootstrapContext<?> context) {
		return BlockPredicateFilter.forPredicate(
			BlockPredicate.allOf(
				ConfigPredicate.blockPredicate(context.lookup(FrozenLibRegistries.CONFIG_PREDICATE).getOrThrow(WWConfigPredicates.GENERATE_CLEARING)),
				NoiseThresholdBlockPredicate.ofMinThreshold(
					WWNoises.CLEARING,
					CLEARING_THRESHOLD,
					false
				)
			)
		);
	}
}
