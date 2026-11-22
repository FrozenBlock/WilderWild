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

package net.frozenblock.wilderwild.levelgen.modification;

import java.util.Map;
import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.levelgen.treedecorators.WWTreeDecorators;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWWorldgen {

	public static void init() {
		WWTreeDecorators.init();
	}

	public static void setup() {
		final Map<ResourceKey<Biome>, ResourceKey<VillagerType>> villagerTypeMap = VillagerType.BY_BIOME;
		villagerTypeMap.put(WWBiomes.CYPRESS_WETLANDS, VillagerType.SWAMP);
		villagerTypeMap.put(WWBiomes.OASIS, VillagerType.DESERT);
		villagerTypeMap.put(WWBiomes.ARID_FOREST, VillagerType.DESERT);
		villagerTypeMap.put(WWBiomes.ARID_SAVANNA, VillagerType.SAVANNA);
		villagerTypeMap.put(WWBiomes.PARCHED_FOREST, VillagerType.SAVANNA);
		villagerTypeMap.put(WWBiomes.BIRCH_JUNGLE, VillagerType.JUNGLE);
		villagerTypeMap.put(WWBiomes.SPARSE_BIRCH_JUNGLE, VillagerType.JUNGLE);
		villagerTypeMap.put(WWBiomes.BIRCH_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.TEMPERATE_RAINFOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.DARK_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.MIXED_FOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.DYING_MIXED_FOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.SNOWY_DYING_MIXED_FOREST, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.SNOWY_DYING_FOREST, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.OLD_GROWTH_BIRCH_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.FLOWER_FIELD, VillagerType.PLAINS);
	}
}
