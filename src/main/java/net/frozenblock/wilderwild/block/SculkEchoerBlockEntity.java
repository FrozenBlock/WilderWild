package net.frozenblock.wilderwild.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.listener.SculkSensorListener;

public class SculkEchoerBlockEntity  extends BlockEntity  {

    public SculkEchoerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
