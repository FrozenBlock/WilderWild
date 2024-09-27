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

package net.frozenblock.wilderwild.entity.ai.turtle;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.entity.impl.TurtleCooldownInterface;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TurtleNearestAttackableGoal<T extends LivingEntity> extends TargetGoal {
	protected final Class<T> targetType;
	protected final int randomInterval;
	@Nullable
	protected LivingEntity target;
	protected TargetingConditions targetConditions;

	public TurtleNearestAttackableGoal(@NotNull Mob mob, @NotNull Class<T> class_, boolean bl) {
		this(mob, class_, 10, bl, false, null);
	}

	public TurtleNearestAttackableGoal(@NotNull Mob mob, @NotNull Class<T> class_, int i, boolean bl, boolean bl2, @Nullable TargetingConditions.Selector predicate) {
		super(mob, bl, bl2);
		this.targetType = class_;
		this.randomInterval = NearestAttackableTargetGoal.reducedTickDelay(i);
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
		this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(predicate);
	}

	@Override
	public boolean canUse() {
		if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
			return false;
		}
		this.findTarget();
		return this.target != null && ((TurtleCooldownInterface) this.mob).wilderWild$getAttackCooldown() <= 0;
	}

	@NotNull
	protected AABB getTargetSearchArea(double d) {
		return this.mob.getBoundingBox().inflate(d, 4.0, d);
	}

	protected void findTarget() {
		this.target = this.targetType == Player.class || this.targetType == ServerPlayer.class ? getServerLevel(this.mob).getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()) : getServerLevel(this.mob).getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), livingEntity -> true), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
	}

	@Override
	public void start() {
		((TurtleCooldownInterface) this.mob).wilderWild$setAttackCooldown(2400);
		this.mob.setTarget(this.target);
		super.start();
	}

	public void setTarget(@Nullable LivingEntity livingEntity) {
		this.target = livingEntity;
	}
}
