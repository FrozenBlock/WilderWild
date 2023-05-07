/*
 * Copyright 2022-2023 FrozenBlock
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

import java.util.List;
import net.frozenblock.lib.worldgen.surface.api.SurfaceRuleEvents;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.frozenblock.wilderwild.world.generation.noise.WilderNoise;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
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
import net.minecraft.world.level.levelgen.SurfaceRules;

public final class RegisterWorldgen implements SurfaceRuleEvents.OverworldSurfaceRuleCallback {

	// Main Biomes
	public static final ResourceKey<Biome> CYPRESS_WETLANDS = register("cypress_wetlands");
	public static final ResourceKey<Biome> MIXED_FOREST = register("mixed_forest");
	public static final ResourceKey<Biome> OASIS = register("oasis");
	public static final ResourceKey<Biome> WARM_RIVER = register("warm_river");
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
	// OLD GROWTH
	public static final ResourceKey<Biome> OLD_GROWTH_BIRCH_TAIGA = register("old_growth_birch_taiga");
	public static final ResourceKey<Biome> OLD_GROWTH_DARK_FOREST = register("old_growth_dark_forest");
	public static final ResourceKey<Biome> SNOWY_OLD_GROWTH_PINE_TAIGA = register("snowy_old_growth_pine_taiga");

	static {
		// MAIN BIOMES
		BuiltinRegistries.register(BuiltinRegistries.BIOME, CYPRESS_WETLANDS, cypressWetlands());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, MIXED_FOREST, mixedForest());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, OASIS, oasis());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, WARM_RIVER, warmRiver());
		// CAVE BIOMES
		BuiltinRegistries.register(BuiltinRegistries.BIOME, JELLYFISH_CAVES, jellyfishCaves());
		// TRANSITION BIOMES
		// HOT
		BuiltinRegistries.register(BuiltinRegistries.BIOME, ARID_FOREST, aridForest());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, ARID_SAVANNA, aridSavanna());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, PARCHED_FOREST, parchedForest());
		// TROPICAL
		BuiltinRegistries.register(BuiltinRegistries.BIOME, BIRCH_JUNGLE, birchJungle());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, SPARSE_BIRCH_JUNGLE, sparseBirchJungle());
		// TEMPERATE
		BuiltinRegistries.register(BuiltinRegistries.BIOME, BIRCH_TAIGA, birchTaiga(false));
		BuiltinRegistries.register(BuiltinRegistries.BIOME, SEMI_BIRCH_FOREST, semiBirchForest());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, DARK_BIRCH_FOREST, darkBirchForest());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, FLOWER_FIELD, flowerField());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, TEMPERATE_RAINFOREST, temperateRainforest());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, RAINFOREST, rainforest());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, DARK_TAIGA, darkTaiga());
		// OLD GROWTH
		BuiltinRegistries.register(BuiltinRegistries.BIOME, OLD_GROWTH_BIRCH_TAIGA, birchTaiga(true));
		BuiltinRegistries.register(BuiltinRegistries.BIOME, OLD_GROWTH_DARK_FOREST, oldGrowthDarkForest());
		BuiltinRegistries.register(BuiltinRegistries.BIOME, SNOWY_OLD_GROWTH_PINE_TAIGA, oldGrowthSnowyTaiga());

		WilderNoise.init();
	}

	public static void registerWorldgen() {
		WilderSharedConstants.logWild("Registering Biomes for", WilderSharedConstants.UNSTABLE_LOGGING);
	}

	private static ResourceKey<Biome> register(String name) {
		return ResourceKey.create(Registry.BIOME_REGISTRY, WilderSharedConstants.id(name));
	}
	// MAIN BIOMES
	// CYPRESS WETLANDS
	public static Biome cypressWetlands() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		addCypressWetlandsMobs(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addCypressWetlandsFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
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
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addCypressPaths(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_SAND_PATH.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH.getHolder());
	}

	public static void addCypressWetlandsFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEAGRASS_CYPRESS.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FLOWER_PLACED.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER.getHolder());
		addCypressPaths(builder);
		addBasicFeatures(builder, CYPRESS_WETLANDS);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		addCypressVegetation(builder);
	}

	public static void addCypressVegetation(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
	}
	public static void addCypressWetlandsMobs(MobSpawnSettings.Builder builder) {
		builder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 5, 2, 6));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 14, 4, 5));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 3, 2, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 2, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 6, 4, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 4, 4));
	}
	// MIXED FOREST
	public static Biome mixedForest() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addMixedForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(WilderSharedWorldgen.MixedForest.TEMP)
				.downfall(WilderSharedWorldgen.MixedForest.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(WilderSharedWorldgen.MixedForest.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.MixedForest.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.MixedForest.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.MixedForest.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addMixedForestFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_TREES.getHolder());
		addBasicFeatures(builder, MIXED_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
	}
	// OASIS
	public static Biome oasis() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.desertSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addOasisFeatures(builder2);

		Music music = Musics.GAME;
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
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
								.backgroundMusic(music)
								.build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addOasisFeatures(BiomeGenerationSettings.Builder builder) {
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
		builder.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.SAND_POOL.getHolder());
		builder.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.MESSY_SAND_POOL.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRASS_PATH.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_GRASS_PLACED.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_CACTUS_PLACED.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALMS_OASIS.getHolder());
		builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, WilderMiscPlaced.DESERT_WELL.getHolder());
	}
	// WARM RIVER
	public static Biome warmRiver() {
		MobSpawnSettings.Builder builder = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4)).addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
		BiomeDefaultFeatures.commonSpawns(builder);
		builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 100, 1, 1));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addWarmRiverFeatures(builder2);

		Music music = Musics.GAME;
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
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
								.backgroundMusic(music)
								.build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addWarmRiverFeatures(BiomeGenerationSettings.Builder builder) {
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
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH_RIVER.getHolder());
	}

	// CAVE BIOMES
	// JELLYFISH CAVES
	public static Biome jellyfishCaves() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addJellyfishCavesFeatures(builder2);
		Music music = Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_JELLYFISH_CAVES);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
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
	public static void addJellyfishCavesFeatures(BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.JELLYFISH_DEEPSLATE_POOL.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.JELLYFISH_STONE_POOL.getHolder());
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addPlainGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder, true);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addPlainVegetation(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA.getHolder());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.LARGE_MESOGLEA_PURPLE.getHolder());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.LARGE_MESOGLEA_BLUE.getHolder());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.MESOGLEA_CLUSTER_PURPLE.getHolder());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.MESOGLEA_CLUSTER_BLUE.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.MESOGLEA_PILLAR.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.PURPLE_MESOGLEA_PILLAR.getHolder());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.BLUE_MESOGLEA_PATH.getHolder());
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.PURPLE_MESOGLEA_PATH.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_CALCITE.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEMATOCYST_BLUE.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEMATOCYST_PURPLE.getHolder());
	}

	// TRANSITION BIOMES
	// HOT
	 public static Biome aridForest() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addAridForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(WilderSharedWorldgen.AridForest.TEMP)
				.downfall(WilderSharedWorldgen.AridForest.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(WilderSharedWorldgen.AridForest.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.AridForest.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.AridForest.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.AridForest.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addAridForestFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_FOREST_TREES.getHolder());
		addBasicFeatures(builder, ARID_FOREST);
		BiomeDefaultFeatures.addSavannaGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_CACTUS_PLACED.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRASS_PATH_RARE.getHolder());
	}
	public static Biome aridSavanna() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addAridSavannaFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_GAME);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(WilderSharedWorldgen.AridSavanna.TEMP)
				.downfall(WilderSharedWorldgen.AridSavanna.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(WilderSharedWorldgen.AridSavanna.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.AridSavanna.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.AridSavanna.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.AridSavanna.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addAridSavannaFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_SAVANNA_TREES.getHolder());
		addBasicFeatures(builder, ARID_SAVANNA);
		BiomeDefaultFeatures.addSavannaGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addSavannaExtraGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_CACTUS_PLACED.getHolder());
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRASS_PATH_RARE.getHolder());
	}
	public static Biome parchedForest() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addParchedForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(WilderSharedWorldgen.ParchedForest.TEMP)
				.downfall(WilderSharedWorldgen.ParchedForest.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(WilderSharedWorldgen.ParchedForest.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.ParchedForest.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.ParchedForest.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.ParchedForest.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addParchedForestFeatures(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PARCHED_FOREST_TREES.getHolder());
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
	public static Biome birchJungle() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.baseJungleSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addBirchJungleFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(WilderSharedWorldgen.BirchJungle.TEMP)
				.downfall(WilderSharedWorldgen.BirchJungle.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(WilderSharedWorldgen.BirchJungle.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.BirchJungle.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.BirchJungle.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.BirchJungle.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addBirchJungleFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, BIRCH_JUNGLE);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addLightBambooVegetation(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addJungleGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addJungleVines(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_MELON.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIRCH_JUNGLE_TREES.getHolder());
	}

	public static Biome sparseBirchJungle() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.baseJungleSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addSparseBirchJungleFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(WilderSharedWorldgen.BirchJungle.TEMP)
				.downfall(WilderSharedWorldgen.BirchJungle.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(WilderSharedWorldgen.BirchJungle.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.BirchJungle.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.BirchJungle.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.BirchJungle.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addSparseBirchJungleFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SPARSE_BIRCH_JUNGLE);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addWarmFlowers(builder);
		BiomeDefaultFeatures.addJungleGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addJungleVines(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_MELON.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SPARSE_BIRCH_JUNGLE_TREES.getHolder());
	}
	// TEMPERATE
	public static Biome birchTaiga(boolean old) {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.farmAnimals(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 2, 3));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addBirchTaigaFeatures(builder2, old);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(WilderSharedWorldgen.BirchTaiga.TEMP)
				.downfall(WilderSharedWorldgen.BirchTaiga.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(WilderSharedWorldgen.BirchTaiga.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.BirchTaiga.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.BirchTaiga.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.BirchTaiga.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addBirchTaigaFeatures(BiomeGenerationSettings.Builder builder, boolean old) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, old ? WilderPlacedFeatures.OLD_GROWTH_BIRCH_TAIGA_TREES.getHolder() : WilderPlacedFeatures.BIRCH_TAIGA_TREES.getHolder());
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
	public static Biome semiBirchForest() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 2, 2));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addSemiBirchForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.temperature(WilderSharedWorldgen.SemiBirchForest.TEMP)
				.downfall(WilderSharedWorldgen.SemiBirchForest.DOWNFALL)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(WilderSharedWorldgen.SemiBirchForest.WATER_COLOR)
								.waterFogColor(WilderSharedWorldgen.SemiBirchForest.WATER_FOG_COLOR)
								.fogColor(WilderSharedWorldgen.SemiBirchForest.FOG_COLOR)
								.skyColor(WilderSharedWorldgen.SemiBirchForest.SKY_COLOR)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addSemiBirchForestFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SEMI_BIRCH_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SEMI_BIRCH_AND_OAK.getHolder());
	}
	public static Biome darkBirchForest() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addDarkBirchForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
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
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addDarkBirchForestFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, DARK_BIRCH_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_BIRCH_FOREST_VEGETATION.getHolder());
	}
	public static Biome flowerField() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 8, 2, 5));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addFlowerFieldFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_GAME);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
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
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static void addFlowerFieldFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, FLOWER_FIELD);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_FLOWER_FIELD_FLOWERS.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FIELD_GRASS_PLACED.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_FLOWER_FIELD.getHolder());
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	public static Biome temperateRainforest() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 3, 4, 4));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addTemperateRainforestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
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
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addTemperateRainforestFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, TEMPERATE_RAINFOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TEMPERATE_RAINFOREST_TREES.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA.getHolder());
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	public static Biome rainforest() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeDefaultFeatures.plainsSpawns(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addRainforestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
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
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addRainforestFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, RAINFOREST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RAINFOREST_TREES.getHolder());
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_RAINFOREST_VANILLA.getHolder());
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
	}

	public static Biome darkTaiga() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addDarkTaigaFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
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
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addDarkTaigaFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, DARK_TAIGA);
		BiomeDefaultFeatures.addFerns(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addTaigaTrees(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addTaigaGrass(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addCommonBerryBushes(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_TAIGA_VEGETATION.getHolder());
	}

	// OLD GROWTH
	public static Biome oldGrowthDarkForest() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		BiomeDefaultFeatures.commonSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addOldGrowthDarkForestFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE_AND_FOREST);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
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
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addOldGrowthDarkForestFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, OLD_GROWTH_DARK_FOREST);
		BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OLD_GROWTH_DARK_FOREST_VEGETATION.getHolder());
	}
	public static Biome oldGrowthSnowyTaiga() {
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(builder);
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
		BiomeDefaultFeatures.caveSpawns(builder);
		BiomeDefaultFeatures.monsters(builder, 100, 25, 100, false);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
		addOldGrowthSnowyTaigaFeatures(builder2);
		Music musicSound = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA);
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.SNOW)
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
								.backgroundMusic(musicSound).build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}
	public static void addOldGrowthSnowyTaigaFeatures(BiomeGenerationSettings.Builder builder) {
		addBasicFeatures(builder, SNOWY_OLD_GROWTH_PINE_TAIGA);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addMossyStoneBlock(builder);
		BiomeDefaultFeatures.addDefaultFlowers(builder);
		BiomeDefaultFeatures.addGiantTaigaVegetation(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.getHolder());
	}

	private static void addBasicFeatures(BiomeGenerationSettings.Builder builder, ResourceKey<Biome> biome) {
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

	@Override
	public void addOverworldSurfaceRules(List<SurfaceRules.RuleSource> context) {
		var rules = SurfaceRules.sequence(
			WilderSharedWorldgen.betaBeaches(),
			WilderSharedWorldgen.cypressSurfaceRules(),
			WilderSharedWorldgen.warmRiverRules(),
			WilderSharedWorldgen.oasisRules(),
			WilderSharedWorldgen.aridGrass(),
			WilderSharedWorldgen.aridRules(),
			WilderSharedWorldgen.oldGrowthSnowyTaigaRules(),
			WilderSharedWorldgen.oldGrowthDarkForestRules(),
			WilderSharedWorldgen.temperateRainforestRules(),
			WilderSharedWorldgen.rainforestRules()
		);

		context.add(rules);
		WilderSharedConstants.log("Wilder Wild's Overworld Surface Rules have been added!", true);
	}
}
