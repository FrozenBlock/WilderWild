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

package net.frozenblock.wilderwild.block.entity;

import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class ScorchedBlockEntity extends BlockEntity {
	private static final int RESET_DELAY = 40;
	private int brushCount;
	private long brushCountResetsAtTick;
	private long coolDownEndsAtTick;

	public ScorchedBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		super(WWBlockEntityTypes.SCORCHED_BLOCK, blockPos, blockState);
	}

	public boolean brush(long l) {
		this.brushCountResetsAtTick = l + RESET_DELAY;
		if (l < this.coolDownEndsAtTick || !(this.level instanceof ServerLevel)) {
			return false;
		}
		this.coolDownEndsAtTick = l + 10L;
		int i = this.getCompletionState();
		if (++this.brushCount >= 10) {
			this.brushingCompleted();
			return true;
		}
		this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), RESET_DELAY);
		int completionState = this.getCompletionState();
		if (i != completionState) {
			BlockState blockState = this.getBlockState();
			BlockState blockState2 = blockState.setValue(BlockStateProperties.DUSTED, completionState);
			this.level.setBlockAndUpdate(this.getBlockPos(), blockState2);
		}
		return false;
	}

	private void brushingCompleted() {
		if (this.level == null || this.level.getServer() == null) return;
		this.level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_BRUSH_BLOCK_COMPLETE, this.worldPosition, Block.getId(this.getBlockState()));
		ScorchedBlock.hydrate(this.getBlockState(), this.level, this.worldPosition);
		this.brushCount = 0;
		this.brushCountResetsAtTick = 0;
		this.coolDownEndsAtTick = 0;
	}

	public void checkReset() {
		if (this.level == null) {
			return;
		}
		if (this.brushCount != 0 && this.level.getGameTime() >= this.brushCountResetsAtTick) {
			int i = this.getCompletionState();
			this.brushCount = Math.max(0, this.brushCount - 2);
			int j = this.getCompletionState();
			if (i != j) {
				this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(BlockStateProperties.DUSTED, j));
			}
			this.brushCountResetsAtTick = this.level.getGameTime() + 4L;
		}
		if (this.brushCount == 0) {
			this.brushCountResetsAtTick = 0L;
			this.coolDownEndsAtTick = 0L;
		} else {
			this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), (int) (this.brushCountResetsAtTick - this.level.getGameTime()));
		}
	}

	private int getCompletionState() {
		if (this.brushCount == 0) return 0;
		if (this.brushCount < 3) return 1;
		if (this.brushCount < 6) return 2;
		return 3;
	}

}
