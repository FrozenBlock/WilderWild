/*
 * Copyright 2024 FrozenBlock
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
import net.frozenblock.wilderwild.networking.packet.WilderJellyfishStingPacket;
import net.frozenblock.wilderwild.networking.packet.WilderLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WilderSensorHiccupPacket;
import net.frozenblock.wilderwild.networking.packet.WilderStoneChestLidPacket;
import net.frozenblock.wilderwild.networking.packet.WilderWindPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class WilderNetworking {

	public static void init() {
		PayloadTypeRegistry<RegistryFriendlyByteBuf> registry = PayloadTypeRegistry.playS2C();
		registry.register(WilderWindPacket.PACKET_TYPE, WilderWindPacket.CODEC);
		registry.register(WilderSensorHiccupPacket.PACKET_TYPE, WilderSensorHiccupPacket.CODEC);
		registry.register(WilderJellyfishStingPacket.PACKET_TYPE, WilderJellyfishStingPacket.CODEC);
		registry.register(WilderLightningStrikePacket.PACKET_TYPE, WilderLightningStrikePacket.CODEC);
		registry.register(WilderStoneChestLidPacket.PACKET_TYPE, WilderStoneChestLidPacket.CODEC);
	}

}
