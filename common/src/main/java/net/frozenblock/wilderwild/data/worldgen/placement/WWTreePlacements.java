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

package net.frozenblock.wilderwild.data.worldgen.placement;

import java.util.List;
import java.util.function.Consumer;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedTreeFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibTreeFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.block.impl.PoplarCollection;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeFeatures;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class WWTreePlacements {
	// OAK
	public static final FrozenLibPlacedTreeFeature OAK = WWTreeFeatures.OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature OAK_NO_FUNGI = WWTreeFeatures.OAK_NO_FUNGI.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_OAK = WWTreeFeatures.DYING_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature OAK_BEES_0004 = WWTreeFeatures.OAK_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_OAK = WWTreeFeatures.SHORT_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_OAK = WWTreeFeatures.FANCY_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_DYING_OAK = WWTreeFeatures.FANCY_DYING_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_FANCY_OAK_BEES_0004 = WWTreeFeatures.FANCY_DYING_OAK_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_OAK_BEES_0004 = WWTreeFeatures.FANCY_OAK_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_DYING_OAK_BEES_025 = WWTreeFeatures.FANCY_DYING_OAK_BEES_025.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_OAK_BEES_025 = WWTreeFeatures.FANCY_OAK_BEES_025.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_OAK_BEES = WWTreeFeatures.FANCY_OAK_BEES.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature OLD_DYING_FANCY_OAK_BEES_0004 = WWTreeFeatures.OLD_FANCY_DYING_OAK_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_DEAD_OAK = WWTreeFeatures.FANCY_DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_SEMI_DEAD_OAK = WWTreeFeatures.FANCY_SEMI_DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SMALL_FANCY_DEAD_OAK = WWTreeFeatures.SMALL_FANCY_DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SMALL_FANCY_SEMI_DEAD_OAK = WWTreeFeatures.SMALL_FANCY_SEMI_DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DEAD_OAK = WWTreeFeatures.DEAD_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DEAD_OAK_BRANCHES = WWTreeFeatures.DEAD_OAK_BRANCHES.toPlacedFeature();


	public static final FrozenLibPlacedFeature FALLEN_OAK = register("fallen_oak");
	public static final FrozenLibPlacedFeature FALLEN_OAK_NO_MOSS = register("fallen_oak_no_moss");
	public static final FrozenLibPlacedFeature FALLEN_OAK_MOSSY = register("fallen_oak_mossy");
	public static final FrozenLibPlacedFeature SNAPPED_OAK = register("snapped_oak");

	// BIRCH
	public static final FrozenLibPlacedTreeFeature BIRCH = WWTreeFeatures.BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature BIRCH_BEES_0004 = WWTreeFeatures.BIRCH_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature BIRCH_BEES_025 = WWTreeFeatures.BIRCH_BEES_025.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_BIRCH = WWTreeFeatures.DYING_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SUPER_BIRCH_BEES_0004 = WWTreeFeatures.SUPER_BIRCH_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_SUPER_BIRCH = WWTreeFeatures.DYING_SUPER_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_BIRCH_BEES_0004 = WWTreeFeatures.SHORT_BIRCH_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_BIRCH = WWTreeFeatures.SHORT_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_SHORT_BIRCH = WWTreeFeatures.DYING_SHORT_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEDIUM_BIRCH_BEES_0004 = WWTreeFeatures.MEDIUM_BIRCH_BEES_0004.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEDIUM_BIRCH_BEES_025 = WWTreeFeatures.MEDIUM_BIRCH_BEES_025.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEDIUM_BIRCH = WWTreeFeatures.MEDIUM_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_MEDIUM_BIRCH = WWTreeFeatures.DYING_MEDIUM_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SUPER_BIRCH_BEES = WWTreeFeatures.SUPER_BIRCH_BEES.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SUPER_BIRCH = WWTreeFeatures.SUPER_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DEAD_BIRCH = WWTreeFeatures.DEAD_BIRCH.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DEAD_MEDIUM_BIRCH = WWTreeFeatures.DEAD_MEDIUM_BIRCH.toPlacedFeature();

	public static final FrozenLibPlacedFeature FALLEN_BIRCH = register("fallen_birch");
	public static final FrozenLibPlacedFeature FALLEN_BIRCH_MOSSY = register("fallen_birch_mossy");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH = register("snapped_birch");

	// SPRUCE
	public static final FrozenLibPlacedTreeFeature SPRUCE = WWTreeFeatures.SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedFeature SPRUCE_ON_SNOW = register("spruce_on_snow");
	public static final FrozenLibPlacedTreeFeature SHORT_SPRUCE = WWTreeFeatures.SHORT_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FUNGUS_PINE = WWTreeFeatures.FUNGUS_PINE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_FUNGUS_PINE = WWTreeFeatures.DYING_FUNGUS_PINE.toPlacedFeature();
	public static final FrozenLibPlacedFeature FUNGUS_PINE_ON_SNOW = register("fungus_pine_on_snow");
	public static final FrozenLibPlacedTreeFeature MEGA_FUNGUS_SPRUCE = WWTreeFeatures.MEGA_FUNGUS_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEGA_FUNGUS_PINE = WWTreeFeatures.MEGA_FUNGUS_PINE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_MEGA_FUNGUS_PINE = WWTreeFeatures.DYING_MEGA_FUNGUS_PINE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_MEGA_SPRUCE = WWTreeFeatures.SHORT_MEGA_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_MEGA_FUNGUS_SPRUCE = WWTreeFeatures.SHORT_MEGA_FUNGUS_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_MEGA_DYING_FUNGUS_SPRUCE = WWTreeFeatures.SHORT_MEGA_DYING_FUNGUS_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SHORT_MEGA_DYING_SPRUCE = WWTreeFeatures.SHORT_MEGA_DYING_SPRUCE.toPlacedFeature();
	public static final FrozenLibPlacedFeature SHORT_MEGA_SPRUCE_ON_SNOW = register("short_mega_spruce_on_snow");
	public static final FrozenLibPlacedFeature SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW = register("short_mega_fungus_spruce_on_snow");
	public static final FrozenLibPlacedFeature SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW = register("short_mega_dying_fungus_spruce_on_snow");
	public static final FrozenLibPlacedFeature SHORT_MEGA_DYING_SPRUCE_ON_SNOW = register("short_mega_dying_spruce_on_snow");

	public static final FrozenLibPlacedFeature FALLEN_SPRUCE = register("fallen_spruce");
	public static final FrozenLibPlacedFeature FALLEN_SPRUCE_MOSSY = register("fallen_spruce_mossy");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE = register("snapped_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_ON_SNOW = register("snapped_spruce_on_snow");
	public static final FrozenLibPlacedFeature LARGE_SNAPPED_SPRUCE = register("large_snapped_spruce");
	public static final FrozenLibPlacedFeature LARGE_SNAPPED_SPRUCE_ON_SNOW = register("large_snapped_spruces_on_snow");

	// JUNGLE
	public static final FrozenLibPlacedTreeFeature JUNGLE = WWTreeFeatures.JUNGLE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature MEGA_JUNGLE = WWTreeFeatures.MEGA_JUNGLE.toPlacedFeature();

	public static final FrozenLibPlacedFeature FALLEN_JUNGLE = register("fallen_jungle");
	public static final FrozenLibPlacedFeature SNAPPED_JUNGLE = register("snapped_jungle");
	public static final FrozenLibPlacedFeature LARGE_SNAPPED_JUNGLE = register("large_snapped_jungle");

	// ACACIA
	public static final FrozenLibPlacedFeature ACACIA_LEAF_LITTER = register("acacia_leaf_litter");

	public static final FrozenLibPlacedFeature FALLEN_ACACIA = register("fallen_acacia");
	public static final FrozenLibPlacedFeature SNAPPED_ACACIA = register("snapped_acacia");

	// BAOBAB
	public static final FrozenLibPlacedTreeFeature BAOBAB = WWTreeFeatures.BAOBAB.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature TALL_BAOBAB = WWTreeFeatures.TALL_BAOBAB.toPlacedFeature();

	// DARK OAK
	public static final FrozenLibPlacedTreeFeature DARK_OAK = WWTreeFeatures.DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_DARK_OAK = WWTreeFeatures.DYING_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature TALL_DARK_OAK = WWTreeFeatures.TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature FANCY_TALL_DARK_OAK = WWTreeFeatures.FANCY_TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_TALL_DARK_OAK = WWTreeFeatures.DYING_TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature DYING_FANCY_TALL_DARK_OAK = WWTreeFeatures.FANCY_DYING_TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature COBWEB_TALL_DARK_OAK = WWTreeFeatures.COBWEB_TALL_DARK_OAK.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature COBWEB_FANCY_TALL_DARK_OAK = WWTreeFeatures.COBWEB_FANCY_TALL_DARK_OAK.toPlacedFeature();

	public static final FrozenLibPlacedFeature LARGE_SNAPPED_DARK_OAK = register("large_snapped_dark_oak");

	// WILLOW & SWAMP
	public static final FrozenLibPlacedTreeFeature SHORT_WILLOW = WWTreeFeatures.SHORT_WILLOW.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature WILLOW = WWTreeFeatures.WILLOW.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature SWAMP_OAK = WWTreeFeatures.SWAMP_OAK.toPlacedFeature();

	public static final FrozenLibPlacedFeature FALLEN_WILLOW_MOSSY = register("fallen_willow_mossy");

	// CYPRESS
	public static final FrozenLibPlacedFeature CYPRESS = register("cypress");
	public static final FrozenLibPlacedFeature FUNGUS_CYPRESS = register("fungus_cypress");
	public static final FrozenLibPlacedFeature SHORT_CYPRESS = register("short_cypress");
	public static final FrozenLibPlacedFeature SHORT_FUNGUS_CYPRESS = register("short_fungus_cypress");
	public static final FrozenLibPlacedFeature SWAMP_CYPRESS = register("swamp_cypress");
	public static final FrozenLibPlacedTreeFeature JUNIPER = WWTreeFeatures.JUNIPER.toPlacedFeature();

	public static final FrozenLibPlacedFeature FALLEN_CYPRESS = register("fallen_cypress");
	public static final FrozenLibPlacedFeature SNAPPED_CYPRESS = register("snapped_cypress");

	// MANGROVE
	public static final FrozenLibPlacedTreeFeature MANGROVE = WWTreeFeatures.MANGROVE.toPlacedFeature();
	public static final FrozenLibPlacedTreeFeature TALL_MANGROVE = WWTreeFeatures.TALL_MANGROVE.toPlacedFeature();

	// PALM
	public static final FrozenLibPlacedFeature PALM = register("palm");
	public static final FrozenLibPlacedFeature TALL_PALM = register("tall_palm");
	public static final FrozenLibPlacedFeature SHORT_WINDMILL_PALM = register("short_windmill_palm");
	public static final FrozenLibPlacedFeature TALL_WINDMILL_PALM = register("tall_windmill_palm");

	public static final FrozenLibPlacedFeature PALM_DIRT = register("palm_dirt");
	public static final FrozenLibPlacedFeature PALM_DIRT_LEAF_LITTER = register("palm_dirt_leaf_litter");
	public static final FrozenLibPlacedFeature TALL_PALM_DIRT = register("tall_palm_dirt");
	public static final FrozenLibPlacedFeature TALL_PALM_DIRT_LEAF_LITTER = register("tall_palm_dirt_leaf_litter");
	public static final FrozenLibPlacedFeature SHORT_WINDMILL_PALM_DIRT = register("short_windmill_palm_dirt");
	public static final FrozenLibPlacedFeature SHORT_WINDMILL_PALM_DIRT_LEAF_LITTER = register("short_windmill_palm_dirt_leaf_litter");
	public static final FrozenLibPlacedFeature TALL_WINDMILL_PALM_DIRT = register("tall_windmill_palm_dirt");
	public static final FrozenLibPlacedFeature TALL_WINDMILL_PALM_DIRT_LEAF_LITTER = register("tall_windmill_palm_dirt_leaf_litter");

	public static final FrozenLibPlacedFeature FALLEN_PALM = register("fallen_palm");

	// CHERRY
	public static final FrozenLibPlacedFeature CHERRY = register("cherry");
	public static final FrozenLibPlacedFeature DYING_CHERRY = register("dying_cherry");
	public static final FrozenLibPlacedFeature TALL_CHERRY = register("tall_cherry");
	public static final FrozenLibPlacedFeature TALL_DYING_CHERRY = register("tall_dying_cherry");
	public static final FrozenLibPlacedFeature CHERRY_BEES_025 = register("cherry_bees_025");
	public static final FrozenLibPlacedFeature TALL_CHERRY_BEES_025 = register("tall_cherry_bees_025");

	public static final FrozenLibPlacedFeature FALLEN_CHERRY = register("fallen_cherry");
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_CHERRY = register("mossy_fallen_cherry");
	public static final FrozenLibPlacedFeature SNAPPED_CHERRY = register("snapped_cherry");

	// MAPLE
	public static final MapleCollection<FrozenLibPlacedTreeFeature> MAPLE = WWTreeFeatures.MAPLE.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final MapleCollection<FrozenLibPlacedTreeFeature> DYING_MAPLE = WWTreeFeatures.DYING_MAPLE.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final MapleCollection<FrozenLibPlacedTreeFeature> TALL_MAPLE = WWTreeFeatures.TALL_MAPLE.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final MapleCollection<FrozenLibPlacedTreeFeature> TALL_DYING_MAPLE = WWTreeFeatures.TALL_DYING_MAPLE.map(FrozenLibTreeFeature::toPlacedFeature);;
	public static final MapleCollection<FrozenLibPlacedTreeFeature> MAPLE_BEES_0004 = WWTreeFeatures.MAPLE_BEES_0004.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final MapleCollection<FrozenLibPlacedTreeFeature> TALL_MAPLE_BEES_0004 = WWTreeFeatures.TALL_MAPLE_BEES_0004.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final MapleCollection<FrozenLibPlacedTreeFeature> SHORT_MAPLE = WWTreeFeatures.SHORT_MAPLE.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final MapleCollection<FrozenLibPlacedTreeFeature> FULL_MAPLE = WWTreeFeatures.FULL_MAPLE.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final MapleCollection<FrozenLibPlacedTreeFeature> BIG_BUSH_MAPLE = WWTreeFeatures.BIG_BUSH_MAPLE.map(FrozenLibTreeFeature::toPlacedFeature);

	public static final FrozenLibPlacedFeature SNAPPED_MAPLE = register("snapped_maple");

	// PALE OAK
	public static final FrozenLibPlacedFeature PALE_OAK = register("pale_oak");
	public static final FrozenLibPlacedFeature PALE_OAK_CREAKING = register("pale_oak_creaking");
	public static final FrozenLibPlacedFeature TALL_PALE_OAK = register("tall_pale_oak");
	public static final FrozenLibPlacedFeature TALL_PALE_OAK_CREAKING = register("tall_pale_oak_creaking");
	public static final FrozenLibPlacedFeature FANCY_TALL_PALE_OAK = register("fancy_tall_pale_oak");
	public static final FrozenLibPlacedFeature FANCY_TALL_PALE_OAK_CREAKING = register("fancy_tall_pale_oak_creaking");
	public static final FrozenLibPlacedFeature COBWEB_TALL_PALE_OAK = register("cobweb_tall_pale_oak");
	public static final FrozenLibPlacedFeature COBWEB_TALL_PALE_OAK_CREAKING = register("cobweb_tall_pale_oak_creaking");
	public static final FrozenLibPlacedFeature COBWEB_FANCY_PALE_OAK = register("cobweb_fancy_tall_pale_oak");
	public static final FrozenLibPlacedFeature COBWEB_FANCY_PALE_OAK_CREAKING = register("cobweb_fancy_tall_pale_oak_creaking");

	public static final FrozenLibPlacedFeature LARGE_SNAPPED_PALE_OAK = register("large_snapped_pale_oak");

	// POPLAR
	public static final PoplarCollection<FrozenLibPlacedTreeFeature> POPLAR = WWTreeFeatures.POPLAR.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final PoplarCollection<FrozenLibPlacedTreeFeature> DYING_POPLAR = WWTreeFeatures.DYING_POPLAR.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final PoplarCollection<FrozenLibPlacedTreeFeature> TALL_POPLAR = WWTreeFeatures.TALL_POPLAR.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final PoplarCollection<FrozenLibPlacedTreeFeature> TALL_DYING_POPLAR = WWTreeFeatures.TALL_DYING_POPLAR.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final PoplarCollection<FrozenLibPlacedTreeFeature> POPLAR_BEES_0002 = WWTreeFeatures.POPLAR_BEES_0002.map(FrozenLibTreeFeature::toPlacedFeature);
	public static final PoplarCollection<FrozenLibPlacedTreeFeature> TALL_POPLAR_BEES_0002 = WWTreeFeatures.TALL_POPLAR_BEES_0002.map(FrozenLibTreeFeature::toPlacedFeature);

	public static final FrozenLibPlacedFeature SNAPPED_POPLAR = register("snapped_poplar");

	// BUSH
	public static final FrozenLibPlacedFeature LARGE_BUSH = register("large_bush");
	public static final FrozenLibPlacedFeature BIG_BUSH = register("big_bush");

	// CRIMSON
	public static final FrozenLibPlacedFeature FALLEN_CRIMSON = register("fallen_crimson");
	public static final FrozenLibPlacedFeature SNAPPED_CRIMSON = register("snapped_crimson");

	// WARPED
	public static final FrozenLibPlacedFeature FALLEN_WARPED = register("fallen_warped");
	public static final FrozenLibPlacedFeature SNAPPED_WARPED = register("snapped_warped");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		final BlockPredicateFilter fallenTreePlacement = fallenTreePlacementFilter();
		final List<PlacementModifier> snowTreePlacement = List.of(
			EnvironmentScanPlacement.scanningFor(
				Direction.UP,
				BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW)),
				8
			),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW)
			)
		);

		// OAK
		final BlockPredicateFilter oakSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING);
		OAK.makeAndSetHolders(oakSaplingPlacement);
		OAK_NO_FUNGI.makeAndSetHolders(oakSaplingPlacement);
		DYING_OAK.makeAndSetHolders(oakSaplingPlacement);
		OAK_BEES_0004.makeAndSetHolders(oakSaplingPlacement);
		SHORT_OAK.makeAndSetHolders(oakSaplingPlacement);
		FANCY_OAK.makeAndSetHolders(oakSaplingPlacement);
		FANCY_DYING_OAK.makeAndSetHolders(oakSaplingPlacement);
		DYING_FANCY_OAK_BEES_0004.makeAndSetHolders(oakSaplingPlacement);
		FANCY_OAK_BEES_0004.makeAndSetHolders(oakSaplingPlacement);
		FANCY_DYING_OAK_BEES_025.makeAndSetHolders(oakSaplingPlacement);
		FANCY_OAK_BEES_025.makeAndSetHolders(oakSaplingPlacement);
		FANCY_OAK_BEES.makeAndSetHolders(oakSaplingPlacement);
		OLD_DYING_FANCY_OAK_BEES_0004.makeAndSetHolders(oakSaplingPlacement);
		FANCY_DEAD_OAK.makeAndSetHolders(oakSaplingPlacement);
		FANCY_SEMI_DEAD_OAK.makeAndSetHolders(oakSaplingPlacement);
		SMALL_FANCY_DEAD_OAK.makeAndSetHolders(oakSaplingPlacement);
		SMALL_FANCY_SEMI_DEAD_OAK.makeAndSetHolders(oakSaplingPlacement);
		DEAD_OAK.makeAndSetHolders(oakSaplingPlacement);
		DEAD_OAK_BRANCHES.makeAndSetHolders(oakSaplingPlacement);

		FALLEN_OAK.makeAndSetHolder(WWTreeFeatures.FALLEN_OAK, fallenTreePlacement);
		FALLEN_OAK_NO_MOSS.makeAndSetHolder(WWTreeFeatures.FALLEN_OAK_NO_MOSS, fallenTreePlacement);
		FALLEN_OAK_MOSSY.makeAndSetHolder(WWTreeFeatures.FALLEN_OAK_MOSSY, fallenTreePlacement);
		SNAPPED_OAK.makeAndSetHolder(WWTreeFeatures.SNAPPED_OAK, oakSaplingPlacement);

		// BIRCH
		BlockPredicateFilter birchSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING);
		BIRCH.makeAndSetHolders(birchSaplingPlacement);
		BIRCH_BEES_0004.makeAndSetHolders(birchSaplingPlacement);
		BIRCH_BEES_025.makeAndSetHolders(birchSaplingPlacement);
		DYING_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		SUPER_BIRCH_BEES_0004.makeAndSetHolders(birchSaplingPlacement);
		DYING_SUPER_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		SHORT_BIRCH_BEES_0004.makeAndSetHolders(birchSaplingPlacement);
		SHORT_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		DYING_SHORT_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		MEDIUM_BIRCH_BEES_0004.makeAndSetHolders(birchSaplingPlacement);
		MEDIUM_BIRCH_BEES_025.makeAndSetHolders(birchSaplingPlacement);
		MEDIUM_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		DYING_MEDIUM_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		SUPER_BIRCH_BEES.makeAndSetHolders(birchSaplingPlacement);
		SUPER_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		DEAD_BIRCH.makeAndSetHolders(birchSaplingPlacement);
		DEAD_MEDIUM_BIRCH.makeAndSetHolders(birchSaplingPlacement);

		FALLEN_BIRCH.makeAndSetHolder(WWTreeFeatures.FALLEN_BIRCH, fallenTreePlacement);
		FALLEN_BIRCH_MOSSY.makeAndSetHolder(WWTreeFeatures.FALLEN_BIRCH_MOSSY, fallenTreePlacement);
		SNAPPED_BIRCH.makeAndSetHolder(WWTreeFeatures.SNAPPED_BIRCH, birchSaplingPlacement);

		// SPRUCE
		final BlockPredicateFilter spruceSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING);
		SPRUCE.makeAndSetHolders(spruceSaplingPlacement);
		SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeFeatures.SPRUCE.getHolder(), snowTreePlacement);
		SHORT_SPRUCE.makeAndSetHolders(spruceSaplingPlacement);
		FUNGUS_PINE.makeAndSetHolders(spruceSaplingPlacement);
		DYING_FUNGUS_PINE.makeAndSetHolders(spruceSaplingPlacement);
		FUNGUS_PINE_ON_SNOW.makeAndSetHolder(WWTreeFeatures.FUNGUS_PINE.getHolder(), snowTreePlacement);
		MEGA_FUNGUS_SPRUCE.makeAndSetHolders(spruceSaplingPlacement);
		MEGA_FUNGUS_PINE.makeAndSetHolders(spruceSaplingPlacement);
		DYING_MEGA_FUNGUS_PINE.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_SPRUCE.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_FUNGUS_SPRUCE.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_DYING_FUNGUS_SPRUCE.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_DYING_SPRUCE.makeAndSetHolders(spruceSaplingPlacement);
		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeFeatures.SHORT_MEGA_SPRUCE.getHolder(), snowTreePlacement);
		SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeFeatures.SHORT_MEGA_FUNGUS_SPRUCE.getHolder(), snowTreePlacement);
		SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeFeatures.SHORT_MEGA_DYING_FUNGUS_SPRUCE.getHolder(), snowTreePlacement);
		SHORT_MEGA_DYING_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeFeatures.SHORT_MEGA_DYING_SPRUCE.getHolder(), snowTreePlacement);

		FALLEN_SPRUCE.makeAndSetHolder(WWTreeFeatures.FALLEN_SPRUCE, fallenTreePlacement);
		FALLEN_SPRUCE_MOSSY.makeAndSetHolder(WWTreeFeatures.FALLEN_SPRUCE_MOSSY, fallenTreePlacement);
		SNAPPED_SPRUCE.makeAndSetHolder(WWTreeFeatures.SNAPPED_SPRUCE, spruceSaplingPlacement);
		SNAPPED_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeFeatures.SNAPPED_SPRUCE, snowTreePlacement);
		LARGE_SNAPPED_SPRUCE.makeAndSetHolder(WWTreeFeatures.LARGE_SNAPPED_SPRUCE, spruceSaplingPlacement);
		LARGE_SNAPPED_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeFeatures.LARGE_SNAPPED_SPRUCE, spruceSaplingPlacement);

		// JUNGLE
		BlockPredicateFilter jungleSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING);
		JUNGLE.makeAndSetHolders(jungleSaplingPlacement);
		MEGA_JUNGLE.makeAndSetHolders(jungleSaplingPlacement);

		FALLEN_JUNGLE.makeAndSetHolder(WWTreeFeatures.FALLEN_JUNGLE, fallenTreePlacement);
		SNAPPED_JUNGLE.makeAndSetHolder(WWTreeFeatures.SNAPPED_JUNGLE, jungleSaplingPlacement);
		LARGE_SNAPPED_JUNGLE.makeAndSetHolder(WWTreeFeatures.LARGE_SNAPPED_JUNGLE, jungleSaplingPlacement);

		// ACACIA
		ACACIA_LEAF_LITTER.makeAndSetHolder(WWTreeFeatures.ACACIA_LEAF_LITTER, PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING));

		FALLEN_ACACIA.makeAndSetHolder(WWTreeFeatures.FALLEN_ACACIA, fallenTreePlacement);
		SNAPPED_ACACIA.makeAndSetHolder(WWTreeFeatures.SNAPPED_ACACIA, PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING));

		// BAOBAB
		BAOBAB.makeAndSetHolders(PlacementUtils.filteredByBlockSurvival(WWBlocks.BAOBAB_NUT.get()));
		TALL_BAOBAB.makeAndSetHolders(PlacementUtils.filteredByBlockSurvival(WWBlocks.BAOBAB_NUT.get()));

		// DARK OAK
		final BlockPredicateFilter darkOakSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING);
		DARK_OAK.makeAndSetHolders(darkOakSaplingPlacement);
		DYING_DARK_OAK.makeAndSetHolders(darkOakSaplingPlacement);
		TALL_DARK_OAK.makeAndSetHolders(darkOakSaplingPlacement);
		FANCY_TALL_DARK_OAK.makeAndSetHolders(darkOakSaplingPlacement);
		DYING_TALL_DARK_OAK.makeAndSetHolders(darkOakSaplingPlacement);
		DYING_FANCY_TALL_DARK_OAK.makeAndSetHolders(darkOakSaplingPlacement);
		COBWEB_TALL_DARK_OAK.makeAndSetHolders(darkOakSaplingPlacement);
		COBWEB_FANCY_TALL_DARK_OAK.makeAndSetHolders(darkOakSaplingPlacement);

		LARGE_SNAPPED_DARK_OAK.makeAndSetHolder(WWTreeFeatures.LARGE_SNAPPED_DARK_OAK, darkOakSaplingPlacement);

		// WILLOW & SWAMP
		final BlockPredicateFilter willowSaplingPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING.get());
		SHORT_WILLOW.makeAndSetHolders(willowSaplingPlacement);
		WILLOW.makeAndSetHolders(willowSaplingPlacement);
		SWAMP_OAK.makeAndSetHolders(willowSaplingPlacement);

		FALLEN_WILLOW_MOSSY.makeAndSetHolder(WWTreeFeatures.FALLEN_WILLOW_MOSSY, fallenTreePlacement);

		// CYPRESS
		final BlockPredicateFilter cypressSaplingPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING.get());
		CYPRESS.makeAndSetHolder(WWTreeFeatures.CYPRESS, cypressSaplingPlacement);
		FUNGUS_CYPRESS.makeAndSetHolder(WWTreeFeatures.FUNGUS_CYPRESS, cypressSaplingPlacement);
		SHORT_CYPRESS.makeAndSetHolder(WWTreeFeatures.SHORT_CYPRESS, cypressSaplingPlacement);
		SHORT_FUNGUS_CYPRESS.makeAndSetHolder(WWTreeFeatures.SHORT_FUNGUS_CYPRESS, cypressSaplingPlacement);
		SWAMP_CYPRESS.makeAndSetHolder(WWTreeFeatures.SWAMP_CYPRESS, cypressSaplingPlacement);
		JUNIPER.makeAndSetHolders(oakSaplingPlacement);

		FALLEN_CYPRESS.makeAndSetHolder(WWTreeFeatures.FALLEN_CYPRESS, fallenTreePlacement);
		SNAPPED_CYPRESS.makeAndSetHolder(WWTreeFeatures.SNAPPED_CYPRESS, cypressSaplingPlacement);

		// MANGROVE
		MANGROVE.makeAndSetHolders(PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));
		TALL_MANGROVE.makeAndSetHolders(PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));

		// PALM
		final BlockPredicateFilter coconutPlacement = PlacementUtils.filteredByBlockSurvival(WWBlocks.COCONUT.get());
		PALM.makeAndSetHolder(WWTreeFeatures.PALM.getHolder(), coconutPlacement);
		TALL_PALM.makeAndSetHolder(WWTreeFeatures.TALL_PALM.getHolder(), coconutPlacement);
		SHORT_WINDMILL_PALM.makeAndSetHolder(WWTreeFeatures.SHORT_WINDMILL_PALM.getHolder(), coconutPlacement);
		TALL_WINDMILL_PALM.makeAndSetHolder(WWTreeFeatures.TALL_WINDMILL_PALM.getHolder(), coconutPlacement);

		PALM_DIRT.makeAndSetHolder(WWTreeFeatures.PALM.getHolder(), oakSaplingPlacement);
		PALM_DIRT_LEAF_LITTER.makeAndSetHolder(WWTreeFeatures.PALM.getLitterVariantHolder(), oakSaplingPlacement);
		TALL_PALM_DIRT.makeAndSetHolder(WWTreeFeatures.TALL_PALM.getHolder(), oakSaplingPlacement);
		TALL_PALM_DIRT_LEAF_LITTER.makeAndSetHolder(WWTreeFeatures.TALL_PALM.getLitterVariantHolder(), oakSaplingPlacement);
		SHORT_WINDMILL_PALM_DIRT.makeAndSetHolder(WWTreeFeatures.SHORT_WINDMILL_PALM.getHolder(), oakSaplingPlacement);
		SHORT_WINDMILL_PALM_DIRT_LEAF_LITTER.makeAndSetHolder(WWTreeFeatures.SHORT_WINDMILL_PALM.getLitterVariantHolder(), oakSaplingPlacement);
		TALL_WINDMILL_PALM_DIRT.makeAndSetHolder(WWTreeFeatures.TALL_WINDMILL_PALM.getHolder(), oakSaplingPlacement);
		TALL_WINDMILL_PALM_DIRT_LEAF_LITTER.makeAndSetHolder(WWTreeFeatures.TALL_WINDMILL_PALM.getLitterVariantHolder(), oakSaplingPlacement);

		FALLEN_PALM.makeAndSetHolder(WWTreeFeatures.FALLEN_PALM, fallenTreePlacement);

		// CHERRY
		final BlockPredicateFilter cherrySaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING);
		CHERRY.makeAndSetHolder(WWTreeFeatures.CHERRY, cherrySaplingPlacement);
		DYING_CHERRY.makeAndSetHolder(WWTreeFeatures.DYING_CHERRY, cherrySaplingPlacement);
		TALL_CHERRY.makeAndSetHolder(WWTreeFeatures.TALL_CHERRY, cherrySaplingPlacement);
		TALL_DYING_CHERRY.makeAndSetHolder(WWTreeFeatures.TALL_DYING_CHERRY, cherrySaplingPlacement);
		CHERRY_BEES_025.makeAndSetHolder(WWTreeFeatures.CHERRY_BEES_025, cherrySaplingPlacement);
		TALL_CHERRY_BEES_025.makeAndSetHolder(WWTreeFeatures.TALL_CHERRY_BEES_025, cherrySaplingPlacement);

		FALLEN_CHERRY.makeAndSetHolder(WWTreeFeatures.FALLEN_CHERRY, fallenTreePlacement);
		MOSSY_FALLEN_CHERRY.makeAndSetHolder(WWTreeFeatures.MOSSY_FALLEN_CHERRY, fallenTreePlacement);
		SNAPPED_CHERRY.makeAndSetHolder(WWTreeFeatures.SNAPPED_CHERRY, cherrySaplingPlacement);

		// MAPLE
		final MapleCollection<BlockPredicateFilter> mapleSaplingPlacement = WWBlocks.MAPLE_SAPLING.map(sapling -> PlacementUtils.filteredByBlockSurvival(sapling.get()));
		final Consumer<MapleCollection<FrozenLibPlacedTreeFeature>> registerMaple = collection -> {
			MapleCollection.zipApply(
				collection,
				MapleCollection.DYE_COLORS,
				(feature, color) -> feature.makeAndSetHolders(mapleSaplingPlacement.pick(color))
			);
		};

		registerMaple.accept(MAPLE);
		registerMaple.accept(DYING_MAPLE);
		registerMaple.accept(TALL_MAPLE);
		registerMaple.accept(TALL_DYING_MAPLE);
		registerMaple.accept(MAPLE_BEES_0004);
		registerMaple.accept(TALL_MAPLE_BEES_0004);
		registerMaple.accept(SHORT_MAPLE);
		registerMaple.accept(FULL_MAPLE);
		registerMaple.accept(BIG_BUSH_MAPLE);

		SNAPPED_MAPLE.makeAndSetHolder(WWTreeFeatures.SNAPPED_MAPLE.getHolder(), fallenTreePlacement);

		// PALE OAK
		final BlockPredicateFilter paleOakSaplingPlacement = PlacementUtils.filteredByBlockSurvival(Blocks.PALE_OAK_SAPLING);
		PALE_OAK.makeAndSetHolder(WWTreeFeatures.PALE_OAK, paleOakSaplingPlacement);
		PALE_OAK_CREAKING.makeAndSetHolder(WWTreeFeatures.PALE_OAK_CREAKING, paleOakSaplingPlacement);
		TALL_PALE_OAK.makeAndSetHolder(WWTreeFeatures.TALL_PALE_OAK, paleOakSaplingPlacement);
		TALL_PALE_OAK_CREAKING.makeAndSetHolder(WWTreeFeatures.TALL_PALE_OAK_CREAKING, paleOakSaplingPlacement);
		FANCY_TALL_PALE_OAK.makeAndSetHolder(WWTreeFeatures.FANCY_TALL_PALE_OAK, paleOakSaplingPlacement);
		FANCY_TALL_PALE_OAK_CREAKING.makeAndSetHolder(WWTreeFeatures.FANCY_TALL_PALE_OAK_CREAKING, paleOakSaplingPlacement);
		COBWEB_TALL_PALE_OAK.makeAndSetHolder(WWTreeFeatures.COBWEB_TALL_PALE_OAK, paleOakSaplingPlacement);
		COBWEB_TALL_PALE_OAK_CREAKING.makeAndSetHolder(WWTreeFeatures.COBWEB_TALL_PALE_OAK_CREAKING, paleOakSaplingPlacement);
		COBWEB_FANCY_PALE_OAK.makeAndSetHolder(WWTreeFeatures.COBWEB_FANCY_PALE_OAK, paleOakSaplingPlacement);
		COBWEB_FANCY_PALE_OAK_CREAKING.makeAndSetHolder(WWTreeFeatures.COBWEB_FANCY_PALE_OAK_CREAKING, paleOakSaplingPlacement);

		LARGE_SNAPPED_PALE_OAK.makeAndSetHolder(WWTreeFeatures.LARGE_SNAPPED_PALE_OAK, paleOakSaplingPlacement);

		// POPLAR
		final PoplarCollection<BlockPredicateFilter> poplarSaplingPlacement = WWBlocks.POPLAR_SAPLING.map(sapling -> PlacementUtils.filteredByBlockSurvival(sapling.get()));
		final Consumer<PoplarCollection<FrozenLibPlacedTreeFeature>> registerPoplar = collection -> {
			PoplarCollection.zipApply(
				collection,
				PoplarCollection.DYE_COLORS,
				(feature, color) -> feature.makeAndSetHolders(poplarSaplingPlacement.pick(color))
			);
		};

		registerPoplar.accept(POPLAR);
		registerPoplar.accept(DYING_POPLAR);
		registerPoplar.accept(TALL_POPLAR);
		registerPoplar.accept(TALL_DYING_POPLAR);
		registerPoplar.accept(POPLAR_BEES_0002);
		registerPoplar.accept(TALL_POPLAR_BEES_0002);

		SNAPPED_POPLAR.makeAndSetHolder(WWTreeFeatures.SNAPPED_POPLAR.getHolder(), fallenTreePlacement);

		// BUSH
		LARGE_BUSH.makeAndSetHolder(WWTreeFeatures.LARGE_BUSH, oakSaplingPlacement);
		BIG_BUSH.makeAndSetHolder(WWTreeFeatures.BIG_BUSH, oakSaplingPlacement);

		// CRIMSON
		FALLEN_CRIMSON.makeAndSetHolder(WWTreeFeatures.FALLEN_CRIMSON,
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM),
				BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.LAVA),
				12
			)
		);

		SNAPPED_CRIMSON.makeAndSetHolder(WWTreeFeatures.SNAPPED_CRIMSON,
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM),
				BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.LAVA),
				12
			)
		);

		// WARPED
		FALLEN_WARPED.makeAndSetHolder(WWTreeFeatures.FALLEN_WARPED,
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM),
				BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.LAVA),
				12
			)
		);

		SNAPPED_WARPED.makeAndSetHolder(WWTreeFeatures.SNAPPED_WARPED,
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			PlacementUtils.FULL_RANGE, BiomeFilter.biome(),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM),
				BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.LAVA),
				12
			)
		);
	}

	public static BlockPredicateFilter fallenTreePlacementFilter() {
		return BlockPredicateFilter.forPredicate(
			BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWFeatureBlockTags.FALLEN_TREE_PLACEABLE)
		);
	}

	public static FrozenLibPlacedFeature register(String name) {
		return new FrozenLibPlacedFeature(WWConstants.id("tree/" + name));
	}

	private WWTreePlacements() {}
}
