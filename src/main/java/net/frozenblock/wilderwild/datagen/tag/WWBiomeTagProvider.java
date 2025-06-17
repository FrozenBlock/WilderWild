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
		this.generateMusicPoolTags();
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

		this.getOrCreateTagBuilder(WWBiomeTags.PALE_GARDEN)
			.add(Biomes.PALE_GARDEN);

		this.getOrCreateTagBuilder(WWBiomeTags.GROVE)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.MEADOW)
			.add(Biomes.MEADOW);

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

	private void generateMusicPoolTags() {
		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FOREST_MUSIC)
			.addOptional(Biomes.FOREST)
			.addOptional(Biomes.BIRCH_FOREST)
			.addOptional(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.TEMPERATE_RAINFOREST)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);
		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FLOWER_FOREST_MUSIC)
			.addOptional(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LUSH_MUSIC)
			.addOptional(Biomes.LUSH_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DRIPSTONE_MUSIC)
			.addOptional(Biomes.DRIPSTONE_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CHERRY_MUSIC)
			.addOptional(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GROVE_MUSIC)
			.addOptional(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_JUNGLE_MUSIC)
			.addOptional(Biomes.JUNGLE);
		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BAMBOO_JUNGLE_MUSIC)
			.addOptional(Biomes.BAMBOO_JUNGLE);
		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SPARSE_JUNGLE_MUSIC)
			.addOptional(Biomes.SPARSE_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNOWY_MUSIC)
			.addOptional(Biomes.SNOWY_TAIGA)
			.addOptional(Biomes.SNOWY_PLAINS)
			.addOptional(Biomes.SNOWY_BEACH)
			.addOptional(Biomes.ICE_SPIKES)
			.addOptional(Biomes.FROZEN_RIVER)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MAPLE_MUSIC)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DYING_MUSIC)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNOWY_DYING_MUSIC)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MAGMATIC_MUSIC)
			.addOptional(WWBiomes.MAGMATIC_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FROZEN_MUSIC)
			.addOptional(WWBiomes.FROZEN_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MESOGLEA_MUSIC)
			.addOptional(WWBiomes.MESOGLEA_CAVES);
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
		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FIREFLY)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BUTTERFLY)
			.add(Biomes.MEADOW)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.BUTTERFLY_COMMON_SPAWN)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.BUTTERFLY_MONARCH)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.BUTTERFLY_RED_LACEWING)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.BUTTERFLY_MARBLED)
			.add(Biomes.MEADOW)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.BUTTERFLY_MORPHO_BLUE)
			.add(Biomes.MEADOW)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.BUTTERFLY_GREEN_HAIRSTREAK)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.BUTTERFLY_CLOUDED_YELLOW)
			.add(Biomes.MEADOW)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.BUTTERFLY_DUSKWING)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.addOptionalTag(WWBiomeTags.JELLYFISH_COMMON_SPAWN);

		this.getOrCreateTagBuilder(WWBiomeTags.JELLYFISH_COMMON_SPAWN)
			.addOptional(WWBiomes.MESOGLEA_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.BLUE_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.FROZEN_RIVER)
			.addOptional(WWBiomes.FROZEN_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.LIME_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.RIVER)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.LUSH_CAVES)
			.addOptional(WWBiomes.FROZEN_CAVES)
			.addOptional(WWBiomes.WARM_RIVER);

		this.getOrCreateTagBuilder(WWBiomeTags.PINK_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.RIVER)
			.add(Biomes.LUSH_CAVES)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.addOptional(WWBiomes.WARM_RIVER);

		this.getOrCreateTagBuilder(WWBiomeTags.RED_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.RIVER)
			.add(Biomes.LUSH_CAVES)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.addOptional(WWBiomes.WARM_RIVER);

		this.getOrCreateTagBuilder(WWBiomeTags.YELLOW_JELLYFISH)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.RIVER)
			.add(Biomes.LUSH_CAVES)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.addOptional(WWBiomes.WARM_RIVER);

		this.getOrCreateTagBuilder(WWBiomeTags.PEARLESCENT_JELLYFISH)
			.addOptional(WWBiomes.MESOGLEA_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MOOBLOOM)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_DANDELION)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptionalTag(BiomeTags.IS_SAVANNA)
			.addOptionalTag(BiomeTags.IS_JUNGLE)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_POPPY)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptionalTag(BiomeTags.IS_SAVANNA)
			.addOptionalTag(BiomeTags.IS_JUNGLE)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_AZURE_BLUET)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_RED_TULIP)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_ORANGE_TULIP)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_WHITE_TULIP)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_PINK_TULIP)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_OXEYE_DAISY)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_CORNFLOWER)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_LILY_OF_THE_VALLEY)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptionalTag(ConventionalBiomeTags.IS_BIRCH_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_ALLIUM)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.PLAINS)
			.addOptionalTag(BiomeTags.IS_FOREST)
			.addOptionalTag(ConventionalBiomeTags.IS_BIRCH_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_BLUE_ORCHID)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.SWAMP)
			.addOptional(WWBiomes.FLOWER_FIELD);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_CARNATION)
			.add(Biomes.FLOWER_FOREST)
			.addOptional(WWBiomes.FLOWER_FIELD)
			.addOptionalTag(WWBiomeTags.HAS_CARNATION);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_MARIGOLD)
			.add(Biomes.SUNFLOWER_PLAINS)
			.addOptionalTag(WWBiomeTags.HAS_MARIGOLD)
			.addOptionalTag(WWBiomeTags.HAS_MARIGOLD_SPARSE);

		this.getOrCreateTagBuilder(WWBiomeTags.MOOBLOOM_PASQUEFLOWER)
			.add(Biomes.MEADOW)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CRAB)
			.addOptional(WWBiomes.WARM_BEACH)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_OSTRICH)
			.add(Biomes.SAVANNA)
			.add(Biomes.SAVANNA_PLATEAU)
			.add(Biomes.WINDSWEPT_SAVANNA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PENGUIN)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.DEEP_FROZEN_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TUMBLEWEED_ENTITY)
			.add(Biomes.DESERT)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.addOptionalTag(BiomeTags.IS_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLAY_PATH)
			.addOptionalTag(WWBiomeTags.BETA_BEACH_SAND)
			.addOptionalTag(WWBiomeTags.BETA_BEACH_MULTI_LAYER_SAND)
			.addOptional(WWBiomes.OASIS)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.WARM_BEACH);

		this.getOrCreateTagBuilder(FrozenBiomeTags.CAN_LIGHTNING_OVERRIDE)
			.add(Biomes.DESERT)
			.add(Biomes.BADLANDS)
			.add(Biomes.ERODED_BADLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.PRODUCES_BARNACLES_FROM_BONEMEAL)
			.add(Biomes.STONY_SHORE)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.PRODUCES_SPONGE_BUDS_FROM_BONEMEAL)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.WARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.PRODUCES_SEA_ANEMONE_FROM_BONEMEAL)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.DEEP_FROZEN_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.PRODUCES_SEA_WHIPS_FROM_BONEMEAL)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.PRODUCES_TUBE_WORMS_FROM_BONEMEAL)
			.add(Biomes.DEEP_COLD_OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.WARM_OCEAN);

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

		this.getOrCreateTagBuilder(WWBiomeTags.BETA_BEACH_GRAVEL)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_PINE_TAIGA)
			.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.RIVER)
			.add(Biomes.PALE_GARDEN)
			.addOptional(WWBiomes.MIXED_FOREST)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.BETA_BEACH_SAND)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.FROZEN_RIVER)
			.add(Biomes.RIVER)
			.add(Biomes.CHERRY_GROVE)
			.addOptional(WWBiomes.PARCHED_FOREST)
			.addOptional(WWBiomes.ARID_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.SPARSE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.BETA_BEACH_MULTI_LAYER_SAND)
			.add(Biomes.JUNGLE)
			.add(Biomes.SAVANNA)
			.add(Biomes.SPARSE_JUNGLE)
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
			.add(Biomes.CHERRY_GROVE);

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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_PALE_OAK)
			.add(Biomes.PALE_GARDEN);

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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_SWAMP_TREES)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_MANGROVE_TREES)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FALLEN_MAPLE_TREES)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.BAMBOO_JUNGLE_TREES)
			.add(Biomes.BAMBOO_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.SPARSE_JUNGLE_TREES)
			.add(Biomes.SPARSE_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.JUNGLE_TREES)
			.add(Biomes.JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.MANGROVE_TREES)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.CHERRY_TREES)
			.add(Biomes.CHERRY_GROVE);

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
			.add(Biomes.PALE_GARDEN)
			.add(Biomes.TAIGA)
			.add(Biomes.MANGROVE_SWAMP)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.CHERRY_GROVE)
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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PALE_MUSHROOM)
			.add(Biomes.PALE_GARDEN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_HUGE_PALE_MUSHROOM)
			.add(Biomes.PALE_GARDEN);

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
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SPONGE_BUD_RARE)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.LUSH_CAVES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CARNATION)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.RAINFOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_EYEBLOSSOM)
			.add(Biomes.PALE_GARDEN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PINK_TULIP_UNCOMMON)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ALLIUM_UNCOMMON)
			.addOptional(WWBiomes.MAPLE_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DATURA)
			.add(Biomes.CHERRY_GROVE)
			.add(Biomes.BIRCH_FOREST)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CATTAIL_MUD)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BARNACLES_COMMON)
			.add(Biomes.STONY_SHORE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BARNACLES_STRUCTURE)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BARNACLES)
			.add(Biomes.MANGROVE_SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BARNACLES_SPARSE)
			.add(Biomes.COLD_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BARNACLES_RARE)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SEA_ANEMONE)
			.add(Biomes.DEEP_LUKEWARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SEA_ANEMONE_SPARSE)
			.add(Biomes.DEEP_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SEA_ANEMONE_RARE)
			.add(Biomes.DEEP_COLD_OCEAN)
			.add(Biomes.DEEP_FROZEN_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SEA_WHIP)
			.add(Biomes.OCEAN)
			.add(Biomes.LUKEWARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SEA_WHIP_SPARSE)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SEA_WHIP_RARE)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.WARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TUBE_WORMS)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN)
			.add(Biomes.WARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TUBE_WORMS_RARE)
			.add(Biomes.DEEP_COLD_OCEAN);

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
			.add(Biomes.CHERRY_GROVE)
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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WILDFLOWERS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PHLOX);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LANTANAS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LANTANAS_SPARSE)
			.add(Biomes.SAVANNA)
			.add(Biomes.WINDSWEPT_SAVANNA)
			.addOptional(WWBiomes.PARCHED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PHLOX_SPARSE)
			.add(Biomes.SWAMP)
			.addOptional(WWBiomes.DYING_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WILDFLOWERS_SPARSE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WILDFLOWERS_AND_PHLOX)
			.add(Biomes.BIRCH_FOREST)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WILDFLOWERS_AND_PHLOX_SPARSE)
			.addOptionalTag(WWBiomeTags.MEADOW);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WILDFLOWERS_AND_LANTANAS)
			.add(Biomes.SUNFLOWER_PLAINS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LANTANAS_AND_PHLOX)
			.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_LANTANAS_AND_PHLOX_SPARSE)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLOVERS)
			.add(Biomes.CHERRY_GROVE)
			.addOptionalTag(WWBiomeTags.MEADOW);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CLOVERS_SPARSE)
			.add(WWBiomes.RAINFOREST)
			.add(Biomes.SPARSE_JUNGLE)
			.add(Biomes.JUNGLE)
			.add(Biomes.BAMBOO_JUNGLE)
			.add(Biomes.DARK_FOREST)
			.add(Biomes.OCEAN)
			.add(Biomes.LUKEWARM_OCEAN)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.RIVER)
			.addOptional(WWBiomes.WARM_RIVER)
			.addOptional(WWBiomes.CYPRESS_WETLANDS)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MILKWEED)
			.add(Biomes.FLOWER_FOREST)
			.add(Biomes.FOREST)
			.add(Biomes.SWAMP)
			.add(Biomes.DARK_FOREST)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.DARK_BIRCH_FOREST)
			.addOptional(WWBiomes.OLD_GROWTH_DARK_FOREST)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNOWY_PLAINS_FLOWERS)
			.add(Biomes.SNOWY_PLAINS)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.ICE_SPIKES)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CHERRY_FLOWERS)
			.add(Biomes.CHERRY_GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SURFACE_ICICLES)
			.add(Biomes.ICE_SPIKES);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SURFACE_FRAGILE_ICE)
			.add(Biomes.SNOWY_PLAINS)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.SNOWY_TAIGA)
			.add(Biomes.SNOWY_BEACH)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.DEEP_FROZEN_OCEAN)
			.add(Biomes.FROZEN_RIVER)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNAPPED_PALE_OAK)
			.add(Biomes.PALE_GARDEN);

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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_HIBISCUS)
			.addOptional(WWBiomes.BIRCH_JUNGLE)
			.addOptional(WWBiomes.SPARSE_BIRCH_JUNGLE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FLOWERING_WATER_LILY)
			.add(Biomes.SWAMP)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

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
			.add(Biomes.FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GENERIC_FLOWERS_NO_CARNATIONS)
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
			.addOptional(WWBiomes.SPARSE_FOREST)
			.addOptional(WWBiomes.SEMI_BIRCH_FOREST);

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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SWAMP_FERN)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DENSE_FERN)
			.add(Biomes.MANGROVE_SWAMP)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SWAMP_TALL_GRASS)
			.add(Biomes.SWAMP);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_DENSE_TALL_GRASS)
			.add(Biomes.SWAMP)
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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_WOODED_BADLANDS_BUSH)
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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PALE_MOSS_PILE)
			.add(Biomes.PALE_GARDEN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_GRAVEL_AND_PALE_MOSS_PATH)
			.add(Biomes.PALE_GARDEN);

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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_STONE_PILE)
			.add(Biomes.STONY_SHORE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK)
			.addOptional(WWBiomes.DYING_FOREST)
			.addOptional(WWBiomes.DYING_MIXED_FOREST)
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
			.addOptional(WWBiomes.RAINFOREST);

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
			.addOptionalTag(WWBiomeTags.BETA_BEACH_SAND)
			.addOptionalTag(WWBiomeTags.BETA_BEACH_MULTI_LAYER_SAND);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_BETA_BEACH_GRAVEL_TRANSITION)
			.addOptionalTag(WWBiomeTags.BETA_BEACH_GRAVEL);

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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_HYDROTHERMAL_VENT)
			.add(Biomes.WARM_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_HYDROTHERMAL_VENT_RARE)
			.add(Biomes.DEEP_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_TAIGA_FOREST_ROCK)
			.add(Biomes.TAIGA)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(WWBiomes.BIRCH_TAIGA)
			.addOptional(WWBiomes.DARK_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_SNOW_PILE)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CREEPING_AUBURN_MOSS)
			.addOptional(WWBiomes.MAPLE_FOREST)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_AUBURN_MOSS)
			.addOptional(WWBiomes.TUNDRA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_CREEPING_AUBURN_MOSS_UNDERWATER)
			.add(Biomes.OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.DEEP_FROZEN_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_AUBURN_MOSS_UNDERWATER)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_AUBURN_MOSS_UNDERWATER_RARE)
			.add(Biomes.OCEAN)
			.add(Biomes.COLD_OCEAN)
			.add(Biomes.FROZEN_OCEAN)
			.add(Biomes.DEEP_FROZEN_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ALGAE_SMALL)
			.addOptionalTag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_ALGAE)
			.addOptional(WWBiomes.CYPRESS_WETLANDS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_PLANKTON)
			.add(Biomes.WARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_MEADOW_SEAGRASS)
			.addOptional(Biomes.DEEP_LUKEWARM_OCEAN);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_OCEAN_MOSS)
			.addOptional(Biomes.DEEP_LUKEWARM_OCEAN);

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
			.add(Biomes.PALE_GARDEN)
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

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FROZEN_FOREST_GRASS)
			.addOptional(WWBiomes.SNOWY_DYING_FOREST)
			.addOptional(WWBiomes.SNOWY_DYING_MIXED_FOREST);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FROZEN_NORMAL_GRASS);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FROZEN_PLAIN_GRASS)
			.add(Biomes.SNOWY_PLAINS)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.GROVE);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FROZEN_TAIGA_GRASS)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FROZEN_TAIGA_2_GRASS)
			.add(Biomes.SNOWY_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FROZEN_LARGE_FERNS)
			.add(Biomes.SNOWY_TAIGA)
			.addOptional(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA);

		this.getOrCreateTagBuilder(WWBiomeTags.HAS_FROZEN_PLAIN_TALL_GRASS)
			.add(Biomes.SNOWY_PLAINS)
			.add(Biomes.ICE_SPIKES);
	}

	private void generateStructureTags() {
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
