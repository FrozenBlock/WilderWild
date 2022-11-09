package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.gen.SharedWorldgen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OverworldBiomeBuilder.class, priority = 69420)
public final class OverworldBiomeBuilderMixin {

    @Shadow
    @Final
    private ResourceKey<Biome>[][] MIDDLE_BIOMES;
    @Shadow
    @Final
    private ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT;

    @Shadow
    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, final float offset, ResourceKey<Biome> biome) {
        parameters.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.0F, 1.0F), weirdness, offset), biome));
    }

	@Shadow
	@Final
	private ResourceKey<Biome>[][] SHATTERED_BIOMES;

	@Inject(method = "<init>", at = @At("TAIL"))
    private void injectBiomes(CallbackInfo ci) {
        if (ClothConfigInteractionHandler.modifyJunglePlacement()) {
            MIDDLE_BIOMES_VARIANT[4][3] = Biomes.JUNGLE;
            MIDDLE_BIOMES[4][4] = Biomes.JUNGLE;
        }
    }

    @Inject(method = "addLowSlice", at = @At("TAIL")) // also can be injectLowBiomes
    private void injectBiomesNearRivers(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (!FrozenBools.hasTerraBlender) {
            this.addSurfaceBiome(
                    parameters,
                    SharedWorldgen.MixedForest.TEMPERATURE,
                    SharedWorldgen.MixedForest.HUMIDITY,
                    SharedWorldgen.MixedForest.CONTINENTALNESS,
                    SharedWorldgen.MixedForest.LOW_EROSION,
                    weirdness,
                    SharedWorldgen.MixedForest.OFFSET,
                    RegisterWorldgen.MIXED_FOREST
            );
            this.addSurfaceBiome(
                    parameters,
                    SharedWorldgen.CypressWetlands.TEMPERATURE,
                    SharedWorldgen.CypressWetlands.HUMIDITY,
                    SharedWorldgen.CypressWetlands.CONTINENTALNESS,
                    SharedWorldgen.CypressWetlands.EROSION,
                    weirdness,
                    SharedWorldgen.CypressWetlands.OFFSET,
                    RegisterWorldgen.CYPRESS_WETLANDS
            );
        }
    }

    @Inject(method = "addMidSlice", at = @At("TAIL")) // also can be injectMidBiomes
    private void injectMixedBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (!FrozenBools.hasTerraBlender) {
            this.addSurfaceBiome(
                    parameters,
                    SharedWorldgen.MixedForest.TEMPERATURE,
                    SharedWorldgen.MixedForest.HUMIDITY,
                    SharedWorldgen.MixedForest.CONTINENTALNESS,
                    SharedWorldgen.MixedForest.MID_EROSION,
                    weirdness,
                    SharedWorldgen.MixedForest.OFFSET,
                    RegisterWorldgen.MIXED_FOREST
            );

            this.addSurfaceBiome(
                    parameters,
                    SharedWorldgen.CypressWetlands.TEMPERATURE,
                    SharedWorldgen.CypressWetlands.HUMIDITY,
                    SharedWorldgen.CypressWetlands.CONTINENTALNESS,
                    SharedWorldgen.CypressWetlands.EROSION,
                    weirdness,
                    SharedWorldgen.CypressWetlands.OFFSET,
                    RegisterWorldgen.CYPRESS_WETLANDS
            );
        }
    }

    @Inject(method = "addValleys", at = @At("TAIL")) // can also be injectValleyBiomes
    private void injectRiverBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (!FrozenBools.hasTerraBlender) {
            this.addSurfaceBiome(
                    parameters,
                    SharedWorldgen.CypressWetlands.TEMPERATURE,
                    SharedWorldgen.CypressWetlands.HUMIDITY,
                    SharedWorldgen.CypressWetlands.CONTINENTALNESS,
                    SharedWorldgen.CypressWetlands.EROSION,
                    weirdness,
                    SharedWorldgen.CypressWetlands.OFFSET,
                    RegisterWorldgen.CYPRESS_WETLANDS
            );
			this.addSurfaceBiome(
					parameters,
					SharedWorldgen.WarmRiver.TEMPERATURE,
					SharedWorldgen.WarmRiver.FULL_RANGE,
					SharedWorldgen.WarmRiver.coastContinentalness,
					Climate.Parameter.span(SharedWorldgen.WarmRiver.EROSION_0, SharedWorldgen.WarmRiver.EROSION_1),
					SharedWorldgen.WarmRiver.WEIRDNESS,
					SharedWorldgen.WarmRiver.OFFSET,
					RegisterWorldgen.WARM_RIVER
			);
			this.addSurfaceBiome(
					parameters,
					SharedWorldgen.WarmRiver.TEMPERATURE,
					SharedWorldgen.WarmRiver.FULL_RANGE,
					SharedWorldgen.WarmRiver.nearInlandContinentalness,
					Climate.Parameter.span(SharedWorldgen.WarmRiver.EROSION_0, SharedWorldgen.WarmRiver.EROSION_1),
					SharedWorldgen.WarmRiver.WEIRDNESS,
					SharedWorldgen.WarmRiver.OFFSET,
					RegisterWorldgen.WARM_RIVER
			);
			this.addSurfaceBiome(
					parameters,
					SharedWorldgen.WarmRiver.TEMPERATURE,
					SharedWorldgen.WarmRiver.FULL_RANGE,
					Climate.Parameter.span(SharedWorldgen.WarmRiver.coastContinentalness, SharedWorldgen.WarmRiver.farInlandContinentalness),
					Climate.Parameter.span(SharedWorldgen.WarmRiver.EROSION_2, SharedWorldgen.WarmRiver.EROSION_5),
					SharedWorldgen.WarmRiver.WEIRDNESS,
					SharedWorldgen.WarmRiver.OFFSET,
					RegisterWorldgen.WARM_RIVER
			);
			this.addSurfaceBiome(
					parameters,
					SharedWorldgen.WarmRiver.TEMPERATURE,
					SharedWorldgen.WarmRiver.FULL_RANGE,
					SharedWorldgen.WarmRiver.coastContinentalness,
					SharedWorldgen.WarmRiver.EROSION_6,
					SharedWorldgen.WarmRiver.WEIRDNESS,
					SharedWorldgen.WarmRiver.OFFSET,
					RegisterWorldgen.WARM_RIVER
			);
        }
    }

    @Inject(method = "addUndergroundBiomes", at = @At("TAIL"))
    private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
        if (!FrozenBools.hasTerraBlender) {
            addSemiDeepBiome(
                    consumer,
                    SharedWorldgen.JellyfishCaves.TEMPERATURE,
                    SharedWorldgen.JellyfishCaves.HUMIDITY,
                    SharedWorldgen.JellyfishCaves.CONTINENTALNESS,
                    SharedWorldgen.JellyfishCaves.EROSION,
                    SharedWorldgen.JellyfishCaves.WEIRDNESS,
                    SharedWorldgen.JellyfishCaves.OFFSET,
                    RegisterWorldgen.JELLYFISH_CAVES
            );
        }
    }

    @Inject(method = "maybePickWindsweptSavannaBiome", at = @At("HEAD"), cancellable = true)
    private void getBiomeOrWindsweptSavanna(int temperature, int humidity, Climate.Parameter weirdness, ResourceKey<Biome> biomeKey, CallbackInfoReturnable<ResourceKey<Biome>> info) {
        if (ClothConfigInteractionHandler.modifyWindsweptSavannaPlacement()) {
            info.setReturnValue(temperature > 2 && humidity < 2 && weirdness.max() >= 0L ? Biomes.WINDSWEPT_SAVANNA : biomeKey);
            info.cancel();
        }
    }

    @Inject(method = "addSurfaceBiome", at = @At("HEAD"), cancellable = true)
    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome, CallbackInfo info) {
        if (!FrozenBools.hasTerraBlender) {
            if (biome.equals(Biomes.MANGROVE_SWAMP) && ClothConfigInteractionHandler.modifyMangroveSwampPlacement()) {
				replaceParameters(
						parameters,
						biome,
						SharedWorldgen.MangroveSwamp.TEMPERATURE,
						SharedWorldgen.MangroveSwamp.HUMIDITY,
						continentalness,
						erosion,
						weirdness,
						offset
				);
                info.cancel();
            } else if (biome.equals(Biomes.SWAMP) && ClothConfigInteractionHandler.modifySwampPlacement()) {
				replaceParameters(
						parameters,
						biome,
						SharedWorldgen.Swamp.TEMPERATURE,
						SharedWorldgen.Swamp.HUMIDITY,
						continentalness,
						erosion,
						weirdness,
						offset
				);
                info.cancel();
            }
        }
    }

	private static void replaceParameters(
			Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters,
			ResourceKey<Biome> biome,
			Climate.Parameter temperature,
			Climate.Parameter humidity,
			Climate.Parameter continentalness,
			Climate.Parameter erosion,
			Climate.Parameter weirdness,
			float offset
	) {
		parameters.accept(Pair.of(Climate.parameters(
						temperature,
						humidity,
						continentalness,
						erosion,
						Climate.Parameter.point(0.0F),
						weirdness,
						offset),
				biome
		));

		parameters.accept(Pair.of(Climate.parameters(
						temperature,
						humidity,
						continentalness,
						erosion,
						Climate.Parameter.point(1.0F),
						weirdness,
						offset),
				biome
		));
	}

	@Unique
    private static void addDeepBiome(
            Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters,
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset,
            ResourceKey<Biome> biome
    ) {
        parameters.accept(Pair.of(SharedWorldgen.deepParameters(temperature, humidity, continentalness, erosion, weirdness, offset), biome));
    }

	@Unique
    private static void addSemiDeepBiome(
            Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters,
            Climate.Parameter temperature,
            Climate.Parameter humidity,
            Climate.Parameter continentalness,
            Climate.Parameter erosion,
            Climate.Parameter weirdness,
            float offset,
            ResourceKey<Biome> biome
    ) {
        parameters.accept(Pair.of(SharedWorldgen.semiDeepParameters(temperature, humidity, continentalness, erosion, weirdness, offset), biome));
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
