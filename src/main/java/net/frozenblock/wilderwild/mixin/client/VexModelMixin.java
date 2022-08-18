package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.VexModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.monster.Vex;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(VexModel.class)
public class VexModelMixin {

    @Inject(method = "createBodyLayer", at = @At("HEAD"))
    private static void createBodyLayer(CallbackInfoReturnable<LayerDefinition> cir) {

    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/Vex;FFFFF)V", at = @At("HEAD"))
    private void setAngles(Vex vexEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {

    }

}
