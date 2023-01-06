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
import org.jetbrains.annotations.NotNull;

public class SculkStairsBlock extends StairBlock implements SculkBehaviour {
    private static final IntProvider EXPERIENCE = ConstantInt.of(1);

    public SculkStairsBlock(BlockState baseBlockState, Properties settings) {
        super(baseBlockState, settings);
    }

    @Override
    public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (dropExperience) {
            this.tryDropExperience(level, pos, stack, EXPERIENCE);
        }
    }

    @Override
    public int attemptUseCharge(SculkSpreader.@NotNull ChargeCursor cursor, @NotNull LevelAccessor level, @NotNull BlockPos catalystPos, RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        return random.nextInt(spreadManager.chargeDecayRate()) == 0 ? Mth.floor((float) cursor.getCharge() * 0.5F) : cursor.getCharge();
    }
}
