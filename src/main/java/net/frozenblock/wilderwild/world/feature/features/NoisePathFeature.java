package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;

public class NoisePathFeature extends Feature<PathFeatureConfig> {
    public NoisePathFeature(Codec<PathFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<PathFeatureConfig> context) {
        boolean generated = false;
        PathFeatureConfig config = context.getConfig();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        ArrayList<BlockPos> poses = posesInCircle(blockPos, config.radius);
        for (BlockPos tempPos : poses) {
            BlockPos pos = tempPos.withY(world.getTopY(Type.WORLD_SURFACE_WG, tempPos.getX(), tempPos.getZ()) - 1);
            if (world.getBlockState(pos).getBlock() instanceof PlantBlock) {
                pos = pos.down();
            }
            if (EasyNoiseSampler.samplePerlinXoro(pos, config.multiplier, config.multiplyY, config.useY) > config.threshold && world.getBlockState(pos).isIn(config.replaceable)) {
                generated = true;
                world.setBlockState(pos, config.pathBlock.getDefaultState(), 3);
            }
        }
        return generated;
    }

    public static ArrayList<BlockPos> posesInCircle(BlockPos pos, int radius) {
        ArrayList<BlockPos> poses = new ArrayList<>();
        int bx = pos.getX();
        int bz = pos.getZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int z = bz - radius; z <= bz + radius; z++) {
                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                if (distance < radius * radius) {
                    poses.add(new BlockPos(x, 0, z));
                }
            }
        }
        return poses;
    }

}
