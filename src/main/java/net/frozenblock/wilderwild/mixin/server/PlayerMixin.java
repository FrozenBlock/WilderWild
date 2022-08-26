package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.damagesource.DamageSource.*;

@Mixin(Player.class)
public class PlayerMixin {
    private final Player player = Player.class.cast(this);
    @Inject(method = "getHurtSound", at = @At("HEAD"))
    public void getHurtSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> info) {

        if (damageSource == ON_FIRE) {
            player.playSound(SoundEvents.PLAYER_HURT_ON_FIRE); //ON FIRE
        } else
            if (damageSource == DROWN) {
            player.playSound(SoundEvents.PLAYER_HURT_DROWN); //DROWN
        } else
            if (damageSource == SWEET_BERRY_BUSH) {
            player.playSound(SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH); //SWEET BERRY BUSH
        } else
            if (damageSource == CACTUS) {
                player.playSound(RegisterSounds.PLAYER_HURT_CACTUS); //CACTUS
        } else
            if (damageSource == FREEZE) {
            player.playSound(SoundEvents.PLAYER_HURT_FREEZE); //FREEZE
        } else {
            player.playSound(SoundEvents.PLAYER_HURT); //UNASSIGNED
        }

    }
}
