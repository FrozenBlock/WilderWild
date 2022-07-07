package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.ai.pathing.AmphibiousPathNodeMaker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;
import org.jetbrains.annotations.Nullable;

public class WardenPathNodeMaker extends AmphibiousPathNodeMaker {
    private final boolean penalizeDeepWater;
    private float oldWalkablePenalty;
    private float oldWaterBorderPenalty;

    public WardenPathNodeMaker(boolean penalizeDeepWater) {
        super(penalizeDeepWater);
        this.penalizeDeepWater = penalizeDeepWater;
    }

    @Override
    public void init(ChunkCache cachedWorld, MobEntity entity) {
        super.init(cachedWorld, entity);
        entity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.oldWalkablePenalty = entity.getPathfindingPenalty(PathNodeType.WALKABLE);
        entity.setPathfindingPenalty(PathNodeType.WALKABLE, 0.0F);
        this.oldWaterBorderPenalty = entity.getPathfindingPenalty(PathNodeType.WATER_BORDER);
        entity.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
    }

    @Override
    public void clear() {
        this.entity.setPathfindingPenalty(PathNodeType.WALKABLE, this.oldWalkablePenalty);
        this.entity.setPathfindingPenalty(PathNodeType.WATER_BORDER, this.oldWaterBorderPenalty);
        super.clear();
    }

    @Nullable
    @Override
    public PathNode getStart() {
        return this.getStart(
                new BlockPos(
                        MathHelper.floor(this.entity.getBoundingBox().minX),
                        MathHelper.floor(this.entity.getBoundingBox().minY + 0.5),
                        MathHelper.floor(this.entity.getBoundingBox().minZ)
                )
        );
    }

    @Nullable
    @Override
    public TargetPathNode getNode(double x, double y, double z) {
        return this.asTargetPathNode(this.getNode(MathHelper.floor(x), MathHelper.floor(y + 0.5), MathHelper.floor(z)));
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
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
        if (isEntityTouchingWaterOrLava(this.entity)) {
            PathNode pathNode = this.getPathNode(node.x, node.y + 1, node.z, Math.max(0, j - 1), d, Direction.UP, pathNodeType2);
            PathNode pathNode2 = this.getPathNode(node.x, node.y - 1, node.z, j, d, Direction.DOWN, pathNodeType2);
            if (this.isValidAquaticAdjacentSuccessor(pathNode, node)) {
                successors[i++] = pathNode;
            }

            if (this.isValidAquaticAdjacentSuccessor(pathNode2, node) && pathNodeType2 != PathNodeType.TRAPDOOR) {
                successors[i++] = pathNode2;
            }

            for (int k = 0; k < i; ++k) {
                PathNode pathNode3 = successors[k];
                if (pathNode3.type == PathNodeType.WATER && this.penalizeDeepWater && pathNode3.y < this.entity.world.getSeaLevel() - 10) {
                    ++pathNode3.penalty;
                }
            }

            return i;
        } else {
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

    private boolean isValidAquaticAdjacentSuccessor(@Nullable PathNode node, PathNode successor) {
        return this.isValidAdjacentSuccessor(node, successor) && node.type == PathNodeType.WATER;
    }

    @Override
    protected double getFeetY(BlockPos pos) {
        return this.entity.isTouchingWater() ? (double)pos.getY() + 0.5 : super.getFeetY(pos);
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
            for(Direction direction : Direction.values()) {
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

