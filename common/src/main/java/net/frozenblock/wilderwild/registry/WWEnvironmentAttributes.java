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
import net.minecraft.core.registries.Registries;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;

public final class WWEnvironmentAttributes {
	private static final FrozenDeferredRegister<EnvironmentAttribute<?>> REGISTER = FrozenDeferredRegister.create(
		Registries.ENVIRONMENT_ATTRIBUTE,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Boolean>> PALE_MUSHROOM_ACTIVE = register(
		"gameplay/pale_mushroom_active",
		() -> EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false).syncable()
	);
	public static final FrozenHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Boolean>> PLANKTON_GLOWING = register(
		"gameplay/plankton_glowing",
		() -> EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false)
	);
	public static final FrozenHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Boolean>> SEA_ANEMONE_GLOWING = register(
		"gameplay/sea_anemone_glowing",
		() -> EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false)
	);
	public static final FrozenHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Float>> OSTRICH_EGG_HATCH_CHANCE = register(
		"gameplay/ostrich_egg_hatch_chance",
		() -> EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(1F / 18F)
	);
	public static final FrozenHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Float>> PENGUIN_EGG_HATCH_CHANCE = register(
		"gameplay/penguin_egg_hatch_chance",
		() -> EnvironmentAttribute.builder(AttributeTypes.FLOAT).defaultValue(1F / 30F)
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <Value> FrozenHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Value>> register(String name, Supplier<EnvironmentAttribute.Builder<Value>> builder) {
		return REGISTER.register(name, () -> builder.get().build());
	}
}
