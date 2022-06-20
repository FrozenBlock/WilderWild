package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.render.entity.FrogEntityRenderer;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(FrogEntityRenderer.class)
public class XfrtrexSusFrogRenderer {
/*
    private static final Identifier SUS_TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/frog/sus_frog.png");


    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/FrogEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void getTexture(FrogEntity frogEntity, CallbackInfoReturnable<Identifier> cir) {
        String string = Formatting.strip(frogEntity.getName().getString());
        if (Objects.equals(string, "Xfrtrex")) {
            cir.setReturnValue(SUS_TEXTURE);
        }
    }
 */
}
