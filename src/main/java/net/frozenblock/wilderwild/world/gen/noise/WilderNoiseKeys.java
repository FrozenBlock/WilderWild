package net.frozenblock.wilderwild.world.gen.noise;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;


public class WilderNoiseKeys {
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SAND_BEACH = register("sand_beach");
    public WilderNoiseKeys() {
}
    private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> register(String id) {
        return RegistryKey.of(Registry.NOISE_KEY, WilderWild.id(id));
    }
}

