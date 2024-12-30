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

package net.frozenblock.wilderwild.entity.ai.firefly;

import net.frozenblock.lib.entity.api.behavior.MoveToBlockBehavior;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;

public class FireflyHide extends MoveToBlockBehavior<Firefly> {

	public FireflyHide(@NotNull Firefly firefly, double speedModifier, int searchRange, int verticalSearchRange) {
		super(firefly, speedModifier, searchRange, verticalSearchRange);
	}

	@Override
	public void start(@NotNull ServerLevel level, @NotNull Firefly firefly, long gameTime) {
		super.start(level, firefly, gameTime);
	}

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull Firefly firefly) {
		return firefly.shouldHide() && super.checkExtraStartConditions(level, firefly);
	}

	@Override
	public boolean canStillUse(@NotNull ServerLevel level, @NotNull Firefly firefly, long gameTime) {
		return firefly.shouldHide() && super.canStillUse(level, firefly, gameTime);
	}

	@Override
	protected void tick(@NotNull ServerLevel level, @NotNull Firefly firefly, long gameTime) {
		super.tick(level, firefly, gameTime);
		if (this.isReachedTarget()) {
			firefly.playSound(WWSounds.ENTITY_FIREFLY_HIDE, 0.6F, 0.9F + level.random.nextFloat() * 0.2F);
			firefly.discard();
		}
	}

	@Override
	public boolean isValidTarget(@NotNull LevelReader level, @NotNull BlockPos pos) {
		return level.getBlockState(pos).is(WWBlockTags.FIREFLY_HIDEABLE_BLOCKS);
	}

	@Override
	public double acceptedDistance() {
		return 0.5D;
	}

	@Override
	protected void moveMobToBlock() {
		this.mob
			.getNavigation()
			.moveTo(this.blockPos.getX() + 0.5D, this.blockPos.getY() + 0.5D, this.blockPos.getZ() + 0.5D, this.speedModifier);
	}

	@Override
	@NotNull
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}
}
