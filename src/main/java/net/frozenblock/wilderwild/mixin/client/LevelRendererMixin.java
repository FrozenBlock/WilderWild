package net.frozenblock.wilderwild.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
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

    @Shadow
    private void renderChunkLayer(RenderType renderType, PoseStack poseStack, double d, double e, double f, Matrix4f matrix4f) {

    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderChunkLayer(Lnet/minecraft/client/renderer/RenderType;Lcom/mojang/blaze3d/vertex/PoseStack;DDDLcom/mojang/math/Matrix4f;)V", ordinal = 3, shift = At.Shift.AFTER))
    private void renderLevel(PoseStack poseStack, float f, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {
        assert this.level != null;
        ProfilerFiller profilerFiller = this.level.getProfiler();
        Vec3 vec3 = camera.getPosition();
        double d = vec3.x();
        double e = vec3.y();
        double g = vec3.z();
        profilerFiller.popPush("translucent_cutout_wilderwild");
        this.renderChunkLayer(WilderWildClient.translucentCutout(), poseStack, d, e, g, matrix4f);
    }

    @Inject(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0), cancellable = true)
    private void levelEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (ClothConfigInteractionHandler.shriekerGargling()) {
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
