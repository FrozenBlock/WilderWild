package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.TermiteBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TermiteBlock extends BlockWithEntity {

    public TermiteBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TermiteBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient ? checkType(type, RegisterBlockEntityType.TERMITE, (worldx, pos, statex, blockEntity) -> blockEntity.tick(worldx, pos)) : null;
    }
}
