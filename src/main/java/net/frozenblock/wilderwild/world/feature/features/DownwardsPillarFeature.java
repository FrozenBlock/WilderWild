package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.world.feature.features.config.UpwardsPillarConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;

public class DownwardsPillarFeature extends Feature<UpwardsPillarConfig> {
    public DownwardsPillarFeature(Codec<UpwardsPillarConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<UpwardsPillarConfig> context) {
        boolean bl = false;
        BlockPos blockPos = context.origin();
        WorldGenLevel world = context.level();
        RandomSource random = world.getRandom();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        int bx = blockPos.getX();
        int bz = blockPos.getZ();
        int by = blockPos.getY();
        int height = -context.config().height.sample(random);
        for (int y = 0; y > height; y--) {
            if (context.config().replaceable.contains(world.getBlockState(mutable).getBlockHolder()) || world.getBlockState(mutable).isAir() || world.getBlockState(mutable).getFluidState() != Fluids.EMPTY.defaultFluidState()) {
                bl = true;
                world.setBlock(mutable, context.config().columnBlock, 3);
                mutable.set(bx, by + y, bz);
            } else {
                mutable.set(bx, by + y, bz);
            }
        }
        return bl;
    }

}
