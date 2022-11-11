package net.frozenblock.wilderwild.registry;

import java.util.Map;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.structure.AbandonedCabinGenerator;
import net.frozenblock.wilderwild.world.structure.WilderStructureProcessors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public final class RegisterStructures {
	private RegisterStructures() {
		throw new UnsupportedOperationException("RegisterStructures contains only static declarations.");
	}

    public static final int MAX_JIGSAW_SIZE = 24;

    public static final int MAX_DISTANCE_FROM_JIGSAW_CENTER = 128;

    public static final ResourceKey<StructureSet> ABANDONED_CABINS_KEY = ofSet("abandoned_cabins");

    private static ResourceKey<StructureSet> ofSet(String id) {
        return ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, WilderSharedConstants.id(id));
    }

    public static Holder<StructureSet> initAndGetDefault(Registry<StructureSet> registry) {
        return registry.holders().iterator().next();
    }

    private static final ResourceKey<Structure> ABANDONED_CABIN_KEY = createKey("abandoned_cabin");

    public static void init() {
        WilderWild.logWild("Registering Structures for", WilderSharedConstants.UNSTABLE_LOGGING);
        WilderStructureProcessors.init();
        AbandonedCabinGenerator.init();
    }

    private static ResourceKey<Structure> createKey(String id) {
        return ResourceKey.create(Registry.STRUCTURE_REGISTRY, WilderSharedConstants.id(id));
    }

    private static Structure.StructureSettings createConfig(
            TagKey<Biome> biomeTag, Map<MobCategory, StructureSpawnOverride> spawns, GenerationStep.Decoration featureStep, TerrainAdjustment terrainAdaptation
    ) {
        return new Structure.StructureSettings(VanillaRegistries.createLookup().lookupOrThrow(Registry.BIOME_REGISTRY).getOrThrow(biomeTag), spawns, featureStep, terrainAdaptation);
    }

    private static Structure.StructureSettings createConfig(TagKey<Biome> biomeTag, GenerationStep.Decoration featureStep, TerrainAdjustment terrainAdaptation) {
        return createConfig(biomeTag, Map.of(), featureStep, terrainAdaptation);
    }

    private static Structure.StructureSettings createConfig(TagKey<Biome> biomeTag, TerrainAdjustment terrainAdaptation) {
        return createConfig(biomeTag, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, terrainAdaptation);
    }

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		var holderGetter = entries.getLookup(Registry.TEMPLATE_POOL_REGISTRY);
		entries.add(
				ABANDONED_CABIN_KEY,
				new JigsawStructure(
						createConfig(
								WilderBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE,
								//Arrays.stream(MobCategory.values())
								//        .collect(Collectors.toMap(spawnGroup -> spawnGroup, spawnGroup -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()))),
								GenerationStep.Decoration.UNDERGROUND_DECORATION,
								TerrainAdjustment.BURY
						),
						holderGetter.getOrThrow(AbandonedCabinGenerator.CABIN),
						5,
						UniformHeight.of(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(0)),
						false
				)
		);
	}

	public static void bootstrapSet(FabricWorldgenProvider.Entries entries) {
		entries.add(
				ABANDONED_CABINS_KEY,
				new StructureSet(
						entries.getLookup(Registry.STRUCTURE_REGISTRY).getOrThrow(ABANDONED_CABIN_KEY),
						new RandomSpreadStructurePlacement(13, 5, RandomSpreadType.LINEAR, 25388232) // ancient city salt is 20083232
				)
		);
	}
}
