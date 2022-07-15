package net.frozenblock.wilderwild.block;

import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class SculkStairsBlock extends StairsBlock implements SculkSpreadable {

    private final IntProvider experience = ConstantIntProvider.create(1);

    public SculkStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, stack, dropExperience);
        if (dropExperience) {
            this.dropExperienceWhenMined(world, pos, stack, this.experience);
        }
    }

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        int i = cursor.getCharge();
        boolean isWorldGen = spreadManager.isWorldGen();
        if (i != 0 && random.nextInt(spreadManager.getSpreadChance()) == 0) {
            if (isWorldGen) {
                BlockPos blockPos = cursor.getPos();
                boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
                if (!bl) {
                    int j = spreadManager.getExtraBlockChance();
                    if (random.nextInt(j) < i) {
                        BlockPos blockPos2 = blockPos.up();
                        if (world.getBlockState(blockPos2).isIn(WilderBlockTags.ANCIENT_CITY_BLOCKS)) {
                            BlockState blockState = this.getExtraBlockState(world, blockPos, random, isWorldGen);
                            if (world.getBlockState(blockPos).isIn(BlockTags.STAIRS)) {
                                blockState = RegisterBlocks.SCULK_STAIRS.getDefaultState();
                            }

                            world.setBlockState(blockPos2, blockState, 0);
                        }
                    }
                }
            }
        }
        return i;
    }

        private BlockState getExtraBlockState(WorldAccess world, BlockPos pos, Random random, boolean allowShrieker) {
            BlockState blockState = Blocks.SCULK_SENSOR.getDefaultState();
            boolean decided = false;
            if (random.nextInt(11) == 0) {
                blockState = Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, allowShrieker);
                decided = true;
            }
            return blockState.contains(Properties.WATERLOGGED) && !world.getFluidState(pos).isEmpty() ? blockState.with(Properties.WATERLOGGED, true) : blockState;
        }
}
