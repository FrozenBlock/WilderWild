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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.networking.packet.WilderJellyfishStingPacket;
import net.frozenblock.wilderwild.networking.packet.WilderLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WilderSensorHiccupPacket;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundSource;

@Environment(EnvType.CLIENT)
public class WilderClientNetworking {

	public static void registerPacketReceivers() {
		WilderSensorHiccupPacket.receive();
		receiveJellyfishStingPacket();
		WilderLightningStrikePacket.receive();
	}

	@Environment(EnvType.CLIENT)
	public static void receiveJellyfishStingPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderJellyfishStingPacket.PACKET_TYPE, (packet, player, responseSender) -> {
			ClientLevel clientLevel = player.clientLevel;
			clientLevel.playSound(
				player,
				player.getX(),
				player.getY(),
				player.getZ(),
				RegisterSounds.ENTITY_JELLYFISH_STING,
				SoundSource.NEUTRAL,
				1F,
				packet.isBaby() ? Jellyfish.STING_PITCH_BABY : Jellyfish.STING_PITCH
			);
		});
	}

}
