/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai.penguin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import org.jetbrains.annotations.NotNull;

public class PenguinCallerTracker extends EntityTracker {

	public PenguinCallerTracker(Entity entity, boolean bl) {
		super(entity, bl);
	}

	@Override
	public boolean isVisibleBy(LivingEntity livingEntity) {
		return this.getEntity().isAlive();
	}

	public @NotNull String toString() {
		return "PenguinCallerTracker for " + this.getEntity();
	}
}
