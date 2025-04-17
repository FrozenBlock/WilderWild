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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;
import org.jetbrains.annotations.NotNull;

public final class WWInstrumentTags {
	public static final TagKey<Instrument> COPPER_HORNS = bind("copper_horns");

	private WWInstrumentTags() {
		throw new UnsupportedOperationException("WWInstrumentTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<Instrument> bind(@NotNull String path) {
		return TagKey.create(Registries.INSTRUMENT, WWConstants.id(path));
	}
}
