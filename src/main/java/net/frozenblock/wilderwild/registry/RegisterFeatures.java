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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.world.impl.features.AlgaeFeature;
import net.frozenblock.wilderwild.world.impl.features.CattailFeature;
import net.frozenblock.wilderwild.world.impl.features.LargeMesogleaFeature;
import net.frozenblock.wilderwild.world.impl.features.NematocystFeature;
import net.frozenblock.wilderwild.world.impl.features.PalmTreeFeature;
import net.frozenblock.wilderwild.world.impl.features.ShelfFungusFeature;
import net.frozenblock.wilderwild.world.impl.features.SmallSpongeFeature;
import net.frozenblock.wilderwild.world.impl.features.SnowAndFreezeDiskFeature;
import net.frozenblock.wilderwild.world.impl.features.SnowBlanketFeature;
import net.frozenblock.wilderwild.world.impl.features.config.AlgaeFeatureConfig;
import net.frozenblock.wilderwild.world.impl.features.config.CattailFeatureConfig;
import net.frozenblock.wilderwild.world.impl.features.config.LargeMesogleaConfig;
import net.frozenblock.wilderwild.world.impl.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.impl.features.config.SmallSpongeFeatureConfig;
import net.frozenblock.wilderwild.world.impl.features.config.SnowAndIceDiskFeatureConfig;
import net.frozenblock.wilderwild.world.impl.foliage.MapleFoliagePlacer;
import net.frozenblock.wilderwild.world.impl.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.world.impl.foliage.ShortPalmFoliagePlacer;
import net.frozenblock.wilderwild.world.impl.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.FallenLargeTrunk;
import net.frozenblock.wilderwild.world.impl.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.impl.trunk.FancyDarkOakTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.JuniperTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.LargeSnappedTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.SnappedTrunkPlacer;
import net.frozenblock.wilderwild.world.impl.trunk.StraightTrunkWithBranches;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class RegisterFeatures {
	public static final TrunkPlacerType<StraightTrunkWithBranches> STRAIGHT_TRUNK_WITH_LOGS_PLACER = registerTrunk("straight_trunk_logs_placer", StraightTrunkWithBranches.CODEC);
	public static final TrunkPlacerType<FallenTrunkWithLogs> FALLEN_TRUNK_WITH_LOGS_PLACER = registerTrunk("fallen_trunk_logs_placer", FallenTrunkWithLogs.CODEC);
	public static final TrunkPlacerType<FallenLargeTrunk> FALLEN_LARGE_TRUNK_PLACER = registerTrunk("fallen_large_trunk_placer", FallenLargeTrunk.CODEC);
	public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
	public static final TrunkPlacerType<PalmTrunkPlacer> PALM_TRUNK_PLACER = registerTrunk("palm_trunk_placer", PalmTrunkPlacer.CODEC);
	public static final TrunkPlacerType<JuniperTrunkPlacer> JUNIPER_TRUNK_PLACER = registerTrunk("juniper_trunk_placer", JuniperTrunkPlacer.CODEC);
	public static final TrunkPlacerType<FancyDarkOakTrunkPlacer> FANCY_DARK_OAK_TRUNK_PLACER = registerTrunk("fancy_dark_oak_trunk_placer", FancyDarkOakTrunkPlacer.CODEC);
	public static final TrunkPlacerType<SnappedTrunkPlacer> SNAPPED_TRUNK_PLACER = registerTrunk("snapped_trunk_placer", SnappedTrunkPlacer.CODEC);
	public static final TrunkPlacerType<LargeSnappedTrunkPlacer> LARGE_SNAPPED_TRUNK_PLACER = registerTrunk("large_snapped_trunk_placer", LargeSnappedTrunkPlacer.CODEC);

	public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE_PLACER = registerFoliage("palm_foliage_placer", PalmFoliagePlacer.CODEC);
	public static final FoliagePlacerType<ShortPalmFoliagePlacer> SHORT_PALM_FOLIAGE_PLACER = registerFoliage("short_palm_foliage_placer", ShortPalmFoliagePlacer.CODEC);
	public static final FoliagePlacerType<MapleFoliagePlacer> MAPLE_FOLIAGE_PLACER = registerFoliage("maple_foliage_placer", MapleFoliagePlacer.CODEC);

	public static final Feature<ShelfFungusFeatureConfig> SHELF_FUNGUS_FEATURE = register("shelf_fungus_feature", new ShelfFungusFeature(ShelfFungusFeatureConfig.CODEC));
	public static final Feature<SmallSpongeFeatureConfig> SMALL_SPONGE_FEATURE = register("small_sponge_feature", new SmallSpongeFeature(SmallSpongeFeatureConfig.CODEC));
	public static final CattailFeature CATTAIL_FEATURE = register("cattail_feature", new CattailFeature(CattailFeatureConfig.CODEC));
	public static final AlgaeFeature ALGAE_FEATURE = register("algae_feature", new AlgaeFeature(AlgaeFeatureConfig.CODEC));
	public static final NematocystFeature NEMATOCYST_FEATURE = register("nematocyst_feature", new NematocystFeature(MultifaceGrowthConfiguration.CODEC));
	public static final LargeMesogleaFeature LARGE_MESOGLEA_FEATURE = register("large_mesoglea_feature", new LargeMesogleaFeature(LargeMesogleaConfig.CODEC));
	public static final SnowBlanketFeature SNOW_BLANKET_FEATURE = register("snow_blanket_feature", new SnowBlanketFeature(NoneFeatureConfiguration.CODEC));
	public static final SnowAndFreezeDiskFeature SNOW_AND_FREEZE_DISK_FEATURE = register("snow_and_freeze_disk_feature", new SnowAndFreezeDiskFeature(SnowAndIceDiskFeatureConfig.CODEC));
	public static final PalmTreeFeature PALM_TREE_FEATURE = register("palm_tree", new PalmTreeFeature(TreeConfiguration.CODEC));
	public static void init() {
	}

	@NotNull
	private static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunk(@NotNull String id, @NotNull MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, WilderConstants.id(id), new TrunkPlacerType<>(codec));
	}

	@NotNull
	private static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliage(@NotNull String id, @NotNull MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, WilderConstants.id(id), new FoliagePlacerType<>(codec));
	}

	@NotNull
	private static <FC extends FeatureConfiguration, T extends Feature<FC>> T register(@NotNull String id, @NotNull T feature) {
		return Registry.register(BuiltInRegistries.FEATURE, WilderConstants.id(id), feature);
	}
}
