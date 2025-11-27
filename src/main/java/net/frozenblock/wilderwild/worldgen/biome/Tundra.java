/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.FrozenGrassColorModifiers;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.FrozenBiomeParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.mod_compat.WWModIntegrations;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Tundra extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.495F, -0.295F);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-1F, -0.2F);
	public static final Climate.Parameter EROSION_A = Climate.Parameter.span(-2.233F, 0.450F);
	public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-0.110F, 0.030F);

	public static final Climate.Parameter TEMPERATURE_B = Climate.Parameter.span(-1.000F, -0.450F);
	public static final Climate.Parameter HUMIDITY_C = Climate.Parameter.span(0.300F, 0.700F);
	public static final Climate.Parameter WEIRDNESS_C = Climate.Parameter.span(-0.6F, -0.05F);
	public static final Climate.Parameter EROSION_B = Climate.Parameter.span(0.050F, 0.450F);
	public static final Climate.Parameter CONTINENTALNESS_B = Climate.Parameter.span(-0.110F, 0.030F);

	public static final Climate.Parameter TEMPERATURE_C = Climate.Parameter.span(-0.495F, -0.245F);
	public static final Climate.Parameter HUMIDITY_D = Climate.Parameter.span(-1.0F, -0.100F);
	public static final Climate.Parameter WEIRDNESS_D = Climate.Parameter.span(-0.750F, -0.05F);
	public static final Climate.Parameter EROSION_C = Climate.Parameter.span(-0.375F, 0.450F);
	public static final Climate.Parameter CONTINENTALNESS_C = Climate.Parameter.span(0.030F, 0.800F);

	// SNOWY SLOPES TRANSITION
	public static final Climate.Parameter WEIRDNESS_SLOPE_A = Climate.Parameter.span(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
	public static final Climate.Parameter WEIRDNESS_SLOPE_B = Climate.Parameter.span(Weirdness.HIGH_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING);
	public static final Climate.Parameter WEIRDNESS_SLOPE_C = Climate.Parameter.span(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
	public static final Climate.Parameter WEIRDNESS_SLOPE_D = Climate.Parameter.span(Weirdness.HIGH_SLICE_VARIANT_DESCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING);

	// WITH MAPLE FOREST
	public static final Climate.Parameter TEMPERATURE_MAPLE = Climate.Parameter.span(-0.495F, -0.255F);
	public static final Climate.Parameter HUMIDITY_MAPLE = Climate.Parameter.span(-1F, -0.2F);

	public static final Climate.Parameter WEIRDNESS_A_MAPLE = Climate.Parameter.span(Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
	public static final Climate.Parameter WEIRDNESS_B_MAPLE = Climate.Parameter.span(Weirdness.HIGH_SLICE_VARIANT_DESCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING);
	public static final Climate.Parameter EROSION_MAPLE = Climate.Parameter.span(Erosion.EROSION_1, Erosion.EROSION_2);
	public static final Climate.Parameter CONTINENTALNESS_MAPLE = Climate.Parameter.span(Continentalness.COAST, Continentalness.FAR_INLAND);

	public static final Climate.Parameter TEMPERATURE_MAPLE_BORDER = Climate.Parameter.span(-0.255F, -0.235F);
	public static final Climate.Parameter HUMIDITY_MAPLE_BORDER = Climate.Parameter.span(-0.2F, -0.16F);
	public static final Climate.Parameter WEIRDNESS_MAPLE_BORDER_CENTER = Climate.Parameter.span(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_DESCENDING);
	public static final Climate.Parameter EROSION_MAPLE_BORDER = Climate.Parameter.span(Erosion.EROSION_1, Erosion.EROSION_6);
	public static final Climate.Parameter EROSION_MAPLE_BORDER_CENTER = Climate.Parameter.span(Erosion.EROSION_3, Erosion.EROSION_6);

	public static final Climate.Parameter WEIRDNESS_MAPLE_PEAK = Weirdness.PEAK_VARIANT;
	public static final Climate.Parameter EROSION_MAPLE_PEAK = Climate.Parameter.span(Erosion.EROSION_3, Erosion.EROSION_6);
	public static final Climate.Parameter CONTINENTALNESS_MAPLE_PEAK = Climate.Parameter.span(Continentalness.COAST, Continentalness.MID_INLAND);

	public static final float TEMP = 0.25F;
	public static final float DOWNFALL = 0.8F;
	public static final int WATER_COLOR = WWSharedWorldgen.STOCK_WATER_COLOR;
	public static final int WATER_FOG_COLOR = WWSharedWorldgen.STOCK_WATER_FOG_COLOR;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final int GRASS_COLOR_MAPLE = 13023096;
	public static final int GRASS_COLOR_ORANGE = 15452037;
	public static final int GRASS_COLOR_LIGHTER_GREEN = 12632212;
	public static final int GRASS_COLOR_BLUE_GREENISH = 10990217;
	public static final int GRASS_COLOR_BROWN = 15712649;
	public static final int GRASS_COLOR_RED = 14909535;
	public static final int FOLIAGE_COLOR = 14995819;
	//public static final int FOLIAGE_COLOR = Integer.parseInt("bcbc58", 16);
	//15648619;
	public static final Tundra INSTANCE = new Tundra();

	public Tundra() {
		super();
		FrozenGrassColorModifiers.addGrassColorModifier(
			this.getKey().location(),
			(x, y, grassColor) -> {
				double noise = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2525L)), ImmutableList.of(0)).getValue(x * 0.0225D, y * 0.0225D, false);
				if (noise < -0.5D) return GRASS_COLOR_BROWN;
				if (noise < -0.35D) return GRASS_COLOR_ORANGE;
				if (noise > 0.8D) return GRASS_COLOR_BLUE_GREENISH;
				if (noise > 0.5D) return GRASS_COLOR_LIGHTER_GREEN;
				//if (noise > 0.34222D) return GRASS_COLOR_RED;
				return grassColor;
			}
		);
	}

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "tundra";
	}

	@Override
	public float temperature() {
		return TEMP;
	}

	@Override
	public float downfall() {
		return DOWNFALL;
	}

	@Override
	public boolean hasPrecipitation() {
		return true;
	}

	@Override
	public int skyColor() {
		return SKY_COLOR;
	}

	@Override
	public int fogColor() {
		return FOG_COLOR;
	}

	@Override
	public int waterColor() {
		return WATER_COLOR;
	}

	@Override
	public int waterFogColor() {
		return WATER_FOG_COLOR;
	}

	@Override
	public @NotNull Integer foliageColorOverride() {
		return FOLIAGE_COLOR;
	}

	@Override
	public @Nullable Integer dryFoliageColorOverride() {
		return null;
	}

	@Override
	public @NotNull Integer grassColorOverride() {
		return GRASS_COLOR_MAPLE;
	}

	@Override
	public @Nullable AmbientParticleSettings ambientParticleSettings() {
		return null;
	}

	@Override
	public @Nullable Holder<SoundEvent> ambientLoopSound() {
		return null;
	}

	@Override
	public @NotNull AmbientMoodSettings ambientMoodSettings() {
		return AmbientMoodSettings.LEGACY_CAVE_SETTINGS;
	}

	@Override
	public @Nullable AmbientAdditionsSettings ambientAdditionsSound() {
		return null;
	}

	@Override
	public @Nullable Music backgroundMusic() {
		return Musics.createGameMusic(SoundEvents.MUSIC_GAME);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		WWSharedWorldgen.addBasicFeatures(features, false);
		BiomeDefaultFeatures.addDefaultOres(features);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addForestFlowers(features);
		BiomeDefaultFeatures.addForestGrass(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features, true);
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 5));
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) return;
		if (WWWorldgenConfig.get().biomeGeneration.generateTundra) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.PLAINS)) {
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE,
					HUMIDITY,
					CONTINENTALNESS,
					EROSION_A,
					point.weirdness(),
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE,
					HUMIDITY,
					CONTINENTALNESS,
					EROSION_A,
					point.weirdness(),
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_B,
					HUMIDITY_C,
					CONTINENTALNESS_B,
					EROSION_B,
					WEIRDNESS_C,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_C,
					HUMIDITY_D,
					CONTINENTALNESS_C,
					EROSION_C,
					WEIRDNESS_D,
					0F
				);
			}

			if (WWWorldgenConfig.get().biomePlacement.modifyTundraPlacement) {
				List<Climate.ParameterPoint> plainsSnowySlopesBorders = FrozenBiomeParameters.findBorderParameters(
					OverworldBiomeBuilderParameters.points(Biomes.PLAINS),
					OverworldBiomeBuilderParameters.points(Biomes.SNOWY_SLOPES),
					0.15F
				);

				plainsSnowySlopesBorders.forEach(parameterPoint -> {
					this.addSurfaceBiome(
						parameters,
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						WEIRDNESS_SLOPE_A,
						parameterPoint.offset()
					);
					this.addSurfaceBiome(
						parameters,
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						WEIRDNESS_SLOPE_B,
						parameterPoint.offset()
					);
					this.addSurfaceBiome(
						parameters,
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						WEIRDNESS_SLOPE_C,
						parameterPoint.offset()
					);
					this.addSurfaceBiome(
						parameters,
						parameterPoint.temperature(),
						parameterPoint.humidity(),
						parameterPoint.continentalness(),
						parameterPoint.erosion(),
						WEIRDNESS_SLOPE_D,
						parameterPoint.offset()
					);
				});
			}

			if (WWWorldgenConfig.get().biomeGeneration.generateMapleForest) {
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE,
					HUMIDITY_MAPLE,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE,
					WEIRDNESS_A_MAPLE,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE,
					HUMIDITY_MAPLE,
					CONTINENTALNESS,
					EROSION_MAPLE,
					WEIRDNESS_B_MAPLE,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE,
					HUMIDITY_MAPLE,
					CONTINENTALNESS_MAPLE_PEAK,
					EROSION_MAPLE_PEAK,
					WEIRDNESS_MAPLE_PEAK,
					0F
				);

				// SURROUNDING
				// A
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE_BORDER,
					HUMIDITY_MAPLE,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER,
					WEIRDNESS_A_MAPLE,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE,
					HUMIDITY_MAPLE_BORDER,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER,
					WEIRDNESS_A_MAPLE,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE_BORDER,
					HUMIDITY_MAPLE_BORDER,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER,
					WEIRDNESS_A_MAPLE,
					0F
				);

				// B
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE_BORDER,
					HUMIDITY_MAPLE,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER_CENTER,
					WEIRDNESS_MAPLE_BORDER_CENTER,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE,
					HUMIDITY_MAPLE_BORDER,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER_CENTER,
					WEIRDNESS_MAPLE_BORDER_CENTER,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE_BORDER,
					HUMIDITY_MAPLE_BORDER,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER_CENTER,
					WEIRDNESS_MAPLE_BORDER_CENTER,
					0F
				);

				// C
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE_BORDER,
					HUMIDITY_MAPLE,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER,
					WEIRDNESS_B_MAPLE,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE,
					HUMIDITY_MAPLE_BORDER,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER,
					WEIRDNESS_B_MAPLE,
					0F
				);
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE_MAPLE_BORDER,
					HUMIDITY_MAPLE_BORDER,
					CONTINENTALNESS_MAPLE,
					EROSION_MAPLE_BORDER,
					WEIRDNESS_B_MAPLE,
					0F
				);
			}
		}
	}
}
