package net.frozenblock.wilderwild.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WildConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GuiComponent.class)
public class GuiComponentMixin {

    @Inject(at = @At(value = "HEAD"), method = "drawString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V", cancellable = true)
    private static void drawStringWithShadow(PoseStack matrices, Font textRenderer, String text, int x, int y, int color, CallbackInfo info) {
        WildConfig.WildConfigJson config = WildConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric() && text.contains("Modded")) {
                info.cancel();
                textRenderer.drawShadow(matrices, (config.getIncludeWild() ? "Minecraft + WilderWild " : "Minecraft ") + WilderWild.snapshotName + "/snapshot", (float) x, (float) y, color);
            }
        }
    }

}