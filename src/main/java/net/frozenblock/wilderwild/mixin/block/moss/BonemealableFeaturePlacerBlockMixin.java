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

package net.frozenblock.wilderwild.mixin.block.moss;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Optional;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.worldgen.features.configured.WWMiscConfigured;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.BonemealableFeaturePlacerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BonemealableFeaturePlacerBlock.class)
public class BonemealableFeaturePlacerBlockMixin {

	@WrapOperation(
		method = "lambda$performBonemeal$0",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/Registry;get(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;"
		)
	)
	public Optional wilderWild$removeAzaleaFromMossPatch(Registry instance, ResourceKey resourceKey, Operation<Optional> original) {
		if (!WWBlockConfig.AZALEA_FROM_MOSS.get()) resourceKey = WWMiscConfigured.MOSS_PATCH_BONEMEAL_NO_AZALEA.getKey();
		return original.call(instance, resourceKey);
	}

}
