package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.World;

public class WhiteDandelionBlock extends FlowerBlock {
    public WhiteDandelionBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, AbstractRandom random) {
        if (random.nextFloat()>0.95) {
            world.addParticle(RegisterParticles.DANDELION_SEED, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
        }
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld server) {
            if (server.random.nextFloat()>0.95) {
                PollenParticle.EasyDandelionSeedPacket.createParticle(world, Vec3d.ofCenter(pos).add(0,0.3,0), server.random.nextBetween(1,3));
            }
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (world instanceof ServerWorld server) {
            PollenParticle.EasyDandelionSeedPacket.createParticle(world, Vec3d.ofCenter(pos).add(0, 0.3, 0), server.random.nextBetween(3, 14));
        }
    }
}
