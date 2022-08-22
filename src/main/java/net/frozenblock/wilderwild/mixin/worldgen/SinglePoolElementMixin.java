package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.datafixers.util.Either;
import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.grower.BirchTreeGrower;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SinglePoolElement.class)
public class SinglePoolElementMixin {

    @Shadow @Final
    public Either<ResourceLocation, StructureTemplate> template;

    /*public boolean place(StructureTemplateManager structureTemplateManager, WorldGenLevel world, StructureManager structureManager, ChunkGenerator chunkGenerator, BlockPos pos, BlockPos pivot, Rotation rotation, BoundingBox box, RandomSource random, boolean keepJigsaws) {

        StructurePlaceSettings structurePlaceSettings;
        StructureTemplate structureTemplate = this.getTemplate(structureTemplateManager);
        if (structureTemplate.placeInWorld(world, pos, pivot, structurePlaceSettings = this.getSettings(rotation, box, keepJigsaws), random, 18)) {
            List<StructureTemplate.StructureBlockInfo> list = StructureTemplate.processBlockInfos(world, pos, pivot, structurePlaceSettings, this.getDataMarkers(structureTemplateManager, pos, rotation, false));
            for (StructureTemplate.StructureBlockInfo structureBlockInfo : list) {
                this.handleDataMarker(world, structureBlockInfo, pos, rotation, random, box);
            }
            return true;
        }
        return false;
    }*/


}