package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(DebugHud.class)
public class DebugHudMixin {

    private static final boolean HIDE_FABRIC_RENDER = true;
    private static final boolean HIDE_ENTITY_CULLING = true;

    @Inject(at = @At("RETURN"), method = "getLeftText", cancellable = true)
    protected void getLeftText(CallbackInfoReturnable<List<String>> info) {
        if (HIDE_FABRIC_RENDER) {
            info.getReturnValue().removeIf(string -> string.contains("Active renderer"));
        }
        if (HIDE_ENTITY_CULLING) {
            info.getReturnValue().removeIf(string -> string.contains("[Culling]"));
        }
    }

}