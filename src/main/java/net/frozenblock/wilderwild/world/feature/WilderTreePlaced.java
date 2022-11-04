package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.lib.worldgen.feature.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.FrozenPlacedFeature;
import net.frozenblock.lib.worldgen.feature.util.FrozenPlacementUtils;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import static net.minecraft.data.worldgen.placement.TreePlacements.SNOW_TREE_FILTER_DECORATOR;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class WilderTreePlaced {
	private WilderTreePlaced() {
		throw new UnsupportedOperationException("WilderTreePlaced contains only static declarations.");
	}

    //BIRCH
    public static final FrozenPlacedFeature NEW_BIRCH_CHECKED = placedFeature("new_birch_checked", WilderTreeConfigured.NEW_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature NEW_BIRCH_BEES_0004 = placedFeature("new_birch_bees_0004", WilderTreeConfigured.NEW_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature DYING_BIRCH = placedFeature("dying_birch", WilderTreeConfigured.DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature SHORT_BIRCH = placedFeature("short_birch", WilderTreeConfigured.SHORT_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature DYING_SHORT_BIRCH = placedFeature("dying_short_birch", WilderTreeConfigured.SHORT_DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature NEW_SHORT_BIRCH_BEES_0004 = placedFeature("new_short_birch_bees_0004", WilderTreeConfigured.NEW_SHORT_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature DYING_SUPER_BIRCH = placedFeature("dying_super_birch", WilderTreeConfigured.DYING_SUPER_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature NEW_SUPER_BIRCH_BEES_0004 = placedFeature("new_super_birch_bees_0004", WilderTreeConfigured.NEW_SUPER_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature NEW_SUPER_BIRCH_BEES = placedFeature("new_super_birch_bees", WilderTreeConfigured.NEW_SUPER_BIRCH_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final FrozenPlacedFeature NEW_FALLEN_BIRCH_CHECKED = placedFeature("new_fallen_birch_checked", WilderTreeConfigured.NEW_FALLEN_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

    //OAK
    public static final FrozenPlacedFeature NEW_OAK_CHECKED = placedFeature("new_oak_checked", WilderTreeConfigured.NEW_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature DYING_OAK_CHECKED = placedFeature("dying_oak_checked", WilderTreeConfigured.DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature NEW_OAK_BEES_0004 = placedFeature("new_oak_bees_00004", WilderTreeConfigured.NEW_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature SHORT_OAK_CHECKED = placedFeature("short_oak_checked", WilderTreeConfigured.SHORT_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature NEW_FANCY_OAK_CHECKED = placedFeature("new_fancy_oak_checked", WilderTreeConfigured.NEW_FANCY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature DYING_FANCY_OAK_CHECKED = placedFeature("dying_fancy_oak_checked", WilderTreeConfigured.FANCY_DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature DYING_FANCY_OAK_BEES_0004 = placedFeature("dying_fancy_oak_bees_0004", WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature NEW_FANCY_OAK_BEES_0004 = placedFeature("new_fancy_oak_bees_0004", WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature NEW_FANCY_OAK_BEES = placedFeature("new_fancy_oak_bees", WilderTreeConfigured.NEW_FANCY_OAK_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final FrozenPlacedFeature NEW_FALLEN_OAK_CHECKED = placedFeature("new_fallen_oak_checked", WilderTreeConfigured.NEW_FALLEN_OAK_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

    //DARK OAK
    public static final FrozenPlacedFeature NEW_TALL_DARK_OAK_CHECKED = placedFeature("new_tall_dark_oak_checked", WilderTreeConfigured.NEW_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final FrozenPlacedFeature DYING_TALL_DARK_OAK_CHECKED = placedFeature("dying_tall_dark_oak_checked", WilderTreeConfigured.DYING_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final FrozenPlacedFeature DYING_DARK_OAK_CHECKED = placedFeature("dying_dark_oak_checked", WilderTreeConfigured.DYING_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));

    //SWAMP TREE
    public static final FrozenPlacedFeature NEW_SWAMP_TREE_CHECKED = placedFeature("new_swamp_tree_checked", WilderTreeConfigured.NEW_SWAMP_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));

    //SPRUCE
    public static final FrozenPlacedFeature NEW_SPRUCE_CHECKED = placedFeature("new_spruce_checked", WilderTreeConfigured.NEW_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final FrozenPlacedFeature NEW_SPRUCE_ON_SNOW = placedFeature("new_spruce_on_snow", WilderTreeConfigured.NEW_SPRUCE, ON_SNOW);
    public static final FrozenPlacedFeature NEW_SPRUCE_SHORT_CHECKED = placedFeature("new_spruce_short_checked", WilderTreeConfigured.NEW_SPRUCE_SHORT, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final FrozenPlacedFeature FUNGUS_PINE_CHECKED = placedFeature("fungus_pine_checked", WilderTreeConfigured.FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final FrozenPlacedFeature DYING_FUNGUS_PINE_CHECKED = placedFeature("dying_fungus_pine_checked", WilderTreeConfigured.DYING_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final FrozenPlacedFeature FUNGUS_PINE_ON_SNOW = placedFeature("fungus_pine_on_snow", WilderTreeConfigured.FUNGUS_PINE, SNOW_TREE_FILTER_DECORATOR);
    public static final FrozenPlacedFeature MEGA_FUNGUS_SPRUCE_CHECKED = placedFeature("mega_fungus_spruce_checked", WilderTreeConfigured.MEGA_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final FrozenPlacedFeature MEGA_FUNGUS_PINE_CHECKED = placedFeature("mega_fungus_pine_checked", WilderTreeConfigured.MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final FrozenPlacedFeature DYING_MEGA_FUNGUS_PINE_CHECKED = placedFeature("dying_mega_fungus_pine_checked", WilderTreeConfigured.DYING_MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final FrozenPlacedFeature FALLEN_SPRUCE_CHECKED = placedFeature("fallen_spruce_checked", WilderTreeConfigured.FALLEN_SPRUCE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));

    //BAOBAB
    public static final FrozenPlacedFeature BAOBAB = placedFeature("baobab", WilderTreeConfigured.BAOBAB, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));
    public static final FrozenPlacedFeature BAOBAB_TALL = placedFeature("baobab_tall", WilderTreeConfigured.BAOBAB_TALL, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));

    //CYPRESS
    public static final FrozenPlacedFeature CYPRESS = placedFeature("cypress", WilderTreeConfigured.CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final FrozenPlacedFeature FUNGUS_CYPRESS = placedFeature("fungus_cypress", WilderTreeConfigured.FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final FrozenPlacedFeature SHORT_CYPRESS = placedFeature("short_cypress", WilderTreeConfigured.SHORT_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final FrozenPlacedFeature SHORT_FUNGUS_CYPRESS = placedFeature("short_fungus_cypress", WilderTreeConfigured.SHORT_FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final FrozenPlacedFeature SWAMP_CYPRESS = placedFeature("swamp_cypress", WilderTreeConfigured.SWAMP_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final FrozenPlacedFeature NEW_FALLEN_CYPRESS_CHECKED = placedFeature("new_fallen_cypress_checked", WilderTreeConfigured.NEW_FALLEN_CYPRESS_TREE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));

    public static void registerTreePlaced() {
        WilderWild.logWild("Registering WilderTreePlaced for", true);
    }

	private static FrozenPlacedFeature placedFeature(String id, FrozenConfiguredFeature feature, PlacementModifier... placementModifiers) {
		return FrozenPlacementUtils.placedFeature(WilderSharedConstants.MOD_ID, id, feature, placementModifiers);
	}
}
