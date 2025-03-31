/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.block.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.entity.impl.InMesogleaInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements InMesogleaInterface {

	@Shadow
	public abstract double distanceToSqr(Vec3 vec3);

	@Unique
	private boolean wilderWild$clipInMesoglea;

	@Unique
	private ParticleOptions wilderWild$replacementSplashParticle;
	@Unique
	private ParticleOptions wilderWild$replacementBubbleParticle;

	@WrapOperation(
		method = "getBlockSpeedFactor",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 1
		)
	)
	public boolean wilderWild$isBubbleColumnOrMesogleaColumn(BlockState state, Block block, Operation<Boolean> operation) {
		return operation.call(state, block) || MesogleaBlock.hasBubbleColumn(state);
	}

	@WrapOperation(
		method = "isInBubbleColumn",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"
		)
	)
	public boolean wilderWild$isInBubbleColumnOrMesogleaColumn(BlockState state, Block block, Operation<Boolean> operation) {
		return operation.call(state, block) || MesogleaBlock.hasBubbleColumn(state);
	}

	@Inject(method = "updateFluidHeightAndDoFluidPushing", at = @At("HEAD"))
	public void wilderWild$setupMesogleaFluidDetection(
		TagKey<Fluid> tagKey, double d, CallbackInfoReturnable<Boolean> info,
		@Share("wilderWild$closestPosDistance") LocalDoubleRef closestPosDistanceRef
	) {
		closestPosDistanceRef.set(999D);
		this.wilderWild$replacementSplashParticle = null;
		this.wilderWild$replacementBubbleParticle = null;
	}

	@WrapOperation(
		method = "updateFluidHeightAndDoFluidPushing",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
		)
	)
	public FluidState wilderWild$saveBlockState(
		Level instance, BlockPos blockPos, Operation<FluidState> original,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef
	) {
		BlockState state = instance.getBlockState(blockPos);
		blockStateRef.set(state);
		return state.getFluidState();
	}

	@ModifyExpressionValue(
		method = "updateFluidHeightAndDoFluidPushing",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(DD)D"
		)
	)
	public double wilderWild$setReplacementParticlesIfMesoglea(
		double original,
		@Local BlockPos.MutableBlockPos blockPos,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef,
		@Share("wilderWild$closestPosDistance") LocalDoubleRef closestPosDistanceRef
	) {
		if (blockStateRef.get().getBlock() instanceof MesogleaBlock mesogleaBlock) {
			double distance = this.distanceToSqr(Vec3.atCenterOf(blockPos));
			if (distance < closestPosDistanceRef.get()) {
				closestPosDistanceRef.set(distance);
				this.wilderWild$replacementSplashParticle = mesogleaBlock.getSplashParticle();
				this.wilderWild$replacementBubbleParticle = mesogleaBlock.getBubbleParticle();
			}
		}

		return original;
	}

	@WrapOperation(
		method = "doWaterSplashEffect",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V",
			ordinal = 0
		)
	)
	public void wilderWild$replaceBubbleParticles(
		Level instance, ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i, Operation<Void> original
	) {
		if (this.wilderWild$replacementBubbleParticle != null) {
			particleOptions = this.wilderWild$replacementBubbleParticle;
		}
		original.call(instance, particleOptions, d, e, f, g, h, i);
	}

	@WrapOperation(
		method = "doWaterSplashEffect",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V",
			ordinal = 1
		)
	)
	public void wilderWild$replaceSplashParticles(
		Level instance, ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i, Operation<Void> original
	) {
		if (this.wilderWild$replacementSplashParticle != null) {
			particleOptions = this.wilderWild$replacementSplashParticle;
		}
		original.call(instance, particleOptions, d, e, f, g, h, i);
	}

	@Unique
	@Override
	public void wilderWild$setClipInMesoglea(boolean clipInMesoglea) {
		this.wilderWild$clipInMesoglea = clipInMesoglea;
	}

	@Unique
	@Override
	public boolean wilderWild$wasClipInMesoglea() {
		return this.wilderWild$clipInMesoglea;
	}
}
