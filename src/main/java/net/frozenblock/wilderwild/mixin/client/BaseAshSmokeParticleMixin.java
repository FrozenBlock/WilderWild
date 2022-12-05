package net.frozenblock.wilderwild.mixin.client;

import net.frozenblock.lib.wind.api.ClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseAshSmokeParticle.class)
public abstract class BaseAshSmokeParticleMixin extends TextureSheetParticle {

	protected BaseAshSmokeParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo info) {
		Vec3 wind = ClientWindManager.getWindMovement(this.level, new BlockPos(this.x, this.y, this.z), 1.5, 1);
		this.xd += wind.x * 0.5;
		this.yd += wind.y * 0.1;
		this.zd += wind.z * 0.5;
	}
}
