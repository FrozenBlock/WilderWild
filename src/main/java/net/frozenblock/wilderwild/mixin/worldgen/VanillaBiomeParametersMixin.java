package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.misc.config.ModMenuInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public final class VanillaBiomeParametersMixin {
    @Shadow
    @Final
    private Climate.Parameter riverContinentalness;
    @Shadow
    @Final
    private Climate.Parameter farInlandContinentalness;
    @Shadow
    @Final
    private Climate.Parameter[] erosionParameters;
    @Shadow
    @Final
    private Climate.Parameter[] humidityParameters;
    @Shadow
    @Final
    private Climate.Parameter[] temperatureParameters;
    @Shadow
    @Final
    private Climate.Parameter defaultParameter;
    @Shadow
    @Final
    private ResourceKey<Biome>[][] commonBiomes;
    @Shadow
    @Final
    private ResourceKey<Biome>[][] uncommonBiomes;

    @Shadow
    private void writeBiomeParameters(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, final float offset, ResourceKey<Biome> biome) {
        parameters.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.0F, 1.0F), weirdness, offset), biome));
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectBiomes(CallbackInfo ci) {
        if (ModMenuInteractionHandler.modJunglePlacement()) {
            uncommonBiomes[4][3] = Biomes.JUNGLE;
            commonBiomes[4][4] = Biomes.JUNGLE;
        }
    }

    @Inject(method = "writeLowBiomes", at = @At("TAIL"))
    // also can be injectLowBiomes
    private void injectBiomesNearRivers(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        this.writeBiomeParameters(
                parameters,
                Climate.Parameter.span(this.temperatureParameters[1], this.temperatureParameters[2]),
                this.defaultParameter,
                Climate.Parameter.span(this.riverContinentalness, this.farInlandContinentalness),
                this.erosionParameters[2],
                weirdness,
                0.0F,
                RegisterWorldgen.MIXED_FOREST
        );
        this.writeBiomeParameters(
                parameters,
                Climate.Parameter.span(this.temperatureParameters[1], this.temperatureParameters[3]),
                Climate.Parameter.span(this.humidityParameters[2], this.humidityParameters[4]),
                Climate.Parameter.span(-0.2F, 0.5F),
                Climate.Parameter.span(0.50F, 1.0F),
                weirdness,
                0.0F,
                RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "writeMidBiomes", at = @At("TAIL"))
    // also can be injectMidBiomes
    private void injectMixedBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        this.writeBiomeParameters(
                parameters,
                Climate.Parameter.span(this.temperatureParameters[1], this.temperatureParameters[2]),
                this.defaultParameter,
                Climate.Parameter.span(this.riverContinentalness, this.farInlandContinentalness),
                this.erosionParameters[1],
                weirdness,
                0.0F,
                RegisterWorldgen.MIXED_FOREST
        );

        this.writeBiomeParameters(
                parameters,
                Climate.Parameter.span(this.temperatureParameters[1], this.temperatureParameters[3]),
                Climate.Parameter.span(this.humidityParameters[2], this.humidityParameters[4]),
                Climate.Parameter.span(-0.2F, 0.5F),
                Climate.Parameter.span(0.50F, 1.0F),
                weirdness,
                0.0F,
                RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "writeValleyBiomes", at = @At("TAIL"))
    // can also be injectValleyBiomes
    private void injectRiverBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        this.writeBiomeParameters(
                parameters,
                Climate.Parameter.span(this.temperatureParameters[1], this.temperatureParameters[3]),
                Climate.Parameter.span(this.humidityParameters[2], this.humidityParameters[4]),
                Climate.Parameter.span(-0.2F, 0.5F),
                Climate.Parameter.span(0.50F, 1.0F),
                weirdness,
                0.0F,
                RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "getBiomeOrWindsweptSavanna", at = @At("HEAD"), cancellable = true)
    private void getBiomeOrWindsweptSavanna(int temperature, int humidity, Climate.Parameter weirdness, ResourceKey<Biome> biomeKey, CallbackInfoReturnable<ResourceKey<Biome>> info) {
        if (ModMenuInteractionHandler.modWindsweptSavannaPlacement()) {
            info.setReturnValue(temperature > 2 && humidity < 2 && weirdness.max() >= 0L ? Biomes.WINDSWEPT_SAVANNA : biomeKey);
            info.cancel();
        }
    }

    private static final int swampHumidity = 2;

    @Inject(method = "writeBiomeParameters", at = @At("HEAD"), cancellable = true)
    private void writeBiomeParameters(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome, CallbackInfo info) {
        if (biome.equals(Biomes.MANGROVE_SWAMP) && ModMenuInteractionHandler.modMangroveSwampPlacement()) {
            parameters.accept(Pair.of(Climate.parameters(
                            Climate.Parameter.span(this.temperatureParameters[2], this.temperatureParameters[4]), //Temperature
                            Climate.Parameter.span(this.humidityParameters[swampHumidity], this.humidityParameters[4]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(0.0F),
                            weirdness,
                            offset),
                    biome));

            parameters.accept(Pair.of(Climate.parameters(
                            Climate.Parameter.span(this.temperatureParameters[2], this.temperatureParameters[4]), //Temperature
                            Climate.Parameter.span(this.humidityParameters[swampHumidity], this.humidityParameters[4]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(1.0F),
                            weirdness,
                            offset),
                    biome));
            info.cancel();
        }
        if (biome.equals(Biomes.SWAMP) && ModMenuInteractionHandler.modSwampPlacement()) {
            parameters.accept(Pair.of(Climate.parameters(
                            Climate.Parameter.span(this.temperatureParameters[1], this.temperatureParameters[3]), //Temperature
                            Climate.Parameter.span(this.humidityParameters[swampHumidity], this.humidityParameters[4]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(0.0F),
                            weirdness,
                            offset),
                    biome));

            parameters.accept(Pair.of(Climate.parameters(
                            Climate.Parameter.span(this.temperatureParameters[1], this.temperatureParameters[3]), //Temperature
                            Climate.Parameter.span(this.humidityParameters[swampHumidity], this.humidityParameters[4]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(1.0F),
                            weirdness,
                            offset),
                    biome));
            info.cancel();
        }
        if ((biome.equals(Biomes.DESERT) && ModMenuInteractionHandler.modDesertPlacement()) || (biome.equals(Biomes.BADLANDS) && ModMenuInteractionHandler.modBadlandsPlacement())) {
            parameters.accept(Pair.of(Climate.parameters(
                            temperature, //Temperature
                            Climate.Parameter.span(this.humidityParameters[0], this.humidityParameters[1]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(0.0F),
                            weirdness,
                            offset),
                    biome));

            parameters.accept(Pair.of(Climate.parameters(
                            temperature, //Temperature
                            Climate.Parameter.span(this.humidityParameters[0], this.humidityParameters[1]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(1.0F),
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
