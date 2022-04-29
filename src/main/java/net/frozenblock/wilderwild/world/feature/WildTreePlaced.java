package net.frozenblock.wilderwild.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

public class WildTreePlaced {
    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_BEES_0002;
    public static final RegistryEntry<PlacedFeature> NEW_SUPER_BIRCH_BEES_0002;
    
    static {
        NEW_SUPER_BIRCH_BEES_0002 = PlacedFeatures.register("new_super_birch_bees_0002", WildTreeConfigured.NEW_SUPER_BIRCH_BEES_0002, new PlacementModifier[]{PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING)});
        
    NEW_BIRCH_CHECKED = PlacedFeatures.register("new_birch_checked", WildTreeConfigured.NEW_BIRCH_TREE,
                    PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

    NEW_BIRCH_BEES_0002 = PlacedFeatures.register("new_birch_bees_0002", WildTreeConfigured.NEW_BIRCH_BEES_0002, new PlacementModifier[]{PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING)});
    }


    public static void registerTreePlaced() {
    }
}

