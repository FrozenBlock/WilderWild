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

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.block.BakedQuadOutput;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
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

@Environment(EnvType.CLIENT)
@Mixin(SectionCompiler.class)
public abstract class SectionCompilerMixin {

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
		RenderSectionRegion renderSectionRegion,
		VertexSorting vertexSorting,
		SectionBufferBuilderPack sectionBufferBuilderPack,
		CallbackInfoReturnable<SectionCompiler.Results> info,
		@Local(name = "poseStack") PoseStack poseStack,
		@Local(name = "quadOutput") BakedQuadOutput quadOutput,
		@Local(name = "opaqueQuadOutput") BakedQuadOutput opaqueQuadOutput,
		@Local(name = "random") RandomSource random,
		@Local(name = "parts") List<BlockModelPart> parts,
		@Local(name = "pos") BlockPos pos,
		@Local(name = "blockState") BlockState blockState
	) {
		if (!SnowloggingUtils.isSnowlogged(blockState)) return;
		final BlockState snowState = SnowloggingUtils.getSnowEquivalent(blockState);
		final boolean forceOpaque = ItemBlockRenderTypes.forceOpaque(snowState);
		random.setSeed(snowState.getSeed(pos));
		this.blockRenderer.getBlockModel(snowState).collectParts(random, parts);
		poseStack.pushPose();
		poseStack.translate(
			(float) SectionPos.sectionRelative(pos.getX()),
			(float) SectionPos.sectionRelative(pos.getY()),
			(float) SectionPos.sectionRelative(pos.getZ())
		);
		this.blockRenderer.renderBatched(snowState, pos, renderSectionRegion, poseStack, forceOpaque ? opaqueQuadOutput : quadOutput, true, parts);
		poseStack.popPose();
		parts.clear();
	}

}
