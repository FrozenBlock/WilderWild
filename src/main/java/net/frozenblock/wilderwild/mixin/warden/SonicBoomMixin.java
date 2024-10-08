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

package net.frozenblock.wilderwild.mixin.warden;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.function.Consumer;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.entity.impl.WilderSonicBoom;
import net.frozenblock.wilderwild.entity.render.animation.WilderWarden;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SonicBoom.class, priority = 1001)
public class SonicBoomMixin implements WilderSonicBoom {

	@Unique
	private static SonicBoom wilderWild$currentBoom;
	@Unique
	private boolean wilderWild$particlesEnded = false;

	@ModifyVariable(method = "method_43265",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
		),
		ordinal = 1
	)
	private static int wilderWild$modifyInt(int original, @Local(ordinal = 0) int particleEnd) {
		return ((WilderSonicBoom) wilderWild$currentBoom).wilderWild$particlesEnded() ? particleEnd : original;
	}

	@Inject(
		method = "method_43265",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I",
			shift = At.Shift.BEFORE
		)
	)
	private static void wilderWild$stopParticles(Warden warden, ServerLevel serverLevel, LivingEntity livingEntity, CallbackInfo info,
		@Local(ordinal = 0) Vec3 vec3, @Local(ordinal = 3) Vec3 vec34
	) {
		BlockPos hitPos = wilderWild$isOccluded(serverLevel, vec3, vec34);
		if (hitPos != null) {
			((WilderSonicBoom) wilderWild$currentBoom).wilderWild$endParticles();
		}
	}

	@Inject(
		method = "method_43265",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"
		),
		cancellable = true
	)
	private static void wilderWild$tick(
		Warden warden, ServerLevel level, LivingEntity livingEntity, CallbackInfo info,
		@Local(ordinal = 0) Vec3 vec3, @Local(ordinal = 1) Vec3 vec32, @Local(ordinal = 2) Vec3 vec33
	) {
		for (int i = 1; i < Mth.floor(vec32.length()) + 7; ++i) {
			Vec3 vec34 = vec3.add(vec33.scale(i));
			BlockPos hitPos = wilderWild$isOccluded(level, vec3, vec34);
			if (hitPos != null) {
				i = Mth.floor(vec32.length()) + 10;
				info.cancel();
				BlockState hitState = level.getBlockState(hitPos);
				if (hitState.getBlock() instanceof EchoGlassBlock) {
					EchoGlassBlock.damage(level, hitPos, hitState, false);
				}
			}
		}
	}

	@ModifyExpressionValue(
		method = "method_43265",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/sounds/SoundEvents;WARDEN_SONIC_BOOM:Lnet/minecraft/sounds/SoundEvent;",
			opcode = Opcodes.GETSTATIC
		)
	)
	private static SoundEvent wilderWild$modifySound(SoundEvent original, Warden warden) {
		return ((WilderWarden) warden).wilderWild$isStella() ? WWSounds.ENTITY_WARDEN_BRAP : original;
	}

	@Unique
	@Nullable
	private static BlockPos wilderWild$isOccluded(@NotNull Level level, @NotNull Vec3 start, @NotNull Vec3 end) {
		Vec3 vec3d = new Vec3((double) Mth.floor(start.x) + 0.5D, (double) Mth.floor(start.y) + 0.5D, (double) Mth.floor(start.z) + 0.5D);
		Vec3 vec3d2 = new Vec3((double) Mth.floor(end.x) + 0.5D, (double) Mth.floor(end.y) + 0.5D, (double) Mth.floor(end.z) + 0.5D);
		BlockPos hitPos = null;
		boolean blocked = true;
		for (Direction direction : Direction.values()) {
			Vec3 vec3d3 = vec3d.relative(direction, 9.999999747378752E-6D);
			BlockHitResult hit = level.isBlockInLine(new ClipBlockStateContext(vec3d3, vec3d2, (state) -> state.is(WWBlocks.ECHO_GLASS)));
			if (hit.getType() != HitResult.Type.BLOCK) {
				blocked = false;
			} else {
				hitPos = hit.getBlockPos();
			}
		}
		if (blocked) {
			return hitPos;
		} else {
			return null;
		}
	}

	@ModifyArg(
		method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V",
			ordinal = 1
		)
	)
	private Consumer<? super LivingEntity> wilderWild$setCurrent(Consumer<? super LivingEntity> original) {
		return target -> {
			wilderWild$currentBoom = SonicBoom.class.cast(this);
			original.accept(target);
		};
	}

	@Inject(method = "stop(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V", at = @At("TAIL"))
	private void wilderWild$reset(ServerLevel level, Warden entity, long gameTime, CallbackInfo info) {
		this.wilderWild$particlesEnded = false;
	}

	@Unique
	@Override
	public boolean wilderWild$particlesEnded() {
		return this.wilderWild$particlesEnded;
	}

	@Unique
	@Override
	public void wilderWild$endParticles() {
		this.wilderWild$particlesEnded = true;
	}

}
