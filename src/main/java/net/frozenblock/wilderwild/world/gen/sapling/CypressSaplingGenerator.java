package net.frozenblock.wilderwild.world.gen.sapling;

import net.frozenblock.wilderwild.world.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CypressSaplingGenerator extends SaplingGenerator {

    public CypressSaplingGenerator() {
    }

    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        if (random.nextFloat() > 0.4F) {
            return random.nextFloat() > 0.7F ? WilderTreeConfigured.CYPRESS : WilderTreeConfigured.FUNGUS_CYPRESS;
        }
        return WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_SAPLING;
    }

}
