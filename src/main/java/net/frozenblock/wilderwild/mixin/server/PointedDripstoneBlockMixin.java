package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.lib.tags.FrozenBlockTags;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
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


    private static Optional<PointedDripstoneBlock.FluidInfo> getFluidAboveStalactite(Level level, BlockPos pos, BlockState state) {
        /*
        return !isStalactite(state) ? Optional.empty() : findRootBlock(level, pos, state, 11).map((posx) -> {
            BlockPos blockPos = posx.above();
            BlockState blockState = level.getBlockState(blockPos);
            Object fluid;
            //TODO: Inject at this spot and add a check for wet sponges.
            if (blockState.is(Blocks.MUD) && !level.dimensionType().ultraWarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = level.getFluidState(blockPos).getType();
            }

            return new PointedDripstoneBlock.FluidInfo(blockPos, (Fluid)fluid, blockState);
        });
        */
    }

    @Shadow
    private static boolean isStalactite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.DOWN);
    }

    @Shadow
    private static boolean isStalagmite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.UP);
    }

    @Shadow
    private static boolean isPointedDripstoneWithDirection(BlockState state, Direction dir) {
        return state.is(Blocks.POINTED_DRIPSTONE) && state.getValue(BlockStateProperties.VERTICAL_DIRECTION) == dir;
    }

    @Shadow
    private static Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxIterations) {
        return Optional.empty();
    }

}
