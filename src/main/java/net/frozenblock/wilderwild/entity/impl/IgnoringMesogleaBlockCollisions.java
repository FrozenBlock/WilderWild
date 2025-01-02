/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.impl;

import com.google.common.collect.AbstractIterator;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IgnoringMesogleaBlockCollisions extends AbstractIterator<VoxelShape> {
	private final AABB box;
	private final CollisionContext context;
	private final Cursor3D cursor;
	private final BlockPos.MutableBlockPos pos;
	private final VoxelShape entityShape;
	private final CollisionGetter collisionGetter;
	private final boolean onlySuffocatingBlocks;
	@Nullable
	private BlockGetter cachedBlockGetter;
	private long cachedBlockGetterPos;

	public IgnoringMesogleaBlockCollisions(CollisionGetter collisionGetter, @Nullable Entity entity, AABB box) {
		this(collisionGetter, entity, box, false);
	}

	public IgnoringMesogleaBlockCollisions(CollisionGetter collisionGetter, @Nullable Entity entity, AABB box, boolean onlySuffocatingBlocks) {
		this.context = entity == null ? CollisionContext.empty() : CollisionContext.of(entity);
		this.pos = new BlockPos.MutableBlockPos();
		this.entityShape = Shapes.create(box);
		this.collisionGetter = collisionGetter;
		this.box = box;
		this.onlySuffocatingBlocks = onlySuffocatingBlocks;
		int i = Mth.floor(box.minX - 1.0E-7) - 1;
		int j = Mth.floor(box.maxX + 1.0E-7) + 1;
		int k = Mth.floor(box.minY - 1.0E-7) - 1;
		int l = Mth.floor(box.maxY + 1.0E-7) + 1;
		int m = Mth.floor(box.minZ - 1.0E-7) - 1;
		int n = Mth.floor(box.maxZ + 1.0E-7) + 1;
		this.cursor = new Cursor3D(i, k, m, j, l, n);
	}

	public static boolean noJellyCollision(Level level, @Nullable Entity entity, AABB collisionBox) {
		for (VoxelShape voxelShape : IgnoringMesogleaBlockCollisions.getJellyBlockCollisions(entity, collisionBox, level)) {
			if (voxelShape.isEmpty()) continue;
			return false;
		}
		if (!level.getEntityCollisions(entity, collisionBox).isEmpty()) {
			return false;
		}
		if (entity != null) {
			WorldBorder worldBorder = level.getWorldBorder();
			VoxelShape voxelShape2 = worldBorder.isInsideCloseToBorder(entity, collisionBox) ? worldBorder.getCollisionShape() : null;
			return voxelShape2 == null || !Shapes.joinIsNotEmpty(voxelShape2, Shapes.create(collisionBox), BooleanOp.AND);
		}
		return true;
	}

	@NotNull
	public static Iterable<VoxelShape> getJellyBlockCollisions(@Nullable Entity entity, AABB collisionBox, Level level) {
		return () -> new IgnoringMesogleaBlockCollisions(level, entity, collisionBox);
	}

	@Nullable
	private BlockGetter getChunk(int x, int z) {
		BlockGetter blockGetter;
		int i = SectionPos.blockToSectionCoord(x);
		int j = SectionPos.blockToSectionCoord(z);
		long l = ChunkPos.asLong(i, j);
		if (this.cachedBlockGetter != null && this.cachedBlockGetterPos == l) {
			return this.cachedBlockGetter;
		}
		this.cachedBlockGetter = blockGetter = this.collisionGetter.getChunkForCollisions(i, j);
		this.cachedBlockGetterPos = l;
		return blockGetter;
	}

	@Override
	protected VoxelShape computeNext() {
		while (this.cursor.advance()) {
			BlockGetter blockGetter;
			int i = this.cursor.nextX();
			int j = this.cursor.nextY();
			int k = this.cursor.nextZ();
			int l = this.cursor.getNextType();
			if (l == 3 || (blockGetter = this.getChunk(i, k)) == null) continue;
			this.pos.set(i, j, k);
			BlockState blockState = blockGetter.getBlockState(this.pos);
			Block block = blockState.getBlock();
			if ((this.onlySuffocatingBlocks && !blockState.isSuffocating(blockGetter, this.pos) || l == 1 && !blockState.hasLargeCollisionShape() || l == 2 && !blockState.is(Blocks.MOVING_PISTON)) || (block instanceof MesogleaBlock && blockState.getValue(BlockStateProperties.WATERLOGGED)))
				continue;
			VoxelShape voxelShape = blockState.getCollisionShape(this.collisionGetter, this.pos, this.context);
			if (voxelShape == Shapes.block()) {
				if (!this.box.intersects(i, j, k, (double) i + 1.0, (double) j + 1.0, (double) k + 1.0)) continue;
				return voxelShape.move(i, j, k);
			}
			VoxelShape voxelShape2 = voxelShape.move(i, j, k);
			if (!Shapes.joinIsNotEmpty(voxelShape2, this.entityShape, BooleanOp.AND)) continue;
			return voxelShape2;
		}
		return this.endOfData();
	}
}

