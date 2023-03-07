/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.additions.feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.frozenblock.lib.feature.FrozenFeatures;
import net.frozenblock.lib.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeature;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.block.SmallSpongeBlock;
import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.world.generation.features.config.LargeMesogleaConfig;
import net.frozenblock.wilderwild.world.generation.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.SmallSpongeFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

public final class WilderConfiguredFeatures {
	private WilderConfiguredFeatures() {
		throw new UnsupportedOperationException("WilderConfiguredFeatures contains only static declarations.");
	}

    //FALLEN TREES
    public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_TREES_MIXED = register("fallen_trees_mixed");

	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MOSSY_FALLEN_TREES_MIXED = register("mossy_fallen_trees_mixed");

	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MOSSY_FALLEN_TREES_OAK_AND_BIRCH = register("mossy_fallen_trees_oak_and_birch");

	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH_AND_SPRUCE = register("fallen_birch_and_spruce");

    public static final FrozenConfiguredFeature<?, ?> FALLEN_BIRCH =
            register("fallen_birch", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED, 1.0F)), WilderTreePlaced.FALLEN_BIRCH_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> FALLEN_SPRUCE =
            register("fallen_spruce", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 1.0F)), WilderTreePlaced.FALLEN_SPRUCE_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> FALLEN_SPRUCE_AND_OAK =
            register("fallen_spruce_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.55F)), WilderTreePlaced.FALLEN_OAK_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> FALLEN_BIRCH_AND_OAK =
            register("fallen_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED, 0.35F)), WilderTreePlaced.FALLEN_OAK_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> FALLEN_CYPRESS_AND_OAK =
            register("fallen_cypress_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_OAK_CHECKED, 0.35F)), WilderTreePlaced.FALLEN_CYPRESS_CHECKED));

    //TREES
    public static final FrozenConfiguredFeature<?, ?> TREES_PLAINS =
            register("trees_plains", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_OAK_BEES_0004.getHolder()), 0.33333334F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004.getHolder()), 0.035F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK.getHolder()), 0.169F)),
                            PlacementUtils.inlinePlaced(WilderTreeConfigured.OAK_BEES_0004.getHolder())));

	public static final FrozenConfiguredFeature<?, ?> TREES_FLOWER_FIELD =
			register("trees_flower_field", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_OAK_BEES_025.getHolder()), 0.577F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_DYING_OAK_BEES_025.getHolder()), 0.09F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.BIRCH_BEES_025.getHolder()), 0.1F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.BIG_SHRUB.getHolder()),0.23F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK.getHolder()), 0.169F)),
							PlacementUtils.inlinePlaced(WilderTreeConfigured.OAK_BEES_0004.getHolder())));

    public static final FrozenConfiguredFeature<?, ?> TREES_BIRCH_AND_OAK =
            register("trees_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.04F),
                            new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004, 0.26F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.055F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.04F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.155F)), WilderTreePlaced.OAK_BEES_0004));

	public static final FrozenConfiguredFeature<?, ?> TREES_SEMI_BIRCH_AND_OAK =
			register("trees_semi_birch_and_oak", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.2F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.04F),
							new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004, 0.06F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.025F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.04F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.13F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.14F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.04F),
							new WeightedPlacedFeature(WilderTreePlaced.SUPER_BIRCH, 0.1F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_BEES_0004, 0.025F)), WilderTreePlaced.OAK_BEES_0004));

    public static final FrozenConfiguredFeature<?, ?> TREES_BIRCH =
            register("trees_birch", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.065F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.012F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.035F)), WilderTreePlaced.BIRCH_BEES_0004));

    public static final FrozenConfiguredFeature<?, ?> TREES_BIRCH_TALL =
            register("trees_birch_tall", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.002F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.0001F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH, 0.032F),
                            new WeightedPlacedFeature(WilderTreePlaced.BIRCH_BEES_0004, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.017F)), WilderTreePlaced.SUPER_BIRCH_BEES_0004));

    public static final FrozenConfiguredFeature<?, ?> TREES_FLOWER_FOREST =
            register("trees_flower_forest", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.035F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.05F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.063F),
                            new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004, 0.205F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.095F)), WilderTreePlaced.OAK_BEES_0004));

    public static final FrozenConfiguredFeature<?, ?> MIXED_TREES =
            register("mixed_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED, 0.39F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.086F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.13F),
                            new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004, 0.27F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.025F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.23F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.325F)), WilderTreePlaced.OAK_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> TEMPERATE_RAINFOREST_TREES =
			register("temperate_rainforest_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.045F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.042F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.02F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.061F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.05F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.025F),
							new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.09F),
							new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.4F),
							new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED, 0.2F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.72F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_SPRUCE_CHECKED, 0.6F)), WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> RAINFOREST_TREES =
			register("rainforest_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED, 0.085F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.12F),
							new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004, 0.27F),
							new WeightedPlacedFeature(WilderTreePlaced.OLD_DYING_FANCY_OAK_BEES_0004, 0.15F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.072F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.120F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.098F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.37F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.21F)), WilderTreePlaced.DYING_OAK_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> BIRCH_TAIGA_TREES =
			register("birch_taiga_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED, 0.39F),
							new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.086F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.02F),
							new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.155F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.37F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.455F)), WilderTreePlaced.BIRCH_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> OLD_GROWTH_BIRCH_TAIGA_TREES =
			register("old_growth_birch_taiga_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED, 0.39F),
							new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.086F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.02F),
							new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.155F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH, 0.37F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.355F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.1F)), WilderTreePlaced.SUPER_BIRCH));

	public static final FrozenConfiguredFeature<?, ?> BIRCH_JUNGLE_TREES =
			register("birch_jungle_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.FANCY_OAK_CHECKED, 0.1F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.049F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.069F),
							new WeightedPlacedFeature(WilderTreePlaced.SUPER_BIRCH, 0.049F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH, 0.049F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.079F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.119F),
							new WeightedPlacedFeature(TreePlacements.JUNGLE_BUSH, 0.25F),
							new WeightedPlacedFeature(TreePlacements.MEGA_JUNGLE_TREE_CHECKED, 0.165F)), TreePlacements.JUNGLE_TREE_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> SPARSE_BIRCH_JUNGLE_TREES =
			register("sparse_birch_jungle_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.FANCY_OAK_CHECKED, 0.07F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.055F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.089F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH, 0.027F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.059F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.069F),
							new WeightedPlacedFeature(TreePlacements.JUNGLE_BUSH, 0.5F)),
							TreePlacements.JUNGLE_TREE_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> DARK_FOREST_VEGETATION =
            register("dark_forest_vegetation", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM), 0.025F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), 0.05F),
                            new WeightedPlacedFeature(TreePlacements.DARK_OAK_CHECKED, 0.55F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED, 0.075F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.015F),
                            new WeightedPlacedFeature(WilderTreePlaced.TALL_DARK_OAK_CHECKED, 0.35F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED, 0.048F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.012F),
                            new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED, 0.185F)), WilderTreePlaced.OAK_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> OLD_GROWTH_DARK_FOREST_VEGETATION =
			register("old_growth_dark_forest_vegetation", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM), 0.045F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), 0.07F),
							new WeightedPlacedFeature(TreePlacements.DARK_OAK_CHECKED, 0.55F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED, 0.255F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.1F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.04F),
							new WeightedPlacedFeature(WilderTreePlaced.TALL_DARK_OAK_CHECKED, 0.8F),
							new WeightedPlacedFeature(WilderTreePlaced.COBWEB_TALL_DARK_OAK_CHECKED, 0.021F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED, 0.222F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.095F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.045F),
							new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED, 0.24F)), WilderTreePlaced.OAK_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> DARK_BIRCH_FOREST_VEGETATION =
			register("dark_birch_forest_vegetation", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM), 0.025F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), 0.035F),
							new WeightedPlacedFeature(TreePlacements.DARK_OAK_CHECKED, 0.235F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED, 0.075F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.35F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.015F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.4F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.015F),
							new WeightedPlacedFeature(WilderTreePlaced.TALL_DARK_OAK_CHECKED, 0.15F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED, 0.048F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.02F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.012F),
							new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED, 0.15F)), WilderTreePlaced.OAK_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> TREES_TAIGA =
            register("trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.075F)), WilderTreePlaced.SPRUCE_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> SHORT_TREES_TAIGA =
            register("short_trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.33333334F)), WilderTreePlaced.SPRUCE_SHORT_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> SHORT_MEGA_SPRUCE =
			register("short_mega_spruce_configured", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_CHECKED, 0.43333334F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED, 0.125F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_SPRUCE_CHECKED, 0.125F)
					), WilderTreePlaced.SHORT_MEGA_SPRUCE_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> SHORT_MEGA_SPRUCE_ON_SNOW =
			register("short_mega_spruce_on_snow_configured", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW, 0.43333334F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW, 0.125F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_SPRUCE_ON_SNOW, 0.125F)
					), WilderTreePlaced.SHORT_MEGA_SPRUCE_ON_SNOW));

    public static final FrozenConfiguredFeature<?, ?> TREES_OLD_GROWTH_PINE_TAIGA =
            register("trees_old_growth_pine_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.025641026F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED, 0.028F),
                            new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED, 0.30769232F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.045F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.SPRUCE_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> TREES_OLD_GROWTH_SPRUCE_TAIGA =
            register("trees_old_growth_spruce_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.33333334F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.075F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.SPRUCE_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA =
			register("trees_old_growth_snowy_pine_taiga", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED, 0.33333334F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.075F),
							new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.0255F),
							new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.18333334F),
							new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.255F)), WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> TREES_GROVE =
            register("trees_grove", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_ON_SNOW, 0.33333334F)), WilderTreePlaced.SPRUCE_ON_SNOW));

    public static final FrozenConfiguredFeature<?, ?> TREES_WINDSWEPT_HILLS =
            register("trees_windswept_hills", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED, 0.666F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED, 0.1F)), WilderTreePlaced.OAK_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> MEADOW_TREES =
            register("meadow_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES, 0.5F)), WilderTreePlaced.SUPER_BIRCH_BEES));

    public static final FrozenConfiguredFeature<?, ?> SAVANNA_TREES =
            register("savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F),
                            new WeightedPlacedFeature(WilderTreePlaced.BAOBAB, 0.062F),
                            new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL, 0.035F)), WilderTreePlaced.OAK_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> WINDSWEPT_SAVANNA_TREES =
            register("windswept_savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F)), WilderTreePlaced.OAK_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> ARID_SAVANNA_TREES =
			register("arid_savanna_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F),
							new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED, 0.08F),
							new WeightedPlacedFeature(WilderTreePlaced.BAOBAB, 0.065F),
							new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED, 0.052F),
							new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL, 0.02F)), TreePlacements.ACACIA_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> PARCHED_FOREST_TREES =
			register("parched_forest_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.59F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.186F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.02F),
							new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED, 0.155F),
							new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.37F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.155F)), WilderTreePlaced.OAK_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> ARID_FOREST_TREES =
			register("arid_forest_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.7085F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.175F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.38F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.2325F)), WilderTreePlaced.DYING_OAK_CHECKED));

    public static final FrozenConfiguredFeature<?, ?> CYPRESS_WETLANDS_TREES =
            register("cypress_wetlands_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.37F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.25F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.81F),
                            new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED, 0.1F)), WilderTreePlaced.FUNGUS_CYPRESS));

    public static final FrozenConfiguredFeature<?, ?> CYPRESS_WETLANDS_TREES_SAPLING =
            register("cypress_wetlands_trees_sapling", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.4F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.15F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.81F)),
                            WilderTreePlaced.FUNGUS_CYPRESS));

    public static final FrozenConfiguredFeature<?, ?> CYPRESS_WETLANDS_TREES_WATER =
            register("cypress_wetlands_trees_water", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.1F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.85F)), WilderTreePlaced.FUNGUS_CYPRESS));

	public static final FrozenConfiguredFeature<?, ?> WOODED_BADLANDS_TREES =
			register("wooded_badlands_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED, 0.095F),
							new WeightedPlacedFeature(WilderTreePlaced.BIG_SHRUB_GRASS_CHECKED, 0.4F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.67F),
							new WeightedPlacedFeature(WilderTreePlaced.JUNIPER, 0.4F)), WilderTreePlaced.JUNIPER));

	public static final FrozenConfiguredFeature<?, ?> BIG_SHRUBS =
			register("big_shrubs", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.BIG_SHRUB_CHECKED, 1.0F)), WilderTreePlaced.BIG_SHRUB_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> PALMS =
			register("palms", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED, 0.1F), new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED, 0.4F)), WilderTreePlaced.PALM_CHECKED));

	public static final FrozenConfiguredFeature<?, ?> PALMS_JUNGLE =
			register("palms_jungle", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED_DIRT, 0.25F), new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED_DIRT, 0.7F), new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED_DIRT, 0.4F)), WilderTreePlaced.PALM_CHECKED_DIRT));

	public static final FrozenConfiguredFeature<?, ?> PALMS_OASIS =
			register("palms_oasis", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED, 0.5F), new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED, 0.1F), new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED, 0.37F)), WilderTreePlaced.PALM_CHECKED));

	//FLOWERS
    public static final FrozenConfiguredFeature<?, ?> SEEDING_DANDELION =
            register("seeding_dandelion", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.SEEDING_DANDELION)))));

    public static final FrozenConfiguredFeature<?, ?> CARNATION =
            register("carnation", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CARNATION)))));

    public static final FrozenConfiguredFeature<?, ?> DATURA =
            register("datura", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.DATURA)))));

    public static final FrozenConfiguredFeature<?, ?> FLOWER_PLAINS =
            register("flower_plain", Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION.defaultBlockState(), List.of(Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()), List.of(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState()))))));

    public static final FrozenConfiguredFeature<?, ?> MILKWEED =
            register("milkweed", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)))));

    public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1).build();
    public static final FrozenConfiguredFeature<?, ?> GLORY_OF_THE_SNOW =
            register("glory_of_the_snow", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(new WeightedStateProvider(GLORY_OF_THE_SNOW_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> FLOWER_FLOWER_FIELD =
			register("flower_flower_field", Feature.FLOWER, new RandomPatchConfiguration(100, 8, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new NoiseProvider(5050L, new NormalNoise.NoiseParameters(0, 1.0), 0.020833334F,
							List.of(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(),
									RegisterBlocks.CARNATION.defaultBlockState(),
									Blocks.DANDELION.defaultBlockState(),
									Blocks.POPPY.defaultBlockState(),
									Blocks.ALLIUM.defaultBlockState(),
									Blocks.AZURE_BLUET.defaultBlockState(),
									Blocks.RED_TULIP.defaultBlockState(),
									Blocks.ORANGE_TULIP.defaultBlockState(),
									Blocks.WHITE_TULIP.defaultBlockState(),
									Blocks.PINK_TULIP.defaultBlockState(),
									Blocks.OXEYE_DAISY.defaultBlockState(),
									Blocks.CORNFLOWER.defaultBlockState(),
									Blocks.LILY_OF_THE_VALLEY.defaultBlockState()))))));

	public static final FrozenConfiguredFeature<?, ?> MOSS_CARPET =
			register("moss_carpet", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(25, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MOSS_CARPET)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO))))));

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_TEMPERATE_RAINFOREST_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.MILKWEED.defaultBlockState(), 3)
			.add(RegisterBlocks.DATURA.defaultBlockState(), 5)
			.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 5)
			.add(Blocks.LILAC.defaultBlockState(), 6)
			.add(Blocks.DANDELION.defaultBlockState(), 10)
			.add(Blocks.BLUE_ORCHID.defaultBlockState(), 8)
			.add(Blocks.POPPY.defaultBlockState(), 10).build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_RAINFOREST_POOL = SimpleWeightedRandomList.<BlockState>builder()
			.add(RegisterBlocks.MILKWEED.defaultBlockState(), 5)
			.add(RegisterBlocks.DATURA.defaultBlockState(), 7)
			.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 7)
			.add(Blocks.LILAC.defaultBlockState(), 3)
			.add(Blocks.DANDELION.defaultBlockState(), 4)
			.add(Blocks.BLUE_ORCHID.defaultBlockState(), 3)
			.add(Blocks.POPPY.defaultBlockState(), 4)
			.add(RegisterBlocks.CARNATION.defaultBlockState(), 8).build();

	public static final FrozenConfiguredFeature<?, ?> FLOWERS_TEMPERATE_RAINFOREST =
			register("flowers_temperate_rainforest", Feature.FLOWER,
					FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_TEMPERATE_RAINFOREST_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> MUSHROOMS_DARK_FOREST =
			register("mushroom_dark_forest", Feature.FLOWER, new RandomPatchConfiguration(52, 8, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new NoiseProvider(5234L, new NormalNoise.NoiseParameters(0, 1.0), 0.020833334F,
							List.of(Blocks.RED_MUSHROOM.defaultBlockState(),
									Blocks.BROWN_MUSHROOM.defaultBlockState()))))));

	public static final FrozenConfiguredFeature<?, ?> FLOWERS_RAINFOREST =
			register("flowers_rainforest", Feature.FLOWER,
					FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_RAINFOREST_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> TALL_FLOWER_FLOWER_FIELD =
			register("tall_flower_flower_field", Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
					PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)))),
					PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)))),
					PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)))),
					PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)))))));

	//VEGETATION
	public static final SimpleWeightedRandomList<BlockState> OASIS_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 2).add(Blocks.GRASS.defaultBlockState(), 5).build();
	public static final SimpleWeightedRandomList<BlockState> OASIS_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.DEAD_BUSH.defaultBlockState(), 8).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 2).build();
	public static final SimpleWeightedRandomList<BlockState> DEAD_BUSH_AND_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.DEAD_BUSH.defaultBlockState(), 5).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 2).build();
	public static final SimpleWeightedRandomList<BlockState> BUSH_AND_DEAD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.DEAD_BUSH.defaultBlockState(), 2).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 2).build();

	public static final FrozenConfiguredFeature<?, ?> OASIS_GRASS =
			register("oasis_grass", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(35, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_GRASS_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> OASIS_BUSH =
			register("oasis_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(23, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_BUSH_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> DEAD_BUSH_AND_BUSH =
			register("dead_bush_and_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(4, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DEAD_BUSH_AND_BUSH_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> BUSH_AND_DEAD_BUSH =
			register("bush_and_dead_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(4, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(BUSH_AND_DEAD_BUSH_POOL)))));

	public static final SimpleWeightedRandomList<BlockState> FLOWER_FIELD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 2).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 5).build();

	public static final FrozenConfiguredFeature<?, ?> FLOWER_FIELD_BUSH =
			register("flower_field_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(18, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(FLOWER_FIELD_BUSH_POOL)))));

	public static final SimpleWeightedRandomList<BlockState> DESERT_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 4).build();

	public static final FrozenConfiguredFeature<?, ?> DESERT_BUSH =
			register("desert_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(8, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> BADLANDS_BUSH_SAND =
			register("badlands_bush_sand", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));

	public static final FrozenConfiguredFeature<?, ?> BADLANDS_BUSH_TERRACOTTA =
			register("badlands_bush_terracotta", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(6, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.SAND)))))));

	public static final FrozenConfiguredFeature<?, ?> WOODED_BADLANDS_BUSH_TERRACOTTA =
			register("wooded_badlands_bush_terracotta", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.SAND)))))));


	public static final FrozenConfiguredFeature<?, ?> PATCH_CACTUS_OASIS = register("patch_cactus_oasis", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(3, 5), BlockStateProvider.simple(Blocks.CACTUS)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));

	public static final FrozenConfiguredFeature<?, ?> PATCH_CACTUS_TALL = register("patch_cactus_tall", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(8, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(4, 5), BlockStateProvider.simple(Blocks.CACTUS)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));

	public static final FrozenConfiguredFeature<?, ?> PATCH_CACTUS_TALL_BADLANDS = register("patch_cactus_tall_badlands", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(12, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 6), BlockStateProvider.simple(Blocks.CACTUS)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));

	public static final SimpleWeightedRandomList<BlockState> PRICKLY_PEAR_POOL = SimpleWeightedRandomList.<BlockState>builder()
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 4)
			.add(Blocks.CACTUS.defaultBlockState(), 2).build();

	public static final FrozenConfiguredFeature<?, ?> PRICKLY_PEAR =
			register("prickly_pear", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(20, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(PRICKLY_PEAR_POOL)))));

	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 3).add(Blocks.LARGE_FERN.defaultBlockState(), 3).build();

	public static final FrozenConfiguredFeature<?, ?> LARGE_FERN_AND_GRASS =
            register("large_fern_and_grass", Feature.RANDOM_PATCH,
                    FeatureUtils.simpleRandomPatchConfiguration(36, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL)))));

	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL_2 = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 5).add(Blocks.LARGE_FERN.defaultBlockState(), 1).build();

	public static final FrozenConfiguredFeature<?, ?> LARGE_FERN_AND_GRASS_2 =
			register("large_fern_and_grass_2", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(36, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL_2)))));

	public static final SimpleWeightedRandomList<BlockState> GRASS_AND_FERN_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 1).build();

	public static final FrozenConfiguredFeature<?, ?> FERN_AND_GRASS =
			register("fern_and_grass", Feature.RANDOM_PATCH,
					new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(GRASS_AND_FERN_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> POLLEN_CONFIGURED =
            register("pollen", Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));

    public static final FrozenConfiguredFeature<?, ?> BROWN_SHELF_FUNGUS_CONFIGURED =
            register("brown_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));

    public static final FrozenConfiguredFeature<?, ?> RED_SHELF_FUNGUS_CONFIGURED =
            register("red_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));

    public static final FrozenConfiguredFeature<?, ?> CATTAIL =
            register("cattail", WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

	public static final FrozenConfiguredFeature<?, ?> CATTAIL_06 =
			register("cattail_06", WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.6F));

    public static final FrozenConfiguredFeature<?, ?> PATCH_FLOWERED_WATERLILY =
            register("patch_flowered_waterlily", Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD)))));

    public static final FrozenConfiguredFeature<?, ?> PATCH_ALGAE =
            register("patch_algae", WilderWild.ALGAE_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

    public static final FrozenConfiguredFeature<?, ?> TERMITE_CONFIGURED =
            register("termite_mound_baobab", FrozenFeatures.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true), UniformInt.of(4, 9), UniformInt.of(3, 7), UniformInt.of(1, 3), HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), HolderSet.direct(Block::builtInRegistryHolder, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));

	public static final SimpleWeightedRandomList<BlockState> TUMBLEWEED_PLANT_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1).build();

	public static final FrozenConfiguredFeature<?, ?> TUMBLEWEED =
			register("tumbleweed", Feature.FLOWER,
					FeatureUtils.simpleRandomPatchConfiguration(5, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(TUMBLEWEED_PLANT_POOL)))));

	public static final FrozenConfiguredFeature<?, ?> MESOGLEA_CLUSTER_PURPLE = register("mesoglea_cluster_purple",
			WilderWild.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
					30,
					UniformInt.of(3, 10), BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), UniformFloat.of(0.2F, 0.75F),
					0.15F, UniformFloat.of(0.1F, 0.25F),
					UniformFloat.of(0.16F, 0.4F), UniformFloat.of(0.0F, 0.25F), 5, 0.2F));

	public static final FrozenConfiguredFeature<?, ?> MESOGLEA_CLUSTER_BLUE = register("mesoglea_cluster_blue",
			WilderWild.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
					30,
					UniformInt.of(3, 10), BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), UniformFloat.of(0.2F, 0.75F),
					0.15F, UniformFloat.of(0.1F, 0.25F),
					UniformFloat.of(0.16F, 0.4F), UniformFloat.of(0.0F, 0.25F), 5, 0.2F));

    public static final FrozenConfiguredFeature<?, ?> BLUE_MESOGLEA = register(
            "mesoglea",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.04F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );

    public static final FrozenConfiguredFeature<?, ?> BLUE_MESOGLEA_POOL = register(
            "mesoglea_pool",
            Feature.WATERLOGGED_VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    5,
                    0.04F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );

    public static final FrozenConfiguredFeature<?, ?> JELLYFISH_CAVES_BLUE_MESOGLEA = register(
            "jellyfish_caves_blue_mesoglea",
            Feature.RANDOM_BOOLEAN_SELECTOR,
            new RandomBooleanFeatureConfiguration(
                    PlacementUtils.inlinePlaced(BLUE_MESOGLEA.getHolder()),
                    PlacementUtils.inlinePlaced(BLUE_MESOGLEA_POOL.getHolder())
            )
    );

    public static final FrozenConfiguredFeature<?, ?> UPSIDE_DOWN_BLUE_MESOGLEA = register(
            "upside_down_blue_mesoglea",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_MESOGLEA_PILLAR.getHolder()),
                    CaveSurface.CEILING,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.08F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );

    public static final FrozenConfiguredFeature<?, ?> PURPLE_MESOGLEA = register(
            "mesoglea_with_dripleaves",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.04F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );

    public static final FrozenConfiguredFeature<?, ?> PURPLE_MESOGLEA_POOL = register(
            "purple_mesoglea_pool",
            Feature.WATERLOGGED_VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    5,
                    0.04F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );

    public static final FrozenConfiguredFeature<?, ?> JELLYFISH_CAVES_PURPLE_MESOGLEA = register(
            "jellyfish_caves_purple_mesoglea",
            Feature.RANDOM_BOOLEAN_SELECTOR,
            new RandomBooleanFeatureConfiguration(
                    PlacementUtils.inlinePlaced(PURPLE_MESOGLEA.getHolder()),
                    PlacementUtils.inlinePlaced(PURPLE_MESOGLEA_POOL.getHolder())
            )
    );

    public static final FrozenConfiguredFeature<?, ?> UPSIDE_DOWN_PURPLE_MESOGLEA = register(
            "upside_down_purple_mesoglea",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_PURPLE_MESOGLEA_PILLAR.getHolder()),
                    CaveSurface.CEILING,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.08F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );

	public static final FrozenConfiguredFeature<?, ?> NEMATOCYST_BLUE = register("nematocyst_blue",
			WilderWild.NEMATOCYST_FEATURE,
			new MultifaceGrowthConfiguration(
					(MultifaceBlock) RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST,
					20,
					true, true, true, 0.98F,
					HolderSet.direct(
							Block::builtInRegistryHolder,
							Blocks.CLAY,
							Blocks.STONE,
							Blocks.ANDESITE,
							Blocks.DIORITE,
							Blocks.GRANITE,
							Blocks.DRIPSTONE_BLOCK,
							Blocks.CALCITE,
							Blocks.TUFF,
							Blocks.DEEPSLATE,
							RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA
					)
			)
	);

	public static final FrozenConfiguredFeature<?, ?> NEMATOCYST_PURPLE = register("nematocyst_purple",
			WilderWild.NEMATOCYST_FEATURE,
			new MultifaceGrowthConfiguration(
					(MultifaceBlock) RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST,
					20,
					true, true, true, 0.98F,
					HolderSet.direct(
							Block::builtInRegistryHolder,
							Blocks.CLAY,
							Blocks.STONE,
							Blocks.ANDESITE,
							Blocks.DIORITE,
							Blocks.GRANITE,
							Blocks.DRIPSTONE_BLOCK,
							Blocks.CALCITE,
							Blocks.TUFF,
							Blocks.DEEPSLATE,
							RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA
					)
			)
	);

	public static final FrozenConfiguredFeature<?, ?> LARGE_MESOGLEA_PURPLE = register("large_mesoglea_purple",
			WilderWild.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
					30,
					UniformInt.of(3, 19), BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), UniformFloat.of(0.2F, 2.0F),
					0.33F, UniformFloat.of(0.1F, 0.9F),
					UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0.0F, 0.3F), 4, 0.2F));

	public static final FrozenConfiguredFeature<?, ?> LARGE_MESOGLEA_BLUE = register("large_mesoglea_blue",
			WilderWild.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
					30,
					UniformInt.of(3, 19), BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)), UniformFloat.of(0.2F, 2.0F),
					0.33F, UniformFloat.of(0.1F, 0.9F),
					UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0.0F, 0.3F), 4, 0.2F));

	public static final FrozenConfiguredFeature<?, ?> SMALL_SPONGE =
			register("small_sponges", WilderWild.SMALL_SPONGE_FEATURE, new SmallSpongeFeatureConfig((SmallSpongeBlock) RegisterBlocks.SMALL_SPONGE, 20, true, true, true, WilderBlockTags.SMALL_SPONGE_GROWS_ON));

	static {
		registerConfiguredFeatures();
	}

	public static void init() {
		WilderSharedConstants.logWild("Registering WilderConfiguredFeatures for", true);
	}

	// TODO: Finish this for the rest of WilderConfiguredFeatures and then do it for the other feature classes
    public static void registerConfiguredFeatures() {
		FALLEN_TREES_MIXED.setHolder(makeHolder(FALLEN_TREES_MIXED, Feature.RANDOM_SELECTOR,
				new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.4F)),
						new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED, 0.3F)), WilderTreePlaced.FALLEN_OAK_CHECKED)));

		MOSSY_FALLEN_TREES_MIXED.setHolder(makeHolder(MOSSY_FALLEN_TREES_MIXED, Feature.RANDOM_SELECTOR,
				new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_SPRUCE_CHECKED, 0.15F)),
						new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED, 0.1F)), WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED)));

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH.setHolder(makeHolder(MOSSY_FALLEN_TREES_OAK_AND_BIRCH, Feature.RANDOM_SELECTOR,
				new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED, 0.15F)),
						new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED, 0.15F)), WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED)));

		FALLEN_BIRCH_AND_SPRUCE.setHolder(makeHolder(FALLEN_BIRCH_AND_SPRUCE, Feature.RANDOM_SELECTOR,
				new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.6F)),
						new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED, 0.4F)), WilderTreePlaced.FALLEN_SPRUCE_CHECKED)));
    }

    private static RandomPatchConfiguration createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block)));
    }

    public static FrozenConfiguredFeature<NoneFeatureConfiguration, ConfiguredFeature<NoneFeatureConfiguration, ?>> register(String id, Feature<NoneFeatureConfiguration> feature) {
        return register(id, feature, FeatureConfiguration.NONE);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> FrozenConfiguredFeature<FC, ConfiguredFeature<FC, ?>> register(@NotNull String id, F feature, @NotNull FC config) {
        var key = WilderSharedConstants.id(id);
		FrozenConfiguredFeature<FC, ConfiguredFeature<FC, ?>> frozen = new FrozenConfiguredFeature<>(key);
		var holder = FeatureUtils.register(key.toString(), feature, config);
		frozen.setHolder(holder);
		return frozen;
    }

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> FrozenConfiguredFeature<FC, ConfiguredFeature<FC, ?>> register(@NotNull String id) {
		var key = WilderSharedConstants.id(id);
		return new FrozenConfiguredFeature<>(key);
	}

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> makeHolder(FrozenConfiguredFeature<FC, ConfiguredFeature<FC, ?>> configuredFeature, @NotNull F feature, @NotNull FC config) {
		return FeatureUtils.register(configuredFeature.getKey().location().toString(), feature, config);
	}
}
