package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.render.entity.DolphinEntityRenderer;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(DolphinEntityRenderer.class)
public class AlexDolphinRenderer {

    private static final Identifier ALEX_DOLPHIN = WilderWild.id("textures/entity/dolphin/alex_dolphin.png");

    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/DolphinEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void getTexture(DolphinEntity dolphinEntity, CallbackInfoReturnable<Identifier> cir) {
        String string = Formatting.strip(dolphinEntity.getName().getString());
        if (Objects.equals(string, "AlexTheDolphin0")) {
            cir.setReturnValue(ALEX_DOLPHIN);
        }
    }
}
