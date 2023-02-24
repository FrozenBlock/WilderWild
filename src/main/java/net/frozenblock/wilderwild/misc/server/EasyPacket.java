package net.frozenblock.wilderwild.misc.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EasyPacket {

    public static class EasySeedPacket {
        public static void createParticle(Level level, Vec3 pos, int count, boolean isMilkweed, int radius) {
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

        public static void createControlledParticle(Level level, Vec3 pos, double xvel, double yvel, double zvel, int count, boolean isMilkweed, int radius) {
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
            for (ServerPlayer player : PlayerLookup.around((ServerLevel) level, pos, radius)) {
                ServerPlayNetworking.send(player, WilderWild.CONTROLLED_SEED_PACKET, byteBuf);
            }
        }
    }

    public static class EasyFloatingSculkBubblePacket {
        public static void createParticle(Level level, Vec3 pos, int size, int maxAge, double yVel, int count) {
            if (level.isClientSide)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(size);
            byteBuf.writeVarInt(maxAge);
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
            for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) level, BlockPos.containing(pos))) {
                ServerPlayNetworking.send(player, WilderWild.TERMITE_PARTICLE_PACKET, byteBuf);
            }
        }
    }

    public static class EasyCompetitionPacket {
        public static void sendFireflyCaptureInfo(Level level, Player player, Firefly firefly) { //Can possibly be used for competitions
            if (level.isClientSide)
                throw new IllegalStateException("FIREFLY CAPTURE ON CLIENT!??!?!?!?! OH HOW TERRIBLE OF YOU!1!!!!!!!!!!!!!!!!!!!!!1!!1!!!!111");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeBoolean(player.isCreative());
            byteBuf.writeBoolean(firefly.natural && !firefly.isFromBottle());
            if (player instanceof ServerPlayer serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, WilderWild.CAPTURE_FIREFLY_NOTIFY_PACKET, byteBuf);
            } else {
                throw new IllegalStateException("NOT A SERVER PLAYER BRUH");
            }
        }

        public static void sendAncientHornKillInfo(Level level, Player player, LivingEntity entity) { //Can possibly be used for competitions
            if (level.isClientSide)
                throw new IllegalStateException("ANCIENT HORN KILL PACKET ON CLIENT");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeBoolean(player.isCreative());
            byteBuf.writeBoolean(true);
            if (player instanceof ServerPlayer serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, WilderWild.ANCIENT_HORN_KILL_NOTIFY_PACKET, byteBuf);
            } else {
                throw new IllegalStateException("NOT A SERVER PLAYER BRUH");
            }
        }
    }

    public static void sendJellySting(ServerPlayer player) {
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        ServerPlayNetworking.send(player, WilderWild.JELLY_STING_PACKET, byteBuf);
    }

}
