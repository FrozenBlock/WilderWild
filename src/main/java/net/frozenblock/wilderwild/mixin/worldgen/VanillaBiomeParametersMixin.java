package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
    private MultiNoiseUtil.ParameterRange[] humidityParameters;
    @Shadow
    @Final
    private MultiNoiseUtil.ParameterRange[] temperatureParameters;
    @Shadow
    @Final
    private MultiNoiseUtil.ParameterRange defaultParameter;
    @Shadow
    @Final
    private RegistryKey<Biome>[][] commonBiomes;
    @Shadow
    @Final
    private RegistryKey<Biome>[][] uncommonBiomes;

    @Shadow
    private void writeBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, final float offset, RegistryKey<Biome> biome) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.0F, 1.0F), weirdness, offset), biome));
    }

    private int mangroveRound = 0;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectBiomes(CallbackInfo ci) {
        uncommonBiomes[1][0] = RegisterWorldgen.MIXED_FOREST;
        uncommonBiomes[4][0] = BiomeKeys.WOODED_BADLANDS;
        //uncommonBiomes[4][3] = RegisterWorldgen.CYPRESS_WETLANDS;
        //uncommonBiomes[4][4] = BiomeKeys.MANGROVE_SWAMP;
        uncommonBiomes[4][3] = BiomeKeys.JUNGLE;
        commonBiomes[4][4] = BiomeKeys.JUNGLE;
    }

    @Inject(method = "writeBiomesNearRivers", at = @At("TAIL"))
    private void writeBiomesNearRivers(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo ci) {
        this.writeBiomeParameters(
                parameters,
                MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[2]),
                this.defaultParameter,
                MultiNoiseUtil.ParameterRange.combine(this.riverContinentalness, this.farInlandContinentalness),
                this.erosionParameters[2],
                weirdness,
                0.0F,
                RegisterWorldgen.MIXED_FOREST
        );
        this.writeBiomeParameters(
                parameters,
                MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[3]),
                MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[3], this.humidityParameters[4]),
                MultiNoiseUtil.ParameterRange.of(-0.2F, 0.5F),
                MultiNoiseUtil.ParameterRange.of(0.50F, 1.0F), weirdness, 0.0F, RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "writeMixedBiomes", at = @At("TAIL"))
    private void injectMixedBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo ci) {
        this.writeBiomeParameters(
                parameters,
                MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[2]),
                this.defaultParameter,
                MultiNoiseUtil.ParameterRange.combine(this.riverContinentalness, this.farInlandContinentalness),
                this.erosionParameters[1],
                weirdness,
                0.0F,
                RegisterWorldgen.MIXED_FOREST
        );

        this.writeBiomeParameters(
                parameters,
                MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[3]),
                MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[3], this.humidityParameters[4]),
                MultiNoiseUtil.ParameterRange.of(-0.2F, 0.5F),
                MultiNoiseUtil.ParameterRange.of(0.50F, 1.0F), weirdness, 0.0F, RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "writeRiverBiomes", at = @At("TAIL"))
    private void writeRiverBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo ci) {
        this.writeBiomeParameters(
                parameters,
                MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[3]),
                MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[3], this.humidityParameters[4]),
                MultiNoiseUtil.ParameterRange.of(-0.2F, 0.5F),
                MultiNoiseUtil.ParameterRange.of(0.50F, 1.0F), weirdness, 0.0F, RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "getBiomeOrWindsweptSavanna", at = @At("HEAD"), cancellable = true)
    private void getBiomeOrWindsweptSavanna(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness, RegistryKey<Biome> biomeKey, CallbackInfoReturnable<RegistryKey<Biome>> info) {
        info.setReturnValue(temperature > 2 && humidity < 2 && weirdness.max() >= 0L ? BiomeKeys.WINDSWEPT_SAVANNA : biomeKey);
        info.cancel();
    }


    @Inject(method = "writeBiomeParameters", at = @At("HEAD"), cancellable = true)
    private void writeBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome, CallbackInfo info) {
        if (biome.equals(BiomeKeys.MANGROVE_SWAMP)) {
            mangroveRound = mangroveRound + 1;
            MultiNoiseUtil.ParameterRange continentalnessNew = mangroveRound == 1 ? MultiNoiseUtil.ParameterRange.combine(MultiNoiseUtil.ParameterRange.of(-0.11F, 0.03F), this.farInlandContinentalness) : mangroveRound == 2 ? MultiNoiseUtil.ParameterRange.combine(MultiNoiseUtil.ParameterRange.of(-0.11F, 0.03F), this.farInlandContinentalness) : MultiNoiseUtil.ParameterRange.combine(MultiNoiseUtil.ParameterRange.of(-0.19F, -0.11F), this.farInlandContinentalness);
            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                    MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[2], this.temperatureParameters[4]), //Temperature
                    MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[3], this.humidityParameters[4]), //Humidity
                    continentalnessNew,
                    erosion,
                    MultiNoiseUtil.ParameterRange.of(0.0F),
                    weirdness,
                    offset),
                    biome));

            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                    MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[2], this.temperatureParameters[4]), //Temperature
                    MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[3], this.humidityParameters[4]), //Humidity
                    continentalnessNew,
                    erosion,
                    MultiNoiseUtil.ParameterRange.of(1.0F),
                    weirdness,
                    offset),
                    biome));
            info.cancel();
        }
        if (biome.equals(BiomeKeys.SWAMP)) {
            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[3]), //Temperature
                            MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[3], this.humidityParameters[4]), //Humidity
                            continentalness,
                            erosion,
                            MultiNoiseUtil.ParameterRange.of(0.0F),
                            weirdness,
                            offset),
                    biome));

            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[1], this.temperatureParameters[3]), //Temperature
                            MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[3], this.humidityParameters[4]), //Humidity
                            continentalness,
                            erosion,
                            MultiNoiseUtil.ParameterRange.of(1.0F),
                            weirdness,
                            offset),
                    biome));
            info.cancel();
        }
        if (biome.equals(BiomeKeys.DESERT) || biome.equals(BiomeKeys.BADLANDS)) {
            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            temperature, //Temperature
                            MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[0], this.humidityParameters[1]), //Humidity
                            continentalness,
                            erosion,
                            MultiNoiseUtil.ParameterRange.of(0.0F),
                            weirdness,
                            offset),
                    biome));

            parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(
                            temperature, //Temperature
                            MultiNoiseUtil.ParameterRange.combine(this.humidityParameters[0], this.humidityParameters[1]), //Humidity
                            continentalness,
                            erosion,
                            MultiNoiseUtil.ParameterRange.of(1.0F),
                            weirdness,
                            offset),
                    biome));
            info.cancel();
        }
    }

    /*
    This is the list of common biomes. Each separate "list" is one increment in temperature, and each value of each list is one increment in humidity. What a flawed system. Or is it?
    this.commonBiomes = new RegistryKey[][]{
        {BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.TAIGA}, //0
        {BiomeKeys.PLAINS, BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA}, //1
        {BiomeKeys.FLOWER_FOREST, BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST}, //2
        {BiomeKeys.SAVANNA, BiomeKeys.SAVANNA, BiomeKeys.FOREST, BiomeKeys.JUNGLE, BiomeKeys.JUNGLE}, //3
        {BiomeKeys.DESERT, BiomeKeys.DESERT, BiomeKeys.DESERT, BiomeKeys.DESERT, BiomeKeys.DESERT} //4
    };
    For instance, let's get the biome with the highest temperature and humidity. So, we go to 4, and it's filled with Deserts.
    Then, starting from 0, we count to the right. So, it's a desert at [4][4]. Not even a Jungle..?

    ..And same goes for Uncommon Biomes. Just this time, if the Weirdness' max is below 0, this list never gets used.
    However, if weirdness is above 0, then it'll pick from the list. If a null value is chosen, however, then it'll use the Common Biome list instead.
    this.uncommonBiomes = new RegistryKey[][]{
        {BiomeKeys.ICE_SPIKES, null, BiomeKeys.SNOWY_TAIGA, null, null}, //0
        {null, null, null, null, BiomeKeys.OLD_GROWTH_PINE_TAIGA}, //1
        {BiomeKeys.SUNFLOWER_PLAINS, null, null, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, null}, //2
        {null, null, BiomeKeys.PLAINS, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE}, //3
        {null, null, null, null, null} //4
    };
    Note how there's absolutely NOTHING that can be chosen instead of Deserts.

    Do also note that these biomes COMPLETELY ignore other parameters like Erosion- only Temperature and Humidity seem to have effect here, as well as weirdness for choosing uncommon biomes.
     */
}
