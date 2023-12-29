/*
 * Copyright 2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = OverworldBiomeBuilder.class, priority = 69420)
public final class OverworldBiomeBuilderMixin {

	@Shadow
	@Final
	private Climate.Parameter[] erosions;
	@Shadow
	@Final
	private Climate.Parameter coastContinentalness;
	@Shadow
	@Final
	private Climate.Parameter nearInlandContinentalness;
	@Shadow
	@Final
	private Climate.Parameter farInlandContinentalness;
	@Shadow
	@Final
	private ResourceKey<Biome>[][] MIDDLE_BIOMES;
	@Shadow
	@Final
	private ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT;
	@Shadow
	@Final
	private Climate.Parameter[] temperatures;

	@Unique
	private static void wilderWild$replaceParameters(
		@NotNull Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters,
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
	private static void wilderWild$addDeepBiome(
		@NotNull Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters,
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
	private static void wilderWild$addSemiDeepBiome(
		@NotNull Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters,
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

	@Shadow
	private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, final float offset, ResourceKey<Biome> biome) {
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$injectBiomes(CallbackInfo ci) {
		if (WorldgenConfig.get().biomePlacement.modifyJunglePlacement) {
			MIDDLE_BIOMES_VARIANT[4][3] = Biomes.JUNGLE;
			MIDDLE_BIOMES[4][4] = Biomes.JUNGLE;
		}
	}

	@Unique
	private void wilderWild$injectBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WorldgenConfig.get().biomeGeneration.generateDarkTaiga) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.DarkTaiga.TEMPERATURE,
					WilderSharedWorldgen.DarkTaiga.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.DARK_TAIGA
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateMixedForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.MixedForest.TEMPERATURE,
					WilderSharedWorldgen.MixedForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.MIXED_FOREST
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateTemperateRainforest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.TemperateRainforest.TEMPERATURE,
					WilderSharedWorldgen.TemperateRainforest.HUMIDITY,
					point.continentalness(),
					WilderSharedWorldgen.TemperateRainforest.EROSION,
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.TEMPERATE_RAINFOREST
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateRainforest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FOREST)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.Rainforest.TEMPERATURE_A,
					WilderSharedWorldgen.Rainforest.HUMIDITY_A,
					WilderSharedWorldgen.Rainforest.CONTINENTALNESS_A,
					WilderSharedWorldgen.Rainforest.EROSION_A,
					WilderSharedWorldgen.Rainforest.WEIRDNESS_A,
					point.offset(),
					RegisterWorldgen.RAINFOREST
				);
				if (point.temperature().equals(Temperature.FOUR)) {
					this.addSurfaceBiome(
						parameters,
						WilderSharedWorldgen.Rainforest.TEMPERATURE_B,
						WilderSharedWorldgen.Rainforest.HUMIDITY_B,
						point.continentalness(),
						point.erosion(),
						point.weirdness(),
						point.offset(),
						RegisterWorldgen.RAINFOREST
					);
				}
				if (point.temperature().equals(Temperature.THREE)) {
					this.addSurfaceBiome(
						parameters,
						WilderSharedWorldgen.Rainforest.TEMPERATURE_C,
						WilderSharedWorldgen.Rainforest.HUMIDITY_C,
						point.continentalness(),
						point.erosion(),
						point.weirdness(),
						point.offset(),
						RegisterWorldgen.RAINFOREST
					);
				}
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateBirchTaiga) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.BirchTaiga.TEMPERATURE,
					WilderSharedWorldgen.BirchTaiga.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.BIRCH_TAIGA
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateOldGrowthBirchTaiga) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.OLD_GROWTH_BIRCH_FOREST)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.BirchTaiga.TEMPERATURE,
					WilderSharedWorldgen.BirchTaiga.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateBirchJungle) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.JUNGLE)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.BirchJungle.TEMPERATURE,
					WilderSharedWorldgen.BirchJungle.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.BIRCH_JUNGLE
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateSparseBirchJungle) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SPARSE_JUNGLE)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.BirchJungle.TEMPERATURE,
					WilderSharedWorldgen.BirchJungle.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.SPARSE_BIRCH_JUNGLE
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateFlowerField) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FLOWER_FOREST)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.FlowerField.TEMPERATURE_A,
					WilderSharedWorldgen.FlowerField.HUMIDITY_A,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.FLOWER_FIELD
				);
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.FlowerField.TEMPERATURE_B,
					WilderSharedWorldgen.FlowerField.HUMIDITY_B,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.FLOWER_FIELD
				);
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.FlowerField.TEMPERATURE_A,
					WilderSharedWorldgen.FlowerField.HUMIDITY_AB,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.FLOWER_FIELD
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateAridSavanna) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SAVANNA)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.AridSavanna.TEMPERATURE,
					WilderSharedWorldgen.AridSavanna.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.ARID_SAVANNA
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateParchedForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.FOREST)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.ParchedForest.TEMPERATURE,
					WilderSharedWorldgen.ParchedForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.PARCHED_FOREST
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateAridForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.DESERT)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.AridForest.TEMPERATURE,
					WilderSharedWorldgen.AridForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.ARID_FOREST
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateOldGrowthSnowyTaiga) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.TEMPERATURE,
					WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateOldGrowthDarkForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST)) {
				if (point.weirdness().max() < 0L) {
					this.addSurfaceBiome(
						parameters,
						WilderSharedWorldgen.OldGrowthDarkForest.TEMPERATURE,
						WilderSharedWorldgen.OldGrowthDarkForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						point.weirdness(),
						point.offset(),
						RegisterWorldgen.OLD_GROWTH_DARK_FOREST
					);
				}
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateDarkBirchForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.DarkBirchForest.TEMPERATURE,
					WilderSharedWorldgen.DarkBirchForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.DARK_BIRCH_FOREST
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateSemiBirchForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.BIRCH_FOREST)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.SemiBirchForest.TEMPERATURE_A,
					WilderSharedWorldgen.SemiBirchForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.SEMI_BIRCH_FOREST
				);
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.SemiBirchForest.TEMPERATURE_B,
					WilderSharedWorldgen.SemiBirchForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.SEMI_BIRCH_FOREST
				);
			}
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.OLD_GROWTH_BIRCH_FOREST)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.SemiBirchForest.TEMPERATURE_A,
					WilderSharedWorldgen.SemiBirchForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.SEMI_BIRCH_FOREST
				);
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.SemiBirchForest.TEMPERATURE_B,
					WilderSharedWorldgen.SemiBirchForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.SEMI_BIRCH_FOREST
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateDyingForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_PLAINS)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.DyingForest.TEMPERATURE,
					WilderSharedWorldgen.DyingForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.DYING_FOREST
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateSnowyDyingForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_PLAINS)) {
				if (point.weirdness().max() >= 0L) {
					this.addSurfaceBiome(
						parameters,
						WilderSharedWorldgen.SnowyDyingForest.TEMPERATURE,
						WilderSharedWorldgen.SnowyDyingForest.HUMIDITY,
						point.continentalness(),
						point.erosion(),
						point.weirdness(),
						point.offset(),
						RegisterWorldgen.SNOWY_DYING_FOREST
					);
				}
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateDyingMixedForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				boolean weird = point.weirdness().max() < 0L;
				this.addSurfaceBiome(
					parameters,
					weird ? WilderSharedWorldgen.DyingMixedForest.TEMPERATURE_WEIRD
						: WilderSharedWorldgen.DyingMixedForest.TEMPERATURE,
					weird ? WilderSharedWorldgen.DyingMixedForest.HUMIDITY_WEIRD
						: WilderSharedWorldgen.DyingMixedForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.DYING_MIXED_FOREST
				);
			}
		}
		if (WorldgenConfig.get().biomeGeneration.generateSnowyDyingMixedForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				boolean weird = point.weirdness().max() < 0L;
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.SnowyDyingMixedForest.TEMPERATURE,
					weird ? WilderSharedWorldgen.SnowyDyingMixedForest.HUMIDITY_WEIRD :
						WilderSharedWorldgen.SnowyDyingMixedForest.HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset(),
					RegisterWorldgen.SNOWY_DYING_MIXED_FOREST
				);
			}
		}
		if (WorldgenConfig.get().biomePlacement.modifyStonyShorePlacement) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.BEACH)) {
				this.addSurfaceBiome(
					parameters,
					WilderSharedWorldgen.StonyShoreTaiga.TEMPERATURE,
					WilderSharedWorldgen.StonyShoreTaiga.HUMIDITY,
					WilderSharedWorldgen.StonyShoreTaiga.CONTINENTALNESS,
					WilderSharedWorldgen.StonyShoreTaiga.EROSION,
					point.weirdness(),
					point.offset(),
					Biomes.STONY_SHORE
				);
			}
		}
	}

	@Inject(method = "addLowSlice", at = @At("TAIL"))
	private void wilderWild$injectLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo info) {
		if (WorldgenConfig.get().biomeGeneration.generateCypressWetlands) {
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
		if (WorldgenConfig.get().biomeGeneration.generateOasis) {
			this.addSurfaceBiome(
				parameters,
				WilderSharedWorldgen.Oasis.TEMPERATURE,
				WilderSharedWorldgen.Oasis.HUMIDITY,
				WilderSharedWorldgen.Oasis.CONTINENTALNESS,
				WilderSharedWorldgen.Oasis.EROSION,
				weirdness,
				WilderSharedWorldgen.Oasis.OFFSET,
				RegisterWorldgen.OASIS
			);
		}
		wilderWild$injectBiomes(parameters);
	}

	@Inject(method = "addMidSlice", at = @At("TAIL")) // also can be injectMidBiomes
	private void wilderWild$injectMixedBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo info) {
		if (WorldgenConfig.get().biomeGeneration.generateCypressWetlands) {
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
		if (WorldgenConfig.get().biomeGeneration.generateOasis) {
			this.addSurfaceBiome(
				parameters,
				WilderSharedWorldgen.Oasis.TEMPERATURE,
				WilderSharedWorldgen.Oasis.HUMIDITY,
				WilderSharedWorldgen.Oasis.CONTINENTALNESS,
				WilderSharedWorldgen.Oasis.EROSION,
				weirdness,
				WilderSharedWorldgen.Oasis.OFFSET,
				RegisterWorldgen.OASIS
			);
		}
		if (WorldgenConfig.get().biomePlacement.modifyCherryGrovePlacement) {
			this.addSurfaceBiome(
				parameters,
				WilderSharedWorldgen.CherryGrove.TEMPERATURE,
				WilderSharedWorldgen.CherryGrove.HUMIDITY,
				WilderSharedWorldgen.CherryGrove.CONTINENTALNESS,
				WilderSharedWorldgen.CherryGrove.EROSION,
				WilderSharedWorldgen.CherryGrove.WEIRDNESS,
				WilderSharedWorldgen.CherryGrove.OFFSET,
				Biomes.CHERRY_GROVE
			);
		}
	}

	@Inject(method = "addValleys", at = @At("TAIL"))
	private void wilderWild$injectRiverBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo info) {
		if (WorldgenConfig.get().biomeGeneration.generateCypressWetlands) {
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
		if (WorldgenConfig.get().biomeGeneration.generateOasis) {
			this.addSurfaceBiome(
				parameters,
				WilderSharedWorldgen.Oasis.TEMPERATURE,
				WilderSharedWorldgen.Oasis.HUMIDITY,
				WilderSharedWorldgen.Oasis.CONTINENTALNESS,
				WilderSharedWorldgen.Oasis.EROSION,
				weirdness,
				WilderSharedWorldgen.Oasis.OFFSET,
				RegisterWorldgen.OASIS
			);
		}
	}

	@Inject(method = "addUndergroundBiomes", at = @At("TAIL"))
	private void wilderWild$addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo info) {
		if (WorldgenConfig.get().biomeGeneration.generateJellyfishCaves) {
			wilderWild$addSemiDeepBiome(
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
	private void wilderWild$getBiomeOrWindsweptSavanna(int temperature, int humidity, Climate.Parameter weirdness, ResourceKey<Biome> biomeKey, CallbackInfoReturnable<ResourceKey<Biome>> info) {
		if (WorldgenConfig.get().biomePlacement.modifyWindsweptSavannaPlacement) {
			info.setReturnValue(temperature > 2 && humidity < 2 && weirdness.max() >= 0L ? Biomes.WINDSWEPT_SAVANNA : biomeKey);
			info.cancel();
		}
	}

	@Inject(method = "addSurfaceBiome", at = @At("HEAD"), cancellable = true)
	private void wilderWild$modifyPlacement(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome, CallbackInfo info) {
		if (biome.equals(Biomes.MANGROVE_SWAMP) && WorldgenConfig.get().biomePlacement.modifyMangroveSwampPlacement) {
			wilderWild$replaceParameters(
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
		} else if (biome.equals(Biomes.SWAMP) && WorldgenConfig.get().biomePlacement.modifySwampPlacement) {
			wilderWild$replaceParameters(
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

	@Inject(method = "addValleys", at = @At("HEAD"))
	private void wilderWild$addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness, CallbackInfo info) {
		if (WorldgenConfig.get().biomeGeneration.generateWarmRiver) {
			if (weirdness.max() < 0L) {
				this.addSurfaceBiome(
					consumer,
					this.temperatures[3],
					WilderSharedWorldgen.WarmRiver.HUMIDITY_TO_TWO,
					this.coastContinentalness,
					Climate.Parameter.span(this.erosions[0], this.erosions[1]),
					weirdness,
					0.0F,
					RegisterWorldgen.WARM_RIVER
				);
			}
			this.addSurfaceBiome(
				consumer,
				this.temperatures[3],
				WilderSharedWorldgen.WarmRiver.HUMIDITY_TO_TWO,
				this.nearInlandContinentalness,
				Climate.Parameter.span(this.erosions[0], this.erosions[1]),
				weirdness,
				0.0F,
				RegisterWorldgen.WARM_RIVER
			);
			this.addSurfaceBiome(
				consumer,
				this.temperatures[3],
				WilderSharedWorldgen.WarmRiver.HUMIDITY_TO_TWO,
				Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
				Climate.Parameter.span(this.erosions[2], this.erosions[5]),
				weirdness,
				0.0F,
				RegisterWorldgen.WARM_RIVER
			);
			this.addSurfaceBiome(
				consumer,
				this.temperatures[3],
				WilderSharedWorldgen.WarmRiver.HUMIDITY_TO_TWO,
				this.coastContinentalness,
				this.erosions[6],
				weirdness,
				0.0F,
				RegisterWorldgen.WARM_RIVER
			);

			if (WorldgenConfig.get().biomePlacement.modifyJunglePlacement) {
				if (weirdness.max() < 0L) {
					this.addSurfaceBiome(
						consumer,
						this.temperatures[4],
						WilderSharedWorldgen.WarmRiver.HUMIDITY_TO_THREE,
						this.coastContinentalness,
						Climate.Parameter.span(this.erosions[0], this.erosions[1]),
						weirdness,
						0.0F,
						RegisterWorldgen.WARM_RIVER
					);
				}
				this.addSurfaceBiome(
					consumer,
					this.temperatures[4],
					WilderSharedWorldgen.WarmRiver.HUMIDITY_TO_THREE,
					this.nearInlandContinentalness,
					Climate.Parameter.span(this.erosions[0], this.erosions[1]),
					weirdness,
					0.0F,
					RegisterWorldgen.WARM_RIVER
				);
				this.addSurfaceBiome(
					consumer,
					this.temperatures[4],
					WilderSharedWorldgen.WarmRiver.HUMIDITY_TO_THREE,
					Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
					Climate.Parameter.span(this.erosions[2], this.erosions[5]),
					weirdness,
					0.0F,
					RegisterWorldgen.WARM_RIVER
				);
				this.addSurfaceBiome(
					consumer,
					this.temperatures[4],
					WilderSharedWorldgen.WarmRiver.HUMIDITY_TO_THREE,
					this.coastContinentalness,
					this.erosions[6],
					weirdness,
					0.0F,
					RegisterWorldgen.WARM_RIVER
				);
			} else {
				if (weirdness.max() < 0L) {
					this.addSurfaceBiome(
						consumer,
						this.temperatures[4],
						Climate.Parameter.span(-1F, 1F),
						this.coastContinentalness,
						Climate.Parameter.span(this.erosions[0], this.erosions[1]),
						weirdness,
						0.0F,
						RegisterWorldgen.WARM_RIVER
					);
				}
				this.addSurfaceBiome(
					consumer,
					this.temperatures[4],
					Climate.Parameter.span(-1F, 1F),
					this.nearInlandContinentalness,
					Climate.Parameter.span(this.erosions[0], this.erosions[1]),
					weirdness,
					0.0F,
					RegisterWorldgen.WARM_RIVER
				);
				this.addSurfaceBiome(
					consumer,
					this.temperatures[4],
					Climate.Parameter.span(-1F, 1F),
					Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness),
					Climate.Parameter.span(this.erosions[2], this.erosions[5]),
					weirdness,
					0.0F,
					RegisterWorldgen.WARM_RIVER
				);
				this.addSurfaceBiome(
					consumer,
					this.temperatures[4],
					Climate.Parameter.span(-1F, 1F),
					this.coastContinentalness,
					this.erosions[6],
					weirdness,
					0.0F,
					RegisterWorldgen.WARM_RIVER
				);
			}
		}
	}

	@Inject(method = "pickBeachBiome", at = @At("HEAD"), cancellable = true)
	private void wilderWild$injectWarmBeach(int temperature, int humidity, CallbackInfoReturnable<ResourceKey<Biome>> info) {
		if (WorldgenConfig.get().biomeGeneration.generateWarmBeach && temperature == 3) {
			info.setReturnValue(RegisterWorldgen.WARM_BEACH);
		}
	}

	@ModifyArgs(method = "addValleys", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V",
		ordinal = 1
	))
	private void wilderWild$fixPar1(Args args) {
		if (WorldgenConfig.get().biomeGeneration.generateWarmRiver) {
			args.set(1, WilderSharedWorldgen.WarmRiver.UNFROZEN_NOT_WARM_RANGE);
		}
	}

	@ModifyArgs(method = "addValleys", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V",
		ordinal = 3
	))
	private void wilderWild$fixPar2(Args args) {
		if (WorldgenConfig.get().biomeGeneration.generateWarmRiver) {
			args.set(1, WilderSharedWorldgen.WarmRiver.UNFROZEN_NOT_WARM_RANGE);
		}
	}

	@ModifyArgs(method = "addValleys", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V",
		ordinal = 5
	))
	private void wilderWild$fixPar3(Args args) {
		if (WorldgenConfig.get().biomeGeneration.generateWarmRiver) {
			args.set(1, WilderSharedWorldgen.WarmRiver.UNFROZEN_NOT_WARM_RANGE);
		}
	}

	@ModifyArgs(method = "addValleys", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V",
		ordinal = 7
	))
	private void wilderWild$fixPar4(Args args) {
		if (WorldgenConfig.get().biomeGeneration.generateWarmRiver) {
			args.set(1, WilderSharedWorldgen.WarmRiver.UNFROZEN_NOT_WARM_RANGE);
		}
	}

}
