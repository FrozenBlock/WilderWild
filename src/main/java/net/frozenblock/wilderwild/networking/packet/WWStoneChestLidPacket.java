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
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record WWStoneChestLidPacket(BlockPos pos, float openProgress, float highestLidPoint, int cooldownTicks, int stillLidTicks, boolean closing) implements CustomPacketPayload {
	public static final Type<WWStoneChestLidPacket> PACKET_TYPE = new Type<>(
		WWConstants.id("stone_chest_lid")
	);

	public static final StreamCodec<FriendlyByteBuf, WWStoneChestLidPacket> CODEC = StreamCodec.ofMember(WWStoneChestLidPacket::write, WWStoneChestLidPacket::new);

	public WWStoneChestLidPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readBlockPos(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readBoolean());
	}

	public static void sendToAll(@NotNull StoneChestBlockEntity blockEntity) {
		for (ServerPlayer player : PlayerLookup.tracking(blockEntity)) {
			ServerPlayNetworking.send(
				player,
				new WWStoneChestLidPacket(
					blockEntity.getBlockPos(),
					blockEntity.openProgress,
					blockEntity.highestLidPoint,
					blockEntity.cooldownTicks,
					blockEntity.stillLidTicks,
					blockEntity.closing
				)
			);
		}
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeBlockPos(this.pos);
		buf.writeFloat(this.openProgress);
		buf.writeFloat(this.highestLidPoint);
		buf.writeInt(this.cooldownTicks);
		buf.writeInt(this.stillLidTicks);
		buf.writeBoolean(this.closing);
	}

	@NotNull
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
