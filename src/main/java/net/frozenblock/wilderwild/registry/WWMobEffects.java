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

import net.frozenblock.lib.block.api.beacon.BeaconEffectRegistry;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.effect.ScorchingMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public final class WWMobEffects {

	public static final Reference<MobEffect> REACH_BOOST = register(
		"reach_boost",
		new MobEffect(
			MobEffectCategory.BENEFICIAL,
			47784
		).addAttributeModifier(
			Attributes.BLOCK_INTERACTION_RANGE,
			"DA6D90D0-722B-11EE-87D4-325096B39F47",
			1.5D,
			AttributeModifier.Operation.ADD_VALUE
		).addAttributeModifier(
			Attributes.ENTITY_INTERACTION_RANGE,
			"F2439145-BA25-4DEC-B11A-218B56EF22BB",
			WWEntityConfig.get().crab.reachAffectsAttack ? 1.5D : 0D,
			AttributeModifier.Operation.ADD_VALUE
		)
	);

	public static final Holder<MobEffect> SCORCHING = register(
		"scorching",
		new ScorchingMobEffect(
			MobEffectCategory.HARMFUL,
			6236672,
			0.25F,
			random -> Mth.randomBetweenInclusive(random, 1, 6),
			random -> Mth.randomBetweenInclusive(random, 1, 4)
		)
	);

	private WWMobEffects() {
		throw new UnsupportedOperationException("WWMobEffects contains only static declarations.");
	}

	public static void init() {
		WWConstants.logWithModId("Registering MobEffects for", WWConstants.UNSTABLE_LOGGING);
		BeaconEffectRegistry.register(WWMobEffects.REACH_BOOST, 3);
	}

	private static @NotNull Reference<MobEffect> register(String id, MobEffect entry) {
		return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, WWConstants.id(id), entry);
	}
}
