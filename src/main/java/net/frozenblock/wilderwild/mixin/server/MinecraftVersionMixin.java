package net.frozenblock.wilderwild.mixin.server;

import com.google.gson.JsonObject;
import net.frozenblock.wilderwild.WilderWild;
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
        this.name = !WilderWild.DEV_LOGGING ? WilderWild.snapshotName : "MOJANGSTAAAAAA";
        this.releaseTarget = "1.20";
    }

    @Inject(at = @At("RETURN"), method = "<init>(Lcom/google/gson/JsonObject;)V")
    public void changeVersion(JsonObject jsonObject, CallbackInfo info) {
        changeVersion(info);
    }


}