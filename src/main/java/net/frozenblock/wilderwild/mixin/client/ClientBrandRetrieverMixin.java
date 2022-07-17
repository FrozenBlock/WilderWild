package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.misc.WildConfig;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {

    @Inject(at = @At(value = "TAIL"), method = "getClientModName", cancellable = true, remap = false)
    private static void getClientModName(CallbackInfoReturnable<String> info) {
        WildConfig.WildConfigJson config = WildConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric())
                info.setReturnValue(FabricLoader.getInstance().isDevelopmentEnvironment() && !config.getIncludeWild() ? "vanilla" : "wilderwild");
        }
    }

}