/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.snowlogging.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(SectionCompiler.class)
public abstract class SectionCompilerMixin {


	@Shadow
	protected abstract BufferBuilder getOrBeginLayer(Map<RenderType, BufferBuilder> map, SectionBufferBuilderPack sectionBufferBuilderPack, RenderType renderType);

	@Shadow
	@Final
	private BlockRenderDispatcher blockRenderer;

	@Inject(
		method = "compile",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getRenderShape()Lnet/minecraft/world/level/block/RenderShape;",
			shift = At.Shift.BEFORE
		)
	)
	public void wilderWild$compileWithSnowlogging(
		SectionPos sectionPos,
		RenderChunkRegion renderChunkRegion,
		VertexSorting vertexSorting,
		SectionBufferBuilderPack sectionBufferBuilderPack,
		CallbackInfoReturnable<SectionCompiler.Results> info,
		@Local PoseStack poseStack,
		@Local Map<RenderType, BufferBuilder> map,
		@Local RandomSource randomSource,
		@Local(ordinal = 2) BlockPos blockPos3,
		@Local BlockState blockState
	) {
		if (SnowloggingUtils.isSnowlogged(blockState)) {
			BlockState snowState = SnowloggingUtils.getSnowEquivalent(blockState);

			RenderType renderType = ItemBlockRenderTypes.getChunkRenderType(snowState);
			BufferBuilder bufferBuilder = this.getOrBeginLayer(map, sectionBufferBuilderPack, renderType);
			poseStack.pushPose();
			poseStack.translate(
				(float)SectionPos.sectionRelative(blockPos3.getX()),
				(float)SectionPos.sectionRelative(blockPos3.getY()),
				(float)SectionPos.sectionRelative(blockPos3.getZ())
			);
			this.blockRenderer.renderBatched(snowState, blockPos3, renderChunkRegion, poseStack, bufferBuilder, true, randomSource);
			poseStack.popPose();
		}
	}
}
