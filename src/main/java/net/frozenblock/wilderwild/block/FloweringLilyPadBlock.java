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

public class FloweringLilyPadBlock extends WaterlilyBlock {
    protected static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 1.5D, 15);

    public FloweringLilyPadBlock(Properties settings) {
        super(settings);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos,
                             Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level instanceof ServerLevel && entity instanceof Boat) {
            level.destroyBlock(new BlockPos(pos), true, entity);
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter level,
                               BlockPos pos, CollisionContext context) {
        return AABB;
    }

    protected boolean mayPlaceOn(BlockState floor, BlockGetter level,
                                 BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        FluidState fluidState2 = level.getFluidState(pos.above());
        return (fluidState.getType() == Fluids.WATER ||
                floor.getMaterial() == Material.ICE) &&
                fluidState2.getType() == Fluids.EMPTY;
    }
}
