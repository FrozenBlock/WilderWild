package net.frozenblock.wilderwild.mixin.server;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.frozenblock.wilderwild.misc.WilderSculkSpreader;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSpreadable;
import net.minecraft.block.SculkVeinBlock;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Mixin(SculkSpreadManager.Cursor.class)
public class SculkSpreadManagerCursorMixin { //TODO: make sculk stairs/slabs/walls spread outside of just worldgen for the sake of making the blocks accessible to builders

    @Final
    @Shadow
    private static final ObjectArrayList<Vec3i> OFFSETS = Util.make(new ObjectArrayList<>(18), (objectArrayList) -> {
        Stream<BlockPos> var10000 = BlockPos.stream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter((pos) -> {
            return (pos.getX() == 0 || pos.getY() == 0 || pos.getZ() == 0) && !pos.equals(BlockPos.ORIGIN);
        }).map(BlockPos::toImmutable);
        Objects.requireNonNull(objectArrayList);
        var10000.forEach(objectArrayList::add);
    });

    @Inject(method = "getSpreadable", at = @At("HEAD"), cancellable = true)
    private static void getSpreadable(BlockState state, CallbackInfoReturnable<SculkSpreadable> cir) {
        if (state.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || state.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || state.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
            cir.setReturnValue(new WilderSculkSpreader());
            cir.cancel();
        }
    }

    @Inject(method = "canSpread(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    private static void canSpread(WorldAccess world, BlockPos sourcePos, BlockPos targetPos, CallbackInfoReturnable<Boolean> cir) {
        if (!(sourcePos.getManhattanDistance(targetPos) == 1)) {
            BlockState cheatState = world.getBlockState(targetPos);
            if (cheatState.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || cheatState.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || cheatState.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    @Shadow
    private static boolean canSpread(WorldAccess world, BlockPos sourcePos, BlockPos targetPos) {
        if (sourcePos.getManhattanDistance(targetPos) == 1) {
            return true;
        } else {
            BlockPos blockPos = targetPos.subtract(sourcePos);
            Direction direction = Direction.from(Direction.Axis.X, blockPos.getX() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            Direction direction2 = Direction.from(Direction.Axis.Y, blockPos.getY() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            Direction direction3 = Direction.from(Direction.Axis.Z, blockPos.getZ() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            if (blockPos.getX() == 0) {
                return canSpread(world, sourcePos, direction2) || canSpread(world, sourcePos, direction3);
            } else if (blockPos.getY() == 0) {
                return canSpread(world, sourcePos, direction) || canSpread(world, sourcePos, direction3);
            } else {
                return canSpread(world, sourcePos, direction) || canSpread(world, sourcePos, direction2);
            }
        }
    }

    @Shadow
    private static boolean canSpread(WorldAccess world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        return !world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
    }

    @Shadow
    private static List<Vec3i> shuffleOffsets(Random random) {
        return Util.copyShuffled(OFFSETS, random);
    }

    @Inject(method = "getSpreadPos", at = @At("HEAD"), cancellable = true)
    private static void getSpreadPos(WorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<BlockPos> cir) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        BlockPos.Mutable mutable2 = pos.mutableCopy();

        boolean canReturn = false;
        for (Vec3i vec3i : shuffleOffsets(random)) {
            mutable2.set(pos, vec3i);
            BlockState blockState = world.getBlockState(mutable2);
            boolean isInTags = blockState.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || blockState.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || blockState.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN);
            if (isInTags && canSpread(world, pos, mutable2)) {
                mutable.set(mutable2);
                canReturn = true;
                if (SculkVeinBlock.veinCoversSculkReplaceable(world, blockState, mutable2)) {
                    break;
                }
            }
        }

        if (canReturn) {
            cir.setReturnValue(mutable.equals(pos) ? null : mutable);
            cir.cancel();
        }
    }
}
