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
import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WilderWindPacket(Vec3 cloudPos) implements CustomPacketPayload {
	public static final Type<WilderWindPacket> PACKET_TYPE = new Type<>(
		WilderConstants.id("wind_extension_sync")
	);

	public static final StreamCodec<FriendlyByteBuf, WilderWindPacket> CODEC = StreamCodec.ofMember(WilderWindPacket::write, WilderWindPacket::new);

	public WilderWindPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readVec3());
	}

	public static void sendTo(ServerPlayer serverPlayer, Vec3 cloudPos) {
		ServerPlayNetworking.send(serverPlayer, new WilderWindPacket(cloudPos));
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeVec3(this.cloudPos());
	}

	@NotNull
	@Override
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
