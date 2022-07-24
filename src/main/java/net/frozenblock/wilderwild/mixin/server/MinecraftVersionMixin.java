package net.frozenblock.wilderwild.mixin.server;

import com.google.gson.JsonObject;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderConfig;
import net.minecraft.MinecraftVersion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftVersion.class)
public class MinecraftVersionMixin {
    @Mutable
    @Final
    @Shadow
    private String name;
    @Mutable
    @Final
    @Shadow
    private String releaseTarget;

    @Inject(at = @At(value = "RETURN"), method = "<init>()V")
    private void changeVersion(CallbackInfo info) {
        WilderConfig.WildConfigJson config = WilderConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric()) {
                this.name = !WilderWild.DEV_LOGGING ? WilderWild.snapshotName : "FROZENBLOCK";
                this.releaseTarget = config.getIncludeWild() ? "1.20" : "1.19";
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "<init>(Lcom/google/gson/JsonObject;)V")
    public void changeVersion(JsonObject jsonObject, CallbackInfo info) {
        WilderConfig.WildConfigJson config = WilderConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric()) {
                changeVersion(info);
            }
        }
    }


}