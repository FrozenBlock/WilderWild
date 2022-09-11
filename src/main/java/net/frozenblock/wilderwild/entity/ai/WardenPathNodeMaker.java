package net.frozenblock.wilderwild.entity.ai;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.Nullable;

public class WardenPathNodeMaker extends WalkNodeEvaluator {
    private float oldWalkablePenalty;
    private float oldWaterBorderPenalty;

    private final Long2ObjectMap<BlockPathTypes> nodeTypes = new Long2ObjectOpenHashMap<>();

    public WardenPathNodeMaker() {
    }

    @Override
    public void prepare(PathNavigationRegion cachedWorld, Mob entity) {
        super.prepare(cachedWorld, entity);
        entity.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.oldWalkablePenalty = entity.getPathfindingMalus(BlockPathTypes.WALKABLE);
        entity.setPathfindingMalus(BlockPathTypes.WALKABLE, 0.0F);
        this.oldWaterBorderPenalty = entity.getPathfindingMalus(BlockPathTypes.WATER_BORDER);
        entity.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 4.0F);
    }

    @Override
    public void done() {
        this.mob.setPathfindingMalus(BlockPathTypes.WALKABLE, this.oldWalkablePenalty);
        this.mob.setPathfindingMalus(BlockPathTypes.WATER_BORDER, this.oldWaterBorderPenalty);
        super.done();
    }

    @Nullable
    @Override
    public Node getStart() {
        if (this.isEntityTouchingWaterOrLava(this.mob)) {
            return this.getStartNode(
                    new BlockPos(
                            Mth.floor(this.mob.getBoundingBox().minX),
                            Mth.floor(this.mob.getBoundingBox().minY + 0.5),
                            Mth.floor(this.mob.getBoundingBox().minZ)
                    )
            );
        } else {
            return super.getStart();
        }
    }

    private BlockPathTypes getBlockPathType(Mob entity, BlockPos pos) {
        return this.getCachedBlockType(entity, pos.getX(), pos.getY(), pos.getZ());
    }

    protected BlockPathTypes getCachedBlockType(Mob entity, int x, int y, int z) {
        return this.nodeTypes
                .computeIfAbsent(
                        BlockPos.asLong(x, y, z),
                        (l -> this.getBlockPathType(
                                this.level, x, y, z, entity, this.entityWidth, this.entityHeight, this.entityDepth, this.canOpenDoors(), this.canPassDoors()
                        ))
                );
    }

    @Nullable
    @Override
    public Target getGoal(double x, double y, double z) {
        return this.isEntityTouchingWaterOrLava(this.mob) ? this.getTargetFromNode(super.getNode(Mth.floor(x), Mth.floor(y + 0.5), Mth.floor(z))) : super.getGoal(x, y, z);
    }

    @Override
    public int getNeighbors(Node[] successors, Node node) {
        if (isEntityTouchingWaterOrLava(this.mob)) {
            int i = super.getNeighbors(successors, node);
            BlockPathTypes pathNodeType = this.getCachedBlockType(this.mob, node.x, node.y + 1, node.z);
            BlockPathTypes pathNodeType2 = this.getCachedBlockType(this.mob, node.x, node.y, node.z);
            int j;
            if (this.mob.getPathfindingMalus(pathNodeType) >= 0.0F && pathNodeType2 != BlockPathTypes.STICKY_HONEY) {
                j = Mth.floor(Math.max(1.0F, this.mob.maxUpStep));
            } else {
                j = 0;
            }

            double d = this.getFloorLevel(new BlockPos(node.x, node.y, node.z));
            Node pathNode = this.findAcceptedNode(node.x, node.y + 1, node.z, Math.max(0, j - 1), d, Direction.UP, pathNodeType2);
            Node pathNode2 = this.findAcceptedNode(node.x, node.y - 1, node.z, j, d, Direction.DOWN, pathNodeType2);
            if (this.isValidAquaticAdjacentSuccessor(pathNode, node)) {
                successors[i++] = pathNode;
            }

            if (this.isValidAquaticAdjacentSuccessor(pathNode2, node) && pathNodeType2 != BlockPathTypes.TRAPDOOR) {
                successors[i++] = pathNode2;
            }

            return i;
        } else {
            return super.getNeighbors(successors, node);
        }
    }

    protected boolean hasNotVisited(@Nullable Node pathNode) {
        return pathNode != null && !pathNode.closed;
    }

    protected boolean method_38488(@Nullable Node pathNode, @Nullable Node pathNode2, @Nullable Node pathNode3) {
        return this.hasNotVisited(pathNode) && pathNode2 != null && pathNode2.costMalus >= 0.0F && pathNode3 != null && pathNode3.costMalus >= 0.0F;
    }

    private boolean isValidAquaticAdjacentSuccessor(@Nullable Node node, Node successor) {
        return this.isNeighborValid(node, successor) && node.type == BlockPathTypes.WATER;
    }

    @Override
    protected double getFloorLevel(BlockPos pos) {
        return this.isEntityTouchingWaterOrLava(this.mob) ? (double) pos.getY() + 0.5 : super.getFloorLevel(pos);
    }

    @Override
    protected boolean isAmphibious() {
        return true;
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter world, int x, int y, int z) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPathTypes pathNodeType = getBlockPathTypeRaw(world, mutable.set(x, y, z));
        if (pathNodeType == BlockPathTypes.WATER || pathNodeType == BlockPathTypes.LAVA) {
            for (Direction direction : Direction.values()) {
                BlockPathTypes pathNodeType2 = getBlockPathTypeRaw(world, mutable.set(x, y, z).move(direction));
                if (pathNodeType2 == BlockPathTypes.BLOCKED) {
                    return BlockPathTypes.WATER_BORDER;
                }
            }

            if (pathNodeType == BlockPathTypes.WATER) {
                return BlockPathTypes.WATER;
            } else {
                return BlockPathTypes.LAVA;
            }
        } else {
            return super.getBlockPathType(world, x, y, z);
        }
    }

    private boolean isEntityTouchingWaterOrLava(Entity entity) {
        return entity.isInWaterOrBubble() || entity.isInLava() || entity.isVisuallySwimming();
    }

    private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
        return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
    }
}

