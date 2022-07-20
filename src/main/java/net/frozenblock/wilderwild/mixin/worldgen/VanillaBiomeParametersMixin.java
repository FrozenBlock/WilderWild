package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.datafixers.util.Pair;
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
public abstract class VanillaBiomeParametersMixin {
    @Shadow
    @Final
    private Climate.Parameter inlandContinentalness;
    @Shadow
    @Final
    private Climate.Parameter farInlandContinentalness;
    @Shadow
    @Final
    private Climate.Parameter[] erosions;
    @Shadow
    @Final
    private Climate.Parameter[] humidities;
    @Shadow
    @Final
    private Climate.Parameter[] temperatures;
    @Shadow
    @Final
    private Climate.Parameter FULL_RANGE;
    @Shadow
    @Final
    private ResourceKey<Biome>[][] MIDDLE_BIOMES;
    @Shadow
    @Final
    private ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT;

    @Shadow
    protected abstract void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, final float offset, ResourceKey<Biome> biome);

    private int mangroveRound = 0;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectBiomes(CallbackInfo ci) {
        MIDDLE_BIOMES_VARIANT[1][0] = RegisterWorldgen.MIXED_FOREST;
        MIDDLE_BIOMES_VARIANT[4][0] = Biomes.WOODED_BADLANDS;
        //uncommonBiomes[4][3] = RegisterWorldgen.CYPRESS_WETLANDS;
        //uncommonBiomes[4][4] = BiomeKeys.MANGROVE_SWAMP;
        MIDDLE_BIOMES_VARIANT[4][3] = Biomes.JUNGLE;
        MIDDLE_BIOMES[4][4] = Biomes.JUNGLE;
    }

    @Inject(method = "addLowSlice", at = @At("TAIL"))
    private void writeBiomesNearRivers(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        this.addSurfaceBiome(
                parameters,
                Climate.Parameter.span(this.temperatures[1], this.temperatures[2]),
                this.FULL_RANGE,
                Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness),
                this.erosions[2],
                weirdness,
                0.0F,
                RegisterWorldgen.MIXED_FOREST
        );
        this.addSurfaceBiome(
                parameters,
                Climate.Parameter.span(this.temperatures[1], this.temperatures[3]),
                Climate.Parameter.span(this.humidities[2], this.humidities[4]),
                Climate.Parameter.span(-0.2F, 0.5F),
                Climate.Parameter.span(0.50F, 1.0F), weirdness, 0.0F, RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "addMidSlice", at = @At("TAIL"))
    private void injectMixedBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        this.addSurfaceBiome(
                parameters,
                Climate.Parameter.span(this.temperatures[1], this.temperatures[2]),
                this.FULL_RANGE,
                Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness),
                this.erosions[1],
                weirdness,
                0.0F,
                RegisterWorldgen.MIXED_FOREST
        );

        this.addSurfaceBiome(
                parameters,
                Climate.Parameter.span(this.temperatures[1], this.temperatures[3]),
                Climate.Parameter.span(this.humidities[2], this.humidities[4]),
                Climate.Parameter.span(-0.2F, 0.5F),
                Climate.Parameter.span(0.50F, 1.0F), weirdness, 0.0F, RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "addValleys", at = @At("TAIL"))
    private void writeRiverBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        this.addSurfaceBiome(
                parameters,
                Climate.Parameter.span(this.temperatures[1], this.temperatures[3]),
                Climate.Parameter.span(this.humidities[2], this.humidities[4]),
                Climate.Parameter.span(-0.2F, 0.5F),
                Climate.Parameter.span(0.50F, 1.0F), weirdness, 0.0F, RegisterWorldgen.CYPRESS_WETLANDS
        );
    }

    @Inject(method = "maybePickWindsweptSavannaBiome", at = @At("HEAD"), cancellable = true)
    private void maybePickWindsweptSavannaBiome(int temperature, int humidity, Climate.Parameter weirdness, ResourceKey<Biome> biomeKey, CallbackInfoReturnable<ResourceKey<Biome>> info) {
        info.setReturnValue(temperature > 2 && humidity < 2 && weirdness.max() >= 0L ? Biomes.WINDSWEPT_SAVANNA : biomeKey);
        info.cancel();
    }


    @Inject(method = "addSurfaceBiome", at = @At("HEAD"), cancellable = true)
    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome, CallbackInfo info) {
        if (biome.equals(Biomes.MANGROVE_SWAMP)) {
            mangroveRound = mangroveRound + 1;
            Climate.Parameter continentalnessNew = mangroveRound == 1 ? Climate.Parameter.span(Climate.Parameter.span(-0.11F, 0.03F), this.farInlandContinentalness) : mangroveRound == 2 ? Climate.Parameter.span(Climate.Parameter.span(-0.11F, 0.03F), this.farInlandContinentalness) : Climate.Parameter.span(Climate.Parameter.span(-0.19F, -0.11F), this.farInlandContinentalness);
            parameters.accept(Pair.of(Climate.parameters(
                            Climate.Parameter.span(this.temperatures[2], this.temperatures[4]), //Temperature
                            Climate.Parameter.span(this.humidities[3], this.humidities[4]), //Humidity
                            continentalnessNew,
                            erosion,
                            Climate.Parameter.point(0.0F),
                            weirdness,
                            offset),
                    biome));

            parameters.accept(Pair.of(Climate.parameters(
                            Climate.Parameter.span(this.temperatures[2], this.temperatures[4]), //Temperature
                            Climate.Parameter.span(this.humidities[3], this.humidities[4]), //Humidity
                            continentalnessNew,
                            erosion,
                            Climate.Parameter.point(1.0F),
                            weirdness,
                            offset),
                    biome));
            info.cancel();
        }
        if (biome.equals(Biomes.SWAMP)) {
            parameters.accept(Pair.of(Climate.parameters(
                            Climate.Parameter.span(this.temperatures[1], this.temperatures[3]), //Temperature
                            Climate.Parameter.span(this.humidities[3], this.humidities[4]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(0.0F),
                            weirdness,
                            offset),
                    biome));

            parameters.accept(Pair.of(Climate.parameters(
                            Climate.Parameter.span(this.temperatures[1], this.temperatures[3]), //Temperature
                            Climate.Parameter.span(this.humidities[3], this.humidities[4]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(1.0F),
                            weirdness,
                            offset),
                    biome));
            info.cancel();
        }
        if (biome.equals(Biomes.DESERT) || biome.equals(Biomes.BADLANDS)) {
            parameters.accept(Pair.of(Climate.parameters(
                            temperature, //Temperature
                            Climate.Parameter.span(this.humidities[0], this.humidities[1]), //Humidity
                            continentalness,
                            erosion,
                            Climate.Parameter.point(0.0F),
                            weirdness,
                            offset),
                    biome));

            parameters.accept(Pair.of(Climate.parameters(
                            temperature, //Temperature
                            Climate.Parameter.span(this.humidities[0], this.humidities[1]), //Humidity
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
