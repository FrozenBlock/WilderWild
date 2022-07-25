package net.frozenblock.wilderwild.misc.simple_pipe_compatability;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.lunade.copper.RegisterPipeNbtMethods;
import net.lunade.copper.blocks.CopperPipe;
import net.minecraft.block.BlockState;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class RegisterSaveableMoveablePipeNbt {
    public static final Identifier horn = new Identifier(WilderWild.MOD_ID, "ancient_horn");

    public static void init() {
        WilderWild.log("Registering A Saveable Moveable Simple Copper Pipe NBT Dispense Method For WilderWild!", true);
        RegisterPipeNbtMethods.register(horn, (nbt, world, pos, blockState, copperPipeEntity) -> {
            if (!nbt.getCanOnlyBeUsedOnce() || nbt.getUseCount() < 1) {
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() instanceof CopperPipe pipe) {
                    Direction direction = state.get(Properties.FACING);
                    if (nbt.getEntity(world) != null) {
                        nbt.withUseCount(nbt.getUseCount() + 1);
                        AncientHornProjectile projectileEntity = new AncientHornProjectile(world, pos.getX() + pipe.getDripX(direction), pos.getY() + pipe.getDripY(direction), pos.getZ() + pipe.getDripZ(direction));
                        projectileEntity.setVelocity(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ(), 1.0F, 0.0F);
                        projectileEntity.setOwner(nbt.foundEntity);
                        projectileEntity.shotByPlayer = true;
                        world.spawnEntity(projectileEntity);
                        EasyPacket.createMovingLoopingSound(world, projectileEntity, RegisterSounds.ANCIENT_HORN_PROJECTILE_LOOP, SoundCategory.NEUTRAL, 1.0F, 1.0F, WilderWild.id("default"));
                    }
                }
            }
        }, (nbt, world, pos, blockState, blockEntity) -> {

        }, (nbt, world, pos, blockState, blockEntity) -> {
            if (nbt.foundEntity != null) {
                nbt.vec3d2 = nbt.foundEntity.getPos();
            }
            if (blockState.getBlock() instanceof CopperPipe pipe) {
                Direction direction = blockState.get(Properties.FACING);
                for (int i = 0; i < world.getRandom().nextBetween(10, 20); i++) {
                    world.spawnParticles(new DustColorTransitionParticleEffect(DustColorTransitionParticleEffect.SCULK_BLUE, DustColorTransitionParticleEffect.SCULK_BLUE, 1.0f), pos.getX() + pipe.getDripX(direction, world.getRandom()), pos.getY() + pipe.getDripY(direction, world.getRandom()), pos.getZ() + pipe.getDripZ(direction, world.getRandom()), 1, 0.0, 0.0, 0.0, 0.7);
                }
            }
        });
    }

}
