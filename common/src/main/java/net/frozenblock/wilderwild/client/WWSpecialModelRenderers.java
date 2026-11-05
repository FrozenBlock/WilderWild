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

package net.frozenblock.wilderwild.client;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.client.ClientRegistryHelper;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.core.registries.ClientRegistries;
import net.minecraft.client.renderer.special.SpecialModelRenderer;

@ClientOnly
public final class WWSpecialModelRenderers {

	static {
		final DeferredRegister<MapCodec<? extends SpecialModelRenderer.Unbaked<?>>> register = ClientRegistryHelper.createDeferredRegister(
			ClientRegistries.SPECIAL_MODEL_RENDERER_TYPE,
			WWConstants.MOD_ID
		);

		register.register("stone_chest", () -> StoneChestSpecialRenderer.Unbaked.MAP_CODEC);

		register.register();
	}

	public static void init() {}

	private WWSpecialModelRenderers() {}
}
