package net.frozenblock.wilderwild.world.gen.sapling;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
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
        return random.nextFloat() < 0.856F ? WilderTreeConfigured.BAOBAB.getResourceKey() : WilderTreeConfigured.BAOBAB_TALL.getResourceKey();
    }

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
        return null;
    }
}
