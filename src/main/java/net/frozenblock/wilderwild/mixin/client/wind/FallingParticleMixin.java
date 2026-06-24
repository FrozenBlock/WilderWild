/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.wind;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.WindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.WWFallingLeavesParticle;
import net.frozenblock.wilderwild.wind.WWWindManagerExtension;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingParticle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(FallingParticle.class)
public abstract class FallingParticleMixin extends SingleQuadParticle {

	@Shadow
	private float rotSpeed;

	@Shadow
	@Final
	private float spinAcceleration;

	@Final
	@Shadow
	@Mutable
	private boolean flowAway;

	@Final
	@Shadow
	@Mutable
	private boolean swirl;

	protected FallingParticleMixin(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
		super(level, x, y, z, sprite);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void wilderWild$rotateLeafOnCreation(CallbackInfo info) {
		this.roll = this.random.nextFloat() * Mth.TWO_PI;
		this.oRoll = this.roll;
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$captureWind(
		CallbackInfo info,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		wind.set(Vec3.ZERO);
		if (!WWWindManagerExtension.shouldUseWind(this.level)) return;
		final Vec3 currentWind = WindManager.getOrCreate(this.level).getWindMovement(new Vec3(this.x, this.y, this.z), 2.5D, 7D, 5D)
			.scale(WWAmbienceAndMiscConfig.PARTICLE_WIND_MOVEMENT.get() * 0.01D);
		wind.set(currentWind);
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/particle/FallingParticle;xaFlowScale:D",
			opcode = Opcodes.GETFIELD
		)
	)
	public double wilderWild$changeFlowA(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWWindManagerExtension.shouldUseWind(this.level)) return original * wind.get().x;
		return original;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/particle/FallingParticle;zaFlowScale:D",
			opcode = Opcodes.GETFIELD
		)
	)
	public double wilderWild$changeFlowB(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWWindManagerExtension.shouldUseWind(this.level)) return original * wind.get().z;
		return original;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;cos(D)D"
		)
	)
	public double wilderWild$changeSwirlA(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWWindManagerExtension.shouldUseWind(this.level)) return original + (wind.get().x * 0.2D);
		return original;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;sin(D)D"
		)
	)
	public double wilderWild$changeSwirlB(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWWindManagerExtension.shouldUseWind(this.level)) return original + (wind.get().z * 0.2D);
		return original;
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/particle/FallingParticle;move(DDD)V"
		)
	)
	public void wilderWild$fixMoveGravity(
		FallingParticle instance, double x, double y, double z, Operation<Void> original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWWindManagerExtension.shouldUseWind(this.level)) y = (this.yd - this.gravity) + wind.get().y * 0.00001D;
		original.call(instance, x, y, z);
	}

	@ModifyConstant(
		method = "tick",
		constant = @Constant(intValue = 299)
	)
	public int wilderWild$fixMoveD(int constant) {
		if (WWWindManagerExtension.shouldUseWind(this.level)) return 10;
		return constant;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/particle/FallingParticle;onGround:Z",
			opcode = Opcodes.GETFIELD
		)
	)
	public boolean wilderWild$bounceOnFloorIfAllowed(boolean original) {
		if (!(FallingParticle.class.cast(this) instanceof WWFallingLeavesParticle wwFallingLeaves)) return original;
		if (original && wwFallingLeaves.bounceOnFloor && !(this.xd == 0D || this.zd == 0D)) {
			this.yd += (Math.abs(this.xd) + Math.abs(this.zd)) * 0.5D;
			this.xd *= 0.5D;
			this.zd *= 0.5D;
			this.stoppedByCollision = false;
			this.flowAway = false;
			this.swirl = false;
			this.rotSpeed = this.rotSpeed + this.spinAcceleration / 10F;
			if (wwFallingLeaves.isLitter) this.yd *= 0.75D;
			return false;
		}
		return original;
	}

}
