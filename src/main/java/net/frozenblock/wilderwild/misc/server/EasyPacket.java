/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EasyPacket {

    public static class EasySeedPacket {
        public static void createParticle(Level level, Vec3 pos, int count, boolean isMilkweed) {
            if (level.isClientSide)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(count);
            byteBuf.writeBoolean(isMilkweed);
            for (ServerPlayer player : PlayerLookup.around((ServerLevel) level, pos, 128)) {
                ServerPlayNetworking.send(player, WilderWild.SEED_PACKET, byteBuf);
            }
        }

        public static void createControlledParticle(Level level, Vec3 pos, double xvel, double yvel, double zvel, int count, boolean isMilkweed, int radius, double posRandomizer) {
            if (level.isClientSide)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeDouble(xvel * 1.5);
            byteBuf.writeDouble(yvel);
            byteBuf.writeDouble(zvel * 1.5);
            byteBuf.writeVarInt(count);
            byteBuf.writeBoolean(isMilkweed);
			byteBuf.writeDouble(posRandomizer);
            for (ServerPlayer player : PlayerLookup.around((ServerLevel) level, pos, radius)) {
                ServerPlayNetworking.send(player, WilderWild.CONTROLLED_SEED_PACKET, byteBuf);
            }
        }
    }

    public static class EasyFloatingSculkBubblePacket {
        public static void createParticle(Level level, Vec3 pos, double size, int maxAge, double yVel, int count) {
            if (level.isClientSide)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeDouble(size);
            byteBuf.writeInt(maxAge);
            byteBuf.writeDouble(yVel);
            byteBuf.writeVarInt(count);
            for (ServerPlayer player : PlayerLookup.around((ServerLevel) level, pos, 32)) {
                ServerPlayNetworking.send(player, WilderWild.FLOATING_SCULK_BUBBLE_PACKET, byteBuf);
            }
        }
    }

    public static class EasySensorHiccupPacket {
        public static void createParticle(Level level, Vec3 pos) {
            if (level.isClientSide)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME STUPID IDIOT");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            for (ServerPlayer player : PlayerLookup.around((ServerLevel) level, pos, 32)) {
                ServerPlayNetworking.send(player, WilderWild.SENSOR_HICCUP_PACKET, byteBuf);
            }
        }
    }

    public static class EasyTermitePacket {
        public static void createParticle(Level level, Vec3 pos, int count) {
            if (level.isClientSide)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(count);
            for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) level, new BlockPos(pos))) {
                ServerPlayNetworking.send(player, WilderWild.TERMITE_PARTICLE_PACKET, byteBuf);
            }
        }
    }

    public static void sendJellySting(ServerPlayer player) {
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        ServerPlayNetworking.send(player, WilderWild.JELLY_STING_PACKET, byteBuf);
    }

}
