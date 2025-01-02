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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class OstrichBodyRotationControl extends BodyRotationControl {
	private final Ostrich mob;

	public OstrichBodyRotationControl(Ostrich ostrich) {
		super(ostrich);
		this.mob = ostrich;
	}

	@Override
	public void clientTick() {
		if (!this.mob.refuseToMove()) {
			super.clientTick();
		}
	}

}
