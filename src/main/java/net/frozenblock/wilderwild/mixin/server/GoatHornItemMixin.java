package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(GoatHornItem.class)
public abstract class GoatHornItemMixin {

    @Shadow protected abstract Optional<RegistryEntry<Instrument>> getInstrument(ItemStack stack);

    @Inject(method = "use", at = @At("RETURN"))
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);
        Optional<RegistryEntry<Instrument>> optional = this.getInstrument(itemStack);
        if (optional.isPresent()) {
            GoatHornItem goatHorn = GoatHornItem.class.cast(this);
            user.getItemCooldownManager().set(goatHorn, 0);
        }
    }


    @Inject(method = "playSound", at = @At("HEAD"), cancellable = true)
    private static void playSound(World world, PlayerEntity player, Instrument instrument, CallbackInfo info) {
        info.cancel();
        SoundEvent soundEvent = instrument.soundEvent();
        float f = instrument.range() / 16.0F;
        if (!world.isClient) {
            EasyPacket.createMovingRestrictionSound(world, player, soundEvent, SoundCategory.RECORDS, f, 1.0F, WilderWild.id("horn"));
        }
        world.emitGameEvent(GameEvent.INSTRUMENT_PLAY, player.getPos(), GameEvent.Emitter.of(player));
    }

}
