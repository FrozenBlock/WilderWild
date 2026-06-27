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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.block.api.waterlike.WaterLikeTypes;
import net.frozenblock.lib.block.impl.waterlike.WaterLikeType;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class WWWaterLikeTypes {
	public static final ResourceKey<WaterLikeType> MESOGLEA = bind("mesoglea");

	public static void bootstrap(BootstrapContext<WaterLikeType> context) {
		WaterLikeTypes.register(
			context,
			MESOGLEA,
			context.lookup(Registries.BLOCK).getOrThrow(WWBlockItemTags.MESOGLEA.block()),
			WWSounds.ENTITY_GENERIC_SWIM_MESOGLEA,
			WWSounds.ENTITY_HOSTILE_SWIM_MESOGLEA,
			WWSounds.ENTITY_PLAYER_SWIM_MESOGLEA,
			WWSounds.ENTITY_GENERIC_SPLASH_MESOGLEA,
			WWSounds.ENTITY_HOSTILE_SPLASH_MESOGLEA,
			WWSounds.ENTITY_PLAYER_SPLASH_MESOGLEA,
			WWSounds.ENTITY_PLAYER_SPLASH_HIGH_SPEED_MESOGLEA,
			WWSounds.AMBIENT_MESOGLEA_ENTER,
			WWSounds.AMBIENT_MESOGLEA_EXIT,
			WWSounds.AMBIENT_MESOGLEA_LOOP
		);
	}

	private static ResourceKey<WaterLikeType> bind(String name) {
		return ResourceKey.create(FrozenLibRegistries.WATER_LIKE_TYPE, WWConstants.id(name));
	}
}
