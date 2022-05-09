package net.frozenblock.wilderwild.world.feature;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class CattailFeature extends Feature<ProbabilityConfig> {
    public CattailFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        boolean bl = false;
        AbstractRandom abstractRandom = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        int loopFor = context.getRandom().nextBetween(7, 21);
        for (int l=0; l<loopFor; l++) {
            int i = abstractRandom.nextBetween(-7,7);
            int j = abstractRandom.nextBetween(-7,7);
            int k = structureWorldAccess.getTopY(Type.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
            BlockPos randomPos = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
            if (structureWorldAccess.getBlockState(randomPos).isOf(Blocks.WATER)) {
                BlockState bottom = RegisterBlocks.CATTAIL.getDefaultState().with(Properties.WATERLOGGED, true);
                if (bottom.canPlaceAt(structureWorldAccess, randomPos)) {
                    BlockState top = bottom.with(WaterloggableTallFlowerBlock.HALF, DoubleBlockHalf.UPPER).with(Properties.WATERLOGGED, false);
                    BlockPos upPos = randomPos.up();
                    if (structureWorldAccess.getBlockState(upPos).isOf(Blocks.AIR)) {
                        structureWorldAccess.setBlockState(randomPos, bottom, 2);
                        structureWorldAccess.setBlockState(upPos, top, 2);
                        bl = true;
                    }
                }
            }
        }

        return bl;
    }
}
