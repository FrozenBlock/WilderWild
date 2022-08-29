package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.damagesource.DamageSource.CACTUS;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    public void getHurtSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> info) {
        if (damageSource == CACTUS) {
            info.cancel();
            info.setReturnValue(RegisterSounds.PLAYER_HURT_CACTUS);
        }
    }

}
