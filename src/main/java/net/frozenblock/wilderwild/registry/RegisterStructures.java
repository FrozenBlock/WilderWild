package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.structure.AbandonedCabinGenerator;
import net.frozenblock.wilderwild.world.structure.WilderStructureProcessors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.structure.StructureSet;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.registry.*;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RegisterStructures {

    public static final RegistryKey<StructureSet> ABANDONED_CABINS_KEY = ofSet("abandoned_cabins");

    private static RegistryKey<StructureSet> ofSet(String id) {
        return RegistryKey.of(Registry.STRUCTURE_SET_KEY, WilderWild.id(id));
    }

    static RegistryEntry<StructureSet> initAndGetDefault(Registry<StructureSet> registry) {
        return registry.streamEntries().iterator().next();
    }

    static RegistryEntry<StructureSet> register(RegistryKey<StructureSet> key, StructureSet structureSet) {
        return BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_SET, key, structureSet);
    }

    static RegistryEntry<StructureSet> register(RegistryKey<StructureSet> key, RegistryEntry<Structure> structure, StructurePlacement placement) {
        return register(key, new StructureSet(structure, placement));
    }

    private static final RegistryKey<Structure> ABANDONED_CABIN_KEY = of("abandoned_cabin");

    public static final RegistryEntry<Structure> ABANDONED_CABIN = register(
            ABANDONED_CABIN_KEY,
            new JigsawStructure(
                    createConfig(
                            WilderBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE,
                            Arrays.stream(SpawnGroup.values())
                                    .collect(Collectors.toMap(spawnGroup -> spawnGroup, spawnGroup -> new StructureSpawns(StructureSpawns.BoundingBox.STRUCTURE, Pool.empty()))),
                            GenerationStep.Feature.UNDERGROUND_DECORATION,
                            StructureTerrainAdaptation.BEARD_BOX
                    ),
                    AbandonedCabinGenerator.CABIN,
                    Optional.of(new Identifier("city_anchor")),
                    3,
                    ConstantHeightProvider.create(YOffset.fixed(-26)),
                    false,
                    Optional.empty(),
                    60
            )
    );

    // ancient city salt is 20083232
    public static final RegistryEntry<StructureSet> ABANDONED_CABINS = register(
            ABANDONED_CABINS_KEY, ABANDONED_CABIN, new RandomSpreadStructurePlacement(8, 3, SpreadType.LINEAR, 20088232)
    );

    public static void init() {
        WilderStructureProcessors.init();
        AbandonedCabinGenerator.init();
    }

    private static RegistryKey<Structure> of(String id) {
        return RegistryKey.of(Registry.STRUCTURE_KEY, WilderWild.id(id));
    }

    private static Structure.Config createConfig(
            TagKey<Biome> biomeTag, Map<SpawnGroup, StructureSpawns> spawns, GenerationStep.Feature featureStep, StructureTerrainAdaptation terrainAdaptation
    ) {
        return new Structure.Config(getOrCreateBiomeTag(biomeTag), spawns, featureStep, terrainAdaptation);
    }

    private static Structure.Config createConfig(TagKey<Biome> biomeTag, GenerationStep.Feature featureStep, StructureTerrainAdaptation terrainAdaptation) {
        return createConfig(biomeTag, Map.of(), featureStep, terrainAdaptation);
    }

    private static Structure.Config createConfig(TagKey<Biome> biomeTag, StructureTerrainAdaptation terrainAdaptation) {
        return createConfig(biomeTag, Map.of(), GenerationStep.Feature.SURFACE_STRUCTURES, terrainAdaptation);
    }

    private static RegistryEntry<Structure> register(RegistryKey<Structure> key, Structure structure) {
        return BuiltinRegistries.add(BuiltinRegistries.STRUCTURE, key, structure);
    }

    private static RegistryEntryList<Biome> getOrCreateBiomeTag(TagKey<Biome> key) {
        return BuiltinRegistries.BIOME.getOrCreateEntryList(key);
    }
}
