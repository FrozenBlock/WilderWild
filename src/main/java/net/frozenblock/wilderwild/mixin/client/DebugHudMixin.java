package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.WildConfig;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(DebugHud.class)
public class DebugHudMixin {

    private static final boolean HIDE_FABRIC_RENDER = true;
    private static final boolean HIDE_ENTITY_CULLING = true;

    @Inject(at = @At("TAIL"), method = "getLeftText", cancellable = true)
    protected void getLeftText(CallbackInfoReturnable<List<String>> info) {
        WildConfig.WildConfigJson config = WildConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric()) {
                List<String> strings = new ArrayList<>() {{
                    addAll(info.getReturnValue());
                }};
                if (HIDE_FABRIC_RENDER) {
                    strings.removeIf(string -> string.contains("Active renderer"));
                }
                if (HIDE_ENTITY_CULLING) {
                    strings.removeIf(string -> string.contains("Culling"));
                }
                info.setReturnValue(strings);
                info.cancel();
            }
        }
    }

}