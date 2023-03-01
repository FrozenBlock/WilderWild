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

import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
<<<<<<< HEAD
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
=======
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.world.generation.features.config.LargeMesogleaConfig;
import net.frozenblock.wilderwild.world.generation.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.SmallSpongeFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.tags.BlockTags;
>>>>>>> dev
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class WilderConfiguredFeatures {
	private WilderConfiguredFeatures() {
		throw new UnsupportedOperationException("WilderConfiguredFeatures contains only static declarations.");
	}

<<<<<<< HEAD
	public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1).build();
	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 3).add(Blocks.LARGE_FERN.defaultBlockState(), 3).build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_TREES_MIXED = key("fallen_trees_mixed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH = key("fallen_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE = key("fallen_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE_AND_OAK = key("fallen_spruce_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_AND_OAK = key("fallen_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_CYPRESS_AND_OAK = key("fallen_cypress_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_AND_SPRUCE = key("fallen_birch_and_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PLAINS = key("trees_plains");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_AND_OAK = key("trees_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH = key("trees_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_TALL = key("trees_birch_tall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOWER_FOREST = key("trees_flower_forest");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_TREES = key("mixed_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_TAIGA_TREES = key("birch_taiga_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_FOREST_VEGETATION = key("dark_forest_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_TAIGA = key("trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_TREES_TAIGA = key("short_trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_PINE_TAIGA = key("trees_old_growth_pine_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA = key("trees_old_growth_spruce_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_GROVE = key("trees_grove");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WINDSWEPT_HILLS = key("trees_windswept_hills");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEADOW_TREES = key("meadow_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAVANNA_TREES = key("savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WINDSWEPT_SAVANNA_TREES = key("windswept_savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES = key("cypress_wetlands_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_SAPLING = key("cypress_wetlands_trees_sapling");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_WATER = key("cypress_wetlands_trees_water");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_SHRUBS = key("big_shrubs");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALMS = key("palms");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALMS_OASIS = key("palms_oasis");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SEEDING_DANDELION = key("seeding_dandelion");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CARNATION = key("carnation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DATURA = key("datura");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PLAINS = key("flower_plains");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MILKWEED = key("milkweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GLORY_OF_THE_SNOW = key("glory_of_the_snow");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOWER_FIELD = key("flower_field_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = key("old_growth_snowy_pine_taiga_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_ARID_SAVANNA = key("arid_savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PARCHED_FOREST = key("parched_forest_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_ARID_FOREST = key("arid_forest_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_FLOWER_FIELD = key("flower_field_flowers");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_FLOWER_FLOWER_FIELD = key("flower_field_tall_flowers");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SEMI_BIRCH_AND_OAK = key("trees_semi_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_GROWTH_BIRCH_TAIGA_TREES = key("old_growth_birch_taiga_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_JUNGLE_TREES = key("birch_jungle_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPARSE_BIRCH_JUNGLE_TREES = key("new_dark_forest_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEW_DARK_FOREST_VEGETATION = key("fallen_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_GROWTH_DARK_FOREST_VEGETATION = key("old_growth_dark_forest_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_BIRCH_FOREST_VEGETATION = key("dark_birch_forest_vegetation");

	//VEGETATION
=======
    //FALLEN TREES
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_TREES_MIXED =
            register("fallen_trees_mixed", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.4F)),
                            new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED, 0.3F)), WilderTreePlaced.FALLEN_OAK_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> MOSSY_FALLEN_TREES_MIXED =
			register("mossy_fallen_trees_mixed", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_SPRUCE_CHECKED, 0.15F)),
							new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED, 0.1F)), WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> MOSSY_FALLEN_TREES_OAK_AND_BIRCH =
			register("mossy_fallen_trees_oak_and_birch", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED, 0.15F)),
							new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED, 0.15F)), WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH_AND_SPRUCE =
			register("fallen_birch_and_spruce", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.6F)),
							new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED, 0.4F)), WilderTreePlaced.FALLEN_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH =
            register("fallen_birch", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED, 1.0F)), WilderTreePlaced.FALLEN_BIRCH_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SPRUCE =
            register("fallen_spruce", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 1.0F)), WilderTreePlaced.FALLEN_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SPRUCE_AND_OAK =
            register("fallen_spruce_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.55F)), WilderTreePlaced.FALLEN_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH_AND_OAK =
            register("fallen_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED, 0.35F)), WilderTreePlaced.FALLEN_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_CYPRESS_AND_OAK =
            register("fallen_cypress_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_OAK_CHECKED, 0.35F)), WilderTreePlaced.FALLEN_CYPRESS_CHECKED));

    //TREES
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_PLAINS =
            register("trees_plains", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_OAK_BEES_0004), 0.33333334F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004), 0.035F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK), 0.169F)),
                            PlacementUtils.inlinePlaced(WilderTreeConfigured.OAK_BEES_0004)));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_FLOWER_FIELD =
			register("trees_flower_field", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_OAK_BEES_025), 0.577F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_DYING_OAK_BEES_025), 0.09F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.BIRCH_BEES_025), 0.1F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.BIG_SHRUB),0.23F),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK), 0.169F)),
							PlacementUtils.inlinePlaced(WilderTreeConfigured.OAK_BEES_0004)));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH_AND_OAK =
            register("trees_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.04F),
                            new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004, 0.26F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.055F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.04F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.155F)), WilderTreePlaced.OAK_BEES_0004));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_SEMI_BIRCH_AND_OAK =
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

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH =
            register("trees_birch", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.065F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.012F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.035F)), WilderTreePlaced.BIRCH_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH_TALL =
            register("trees_birch_tall", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.002F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.0001F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH, 0.032F),
                            new WeightedPlacedFeature(WilderTreePlaced.BIRCH_BEES_0004, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.017F)), WilderTreePlaced.SUPER_BIRCH_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_FLOWER_FOREST =
            register("trees_flower_forest", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.035F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.05F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.063F),
                            new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004, 0.205F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.095F)), WilderTreePlaced.OAK_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> MIXED_TREES =
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

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TEMPERATE_RAINFOREST_TREES =
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

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> RAINFOREST_TREES =
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

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> BIRCH_TAIGA_TREES =
			register("birch_taiga_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED, 0.39F),
							new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.086F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.02F),
							new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.155F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.37F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.455F)), WilderTreePlaced.BIRCH_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> OLD_GROWTH_BIRCH_TAIGA_TREES =
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

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> BIRCH_JUNGLE_TREES =
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

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SPARSE_BIRCH_JUNGLE_TREES =
			register("sparse_birch_jungle_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.FANCY_OAK_CHECKED, 0.07F),
							new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED, 0.055F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.089F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH, 0.027F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.059F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.069F),
							new WeightedPlacedFeature(TreePlacements.JUNGLE_BUSH, 0.5F)),
							TreePlacements.JUNGLE_TREE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_FOREST_VEGETATION =
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

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> OLD_GROWTH_DARK_FOREST_VEGETATION =
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

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_BIRCH_FOREST_VEGETATION =
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

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_TAIGA =
            register("trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.075F)), WilderTreePlaced.SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SHORT_TREES_TAIGA =
            register("short_trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.33333334F)), WilderTreePlaced.SPRUCE_SHORT_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SHORT_MEGA_SPRUCE =
			register("short_mega_spruce_configured", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_CHECKED, 0.43333334F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED, 0.125F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_SPRUCE_CHECKED, 0.125F)
					), WilderTreePlaced.SHORT_MEGA_SPRUCE_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SHORT_MEGA_SPRUCE_ON_SNOW =
			register("short_mega_spruce_on_snow_configured", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW, 0.43333334F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW, 0.125F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_SPRUCE_ON_SNOW, 0.125F)
					), WilderTreePlaced.SHORT_MEGA_SPRUCE_ON_SNOW));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_PINE_TAIGA =
            register("trees_old_growth_pine_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.025641026F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED, 0.028F),
                            new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED, 0.30769232F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.045F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA =
            register("trees_old_growth_spruce_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.33333334F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.075F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.SPRUCE_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA =
			register("trees_old_growth_snowy_pine_taiga", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED, 0.33333334F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.075F),
							new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED, 0.0255F),
							new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.18333334F),
							new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.255F)), WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_GROVE =
            register("trees_grove", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_ON_SNOW, 0.33333334F)), WilderTreePlaced.SPRUCE_ON_SNOW));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_WINDSWEPT_HILLS =
            register("trees_windswept_hills", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED, 0.666F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED, 0.1F)), WilderTreePlaced.OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> MEADOW_TREES =
            register("meadow_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES, 0.5F)), WilderTreePlaced.SUPER_BIRCH_BEES));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SAVANNA_TREES =
            register("savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F),
                            new WeightedPlacedFeature(WilderTreePlaced.BAOBAB, 0.062F),
                            new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL, 0.035F)), WilderTreePlaced.OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> WINDSWEPT_SAVANNA_TREES =
            register("windswept_savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F)), WilderTreePlaced.OAK_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> ARID_SAVANNA_TREES =
			register("arid_savanna_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F),
							new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED, 0.08F),
							new WeightedPlacedFeature(WilderTreePlaced.BAOBAB, 0.065F),
							new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED, 0.052F),
							new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL, 0.02F)), TreePlacements.ACACIA_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> PARCHED_FOREST_TREES =
			register("parched_forest_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.59F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.186F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.02F),
							new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED, 0.155F),
							new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.37F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.01F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.155F)), WilderTreePlaced.OAK_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> ARID_FOREST_TREES =
			register("arid_forest_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.7085F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.175F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.38F),
							new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.2325F)), WilderTreePlaced.DYING_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES =
            register("cypress_wetlands_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.37F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.25F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.81F),
                            new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED, 0.1F)), WilderTreePlaced.FUNGUS_CYPRESS));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_SAPLING =
            register("cypress_wetlands_trees_sapling", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.4F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.15F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.81F)),
                            WilderTreePlaced.FUNGUS_CYPRESS));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_WATER =
            register("cypress_wetlands_trees_water", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.1F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.85F)), WilderTreePlaced.FUNGUS_CYPRESS));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> WOODED_BADLANDS_TREES =
			register("wooded_badlands_trees", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED, 0.095F),
							new WeightedPlacedFeature(WilderTreePlaced.BIG_SHRUB_GRASS_CHECKED, 0.4F),
							new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.67F),
							new WeightedPlacedFeature(WilderTreePlaced.JUNIPER, 0.4F)), WilderTreePlaced.JUNIPER));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> BIG_SHRUBS =
			register("big_shrubs", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.BIG_SHRUB_CHECKED, 1.0F)), WilderTreePlaced.BIG_SHRUB_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS =
			register("palms", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED, 0.1F), new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED, 0.4F)), WilderTreePlaced.PALM_CHECKED));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS_JUNGLE =
			register("palms_jungle", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED_DIRT, 0.25F), new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED_DIRT, 0.7F), new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED_DIRT, 0.4F)), WilderTreePlaced.PALM_CHECKED_DIRT));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS_OASIS =
			register("palms_oasis", Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED, 0.5F), new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED, 0.1F), new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED, 0.37F)), WilderTreePlaced.PALM_CHECKED));

	//FLOWERS
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SEEDING_DANDELION =
            register("seeding_dandelion", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.SEEDING_DANDELION)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CARNATION =
            register("carnation", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CARNATION)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DATURA =
            register("datura", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.DATURA)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PLAINS =
            register("flower_plain", Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION.defaultBlockState(), List.of(Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()), List.of(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState()))))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> MILKWEED =
            register("milkweed", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)))));

    public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1).build();
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> GLORY_OF_THE_SNOW =
            register("glory_of_the_snow", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(new WeightedStateProvider(GLORY_OF_THE_SNOW_POOL)))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_FLOWER_FIELD =
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

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> MOSS_CARPET =
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

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_TEMPERATE_RAINFOREST =
			register("flowers_temperate_rainforest", Feature.FLOWER,
					FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_TEMPERATE_RAINFOREST_POOL)))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> MUSHROOMS_DARK_FOREST =
			register("mushroom_dark_forest", Feature.FLOWER, new RandomPatchConfiguration(52, 8, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new NoiseProvider(5234L, new NormalNoise.NoiseParameters(0, 1.0), 0.020833334F,
							List.of(Blocks.RED_MUSHROOM.defaultBlockState(),
									Blocks.BROWN_MUSHROOM.defaultBlockState()))))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_RAINFOREST =
			register("flowers_rainforest", Feature.FLOWER,
					FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_RAINFOREST_POOL)))));

	public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> TALL_FLOWER_FLOWER_FIELD =
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

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> OASIS_GRASS =
			register("oasis_grass", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(35, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_GRASS_POOL)))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> OASIS_BUSH =
			register("oasis_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(23, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_BUSH_POOL)))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DEAD_BUSH_AND_BUSH =
			register("dead_bush_and_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(4, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DEAD_BUSH_AND_BUSH_POOL)))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BUSH_AND_DEAD_BUSH =
			register("bush_and_dead_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(4, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(BUSH_AND_DEAD_BUSH_POOL)))));

>>>>>>> dev
	public static final SimpleWeightedRandomList<BlockState> FLOWER_FIELD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 2).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 5).build();
	public static final SimpleWeightedRandomList<BlockState> DESERT_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 4).build();
<<<<<<< HEAD
	public static final SimpleWeightedRandomList<BlockState> OASIS_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.DEAD_BUSH.defaultBlockState(), 8).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 2).build();
	public static final SimpleWeightedRandomList<BlockState> OASIS_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 2).add(Blocks.GRASS.defaultBlockState(), 5).build();
=======

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DESERT_BUSH =
			register("desert_bush", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(8, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BADLANDS_BUSH_SAND =
			register("badlands_bush_sand", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> BADLANDS_BUSH_TERRACOTTA =
			register("badlands_bush_terracotta", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(6, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.SAND)))))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> WOODED_BADLANDS_BUSH_TERRACOTTA =
			register("wooded_badlands_bush_terracotta", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.SAND)))))));


	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_OASIS = register("patch_cactus_oasis", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(3, 5), BlockStateProvider.simple(Blocks.CACTUS)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_TALL = register("patch_cactus_tall", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(8, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(4, 5), BlockStateProvider.simple(Blocks.CACTUS)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_TALL_BADLANDS = register("patch_cactus_tall_badlands", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(12, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 6), BlockStateProvider.simple(Blocks.CACTUS)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO))))));

>>>>>>> dev
	public static final SimpleWeightedRandomList<BlockState> PRICKLY_PEAR_POOL = SimpleWeightedRandomList.<BlockState>builder()
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 4)
			.add(Blocks.CACTUS.defaultBlockState(), 2).build();
	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL_2 = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 5).add(Blocks.LARGE_FERN.defaultBlockState(), 1).build();
<<<<<<< HEAD
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_FERN_AND_GRASS_2 = key("large_fern_and_grass_2");
=======

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> LARGE_FERN_AND_GRASS_2 =
			register("large_fern_and_grass_2", Feature.RANDOM_PATCH,
					FeatureUtils.simpleRandomPatchConfiguration(36, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL_2)))));

	public static final SimpleWeightedRandomList<BlockState> GRASS_AND_FERN_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 1).build();

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FERN_AND_GRASS =
			register("fern_and_grass", Feature.RANDOM_PATCH,
					new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(GRASS_AND_FERN_POOL)))));

	public static final Holder<ConfiguredFeature<MultifaceGrowthConfiguration, ?>> POLLEN_CONFIGURED =
            register("pollen", Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));

    public static final Holder<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> BROWN_SHELF_FUNGUS_CONFIGURED =
            register("brown_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));

    public static final Holder<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> RED_SHELF_FUNGUS_CONFIGURED =
            register("red_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));

    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> CATTAIL =
            register("cattail", WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

	public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> CATTAIL_06 =
			register("cattail_06", WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.6F));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FLOWERED_WATERLILY =
            register("patch_flowered_waterlily", Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD)))));

    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> PATCH_ALGAE =
            register("patch_algae", WilderWild.ALGAE_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

    public static final Holder<ConfiguredFeature<ColumnWithDiskFeatureConfig, ?>> TERMITE_CONFIGURED =
            register("termite_mound_baobab", FrozenFeatures.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true), UniformInt.of(4, 9), UniformInt.of(3, 7), UniformInt.of(1, 3), HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), HolderSet.direct(Block::builtInRegistryHolder, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));

>>>>>>> dev
	public static final SimpleWeightedRandomList<BlockState> TUMBLEWEED_PLANT_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1).build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_TALL = key("patch_cactus_tall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_FERN_AND_GRASS = key("large_fern_and_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> POLLEN = key("pollen");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_SHELF_FUNGUS = key("brown_shelf_fungus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> RED_SHELF_FUNGUS = key("red_shelf_fungus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAIL = key("cattail");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOWERED_WATERLILY = key("patch_flowered_waterlily");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_ALGAE = key("patch_algae");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TERMITE = key("termite_mound_baobab");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS_GRASS = key("oasis_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS_BUSH = key("oasis_bush");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BUSH_FLOWER_FIELD = key("bush_flower_field");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DESERT_BUSH = key("desert_bush");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_OASIS = key("patch_cactus_oasis");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BADLANDS_BUSH_SAND = key("badlands_bush_sand");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BADLANDS_BUSH_TERRACOTTA = key("badlands_bush_terracotta");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_TALL_BADLANDS = key("patch_cactus_tall_badlands");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PRICKLY_PEAR = key("prickly_pear");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TUMBLEWEED = key("tumbleweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA = key("mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA_POOL = key("mesoglea_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYFISH_CAVES_BLUE_MESOGLEA = key("jellyfish_caves_blue_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPSIDE_DOWN_BLUE_MESOGLEA = key("upside_down_blue_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA = key("mesoglea_with_dripleaves");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA_POOL = key("purple_mesoglea_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYFISH_CAVES_PURPLE_MESOGLEA = key("jellyfish_caves_purple_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPSIDE_DOWN_PURPLE_MESOGLEA = key("upside_down_purple_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEMATOCYST = key("nematocyst");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEMATOCYST_PURPLE = key("nematocyst_purple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MESOGLEA_CLUSTER_PURPLE = key("mesoglea_cluster_purple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MESOGLEA_CLUSTER_BLUE = key("mesoglea_cluster_blue");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_MESOGLEA_PURPLE = key("large_mesoglea_purple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_MESOGLEA_BLUE = key("large_mesoglea_blue");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_SPONGE = key("small_sponges");

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}

}
