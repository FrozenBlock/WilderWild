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
import net.frozenblock.wilderwild.registry.WWBiomes;
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
			.add(WWBiomes.FROZEN_CAVES)
			.add(WWBiomes.MESOGLEA_CAVES)
			.add(WWBiomes.MAGMATIC_CAVES)
			.add(WWBiomes.WARM_RIVER)
			.add(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(getTag("sereneseasons:tropical_biomes"))
			.add(WWBiomes.OASIS)
			.add(WWBiomes.ARID_SAVANNA)
			.add(WWBiomes.ARID_FOREST)
			.add(WWBiomes.PARCHED_FOREST)
			.add(WWBiomes.RAINFOREST)
			.add(WWBiomes.BIRCH_JUNGLE)
			.add(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.add(WWBiomes.WARM_RIVER)
			.add(WWBiomes.WARM_BEACH);
	}

	private void generateBiomeTags() {
		this.getOrCreateTagBuilder(WWBiomeTags.WILDER_WILD_BIOMES)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.WARM_BEACH)
			.addOptional(WWBiomes.FROZEN_CAVES)
			.addOptional(WWBiomes.MESOGLEA_CAVES)
			.addOptional(WWBiomes.MAGMATIC_CAVES)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(BiomeTags.IS_OVERWORLD)
			.addOptionalTag(WWBiomeTags.WILDER_WILD_BIOMES);

		this.getOrCreateTagBuilder(BiomeTags.IS_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_CAVE)
			.addOptional(WWBiomes.FROZEN_CAVES)
			.addOptional(WWBiomes.MESOGLEA_CAVES)
			.addOptional(WWBiomes.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.DARK_FOREST)
			.add(Biomes.DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.GROVE)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.MEADOW)
			.add(Biomes.MEADOW);

		this.getOrCreateTagBuilder(WWBiomeTags.OAK_SAPLINGS_GROW_SWAMP_VARIANT)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptionalTag(BiomeTags.IS_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.NON_FROZEN_PLAINS)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.NORMAL_SAVANNA)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(WWBiomes.ARID_SAVANNA);

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
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.IS_SAVANNA)
			.addOptional(WWBiomes.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.IS_RIVER)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.IS_BEACH)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_BEACH)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.RAINFOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST);
	}

	private void generateClimateAndVegetationTags() {
		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_HOT_OVERWORLD)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_TEMPERATE_OVERWORLD)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.WARM_BEACH)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_COLD_OVERWORLD)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.FROZEN_CAVES)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_WET_OVERWORLD)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.MESOGLEA_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_DRY_OVERWORLD)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.add(WWBiomes.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_DECIDUOUS_TREE)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_VEGETATION_SPARSE_OVERWORLD)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_VEGETATION_DENSE_OVERWORLD)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_FLORAL)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_DEAD)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_SNOWY)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_AQUATIC)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.MESOGLEA_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_JUNGLE_TREE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_SAVANNA_TREE)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_SAVANNA)
			.addOptional(WWBiomes.ARID_SAVANNA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_DESERT)
			.addOptional(WWBiomes.OASIS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_FOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_TAIGA);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_RIVER)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_UNDERGROUND)
			.addOptional(WWBiomes.MESOGLEA_CAVES)
			.addOptional(WWBiomes.MAGMATIC_CAVES)
			.addOptional(WWBiomes.FROZEN_CAVES);

		this.getOrCreateTagBuilder(ConventionalBiomeTags.IS_ICY)
			.addOptional(WWBiomes.FROZEN_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.LUKEWARM_WATER)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HOT_WATER)
			.add(Biomes.DESERT)
			.add(Biomes.BADLANDS)
			.add(Biomes.ERODED_BADLANDS)
			.add(Biomes.WOODED_BADLANDS)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.SNOWY_WATER)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.SNOWY_BEACH)
			.add(Biomes.SNOWY_PLAINS)
			.add(Biomes.SNOWY_SLOPES)
			.add(Biomes.GROVE)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.FROZEN_WATER)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.FROZEN_PEAKS)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.FROZEN_PEAKS)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);
	}

	private void generateUtilityTags() {
		this.getOrCreateTagBuilder(WWBiomeTags.FIREFLY_SPAWNABLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.FIREFLY_SPAWNABLE_CAVE);

		this.getOrCreateTagBuilder(WWBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.addOptional(WWBiomes.MESOGLEA_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.NO_POOLS)
			.addOptional(Biomes.DEEP_DARK);

		this.getOrCreateTagBuilder(WWBiomeTags.PEARLESCENT_JELLYFISH)
			.addOptional(WWBiomes.MESOGLEA_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.JELLYFISH_SPECIAL_SPAWN)
			.addOptional(WWBiomes.MESOGLEA_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CRAB)
			.add(Biomes.BEACH)
			.addOptional(WWBiomes.WARM_BEACH)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PENGUIN)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.SNOWY_BEACH)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TUMBLEWEED_ENTITY)
			.add(Biomes.DESERT)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLAY_PATH)
			.addOptionalTag(WWBiomeTags.SAND_BEACHES)
			.addOptionalTag(WWBiomeTags.MULTI_LAYER_SAND_BEACHES)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(FrozenBiomeTags.CAN_LIGHTNING_OVERRIDE)
			.add(Biomes.DESERT)
			.add(Biomes.BADLANDS)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.MESOGLEA_CAVES)
			.addOptional(WWBiomes.FROZEN_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.IS_FOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.WARM_BEACH)
			.addOptional(WWBiomes.MESOGLEA_CAVES)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_GOLD_RABBITS)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.STRONGHOLD_BIASED_TO)
			.addOptionalTag(WWBiomeTags.WILDER_WILD_BIOMES);

		this.getOrCreateTagBuilder(BiomeTags.WATER_ON_MAP_OUTLINES)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.MESOGLEA_CAVES)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.GRAVEL_BEACH)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.RIVER)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.SAND_BEACHES)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.RIVER)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.MULTI_LAYER_SAND_BEACHES)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.SAVANNA)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.CHERRY_GROVE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(ResourceLocation.parse("terralith:arid_highlands"));

		this.getOrCreateTagBuilder(WWBiomeTags.BELOW_SURFACE_SNOW)
			.add(Biomes.FROZEN_PEAKS)
			.add(Biomes.JAGGED_PEAKS)
			.add(Biomes.SNOWY_SLOPES)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.STRAYS_CAN_SPAWN_UNDERGROUND)
			.addOptional(WWBiomes.FROZEN_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.SNOW_GOLEM_MELTS)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_SNOW_FOXES)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.SPAWNS_WHITE_RABBITS)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);
	}

	private void generateFeatureTags() {
		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_BIRCH_TREES)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_CHERRY_TREES)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_OAK_AND_CYPRESS_TREES)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSSY_FALLEN_MIXED_TREES)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSSY_FALLEN_OAK_AND_BIRCH)
			.addOptional(WWBiomes.RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_ACACIA_AND_OAK)
			.addOptionalTag(BiomeTags.IS_SAVANNA)
			.addOptionalTag(ConventionalBiomeTags.IS_SAVANNA)
			.addOptionalTag(ConventionalBiomeTags.IS_SAVANNA_TREE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_PALM)
			.addOptional(WWBiomes.OASIS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_PALM_RARE)
			.add(Biomes.DESERT)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_LARGE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_FALLEN_LARGE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_DARK_OAK)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_FALLEN_DARK_OAK)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_BIRCH_AND_OAK_DARK_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_SPRUCE_TREES)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_LARGE_SPRUCE)
			.add(Biomes.TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_FALLEN_LARGE_SPRUCE)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_CLEAN_FALLEN_LARGE_SPRUCE)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLEAN_FALLEN_LARGE_SPRUCE)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLEAN_FALLEN_SPRUCE_TREES)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_SWAMP_OAK_TREES)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_MANGROVE_TREES)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_MAPLE_TREES)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.CHERRY_TREES)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_LAKE)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_LAKE_RARE)
			.addOptionalTag(BiomeTags.IS_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE);

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
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.PLAINS_GRASS)
			.add(Biomes.PLAINS)
			.add(Biomes.MEADOW)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_HUGE_RED_MUSHROOM)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_HUGE_BROWN_MUSHROOM)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_BROWN_MUSHROOM)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_RED_MUSHROOM)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIG_MUSHROOMS)
			.add(Biomes.BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DYING_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIG_MUSHROOM_PATCH)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST);

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
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MARIGOLD)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MARIGOLD_SPARSE)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PINK_TULIP_UNCOMMON)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ALLIUM_UNCOMMON)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DATURA)
			.add(Biomes.CHERRY_GROVE)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ROSE_BUSH)
			.add(Biomes.CHERRY_GROVE)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PEONY)
			.add(Biomes.CHERRY_GROVE)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LILAC)
			.add(Biomes.CHERRY_GROVE)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CATTAIL)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA);

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
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SEEDING_DANDELION)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.MEADOW)
			.add(Biomes.WINDSWEPT_HILLS)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.CHERRY_GROVE)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_SEEDING_DANDELION)
			.addOptional(Biomes.CHERRY_GROVE)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_SEEDING_DANDELION)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_VERY_RARE_SEEDING_DANDELION)
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
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.DYING_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.CHERRY_FLOWERS)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TUMBLEWEED_PLANT)
			.add(Biomes.DESERT)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.SWAMP_TREES)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PALMS)
			.add(Biomes.DESERT)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.addOptional(WWBiomes.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WARM_BEACH_PALMS)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SHORT_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIG_COARSE_SHRUB)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_OAK)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.SWAMP)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS)
			.add(Biomes.GROVE)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_SNAPPED_LARGE_SPRUCE)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_LARGE_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_SPRUCE_SNOWY)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_LARGE_SPRUCE_SNOWY);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_SPRUCE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_CYPRESS)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_LARGE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.SPARSE_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_ACACIA)
			.addOptional(WWBiomes.ARID_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_ACACIA_AND_OAK)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_CHERRY)
			.add(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_DARK_OAK)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_MAPLE)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_POLLEN)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.CHERRY_GROVE)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COMMON_PUMPKIN)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FIELD_FLOWERS)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SUNFLOWER_PLAINS_FLOWERS)
			.add(Biomes.SUNFLOWER_PLAINS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SHORT_MEGA_SPRUCE)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SHORT_MEGA_SPRUCE_SNOWY)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.GROVE)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RED_SHELF_FUNGI)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.PLAINS)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.CHERRY_GROVE)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BROWN_SHELF_FUNGI)
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
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CRIMSON_SHELF_FUNGI)
			.add(Biomes.CRIMSON_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WARPED_SHELF_FUNGI)
			.add(Biomes.WARPED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WARPED_SHELF_FUNGI_RARE)
			.add(Biomes.CRIMSON_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CRIMSON_SHELF_FUNGI_RARE)
			.add(Biomes.WARPED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_CRIMSON_FUNGI)
			.add(Biomes.CRIMSON_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_CRIMSON_FUNGI)
			.add(Biomes.CRIMSON_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_WARPED_FUNGI)
			.add(Biomes.WARPED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_WARPED_FUNGI)
			.add(Biomes.WARPED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RAINFOREST_MUSHROOM)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MIXED_MUSHROOM)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GLORY_OF_THE_SNOW)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FLOWERING_WATER_LILY)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BERRY_PATCH)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BUSH)
			.add(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FOREST_SHRUB)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SHRUB)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.SAVANNA)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GENERIC_FLOWERS)
			.add(Biomes.FOREST)
			.addOptional(WWBiomes.DYING_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PLAINS_FLOWERS)
			.add(Biomes.PLAINS)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TUNDRA_FLOWERS)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIRCH_FLOWERS)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CYPRESS_FLOWERS)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_MILKWEED)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MYCELIUM_GROWTH)
			.add(Biomes.MUSHROOM_FIELDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LARGE_FERN_AND_GRASS)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE)
			.add(Biomes.TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_NEW_RARE_GRASS)
			.add(Biomes.WINDSWEPT_FOREST)
			.add(Biomes.WINDSWEPT_HILLS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FLOWER_FIELD_TALL_GRASS)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DENSE_FERN)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DENSE_TALL_GRASS)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SPARSE_JUNGLE_FLOWERS)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_JUNGLE_FLOWERS)
			.add(Biomes.JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_JUNGLE_BUSH)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SPARSE_BUSH)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ARID_BUSH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.PARCHED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FLOWER_FIELD_BUSH)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RAINFOREST_BUSH)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST);

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
			.addOptional(WWBiomes.OASIS);

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
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DECORATIVE_MUD)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PACKED_MUD_ORE)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.add(Biomes.DESERT)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_PATH)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.WINDSWEPT_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_PATH_SMALL)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PACKED_MUD_PATH_BADLANDS)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SANDSTONE_PATH)
			.add(Biomes.DESERT)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_STONE_PILE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_STONE_PILE_COMMON)
			.add(Biomes.STONY_SHORE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_STONE_PILE_RARE)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK_RARE)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_TRANSITION_DISK)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

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
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

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
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GRAVEL_CLEARING)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BIRCH_CLEARING_FLOWERS)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FOREST_CLEARING_FLOWERS)
			.add(Biomes.FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SCORCHED_SAND)
			.add(Biomes.DESERT)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SCORCHED_RED_SAND)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SMALL_SAND_TRANSITION)
			.add(Biomes.SNOWY_BEACH)
			.add(Biomes.BEACH)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SAND_TRANSITION)
			.add(Biomes.DESERT)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST);

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
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_PATH)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.OASIS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PACKED_MUD_PATH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.SAVANNA_PLATEAU)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.PARCHED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MUD_BASIN)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MUD_PILE)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MUD_LAKE)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ALGAE_SMALL)
			.addOptionalTag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ALGAE)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

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
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PODZOL_BASIN)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOSS_CARPET)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SWAMP)
			.add(Biomes.JUNGLE)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_COARSE)
			.add(Biomes.FOREST)
			.add(Biomes.SAVANNA)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_RARE_GRAVEL)
			.add(Biomes.FOREST)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

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
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST)
			.addOptional(WWBiomes.TUNDRA);
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
			.addOptional(WWBiomes.OASIS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_IGLOO)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_MINESHAFT)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.WARM_BEACH)
			.addOptional(WWBiomes.FROZEN_CAVES)
			.addOptional(WWBiomes.MESOGLEA_CAVES)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_PILLAGER_OUTPOST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.ICE_SPIKES)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_DESERT)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.ARID_SAVANNA)
			.addOptional(WWBiomes.ARID_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_STANDARD)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.MAGMATIC_CAVES)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_OCEAN)
			.addOptional(WWBiomes.MESOGLEA_CAVES);

		this.getOrCreateTagBuilder(BiomeTags.HAS_RUINED_PORTAL_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_SAVANNA)
			.addOptional(WWBiomes.ARID_SAVANNA);

		this.getOrCreateTagBuilder(BiomeTags.HAS_VILLAGE_SNOWY)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(BiomeTags.HAS_WOODLAND_MANSION)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(BiomeTags.HAS_TRAIL_RUINS)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.MAPLE_FOREST);
	}
}
