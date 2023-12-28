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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;

public final class RegisterWorldgen {
	// Main Biomes
	public static final ResourceKey<Biome> CYPRESS_WETLANDS = register("cypress_wetlands");
	public static final ResourceKey<Biome> OASIS = register("oasis");
	public static final ResourceKey<Biome> WARM_RIVER = register("warm_river");
	public static final ResourceKey<Biome> WARM_BEACH = register("warm_beach");
	// Cave Biomes
	public static final ResourceKey<Biome> JELLYFISH_CAVES = register("jellyfish_caves");
	// Transition Biomes
	// HOT
	public static final ResourceKey<Biome> ARID_FOREST = register("arid_forest");
	public static final ResourceKey<Biome> ARID_SAVANNA = register("arid_savanna");
	public static final ResourceKey<Biome> PARCHED_FOREST = register("parched_forest");
	// TROPICAL
	public static final ResourceKey<Biome> BIRCH_JUNGLE = register("birch_jungle");
	public static final ResourceKey<Biome> SPARSE_BIRCH_JUNGLE = register("sparse_birch_jungle");
	// TEMPERATE
	public static final ResourceKey<Biome> BIRCH_TAIGA = register("birch_taiga");
	public static final ResourceKey<Biome> SEMI_BIRCH_FOREST = register("semi_birch_forest");
	public static final ResourceKey<Biome> DARK_BIRCH_FOREST = register("dark_birch_forest");
	public static final ResourceKey<Biome> FLOWER_FIELD = register("flower_field");
	public static final ResourceKey<Biome> TEMPERATE_RAINFOREST = register("temperate_rainforest");
	public static final ResourceKey<Biome> RAINFOREST = register("rainforest");
	public static final ResourceKey<Biome> DARK_TAIGA = register("dark_taiga");
	public static final ResourceKey<Biome> MIXED_FOREST = register("mixed_forest");
	// COLD
	public static final ResourceKey<Biome> DYING_FOREST = register("dying_forest");
	public static final ResourceKey<Biome> SNOWY_DYING_FOREST = register("snowy_dying_forest");
	// OLD GROWTH
	public static final ResourceKey<Biome> OLD_GROWTH_BIRCH_TAIGA = register("old_growth_birch_taiga");
	public static final ResourceKey<Biome> OLD_GROWTH_DARK_FOREST = register("old_growth_dark_forest");
	public static final ResourceKey<Biome> SNOWY_OLD_GROWTH_PINE_TAIGA = register("snowy_old_growth_pine_taiga");

	private RegisterWorldgen() {
		throw new UnsupportedOperationException("RegisterWorldgen contains only static declarations.");
	}

	public static void init() {

	}

	public static void bootstrap(@NotNull BootstapContext<Biome> context) {
		WilderSharedConstants.logWithModId("Registering Biomes for", WilderSharedConstants.UNSTABLE_LOGGING);

		// MAIN BIOMES
		register(context, CYPRESS_WETLANDS, cypressWetlands(context));
		register(context, MIXED_FOREST, mixedForest(context));
		register(context, DYING_FOREST, dyingForest(context));
		register(context, SNOWY_DYING_FOREST, dyingForest(context));
		register(context, OASIS, oasis(context));
		register(context, WARM_RIVER, warmRiver(context));
		register(context, WARM_BEACH, warmBeach(context));
		// CAVE BIOMES
		register(context, JELLYFISH_CAVES, jellyfishCaves(context));
		// TRANSITION BIOMES
		// HOT
		register(context, ARID_FOREST, aridForest(context));
		register(context, ARID_SAVANNA, aridSavanna(context));
		register(context, PARCHED_FOREST, parchedForest(context));
		// TROPICAL
		register(context, BIRCH_JUNGLE, birchJungle(context));
		register(context, SPARSE_BIRCH_JUNGLE, sparseBirchJungle(context));
		// TEMPERATE
		register(context, BIRCH_TAIGA, birchTaiga(context, false));
		register(context, SEMI_BIRCH_FOREST, semiBirchForest(context));
		register(context, DARK_BIRCH_FOREST, darkBirchForest(context));
		register(context, FLOWER_FIELD, flowerField(context));
		register(context, TEMPERATE_RAINFOREST, temperateRainforest(context));
		register(context, RAINFOREST, rainforest(context));
		register(context, DARK_TAIGA, darkTaiga(context));
		// OLD GROWTH
		register(context, OLD_GROWTH_BIRCH_TAIGA, birchTaiga(context, true));
		register(context, OLD_GROWTH_DARK_FOREST, oldGrowthDarkForest(context));
		register(context, SNOWY_OLD_GROWTH_PINE_TAIGA, oldGrowthSnowyTaiga(context));
	}

	@NotNull
	private static ResourceKey<Biome> register(@NotNull String name) {
		return ResourceKey.create(Registries.BIOME, WilderSharedConstants.id(name));
	}

	private static void register(@NotNull BootstapContext<Biome> entries, @NotNull ResourceKey<Biome> key, @NotNull Biome biome) {
		WilderSharedConstants.log("Registering biome " + key.location(), true);
		entries.register(key, biome);
	}

	// MAIN BIOMES
	// CYPRESS WETLANDS
	@NotNull
	public static Biome cypressWetlands(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		addCypressWetlandsMobs(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addCypressWetlandsFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.CypressWetlands.TEMP)
			.downfall(WilderSharedWorldgen.CypressWetlands.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.CypressWetlands.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.CypressWetlands.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.CypressWetlands.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.CypressWetlands.SKY_COLOR)
					.foliageColorOverride(WilderSharedWorldgen.CypressWetlands.FOLIAGE_COLOR)
					.grassColorOverride(WilderSharedWorldgen.CypressWetlands.GRASS_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addCypressPaths(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_SAND_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH.getKey());
	}

	public static void addCypressWetlandsFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEAGRASS_CYPRESS.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FLOWER_PLACED.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER.getKey());
		addCypressPaths(builder);
		addBasicFeatures(builder, CYPRESS_WETLANDS);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		addCypressVegetation(builder);
	}

	public static void addCypressVegetation(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
	}

	public static void addCypressWetlandsMobs(@NotNull MobSpawnSettings.Builder builder) {
		builder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 5, 2, 6));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 14, 4, 5));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 3, 2, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 2, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 6, 4, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 4, 4));
	}

	// MIXED FOREST
	@NotNull
	public static Biome mixedForest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addMixedForestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.MixedForest.TEMP)
			.downfall(WilderSharedWorldgen.MixedForest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.MixedForest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.MixedForest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.MixedForest.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.MixedForest.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addMixedForestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_TREES.getKey());
		addBasicFeatures(builder, MIXED_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
	}

	// DYING FOREST
	@NotNull
	public static Biome dyingForest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addDyingForestFeatures(builder2);
		return new Biome.BiomeBuilder()
				.hasPrecipitation(true)
				.temperature(WilderSharedWorldgen.DyingForest.TEMP)
				.downfall(WilderSharedWorldgen.DyingForest.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.grassColorOverride(WilderSharedWorldgen.DyingForest.GRASS_COLOR)
								.foliageColorOverride(WilderSharedWorldgen.DyingForest.FOLIAGE_COLOR)
								.waterColor(WilderSharedWorldgen.DyingForest.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.DyingForest.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.DyingForest.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.DyingForest.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static void addDyingForestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, DYING_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_DYING_FOREST.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
	}

	// SNOWY DYING FOREST
	@NotNull
	public static Biome snowyDyingForest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		builder.creatureGenerationProbability(0.07F);
		BiomeDefaultFeatures.snowySpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addDyingForestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.DyingForest.TEMP)
			.downfall(WilderSharedWorldgen.DyingForest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.grassColorOverride(WilderSharedWorldgen.DyingForest.GRASS_COLOR)
					.foliageColorOverride(WilderSharedWorldgen.DyingForest.FOLIAGE_COLOR)
					.waterColor(WilderSharedWorldgen.DyingForest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.DyingForest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.DyingForest.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.DyingForest.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addSnowyDyingForestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SNOWY_DYING_FOREST);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addSnowyTrees(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addDefaultGrass(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_2);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_DYING_FOREST.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
	}

	// OASIS
	@NotNull
	public static Biome oasis(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.desertSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addOasisFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(false)
			.temperature(WilderSharedWorldgen.Oasis.TEMP)
			.downfall(WilderSharedWorldgen.Oasis.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.grassColorOverride(WilderSharedWorldgen.Oasis.GRASS_COLOR)
					.foliageColorOverride(WilderSharedWorldgen.Oasis.FOLIAGE_COLOR)
					.waterColor(WilderSharedWorldgen.Oasis.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.Oasis.WATER_FOG_COLOR)
					.skyColor(WilderSharedWorldgen.Oasis.SKY_COLOR)
					.fogColor(WilderSharedWorldgen.Oasis.FOG_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT))
					.build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addOasisFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addDefaultSprings(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDesertExtraDecoration(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_DESERT);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
		builder.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.SAND_POOL.getKey());
		builder.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.MESSY_SAND_POOL.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRASS_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_GRASS_PLACED.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_CACTUS_PLACED.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALMS_OASIS.getKey());
		builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, WilderMiscPlaced.DESERT_WELL.getKey());
	}

	// WARM RIVER
	@NotNull
	public static Biome warmRiver(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4)).addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
		BiomeDefaultFeatures.commonSpawns(builder);
		builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 100, 1, 1));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addWarmRiverFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.WarmRiver.TEMP)
			.downfall(WilderSharedWorldgen.WarmRiver.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.grassColorOverride(WilderSharedWorldgen.WarmRiver.GRASS_COLOR)
					.foliageColorOverride(WilderSharedWorldgen.WarmRiver.FOLIAGE_COLOR)
					.waterColor(WilderSharedWorldgen.WarmRiver.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.WarmRiver.WATER_FOG_COLOR)
					.skyColor(WilderSharedWorldgen.WarmRiver.SKY_COLOR)
					.fogColor(WilderSharedWorldgen.WarmRiver.FOG_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(null)
					.build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addWarmRiverFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addDefaultSprings(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWaterTrees(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addDefaultGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH_RIVER.getKey());
	}

	//WARM BEACH
	@NotNull
	public static Biome warmBeach(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TURTLE, 5, 2, 5));
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addWarmBeachFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.WarmBeach.TEMP)
			.downfall(WilderSharedWorldgen.WarmBeach.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.WarmBeach.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.WarmBeach.WATER_FOG_COLOR)
					.skyColor(WilderSharedWorldgen.WarmBeach.SKY_COLOR)
					.fogColor(WilderSharedWorldgen.WarmBeach.FOG_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(null)
					.build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addWarmBeachFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addDefaultSprings(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addDefaultGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH_RIVER.getKey());
	}

	// CAVE BIOMES
	// JELLYFISH CAVES
	@NotNull
	public static Biome jellyfishCaves(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addJellyfishCavesFeatures(builder2);
		Music music = Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_JELLYFISH_CAVES);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.JellyfishCaves.TEMP)
			.downfall(WilderSharedWorldgen.JellyfishCaves.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.JellyfishCaves.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.JellyfishCaves.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.JellyfishCaves.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.JellyfishCaves.SKY_COLOR)
					.ambientLoopSound(RegisterSounds.AMBIENT_JELLYFISH_CAVES_LOOP)
					.ambientAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_JELLYFISH_CAVES_ADDITIONS, 0.005D))
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(music).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addJellyfishCavesFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
		builder.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.JELLYFISH_STONE_POOL.getKey());
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addPlainGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder, true);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addPlainVegetation(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA.getKey());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.LARGE_MESOGLEA_PURPLE.getKey());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.LARGE_MESOGLEA_BLUE.getKey());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.MESOGLEA_CLUSTER_PURPLE.getKey());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.MESOGLEA_CLUSTER_BLUE.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.MESOGLEA_PILLAR.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.PURPLE_MESOGLEA_PILLAR.getKey());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.BLUE_MESOGLEA_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.PURPLE_MESOGLEA_PATH.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_CALCITE.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEMATOCYST_BLUE.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEMATOCYST_PURPLE.getKey());
	}

	// TRANSITION BIOMES
	// HOT
	@NotNull
	public static Biome aridForest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addAridForestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(false)
			.temperature(WilderSharedWorldgen.AridForest.TEMP)
			.downfall(WilderSharedWorldgen.AridForest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.AridForest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.AridForest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.AridForest.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.AridForest.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addAridForestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_FOREST_TREES.getKey());
		addBasicFeatures(builder, ARID_FOREST);
		BiomeDefaultFeatures.addSavannaGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_CACTUS_PLACED.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRASS_PATH_RARE.getKey());
	}

	@NotNull
	public static Biome aridSavanna(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addAridSavannaFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(false)
			.temperature(WilderSharedWorldgen.AridSavanna.TEMP)
			.downfall(WilderSharedWorldgen.AridSavanna.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.AridSavanna.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.AridSavanna.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.AridSavanna.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.AridSavanna.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addAridSavannaFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_SAVANNA_TREES.getKey());
		addBasicFeatures(builder, ARID_SAVANNA);
		BiomeDefaultFeatures.addSavannaGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_CACTUS_PLACED.getKey());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRASS_PATH_RARE.getKey());
	}

	@NotNull
	public static Biome parchedForest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addParchedForestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(false)
			.temperature(WilderSharedWorldgen.ParchedForest.TEMP)
			.downfall(WilderSharedWorldgen.ParchedForest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.ParchedForest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.ParchedForest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.ParchedForest.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.ParchedForest.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addParchedForestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PARCHED_FOREST_TREES.getKey());
		addBasicFeatures(builder, PARCHED_FOREST);
		BiomeDefaultFeatures.addSavannaGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	// TROPICAL
	@NotNull
	public static Biome birchJungle(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.baseJungleSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addBirchJungleFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.BirchJungle.TEMP)
			.downfall(WilderSharedWorldgen.BirchJungle.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.BirchJungle.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.BirchJungle.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.BirchJungle.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.BirchJungle.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addBirchJungleFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, BIRCH_JUNGLE);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addLightBambooVegetation(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addJungleGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addJungleVines(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_MELON.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIRCH_JUNGLE_TREES.getKey());
	}

	@NotNull
	public static Biome sparseBirchJungle(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.baseJungleSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addSparseBirchJungleFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.BirchJungle.TEMP)
			.downfall(WilderSharedWorldgen.BirchJungle.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.BirchJungle.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.BirchJungle.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.BirchJungle.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.BirchJungle.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SPARSE_JUNGLE)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addSparseBirchJungleFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SPARSE_BIRCH_JUNGLE);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addJungleGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addJungleVines(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_MELON.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SPARSE_BIRCH_JUNGLE_TREES.getKey());
	}

	// TEMPERATE
	@NotNull
	public static Biome birchTaiga(@NotNull BootstapContext<Biome> entries, boolean old) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.farmAnimals(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 2, 3));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addBirchTaigaFeatures(builder2, old);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.BirchTaiga.TEMP)
			.downfall(WilderSharedWorldgen.BirchTaiga.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.BirchTaiga.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.BirchTaiga.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.BirchTaiga.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.BirchTaiga.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addBirchTaigaFeatures(@NotNull BiomeGenerationSettings.Builder builder, boolean old) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, old ? WilderPlacedFeatures.OLD_GROWTH_BIRCH_TAIGA_TREES.getKey() : WilderPlacedFeatures.BIRCH_TAIGA_TREES.getKey());
		addBasicFeatures(builder, BIRCH_TAIGA);
		BiomeDefaultFeatures.addFerns(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addTaigaTrees(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addTaigaGrass(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addCommonBerryBushes(builder);
	}

	@NotNull
	public static Biome semiBirchForest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 2, 2));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addSemiBirchForestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.SemiBirchForest.TEMP)
			.downfall(WilderSharedWorldgen.SemiBirchForest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.SemiBirchForest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.SemiBirchForest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.SemiBirchForest.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.SemiBirchForest.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addSemiBirchForestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SEMI_BIRCH_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SEMI_BIRCH_AND_OAK.getKey());
	}

	@NotNull
	public static Biome darkBirchForest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addDarkBirchForestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.DarkBirchForest.TEMP)
			.downfall(WilderSharedWorldgen.DarkBirchForest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.DarkBirchForest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.DarkBirchForest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.DarkBirchForest.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.DarkBirchForest.SKY_COLOR)
					.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addDarkBirchForestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, DARK_BIRCH_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_BIRCH_FOREST_VEGETATION.getKey());
	}

	@NotNull
	public static Biome flowerField(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 8, 2, 5));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addFlowerFieldFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.FlowerField.TEMP)
			.downfall(WilderSharedWorldgen.FlowerField.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.FlowerField.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.FlowerField.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.FlowerField.FOG_COLOR)
					.foliageColorOverride(WilderSharedWorldgen.FlowerField.FOLIAGE_COLOR)
					.skyColor(WilderSharedWorldgen.FlowerField.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FLOWER_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addFlowerFieldFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, FLOWER_FIELD);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_FLOWER_FIELD_FLOWERS.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FIELD_GRASS_PLACED.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_FLOWER_FIELD.getKey());
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	@NotNull
	public static Biome temperateRainforest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 3, 4, 4));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addTemperateRainforestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.TemperateRainforest.TEMP)
			.downfall(WilderSharedWorldgen.TemperateRainforest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.TemperateRainforest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.TemperateRainforest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.TemperateRainforest.FOG_COLOR)
					.foliageColorOverride(WilderSharedWorldgen.TemperateRainforest.FOLIAGE_COLOR)
					.skyColor(WilderSharedWorldgen.TemperateRainforest.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SPARSE_JUNGLE)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addTemperateRainforestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, TEMPERATE_RAINFOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TEMPERATE_RAINFOREST_TREES.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey());
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	@NotNull
	public static Biome rainforest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addRainforestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.Rainforest.TEMP)
			.downfall(WilderSharedWorldgen.Rainforest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.Rainforest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.Rainforest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.Rainforest.FOG_COLOR)
					.foliageColorOverride(WilderSharedWorldgen.Rainforest.FOLIAGE_COLOR)
					.skyColor(WilderSharedWorldgen.Rainforest.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SPARSE_JUNGLE)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addRainforestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, RAINFOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RAINFOREST_TREES.getKey());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_RAINFOREST_VANILLA.getKey());
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	@NotNull
	public static Biome darkTaiga(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addDarkTaigaFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.DarkTaiga.TEMP)
			.downfall(WilderSharedWorldgen.DarkTaiga.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.DarkTaiga.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.DarkTaiga.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.DarkTaiga.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.DarkTaiga.SKY_COLOR)
					.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addDarkTaigaFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, DARK_TAIGA);
		BiomeDefaultFeatures.addFerns(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addTaigaTrees(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addTaigaGrass(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addCommonBerryBushes(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_TAIGA_VEGETATION.getKey());
	}

	// OLD GROWTH
	@NotNull
	public static Biome oldGrowthDarkForest(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addOldGrowthDarkForestFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperature(WilderSharedWorldgen.OldGrowthDarkForest.TEMP)
			.downfall(WilderSharedWorldgen.OldGrowthDarkForest.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.OldGrowthDarkForest.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.OldGrowthDarkForest.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.OldGrowthDarkForest.FOG_COLOR)
					.skyColor(WilderSharedWorldgen.OldGrowthDarkForest.SKY_COLOR)
					.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addOldGrowthDarkForestFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, OLD_GROWTH_DARK_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OLD_GROWTH_DARK_FOREST_VEGETATION.getKey());
	}

	@NotNull
	public static Biome oldGrowthSnowyTaiga(@NotNull BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
		BiomeDefaultFeatures.caveSpawns(builder);
		BiomeDefaultFeatures.monsters(builder, 100, 25, 100, false);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addOldGrowthSnowyTaigaFeatures(builder2);
		return new Biome.BiomeBuilder()
			.hasPrecipitation(true)
			.temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
			.temperature(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.TEMP)
			.downfall(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.DOWNFALL)
			.specialEffects(
				new BiomeSpecialEffects.Builder()
					.waterColor(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.WATER_COLOR)
					.waterFogColor(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.WATER_FOG_COLOR)
					.fogColor(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.FOG_COLOR)
					.grassColorOverride(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.GRASS_COLOR)
					.foliageColorOverride(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.FOLIAGE_COLOR)
					.skyColor(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.SKY_COLOR)
					.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
					.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA)).build())
			.mobSpawnSettings(builder.build())
			.generationSettings(builder2.build())
			.build();
	}

	public static void addOldGrowthSnowyTaigaFeatures(@NotNull BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SNOWY_OLD_GROWTH_PINE_TAIGA);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addMossyStoneBlock(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addGiantTaigaVegetation(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.getKey());
	}

	private static void addBasicFeatures(@NotNull BiomeGenerationSettings.Builder builder, @NotNull ResourceKey<Biome> biome) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		if (biome == CYPRESS_WETLANDS) {
			builder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
		} else {
			BiomeDefaultFeatures.addDefaultSprings(builder);
		}
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
	}
}
