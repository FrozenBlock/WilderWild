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

package net.frozenblock.wilderwild.mixin.block.leaves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import java.util.Optional;
import net.frozenblock.wilderwild.block.api.FallingLeafRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

	@ModifyExpressionValue(
		method = "isRandomlyTicking",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;",
			ordinal = 0
		)
	)
	public Comparable<?> wilderWild$isRandomlyTicking(Comparable<?> original) {
		if (original instanceof Integer) {
			return 7;
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "decaying",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;",
			ordinal = 1
		)
	)
	public Comparable<?> wilderWild$decaying(Comparable<?> original) {
		if (original instanceof Integer integer) {
			return Mth.clamp(integer, 1, 7);
		}
		return original;
	}

	@Inject(method = "randomTick", at = @At("HEAD"))
	public void wilderWild$potentiallySpawnLeaves(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (state.getValue(LeavesBlock.DISTANCE) < 7 && !state.getValue(LeavesBlock.PERSISTENT)) {
			Optional<FallingLeafRegistry.FallingLeafData> optionalFallingLeafData = FallingLeafRegistry.getFallingLeafData(state.getBlock());
			if (optionalFallingLeafData.isPresent()) {
				FallingLeafRegistry.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
				BlockPos belowPos = pos.below();
				BlockState belowState = world.getBlockState(belowPos);
				if (!Block.isFaceFull(belowState.getCollisionShape(world, belowPos), Direction.UP)) {
					if (random.nextFloat() <= fallingLeafData.litterChance()) {
						BlockHitResult hitResult = world.clip(
							new ClipContext(
								Vec3.atBottomCenterOf(pos),
								Vec3.atCenterOf(pos.below(10)),
								ClipContext.Block.COLLIDER,
								ClipContext.Fluid.ANY,
								CollisionContext.empty()
							)
						);
						if (hitResult.getType() != HitResult.Type.MISS) {
							BlockPos hitPos = hitResult.getBlockPos();
							BlockPos placePos = hitPos.above();
							if (!placePos.equals(pos)) {
								BlockState stateToReplace = world.getBlockState(placePos);
								Block leafLitter = fallingLeafData.leafLitterBlock();
								if (wilderWild$isSafePosToPlaceLitter(world, placePos, stateToReplace, leafLitter)) {
									world.setBlockAndUpdate(placePos, leafLitter.defaultBlockState());
								}
							}
						}
					}
				}
			}
		}
	}

	@Unique
	private static boolean wilderWild$isSafePosToPlaceLitter(@NotNull Level world, BlockPos pos, @NotNull BlockState stateToReplace, Block leafLitterBlock) {
		if ((stateToReplace.isAir() || stateToReplace.canBeReplaced()) && stateToReplace.getFluidState().isEmpty()) {
			return leafLitterBlock.canSurvive(leafLitterBlock.defaultBlockState(), world, pos);
		}
		return false;
	}

	@Inject(method = "animateTick", at = @At("HEAD"))
	public void wilderWild$fallingLeafParticles(BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo info) {
		Optional<FallingLeafRegistry.FallingLeafData> optionalFallingLeafData = FallingLeafRegistry.getFallingLeafData(state.getBlock());
		if (optionalFallingLeafData.isPresent()) {
			FallingLeafRegistry.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
			ParticleOptions leafParticle = fallingLeafData.particle();
			if (random.nextFloat() <= FallingLeafRegistry.getLeafParticleData(leafParticle).particleChance()) {
				BlockPos blockPos = pos.below();
				BlockState blockState = world.getBlockState(blockPos);
				if (!Block.isFaceFull(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
					ParticleUtils.spawnParticleBelow(world, pos, random, leafParticle);
				}
			}
		}
	}

}
