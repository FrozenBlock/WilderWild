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

package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class BlockMixin {
	@WrapOperation(method = "spawnDestroyParticles",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;getId(Lnet/minecraft/world/level/block/state/BlockState;)I"
		)
	)
	public int wilderWild$spawnDestroyParticles(BlockState state, Operation<Integer> original, Level world, Player player, BlockPos pos) {
		if (SnowloggingUtils.isSnowlogged(state) && SnowloggingUtils.shouldHitSnow(state, pos, world, player)) {
			return original.call(SnowloggingUtils.getSnowEquivalent(state));
		}
		return original.call(state);
	}
}
