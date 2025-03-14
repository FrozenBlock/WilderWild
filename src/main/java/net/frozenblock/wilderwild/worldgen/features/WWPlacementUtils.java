/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.features;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.worldgen.feature.api.placementmodifier.NoisePlacementFilter;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.NotNull;

public final class WWPlacementUtils {
	public static final NoisePlacementFilter TREE_CLEARING_FILTER = new NoisePlacementFilter(4, 0.0065, 0.625, 1.0, 0.2, false, false, false);
	public static final NoisePlacementFilter SHRUB_CLEARING_FILTER = new NoisePlacementFilter(4, 0.0065, 0.69, 1.0, 0.2, false, false, false);
	public static final NoisePlacementFilter TREE_CLEARING_FILTER_INVERTED = new NoisePlacementFilter(4, 0.0065, 0.675, 1.0, 0.175, false, false, true);

	private WWPlacementUtils() {
		throw new UnsupportedOperationException("WWPlacementUtils contains only static declarations.");
	}

	@NotNull
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <FC extends FeatureConfiguration> FrozenLibPlacedFeature register(@NotNull String id, Holder<ConfiguredFeature<FC, ?>> configured, @NotNull List<PlacementModifier> modifiers) {
		return new FrozenLibPlacedFeature(WWConstants.id(id))
			.makeAndSetHolder((Holder) configured, modifiers);
	}

	@NotNull
	public static <FC extends FeatureConfiguration> FrozenLibPlacedFeature register(@NotNull String id, Holder<ConfiguredFeature<FC, ?>> registryEntry, @NotNull PlacementModifier... modifiers) {
		return register(id, registryEntry, List.of(modifiers));
	}

	@NotNull
	public static FrozenLibPlacedFeature register(@NotNull String id) {
		var key = WWConstants.id(id);
		return new FrozenLibPlacedFeature(key);
	}

}
