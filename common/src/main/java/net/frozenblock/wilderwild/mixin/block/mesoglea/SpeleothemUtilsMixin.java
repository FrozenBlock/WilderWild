/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.SpeleothemUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpeleothemUtils.class)
public abstract class SpeleothemUtilsMixin {

	@ModifyExpressionValue(
		method = "isBaseOrLava",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"
		)
	)
	private static boolean wilderWild$preventLargeMesogleaInLava(
		boolean original,
		@Local(argsOnly = true) Block baseBlock
	) {
		if (original && baseBlock.builtInRegistryHolder().is(WWBlockItemTags.MESOGLEA.block())) return false;
		return original;
	}
}
