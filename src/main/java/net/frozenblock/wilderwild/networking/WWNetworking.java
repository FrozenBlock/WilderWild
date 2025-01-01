/*
 * Copyright 2024-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.frozenblock.wilderwild.networking.packet.WWJellyfishStingPacket;
import net.frozenblock.wilderwild.networking.packet.WWLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WWScorchingFirePlacePacket;
import net.frozenblock.wilderwild.networking.packet.WWStoneChestLidPacket;
import net.frozenblock.wilderwild.networking.packet.WWWindPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;

public final class WWNetworking {

	public static void init() {
		PayloadTypeRegistry<RegistryFriendlyByteBuf> registry = PayloadTypeRegistry.playS2C();
		registry.register(WWWindPacket.PACKET_TYPE, WWWindPacket.CODEC);
		registry.register(WWJellyfishStingPacket.PACKET_TYPE, WWJellyfishStingPacket.CODEC);
		registry.register(WWLightningStrikePacket.PACKET_TYPE, WWLightningStrikePacket.CODEC);
		registry.register(WWStoneChestLidPacket.PACKET_TYPE, WWStoneChestLidPacket.CODEC);
		registry.register(WWScorchingFirePlacePacket.PACKET_TYPE, WWScorchingFirePlacePacket.CODEC);
	}
}
