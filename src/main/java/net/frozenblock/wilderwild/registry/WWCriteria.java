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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.advancement.FragileIceFallOntoAndBreakTrigger;
import net.frozenblock.wilderwild.advancement.MobBottleTrigger;
import net.frozenblock.wilderwild.advancement.TermiteEatTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public final class WWCriteria {
	public static final MobBottleTrigger MOB_BOTTLE = CriteriaTriggers.register(WWConstants.string("mob_bottle"), new MobBottleTrigger());
	public static final TermiteEatTrigger TERMITE_EAT = CriteriaTriggers.register(WWConstants.string("termite_eat"), new TermiteEatTrigger());
	public static final FragileIceFallOntoAndBreakTrigger FRAGILE_ICE_FAL_ONTO_AND_BREAK = CriteriaTriggers.register(WWConstants.string("fragile_ice_fall_onto_and_break"), new FragileIceFallOntoAndBreakTrigger());

	private WWCriteria() {
		throw new UnsupportedOperationException("WWCriteria contains only static declarations.");
	}

	public static void init() {
	}

}
