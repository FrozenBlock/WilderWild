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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

    //TODO: WORK
    private static BlockPos savedBlockPos;

    @Inject(method = "m_ulptarvl(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo;", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void getFluidAboveStalactite(Level level, BlockPos pos, CallbackInfoReturnable<PointedDripstoneBlock.FluidInfo> cir, BlockPos blockPos, BlockState blockState) {
        if (blockState.is(Blocks.WET_SPONGE) && !level.dimensionType().ultraWarm() && savedBlockPos != null) {
            cir.setReturnValue(new PointedDripstoneBlock.FluidInfo(savedBlockPos, Fluids.WATER, blockState));
            savedBlockPos = null;
        }
    }

    @Inject(method = "m_ulptarvl(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo;", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/core/BlockPos;above()Lnet/minecraft/core/BlockPos;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void getFluidAboveStalactite1(Level level, BlockPos pos, CallbackInfoReturnable<PointedDripstoneBlock.FluidInfo> cir, BlockPos blockPos) {
        savedBlockPos = blockPos;
    }


    @Inject(method = "maybeTransferFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void maybeTransferFluid(BlockState state, ServerLevel level, BlockPos pos, float randChance, CallbackInfo ci, Optional<PointedDripstoneBlock.FluidInfo> optional, Fluid fluid, float f, BlockPos blockPos) {
        if (optional.get().sourceState().is(Blocks.WET_SPONGE) && fluid == Fluids.WATER) {
            BlockState blockState = Blocks.SPONGE.defaultBlockState();
            level.setBlockAndUpdate(optional.get().pos(), blockState);
            Block.pushEntitiesUp(
                    optional.get().sourceState(), blockState, level, optional.get().pos()
            );
            level.gameEvent(GameEvent.BLOCK_CHANGE, optional.get().pos(), GameEvent.Context.of(blockState));
            level.levelEvent(1504, blockPos, 0);
            ci.cancel();
        }
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
