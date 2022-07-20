package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WildConfig;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    private static final boolean HIDE_FABRIC_RENDER = true;
    private static final boolean HIDE_ENTITY_CULLING = true;

    @Inject(at = @At("TAIL"), method = "getGameInformation", cancellable = true)
    protected void getGameInformation(CallbackInfoReturnable<List<String>> info) {
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
                if (FabricLoader.getInstance().isDevelopmentEnvironment() && !config.getIncludeWild()) {
                    for (String string : strings) {
                        String newString = string.replaceAll(WilderWild.MOD_ID + ";", "minecraft:");
                        strings.set(strings.indexOf(string), newString);
                    }

                }
                info.setReturnValue(strings);
                info.cancel();
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "getSystemInformation", cancellable = true)
    protected void getSystemInformation(CallbackInfoReturnable<List<String>> info) {
        WildConfig.WildConfigJson config = WildConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric()) {
                List<String> strings = new ArrayList<>() {{
                    addAll(info.getReturnValue());
                }};
                if (FabricLoader.getInstance().isDevelopmentEnvironment() && !config.getIncludeWild()) {
                    for (String string : strings) {
                        String newString = string.replaceAll(WilderWild.MOD_ID + ":", "minecraft:");
                        strings.set(strings.indexOf(string), newString);
                    }

                }
                info.setReturnValue(strings);
                info.cancel();
            }
        }
    }

}