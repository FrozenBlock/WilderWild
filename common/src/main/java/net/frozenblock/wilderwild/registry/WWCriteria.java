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

import java.util.function.Supplier;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.advancements.trigger.FragileIceFallOntoAndBreakTrigger;
import net.frozenblock.wilderwild.advancements.trigger.GeothermalVentPushMobTrigger;
import net.frozenblock.wilderwild.advancements.trigger.MobBottleTrigger;
import net.frozenblock.wilderwild.advancements.trigger.TermiteEatTrigger;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public final class WWCriteria {
	private static final FrozenDeferredRegister<CriterionTrigger<?>> REGISTER = FrozenDeferredRegister.create(
		Registries.TRIGGER_TYPE,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<CriterionTrigger<?>, MobBottleTrigger> MOB_BOTTLE = register("mob_bottle", MobBottleTrigger::new);
	public static final FrozenHolder<CriterionTrigger<?>, TermiteEatTrigger> TERMITE_EAT = register("termite_eat", TermiteEatTrigger::new);
	public static final FrozenHolder<CriterionTrigger<?>, FragileIceFallOntoAndBreakTrigger> FRAGILE_ICE_FAL_ONTO_AND_BREAK = register("fragile_ice_fall_onto_and_break", FragileIceFallOntoAndBreakTrigger::new);
	public static final FrozenHolder<CriterionTrigger<?>, GeothermalVentPushMobTrigger> GEOTHERMAL_VENT_PUSH_MOB_TRIGGER = register("geothermal_vent_push_mob", GeothermalVentPushMobTrigger::new);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <T extends CriterionTrigger<?>> FrozenHolder<CriterionTrigger<?>, T> register(String name, Supplier<T> criterion) {
		return REGISTER.register(name, criterion);
	}
}
