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

package net.frozenblock.wilderwild.data.worldgen.feature;

import java.util.List;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.levelgen.placement.api.NoisePlacementFilter;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class WWPlacementUtils {
	public static final NoisePlacementFilter TREE_CLEARING_FILTER = new NoisePlacementFilter(EasyNoiseSampler.NoiseType.XORO, 0.0065D, 0.625D, 1D, 0.2D, false, false, false);
	public static final NoisePlacementFilter SHRUB_CLEARING_FILTER = new NoisePlacementFilter(EasyNoiseSampler.NoiseType.XORO, 0.0065D, 0.69D, 1D, 0.2D, false, false, false);
	public static final NoisePlacementFilter TREE_CLEARING_FILTER_INVERTED = new NoisePlacementFilter(EasyNoiseSampler.NoiseType.XORO, 0.0065D, 0.675D, 1D, 0.175D, false, false, true);

	public static FrozenLibPlacedFeature register(String name, Holder<Feature> feature, List<PlacementModifier> modifiers) {
		return new FrozenLibPlacedFeature(WWConstants.id(name)).makeAndSetHolder(feature, modifiers);
	}

	public static FrozenLibPlacedFeature register(String name, Holder<Feature> feature, PlacementModifier... modifiers) {
		return register(name, feature, List.of(modifiers));
	}

	public static FrozenLibPlacedFeature register(String id) {
		return new FrozenLibPlacedFeature(WWConstants.id(id));
	}
}
