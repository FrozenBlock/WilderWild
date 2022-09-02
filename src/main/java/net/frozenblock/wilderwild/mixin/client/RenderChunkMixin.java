package net.frozenblock.wilderwild.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.mojang.blaze3d.vertex.BufferBuilder;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ChunkRenderDispatcher.RenderChunk.RebuildTask.class)
public class RenderChunkMixin {

    @ModifyExpressionValue(method = "compile", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z", ordinal = 0))
    private boolean sussy(boolean originalTranslucent) {
        Set<RenderType> set = new ReferenceArraySet<>(RenderType.chunkBufferLayers().size());
        return originalTranslucent || set.contains(WilderWildClient.translucentCutout());
    }
}
