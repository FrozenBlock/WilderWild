package net.frozenblock.wilderwild.world.additions.structure;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class WilderStructureProcessors {

	public static final ResourceKey<StructureProcessorList> ABANDONED_CABIN = createKey("abandoned_cabin");

    public static void init() {
    }

	private static ResourceKey<StructureProcessorList> createKey(String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, WilderSharedConstants.id(string));
	}
}
