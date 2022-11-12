package net.frozenblock.wilderwild.world.feature;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacementUtils;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class WilderTreePlaced {
	private WilderTreePlaced() {
		throw new UnsupportedOperationException("WilderTreePlaced contains only static declarations.");
	}

	public static final List<PlacementModifier> SNOW_TREE_FILTER_DECORATOR = List.of(
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW))
	);

    //BIRCH
    public static final ResourceKey<PlacedFeature> BIRCH_CHECKED = key("birch_checked");
    public static final ResourceKey<PlacedFeature> BIRCH_BEES_0004 = key("birch_bees_0004");
    public static final ResourceKey<PlacedFeature> DYING_BIRCH = key("dying_birch");
    public static final ResourceKey<PlacedFeature> SHORT_BIRCH = key("short_birch");
    public static final ResourceKey<PlacedFeature> DYING_SHORT_BIRCH = key("dying_short_birch");
    public static final ResourceKey<PlacedFeature> SHORT_BIRCH_BEES_0004 = key("short_birch_bees_0004");
    public static final ResourceKey<PlacedFeature> DYING_SUPER_BIRCH = key("dying_super_birch");
    public static final ResourceKey<PlacedFeature> SUPER_BIRCH_BEES_0004 = key("super_birch_bees_0004");
    public static final ResourceKey<PlacedFeature> SUPER_BIRCH_BEES = key("super_birch_bees");
    public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_CHECKED = key("fallen_birch_checked");

    //OAK
    public static final ResourceKey<PlacedFeature> OAK_CHECKED = key("oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_OAK_CHECKED = key("dying_oak_checked");
    public static final ResourceKey<PlacedFeature> OAK_BEES_0004 = key("oak_bees_00004");
    public static final ResourceKey<PlacedFeature> SHORT_OAK_CHECKED = key("short_oak_checked");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_CHECKED = key("fancy_oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_FANCY_OAK_CHECKED = key("dying_fancy_oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_FANCY_OAK_BEES_0004 = key("dying_fancy_oak_bees_0004");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES_0004 = key("fancy_oak_bees_0004");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES = key("fancy_oak_bees");
    public static final ResourceKey<PlacedFeature> FALLEN_OAK_CHECKED = key("fallen_oak_checked");

    //DARK OAK
    public static final ResourceKey<PlacedFeature> TALL_DARK_OAK_CHECKED = key("tall_dark_oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_TALL_DARK_OAK_CHECKED = key("dying_tall_dark_oak_checked");
    public static final ResourceKey<PlacedFeature> DYING_DARK_OAK_CHECKED = key("dying_dark_oak_checked");

    //SWAMP TREE
    public static final ResourceKey<PlacedFeature> SWAMP_TREE_CHECKED = key("swamp_tree_checked");

    //SPRUCE
    public static final ResourceKey<PlacedFeature> SPRUCE_CHECKED = key("spruce_checked");
    public static final ResourceKey<PlacedFeature> SPRUCE_ON_SNOW = key("spruce_on_snow");
    public static final ResourceKey<PlacedFeature> SPRUCE_SHORT_CHECKED = key("spruce_short_checked");
    public static final ResourceKey<PlacedFeature> FUNGUS_PINE_CHECKED = key("fungus_pine_checked");
    public static final ResourceKey<PlacedFeature> DYING_FUNGUS_PINE_CHECKED = key("dying_fungus_pine_checked");
    public static final ResourceKey<PlacedFeature> FUNGUS_PINE_ON_SNOW = key("fungus_pine_on_snow");
    public static final ResourceKey<PlacedFeature> MEGA_FUNGUS_SPRUCE_CHECKED = key("mega_fungus_spruce_checked");
    public static final ResourceKey<PlacedFeature> MEGA_FUNGUS_PINE_CHECKED = key("mega_fungus_pine_checked");
    public static final ResourceKey<PlacedFeature> DYING_MEGA_FUNGUS_PINE_CHECKED = key("dying_mega_fungus_pine_checked");
    public static final ResourceKey<PlacedFeature> FALLEN_SPRUCE_CHECKED = key("fallen_spruce_checked");

    //BAOBAB
    public static final ResourceKey<PlacedFeature> BAOBAB = key("baobab");
    public static final ResourceKey<PlacedFeature> BAOBAB_TALL = key("baobab_tall");

    //CYPRESS
    public static final ResourceKey<PlacedFeature> CYPRESS = key("cypress");
    public static final ResourceKey<PlacedFeature> FUNGUS_CYPRESS = key("fungus_cypress");
    public static final ResourceKey<PlacedFeature> SHORT_CYPRESS = key("short_cypress");
    public static final ResourceKey<PlacedFeature> SHORT_FUNGUS_CYPRESS = key("short_fungus_cypress");
    public static final ResourceKey<PlacedFeature> SWAMP_CYPRESS = key("swamp_cypress");
    public static final ResourceKey<PlacedFeature> FALLEN_CYPRESS_CHECKED = key("fallen_cypress_checked");

	public static ResourceKey<PlacedFeature> key(String path) {
		return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, WilderSharedConstants.id(path));
	}
}
