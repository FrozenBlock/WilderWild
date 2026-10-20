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
import net.frozenblock.lib.levelgen.biome.api.BiomeSelectors;
import net.frozenblock.lib.levelgen.biome.api.modifications.BiomeModificationContext;
import net.frozenblock.lib.levelgen.biome.api.modifications.BiomeModifications;
import net.frozenblock.lib.levelgen.biome.api.modifications.ModificationPhase;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.feature.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.data.worldgen.feature.placed.WWPlacedFeatures;
import net.frozenblock.wilderwild.levelgen.treedecorators.WWTreeDecorators;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WWWorldgen {

	public static void init() {
		WWTreeDecorators.init();
	}

	public static void setup() {
		WWFlowerGeneration.setup();
		WWTreeGeneration.setup();

		replaceFeatures();
		WWVegetationGeneration.generateFlower();
		WWVegetationGeneration.generateShrub();
		WWVegetationGeneration.generateCacti();
		WWAquaticGeneration.generateAquaticFeatures();
		WWVegetationGeneration.generateGrass();
		WWMiscGeneration.generateMisc();

		WWVegetationGeneration.generateMushroom();
		WWVegetationGeneration.generatePumpkin();

		WWBiomeSettings.setup();

		generatePollen();

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

	private static void replaceFeatures() {
		BiomeModifications.create(WWConstants.id("add_new_snow")).add(
			ModificationPhase.POST_PROCESSING,
			BiomeSelectors.all(),
			context -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				if (!generationSettings.removeFeature(MiscOverworldPlacements.FREEZE_TOP_LAYER)) return;

				generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
				if (WWWorldgenConfig.SNOW_BELOW_TREES.get()) {
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SNOW_BLANKET.getKey());
				}
				if (WWWorldgenConfig.SNOW_TRANSITION_GENERATION.get()) {
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SNOW_AND_ICE_TRANSITION.getKey());
				}
			});
	}

	private static void generatePollen() {
		BiomeModifications.create(WWConstants.id("pollen_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.POLLEN_GENERATION.get() || !biomeSelectionContext.hasTag(WWBiomeTags.HAS_POLLEN)) return;
				context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.POLLEN.getKey());
			});
	}

	private WWWorldgen() {}
}
