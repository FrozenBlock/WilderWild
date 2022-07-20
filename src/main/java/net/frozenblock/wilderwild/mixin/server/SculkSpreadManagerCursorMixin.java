package net.frozenblock.wilderwild.mixin.server;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.frozenblock.wilderwild.misc.ToSculkSpreader;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Mixin(SculkSpreader.ChargeCursor.class)
public class SculkSpreadManagerCursorMixin {

    @Final
    @Shadow
    private static ObjectArrayList<Vec3i> NON_CORNER_NEIGHBOURS = Util.make(new ObjectArrayList<>(18), (objectArrayList) -> {
        Stream<BlockPos> var10000 = BlockPos.betweenClosedStream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter((pos) -> {
            return (pos.getX() == 0 || pos.getY() == 0 || pos.getZ() == 0) && !pos.equals(BlockPos.ZERO);
        }).map(BlockPos::immutable);
        Objects.requireNonNull(objectArrayList);
        var10000.forEach(objectArrayList::add);
    });
    @Shadow
    private BlockPos pos;
    @Shadow
    int charge;
    @Shadow
    private int updateDelay;
    @Shadow
    private int decayDelay;
    @Nullable
    @Shadow
    private Set<Direction> facings;

    @Overwrite
    public void update(LevelAccessor world, BlockPos pos, RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        SculkSpreader.ChargeCursor cursor = SculkSpreader.ChargeCursor.class.cast(this);
        if (this.shouldUpdate(world, pos, spreadManager.isWorldGeneration())) {
            if (this.updateDelay > 0) {
                --this.updateDelay;
            } else {
                BlockState blockState = world.getBlockState(this.pos);
                SculkBehaviour sculkSpreadable = getSpreadableNew(blockState, spreadManager.isWorldGeneration());
                if (shouldConvertToBlock && sculkSpreadable.attemptSpreadVein(world, this.pos, blockState, this.facings, spreadManager.isWorldGeneration())) { //Place Veins
                    if (sculkSpreadable.canChangeBlockStateOnSpread()) {
                        blockState = world.getBlockState(this.pos);
                        sculkSpreadable = getSpreadableNew(blockState, spreadManager.isWorldGeneration());
                    }

                    world.playSound(null, this.pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                this.charge = sculkSpreadable.attemptUseCharge(cursor, world, pos, random, spreadManager, shouldConvertToBlock);
                if (this.charge <= 0) {
                    sculkSpreadable.onDischarged(world, blockState, this.pos, random);
                } else {
                    BlockPos blockPos = getValidMovementPos(world, this.pos, random);
                    if (blockPos != null) {
                        sculkSpreadable.onDischarged(world, blockState, this.pos, random);
                        this.pos = blockPos.immutable();
                        if (spreadManager.isWorldGeneration() && !this.pos.closerThan(new Vec3i(pos.getX(), this.pos.getY(), pos.getZ()), 15.0D)) {
                            this.charge = 0;
                            return;
                        }

                        blockState = world.getBlockState(blockPos);
                    }

                    if (blockState.getBlock() instanceof SculkBehaviour) {
                        this.facings = MultifaceBlock.availableFaces(blockState);
                    }

                    this.decayDelay = sculkSpreadable.updateDecayDelay(this.decayDelay);
                    this.updateDelay = sculkSpreadable.getSculkSpreadDelay();
                }
            }
        }
    }

    private static SculkBehaviour getSpreadableNew(BlockState state, boolean isWorldGen) {
        Block var2 = state.getBlock();
        SculkBehaviour var10000;
        if (var2 instanceof SculkBehaviour) {
            var10000 = (SculkBehaviour) var2;
        } else if (isWorldGen && (state.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || state.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || state.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN))) {
            var10000 = new ToSculkSpreader();
        } else {
            var10000 = SculkBehaviour.DEFAULT;
        }

        return var10000;
    }

    @Shadow
    private boolean shouldUpdate(LevelAccessor world, BlockPos pos, boolean worldGen) {
        if (this.charge <= 0) {
            return false;
        } else if (worldGen) {
            return true;
        } else if (world instanceof ServerLevel serverWorld) {
            return serverWorld.shouldTickBlocksAt(pos);
        } else {
            return false;
        }
    }

    @Overwrite
    private static boolean isMovementUnobstructed(LevelAccessor world, BlockPos sourcePos, BlockPos targetPos) {
        if (sourcePos.distManhattan(targetPos) == 1) {
            return true;
        } else {
            BlockState cheatState = world.getBlockState(targetPos);
            if (cheatState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || cheatState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || cheatState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
                return true;
            }
            BlockPos blockPos = targetPos.subtract(sourcePos);
            Direction direction = Direction.fromAxisAndDirection(Direction.Axis.X, blockPos.getX() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            Direction direction2 = Direction.fromAxisAndDirection(Direction.Axis.Y, blockPos.getY() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            Direction direction3 = Direction.fromAxisAndDirection(Direction.Axis.Z, blockPos.getZ() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
            if (blockPos.getX() == 0) {
                return isUnobstructed(world, sourcePos, direction2) || isUnobstructed(world, sourcePos, direction3);
            } else if (blockPos.getY() == 0) {
                return isUnobstructed(world, sourcePos, direction) || isUnobstructed(world, sourcePos, direction3);
            } else {
                return isUnobstructed(world, sourcePos, direction) || isUnobstructed(world, sourcePos, direction2);
            }
        }
    }

    @Shadow
    private static boolean isUnobstructed(LevelAccessor world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.relative(direction);
        return !world.getBlockState(blockPos).isFaceSturdy(world, blockPos, direction.getOpposite());
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


    @Overwrite
    @Nullable
    private static BlockPos getValidMovementPos(LevelAccessor world, BlockPos pos, RandomSource random) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        BlockPos.MutableBlockPos mutable2 = pos.mutable();

        for (Vec3i vec3i : getRandomizedNonCornerNeighbourOffsets(random)) {
            mutable2.setWithOffset(pos, vec3i);
            BlockState blockState = world.getBlockState(mutable2);
            boolean isInTags = blockState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || blockState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN);
            if ((blockState.getBlock() instanceof SculkBehaviour || isInTags) && isMovementUnobstructed(world, pos, mutable2)) {
                mutable.set(mutable2);
                if (SculkVeinBlock.hasSubstrateAccess(world, blockState, mutable2)) {
                    break;
                }
            }
        }

        return mutable.equals(pos) ? null : mutable;
    }
}
