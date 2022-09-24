package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.lib.tags.FrozenBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

    @Inject(method = "getFluidAboveStalactite", at = @At("HEAD"), cancellable = true)
    private static void getFluidAboveStalactite(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Optional<PointedDripstoneBlock.FluidInfo>> info) {
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

    @Inject(method = "maybeTransferFluid", at = @At("HEAD"), cancellable = true)
    private static void maybeTransferFluid(BlockState state, ServerLevel level, BlockPos pos, float randChance, CallbackInfo) {
        /*
        if (!(randChance > 0.17578125F) || !(randChance > 0.05859375F)) {
            if (isStalactiteStartPos(state, level, pos)) {
                Optional<PointedDripstoneBlock.FluidInfo> optional = getFluidAboveStalactite(level, pos, state);
                if (!optional.isEmpty()) {
                    Fluid fluid = ((PointedDripstoneBlock.FluidInfo)optional.get()).fluid;
                    float f;
                    if (fluid == Fluids.WATER) {
                        f = 0.17578125F;
                    } else {
                        if (fluid != Fluids.LAVA) {
                            return;
                        }

                        f = 0.05859375F;
                    }

                    if (!(randChance >= f)) {
                        BlockPos blockPos = findTip(state, level, pos, 11, false);
                        if (blockPos != null) {
                        //TODO: Also add a check here for wet sponges.

                            if (((PointedDripstoneBlock.FluidInfo)optional.get()).sourceState.is(Blocks.MUD) && fluid == Fluids.WATER) {
                                BlockState blockState = Blocks.CLAY.defaultBlockState();
                                level.setBlockAndUpdate(((PointedDripstoneBlock.FluidInfo)optional.get()).pos, blockState);
                                Block.pushEntitiesUp(((PointedDripstoneBlock.FluidInfo)optional.get()).sourceState, blockState, level, ((PointedDripstoneBlock.FluidInfo)optional.get()).pos);
                                level.gameEvent(GameEvent.BLOCK_CHANGE, ((PointedDripstoneBlock.FluidInfo)optional.get()).pos, GameEvent.Context.of(blockState));
                                level.levelEvent(1504, blockPos, 0);
                            } else {
                                BlockPos blockPos2 = findFillableCauldronBelowStalactiteTip(level, blockPos, fluid);
                                if (blockPos2 != null) {
                                    level.levelEvent(1504, blockPos, 0);
                                    int i = blockPos.getY() - blockPos2.getY();
                                    int j = 50 + i;
                                    BlockState blockState2 = level.getBlockState(blockPos2);
                                    level.scheduleTick(blockPos2, blockState2.getBlock(), j);
                                }
                            }
                        }
                    }
                }
            }
        }
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
