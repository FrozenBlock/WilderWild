package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class WaterMossFeature extends Feature<ProbabilityFeatureConfiguration> {
    public WaterMossFeature(Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        boolean bl = false;
        BlockPos blockPos = context.origin();
        WorldGenLevel world = context.level();
        BlockPos s = blockPos.atY(world.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()));
        RandomSource random = world.getRandom();
        int radius = random.nextIntBetweenInclusive(4, 10);
        //DISK
        BlockPos.MutableBlockPos mutableDisk = s.mutable();
        int bx = s.getX();
        int bz = s.getZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int z = bz - radius; z <= bz + radius; z++) {
                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                if (distance < radius * radius) {
                    mutableDisk.set(x, world.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
                    boolean fade = !mutableDisk.closerThan(s, radius * 0.8);
                    if (world.getBlockState(mutableDisk.below()).is(Blocks.WATER)) {
                        bl = true;
                        if (random.nextFloat() > 0.2F) {
                            if (fade) {
                                if (random.nextFloat() > 0.5F) {
                                    world.setBlock(mutableDisk, RegisterBlocks.FLOATING_MOSS.defaultBlockState(), 3);
                                }
                            } else {
                                world.setBlock(mutableDisk, RegisterBlocks.FLOATING_MOSS.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }
}
