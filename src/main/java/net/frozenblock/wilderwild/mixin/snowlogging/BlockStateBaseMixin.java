/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.ArrayList;
import java.util.List;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

	@Shadow
	protected abstract BlockState asState();

	@Shadow
	public abstract Block getBlock();

	@ModifyReturnValue(
		method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getCollisionShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (RegisterProperties.isSnowlogged(blockState)) {
			return wilderWild$combineShapesWithNullSafety(original, RegisterProperties.getSnowEquivalent(blockState).getCollisionShape(level, pos, context));
		}
		return original;
	}

	@ModifyReturnValue(
		method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (RegisterProperties.isSnowlogged(blockState)) {
			return wilderWild$combineShapesWithNullSafety(original, RegisterProperties.getSnowEquivalent(blockState).getShape(level, pos, context));
		}
		return original;
	}

	@ModifyReturnValue(
		method = "getVisualShape",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getVisualShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
		BlockState blockState = this.asState();
		if (RegisterProperties.isSnowlogged(blockState)) {
			return wilderWild$combineShapesWithNullSafety(original, RegisterProperties.getSnowEquivalent(blockState).getVisualShape(level, pos, context));
		}
		return original;
	}

	@ModifyReturnValue(
		method = "getInteractionShape",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getInteractionShape(VoxelShape original, BlockGetter level, BlockPos pos) {
		BlockState blockState = this.asState();
		if (RegisterProperties.isSnowlogged(blockState)) {
			return wilderWild$combineShapesWithNullSafety(original, RegisterProperties.getSnowEquivalent(blockState).getInteractionShape(level, pos));
		}
		return original;
	}

	@ModifyReturnValue(
		method = "getBlockSupportShape",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getBlockSupportShape(VoxelShape original, BlockGetter level, BlockPos pos) {
		BlockState blockState = this.asState();
		if (RegisterProperties.isSnowlogged(blockState)) {
			return wilderWild$combineShapesWithNullSafety(original, RegisterProperties.getSnowEquivalent(blockState).getBlockSupportShape(level, pos));
		}
		return original;
	}

	@ModifyReturnValue(
		method = "getOcclusionShape",
		at = @At("RETURN")
	)
	public VoxelShape wilderWild$getOcclusionShape(VoxelShape original, BlockGetter level, BlockPos pos) {
		BlockState blockState = this.asState();
		if (RegisterProperties.isSnowlogged(blockState)) {
			return wilderWild$combineShapesWithNullSafety(original, RegisterProperties.getSnowEquivalent(blockState).getOcclusionShape(level, pos));
		}
		return original;
	}

	@ModifyReturnValue(
		method = "getDrops",
		at = @At("RETURN")
	)
	public List<ItemStack> wilderWild$getDrops(List<ItemStack> original, LootParams.Builder lootParams) {
        List<ItemStack> finalList = new ArrayList<>(original);
		BlockState state = this.asState();
		if (RegisterProperties.isSnowlogged(state)) {
			BlockState snowEquivalent = RegisterProperties.getSnowEquivalent(state);
			finalList.addAll(snowEquivalent.getDrops(lootParams));
		}
		return finalList;
	}

	@Unique
	private static VoxelShape wilderWild$combineShapesWithNullSafety(VoxelShape shape1, VoxelShape shape2) {
		if (shape1 == null) {
			shape1 = Shapes.empty();
		}
		if (shape2 == null) {
			shape2 = Shapes.empty();
		}
		return Shapes.or(shape1, shape2);
	}

}
