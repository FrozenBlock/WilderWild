package net.frozenblock.wilderwild.world.gen.sapling;

import net.frozenblock.wilderwild.world.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CypressSaplingGenerator extends AbstractTreeGrower {

    public CypressSaplingGenerator() {
    }

    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
        if (random.nextFloat() > 0.4F) {
            return random.nextFloat() > 0.7F ? WilderTreeConfigured.CYPRESS.getResourceKey() : WilderTreeConfigured.FUNGUS_CYPRESS.getResourceKey();
        }
        return WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_SAPLING;
    }

}
