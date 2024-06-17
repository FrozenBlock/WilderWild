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

package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record WilderJellyfishStingPacket(boolean isBaby) implements FabricPacket {

	public static final PacketType<WilderJellyfishStingPacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("jellyfish_sting"),
			WilderJellyfishStingPacket::new
	);

	public WilderJellyfishStingPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readBoolean());
	}

	public static void sendTo(ServerPlayer serverPlayer, boolean isBaby) {
		WilderJellyfishStingPacket jellyfishStingPacket = new WilderJellyfishStingPacket(isBaby);
		ServerPlayNetworking.send(serverPlayer, jellyfishStingPacket);
	}

	@Override
	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeBoolean(this.isBaby());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
