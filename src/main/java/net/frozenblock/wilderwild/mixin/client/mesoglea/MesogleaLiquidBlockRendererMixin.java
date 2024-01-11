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

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.liquid.render.api.LiquidRenderUtils;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.client.Minecraft;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = LiquidBlockRenderer.class, priority = 69420)
public class MesogleaLiquidBlockRendererMixin {

	@Inject(method = "shouldRenderFace", at = @At(value = "HEAD"), cancellable = true, require = 0)
	private static void wilderWild$shouldRenderFace(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, FluidState fluidState, BlockState blockState, Direction side, FluidState fluidState2, CallbackInfoReturnable<Boolean> info) {
		if (blockState.getBlock() instanceof MesogleaBlock && side != Direction.UP) {
			info.setReturnValue(false);
		}
	}

	@Inject(method = "tesselate", at = @At("HEAD"), cancellable = true, require = 0)
	private void wilderWild$getIsWater(BlockAndTintGetter level, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState, CallbackInfo info) {
		if (BlockConfig.get().mesoglea.mesogleaLiquid && blockState.getBlock() instanceof MesogleaBlock) {
			LiquidRenderUtils.tesselateWithSingleTexture(level, pos, vertexConsumer, blockState, fluidState, Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(blockState).getParticleIcon());
			info.cancel();
		}
	}

}
