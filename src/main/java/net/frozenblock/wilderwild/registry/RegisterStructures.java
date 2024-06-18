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

import java.util.Map;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.world.structure.AbandonedCabinGenerator;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

public final class RegisterStructures {

	private RegisterStructures() {
		throw new UnsupportedOperationException("RegisterStructures contains only static declarations.");
	}

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> context) {
		AbandonedCabinGenerator.bootstrapTemplatePool(context);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		AbandonedCabinGenerator.bootstrap(context);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		AbandonedCabinGenerator.bootstrapStructureSet(context);
	}

	@NotNull
	public static ResourceKey<StructureSet> ofSet(@NotNull String id) {
		return ResourceKey.create(Registries.STRUCTURE_SET, WilderSharedConstants.id(id));
	}

	@NotNull
	public static ResourceKey<Structure> createKey(@NotNull String id) {
		return ResourceKey.create(Registries.STRUCTURE, WilderSharedConstants.id(id));
	}

	@NotNull
	public static Structure.StructureSettings structure(
		@NotNull HolderSet<Biome> holderSet,
		@NotNull Map<MobCategory, StructureSpawnOverride> spawns,
		@NotNull GenerationStep.Decoration featureStep,
		@NotNull TerrainAdjustment terrainAdaptation
	) {
		return new Structure.StructureSettings(holderSet, spawns, featureStep, terrainAdaptation);
	}

	@NotNull
	public static Structure.StructureSettings structure(
		@NotNull HolderSet<Biome> holderSet,
		@NotNull GenerationStep.Decoration featureStep,
		@NotNull TerrainAdjustment terrainAdaptation
	) {
		return structure(holderSet, Map.of(), featureStep, terrainAdaptation);
	}

	public static void register(@NotNull BootstrapContext<StructureTemplatePool> pool, String location, StructureTemplatePool templatePool) {
		pool.register(Pools.createKey(location), templatePool);
	}
}
