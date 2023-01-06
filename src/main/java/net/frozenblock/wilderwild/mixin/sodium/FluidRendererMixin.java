package net.frozenblock.wilderwild.mixin.sodium;

import me.jellysquid.mods.sodium.client.model.quad.ModelQuadViewMutable;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.pipeline.FluidRenderer;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

	@Unique private final TextureAtlasSprite waterOverlay = ModelBakery.WATER_OVERLAY.sprite();

	@Unique
	private float wilderWild$u0;
	@Unique
	private float wilderWild$u1;
	@Unique
	private float wilderWild$v0;
	@Unique
	private float wilderWild$v1;
	@Unique
	private boolean isWater;

	@Inject(method = "isFluidOccluded", at = @At(value = "HEAD"), cancellable = true)
	private void isFluidOccluded(BlockAndTintGetter world, int x, int y, int z, Direction dir, Fluid fluid, CallbackInfoReturnable<Boolean> info) {
		if (world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof MesogleaBlock && dir != Direction.UP) {
			info.setReturnValue(true);
		}
	}

	@Inject(method = "render", at = @At(value = "HEAD"))
	private void getIsWater(BlockAndTintGetter world, FluidState fluidState, BlockPos pos, BlockPos offset, ChunkModelBuilder buffers, CallbackInfoReturnable<Boolean> info) {
		this.isWater = fluidState.getType().is(FluidTags.WATER);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;setSprite(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V", ordinal = 1))
	private void switchSprites(ModelQuadViewMutable quad, TextureAtlasSprite sprite) {
		if (this.isWater) {
			quad.setSprite(this.waterOverlay);
		} else {
			quad.setSprite(sprite);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 4))
	private void sideTextureBottom1(Args args) {
		if (this.isWater) {
			this.wilderWild$u0 = this.waterOverlay.getU0();
			this.wilderWild$u1 = this.waterOverlay.getU1();
			this.wilderWild$v0 = this.waterOverlay.getV0();
			this.wilderWild$v1 = this.waterOverlay.getV1();
			args.set(5, this.wilderWild$u0);
			args.set(6, this.wilderWild$v1);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 5))
	private void sideTextureBottom2(Args args) {
		if (this.isWater) {
			args.set(5, this.wilderWild$u0);
			args.set(6, this.wilderWild$v0);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 6))
	private void sideTextureBottom3(Args args) {
		if (this.isWater) {
			args.set(5, this.wilderWild$u1);
			args.set(6, this.wilderWild$v0);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 7))
	private void sideTextureBottom4(Args args) {
		if (this.isWater) {
			args.set(5, this.wilderWild$u1);
			args.set(6, this.wilderWild$v1);
		}
	}

}
