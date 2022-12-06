package net.frozenblock.wilderwild.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix4f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = LevelRenderer.class, priority = 1001)
public class LevelRendererMixin {

    @Shadow @Nullable
    private ClientLevel level;
	@Shadow @Final
	private Minecraft minecraft;
	@Shadow @Final
	private RenderBuffers renderBuffers;
	@Shadow @Final
	private EntityRenderDispatcher entityRenderDispatcher;
	@Shadow
	private int ticks;
	@Shadow
	private boolean generateClouds;
	@Shadow @Nullable
	private VertexBuffer cloudBuffer;
	@Shadow
	private int prevCloudX;
	@Shadow
	private int prevCloudY;
	@Shadow
	private int prevCloudZ;
	@Shadow
	private Vec3 prevCloudColor;
	@Shadow @Nullable
	private CloudStatus prevCloudsType;
	@Shadow @Final
	private static ResourceLocation CLOUDS_LOCATION;

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

	@Unique
	float cloudHeight;

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 1)
	private float getCloudHeight(float original) {
		this.cloudHeight = original;
		return original;
	}

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 5)
	private double modifyX(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX) {
		return (camX / 12.0) - ClientWindManager.getCloudX(partialTick);
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 6)
	private double modifyY(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY) {
		return (double)(this.cloudHeight - (float)camY + 0.33F) + Mth.clamp(ClientWindManager.getCloudY(partialTick), -10, 10);
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 7)
	private double modifyZ(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ) {
		return (camZ / 12.0D + 0.33000001311302185D) - ClientWindManager.getCloudZ(partialTick);
	}

	@Shadow
	private BufferBuilder.RenderedBuffer buildClouds(BufferBuilder builder, double x, double y, double z, Vec3 cloudColor) {
		throw new AssertionError("Mixin injection failed - WIlderWild LevelRendererMixin");
	}

    /*@Shadow
    private void renderChunkLayer(RenderType renderType, PoseStack poseStack, double d, double e, double f, Matrix4f matrix4f) {
    }

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private double xTransparentOld;

    @Shadow
    private double yTransparentOld;

    @Shadow
    private double zTransparentOld;

    @Shadow
    @Final
    private ObjectArrayList<LevelRenderer.RenderChunkInfo> renderChunksInFrustum;

    @Shadow
    private @Nullable ChunkRenderDispatcher chunkRenderDispatcher;

    @Unique
    private final RenderType renderType = null;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderChunkLayer(Lnet/minecraft/client/renderer/RenderType;Lcom/mojang/blaze3d/vertex/PoseStack;DDDLcom/mojang/math/Matrix4f;)V", ordinal = 3, shift = At.Shift.AFTER))
    private void renderLevel(PoseStack poseStack, float f, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {
        assert this.level != null;
        ProfilerFiller profilerFiller = this.level.getProfiler();
        Vec3 vec3 = camera.getPosition();
        double x = vec3.x();
        double y = vec3.y();
        double z = vec3.z();
        profilerFiller.popPush("translucent_cutout_wilderwild");
        this.renderChunkLayer(WilderWildClient.translucentCutout(), poseStack, x, y, z, matrix4f);
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderChunkLayer(Lnet/minecraft/client/renderer/RenderType;Lcom/mojang/blaze3d/vertex/PoseStack;DDDLcom/mojang/math/Matrix4f;)V", ordinal = 5))
    private void renderLevelOther(PoseStack poseStack, float f, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {
        assert this.level != null;
        ProfilerFiller profilerFiller = this.level.getProfiler();
        Vec3 vec3 = camera.getPosition();
        double x = vec3.x();
        double y = vec3.y();
        double z = vec3.z();
        profilerFiller.popPush("translucent_cutout_wilderwild");
        this.renderChunkLayer(WilderWildClient.translucentCutout(), poseStack, x, y, z, matrix4f);
    }

    @Inject(method = "renderChunkLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;setupRenderState()V", shift = At.Shift.AFTER))
    private void renderChunkLayer(RenderType renderType, PoseStack poseStack, double d, double e, double f, Matrix4f matrix4f, CallbackInfo ci) {
        this.renderType = renderType;
        if (renderType == WilderWildClient.translucentCutout()) {
            this.minecraft.getProfiler().push("translucent_sort");
            double g = d - this.xTransparentOld;
            double h = e - this.yTransparentOld;
            double i = f - this.zTransparentOld;
            if (g * g + h * h + i * i > 1.0) {
                this.xTransparentOld = d;
                this.yTransparentOld = e;
                this.zTransparentOld = f;
                int j = 0;

                for (LevelRenderer.RenderChunkInfo renderChunkInfo : this.renderChunksInFrustum) {
                    if (j < 15) {
                        assert this.chunkRenderDispatcher != null;
                        if (renderChunkInfo.chunk.resortTransparency(renderType, this.chunkRenderDispatcher)) {
                            ++j;
                        }
                    }
                }
            }

            this.minecraft.getProfiler().pop();
        }

        boolean bl = renderType != RenderType.translucent();
        ObjectListIterator<LevelRenderer.RenderChunkInfo> objectListIterator = this.renderChunksInFrustum.listIterator(bl ? 0 : this.renderChunksInFrustum.size());
        var shaderInstance = RenderSystem.getShader();
        assert shaderInstance != null;
        Uniform uniform = shaderInstance.CHUNK_OFFSET;

        while (true) {
            if (bl) {
                if (!objectListIterator.hasNext()) {
                    break;
                }
            } else if (!objectListIterator.hasPrevious()) {
                break;
            }

            LevelRenderer.RenderChunkInfo renderChunkInfo2 = bl ? objectListIterator.next() : objectListIterator.previous();
            ChunkRenderDispatcher.RenderChunk renderChunk = renderChunkInfo2.chunk;
            if (!renderChunk.getCompiledChunk().isEmpty(renderType)) {
                VertexBuffer vertexBuffer = renderChunk.getBuffer(renderType);
                BlockPos blockPos = renderChunk.getOrigin();
                if (uniform != null) {
                    uniform.set((float) ((double) blockPos.getX() - d), (float) ((double) blockPos.getY() - e), (float) ((double) blockPos.getZ() - f));
                    uniform.upload();
                }

                vertexBuffer.bind();
                vertexBuffer.draw();
            }
        }
    }
*/
}
