package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.lib.sound.FrozenSoundPackets;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.ClientMethodInteractionHandler;
import net.frozenblock.wilderwild.misc.WilderEnderman;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
	public void setCanPlayLoopingSound() {
		this.wilderWild$canPlayLoopingSound = true;
	}

    @Inject(method = "playStareSound", at = @At(value = "HEAD"), cancellable = true)
    public void playStareSound(CallbackInfo info) {
        //NOTE: This only runs on the client.
		if (ClothConfigInteractionHandler.movingStareSound()) {
			info.cancel();
			if (this.tickCount >= this.lastStareSound + 400) {
				this.lastStareSound = this.tickCount;
				if (!this.isSilent()) {
					ClientMethodInteractionHandler.playClientEnderManSound(EnderMan.class.cast(this));
					this.level.playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
				}
			}
		}
		if (ClothConfigInteractionHandler.angerLoopSound()) {
			EnderMan enderMan = EnderMan.class.cast(this);
			if (!enderMan.level.isClientSide && this.wilderWild$canPlayLoopingSound) {
				FrozenSoundPackets.createMovingRestrictionLoopingSound(enderMan.level, enderMan, RegisterSounds.ENTITY_ENDERMAN_ANGER_LOOP, SoundSource.HOSTILE, 1.0F, 0.9F, WilderWild.id("enderman_anger"));
				this.wilderWild$canPlayLoopingSound = false;
			}
		}
    }

    @Inject(method = "setTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData;set(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V", ordinal = 2, shift = At.Shift.AFTER))
    public void setTarget(@Nullable LivingEntity target, CallbackInfo info) {
		if (ClothConfigInteractionHandler.angerLoopSound()) {
			EnderMan enderMan = EnderMan.class.cast(this);
			if (!enderMan.level.isClientSide && this.wilderWild$canPlayLoopingSound) {
				FrozenSoundPackets.createMovingRestrictionLoopingSound(enderMan.level, enderMan, RegisterSounds.ENTITY_ENDERMAN_ANGER_LOOP, SoundSource.HOSTILE, 1.0F, 0.9F, WilderWild.id("enderman_anger"));
				this.wilderWild$canPlayLoopingSound = false;
			}
		}
    }

}
