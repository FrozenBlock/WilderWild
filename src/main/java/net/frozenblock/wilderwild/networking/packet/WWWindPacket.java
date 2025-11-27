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

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record WWWindPacket(double cloudX, double cloudZ) implements CustomPacketPayload {
	public static final Type<WWWindPacket> PACKET_TYPE = new Type<>(WWConstants.id("wind_extension_sync"));
	public static final StreamCodec<FriendlyByteBuf, WWWindPacket> CODEC = StreamCodec.ofMember(WWWindPacket::write, WWWindPacket::new);

	public WWWindPacket(FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeDouble(this.cloudX);
		buf.writeDouble(this.cloudZ);
	}

	@Override
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
