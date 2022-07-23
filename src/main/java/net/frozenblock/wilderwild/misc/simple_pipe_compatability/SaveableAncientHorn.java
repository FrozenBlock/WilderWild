package net.frozenblock.wilderwild.misc.simple_pipe_compatability;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

public class SaveableAncientHorn {

    private static final Logger LOGGER = LogUtils.getLogger();
    public String uuid;
    public Vec3d originPos;
    public boolean hasEmitted;

    //TEMP STORAGE
    public Entity foundEntity;

    public static final Codec<SaveableAncientHorn> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("uuid").forGetter(SaveableAncientHorn::getUUID),
            Vec3d.CODEC.fieldOf("originPos").forGetter(SaveableAncientHorn::getOriginPos),
            Codec.BOOL.fieldOf("hasEmitted").forGetter(SaveableAncientHorn::hasEmitted)
    ).apply(instance, SaveableAncientHorn::new));

    public SaveableAncientHorn(String uuid, Vec3d originPos) {
        this.uuid = uuid;
        this.originPos = originPos;
        this.hasEmitted = false;
    }

    public SaveableAncientHorn(String uuid, Vec3d originPos, boolean hasEmitted) {
        this.uuid = uuid;
        this.originPos = originPos;
        this.hasEmitted = hasEmitted;
    }

    public void emit(World world, BlockPos pos) {
        if (!this.hasEmitted) {
            BlockState state = world.getBlockState(pos);
            Identifier id = Registry.BLOCK.getId(state.getBlock());
            if (id.getNamespace().equals("lunade") && id.getPath().contains("pipe")) {
                Direction direction = state.get(Properties.FACING);
                if (world instanceof ServerWorld server && this.getEntity(world) != null) {
                    BlockPos offsetPos = pos.offset(direction);
                    AncientHornProjectile projectileEntity = new AncientHornProjectile(world, offsetPos.getX() + 0.5, offsetPos.getY() + 0.5, offsetPos.getZ() + 0.5);
                    projectileEntity.setVelocity(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ(), 1.0F, 0.0F);
                    projectileEntity.setOwner(this.foundEntity);
                    projectileEntity.shotByPlayer = true;
                    server.spawnEntity(projectileEntity);
                    EasyPacket.createMovingLoopingSound(server, projectileEntity, RegisterSounds.ANCIENT_HORN_PROJECTILE_LOOP, SoundCategory.NEUTRAL, 1.0F, 1.0F, WilderWild.id("default"));
                }
            }
        }
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
                    this.originPos = entity.getPos();
                    return entity;
                }
            }
        }
        return null;
    }


    public Vec3d getOriginPos() {
        return this.originPos;
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean hasEmitted() {
        return this.hasEmitted;
    }

    public static SaveableAncientHorn readNbt(NbtCompound nbt) {
        Optional<SaveableAncientHorn> horn = Optional.empty();
        if (nbt.contains("savedAncientHorn", 10)) {
            horn = SaveableAncientHorn.CODEC
                    .parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("savedAncientHorn")))
                    .resultOrPartial(LOGGER::error);
        }
        return horn.orElse(null);
    }

    public static void writeNbt(NbtCompound nbt, @Nullable SaveableAncientHorn saveableAncientHorn) {
        if (saveableAncientHorn != null) {
            SaveableAncientHorn.CODEC
                    .encodeStart(NbtOps.INSTANCE, saveableAncientHorn)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(saveableHornNbt -> nbt.put("savedAncientHorn", saveableHornNbt));
        } else {
            if (nbt.contains("savedAncientHorn", 10)) {
                nbt.remove("savedAncientHorn");
            }
        }
    }

    public SaveableAncientHorn copyOf() {
        return new SaveableAncientHorn(this.uuid, this.originPos, this.hasEmitted);
    }
}
