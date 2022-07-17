package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At(value = "HEAD"), method = "getWindowTitle", cancellable = true)
    private void getWindowTitle(CallbackInfoReturnable<String> info) {
        MinecraftClient client = MinecraftClient.class.cast(this);
        StringBuilder title = new StringBuilder();
        title.append("Minecraft + WilderWild ").append(SharedConstants.getGameVersion().getName());
        ClientPlayNetworkHandler clientPlayNetworkHandler = client.getNetworkHandler();
        if (clientPlayNetworkHandler != null && clientPlayNetworkHandler.getConnection().isOpen()) {
            title.append(" - ");
            if (client.getServer() != null && !client.getServer().isRemote()) {
                title.append(I18n.translate("title.singleplayer"));
            } else if (client.isConnectedToRealms()) {
                title.append(I18n.translate("title.multiplayer.realms"));
            } else if (client.getServer() == null && (client.getCurrentServerEntry() == null || !client.getCurrentServerEntry().isLocal())) {
                title.append(I18n.translate("title.multiplayer.other"));
            } else {
                title.append(I18n.translate("title.multiplayer.lan"));
            }
        }
        info.setReturnValue(title.toString());
    }

    @Inject(at = @At(value = "HEAD"), method = "getGameVersion", cancellable = true)
    public void getGameVersion(CallbackInfoReturnable<String> info) {
        info.setReturnValue(!WilderWild.DEV_LOGGING ? WilderWild.snapshotName : "MOJANGSTAAAAAA");
        info.cancel();
    }

    @Inject(at = @At(value = "HEAD"), method = "getVersionType", cancellable = true)
    public void getVersionType(CallbackInfoReturnable<String> info) {
        info.setReturnValue("snapshot");
        info.cancel();
    }

}