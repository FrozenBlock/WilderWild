package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.lib.sound.FrozenSoundPackets;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.ClientMethodInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderMan.class)
public abstract class EnderManMixin extends Monster {

    @Shadow
    private int lastStareSound;

    protected EnderManMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "playStareSound", at = @At("HEAD"), cancellable = true)
    public void playStareSound(CallbackInfo info) {
        //NOTE: This only runs on the client.
        info.cancel();
        if (this.tickCount >= this.lastStareSound + 400 && this.level.isClientSide) {
            this.lastStareSound = this.tickCount;
            if (!this.isSilent()) {
                ClientMethodInteractionHandler.playClientEnderManSound(EnderMan.class.cast(this));
            }
        }
    }

    @Inject(method = "setTarget", at = @At("TAIL"))
    public void setTarget(@Nullable LivingEntity target, CallbackInfo info) {
        if (target != null) {
            EnderMan enderMan = EnderMan.class.cast(this);
            if (!enderMan.level.isClientSide) {
                FrozenSoundPackets.createMovingRestrictionLoopingSound(enderMan.level, enderMan, RegisterSounds.ENTITY_ENDERMAN_ANGER_LOOP, SoundSource.HOSTILE, 0.1F, 0.9F, WilderWild.id("enderman_anger"));
            }
        }
    }

}
