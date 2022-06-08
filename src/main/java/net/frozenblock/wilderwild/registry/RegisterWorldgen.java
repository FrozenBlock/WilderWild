package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.sound.MusicType;
import net.minecraft.data.server.BiomeParametersProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

import static net.minecraft.world.biome.OverworldBiomeCreator.*;


public class RegisterWorldgen {
    public static final RegistryKey<Biome> MIXED_FOREST = register("mixed_forest");
    public static final RegistryKey<Biome> CYPRESS_FOREST = register("cypress_forest");

    public static void RegisterWorldGen() {
        BuiltinRegistries.add(BuiltinRegistries.BIOME, MIXED_FOREST, createMixedForest());
        BuiltinRegistries.add(BuiltinRegistries.BIOME, CYPRESS_FOREST, createCypressForest());
    }

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(WilderWild.MOD_ID, name));
    }

    public static Biome createMixedForest() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        DefaultBiomeFeatures.addPlainsMobs(builder);
        GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
        addBasicFeatures(builder2);
        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addDefaultDisks(builder2);
        MusicSound musicSound = MusicType.createIngameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS);
        return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).temperature(0.7F).downfall(0.8F).effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(createJungle().getSkyColor()).foliageColor(5877296).grassColor(7979098).moodSound(BiomeMoodSound.CAVE).music(musicSound).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
    }


    public static Biome createCypressForest() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FROG, 1, 1, 2));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PIG, 10, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 9, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.COW, 3, 4, 4));
        builder.spawn(WilderWild.FIREFLIES, new SpawnSettings.SpawnEntry(RegisterEntities.FIREFLY, 1, 2, 6));
        GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
        addBasicFeatures(builder2);
        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addDefaultDisks(builder2);
        MusicSound musicSound = MusicType.createIngameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS);
        return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).temperature(0.7F).downfall(0.7F).effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(createJungle().getSkyColor()).foliageColor(5877296).grassColor(7979098).moodSound(BiomeMoodSound.CAVE).music(musicSound).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
    }

    private static void addBasicFeatures(GenerationSettings.Builder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }
}