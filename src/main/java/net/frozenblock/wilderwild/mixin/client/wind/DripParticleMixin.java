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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.client.wind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.impl.WilderDripSuspendedParticleInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(DripParticle.class)
public abstract class DripParticleMixin extends TextureSheetParticle implements WilderDripSuspendedParticleInterface {

	@Unique
	private boolean wilderWild$usesWind = false;

	protected DripParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Inject(method = "createSporeBlossomFallParticle", at = @At("RETURN"))
	private static void wilderWild$createBlossomFallParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfoReturnable<Particle> info) {
		if (info.getReturnValue() instanceof WilderDripSuspendedParticleInterface dripParticle) {
			dripParticle.wilderWild$setUsesWind(true);
		}
	}

	@Inject(method = "createNectarFallParticle", at = @At("RETURN"))
	private static void wilderWild$createNectarFallParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfoReturnable<Particle> info) {
		if (info.getReturnValue() instanceof WilderDripSuspendedParticleInterface dripParticle) {
			dripParticle.wilderWild$setUsesWind(true);
			info.getReturnValue().setColor(250F / 255F, 171F / 255F, 28F / 255F);
		}
	}

	@Unique
	@Override
	public void wilderWild$setUsesWind(boolean bl) {
		this.wilderWild$usesWind = bl;
	}

	@Unique
	@Override
	public boolean wilderWild$usesWind() {
		return this.wilderWild$usesWind;
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(CallbackInfo info) {
		if (this.wilderWild$usesWind()) {
			Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1.5D, 7D, 5D)
				.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
			this.xd += wind.x * 0.001D;
			this.yd += wind.y * 0.00005D;
			this.zd += wind.z * 0.001D;
		}
	}

}
