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

package net.frozenblock.wilderwild.world.biome;

import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CypressWetlands extends FrozenBiome {
	public static final CypressWetlands INSTANCE = new CypressWetlands();

	@Override
	public String modID() {
		return WilderSharedConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "cypress_wetlands";
	}

	@Override
	public float temperature() {
		return WilderSharedWorldgen.CypressWetlands.TEMP;
	}

	@Override
	public float downfall() {
		return WilderSharedWorldgen.CypressWetlands.DOWNFALL;
	}

	@Override
	public int skyColor() {
		return WilderSharedWorldgen.CypressWetlands.SKY_COLOR;
	}

	@Override
	public int fogColor() {
		return WilderSharedWorldgen.CypressWetlands.FOG_COLOR;
	}

	@Override
	public int waterColor() {
		return WilderSharedWorldgen.CypressWetlands.WATER_COLOR;
	}

	@Override
	public int waterFogColor() {
		return WilderSharedWorldgen.CypressWetlands.WATER_FOG_COLOR;
	}

	@Override
	public @Nullable Integer foliageColorOverride() {
		return WilderSharedWorldgen.CypressWetlands.FOLIAGE_COLOR;
	}

	@Override
	public @Nullable Integer grassColorOverride() {
		return WilderSharedWorldgen.CypressWetlands.GRASS_COLOR;
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
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEAGRASS_CYPRESS.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FLOWER_PLACED.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER.getKey());
		this.addCypressPaths(features);
		WilderSharedWorldgen.addBasicFeatures(features, true);
		BiomeDefaultFeatures.addForestGrass(features);
		BiomeDefaultFeatures.addDefaultOres(features);
		this.addCypressVegetation(features);
	}

	public void addCypressPaths(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_SAND_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH.getKey());
	}

	public void addCypressVegetation(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 5, 2, 6));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 14, 4, 5));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 3, 2, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 2, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 6, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 4, 4));
	}

}
