/*
 * Copyright 2025 FrozenBlock
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public final class WWFluidRendering {

	public static void init() { // Credit to embeddedt: https://github.com/embeddedt/embeddium/issues/284#issuecomment-2098864705
		FluidRenderHandler originalHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER);
		// Assert that the original handler exists now, otherwise the crash will happen later inside our handler
		Objects.requireNonNull(originalHandler);
		var customWaterHandler = new FluidRenderHandler() {
			private final Minecraft minecraft = Minecraft.getInstance();

			@Override
			public TextureAtlasSprite[] getFluidSprites(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
				return originalHandler.getFluidSprites(view, pos, state);
			}

			@Override
			public void renderFluid(BlockPos pos, BlockAndTintGetter world, VertexConsumer vertexConsumer, @NotNull BlockState blockState, FluidState fluidState) {
				if (blockState.getBlock() instanceof MesogleaBlock) {
					if (WWBlockConfig.Client.MESOGLEA_FLUID) {
						TextureAtlasSprite sprite = this.minecraft.getModelManager().getBlockModelShaper().getParticleIcon(blockState);
						LiquidRenderUtils.tesselateWithSingleTexture(
							world,
							pos,
							vertexConsumer,
							blockState,
							fluidState,
							sprite
						);
						return;
					}
					return;
				}
				originalHandler.renderFluid(pos, world, vertexConsumer, blockState, fluidState);
			}

			// Delegate all other methods to the original

			@Override
			public int getFluidColor(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
				return isMesoglea(view, pos) ? 0xFFFFFFFF : originalHandler.getFluidColor(view, pos, state);
			}
		};

		FluidRenderHandlerRegistry.INSTANCE.register(Fluids.WATER, Fluids.FLOWING_WATER, customWaterHandler);
	}

	private static boolean isMesoglea(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos) {
		if (view != null && pos != null && WWBlockConfig.Client.MESOGLEA_FLUID) return view.getBlockState(pos).getBlock() instanceof MesogleaBlock;
		return false;
	}
}
