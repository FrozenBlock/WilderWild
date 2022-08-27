package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.misc.config.ModMenuInteractionHandler;
import net.frozenblock.wilderwild.world.gen.noise.WilderNoiseKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(NoiseData.class)
public class NoiseDataMixin {

    @Shadow
    private static Holder<NormalNoise.NoiseParameters> register(Registry<NormalNoise.NoiseParameters> registry, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        return null;
    }

    @Inject(method = "bootstrap", at = @At("RETURN"))
    private static void bootstrap(Registry<NormalNoise.NoiseParameters> registry, CallbackInfoReturnable<Holder<NormalNoise.NoiseParameters>> cir) {
        if (ModMenuInteractionHandler.betaBeaches()) {
            register(registry, WilderNoiseKeys.SAND_BEACH, -9,
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
}
