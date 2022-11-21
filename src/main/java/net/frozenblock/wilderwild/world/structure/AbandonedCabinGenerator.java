package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;
import net.frozenblock.wilderwild.WilderWild;
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
					WilderWild.id("abandoned_cabin/cabin"),
					WilderWild.vanillaId("empty"),
					ImmutableList.of(
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
		return projection -> new SinglePoolElement(Either.left(WilderWild.id(id)), processorListEntry, projection);
	}

	/**
	 * Initializes this class to register the {@link StructureTemplatePool}s
	 */
	public static void init() {
	}
}
