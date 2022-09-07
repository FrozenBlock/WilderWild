package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SoulFireBlock.class)
public class SoulFireBlockMixin {
    public void tick(Level level, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(24) == 0) {
            level.playLocalSound((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, RegisterSounds.BLOCK_SOUL_FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.3F, false);
        }
    }
}
