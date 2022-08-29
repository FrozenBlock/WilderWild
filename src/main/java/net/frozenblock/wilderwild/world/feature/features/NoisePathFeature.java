package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.lib.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

public class NoisePathFeature extends Feature<PathFeatureConfig> {
    public NoisePathFeature(Codec<PathFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<PathFeatureConfig> context) {
        boolean generated = false;
        PathFeatureConfig config = context.config();
        BlockPos blockPos = context.origin();
        WorldGenLevel world = context.level();
        int radiusSquared = config.radius * config.radius;
        EasyNoiseSampler.setSeed(world.getSeed());
        RandomSource random = world.getRandom();
        ImprovedNoise sampler = config.noise == 1 ? EasyNoiseSampler.perlinSimple : config.noise == 2 ? EasyNoiseSampler.perlinAtomic : config.noise == 3 ? EasyNoiseSampler.perlinBlocking : EasyNoiseSampler.perlinXoro;
        int bx = blockPos.getX();
        int bz = blockPos.getZ();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();

        for (int x = bx - config.radius; x <= bx + config.radius; x++) {
            for (int z = bz - config.radius; z <= bz + config.radius; z++) {
                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                if (distance < radiusSquared) {
                    mutable.set(x, world.getHeight(Types.OCEAN_FLOOR, x, z) - 1, z);
                    double sample = EasyNoiseSampler.sample(sampler, mutable, config.multiplier, config.multiplyY, config.useY);
                    if (sample > config.minThresh && sample < config.maxThresh && world.getBlockState(mutable).is(config.replaceable)) {
                        generated = true;
                        world.setBlock(mutable, config.pathBlock.getState(random, mutable), 3);
                    }
                }
            }
        }
        return generated;
    }

}
