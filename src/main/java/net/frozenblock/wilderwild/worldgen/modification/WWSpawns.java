/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
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

		BiomeModifications.create(WWConstants.id("butterfly_spawns")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.tag(WWBiomeTags.HAS_BUTTERFLY),
			(selectionContext, modificationContext) -> {
				BiomeModificationContext.SpawnSettingsContext spawnSettings = modificationContext.getSpawnSettings();

				double butterflyCharge = 0.3D;
				double butterflyLimit = 0.15D;
				int butterflyWeight = 2;

				if (!selectionContext.hasTag(WWBiomeTags.BUTTERFLY_COMMON_SPAWN)) {
					butterflyCharge = 1.35D;
					butterflyLimit = 0.075D;
					butterflyWeight = 1;
				}

				spawnSettings.addSpawn(
					FrozenMobCategories.getCategory(WWConstants.MOD_ID, "butterfly"),
					new MobSpawnSettings.SpawnerData(WWEntityTypes.BUTTERFLY, butterflyWeight, 1, 1)
				);

				spawnSettings.setSpawnCost(WWEntityTypes.BUTTERFLY, butterflyCharge, butterflyLimit);
			}
		);
	}

	public static void addJellyfish() {
		BiomeModifications.create(WWConstants.id("jellyfish_spawns")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.tag(WWBiomeTags.HAS_JELLYFISH),
			(selectionContext, modificationContext) -> {
				BiomeModificationContext.SpawnSettingsContext spawnSettings = modificationContext.getSpawnSettings();

				spawnSettings.addSpawn(
					FrozenMobCategories.getCategory(WWConstants.MOD_ID, "jellyfish"),
					new MobSpawnSettings.SpawnerData(WWEntityTypes.JELLYFISH, 2, 1, 1)
				);

				double jellyfishCharge = 0.3D;
				if (!selectionContext.hasTag(WWBiomeTags.JELLYFISH_COMMON_SPAWN)) jellyfishCharge = 1.3D;
				spawnSettings.setSpawnCost(WWEntityTypes.JELLYFISH, jellyfishCharge, 0.15D);
			}
		);
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
