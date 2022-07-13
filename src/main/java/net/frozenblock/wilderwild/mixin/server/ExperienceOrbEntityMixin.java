package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.item.AncientHorn;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    @Shadow
    private int amount;

    @Inject(at = @At("TAIL"), method = "repairPlayerGears", cancellable = true)
    private void repairPlayerGears(PlayerEntity player, int amount, CallbackInfoReturnable<Integer> info) {
        int hornCooldown = AncientHorn.decreaseCooldown(player, amount * 8);
        if (hornCooldown != -1) {
            info.setReturnValue(0);
            info.cancel();
        }
    }

}
