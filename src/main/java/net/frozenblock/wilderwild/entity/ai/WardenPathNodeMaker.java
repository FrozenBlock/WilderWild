package net.frozenblock.wilderwild.entity.ai;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WardenPathNodeMaker extends LandPathNodeMaker {
    private float oldWalkablePenalty;
    private float oldWaterBorderPenalty;

    private final Long2ObjectMap<PathNodeType> nodeTypes = new Long2ObjectOpenHashMap<>();

    public WardenPathNodeMaker() {
    }

    @Override
    public void init(ChunkCache cachedWorld, MobEntity entity) {
        super.init(cachedWorld, entity);
        entity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.oldWalkablePenalty = entity.getPathfindingPenalty(PathNodeType.WALKABLE);
        entity.setPathfindingPenalty(PathNodeType.WALKABLE, 0.0F);
        this.oldWaterBorderPenalty = entity.getPathfindingPenalty(PathNodeType.WATER_BORDER);
        entity.setPathfindingPenalty(PathNodeType.WATER_BORDER, 4.0F);
    }

    @Override
    public void clear() {
        this.entity.setPathfindingPenalty(PathNodeType.WALKABLE, this.oldWalkablePenalty);
        this.entity.setPathfindingPenalty(PathNodeType.WATER_BORDER, this.oldWaterBorderPenalty);
        this.nodeTypes.clear();
        super.clear();
    }

    @Nullable
    @Override
    public PathNode getStart() {
        if (this.isEntityTouchingWaterOrLava(this.entity)) {
            return this.getStart(
                    new BlockPos(
                            MathHelper.floor(this.entity.getBoundingBox().minX),
                            MathHelper.floor(this.entity.getBoundingBox().minY + 0.5),
                            MathHelper.floor(this.entity.getBoundingBox().minZ)
                    )
            );
        } else {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int i = this.entity.getBlockY();
            BlockState blockState = this.cachedWorld.getBlockState(mutable.set(this.entity.getX(), i, this.entity.getZ()));
            if (!this.entity.canWalkOnFluid(blockState.getFluidState())) {
                if (this.canSwim() && this.entity.isTouchingWater()) {
                    while (true) {
                        if (!blockState.isOf(Blocks.WATER) && blockState.getFluidState() != Fluids.WATER.getStill(false)) {
                            --i;
                            break;
                        }

                        blockState = this.cachedWorld.getBlockState(mutable.set(this.entity.getX(), ++i, this.entity.getZ()));
                    }
                } else if (this.entity.isOnGround()) {
                    i = MathHelper.floor(this.entity.getY() + 0.5);
                } else {
                    BlockPos blockPos = this.entity.getBlockPos();

                    while (
                            (
                                    this.cachedWorld.getBlockState(blockPos).isAir()
                                            || this.cachedWorld.getBlockState(blockPos).canPathfindThrough(this.cachedWorld, blockPos, NavigationType.LAND)
                            )
                                    && blockPos.getY() > this.entity.world.getBottomY()
                    ) {
                        blockPos = blockPos.down();
                    }

                    i = blockPos.up().getY();
                }
            } else {
                while (this.entity.canWalkOnFluid(blockState.getFluidState())) {
                    blockState = this.cachedWorld.getBlockState(mutable.set(this.entity.getX(), ++i, this.entity.getZ()));
                }

                --i;
            }

            BlockPos blockPos = this.entity.getBlockPos();
            PathNodeType pathNodeType = this.getNodeType(this.entity, blockPos.getX(), i, blockPos.getZ());
            if (this.entity.getPathfindingPenalty(pathNodeType) < 0.0F) {
                Box box = this.entity.getBoundingBox();
                if (this.canPathThrough(mutable.set(box.minX, i, box.minZ))
                        || this.canPathThrough(mutable.set(box.minX, i, box.maxZ))
                        || this.canPathThrough(mutable.set(box.maxX, i, box.minZ))
                        || this.canPathThrough(mutable.set(box.maxX, i, box.maxZ))) {
                    return this.getStart(mutable);
                }
            }

            return this.getStart(new BlockPos(blockPos.getX(), i, blockPos.getZ()));
        }
    }

    private boolean canPathThrough(BlockPos pos) {
        PathNodeType pathNodeType = this.getNodeType(this.entity, pos);
        return this.entity.getPathfindingPenalty(pathNodeType) >= 0.0F;
    }

    private PathNodeType getNodeType(MobEntity entity, BlockPos pos) {
        return this.getNodeType(entity, pos.getX(), pos.getY(), pos.getZ());
    }

    protected PathNodeType getNodeType(MobEntity entity, int x, int y, int z) {
        return this.nodeTypes
                .computeIfAbsent(
                        BlockPos.asLong(x, y, z),
                        (l -> this.getNodeType(
                                this.cachedWorld, x, y, z, entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, this.canOpenDoors(), this.canEnterOpenDoors()
                        ))
                );
    }

    @Nullable
    @Override
    public TargetPathNode getNode(double x, double y, double z) {
        return this.asTargetPathNode(super.getNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        if (isEntityTouchingWaterOrLava(this.entity)) {
            int i = 0;
            Map<Direction, PathNode> map = Maps.newEnumMap(Direction.class);

            for(Direction direction : Direction.values()) {
                PathNode pathNode = this.getNode(node.x + direction.getOffsetX(), node.y + direction.getOffsetY(), node.z + direction.getOffsetZ());
                map.put(direction, pathNode);
                if (this.hasNotVisited(pathNode)) {
                    successors[i++] = pathNode;
                }
            }

            for(Direction direction2 : Direction.Type.HORIZONTAL) {
                Direction direction3 = direction2.rotateYClockwise();
                PathNode pathNode2 = this.getNode(
                        node.x + direction2.getOffsetX() + direction3.getOffsetX(), node.y, node.z + direction2.getOffsetZ() + direction3.getOffsetZ()
                );
                if (this.method_38488(pathNode2, map.get(direction2), map.get(direction3))) {
                    successors[i++] = pathNode2;
                }
            }

            return i;
        } else {
            int i = super.getSuccessors(successors, node);
            PathNodeType pathNodeType = this.getNodeType(this.entity, node.x, node.y + 1, node.z);
            PathNodeType pathNodeType2 = this.getNodeType(this.entity, node.x, node.y, node.z);
            int j;
            if (this.entity.getPathfindingPenalty(pathNodeType) >= 0.0F && pathNodeType2 != PathNodeType.STICKY_HONEY) {
                j = MathHelper.floor(Math.max(1.0F, this.entity.stepHeight));
            } else {
                j = 0;
            }

            double d = this.getFeetY(new BlockPos(node.x, node.y, node.z));
            PathNode pathNode = this.getPathNode(node.x, node.y, node.z + 1, j, d, Direction.SOUTH, pathNodeType2);
            if (this.isValidAdjacentSuccessor(pathNode, node)) {
                successors[i++] = pathNode;
            }

            PathNode pathNode2 = this.getPathNode(node.x - 1, node.y, node.z, j, d, Direction.WEST, pathNodeType2);
            if (this.isValidAdjacentSuccessor(pathNode2, node)) {
                successors[i++] = pathNode2;
            }

            PathNode pathNode3 = this.getPathNode(node.x + 1, node.y, node.z, j, d, Direction.EAST, pathNodeType2);
            if (this.isValidAdjacentSuccessor(pathNode3, node)) {
                successors[i++] = pathNode3;
            }

            PathNode pathNode4 = this.getPathNode(node.x, node.y, node.z - 1, j, d, Direction.NORTH, pathNodeType2);
            if (this.isValidAdjacentSuccessor(pathNode4, node)) {
                successors[i++] = pathNode4;
            }

            PathNode pathNode5 = this.getPathNode(node.x - 1, node.y, node.z - 1, j, d, Direction.NORTH, pathNodeType2);
            if (this.isValidDiagonalSuccessor(node, pathNode2, pathNode4, pathNode5)) {
                successors[i++] = pathNode5;
            }

            PathNode pathNode6 = this.getPathNode(node.x + 1, node.y, node.z - 1, j, d, Direction.NORTH, pathNodeType2);
            if (this.isValidDiagonalSuccessor(node, pathNode3, pathNode4, pathNode6)) {
                successors[i++] = pathNode6;
            }

            PathNode pathNode7 = this.getPathNode(node.x - 1, node.y, node.z + 1, j, d, Direction.SOUTH, pathNodeType2);
            if (this.isValidDiagonalSuccessor(node, pathNode2, pathNode, pathNode7)) {
                successors[i++] = pathNode7;
            }

            PathNode pathNode8 = this.getPathNode(node.x + 1, node.y, node.z + 1, j, d, Direction.SOUTH, pathNodeType2);
            if (this.isValidDiagonalSuccessor(node, pathNode3, pathNode, pathNode8)) {
                successors[i++] = pathNode8;
            }

            return i;
        }
    }

    protected boolean hasNotVisited(@Nullable PathNode pathNode) {
        return pathNode != null && !pathNode.visited;
    }

    protected boolean method_38488(@Nullable PathNode pathNode, @Nullable PathNode pathNode2, @Nullable PathNode pathNode3) {
        return this.hasNotVisited(pathNode) && pathNode2 != null && pathNode2.penalty >= 0.0F && pathNode3 != null && pathNode3.penalty >= 0.0F;
    }

    private boolean isValidAquaticAdjacentSuccessor(@Nullable PathNode node, PathNode successor) {
        return this.isValidAdjacentSuccessor(node, successor) && node.type == PathNodeType.WATER;
    }

    @Override
    protected double getFeetY(BlockPos pos) {
        return this.isEntityTouchingWaterOrLava(this.entity) ? (double) pos.getY() + 0.5 : super.getFeetY(pos);
    }

    @Override
    protected boolean isAmphibious() {
        return true;
    }

    @Override
    public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        PathNodeType pathNodeType = getCommonNodeType(world, mutable.set(x, y, z));
        if (pathNodeType == PathNodeType.WATER || pathNodeType == PathNodeType.LAVA) {
            for (Direction direction : Direction.values()) {
                PathNodeType pathNodeType2 = getCommonNodeType(world, mutable.set(x, y, z).move(direction));
                if (pathNodeType2 == PathNodeType.BLOCKED) {
                    return PathNodeType.WATER_BORDER;
                }
            }

            if (pathNodeType == PathNodeType.WATER) {
                return PathNodeType.WATER;
            } else {
                return PathNodeType.LAVA;
            }
        } else {
            return getLandNodeType(world, mutable);
        }
    }

    private boolean isEntityTouchingWaterOrLava(Entity entity) {
        return entity.isTouchingWater() || entity.isInLava();
    }

    private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
        return entity.isSubmergedIn(FluidTags.WATER) || entity.isSubmergedIn(FluidTags.LAVA);
    }
}

