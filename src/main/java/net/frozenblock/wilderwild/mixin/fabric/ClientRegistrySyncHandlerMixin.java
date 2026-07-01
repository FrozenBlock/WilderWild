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

package net.frozenblock.wilderwild.mixin.fabric;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.event.registry.RegistryIdRemapCallback;
import net.fabricmc.fabric.impl.client.registry.sync.ClientRegistrySyncHandler;
import net.fabricmc.fabric.impl.registry.sync.ListenableRegistry;
import net.fabricmc.fabric.impl.registry.sync.RemapException;
import net.fabricmc.fabric.impl.registry.sync.RemapStateImpl;
import net.fabricmc.fabric.impl.registry.sync.packet.RegistrySyncPayload;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ApiStatus.Experimental
@Mixin(ClientRegistrySyncHandler.class)
public class ClientRegistrySyncHandlerMixin  {

	@Inject(method = "receivePacket", at = @At("HEAD"))
	private static void wilderWild$checkForWilderWildBlocks(RegistrySyncPayload payload, ClientConfigurationNetworking.Context context, CallbackInfo info) throws RemapException {
		final Identifier blocksRegistryId = payload.registryMap().keySet().stream()
			.filter(id -> BuiltInRegistries.REGISTRY.getValue(id) == BuiltInRegistries.BLOCK)
			.findFirst()
			.orElse(null);

		if (blocksRegistryId == null) {
			WWConstants.log("NO BLOCKS REGISTRY WHAAAAAAT", WWConstants.UNSTABLE_LOGGING);
			WWConstants.SERVER_HAS_WIILDER_WILD_BLOCKS = false;
			WWConstants.log("SERVER HAS WILDER WILD BLOCKS: " + false, WWConstants.UNSTABLE_LOGGING);
			if (BuiltInRegistries.BLOCK instanceof ListenableRegistry listenableRegistry) {
				RegistryIdRemapCallback.event(BuiltInRegistries.BLOCK).invoker()
					.onRemap(new RemapStateImpl<>(BuiltInRegistries.BLOCK, new Int2ObjectOpenHashMap<>(), new Int2IntArrayMap()));
			}
			return;
		}

		WWConstants.SERVER_HAS_WIILDER_WILD_BLOCKS = payload.registryMap().get(blocksRegistryId)
			.keySet()
			.stream()
			.anyMatch(id -> id.getNamespace().equals(WWConstants.MOD_ID));
		WWConstants.log("SERVER HAS WILDER WILD BLOCKS: " + WWConstants.SERVER_HAS_WIILDER_WILD_BLOCKS, WWConstants.UNSTABLE_LOGGING);
	}
}
