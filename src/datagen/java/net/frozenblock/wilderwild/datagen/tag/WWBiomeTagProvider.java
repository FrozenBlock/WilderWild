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

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.frozenblock.lib.datagen.api.FrozenBiomeTagProvider;
import net.frozenblock.lib.tag.api.FrozenBiomeTags;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public final class WWBiomeTagProvider extends FrozenBiomeTagProvider {

	public WWBiomeTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.generateBiomeTags();
		this.generateClimateAndVegetationTags();
		this.generateUtilityTags();
		this.generateFeatureTags();
		this.generateStructureTags();
	}

	private void generateBiomeTags() {
		this.getOrCreateTagBuilder(WilderBiomeTags.WILDER_WILD_BIOMES)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.WARM_BEACH)
			.addOptional(RegisterWorldgen.FROZEN_CAVES)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
			.addOptional(RegisterWorldgen.MAGMATIC_CAVES)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.IS_OVERWORLD)
			.addOptionalTag(WilderBiomeTags.WILDER_WILD_BIOMES);

		this.getOrCreateTagBuilder(BiomeTags.IS_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.CAVES)
			.addOptional(RegisterWorldgen.FROZEN_CAVES)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
			.addOptional(RegisterWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(WilderBiomeTags.DARK_FOREST)
			.add(Biomes.DARK_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.GROVE)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WilderBiomeTags.MEADOW)
			.add(Biomes.MEADOW);

		this.getOrCreateTagBuilder(WilderBiomeTags.OAK_SAPLINGS_GROW_SWAMP_VARIANT)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptionalTag(BiomeTags.IS_OCEAN);

		this.getOrCreateTagBuilder(WilderBiomeTags.NON_FROZEN_PLAINS)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(RegisterWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WilderBiomeTags.NORMAL_SAVANNA)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WilderBiomeTags.SHORT_TAIGA)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.SNOWY_PLAINS)
			.add(Biomes.SNOWY_PLAINS);

		this.getOrCreateTagBuilder(WilderBiomeTags.TALL_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.TALL_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_HILLS)
			.add(Biomes.WINDSWEPT_HILLS)
			.add(Biomes.WINDSWEPT_GRAVELLY_HILLS);

		this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_SAVANNA)
			.add(Biomes.WINDSWEPT_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.IS_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.IS_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.IS_RIVER)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.IS_BEACH)
			.addOptional(RegisterWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.BEACH)
			.addOptional(RegisterWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WilderBiomeTags.RAINFOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST);
	}

	private void generateClimateAndVegetationTags() {
		this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_HOT)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.WARM_BEACH)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_COLD)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.FROZEN_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_WET)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_DRY)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.add(RegisterWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_CONIFEROUS)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_DECIDUOUS)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.VEGETATION_DENSE)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.RAINFOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.FLORAL)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.SNOWY)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.AQUATIC)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_SAVANNA)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.SAVANNA)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.DESERT)
			.addOptional(RegisterWorldgen.OASIS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.FOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.RIVER)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.SWAMP)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.UNDERGROUND)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
			.addOptional(RegisterWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(WilderBiomeTags.LUKEWARM_WATER)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WilderBiomeTags.HOT_WATER)
			.add(Biomes.DESERT)
			.add(Biomes.BADLANDS)
			.add(Biomes.ERODED_BADLANDS)
			.add(Biomes.WOODED_BADLANDS)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WilderBiomeTags.SNOWY_WATER)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.SNOWY_BEACH)
			.add(Biomes.SNOWY_PLAINS)
			.add(Biomes.SNOWY_SLOPES)
			.add(Biomes.GROVE)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.FROZEN_WATER)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.FROZEN_PEAKS)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.FROZEN_PEAKS)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);
	}

	private void generateUtilityTags() {
		this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE);

		this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(WilderBiomeTags.NO_POOLS)
			.addOptional(Biomes.DEEP_DARK);

		this.getOrCreateTagBuilder(WilderBiomeTags.PEARLESCENT_JELLYFISH)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(WilderBiomeTags.JELLYFISH_SPECIAL_SPAWN)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_CRAB)
			.add(Biomes.BEACH)
			.addOptional(RegisterWorldgen.WARM_BEACH)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptionalTag(WilderBiomeTags.HAS_COMMON_CRAB);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COMMON_CRAB)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_OSTRICH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TUMBLEWEED_ENTITY)
			.add(Biomes.DESERT)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_CLAY_PATH)
			.addOptionalTag(WilderBiomeTags.SAND_BEACHES)
			.addOptionalTag(WilderBiomeTags.MULTI_LAYER_SAND_BEACHES)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(FrozenBiomeTags.CAN_LIGHTNING_OVERRIDE)
			.add(Biomes.DESERT)
			.add(Biomes.BADLANDS)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
			.addOptional(RegisterWorldgen.FROZEN_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.IS_FOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.WARM_BEACH)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_GOLD_RABBITS)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.STRONGHOLD_BIASED_TO)
			.addOptionalTag(WilderBiomeTags.WILDER_WILD_BIOMES);

		this.getOrCreateTagBuilder(BiomeTags.WATER_ON_MAP_OUTLINES)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.GRAVEL_BEACH)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.RIVER)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.SAND_BEACHES)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.RIVER)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.MULTI_LAYER_SAND_BEACHES)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.SAVANNA)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.CHERRY_GROVE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(new ResourceLocation("terralith", "arid_highlands"));

		this.getOrCreateTagBuilder(WilderBiomeTags.BELOW_SURFACE_SNOW)
			.add(Biomes.FROZEN_PEAKS)
			.add(Biomes.JAGGED_PEAKS)
			.add(Biomes.SNOWY_SLOPES)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WilderBiomeTags.STRAYS_CAN_SPAWN_UNDERGROUND)
			.addOptional(RegisterWorldgen.FROZEN_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.SNOW_GOLEM_MELTS)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_SNOW_FOXES)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_WHITE_RABBITS)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);
	}

	private void generateFeatureTags() {
		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_BIRCH_TREES)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_CHERRY_TREES)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_OAK_AND_CYPRESS_TREES)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MOSSY_FALLEN_MIXED_TREES)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MOSSY_FALLEN_OAK_AND_BIRCH)
			.addOptional(RegisterWorldgen.RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_ACACIA_AND_OAK)
			.addOptionalTag(BiomeTags.IS_SAVANNA)
			.addOptionalTag(ConventionalBiomeTags.SAVANNA)
			.addOptionalTag(ConventionalBiomeTags.TREE_SAVANNA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_PALM)
			.addOptional(RegisterWorldgen.OASIS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_PALM_RARE)
			.add(Biomes.DESERT)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_BIRCH_AND_OAK_DARK_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_SPRUCE_TREES)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_CLEAN_FALLEN_SPRUCE_TREES)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_SWAMP_OAK_TREES)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_MANGROVE_TREES)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_CRIMSON)
			.add(Biomes.CRIMSON_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FALLEN_WARPED)
			.add(Biomes.WARPED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.CHERRY_TREES)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MOSS_LAKE)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MOSS_LAKE_RARE)
			.addOptionalTag(BiomeTags.IS_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.FOREST_GRASS)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.PLAINS_GRASS)
			.add(Biomes.PLAINS)
			.add(Biomes.MEADOW);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_HUGE_RED_MUSHROOM)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_HUGE_BROWN_MUSHROOM)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COMMON_BROWN_MUSHROOM)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COMMON_RED_MUSHROOM)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BIG_MUSHROOMS)
			.add(Biomes.BIRCH_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BIG_MUSHROOM_PATCH)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SWAMP_MUSHROOM)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SMALL_SPONGE)
			.add(Biomes.WARM_OCEAN);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SMALL_SPONGE_RARE)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUSH_CAVES);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_CARNATION)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.MEADOW)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_DATURA)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_CATTAIL)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_CATTAIL_UNCOMMON)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.BEACH);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_CATTAIL_COMMON)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SEEDING_DANDELION)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.MEADOW)
			.add(Biomes.WINDSWEPT_HILLS)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COMMON_SEEDING_DANDELION)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RARE_SEEDING_DANDELION)
			.add(Biomes.PLAINS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MILKWEED)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.SWAMP)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.DYING_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.CHERRY_FLOWERS)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TUMBLEWEED_PLANT)
			.add(Biomes.DESERT)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.SWAMP_TREES)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_PALMS)
			.add(Biomes.DESERT)
			.add(Biomes.BEACH)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_WARM_BEACH_PALMS)
			.addOptional(RegisterWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SHORT_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BIG_COARSE_SHRUB)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_OAK)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.SWAMP)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_BIRCH)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS)
			.add(Biomes.GROVE)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_SPRUCE_SNOWY)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE_SNOWY)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_SPRUCE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_CYPRESS)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_LARGE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_ACACIA)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_ACACIA_AND_OAK)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_CHERRY)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_DARK_OAK)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_CRIMSON)
			.add(Biomes.CRIMSON_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SNAPPED_WARPED)
			.add(Biomes.WARPED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_POLLEN)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COMMON_PUMPKIN)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FIELD_FLOWERS)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(RegisterWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SUNFLOWER_PLAINS_FLOWERS)
			.add(Biomes.SUNFLOWER_PLAINS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SHORT_MEGA_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SHORT_MEGA_SPRUCE_SNOWY)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RED_SHELF_FUNGUS)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.PLAINS)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BROWN_SHELF_FUNGUS)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.PLAINS)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RAINFOREST_MUSHROOM)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MIXED_MUSHROOM)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_GLORY_OF_THE_SNOW)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FLOWERING_WATER_LILY)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BERRY_PATCH)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BUSH)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FOREST_SHRUB)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SHRUB)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.SAVANNA)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_PLAINS_FLOWERS)
			.add(Biomes.PLAINS)
			.add(Biomes.FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_CYPRESS_FLOWERS)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RARE_MILKWEED)
			.add(Biomes.PLAINS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE)
			.add(Biomes.TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_NEW_RARE_GRASS)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FLOWER_FIELD_TALL_GRASS)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(RegisterWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_DENSE_FERN)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_DENSE_TALL_GRASS)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SPARSE_JUNGLE_FLOWERS)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_JUNGLE_FLOWERS)
			.add(Biomes.JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_JUNGLE_BUSH)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SPARSE_BUSH)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_ARID_BUSH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.PARCHED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FLOWER_FIELD_BUSH)
			.addOptional(RegisterWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RAINFOREST_BUSH)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BADLANDS_SAND_BUSH)
			.add(Biomes.BADLANDS)
			.add(Biomes.WOODED_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BADLANDS_TERRACOTTA_BUSH)
			.add(Biomes.BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_WOODED_BADLANDS_TERRACOTTA_BUSH)
			.add(Biomes.WOODED_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BADLANDS_RARE_SAND_BUSH)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_DESERT_BUSH)
			.add(Biomes.DESERT);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_OASIS_BUSH)
			.addOptional(RegisterWorldgen.OASIS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TALL_CACTUS)
			.add(Biomes.DESERT);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_PRICKLY_PEAR)
			.add(Biomes.BADLANDS)
			.add(Biomes.WOODED_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RARE_PRICKLY_PEAR)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TALL_BADLANDS_CACTUS)
			.add(Biomes.BADLANDS)
			.add(Biomes.WOODED_BADLANDS)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MOSS_PILE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.SWAMP)
			.addOptional(Biomes.MANGROVE_SWAMP)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_DECORATIVE_MUD)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_PACKED_MUD_ORE)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.DESERT)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COARSE_DIRT_PATH)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COARSE_DIRT_PATH_SMALL)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_PACKED_MUD_PATH_BADLANDS)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SANDSTONE_PATH)
			.add(Biomes.DESERT)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_COARSE_DIRT_CLEARING)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_ROOTED_DIRT_CLEARING)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_GRAVEL_CLEARING)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BIRCH_CLEARING_FLOWERS)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_FOREST_CLEARING_FLOWERS)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SCORCHED_SAND)
			.add(Biomes.DESERT)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SCORCHED_RED_SAND)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SMALL_SAND_TRANSITION)
			.add(Biomes.SNOWY_BEACH)
			.add(Biomes.BEACH)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SAND_TRANSITION)
			.add(Biomes.DESERT)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RED_SAND_TRANSITION)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_STONE_TRANSITION)
			.add(Biomes.STONY_PEAKS)
			.add(Biomes.STONY_SHORE)
			.add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_STONE_TRANSITION)
			.add(Biomes.STONY_PEAKS)
			.add(Biomes.STONY_SHORE)
			.add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BETA_BEACH_SAND_TRANSITION)
			.addOptionalTag(WilderBiomeTags.SAND_BEACHES)
			.addOptionalTag(WilderBiomeTags.MULTI_LAYER_SAND_BEACHES);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_BETA_BEACH_GRAVEL_TRANSITION)
			.addOptionalTag(WilderBiomeTags.GRAVEL_BEACH);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_GRAVEL_TRANSITION)
			.add(Biomes.WINDSWEPT_GRAVELLY_HILLS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MUD_TRANSITION)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TERMITE_MOUND)
			.addOptionalTag(BiomeTags.IS_SAVANNA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_NETHER_GEYSER)
			.add(Biomes.BASALT_DELTAS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_NETHER_LAVA_GEYSER)
			.add(Biomes.NETHER_WASTES);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TAIGA_FOREST_ROCK)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MOSS_PATH)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.OASIS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_PACKED_MUD_PATH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.PARCHED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MUD_BASIN)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MUD_PILE)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MUD_LAKE)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_ALGAE_SMALL)
			.addOptionalTag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_ALGAE)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_WATER_POOLS)
			.add(Biomes.RIVER)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(ConventionalBiomeTags.OCEAN);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_WATER_SHRUBS)
			.add(Biomes.RIVER)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(ConventionalBiomeTags.OCEAN);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_WATER_GRASS)
			.add(Biomes.RIVER)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(ConventionalBiomeTags.OCEAN);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MOSS_BASIN)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_PODZOL_BASIN)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_MOSS_CARPET)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SWAMP)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RARE_COARSE)
			.add(Biomes.FOREST)
			.add(Biomes.SAVANNA)
			.add(Biomes.DARK_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RARE_GRAVEL)
			.add(Biomes.FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WilderBiomeTags.HAS_RARE_STONE)
			.add(Biomes.FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);
	}

	private void generateStructureTags() {
		this.getOrCreateTagBuilder(WilderBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(BiomeTags.IS_RIVER)
			.addOptionalTag(BiomeTags.IS_MOUNTAIN)
			.addOptionalTag(BiomeTags.IS_HILL)
			.addOptionalTag(BiomeTags.IS_TAIGA)
			.addOptionalTag(BiomeTags.IS_JUNGLE)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptional(Biomes.CHERRY_GROVE)
			.add(Biomes.STONY_SHORE)
			.add(Biomes.MUSHROOM_FIELDS)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.DESERT)
			.add(Biomes.SAVANNA)
			.add(Biomes.SNOWY_PLAINS)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.DRIPSTONE_CAVES)
			.add(Biomes.DEEP_DARK)
			.addOptionalTag(WilderBiomeTags.WILDER_WILD_BIOMES);

		this.getOrCreateTagBuilder(BiomeTags.HAS_DESERT_PYRAMID)
			.addOptional(RegisterWorldgen.OASIS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_IGLOO)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_MINESHAFT)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.WARM_RIVER)
			.addOptional(RegisterWorldgen.WARM_BEACH)
			.addOptional(RegisterWorldgen.FROZEN_CAVES)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST)
			.addOptional(RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_PILLAGER_OUTPOST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.ICE_SPIKES)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.ARID_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_DESERT)
			.addOptional(RegisterWorldgen.OASIS)
			.addOptional(RegisterWorldgen.ARID_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_STANDARD)
			.addOptional(RegisterWorldgen.FLOWER_FIELD)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.DARK_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(RegisterWorldgen.MIXED_FOREST)
			.addOptional(RegisterWorldgen.PARCHED_FOREST)
			.addOptional(RegisterWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_OCEAN)
			.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_SWAMP)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(RegisterWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_SAVANNA)
			.addOptional(RegisterWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_SNOWY)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.HAS_WOODLAND_MANSION)
			.addOptional(RegisterWorldgen.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_TRAIL_RUINS)
			.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
			.addOptional(RegisterWorldgen.BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(RegisterWorldgen.BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(RegisterWorldgen.RAINFOREST)
			.addOptional(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(RegisterWorldgen.DARK_TAIGA)
			.addOptional(RegisterWorldgen.DYING_FOREST)
			.addOptional(RegisterWorldgen.DYING_MIXED_FOREST);
	}
}
