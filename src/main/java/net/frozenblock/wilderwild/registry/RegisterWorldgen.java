package net.frozenblock.wilderwild.registry;

import java.util.ArrayList;
import net.frozenblock.lib.mobcategory.api.FrozenMobCategories;
import net.frozenblock.lib.worldgen.surface.api.FrozenPresetBoundRuleSource;
import net.frozenblock.lib.worldgen.surface.api.FrozenSurfaceRuleEntrypoint;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.feature.WilderFeatureBootstrap;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.minecraft.core.registries.Registries;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.frozenblock.wilderwild.world.generation.noise.WilderNoise;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import static net.minecraft.data.worldgen.biome.OverworldBiomes.jungle;
import static net.minecraft.data.worldgen.biome.OverworldBiomes.swamp;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
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

public final class RegisterWorldgen {
	private RegisterWorldgen() {
		throw new UnsupportedOperationException("RegisterWorldgen contains only static declarations.");
	}

	public static final ResourceKey<Biome> CYPRESS_WETLANDS = register("cypress_wetlands");
	public static final ResourceKey<Biome> JELLYFISH_CAVES = register("jellyfish_caves");
    public static final ResourceKey<Biome> MIXED_FOREST = register("mixed_forest");
	public static final ResourceKey<Biome> WARM_RIVER = register("warm_river");
	public static final ResourceKey<Biome> OASIS = register("oasis");

	public static void bootstrap(BootstapContext<Biome> context) {
		context.register(RegisterWorldgen.CYPRESS_WETLANDS, RegisterWorldgen.cypressWetlands(context));
		context.register(RegisterWorldgen.JELLYFISH_CAVES, RegisterWorldgen.jellyfishCaves(context));
		context.register(RegisterWorldgen.MIXED_FOREST, RegisterWorldgen.mixedForest(context));
		context.register(RegisterWorldgen.WARM_RIVER, RegisterWorldgen.warmRiver(context));
		context.register(RegisterWorldgen.OASIS, RegisterWorldgen.oasis(context));
	}

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, WilderSharedConstants.id(name));
    }

    public static Biome mixedForest(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
        BiomeDefaultFeatures.plainsSpawns(builder);
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        addMixedForestFeatures(builder2);
        Music musicSound = Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS);
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.7F)
                .downfall(0.7F)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(12638463)
                                .skyColor(jungle(placedFeatures, worldCarvers).getSkyColor())
                                .foliageColorOverride(5879634)
                                .grassColorOverride(8437607)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(musicSound).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static Biome cypressWetlands(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
        addCypressWetlandsMobs(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        addCypressWetlandsFeatures(builder2);
        Music musicSound = Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS);
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.6F)
                .downfall(0.7F)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(4552818)
                                .waterFogColor(4552818)
                                .fogColor(12638463)
								.skyColor(swamp(placedFeatures, worldCarvers).getSkyColor())
                                .foliageColorOverride(5877296)
                                .grassColorOverride(7979098)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(musicSound).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static Biome jellyfishCaves(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        addJellyfishCavesFeatures(builder2);
        Music music = Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_JELLYFISH_CAVES);
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.8F)
                .downfall(0.4F)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(9817343)
                                .waterFogColor(6069471)
                                .fogColor(0)
                                .skyColor(OverworldBiomes.calculateSkyColor(0.8F))
								.ambientLoopSound(RegisterSounds.AMBIENT_JELLYFISH_CAVES_LOOP)
								.ambientAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_JELLYFISH_CAVES_ADDITIONS, 0.0005D))
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(music).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

	public static Biome warmRiver(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4)).addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
		BiomeDefaultFeatures.commonSpawns(builder);
		builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 100, 1, 1));
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addWarmRiverFeatures(builder2);

		Music music = Musics.GAME;
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(1.5F)
				.downfall(0.0F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.grassColorOverride(12564309)
								.foliageColorOverride(11445290)
								.waterColor(4566514)
								.waterFogColor(267827)
								.skyColor(OverworldBiomes.calculateSkyColor(1.5F))
								.fogColor(12638463)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(music)
								.build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static Biome oasis(BootstapContext<Biome> entries) {
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		var worldCarvers = entries.lookup(Registries.CONFIGURED_CARVER);
		MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.desertSpawns(builder);
		BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
		addOasisFeatures(builder2);

		Music music = Musics.GAME;
		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.temperature(2.0F)
				.downfall(0.0F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.grassColorOverride(8569413)
								.foliageColorOverride(3193611)
								.waterColor(4566514)
								.waterFogColor(267827)
								.skyColor(OverworldBiomes.calculateSkyColor(2.0F))
								.fogColor(12638463)
								.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
								.backgroundMusic(music)
								.build())
				.mobSpawnSettings(builder.build())
				.generationSettings(builder2.build())
				.build();
	}

	public static void addCypressPaths(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_SAND_PATH);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH);
	}

    public static void addCypressWetlandsFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FERN_PLACED);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_TALL_GRASS_PLACED);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEAGRASS_CYPRESS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_CYPRESS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED_CYPRESS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED);
        addCypressPaths(builder);
        addBasicFeatures(builder, CYPRESS_WETLANDS);
        addCypressVegetation(builder);
    }

	public static void addCypressVegetation(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
	}

    public static void addMixedForestFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_MIXED);
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_5);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_TREES);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED);
        addBasicFeatures(builder, MIXED_FOREST);
        BiomeDefaultFeatures.addForestFlowers(builder);
		BiomeDefaultFeatures.addForestGrass(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
    }

	public static void addJellyfishCavesFeatures(BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.JELLYFISH_DEEPSLATE_POOL);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.JELLYFISH_STONE_POOL);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.addPlainGrass(builder);
		BiomeDefaultFeatures.addDefaultOres(builder, true);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addPlainVegetation(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.MESOGLEA_PILLAR);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.PURPLE_MESOGLEA_PILLAR);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_CALCITE);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEMATOCYST);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEMATOCYST_PURPLE);
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
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH_RIVER);
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
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_PACKED_MUD);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.SAND_POOL);
		builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MOSS_PATH);
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.GRASS_PATH);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_GRASS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_BUSH_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_CACTUS_PLACED);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALMS_OASIS);
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

	public static void addCypressWetlandsMobs(MobSpawnSettings.Builder builder) {
		builder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 5, 2, 6));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 12, 4, 5));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 3, 2, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 2, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 6, 4, 4));
		builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 4, 4));
		builder.addSpawn(FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "fireflies"), new MobSpawnSettings.SpawnerData(RegisterEntities.FIREFLY, 1, 2, 6));
	}

}
