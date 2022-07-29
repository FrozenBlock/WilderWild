package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.render.entity.SlimeEntityRenderer;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(SlimeEntityRenderer.class)
public class MerpSlimeRenderer {

    private static final Identifier MERP_SLIME = WilderWild.id("textures/entity/slime/merp_slime.png");

    @Inject(method = "getTexture(Lnet/minecraft/entity/mob/SlimeEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void getTexture(SlimeEntity slimeEntity, CallbackInfoReturnable<Identifier> cir) {
        String string = Formatting.strip(slimeEntity.getName().getString());
        if (Objects.equals(string, "Merp")) {
            cir.setReturnValue(MERP_SLIME);
        }
    }
}
