/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.entity.monster.skeleton;

import java.util.EnumSet;
import java.util.List;
import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.SwingAnimation;
import org.jspecify.annotations.Nullable;

public class RangedHarpoonCrossbowAttackGoal<T extends Monster & RangedAttackMob & CrossbowAttackMob> extends Goal {
	private static final int MAX_TICKS_SINCE_SHOT_BEFORE_REEL_IN = 100;
	private static final int TICKS_SINCE_SHOT_BEFORE_REEL_IN_ENTITY = 60;
	public static final UniformInt PATHFINDING_DELAY_RANGE = TimeUtil.rangeOfSeconds(1, 2);
	private final T mob;
	public RangedCrossbowAttackGoal.CrossbowState crossbowState = RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;
	public final double speedModifier;
	public final float attackRadiusSqr;
	private final float attackRadiusInWaterSqr;
	public int seeTime;
	public int attackDelay;
	public int updatePathDelay;
	private boolean strafingClockwise;
	private int strafingTime = -1;
	private int ticksSinceShot;
	private boolean reelingIn;

	public RangedHarpoonCrossbowAttackGoal(T mob, double speedModifier, float attackRadius, float attackRadiusInWater) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.attackRadiusSqr = attackRadius * attackRadius;
		this.attackRadiusInWaterSqr = attackRadiusInWater * attackRadiusInWater;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.isValidTarget() && this.isHoldingCrossbow();
	}

	private boolean isHoldingCrossbow() {
		return this.mob.isHolding(Items.CROSSBOW);
	}

	@Override
	public boolean canContinueToUse() {
		return this.isValidTarget() && (this.canUse() || !this.mob.getNavigation().isDone()) && this.isHoldingCrossbow();
	}

	private boolean isValidTarget() {
		return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void stop() {
		super.stop();
		this.mob.setAggressive(false);
		this.mob.setTarget(null);
		this.seeTime = 0;
		this.strafingTime = -1;
		if (this.mob.isUsingItem()) {
			this.mob.stopUsingItem();
			this.mob.setChargingCrossbow(false);
			this.mob.getUseItem().set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
		}
		this.resetHarpoonState();
	}

	@Override
	public void tick() {
		final LivingEntity target = this.mob.getTarget();

		final boolean hasLineOfSight;
		if (target != null) {
			hasLineOfSight = this.mob.getSensing().hasLineOfSight(target);
			final boolean hadLineOfSight = this.seeTime > 0;
			if (hasLineOfSight != hadLineOfSight) this.seeTime = 0;

			if (hasLineOfSight) {
				this.seeTime++;
			} else {
				this.seeTime--;
			}
		} else {
			hasLineOfSight = false;
		}

		final Harpoon harpoon = this.getHarpoon();
		if (harpoon != null) {
			this.ticksSinceShot += 1;

			if (harpoon.reelingIn() && this.reelingIn) {
				this.strafingTime = -1;
			} else if (this.shouldReelIn(harpoon) || target == null) {
				harpoon.retrieve(null);
				this.reelingIn = true;
				this.strafingTime = -1;
			} else if (this.isHookedInEntity(harpoon) && !this.shouldReelInEntity(harpoon.getHookedIn())) {
				this.resetHarpoonState();
				this.mob.swing(ProjectileUtil.getWeaponHoldingHand(this.mob, Items.CROSSBOW), SwingAnimation.DEFAULT);
				harpoon.discard();
				this.strafingTime = -1;
			} else {
				this.strafingTime++;
			}

			this.attackDelay = 1;
			this.crossbowState = RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;
			this.tickStrafeAndLook(target, hasLineOfSight);
			return;
		} else {
			this.resetHarpoonState();
		}

		if (target != null) {
			final double activeAttackRange = this.activeAttackRange();
			final double targetDistSqr = this.mob.distanceToSqr(target);

			final boolean needsToMoveCloser = (targetDistSqr > activeAttackRange || this.seeTime < 5)
				&& (this.attackDelay == 0 && this.crossbowState == RangedCrossbowAttackGoal.CrossbowState.UNCHARGED);
			if (needsToMoveCloser) {
				this.updatePathDelay--;
				if (this.updatePathDelay <= 0) {
					this.mob.getNavigation().moveTo(target, this.canRun() ? this.speedModifier : this.speedModifier * 0.5);
					this.updatePathDelay = PATHFINDING_DELAY_RANGE.sample(this.mob.getRandom());
				}
				this.strafingTime = -1;
			} else {
				this.updatePathDelay = 0;
				this.mob.getNavigation().stop();
				this.strafingTime++;
			}

			this.tickStrafeAndLook(target, hasLineOfSight);

			if (this.crossbowState == RangedCrossbowAttackGoal.CrossbowState.UNCHARGED) {
				if (!needsToMoveCloser) {
					this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.mob, Items.CROSSBOW));
					this.crossbowState = RangedCrossbowAttackGoal.CrossbowState.CHARGING;
					this.mob.setChargingCrossbow(true);
				}
			} else if (this.crossbowState == RangedCrossbowAttackGoal.CrossbowState.CHARGING) {
				if (!this.mob.isUsingItem()) this.crossbowState = RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;

				final int pullTime = this.mob.getTicksUsingItem();
				final ItemStack useItem = this.mob.getUseItem();
				if (pullTime >= CrossbowItem.getChargeDuration(useItem, this.mob)) {
					this.mob.releaseUsingItem();
					this.crossbowState = RangedCrossbowAttackGoal.CrossbowState.CHARGED;
					if (useItem.is(Items.CROSSBOW)) useItem.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.ofNonEmpty(List.of(WWItems.HARPOON.toStack())));
					this.attackDelay = 10 + this.mob.getRandom().nextInt(20);
					this.mob.setChargingCrossbow(false);
				}
			} else if (this.crossbowState == RangedCrossbowAttackGoal.CrossbowState.CHARGED) {
				this.attackDelay--;
				if (this.attackDelay == 0) this.crossbowState = RangedCrossbowAttackGoal.CrossbowState.READY_TO_ATTACK;
			} else if (this.crossbowState == RangedCrossbowAttackGoal.CrossbowState.READY_TO_ATTACK && hasLineOfSight) {
				this.mob.performRangedAttack(target, 1F);
				this.crossbowState = RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;
			}
		}
	}

	private void tickStrafeAndLook(Entity target, boolean hasLineOfSight) {
		if (this.strafingTime >= 20) {
			if (this.mob.getRandom().nextFloat() < 0.3F) this.strafingClockwise = !this.strafingClockwise;
			this.strafingTime = 0;
		}

		if (this.strafingTime > -1) {
			final boolean strafingBackwards = !(this.mob.distanceToSqr(target) > this.activeAttackRange() * 0.75F) && hasLineOfSight;

			final float strafeSpeed = this.mob.isInLiquid() ? 2F : 1F;
			this.mob.getMoveControl().strafe((strafingBackwards ? -1F : 0.5F) * strafeSpeed, (this.strafingClockwise ? 1F : -1F) * strafeSpeed);
			if (this.mob.getControlledVehicle() instanceof Mob vehicle) vehicle.lookAt(target, 30F, 30F);
			this.mob.lookAt(target, 30F, 30F);
		} else {
			this.mob.getLookControl().setLookAt(target, 30F, 30F);
		}
	}

	private void resetHarpoonState() {
		this.ticksSinceShot = 0;
		this.reelingIn = false;
	}

	private boolean isHookedInGround(Harpoon harpoon) {
		return harpoon.getHookedIn() == null && harpoon.isInGround();
	}

	private boolean isHookedInEntity(Harpoon harpoon) {
		return harpoon.getHookedIn() != null;
	}

	private boolean shouldReelInEntity(@Nullable Entity hookedIn) {
		return hookedIn != null && hookedIn == this.mob.getTarget();
	}

	private boolean shouldReelIn(Harpoon harpoon) {
		return this.ticksSinceShot >= MAX_TICKS_SINCE_SHOT_BEFORE_REEL_IN
			|| this.isHookedInGround(harpoon)
			|| (this.isHookedInEntity(harpoon)
				&& (this.ticksSinceShot >= TICKS_SINCE_SHOT_BEFORE_REEL_IN_ENTITY)
				&& this.shouldReelInEntity(harpoon.getHookedIn())
			);
	}

	private @Nullable Harpoon getHarpoon() {
		return EntityReference.get(WWAttachmentTypes.HARPOON.get(this.mob), this.mob.level(), Harpoon.class);
	}

	private float activeAttackRange() {
		return this.mob.isInWater() ? this.attackRadiusInWaterSqr : this.attackRadiusSqr;
	}

	private boolean canRun() {
		return this.crossbowState == RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;
	}
}
