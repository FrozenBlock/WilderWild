/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.tag;

import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

@UtilityClass
public final class WWPlacedFeatureTags {
	public static final TagKey<PlacedFeature> FOREST_DEFAULT_FLOWERS = bind("forest_default_flowers");
	public static final TagKey<PlacedFeature> WARM_FOREST_DEFAULT_FLOWERS = bind("warm_forest_default_flowers");
	public static final TagKey<PlacedFeature> PLAINS_DEFAULT_FLOWERS = bind("plains_default_flowers");
	public static final TagKey<PlacedFeature> FLOWER_FOREST_FLOWERS = bind("flower_forest_flowers");

	private static TagKey<PlacedFeature> bind(String name) {
		return TagKey.create(Registries.PLACED_FEATURE, WWConstants.id(name));
	}
}
