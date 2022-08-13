package net.frozenblock.wilderwild.mixin.client;

import net.frozenblock.wilderwild.misc.WilderConstants;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MainMixin {

    @Inject(method = "main([Ljava/lang/String;Z)V", at = @At("HEAD"))
    private static void main(String[] args, boolean optimizeDataFixer, CallbackInfo ci) {
        if (optimizeDataFixer) {
            WilderConstants.enableDataFixerOptimization();
        }
    }
}
