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

package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.snowlogging.SnowloggingUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.sounds.BlockSoundSet;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockItem.class)
public class BlockItemMixin { // in common mixins.json

	@ModifyExpressionValue(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/BlockItem;getPlacementState(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$getSnowloggedPlacementState(
		BlockState original,
		BlockPlaceContext placeContext
	) {
		return SnowloggingUtil.getSnowloggedPlacementState(original, placeContext);
	}

	@WrapOperation(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getSounds(Lnet/minecraft/world/level/LevelReader;)Lnet/minecraft/world/level/block/sounds/BlockSoundSet;"
		)
	)
	public BlockSoundSet wilderWild$useSnowloggedBlockSoundSet(BlockState instance, LevelReader levelReader, Operation<BlockSoundSet> original) {
		return SnowloggingUtil.isSnowlogged(instance)
			? original.call(SnowloggingUtil.getSnowEquivalent(instance), levelReader)
			: original.call(instance, levelReader);
	}
}
