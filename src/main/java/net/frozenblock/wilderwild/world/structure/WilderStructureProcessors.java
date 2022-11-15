package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class WilderStructureProcessors {

	public static final ResourceKey<StructureProcessorList> ABANDONED_CABIN = createKey("abandoned_cabin");

    public static void init() {
    }

	private static ResourceKey<StructureProcessorList> createKey(String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, WilderSharedConstants.id(string));
	}
}
