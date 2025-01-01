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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;

public final class WWDamageTypeTags {
	public static final TagKey<DamageType> EMPTY = bind("empty");

	private WWDamageTypeTags() {
		throw new UnsupportedOperationException("WilderDamageTypeTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<DamageType> bind(@NotNull String path) {
		return TagKey.create(Registries.DAMAGE_TYPE, WWConstants.id(path));
	}
}
