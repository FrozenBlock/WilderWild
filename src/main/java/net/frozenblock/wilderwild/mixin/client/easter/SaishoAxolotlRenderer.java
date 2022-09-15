package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.AxolotlRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(AxolotlRenderer.class)
public class SaishoAxolotlRenderer {

    @Unique
    private static final ResourceLocation SAISHO_AXOLOTL = WilderWild.id("textures/entity/axolotl/saisho_axolotl.png");

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/axolotl/Axolotl;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    public void getTextureLocation(Axolotl axolotlEntity, CallbackInfoReturnable<ResourceLocation> cir) {
        String string = ChatFormatting.stripFormatting(axolotlEntity.getName().getString());
        if (Objects.equals(string, "Saisho")) {
            cir.setReturnValue(SAISHO_AXOLOTL);
        }
    }
}
