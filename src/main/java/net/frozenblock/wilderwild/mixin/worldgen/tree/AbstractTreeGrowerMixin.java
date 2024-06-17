/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import net.frozenblock.wilderwild.world.impl.sapling.AbstractTreeGrowerInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractTreeGrower.class, priority = 69420)
public class AbstractTreeGrowerMixin implements AbstractTreeGrowerInterface {

	@Unique
	private ServerLevel wilderWild$level;

	@Unique
	private BlockPos wilderWild$pos;

	@Inject(method = "growTree", at = @At("HEAD"))
	public void wilderWild$growTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource random, CallbackInfoReturnable<Boolean> info) {
		this.wilderWild$level = level;
		this.wilderWild$pos = pos;
	}

	@Unique
	@Override
	public ServerLevel wilderWild$getLevel() {
		return this.wilderWild$level;
	}

	@Unique
	@Override
	public BlockPos wilderWild$getPos() {
		return this.wilderWild$pos;
	}

}
