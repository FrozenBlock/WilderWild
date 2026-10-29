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

import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jspecify.annotations.Nullable;

public class SunkenMeleeAttackGoal extends MeleeAttackGoal {
	private final Sunken mob;

	public SunkenMeleeAttackGoal(Sunken mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
		super(mob, speedModifier, followingTargetEvenIfNotSeen);
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		if (!super.canUse()) return false;

		final LivingEntity target = this.mob.getTarget();
		if (target == null) return false;

		if (this.getHarpoon() != null) return false;

		if (!this.mob.getAttackBoundingBox(4D).intersects(this.mob.getTarget().getBoundingBox())) return false;

		return true;
	}

	private @Nullable Harpoon getHarpoon() {
		return EntityReference.get(WWAttachmentTypes.HARPOON.get(this.mob), this.mob.level(), Harpoon.class);
	}
}
