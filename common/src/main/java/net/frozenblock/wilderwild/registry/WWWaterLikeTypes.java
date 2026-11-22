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

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.block.api.waterlike.WaterLikeTypes;
import net.frozenblock.lib.block.impl.waterlike.WaterLikeType;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWWaterLikeTypes {
	public static final ResourceKey<WaterLikeType> MESOGLEA = createKey("mesoglea");

	public static void bootstrap(BootstrapContext<WaterLikeType> context) {
		WaterLikeTypes.register(
			context,
			MESOGLEA,
			context.lookup(Registries.BLOCK).getOrThrow(WWBlockItemTags.MESOGLEA.block()),
			WWSoundEvents.GENERIC_SWIM_MESOGLEA.asHolder(),
			WWSoundEvents.HOSTILE_SWIM_MESOGLEA.asHolder(),
			WWSoundEvents.PLAYER_SWIM_MESOGLEA.asHolder(),
			WWSoundEvents.GENERIC_SPLASH_MESOGLEA.asHolder(),
			WWSoundEvents.HOSTILE_SPLASH_MESOGLEA.asHolder(),
			WWSoundEvents.PLAYER_SPLASH_MESOGLEA.asHolder(),
			WWSoundEvents.PLAYER_SPLASH_HIGH_SPEED_MESOGLEA.asHolder(),
			WWSoundEvents.AMBIENT_MESOGLEA_ENTER.asHolder(),
			WWSoundEvents.AMBIENT_MESOGLEA_EXIT.asHolder(),
			WWSoundEvents.AMBIENT_MESOGLEA_LOOP.asHolder()
		);
	}

	private static ResourceKey<WaterLikeType> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.WATER_LIKE_TYPE, WWConstants.id(name));
	}
}
