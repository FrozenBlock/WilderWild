package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(JigsawPlacement.class)
public class JigsawPlacementMixin {

    @Inject(method = "generateJigsaw", at = @At("HEAD"), cancellable = true)
    public static void generateJigsaw(ServerLevel serverLevel, Holder<StructureTemplatePool> holder2, ResourceLocation resourceLocation, int i, BlockPos blockPos, boolean bl, CallbackInfoReturnable<Boolean> info) {
        ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
        StructureTemplateManager structureTemplateManager = serverLevel.getStructureManager();
        StructureManager structureManager = serverLevel.structureManager();
        RandomSource randomSource = serverLevel.getRandom();
        Structure.GenerationContext generationContext = new Structure.GenerationContext(serverLevel.registryAccess(), chunkGenerator, chunkGenerator.getBiomeSource(), serverLevel.getChunkSource().randomState(), structureTemplateManager, serverLevel.getSeed(), new ChunkPos(blockPos), serverLevel, holder -> true);
        Optional<Structure.GenerationStub> optional = JigsawPlacement.addPieces(generationContext, holder2, Optional.of(resourceLocation), i, blockPos, false, Optional.empty(), 320);
        if (optional.isPresent()) {
            StructurePiecesBuilder structurePiecesBuilder = optional.get().getPiecesBuilder();
            for (StructurePiece structurePiece : structurePiecesBuilder.build().pieces()) {
                if (!(structurePiece instanceof PoolElementStructurePiece)) continue;
                PoolElementStructurePiece poolElementStructurePiece = (PoolElementStructurePiece)structurePiece;
                poolElementStructurePiece.place(serverLevel, structureManager, chunkGenerator, randomSource, BoundingBox.infinite(), blockPos, bl);
            }
            info.cancel();
            info.setReturnValue(true);
        }
        info.cancel();
        info.setReturnValue(false);
    }

}