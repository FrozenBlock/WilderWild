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
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.frozenblock.lib.datagen.api.FrozenBiomeTagProvider;
import net.frozenblock.lib.tag.api.FrozenBiomeTags;
import net.frozenblock.wilderwild.registry.WWWorldgen;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public final class WWBiomeTagProvider extends FrozenBiomeTagProvider {

	public WWBiomeTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.generateCompat();
		this.generateBiomeTags();
		this.generateClimateAndVegetationTags();
		this.generateUtilityTags();
		this.generateFeatureTags();
		this.generateStructureTags();
	}

	@NotNull
	private TagKey<Biome> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

	private void generateCompat() {
		this.getOrCreateTagBuilder(getTag("sereneseasons:blacklisted_biomes"))
			.add(WWWorldgen.FROZEN_CAVES)
			.add(WWWorldgen.JELLYFISH_CAVES)
			.add(WWWorldgen.MAGMATIC_CAVES)
			.add(WWWorldgen.WARM_RIVER)
			.add(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(getTag("sereneseasons:tropical_biomes"))
			.add(WWWorldgen.OASIS)
			.add(WWWorldgen.ARID_SAVANNA)
			.add(WWWorldgen.ARID_FOREST)
			.add(WWWorldgen.PARCHED_FOREST)
			.add(WWWorldgen.RAINFOREST)
			.add(WWWorldgen.BIRCH_JUNGLE)
			.add(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.add(WWWorldgen.WARM_RIVER)
			.add(WWWorldgen.WARM_BEACH);
	}

	private void generateBiomeTags() {
		this.getOrCreateTagBuilder(WWBiomeTags.WILDER_WILD_BIOMES)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.WARM_BEACH)
			.addOptional(WWWorldgen.FROZEN_CAVES)
			.addOptional(WWWorldgen.JELLYFISH_CAVES)
			.addOptional(WWWorldgen.MAGMATIC_CAVES)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(BiomeTags.IS_OVERWORLD)
			.addOptionalTag(WWBiomeTags.WILDER_WILD_BIOMES);

		this.getOrCreateTagBuilder(BiomeTags.IS_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_CAVE)
			.addOptional(WWWorldgen.FROZEN_CAVES)
			.addOptional(WWWorldgen.JELLYFISH_CAVES)
			.addOptional(WWWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.DARK_FOREST)
			.add(Biomes.DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.GROVE)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.MEADOW)
			.add(Biomes.MEADOW);

		this.getOrCreateTagBuilder(WWBiomeTags.OAK_SAPLINGS_GROW_SWAMP_VARIANT)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptionalTag(BiomeTags.IS_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.NON_FROZEN_PLAINS)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.NORMAL_SAVANNA)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.SHORT_TAIGA)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.SNOWY_PLAINS)
			.add(Biomes.SNOWY_PLAINS);

		this.getOrCreateTagBuilder(WWBiomeTags.TALL_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.TALL_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.WINDSWEPT_HILLS)
			.add(Biomes.WINDSWEPT_HILLS)
			.add(Biomes.WINDSWEPT_GRAVELLY_HILLS);

		this.getOrCreateTagBuilder(WWBiomeTags.WINDSWEPT_SAVANNA)
			.add(Biomes.WINDSWEPT_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.IS_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.IS_SAVANNA)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.IS_RIVER)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.IS_BEACH)
			.addOptional(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_BEACH)
			.addOptional(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.RAINFOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST);
	}

	private void generateClimateAndVegetationTags() {
		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_HOT_OVERWORLD)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_TEMPERATE_OVERWORLD)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.WARM_BEACH)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_COLD_OVERWORLD)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.FROZEN_CAVES)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_WET_OVERWORLD)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_DRY_OVERWORLD)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.add(WWWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_DECIDUOUS_TREE)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_VEGETATION_SPARSE_OVERWORLD)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_VEGETATION_DENSE_OVERWORLD)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_FLORAL)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_DEAD)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_SNOWY)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_AQUATIC)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_JUNGLE_TREE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_SAVANNA_TREE)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_SAVANNA)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_DESERT)
			.addOptional(WWWorldgen.OASIS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_FOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_TAIGA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_RIVER)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_SWAMP)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_UNDERGROUND)
			.addOptional(WWWorldgen.JELLYFISH_CAVES)
			.addOptional(WWWorldgen.MAGMATIC_CAVES)
			.addOptional(WWWorldgen.FROZEN_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_ICY)
			.addOptional(WWWorldgen.FROZEN_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.LUKEWARM_WATER)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HOT_WATER)
			.add(Biomes.DESERT)
			.add(Biomes.BADLANDS)
			.add(Biomes.ERODED_BADLANDS)
			.add(Biomes.WOODED_BADLANDS)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.SNOWY_WATER)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.SNOWY_BEACH)
			.add(Biomes.SNOWY_PLAINS)
			.add(Biomes.SNOWY_SLOPES)
			.add(Biomes.GROVE)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.FROZEN_WATER)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.FROZEN_PEAKS)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.FROZEN_PEAKS)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);
	}

	private void generateUtilityTags() {
		this.getOrCreateTagBuilder(WWBiomeTags.FIREFLY_SPAWNABLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.FIREFLY_SPAWNABLE_CAVE);

		this.getOrCreateTagBuilder(WWBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.addOptional(WWWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.NO_POOLS)
			.addOptional(Biomes.DEEP_DARK);

		this.getOrCreateTagBuilder(WWBiomeTags.PEARLESCENT_JELLYFISH)
			.addOptional(WWWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.JELLYFISH_SPECIAL_SPAWN)
			.addOptional(WWWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CRAB)
			.add(Biomes.BEACH)
			.addOptional(WWWorldgen.WARM_BEACH)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptionalTag(WWBiomeTags.HAS_COMMON_CRAB);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_CRAB)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_OSTRICH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TUMBLEWEED_ENTITY)
			.add(Biomes.DESERT)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLAY_PATH)
			.addOptionalTag(WWBiomeTags.SAND_BEACHES)
			.addOptionalTag(WWBiomeTags.MULTI_LAYER_SAND_BEACHES)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(FrozenBiomeTags.CAN_LIGHTNING_OVERRIDE)
			.add(Biomes.DESERT)
			.add(Biomes.BADLANDS)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.JELLYFISH_CAVES)
			.addOptional(WWWorldgen.FROZEN_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.IS_FOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.WARM_BEACH)
			.addOptional(WWWorldgen.JELLYFISH_CAVES)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_GOLD_RABBITS)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.STRONGHOLD_BIASED_TO)
			.addOptionalTag(WWBiomeTags.WILDER_WILD_BIOMES);

		this.getOrCreateTagBuilder(BiomeTags.WATER_ON_MAP_OUTLINES)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.JELLYFISH_CAVES)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.GRAVEL_BEACH)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.RIVER)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.SAND_BEACHES)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.RIVER)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.MULTI_LAYER_SAND_BEACHES)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.SAVANNA)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.CHERRY_GROVE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.BELOW_SURFACE_SNOW)
			.add(Biomes.FROZEN_PEAKS)
			.add(Biomes.JAGGED_PEAKS)
			.add(Biomes.SNOWY_SLOPES)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.STRAYS_CAN_SPAWN_UNDERGROUND)
			.addOptional(WWWorldgen.FROZEN_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.SNOW_GOLEM_MELTS)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_SNOW_FOXES)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_WHITE_RABBITS)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);
	}

	private void generateFeatureTags() {
		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_BIRCH_TREES)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_CHERRY_TREES)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_OAK_AND_CYPRESS_TREES)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSSY_FALLEN_MIXED_TREES)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSSY_FALLEN_OAK_AND_BIRCH)
			.addOptional(WWWorldgen.RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_ACACIA_AND_OAK)
			.addOptionalTag(BiomeTags.IS_SAVANNA)
			.addOptionalTag(ConventionalBiomeTags.IS_SAVANNA)
			.addOptionalTag(ConventionalBiomeTags.IS_SAVANNA_TREE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_PALM)
			.addOptional(WWWorldgen.OASIS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_PALM_RARE)
			.add(Biomes.DESERT)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_LARGE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_FALLEN_LARGE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_DARK_OAK)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_FALLEN_DARK_OAK)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_BIRCH_AND_OAK_DARK_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_SPRUCE_TREES)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_LARGE_SPRUCE)
			.add(Biomes.TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_FALLEN_LARGE_SPRUCE)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_CLEAN_FALLEN_LARGE_SPRUCE)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLEAN_FALLEN_LARGE_SPRUCE)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLEAN_FALLEN_SPRUCE_TREES)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_SWAMP_OAK_TREES)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_MANGROVE_TREES)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.CHERRY_TREES)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_LAKE)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_LAKE_RARE)
			.addOptionalTag(BiomeTags.IS_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.FOREST_GRASS)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.PLAINS_GRASS)
			.add(Biomes.PLAINS)
			.add(Biomes.MEADOW);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_HUGE_RED_MUSHROOM)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_HUGE_BROWN_MUSHROOM)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_BROWN_MUSHROOM)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_RED_MUSHROOM)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIG_MUSHROOMS)
			.add(Biomes.BIRCH_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DYING_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIG_MUSHROOM_PATCH)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SWAMP_MUSHROOM)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SPONGE_BUD)
			.add(Biomes.WARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SPONGE_BUD_RARE)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUSH_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CARNATION)
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
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST);


		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MARIGOLD)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MARIGOLD_SPARSE)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);


		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DATURA)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CATTAIL)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CATTAIL_UNCOMMON)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CATTAIL_COMMON)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SEEDING_DANDELION)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.MEADOW)
			.add(Biomes.WINDSWEPT_HILLS)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_SEEDING_DANDELION)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_SEEDING_DANDELION)
			.add(Biomes.PLAINS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MILKWEED)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.SWAMP)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.DYING_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.CHERRY_FLOWERS)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TUMBLEWEED_PLANT)
			.add(Biomes.DESERT)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.SWAMP_TREES)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PALMS)
			.add(Biomes.DESERT)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WARM_BEACH_PALMS)
			.addOptional(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SHORT_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIG_COARSE_SHRUB)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_OAK)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.SWAMP)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS)
			.add(Biomes.GROVE)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_SNAPPED_LARGE_SPRUCE)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_LARGE_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_SPRUCE_SNOWY)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_LARGE_SPRUCE_SNOWY);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_SPRUCE)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_CYPRESS)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_LARGE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_ACACIA)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_ACACIA_AND_OAK)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_CHERRY)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_DARK_OAK)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_POLLEN)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_PUMPKIN)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FIELD_FLOWERS)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SUNFLOWER_PLAINS_FLOWERS)
			.add(Biomes.SUNFLOWER_PLAINS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SHORT_MEGA_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SHORT_MEGA_SPRUCE_SNOWY)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RED_SHELF_FUNGUS)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.PLAINS)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BROWN_SHELF_FUNGUS)
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
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RAINFOREST_MUSHROOM)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MIXED_MUSHROOM)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GLORY_OF_THE_SNOW)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FLOWERING_WATER_LILY)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BERRY_PATCH)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BUSH)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FOREST_SHRUB)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SHRUB)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.SAVANNA)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PLAINS_FLOWERS)
			.add(Biomes.PLAINS)
			.add(Biomes.FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CYPRESS_FLOWERS)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_MILKWEED)
			.add(Biomes.PLAINS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LARGE_FERN_AND_GRASS)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE)
			.add(Biomes.TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_NEW_RARE_GRASS)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FLOWER_FIELD_TALL_GRASS)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DENSE_FERN)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DENSE_TALL_GRASS)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SPARSE_JUNGLE_FLOWERS)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_JUNGLE_FLOWERS)
			.add(Biomes.JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_JUNGLE_BUSH)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SPARSE_BUSH)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ARID_BUSH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.PARCHED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FLOWER_FIELD_BUSH)
			.addOptional(WWWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RAINFOREST_BUSH)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BADLANDS_SAND_BUSH)
			.add(Biomes.BADLANDS)
			.add(Biomes.WOODED_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BADLANDS_TERRACOTTA_BUSH)
			.add(Biomes.BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WOODED_BADLANDS_TERRACOTTA_BUSH)
			.add(Biomes.WOODED_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BADLANDS_RARE_SAND_BUSH)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DESERT_BUSH)
			.add(Biomes.DESERT);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_OASIS_BUSH)
			.addOptional(WWWorldgen.OASIS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TALL_CACTUS)
			.add(Biomes.DESERT);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PRICKLY_PEAR)
			.add(Biomes.BADLANDS)
			.add(Biomes.WOODED_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_PRICKLY_PEAR)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TALL_BADLANDS_CACTUS)
			.add(Biomes.BADLANDS)
			.add(Biomes.WOODED_BADLANDS)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_PILE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.SWAMP)
			.addOptional(Biomes.MANGROVE_SWAMP)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DECORATIVE_MUD)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PACKED_MUD_ORE)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.DESERT)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_PATH)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_PATH_SMALL)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PACKED_MUD_PATH_BADLANDS)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SANDSTONE_PATH)
			.add(Biomes.DESERT)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_STONE_PILE)
			.add(Biomes.STONY_SHORE)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_STONE_PILE_RARE)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.FLOWER_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_TRANSITION_DISK)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_CLEARING)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ROOTED_DIRT_CLEARING)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GRAVEL_CLEARING)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIRCH_CLEARING_FLOWERS)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FOREST_CLEARING_FLOWERS)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SCORCHED_SAND)
			.add(Biomes.DESERT)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SCORCHED_RED_SAND)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SMALL_SAND_TRANSITION)
			.add(Biomes.SNOWY_BEACH)
			.add(Biomes.BEACH)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SAND_TRANSITION)
			.add(Biomes.DESERT)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RED_SAND_TRANSITION)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_STONE_TRANSITION)
			.add(Biomes.STONY_PEAKS)
			.add(Biomes.STONY_SHORE)
			.add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_STONE_TRANSITION)
			.add(Biomes.STONY_PEAKS)
			.add(Biomes.STONY_SHORE)
			.add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BETA_BEACH_SAND_TRANSITION)
			.addOptionalTag(WWBiomeTags.SAND_BEACHES)
			.addOptionalTag(WWBiomeTags.MULTI_LAYER_SAND_BEACHES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BETA_BEACH_GRAVEL_TRANSITION)
			.addOptionalTag(WWBiomeTags.GRAVEL_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GRAVEL_TRANSITION)
			.add(Biomes.WINDSWEPT_GRAVELLY_HILLS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MUD_TRANSITION)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TERMITE_MOUND)
			.addOptionalTag(BiomeTags.IS_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_NETHER_GEYSER)
			.add(Biomes.BASALT_DELTAS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_NETHER_LAVA_GEYSER)
			.add(Biomes.NETHER_WASTES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TAIGA_FOREST_ROCK)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_PATH)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.OASIS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PACKED_MUD_PATH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.PARCHED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MUD_BASIN)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MUD_PILE)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MUD_LAKE)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ALGAE_SMALL)
			.addOptionalTag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ALGAE)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WATER_POOLS)
			.add(Biomes.RIVER)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(ConventionalBiomeTags.IS_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WATER_SHRUBS)
			.add(Biomes.RIVER)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(ConventionalBiomeTags.IS_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WATER_GRASS)
			.add(Biomes.RIVER)
			.addOptionalTag(BiomeTags.IS_OCEAN)
			.addOptionalTag(ConventionalBiomeTags.IS_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_BASIN)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PODZOL_BASIN)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_CARPET)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SWAMP)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_COARSE)
			.add(Biomes.FOREST)
			.add(Biomes.SAVANNA)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_GRAVEL)
			.add(Biomes.FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_STONE)
			.add(Biomes.FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST);
	}

	private void generateStructureTags() {
		this.getOrCreateTagBuilder(WWBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE)
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
			.addOptionalTag(WWBiomeTags.WILDER_WILD_BIOMES);

		this.getOrCreateTagBuilder(BiomeTags.HAS_DESERT_PYRAMID)
			.addOptional(WWWorldgen.OASIS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_IGLOO)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_MINESHAFT)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.WARM_RIVER)
			.addOptional(WWWorldgen.WARM_BEACH)
			.addOptional(WWWorldgen.FROZEN_CAVES)
			.addOptional(WWWorldgen.JELLYFISH_CAVES)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(BiomeTags.HAS_PILLAGER_OUTPOST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.ICE_SPIKES)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.ARID_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_DESERT)
			.addOptional(WWWorldgen.OASIS)
			.addOptional(WWWorldgen.ARID_SAVANNA)
			.addOptional(WWWorldgen.ARID_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_STANDARD)
			.addOptional(WWWorldgen.FLOWER_FIELD)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.DARK_BIRCH_FOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWWorldgen.MIXED_FOREST)
			.addOptional(WWWorldgen.PARCHED_FOREST)
			.addOptional(WWWorldgen.SEMI_BIRCH_FOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.MAGMATIC_CAVES)
			.addOptional(WWWorldgen.MAPLE_GROVE);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_OCEAN)
			.addOptional(WWWorldgen.JELLYFISH_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_SWAMP)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWWorldgen.FLOWER_FIELD);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_SAVANNA)
			.addOptional(WWWorldgen.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_SNOWY)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.HAS_WOODLAND_MANSION)
			.addOptional(WWWorldgen.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_TRAIL_RUINS)
			.addOptional(WWWorldgen.CYPRESS_WETLANDS)
			.addOptional(WWWorldgen.BIRCH_JUNGLE)
			.addOptional(WWWorldgen.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWWorldgen.BIRCH_TAIGA)
			.addOptional(WWWorldgen.TEMPERATE_RAINFOREST)
			.addOptional(WWWorldgen.RAINFOREST)
			.addOptional(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWWorldgen.DARK_TAIGA)
			.addOptional(WWWorldgen.DYING_FOREST)
			.addOptional(WWWorldgen.DYING_MIXED_FOREST)
			.addOptional(WWWorldgen.MAPLE_GROVE);
	}
}
