package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Iterator;

public class NoisePathUnderWaterFeature extends Feature<PathFeatureConfig> {
    public NoisePathUnderWaterFeature(Codec<PathFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<PathFeatureConfig> context) {
        boolean generated = false;
        PathFeatureConfig config = context.getConfig();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        EasyNoiseSampler.setSeed(world.getSeed());
        PerlinNoiseSampler sampler = config.noise == 1 ? EasyNoiseSampler.perlinSimple : config.noise == 2 ? EasyNoiseSampler.perlinAtomic :  config.noise == 3 ? EasyNoiseSampler.perlinBlocking : EasyNoiseSampler.perlinXoro;
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        int bx = mutable.getX();
        int bz = mutable.getZ();
        int by = mutable.getY();;
        int radiusSquared = config.radius * config.radius;

        for (int x = bx - config.radius; x <= bx + config.radius; x++) {
            for (int z = bz - config.radius; z <= bz + config.radius; z++) {
                for (int y = by - config.radius; y <= by + config.radius; y++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance < radiusSquared) {
                        mutable.set(x, y, z);
                        double sample1 = EasyNoiseSampler.sample(sampler, mutable, config.multiplier, config.multiplyY, config.useY);
                        //double sample2 = EasyNoiseSampler.samplePerlinSimplePositive(mutable, config.multiplier, config.multiplyY, config.useY);
                        if (sample1 > config.minThresh && sample1 < config.maxThresh && world.getBlockState(mutable).isIn(config.replaceable) && isWaterNearby(world, mutable, 3)) {
                            generated = true;
                            world.setBlockState(mutable, config.pathBlock.getDefaultState(), 3);
                        }
                    }
                }
            }
        }
        return generated;
    }

    public static boolean isWaterNearby(StructureWorldAccess world, BlockPos blockPos, int x) {
        Iterator<BlockPos> var2 = BlockPos.iterate(blockPos.add(-x, -x, -x), blockPos.add(x, x, x)).iterator();
        BlockPos blockPos2;
        do {
            if (!var2.hasNext()) {
                return false;
            }
            blockPos2 = var2.next();
        } while (!world.getBlockState(blockPos2).isOf(Blocks.WATER));
        return true;
    }

}
