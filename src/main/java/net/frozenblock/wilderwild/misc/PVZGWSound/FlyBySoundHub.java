package net.frozenblock.wilderwild.misc.PVZGWSound;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FlyBySoundHub {

    public static final ArrayList<EntityType<?>> flybyEntitiesAuto = new ArrayList<>() {{
        add(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY);
        add(EntityType.ARROW);
        add(EntityType.BAT);
    }};
    public static final ArrayList<SoundEvent> flybySoundsAuto = new ArrayList<>() {{
        add(RegisterSounds.ANCIENT_HORN_PROJECTILE_FLYBY);
        add(RegisterSounds.ANCIENT_HORN_PROJECTILE_FLYBY);
        add(SoundEvents.ENTITY_BAT_TAKEOFF);
    }};
    public static final ArrayList<SoundCategory> categoriesAuto = new ArrayList<>() {{
        add(SoundCategory.PLAYERS);
        add(SoundCategory.NEUTRAL);
        add(SoundCategory.NEUTRAL);
    }};
    public static final FloatArrayList volumesAuto = new FloatArrayList() {{
        add(0.15F);
        add(0.15F);
        add(0.15F);
    }};
    public static final FloatArrayList pitchesAuto = new FloatArrayList() {{
        add(0.8F);
        add(1.6F);
        add(1.0F);
    }};

    public static final ArrayList<Entity> flybyEntities = new ArrayList<>();
    public static final ArrayList<SoundEvent> flybySounds = new ArrayList<>();
    public static final ArrayList<SoundCategory> categories = new ArrayList<>();
    public static final FloatArrayList volumes = new FloatArrayList();
    public static final FloatArrayList pitches = new FloatArrayList();
    public static final IntArrayList cooldowns = new IntArrayList();

    public static final ArrayList<Entity> flybyEntitiesClear = new ArrayList<>();
    private static int checkAroundCooldown;

    public static void update(MinecraftClient client, PlayerEntity player, boolean autoSounds) {
        for (Entity entity : flybyEntities) {
            if (flybySounds.size() != flybyEntities.size() || client.world == null) {
                flybySounds.clear();
                flybyEntities.clear();
                volumes.clear();
                pitches.clear();
                cooldowns.clear();
                return;
            }
            int index = flybyEntities.indexOf(entity);
            if (entity != null) {
                Vec3d vel = entity.getVelocity();
                Vec3d playerVel = player.getVelocity();
                Vec3d entityPos = entity.getPos();
                Vec3d playerPos = player.getEyePos();
                double distanceTo = entityPos.distanceTo(playerPos);
                double newDistanceTo = entityPos.add(vel).add(vel).distanceTo(playerPos.add(playerVel));
                cooldowns.set(index, cooldowns.getInt(index) - 1);
                if ((distanceTo > newDistanceTo && distanceTo < (vel.lengthSquared() + playerVel.length()) * 2) && cooldowns.getInt(index) <= 0) {
                    float volume = (float) (volumes.getFloat(index) + (vel.length() / 2));
                    client.getSoundManager().play(new EntityTrackingSoundInstance(flybySounds.get(index), categories.get(index), volume, pitches.getFloat(index), entity, client.world.random.nextLong()));
                    cooldowns.set(index, 40);
                }
            } else {
                removeEntityStart(entity);
            }
        }
        removeEntityFinish();
        if (checkAroundCooldown > 0) {
            --checkAroundCooldown;
        } else {
            if (client.world != null && autoSounds) {
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
