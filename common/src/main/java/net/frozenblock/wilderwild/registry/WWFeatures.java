/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.levelgen.feature.CattailFeature;
import net.frozenblock.wilderwild.levelgen.feature.HugePaleMushroomFeature;
import net.frozenblock.wilderwild.levelgen.feature.HydrothermalVentFeature;
import net.frozenblock.wilderwild.levelgen.feature.LargeMesogleaFeature;
import net.frozenblock.wilderwild.levelgen.feature.ShelfFungiFeature;
import net.frozenblock.wilderwild.levelgen.feature.SnowAndFreezeDiskFeature;
import net.frozenblock.wilderwild.levelgen.feature.SnowBlanketFeature;
import net.frozenblock.wilderwild.levelgen.feature.SpongeBudFeature;
import net.frozenblock.wilderwild.levelgen.feature.TubeWormsFeature;
import net.frozenblock.wilderwild.levelgen.feature.WaterCoverFeature;
import net.frozenblock.wilderwild.levelgen.foliageplacers.LegacyMapleFoliagePlacer;
import net.frozenblock.wilderwild.levelgen.foliageplacers.MapleFoliagePlacer;
import net.frozenblock.wilderwild.levelgen.foliageplacers.NoOpFoliagePlacer;
import net.frozenblock.wilderwild.levelgen.foliageplacers.PalmFoliagePlacer;
import net.frozenblock.wilderwild.levelgen.foliageplacers.SmallBushFoliagePlacer;
import net.frozenblock.wilderwild.levelgen.foliageplacers.WillowFoliagePlacer;
import net.frozenblock.wilderwild.levelgen.foliageplacers.WindmillPalmFoliagePlacer;
import net.frozenblock.wilderwild.levelgen.rootplacers.WillowRootPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.FallenLargeTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.FallenWithBranchesTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.FancyDarkOakTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.JuniperTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.LargeSnappedTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.MapleTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.PalmTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.SnappedTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.StraightWithBranchesTrunkPlacer;
import net.frozenblock.wilderwild.levelgen.trunkplacers.WillowTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public final class WWFeatures {
	private static final FrozenDeferredRegister<TrunkPlacerType<?>> TRUNK = FrozenDeferredRegister.create(
		Registries.TRUNK_PLACER_TYPE,
		WWConstants.MOD_ID
	);
	private static final FrozenDeferredRegister<FoliagePlacerType<?>> FOLIAGE = FrozenDeferredRegister.create(
		Registries.FOLIAGE_PLACER_TYPE,
		WWConstants.MOD_ID
	);
	private static final FrozenDeferredRegister<RootPlacerType<?>> ROOT = FrozenDeferredRegister.create(
		Registries.ROOT_PLACER_TYPE,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<StraightWithBranchesTrunkPlacer>> STRAIGHT_WITH_BRANCHES_TRUNK_PLACER = registerTrunk("straight_with_branches_trunk_placer", StraightWithBranchesTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<FallenWithBranchesTrunkPlacer>> FALLEN_WITH_BRANCHES_TRUNK_PLACER = registerTrunk("fallen_with_branches_trunk_placer", FallenWithBranchesTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<FallenLargeTrunkPlacer>> FALLEN_LARGE_TRUNK_PLACER = registerTrunk("fallen_large_trunk_placer", FallenLargeTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<BaobabTrunkPlacer>> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<PalmTrunkPlacer>> PALM_TRUNK_PLACER = registerTrunk("palm_trunk_placer", PalmTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<JuniperTrunkPlacer>> JUNIPER_TRUNK_PLACER = registerTrunk("juniper_trunk_placer", JuniperTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<WillowTrunkPlacer>> WILLOW_TRUNK_PLACER = registerTrunk("willow_trunk_placer", WillowTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<FancyDarkOakTrunkPlacer>> FANCY_DARK_OAK_TRUNK_PLACER = registerTrunk("fancy_dark_oak_trunk_placer", FancyDarkOakTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<MapleTrunkPlacer>> MAPLE_TRUNK_PLACER = registerTrunk("maple_trunk_placer", MapleTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<SnappedTrunkPlacer>> SNAPPED_TRUNK_PLACER = registerTrunk("snapped_trunk_placer", SnappedTrunkPlacer.CODEC);
	public static final FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<LargeSnappedTrunkPlacer>> LARGE_SNAPPED_TRUNK_PLACER = registerTrunk("large_snapped_trunk_placer", LargeSnappedTrunkPlacer.CODEC);

	public static final FrozenHolder<FoliagePlacerType<?>, FoliagePlacerType<PalmFoliagePlacer>> PALM_FOLIAGE_PLACER = registerFoliage("palm_foliage_placer", PalmFoliagePlacer.CODEC);
	public static final FrozenHolder<FoliagePlacerType<?>, FoliagePlacerType<WindmillPalmFoliagePlacer>> WINDMILL_PALM_FOLIAGE_PLACER = registerFoliage("windmill_palm_foliage_placer", WindmillPalmFoliagePlacer.CODEC);
	public static final FrozenHolder<FoliagePlacerType<?>, FoliagePlacerType<MapleFoliagePlacer>> MAPLE_FOLIAGE_PLACER = registerFoliage("maple_foliage_placer", MapleFoliagePlacer.CODEC);
	public static final FrozenHolder<FoliagePlacerType<?>, FoliagePlacerType<LegacyMapleFoliagePlacer>> LEGACY_MAPLE_FOLIAGE_PLACER = registerFoliage("legacy_maple_foliage_placer", LegacyMapleFoliagePlacer.CODEC);
	public static final FrozenHolder<FoliagePlacerType<?>, FoliagePlacerType<NoOpFoliagePlacer>> NO_OP_FOLIAGE_PLACER = registerFoliage("no_op_foliage_placer", NoOpFoliagePlacer.CODEC);
	public static final FrozenHolder<FoliagePlacerType<?>, FoliagePlacerType<WillowFoliagePlacer>> WILLOW_FOLIAGE_PLACER = registerFoliage("willow_foliage_placer", WillowFoliagePlacer.CODEC);
	public static final FrozenHolder<FoliagePlacerType<?>, FoliagePlacerType<SmallBushFoliagePlacer>> SMALL_BUSH_FOLIAGE_PLACER = registerFoliage("small_bush_foliage_placer", SmallBushFoliagePlacer.CODEC);

	public static final FrozenHolder<RootPlacerType<?>, RootPlacerType<WillowRootPlacer>> WILLOW_ROOT_PLACER = register("willow_root_placer", WillowRootPlacer.CODEC);

	static {
		TRUNK.register();
		FOLIAGE.register();
		ROOT.register();
	}

	public static void init() {
		var register = FrozenDeferredRegister.create(
			Registries.FEATURE_TYPE,
			WWConstants.MOD_ID
		);

		register.register("shelf_fungi", () -> ShelfFungiFeature.CODEC);
		register.register("sponge_bud", () -> SpongeBudFeature.CODEC);
		register.register("cattail", () -> CattailFeature.CODEC);
		register.register("water_cover", () -> WaterCoverFeature.CODEC);
		register.register("tube_worms", () -> TubeWormsFeature.CODEC);
		register.register("hydrothermal_vent", () -> HydrothermalVentFeature.CODEC);
		register.register("large_mesoglea", () -> LargeMesogleaFeature.CODEC);
		register.register("snow_blanket", () -> SnowBlanketFeature.CODEC);
		register.register("snow_and_freeze_disk", () -> SnowAndFreezeDiskFeature.CODEC);
		register.register("huge_pale_mushroom", () -> HugePaleMushroomFeature.CODEC);

		register.register();
	}

	private static <P extends TrunkPlacer> FrozenHolder<TrunkPlacerType<?>, TrunkPlacerType<P>> registerTrunk(String name, MapCodec<P> codec) {
		return TRUNK.register(name, () -> new TrunkPlacerType<>(codec));
	}

	private static <P extends FoliagePlacer> FrozenHolder<FoliagePlacerType<?>, FoliagePlacerType<P>> registerFoliage(String name, MapCodec<P> codec) {
		return FOLIAGE.register(name, () -> new FoliagePlacerType<>(codec));
	}

	private static <P extends RootPlacer> FrozenHolder<RootPlacerType<?>, RootPlacerType<P>> register(String name, MapCodec<P> codec) {
		return ROOT.register(name, () -> new RootPlacerType<>(codec));
	}
}
