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
import net.fabricmc.fabric.impl.client.indigo.Indigo;
import net.fabricmc.fabric.impl.client.indigo.renderer.accessor.AccessChunkRendererRegion;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderDispatcher.class)
public class BlockRenderDispatcherMixin {

	@Shadow
	@Final
	private ModelBlockRenderer modelRenderer;

	@Inject(method = "renderBatched", at = @At("HEAD"))
	public void wilderWild$renderBatched(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, RandomSource random, CallbackInfo ci) {
		if (state.hasProperty(RegisterProperties.SNOWLOGGED) && state.getValue(RegisterProperties.SNOWLOGGED)) {
			BakedModel model = this.getBlockModel(Blocks.SNOW.defaultBlockState());
			if (Indigo.ALWAYS_TESSELATE_INDIGO || !model.isCustomRenderer()) {
				((AccessChunkRendererRegion) level).fabric_getRenderer().tessellateBlock(Blocks.SNOW.defaultBlockState(), pos, model, poseStack);
				return;
			}
			this.modelRenderer
				.tesselateBlock(level, model, state, pos, poseStack, consumer, checkSides, random, state.getSeed(pos), OverlayTexture.NO_OVERLAY);
		}
	}

	@Shadow
	public BakedModel getBlockModel(BlockState state) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BlockRenderDispatcherMixin.");
	}

	@Shadow
	public void renderBatched(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, RandomSource random) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BlockRenderDispatcherMixin.");
	}
}
