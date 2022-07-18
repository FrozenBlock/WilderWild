package net.frozenblock.wilderwild.entity.ai;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.TargetPathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;
import org.jetbrains.annotations.Nullable;

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
            return super.getStart();
        }
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
        return this.isEntityTouchingWaterOrLava(this.entity) ? this.asTargetPathNode(super.getNode(MathHelper.floor(x), MathHelper.floor(y + 0.5), MathHelper.floor(z))) : super.getNode(x, y, z);
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        if (isEntityTouchingWaterOrLava(this.entity)) {
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
            PathNode pathNode = this.getPathNode(node.x, node.y + 1, node.z, Math.max(0, j - 1), d, Direction.UP, pathNodeType2);
            PathNode pathNode2 = this.getPathNode(node.x, node.y - 1, node.z, j, d, Direction.DOWN, pathNodeType2);
            if (this.isValidAquaticAdjacentSuccessor(pathNode, node)) {
                successors[i++] = pathNode;
            }

            if (this.isValidAquaticAdjacentSuccessor(pathNode2, node) && pathNodeType2 != PathNodeType.TRAPDOOR) {
                successors[i++] = pathNode2;
            }

            return i;
        } else {
            return super.getSuccessors(successors, node);
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
            return super.getDefaultNodeType(world, x, y, z);
        }
    }

    private boolean isEntityTouchingWaterOrLava(Entity entity) {
        return entity.isTouchingWater() || entity.isInLava();
    }

    private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
        return entity.isSubmergedIn(FluidTags.WATER) || entity.isSubmergedIn(FluidTags.LAVA);
    }
}

