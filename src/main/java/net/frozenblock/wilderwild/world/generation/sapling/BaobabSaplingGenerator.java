package net.frozenblock.wilderwild.world.generation.sapling;

import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class BaobabSaplingGenerator extends BaobabTreeSaplingGenerator {

    public BaobabSaplingGenerator() {
    }


    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getBaobabTreeFeature(RandomSource random) {
        return random.nextFloat() < 0.856F ? WilderTreeConfigured.BAOBAB : WilderTreeConfigured.BAOBAB_TALL;
    }

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
        return null;
    }
}
