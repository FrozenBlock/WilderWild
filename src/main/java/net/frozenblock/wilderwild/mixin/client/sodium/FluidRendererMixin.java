/*
 * Copyright 2024-2025 FrozenBlock
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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.caffeinemc.mods.sodium.client.model.color.ColorProvider;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(DefaultFluidRenderer.class)
public class FluidRendererMixin {

	@Inject(method = "render", at = @At(value = "HEAD"), require = 0)
	private void wilderWild$renderMesoglea(
		LevelSlice level,
		BlockState blockState,
		FluidState fluidState,
		BlockPos blockPos,
		BlockPos offset,
		TranslucentGeometryCollector collector,
		ChunkModelBuilder meshBuilder,
		Material material,
		ColorProvider<FluidState> colorProvider,
		TextureAtlasSprite[] sprites,
		CallbackInfo info,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (blockState.getBlock() instanceof MesogleaBlock) {
			isMesoglea.set(blockState.getBlock() instanceof MesogleaBlock);
		}
	}

	@WrapOperation(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/DefaultFluidRenderer;isFullBlockFluidOccluded(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Z"
		),
		require = 0
	)
	private boolean wilderWild$isMesoglea(
		DefaultFluidRenderer instance,
		BlockAndTintGetter level,
		BlockPos blockPos,
		Direction direction,
		BlockState blockState,
		FluidState fluidState,
		Operation<Boolean> original,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (isMesoglea.get()) return true;
		return original.call(instance, level, blockPos, direction, blockState, fluidState);
	}
}
