package net.frozenblock.wilderwild.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

public class WildMiscPlaced {
    public static final RegistryEntry<PlacedFeature> DISK_MUD;

    public WildMiscPlaced() {
    }
    static {
        DISK_MUD = PlacedFeatures.register("disk_mud", WildMiscConfigured.DISK_MUD, new PlacementModifier[]{CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BlockFilterPlacementModifier.of(BlockPredicate.matchingBlocks(new Block[]{Blocks.GRASS_BLOCK, Blocks.DIRT})), BiomePlacementModifier.of()});
    }
}
