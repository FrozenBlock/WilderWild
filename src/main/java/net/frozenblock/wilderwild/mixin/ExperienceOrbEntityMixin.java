package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.item.AncientCityGoatHorn;
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
        int hornCooldown = AncientCityGoatHorn.decreaseCooldown(player, amount * 5);
        if (hornCooldown != -1) {
            info.setReturnValue(0);
            info.cancel();
        }
    }

}
