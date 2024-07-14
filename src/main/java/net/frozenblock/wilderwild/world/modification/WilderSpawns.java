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

package net.frozenblock.wilderwild.world.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.lib.mobcategory.api.FrozenMobCategories;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;

public final class WilderSpawns {

	public static void addFireflies() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY),
			FrozenMobCategories.getCategory(WilderConstants.MOD_ID, "fireflies"), RegisterEntities.FIREFLY, 2, 1, 2);

		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE),
			FrozenMobCategories.getCategory(WilderConstants.MOD_ID, "fireflies"), RegisterEntities.FIREFLY, 2, 1, 2);

		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE),
			FrozenMobCategories.getCategory(WilderConstants.MOD_ID, "fireflies"), RegisterEntities.FIREFLY, 2, 1, 2);
	}

	public static void addJellyfish() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.HAS_JELLYFISH),
			FrozenMobCategories.getCategory(WilderConstants.MOD_ID, "jellyfish"), RegisterEntities.JELLYFISH, 2, 1, 1);
	}

	public static void addCrabs() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.HAS_CRAB),
			FrozenMobCategories.getCategory(WilderConstants.MOD_ID, "crab"), RegisterEntities.CRAB, 1, 1, 3);
	}

	public static void addOstriches() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.HAS_OSTRICH),
			MobCategory.CREATURE, RegisterEntities.OSTRICH, 4, 2, 4);
	}

	public static void addTumbleweed() {
		BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.HAS_TUMBLEWEED_ENTITY),
			FrozenMobCategories.getCategory(WilderConstants.MOD_ID, "tumbleweed"), RegisterEntities.TUMBLEWEED, 60, 1, 1);
	}

	public static void addRabbits() {
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST),
			MobCategory.CREATURE, EntityType.RABBIT, 10, 2, 4);
	}
}
