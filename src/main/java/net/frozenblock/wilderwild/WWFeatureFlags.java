/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild;

import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;

public class WWFeatureFlags {
	public static final FeatureFlag TRAILIER_TALES_COMPAT = FeatureFlagApi.builder.create(WWConstants.id("trailiertales"));
	public static final FeatureFlagSet TRAILIER_TALES_COMPAT_FLAG_SET = FeatureFlagSet.of(TRAILIER_TALES_COMPAT);

	public static void init() {
	}
}
