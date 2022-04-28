package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountMultilayerPlacementModifier;
import net.minecraft.world.gen.placementmodifier.EnvironmentScanPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

public class WildTreePlaced {
    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_CHECKED;
    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_BEES_0002;
    
    static {
        
    NEW_BIRCH_CHECKED = PlacedFeatures.register("new_birch_checked", WildTreeConfigured.NEW_BIRCH_TREE,
                    PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING));

    NEW_BIRCH_BEES_0002 = PlacedFeatures.register("new_birch_bees_0002", TreeConfiguredFeatures.OAK_BEES_0002, new PlacementModifier[]{PlacedFeatures.wouldSurvive(Blocks.BIRCH_SAPLING)});
    }

    public static void registerTreePlaced() {
    }
}

