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

package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.impl.SculkBuildingBlockBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SculkWallBlock extends WallBlock implements SculkBuildingBlockBehaviour {
	private static final IntProvider EXPERIENCE = ConstantInt.of(1);

	public SculkWallBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
		super.spawnAfterBreak(state, level, pos, stack, dropExperience);
		if (dropExperience) this.tryDropExperience(level, pos, stack, EXPERIENCE);
	}

	@Override
	public boolean supportsSculkGrowths() {
		return false;
	}
}
