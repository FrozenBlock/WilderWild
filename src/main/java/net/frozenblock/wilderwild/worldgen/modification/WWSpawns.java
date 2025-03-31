/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.lib.entity.api.category.FrozenMobCategories;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;

public final class WWSpawns {

	public static void addBugs() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WWBiomeTags.HAS_FIREFLY),
			FrozenMobCategories.getCategory(WWConstants.MOD_ID, "firefly"), WWEntityTypes.FIREFLY, 4, 12, 24);

		BiomeModifications.addSpawn(BiomeSelectors.tag(WWBiomeTags.HAS_BUTTERFLY),
			FrozenMobCategories.getCategory(WWConstants.MOD_ID, "butterfly"), WWEntityTypes.BUTTERFLY, 1, 1, 1);

		BiomeModifications.addSpawn(BiomeSelectors.tag(WWBiomeTags.HAS_COMMON_BUTTERFLY),
			FrozenMobCategories.getCategory(WWConstants.MOD_ID, "butterfly"), WWEntityTypes.BUTTERFLY, 2, 1, 1);
	}

	public static void addJellyfish() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WWBiomeTags.HAS_JELLYFISH),
			FrozenMobCategories.getCategory(WWConstants.MOD_ID, "jellyfish"), WWEntityTypes.JELLYFISH, 2, 1, 1);
	}

	public static void addCrabs() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WWBiomeTags.HAS_CRAB),
			FrozenMobCategories.getCategory(WWConstants.MOD_ID, "crab"), WWEntityTypes.CRAB, 1, 1, 3);
	}

	public static void addOstriches() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WWBiomeTags.HAS_OSTRICH),
			MobCategory.CREATURE, WWEntityTypes.OSTRICH, 4, 2, 4);
	}

	public static void addPenguins() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WWBiomeTags.HAS_PENGUIN),
			MobCategory.CREATURE, WWEntityTypes.PENGUIN, 3, 3, 5);
	}

	public static void addTumbleweed() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WWBiomeTags.HAS_TUMBLEWEED_ENTITY),
			FrozenMobCategories.getCategory(WWConstants.MOD_ID, "tumbleweed"), WWEntityTypes.TUMBLEWEED, 60, 1, 1);
	}

	public static void addRabbits() {
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST),
			MobCategory.CREATURE, EntityType.RABBIT, 10, 2, 4);
	}

	public static void addMooblooms() {
		BiomeModifications.create(WWConstants.id("moobloom_spawns")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_MOOBLOOM),
			(biomeSelectionContext, biomeModificationContext) -> {
				biomeModificationContext.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
				biomeModificationContext.getSpawnSettings().addSpawn(
					MobCategory.CREATURE,
					new MobSpawnSettings.SpawnerData(
						WWEntityTypes.MOOBLOOM,
						34,
						2,
						4
					)
				);
			}
		);
	}
}
