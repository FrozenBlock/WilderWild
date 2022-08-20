package net.frozenblock.wilderwild.mixin.server;

import com.mojang.serialization.Lifecycle;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimaryLevelData.class)
public class NoExperimentalMixin {

    @Inject(method = "worldGenSettingsLifecycle", at = @At("HEAD"), cancellable = true)
    public void worldGenSettingsLifecycle(CallbackInfoReturnable<Lifecycle> info) {
        info.cancel();
        info.setReturnValue(Lifecycle.stable());
    }

}
