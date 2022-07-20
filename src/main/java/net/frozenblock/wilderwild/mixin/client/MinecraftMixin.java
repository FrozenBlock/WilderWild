package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WildConfig;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(at = @At(value = "HEAD"), method = "createTitle", cancellable = true)
    private void createTitle(CallbackInfoReturnable<String> info) {
        WildConfig.WildConfigJson config = WildConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric()) {
                Minecraft client = Minecraft.class.cast(this);
                StringBuilder title = new StringBuilder();
                title.append(config.getIncludeWild() ? "Minecraft + WilderWild " : "Minecraft ").append(SharedConstants.getCurrentVersion().getName());
                ClientPacketListener clientPlayNetworkHandler = client.getConnection();
                if (clientPlayNetworkHandler != null && clientPlayNetworkHandler.getConnection().isConnected()) {
                    title.append(" - ");
                    if (client.getSingleplayerServer() != null && !client.getSingleplayerServer().isPublished()) {
                        title.append(I18n.get("title.singleplayer"));
                    } else if (client.isConnectedToRealms()) {
                        title.append(I18n.get("title.multiplayer.realms"));
                    } else if (client.getSingleplayerServer() == null && (client.getCurrentServer() == null || !client.getCurrentServer().isLan())) {
                        title.append(I18n.get("title.multiplayer.other"));
                    } else {
                        title.append(I18n.get("title.multiplayer.lan"));
                    }
                }
                info.setReturnValue(title.toString());
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getLaunchedVersion", cancellable = true)
    public void getLaunchedVersion(CallbackInfoReturnable<String> info) {
        WildConfig.WildConfigJson config = WildConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric()) {
                info.setReturnValue(!WilderWild.DEV_LOGGING ? WilderWild.snapshotName : "FROZENBLOCK");
                info.cancel();
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getVersionType", cancellable = true)
    public void getVersionType(CallbackInfoReturnable<String> info) {
        WildConfig.WildConfigJson config = WildConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric()) {
                info.setReturnValue("snapshot");
                info.cancel();
            }
        }
    }

}