package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    public void getHurtSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> info) {
        if (damageSource == DamageSource.CACTUS) { //this only works for other players, so we have to find the class that controls the damage sound for the current player
            info.setReturnValue(RegisterSounds.PLAYER_HURT_CACTUS);
            info.cancel();
        }
    }

}
