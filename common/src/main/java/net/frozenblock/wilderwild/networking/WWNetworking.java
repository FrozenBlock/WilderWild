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

package net.frozenblock.wilderwild.networking;

import net.frozenblock.lib.platform.FrozenLibInitPlatformUtils;
import net.frozenblock.lib.platform.service.NetworkingHelper;
import net.frozenblock.wilderwild.networking.packet.WWIcicleLandPacket;
import net.frozenblock.wilderwild.networking.packet.WWJellyfishStingPacket;
import net.frozenblock.wilderwild.networking.packet.WWLeavesExplosionParticlePacket;
import net.frozenblock.wilderwild.networking.packet.WWLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WWScorchingFirePlacePacket;
import net.frozenblock.wilderwild.networking.packet.WWStoneChestLidPacket;

public final class WWNetworking {

	public static void setup() {
		final NetworkingHelper networking = FrozenLibInitPlatformUtils.NETWORKING;
		networking.registerS2CPayloadType(WWJellyfishStingPacket.PACKET_TYPE, WWJellyfishStingPacket.CODEC);
		networking.registerS2CPayloadType(WWLightningStrikePacket.PACKET_TYPE, WWLightningStrikePacket.CODEC);
		networking.registerS2CPayloadType(WWStoneChestLidPacket.PACKET_TYPE, WWStoneChestLidPacket.CODEC);
		networking.registerS2CPayloadType(WWScorchingFirePlacePacket.PACKET_TYPE, WWScorchingFirePlacePacket.CODEC);
		networking.registerS2CPayloadType(WWIcicleLandPacket.PACKET_TYPE, WWIcicleLandPacket.CODEC);
		networking.registerS2CPayloadType(WWLeavesExplosionParticlePacket.PACKET_TYPE, WWLeavesExplosionParticlePacket.CODEC);
	}
}
