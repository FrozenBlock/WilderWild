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

package net.frozenblock.wilderwild.mixin.datagen.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = BlockModelGenerators.class, priority = 1)
public class BlockModelGeneratorsMixin {

	@Shadow
	@Final
	@Mutable
	static Map<Block, BlockModelGenerators.BlockStateGeneratorSupplier> FULL_BLOCK_MODEL_CUSTOM_GENERATORS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void wilderWild$addRotatedGabbroGeneration(CallbackInfo info) {
		final Map<Block, BlockModelGenerators.BlockStateGeneratorSupplier> newMap = new Object2ObjectLinkedOpenHashMap<>(FULL_BLOCK_MODEL_CUSTOM_GENERATORS);
		newMap.put(WWBlocks.GABBRO, BlockModelGenerators::createMirroredCubeGenerator);

		FULL_BLOCK_MODEL_CUSTOM_GENERATORS = Map.copyOf(newMap);
	}
}
