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

import java.util.List;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public record WWLeavesExplosionParticlePacket(BlockState state, BlockPos pos, Vec3 velocity, List<Direction> directions, int count) implements CustomPacketPayload {
	public static final Type<WWLeavesExplosionParticlePacket> PACKET_TYPE = new Type<>(WWConstants.id("leaves_explosion"));
	public static final StreamCodec<RegistryFriendlyByteBuf, WWLeavesExplosionParticlePacket> CODEC = StreamCodec.ofMember(WWLeavesExplosionParticlePacket::write, WWLeavesExplosionParticlePacket::new);

	public WWLeavesExplosionParticlePacket(RegistryFriendlyByteBuf buf) {
		this(buf.readById(Block::stateById), buf.readBlockPos(), buf.readVec3(), buf.readList(Direction.STREAM_CODEC), buf.readVarInt());
	}

	public static void sendToAll(BlockState state, BlockPos pos, Vec3 vec3, List<Direction> directions, int count, ServerLevel level) {
		for (ServerPlayer player : PlayerLookup.around(level, pos, 64D)) {
			ServerPlayNetworking.send(
				player,
				new WWLeavesExplosionParticlePacket(state, pos, vec3, directions, count)
			);
		}
	}

	public void write(RegistryFriendlyByteBuf buf) {
		buf.writeById(Block::getId, this.state);
		buf.writeBlockPos(this.pos);
		buf.writeVec3(this.velocity);
		buf.writeCollection(this.directions, Direction.STREAM_CODEC);
		buf.writeVarInt(this.count);
	}

	public Type<?> type() {
		return PACKET_TYPE;
	}
}
