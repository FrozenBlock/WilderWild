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

package net.frozenblock.wilderwild.mixin.client.indium;

import link.infra.indium.renderer.render.TerrainRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(TerrainRenderContext.class)
public abstract class BlockRendererMixin {

	@Unique
	private static final BlockModelShaper WILDERWILD$BLOCK_MODEL_SHAPER = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();

	@Inject(
		method = "tessellateBlock",
		at = @At("HEAD"),
		remap = false
	)
	public void wilderWild$tessellateBlock(BlockRenderContext ctx, CallbackInfo info) {
		try {
			if (SnowloggingUtils.isSnowlogged(ctx.state())) {
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
				this.tessellateBlock(snowRenderContext);
			}
		} catch (Exception ignored) {}
	}

	@Shadow
	public void tessellateBlock(BlockRenderContext ctx) {}

}
