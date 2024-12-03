/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.datagen.loot;

import com.google.common.collect.Maps;
import java.util.Map;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.Util;
import net.minecraft.world.level.ItemLike;

public class WWLootData {
	static Map<JellyfishVariant, ItemLike> NEMATOCYST_BY_VARIANT = Util.make(Maps.newEnumMap(JellyfishVariant.class), map -> {
		map.put(JellyfishVariant.BLUE, WWBlocks.BLUE_NEMATOCYST);
		map.put(JellyfishVariant.LIME, WWBlocks.LIME_NEMATOCYST);
		map.put(JellyfishVariant.PINK, WWBlocks.PINK_NEMATOCYST);
		map.put(JellyfishVariant.RED, WWBlocks.RED_NEMATOCYST);
		map.put(JellyfishVariant.YELLOW, WWBlocks.YELLOW_NEMATOCYST);

		map.put(JellyfishVariant.PEARLESCENT_BLUE, WWBlocks.BLUE_PEARLESCENT_NEMATOCYST);
		map.put(JellyfishVariant.PEARLESCENT_PURPLE, WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST);
	});
}
