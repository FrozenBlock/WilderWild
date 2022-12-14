package net.frozenblock.wilderwild.mixin.client.general;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(value = LiquidBlockRenderer.class, priority = 69420)
public class LiquidBlockRendererMixin {

	@Shadow
	private TextureAtlasSprite waterOverlay;

	@Unique
	private float wilderWild$u0;
	@Unique
	private float wilderWild$u1;
	@Unique
	private float wilderWild$v0;
	@Unique
	private float wilderWild$v1;

    @Inject(method = "shouldRenderFace", at = @At(value = "HEAD"), cancellable = true)
    private static void shouldRenderFace(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, FluidState fluidState, BlockState blockState, Direction side, FluidState fluidState2, CallbackInfoReturnable<Boolean> info) {
        if (blockState.getBlock() instanceof MesogleaBlock && side != Direction.UP && !FrozenBools.HAS_SODIUM) {
            info.setReturnValue(false);
        }
    }

	/*@ModifyVariable(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getU0()F"), ordinal = 1)
	private float sideTextureBottom1(float original) {
		this.wilderWild$u0 = this.waterOverlay.getU0();
		this.wilderWild$u1 = this.waterOverlay.getU1();
		this.wilderWild$v0 = this.waterOverlay.getV0();
		this.wilderWild$v1 = this.waterOverlay.getV1();
		return this.wilderWild$u0;
	}

	@ModifyVariable(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getV0()F"), ordinal = 1)
	private float sideTextureBottom2(float original) {
		return this.wilderWild$v0;
	}

	@ModifyVariable(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getU1()F"), ordinal = 1)
	private float sideTextureBottom3(float original) {
		return this.wilderWild$u1;
	}

	@ModifyVariable(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getV1()F"), ordinal = 1)
	private float sideTextureBottom4(float original) {
		return this.wilderWild$v1;
	}*/

	@ModifyArgs(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFI)V", ordinal = 8))
	private void sideTextureBottom1(Args args) {
		this.wilderWild$u0 = this.waterOverlay.getU0();
		this.wilderWild$u1 = this.waterOverlay.getU1();
		this.wilderWild$v0 = this.waterOverlay.getV0();
		this.wilderWild$v1 = this.waterOverlay.getV1();
		args.set(7, this.wilderWild$u0);
		args.set(8, this.wilderWild$v1);
	}

	@ModifyArgs(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFI)V", ordinal = 9))
	private void sideTextureBottom2(Args args) {
		args.set(7, this.wilderWild$u0);
		args.set(8, this.wilderWild$v0);
	}

	@ModifyArgs(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFI)V", ordinal = 10))
	private void sideTextureBottom3(Args args) {
		args.set(7, this.wilderWild$u1);
		args.set(8, this.wilderWild$v0);
	}

	@ModifyArgs(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFI)V", ordinal = 11))
	private void sideTextureBottom4(Args args) {
		args.set(7, this.wilderWild$u1);
		args.set(8, this.wilderWild$v1);
	}
}
