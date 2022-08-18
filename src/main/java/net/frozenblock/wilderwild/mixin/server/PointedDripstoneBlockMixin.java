package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

    @Final
    @Shadow
    private static VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    @Inject(at = @At("HEAD"), method = "findFillableCauldronBelowStalactiteTip", cancellable = true)
    private static void findFillableCauldronBelowStalactiteTip(Level world, BlockPos pos2, Fluid fluid, CallbackInfoReturnable<BlockPos> info) {
        Predicate<BlockState> predicate = state -> (state.getBlock() instanceof AbstractCauldronBlock && ((AbstractCauldronBlock) state.getBlock()).canReceiveStalactiteDrip(fluid)) || state.is(Blocks.DIRT);
        BiPredicate<BlockPos, BlockState> biPredicate = (pos, state) -> canDripThrough(world, pos, state);
        info.setReturnValue(findBlockVertical(world, pos2, Direction.DOWN.getAxisDirection(), biPredicate, predicate, 11).orElse(null));
        info.cancel();
    }

    @Shadow
    private static boolean canDripThrough(BlockGetter world, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        }
        if (state.isSolidRender(world, pos)) {
            return false;
        }
        if (!state.getFluidState().isEmpty()) {
            return false;
        }
        VoxelShape voxelShape = state.getCollisionShape(world, pos);
        return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelShape, BooleanOp.AND);
    }

    @Shadow
    private static Optional<BlockPos> findBlockVertical(LevelAccessor world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y);
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int i = 1; i < range; ++i) {
            mutable.move(direction2);
            BlockState blockState = world.getBlockState(mutable);
            if (stopPredicate.test(blockState)) {
                return Optional.of(mutable.immutable());
            }
            if (!world.isOutsideBuildHeight(mutable.getY()) && continuePredicate.test(mutable, blockState)) continue;
            return Optional.empty();
        }
        return Optional.empty();
    }

}
