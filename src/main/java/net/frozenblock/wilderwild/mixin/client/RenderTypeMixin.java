/*package net.frozenblock.wilderwild.mixin.client;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(RenderType.class)
public class RenderTypeMixin {

    @Shadow
    public static RenderType tripwire() {
        return null;
    }

    @Shadow
    public static RenderType translucent() {
        return null;
    }

    @Shadow
    public static RenderType cutout() {
        return null;
    }

    @Shadow
    public static RenderType cutoutMipped() {
        return null;
    }

    @Shadow
    public static RenderType solid() {
        return null;
    }

    @Shadow
    @Final
    @Mutable
    private static final ImmutableList<RenderType> CHUNK_BUFFER_LAYERS = ImmutableList.of(solid(), cutoutMipped(), cutout(), translucent(), WilderWildClient.translucentCutout(), tripwire());
}
*/