package net.frozenblock.wilderwild.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BaobabLeaves extends LeavesBlock implements Fertilizable {
    public BaobabLeaves(AbstractBlock.Settings settings) {
        super(settings);
    }
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.down()).isAir();
    }
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }


    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos.down(), BaobabNutBlock.getDefaultHangingState(), 2);
    }
}