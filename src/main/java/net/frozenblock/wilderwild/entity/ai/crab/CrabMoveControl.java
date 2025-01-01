/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.world.entity.ai.control.MoveControl;

public class CrabMoveControl extends MoveControl {

	private final Crab crab;

	public CrabMoveControl(Crab crab) {
		super(crab);
		this.crab = crab;
	}

	@Override
	public void tick() {
		if (!this.crab.cancelMovementToDescend && !this.crab.isDiggingOrEmerging()) {
			super.tick();
		}
		if (crab.getNavigation().isStuck()) {
			this.operation = Operation.WAIT;
		}
	}

}
