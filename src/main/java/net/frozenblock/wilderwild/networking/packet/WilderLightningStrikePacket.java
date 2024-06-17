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

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public record WilderLightningStrikePacket(int blockStateId, double x, double y, double z, int tickCount) implements FabricPacket {

	public static final PacketType<WilderLightningStrikePacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("lightning_strike"),
			WilderLightningStrikePacket::new
	);

	public WilderLightningStrikePacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt());
	}

	public static void sendToAll(@NotNull Entity entity, @NotNull BlockState blockState, int tickCount) {
		WilderLightningStrikePacket lightningStrikePacket = new WilderLightningStrikePacket(
				Block.getId(blockState),
				entity.getX(),
				entity.getY(),
				entity.getZ(),
				tickCount
		);
		for (ServerPlayer player : PlayerLookup.tracking(entity)) {
			ServerPlayNetworking.send(player, lightningStrikePacket);
		}
	}

	@Override
	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeInt(this.blockStateId());
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
		buf.writeVarInt(this.tickCount());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
