package net.frozenblock.wilderwild.world.gen.noise;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;


public class WilderNoise {
    public static final ResourceKey<NormalNoise.NoiseParameters> SAND_BEACH = register("sand_beach");
    public static final ResourceKey<NormalNoise.NoiseParameters> GRAVEL_BEACH = register("gravel_beach");

    public static void init() {
        if (ClothConfigInteractionHandler.betaBeaches()) {
            NoiseData.register(BuiltinRegistries.NOISE, SAND_BEACH, -9,
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
    }

    private static ResourceKey<NormalNoise.NoiseParameters> register(String id) {
        return ResourceKey.create(Registry.NOISE_REGISTRY, WilderWild.id(id));
    }
}

