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

package net.frozenblock.wilderwild.data.sound;

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.sound.api.damage.PlayerDamageTypeSounds;
import net.frozenblock.lib.sound.impl.damage.PlayerDamageTypeSound;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

public final class WWPlayerDamageTypeSounds {

	public static void bootstrap(BootstrapContext<PlayerDamageTypeSound> context) {
		final HolderGetter<DamageType> damageTypes = context.lookup(Registries.DAMAGE_TYPE);

		PlayerDamageTypeSounds.register(
			context,
			WWConstants.id("cactus"),
			HolderSet.direct(damageTypes.getOrThrow(DamageTypes.CACTUS)),
			WWSounds.PLAYER_HURT_CACTUS.asHolder(),
			ConfigPredicate.equalTo(WWBlockConfig.CACTUS_SOUNDS, true)
		);
	}
}
