/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.snowlogging.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BlockRenderDispatcher.class)
public abstract class BlockRenderDispatcherMixin {

	@Shadow
	public abstract void renderBreakingTexture(BlockState state, BlockPos pos, PoseStack poseStack, MultiBufferSource bufferSource, int progress);

	@Inject(method = "renderBreakingTexture", at = @At("HEAD"), cancellable = true)
	public void wilderWild$renderSnowloggedBreakingTexture(
		BlockState state,
		BlockPos pos,
		PoseStack poseStack,
		MultiBufferSource bufferSource,
		int progress,
		CallbackInfo info
	) {
		if (!SnowloggingUtils.isSnowlogged(state)) return;
		this.renderBreakingTexture(SnowloggingUtils.getSnowEquivalent(state), pos, poseStack, bufferSource, progress);
		info.cancel();
	}

}
