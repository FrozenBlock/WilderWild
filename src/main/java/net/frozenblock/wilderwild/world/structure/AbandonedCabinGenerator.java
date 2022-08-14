package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.AncientCityOutskirtsGenerator;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.function.Function;

public class AbandonedCabinGenerator {

    public static final RegistryEntry<StructurePool> CITY_CENTER = StructurePools.register(
            new StructurePool(
                    WilderWild.id("abandoned_cabin/cabin_center"),
                    new Identifier("empty"),
                    ImmutableList.of(
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin_center/city_center_1", StructureProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1),
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin_center/city_center_2", StructureProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1),
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin_center/city_center_3", StructureProcessorLists.ANCIENT_CITY_START_DEGRADATION), 1)
                    ),
                    StructurePool.Projection.RIGID
            )
    );

    public static Function<StructurePool.Projection, SinglePoolElement> ofProcessedSingle(String id, RegistryEntry<StructureProcessorList> processorListEntry) {
        return projection -> new SinglePoolElement(Either.left(WilderWild.id(id)), processorListEntry, projection);
    }

    public static void init() {
        AncientCityOutskirtsGenerator.init();
    }
}
