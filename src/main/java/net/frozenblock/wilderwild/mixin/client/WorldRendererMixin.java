package net.frozenblock.wilderwild.mixin.client;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    @Shadow
    private @Nullable ClientLevel level;

    @Inject(method = "levelEvent", at = @At("HEAD"), cancellable = true)
    private void levelEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        assert this.level != null;
        RandomSource random = this.level.random;
        switch (eventId) {
            case 3007:
                for (int j = 0; j < 10; ++j) {
                    this.level
                            .addParticle(
                                    new ShriekParticleOption(j * 5), false, (double) pos.getX() + 0.5, (double) pos.getY() + SculkShriekerBlock.TOP_Y, (double) pos.getZ() + 0.5, 0.0, 0.0, 0.0
                            );
                }
                if (this.level.getBlockState(pos).getValue(BlockStateProperties.WATERLOGGED)) {
                    this.level
                            .playLocalSound(
                                    (double) pos.getX() + 0.5,
                                    (double) pos.getY() + SculkShriekerBlock.TOP_Y,
                                    (double) pos.getZ() + 0.5,
                                    RegisterSounds.BLOCK_SCULK_SHRIEKER_GARGLE,
                                    SoundSource.BLOCKS,
                                    2.0F,
                                    0.6F + this.level.random.nextFloat() * 0.4F,
                                    false
                            );
                } else {
                    this.level
                            .playLocalSound(
                                    (double) pos.getX() + 0.5,
                                    (double) pos.getY() + SculkShriekerBlock.TOP_Y,
                                    (double) pos.getZ() + 0.5,
                                    SoundEvents.SCULK_SHRIEKER_SHRIEK,
                                    SoundSource.BLOCKS,
                                    2.0F,
                                    0.6F + this.level.random.nextFloat() * 0.4F,
                                    false
                            );
                }
                ci.cancel();
        }
    }
}
