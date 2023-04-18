/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.world.additions.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Function;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

/**
 * Contains the StructureTemplatePool for Abandoned Cabins
 */
public class AbandonedCabinGenerator {

    public static final Holder<StructureTemplatePool> CABIN = Pools.register(
            new StructureTemplatePool(
                    WilderSharedConstants.id("abandoned_cabin/cabin"),
                    WilderSharedConstants.vanillaId("empty"),
                    List.of(
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_1", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1),
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_2", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1),
                            Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_3", WilderStructureProcessors.ABANDONED_CABIN_PROCESSOR), 1)
                    ),
                    StructureTemplatePool.Projection.RIGID
            )
    );

    /**
     * @param id                 The id for the {@link SinglePoolElement}'s {@link ResourceLocation}
     * @param processorListEntry The processor list for the {@link SinglePoolElement}
     * @return A {@link SinglePoolElement} of the parameters given.
     */
    public static Function<StructureTemplatePool.Projection, SinglePoolElement> ofProcessedSingle(String id, Holder<StructureProcessorList> processorListEntry) {
        return projection -> new SinglePoolElement(Either.left(WilderSharedConstants.id(id)), processorListEntry, projection);
    }

    /**
     * Initializes this class to register the {@link StructureTemplatePool}s
     */
    public static void init() {
    }
}
