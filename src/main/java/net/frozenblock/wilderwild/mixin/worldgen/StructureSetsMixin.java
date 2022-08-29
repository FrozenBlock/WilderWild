package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureSets.class)
public interface StructureSetsMixin {

    @Shadow
    static Holder<StructureSet> register(ResourceKey<StructureSet> resourceKey, StructureSet structureSet) {
        return null;
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/worldgen/StructureSets;register(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/Holder;Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement;)Lnet/minecraft/core/Holder;", ordinal = 5))
    private static Holder<StructureSet> newAncientCity(ResourceKey<StructureSet> resourceKey, Holder<Structure> holder, StructurePlacement structurePlacement) {
        return register(BuiltinStructureSets.ANCIENT_CITIES, new StructureSet(Structures.ANCIENT_CITY, new RandomSpreadStructurePlacement(35, 24, RandomSpreadType.LINEAR, 20083232)));
    }
}
