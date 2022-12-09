package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.item.AncientHorn;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {

    @Inject(at = @At("RETURN"), method = "repairPlayerItems", cancellable = true)
    private void repairAncientHorn(Player player, int amount, CallbackInfoReturnable<Integer> info) {
        int hornCooldown = AncientHorn.decreaseCooldown(player, amount * 8);
        if (hornCooldown != -1) {
            info.setReturnValue(0);
        }
    }

}
