package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Function;

@Mixin(JigsawStructure.class)
public class JigsawStructureMixin {
    @Shadow @Final @Mutable
    private static int MAX_TOTAL_STRUCTURE_RANGE;
    @Shadow @Final @Mutable
    private static Codec<JigsawStructure> CODEC;

    @Shadow @Final
    private Holder<StructureTemplatePool> startPool;
    @Shadow @Final
    private Optional<ResourceLocation> startJigsawName;
    @Shadow @Final
    private int maxDepth;
    @Shadow @Final
    private HeightProvider startHeight;
    @Shadow @Final
    private boolean useExpansionHack;
    @Shadow @Final
    private Optional<Heightmap.Types> projectStartToHeightmap;
    @Shadow @Final
    private int maxDistanceFromCenter;

    @Inject(method = "<clinit>", at = @At("HEAD"))
    public void classInit(CallbackInfo info) {
        MAX_TOTAL_STRUCTURE_RANGE = 256;
        Codec<Object> structureCodec = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(Structure.StructureSettings.CODEC.forGetter(structure -> ((Structure)structure).settings), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((jigsawStructure) -> {
                return ((JigsawStructure)jigsawStructure).startPool;
            }), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((jigsawStructure) -> {
                return ((JigsawStructure)jigsawStructure).startJigsawName;
            }), Codec.intRange(0, 7).fieldOf("size").forGetter((jigsawStructure) -> {
                return ((JigsawStructure)jigsawStructure).maxDepth;
            }), HeightProvider.CODEC.fieldOf("start_height").forGetter((jigsawStructure) -> {
                return ((JigsawStructure)jigsawStructure).startHeight;
            }), Codec.BOOL.fieldOf("use_expansion_hack").forGetter((jigsawStructure) -> {
                return ((JigsawStructure)jigsawStructure).useExpansionHack;
            }), Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((jigsawStructure) -> {
                return ((JigsawStructure)jigsawStructure).projectStartToHeightmap;
            }), Codec.intRange(1, 256).fieldOf("max_distance_from_center").forGetter((jigsawStructure) -> {
                return ((JigsawStructure)jigsawStructure).maxDistanceFromCenter;
            })).apply(instance, JigsawStructure::new);
        }).codec();

        CODEC = Codec.class.cast(structureCodec);

    }


    private static Function<JigsawStructure, DataResult<JigsawStructure>> verifyRange() {
        return (jigsawStructure) -> {

            int i = switch (jigsawStructure.terrainAdaptation()) {
                case NONE -> 0;
                case BURY, BEARD_THIN, BEARD_BOX -> 12;
            };
            return jigsawStructure.maxDistanceFromCenter + i > 256 ? DataResult.error("Structure size including terrain adaptation must not exceed 256") : DataResult.success(jigsawStructure);
        };
    }
}