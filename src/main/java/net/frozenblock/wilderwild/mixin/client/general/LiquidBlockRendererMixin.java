package net.frozenblock.wilderwild.mixin.client.general;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
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

	@Unique
	private boolean isWater;

	@Redirect(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
	private boolean getIsWater(FluidState par1, TagKey<Fluid> lava, BlockAndTintGetter level, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
		this.isWater = par1.is(FluidTags.WATER);
		return par1.is(FluidTags.LAVA);
	}

	@ModifyArgs(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFI)V", ordinal = 8))
	private void sideTextureBottom1(Args args) {
		if (this.isWater) {
			this.wilderWild$u0 = this.waterOverlay.getU0();
			this.wilderWild$u1 = this.waterOverlay.getU1();
			this.wilderWild$v0 = this.waterOverlay.getV0();
			this.wilderWild$v1 = this.waterOverlay.getV1();
			args.set(7, this.wilderWild$u0);
			args.set(8, this.wilderWild$v1);
		}
	}

	@ModifyArgs(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFI)V", ordinal = 9))
	private void sideTextureBottom2(Args args) {
		if (this.isWater) {
			args.set(7, this.wilderWild$u0);
			args.set(8, this.wilderWild$v0);
		}
	}

	@ModifyArgs(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFI)V", ordinal = 10))
	private void sideTextureBottom3(Args args) {
		if (this.isWater) {
			args.set(7, this.wilderWild$u1);
			args.set(8, this.wilderWild$v0);
		}
	}

	@ModifyArgs(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFI)V", ordinal = 11))
	private void sideTextureBottom4(Args args) {
		if (this.isWater) {
			args.set(7, this.wilderWild$u1);
			args.set(8, this.wilderWild$v1);
		}
	}
}
