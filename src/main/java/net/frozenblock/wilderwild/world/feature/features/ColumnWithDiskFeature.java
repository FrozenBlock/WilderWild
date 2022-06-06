package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.block.TermiteMound;
import net.frozenblock.wilderwild.registry.RegisterProperties;
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
        ArrayList<BlockPos> poses = posesInCircle(blockPos, (config.radius.get(random))*3);
        Block column1 = config.columnBlock;
        Block column2 = config.secondaryBlock;
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
        for (int i=0; i<height; i++) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            mutable.set(startPos);
            for (int r=0; r<startRadius; r++) {
                for (int ra=0; ra<startRadius; ra++) {
                    double radius = Math.cos(((i*Math.PI)/height)+1);
                    mutable.set(startPos.add(r, i, ra));
                    if (mutable.isWithinDistance(startPos.up(i), radius)) {
                        //TODO: USE BLOCKSTATE PROVIDERS
                        world.setBlockState(mutable, random.nextBoolean() ? (column1 instanceof TermiteMound ? column1.getDefaultState().with(RegisterProperties.NATURAL, true) : column1.getDefaultState()) : column2.getDefaultState(), 3);
                        generated = true;
                    }
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
