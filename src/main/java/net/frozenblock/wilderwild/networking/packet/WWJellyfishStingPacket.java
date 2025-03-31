/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record WWJellyfishStingPacket(boolean isBaby) implements CustomPacketPayload {
	public static final Type<WWJellyfishStingPacket> PACKET_TYPE = new Type<>(
		WWConstants.id("jellyfish_sting")
	);

	public static final StreamCodec<FriendlyByteBuf, WWJellyfishStingPacket> CODEC = ByteBufCodecs.BOOL
		.map(WWJellyfishStingPacket::new, WWJellyfishStingPacket::isBaby)
		.cast();

	public static void sendTo(ServerPlayer serverPlayer, boolean isBaby) {
		ServerPlayNetworking.send(serverPlayer, new WWJellyfishStingPacket(isBaby));
	}

	@NotNull
	@Override
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
