package net.frozenblock.wilderwild.misc;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class SaveableGameEvent {

    private static final Logger LOGGER = LogUtils.getLogger();
    public ResourceLocation event;
    public Vec3 originPos;
    public String uuid;

    //TEMP STORAGE
    public Entity foundEntity;

    public static final Codec<SaveableGameEvent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("eventID").forGetter(SaveableGameEvent::getEventID),
            Vec3.CODEC.fieldOf("originPos").forGetter(SaveableGameEvent::getOriginPos),
            Codec.STRING.fieldOf("uuid").forGetter(SaveableGameEvent::getUUID)
    ).apply(instance, SaveableGameEvent::new));

    public SaveableGameEvent(ResourceLocation event, Vec3 originPos, String uuid) {
        this.event = event;
        this.originPos = originPos;
        this.uuid = uuid;
    }

    public SaveableGameEvent(GameEvent event, Vec3 originPos, GameEvent.Context emitter) {
        this.event = Registry.GAME_EVENT.getKey(event);
        this.originPos = originPos;
        if (emitter.sourceEntity() != null) {
            this.uuid = emitter.sourceEntity().getUUID().toString();
        } else {
            this.uuid = "noEntity";
        }
    }

    public SaveableGameEvent(GameEvent event, Vec3 originPos, @Nullable Entity entity) {
        this.event = Registry.GAME_EVENT.getKey(event);
        this.originPos = originPos;
        if (entity != null) {
            this.uuid = entity.getUUID().toString();
        } else {
            this.uuid = "noEntity";
        }
    }

    public boolean isViable() {
        Optional<GameEvent> event = Registry.GAME_EVENT.getOptional(this.event);
        if (event.isPresent()) {
            return this.originPos != null;
        }
        return false;
    }

    public void emitGameEvent(Level world, BlockPos exitPos) {
        if (this.isViable()) {
            world.gameEvent(this.getEntity(world), this.getGameEvent(), exitPos);
        }
    }

    public void emitGameEvent(Level world, Vec3 exitPos) {
        if (this.isViable()) {
            world.gameEvent(this.getEntity(world), this.getGameEvent(), exitPos);
        }
    }

    public GameEvent getGameEvent() {
        return Registry.GAME_EVENT.get(this.event);
    }

    @Nullable
    public Entity getEntity(Level world) {
        if (!this.uuid.equals("noEntity")) {
            if (this.foundEntity != null) {
                if (this.foundEntity.getUUID().toString().equals(this.uuid)) {
                    return this.foundEntity;
                } else {
                    this.foundEntity = null;
                }
            }
            AABB box = new AABB(this.originPos.add(-32, -32, -32), this.originPos.add(32, 32, 32));
            List<Entity> entities = world.getEntitiesOfClass(Entity.class, box);
            for (Entity entity : entities) {
                if (entity.getUUID().toString().equals(this.uuid)) {
                    this.foundEntity = entity;
                    return entity;
                }
            }
        }
        return null;
    }

    public ResourceLocation getEventID() {
        return this.event;
    }

    public Vec3 getOriginPos() {
        return this.originPos;
    }

    public String getUUID() {
        return this.uuid;
    }

    public static SaveableGameEvent readNbt(CompoundTag nbt) {
        Optional<SaveableGameEvent> event = Optional.empty();
        if (nbt.contains("savedGameEvent", 10)) {
            event = SaveableGameEvent.CODEC
                    .parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("savedGameEvent")))
                    .resultOrPartial(LOGGER::error);
        }
        return event.orElse(null);
    }

    public static void writeNbt(CompoundTag nbt, @Nullable SaveableGameEvent saveableGameEvent) {
        if (saveableGameEvent != null) {
            SaveableGameEvent.CODEC
                    .encodeStart(NbtOps.INSTANCE, saveableGameEvent)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(saveableGameEventNbt -> nbt.put("savedGameEvent", saveableGameEventNbt));
        } else {
            if (nbt.contains("savedGameEvent", 10)) {
                nbt.remove("savedGameEvent");
            }
        }
    }

    public SaveableGameEvent copyOf() {
        return new SaveableGameEvent(this.event, this.originPos, this.uuid);
    }

    /** EXAMPLES FOR READING & SAVING NBT */
    /*
    READ
    this.savedEvent = SaveableGameEvent.readNbt(nbtCompound);

    WRITE
    SaveableGameEvent.writeNbt(nbtCompound, this.savedEvent);
    */


    /** USAGE */
    /*
    Saveable Game Events are particularly useful for sending information about a GameEvent between multiple blocks, for example;
    Pipe A picks up a GameEvent. Instead of manually checking through the pipeline and emitting a GameEvent at the end, it can simply just
    send the GameEvent THROUGH the pipeline.

    Saveable Game Events are also useful for simply delaying the creation of a Game Event, for example;
    A Sculk Echoer. You know this already.
    nvm
     */
}
