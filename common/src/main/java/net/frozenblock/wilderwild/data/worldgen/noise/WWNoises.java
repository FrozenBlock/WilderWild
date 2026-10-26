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

package net.frozenblock.wilderwild.data.worldgen.noise;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class WWNoises {
	public static final ResourceKey<NormalNoise> SAND_BEACH = createKey("sand_beach");
	public static final ResourceKey<NormalNoise> GRAVEL_BEACH = createKey("gravel_beach");
	public static final ResourceKey<NormalNoise> MAGMATIC_CAVE_GRADIENT = createKey("magmatic_cave_gradient");
	public static final ResourceKey<NormalNoise> OCEAN_MOSS = createKey("ocean_moss");
	public static final ResourceKey<NormalNoise> OASIS_GRASS = createKey("oasis_grass");
	public static final ResourceKey<NormalNoise> JUNGLE_MOSS_PATH = createKey("jungle_moss_path");
	public static final ResourceKey<NormalNoise> JUNGLE_MOSS_PATH_SELECTOR = createKey("jungle_moss_path_selector");
	public static final ResourceKey<NormalNoise> COARSE_DIRT_PATH = createKey("coarse_dirt_path");
	public static final ResourceKey<NormalNoise> COARSE_DIRT_PATH_SELECTOR = createKey("coarse_dirt_path_selector");
	public static final ResourceKey<NormalNoise> COARSE_DIRT_SELECTOR_PATCHY = createKey("coarse_dirt_selector_patchy");
	public static final ResourceKey<NormalNoise> SCORCHED_SAND = createKey("scorched_sand");
	public static final ResourceKey<NormalNoise> SCORCHED_SAND_SELECTOR = createKey("scorched_sand_selector");

	public static void bootstrap(BootstrapContext<NormalNoise> context) {

		registerBetaBeach(context, SAND_BEACH);
		registerBetaBeach(context, GRAVEL_BEACH);
		NoiseData.register(context, MAGMATIC_CAVE_GRADIENT, -6, 0D, 3D, 5D, 2D);
		NoiseData.register(context, OCEAN_MOSS, -4, 0D, 2D, 2D, 1D);
		NoiseData.register(context, OASIS_GRASS, -6, 0D, 1D, 6D, 7D, 4D, 7D);
		NoiseData.register(context, JUNGLE_MOSS_PATH, -6, 2D, 6D, 5D, 4D, 6D, 8D);
		NoiseData.register(context, JUNGLE_MOSS_PATH_SELECTOR, -6, 5D, 3D);
		NoiseData.register(context, COARSE_DIRT_PATH, -8, 0D, 0D, 5D, 10D, 20D, 25D, 30D);
		NoiseData.register(context, COARSE_DIRT_PATH_SELECTOR, -6, 0D, 1D, 1D, 1D, 2D, 2D);
		NoiseData.register(context, COARSE_DIRT_SELECTOR_PATCHY, -6, 0D, 0D, 0D, 3D, 2D, 2D);
		NoiseData.register(context, SCORCHED_SAND_SELECTOR, -7, 1.25D);
	}

	private static void registerBetaBeach(BootstrapContext<NormalNoise> context, ResourceKey<NormalNoise> key) {
		NoiseData.register(
			context,
			key,
			-9,
			1D,
			1D,
			1D,
			1D,
			1D,
			1D,
			1D,
			1D,
			1D,
			1D,
			40D,
			20D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D,
			10D
		);
	}

	private static ResourceKey<NormalNoise> createKey(String name) {
		return ResourceKey.create(Registries.NOISE, WWConstants.id(name));
	}
}

