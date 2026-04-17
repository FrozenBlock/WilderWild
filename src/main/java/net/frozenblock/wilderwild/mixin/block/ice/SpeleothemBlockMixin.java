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

package net.frozenblock.wilderwild.mixin.block.ice;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.SpeleothemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpeleothemBlock.class)
public class SpeleothemBlockMixin {

	@ModifyExpressionValue(
		method = "growStalactiteOrStalagmiteIfPossible",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/RandomSource;nextBoolean()Z"
		)
	)
	public boolean wilderWild$preventIciclesFromGrowingBelow(boolean original) {
		return SpeleothemBlock.class.cast(this) == WWBlocks.ICICLE || original;
	}

	@WrapOperation(
		method = "spawnFallingStalactite",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;setHurtsEntities(FI)V"
		)
	)
	private static void wilderWild$fallingIciclesAreWeaker(FallingBlockEntity instance, float damagePerDistance, int damageMax, Operation<Void> original) {
		if (instance.getBlockState().is(WWBlocks.ICICLE)) damagePerDistance *= 10F;
		original.call(instance, damagePerDistance, damageMax);
	}

}
