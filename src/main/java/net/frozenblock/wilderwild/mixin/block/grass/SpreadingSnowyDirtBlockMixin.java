/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.grass;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {

	@ModifyExpressionValue(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/SpreadingSnowyDirtBlock;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$spreadFoliatedGrassLikeGrass(BlockState original) {
		if (original.is(RegisterBlocks.FOLIATED_GRASS)) {
			return Blocks.GRASS_BLOCK.defaultBlockState();
		}
		return original;
	}
}
