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

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Shadow
	private @Nullable ClientLevel level;

	@WrapOperation(
		method = "addParticleInternal(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/particle/ParticleEngine;createParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)Lnet/minecraft/client/particle/Particle;"
		)
	)
	private Particle wilderWild$replaceWithMesogleaParticles(
		ParticleEngine instance, ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i, Operation<Particle> original
	) {
		if (this.level != null) {
			if (particleOptions.equals(ParticleTypes.BUBBLE)) {
				BlockState state = this.level.getBlockState(BlockPos.containing(d, e, f));
				if (state.getBlock() instanceof MesogleaBlock mesogleaBlock) {
					particleOptions = mesogleaBlock.getBubbleParticle();
				}
			} else if (particleOptions.equals(ParticleTypes.SPLASH)) {
				BlockState state = this.level.getBlockState(BlockPos.containing(d, e, f));
				if (state.getBlock() instanceof MesogleaBlock mesogleaBlock) {
					particleOptions = mesogleaBlock.getSplashParticle();
				}
			}
		}

		return original.call(instance, particleOptions, d, e, f, g, h, i);
	}

}
