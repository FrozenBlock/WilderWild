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

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public record WWScorchingFirePlacePacket(BlockPos pos) implements CustomPacketPayload {
	public static final Type<WWScorchingFirePlacePacket> PACKET_TYPE = new Type<>(WWConstants.id("scorching_fire_place"));
	public static final StreamCodec<FriendlyByteBuf, WWScorchingFirePlacePacket> CODEC = StreamCodec.ofMember(WWScorchingFirePlacePacket::write, WWScorchingFirePlacePacket::new);

	public WWScorchingFirePlacePacket(FriendlyByteBuf buf) {
		this(buf.readBlockPos());
	}

	public static void sendToAll(ServerLevel serverLevel, BlockPos pos) {
		for (ServerPlayer player : PlayerLookup.tracking(serverLevel, pos)) {
			ServerPlayNetworking.send(player, new WWScorchingFirePlacePacket(pos));
		}
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeBlockPos(this.pos());
	}

	public Type<?> type() {
		return PACKET_TYPE;
	}
}
