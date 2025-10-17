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

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Environment(EnvType.CLIENT)
@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level {

	protected ClientLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, boolean bl, boolean bl2, long l, int i) {
		super(writableLevelData, resourceKey, registryAccess, holder, bl, bl2, l, i);
	}

	@ModifyExpressionValue(
		method = "doAnimateTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/material/FluidState;getDripParticle()Lnet/minecraft/core/particles/ParticleOptions;"
		)
	)
	public ParticleOptions wilderWild$useMesogleaDripParticle(
		ParticleOptions original,
		@Local BlockState state,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (state.getBlock() instanceof MesogleaBlock mesoglea) {
			isMesoglea.set(true);
			return mesoglea.getDripParticle();
		}
		return original;
	}

	@WrapOperation(
		method = "doAnimateTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/level/material/FluidState;getDripParticle()Lnet/minecraft/core/particles/ParticleOptions;"
			)
		)
	)
	public int wilderWild$lessDrippingIfMesoglea(
		RandomSource instance, int i, Operation<Integer> original,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		return original.call(instance, isMesoglea.get() ? MesogleaBlock.DRIP_PARTICLE_CHANCE : i);
	}

	@WrapOperation(
		method = "doAddParticle(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/particle/ParticleEngine;createParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)Lnet/minecraft/client/particle/Particle;"
		)
	)
	private Particle wilderWild$replaceWithMesogleaParticles(
		ParticleEngine instance, ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i, Operation<Particle> original
	) {
		if (particleOptions.equals(ParticleTypes.BUBBLE)) {
			BlockState state = this.getBlockState(BlockPos.containing(d, e, f));
			if (state.getBlock() instanceof MesogleaBlock mesogleaBlock) particleOptions = mesogleaBlock.getBubbleParticle();
		} else if (particleOptions.equals(ParticleTypes.SPLASH)) {
			BlockState state = this.getBlockState(BlockPos.containing(d, e, f));
			if (state.getBlock() instanceof MesogleaBlock mesogleaBlock) particleOptions = mesogleaBlock.getSplashParticle();
		}

		return original.call(instance, particleOptions, d, e, f, g, h, i);
	}

}
