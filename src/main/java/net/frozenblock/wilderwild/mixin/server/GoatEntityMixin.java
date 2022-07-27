package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(GoatEntity.class)
public class GoatEntityMixin {

    private boolean isTreetrain1() {
        GoatEntity goat = GoatEntity.class.cast(this);
        String string = Formatting.strip(goat.getName().getString());
        return Objects.equals(string, "Treetrain1");
    }

    @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
    private void getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.isTreetrain1()) {
            cir.setReturnValue(SoundEvents.ENTITY_GOAT_SCREAMING_AMBIENT);
        }
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void getHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        if (this.isTreetrain1()) {
            cir.setReturnValue(SoundEvents.ENTITY_GOAT_SCREAMING_HURT);
        }
    }

    @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
    private void getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.isTreetrain1()) {
            cir.setReturnValue(SoundEvents.ENTITY_GOAT_SCREAMING_DEATH);
        }
    }

    @Inject(method = "getMilkingSound", at = @At("HEAD"), cancellable = true)
    private void getMilkingSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.isTreetrain1()) {
            cir.setReturnValue(SoundEvents.ENTITY_GOAT_SCREAMING_MILK);
        }
    }

    @Inject(method = "getEatSound", at = @At("HEAD"), cancellable = true)
    private void getEatSound(ItemStack stack, CallbackInfoReturnable<SoundEvent> cir) {
        if (this.isTreetrain1()) {
            cir.setReturnValue(SoundEvents.ENTITY_GOAT_SCREAMING_EAT);
        }
    }
}
