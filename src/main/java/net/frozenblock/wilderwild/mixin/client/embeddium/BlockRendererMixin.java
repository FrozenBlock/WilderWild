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

package net.frozenblock.wilderwild.mixin.client.embeddium;

import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderer.class)
public abstract class BlockRendererMixin {
	@Unique
	private static final BlockModelShaper WILDERWILD$BLOCK_MODEL_SHAPER = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();
	@Unique
	private static boolean wilderWild$hasCheckedSafety = false;
	@Unique
	private static boolean wilderWild$skipSnowloggedRendering = false;

	@Shadow
	public abstract void renderModel(BlockRenderContext ctx, ChunkBuildBuffers buffers);

	@Inject(method = "renderModel", at = @At("HEAD"), remap = false)
	public void wilderWild$renderModel(BlockRenderContext ctx, ChunkBuildBuffers buffers, CallbackInfo info) {
		if (!wilderWild$hasCheckedSafety) {
			wilderWild$hasCheckedSafety = true;
			try {
				BlockRenderContext dummyRenderContext = new BlockRenderContext(ctx.world());
				Vector3fc origin = ctx.origin();
				dummyRenderContext.update(
					ctx.pos(),
					new BlockPos((int) origin.x(), (int) origin.y(), (int) origin.z()),
					Blocks.GRASS_BLOCK.defaultBlockState(),
					WILDERWILD$BLOCK_MODEL_SHAPER.getBlockModel(Blocks.GRASS_BLOCK.defaultBlockState()),
					ctx.seed()
				);
			} catch (Exception ignored) {
				wilderWild$skipSnowloggedRendering = true;
			}
		}

		if (!wilderWild$skipSnowloggedRendering && SnowloggingUtils.isSnowlogged(ctx.state())) {
			BlockState snowState = SnowloggingUtils.getSnowEquivalent(ctx.state());
			BlockRenderContext snowRenderContext = new BlockRenderContext(ctx.world());
			Vector3fc origin = ctx.origin();
			snowRenderContext.update(
				ctx.pos(),
				new BlockPos((int) origin.x(), (int) origin.y(), (int) origin.z()),
				snowState,
				WILDERWILD$BLOCK_MODEL_SHAPER.getBlockModel(snowState),
				ctx.seed()
			);
			this.renderModel(snowRenderContext, buffers);
		}
	}

}
