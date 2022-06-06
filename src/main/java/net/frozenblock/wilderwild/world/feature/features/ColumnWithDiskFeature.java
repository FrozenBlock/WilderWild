package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
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
        Random random = world.getRandom();
        ArrayList<BlockPos> poses = posesInCircle(blockPos, (config.radius.get(random))*4);
        Optional<RegistryEntry<Block>> diskOptional = config.diskBlocks.getRandom(random);
        //DISK
        if (diskOptional.isPresent()) {
            BlockState disk = diskOptional.get().value().getDefaultState();
            for (BlockPos tempPos : poses) {
                BlockPos pos = tempPos.withY(world.getTopY(Type.WORLD_SURFACE_WG, tempPos.getX(), tempPos.getZ()) - 1);
                if (world.getBlockState(pos).getBlock() instanceof PlantBlock) {
                    pos = pos.down();
                }
                if (world.getBlockState(pos).isIn(config.replaceable)) {
                    generated = true;
                    world.setBlockState(pos, disk, 3);
                }
            }
        }
        //COLUMN / TERMITE MOUND
        int height = config.height.get(random);
        int startRadius = config.radius.get(random);
        BlockPos startPos = blockPos.withY(world.getTopY(Type.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()));
        BlockState column1 = config.columnBlock;
        BlockState column2 = config.secondaryBlock;
        ArrayList<BlockPos> circle = posesInCircle(startPos, startRadius);
        for (int i=0; i<height; i++) {
            int y = startPos.getY()+i;
            double radius = Math.max(startRadius * Math.cos(((i*Math.PI)/(height*0.8))) + (startRadius/3),0);
            for (BlockPos pos : circle) {
                pos = pos.withY(y);
                if (pos.isWithinDistance(startPos.up(i), radius)) {
                    BlockState placeState = random.nextFloat()>0.8F ? column2 : column1;
                    world.setBlockState(pos, placeState, 3);
                    generated=true;
                }
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
