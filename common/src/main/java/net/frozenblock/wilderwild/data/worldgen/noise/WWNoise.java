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
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class WWNoise {
	public static final ResourceKey<NormalNoise> SAND_BEACH = createKey("sand_beach");
	public static final ResourceKey<NormalNoise> GRAVEL_BEACH = createKey("gravel_beach");
	public static final ResourceKey<NormalNoise> MAGMATIC_CAVE_GRADIENT = createKey("magmatic_cave_gradient");
	public static final ResourceKey<NormalNoise> OCEAN_MOSS = createKey("ocean_moss");

	public static void bootstrap(BootstrapContext<NormalNoise> entries) {
		register(entries, SAND_BEACH, -9,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			40.0,
			20.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0
		);
		register(entries, GRAVEL_BEACH, -9,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			1.0,
			40.0,
			20.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0,
			10.0
		);
		register(entries, MAGMATIC_CAVE_GRADIENT, -6, 0D, 3D, 5D, 2D);
		register(entries, OCEAN_MOSS, -4, 0D, 2D, 2D, 1D);
	}

	private static ResourceKey<NormalNoise> createKey(String name) {
		return ResourceKey.create(Registries.NOISE, WWConstants.id(name));
	}

	public static Holder.Reference<NormalNoise> register(
		BootstrapContext<NormalNoise> entries,
		ResourceKey<NormalNoise> id,
		int firstOctave,
		double firstAmplitude,
		double... amplitudes
	) {
		WWConstants.log("Registering noise " + id.identifier(), true);

		final double[] allAmplitudes = new double[amplitudes.length + 1];
		allAmplitudes[0] = firstAmplitude;
		System.arraycopy(amplitudes, 0, allAmplitudes, 1, amplitudes.length);
		return entries.register(id, NormalNoise.createParity(firstOctave, allAmplitudes));
	}
}

