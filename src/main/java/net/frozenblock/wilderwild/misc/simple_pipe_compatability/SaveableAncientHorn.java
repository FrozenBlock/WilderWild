package net.frozenblock.wilderwild.misc.simple_pipe_compatability;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.lunade.copper.pipe_nbt.MoveablePipeDataHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
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

import java.util.List;

public class SaveableAncientHorn extends MoveablePipeDataHandler.SaveableMovablePipeNbt {

    public SaveableAncientHorn(Vec3d originPos, String uuid, boolean hasEmittedParticle) {
        super(RegisterSaveableMoveablePipeNbt.horn, originPos, uuid, hasEmittedParticle, new BlockPos(2,2,2));
        this.originPos = originPos;
        this.uuid = uuid;
        this.hasEmittedParticle = hasEmittedParticle;
        this.setNbtId(RegisterSaveableMoveablePipeNbt.horn);
    }

    public void dispense(World world, BlockPos pos) {
        if (!this.hasEmittedParticle) {
            BlockState state = world.getBlockState(pos);
            Identifier id = Registry.BLOCK.getId(state.getBlock());
            if (id.getNamespace().equals("lunade") && id.getPath().contains("pipe")) {
                Direction direction = state.get(Properties.FACING);
                if (world instanceof ServerWorld server && this.getEntity(world) != null) {
                    this.hasEmittedParticle = true;
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

}
