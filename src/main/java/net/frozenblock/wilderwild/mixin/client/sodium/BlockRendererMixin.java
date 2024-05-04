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

package net.frozenblock.wilderwild.mixin.client.sodium;

import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.core.BlockPos;
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
	private static final BlockModelShaper frozenLib$blockModelShaper = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();

	@Shadow
	public abstract void renderModel(BlockRenderContext ctx, ChunkBuildBuffers buffers);

	@Inject(method = "renderModel", at = @At("HEAD"), remap = false, require = 0)
	public void wilderWild$renderModel(BlockRenderContext ctx, ChunkBuildBuffers buffers, CallbackInfo info) {
		try {
			if (SnowloggingUtils.isSnowlogged(ctx.state())) {
				BlockState snowState = SnowloggingUtils.getSnowEquivalent(ctx.state());
				BlockRenderContext snowRenderContext = new BlockRenderContext(ctx.world());
				Vector3fc origin = ctx.origin();
				snowRenderContext.update(
					ctx.pos(),
					new BlockPos((int) origin.x(), (int) origin.y(), (int) origin.z()),
					snowState,
					frozenLib$blockModelShaper.getBlockModel(snowState),
					ctx.seed(),
					ctx.renderLayer()
				);
				this.renderModel(snowRenderContext, buffers);
			}
		} catch (Exception ignored) {}
	}

}
