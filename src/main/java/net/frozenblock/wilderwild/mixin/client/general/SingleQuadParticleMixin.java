/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.general;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SuspendedParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SingleQuadParticle.class)
public abstract class SingleQuadParticleMixin extends Particle {

	protected SingleQuadParticleMixin(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
	}

	@Inject(method = "getQuadSize", at = @At("RETURN"), cancellable = true)
	public void wilderWild$getQuadSize(float partialTicks, CallbackInfoReturnable<Float> info) {
		if (SingleQuadParticle.class.cast(this) instanceof SuspendedParticle suspendedParticle) {
			info.setReturnValue(info.getReturnValue() * ((WilderDripSuspendedParticleInterface)suspendedParticle).wilderWild$getScale(partialTicks));
		}
	}

	@Inject(method = "render", at = @At("RETURN"), cancellable = true)
	public void wilderWild$render(VertexConsumer buffer, Camera renderInfo, float partialTicks, CallbackInfo info) {
		if (SingleQuadParticle.class.cast(this) instanceof ExplodeParticle explodeParticle) {
			this.alpha = 1F - ((this.age + partialTicks) / this.lifetime);
		}
	}

}
