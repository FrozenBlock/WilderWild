package net.frozenblock.wilderwild.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;

import static net.minecraft.world.gen.feature.TreePlacedFeatures.ON_SNOW_MODIFIERS;

public class WildTreePlaced {
    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_BEES_0004;
    public static final RegistryEntry<PlacedFeature> NEW_SHORT_BIRCH_BEES_0004;
    public static final RegistryEntry<PlacedFeature> NEW_SUPER_BIRCH_BEES_0004;
    public static final RegistryEntry<PlacedFeature> NEW_FALLEN_BIRCH_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_OAK_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_FANCY_OAK_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_OAK_BEES_0004;
    public static final RegistryEntry<PlacedFeature> NEW_FANCY_OAK_BEES_0004;
    public static final RegistryEntry<PlacedFeature> NEW_FALLEN_OAK_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_TALL_DARK_OAK_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_SWAMP_TREE_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_SPRUCE_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_SPRUCE_SHORT_CHECKED;
    public static final RegistryEntry<PlacedFeature> FUNGUS_PINE_CHECKED;
    public static final RegistryEntry<PlacedFeature> FALLEN_SPRUCE_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_SPRUCE_ON_SNOW;
    public static final RegistryEntry<PlacedFeature> FUNGUS_PINE_ON_SNOW;
    public static final RegistryEntry<PlacedFeature> MEGA_FUNGUS_SPRUCE_CHECKED;
    public static final RegistryEntry<PlacedFeature> MEGA_FUNGUS_PINE_CHECKED;
    public static final RegistryEntry<PlacedFeature> SHORT_BIRCH;
    public static final RegistryEntry<PlacedFeature> NEW_FANCY_OAK_BEES;
    public static final RegistryEntry<PlacedFeature> NEW_SUPER_BIRCH_BEES;



    static {
        NEW_FANCY_OAK_BEES_0004 = PlacedFeatures.register("new_fancy_oak_bees_0004", WildTreeConfigured.NEW_FANCY_OAK_BEES_0004, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_OAK_BEES_0004 = PlacedFeatures.register("new_oak_bees_00004", WildTreeConfigured.NEW_OAK_BEES_0004, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_FANCY_OAK_CHECKED = PlacedFeatures.register("new_fancy_oak_checked", WildTreeConfigured.NEW_FANCY_OAK, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_OAK_CHECKED = PlacedFeatures.register("new_oak_checked", WildTreeConfigured.NEW_OAK, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_SUPER_BIRCH_BEES_0004 = PlacedFeatures.register("new_super_birch_bees_0004", WildTreeConfigured.NEW_SUPER_BIRCH_BEES_0004, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_BIRCH_CHECKED = PlacedFeatures.register("new_birch_checked", WildTreeConfigured.NEW_BIRCH_TREE, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_FALLEN_BIRCH_CHECKED = PlacedFeatures.register("new_fallen_birch_checked", WildTreeConfigured.NEW_FALLEN_BIRCH_TREE, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_BIRCH_BEES_0004 = PlacedFeatures.register("new_birch_bees_0004", WildTreeConfigured.NEW_BIRCH_BEES_0004, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_SHORT_BIRCH_BEES_0004 = PlacedFeatures.register("new_short_birch_bees_0004", WildTreeConfigured.NEW_SHORT_BIRCH_BEES_0004, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_FALLEN_OAK_CHECKED = PlacedFeatures.register("new_fallen_oak_checked", WildTreeConfigured.NEW_FALLEN_OAK_TREE, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_TALL_DARK_OAK_CHECKED = PlacedFeatures.register("new_tall_dark_oak_checked", WildTreeConfigured.NEW_TALL_DARK_OAK, PlacedFeatures.wouldSurvive(Blocks.DARK_OAK_SAPLING));
        NEW_SWAMP_TREE_CHECKED = PlacedFeatures.register("new_swamp_tree_checked", WildTreeConfigured.NEW_SWAMP_TREE, PlacedFeatures.wouldSurvive(Blocks.MANGROVE_PROPAGULE));
        NEW_SPRUCE_CHECKED = PlacedFeatures.register("new_spruce_checked", WildTreeConfigured.NEW_SPRUCE, PlacedFeatures.wouldSurvive(Blocks.SPRUCE_SAPLING));
        NEW_SPRUCE_SHORT_CHECKED = PlacedFeatures.register("new_spruce_short_checked", WildTreeConfigured.NEW_SPRUCE_SHORT, PlacedFeatures.wouldSurvive(Blocks.SPRUCE_SAPLING));
        FUNGUS_PINE_CHECKED = PlacedFeatures.register("fungus_pine_checked", WildTreeConfigured.FUNGUS_PINE, PlacedFeatures.wouldSurvive(Blocks.SPRUCE_SAPLING));
        FALLEN_SPRUCE_CHECKED = PlacedFeatures.register("fallen_spruce_checked", WildTreeConfigured.FALLEN_SPRUCE_TREE, PlacedFeatures.wouldSurvive(Blocks.SPRUCE_SAPLING));
        NEW_SPRUCE_ON_SNOW = PlacedFeatures.register("new_spruce_on_snow", WildTreeConfigured.NEW_SPRUCE, ON_SNOW_MODIFIERS);
        FUNGUS_PINE_ON_SNOW = PlacedFeatures.register("fungus_pine_on_snow", WildTreeConfigured.FUNGUS_PINE, ON_SNOW_MODIFIERS);
        MEGA_FUNGUS_SPRUCE_CHECKED = PlacedFeatures.register("mega_fungus_spruce_checked", WildTreeConfigured.MEGA_FUNGUS_SPRUCE, PlacedFeatures.wouldSurvive(Blocks.SPRUCE_SAPLING));
        MEGA_FUNGUS_PINE_CHECKED = PlacedFeatures.register("mega_fungus_pine_checked", WildTreeConfigured.MEGA_FUNGUS_PINE, PlacedFeatures.wouldSurvive(Blocks.SPRUCE_SAPLING));
        SHORT_BIRCH = PlacedFeatures.register("short_birch", WildTreeConfigured.SHORT_BIRCH, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_FANCY_OAK_BEES = PlacedFeatures.register("new_fancy_oak_bees", WildTreeConfigured.NEW_FANCY_OAK_BEES, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_SUPER_BIRCH_BEES = PlacedFeatures.register("new_super_birch_bees", WildTreeConfigured.NEW_SUPER_BIRCH_BEES, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    }

    public static void registerTreePlaced() {
    }
}
