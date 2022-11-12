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
		return ResourceKey.create(Registry.TEMPLATE_POOL_REGISTRY, WilderSharedConstants.id(string));
	}
}
