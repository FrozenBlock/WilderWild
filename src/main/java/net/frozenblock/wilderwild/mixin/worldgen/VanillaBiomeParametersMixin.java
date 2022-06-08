package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public final class VanillaBiomeParametersMixin {
    @Shadow
    @Final
    private MultiNoiseUtil.ParameterRange riverContinentalness;
    @Shadow
    @Final
    private MultiNoiseUtil.ParameterRange farInlandContinentalness;
    @Shadow
    @Final
    private MultiNoiseUtil.ParameterRange[] erosionParameters;
    @Shadow
    @Final
    private MultiNoiseUtil.ParameterRange nearInlandContinentalness;
    @Shadow
    @Final
    private MultiNoiseUtil.ParameterRange[] temperatureParameters;
    @Shadow
    @Final
    private MultiNoiseUtil.ParameterRange defaultParameter;
    @Shadow
    @Final
    private RegistryKey<Biome>[][] uncommonBiomes;

    private void writeBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, final float offset, RegistryKey<Biome> biome) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.0F, 1.0F), weirdness, offset), biome));
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectBiomes(CallbackInfo ci) {
        uncommonBiomes[1][0] = RegisterWorldgen.MIXED_FOREST;
        uncommonBiomes[1][1] = RegisterWorldgen.CYPRESS_FOREST;
    }


    private void method_41419(
            Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> consumer,
            MultiNoiseUtil.ParameterRange parameterRange,
            MultiNoiseUtil.ParameterRange parameterRange2,
            MultiNoiseUtil.ParameterRange parameterRange3,
            MultiNoiseUtil.ParameterRange parameterRange4,
            MultiNoiseUtil.ParameterRange parameterRange5,
            float f,
            RegistryKey<Biome> registryKey
    ) {
        consumer.accept(
                Pair.of(
                        MultiNoiseUtil.createNoiseHypercube(
                                parameterRange, parameterRange2, parameterRange3, parameterRange4, MultiNoiseUtil.ParameterRange.of(1.1F), parameterRange5, f
                        ),
                        registryKey
                )
        );
    }

    @Inject(method = "writeBiomesNearRivers", at = @At("TAIL"))
    private void writeBiomesNearRivers(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo ci) {
        this.writeBiomeParameters(
                parameters,
                MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[3]),
                this.defaultParameter,
                MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness, this.farInlandContinentalness),
                this.erosionParameters[3],
                weirdness,
                0.0F,
                RegisterWorldgen.MIXED_FOREST
        );
        this.writeBiomeParameters( //TODO: figure out what this means and make it fit irl cypress forests
                parameters,
                MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[3]),
                this.defaultParameter,
                MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness, this.farInlandContinentalness),
                this.erosionParameters[3],
                weirdness,
                0.0F,
                RegisterWorldgen.CYPRESS_FOREST
        );
    }

    @Inject(method = "writeMixedBiomes", at = @At("TAIL"))
    private void injectMixedBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo ci) {
        this.writeBiomeParameters(
                parameters,
                MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[4], this.temperatureParameters[3]),
                this.defaultParameter,
                MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness, this.farInlandContinentalness),
                this.erosionParameters[1],
                weirdness,
                0.0F,
                RegisterWorldgen.MIXED_FOREST
        );
    }
}