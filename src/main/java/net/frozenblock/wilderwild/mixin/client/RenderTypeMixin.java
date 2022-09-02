package net.frozenblock.wilderwild.mixin.client;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderType.class)
public class RenderTypeMixin {

    @Shadow
    public static RenderType solid() {
        return null;
    }

    @Shadow
    public static RenderType cutoutMipped() {
        return null;
    }

    @Shadow
    public static RenderType cutout() {
        return null;
    }

    @Shadow
    public static RenderType translucent() {
        return null;
    }

    @Shadow
    public static RenderType tripwire() {
        return null;
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;", ordinal = 0))
    private static ImmutableList chunkBufferLayers(Object renderType, Object renderType1, Object renderType2, Object renderType3, Object renderType4) {
        return ImmutableList.of(renderType, renderType1, renderType2, renderType3, WilderWildClient.translucentCutout(), renderType4);
    }
}
