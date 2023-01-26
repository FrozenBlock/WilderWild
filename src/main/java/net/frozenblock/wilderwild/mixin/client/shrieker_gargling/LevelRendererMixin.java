package net.frozenblock.wilderwild.mixin.client.shrieker_gargling;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Blocks;
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

    @Inject(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0), cancellable = true)
    private void levelEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().shriekerGargling()) {
            assert this.level != null;
            if (this.level.getBlockState(pos).getValue(BlockStateProperties.WATERLOGGED) || this.level.getBlockState(pos.above()).getBlock() == Blocks.WATER || this.level.getFluidState(pos.above()).is(FluidTags.WATER)) {
                this.level
                        .playLocalSound(
                                (double) pos.getX() + 0.5D,
                                (double) pos.getY() + SculkShriekerBlock.TOP_Y,
                                (double) pos.getZ() + 0.5D,
                                RegisterSounds.BLOCK_SCULK_SHRIEKER_GARGLE,
                                SoundSource.BLOCKS,
                                2.0F,
                                0.6F + this.level.random.nextFloat() * 0.4F,
                                false
                        );
                ci.cancel();
            }
        }
    }
}
