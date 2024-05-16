/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.embeddium;

import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Pseudo
@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {

	@Unique
	private final TextureAtlasSprite wilderWild$waterOverlay = ModelBakery.WATER_OVERLAY.sprite();
	@Unique
	private float wilderWild$u0;
	@Unique
	private float wilderWild$u1;
	@Unique
	private float wilderWild$v0;
	@Unique
	private float wilderWild$v1;
	@Unique
	private boolean wilderWild$isWater;


	@Inject(method = "isFluidOccluded", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(III)Lnet/minecraft/core/BlockPos$MutableBlockPos;", ordinal = 1), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT, require = 0)
	private void wilderWild$isFluidOccluded(BlockAndTintGetter world, int x, int y, int z, Direction dir, Fluid fluid, CallbackInfoReturnable<Boolean> info, BlockPos pos, BlockState blockState) {
		if (blockState.getBlock() instanceof MesogleaBlock && dir != Direction.UP) {
			info.setReturnValue(true);
		}
	}

	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true, require = 0)
	private void wilderWild$getIsWater(WorldSlice world, FluidState fluidState, BlockPos pos, BlockPos offset, ChunkBuildBuffers buffers, CallbackInfo info) {
		this.wilderWild$isWater = fluidState.is(FluidTags.WATER);
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;setSprite(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V", ordinal = 1), require = 0, remap = false)
	private void wilderWild$switchSprites(Args args) {
		if (this.wilderWild$isWater) {
			args.set(0, this.wilderWild$waterOverlay);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 4), require = 0, remap = false)
	private void wilderWild$sideTextureBottom1(Args args) {
		if (this.wilderWild$isWater) {
			this.wilderWild$u0 = this.wilderWild$waterOverlay.getU0();
			this.wilderWild$u1 = this.wilderWild$waterOverlay.getU1();
			this.wilderWild$v0 = this.wilderWild$waterOverlay.getV0();
			this.wilderWild$v1 = this.wilderWild$waterOverlay.getV1();
			args.set(5, this.wilderWild$u0);
			args.set(6, this.wilderWild$v1);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 5), require = 0, remap = false)
	private void wilderWild$sideTextureBottom2(Args args) {
		if (this.wilderWild$isWater) {
			args.set(5, this.wilderWild$u0);
			args.set(6, this.wilderWild$v0);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 6), require = 0, remap = false)
	private void wilderWild$sideTextureBottom3(Args args) {
		if (this.wilderWild$isWater) {
			args.set(5, this.wilderWild$u1);
			args.set(6, this.wilderWild$v0);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 7), require = 0, remap = false)
	private void wilderWild$sideTextureBottom4(Args args) {
		if (this.wilderWild$isWater) {
			args.set(5, this.wilderWild$u1);
			args.set(6, this.wilderWild$v1);
		}
	}
}
