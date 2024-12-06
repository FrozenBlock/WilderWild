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

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;

public final class WWDamageTypes {
	public static final ResourceKey<DamageType> PRICKLY_PEAR = bind("prickly_pear");
	public static final ResourceKey<DamageType> TUMBLEWEED = bind("tumbleweed");
	public static final ResourceKey<DamageType> OSTRICH = bind("ostrich");

	public static void init() {
	}

	public static void bootstrap(@NotNull BootstrapContext<DamageType> context) {
		context.register(PRICKLY_PEAR, new DamageType("prickly_pear", 0.1F));
		context.register(TUMBLEWEED, new DamageType("tumbleweed", DamageScaling.ALWAYS, 0.1F));
		context.register(OSTRICH, new DamageType("ostrich", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F));
	}

	@NotNull
	private static ResourceKey<DamageType> bind(@NotNull String path) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, WWConstants.id(path));
	}
}
