package net.frozenblock.wilderwild.misc;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

public class SaveableGameEvent {

    private static final Logger LOGGER = LogUtils.getLogger();
    public Identifier event;
    public Vec3d originPos;
    public String uuid;

    //TEMP STORAGE
    public Entity foundEntity;

    public static final Codec<SaveableGameEvent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("eventID").forGetter(SaveableGameEvent::getEventID),
            Vec3d.CODEC.fieldOf("originPos").forGetter(SaveableGameEvent::getOriginPos),
            Codec.STRING.fieldOf("uuid").forGetter(SaveableGameEvent::getUUID)
    ).apply(instance, SaveableGameEvent::new));

    public SaveableGameEvent(Identifier event, Vec3d originPos, String uuid) {
        this.event = event;
        this.originPos = originPos;
        this.uuid = uuid;
    }

    public SaveableGameEvent(GameEvent event, Vec3d originPos, GameEvent.Emitter emitter) {
        this.event = Registry.GAME_EVENT.getId(event);
        this.originPos = originPos;
        if (emitter.sourceEntity()!=null) {
            this.uuid = emitter.sourceEntity().getUuid().toString();
        } else {
            this.uuid = "noEntity";
        }
    }

    public SaveableGameEvent(GameEvent event, Vec3d originPos, @Nullable Entity entity) {
        this.event = Registry.GAME_EVENT.getId(event);
        this.originPos = originPos;
        if (entity!=null) {
            this.uuid = entity.getUuid().toString();
        } else {
            this.uuid = "noEntity";
        }
    }

    public boolean isViable() {
        Optional<GameEvent> event = Registry.GAME_EVENT.getOrEmpty(this.event);
        if (event.isPresent()) {
            return this.originPos != null;
        } return false;
    }

    public void emitGameEvent(World world, BlockPos exitPos) {
        if (this.isViable()) {
            world.emitGameEvent(this.getEntity(world), this.getGameEvent(), exitPos);
        }
    }

    public void emitGameEvent(World world, Vec3d exitPos) {
        if (this.isViable()) {
            world.emitGameEvent(this.getEntity(world), this.getGameEvent(), exitPos);
        }
    }

    public GameEvent getGameEvent() {
        return Registry.GAME_EVENT.get(this.event);
    }

    @Nullable
    public Entity getEntity(World world) {
        if (!this.uuid.equals("noEntity")) {
            if (this.foundEntity != null) {
                if (this.foundEntity.getUuid().toString().equals(this.uuid)) {
                    return this.foundEntity;
                } else {
                    this.foundEntity = null;
                }
            }
            Box box = new Box(this.originPos.add(-32, -32, -32), this.originPos.add(32, 32, 32));
            List<Entity> entities = world.getNonSpectatingEntities(Entity.class, box);
            for (Entity entity : entities) {
                if (entity.getUuid().toString().equals(this.uuid)) {
                    this.foundEntity = entity;
                    return entity;
                }
            }
        } return null;
    }

    public Identifier getEventID() { return this.event; }
    public Vec3d getOriginPos() { return this.originPos; }
    public String getUUID() { return this.uuid; }

    public static SaveableGameEvent readNbt(NbtCompound nbt) {
        Optional<SaveableGameEvent> event = Optional.empty();
        if (nbt.contains("savedGameEvent", 10)) {
            event = SaveableGameEvent.CODEC
                    .parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("savedGameEvent")))
                    .resultOrPartial(LOGGER::error);
        }
        return event.orElse(null);
    }

    public static void writeNbt(NbtCompound nbt, @Nullable SaveableGameEvent saveableGameEvent) {
        if (saveableGameEvent!=null) {
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
