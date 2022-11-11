package net.frozenblock.wilderwild.world.feature;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.util.FrozenPlacementUtils;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
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

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		WilderWild.logWild("Registering WilderTreePlaced for", true);
		register(entries, BIRCH_CHECKED, WilderTreeConfigured.BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, BIRCH_BEES_0004, WilderTreeConfigured.BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, DYING_BIRCH, WilderTreeConfigured.DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, SHORT_BIRCH, WilderTreeConfigured.SHORT_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, DYING_SHORT_BIRCH, WilderTreeConfigured.SHORT_DYING_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, SHORT_BIRCH_BEES_0004, WilderTreeConfigured.SHORT_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, DYING_SUPER_BIRCH, WilderTreeConfigured.DYING_SUPER_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, SUPER_BIRCH_BEES_0004, WilderTreeConfigured.SUPER_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, SUPER_BIRCH_BEES, WilderTreeConfigured.SUPER_BIRCH_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, FALLEN_BIRCH_CHECKED, WilderTreeConfigured.FALLEN_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
		register(entries, OAK_CHECKED, WilderTreeConfigured.OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, DYING_OAK_CHECKED, WilderTreeConfigured.DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, OAK_BEES_0004, WilderTreeConfigured.OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, SHORT_OAK_CHECKED, WilderTreeConfigured.SHORT_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, FANCY_OAK_CHECKED, WilderTreeConfigured.FANCY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, DYING_FANCY_OAK_CHECKED, WilderTreeConfigured.FANCY_DYING_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, DYING_FANCY_OAK_BEES_0004, WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, FANCY_OAK_BEES_0004, WilderTreeConfigured.FANCY_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, FANCY_OAK_BEES, WilderTreeConfigured.FANCY_OAK_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, FALLEN_OAK_CHECKED, WilderTreeConfigured.FALLEN_OAK_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		register(entries, TALL_DARK_OAK_CHECKED, WilderTreeConfigured.TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
		register(entries, DYING_TALL_DARK_OAK_CHECKED, WilderTreeConfigured.DYING_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
		register(entries, DYING_DARK_OAK_CHECKED, WilderTreeConfigured.DYING_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
		register(entries, SWAMP_TREE_CHECKED, WilderTreeConfigured.SWAMP_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));
		register(entries, SPRUCE_CHECKED, WilderTreeConfigured.SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		register(entries, SPRUCE_ON_SNOW, WilderTreeConfigured.SPRUCE, SNOW_TREE_FILTER_DECORATOR);
		register(entries, SPRUCE_SHORT_CHECKED, WilderTreeConfigured.SPRUCE_SHORT, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		register(entries, FUNGUS_PINE_CHECKED, WilderTreeConfigured.FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		register(entries, DYING_FUNGUS_PINE_CHECKED, WilderTreeConfigured.DYING_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		register(entries, FUNGUS_PINE_ON_SNOW, WilderTreeConfigured.FUNGUS_PINE, SNOW_TREE_FILTER_DECORATOR);
		register(entries, MEGA_FUNGUS_SPRUCE_CHECKED, WilderTreeConfigured.MEGA_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		register(entries, MEGA_FUNGUS_PINE_CHECKED, WilderTreeConfigured.MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		register(entries, DYING_MEGA_FUNGUS_PINE_CHECKED, WilderTreeConfigured.DYING_MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		register(entries, FALLEN_SPRUCE_CHECKED, WilderTreeConfigured.FALLEN_SPRUCE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
		register(entries, BAOBAB, WilderTreeConfigured.BAOBAB, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));
		register(entries, BAOBAB_TALL, WilderTreeConfigured.BAOBAB_TALL, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_NUT));
		register(entries, CYPRESS, WilderTreeConfigured.CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		register(entries, FUNGUS_CYPRESS, WilderTreeConfigured.FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		register(entries, SHORT_CYPRESS, WilderTreeConfigured.SHORT_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		register(entries, SHORT_FUNGUS_CYPRESS, WilderTreeConfigured.SHORT_FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		register(entries, SWAMP_CYPRESS, WilderTreeConfigured.SWAMP_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
		register(entries, FALLEN_CYPRESS_CHECKED, WilderTreeConfigured.FALLEN_CYPRESS_TREE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
	}

	public static ResourceKey<PlacedFeature> key(String path) {
		return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, WilderSharedConstants.id(path));
	}

	private static void register(FabricWorldgenProvider.Entries entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, PlacementModifier... modifiers) {
		register(entries, resourceKey, configuredResourceKey, Arrays.asList(modifiers));
	}

	private static void register(FabricWorldgenProvider.Entries entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, List<PlacementModifier> modifiers) {
		FrozenPlacementUtils.register(entries, resourceKey, entries.getLookup(Registry.CONFIGURED_FEATURE_REGISTRY).getOrThrow(configuredResourceKey), modifiers);
	}
}
