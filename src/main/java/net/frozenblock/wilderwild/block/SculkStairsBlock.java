package net.frozenblock.wilderwild.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SculkStairsBlock extends StairBlock implements SculkBehaviour {

    private final IntProvider experienceDropped = ConstantInt.of(1);

    public SculkStairsBlock(BlockState baseBlockState, Properties settings) {
        super(baseBlockState, settings);
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (dropExperience) {
            this.tryDropExperience(level, pos, stack, this.experienceDropped);
        }
    }

    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        return random.nextInt(spreadManager.chargeDecayRate()) == 0 ? Mth.floor((float) cursor.getCharge() * 0.5F) : cursor.getCharge();
    }
}
