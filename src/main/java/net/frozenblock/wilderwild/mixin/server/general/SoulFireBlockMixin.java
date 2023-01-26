package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoulFireBlock.class)
public class SoulFireBlockMixin extends BaseFireBlock {

    private SoulFireBlockMixin(Properties properties, float fireDamage) {
        super(properties, fireDamage);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().soulFireSounds()) {
            if (random.nextInt(48) == 0) {
                level.playLocalSound(
                        (double) pos.getX() + 0.5,
                        (double) pos.getY() + 0.5,
                        (double) pos.getZ() + 0.5,
                        RegisterSounds.BLOCK_SOUL_FIRE_AMBIENT,
                        SoundSource.BLOCKS,
                        0.6F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false
                );
            }
        } else {
            super.animateTick(state, level, pos, random);
        }
    }

    @Shadow
    protected boolean canBurn(@NotNull BlockState state) {
		throw new AssertionError("Mixin injection failed - WilderWild SoulFireBlockMixin.");
    }

}
