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

package net.frozenblock.wilderwild.worldgen.impl.noise;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class WWNoise {
	public static final ResourceKey<NormalNoise.NoiseParameters> SAND_BEACH_KEY = createKey("sand_beach");
	public static final ResourceKey<NormalNoise.NoiseParameters> GRAVEL_BEACH_KEY = createKey("gravel_beach");
	public static final ResourceKey<NormalNoise.NoiseParameters> TUNDRA_NOISE_KEY = createKey("tundra_noise");

	public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> entries) {
		register(entries, SAND_BEACH_KEY, -9,
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
		register(entries, GRAVEL_BEACH_KEY, -9,
			-1.0,
			-1.0,
			-1.0,
			-1.0,
			-1.0,
			-1.0,
			-1.0,
			-1.0,
			-1.0,
			-1.0,
			-40.0,
			-20.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0,
			-10.0
		);
		register(entries, TUNDRA_NOISE_KEY, -1,
			-1.0,
			1.0,
			1.0
		);
	}

	private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
		return ResourceKey.create(Registries.NOISE, WWConstants.id(name));
	}

	public static Holder.Reference<NormalNoise.NoiseParameters> register(
		BootstrapContext<NormalNoise.NoiseParameters> entries,
		ResourceKey<NormalNoise.NoiseParameters> id,
		int firstOctave,
		double firstAmplitude,
		double... amplitudes
	) {
		WWConstants.log("Registering noise " + id.identifier(), true);
		return entries.register(id, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
	}
}

