package net.frozenblock.wilderwild.mixin.client.easter;

import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SlimeRenderer.class)
public final class MerpSlimeRenderer {

    @Unique
    private static final ResourceLocation WILDERWILD$MERP_SLIME = WilderSharedConstants.id("textures/entity/slime/merp_slime.png");

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/monster/Slime;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    public void getTextureLocation(Slime slimeEntity, CallbackInfoReturnable<ResourceLocation> cir) {
        String string = ChatFormatting.stripFormatting(slimeEntity.getName().getString());
        if (Objects.equals(string, "Merp")) {
            cir.setReturnValue(WILDERWILD$MERP_SLIME);
        }
    }
}
