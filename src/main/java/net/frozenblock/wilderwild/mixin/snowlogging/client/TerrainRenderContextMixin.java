/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.snowlogging.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(TerrainRenderContext.class)
public class TerrainRenderContextMixin {


	@Inject(method = "tessellateBlock", at = @At("HEAD"), require = 0)
	public void wilderWild$tessellateBlock(BlockState blockState, BlockPos blockPos, BakedModel model, PoseStack matrixStack, CallbackInfo info) {
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			BlockState snowState = SnowloggingUtils.getSnowEquivalent(blockState);
			this.tessellateBlock(snowState, blockPos, Minecraft.getInstance().getBlockRenderer().getBlockModel(snowState), matrixStack);
		}
	}

	@Shadow
	public void tessellateBlock(BlockState blockState, BlockPos blockPos, BakedModel model, PoseStack matrixStack) {
		throw new AssertionError("Mixin injection failed - Wilder Wild TerrainRenderContextMixin.");
	}
}
