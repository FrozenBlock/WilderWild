package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.EnumSet;

@Mixin(ChunkStatus.class)
public class ChunkStatusMixin {

    @Shadow @Final public static ChunkStatus STRUCTURE_STARTS;
    @Final
    @Shadow
    private static EnumSet<Heightmap.Types> PRE_FEATURES;

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkStatus;registerSimple(Ljava/lang/String;Lnet/minecraft/world/level/chunk/ChunkStatus;ILjava/util/EnumSet;Lnet/minecraft/world/level/chunk/ChunkStatus$ChunkType;Lnet/minecraft/world/level/chunk/ChunkStatus$SimpleGenerationTask;)Lnet/minecraft/world/level/chunk/ChunkStatus;", ordinal = 1))
    private static void gbnruibvis(Args args) {
        args.set(2, 16);
    }

    @Shadow
    private static ChunkStatus registerSimple(String string, @Nullable ChunkStatus chunkStatus, int i, EnumSet<Heightmap.Types> enumSet, ChunkStatus.ChunkType chunkType, ChunkStatus.SimpleGenerationTask simpleGenerationTask) {
        return null;
    }

}