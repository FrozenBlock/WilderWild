package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;

import java.util.function.Function;

public class AbandonedCabinGenerator {

    public static final RegistryEntry<StructurePool> CABIN = StructurePools.register(
            new StructurePool(
                    WilderWild.id("abandoned_cabin/cabin_center"),
                    new Identifier("empty"),
                    ImmutableList.of(
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin_center/city_center_1", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1),
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin_center/city_center_2", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1),
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin_center/city_center_3", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1)
                    ),
                    StructurePool.Projection.RIGID
            )
    );

    public static Function<StructurePool.Projection, SinglePoolElement> ofProcessedSingle(String id, RegistryEntry<StructureProcessorList> processorListEntry) {
        return projection -> new SinglePoolElement(Either.left(WilderWild.id(id)), processorListEntry, projection);
    }

    public static void init() {
    }
}
