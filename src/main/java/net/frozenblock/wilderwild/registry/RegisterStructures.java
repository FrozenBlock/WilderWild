/*
 * Copyright 2023-2024 FrozenBlock
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

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.features.structure.AbandonedCabinGenerator;
import net.frozenblock.wilderwild.world.features.structure.WilderStructureProcessors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
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
import org.jetbrains.annotations.NotNull;

public final class RegisterStructures {
	public static final ResourceKey<StructureSet> ABANDONED_CABINS_KEY = ofSet("abandoned_cabins");
	private static final ResourceKey<Structure> ABANDONED_CABIN_KEY = createKey("abandoned_cabin");

	private RegisterStructures() {
		throw new UnsupportedOperationException("RegisterStructures contains only static declarations.");
	}

	@NotNull
	private static ResourceKey<StructureSet> ofSet(@NotNull String id) {
		return ResourceKey.create(Registries.STRUCTURE_SET, WilderSharedConstants.id(id));
	}

	public static void init() {
		WilderSharedConstants.logWithModId("Registering Structures for", WilderSharedConstants.UNSTABLE_LOGGING);
		WilderStructureProcessors.init();
		AbandonedCabinGenerator.init();
	}

	@NotNull
	private static ResourceKey<Structure> createKey(@NotNull String id) {
		return ResourceKey.create(Registries.STRUCTURE, WilderSharedConstants.id(id));
	}

	@NotNull
	private static Structure.StructureSettings structure(@NotNull HolderSet<Biome> holderSet, @NotNull Map<MobCategory, StructureSpawnOverride> spawns, @NotNull GenerationStep.Decoration featureStep, @NotNull TerrainAdjustment terrainAdaptation) {
		return new Structure.StructureSettings(holderSet, spawns, featureStep, terrainAdaptation);
	}

	@NotNull
	private static Structure.StructureSettings structure(@NotNull HolderSet<Biome> holderSet, @NotNull GenerationStep.Decoration featureStep, @NotNull TerrainAdjustment terrainAdaptation) {
		return structure(holderSet, Map.of(), featureStep, terrainAdaptation);
	}

	public static void bootstrapProcessor(@NotNull BootstapContext<StructureProcessorList> context) {
		register(
			context,
			WilderStructureProcessors.ABANDONED_CABIN,
			List.of(
				new RuleProcessor(
					List.of(
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
	}

	public static void bootstrapTemplatePool(@NotNull BootstapContext<StructureTemplatePool> context) {
		HolderGetter<StructureProcessorList> processor = context.lookup(Registries.PROCESSOR_LIST);
		HolderGetter<StructureTemplatePool> holderGetter2 = context.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> holder2 = holderGetter2.getOrThrow(Pools.EMPTY);

		context.register(
			AbandonedCabinGenerator.CABIN,
			new StructureTemplatePool(
				holder2,
				List.of(
					Pair.of(AbandonedCabinGenerator.ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_1", processor.getOrThrow(WilderStructureProcessors.ABANDONED_CABIN)), 1),
					Pair.of(AbandonedCabinGenerator.ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_2", processor.getOrThrow(WilderStructureProcessors.ABANDONED_CABIN)), 1),
					Pair.of(AbandonedCabinGenerator.ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_3", processor.getOrThrow(WilderStructureProcessors.ABANDONED_CABIN)), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);
		context.register(
			ABANDONED_CABIN_KEY,
			new JigsawStructure(
				structure(
					holderGetter.getOrThrow(WilderBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE),
					//Arrays.stream(MobCategory.values())
					//        .collect(Collectors.toMap(spawnGroup -> spawnGroup, spawnGroup -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()))),
					GenerationStep.Decoration.UNDERGROUND_DECORATION,
					TerrainAdjustment.BURY
				),
				templatePool.getOrThrow(AbandonedCabinGenerator.CABIN),
				5,
				UniformHeight.of(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(0)),
				false
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);
		context.register(
			ABANDONED_CABINS_KEY,
			new StructureSet(
				structure.getOrThrow(ABANDONED_CABIN_KEY),
				new RandomSpreadStructurePlacement(13, 5, RandomSpreadType.LINEAR, 25388232) // ancient city salt is 20083232
			)
		);
	}

	@NotNull
	private static Holder<StructureProcessorList> register(@NotNull BootstapContext<StructureProcessorList> entries, @NotNull ResourceKey<StructureProcessorList> key, @NotNull List<StructureProcessor> list) {
		WilderSharedConstants.log("Registering structure processor list " + key.location(), true);
		return entries.register(key, new StructureProcessorList(list));
	}
}
