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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.advancement.FragileIceFallOntoAndBreakTrigger;
import net.frozenblock.wilderwild.advancement.GeyserPushMobTrigger;
import net.frozenblock.wilderwild.advancement.MobBottleTrigger;
import net.frozenblock.wilderwild.advancement.TermiteEatTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public final class WWCriteria {
	public static final MobBottleTrigger MOB_BOTTLE = CriteriaTriggers.register(WWConstants.string("mob_bottle"), new MobBottleTrigger());
	public static final TermiteEatTrigger TERMITE_EAT = CriteriaTriggers.register(WWConstants.string("termite_eat"), new TermiteEatTrigger());
	public static final FragileIceFallOntoAndBreakTrigger FRAGILE_ICE_FAL_ONTO_AND_BREAK = CriteriaTriggers.register(WWConstants.string("fragile_ice_fall_onto_and_break"), new FragileIceFallOntoAndBreakTrigger());
	public static final GeyserPushMobTrigger GEYSER_PUSH_MOB_TRIGGER = CriteriaTriggers.register(WWConstants.string("geyser_push_mob"), new GeyserPushMobTrigger());

	private WWCriteria() {
		throw new UnsupportedOperationException("WWCriteria contains only static declarations.");
	}

	public static void init() {
	}

}
