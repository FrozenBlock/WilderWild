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
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;

public final class WWEnvironmentAttributes {
	public static final EnvironmentAttribute<Boolean> PALE_MUSHROOM_ACTIVE = register(
		"gameplay/pale_mushroom_active",
		EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false).syncable()
	);
	public static final EnvironmentAttribute<Boolean> PLANKTON_GLOWING = register(
		"gameplay/plankton_glowing",
		EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false)
	);
	public static final EnvironmentAttribute<Boolean> SEA_ANEMONE_GLOWING = register(
		"gameplay/sea_anemone_glowing",
		EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false)
	);

	private WWEnvironmentAttributes() {
		throw new UnsupportedOperationException("WWEnvironmentAttributes contains only static declarations.");
	}

	public static void init() {
	}

	private static <Value> EnvironmentAttribute<Value> register(String string, EnvironmentAttribute.Builder<Value> builder) {
		EnvironmentAttribute<Value> environmentAttribute = builder.build();
		Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, WWConstants.id(string), environmentAttribute);
		return environmentAttribute;
	}
}
