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

package net.frozenblock.wilderwild.mixin.client.wind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.impl.WilderDripSuspendedParticleInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Particle.class)
public abstract class ParticleMixin {

	@Shadow
	@Final
	protected ClientLevel level;
	@Shadow
	public double xd;
	@Shadow
	public double yd;
	@Shadow
	public double zd;
	@Shadow
	public double x;
	@Shadow
	public double y;
	@Shadow
	public double z;

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(CallbackInfo info) {
		if (Particle.class.cast(this) instanceof WilderDripSuspendedParticleInterface dripSuspendedParticleInterface) {
			if (dripSuspendedParticleInterface.wilderWild$usesWind()) {
				BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
				FluidState fluidState = this.level.getBlockState(blockPos).getFluidState();
				if (!fluidState.isEmpty() && (fluidState.getHeight(this.level, blockPos) + blockPos.getY()) >= this.y) {
					return;
				}
				Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1.5D, 7D, 5D)
					.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
				this.xd += wind.x * 0.001D;
				this.yd += wind.y * 0.00005D;
				this.zd += wind.z * 0.001D;
			}
		}
	}

}
