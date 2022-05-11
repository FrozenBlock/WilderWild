package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.particle.PollenParticle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.World;

public class MilkweedBlock extends TallFlowerBlock {

    public MilkweedBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.AGE_3);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (world instanceof ServerWorld server) {
            if (state.get(Properties.AGE_3)==3) {
                PollenParticle.EasySeedPacket.createParticle(world, Vec3d.ofCenter(pos).add(0, 0.3, 0), server.random.nextBetween(7, 14), true);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, AbstractRandom random) {
        if (state.isOf(this)) {
            if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                if (state.get(Properties.AGE_3)<3) {
                    world.setBlockState(pos, state.with(Properties.AGE_3, state.get(Properties.AGE_3) + 1));
                    world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(Properties.AGE_3, state.get(Properties.AGE_3) + 1));
                }
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world instanceof ServerWorld server) {
            if (state.get(Properties.AGE_3)==3) {
                PollenParticle.EasySeedPacket.createParticle(world, Vec3d.ofCenter(pos).add(0, 0.3, 0), server.random.nextBetween(14, 28), true);
                if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                    world.setBlockState(pos, state.with(Properties.AGE_3, state.get(Properties.AGE_3) - 1));
                    world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(Properties.AGE_3, state.get(Properties.AGE_3) - 1));
                } else if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
                    world.setBlockState(pos, state.with(Properties.AGE_3, state.get(Properties.AGE_3) - 1));
                    world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(Properties.AGE_3, state.get(Properties.AGE_3) - 1));
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);

    }

    @Override
    public boolean hasRandomTicks(BlockState state) { return true; }
}