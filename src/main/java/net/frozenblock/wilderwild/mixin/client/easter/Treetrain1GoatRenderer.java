package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.GoatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.goat.Goat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(GoatRenderer.class)
public class Treetrain1GoatRenderer {

    @Unique
    private static final ResourceLocation TREETRAIN1_GOAT = WilderWild.id("textures/entity/goat/treetrain1_goat.png");


    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/goat/Goat;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    public void getTextureLocation(Goat goatEntity, CallbackInfoReturnable<ResourceLocation> cir) {
        String string = ChatFormatting.stripFormatting(goatEntity.getName().getString());
        if (Objects.equals(string, "Treetrain1") || Objects.equals(string, "Treetrain")) {
            cir.setReturnValue(TREETRAIN1_GOAT);
        }
    }
}
