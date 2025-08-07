/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.block.leaves;

import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeafLitterBlock.class)
public abstract class LeafLitterBlockMixin extends VegetationBlock {

	protected LeafLitterBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public void entityInside(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Entity entity,
		InsideBlockEffectApplier insideBlockEffectApplier
	) {
		AABB shape = this.getShape(state, level, pos, CollisionContext.of(entity)).bounds().move(pos);
		if (shape.intersects(entity.getBoundingBox())) {
			Vec3 movement = entity.getDeltaMovement();
			double horizontalDistance = movement.horizontalDistance();
			movement = new Vec3(movement.x * 0.5D, horizontalDistance * 0.1D, movement.z * 0.5D);

			if (level.random.nextFloat() > (horizontalDistance * 0.5D)) return;
			FallingLeafUtil.spawnLitterParticles(level, pos, state, movement);
		}
	}

}
