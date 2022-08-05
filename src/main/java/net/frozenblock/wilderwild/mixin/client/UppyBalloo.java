package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PigEntityRenderer.class)
public class UppyBalloo {

    private static final Identifier UPPY_BALLOO = WilderWild.id("textures/entity/pig/uppy_balloo.png");

    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/PigEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void getTexture(PigEntity pig, CallbackInfoReturnable<Identifier> cir) {
        String string = Formatting.strip(pig.getName().getString());
        if (string != null && string.equalsIgnoreCase("a view from the top")) {
            cir.setReturnValue(UPPY_BALLOO);
        }
    }
}
