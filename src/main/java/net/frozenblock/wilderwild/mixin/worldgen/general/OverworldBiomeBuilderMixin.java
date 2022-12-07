package net.frozenblock.wilderwild.mixin.worldgen.general;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.gen.WilderSharedWorldgen;
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
    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, final float offset, ResourceKey<Biome> biome) {
        parameters.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.0F, 1.0F), weirdness, offset), biome));
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectBiomes(CallbackInfo ci) {
        if (ClothConfigInteractionHandler.modifyJunglePlacement()) {
            MIDDLE_BIOMES_VARIANT[4][3] = Biomes.JUNGLE;
            MIDDLE_BIOMES[4][4] = Biomes.JUNGLE;
        }
    }

    @Inject(method = "addLowSlice", at = @At("TAIL")) // also can be injectLowBiomes
    private void injectBiomesNearRivers(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (!FrozenBools.HAS_TERRABLENDER) {
            this.addSurfaceBiome(
                    parameters,
                    WilderSharedWorldgen.MixedForest.TEMPERATURE,
                    WilderSharedWorldgen.MixedForest.HUMIDITY,
                    WilderSharedWorldgen.MixedForest.CONTINENTALNESS,
                    WilderSharedWorldgen.MixedForest.LOW_EROSION,
                    weirdness,
                    WilderSharedWorldgen.MixedForest.OFFSET,
                    RegisterWorldgen.MIXED_FOREST
            );
            this.addSurfaceBiome(
                    parameters,
                    WilderSharedWorldgen.CypressWetlands.TEMPERATURE,
                    WilderSharedWorldgen.CypressWetlands.HUMIDITY,
                    WilderSharedWorldgen.CypressWetlands.CONTINENTALNESS,
                    WilderSharedWorldgen.CypressWetlands.EROSION,
                    weirdness,
                    WilderSharedWorldgen.CypressWetlands.OFFSET,
                    RegisterWorldgen.CYPRESS_WETLANDS
            );
        }
    }

    @Inject(method = "addMidSlice", at = @At("TAIL")) // also can be injectMidBiomes
    private void injectMixedBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (!FrozenBools.HAS_TERRABLENDER) {
            this.addSurfaceBiome(
                    parameters,
                    WilderSharedWorldgen.MixedForest.TEMPERATURE,
                    WilderSharedWorldgen.MixedForest.HUMIDITY,
                    WilderSharedWorldgen.MixedForest.CONTINENTALNESS,
                    WilderSharedWorldgen.MixedForest.MID_EROSION,
                    weirdness,
                    WilderSharedWorldgen.MixedForest.OFFSET,
                    RegisterWorldgen.MIXED_FOREST
            );

            this.addSurfaceBiome(
                    parameters,
                    WilderSharedWorldgen.CypressWetlands.TEMPERATURE,
                    WilderSharedWorldgen.CypressWetlands.HUMIDITY,
                    WilderSharedWorldgen.CypressWetlands.CONTINENTALNESS,
                    WilderSharedWorldgen.CypressWetlands.EROSION,
                    weirdness,
                    WilderSharedWorldgen.CypressWetlands.OFFSET,
                    RegisterWorldgen.CYPRESS_WETLANDS
            );
        }
    }

    @Inject(method = "addValleys", at = @At("TAIL")) // can also be injectValleyBiomes
    private void injectRiverBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo ci) {
        if (!FrozenBools.HAS_TERRABLENDER) {
            this.addSurfaceBiome(
                    parameters,
                    WilderSharedWorldgen.CypressWetlands.TEMPERATURE,
                    WilderSharedWorldgen.CypressWetlands.HUMIDITY,
                    WilderSharedWorldgen.CypressWetlands.CONTINENTALNESS,
                    WilderSharedWorldgen.CypressWetlands.EROSION,
                    weirdness,
                    WilderSharedWorldgen.CypressWetlands.OFFSET,
                    RegisterWorldgen.CYPRESS_WETLANDS
            );
        }
    }

    @Inject(method = "addUndergroundBiomes", at = @At("TAIL"))
    private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo ci) {
        if (!FrozenBools.HAS_TERRABLENDER) {
            addSemiDeepBiome(
                    consumer,
                    WilderSharedWorldgen.JellyfishCaves.TEMPERATURE,
                    WilderSharedWorldgen.JellyfishCaves.HUMIDITY,
                    WilderSharedWorldgen.JellyfishCaves.CONTINENTALNESS,
                    WilderSharedWorldgen.JellyfishCaves.EROSION,
                    WilderSharedWorldgen.JellyfishCaves.WEIRDNESS,
                    WilderSharedWorldgen.JellyfishCaves.OFFSET,
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
        if (!FrozenBools.HAS_TERRABLENDER) {
            if (biome.equals(Biomes.MANGROVE_SWAMP) && ClothConfigInteractionHandler.modifyMangroveSwampPlacement()) {
				replaceParameters(
						parameters,
						biome,
						WilderSharedWorldgen.MangroveSwamp.TEMPERATURE,
						WilderSharedWorldgen.MangroveSwamp.HUMIDITY,
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
						WilderSharedWorldgen.Swamp.TEMPERATURE,
						WilderSharedWorldgen.Swamp.HUMIDITY,
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
        parameters.accept(Pair.of(WilderSharedWorldgen.deepParameters(temperature, humidity, continentalness, erosion, weirdness, offset), biome));
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
        parameters.accept(Pair.of(WilderSharedWorldgen.semiDeepParameters(temperature, humidity, continentalness, erosion, weirdness, offset), biome));
    }

	@Shadow @Final private static float VALLEY_SIZE;
	@Shadow @Final private static float LOW_START;
	@Shadow @Final public static float HIGH_START;
	@Shadow @Final private static float HIGH_END;
	@Shadow @Final private static float PEAK_SIZE;
	@Shadow @Final public static float PEAK_START;
	@Shadow @Final private static float PEAK_END;
	@Shadow @Final public static float NEAR_INLAND_START;
	@Shadow @Final public static float MID_INLAND_START;
	@Shadow @Final public static float FAR_INLAND_START;
	@Shadow @Final public static float EROSION_INDEX_1_START;
	@Shadow @Final public static float EROSION_INDEX_2_START;
	@Shadow @Final private static float EROSION_DEEP_DARK_DRYNESS_THRESHOLD;
	@Shadow @Final private static float DEPTH_DEEP_DARK_DRYNESS_THRESHOLD;
	@Shadow @Final private Climate.Parameter FULL_RANGE;
	@Shadow @Final private Climate.Parameter[] temperatures;
	@Shadow @Final private Climate.Parameter[] humidities;
	@Shadow @Final private Climate.Parameter[] erosions;
	@Shadow @Final private Climate.Parameter FROZEN_RANGE;
	@Shadow @Final private Climate.Parameter UNFROZEN_RANGE;
	@Shadow @Final private Climate.Parameter mushroomFieldsContinentalness;
	@Shadow @Final private Climate.Parameter deepOceanContinentalness;
	@Shadow @Final private Climate.Parameter oceanContinentalness;
	@Shadow @Final private Climate.Parameter coastContinentalness;
	@Shadow @Final private Climate.Parameter inlandContinentalness;
	@Shadow @Final private Climate.Parameter nearInlandContinentalness;
	@Shadow @Final private Climate.Parameter midInlandContinentalness;
	@Shadow @Final private Climate.Parameter farInlandContinentalness;
	@Shadow @Final private ResourceKey<Biome>[][] OCEANS;
	@Shadow @Final private ResourceKey<Biome>[][] MIDDLE_BIOMES;
	@Shadow @Final private ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT;
	@Shadow @Final private ResourceKey<Biome>[][] PLATEAU_BIOMES;
	@Shadow @Final private ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT;
	@Shadow @Final private ResourceKey<Biome>[][] SHATTERED_BIOMES;
}
