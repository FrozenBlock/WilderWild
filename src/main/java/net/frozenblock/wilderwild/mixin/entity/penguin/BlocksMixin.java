/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
	private static void wilderWild$allowPenguinSpawnA(
		BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType entityType, CallbackInfoReturnable<Boolean> info
	) {
		if (entityType == WWEntityTypes.PENGUIN) {
			info.setReturnValue(true);
		}
	}

	@Inject(method = "method_26132", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$allowPenguinSpawnB(
		BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType entityType, CallbackInfoReturnable<Boolean> info
	) {
		if (entityType == WWEntityTypes.PENGUIN) {
			info.setReturnValue(true);
		}
	}

}
