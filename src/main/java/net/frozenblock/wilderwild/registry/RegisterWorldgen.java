package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.gen.SharedWorldgen;
import net.frozenblock.wilderwild.world.gen.noise.WilderNoise;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.worldgen.surface_rule.api.SurfaceRuleContext;
import org.quiltmc.qsl.frozenblock.worldgen.surface_rule.api.SurfaceRuleEvents;
import static net.minecraft.data.worldgen.biome.OverworldBiomes.*;

public final class RegisterWorldgen implements SurfaceRuleEvents.OverworldModifierCallback {

	public static final ResourceKey<Biome> CYPRESS_WETLANDS = register("cypress_wetlands");
	public static final ResourceKey<Biome> JELLYFISH_CAVES = register("jellyfish_caves");
    public static final ResourceKey<Biome> MIXED_FOREST = register("mixed_forest");

    public static void registerWorldgen(FabricWorldgenProvider.Entries entries) {
        WilderWild.logWild("Registering Biomes for", WilderSharedConstants.UNSTABLE_LOGGING);
		HolderGetter<PlacedFeature> placedFeatures = entries.placedFeatures();
		HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = entries.configuredCarvers();
		entries.add(CYPRESS_WETLANDS, cypressWetlands(placedFeatures, worldCarvers));
		entries.add(JELLYFISH_CAVES, jellyfishCaves(placedFeatures, worldCarvers));
		entries.add(MIXED_FOREST, mixedForest(placedFeatures, worldCarvers));

        WilderNoise.init();
    }

    @Override
    public void modifyOverworldRules(SurfaceRuleContext.@NotNull Overworld context) {
        context.ruleSources().add(0, SharedWorldgen.cypressSurfaceRules());
		context.ruleSources().add(SharedWorldgen.cypressSurfaceRules());
        if (ClothConfigInteractionHandler.betaBeaches()) {
            context.ruleSources().add(0, SharedWorldgen.gravelBetaBeaches());
			context.ruleSources().add(0, SharedWorldgen.sandBetaBeaches());
			context.ruleSources().add(0, SharedWorldgen.multilayerSandBetaBeaches());
			context.ruleSources().add(SharedWorldgen.gravelBetaBeaches());
			context.ruleSources().add(SharedWorldgen.sandBetaBeaches());
			context.ruleSources().add(SharedWorldgen.multilayerSandBetaBeaches());
        }
        WilderWild.log("Wilder Wild's Overworld Surface Rules have been added!", WilderSharedConstants.UNSTABLE_LOGGING);
    }

    // SPONGEBOB
    /*@Override
    public void modifyNetherRules(SurfaceRuleContext.@NotNull Nether context) {
        /*context.materialRules().clear();
        context.materialRules().add(0, FrozenSurfaceRules.makeStateRule(Blocks.SPONGE));
        WilderWild.log("SPONGEBOB", WilderSharedConstants.UNSTABLE_LOGGING);
    }*/

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, WilderSharedConstants.id(name));
    }

    public static Biome mixedForest(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
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
                                .skyColor(calculateSkyColor(0.7F))
                                .foliageColorOverride(5879634)
                                .grassColorOverride(8437607)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(musicSound).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static Biome cypressWetlands(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
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
								.skyColor(calculateSkyColor(0.8F))
                                .foliageColorOverride(5877296)
                                .grassColorOverride(7979098)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(musicSound).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static Biome jellyfishCaves(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
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
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(music).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static void addCypressPaths(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_SAND_PATH.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH.getResourceKey());
    }

    public static void addCypressWetlandsFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FERN_PLACED.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_TALL_GRASS_PLACED.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEAGRASS_CYPRESS.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_CYPRESS.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED_CYPRESS.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER.getResourceKey());
        if (ClothConfigInteractionHandler.fallenLogs()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED.getResourceKey());
        }
        addCypressPaths(builder);
        addBasicFeatures(builder, CYPRESS_WETLANDS);
        addCypressVegetation(builder);
    }

    public static void addCypressVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
    }

    public static void addMixedForestFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_MIXED.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_5.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_TREES.getResourceKey());
        if (ClothConfigInteractionHandler.fallenLogs()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getResourceKey());
        }
        addBasicFeatures(builder, MIXED_FOREST);
        BiomeDefaultFeatures.addForestFlowers(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
    }

    public static void addJellyfishCavesFeatures(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.JELLYFISH_DEEPSLATE_POOL.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.JELLYFISH_STONE_POOL.getResourceKey());
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        BiomeDefaultFeatures.addPlainGrass(builder);
        BiomeDefaultFeatures.addDefaultOres(builder, true);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
        BiomeDefaultFeatures.addPlainVegetation(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.MESOGLEA_PILLAR.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.PURPLE_MESOGLEA_PILLAR.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_CALCITE.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_UP.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_DOWN.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_EAST.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_WEST.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_NORTH.getResourceKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_SOUTH.getResourceKey());
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
        builder.addSpawn(WilderWild.FIREFLIES, new MobSpawnSettings.SpawnerData(RegisterEntities.FIREFLY, 1, 2, 6));
    }

}
