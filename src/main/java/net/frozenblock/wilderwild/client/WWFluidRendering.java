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

package net.frozenblock.wilderwild.client;

import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.frozenblock.lib.block.client.api.LiquidRenderUtils;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidRenderer;
import net.minecraft.client.renderer.block.FluidStateModelSet;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

@Environment(EnvType.CLIENT)
public final class WWFluidRendering {

	public static void init() { // Credit to embeddedt: https://github.com/embeddedt/embeddium/issues/284#issuecomment-2098864705
		final FluidRenderHandler waterHandler = FluidRenderingRegistry.get(Fluids.WATER);
		// Assert that the original handler exists now, otherwise the crash will happen later inside our handler
		Objects.requireNonNull(waterHandler);
		final FluidRenderHandler mesogleaHandler = new FluidRenderHandler() {
			private final Minecraft minecraft = Minecraft.getInstance();

			@Override
			public void renderFluid(FluidRenderer renderer, BlockPos pos, BlockAndTintGetter level, FluidRenderer.Output output, BlockState state, FluidState fluidState) {
				if (!(state.getBlock() instanceof MesogleaBlock)) {
					waterHandler.renderFluid(renderer, pos, level, output, state, fluidState);
					return;
				}
				if (!WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID.get()) return;

				final TextureAtlasSprite sprite = this.minecraft.getModelManager().getBlockStateModelSet().getParticleMaterial(state).sprite();
				LiquidRenderUtils.tessellateWithSingleSprite(renderer, level, pos, sprite, ChunkSectionLayer.TRANSLUCENT, output, state, fluidState);
			}
		};

		FluidRenderingRegistry.register(Fluids.WATER, Fluids.FLOWING_WATER, FluidStateModelSet.WATER_MODEL, mesogleaHandler);
	}
}
