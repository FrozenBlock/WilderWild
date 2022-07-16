package net.frozenblock.wilderwild.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSpreadable;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class SculkSlabBlock extends SlabBlock implements SculkSpreadable {

    private final IntProvider experience = ConstantIntProvider.create(1);

    public SculkSlabBlock(Settings settings) {
        super(settings);
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, stack, dropExperience);
        if (dropExperience) {
            this.dropExperienceWhenMined(world, pos, stack, this.experience);
        }
    }

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        return random.nextInt(spreadManager.getSpreadChance()) == 0 ? MathHelper.floor((float)cursor.getCharge() * 0.5F) : cursor.getCharge();
    }
}
