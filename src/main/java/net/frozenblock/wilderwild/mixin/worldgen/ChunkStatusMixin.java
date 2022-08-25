package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.EnumSet;
import java.util.Optional;

import static net.minecraft.world.level.chunk.ChunkStatus.STRUCTURE_STARTS;

@Mixin(ChunkStatus.class)
public class ChunkStatusMixin {

    @Shadow
    private static final EnumSet<Heightmap.Types> PRE_FEATURES = EnumSet.of(Heightmap.Types.OCEAN_FLOOR_WG, Heightmap.Types.WORLD_SURFACE_WG);


    @Shadow @Final @Mutable
    public static ChunkStatus STRUCTURE_REFERENCES = registerSimple("structure_references", STRUCTURE_STARTS, 16, PRE_FEATURES, ChunkStatus.ChunkType.PROTOCHUNK, (chunkStatus, serverLevel, chunkGenerator, list, chunkAccess) -> {
        WorldGenRegion worldGenRegion = new WorldGenRegion(serverLevel, list, chunkStatus, -1);
        chunkGenerator.createReferences(worldGenRegion, serverLevel.structureManager().forWorldGenRegion(worldGenRegion), chunkAccess);
    });

    @Inject(method = "<clinit>", at = @At("TAIL")) {
        STRUCTURE_REFERENCES = registerSimple("structure_references", STRUCTURE_STARTS, 16, PRE_FEATURES, ChunkStatus.ChunkType.PROTOCHUNK, (chunkStatus, serverLevel, chunkGenerator, list, chunkAccess) -> {
            WorldGenRegion worldGenRegion = new WorldGenRegion(serverLevel, list, chunkStatus, -1);
            chunkGenerator.createReferences(worldGenRegion, serverLevel.structureManager().forWorldGenRegion(worldGenRegion), chunkAccess);
        });
    }

    @Shadow
    private static ChunkStatus registerSimple(String string, @Nullable ChunkStatus chunkStatus, int i, EnumSet<Heightmap.Types> enumSet, ChunkStatus.ChunkType chunkType, ChunkStatus.SimpleGenerationTask simpleGenerationTask) {
        return null;
    }

}