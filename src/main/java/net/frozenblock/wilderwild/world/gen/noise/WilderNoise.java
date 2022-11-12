package net.frozenblock.wilderwild.world.gen.noise;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;


public class WilderNoise {
    public static final ResourceKey<NormalNoise.NoiseParameters> SAND_BEACH_KEY = createKey("sand_beach");
    public static final ResourceKey<NormalNoise.NoiseParameters> GRAVEL_BEACH_KEY = createKey("gravel_beach");

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
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
	}

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String id) {
        return ResourceKey.create(Registry.NOISE_REGISTRY, WilderSharedConstants.id(id));
    }

	public static void register(
			FabricWorldgenProvider.Entries entries,
			ResourceKey<NormalNoise.NoiseParameters> key,
			int firstOctave,
			double firstAmplitude,
			double... amplitudes
	) {
		entries.add(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
	}
}

