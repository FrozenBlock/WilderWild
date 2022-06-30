package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.render.entity.WardenEntityRenderer;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(WardenEntityRenderer.class)
public class OsmioooWardenRenderer {

    private static final Identifier OSMIOOO_WARDEN = WilderWild.id("textures/entity/warden/osmiooo_warden.png");

    @Inject(method = "getTexture(Lnet/minecraft/entity/mob/WardenEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void getTexture(WardenEntity warden, CallbackInfoReturnable<Identifier> cir) {
        String string = Formatting.strip(warden.getName().getString());
        if (Objects.equals(string, "Osmiooo")) {
            cir.setReturnValue(OSMIOOO_WARDEN);
        }
    }
}
