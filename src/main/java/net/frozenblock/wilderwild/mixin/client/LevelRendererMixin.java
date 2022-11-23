package net.frozenblock.wilderwild.mixin.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
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
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.ShaderInstance;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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

	@Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
	public void renderClouds(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ, CallbackInfo info) {
		info.cancel();
		float f = this.level.effects().getCloudHeight();
		if (!Float.isNaN(f)) {
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.enableDepthTest();
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			RenderSystem.depthMask(true);

			double cloudX = (camX / 12.0D) - ClientWindManager.getCloudX(partialTick);
			double cloudY = (double)(f - (float)camY + 0.33F) + Mth.clamp(ClientWindManager.getCloudY(partialTick), -10, 10);
			double cloudZ = (camZ / 12.0D + 0.33000001311302185D) - ClientWindManager.getCloudZ(partialTick);

			cloudX -= Mth.floor(cloudX / 2048.0D) * 2048;
			cloudZ -= Mth.floor(cloudZ / 2048.0D) * 2048;
			float l = (float)(cloudX - (double)Mth.floor(cloudX));
			float m = (float)(cloudY / 4.0D - (double)Mth.floor(cloudY / 4.0D)) * 4.0F;
			float n = (float)(cloudZ - (double)Mth.floor(cloudZ));
			Vec3 vec3 = this.level.getCloudColor(partialTick);
			int o = (int)Math.floor(cloudX);
			int p = (int)Math.floor(cloudY / 4.0D);
			int q = (int)Math.floor(cloudZ);
			if (o != this.prevCloudX || p != this.prevCloudY || q != this.prevCloudZ || this.minecraft.options.getCloudsType() != this.prevCloudsType || this.prevCloudColor.distanceToSqr(vec3) > 2.0E-4D) {
				this.prevCloudX = o;
				this.prevCloudY = p;
				this.prevCloudZ = q;
				this.prevCloudColor = vec3;
				this.prevCloudsType = this.minecraft.options.getCloudsType();
				this.generateClouds = true;
			}

			if (this.generateClouds) {
				this.generateClouds = false;
				BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
				if (this.cloudBuffer != null) {
					this.cloudBuffer.close();
				}

				this.cloudBuffer = new VertexBuffer();
				BufferBuilder.RenderedBuffer renderedBuffer = this.buildClouds(bufferBuilder, cloudX, cloudY, cloudZ, vec3);
				this.cloudBuffer.bind();
				this.cloudBuffer.upload(renderedBuffer);
				VertexBuffer.unbind();
			}

			RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
			RenderSystem.setShaderTexture(0, CLOUDS_LOCATION);
			FogRenderer.levelFogColor();
			poseStack.pushPose();
			poseStack.scale(12.0F, 1.0F, 12.0F);
			poseStack.translate(-l, m, -n);
			if (this.cloudBuffer != null) {
				this.cloudBuffer.bind();
				int r = this.prevCloudsType == CloudStatus.FANCY ? 0 : 1;

				for(int s = r; s < 2; ++s) {
					if (s == 0) {
						RenderSystem.colorMask(false, false, false, false);
					} else {
						RenderSystem.colorMask(true, true, true, true);
					}

					ShaderInstance shaderInstance = RenderSystem.getShader();
					this.cloudBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderInstance);
				}

				VertexBuffer.unbind();
			}

			poseStack.popPose();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.enableCull();
			RenderSystem.disableBlend();
		}
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
