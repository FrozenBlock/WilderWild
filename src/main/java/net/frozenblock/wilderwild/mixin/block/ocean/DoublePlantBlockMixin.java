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

package net.frozenblock.wilderwild.mixin.block.ocean;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.frozenblock.wilderwild.block.CattailBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DoublePlantBlock.class)
public class DoublePlantBlockMixin {

	@ModifyExpressionValue(
		method = "placeAt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/DoublePlantBlock;copyWaterloggedFrom(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;",
			ordinal = 0
		)
	)
	private static BlockState wilderWild$setWaterloggedValueAndSwayIfCattail(
		BlockState original,
		@Share("wilderWild$isBottomWaterlogged") LocalBooleanRef isBottomWaterlogged
		) {
		if (original.getBlock() instanceof CattailBlock) {
			isBottomWaterlogged.set(original.getOptionalValue(BlockStateProperties.WATERLOGGED).orElse(false));
			original = original.setValue(CattailBlock.SWAYING, isBottomWaterlogged.get());
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "placeAt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/DoublePlantBlock;copyWaterloggedFrom(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;",
			ordinal = 1
		)
	)
	private static BlockState wilderWild$swayIfCattailTop(
		BlockState original,
		@Share("wilderWild$isBottomWaterlogged") LocalBooleanRef isBottomWaterlogged
	) {
		if (original.getBlock() instanceof CattailBlock) original = original.setValue(CattailBlock.SWAYING, isBottomWaterlogged.get());
		return original;
	}

}
