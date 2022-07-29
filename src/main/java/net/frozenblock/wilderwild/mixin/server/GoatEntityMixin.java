package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.entity.passive.GoatEntity;
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

    @Inject(method = "isScreaming", at = @At("HEAD"), cancellable = true)
    private void isScreaming(CallbackInfoReturnable<Boolean> cir) {
        if (this.isTreetrain1()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
