package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class NoisePlantFeature extends Feature<PathFeatureConfig> {
    public NoisePlantFeature(Codec<PathFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<PathFeatureConfig> context) {
        boolean generated = false;
        PathFeatureConfig config = context.getConfig();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        int radiusSquared = config.radius * config.radius;
        EasyNoiseSampler.setSeed(world.getSeed());
        Random random = world.getRandom();
        PerlinNoiseSampler sampler = config.noise == 1 ? EasyNoiseSampler.perlinSimple : config.noise == 2 ? EasyNoiseSampler.perlinAtomic : config.noise == 3 ? EasyNoiseSampler.perlinBlocking : EasyNoiseSampler.perlinXoro;
        int bx = blockPos.getX();
        int bz = blockPos.getZ();
        BlockPos.Mutable mutable = blockPos.mutableCopy();

        for (int x = bx - config.radius; x <= bx + config.radius; x++) {
            for (int z = bz - config.radius; z <= bz + config.radius; z++) {
                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                if (distance < radiusSquared) {
                    mutable.set(x, world.getTopY(Type.OCEAN_FLOOR, x, z), z);
                    double sample = EasyNoiseSampler.sample(sampler, mutable, config.multiplier, config.multiplyY, config.useY);
                    if (sample > config.minThresh && sample < config.maxThresh && world.getBlockState(mutable).isIn(config.replaceable) && world.getBlockState(mutable.down()).isIn(BlockTags.DIRT)) {
                        generated = true;
                        world.setBlockState(mutable, config.pathBlock.getBlockState(random, mutable), 3);
                    }
                }
            }
        }
        return generated;
    }

}
