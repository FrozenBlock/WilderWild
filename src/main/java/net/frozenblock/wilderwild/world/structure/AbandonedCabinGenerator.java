package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.AncientCityStructurePools;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

/**
 * Contains the StructureTemplatePool for Abandoned Cabins
 */
public class AbandonedCabinGenerator {

	public static final ResourceKey<StructureTemplatePool> CABIN = createKey("abandoned_cabin/cabin");

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		HolderGetter<StructureTemplatePool> holderGetter2 = entries.getLookup(Registry.TEMPLATE_POOL_REGISTRY);
		Holder<StructureTemplatePool> holder2 = holderGetter2.getOrThrow(Pools.EMPTY);
		entries.add(
				CABIN,
				new StructureTemplatePool(
						holder2,
						ImmutableList.of(
								Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_1", WilderStructureProcessors.ABANDONED_CABIN), 1),
								Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_2", WilderStructureProcessors.ABANDONED_CABIN), 1),
								Pair.of(ofProcessedSingle("abandoned_cabin/cabin/abandoned_cabin_3", WilderStructureProcessors.ABANDONED_CABIN), 1)
						),
						StructureTemplatePool.Projection.RIGID
				)
		);
	}

    /**
     * @param id                 The id for the {@link SinglePoolElement}'s {@link ResourceLocation}
     * @param processorListEntry The processor list for the {@link SinglePoolElement}
     * @return A {@link SinglePoolElement} of the parameters given.
     */
    public static Function<StructureTemplatePool.Projection, SinglePoolElement> ofProcessedSingle(String id, ResourceKey<StructureProcessorList> processorListEntry) {
		final var registry = VanillaRegistries.createLookup().lookupOrThrow(Registry.PROCESSOR_LIST_REGISTRY);
        return projection -> new SinglePoolElement(Either.left(WilderSharedConstants.id(id)), registry.getOrThrow(processorListEntry), projection);
    }

    /**
     * Initializes this class to register the {@link StructureTemplatePool}s
     */
    public static void init() {
    }

	public static ResourceKey<StructureTemplatePool> createKey(String string) {
		return ResourceKey.create(Registry.TEMPLATE_POOL_REGISTRY, WilderSharedConstants.id(string));
	}
}
