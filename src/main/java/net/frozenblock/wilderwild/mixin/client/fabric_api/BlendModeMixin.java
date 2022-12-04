/*package net.frozenblock.wilderwild.mixin.client.fabric_api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.client.renderer.RenderType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
@Pseudo
@Mixin(value = BlendMode.class, remap = false)
public class BlendModeMixin {

    @SuppressWarnings("InvokerTarget")
    @Invoker(value = "<init>", remap = false)
    private static BlendMode newMode(String internalName, int internalId, RenderType renderType) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Final
    @Shadow(remap = false)
    @Mutable
    private static BlendMode[] $VALUES;

    @Unique
    private static BlendMode TRANSLUCENT_CUTOUT;

    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/fabricmc/fabric/api/renderer/v1/material/BlendMode;$VALUES:[Lnet/fabricmc/fabric/api/renderer/v1/material/BlendMode;",
            shift = At.Shift.AFTER), remap = false)
    private static void addCustomBlockRenderPass(CallbackInfo ci) {
        var types = new ArrayList<>(Arrays.asList($VALUES));
        var last = types.get(types.size() - 1);

        var translucentCutout = newMode("WILDERWILDTRANSLUCENTCUTOUT", (last.ordinal() + 1), WilderWildClient.translucentCutout());
        TRANSLUCENT_CUTOUT = translucentCutout;
        types.add(translucentCutout);

        $VALUES = types.toArray(new BlendMode[0]);
    }

    @Inject(method = "fromRenderLayer", at = @At("HEAD"), remap = false, cancellable = true)
    private static void fromRenderLayer(RenderType renderLayer, CallbackInfoReturnable<BlendMode> cir) {
        if (renderLayer == WilderWildClient.translucentCutout()) {
            cir.setReturnValue(TRANSLUCENT_CUTOUT);
        }
    }
}
*/