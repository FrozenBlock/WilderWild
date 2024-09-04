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

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WWSensorHiccupPacket(double x, double y, double z) implements CustomPacketPayload {
	public static final int PARTICLE_COLOR = 5578058;
	public static final Type<WWSensorHiccupPacket> PACKET_TYPE = new Type<>(
		WWConstants.id("sensor_hiccup")
	);

	public static final StreamCodec<FriendlyByteBuf, WWSensorHiccupPacket> CODEC = StreamCodec.ofMember(WWSensorHiccupPacket::write, WWSensorHiccupPacket::new);

	public WWSensorHiccupPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}

	public static void sendToAll(@NotNull BlockEntity blockEntity, @NotNull Vec3 pos) {
		for (ServerPlayer player : PlayerLookup.tracking(blockEntity)) {
			ServerPlayNetworking.send(
				player,
				new WWSensorHiccupPacket(pos.x(), pos.y(), pos.z())
			);
		}
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
	}

	@NotNull
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
