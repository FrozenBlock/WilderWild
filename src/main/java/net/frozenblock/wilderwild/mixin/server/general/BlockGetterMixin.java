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

package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockGetter.class)
public interface BlockGetterMixin {

	@Shadow
	BlockState getBlockState(BlockPos var1);

	@Shadow
	FluidState getFluidState(BlockPos var1);

	@Shadow
	@Nullable
	default BlockHitResult clipWithInteractionOverride(Vec3 startVec, Vec3 endVec, BlockPos pos, VoxelShape shape, BlockState state) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BlockGetterMixin.");
	}

	@Inject(method = "clip", at = @At(value = "HEAD"), cancellable = true)
	default void wilderWild$clip(ClipContext context2, CallbackInfoReturnable<BlockHitResult> info) {
		if (context2.collisionContext instanceof EntityCollisionContext entityCollisionContext) {
			if (entityCollisionContext.getEntity() instanceof Player player) {
				BlockState headState = getBlockState(BlockPos.containing(player.getEyePosition()));
				if (headState.getBlock() instanceof MesogleaBlock && headState.getValue(BlockStateProperties.WATERLOGGED)) {
					BlockGetter blockGetter = (BlockGetter) this;
					info.setReturnValue(
						BlockGetter.traverseBlocks(
							context2.getFrom(), context2.getTo(), context2, (context, pos) -> {
								BlockState blockState = this.getBlockState(pos);
								FluidState fluidState = this.getFluidState(pos);
								Vec3 vec3 = context.getFrom();
								Vec3 vec32 = context.getTo();
								VoxelShape voxelShape = (blockState.getBlock() instanceof MesogleaBlock && blockState.getValue(BlockStateProperties.WATERLOGGED)) ? Shapes.empty() : context.getBlockShape(blockState, blockGetter, pos);
								BlockHitResult blockHitResult = this.clipWithInteractionOverride(vec3, vec32, pos, voxelShape, blockState);
								VoxelShape voxelShape2 = context.getFluidShape(fluidState, blockGetter, pos);
								BlockHitResult blockHitResult2 = voxelShape2.clip(vec3, vec32, pos);
								double d = blockHitResult == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(blockHitResult.getLocation());
								double e = blockHitResult2 == null ? Double.MAX_VALUE : context.getFrom().distanceToSqr(blockHitResult2.getLocation());
								return d <= e ? blockHitResult : blockHitResult2;
							},
							context -> {
								Vec3 vec3 = context.getFrom().subtract(context.getTo());
								return BlockHitResult.miss(context.getTo(), Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(context.getTo()));
							}
						)
					);
				}
			}
		}
	}

}
