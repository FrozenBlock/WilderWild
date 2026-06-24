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

import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibTreeFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class WWFeatureUtils {

	public static <F extends Feature> FrozenLibFeature register(String id, F feature) {
		final FrozenLibFeature frozenLibFeature = new FrozenLibFeature(WWConstants.id(id));
		frozenLibFeature.makeAndSetHolder(feature);
		return frozenLibFeature;
	}

	public static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id(id));
	}

	public static FrozenLibTreeFeature registerTree(
		String id,
		Block leafLitterBlock,
		int triesA, int radiusA, int heightA,
		int triesB, int radiusB, int heightB
	) {
		return new FrozenLibTreeFeature(WWConstants.id(id), leafLitterBlock, triesA, radiusA, heightA, triesB, radiusB, heightB);
	}
}
