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

package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record WWJellyfishStingPacket(boolean isBaby) implements CustomPacketPayload {
	public static final Type<WWJellyfishStingPacket> PACKET_TYPE = new Type<>(WWConstants.id("jellyfish_sting"));
	public static final StreamCodec<FriendlyByteBuf, WWJellyfishStingPacket> CODEC = ByteBufCodecs.BOOL.map(WWJellyfishStingPacket::new, WWJellyfishStingPacket::isBaby).cast();

	public static void sendTo(ServerPlayer player, boolean isBaby) {
		ServerPlayNetworking.send(player, new WWJellyfishStingPacket(isBaby));
	}

	@Override
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
