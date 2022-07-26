package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.render.entity.GoatEntityRenderer;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(GoatEntityRenderer.class)
public class Treetrain1GoatRenderer {

    private static final Identifier TREETRAIN1_GOAT = WilderWild.id("textures/entity/goat/treetrain1_goat.png");


    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/GoatEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void getTexture(GoatEntity goatEntity, CallbackInfoReturnable<Identifier> cir) {
        String string = Formatting.strip(goatEntity.getName().getString());
        if (Objects.equals(string, "Treetrain1")) {
            cir.setReturnValue(TREETRAIN1_GOAT);
        }
    }
}
