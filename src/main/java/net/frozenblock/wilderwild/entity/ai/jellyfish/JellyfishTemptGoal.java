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

import java.util.EnumSet;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JellyfishTemptGoal extends Goal {
	private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0).ignoreLineOfSight();
	protected final Jellyfish mob;
	private final TargetingConditions targetingConditions;
	private final double speedModifier;
	@Nullable
	protected Player player;
	private int calmDown;
	private boolean isRunning;

	public JellyfishTemptGoal(Jellyfish mob, double speedModifier) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
	}

	@Override
	public boolean canUse() {
		if (this.mob.getTarget() != null) return false;
		if (this.calmDown > 0) {
			--this.calmDown;
			return false;
		}
		this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
		return this.player != null;
	}

	private boolean shouldFollow(@NotNull LivingEntity entity) {
		HolderSet<Item> tag = this.mob.getVariant().getReproductionFood();
		return entity.getMainHandItem().is(tag) || entity.getOffhandItem().is(tag);
	}

	@Override
	public void start() {
		this.isRunning = true;
	}

	@Override
	public void stop() {
		this.player = null;
		this.mob.getNavigation().stop();
		this.calmDown = JellyfishTemptGoal.reducedTickDelay(100);
		this.isRunning = false;
	}

	@Override
	public void tick() {
		this.mob.getLookControl().setLookAt(this.player, this.mob.getMaxHeadYRot() + 20, this.mob.getMaxHeadXRot());
		if (this.mob.distanceToSqr(this.player) < 6.25) {
			this.mob.getNavigation().stop();
		} else {
			this.mob.moveToAccurate(this.player, this.speedModifier);
		}
	}

	public boolean isRunning() {
		return this.isRunning;
	}
}

