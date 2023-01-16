package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SeedingDandelionBlock extends FlowerBlock {

    public SeedingDandelionBlock(MobEffect suspiciousStewEffect, int effectDuration, Properties settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

	@Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.95) {
            level.addParticle(new SeedParticleOptions(false, false), pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
        }
    }

	@Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (level instanceof ServerLevel server) {
            if (server.random.nextFloat() > 0.95) {
                EasyPacket.EasySeedPacket.createParticle(level, Vec3.atCenterOf(pos).add(0, 0.3, 0), server.random.nextIntBetweenInclusive(1, 3), false);
            }
        }
    }

    @Override
    public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        super.playerWillDestroy(level, pos, state, player);
        if (level instanceof ServerLevel server) {
            EasyPacket.EasySeedPacket.createParticle(level, Vec3.atCenterOf(pos).add(0, 0.3, 0), server.random.nextIntBetweenInclusive(3, 7), false);
        }
    }
}
