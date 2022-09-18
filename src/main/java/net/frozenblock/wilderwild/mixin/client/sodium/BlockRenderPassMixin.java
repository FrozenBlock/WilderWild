/*package net.frozenblock.wilderwild.mixin.client.sodium;

import me.jellysquid.mods.sodium.client.render.chunk.passes.BlockRenderPass;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.misc.mod_compat.sodium.WilderBlockRenderPass;
import net.minecraft.client.renderer.RenderType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
@Pseudo
@Mixin(BlockRenderPass.class)
public class BlockRenderPassMixin {

    @SuppressWarnings("InvokerTarget")
    @Invoker(value = "<init>", remap = false)
    private static BlockRenderPass newPass(String internalName, int internalId, RenderType layer, boolean translucent, float alphaCutoff) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Final
    @Shadow(remap = false)
    @Mutable
    private static BlockRenderPass[] $VALUES;

    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lme/jellysquid/mods/sodium/client/render/chunk/passes/BlockRenderPass;$VALUES:[Lme/jellysquid/mods/sodium/client/render/chunk/passes/BlockRenderPass;",
            shift = At.Shift.AFTER), remap = false)
    private static void addCustomBlockRenderPass(CallbackInfo ci) {
        var types = new ArrayList<>(Arrays.asList($VALUES));
        var last = types.get(types.size() - 1);

        var wildertranslucent = newPass("WILDERWILDTRANSLUCENT", -(last.ordinal() + 1), WilderWildClient.translucentCutout(), true, 0.01F);
        WilderBlockRenderPass.WILDERTRANSLUCENT = wildertranslucent;
        types.add(wildertranslucent);

        $VALUES = types.toArray(new BlockRenderPass[0]);
    }
}
*/