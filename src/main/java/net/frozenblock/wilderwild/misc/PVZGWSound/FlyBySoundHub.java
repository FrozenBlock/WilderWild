package net.frozenblock.wilderwild.misc.PVZGWSound;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FlyBySoundHub {

    @Environment(EnvType.CLIENT)
    public static class clientFlyby {

        public static ArrayList<EntityType> flybyEntitiesAuto = new ArrayList<>() {{
            add(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY);
            add(EntityType.ARROW);
            add(EntityType.BAT);
        }};
        public static ArrayList<SoundEvent> flybySoundsAuto = new ArrayList<>() {{
            add(RegisterSounds.PVZGW_CHEETO_FLYBY);
            add(SoundEvents.ENTITY_ARROW_SHOOT);
            add(SoundEvents.ENTITY_BAT_TAKEOFF);
        }};
        public static ArrayList<SoundCategory> categoriesAuto = new ArrayList<>() {{
            add(SoundCategory.PLAYERS);
            add(SoundCategory.NEUTRAL);
            add(SoundCategory.NEUTRAL);
        }};
        public static FloatArrayList volumesAuto = new FloatArrayList() {{
            add(0.6F);
            add(0.6F);
            add(0.6F);
        }};
        public static FloatArrayList pitchesAuto = new FloatArrayList() {{
            add(0.8F);
            add(1.3F);
            add(1.0F);
        }};

        public static ArrayList<Entity> flybyEntities = new ArrayList<>();
        public static ArrayList<SoundEvent> flybySounds = new ArrayList<>();
        public static ArrayList<SoundCategory> categories = new ArrayList<>();
        public static FloatArrayList volumes = new FloatArrayList();
        public static FloatArrayList pitches = new FloatArrayList(); //get some
        public static IntArrayList cooldowns = new IntArrayList();

        public static ArrayList<Entity> flybyEntitiesClear = new ArrayList<>();
        public static int checkAroundCooldown;

        public static void update(MinecraftClient client, PlayerEntity player, boolean autoSounds) {
            for (Entity entity : flybyEntities) {
                if (flybySounds.size() != flybyEntities.size() || client.world==null) {
                    flybySounds.clear();
                    flybyEntities.clear();
                    volumes.clear();
                    pitches.clear();
                    cooldowns.clear();
                    return;
                }
                int index = flybyEntities.indexOf(entity);
                if (entity!=null) {
                    Vec3d vel = entity.getVelocity();
                    Vec3d playerVel = player.getVelocity();
                    Vec3d entityPos = entity.getVelocity();
                    Vec3d playerPos = player.getEyePos();
                    double distanceTo = entityPos.distanceTo(playerPos);
                    double newDistanceTo = entityPos.add(vel).distanceTo(playerPos);
                    double playerTo = entityPos.distanceTo(playerPos.add(playerVel));
                    cooldowns.set(index, cooldowns.getInt(index)-1);
                    if (((distanceTo > newDistanceTo && distanceTo < vel.lengthSquared()*2) || (distanceTo > playerTo && distanceTo < playerVel.lengthSquared()*2)) && cooldowns.getInt(index)<=0) {
                        float volume = (float) (volumes.getFloat(index) + ((distanceTo - newDistanceTo)));
                        client.getSoundManager().play(new EntityTrackingSoundInstance(flybySounds.get(index), categories.get(index), volume, pitches.getFloat(index), entity, client.world.random.nextLong()));
                        cooldowns.set(index, 40);
                    }
                } else {
                    removeEntityStart(entity);
                }
            }
            removeEntityFinish();
            if (checkAroundCooldown>0) { --checkAroundCooldown; } else {
                if (client.world!=null && autoSounds) {
                    Box box = new Box(player.getBlockPos().add(-3, -3, -3), player.getBlockPos().add(3, 3, 3));
                    List<Entity> entities = client.world.getOtherEntities(player, box);
                    for (Entity entity : entities) {
                        EntityType<?> type = entity.getType();
                        if (flybyEntitiesAuto.contains(type)) {
                            int index = flybyEntitiesAuto.indexOf(type);
                            addEntity(entity, flybySoundsAuto.get(index), categoriesAuto.get(index), volumesAuto.getFloat(index), pitchesAuto.getFloat(index));
                        }
                    }
                }
            }
        }


        public static void addEntity(Entity entity, SoundEvent soundEvent, SoundCategory category, float volume, float pitch) {
            if (!flybyEntities.contains(entity)) {
                flybyEntities.add(entity);
                flybySounds.add(soundEvent);
                categories.add(category);
                volumes.add(volume);
                pitches.add(pitch);
                cooldowns.add(1);
            } else {
                int index = flybyEntities.indexOf(entity);
                flybySounds.set(index, soundEvent);
                categories.set(index, category);
                volumes.set(index, volume);
                pitches.set(index, pitch);
            }
        }

        public static void removeEntityStart(Entity entity) {
            if (!flybyEntitiesClear.contains(entity)) {
                flybyEntitiesClear.add(entity);
            }
        }

        public static void removeEntityFinish() {
            for (Entity entity : flybyEntitiesClear) {
                if (flybyEntities.contains(entity)) {
                    int index = flybyEntities.indexOf(entity);
                    flybySounds.remove(index);
                    volumes.removeFloat(index);
                    pitches.removeFloat(index);
                    categories.remove(index);
                    flybyEntities.remove(entity);
                }
            }
        }
    }

    public static void createFlybySound(World world, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        if (world.isClient)
            throw new IllegalStateException("no sounds on the client, you freaking idiot!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(entity.getId());
        byteBuf.writeRegistryValue(Registry.SOUND_EVENT, sound);
        byteBuf.writeEnumConstant(category);
        byteBuf.writeFloat(volume);
        byteBuf.writeFloat(pitch);
        for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld)world, entity.getBlockPos(), 128)) {
            ServerPlayNetworking.send(player, WilderWild.FLYBY_SOUND_PACKET, byteBuf);
        }
    }
}
