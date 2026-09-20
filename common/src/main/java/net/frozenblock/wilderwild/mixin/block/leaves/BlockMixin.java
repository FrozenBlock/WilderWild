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

package net.frozenblock.wilderwild.mixin.block.leaves;

import net.frozenblock.wilderwild.block.leaves.FallingLeafData;
import net.frozenblock.wilderwild.block.leaves.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

	@Inject(method = "stepOn", at = @At("HEAD"))
	public void wilderWild$trySpawnLeavesWalkParticles(Level level, BlockPos pos, BlockState onState, Entity entity, CallbackInfo info) {
		final Block block = Block.class.cast(this);
		final FallingLeafData fallingLeafData = block.frozenLib$getAttached(FallingLeafUtil.FALLING_LEAF_DATA_KEY);
		if (fallingLeafData == null || !onState.is(fallingLeafData.leavesBlock())) return;

		FallingLeafUtil.trySpawnWalkParticles(onState, level, pos, entity, false, fallingLeafData, false);
	}
}
