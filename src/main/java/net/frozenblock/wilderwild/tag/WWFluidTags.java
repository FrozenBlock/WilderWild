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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public final class WWFluidTags {
	public static final TagKey<Fluid> SUPPORTS_ALGAE = bind("supports_algae");
	public static final TagKey<Fluid> SUPPORTS_PLANKTON = bind("supports_plankton");

	private WWFluidTags() {
		throw new UnsupportedOperationException("WWFluidTags contains only static declarations.");
	}

	private static TagKey<Fluid> bind(String path) {
		return TagKey.create(Registries.FLUID, WWConstants.id(path));
	}
}
