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

package net.frozenblock.wilderwild.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.frozenblock.wilderwild.networking.packet.WWJellyfishStingPacket;
import net.frozenblock.wilderwild.networking.packet.WWLeavesExplosionParticlePacket;
import net.frozenblock.wilderwild.networking.packet.WWLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WWScorchingFirePlacePacket;
import net.frozenblock.wilderwild.networking.packet.WWStoneChestLidPacket;
import net.frozenblock.wilderwild.networking.packet.WWWindPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;

public final class WWNetworking {

	public static void init() {
		final PayloadTypeRegistry<RegistryFriendlyByteBuf> registry = PayloadTypeRegistry.playS2C();
		registry.register(WWWindPacket.PACKET_TYPE, WWWindPacket.CODEC);
		registry.register(WWJellyfishStingPacket.PACKET_TYPE, WWJellyfishStingPacket.CODEC);
		registry.register(WWLightningStrikePacket.PACKET_TYPE, WWLightningStrikePacket.CODEC);
		registry.register(WWStoneChestLidPacket.PACKET_TYPE, WWStoneChestLidPacket.CODEC);
		registry.register(WWScorchingFirePlacePacket.PACKET_TYPE, WWScorchingFirePlacePacket.CODEC);
		registry.register(WWLeavesExplosionParticlePacket.PACKET_TYPE, WWLeavesExplosionParticlePacket.CODEC);
	}
}
