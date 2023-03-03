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

import java.util.OptionalInt;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.generation.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.foliage.ShortPalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.treedecorators.HeightBasedCobwebTreeDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.HeightBasedVineTreeDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.LeavesAroundTopLogDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.PollenTreeDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.ShelfFungusTreeDecorator;
import net.frozenblock.wilderwild.world.generation.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.generation.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.StraightTrunkWithLogs;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public final class WilderTreeConfigured {
	private WilderTreeConfigured() {
		throw new UnsupportedOperationException("WilderTreeConfigured contains only static declarations.");
	}


	public static final ShelfFungusTreeDecorator SHELF_FUNGUS_007 = new ShelfFungusTreeDecorator(0.074F, 0.3F);
	public static final ShelfFungusTreeDecorator SHELF_FUNGUS_006 = new ShelfFungusTreeDecorator(0.064F, 0.15F);
	public static final ShelfFungusTreeDecorator SHELF_FUNGUS_006_ONLY_BROWN = new ShelfFungusTreeDecorator(0.064F, 0.0F);
	public static final HeightBasedVineTreeDecorator VINES_012_UNDER_76 = new HeightBasedVineTreeDecorator(0.12F, 76, 0.25F);
	public static final HeightBasedVineTreeDecorator VINES_008_UNDER_82 = new HeightBasedVineTreeDecorator(0.08F, 82, 0.25F);
	public static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_03 = new HeightBasedVineTreeDecorator(1F, 260, 0.3F);
	public static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_05 = new HeightBasedVineTreeDecorator(1F, 260, 0.5F);
	public static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_075 = new HeightBasedVineTreeDecorator(1F, 260, 0.75F);
	public static final HeightBasedVineTreeDecorator VINES_08_UNDER_260_075 = new HeightBasedVineTreeDecorator(0.8F, 260, 0.75F);
	public static final HeightBasedCobwebTreeDecorator COBWEB_1_UNDER_260_025 = new HeightBasedCobwebTreeDecorator(1F, 260, 0.17F);
	public static final BeehiveDecorator NEW_BEES_0004 = new BeehiveDecorator(0.004F);
	public static final BeehiveDecorator NEW_BEES_025 = new BeehiveDecorator(0.25F);
	public static final BeehiveDecorator NEW_BEES = new BeehiveDecorator(1.0F);

	//BIRCH
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_TREE = key("birch_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_0004 = key("birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_025 = key("birch_bees_025");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_BIRCH = key("dying_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_BIRCH_BEES_0004 = key("short_birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES_0004 = key("super_birch_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_SUPER_BIRCH = key("dying_super_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_TREE = key("fallen_birch_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_FALLEN_BIRCH_TREE = key("mossy_fallen_birch_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_BIRCH = key("short_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_DYING_BIRCH = key("short_dying_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES = key("super_birch_bees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH = key("super_birch");

	//OAK
	public static final ResourceKey<ConfiguredFeature<?, ?>> OAK = key("oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_OAK = key("short_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BEES_0004 = key("oak_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_OAK = key("dying_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK = key("fancy_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DYING_OAK = key("fancy_dying_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DYING_OAK_BEES_0004 = key("fancy_dying_oak_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_0004 = key("fancy_oak_bees_0004");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DYING_OAK_BEES_025 = key("fancy_dying_oak_bees_025");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_025 = key("fancy_oak_bees_025");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_OAK_TREE = key("fallen_oak_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_FALLEN_OAK_TREE = key("mossy_fallen_oak_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES = key("fancy_oak_bees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_FANCY_DYING_OAK_BEES_0004 = key("old_fancy_dying_oak_bees_0004");

	//DARK OAK
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_DARK_OAK = key("dying_dark_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_DARK_OAK = key("tall_dark_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_TALL_DARK_OAK = key("dying_tall_dark_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COBWEB_TALL_DARK_OAK = key("cobweb_tall_dark_oak");

	//SWAMP TREE
	public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_TREE = key("swamp_tree");

	//SPRUCE
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE = key("spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_SHORT = key("spruce_short");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGUS_PINE = key("fungus_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_FUNGUS_PINE = key("dying_fungus_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FUNGUS_SPRUCE = key("mega_fungus_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FUNGUS_PINE = key("mega_fungus_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DYING_MEGA_FUNGUS_PINE = key("dying_mega_fungus_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE_TREE = key("fallen_spruce_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_FALLEN_SPRUCE_TREE = key("mossy_fallen_spruce_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_MEGA_SPRUCE = key("short_mega_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_MEGA_FUNGUS_SPRUCE = key("short_mega_fungus_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_MEGA_DYING_FUNGUS_SPRUCE = key("short_mega_dying_fungus_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_MEGA_DYING_SPRUCE = key("short_mega_dying_spruce");


	//BAOBAB
	public static final ResourceKey<ConfiguredFeature<?, ?>> BAOBAB = key("baobab");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BAOBAB_TALL = key("baobab_tall");

	//CYPRESS
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS = key("cypress");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_CYPRESS_TREE = key("fallen_cypress_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGUS_CYPRESS = key("fungus_cypress");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_CYPRESS = key("short_cypress");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_FUNGUS_CYPRESS = key("short_fungus_cypress");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_CYPRESS = key("swamp_cypress");

	//BIG SHRUB
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_SHRUB = key("big_shrub");

	//PALM
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALM = key("palm");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_PALM = key("tall_palm");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_WINE_PALM = key("small_wine_palm");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_WINE_PALM = key("tall_wine_palm");

	// JUNIPER
	public static final ResourceKey<ConfiguredFeature<?, ?>> JUNIPER = key("juniper");

	public static final BeehiveDecorator BEES_0004 = new BeehiveDecorator(0.004F);
	public static final BeehiveDecorator BEES_025 = new BeehiveDecorator(0.25F);
	public static final BeehiveDecorator BEES = new BeehiveDecorator(1.0F);
	public static final PollenTreeDecorator POLLEN_01 = new PollenTreeDecorator(0.1F, 0.025F, 3);
	public static final PollenTreeDecorator POLLEN_025 = new PollenTreeDecorator(0.25F, 0.025F, 5);
	public static final PollenTreeDecorator POLLEN = new PollenTreeDecorator(1.0F, 0.035F, 5);

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}

	public static TreeConfiguration.TreeConfigurationBuilder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkWithLogs(baseHeight, firstRandomHeight, secondRandomHeight, logChance, maxLogs, logHeightFromTop, extraBranchLength),
				BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
				new TwoLayersFeatureSize(1, 0, 1));
	}

	public static TreeConfiguration.TreeConfigurationBuilder fallenTrunkBuilder(Block log, Block leaves, int baseHeight, int firstRHeight, int secondRHeight, float logChance, float mossChance, IntProvider maxLogs, IntProvider maxHeightAboveHole, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new FallenTrunkWithLogs(baseHeight, firstRHeight, secondRHeight, logChance, mossChance, maxLogs, maxHeightAboveHole),
				BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3), //FOILAGE PLACER DOES NOTHING
				new TwoLayersFeatureSize(1, 0, 1));
	}

	public static TreeConfiguration.TreeConfigurationBuilder darkOakBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new DarkOakTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new DarkOakFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()));
	}

	public static TreeConfiguration.TreeConfigurationBuilder palmBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int minRad, int maxRad, int minFronds, int maxFronds) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new PalmTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new PalmFoliagePlacer(UniformInt.of(minRad, maxRad), ConstantInt.of(0), UniformInt.of(minFronds, maxFronds)),
				new TwoLayersFeatureSize(1, 0, 1));
	}

	public static TreeConfiguration.TreeConfigurationBuilder winePalmBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new ShortPalmFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 1));
	}

    public static TreeConfiguration.TreeConfigurationBuilder birch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 5, 4, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }

	public static TreeConfiguration.TreeConfigurationBuilder superBirch() {
		return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 6, 6, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

    public static TreeConfiguration.TreeConfigurationBuilder shortBirch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 6, 2, 2, 0.12F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }

	public static TreeConfiguration.TreeConfigurationBuilder fallenBirch() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_BIRCH_LOG, Blocks.BIRCH_LEAVES, 3, 1, 2, 0.4F, 0.47F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

    public static TreeConfiguration.TreeConfigurationBuilder oak() {
        return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 6, 2, 1, 0.1F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }


	public static TreeConfiguration.TreeConfigurationBuilder shortOak() {
		return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 1, 0, 0.095F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder fancyOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(5, 16, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
	}

    public static TreeConfiguration.TreeConfigurationBuilder fallenOak() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.4F, 0.4F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
    }

	public static TreeConfiguration.TreeConfigurationBuilder mossy_fallen_oak() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.55F, 0.877F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder fallenCypress() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_CYPRESS_LOG, RegisterBlocks.CYPRESS_LEAVES, 3, 1, 2, 0.4F, 0.6F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder tallDarkOak() {
		return darkOakBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 8, 3, 4, 1).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder fallenSpruce() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_SPRUCE_LOG, Blocks.SPRUCE_LEAVES, 5, 1, 2, 0.0F, 0.5F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}
}
