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

package net.frozenblock.wilderwild.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(DefaultTerrainRenderPasses.class)
public abstract class DefaultTerrainRenderPassesMixin {

	@WrapOperation(
		method = "<clinit>",
		at = @At(
			value = "NEW",
			target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/TerrainRenderPass;",
			ordinal = 2
		),
		remap = false,
		require = 0
	)
	private static TerrainRenderPass wilderwild$allowFragmentDiscardOnTranslucent(
		ChunkSectionLayer chunkSectionLayer, boolean isTranslucent, boolean allowFragmentDiscard, Operation<TerrainRenderPass> original
	) {
		return original.call(chunkSectionLayer, isTranslucent, true);
	}

}
