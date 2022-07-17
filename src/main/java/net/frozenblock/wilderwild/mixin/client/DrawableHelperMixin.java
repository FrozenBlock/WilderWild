package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(DrawableHelper.class)
public class DrawableHelperMixin {

    @Inject(at = @At(value = "HEAD"), method = "drawStringWithShadow", cancellable = true)
    private static void drawStringWithShadow(MatrixStack matrices, TextRenderer textRenderer, String text, int x, int y, int color, CallbackInfo info) {
        if (text.contains("Modded")) {
            info.cancel();
            textRenderer.drawWithShadow(matrices, "Minecraft + WilderWild " + WilderWild.snapshotName + "/snapshot", (float) x, (float) y, color);
        }
    }

}