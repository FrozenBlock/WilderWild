package net.frozenblock.wilderwild.particle.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.SculkChargeParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;

public class EasyParticlePacket {

    public static class EasySeedPacket {
        public static void createParticle(World world, Vec3d pos, int count, boolean isMilkweed) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(count);
            byteBuf.writeBoolean(isMilkweed);
            for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld)world, pos, 32)) {
                ServerPlayNetworking.send(player, WilderWild.SEED_PACKET, byteBuf);
            }
        }
        public static void createControlledParticle(World world, Vec3d pos, double xvel, double yvel, double zvel, int count, boolean isMilkweed) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeDouble(xvel*1.5);
            byteBuf.writeDouble(yvel);
            byteBuf.writeDouble(zvel*1.5);
            byteBuf.writeVarInt(count);
            byteBuf.writeBoolean(isMilkweed);
            for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld)world, pos, 32)) {
                ServerPlayNetworking.send(player, WilderWild.CONTROLLED_SEED_PACKET, byteBuf);
            }
        }
    }

    public static class EasyFloatingSculkBubblePacket {
        public static void createParticle(World world, Vec3d pos, int size, int maxAge, double yVel, int count) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(size);
            byteBuf.writeVarInt(maxAge);
            byteBuf.writeDouble(yVel);
            byteBuf.writeVarInt(count);
            for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld)world, pos, 32)) {
                ServerPlayNetworking.send(player, WilderWild.FLOATING_SCULK_BUBBLE_PACKET, byteBuf);
            }
        }
    }

    public static class EasyTermitePacket {
        public static void createParticle(World world, Vec3d pos, int count) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(count);
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld)world, new BlockPos(pos))) {
                ServerPlayNetworking.send(player, WilderWild.TERMITE_PARTICLE_PACKET, byteBuf);
            }
        }
    }

    public static class EasyEffectsPacket {
        public static void createPointParticle(World world, Vec3d pos, int count) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(count);
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld)world, new BlockPos(pos))) {
                ServerPlayNetworking.send(player, WilderWild.EFFECT_POINT_PARTICLE_PACKET, byteBuf);
            }
        }
    }
}
