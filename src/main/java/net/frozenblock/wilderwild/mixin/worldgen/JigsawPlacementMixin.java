package net.frozenblock.wilderwild.mixin.worldgen;

import com.google.common.collect.Lists;
import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(JigsawPlacement.class)
public class JigsawPlacementMixin {

    /*@Shadow
    public static Optional<Structure.GenerationStub> addPieces(Structure.GenerationContext generationContext, Holder<StructureTemplatePool> holder, Optional<ResourceLocation> resourceLocation, int i, BlockPos blockPos, boolean bl, Optional<Heightmap.Types> optional2, int j) {
        return Optional.empty();
    }*/

    @Redirect(method = "generateJigsaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/pools/JigsawPlacement;addPieces(Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;Lnet/minecraft/core/Holder;Ljava/util/Optional;ILnet/minecraft/core/BlockPos;ZLjava/util/Optional;I)Ljava/util/Optional;"))
    private static Optional<Structure.GenerationStub> generateJigsaw(Structure.GenerationContext generationContext, Holder<StructureTemplatePool> holder, Optional<ResourceLocation> resourceLocation, int i, BlockPos blockPos, boolean bl, Optional<Heightmap.Types> optional2, int j) {
        return addPieces(generationContext, holder, resourceLocation, i, blockPos, false, Optional.empty(), RegisterStructures.MAX_DISTANCE_FROM_JIGSAW_CENTER);
    }

    //@Inject(method = "addPieces(Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;Lnet/minecraft/core/Holder;Ljava/util/Optional;ILnet/minecraft/core/BlockPos;ZLjava/util/Optional;I)Ljava/util/Optional;", at = @At("HEAD"))
    @Overwrite
    private static Optional<Structure.GenerationStub> addPieces(Structure.GenerationContext generationContext, Holder<StructureTemplatePool> holder, Optional<ResourceLocation> optional, int i, BlockPos blockPos, boolean bl, Optional<Heightmap.Types> optional2, int j) {
        BlockPos blockPos2;
        RegistryAccess registryAccess = generationContext.registryAccess();
        ChunkGenerator chunkGenerator = generationContext.chunkGenerator();
        StructureTemplateManager structureTemplateManager = generationContext.structureTemplateManager();
        LevelHeightAccessor levelHeightAccessor = generationContext.heightAccessor();
        WorldgenRandom worldgenRandom = generationContext.random();
        Registry<StructureTemplatePool> registry = registryAccess.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
        Rotation rotation = Rotation.getRandom(worldgenRandom);
        StructureTemplatePool structureTemplatePool = holder.value();
        StructurePoolElement structurePoolElement = structureTemplatePool.getRandomTemplate(worldgenRandom);
        if (structurePoolElement == EmptyPoolElement.INSTANCE) {
            return Optional.empty();
        }
        if (optional.isPresent()) {
            ResourceLocation resourceLocation = optional.get();
            Optional<BlockPos> optional3 = getRandomNamedJigsaw(structurePoolElement, resourceLocation, blockPos, rotation, structureTemplateManager, worldgenRandom);
            if (optional3.isEmpty()) {
                //LOGGER.error("No starting jigsaw {} found in start pool {}", (Object)resourceLocation, (Object)holder.unwrapKey().get().location());
                return Optional.empty();
            }
            blockPos2 = optional3.get();
        } else {
            blockPos2 = blockPos;
        }
        BlockPos vec3i = blockPos2.subtract(blockPos);
        BlockPos blockPos3 = blockPos.subtract(vec3i);
        PoolElementStructurePiece poolElementStructurePiece = new PoolElementStructurePiece(structureTemplateManager, structurePoolElement, blockPos3, structurePoolElement.getGroundLevelDelta(), rotation, structurePoolElement.getBoundingBox(structureTemplateManager, blockPos3, rotation));
        BoundingBox boundingBox = poolElementStructurePiece.getBoundingBox();
        int k = (boundingBox.maxX() + boundingBox.minX()) / 2;
        int l = (boundingBox.maxZ() + boundingBox.minZ()) / 2;
        int m = optional2.isPresent() ? blockPos.getY() + chunkGenerator.getFirstFreeHeight(k, l, optional2.get(), levelHeightAccessor, generationContext.randomState()) : blockPos3.getY();
        int n = boundingBox.minY() + poolElementStructurePiece.getGroundLevelDelta();
        poolElementStructurePiece.move(0, m - n, 0);
        int o = m + vec3i.getY();
        return Optional.of(new Structure.GenerationStub(new BlockPos(k, o, l), structurePiecesBuilder -> {
            ArrayList<PoolElementStructurePiece> list = Lists.newArrayList();
            list.add(poolElementStructurePiece);
            if (i <= 0) {
                return;
            }
            AABB aABB = new AABB(k - j, o - j, l - j, k + j + 1, o + j + 1, l + j + 1);
            VoxelShape voxelShape = Shapes.join(Shapes.create(aABB), Shapes.create(AABB.of(boundingBox)), BooleanOp.TRUE);
            addPieces(generationContext.randomState(), i, bl, chunkGenerator, structureTemplateManager, levelHeightAccessor, worldgenRandom, registry, poolElementStructurePiece, list, voxelShape);
            list.forEach(structurePiecesBuilder::addPiece);
        }));
    }

    @Shadow
    private static void addPieces(RandomState randomState, int i, boolean bl, ChunkGenerator chunkGenerator, StructureTemplateManager structureTemplateManager, LevelHeightAccessor levelHeightAccessor, RandomSource randomSource, Registry<StructureTemplatePool> registry, PoolElementStructurePiece poolElementStructurePiece, List<PoolElementStructurePiece> list, VoxelShape voxelShape) {

    }

    @Shadow
    private static Optional<BlockPos> getRandomNamedJigsaw(StructurePoolElement structurePoolElement, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotation, StructureTemplateManager structureTemplateManager, WorldgenRandom worldgenRandom) {
        return Optional.empty();
    }

}