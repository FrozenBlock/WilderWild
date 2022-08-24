package net.frozenblock.wilderwild.mixin.worldgen;

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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(Structures.class)
public class StructuresMixin {

    @Shadow @Final @Mutable
    public static Holder<Structure> ANCIENT_CITY;

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static void register(ResourceKey<Structure> resourceKey, Structure structure, CallbackInfoReturnable<Holder<Structure>> info) {
        if (resourceKey == BuiltinStructures.ANCIENT_CITY) {
            info.cancel();
            info.setReturnValue(BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, resourceKey,
                    new JigsawStructure(structure(BiomeTags.HAS_ANCIENT_CITY, Arrays.stream(MobCategory.values()).collect(Collectors.toMap
                            (mobCategory -> mobCategory, mobCategory -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()))),
                    GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.BEARD_BOX),
                    AncientCityStructurePieces.START, Optional.of(new ResourceLocation("city_anchor")),
                    7, ConstantHeight.of(VerticalAnchor.absolute(-27)), true, Optional.empty(), 240))
            );
        }
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