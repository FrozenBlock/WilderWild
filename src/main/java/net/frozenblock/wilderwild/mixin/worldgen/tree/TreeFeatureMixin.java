/*
 * Copyright 2023 FrozenBlock
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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.misc.interfaces.TreeFeatureLeavesUpdate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Set;

@Mixin(value = TreeFeature.class, priority = 69420)
public abstract class TreeFeatureMixin implements TreeFeatureLeavesUpdate {

	@Shadow
	private static DiscreteVoxelShape updateLeaves(LevelAccessor level, BoundingBox box, Set<BlockPos> rootPositions, Set<BlockPos> trunkPositions, Set<BlockPos> foliagePositions) {
		return null;
	}

	@Unique
	private static TreeFeature currentFeature = null;

	@Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/BoundingBox;encapsulatingPositions(Ljava/lang/Iterable;)Ljava/util/Optional;"))
	private void headPlace(FeaturePlaceContext<TreeConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
		currentFeature = (TreeFeature) (Object) this;
	}

	@WrapOperation(
		method = "method_35363",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/feature/TreeFeature;updateLeaves(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)Lnet/minecraft/world/phys/shapes/DiscreteVoxelShape;"
		)
	)
	private static DiscreteVoxelShape updateLeaves(LevelAccessor level, BoundingBox box, Set<BlockPos> rootPositions, Set<BlockPos> trunkPositions, Set<BlockPos> foliagePositions, Operation<DiscreteVoxelShape> original) {
		return Util.make(() -> {
			DiscreteVoxelShape shape = ((TreeFeatureLeavesUpdate) currentFeature).wilderWild$updateLeaves(level, box, rootPositions, trunkPositions, foliagePositions);
			currentFeature = null;
			return shape;
		});
	}

	@Override
	public DiscreteVoxelShape wilderWild$updateLeaves(LevelAccessor level, @NotNull BoundingBox box, Set<BlockPos> rootPositions, Set<BlockPos> trunkPositions, Set<BlockPos> foliagePositions) {
		return updateLeaves(level, box, rootPositions, trunkPositions, foliagePositions);
	}
}
