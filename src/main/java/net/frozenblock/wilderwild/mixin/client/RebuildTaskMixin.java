/*package net.frozenblock.wilderwild.mixin.client;

import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChunkRenderDispatcher.RenderChunk.RebuildTask.class)
public class RebuildTaskMixin {

    @Unique
    private ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults compileResults;

    @ModifyVariable(method = "compile", at = @At(value = "STORE", target = "Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$RenderChunk$RebuildTask$CompileResults;<init>()V"), index = 5)
    private ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults setBlockPos(ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults value) {
        this.compileResults = value;
        return value;
    }

    /*@Inject(method = "compile", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z", shift = At.Shift.BEFORE))
    private void compile(float f, float g, float h, ChunkBufferBuilderPack chunkBufferBuilderPack, CallbackInfoReturnable<ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults> cir) {
        ChunkRenderDispatcher.RenderChunk.RebuildTask rebuildTask = ChunkRenderDispatcher.RenderChunk.RebuildTask.class.cast(this);
        BlockPos blockPos = rebuildTask.getClass().asSubclass(ChunkRenderDispatcher.RenderChunk.class).cast(rebuildTask).getOrigin().immutable();
        BlockPos blockPos2 = blockPos.offset(15, 15, 15);
        var set = new ReferenceArraySet<>(RenderType.chunkBufferLayers().size());
        if (set.contains(WilderWildClient.translucentCutout())) {
            BufferBuilder bufferBuilder2 = chunkBufferBuilderPack.builder(RenderType.translucent());
            if (!bufferBuilder2.isCurrentBatchEmpty()) {
                bufferBuilder2.setQuadSortOrigin(f - (float)blockPos.getX(), g - (float)blockPos.getY(), h - (float)blockPos.getZ());
                compileResults.transparencyState = bufferBuilder2.getSortState();
            }
        }
    }
}
*/