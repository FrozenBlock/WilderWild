package net.frozenblock.wilderwild.mixin.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.sound.api.instances.RestrictedMovingSoundLoop;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.lib.sound.impl.EntityLoopingSoundInterface;
import net.frozenblock.wilderwild.misc.ClientMethodInteractionHandler;
import net.frozenblock.wilderwild.misc.WilderEnderman;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(EnderMan.class)
public final class EnderManMixin extends Monster implements WilderEnderman {

	@Unique
	private boolean wilderWild$canPlayLoopingSound = true;

	@Shadow
	private int lastStareSound;

    private EnderManMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

	@Unique
	@Override
	public boolean canPlayLoopingSound() {
		return this.wilderWild$canPlayLoopingSound;
	}

	@Unique
	@Override
	public void setCanPlayLoopingSound(boolean bl) {
		this.wilderWild$canPlayLoopingSound = true;
	}

	@Inject(method = "playStareSound", at = @At(value = "HEAD"), cancellable = true)
    public void playStareSound(CallbackInfo info) {
        //NOTE: This only runs on the client.
		if (this.level.isClientSide && ClothConfigInteractionHandler.movingStareSound()) {
			info.cancel();
			if (this.tickCount >= this.lastStareSound + 400) {
				this.lastStareSound = this.tickCount;
				if (!this.isSilent()) {
					ClientMethodInteractionHandler.playClientEnderManSound(EnderMan.class.cast(this));
					this.level.playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
				}
			}
		}
		createAngerLoopSound();
    }

    @Inject(method = "setTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData;set(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V", ordinal = 2, shift = At.Shift.AFTER))
    public void setTarget(@Nullable LivingEntity target, CallbackInfo info) {
		createAngerLoopSound();
    }

	@Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Monster;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", shift = At.Shift.AFTER))
	private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (source.getEntity() instanceof LivingEntity) {
			createAngerLoopSound();
		}
	}

	@Unique
	@Override
	public void createAngerLoopSound() {
		if (ClothConfigInteractionHandler.angerLoopSound()) {
			ClientMethodInteractionHandler.playEnderManAngerLoop(EnderMan.class.cast(this));
		}
	}
}
