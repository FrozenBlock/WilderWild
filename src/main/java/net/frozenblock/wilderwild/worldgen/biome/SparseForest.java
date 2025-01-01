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
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;
import net.frozenblock.wilderwild.worldgen.feature.placed.WWPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
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
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SparseForest extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.45F, 0F);
	public static final Climate.Parameter TEMPERATURE_WITH_DYING_FORESTS = Climate.Parameter.span(-0.3F, 0F);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-0.1F, 0F);
	public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_0, Erosion.EROSION_3);
	public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.INLAND, Continentalness.FAR_INLAND);
	public static final Climate.Parameter WEIRDNESS_A = Climate.Parameter.span(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_ASCENDING);
	public static final Climate.Parameter WEIRDNESS_B = Climate.Parameter.span(Weirdness.HIGH_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING);
	public static final Climate.Parameter WEIRDNESS_C = Climate.Parameter.span(Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_ASCENDING);
	public static final Climate.Parameter WEIRDNESS_D = Climate.Parameter.span(Weirdness.HIGH_SLICE_VARIANT_DESCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING);
	public static final float TEMP = 0.75F;
	public static final float DOWNFALL = 0.6F;
	public static final int WATER_COLOR = WWSharedWorldgen.STOCK_WATER_COLOR;
	public static final int WATER_FOG_COLOR = WWSharedWorldgen.STOCK_WATER_FOG_COLOR;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final SparseForest INSTANCE = new SparseForest();

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "sparse_forest";
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
	public @Nullable Integer foliageColorOverride() {
		return null;
	}

	@Override
	public @Nullable Integer grassColorOverride() {
		return null;
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
	public @Nullable AmbientMoodSettings ambientMoodSettings() {
		return AmbientMoodSettings.LEGACY_CAVE_SETTINGS;
	}

	@Override
	public @Nullable AmbientAdditionsSettings ambientAdditionsSound() {
		return null;
	}

	@Override
	public @Nullable Music backgroundMusic() {
		return Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		WWSharedWorldgen.addBasicFeatures(features, false);
		BiomeDefaultFeatures.addForestFlowers(features);
		BiomeDefaultFeatures.addDefaultOres(features);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addDefaultFlowers(features);
		BiomeDefaultFeatures.addForestGrass(features);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SPARSE_FOREST.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
		BiomeDefaultFeatures.farmAnimals(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4, 4));
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, OverworldBiomeBuilder.Modifier modifier) {
		WWWorldgenConfig worldgenConfig = WWWorldgenConfig.get();
		if (worldgenConfig.biomeGeneration.generateSparseForest) {
			Climate.Parameter temperature = worldgenConfig.biomeGeneration.generateDyingForest ? TEMPERATURE_WITH_DYING_FORESTS : TEMPERATURE;

			this.addSurfaceBiome(
				parameters,
				temperature,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS_A,
				0F
			);
			this.addSurfaceBiome(
				parameters,
				temperature,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS_B,
				0F
			);
			this.addSurfaceBiome(
				parameters,
				temperature,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS_C,
				0F
			);
			this.addSurfaceBiome(
				parameters,
				temperature,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS_D,
				0F
			);
		}
	}

}
