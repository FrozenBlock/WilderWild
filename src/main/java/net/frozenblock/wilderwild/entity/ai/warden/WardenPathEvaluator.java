/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.ai.warden;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WardenPathEvaluator extends WalkNodeEvaluator {
    private float oldWalkablePenalty;
    private float oldWaterBorderPenalty;

    private final Long2ObjectMap<BlockPathTypes> nodeTypes = new Long2ObjectOpenHashMap<>();

    public WardenPathEvaluator() {
    }

    @Override
    public void prepare(@NotNull PathNavigationRegion level, @NotNull Mob mob) {
        super.prepare(level, mob);
        mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.oldWalkablePenalty = mob.getPathfindingMalus(BlockPathTypes.WALKABLE);
        mob.setPathfindingMalus(BlockPathTypes.WALKABLE, 0.0F);
        this.oldWaterBorderPenalty = mob.getPathfindingMalus(BlockPathTypes.WATER_BORDER);
        mob.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 4.0F);
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

	@Override
    protected BlockPathTypes getBlockPathType(@NotNull Mob entity, BlockPos pos) {
        return this.getCachedBlockType(entity, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected BlockPathTypes getCachedBlockType(@NotNull Mob entity, int x, int y, int z) {
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
    public int getNeighbors(Node @NotNull [] successors, @NotNull Node node) {
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

    private boolean isValidAquaticAdjacentSuccessor(@Nullable Node node, Node successor) {
        return this.isNeighborValid(node, successor) && node.type == BlockPathTypes.WATER;
    }

    @Override
    protected double getFloorLevel(@NotNull BlockPos pos) {
        return this.isEntityTouchingWaterOrLava(this.mob) ? (double) pos.getY() + 0.5 : super.getFloorLevel(pos);
    }

    @Override
    protected boolean isAmphibious() {
        return true;
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPathTypes pathNodeType = getBlockPathTypeRaw(level, mutable.set(x, y, z));
        if (pathNodeType == BlockPathTypes.WATER || pathNodeType == BlockPathTypes.LAVA) {
            for (Direction direction : Direction.values()) {
                BlockPathTypes pathNodeType2 = getBlockPathTypeRaw(level, mutable.set(x, y, z).move(direction));
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
            return super.getBlockPathType(level, x, y, z);
        }
    }

    private boolean isEntityTouchingWaterOrLava(Entity entity) {
        return entity.isInWaterOrBubble() || entity.isInLava() || entity.isVisuallySwimming();
    }

    private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
        return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
    }
}

