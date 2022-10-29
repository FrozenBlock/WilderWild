package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.lib.sound.FrozenSoundPackets;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InstrumentItem.class)
public final class InstrumentItemMixin {

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V", shift = At.Shift.AFTER))
    private void removeCooldown(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        player.getCooldowns().removeCooldown(InstrumentItem.class.cast(this));
    }


    @Redirect(method = "play", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private static void playRestrictionSound(Level level, Player player, Entity entity, SoundEvent soundEvent, SoundSource soundSource, float f, float g) {
        if (!level.isClientSide) {
            FrozenSoundPackets.createMovingRestrictionSound(level, player, soundEvent, soundSource, f, g, WilderSharedConstants.id("instrument"));
        }
    }

}
