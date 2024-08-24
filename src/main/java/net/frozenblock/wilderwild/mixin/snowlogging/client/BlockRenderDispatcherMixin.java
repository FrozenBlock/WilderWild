/*
 * Copyright 2024 FrozenBlock
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
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderDispatcher.class)
public class BlockRenderDispatcherMixin {

	// Rn this causes a weird visual effect with harder snowloggable blocks like fences.
	// Instead of showing the original getting mined, it shows only the snow getting mined.
	@Inject(method = "renderBreakingTexture", at = @At("HEAD"), cancellable = true)
	public void wilderWild$renderBreakingTexture(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, CallbackInfo info) {
//		if (SnowloggingUtils.isSnowlogged(state)) {
//			this.renderBreakingTexture(SnowloggingUtils.getSnowEquivalent(state), pos, level, poseStack, consumer);
//			info.cancel();
//		}
	}

	@Inject(method = "renderBatched", at = @At("HEAD"))
	public void wilderWild$renderBatched(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, RandomSource random, CallbackInfo ci) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			this.renderBatched(SnowloggingUtils.getSnowEquivalent(state), pos, level, poseStack, consumer, checkSides, random);
		}
	}

	@Shadow
	public void renderBreakingTexture(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BlockRenderDispatcherMixin.");
	}

	@Shadow
	public void renderBatched(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, RandomSource random) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BlockRenderDispatcherMixin.");
	}
}
