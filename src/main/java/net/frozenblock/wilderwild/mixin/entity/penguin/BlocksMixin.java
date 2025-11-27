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

package net.frozenblock.wilderwild.mixin.entity.penguin;

import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Blocks.class)
public class BlocksMixin {

	@Inject(method = "method_26128", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$allowPenguinSpawnA(BlockState state, BlockGetter level, BlockPos pos, EntityType entityType, CallbackInfoReturnable<Boolean> info) {
		if (entityType == WWEntityTypes.PENGUIN) info.setReturnValue(true);
	}

	@Inject(method = "method_26132", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$allowPenguinSpawnB(BlockState state, BlockGetter level, BlockPos pos, EntityType entityType, CallbackInfoReturnable<Boolean> info) {
		if (entityType == WWEntityTypes.PENGUIN) info.setReturnValue(true);
	}

}
