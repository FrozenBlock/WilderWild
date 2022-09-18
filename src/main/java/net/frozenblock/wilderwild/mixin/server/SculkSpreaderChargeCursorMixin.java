package net.frozenblock.wilderwild.mixin.server;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.frozenblock.wilderwild.misc.BooleanPropertySculkBehavior;
import net.frozenblock.wilderwild.misc.SlabWallStairSculkBehavior;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Mixin(SculkSpreader.ChargeCursor.class)
public class SculkSpreaderChargeCursorMixin {


    @Shadow
    private static final ObjectArrayList<Vec3i> NON_CORNER_NEIGHBOURS = Util.make(new ObjectArrayList<>(18), (objectArrayList) -> {
        Stream<BlockPos> var10000 = BlockPos.betweenClosedStream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter((pos) -> (pos.getX() == 0 || pos.getY() == 0 || pos.getZ() == 0) && !pos.equals(BlockPos.ZERO)).map(BlockPos::immutable);
        Objects.requireNonNull(objectArrayList);
        var10000.forEach(objectArrayList::add);
    });

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkSpreader$ChargeCursor;getBlockBehaviour(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/SculkBehaviour;"))
    private SculkBehaviour newSculkBehaviour(BlockState blockState, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader behavior, boolean spread) {
        return getBlockBehaviourNew(blockState, behavior.isWorldGeneration());
    }

    @Unique
    private static SculkBehaviour getBlockBehaviourNew(BlockState state, boolean isWorldGen) {
        if (isWorldGen) {
            if (state.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || state.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || state.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || state.is(WilderBlockTags.SCULK_WALL_REPLACEABLE) || state.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE) || state.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
                return new SlabWallStairSculkBehavior();
            } else if (state.is(RegisterBlocks.STONE_CHEST)) {
                return new BooleanPropertySculkBehavior(RegisterProperties.HAS_SCULK, true);
            }
        } else {
            if (state.is(WilderBlockTags.SCULK_WALL_REPLACEABLE) || state.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE) || state.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
                return new SlabWallStairSculkBehavior();
            }
        }
        return getBlockBehaviour(state);
    }

    @Inject(method = "isMovementUnobstructed", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;subtract(Lnet/minecraft/core/Vec3i;)Lnet/minecraft/core/BlockPos;", shift = At.Shift.BEFORE), cancellable = true)
    private static void isMovementUnobstructed(LevelAccessor level, BlockPos startPos, BlockPos spreadPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState cheatState = level.getBlockState(spreadPos);
        if (cheatState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || cheatState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || cheatState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || cheatState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE) || cheatState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE) || cheatState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE) || (cheatState.is(RegisterBlocks.STONE_CHEST) && !cheatState.getValue(RegisterProperties.HAS_SCULK))) {
            cir.setReturnValue(true);
        }
    }

    @Shadow
    private static SculkBehaviour getBlockBehaviour(BlockState state) {
        Block var2 = state.getBlock();
        SculkBehaviour var10000;
        if (var2 instanceof SculkBehaviour) {
            var10000 = (SculkBehaviour) var2;
        } else {
            var10000 = SculkBehaviour.DEFAULT;
        }

        return var10000;
    }

    @Shadow
    private static List<Vec3i> getRandomizedNonCornerNeighbourOffsets(RandomSource random) {
        return Util.shuffledCopy(NON_CORNER_NEIGHBOURS, random);
    }

    @Shadow
    private static boolean isMovementUnobstructed(LevelAccessor level, BlockPos sourcePos, BlockPos targetPos) {
        return false;
    }

    @Inject(method = "getValidMovementPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void getValidMovementPos(LevelAccessor level, BlockPos pos, RandomSource random, CallbackInfoReturnable<BlockPos> cir, BlockPos.MutableBlockPos mutable, BlockPos.MutableBlockPos mutable2, Iterator<Vec3i> var5, Vec3i vec3i) {
        boolean canReturn = false;
        BlockState blockState = level.getBlockState(mutable2);
        boolean isInTags = blockState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE) || blockState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE) || blockState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE) || (blockState.is(RegisterBlocks.STONE_CHEST) && !blockState.getValue(RegisterProperties.HAS_SCULK));
        if (isInTags && isMovementUnobstructed(level, pos, mutable2)) {
            mutable.set(mutable2);
            canReturn = true;
            if (SculkVeinBlock.hasSubstrateAccess(level, blockState, mutable2)) {
                cir.cancel();
            }
        }

        if (canReturn) {
            cir.setReturnValue(mutable.equals(pos) ? null : mutable);
        }
    }
}
