package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Dolphin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(DolphinRenderer.class)
public class AlexDolphinRenderer {

    @Unique
    private static final ResourceLocation ALEX_DOLPHIN = WilderWild.id("textures/entity/dolphin/alex_dolphin.png");

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Dolphin;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    public void getTextureLocation(Dolphin dolphinEntity, CallbackInfoReturnable<ResourceLocation> cir) {
        String string = ChatFormatting.stripFormatting(dolphinEntity.getName().getString());
        if (string != null && string.equalsIgnoreCase("AlexTheDolphin0")) {
            cir.setReturnValue(ALEX_DOLPHIN);
        }
    }
}
