package net.frozenblock.wilderwild.mixin.client;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ShriekParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow
    private @Nullable ClientWorld world;

    @Inject(method = "processWorldEvent", at = @At("HEAD"), cancellable = true)
    private void processWorldEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        assert this.world != null;
        Random random = this.world.random;
        if (eventId == 3007) {
            for (int j = 0; j < 10; ++j) {
                this.world
                        .addParticle(
                                new ShriekParticleEffect(j * 5), false, (double) pos.getX() + 0.5, (double) pos.getY() + SculkShriekerBlock.TOP, (double) pos.getZ() + 0.5, 0.0, 0.0, 0.0
                        );
            }
            if (this.world.getBlockState(pos).get(Properties.WATERLOGGED) || this.world.getBlockState(pos.up()).getBlock() == Blocks.WATER) {
                this.world
                        .playSound(
                                (double) pos.getX() + 0.5,
                                (double) pos.getY() + SculkShriekerBlock.TOP,
                                (double) pos.getZ() + 0.5,
                                RegisterSounds.BLOCK_SCULK_SHRIEKER_GARGLE,
                                SoundCategory.BLOCKS,
                                2.0F,
                                0.6F + this.world.random.nextFloat() * 0.4F,
                                false
                        );
            } else {
                this.world
                        .playSound(
                                (double) pos.getX() + 0.5,
                                (double) pos.getY() + SculkShriekerBlock.TOP,
                                (double) pos.getZ() + 0.5,
                                SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK,
                                SoundCategory.BLOCKS,
                                2.0F,
                                0.6F + this.world.random.nextFloat() * 0.4F,
                                false
                        );
            }
            ci.cancel();
        }
    }
}
