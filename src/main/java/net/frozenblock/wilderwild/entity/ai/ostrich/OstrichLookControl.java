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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.world.entity.ai.control.LookControl;

public class OstrichLookControl extends LookControl {
	private final Ostrich mob;

	public OstrichLookControl(Ostrich ostrich) {
		super(ostrich);
		this.mob = ostrich;
	}

	@Override
	public void tick() {
		if (!this.mob.hasControllingPassenger()) {
			super.tick();
		}
	}

}
