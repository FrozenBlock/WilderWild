package net.frozenblock.wilderwild.world.additions.feature;

import java.util.List;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import static net.minecraft.data.worldgen.placement.TreePlacements.SNOW_TREE_FILTER_DECORATOR;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class WilderTreePlaced {
	private WilderTreePlaced() {
		throw new UnsupportedOperationException("WilderTreePlaced contains only static declarations.");
	}

    //BIRCH
    public static final Holder<PlacedFeature> NEW_BIRCH_CHECKED = PlacementUtils.register("new_birch_checked", WilderTreeConfigured.NEW_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_BIRCH_BEES_0004 = PlacementUtils.register("new_birch_bees_0004", WilderTreeConfigured.NEW_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> NEW_BIRCH_BEES_025 = PlacementUtils.register("new_birch_bees_025", WilderTreeConfigured.NEW_BIRCH_BEES_025, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> DYING_BIRCH = PlacementUtils.register("dying_birch", WilderTreeConfigured.DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SHORT_BIRCH = PlacementUtils.register("short_birch", WilderTreeConfigured.SHORT_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_SHORT_BIRCH = PlacementUtils.register("dying_short_birch", WilderTreeConfigured.SHORT_DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SHORT_BIRCH_BEES_0004 = PlacementUtils.register("new_short_birch_bees_0004", WilderTreeConfigured.NEW_SHORT_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_SUPER_BIRCH = PlacementUtils.register("dying_super_birch", WilderTreeConfigured.DYING_SUPER_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SUPER_BIRCH_BEES_0004 = PlacementUtils.register("new_super_birch_bees_0004", WilderTreeConfigured.NEW_SUPER_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SUPER_BIRCH_BEES = PlacementUtils.register("new_super_birch_bees", WilderTreeConfigured.NEW_SUPER_BIRCH_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> NEW_SUPER_BIRCH = PlacementUtils.register("new_super_birch", WilderTreeConfigured.NEW_SUPER_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> NEW_FALLEN_BIRCH_CHECKED = PlacementUtils.register("new_fallen_birch_checked", WilderTreeConfigured.NEW_FALLEN_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

    //OAK
    public static final Holder<PlacedFeature> NEW_OAK_CHECKED = PlacementUtils.register("new_oak_checked", WilderTreeConfigured.NEW_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_OAK_CHECKED = PlacementUtils.register("dying_oak_checked", WilderTreeConfigured.DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_OAK_BEES_0004 = PlacementUtils.register("new_oak_bees_00004", WilderTreeConfigured.NEW_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> SHORT_OAK_CHECKED = PlacementUtils.register("short_oak_checked", WilderTreeConfigured.SHORT_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FANCY_OAK_CHECKED = PlacementUtils.register("new_fancy_oak_checked", WilderTreeConfigured.NEW_FANCY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_FANCY_OAK_CHECKED = PlacementUtils.register("dying_fancy_oak_checked", WilderTreeConfigured.FANCY_DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_FANCY_OAK_BEES_0004 = PlacementUtils.register("dying_fancy_oak_bees_0004", WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FANCY_OAK_BEES_0004 = PlacementUtils.register("new_fancy_oak_bees_0004", WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> DYING_FANCY_OAK_BEES_025 = PlacementUtils.register("dying_fancy_oak_bees_025", WilderTreeConfigured.FANCY_DYING_OAK_BEES_025, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> NEW_FANCY_OAK_BEES_025 = PlacementUtils.register("new_fancy_oak_bees_025", WilderTreeConfigured.NEW_FANCY_OAK_BEES_025, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> NEW_FANCY_OAK_BEES = PlacementUtils.register("new_fancy_oak_bees", WilderTreeConfigured.NEW_FANCY_OAK_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FALLEN_OAK_CHECKED = PlacementUtils.register("new_fallen_oak_checked", WilderTreeConfigured.NEW_FALLEN_OAK_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

    //DARK OAK
    public static final Holder<PlacedFeature> NEW_TALL_DARK_OAK_CHECKED = PlacementUtils.register("new_tall_dark_oak_checked", WilderTreeConfigured.NEW_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_TALL_DARK_OAK_CHECKED = PlacementUtils.register("dying_tall_dark_oak_checked", WilderTreeConfigured.DYING_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_DARK_OAK_CHECKED = PlacementUtils.register("dying_dark_oak_checked", WilderTreeConfigured.DYING_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));

    //SWAMP TREE
    public static final Holder<PlacedFeature> NEW_SWAMP_TREE_CHECKED = PlacementUtils.register("new_swamp_tree_checked", WilderTreeConfigured.NEW_SWAMP_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));

    //SPRUCE
    public static final Holder<PlacedFeature> NEW_SPRUCE_CHECKED = PlacementUtils.register("new_spruce_checked", WilderTreeConfigured.NEW_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> NEW_SPRUCE_ON_SNOW = PlacementUtils.register("new_spruce_on_snow", WilderTreeConfigured.NEW_SPRUCE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> NEW_SPRUCE_SHORT_CHECKED = PlacementUtils.register("new_spruce_short_checked", WilderTreeConfigured.NEW_SPRUCE_SHORT, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_CHECKED = PlacementUtils.register("fungus_pine_checked", WilderTreeConfigured.FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> DYING_FUNGUS_PINE_CHECKED = PlacementUtils.register("dying_fungus_pine_checked", WilderTreeConfigured.DYING_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_ON_SNOW = PlacementUtils.register("fungus_pine_on_snow", WilderTreeConfigured.FUNGUS_PINE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> MEGA_FUNGUS_SPRUCE_CHECKED = PlacementUtils.register("mega_fungus_spruce_checked", WilderTreeConfigured.MEGA_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> MEGA_FUNGUS_PINE_CHECKED = PlacementUtils.register("mega_fungus_pine_checked", WilderTreeConfigured.MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> DYING_MEGA_FUNGUS_PINE_CHECKED = PlacementUtils.register("dying_mega_fungus_pine_checked", WilderTreeConfigured.DYING_MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_SPRUCE_CHECKED = PlacementUtils.register("fallen_spruce_checked", WilderTreeConfigured.FALLEN_SPRUCE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));

    //BAOBAB
    public static final Holder<PlacedFeature> BAOBAB = PlacementUtils.register("baobab", WilderTreeConfigured.BAOBAB, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));
    public static final Holder<PlacedFeature> BAOBAB_TALL = PlacementUtils.register("baobab_tall", WilderTreeConfigured.BAOBAB_TALL, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));

    //CYPRESS
    public static final Holder<PlacedFeature> CYPRESS = PlacementUtils.register("cypress", WilderTreeConfigured.CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_CYPRESS = PlacementUtils.register("fungus_cypress", WilderTreeConfigured.FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SHORT_CYPRESS = PlacementUtils.register("short_cypress", WilderTreeConfigured.SHORT_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SHORT_FUNGUS_CYPRESS = PlacementUtils.register("short_fungus_cypress", WilderTreeConfigured.SHORT_FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SWAMP_CYPRESS = PlacementUtils.register("swamp_cypress", WilderTreeConfigured.SWAMP_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> NEW_FALLEN_CYPRESS_CHECKED = PlacementUtils.register("new_fallen_cypress_checked", WilderTreeConfigured.NEW_FALLEN_CYPRESS_TREE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
	//TREE ON SAND
	public static final BlockPredicate SAND_GRASS_TREE_PREDICATE = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.RED_SAND, Blocks.SAND, Blocks.GRASS);
	public static final List<PlacementModifier> SAND_TREE_FILTER_DECORATOR = List.of(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.SANDSTONE)), 8), BlockPredicateFilter.forPredicate(SAND_GRASS_TREE_PREDICATE));
	//SHRUB
	public static final Holder<PlacedFeature> BIG_SHRUB_CHECKED = PlacementUtils.register("big_shrub_checked", WilderTreeConfigured.BIG_SHRUB, SAND_TREE_FILTER_DECORATOR);
	//PALM
	public static final Holder<PlacedFeature> PALM_CHECKED = PlacementUtils.register("palm_checked", WilderTreeConfigured.PALM, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
	public static final Holder<PlacedFeature> TALL_PALM_CHECKED = PlacementUtils.register("tall_palm_checked", WilderTreeConfigured.TALL_PALM, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
	public static final Holder<PlacedFeature> TALL_WINE_PALM_CHECKED = PlacementUtils.register("tall_wine_palm_checked", WilderTreeConfigured.TALL_WINE_PALM, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
	public static final Holder<PlacedFeature> SMALL_WINE_PALM_CHECKED = PlacementUtils.register("small_wine_palm_checked", WilderTreeConfigured.SMALL_WINE_PALM, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.COCONUT));
	//TREE ON GRASS
	public static final Holder<PlacedFeature> PALM_CHECKED_DIRT = PlacementUtils.register("palm_checked_dirt", WilderTreeConfigured.PALM, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> TALL_PALM_CHECKED_DIRT = PlacementUtils.register("tall_palm_checked_dirt", WilderTreeConfigured.TALL_PALM, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> TALL_WINE_PALM_CHECKED_DIRT = PlacementUtils.register("tall_wine_palm_checked_dirt", WilderTreeConfigured.TALL_WINE_PALM, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> SMALL_WINE_PALM_CHECKED_DIRT = PlacementUtils.register("small_wine_palm_checked_dirt", WilderTreeConfigured.SMALL_WINE_PALM, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

	public static void registerTreePlaced() {
        WilderSharedConstants.logWild("Registering WilderTreePlaced for", true);
    }
}
