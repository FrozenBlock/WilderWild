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

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.MappedRegistry;

public final class WilderRegistry {
	private WilderRegistry() {
		throw new UnsupportedOperationException("WilderRegistry contains only static declarations.");
	}

    public static final MappedRegistry<FireflyColor> FIREFLY_COLOR = FabricRegistryBuilder.createSimple(FireflyColor.class, WilderSharedConstants.id("firefly_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
    public static final MappedRegistry<JellyfishVariant> JELLYFISH_VARIANT = FabricRegistryBuilder.createSimple(JellyfishVariant.class, WilderSharedConstants.id("jellyfish_color"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static void initRegistry() {
    }
}
