package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {

    @Inject(method = "onHit", at = @At("HEAD"))
	public void onHit(HitResult result, CallbackInfo info) {
		ThrownEnderpearl pearl = ThrownEnderpearl.class.cast(this);
		if (!pearl.level.isClientSide && !pearl.isRemoved()) {
			pearl.level.playSound(null, pearl.getX(), pearl.getY(), pearl.getZ(), RegisterSounds.ITEM_ENDERPEARL_LAND, SoundSource.BLOCKS, 0.6F, 0.85F + (pearl.level.random.nextFloat() * 0.2F));
			pearl.level.playSound(null, pearl.getX(), pearl.getY(), pearl.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.BLOCKS, 0.4F, 1.0F);
			for(int i = 0; i < 32; ++i) {
				pearl.level.addParticle(ParticleTypes.PORTAL, pearl.getX(), pearl.getY() + pearl.level.random.nextDouble() * 2.0, pearl.getZ(), pearl.level.random.nextGaussian(), 0.0, pearl.level.random.nextGaussian());
			}
		}
	}
}
