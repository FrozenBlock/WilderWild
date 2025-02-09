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
