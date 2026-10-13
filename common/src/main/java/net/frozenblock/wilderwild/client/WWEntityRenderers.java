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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.renderer.entity.ButterflyRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.CrabRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.FireflyRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.FlowerCowRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.JellyfishRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.OstrichRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.PenguinRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.ScorchedRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.TumbleweedRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.UndeadOstrichRenderer;
import net.frozenblock.wilderwild.references.WWEntityTypeIds;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.core.registries.ClientRegistries;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

@ClientOnly
public final class WWEntityRenderers {

	public static void init() {
		final DeferredRegister<EntityRendererProvider<?>> register = DeferredRegister.create(
			ClientRegistries.ENTITY_RENDERER,
			WWConstants.MOD_ID
		);

		register.register(
			entityKey(WWEntityTypeIds.BUTTERFLY),
			() ->  (EntityRendererProvider) ButterflyRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.FIREFLY),
			() ->  (EntityRendererProvider) FireflyRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.JELLYFISH),
			() ->  (EntityRendererProvider) JellyfishRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.CRAB),
			() ->  (EntityRendererProvider) CrabRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.OSTRICH),
			() ->  (EntityRendererProvider) OstrichRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.ZOMBIE_OSTRICH),
			() ->  (EntityRendererProvider) UndeadOstrichRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.SCORCHED),
			() ->  (EntityRendererProvider) ScorchedRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.MOOBLOOM),
			() ->  (EntityRendererProvider) FlowerCowRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.PENGUIN),
			() ->  (EntityRendererProvider) PenguinRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.TUMBLEWEED),
			() ->  (EntityRendererProvider) TumbleweedRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.COCONUT),
			() ->  (EntityRendererProvider) ThrownItemRenderer::new
		);

		register.register(
			entityKey(WWEntityTypeIds.FALLING_LEAVES),
			() ->  (EntityRendererProvider) NoopRenderer::new
		);

		// BOATS
		register.register(
			entityKey(WWEntityTypeIds.BAOBAB_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.BAOBAB_BOAT)
		);
		register.register(
			entityKey(WWEntityTypeIds.BAOBAB_CHEST_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.BAOBAB_CHEST_BOAT)
		);

		register.register(
			entityKey(WWEntityTypeIds.WILLOW_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.WILLOW_BOAT)
		);
		register.register(
			entityKey(WWEntityTypeIds.WILLOW_CHEST_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.WILLOW_CHEST_BOAT)
		);

		register.register(
			entityKey(WWEntityTypeIds.CYPRESS_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.CYPRESS_BOAT)
		);
		register.register(
			entityKey(WWEntityTypeIds.CYPRESS_CHEST_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.CYPRESS_CHEST_BOAT)
		);

		register.register(
			entityKey(WWEntityTypeIds.PALM_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.PALM_BOAT)
		);
		register.register(
			entityKey(WWEntityTypeIds.PALM_CHEST_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.PALM_CHEST_BOAT)
		);

		register.register(
			entityKey(WWEntityTypeIds.MAPLE_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.MAPLE_BOAT)
		);
		register.register(
			entityKey(WWEntityTypeIds.MAPLE_CHEST_BOAT),
			() -> (EntityRendererProvider) context -> new BoatRenderer(context, WWModelLayers.MAPLE_CHEST_BOAT)
		);

		register.register();
	}

	private static ResourceKey<EntityRendererProvider<?>> entityKey(ResourceKey<EntityType<?>> type) {
		return ResourceKey.create(ClientRegistries.ENTITY_RENDERER, type.identifier());
	}

	private WWEntityRenderers() {}
}
