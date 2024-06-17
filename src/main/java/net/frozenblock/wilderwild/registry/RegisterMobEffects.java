/*
 * Copyright 2023-2024 FrozenBlock
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

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.effect.ScorchingMobEffect;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public final class RegisterMobEffects {

	public static final MobEffect REACH_BOOST = register(
		"reach_boost",
		new MobEffect(
			MobEffectCategory.BENEFICIAL,
			47784
		).addAttributeModifier(
			ReachEntityAttributes.REACH,
			"DA6D90D0-722B-11EE-87D4-325096B39F47",
			1,
			AttributeModifier.Operation.ADDITION
		).addAttributeModifier(
			ReachEntityAttributes.ATTACK_RANGE,
			"F2439145-BA25-4DEC-B11A-218B56EF22BB",
			EntityConfig.get().crab.reachAffectsAttack ? 1D : 0D,
			AttributeModifier.Operation.ADDITION
		)
	);

	public static final MobEffect SCORCHING = register(
		"scorching",
		new ScorchingMobEffect(
			MobEffectCategory.HARMFUL,
			6236672,
			0.25F,
			random -> Mth.randomBetweenInclusive(random, 1, 4)
		)
	);

	private RegisterMobEffects() {
		throw new UnsupportedOperationException("RegisterMobEffects contains only static declarations.");
	}

	public static void init() {
		WilderSharedConstants.logWithModId("Registering MobEffects for", WilderSharedConstants.UNSTABLE_LOGGING);
	}

	private static @NotNull MobEffect register(String id, MobEffect entry) {
		return Registry.register(BuiltInRegistries.MOB_EFFECT, WilderSharedConstants.id(id), entry);
	}
}
