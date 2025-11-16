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

package net.frozenblock.wilderwild.entity.ai.jellyfish;

import net.frozenblock.lib.entity.api.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.block.NematocystBlock;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class JellyfishHide extends MoveToBlockBehavior<Jellyfish> {

	public JellyfishHide(Jellyfish mob, double speedModifier, int searchRange, int verticalSearchRange) {
		super(mob, speedModifier, searchRange, verticalSearchRange);
	}

	@Override
	public void start(ServerLevel level, Jellyfish entity, long gameTime) {
		super.start(level, entity, gameTime);
	}

	@Override
	public boolean checkExtraStartConditions(ServerLevel level, Jellyfish owner) {
		return owner.shouldHide() && super.checkExtraStartConditions(level, owner);
	}

	@Override
	protected void tick(ServerLevel level, Jellyfish owner, long gameTime) {
		super.tick(level, owner, gameTime);
		if (this.isReachedTarget() && !owner.vanishing) {
			level.broadcastEntityEvent(owner, EntityEvent.TENDRILS_SHIVER);
			owner.vanishing = true;
		}
	}

	@Override
	public boolean isValidTarget(LevelReader level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		return state.getBlock() instanceof MesogleaBlock || (state.getBlock() instanceof NematocystBlock && state.getFluidState().is(FluidTags.WATER));
	}

	@Override
	public double acceptedDistance() {
		return 0.5D;
	}

	@Override
	protected void moveMobToBlock() {
		this.mob
			.getNavigation()
			.moveTo(this.blockPos.getX() + 0.5, this.blockPos.getY() + 0.5, this.blockPos.getZ() + 0.5, this.speedModifier);
	}

	@Override
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}
}
