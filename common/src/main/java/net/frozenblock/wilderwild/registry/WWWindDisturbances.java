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

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.lib.wind.disturbance.WindDisturbance;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.wind.disturbance.GeothermalVentBaseWindDisturbance;
import net.frozenblock.wilderwild.wind.disturbance.GeothermalVentEffectiveWindDisturbance;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public final class WWWindDisturbances {
	private static final DeferredRegister<WindDisturbanceType<?>> REGISTER = DeferredRegister.create(
		FrozenLibRegistries.WIND_DISTURBANCE_TYPE,
		WWConstants.MOD_ID
	);
	public static final DeferredHolder<WindDisturbanceType<?>, WindDisturbanceType<GeothermalVentEffectiveWindDisturbance>> GEOTHERMAL_VENT_EFFECTIVE = register(
		"geothermal_vent_effective",
		GeothermalVentEffectiveWindDisturbance.CODEC,
		GeothermalVentEffectiveWindDisturbance.STREAM_CODEC
	);
	public static final DeferredHolder<WindDisturbanceType<?>, WindDisturbanceType<GeothermalVentBaseWindDisturbance>> GEOTHERMAL_VENT_BASE = register(
		"geothermal_vent_base",
		GeothermalVentBaseWindDisturbance.CODEC,
		GeothermalVentBaseWindDisturbance.STREAM_CODEC
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <T extends WindDisturbance<?>> DeferredHolder<WindDisturbanceType<?>, WindDisturbanceType<T>> register(
		String name,
		MapCodec<T> codec,
		StreamCodec<RegistryFriendlyByteBuf, T> streamCodec
	) {
		return REGISTER.register(
			name,
			() -> new WindDisturbanceType<>() {
				@Override
				public MapCodec<T> codec() {
					return codec;
				}

				@Override
				public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
					return streamCodec;
				}
			}
		);
	}

	private WWWindDisturbances() {}
}
