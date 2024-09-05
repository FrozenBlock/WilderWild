/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import com.google.common.collect.Iterables;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.Set;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.worldgen.impl.sapling.impl.TreeFeatureLeavesUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TreeFeature.class, priority = 69420)
public abstract class TreeFeatureMixin implements TreeFeatureLeavesUpdate {

	@Shadow
	private static DiscreteVoxelShape updateLeaves(LevelAccessor level, BoundingBox box, Set<BlockPos> rootPositions, Set<BlockPos> trunkPositions, Set<BlockPos> foliagePositions) {
		throw new AssertionError("Mixin injection failed - Wilder Wild TreeFeatureMixin.");
	}

	@Inject(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/structure/BoundingBox;encapsulatingPositions(Ljava/lang/Iterable;)Ljava/util/Optional;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	private void wilderWild$place(
		FeaturePlaceContext<TreeConfiguration> context, CallbackInfoReturnable<Boolean> info,
		@Local WorldGenLevel worldGenLevel,
		@Local(ordinal = 0) Set<BlockPos> set,
		@Local(ordinal = 1) Set<BlockPos> set2,
		@Local(ordinal = 2) Set<BlockPos> set3,
		@Local(ordinal = 3) Set<BlockPos> set4
	) {
		info.setReturnValue(this.wilderWild$encapsulatePositionsAndUpdateLeaves(worldGenLevel, set, set2, set3, set4));
	}

	@ModifyVariable(method = "method_49238", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private static BlockState wilderWild$setTermiteEdibleA(BlockState state) {
		if (state.hasProperty(WWBlockStateProperties.TERMITE_EDIBLE)) {
			return state.setValue(WWBlockStateProperties.TERMITE_EDIBLE, true);
		}
		return state;
	}

	@ModifyVariable(method = "method_35364", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private static BlockState wilderWild$setTermiteEdibleB(BlockState state) {
		if (state.hasProperty(WWBlockStateProperties.TERMITE_EDIBLE)) {
			return state.setValue(WWBlockStateProperties.TERMITE_EDIBLE, true);
		}
		return state;
	}

	@ModifyVariable(method = "method_43162", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private static BlockState wilderWild$setTermiteEdibleC(BlockState state) {
		if (state.hasProperty(WWBlockStateProperties.TERMITE_EDIBLE)) {
			return state.setValue(WWBlockStateProperties.TERMITE_EDIBLE, true);
		}
		return state;
	}

	@Unique
	public boolean wilderWild$encapsulatePositionsAndUpdateLeaves(LevelAccessor worldGenLevel, Set<BlockPos> set, Set<BlockPos> set2, Set<BlockPos> set3, Set<BlockPos> set4) {
		return BoundingBox.encapsulatingPositions(Iterables.concat(set, set2, set3, set4)).map((boundingBox) -> {
			DiscreteVoxelShape discreteVoxelShape = this.wilderWild$updateLeaves(worldGenLevel, boundingBox, set2, set4, set);
			StructureTemplate.updateShapeAtEdge(worldGenLevel, 3, discreteVoxelShape, boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
			return true;
		}).orElse(false);
	}

	@Unique
	@Override
	public DiscreteVoxelShape wilderWild$updateLeaves(LevelAccessor level, @NotNull BoundingBox box, Set<BlockPos> rootPositions, Set<BlockPos> trunkPositions, Set<BlockPos> foliagePositions) {
		return updateLeaves(level, box, rootPositions, trunkPositions, foliagePositions);
	}
}
