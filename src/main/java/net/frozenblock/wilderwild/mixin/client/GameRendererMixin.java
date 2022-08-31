package net.frozenblock.wilderwild.mixin.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {


    @Shadow @Final private Map<String, ShaderInstance> shaders;

    @Inject(method = "reloadShaders", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, shift = At.Shift.BEFORE))
    private void reloadShaders(ResourceManager manager, CallbackInfo ci) throws IOException {
        List<Pair<ShaderInstance, Consumer<ShaderInstance>>> list2 = Lists.newArrayListWithCapacity(this.shaders.size());
        list2.add(
                Pair.of(new ShaderInstance(manager, "rendertype_translucent_cutout_wilderwild", DefaultVertexFormat.BLOCK), shader -> WilderWildClient.renderTypeTranslucentCutoutShader = shader)
        );
    }
}
