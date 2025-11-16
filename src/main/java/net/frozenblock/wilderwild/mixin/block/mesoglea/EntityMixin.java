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
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements InMesogleaInterface {

	@Shadow
	public abstract double distanceToSqr(Vec3 vec3);

	@Shadow
	public abstract Level level();

	@Shadow
	protected boolean wasTouchingWater;

	@Shadow
	public abstract boolean isUnderWater();

	@Unique
	private boolean wilderWild$clipInMesoglea;

	@Unique
	private boolean wilderWild$wasInMesoglea;

	@Unique
	private boolean wilderWild$wasTouchingMesoglea;

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
		Level instance, BlockPos pos, Operation<FluidState> original,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef
	) {
		BlockState state = instance.getBlockState(pos);
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
		@Local BlockPos.MutableBlockPos mutable,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef,
		@Share("wilderWild$closestPosDistance") LocalDoubleRef closestPosDistanceRef
	) {
		if (blockStateRef.get().getBlock() instanceof MesogleaBlock mesogleaBlock) {
			this.wilderWild$setTouchingMesoglea(true);
			final double distance = this.distanceToSqr(Vec3.atCenterOf(mutable));
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
		Level instance, ParticleOptions options, double d, double e, double f, double g, double h, double i, Operation<Void> original
	) {
		if (this.wilderWild$replacementBubbleParticle != null) options = this.wilderWild$replacementBubbleParticle;
		original.call(instance, options, d, e, f, g, h, i);
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
		Level instance, ParticleOptions options, double d, double e, double f, double g, double h, double i, Operation<Void> original
	) {
		if (this.wilderWild$replacementSplashParticle != null) options = this.wilderWild$replacementSplashParticle;
		original.call(instance, options, d, e, f, g, h, i);
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

	@Inject(method = "updateFluidOnEyes", at = @At("HEAD"))
	public void wilderwild$updateIsInMesolgeaA(CallbackInfo info) {
		this.wilderWild$setInMesoglea(false);
	}

	@Inject(
		method = "updateFluidOnEyes",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/material/FluidState;getTags()Ljava/util/stream/Stream;"
		)
	)
	public void wilderwild$updateIsInMesolgeaB(
		CallbackInfo info,
		@Local BlockPos eyePos
	) {
		this.wilderWild$setInMesoglea(this.level().getBlockState(eyePos).getBlock() instanceof MesogleaBlock);
	}

	@Unique
	@Override
	public void wilderWild$setInMesoglea(boolean inMesoglea) {
		this.wilderWild$wasInMesoglea = inMesoglea;
	}

	@Unique
	@Override
	public boolean wilderWild$wasInMesoglea() {
		return this.wilderWild$wasInMesoglea;
	}

	@Inject(method = "getSwimSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getSwimSound(CallbackInfoReturnable<SoundEvent> info) {
		if (this.wilderWild$isTouchingMesogleaOrUnderWaterAndMesoglea()) info.setReturnValue(WWSounds.ENTITY_GENERIC_SWIM_MESOGLEA);
	}

	@Inject(method = "updateInWaterStateAndDoWaterCurrentPushing", at = @At("TAIL"))
	public void wilderWild$setNotTouchingMesoglea(CallbackInfo info) {
		if (!this.wasTouchingWater) this.wilderWild$wasTouchingMesoglea = false;
	}

	@Unique
	@Override
	public void wilderWild$setTouchingMesoglea(boolean inMesoglea) {
		this.wilderWild$wasTouchingMesoglea = inMesoglea;
	}

	@Unique
	@Override
	public boolean wilderWild$wasTouchingMesoglea() {
		return this.wilderWild$wasTouchingMesoglea;
	}

	@Unique
	@Override
	public boolean wilderWild$isTouchingMesogleaOrUnderWaterAndMesoglea() {
		return this.isUnderWater() ? this.wilderWild$wasInMesoglea() : this.wilderWild$wasTouchingMesoglea();
	}

	@Inject(method = "getSwimSplashSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getSwimSplashSound(CallbackInfoReturnable<SoundEvent> info) {
		if (this.wilderWild$wasTouchingMesoglea()) info.setReturnValue(WWSounds.ENTITY_GENERIC_SPLASH_MESOGLEA);
	}

	@Inject(method = "getSwimHighSpeedSplashSound", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getSwimHighSpeedSplashSound(CallbackInfoReturnable<SoundEvent> info) {
		if (this.wilderWild$wasTouchingMesoglea()) info.setReturnValue(WWSounds.ENTITY_GENERIC_SPLASH_MESOGLEA);
	}
}
