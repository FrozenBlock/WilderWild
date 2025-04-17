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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.networking.packet;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WWWindPacket(Vec3 cloudPos) implements CustomPacketPayload {
	public static final Type<WWWindPacket> PACKET_TYPE = new Type<>(
		WWConstants.id("wind_extension_sync")
	);

	public static final StreamCodec<FriendlyByteBuf, WWWindPacket> CODEC = StreamCodec.ofMember(WWWindPacket::write, WWWindPacket::new);

	public WWWindPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readVec3());
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
