package net.frozenblock.wilderwild.world.gen.sapling;

import net.frozenblock.wilderwild.world.feature.WildTreeConfigured;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CypressSaplingGenerator extends SaplingGenerator {

    public CypressSaplingGenerator() {}

    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return random.nextFloat()>0.7F ? WildTreeConfigured.CYPRESS : WildTreeConfigured.FUNGUS_CYPRESS;
    }

}
