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

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeature;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.generation.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.foliage.ShortPalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.treedecorators.HeightBasedCobwebTreeDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.HeightBasedVineTreeDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.LeavesAroundTopLogDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.PollenTreeDecorator;
import net.frozenblock.wilderwild.world.generation.treedecorators.ShelfFungusTreeDecorator;
import net.frozenblock.wilderwild.world.generation.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.generation.trunk.JuniperTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.StraightTrunkWithLogs;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;


public final class WilderTreeConfigured {
	private WilderTreeConfigured() {
		throw new UnsupportedOperationException("WilderTreeConfigured contains only static declarations.");
	}

    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_007 = new ShelfFungusTreeDecorator(0.074F, 0.3F);
    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_006 = new ShelfFungusTreeDecorator(0.064F, 0.15F);
    private static final ShelfFungusTreeDecorator SHELF_FUNGUS_006_ONLY_BROWN = new ShelfFungusTreeDecorator(0.064F, 0.0F);
    private static final HeightBasedVineTreeDecorator VINES_012_UNDER_76 = new HeightBasedVineTreeDecorator(0.12F, 76, 0.25F);
    private static final HeightBasedVineTreeDecorator VINES_008_UNDER_82 = new HeightBasedVineTreeDecorator(0.08F, 82, 0.25F);
    private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_03 = new HeightBasedVineTreeDecorator(1F, 260, 0.3F);
    private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_05 = new HeightBasedVineTreeDecorator(1F, 260, 0.5F);
    private static final HeightBasedVineTreeDecorator VINES_1_UNDER_260_075 = new HeightBasedVineTreeDecorator(1F, 260, 0.75F);
    private static final HeightBasedVineTreeDecorator VINES_08_UNDER_260_075 = new HeightBasedVineTreeDecorator(0.8F, 260, 0.75F);
	private static final HeightBasedCobwebTreeDecorator COBWEB_1_UNDER_260_025 = new HeightBasedCobwebTreeDecorator(1F, 260, 0.17F);
    private static final BeehiveDecorator BEES_0004 = new BeehiveDecorator(0.004F);
	private static final BeehiveDecorator BEES_025 = new BeehiveDecorator(0.25F);
    private static final BeehiveDecorator BEES = new BeehiveDecorator(1.0F);
	private static final PollenTreeDecorator POLLEN_01 = new PollenTreeDecorator(0.1F, 0.025F, 3);
	private static final PollenTreeDecorator POLLEN_025 = new PollenTreeDecorator(0.25F, 0.025F, 5);
	private static final PollenTreeDecorator POLLEN = new PollenTreeDecorator(1.0F, 0.035F, 5);
    //BIRCH
    public static final FrozenConfiguredFeature BIRCH_TREE = WilderConfiguredFeatures.register("birch_tree", Feature.TREE, birch().dirt(BlockStateProvider.simple(Blocks.DIRT)).decorators(ImmutableList.of(SHELF_FUNGUS_007)).build());
    public static final FrozenConfiguredFeature BIRCH_BEES_0004 = WilderConfiguredFeatures.register("birch_bees_0004", Feature.TREE, birch().decorators(ImmutableList.of(BEES_0004, SHELF_FUNGUS_007, POLLEN_01)).ignoreVines().build());
	public static final FrozenConfiguredFeature BIRCH_BEES_025 = WilderConfiguredFeatures.register("birch_bees_025", Feature.TREE, birch().decorators(ImmutableList.of(BEES_025, SHELF_FUNGUS_007, POLLEN_025)).ignoreVines().build());
	public static final FrozenConfiguredFeature DYING_BIRCH = WilderConfiguredFeatures.register("dying_birch", Feature.TREE, birch().decorators(ImmutableList.of(BEES_0004, SHELF_FUNGUS_007)).ignoreVines().build());
    public static final FrozenConfiguredFeature SHORT_BIRCH_BEES_0004 = WilderConfiguredFeatures.register("short_birch_bees_0004", Feature.TREE, shortBirch().decorators(ImmutableList.of(BEES_0004, SHELF_FUNGUS_006, POLLEN_01)).ignoreVines().build());
    public static final FrozenConfiguredFeature SUPER_BIRCH_BEES_0004 = WilderConfiguredFeatures.register("super_birch_bees_0004", Feature.TREE, superBirch().decorators(ImmutableList.of(BEES_0004, SHELF_FUNGUS_007, POLLEN_01)).build());
    public static final FrozenConfiguredFeature DYING_SUPER_BIRCH = WilderConfiguredFeatures.register("dying_super_birch", Feature.TREE, superBirch().decorators(ImmutableList.of(VINES_1_UNDER_260_05, SHELF_FUNGUS_007)).build());
    public static final FrozenConfiguredFeature FALLEN_BIRCH_TREE = WilderConfiguredFeatures.register("fallen_birch_tree", Feature.TREE, fallen_birch().decorators(List.of(VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
	public static final FrozenConfiguredFeature MOSSY_FALLEN_BIRCH_TREE = WilderConfiguredFeatures.register("mossy_fallen_birch_tree", Feature.TREE, fallenTrunkBuilder(RegisterBlocks.HOLLOWED_BIRCH_LOG, Blocks.BIRCH_LEAVES, 3, 1, 2, 0.55F, 1.0F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines().decorators(List.of(VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
	public static final FrozenConfiguredFeature SHORT_BIRCH = WilderConfiguredFeatures.register("short_birch", Feature.TREE, shortBirch().decorators(ImmutableList.of(SHELF_FUNGUS_006)).ignoreVines().build());
    public static final FrozenConfiguredFeature SHORT_DYING_BIRCH = WilderConfiguredFeatures.register("short_dying_birch", Feature.TREE, shortBirch().decorators(ImmutableList.of(SHELF_FUNGUS_006, VINES_1_UNDER_260_03)).ignoreVines().build());
    public static final FrozenConfiguredFeature SUPER_BIRCH_BEES = WilderConfiguredFeatures.register("super_birch_bees", Feature.TREE, superBirch().decorators(ImmutableList.of(BEES, POLLEN_025)).build());
	public static final FrozenConfiguredFeature SUPER_BIRCH = WilderConfiguredFeatures.register("super_birch", Feature.TREE, superBirch().build());
    //OAK
    public static final FrozenConfiguredFeature OAK = WilderConfiguredFeatures.register("oak", Feature.TREE, oak().build());
    public static final FrozenConfiguredFeature SHORT_OAK = WilderConfiguredFeatures.register("short_oak", Feature.TREE, short_oak().build());
    public static final FrozenConfiguredFeature OAK_BEES_0004 = WilderConfiguredFeatures.register("oak_bees_0004", Feature.TREE, oak().decorators(ImmutableList.of(BEES_0004, SHELF_FUNGUS_006, POLLEN_01)).ignoreVines().build());
    public static final FrozenConfiguredFeature DYING_OAK = WilderConfiguredFeatures.register("dying_oak", Feature.TREE, oak().decorators(ImmutableList.of(VINES_1_UNDER_260_03, SHELF_FUNGUS_006)).ignoreVines().build());
    public static final FrozenConfiguredFeature FANCY_OAK = WilderConfiguredFeatures.register("fancy_oak", Feature.TREE, fancyOak().build());
    public static final FrozenConfiguredFeature FANCY_DYING_OAK = WilderConfiguredFeatures.register("fancy_dying_oak", Feature.TREE, fancyOak().decorators(List.of(VINES_1_UNDER_260_05)).build());
    public static final FrozenConfiguredFeature FANCY_DYING_OAK_BEES_0004 = WilderConfiguredFeatures.register("fancy_dying_oak_bees_0004", Feature.TREE, fancyOak().decorators(List.of(BEES_0004, VINES_1_UNDER_260_05, POLLEN_01)).build());
    public static final FrozenConfiguredFeature FANCY_OAK_BEES_0004 = WilderConfiguredFeatures.register("fancy_oak_bees_0004", Feature.TREE, fancyOak().decorators(List.of(BEES_0004, POLLEN_01)).build());
	public static final FrozenConfiguredFeature FANCY_DYING_OAK_BEES_025 = WilderConfiguredFeatures.register("fancy_dying_oak_bees_025", Feature.TREE, fancyOak().decorators(List.of(BEES_025, VINES_1_UNDER_260_05, POLLEN_01)).build());
	public static final FrozenConfiguredFeature FANCY_OAK_BEES_025 = WilderConfiguredFeatures.register("fancy_oak_bees_025", Feature.TREE, fancyOak().decorators(List.of(BEES_025, POLLEN_01)).build());
	public static final FrozenConfiguredFeature FALLEN_OAK_TREE = WilderConfiguredFeatures.register("fallen_oak_tree", Feature.TREE, fallen_oak().decorators(List.of(VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
	public static final FrozenConfiguredFeature MOSSY_FALLEN_OAK_TREE = WilderConfiguredFeatures.register("mossy_fallen_oak_tree", Feature.TREE, fallenTrunkBuilder(RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.55F, 1.0F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines().decorators(List.of(VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
	public static final FrozenConfiguredFeature FANCY_OAK_BEES = WilderConfiguredFeatures.register("fancy_oak_bees", Feature.TREE, fancyOak().decorators(List.of(BEES, POLLEN_025)).build());
	public static final FrozenConfiguredFeature OLD_FANCY_DYING_OAK_BEES_0004 = WilderConfiguredFeatures.register("old_fancy_dying_oak_bees_0004", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(5, 12, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().decorators(List.of(BEES_0004, VINES_1_UNDER_260_05, POLLEN_01)).build());
	//DARK OAK
    public static final FrozenConfiguredFeature DYING_DARK_OAK = WilderConfiguredFeatures.register("dying_dark_oak", Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG), new DarkOakTrunkPlacer(6, 2, 1), BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES),
                    new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())).decorators(List.of(VINES_1_UNDER_260_05)).ignoreVines().build());
    public static final FrozenConfiguredFeature TALL_DARK_OAK = WilderConfiguredFeatures.register("tall_dark_oak", Feature.TREE, tallDarkOak().ignoreVines().build());
    public static final FrozenConfiguredFeature DYING_TALL_DARK_OAK = WilderConfiguredFeatures.register("dying_tall_dark_oak", Feature.TREE, tallDarkOak().decorators(List.of(VINES_1_UNDER_260_05)).ignoreVines().build());
	public static final FrozenConfiguredFeature COBWEB_TALL_DARK_OAK = WilderConfiguredFeatures.register("cobweb_tall_dark_oak", Feature.TREE, tallDarkOak().decorators(List.of(COBWEB_1_UNDER_260_025)).ignoreVines().build());

	//SWAMP TREE
    public static final FrozenConfiguredFeature SWAMP_TREE = WilderConfiguredFeatures.register("swamp_tree", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.MANGROVE_LOG),
            new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.simple(Blocks.MANGROVE_LEAVES),
            new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3), Optional.of(new MangroveRootPlacer(UniformInt.of(1, 1), BlockStateProvider.simple(Blocks.MANGROVE_ROOTS), Optional.of(new AboveRootPlacement(BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.45F)),
            new MangroveRootPlacement(Registry.BLOCK.getOrCreateTag(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), HolderSet.direct(Block::builtInRegistryHolder, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS), BlockStateProvider.simple(Blocks.MUDDY_MANGROVE_ROOTS), 8, 15, 0.2F))),
            new TwoLayersFeatureSize(2, 0, 2))
			.decorators(List.of(
							new LeaveVineDecorator(0.125F),
							new AttachedToLeavesDecorator(
									0.12F,
									1,
									0,
									new RandomizedIntStateProvider(BlockStateProvider.simple(
											Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue(MangrovePropaguleBlock.HANGING, true)
									),
									MangrovePropaguleBlock.AGE,
									UniformInt.of(0, 4)),
									2,
									List.of(Direction.DOWN)
							)
			)).ignoreVines().dirt(BlockStateProvider.simple(Blocks.MANGROVE_ROOTS)).build()
	));

    //SPRUCE
    public static final FrozenConfiguredFeature SPRUCE = WilderConfiguredFeatures.register("spruce", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(8, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(2, 3)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN)).ignoreVines().build());
    public static final FrozenConfiguredFeature SPRUCE_SHORT = WilderConfiguredFeatures.register("spruce_short", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(3, 1, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformInt.of(1, 2), UniformInt.of(0, 2), UniformInt.of(2, 3)), new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build());
    public static final FrozenConfiguredFeature FUNGUS_PINE = WilderConfiguredFeatures.register("fungus_pine", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(6, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN)).ignoreVines().build());
    public static final FrozenConfiguredFeature DYING_FUNGUS_PINE = WilderConfiguredFeatures.register("dying_fungus_pine", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StraightTrunkPlacer(6, 4, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)), new TwoLayersFeatureSize(2, 0, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, VINES_1_UNDER_260_05)).ignoreVines().build());
    public static final FrozenConfiguredFeature MEGA_FUNGUS_SPRUCE = WilderConfiguredFeatures.register("mega_fungus_spruce", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), SHELF_FUNGUS_006_ONLY_BROWN)).build());
    public static final FrozenConfiguredFeature MEGA_FUNGUS_PINE = WilderConfiguredFeatures.register("mega_fungus_pine", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), SHELF_FUNGUS_006_ONLY_BROWN)).build());
    public static final FrozenConfiguredFeature DYING_MEGA_FUNGUS_PINE = WilderConfiguredFeatures.register("dying_mega_fungus_pine", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)), SHELF_FUNGUS_006_ONLY_BROWN, VINES_1_UNDER_260_075)).build());
    public static final FrozenConfiguredFeature FALLEN_SPRUCE_TREE = WilderConfiguredFeatures.register("fallen_spruce_tree", Feature.TREE, fallen_spruce().decorators(List.of(VINES_1_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
	public static final FrozenConfiguredFeature MOSSY_FALLEN_SPRUCE_TREE = WilderConfiguredFeatures.register("mossy_fallen_spruce_tree", Feature.TREE, fallenTrunkBuilder(RegisterBlocks.HOLLOWED_SPRUCE_LOG, Blocks.SPRUCE_LEAVES, 5, 1, 2, 0.0F, 1.0F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines().decorators(List.of(VINES_08_UNDER_260_075)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
	public static final FrozenConfiguredFeature SHORT_MEGA_SPRUCE = WilderConfiguredFeatures.register("short_mega_spruce", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(12, 2, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN)).build());
	public static final FrozenConfiguredFeature SHORT_MEGA_FUNGUS_SPRUCE = WilderConfiguredFeatures.register("short_mega_fungus_spruce", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(12, 2, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, SHELF_FUNGUS_006_ONLY_BROWN)).build());
	public static final FrozenConfiguredFeature SHORT_MEGA_DYING_FUNGUS_SPRUCE = WilderConfiguredFeatures.register("short_mega_dying_fungus_spruce", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(12, 2, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, SHELF_FUNGUS_006_ONLY_BROWN, VINES_1_UNDER_260_075)).build());
	public static final FrozenConfiguredFeature SHORT_MEGA_DYING_SPRUCE = WilderConfiguredFeatures.register("short_mega_dying_spruce", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new GiantTrunkPlacer(12, 2, 2), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(11, 14)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, VINES_1_UNDER_260_075)).build());

	//BAOBAB
    public static final FrozenConfiguredFeature BAOBAB = WilderConfiguredFeatures.register("baobab", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.BAOBAB_LOG), new BaobabTrunkPlacer(13, 3, 2, BlockStateProvider.simple(RegisterBlocks.STRIPPED_BAOBAB_LOG)), BlockStateProvider.simple(RegisterBlocks.BAOBAB_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2)).decorators(List.of(new AttachedToLeavesDecorator(0.065F, 1, 0,
            new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)), BaobabNutBlock.AGE, UniformInt.of(0, 2)), 4, List.of(Direction.DOWN)))).ignoreVines().build());
    public static final FrozenConfiguredFeature BAOBAB_TALL = WilderConfiguredFeatures.register("baobab_tall", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.BAOBAB_LOG), new BaobabTrunkPlacer(16, 4, 2, BlockStateProvider.simple(RegisterBlocks.STRIPPED_BAOBAB_LOG)), BlockStateProvider.simple(RegisterBlocks.BAOBAB_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2)).decorators(List.of(new AttachedToLeavesDecorator(0.065F, 1, 0,
            new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)), BaobabNutBlock.AGE, UniformInt.of(0, 2)), 4, List.of(Direction.DOWN)))).ignoreVines().build());
    //CYPRESS
    public static final FrozenConfiguredFeature CYPRESS = WilderConfiguredFeatures.register("cypress", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(6, 2, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(VINES_012_UNDER_76)).ignoreVines().build());
    public static final FrozenConfiguredFeature FALLEN_CYPRESS_TREE = WilderConfiguredFeatures.register("fallen_cypress_tree", Feature.TREE, fallen_cypress().decorators(List.of(VINES_008_UNDER_82)).dirt(BlockStateProvider.simple(Blocks.DIRT)).build());
    public static final FrozenConfiguredFeature FUNGUS_CYPRESS = WilderConfiguredFeatures.register("fungus_cypress", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(8, 4, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, VINES_008_UNDER_82)).ignoreVines().build());
    public static final FrozenConfiguredFeature SHORT_CYPRESS = WilderConfiguredFeatures.register("short_cypress", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(3, 2, 3), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(4, 6)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(VINES_012_UNDER_76)).ignoreVines().build());
    public static final FrozenConfiguredFeature SHORT_FUNGUS_CYPRESS = WilderConfiguredFeatures.register("short_fungus_cypress", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new StraightTrunkPlacer(4, 3, 1), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new SpruceFoliagePlacer(ConstantInt.of(1), UniformInt.of(1, 3), UniformInt.of(6, 8)), new TwoLayersFeatureSize(2, 1, 2)).decorators(ImmutableList.of(SHELF_FUNGUS_006_ONLY_BROWN, VINES_008_UNDER_82)).ignoreVines().build());
    public static final FrozenConfiguredFeature SWAMP_CYPRESS = WilderConfiguredFeatures.register("swamp_cypress", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new UpwardsBranchingTrunkPlacer(15, 5, 2, UniformInt.of(4, 5), 0.2F, UniformInt.of(1, 3), Registry.BLOCK.getOrCreateTag(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2), 14), new TwoLayersFeatureSize(1, 0, 1))).decorators(ImmutableList.of(new LeaveVineDecorator(0.1F), SHELF_FUNGUS_006_ONLY_BROWN, VINES_008_UNDER_82)).build());
	//BIG SHRUB
	public static final FrozenConfiguredFeature BIG_SHRUB = WilderConfiguredFeatures.register("big_shrub", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2), new TwoLayersFeatureSize(0, 0, 0))).dirt(BlockStateProvider.simple(Blocks.COARSE_DIRT)).build());
	//PALM
	public static final FrozenConfiguredFeature PALM = WilderConfiguredFeatures.register("palm", Feature.TREE, palmBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.PALM_LEAVES, 6, 2, 1, 1, 3, 4, 9)
			.decorators(List.of(new LeavesAroundTopLogDecorator(0.25F, 0, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)), CoconutBlock.AGE, ConstantInt.of(0)), 4, List.of(Direction.DOWN)))).build());

	public static final FrozenConfiguredFeature TALL_PALM = WilderConfiguredFeatures.register("tall_palm", Feature.TREE, palmBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.PALM_LEAVES, 8, 3, 2, 1, 3, 5, 10)
			.decorators(List.of(new LeavesAroundTopLogDecorator(0.25F, 0, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)), CoconutBlock.AGE, ConstantInt.of(0)), 4, List.of(Direction.DOWN)))).build());

	public static final FrozenConfiguredFeature SMALL_WINE_PALM = WilderConfiguredFeatures.register("small_wine_palm", Feature.TREE, winePalmBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.PALM_LEAVES, 5, 1, 2, 2).build());

	public static final FrozenConfiguredFeature TALL_WINE_PALM = WilderConfiguredFeatures.register("tall_wine_palm", Feature.TREE, winePalmBuilder(RegisterBlocks.PALM_LOG, RegisterBlocks.PALM_LEAVES, 10, 3, 3, 2)
			.decorators(List.of(new LeavesAroundTopLogDecorator(0.3F, 0, 0, new RandomizedIntStateProvider(BlockStateProvider.simple(RegisterBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true)), CoconutBlock.AGE, ConstantInt.of(0)), 4, List.of(Direction.DOWN)))).build());
	//JUNIPER
	public static final FrozenConfiguredFeature JUNIPER = WilderConfiguredFeatures.register("juniper", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegisterBlocks.CYPRESS_LOG), new JuniperTrunkPlacer(2,1,1, UniformInt.of(1, 3), UniformInt.of(2, 4), UniformInt.of(-8, -5), UniformInt.of(-3, 2)), BlockStateProvider.simple(RegisterBlocks.CYPRESS_LEAVES), new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(2), ConstantInt.of(2), 32), new TwoLayersFeatureSize(1, 0, 2))).build());


	private static TreeConfiguration.TreeConfigurationBuilder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength, int radius) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkWithLogs(baseHeight, firstRandomHeight, secondRandomHeight, logChance, maxLogs, logHeightFromTop, extraBranchLength),
                BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder fallenTrunkBuilder(Block log, Block leaves, int baseHeight, int firstRHeight, int secondRHeight, float logChance, float mossChance, IntProvider maxLogs, IntProvider maxHeightAboveHole, int radius) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new FallenTrunkWithLogs(baseHeight, firstRHeight, secondRHeight, logChance, mossChance, maxLogs, maxHeightAboveHole),
                BlockStateProvider.simple(leaves), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3), //FOILAGE PLACER DOES NOTHING
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder darkOakBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new DarkOakTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
                BlockStateProvider.simple(leaves), new DarkOakFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
                new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()));
    }

	private static TreeConfiguration.TreeConfigurationBuilder palmBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int minRad, int maxRad, int minFronds, int maxFronds) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new PalmTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new PalmFoliagePlacer(UniformInt.of(minRad, maxRad), ConstantInt.of(0), UniformInt.of(minFronds, maxFronds)),
				new TwoLayersFeatureSize(1, 0, 1));
	}

	private static TreeConfiguration.TreeConfigurationBuilder winePalmBuilder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log), new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight),
				BlockStateProvider.simple(leaves), new ShortPalmFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 1));
	}

    private static TreeConfiguration.TreeConfigurationBuilder birch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 5, 4, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder superBirch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 8, 6, 6, 0.15F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder shortBirch() {
        return builder(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 6, 2, 2, 0.12F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder fallen_birch() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_BIRCH_LOG, Blocks.BIRCH_LEAVES, 3, 1, 2, 0.4F, 0.47F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder oak() {
        return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 6, 2, 1, 0.1F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder short_oak() {
        return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 1, 0, 0.095F, UniformInt.of(1, 2), UniformInt.of(1, 3), ConstantInt.of(1), 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder fancyOak() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(5, 16, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder fallen_oak() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.4F, 0.4F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
    }
	private static TreeConfiguration.TreeConfigurationBuilder mossy_fallen_oak() {
		return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.OAK_LEAVES, 3, 1, 2, 0.55F, 0.877F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
	}

    private static TreeConfiguration.TreeConfigurationBuilder fallen_cypress() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_CYPRESS_LOG, RegisterBlocks.CYPRESS_LEAVES, 3, 1, 2, 0.4F, 0.6F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder tallDarkOak() {
        return darkOakBuilder(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, 8, 3, 4, 1).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder fallen_spruce() {
        return fallenTrunkBuilder(RegisterBlocks.HOLLOWED_SPRUCE_LOG, Blocks.SPRUCE_LEAVES, 5, 1, 2, 0.0F, 0.5F, UniformInt.of(1, 2), UniformInt.of(1, 2), 1).ignoreVines();
    }

    public static void registerTreeConfigured() {
        WilderSharedConstants.logWild("Registering WilderTreeConfigured for", true);
    }
}
