package net.frozenblock.wilderwild.mixin.server;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.frozenblock.wilderwild.misc.ToSculkSpreader;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Mixin(SculkSpreadManager.Cursor.class)
public class SculkSpreadManagerCursorMixin {

    @Final
    @Shadow
    private static ObjectArrayList OFFSETS = Util.make(new ObjectArrayList(18), (objectArrayList) -> {
        Stream<BlockPos> var10000 = BlockPos.stream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter((pos) -> {
            return (pos.getX() == 0 || pos.getY() == 0 || pos.getZ() == 0) && !pos.equals(BlockPos.ORIGIN);
        }).map(BlockPos::toImmutable);
        Objects.requireNonNull(objectArrayList);
        var10000.forEach(objectArrayList::add);
    });
    @Shadow
    private BlockPos pos;
    @Shadow
    int charge;
    @Shadow
    private int update;
    @Shadow
    private int decay;
    @Nullable
    @Shadow
    private Set<Direction> faces;

    /**
     * @author Frozenblock
     * @reason
     */
    @Overwrite
    public void spread(WorldAccess world, BlockPos pos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        SculkSpreadManager.Cursor cursor = SculkSpreadManager.Cursor.class.cast(this);
        if (this.canSpread(world, pos, !spreadManager.isWorldGen())) {
            if (this.update > 0) {
                --this.update;
            } else {
                BlockState blockState = world.getBlockState(this.pos);
                SculkSpreadable sculkSpreadable = getSpreadableNew(blockState, !spreadManager.isWorldGen());
                if (shouldConvertToBlock && sculkSpreadable.spread(world, this.pos, blockState, this.faces, spreadManager.isWorldGen())) { //Place Veins
                    if (sculkSpreadable.shouldConvertToSpreadable()) {
                        blockState = world.getBlockState(this.pos);
                        sculkSpreadable = getSpreadableNew(blockState, !spreadManager.isWorldGen());
                    }

                    world.playSound(null, this.pos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                this.charge = sculkSpreadable.spread(cursor, world, pos, random, spreadManager, shouldConvertToBlock);
                if (this.charge <= 0) {
                    sculkSpreadable.spreadAtSamePosition(world, blockState, this.pos, random);
                } else {
                    BlockPos blockPos = getSpreadPos(world, this.pos, random);
                    if (blockPos != null) {
                        sculkSpreadable.spreadAtSamePosition(world, blockState, this.pos, random);
                        this.pos = blockPos.toImmutable();
                        if (!spreadManager.isWorldGen() && !this.pos.isWithinDistance(new Vec3i(pos.getX(), this.pos.getY(), pos.getZ()), 15.0D)) {
                            this.charge = 0;
                            return;
                        }

                        blockState = world.getBlockState(blockPos);
                    }

                    if (blockState.getBlock() instanceof SculkSpreadable) {
                        this.faces = MultifaceGrowthBlock.collectDirections(blockState);
                    }

                    this.decay = sculkSpreadable.getDecay(this.decay);
                    this.update = sculkSpreadable.getUpdate();
                }
            }
        }
    }

    private static SculkSpreadable getSpreadableNew(BlockState state, boolean isWorldGen) {
        Block var2 = state.getBlock();
        SculkSpreadable var10000;
        if (var2 instanceof SculkSpreadable) {
            var10000 = (SculkSpreadable)var2;
        } else if (isWorldGen && (state.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || state.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || state.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN))) {
            var10000 = new ToSculkSpreader();
        } else {
            var10000 = SculkSpreadable.VEIN_ONLY_SPREADER;
        }

        return var10000;
    }

    @Shadow
    private boolean canSpread(WorldAccess world, BlockPos pos, boolean worldGen) {
        if (this.charge <= 0) {
            return false;
        } else if (worldGen) {
            return true;
        } else if (world instanceof ServerWorld serverWorld) {
            return serverWorld.shouldTickBlockPos(pos);
        } else {
            return false;
        }
    }

    @Shadow
    private static boolean canSpread(WorldAccess world, BlockPos sourcePos, BlockPos targetPos) {
        if (sourcePos.getManhattanDistance(targetPos) == 1) {
            return true;
        } else {
            BlockState cheatState = world.getBlockState(targetPos);
            if (cheatState.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || cheatState.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || cheatState.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
                return true;
            }
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
    private static SculkSpreadable getSpreadable(BlockState state) {
        Block var2 = state.getBlock();
        SculkSpreadable var10000;
        if (var2 instanceof SculkSpreadable) {
            var10000 = (SculkSpreadable)var2;
        } else {
            var10000 = SculkSpreadable.VEIN_ONLY_SPREADER;
        }

        return var10000;
    }

    @Shadow
    private static List<Vec3i> shuffleOffsets(Random random) {
        return Util.copyShuffled(OFFSETS, random);
    }

    @Shadow
    @Nullable
    private static BlockPos getSpreadPos(WorldAccess world, BlockPos pos, Random random) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        BlockPos.Mutable mutable2 = pos.mutableCopy();

        for (Vec3i vec3i : shuffleOffsets(random)) {
            mutable2.set(pos, vec3i);
            BlockState blockState = world.getBlockState(mutable2);
            boolean isInTags = blockState.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || blockState.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || blockState.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN);
            if ((blockState.getBlock() instanceof SculkSpreadable || isInTags) && canSpread(world, pos, mutable2)) {
                mutable.set(mutable2);
                if (SculkVeinBlock.veinCoversSculkReplaceable(world, blockState, mutable2)) {
                    break;
                }
            }
        }

        return mutable.equals(pos) ? null : mutable;
    }

}
