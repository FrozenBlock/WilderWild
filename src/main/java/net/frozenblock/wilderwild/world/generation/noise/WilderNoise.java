package net.frozenblock.wilderwild.world.generation.noise;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;


public class WilderNoise {
    public static final ResourceKey<NormalNoise.NoiseParameters> SAND_BEACH_KEY = register("sand_beach");
    public static final ResourceKey<NormalNoise.NoiseParameters> GRAVEL_BEACH_KEY = register("gravel_beach");

    public static final Holder<NormalNoise.NoiseParameters> SAND_BEACH = NoiseData.register(BuiltinRegistries.NOISE, SAND_BEACH_KEY, -9,
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

    public static final Holder<NormalNoise.NoiseParameters> GRAVEL_BEACH = NoiseData.register(BuiltinRegistries.NOISE, GRAVEL_BEACH_KEY, -9,
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

    public static void init() {

    }

    private static ResourceKey<NormalNoise.NoiseParameters> register(String id) {
        return ResourceKey.create(Registry.NOISE_REGISTRY, WilderSharedConstants.id(id));
    }
}

