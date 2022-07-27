package net.frozenblock.wilderwild.misc.simple_pipe_compatability;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.lunade.copper.FittingPipeDispenses;
import net.lunade.copper.RegisterPipeNbtMethods;
import net.lunade.copper.blocks.CopperPipe;
import net.minecraft.block.BlockState;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;

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
        }, (nbt, world, pos, blockState, blockEntity) -> true);

        FittingPipeDispenses.register(RegisterItems.MILKWEED_POD, (world, stack, i, direction, position, state, corroded, pos, pipe) -> {
            double d = position.getX();
            double e = position.getY();
            double f = position.getZ();
            Random random = world.random;
            double random1 = random.nextDouble() * 7.0D - 3.5D;
            double random2 = random.nextDouble() * 7.0D - 3.5D;
            Direction.Axis axis = direction.getAxis();
            if (axis == Direction.Axis.Y) {
                e -= 0.125D;
            } else {
                e -= 0.15625D;
            }

            int offX = direction.getOffsetX();
            int offY = direction.getOffsetY();
            int offZ = direction.getOffsetZ();
            double velX = axis == Direction.Axis.X ? (double)(i * offX * 2) : (axis == Direction.Axis.Z ? (corroded ? random2 : random2 * 0.1D) : (corroded ? random1 : random1 * 0.1D));
            double velY = axis == Direction.Axis.Y ? (double)(i * offY * 2) : (corroded ? random1 : random1 * 0.1D);
            double velZ = axis == Direction.Axis.Z ? (double)(i * offZ * 2) : (corroded ? random2 : random2 * 0.1D);
            UniformIntProvider ran1 = UniformIntProvider.create(-3, 3);
            UniformIntProvider ran2 = UniformIntProvider.create(-1, 1);
            UniformIntProvider ran3 = UniformIntProvider.create(-3, 3);
            for(int o = 0; o < random.nextBetween(10, 30); ++o) {
                EasyPacket.EasySeedPacket.createControlledParticle(world, new Vec3d(d + (double)ran1.get(world.random) * 0.1D, e + (double)ran2.get(world.random) * 0.1D, f + (double)ran3.get(world.random) * 0.1D), velX, velY, velZ, 1, true, 64);
            }
        });

        FittingPipeDispenses.register(RegisterBlocks.SEEDING_DANDELION.asItem(), (world, stack, i, direction, position, state, corroded, pos, pipe) -> {
            double d = position.getX();
            double e = position.getY();
            double f = position.getZ();
            Random random = world.random;
            double random1 = random.nextDouble() * 7.0D - 3.5D;
            double random2 = random.nextDouble() * 7.0D - 3.5D;
            Direction.Axis axis = direction.getAxis();
            if (axis == Direction.Axis.Y) {
                e -= 0.125D;
            } else {
                e -= 0.15625D;
            }

            int offX = direction.getOffsetX();
            int offY = direction.getOffsetY();
            int offZ = direction.getOffsetZ();
            double velX = axis == Direction.Axis.X ? (double)(i * offX * 2) : (axis == Direction.Axis.Z ? (corroded ? random2 : random2 * 0.1D) : (corroded ? random1 : random1 * 0.1D));
            double velY = axis == Direction.Axis.Y ? (double)(i * offY * 2) : (corroded ? random1 : random1 * 0.1D);
            double velZ = axis == Direction.Axis.Z ? (double)(i * offZ * 2) : (corroded ? random2 : random2 * 0.1D);
            UniformIntProvider ran1 = UniformIntProvider.create(-3, 3);
            UniformIntProvider ran2 = UniformIntProvider.create(-1, 1);
            UniformIntProvider ran3 = UniformIntProvider.create(-3, 3);
            for(int o = 0; o < random.nextBetween(1, 10); ++o) {
                EasyPacket.EasySeedPacket.createControlledParticle(world, new Vec3d(d + (double)ran1.get(world.random) * 0.1D, e + (double)ran2.get(world.random) * 0.1D, f + (double)ran3.get(world.random) * 0.1D), velX, velY, velZ, 1, false, 64);
            }
        });

        FittingPipeDispenses.register(RegisterItems.ANCIENT_HORN, (world, stack, i, direction, position, state, corroded, pos, pipe) -> {
            if (!world.isClient) {
                double d = position.getX();
                double e = position.getY();
                double f = position.getZ();
                Random random = world.random;
                double random1 = random.nextDouble() * 7.0D - 3.5D;
                Direction.Axis axis = direction.getAxis();
                if (axis == Direction.Axis.Y) {
                    e -= 0.125D;
                } else {
                    e -= 0.15625D;
                }

                int offY = direction.getOffsetY();
                double velY = axis == Direction.Axis.Y ? (double) (i * offY * 2) : (corroded ? random1 : random1 * 0.1D);
                UniformIntProvider ran1 = UniformIntProvider.create(-3, 3);
                UniformIntProvider ran2 = UniformIntProvider.create(-1, 1);
                UniformIntProvider ran3 = UniformIntProvider.create(-3, 3);
                for (int o = 0; o < random.nextBetween(1, 4); ++o) {
                    EasyPacket.EasyFloatingSculkBubblePacket.createParticle(world, new Vec3d(d + (double) ran1.get(world.random) * 0.1D, e + (double) ran2.get(world.random) * 0.1D, f + (double) ran3.get(world.random) * 0.1D), Math.random() > 0.7 ? 1 : 0, random.nextBetween(60, 80), velY * 0.05, 1);
                }
            }
        });
        
    }

}
