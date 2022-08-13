package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MilkweedBlock extends TallFlowerBlock {

    public MilkweedBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.AGE_3);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.8F) {
            if (state.is(RegisterBlocks.MILKWEED)) {
                if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                    if (state.getValue(BlockStateProperties.AGE_3) < 3) {
                        if (world.getBlockState(pos).is(RegisterBlocks.MILKWEED)) {
                            world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_3, state.getValue(BlockStateProperties.AGE_3) + 1));
                        }
                        if (world.getBlockState(pos.above()).is(RegisterBlocks.MILKWEED)) {
                            world.setBlockAndUpdate(pos.above(), world.getBlockState(pos.above()).setValue(BlockStateProperties.AGE_3, state.getValue(BlockStateProperties.AGE_3) + 1));
                        }
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world instanceof ServerLevel server) {
            if (state.getValue(BlockStateProperties.AGE_3) == 3) {
                ItemStack itemStack = player.getItemInHand(hand);
                if (itemStack.is(Items.SHEARS)) {
                    ItemStack stack = new ItemStack(RegisterItems.MILKWEED_POD);
                    stack.setCount(world.random.nextIntBetweenInclusive(2, 7));
                    popResource(world, pos, stack);
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
                    world.gameEvent(player, GameEvent.SHEAR, pos);
                    if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                        world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_3, 0));
                        world.setBlockAndUpdate(pos.above(), world.getBlockState(pos.above()).setValue(BlockStateProperties.AGE_3, 0));
                    } else if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
                        world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_3, 0));
                        world.setBlockAndUpdate(pos.below(), world.getBlockState(pos.below()).setValue(BlockStateProperties.AGE_3, 0));
                    }
                } else {
                    EasyPacket.EasySeedPacket.createParticle(world, Vec3.atCenterOf(pos).add(0, 0.3, 0), server.random.nextIntBetweenInclusive(14, 28), true, 48);
                    if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                        world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_3, 1));
                        world.setBlockAndUpdate(pos.above(), world.getBlockState(pos.above()).setValue(BlockStateProperties.AGE_3, 1));
                    } else if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
                        world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_3, 1));
                        world.setBlockAndUpdate(pos.below(), world.getBlockState(pos.below()).setValue(BlockStateProperties.AGE_3, 1));
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, hit);

    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}