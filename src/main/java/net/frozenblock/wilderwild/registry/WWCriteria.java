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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.advancements.trigger.FragileIceFallOntoAndBreakTrigger;
import net.frozenblock.wilderwild.advancements.trigger.GeyserPushMobTrigger;
import net.frozenblock.wilderwild.advancements.trigger.MobBottleTrigger;
import net.frozenblock.wilderwild.advancements.trigger.TermiteEatTrigger;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class WWCriteria {
	public static final MobBottleTrigger MOB_BOTTLE = register("mob_bottle", new MobBottleTrigger());
	public static final TermiteEatTrigger TERMITE_EAT = register("termite_eat", new TermiteEatTrigger());
	public static final FragileIceFallOntoAndBreakTrigger FRAGILE_ICE_FAL_ONTO_AND_BREAK = register("fragile_ice_fall_onto_and_break", new FragileIceFallOntoAndBreakTrigger());
	public static final GeyserPushMobTrigger GEYSER_PUSH_MOB_TRIGGER = register("geyser_push_mob", new GeyserPushMobTrigger());

	private WWCriteria() {
		throw new UnsupportedOperationException("WWCriteria contains only static declarations.");
	}

	public static void init() {}

	private static <T extends CriterionTrigger<?>> T register(String name, T criterion) {
		return Registry.register(BuiltInRegistries.TRIGGER_TYPES, WWConstants.id(name), criterion);
	}

}
