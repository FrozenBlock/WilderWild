package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.function.Function;

public class AbandonedCabinGenerator {

    public static final Holder<StructureTemplatePool> CABIN = Pools.register(
            new StructureTemplatePool(
                    WilderWild.id("abandoned_cabin/cabin"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_1", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1),
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_2", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1),
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_3", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1)
                    ),
                    StructureTemplatePool.Projection.RIGID
            )
    );

    public static Function<StructureTemplatePool.Projection, SinglePoolElement> ofProcessedSingle(String id, Holder<StructureProcessorList> processorListEntry) {
        return projection -> new SinglePoolElement(Either.left(WilderWild.id(id)), processorListEntry, projection);
    }

    public static void init() {
    }
}
