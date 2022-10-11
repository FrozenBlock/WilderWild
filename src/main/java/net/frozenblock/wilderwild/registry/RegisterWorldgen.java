package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.worldgen.surface.FrozenSurfaceRules;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.gen.SharedWorldgen;
import net.frozenblock.wilderwild.world.gen.noise.WilderNoise;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.worldgen.surface_rule.api.SurfaceRuleContext;
import org.quiltmc.qsl.frozenblock.worldgen.surface_rule.api.SurfaceRuleEvents;

import static net.minecraft.data.worldgen.biome.OverworldBiomes.jungle;
import static net.minecraft.data.worldgen.biome.OverworldBiomes.swamp;

public final class RegisterWorldgen implements SurfaceRuleEvents.OverworldModifierCallback, SurfaceRuleEvents.NetherModifierCallback {
    public static final ResourceKey<Biome> MIXED_FOREST = register("mixed_forest");
    public static final ResourceKey<Biome> CYPRESS_WETLANDS = register("cypress_wetlands");
    public static final ResourceKey<Biome> JELLYFISH_CAVES = register("jellyfish_caves");

    public static void registerWorldgen() {
        WilderWild.logWild("Registering Biomes for", WilderWild.UNSTABLE_LOGGING);
        BuiltinRegistries.register(BuiltinRegistries.BIOME, MIXED_FOREST.location(), mixedForest());
        BuiltinRegistries.register(BuiltinRegistries.BIOME, CYPRESS_WETLANDS.location(), cypressWetlands());
        BuiltinRegistries.register(BuiltinRegistries.BIOME, JELLYFISH_CAVES.location(), jellyfishCaves());

        WilderNoise.init();
    }

    @Override
    public void modifyOverworldRules(SurfaceRuleContext.@NotNull Overworld context) {
        context.materialRules().add(0, SharedWorldgen.cypressSurfaceRules());
        if (ClothConfigInteractionHandler.betaBeaches()) {
            context.materialRules().add(0, SharedWorldgen.betaBeachSurfaceRules());
        }

        // FROM QUILT'S TEST MOD
        var blueNoise1 = SurfaceRules.noiseCondition(Noises.CALCITE, 0.05, 0.1);
        var pinkNoise1 = SurfaceRules.noiseCondition(Noises.CALCITE, 0.1, 0.15);
        var whiteNoise = SurfaceRules.noiseCondition(Noises.CALCITE, 0.15, 0.20);
        var pinkNoise2 = SurfaceRules.noiseCondition(Noises.CALCITE, 0.20, 0.25);
        var blueNoise2 = SurfaceRules.noiseCondition(Noises.CALCITE, 0.25, 0.30);

        var LIGHT_BLUE_CONCRETE = FrozenSurfaceRules.makeStateRule(Blocks.LIGHT_BLUE_CONCRETE);
        var PINK_CONCRETE = FrozenSurfaceRules.makeStateRule(Blocks.PINK_CONCRETE);
        var WHITE_CONCRETE = FrozenSurfaceRules.makeStateRule(Blocks.WHITE_CONCRETE);
/*
        context.addMaterialRule(
                (
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(blueNoise1, LIGHT_BLUE_CONCRETE),
                                        SurfaceRules.ifTrue(pinkNoise1, PINK_CONCRETE),
                                        SurfaceRules.ifTrue(whiteNoise, WHITE_CONCRETE),
                                        SurfaceRules.ifTrue(pinkNoise2, PINK_CONCRETE),
                                        SurfaceRules.ifTrue(blueNoise2, LIGHT_BLUE_CONCRETE)
                                )
                        )
                )
        )
        );

 */
        WilderWild.log("Wilder Wild's Overworld Surface Rules have been added!", WilderWild.UNSTABLE_LOGGING);
    }

    // SPONGEBOB
    @Override
    public void modifyNetherRules(SurfaceRuleContext.@NotNull Nether context) {
        /*context.materialRules().clear();
        context.materialRules().add(0, FrozenSurfaceRules.makeStateRule(Blocks.SPONGE));*/
        WilderWild.log("SPONGEBOB", WilderWild.UNSTABLE_LOGGING);
    }

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, WilderWild.id(name));
    }

    public static Biome mixedForest() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
        BiomeDefaultFeatures.plainsSpawns(builder);
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
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
                                .skyColor(jungle().getSkyColor())
                                .foliageColorOverride(5879634)
                                .grassColorOverride(8437607)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(musicSound).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static Biome cypressWetlands() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
        addCypressWetlandsMobs(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
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
                                .skyColor(swamp().getSkyColor())
                                .foliageColorOverride(5877296)
                                .grassColorOverride(7979098)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(musicSound).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static Biome jellyfishCaves() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder();
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
        if (ClothConfigInteractionHandler.fallenLogs()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED);
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
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_MIXED);
        builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_5);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_TREES);
        if (ClothConfigInteractionHandler.fallenLogs()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED);
        }
        addBasicFeatures(builder, MIXED_FOREST);
        BiomeDefaultFeatures.addForestFlowers(builder);
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
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_UP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_DOWN);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_EAST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_WEST);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_NORTH);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_NEMATOCYST_SOUTH);
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