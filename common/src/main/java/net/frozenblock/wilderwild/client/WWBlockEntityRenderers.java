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

package net.frozenblock.wilderwild.client;

import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.client.ClientRegistryHelper;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.renderer.blockentity.HangingTendrilRenderer;
import net.frozenblock.wilderwild.client.renderer.blockentity.SculkSensorRenderer;
import net.frozenblock.wilderwild.client.renderer.blockentity.StoneChestRenderer;
import net.frozenblock.wilderwild.references.WWBlockEntityTypeIds;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.core.registries.ClientRegistries;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypeIds;

@ClientOnly
public final class WWBlockEntityRenderers {

	public static void init() {
		final DeferredRegister<BlockEntityRendererProvider<?, ?>> register = ClientRegistryHelper.createDeferredRegister(
			ClientRegistries.BLOCK_ENTITY_RENDERER,
			WWConstants.MOD_ID
		);

		register.register(
			blockEntityKey(BlockEntityTypeIds.SCULK_SENSOR),
			() -> (BlockEntityRendererProvider) SculkSensorRenderer::new
		);

		register.register(
			blockEntityKey(BlockEntityTypeIds.CALIBRATED_SCULK_SENSOR),
			() -> (BlockEntityRendererProvider) SculkSensorRenderer::new
		);

		register.register(
			blockEntityKey(WWBlockEntityTypeIds.HANGING_TENDRIL),
			() -> (BlockEntityRendererProvider) HangingTendrilRenderer::new
		);

		register.register(
			blockEntityKey(WWBlockEntityTypeIds.STONE_CHEST),
			() -> (BlockEntityRendererProvider) StoneChestRenderer::new
		);

		register.register();
	}

	private static ResourceKey<BlockEntityRendererProvider<?, ?>> blockEntityKey(ResourceKey<BlockEntityType<?>> type) {
		return ResourceKey.create(ClientRegistries.BLOCK_ENTITY_RENDERER, type.identifier());
	}

	private WWBlockEntityRenderers() {}
}
