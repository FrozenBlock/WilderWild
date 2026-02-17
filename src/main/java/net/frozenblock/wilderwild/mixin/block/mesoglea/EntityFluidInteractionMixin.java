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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFluidInteraction.class)
public class EntityFluidInteractionMixin {

	@Inject(method = "update", at = @At("HEAD"))
	public void wilderWild$setupMesogleaFluidDetection(
		Entity entity, boolean ignoreCurrent, CallbackInfo info,
		@Share("wilderWild$closestPosDistance") LocalDoubleRef closestPosDistanceRef
	) {
		closestPosDistanceRef.set(Double.MAX_VALUE);
		if (!(entity instanceof InMesogleaInterface inMesogleaInterface)) return;
		inMesogleaInterface.wilderWild$setMesogleaReplacementParticlesFromBlock(null);
		inMesogleaInterface.wilderWild$setInMesoglea(false);
	}

	@WrapOperation(
		method = "update",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/BlockGetter;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
		)
	)
	public FluidState wilderWild$saveBlockState(
		BlockGetter instance, BlockPos pos, Operation<FluidState> original,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef
	) {
		final BlockState state = instance.getBlockState(pos);
		blockStateRef.set(state);
		return state.getFluidState();
	}

	@ModifyExpressionValue(
		method = "update",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(DD)D"
		)
	)
	public double wilderWild$setReplacementParticlesIfMesoglea(
		double original,
		Entity entity,
		@Local(name = "mutablePos") BlockPos.MutableBlockPos mutablePos,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef,
		@Share("wilderWild$closestPosDistance") LocalDoubleRef closestPosDistanceRef
	) {
		if (!(blockStateRef.get().getBlock() instanceof MesogleaBlock mesoglea)) return original;
		if (!(entity instanceof InMesogleaInterface inMesogleaInterface)) return original;

		inMesogleaInterface.wilderWild$setTouchingMesoglea(true);
		final double distance = entity.distanceToSqr(Vec3.atCenterOf(mutablePos));
		if (distance >= closestPosDistanceRef.get()) return original;

		closestPosDistanceRef.set(distance);
		inMesogleaInterface.wilderWild$setMesogleaReplacementParticlesFromBlock(mesoglea);

		return original;
	}

	@Inject(
		method = "update",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/entity/EntityFluidInteraction$Tracker;eyesInside:Z",
			opcode = Opcodes.PUTFIELD
		)
	)
	public void wilderwild$updateEyeInMesoglea(
		Entity entity, boolean ignoreCurrent, CallbackInfo info,
		@Share("wilderWild$blockState") LocalRef<BlockState> blockStateRef
	) {
		if (!(entity instanceof InMesogleaInterface inMesogleaInterface)) return;
		inMesogleaInterface.wilderWild$setInMesoglea(blockStateRef.get().getBlock() instanceof MesogleaBlock);
	}
}
