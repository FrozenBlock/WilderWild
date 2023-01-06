package net.frozenblock.wilderwild.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FloweringLilyPadBlock extends WaterlilyBlock {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

    public FloweringLilyPadBlock(Properties settings) {
        super(settings);
    }

	@Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level instanceof ServerLevel && entity instanceof Boat) {
            level.destroyBlock(new BlockPos(pos), true, entity);
        }
    }

	@Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return AABB;
    }

	@Override
    protected boolean mayPlaceOn(@NotNull BlockState floor, BlockGetter level, @NotNull BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        FluidState fluidState2 = level.getFluidState(pos.above());
        return (fluidState.getType() == Fluids.WATER || floor.getMaterial() == Material.ICE) && fluidState2.getType() == Fluids.EMPTY;
    }
}
