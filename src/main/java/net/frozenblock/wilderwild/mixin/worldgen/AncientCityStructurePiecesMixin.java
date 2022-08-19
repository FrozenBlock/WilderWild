package net.frozenblock.wilderwild.mixin.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.AncientCityStructurePieces;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(AncientCityStructurePieces.class)
public class AncientCityStructurePiecesMixin {

    @Shadow
    @Final
    @Mutable
    private static Holder<StructureTemplatePool> START = Pools.register(new StructureTemplatePool(WilderWild.id("ancient_city/city_center"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(single("ancient_city/city_center/city_center_1", ProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1), Pair.of(single("ancient_city/city_center/city_center_2", ProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1), Pair.of(single("ancient_city/city_center/city_center_3", ProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1)), StructureTemplatePool.Projection.RIGID));

    @Inject(method = "<clinit>", at = @At("HEAD"), cancellable = true)
    private static void name(CallbackInfo info) {
        info.cancel();
        START = Pools.register(new StructureTemplatePool(WilderWild.id("ancient_city/city_center"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(single("ancient_city/city_center/city_center_1", ProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1), Pair.of(single("ancient_city/city_center/city_center_2", ProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1), Pair.of(single("ancient_city/city_center/city_center_3", ProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1)), StructureTemplatePool.Projection.RIGID));
    }


    private static Function<StructureTemplatePool.Projection, SinglePoolElement> single(String id, Holder<StructureProcessorList> processors) {
        return projection -> new SinglePoolElement(Either.left(WilderWild.id(id)), processors, projection);
    }

    private static Function<StructureTemplatePool.Projection, SinglePoolElement> single(String id) {
        return projection -> new SinglePoolElement(Either.left(WilderWild.id(id)), ProcessorLists.EMPTY, projection);
    }
    
}