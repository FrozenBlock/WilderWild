package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import static net.minecraft.data.worldgen.placement.TreePlacements.SNOW_TREE_FILTER_DECORATOR;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WilderTreePlaced {
	private WilderTreePlaced() {
		throw new UnsupportedOperationException("WilderTreePlaced contains only static declarations.");
	}

    //BIRCH
    public static final ResourceKey<PlacedFeature> NEW_BIRCH_CHECKED = WilderPlacementUtils.createKey("new_birch_checked");
    public static final ResourceKey<PlacedFeature> NEW_BIRCH_BEES_0004 = WilderPlacementUtils.createKey("new_birch_bees_0004");
    public static final Holder<PlacedFeature> DYING_BIRCH = WilderPlacementUtils.register("dying_birch", WilderTreeConfigured.DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SHORT_BIRCH = WilderPlacementUtils.register("short_birch", WilderTreeConfigured.SHORT_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_SHORT_BIRCH = WilderPlacementUtils.register("dying_short_birch", WilderTreeConfigured.SHORT_DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SHORT_BIRCH_BEES_0004 = WilderPlacementUtils.register("new_short_birch_bees_0004", WilderTreeConfigured.NEW_SHORT_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> DYING_SUPER_BIRCH = WilderPlacementUtils.register("dying_super_birch", WilderTreeConfigured.DYING_SUPER_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SUPER_BIRCH_BEES_0004 = WilderPlacementUtils.register("new_super_birch_bees_0004", WilderTreeConfigured.NEW_SUPER_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SUPER_BIRCH_BEES = WilderPlacementUtils.register("new_super_birch_bees", WilderTreeConfigured.NEW_SUPER_BIRCH_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_FALLEN_BIRCH_CHECKED = WilderPlacementUtils.register("new_fallen_birch_checked", WilderTreeConfigured.NEW_FALLEN_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

    //OAK
    public static final Holder<PlacedFeature> NEW_OAK_CHECKED = WilderPlacementUtils.register("new_oak_checked", WilderTreeConfigured.NEW_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_OAK_CHECKED = WilderPlacementUtils.register("dying_oak_checked", WilderTreeConfigured.DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_OAK_BEES_0004 = WilderPlacementUtils.register("new_oak_bees_00004", WilderTreeConfigured.NEW_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> SHORT_OAK_CHECKED = WilderPlacementUtils.register("short_oak_checked", WilderTreeConfigured.SHORT_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FANCY_OAK_CHECKED = WilderPlacementUtils.register("new_fancy_oak_checked", WilderTreeConfigured.NEW_FANCY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_FANCY_OAK_CHECKED = WilderPlacementUtils.register("dying_fancy_oak_checked", WilderTreeConfigured.FANCY_DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_FANCY_OAK_BEES_0004 = WilderPlacementUtils.register("dying_fancy_oak_bees_0004", WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FANCY_OAK_BEES_0004 = WilderPlacementUtils.register("new_fancy_oak_bees_0004", WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FANCY_OAK_BEES = WilderPlacementUtils.register("new_fancy_oak_bees", WilderTreeConfigured.NEW_FANCY_OAK_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FALLEN_OAK_CHECKED = WilderPlacementUtils.register("new_fallen_oak_checked", WilderTreeConfigured.NEW_FALLEN_OAK_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

    //DARK OAK
    public static final Holder<PlacedFeature> NEW_TALL_DARK_OAK_CHECKED = WilderPlacementUtils.register("new_tall_dark_oak_checked", WilderTreeConfigured.NEW_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_TALL_DARK_OAK_CHECKED = WilderPlacementUtils.register("dying_tall_dark_oak_checked", WilderTreeConfigured.DYING_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
    public static final Holder<PlacedFeature> DYING_DARK_OAK_CHECKED = WilderPlacementUtils.register("dying_dark_oak_checked", WilderTreeConfigured.DYING_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));

    //SWAMP TREE
    public static final Holder<PlacedFeature> NEW_SWAMP_TREE_CHECKED = WilderPlacementUtils.register("new_swamp_tree_checked", WilderTreeConfigured.NEW_SWAMP_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));

    //SPRUCE
    public static final Holder<PlacedFeature> NEW_SPRUCE_CHECKED = WilderPlacementUtils.register("new_spruce_checked", WilderTreeConfigured.NEW_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> NEW_SPRUCE_ON_SNOW = WilderPlacementUtils.register("new_spruce_on_snow", WilderTreeConfigured.NEW_SPRUCE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> NEW_SPRUCE_SHORT_CHECKED = WilderPlacementUtils.register("new_spruce_short_checked", WilderTreeConfigured.NEW_SPRUCE_SHORT, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_CHECKED = WilderPlacementUtils.register("fungus_pine_checked", WilderTreeConfigured.FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> DYING_FUNGUS_PINE_CHECKED = WilderPlacementUtils.register("dying_fungus_pine_checked", WilderTreeConfigured.DYING_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_ON_SNOW = WilderPlacementUtils.register("fungus_pine_on_snow", WilderTreeConfigured.FUNGUS_PINE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> MEGA_FUNGUS_SPRUCE_CHECKED = WilderPlacementUtils.register("mega_fungus_spruce_checked", WilderTreeConfigured.MEGA_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> MEGA_FUNGUS_PINE_CHECKED = WilderPlacementUtils.register("mega_fungus_pine_checked", WilderTreeConfigured.MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> DYING_MEGA_FUNGUS_PINE_CHECKED = WilderPlacementUtils.register("dying_mega_fungus_pine_checked", WilderTreeConfigured.DYING_MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_SPRUCE_CHECKED = WilderPlacementUtils.register("fallen_spruce_checked", WilderTreeConfigured.FALLEN_SPRUCE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));

    //BAOBAB
    public static final Holder<PlacedFeature> BAOBAB = WilderPlacementUtils.register("baobab", WilderTreeConfigured.BAOBAB, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));
    public static final Holder<PlacedFeature> BAOBAB_TALL = WilderPlacementUtils.register("baobab_tall", WilderTreeConfigured.BAOBAB_TALL, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));

    //CYPRESS
    public static final Holder<PlacedFeature> CYPRESS = WilderPlacementUtils.register("cypress", WilderTreeConfigured.CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_CYPRESS = WilderPlacementUtils.register("fungus_cypress", WilderTreeConfigured.FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SHORT_CYPRESS = WilderPlacementUtils.register("short_cypress", WilderTreeConfigured.SHORT_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SHORT_FUNGUS_CYPRESS = WilderPlacementUtils.register("short_fungus_cypress", WilderTreeConfigured.SHORT_FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SWAMP_CYPRESS = WilderPlacementUtils.register("swamp_cypress", WilderTreeConfigured.SWAMP_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> NEW_FALLEN_CYPRESS_CHECKED = WilderPlacementUtils.register("new_fallen_cypress_checked", WilderTreeConfigured.NEW_FALLEN_CYPRESS_TREE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));

	public static void bootstrap(BootstapContext<PlacedFeature> bootstrapContext) {
		HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstrapContext.lookup(Registry.CONFIGURED_FEATURE_REGISTRY);
		WilderPlacementUtils.register(bootstrapContext, NEW_BIRCH_CHECKED, holderGetter.getOrThrow(WilderTreeConfigured.NEW_BIRCH_TREE), PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		WilderPlacementUtils.register(bootstrapContext, NEW_BIRCH_BEES_0004, holderGetter.getOrThrow(WilderTreeConfigured.NEW_BIRCH_BEES_0004), PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	}

    public static void registerTreePlaced() {
        WilderWild.logWild("Registering WilderTreePlaced for", true);
    }
}
