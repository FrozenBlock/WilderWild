package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Optional;

public class ColumnWithDiskFeature extends Feature<ColumnWithDiskFeatureConfig> {
    public ColumnWithDiskFeature(Codec<ColumnWithDiskFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<ColumnWithDiskFeatureConfig> context) {
        boolean generated = false;
        ColumnWithDiskFeatureConfig config = context.getConfig();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        BlockPos s = blockPos.withY(world.getTopY(Type.WORLD_SURFACE_WG, blockPos.getX(), blockPos.getZ()) - 1);
        Random random = world.getRandom();
        int radius = config.radius.get(random);
        Optional<RegistryEntry<Block>> diskOptional = config.diskBlocks.getRandom(random);
        //DISK
        if (diskOptional.isPresent()) {
            BlockPos.Mutable mutableDisk = s.mutableCopy();
            BlockState disk = diskOptional.get().value().getDefaultState();
            int bx = s.getX();
            int bz = s.getZ();
            for (int x = bx - radius; x <= bx + radius; x++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                    if (distance < radius * radius) {
                        mutableDisk.set(x, world.getTopY(Type.WORLD_SURFACE_WG, x, z) - 1, z);
                        if (world.getBlockState(mutableDisk).getBlock() instanceof PlantBlock) {
                            mutableDisk.set(mutableDisk.down());
                        }
                        boolean fade = !mutableDisk.isWithinDistance(s, radius * 0.8);
                        if (world.getBlockState(mutableDisk).isIn(config.replaceable)) {
                            generated = true;
                            if (fade) {
                                if (random.nextFloat() > 0.65F) {
                                    world.setBlockState(mutableDisk, disk, 3);
                                }
                            } else {
                                world.setBlockState(mutableDisk, disk, 3);
                            }
                        }
                    }
                }
            }
        }
        //COLUMN / TERMITE MOUND
        BlockPos startPos = blockPos.withY(world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()) - 1);
        BlockState column = config.columnBlock;
        BlockPos.Mutable pos = startPos.mutableCopy();
        for (int i = 0; i < config.height.get(random); i++) {
            pos.set(pos.up());
            BlockState state = world.getBlockState(pos);
            if (world.getBlockState(pos.down()).isOf(Blocks.WATER)) {
                break;
            }
            if (state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof PlantBlock || state.isAir()) {
                world.setBlockState(pos, column, 3);
                generated = true;
            }
        }
        startPos = startPos.add(-1, 0, 0);
        pos.set(startPos.withY(world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, startPos.getX(), startPos.getZ()) - 1).mutableCopy());
        for (int i = 0; i < config.height2.get(random); i++) {
            pos.set(pos.up());
            BlockState state = world.getBlockState(pos);
            if (world.getBlockState(pos.down()).isOf(Blocks.WATER)) {
                break;
            }
            if (state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof PlantBlock || state.isAir()) {
                world.setBlockState(pos, column, 3);
                generated = true;
            }
        }
        startPos = startPos.add(1, 0, 1);
        pos.set(startPos.withY(world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, startPos.getX(), startPos.getZ()) - 1).mutableCopy());
        for (int i = 0; i < config.height2.get(random); i++) {
            pos.set(pos.up());
            BlockState state = world.getBlockState(pos);
            if (world.getBlockState(pos.down()).isOf(Blocks.WATER)) {
                break;
            }
            if (state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof PlantBlock || state.isAir()) {
                world.setBlockState(pos, column, 3);
                generated = true;
            }
        }
        return generated;
    }

}
