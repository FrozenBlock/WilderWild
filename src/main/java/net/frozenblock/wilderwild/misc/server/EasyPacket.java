package net.frozenblock.wilderwild.misc.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EasyPacket {

    public static class EasySeedPacket {
        public static void createParticle(World world, Vec3d pos, int count, boolean isMilkweed) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
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
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
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
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
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
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
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

    public static class EasyCompetitionPacket {
        public static void sendFireflyCaptureInfo(World world, PlayerEntity player, FireflyEntity firefly) { //Can possibly be used for competitions
            if (world.isClient)
                throw new IllegalStateException("FIREFLY CAPTURE ON CLIENT!??!?!?!?! OH HOW TERRIBLE OF YOU!1!!!!!!!!!!!!!!!!!!!!!1!!1!!!!111");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeBoolean(player.isCreative());
            byteBuf.writeBoolean(firefly.natural && !firefly.isFromBottle());
            if (player instanceof ServerPlayerEntity serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, WilderWild.CAPTURE_FIREFLY_NOTIFY_PACKET, byteBuf);
            } else {
                throw new IllegalStateException("NOT A SERVER PLAYER BRUH");
            }
        }
        public static void sendAncientHornKillInfo(World world, PlayerEntity player, LivingEntity entity) { //Can possibly be used for competitions
            if (world.isClient)
                throw new IllegalStateException("ANCIENT HORN KILL PACKET ON CLIENT");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeBoolean(player.isCreative());
            byteBuf.writeBoolean(true);
            if (player instanceof ServerPlayerEntity serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, WilderWild.ANCIENT_HORN_KILL_NOTIFY_PACKET, byteBuf);
            } else {
                throw new IllegalStateException("NOT A SERVER PLAYER BRUH");
            }
        }
    }
}
