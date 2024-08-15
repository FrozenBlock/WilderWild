package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {

	@Inject(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;shouldRenderFace(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z",
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		require = 0
	)
	private void wilderWild$isMesoglea(
		BlockAndTintGetter world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState state, CallbackInfo info,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		isMesoglea.set(blockState.getBlock() instanceof MesogleaBlock);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;shouldRenderFace(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z"
		),
		require = 0
	)
	private boolean wilderWild$isMesoglea(
		BlockAndTintGetter world, BlockPos pos, FluidState state, BlockState blockState, Direction direction, FluidState adjacentFluidState, Operation<Boolean> original,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (isMesoglea.get() && direction != Direction.UP) {
			return false;
		}
		return original.call(world, pos, state, blockState, direction, adjacentFluidState);
	}

}
