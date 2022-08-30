package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.AncientCityStructurePieces;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(value = Structures.class, priority = 69420)
public class StructuresMixin {

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/worldgen/Structures;register(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/levelgen/structure/Structure;)Lnet/minecraft/core/Holder;", ordinal = 31))
    private static Holder<Structure> newAncientCity(ResourceKey<Structure> resourceKey, Structure structure) {
        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, BuiltinStructures.ANCIENT_CITY, new JigsawStructure(
                structure(
                        BiomeTags.HAS_ANCIENT_CITY,
                        Arrays.stream(MobCategory.values())
                                .collect(
                                        Collectors.toMap(
                                                mobCategory -> mobCategory, mobCategory -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                                        )
                                ),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION,
                        TerrainAdjustment.BEARD_BOX
                ),
                AncientCityStructurePieces.START,
                Optional.of(new ResourceLocation("city_anchor")),
                20,
                ConstantHeight.of(VerticalAnchor.absolute(-27)),
                false,
                Optional.empty(),
                RegisterStructures.MAX_DISTANCE_FROM_JIGSAW_CENTER - 12
        ));
    }

    @Shadow
    private static Structure.StructureSettings structure(TagKey<Biome> tagKey, Map<MobCategory, StructureSpawnOverride> map, GenerationStep.Decoration decoration, TerrainAdjustment terrainAdjustment) {
        return new Structure.StructureSettings(biomes(tagKey), map, decoration, terrainAdjustment);
    }

    @Shadow
    private static HolderSet<Biome> biomes(TagKey<Biome> tagKey) {
        return BuiltinRegistries.BIOME.getOrCreateTag(tagKey);
    }
}