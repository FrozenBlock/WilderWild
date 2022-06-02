package net.frozenblock.wilderwild.misc.PVZGWSound;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;

public class FlyBySoundHub {

    @Environment(EnvType.CLIENT)
    public static class clientFlyby {

        public static ArrayList<Entity> flybyEntities = new ArrayList<>();
        public static ArrayList<SoundEvent> flybySounds = new ArrayList<>();

        public static ArrayList<Entity> flybyEntitiesClear = new ArrayList<>();
        public static ArrayList<SoundEvent> flybySoundsClear = new ArrayList<>();

        public static void update(MinecraftClient client, PlayerEntity player) {
            //WilderWild.log("UPDATING FLYBY SOUNDS", WilderWild.DEV_LOGGING);
            for (Entity entity : flybyEntities) {
                if (flybySounds.size() != flybyEntities.size()) {
                    flybySounds.clear();
                    flybyEntities.clear();
                    return;
                }
                int index = flybyEntities.indexOf(entity);
                if (entity!=null) {
                    Vec3d vel = entity.getVelocity();
                    double distanceTo = entity.getPos().distanceTo(player.getEyePos());
                    double newDistanceTo = entity.getPos().add(vel).distanceTo(player.getEyePos());
                    if (distanceTo < vel.lengthSquared()*2 && distanceTo > newDistanceTo) {
                        client.getSoundManager().play(new EntityTrackingSoundInstance(flybySounds.get(index), SoundCategory.NEUTRAL, 1.0F, (float) entity.getVelocity().length(), entity, client.world.random.nextLong()));
                        removeEntityStart(entity);
                    }
                } else {
                    removeEntityStart(entity);
                }
            }
            removeEntityFinish();
        }


        public static void addEntity(Entity entity, SoundEvent soundEvent) {
            if (!flybyEntities.contains(entity)) {
                flybyEntities.add(entity);
                flybySounds.add(soundEvent);
            } else {
                flybySounds.set(flybyEntities.indexOf(entity), soundEvent);
            }
        }

        public static void removeEntityStart(Entity entity) {
            int index = flybyEntities.indexOf(entity);
            if (!flybyEntitiesClear.contains(entity)) {
                flybyEntitiesClear.add(entity);
            }
            if (!flybySoundsClear.contains(flybySounds.get(index))) {
                flybySoundsClear.add(flybySounds.get(index));
            }
        }

        public static void removeEntityFinish() {
            for (Entity entity : flybyEntitiesClear) {
                if (flybyEntities.contains(entity)) {
                    flybyEntities.remove(entity);
                }
            }
            for (SoundEvent sound : flybySoundsClear) {
                if (flybySounds.contains(sound)) {
                    flybySounds.remove(sound);
                }
            }
        }
    }

    public static void createFlybySound(World world, Entity entity, SoundEvent sound) {
        if (world.isClient)
            throw new IllegalStateException("no sounds on the client, you freaking idiot!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(entity.getId());
        byteBuf.writeRegistryValue(Registry.SOUND_EVENT, sound);
        for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld)world, entity.getBlockPos(), 128)) {
            ServerPlayNetworking.send(player, WilderWild.FLYBY_SOUND_PACKET, byteBuf);
        }
    }
}
