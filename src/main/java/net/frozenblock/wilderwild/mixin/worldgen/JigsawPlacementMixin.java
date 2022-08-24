package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(JigsawPlacement.class)
public class JigsawPlacementMixin {

    @Shadow
    public static Optional<Structure.GenerationStub> addPieces(Structure.GenerationContext generationContext, Holder<StructureTemplatePool> holder, Optional<ResourceLocation> resourceLocation, int i, BlockPos blockPos, boolean bl, Optional<Heightmap.Types> optional2, int j) {
        return Optional.empty();
    }

    @Redirect(method = "generateJigsaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/pools/JigsawPlacement;addPieces(Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;Lnet/minecraft/core/Holder;Ljava/util/Optional;ILnet/minecraft/core/BlockPos;ZLjava/util/Optional;I)Ljava/util/Optional;"))
    private static Optional<Structure.GenerationStub> generateJigsaw(Structure.GenerationContext generationContext, Holder<StructureTemplatePool> holder, Optional<ResourceLocation> resourceLocation, int i, BlockPos blockPos, boolean bl, Optional<Heightmap.Types> optional2, int j) {
        return addPieces(generationContext, holder, resourceLocation, i, blockPos, false, Optional.empty(), RegisterStructures.MAX_DISTANCE_FROM_JIGSAW_CENTER);
    }

    @Inject(method = "addPieces(Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;Lnet/minecraft/core/Holder;Ljava/util/Optional;ILnet/minecraft/core/BlockPos;ZLjava/util/Optional;I)Ljava/util/Optional;", at = @At("HEAD"))
    private static void addPieces(Structure.GenerationContext generationContext, Holder<StructureTemplatePool> holder, Optional<ResourceLocation> optional, int i, BlockPos blockPos, boolean bl, Optional<Heightmap.Types> optional2, int j, CallbackInfoReturnable<Optional<Structure.GenerationStub>> cir) {
        if (j == 128) {
            j = RegisterStructures.MAX_DISTANCE_FROM_JIGSAW_CENTER;
        } else if (j == 256) {
            j = RegisterStructures.MAX_DISTANCE_FROM_JIGSAW_CENTER * 2;
        }
    }
}