package net.frozenblock.wilderwild.mixin.client;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.VexEntityModel;
import net.minecraft.entity.mob.VexEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VexEntityModel.class)
public class VexModelMixin {

    @Inject(method = "getTexturedModelData", at = @At("HEAD"))
    private static void getTexturedModelData(CallbackInfoReturnable<TexturedModelData> cir) {

    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/mob/VexEntity;FFFFF)V", at = @At("HEAD"))
    private void setAngles(VexEntity vexEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {

    }

}
