package net.frozenblock.wilderwild.mixin.client;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.User;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SplashManager.class)
public class ScottTheWozMixin {

    @Shadow
    @Final
    private static RandomSource RANDOM;
    @Shadow
    @Final
    private List<String> splashes;
    @Shadow
    @Final
    private User user;

    @Inject(method = "getSplash", at = @At("TAIL"), cancellable = true)
    public void getSplash(CallbackInfoReturnable<String> info) {
		String lowerName = this.user.getName().toLowerCase();
        if (this.user != null && (RANDOM.nextInt(this.splashes.size()) == 42 || lowerName.contains("scot") || lowerName.contains("skot") || lowerName.contains("sct") || lowerName.contains("skt"))) {
            info.setReturnValue("Hey all, " + this.user.getName() + " here.");
        }
    }
}
