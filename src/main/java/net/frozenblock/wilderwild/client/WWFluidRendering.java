/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.frozenblock.lib.block.client.api.LiquidRenderUtils;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public final class WWFluidRendering {

	public static void init() { // Credit to embeddedt: https://github.com/embeddedt/embeddium/issues/284#issuecomment-2098864705
		FluidRenderHandler originalHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER);
		// Assert that the original handler exists now, otherwise the crash will happen later inside our handler
		Objects.requireNonNull(originalHandler);
		var customWaterHandler = new FluidRenderHandler() {

			private boolean isSingleTexture(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos) {
				if (view != null && pos != null && WWBlockConfig.Client.MESOGLEA_LIQUID) {
					BlockState state = view.getBlockState(pos);
					return state.getBlock() instanceof MesogleaBlock && state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED);
				}
				return false;
			}

			@Override
			public TextureAtlasSprite[] getFluidSprites(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
				return originalHandler.getFluidSprites(view, pos, state);
			}

			@Override
			public void renderFluid(BlockPos pos, BlockAndTintGetter world, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
				if (isSingleTexture(world, pos)) {
					TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getBlockModelShaper().getParticleIcon(world.getBlockState(pos));
					LiquidRenderUtils.tesselateWithSingleTexture(
						world,
						pos,
						vertexConsumer,
						blockState,
						fluidState,
						sprite
					);
				} else {
					originalHandler.renderFluid(pos, world, vertexConsumer, blockState, fluidState);
				}
			}

			// Delegate all other methods to the original

			@Override
			public int getFluidColor(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
				return isSingleTexture(view, pos) ? 0xFFFFFFFF : originalHandler.getFluidColor(view, pos, state);
			}
		};

		FluidRenderHandlerRegistry.INSTANCE.register(Fluids.WATER, Fluids.FLOWING_WATER, customWaterHandler);
	}
}
