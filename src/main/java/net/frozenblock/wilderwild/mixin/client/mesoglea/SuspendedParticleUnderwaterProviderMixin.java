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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(SuspendedParticle.UnderwaterProvider.class)
public class SuspendedParticleUnderwaterProviderMixin {

	@WrapOperation(
		method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDDLnet/minecraft/util/RandomSource;)Lnet/minecraft/client/particle/Particle;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/particle/SuspendedParticle;setColor(FFF)V"
		)
	)
	public void wilderWild$changeColorToMesoglea(
		SuspendedParticle instance, float r, float g, float b, Operation<Void> original,
		SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z
	) {
		final BlockState state = clientLevel.getBlockState(BlockPos.containing(x, y, z));
		if (state.getBlock() instanceof MesogleaBlock mesogleaBlock) {
			final int mesogleaColor = mesogleaBlock.getWaterFogColorOverride().rgba();
			r = ARGB.red(mesogleaColor) / 255F;
			g = ARGB.green(mesogleaColor) / 255F;
			b  = ARGB.blue(mesogleaColor) / 255F;
		}

		original.call(instance, r, g, b);
	}

}
