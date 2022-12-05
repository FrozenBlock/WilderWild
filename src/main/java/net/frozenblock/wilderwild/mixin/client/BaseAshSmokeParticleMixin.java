package net.frozenblock.wilderwild.mixin.client;

import net.frozenblock.lib.wind.api.ClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseAshSmokeParticle.class)
public abstract class BaseAshSmokeParticleMixin extends TextureSheetParticle {

	protected BaseAshSmokeParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo info) {
		this.xd += ClientWindManager.windX * 0.1;
		this.yd += ClientWindManager.windY * 0.05;
		this.zd += ClientWindManager.windZ * 0.1;
	}
}
