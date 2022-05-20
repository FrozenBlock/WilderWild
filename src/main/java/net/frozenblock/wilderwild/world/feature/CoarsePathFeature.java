package net.frozenblock.wilderwild.world.feature;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.tag.WildBlockTags;
import net.frozenblock.wilderwild.world.EasyNoiseSampler;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;

public class CoarsePathFeature extends Feature<DefaultFeatureConfig> {
    public CoarsePathFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        boolean generated = false;
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        ArrayList<BlockPos> poses = blocksInSphere(blockPos, 11);
        for (BlockPos tempPos : poses) {
            BlockPos pos = tempPos.withY(world.getTopY(Type.WORLD_SURFACE_WG, tempPos.getX(), tempPos.getZ()) - 1);
            if (EasyNoiseSampler.samplePerlinXoro(pos, 0.07, false, false)>0.2 && world.getBlockState(pos).isIn(WildBlockTags.COARSE_PATH_REPLACEABLE)) {
                generated = true;
                world.setBlockState(pos, Blocks.COARSE_DIRT.getDefaultState(), 3);
            }
        }
        return generated;
    }

    public static ArrayList<BlockPos> blocksInSphere(BlockPos pos, int radius) {
        ArrayList<BlockPos> poses = new ArrayList<>();
        int bx = pos.getX();
        int by = pos.getY();
        int bz = pos.getZ();
        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                    if(distance < radius * radius) {
                        if (y==pos.getY()) {
                            poses.add(new BlockPos(x, y, z));
                        }
                    }
                }
            }
        } return poses;
    }

}
