package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class AlgaeFeature extends Feature<ProbabilityConfig> {
    public AlgaeFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        boolean bl = false;
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        BlockPos s = blockPos.withY(world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()));
        Random random = world.getRandom();
        int radius = random.nextBetween(4, 10);
        //DISK
        BlockPos.Mutable mutableDisk = s.mutableCopy();
        int bx = s.getX();
        int bz = s.getZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int z = bz - radius; z <= bz + radius; z++) {
                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                if (distance < radius * radius) {
                    mutableDisk.set(x, world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
                    boolean fade = !mutableDisk.isWithinDistance(s, radius * 0.8);
                    if (world.getBlockState(mutableDisk.down()).isOf(Blocks.WATER)) {
                        bl = true;
                        if (random.nextFloat() > 0.2F) {
                            if (fade) {
                                if (random.nextFloat() > 0.5F) {
                                    world.setBlockState(mutableDisk, RegisterBlocks.ALGAE.getDefaultState(), 3);
                                }
                            } else {
                                world.setBlockState(mutableDisk, RegisterBlocks.ALGAE.getDefaultState(), 3);
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }
}
