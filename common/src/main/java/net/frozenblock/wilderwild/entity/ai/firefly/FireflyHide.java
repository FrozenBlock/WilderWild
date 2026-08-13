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

package net.frozenblock.wilderwild.entity.ai.firefly;

import net.frozenblock.lib.entity.api.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;

public class FireflyHide extends MoveToBlockBehavior<Firefly> {

	public FireflyHide(double speedModifier, int searchRange, int verticalSearchRange) {
		super(speedModifier, searchRange, verticalSearchRange);
	}

	@Override
	public boolean checkExtraStartConditions(ServerLevel level, Firefly body) {
		return body.shouldHide() && super.checkExtraStartConditions(level, body);
	}

	@Override
	public boolean canStillUse(ServerLevel level, Firefly body, long timestamp) {
		return body.shouldHide() && super.canStillUse(level, body, timestamp);
	}

	@Override
	protected void tick(ServerLevel level, Firefly body, long timestamp) {
		super.tick(level, body, timestamp);
		if (this.isReachedTarget()) {
			body.playSound(WWSounds.ENTITY_FIREFLY_HIDE.get(), 0.6F, 0.9F + level.getRandom().nextFloat() * 0.2F);
			body.discard();
		}
	}

	@Override
	public boolean isValidTarget(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos).is(WWBlockTags.FIREFLY_HIDEABLE_BLOCKS);
	}

	@Override
	public double acceptedDistance() {
		return 0.5D;
	}

	@Override
	protected void moveMobToBlock(Firefly body) {
		body.getNavigation().moveTo(this.blockPos.getX() + 0.5D, this.blockPos.getY() + 0.5D, this.blockPos.getZ() + 0.5D, this.speedModifier);
	}

	@Override
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}
}
