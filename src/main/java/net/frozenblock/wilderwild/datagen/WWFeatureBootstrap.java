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

package net.frozenblock.wilderwild.datagen;

import net.frozenblock.lib.worldgen.feature.api.FrozenLibFeatureUtils;
import net.frozenblock.wilderwild.worldgen.features.configured.WWAquaticConfigured;
import net.frozenblock.wilderwild.worldgen.features.configured.WWCaveConfigured;
import net.frozenblock.wilderwild.worldgen.features.configured.WWConfiguredFeatures;
import net.frozenblock.wilderwild.worldgen.features.configured.WWMiscConfigured;
import net.frozenblock.wilderwild.worldgen.features.configured.WWTreeConfigured;
import net.frozenblock.wilderwild.worldgen.features.placed.WWAquaticPlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWCavePlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWPlacedFeatures;
import net.frozenblock.wilderwild.worldgen.features.placed.WWTreePlaced;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

public final class WWFeatureBootstrap {

	public static void bootstrapConfigured(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		FrozenLibFeatureUtils.BOOTSTRAP_CONTEXT = (BootstrapContext) entries;

		WWTreeConfigured.registerTreeConfigured(entries);
		WWMiscConfigured.registerMiscConfigured();
		WWCaveConfigured.registerCaveConfigured(entries);
		WWAquaticConfigured.registerAquaticConfigured();
		WWConfiguredFeatures.registerConfiguredFeatures(entries);
	}

	public static void bootstrapPlaced(@NotNull BootstrapContext<PlacedFeature> entries) {
		FrozenLibFeatureUtils.BOOTSTRAP_CONTEXT = (BootstrapContext) entries;

		WWTreePlaced.registerTreePlaced();
		WWMiscPlaced.registerMiscPlaced(entries);
		WWCavePlaced.registerCavePlaced(entries);
		WWAquaticPlaced.registerAquaticPlaced(entries);
		WWPlacedFeatures.registerPlacedFeatures(entries);
	}
}
