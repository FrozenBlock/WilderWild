package net.frozenblock.wilderwild.world.generation.features;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.frozenblock.wilderwild.world.generation.features.config.ColumnWithDiskFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ColumnWithDiskFeature extends Feature<ColumnWithDiskFeatureConfig> {
    public ColumnWithDiskFeature(Codec<ColumnWithDiskFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ColumnWithDiskFeatureConfig> context) {
        boolean generated = false;
        ColumnWithDiskFeatureConfig config = context.config();
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        BlockPos s = blockPos.atY(level.getHeight(Types.WORLD_SURFACE_WG, blockPos.getX(), blockPos.getZ()) - 1);
        RandomSource random = level.getRandom();
        int radius = config.radius.sample(random);
        Optional<Holder<Block>> diskOptional = config.diskBlocks.getRandomElement(random);
        //DISK
        if (diskOptional.isPresent()) {
            BlockPos.MutableBlockPos mutableDisk = s.mutable();
            BlockState disk = diskOptional.get().value().defaultBlockState();
            int bx = s.getX();
            int bz = s.getZ();
            for (int x = bx - radius; x <= bx + radius; x++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                    if (distance < radius * radius) {
                        mutableDisk.set(x, level.getHeight(Types.WORLD_SURFACE_WG, x, z) - 1, z);
                        if (level.getBlockState(mutableDisk).getBlock() instanceof BushBlock) {
                            mutableDisk.set(mutableDisk.below());
                        }
                        boolean fade = !mutableDisk.closerThan(s, radius * 0.8);
                        if (level.getBlockState(mutableDisk).is(config.replaceable)) {
                            generated = true;
                            if (fade) {
                                if (random.nextFloat() > 0.65F) {
                                    level.setBlock(mutableDisk, disk, 3);
                                }
                            } else {
                                level.setBlock(mutableDisk, disk, 3);
                            }
                        }
                    }
                }
            }
        }
        //COLUMN / TERMITE MOUND
        BlockPos startPos = blockPos.atY(level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()) - 1);
        BlockState column = config.columnBlock;
        BlockPos.MutableBlockPos pos = startPos.mutable();
        for (int i = 0; i < config.height.sample(random); i++) {
            pos.set(pos.above());
            BlockState state = level.getBlockState(pos);
            if (level.getBlockState(pos.below()).is(Blocks.WATER)) {
                break;
            }
            if (state.getBlock() instanceof GrowingPlantBodyBlock || state.getBlock() instanceof BushBlock || state.isAir()) {
                level.setBlock(pos, column, 3);
                generated = true;
            }
        }
        startPos = startPos.offset(-1, 0, 0);
        pos.set(startPos.atY(level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, startPos.getX(), startPos.getZ()) - 1).mutable());
        for (int i = 0; i < config.height2.sample(random); i++) {
            pos.set(pos.above());
            BlockState state = level.getBlockState(pos);
            if (level.getBlockState(pos.below()).is(Blocks.WATER)) {
                break;
            }
            if (state.getBlock() instanceof GrowingPlantBodyBlock || state.getBlock() instanceof BushBlock || state.isAir()) {
                level.setBlock(pos, column, 3);
                generated = true;
            }
        }
        startPos = startPos.offset(1, 0, 1);
        pos.set(startPos.atY(level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, startPos.getX(), startPos.getZ()) - 1).mutable());
        for (int i = 0; i < config.height2.sample(random); i++) {
            pos.set(pos.above());
            BlockState state = level.getBlockState(pos);
            if (level.getBlockState(pos.below()).is(Blocks.WATER)) {
                break;
            }
            if (state.getBlock() instanceof GrowingPlantBodyBlock || state.getBlock() instanceof BushBlock || state.isAir()) {
                level.setBlock(pos, column, 3);
                generated = true;
            }
        }
        return generated;
    }

}
