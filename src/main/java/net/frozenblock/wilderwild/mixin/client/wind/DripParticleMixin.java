package net.frozenblock.wilderwild.mixin.client.wind;

import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DripParticle.class)
public abstract class DripParticleMixin extends TextureSheetParticle implements WilderDripSuspendedParticleInterface {

	@Unique
	private boolean wilderWild$usesWind;

	protected DripParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
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

	@Inject(method = "tick", at = @At("TAIL"))
	public void wilderWild$tick(CallbackInfo info) {
		if (this.wilderWild$usesWind()) {
			Vec3 wind = ClientWindManager.getWindMovement(this.level, new BlockPos(this.x, this.y, this.z), 1.5, 1);
			this.xd += wind.x * 0.001;
			this.yd += wind.y * 0.00005;
			this.zd += wind.z * 0.001;
		}
	}

}
