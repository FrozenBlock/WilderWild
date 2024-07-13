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

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class WilderRegistry {
	public static final ResourceKey<Registry<FireflyColor>> FIREFLY_COLOR_REGISTRY = ResourceKey.createRegistryKey(WilderConstants.id("firefly_color"));
	public static final ResourceKey<Registry<JellyfishVariant>> JELLYFISH_VARIANT_REGISTRY = ResourceKey.createRegistryKey(WilderConstants.id("jellyfish_color"));
	public static final MappedRegistry<FireflyColor> FIREFLY_COLOR = FabricRegistryBuilder.createSimple(FIREFLY_COLOR_REGISTRY)
		.attribute(RegistryAttribute.SYNCED)
		.buildAndRegister();
	public static final MappedRegistry<JellyfishVariant> JELLYFISH_VARIANT = FabricRegistryBuilder.createSimple(JELLYFISH_VARIANT_REGISTRY)
		.attribute(RegistryAttribute.SYNCED)
		.buildAndRegister();

	private WilderRegistry() {
		throw new UnsupportedOperationException("WilderRegistry contains only static declarations.");
	}

	public static void initRegistry() {
		FireflyColor.init();
		JellyfishVariant.init();
	}
}
