package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public class CreeperMixin {

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    public void setTarget(@Nullable LivingEntity livingEntity, CallbackInfo info) {
        if (livingEntity != null) {
            if (livingEntity.getType().is(WilderEntityTags.CREEPER_IGNORES)) {
                info.cancel();
            }
        }
    }

}
