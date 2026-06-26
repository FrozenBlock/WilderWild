/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.data.worldgen.structure;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public class WWStructures {
	private static final ResourceKey<Structure> ABANDONED_CAMP_MAPLE_FOREST = createKey("abandoned_camp_maple_forest");

	// TODO: structure set modification api so it can generate
	public static void bootstrap(BootstrapContext<Structure> context) {
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<StructureTemplatePool> templates = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			ABANDONED_CAMP_MAPLE_FOREST,
			new JigsawStructure(
				new Structure.StructureSettings.Builder(biomes.getOrThrow(WWBiomeTags.HAS_ABANDONED_CAMP_MAPLE_FOREST))
					.terrainAdapation(TerrainAdjustment.BEARD_THIN)
					.build(),
				templates.getOrThrow(WWAbandonedCampStructurePools.MAPLE_FOREST.tentStructureDirectory()),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				true,
				Heightmap.Types.WORLD_SURFACE_WG
			)
		);
	}

	private static ResourceKey<Structure> createKey(String name) {
		return ResourceKey.create(Registries.STRUCTURE, WWConstants.id(name));
	}
}
