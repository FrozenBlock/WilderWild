package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
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
        ArrayList<BlockPos> poses = posesInCircle(blockPos, radius);
        Optional<RegistryEntry<Block>> diskOptional = config.diskBlocks.getRandom(random);
        //DISK
        if (diskOptional.isPresent()) {
            BlockState disk = diskOptional.get().value().getDefaultState();
            for (BlockPos tempPos : poses) {
                boolean fade = !tempPos.isWithinDistance(s, radius*0.777);
                BlockPos pos = tempPos.withY(world.getTopY(Type.WORLD_SURFACE_WG, tempPos.getX(), tempPos.getZ()) - 1);
                if (world.getBlockState(pos).getBlock() instanceof PlantBlock) {
                    pos = pos.down();
                }
                if (world.getBlockState(pos).isIn(config.replaceable)) {
                    generated = true;
                    if (fade) {
                        if (random.nextFloat()>0.76F) {
                            world.setBlockState(pos, disk, 3);
                        }
                    } else {
                        world.setBlockState(pos, disk, 3);
                    }
                }
            }
        }
        //COLUMN / TERMITE MOUND
        BlockPos startPos = blockPos.withY(world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()));
        BlockState column = config.columnBlock;
        BlockPos.Mutable pos = startPos.mutableCopy();
        for (int i=0; i<config.height.get(random); i++) {
            pos.set(pos.up(i));
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof PlantBlock || state.isAir()) {
                world.setBlockState(pos, column, 3);
                generated = true;
            }
        }
        pos = startPos.add(-1,0,0).mutableCopy();
        pos = startPos.withY(world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, startPos.getX(), startPos.getZ())).mutableCopy();
        for (int i=0; i<config.height2.get(random); i++) {
            pos.set(pos.up(i));
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof PlantBlock || state.isAir()) {
                world.setBlockState(pos, column, 3);
                generated = true;
            }
        }
        pos = startPos.add(1,0,0).mutableCopy();
        pos = startPos.withY(world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, startPos.getX(), startPos.getZ())).mutableCopy();
        for (int i=0; i<config.height2.get(random); i++) {
            pos.set(pos.up(i));
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof PlantBlock || state.isAir()) {
                world.setBlockState(pos, column, 3);
                generated = true;
            }
        }
        return generated;
    }

    public static ArrayList<BlockPos> posesInCircle(BlockPos pos, int radius) {
        ArrayList<BlockPos> poses = new ArrayList<>();
        int bx = pos.getX();
        int bz = pos.getZ();
        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int z = bz - radius; z <= bz + radius; z++) {
                double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)));
                if(distance < radius * radius) {
                    poses.add(new BlockPos(x, 0, z));
                }
            }
        } return poses;
    }

}
