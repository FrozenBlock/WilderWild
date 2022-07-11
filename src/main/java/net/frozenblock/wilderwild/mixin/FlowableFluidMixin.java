package net.frozenblock.wilderwild.mixin;

import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(FlowableFluid.class)
public abstract class FlowableFluidMixin {

    @Accessor("field_15901")
    public static ThreadLocal<Object2ByteLinkedOpenHashMap<Block.NeighborGroup>> getField_15901() {
        throw new AssertionError();
    }


    /*@Inject(at = @At("RETURN"), method = "canFlow", cancellable = true)
    private void canFlowHollowedLog(BlockView world, BlockPos fluidPos, BlockState fluidBlockState, Direction flowDirection, BlockPos flowTo, BlockState flowToBlockState, FluidState fluidState, Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        if (world.getFluidState(fluidPos).getFluid() == Fluids.WATER && world.getBlockState(fluidPos).getBlock() instanceof HollowedLogBlock) {
            if (world.getBlockState(fluidPos).get(HollowedLogBlock.FACING) != flowDirection) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "getUpdatedState", cancellable = true)
    private void recivesFlowHollowed(WorldView world, BlockPos pos, BlockState state, CallbackInfoReturnable<FluidState> cir) {
        if (world.getFluidState(pos).getFluid() == Fluids.WATER) {
            int i = 0;
            Iterator<Direction> var6 = Direction.Type.HORIZONTAL.iterator();

            while (var6.hasNext()) {
                Direction direction = var6.next();
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                FluidState fluidState = blockState.getFluidState();
                if (fluidState.getFluid().matchesType(Fluids.WATER) && receivesFlow(direction, world, pos, state, blockPos, blockState)) {
                    if (blockState.getBlock() instanceof HollowedLogBlock) {
                        if (blockState.get(HollowedLogBlock.FACING) != direction) {
                            i = Math.max(i, fluidState.getLevel());
                        }
                    } else {
                        i = Math.max(i, fluidState.getLevel());
                    }
                }
            }

            BlockPos blockPos2 = pos.up();
            BlockState blockState3 = world.getBlockState(blockPos2);
            FluidState fluidState3 = blockState3.getFluidState();
            if (!fluidState3.isEmpty() && fluidState3.getFluid().matchesType(Fluids.WATER) && receivesFlow(Direction.UP, world, pos, state, blockPos2, blockState3)) {
                cir.setReturnValue(((FlowableFluid) (Object) this).getFlowing(8, true));
            } else {
                int k = i - 1;
                if (k <= 0) {
                    cir.setReturnValue(Fluids.EMPTY.getDefaultState());
                } else {
                    cir.setReturnValue(((FlowableFluid) (Object) this).getFlowing(k, false));
                }
            }
        }
    }

    private boolean receivesFlow(Direction face, BlockView world, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState) {
        Object2ByteLinkedOpenHashMap<Block.NeighborGroup> object2ByteLinkedOpenHashMap;
        if (!state.getBlock().hasDynamicBounds() && !fromState.getBlock().hasDynamicBounds()) {
            object2ByteLinkedOpenHashMap = getField_15901().get();
        } else {
            object2ByteLinkedOpenHashMap = null;
        }

        Block.NeighborGroup neighborGroup;
        if (object2ByteLinkedOpenHashMap != null) {
            neighborGroup = new Block.NeighborGroup(state, fromState, face);
            byte b = object2ByteLinkedOpenHashMap.getAndMoveToFirst(neighborGroup);
            if (b != 127) {
                return b != 0;
            }
        } else {
            neighborGroup = null;
        }

        VoxelShape voxelShape = state.getCollisionShape(world, pos);
        VoxelShape voxelShape2 = fromState.getCollisionShape(world, fromPos);
        boolean bl = !VoxelShapes.adjacentSidesCoverSquare(voxelShape, voxelShape2, face);
        if (object2ByteLinkedOpenHashMap != null) {
            if (object2ByteLinkedOpenHashMap.size() == 200) {
                object2ByteLinkedOpenHashMap.removeLastByte();
            }

            object2ByteLinkedOpenHashMap.putAndMoveToFirst(neighborGroup, (byte) (bl ? 1 : 0));
        }

        return bl;
    }*/
}
