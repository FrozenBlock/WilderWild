package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.lib.sound.FrozenSoundPackets;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(InstrumentItem.class)
public abstract class InstrumentItemMixin {

    @Shadow
    protected abstract Optional<Holder<Instrument>> getInstrument(ItemStack stack);

    @Inject(method = "use", at = @At("RETURN"))
    private void use(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemStack = user.getItemInHand(hand);
        Optional<Holder<Instrument>> optional = this.getInstrument(itemStack);
        if (optional.isPresent()) {
            InstrumentItem goatHorn = InstrumentItem.class.cast(this);
            user.getCooldowns().addCooldown(goatHorn, 0);
        }
    }


    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private static void play(Level world, Player player, Instrument instrument, CallbackInfo info) {
        info.cancel();
        SoundEvent soundEvent = instrument.soundEvent();
        float f = instrument.range() / 16.0F;
        if (!world.isClientSide) {
            FrozenSoundPackets.createMovingRestrictionSound(world, player, soundEvent, SoundSource.RECORDS, f, 1.0F, WilderWild.id("horn"));
        }
        world.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
    }

}
