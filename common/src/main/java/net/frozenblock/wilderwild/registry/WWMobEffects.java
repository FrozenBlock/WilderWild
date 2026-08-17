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

import net.frozenblock.lib.block.api.beacon.BeaconEffectRegistry;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.effect.ScorchingMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class WWMobEffects {
	private static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(
		Registries.MOB_EFFECT,
		WWConstants.MOD_ID
	);

	public static final DeferredHolder<MobEffect, MobEffect> REACH_BOOST = REGISTER.register(
		"reach_boost",
		() -> new MobEffect(
			MobEffectCategory.BENEFICIAL,
			47784
		).addAttributeModifier(
			Attributes.BLOCK_INTERACTION_RANGE,
			Crab.BLOCK_REACH_BOOST_MODIFIER_ID,
			1.5D,
			AttributeModifier.Operation.ADD_VALUE
		).addAttributeModifier(
			Attributes.ENTITY_INTERACTION_RANGE,
			Crab.ENTITY_REACH_BOOST_MODIFIER_ID,
			WWEntityConfig.CRAB_REACH_AFFECTS_ATTACK.get() ? 1.5D : 0D,
			AttributeModifier.Operation.ADD_VALUE
		)
	);

	public static final DeferredHolder<MobEffect, MobEffect> SCORCHING = REGISTER.register(
		"scorching",
		() -> new ScorchingMobEffect(
			MobEffectCategory.HARMFUL,
			6236672,
			0.25F,
			random -> Mth.randomBetweenInclusive(random, 1, 6),
			random -> Mth.randomBetweenInclusive(random, 1, 4)
		)
	);

	static {
		REGISTER.register();
	}

	public static void init() {
		BeaconEffectRegistry.register(WWMobEffects.REACH_BOOST.asHolder(), 3);
	}

	private WWMobEffects() {}
}
