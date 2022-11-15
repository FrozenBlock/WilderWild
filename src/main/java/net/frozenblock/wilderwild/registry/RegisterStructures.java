package net.frozenblock.wilderwild.registry;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.structure.AbandonedCabinGenerator;
import net.frozenblock.wilderwild.world.structure.WilderStructureProcessors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public final class RegisterStructures {
	private RegisterStructures() {
		throw new UnsupportedOperationException("RegisterStructures contains only static declarations.");
	}

    public static final int MAX_JIGSAW_SIZE = 24;

    public static final int MAX_DISTANCE_FROM_JIGSAW_CENTER = 128;

    public static final ResourceKey<StructureSet> ABANDONED_CABINS_KEY = ofSet("abandoned_cabins");

    private static ResourceKey<StructureSet> ofSet(String id) {
        return ResourceKey.create(Registries.STRUCTURE_SET, WilderSharedConstants.id(id));
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
        return ResourceKey.create(Registries.STRUCTURE, WilderSharedConstants.id(id));
    }

    private static Structure.StructureSettings structure(
			HolderSet<Biome> holderSet, Map<MobCategory, StructureSpawnOverride> spawns, GenerationStep.Decoration featureStep, TerrainAdjustment terrainAdaptation
    ) {
        return new Structure.StructureSettings(holderSet, spawns, featureStep, terrainAdaptation);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> holderSet, GenerationStep.Decoration featureStep, TerrainAdjustment terrainAdaptation) {
        return structure(holderSet, Map.of(), featureStep, terrainAdaptation);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> holderSet, TerrainAdjustment terrainAdaptation) {
        return structure(holderSet, GenerationStep.Decoration.SURFACE_STRUCTURES, terrainAdaptation);
    }

	public static void bootstrap(FabricDynamicRegistryProvider.Entries entries) {
		var abandonedCabinProcessor = register(
				entries,
				WilderStructureProcessors.ABANDONED_CABIN,
				ImmutableList.of(
						new RuleProcessor(
								ImmutableList.of(
										new ProcessorRule(
												new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()
										),
										new ProcessorRule(
												new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()
										),
										new ProcessorRule(new RandomBlockMatchTest(Blocks.SOUL_LANTERN, 0.05F), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState())
								)
						),
						new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
				)
		);

		/*HolderGetter<Biome> holderGetter = entries.getLookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> holderGetter2 = entries.getLookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> holder2 = holderGetter2.getOrThrow(Pools.EMPTY);

		var cabinTemplatePool = entries.add(
				AbandonedCabinGenerator.CABIN,
				new StructureTemplatePool(
						holder2,
						ImmutableList.of(
								Pair.of(AbandonedCabinGenerator.ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_1", abandonedCabinProcessor), 1),
								Pair.of(AbandonedCabinGenerator.ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_2", abandonedCabinProcessor), 1),
								Pair.of(AbandonedCabinGenerator.ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_3", abandonedCabinProcessor), 1)
						),
						StructureTemplatePool.Projection.RIGID
				)
		);

		var abandonedCabin = entries.add(
				ABANDONED_CABIN_KEY,
				new JigsawStructure(
						structure(
								holderGetter.getOrThrow(WilderBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE),
								//Arrays.stream(MobCategory.values())
								//        .collect(Collectors.toMap(spawnGroup -> spawnGroup, spawnGroup -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()))),
								GenerationStep.Decoration.UNDERGROUND_DECORATION,
								TerrainAdjustment.BURY
						),
						cabinTemplatePool,
						5,
						UniformHeight.of(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(0)),
						false
				)
		);
		entries.add(
				ABANDONED_CABINS_KEY,
				new StructureSet(
						abandonedCabin,
						new RandomSpreadStructurePlacement(13, 5, RandomSpreadType.LINEAR, 25388232) // ancient city salt is 20083232
				)
		);*/
	}

	private static Holder<StructureProcessorList> register(
			FabricDynamicRegistryProvider.Entries entries, ResourceKey<StructureProcessorList> registryKey, List<StructureProcessor> list
	) {
		return entries.add(registryKey, new StructureProcessorList(list));
	}
}
