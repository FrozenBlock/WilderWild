/*
 * Copyright 2026 FrozenBlock
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

import net.frozenblock.lib.levelgen.biome.api.modifications.data.selector.BiomeSelector;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public final class WWBiomeSelectors {
	public static final ResourceKey<BiomeSelector> GENERATE_FLOWER = createKey("generate_flower");

	public static void bootstrap(BootstrapContext<BiomeSelector> context) {
		context.register(GENERATE_FLOWER, BiomeSelector.configPredicate(context, WWConfigPredicates.GENERATE_FLOWER));
	}

	private static ResourceKey<BiomeSelector> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.BIOME_SELECTOR, WWConstants.id(name));
	}

	private WWBiomeSelectors() {}
}
