package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.data.worldgen.Structures;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = StructureStart.class, priority = 69420)
public class StructureStartMixin {
    @Shadow
    @Mutable
    @Nullable
    private volatile BoundingBox cachedBoundingBox;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void inject(Structure structure, ChunkPos chunkPos, int i, PiecesContainer piecesContainer, CallbackInfo info) {
        if (structure == Structures.ANCIENT_CITY.value()) {
            BoundingBox cachedBoundingBox = this.cachedBoundingBox;
            assert cachedBoundingBox != null;
            this.cachedBoundingBox = cachedBoundingBox.inflatedBy(320);
        }
    }

}