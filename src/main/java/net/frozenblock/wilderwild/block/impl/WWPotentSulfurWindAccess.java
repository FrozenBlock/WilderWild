/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.block.impl;

import net.minecraft.world.phys.AABB;

/**
 * Mixed into {@code PotentSulfurBlockEntity} so {@code GeyserWindDisturbance} can tell whether
 * the geyser is actively spouting right now, without depending on vanilla's internal fields.
 *
 * <p> Vanilla's particle-area AABB lambda only runs while the geyser is actively spouting, so
 * {@code PotentSulfurBlockEntityMixin} pings this access point every time that lambda fires.
 */
public interface WWPotentSulfurWindAccess {
	void wilderWild$pingWindActive(AABB area, long gameTime);

	AABB wilderWild$getWindArea();

	boolean wilderWild$isWindActive(long currentGameTime);
}
