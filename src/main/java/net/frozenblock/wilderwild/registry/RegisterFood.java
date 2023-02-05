/*
 * Copyright 2022-2023 FrozenBlock
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

import net.minecraft.world.food.FoodProperties;

public final class RegisterFood {
	private RegisterFood() {
		throw new UnsupportedOperationException("RegisterFood contains only static declarations.");
	}

    public static final FoodProperties BAOBAB_NUT = new FoodProperties.Builder().nutrition(1).saturationMod(0.2F).build();
    public static final FoodProperties PRICKLY_PEAR = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build();

}
