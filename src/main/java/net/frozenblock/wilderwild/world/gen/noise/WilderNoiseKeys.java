package net.frozenblock.wilderwild.world.gen.noise;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;


public class WilderNoiseKeys {
    public static final ResourceKey<NormalNoise.NoiseParameters> SAND_BEACH = register("sand_beach");
    public static final ResourceKey<NormalNoise.NoiseParameters> GRAVEL_BEACH = register("gravel_beach");

    public static void init() {
    }

    private static ResourceKey<NormalNoise.NoiseParameters> register(String id) {
        return ResourceKey.create(Registry.NOISE_REGISTRY, WilderWild.id(id));
    }
}

