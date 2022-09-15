package net.frozenblock.wilderwild.mixin.client.sodium;

import me.jellysquid.mods.sodium.client.render.chunk.passes.BlockRenderPass;
import me.jellysquid.mods.sodium.client.render.chunk.passes.BlockRenderPassManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.misc.mod_compat.sodium.SodiumInteraction;
import net.frozenblock.wilderwild.misc.mod_compat.sodium.WilderBlockRenderPass;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Pseudo
@Mixin(BlockRenderPassManager.class)
public class BlockRenderPassManagerMixin implements SodiumInteraction {

    @Shadow
    private void addMapping(RenderType layer, BlockRenderPass type) {
    }

    @ModifyVariable(method = "createDefaultMappings", at = @At(value = "LOAD", target = "Lme/jellysquid/mods/sodium/client/render/chunk/passes/BlockRenderPassManager;<init>()V"), remap = false)
    private static BlockRenderPassManager createDefaultMappings(BlockRenderPassManager value) {
        ((SodiumInteraction) value).frozenAddMapping(WilderWildClient.translucentCutout(), WilderBlockRenderPass.WILDERTRANSLUCENT);
        return value;
    }

    @Override
    public void frozenAddMapping(RenderType layer, BlockRenderPass type) {
        this.addMapping(layer, type);
    }
}
