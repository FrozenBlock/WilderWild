package net.frozenblock.wilderwild.mixin.client.general;

import net.frozenblock.lib.wind.api.ClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExplodeParticle.class)
public abstract class ExplodeParticleMixin extends TextureSheetParticle {

	protected ExplodeParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo info) {
		Vec3 wind = ClientWindManager.getWindMovement(this.level, new BlockPos(this.x, this.y, this.z), 1.5, 1);
		this.xd += wind.x * 0.0005;
		this.yd += wind.y * 0.00000025;
		this.zd += wind.z * 0.0005;
	}
}
