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

package net.frozenblock.wilderwild.mixin.block.mesoglea;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.entity.impl.InMesogleaInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockGetter.class)
public interface BlockGetterMixin {

	@Shadow
	BlockState getBlockState(BlockPos var1);

	@Inject(method = "clip", at = @At("HEAD"))
	default void wilderWild$setClipInMesoglea(ClipContext context, CallbackInfoReturnable<BlockHitResult> info) {
		if (context.collisionContext instanceof EntityCollisionContext entityCollisionContext) {
			Entity entity = entityCollisionContext.getEntity();
			if (entity instanceof InMesogleaInterface inMesogleaInterface) {
				BlockState eyeState = getBlockState(BlockPos.containing(entity.getEyePosition()));
				if (eyeState != null) {
					inMesogleaInterface.wilderWild$setClipInMesoglea(
						eyeState.getBlock() instanceof MesogleaBlock
					);
				}
			}
		}
	}

	@WrapOperation(
		method = "method_17743",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/BlockGetter;clipWithInteractionOverride(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/phys/BlockHitResult;"
		)
	)
	default BlockHitResult wilderWild$mesogleaClip(
		BlockGetter instance, Vec3 startVec, Vec3 endVec, BlockPos pos, VoxelShape shape, BlockState state, Operation<BlockHitResult> operation,
		ClipContext context
	) {
		if (context.collisionContext instanceof EntityCollisionContext entityCollisionContext) {
			if (
				entityCollisionContext.getEntity() instanceof InMesogleaInterface inMesogleaInterface
					&& inMesogleaInterface.wilderWild$wasClipInMesoglea()
					&& state.getBlock() instanceof MesogleaBlock
			) {
				shape = Shapes.empty();
			}
		}
		return operation.call(instance, startVec, endVec, pos, shape, state);
	}

}
