package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
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

    public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		register(
				entries,
				ABANDONED_CABIN,
				ImmutableList.of(
						new RuleProcessor(
								ImmutableList.of(
										new ProcessorRule(
												new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()
										),
										new ProcessorRule(
												new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()
										),
										new ProcessorRule(new RandomBlockMatchTest(Blocks.SOUL_LANTERN, 0.05F), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState())
								)
						),
						new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
				)
		);
	}

    public static void init() {
    }

	private static ResourceKey<StructureProcessorList> createKey(String string) {
		return ResourceKey.create(Registry.PROCESSOR_LIST_REGISTRY, WilderSharedConstants.id(string));
	}

	private static void register(
			FabricWorldgenProvider.Entries entries, ResourceKey<StructureProcessorList> registryKey, List<StructureProcessor> list
	) {
		entries.add(registryKey, new StructureProcessorList(list));
	}
}
