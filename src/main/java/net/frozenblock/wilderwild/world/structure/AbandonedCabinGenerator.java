package net.frozenblock.wilderwild.world.structure;

import com.mojang.datafixers.util.Either;
import java.util.function.Function;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
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

	public static ResourceKey<StructureTemplatePool> createKey(String string) {
		return ResourceKey.create(Registries.TEMPLATE_POOL, WilderSharedConstants.id(string));
	}
}
