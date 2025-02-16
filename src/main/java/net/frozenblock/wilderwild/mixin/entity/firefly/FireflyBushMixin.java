package net.frozenblock.wilderwild.mixin.entity.firefly;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireflyBushBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireflyBushBlock.class)
public class FireflyBushMixin {

	@WrapWithCondition(
		method = "animateTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
		)
	)
	public boolean wilderWild$animateTick(Level instance, ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i) {
		return WWEntityConfig.Client.SPAWN_FIREFLY_PARTICLES;
	}

}
