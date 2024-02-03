package net.frozenblock.wilderwild.mixin.snowlogging.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TerrainRenderContext.class)
public class TerrainRenderContextMixin {

	@Inject(method = "tessellateBlock", at = @At("HEAD"), cancellable = true)
	public void wilderWild$tessellateBlock(BlockState blockState, BlockPos blockPos, BakedModel model, PoseStack matrixStack, CallbackInfo info) {
		if (blockState.hasProperty(RegisterProperties.SNOWLOGGED) && blockState.getValue(RegisterProperties.SNOWLOGGED)) {
			BlockState snowState = Blocks.SNOW.defaultBlockState();
			this.tessellateBlock(snowState, blockPos, Minecraft.getInstance().getBlockRenderer().getBlockModel(snowState), matrixStack);
		}
	}

	@Shadow
	public void tessellateBlock(BlockState blockState, BlockPos blockPos, BakedModel model, PoseStack matrixStack) {
		throw new AssertionError("Mixin injection failed - Wilder Wild TerrainRenderContextMixin.");
	}
}
