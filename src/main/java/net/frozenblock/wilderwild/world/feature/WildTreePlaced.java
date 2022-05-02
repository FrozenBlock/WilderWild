package net.frozenblock.wilderwild.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;

public class WildTreePlaced {
    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_BEES_0002;
    public static final RegistryEntry<PlacedFeature> NEW_SUPER_BIRCH_BEES_0002;
    public static final RegistryEntry<PlacedFeature> NEW_FALLEN_BIRCH_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_OAK_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_FANCY_OAK_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_OAK_BEES_0002;
    public static final RegistryEntry<PlacedFeature> NEW_FANCY_OAK_BEES_0002;
    static {
        NEW_FANCY_OAK_BEES_0002 = PlacedFeatures.register("new_fancy_oak_bees_0002", WildTreeConfigured.NEW_FANCY_OAK_BEES_0002, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_OAK_BEES_0002 = PlacedFeatures.register("new_oak_bees_0002", WildTreeConfigured.NEW_OAK_BEES_0002, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_FANCY_OAK_CHECKED = PlacedFeatures.register("new_fancy_oak_checked", WildTreeConfigured.NEW_FANCY_OAK, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_OAK_CHECKED = PlacedFeatures.register("new_oak_checked", WildTreeConfigured.NEW_OAK, PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING));
        NEW_SUPER_BIRCH_BEES_0002 = PlacedFeatures.register("new_super_birch_bees_0002", WildTreeConfigured.NEW_SUPER_BIRCH_BEES_0002, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_BIRCH_CHECKED = PlacedFeatures.register("new_birch_checked", WildTreeConfigured.NEW_BIRCH_TREE, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_FALLEN_BIRCH_CHECKED = PlacedFeatures.register("new_fallen_birch_checked", WildTreeConfigured.NEW_FALLEN_BIRCH_TREE, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
        NEW_BIRCH_BEES_0002 = PlacedFeatures.register("new_birch_bees_0002", WildTreeConfigured.NEW_BIRCH_BEES_0002, PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));
    }

    public static void registerTreePlaced() {
    }
}

