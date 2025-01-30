/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.worldgen.impl.features.AlgaeFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.CattailFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.HugePaleMushroomFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.LargeMesogleaFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.NematocystFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.PalmTreeFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.ShelfFungiFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.SnowAndFreezeDiskFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.SnowBlanketFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.SpongeBudFeature;
import net.frozenblock.wilderwild.worldgen.impl.features.config.AlgaeFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.features.config.CattailFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.features.config.LargeMesogleaConfig;
import net.frozenblock.wilderwild.worldgen.impl.features.config.ShelfFungiFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.features.config.SnowAndIceDiskFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.features.config.SpongeBudFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.foliage.MapleFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.NoOpFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.RoundMapleFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.SmallBushFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.WillowFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.foliage.WindmillPalmFoliagePlacer;
import net.frozenblock.wilderwild.worldgen.impl.root.WillowRootPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.FallenLargeTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.FallenWithLogsTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.FancyDarkOakTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.JuniperTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.LargeSnappedTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.SnappedTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.StraightWithBranchesTrunkPlacer;
import net.frozenblock.wilderwild.worldgen.impl.trunk.WillowTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class WWFeatures {
	public static final TrunkPlacerType<StraightWithBranchesTrunkPlacer> STRAIGHT_WITH_LOGS_TRUNK_PLACER = registerTrunk("straight_with_logs_trunk_placer", StraightWithBranchesTrunkPlacer.CODEC);
	public static final TrunkPlacerType<FallenWithLogsTrunkPlacer> FALLEN_WITH_LOGS_TRUNK_PLACER = registerTrunk("fallen_with_logs_trunk_placer", FallenWithLogsTrunkPlacer.CODEC);
	public static final TrunkPlacerType<FallenLargeTrunkPlacer> FALLEN_LARGE_TRUNK_PLACER = registerTrunk("fallen_large_trunk_placer", FallenLargeTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
	public static final TrunkPlacerType<PalmTrunkPlacer> PALM_TRUNK_PLACER = registerTrunk("palm_trunk_placer", PalmTrunkPlacer.CODEC);
	public static final TrunkPlacerType<JuniperTrunkPlacer> JUNIPER_TRUNK_PLACER = registerTrunk("juniper_trunk_placer", JuniperTrunkPlacer.CODEC);
	public static final TrunkPlacerType<WillowTrunkPlacer> WILLOW_TRUNK_PLACER = registerTrunk("willow_trunk_placer", WillowTrunkPlacer.CODEC);
	public static final TrunkPlacerType<FancyDarkOakTrunkPlacer> FANCY_DARK_OAK_TRUNK_PLACER = registerTrunk("fancy_dark_oak_trunk_placer", FancyDarkOakTrunkPlacer.CODEC);
	public static final TrunkPlacerType<SnappedTrunkPlacer> SNAPPED_TRUNK_PLACER = registerTrunk("snapped_trunk_placer", SnappedTrunkPlacer.CODEC);
	public static final TrunkPlacerType<LargeSnappedTrunkPlacer> LARGE_SNAPPED_TRUNK_PLACER = registerTrunk("large_snapped_trunk_placer", LargeSnappedTrunkPlacer.CODEC);

	public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE_PLACER = registerFoliage("palm_foliage_placer", PalmFoliagePlacer.CODEC);
	public static final FoliagePlacerType<WindmillPalmFoliagePlacer> WINDMILL_PALM_FOLIAGE_PLACER = registerFoliage("windmill_palm_foliage_placer", WindmillPalmFoliagePlacer.CODEC);
	public static final FoliagePlacerType<MapleFoliagePlacer> MAPLE_FOLIAGE_PLACER = registerFoliage("maple_foliage_placer", MapleFoliagePlacer.CODEC);
	public static final FoliagePlacerType<RoundMapleFoliagePlacer> ROUND_MAPLE_FOLIAGE_PLACER = registerFoliage("round_maple_foliage_placer", RoundMapleFoliagePlacer.CODEC);
	public static final FoliagePlacerType<NoOpFoliagePlacer> NO_OP_FOLIAGE_PLACER = registerFoliage("no_op_foliage_placer", NoOpFoliagePlacer.CODEC);
	public static final FoliagePlacerType<WillowFoliagePlacer> WILLOW_FOLIAGE_PLACER = registerFoliage("willow_foliage_placer", WillowFoliagePlacer.CODEC);
	public static final FoliagePlacerType<SmallBushFoliagePlacer> SMALL_BUSH_FOLIAGE_PLACER = registerFoliage("small_bush_foliage_placer", SmallBushFoliagePlacer.CODEC);

	public static final RootPlacerType<WillowRootPlacer> WILLOW_ROOT_PLACER = register("willow_root_placer", WillowRootPlacer.CODEC);

	public static final Feature<ShelfFungiFeatureConfig> SHELF_FUNGI_FEATURE = register("shelf_fungi_feature", new ShelfFungiFeature(ShelfFungiFeatureConfig.CODEC));
	public static final Feature<SpongeBudFeatureConfig> SPONGE_BUD_FEATURE = register("sponge_bud_feature", new SpongeBudFeature(SpongeBudFeatureConfig.CODEC));
	public static final CattailFeature CATTAIL_FEATURE = register("cattail_feature", new CattailFeature(CattailFeatureConfig.CODEC));
	public static final AlgaeFeature ALGAE_FEATURE = register("algae_feature", new AlgaeFeature(AlgaeFeatureConfig.CODEC));
	public static final NematocystFeature NEMATOCYST_FEATURE = register("nematocyst_feature", new NematocystFeature(MultifaceGrowthConfiguration.CODEC));
	public static final LargeMesogleaFeature LARGE_MESOGLEA_FEATURE = register("large_mesoglea_feature", new LargeMesogleaFeature(LargeMesogleaConfig.CODEC));
	public static final SnowBlanketFeature SNOW_BLANKET_FEATURE = register("snow_blanket_feature", new SnowBlanketFeature(NoneFeatureConfiguration.CODEC));
	public static final SnowAndFreezeDiskFeature SNOW_AND_FREEZE_DISK_FEATURE = register("snow_and_freeze_disk_feature", new SnowAndFreezeDiskFeature(SnowAndIceDiskFeatureConfig.CODEC));
	public static final PalmTreeFeature PALM_TREE_FEATURE = register("palm_tree", new PalmTreeFeature(TreeConfiguration.CODEC));
	public static final Feature<HugeMushroomFeatureConfiguration> HUGE_PALE_MUSHROOM_FEATURE = register("huge_pale_mushroom", new HugePaleMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));

	public static void init() {
	}

	@NotNull
	private static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunk(@NotNull String id, @NotNull MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, WWConstants.id(id), new TrunkPlacerType<>(codec));
	}

	@NotNull
	private static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliage(@NotNull String id, @NotNull MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, WWConstants.id(id), new FoliagePlacerType<>(codec));
	}

	@NotNull
	private static <FC extends FeatureConfiguration, T extends Feature<FC>> T register(@NotNull String id, @NotNull T feature) {
		return Registry.register(BuiltInRegistries.FEATURE, WWConstants.id(id), feature);
	}

	@NotNull
	private static <P extends RootPlacer> RootPlacerType<P> register(@NotNull String id, @NotNull MapCodec<P> mapCodec) {
		return Registry.register(BuiltInRegistries.ROOT_PLACER_TYPE, WWConstants.id(id), new RootPlacerType<>(mapCodec));
	}
}
