package net.frozenblock.wilderwild.mixin.client.embeddium;

import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderer.class)
public abstract class BlockRendererMixin {

	@Unique
	private static final BlockModelShaper frozenLib$blockModelShaper = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();

	@Shadow
	public abstract void renderModel(BlockRenderContext ctx, ChunkBuildBuffers buffers);

	@Inject(method = "renderModel", at = @At("HEAD"), remap = false)
	public void wilderWild$renderModel(BlockRenderContext ctx, ChunkBuildBuffers buffers, CallbackInfo info) {
		if (SnowloggingUtils.isSnowlogged(ctx.state())) {
			BlockState snowState = SnowloggingUtils.getSnowEquivalent(ctx.state());
			BlockRenderContext snowRenderContext = new BlockRenderContext(ctx.world());
			Vector3fc origin = ctx.origin();
			snowRenderContext.update(
				ctx.pos(),
				new BlockPos((int) origin.x(), (int) origin.y(), (int) origin.z()),
				snowState,
				frozenLib$blockModelShaper.getBlockModel(snowState),
				ctx.seed(),
				ctx.renderLayer()
			);
			this.renderModel(snowRenderContext, buffers);
		}
	}

}
