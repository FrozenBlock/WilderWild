package net.frozenblock.wilderwild.misc.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class EasyPacket {

    public static class EasySeedPacket {
        public static void createParticle(World world, Vec3d pos, int count, boolean isMilkweed, int radius) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(count);
            byteBuf.writeBoolean(isMilkweed);
            for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld) world, pos, 128)) {
                ServerPlayNetworking.send(player, WilderWild.SEED_PACKET, byteBuf);
            }
        }

        public static void createControlledParticle(World world, Vec3d pos, double xvel, double yvel, double zvel, int count, boolean isMilkweed, int radius) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME PLS");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeDouble(xvel * 1.5);
            byteBuf.writeDouble(yvel);
            byteBuf.writeDouble(zvel * 1.5);
            byteBuf.writeVarInt(count);
            byteBuf.writeBoolean(isMilkweed);
            for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld) world, pos, radius)) {
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
            for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld) world, pos, 32)) {
                ServerPlayNetworking.send(player, WilderWild.FLOATING_SCULK_BUBBLE_PACKET, byteBuf);
            }
        }
    }

    public static class EasySensorHiccupPacket {
        public static void createParticle(World world, Vec3d pos) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK SPAWN ON SERVER NEXT TIME STUPID IDIOT");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld) world, pos, 32)) {
                ServerPlayNetworking.send(player, WilderWild.SENSOR_HICCUP_PACKET, byteBuf);
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
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, new BlockPos(pos))) {
                ServerPlayNetworking.send(player, WilderWild.TERMITE_PARTICLE_PACKET, byteBuf);
            }
        }
    }

    public static class EasyCompetitionPacket {
        public static void sendFireflyCaptureInfo(World world, PlayerEntity player, Firefly firefly) { //Can possibly be used for competitions
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

    public static void createMovingLoopingSound(World world, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch, Identifier id) {
        if (world.isClient)
            throw new IllegalStateException("no sounds on the client, you freaking idiot!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(entity.getId());
        byteBuf.writeRegistryValue(Registry.SOUND_EVENT, sound);
        byteBuf.writeEnumConstant(category);
        byteBuf.writeFloat(volume);
        byteBuf.writeFloat(pitch);
        byteBuf.writeIdentifier(id);
        for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld) world, entity.getBlockPos(), 32)) {
            ServerPlayNetworking.send(player, WilderWild.MOVING_LOOPING_SOUND_PACKET, byteBuf);
        }
    }

    public static void createMovingRestrictionSound(World world, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch, Identifier id) {
        if (world.isClient)
            throw new IllegalStateException("no sounds on the client, you freaking idiot!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(entity.getId());
        byteBuf.writeRegistryValue(Registry.SOUND_EVENT, sound);
        byteBuf.writeEnumConstant(category);
        byteBuf.writeFloat(volume);
        byteBuf.writeFloat(pitch);
        byteBuf.writeIdentifier(id);
        for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld) world, entity.getBlockPos(), 64)) {
            ServerPlayNetworking.send(player, WilderWild.MOVING_RESTRICTION_SOUND_PACKET, byteBuf);
        }
    }

    public static void createMovingRestrictionLoopingSound(World world, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch, Identifier id) {
        if (world.isClient)
            throw new IllegalStateException("no sounds on the client, you freaking idiot!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(entity.getId());
        byteBuf.writeRegistryValue(Registry.SOUND_EVENT, sound);
        byteBuf.writeEnumConstant(category);
        byteBuf.writeFloat(volume);
        byteBuf.writeFloat(pitch);
        byteBuf.writeIdentifier(id);
        for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld) world, entity.getBlockPos(), 64)) {
            ServerPlayNetworking.send(player, WilderWild.MOVING_RESTRICTION_LOOPING_SOUND_PACKET, byteBuf);
        }
    }

}
