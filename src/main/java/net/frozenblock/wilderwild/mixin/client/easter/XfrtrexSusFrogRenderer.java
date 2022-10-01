package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(FrogRenderer.class)
public class XfrtrexSusFrogRenderer {

    @Unique
    private static final ResourceLocation SUS_TEXTURE = WilderWild.id("textures/entity/frog/sus_frog.png");


    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/frog/Frog;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    public void getTextureLocation(Frog frogEntity, CallbackInfoReturnable<ResourceLocation> cir) {
        String string = ChatFormatting.stripFormatting(frogEntity.getName().getString());
        if (string != null && string.equalsIgnoreCase("Xfrtrex")) {
            cir.setReturnValue(SUS_TEXTURE);
        }
    }

}
