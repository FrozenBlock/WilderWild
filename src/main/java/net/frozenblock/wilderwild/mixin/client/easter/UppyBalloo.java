package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PigRenderer.class)
public final class UppyBalloo {

    @Unique
    private static final ResourceLocation WILDERWILD$UPPY_BALLOO = WilderWild.id("textures/entity/pig/uppy_balloo.png");

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Pig;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    public void getTextureLocation(Pig pig, CallbackInfoReturnable<ResourceLocation> cir) {
        String string = ChatFormatting.stripFormatting(pig.getName().getString());
        if (string != null && string.equalsIgnoreCase("a view from the top")) {
            cir.setReturnValue(WILDERWILD$UPPY_BALLOO);
        }
    }
}
