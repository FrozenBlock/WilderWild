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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WilderSensorHiccupPacket(double x, double y, double z) implements FabricPacket {
	@Environment(EnvType.CLIENT)
	private static final int PARTICLE_COLOR = 5578058;
	@Environment(EnvType.CLIENT)
	public static final double COLOR_X = (double) (PARTICLE_COLOR >> 16 & 255) / 255.0D;
	@Environment(EnvType.CLIENT)
	public static final double COLOR_Y = (double) (PARTICLE_COLOR >> 8 & 255) / 255.0D;
	@Environment(EnvType.CLIENT)
	public static final double COLOR_Z = (double) (PARTICLE_COLOR & 255) / 255.0D;

	public static final PacketType<WilderSensorHiccupPacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("sensor_hiccup"),
			WilderSensorHiccupPacket::new
	);

	public WilderSensorHiccupPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}

	public static void sendToAll(@NotNull BlockEntity blockEntity, @NotNull Vec3 pos) {
		WilderSensorHiccupPacket sensorHiccupPacket = new WilderSensorHiccupPacket(
				pos.x(),
				pos.y(),
				pos.z()
		);
		for (ServerPlayer player : PlayerLookup.tracking(blockEntity)) {
			ServerPlayNetworking.send(player, sensorHiccupPacket);
		}
	}

	@Override
	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
