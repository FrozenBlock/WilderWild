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

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import net.frozenblock.lib.block.client.api.LiquidRenderUtils;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidRenderer;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

	// NeoForge doesn't contain a fluid rendering API as vast as Fabric's, so we must revert to using a mixin for our Mesoglea rendering.
	@Inject(method = "tesselate", at = @At("HEAD"), cancellable = true)
	public void wilderWild$tesselateAsMesoglea(
		BlockAndTintGetter level, BlockPos pos, FluidRenderer.Output output, BlockState blockState, FluidState fluidState, CallbackInfo info
	) {
		if (!(blockState.getBlock() instanceof MesogleaBlock)) return;
		if (!WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID.get()) return;

		final TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(blockState).sprite();
		LiquidRenderUtils.tessellateWithSingleSprite(
			FluidRenderer.class.cast(this),
			level,
			pos,
			sprite,
			ChunkSectionLayer.TRANSLUCENT,
			output,
			blockState,
			fluidState,
			true,
			true
		);
		info.cancel();
	}
}
