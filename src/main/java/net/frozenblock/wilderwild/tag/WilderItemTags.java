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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public final class WilderItemTags {
	public static final TagKey<Item> GOAT_DROP_MUSIC_DISCS = bind("goat_drop_music_discs");
	public static final TagKey<Item> TUMBLEWEED_COMMON = bind("tumbleweed_common");
	public static final TagKey<Item> TUMBLEWEED_MEDIUM = bind("tumbleweed_medium");
	public static final TagKey<Item> TUMBLEWEED_RARE = bind("tumbleweed_rare");
	private WilderItemTags() {
		throw new UnsupportedOperationException("WilderItemTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<Item> bind(@NotNull String path) {
		return TagKey.create(Registries.ITEM, WilderSharedConstants.id(path));
	}
}
