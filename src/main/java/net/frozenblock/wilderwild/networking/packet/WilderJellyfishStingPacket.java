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

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record WilderJellyfishStingPacket(boolean isBaby) implements CustomPacketPayload {
	public static final Type<WilderJellyfishStingPacket> PACKET_TYPE = new Type<>(
			WilderSharedConstants.id("jellyfish_sting_packet")
	);

	public static final StreamCodec<FriendlyByteBuf, WilderJellyfishStingPacket> CODEC = ByteBufCodecs.BOOL
		.map(WilderJellyfishStingPacket::new, WilderJellyfishStingPacket::isBaby)
		.cast();

	public static void sendTo(ServerPlayer serverPlayer, boolean isBaby) {
		ServerPlayNetworking.send(serverPlayer, new WilderJellyfishStingPacket(isBaby));
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeBoolean(this.isBaby());
	}

	@NotNull
	@Override
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
