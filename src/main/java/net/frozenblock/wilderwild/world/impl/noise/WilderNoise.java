/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.world.impl.noise;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;


public class WilderNoise {
	public static final ResourceKey<NormalNoise.NoiseParameters> SAND_BEACH_KEY = createKey("sand_beach");
	public static final ResourceKey<NormalNoise.NoiseParameters> GRAVEL_BEACH_KEY = createKey("gravel_beach");

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
	}

	@NotNull
	private static ResourceKey<NormalNoise.NoiseParameters> createKey(String id) {
		return ResourceKey.create(Registries.NOISE, WilderSharedConstants.id(id));
	}

	@NotNull
	public static Holder.Reference<NormalNoise.NoiseParameters> register(
		@NotNull BootstrapContext<NormalNoise.NoiseParameters> entries,
		@NotNull ResourceKey<NormalNoise.NoiseParameters> key,
		int firstOctave,
		double firstAmplitude,
		double... amplitudes
	) {
		WilderSharedConstants.log("Registering noise " + key.location(), true);
		return entries.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
	}
}

