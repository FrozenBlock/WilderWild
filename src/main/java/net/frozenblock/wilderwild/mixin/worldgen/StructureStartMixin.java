package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(StructureStart.class)
public class StructureStartMixin {
    @Shadow @Final @Mutable @Nullable
    private volatile BoundingBox cachedBoundingBox;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void inject(Structure structure, ChunkPos chunkPos, int i, PiecesContainer piecesContainer, CallbackInfo info) {
        if (structure == Structures.ANCIENT_CITY.value()) {
            this.cachedBoundingBox = this.cachedBoundingBox.inflatedBy(320);
        }
    }

}