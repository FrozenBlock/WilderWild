/*
 * Copyright 2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.client.liquid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = LiquidBlockRenderer.class, priority = 69420)
public class LiquidBlockRendererMixin {

	@Shadow
	private TextureAtlasSprite waterOverlay;

	@Inject(method = "tesselate", at = @At("HEAD"), require = 0)
	private void wilderWild$getIsWater(BlockAndTintGetter level, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState, CallbackInfo info, @Share("wilderWild$isWater") LocalBooleanRef isWater) {
		isWater.set(fluidState.is(FluidTags.WATER));
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getU0()F", ordinal = 0),
		require = 0
	)
	private float wilderWild$setBottomU0(TextureAtlasSprite instance, Operation<Float> operation, @Share("wilderWild$isWater") LocalBooleanRef isWater) {
		return operation.call(isWater.get() ? this.waterOverlay : instance);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getU1()F", ordinal = 0),
		require = 0
	)
	private float wilderWild$setBottomU1(TextureAtlasSprite instance, Operation<Float> operation, @Share("wilderWild$isWater") LocalBooleanRef isWater) {
		return operation.call(isWater.get() ? this.waterOverlay : instance);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getV0()F", ordinal = 0),
		require = 0
	)
	private float wilderWild$setBottomV0(TextureAtlasSprite instance, Operation<Float> operation, @Share("wilderWild$isWater") LocalBooleanRef isWater) {
		return operation.call(isWater.get() ? this.waterOverlay : instance);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getV1()F", ordinal = 0),
		require = 0
	)
	private float wilderWild$setBottomV1(TextureAtlasSprite instance, Operation<Float> operation, @Share("wilderWild$isWater") LocalBooleanRef isWater) {
		return operation.call(isWater.get() ? this.waterOverlay : instance);
	}

}
