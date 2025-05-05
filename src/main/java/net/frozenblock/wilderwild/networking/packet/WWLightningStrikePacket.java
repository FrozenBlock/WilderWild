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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public record WWLightningStrikePacket(int blockStateId, double x, double y, double z, int tickCount) implements CustomPacketPayload {
	public static final Type<WWLightningStrikePacket> PACKET_TYPE = new Type<>(WWConstants.id("lightning_strike"));

	public static final StreamCodec<FriendlyByteBuf, WWLightningStrikePacket> CODEC = StreamCodec.ofMember(WWLightningStrikePacket::write, WWLightningStrikePacket::new);

	public WWLightningStrikePacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt());
	}

	public static void sendToAll(@NotNull Entity entity, @NotNull BlockState blockState, int tickCount) {
		for (ServerPlayer player : PlayerLookup.tracking(entity)) {
			ServerPlayNetworking.send(
				player,
				new WWLightningStrikePacket(
					Block.getId(blockState),
					entity.getX(),
					entity.getY(),
					entity.getZ(),
					tickCount
				)
			);
		}
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeInt(this.blockStateId());
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
		buf.writeVarInt(this.tickCount());
	}

	@NotNull
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
