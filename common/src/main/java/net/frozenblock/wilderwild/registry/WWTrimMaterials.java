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

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;

public final class WWTrimMaterials {
	public static final ResourceKey<TrimMaterial> PEARL = createKey("pearl");

	public static void bootstrap(BootstrapContext<TrimMaterial> context) {
		TrimMaterials.register(context, PEARL, Style.EMPTY.withColor(ARGB.color(0, 189, 150, 168)), WWPalettes.WILDERWILD_PEARL);
	}

	private static ResourceKey<TrimMaterial> createKey(String name) {
		return ResourceKey.create(Registries.TRIM_MATERIAL, WWConstants.id(name));
	}

	public static final class WWPalettes {
		public static TrimMaterials.Palette WILDERWILD_PEARL;

		static {
			TrimMaterials.Palette.values();
		}

		private WWPalettes() {}
	}

	private WWTrimMaterials() {}
}
