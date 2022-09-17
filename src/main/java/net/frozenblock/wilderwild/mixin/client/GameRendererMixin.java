/*package net.frozenblock.wilderwild.mixin.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {


    @Shadow
    @Final
    private Map<String, ShaderInstance> shaders;

    @Unique
    private List<Pair<ShaderInstance, Consumer<ShaderInstance>>> shaderList;

    @Inject(method = "reloadShaders", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;shutdownShaders()V", ordinal = 0, shift = At.Shift.BEFORE))
    private void reloadShaders(ResourceManager manager, CallbackInfo ci) {
        List<Pair<ShaderInstance, Consumer<ShaderInstance>>> shaderList = Lists.newArrayListWithCapacity(this.shaders.size());
        try {
            shaderList.add(
                    Pair.of(new ShaderInstance(manager, "rendertype_translucent_cutout_wilderwild", DefaultVertexFormat.BLOCK), shader -> WilderWildClient.renderTypeTranslucentCutoutShader = shader)
            );
        } catch (IOException var5) {
            shaderList.forEach(pair -> pair.getFirst().close());
            throw new RuntimeException("Could not reload Wilder Wild shaders", var5);
        }

        this.shaderList = shaderList;
    }

    @Inject(method = "reloadShaders", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;shutdownShaders()V", ordinal = 0, shift = At.Shift.AFTER))
    private void postReloadShaders(ResourceManager resourceManager, CallbackInfo ci) {
        this.shaderList.forEach(pair -> {
            ShaderInstance shaderInstance = pair.getFirst();
            this.shaders.put(shaderInstance.getName(), shaderInstance);
            pair.getSecond().accept(shaderInstance);
        });
    }
}
*/