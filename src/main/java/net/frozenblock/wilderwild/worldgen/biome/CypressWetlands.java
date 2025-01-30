/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.parameters.FrozenBiomeParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;
import net.frozenblock.wilderwild.worldgen.feature.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.worldgen.feature.placed.WWPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CypressWetlands extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.09F, 0.5F);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.NEUTRAL, Humidity.HUMID);
	public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-0.200F, 0.500F);
	public static final Climate.Parameter EROSION = Climate.Parameter.span(0.500F, 1.000F);
	public static final Climate.Parameter WEIRDNESS_A = Weirdness.LOW_SLICE_NORMAL_DESCENDING;
	public static final Climate.Parameter WEIRDNESS_B = Weirdness.LOW_SLICE_VARIANT_ASCENDING;
	public static final Climate.Parameter WEIRDNESS_C = Weirdness.VALLEY;
	public static final Climate.Parameter WEIRDNESS_D = Weirdness.MID_SLICE_NORMAL_ASCENDING;
	public static final Climate.Parameter WEIRDNESS_E = Weirdness.MID_SLICE_NORMAL_DESCENDING;
	public static final Climate.Parameter WEIRDNESS_F = Weirdness.MID_SLICE_VARIANT_ASCENDING;
	public static final Climate.Parameter WEIRDNESS_G = Weirdness.MID_SLICE_VARIANT_DESCENDING;
	public static final float OFFSET = 0F;
	public static final float TEMP = 0.6F;
	public static final float DOWNFALL = 0.7F;
	public static final int WATER_COLOR = 4552818;
	public static final int WATER_FOG_COLOR = 4552818;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(0.8F);
	public static final int FOLIAGE_COLOR = 5877296;
	public static final int GRASS_COLOR = 7979098;
	public static final CypressWetlands INSTANCE = new CypressWetlands();

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "cypress_wetlands";
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
	public @NotNull Integer grassColorOverride() {
		return GRASS_COLOR;
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
	public @NotNull Music backgroundMusic() {
		return Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SEAGRASS_CYPRESS.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FLOWER_FOREST_FLOWERS.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.DENSE_FLOWER_PLACED.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.CYPRESS_WETLANDS_TREES.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER.getKey());
		this.addCypressPaths(features);
		WWSharedWorldgen.addBasicFeatures(features, true);
		BiomeDefaultFeatures.addForestGrass(features);
		BiomeDefaultFeatures.addDefaultOres(features);
		this.addCypressVegetation(features);
	}

	public void addCypressPaths(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.UNDER_WATER_SAND_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.UNDER_WATER_GRAVEL_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.UNDER_WATER_CLAY_PATH.getKey());
	}

	public void addCypressVegetation(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 5, 2, 6))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 14, 4, 5))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 3, 2, 4))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 2, 4))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 6, 4, 4))
			.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 4, 4));
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WWWorldgenConfig.get().biomeGeneration.generateCypressWetlands) {
			this.addSurfaceBiome(
				parameters,
				TEMPERATURE,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS_A,
				OFFSET
			);
			this.addSurfaceBiome(
				parameters,
				TEMPERATURE,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS_B,
				OFFSET
			);
			this.addSurfaceBiome(
				parameters,
				TEMPERATURE,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS_C,
				OFFSET
			);

			List<Climate.ParameterPoint> swampJungleBorders = FrozenBiomeParameters.findBorderParameters(
				OverworldBiomeBuilderParameters.points(Biomes.SWAMP),
				OverworldBiomeBuilderParameters.points(Biomes.JUNGLE),
				0.35F
			);

			swampJungleBorders.forEach(parameterPoint -> {
				this.addSurfaceBiome(
					parameters,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					WEIRDNESS_A,
					parameterPoint.offset()
				);
				this.addSurfaceBiome(
					parameters,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					WEIRDNESS_B,
					parameterPoint.offset()
				);
				this.addSurfaceBiome(
					parameters,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					WEIRDNESS_C,
					parameterPoint.offset()
				);
				this.addSurfaceBiome(
					parameters,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					WEIRDNESS_D,
					parameterPoint.offset()
				);
				this.addSurfaceBiome(
					parameters,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					WEIRDNESS_E,
					parameterPoint.offset()
				);
				this.addSurfaceBiome(
					parameters,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					WEIRDNESS_F,
					parameterPoint.offset()
				);
				this.addSurfaceBiome(
					parameters,
					parameterPoint.temperature(),
					parameterPoint.humidity(),
					parameterPoint.continentalness(),
					parameterPoint.erosion(),
					WEIRDNESS_G,
					parameterPoint.offset()
				);
			});
		}
	}

}
