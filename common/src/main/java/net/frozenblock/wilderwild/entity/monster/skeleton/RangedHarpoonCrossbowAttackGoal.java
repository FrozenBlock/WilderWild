package net.frozenblock.wilderwild.entity.monster.skeleton;

import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class RangedHarpoonCrossbowAttackGoal<T extends Monster & RangedAttackMob & CrossbowAttackMob> extends RangedCrossbowAttackGoal<T> {
	private static final int MAX_TICKS_SINCE_SHOT_BEFORE_REEL_IN = 100;
	private static final int TICKS_SINCE_SHOT_BEFORE_REEL_IN_ENTITY = 60;
	private final T mob;
	private final double speedModifierWhenRepositioning;
	private final float attackRadiusInWaterSqr;
	private int ticksSinceShot;
	private @Nullable Vec3 awayPos;
	private boolean navigatedToAwayPos;

	public RangedHarpoonCrossbowAttackGoal(T mob, double speedModifier, float attackRadius, double speedModifierWhenRepositioning, float attackRadiusInWater) {
		super(mob, speedModifier, attackRadius);
		this.mob = mob;
		this.speedModifierWhenRepositioning = speedModifierWhenRepositioning;
		this.attackRadiusInWaterSqr = attackRadiusInWater * attackRadiusInWater;
	}

	@Override
	public void tick() {
		final LivingEntity target = this.mob.getTarget();
		final Harpoon harpoon = this.getHarpoon();
		if (harpoon != null) {
			this.ticksSinceShot += 1;

			if (this.shouldReelIn(harpoon) || target == null) {
				harpoon.retrieve(null);
				this.resetHarpoonState();
			} else if (this.isHookedInEntity(harpoon) && !this.shouldReelInEntity(harpoon.getHookedIn())) {
				this.resetHarpoonState();
				this.mob.swing(ProjectileUtil.getWeaponHoldingHand(this.mob, Items.CROSSBOW), SwingAnimation.DEFAULT);
				harpoon.discard();
			} else {
				if (this.awayPos != null) {
					this.mob
						.getNavigation()
						.moveTo(this.awayPos.x, this.awayPos.y, this.awayPos.z, this.speedModifier * this.speedModifierWhenRepositioning);
					if (this.mob.getNavigation().isDone()) {
						this.awayPos = null;
						this.navigatedToAwayPos = true;
					}
				} else {
					final double targetDistSqr = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
					final double distance = Math.sqrt(targetDistSqr);
					final int mountDistance = this.mob.isPassenger() ? 2 : 0;
					this.awayPos = LandRandomPos.getPosAway(
						this.mob,
						Math.max(0D, 6 + mountDistance - distance),
						Math.max(1D, 16 + mountDistance - distance),
						16,
						target.position()
					);
				}
			}

			final boolean hasLineOfSight = this.mob.getSensing().hasLineOfSight(target);
			final boolean hadLineOfSight = this.seeTime > 0;
			if (hasLineOfSight != hadLineOfSight) this.seeTime = 0;

			if (hasLineOfSight) {
				this.seeTime++;
			} else {
				this.seeTime--;
			}

			this.attackDelay = 0;
			this.crossbowState = CrossbowState.UNCHARGED;
			return;
		} else {
			this.resetHarpoonState();
		}

		super.tick();
	}

	private void resetHarpoonState() {
		this.ticksSinceShot = 0;
		this.awayPos = null;
		this.navigatedToAwayPos = false;
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
				&& (this.navigatedToAwayPos || this.ticksSinceShot >= TICKS_SINCE_SHOT_BEFORE_REEL_IN_ENTITY)
				&& this.shouldReelInEntity(harpoon.getHookedIn())
			);
	}

	private @Nullable Harpoon getHarpoon() {
		return EntityReference.get(WWAttachmentTypes.HARPOON.get(this.mob), this.mob.level(), Harpoon.class);
	}

	public float getAttackRadiusInWaterSqr() {
		return this.attackRadiusInWaterSqr;
	}
}
