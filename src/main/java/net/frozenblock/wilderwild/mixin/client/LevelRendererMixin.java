package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.config.ModMenuInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    private @Nullable ClientLevel level;

    @Inject(method = "globalLevelEvent", at = @At("HEAD"), cancellable = true)
    private void globalLevelEvent(int eventId, BlockPos blockPos, int data, CallbackInfo ci) {
        if (eventId == 3007 && ModMenuInteractionHandler.shriekerGargling()) {
            assert this.level != null;
            if (this.level.getBlockState(blockPos).getValue(BlockStateProperties.WATERLOGGED)) {
                this.level
                        .playLocalSound(
                                (double) blockPos.getX() + 0.5D,
                                (double) blockPos.getY() + SculkShriekerBlock.TOP_Y,
                                (double) blockPos.getZ() + 0.5D,
                                RegisterSounds.BLOCK_SCULK_SHRIEKER_GARGLE,
                                SoundSource.BLOCKS,
                                2.0F,
                                0.6F + this.level.random.nextFloat() * 0.4F,
                                false
                        );
                for (int l = 0; l < 10; ++l) {
                    this.level
                            .addParticle(
                                    new ShriekParticleOption(l * 5), false, (double) blockPos.getX() + 0.5, (double) blockPos.getY() + SculkShriekerBlock.TOP_Y, (double) blockPos.getZ() + 0.5, 0.0, 0.0, 0.0
                            );
                }
                ci.cancel();
            }
        }
    }
}
