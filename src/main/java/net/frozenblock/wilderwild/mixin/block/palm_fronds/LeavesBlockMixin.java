/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.palm_fronds;

import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.world.level.block.LeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

	@ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;", ordinal = 0))
	public void wilderWild$usePalmValues(Args args) {
		args.set(1, BlockConfig.get().leafDistance);
	}

	@ModifyConstant(method = "isRandomlyTicking", constant = @Constant(intValue = 7))
	private static int wilderWild$isRandomlyTicking(int constant) {
		return BlockConfig.get().leafDistance;
	}

	@ModifyConstant(method = "decaying", constant = @Constant(intValue = 7))
	private static int wilderWild$decaying(int constant) {
		return BlockConfig.get().leafDistance;
	}

	@ModifyConstant(method = "updateDistance", constant = @Constant(intValue = 7))
	private static int wilderWild$updateDistance(int constant) {
		return BlockConfig.get().leafDistance;
	}

	@ModifyConstant(method = "getDistanceAt", constant = @Constant(intValue = 7))
	private static int wilderWild$getDistanceAt(int constant) {
		return BlockConfig.get().leafDistance;
	}

}
