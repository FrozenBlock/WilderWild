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

package net.frozenblock.wilderwild.data;

import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeatureUtil;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWCaveConfigured;
import net.frozenblock.wilderwild.data.worldgen.features.WWVegetationFeatures;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWMiscConfigured;
import net.frozenblock.wilderwild.data.worldgen.feature.placed.WWCavePlaced;
import net.frozenblock.wilderwild.data.worldgen.feature.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.data.worldgen.features.WWAquaticFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWCaveFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWFlowerFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeVegetationFeatures;
import net.frozenblock.wilderwild.data.worldgen.placement.WWAquaticPlacements;
import net.frozenblock.wilderwild.data.worldgen.placement.WWCavePlacements;
import net.frozenblock.wilderwild.data.worldgen.placement.WWFlowerPlacements;
import net.frozenblock.wilderwild.data.worldgen.placement.WWTreePlacements;
import net.frozenblock.wilderwild.data.worldgen.placement.WWTreeVegetationPlacements;
import net.frozenblock.wilderwild.data.worldgen.placement.WWVegetationPlacements;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WWFeatureBootstrap {

	public static void bootstrapConfigured(BootstrapContext<Feature> context) {
		FrozenLibFeatureUtil.BOOTSTRAP_CONTEXT = (BootstrapContext) context;

		WWAquaticFeatures.bootstrap(context);
		WWCaveFeatures.bootstrap(context);
		WWFlowerFeatures.bootstrap(context);
		WWTreeFeatures.bootstrap(context);
		WWTreeVegetationFeatures.bootstrap(context);
		WWVegetationFeatures.bootstrap(context);

		WWMiscConfigured.registerMiscConfigured(context);
		WWCaveConfigured.registerCaveConfigured(context);
	}

	public static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
		FrozenLibFeatureUtil.BOOTSTRAP_CONTEXT = (BootstrapContext) context;

		WWAquaticPlacements.bootstrap(context);
		WWCavePlacements.bootstrap(context);
		WWFlowerPlacements.bootstrap(context);
		WWTreePlacements.bootstrap(context);
		WWTreeVegetationPlacements.bootstrap(context);
		WWVegetationPlacements.bootstrap(context);

		WWMiscPlaced.registerMiscPlaced(context);
		WWCavePlaced.registerCavePlaced(context);
	}

	private WWFeatureBootstrap() {}
}
