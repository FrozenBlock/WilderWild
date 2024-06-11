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

package net.frozenblock.wilderwild.world.structure;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterStructureProcessors;
import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

/**
 * Contains the StructureTemplatePool for Abandoned Cabins
 */
public class AbandonedCabinGenerator {
	public static final ResourceKey<StructureSet> ABANDONED_CABINS_KEY =  RegisterStructures.ofSet("abandoned_cabin");
	private static final ResourceKey<Structure> ABANDONED_CABIN_KEY = RegisterStructures.createKey("abandoned_cabin");
	public static final ResourceKey<StructureTemplatePool> ABANDONED_CABIN = Pools.parseKey(WilderSharedConstants.string("abandoned_cabin"));

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> pool) {
		HolderGetter<StructureTemplatePool> holderGetter = pool.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> empty = holderGetter.getOrThrow(Pools.EMPTY);
		HolderGetter<StructureProcessorList> structureProcessorGetter = pool.lookup(Registries.PROCESSOR_LIST);
		Holder<StructureProcessorList> abandonedCabinDegradation = structureProcessorGetter.getOrThrow(RegisterStructureProcessors.ABANDONED_CABIN_DEGRADATION);

		pool.register(
			ABANDONED_CABIN,
			new StructureTemplatePool(
				empty,
				List.of(
					Pair.of(StructurePoolElement.single(string("cabin1"), abandonedCabinDegradation), 1),
					Pair.of(StructurePoolElement.single(string("cabin2"), abandonedCabinDegradation), 1),
					Pair.of(StructurePoolElement.single(string("cabin3"), abandonedCabinDegradation), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			ABANDONED_CABIN_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(WilderBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE),
					GenerationStep.Decoration.UNDERGROUND_DECORATION,
					TerrainAdjustment.BURY
				),
				templatePool.getOrThrow(ABANDONED_CABIN),
				5,
				UniformHeight.of(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(0)),
				false
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);
		context.register(
			ABANDONED_CABINS_KEY,
			new StructureSet(
				structure.getOrThrow(ABANDONED_CABIN_KEY),
				new RandomSpreadStructurePlacement(14, 8, RandomSpreadType.LINEAR, 253988502) // ancient city salt is 20083232
			)
		);
	}

	private static @NotNull String string(String name) {
		return WilderSharedConstants.string("abandoned_cabin/" + name);
	}
}
