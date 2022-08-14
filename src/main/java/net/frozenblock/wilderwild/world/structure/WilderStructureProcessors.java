package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.Blocks;
import net.minecraft.structure.processor.*;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;

public class WilderStructureProcessors {

    public static final RegistryEntry<StructureProcessorList> ABANDONED_CABIN_PROCESSOR = registerProcessorList(
            "abandoned_cabin",
            ImmutableList.of(
                    new RuleStructureProcessor(
                            ImmutableList.of(
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()
                                    ),
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()
                                    ),
                                    new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05F), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState())
                            )
                    ),
                    new ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
            )
    );

    private static RegistryEntry<StructureProcessorList> registerProcessorList(String id, ImmutableList<StructureProcessor> processorList) {
        Identifier identifier = WilderWild.id(id);
        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);
        return BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, identifier, structureProcessorList);
    }

    public static void init() {
    }
}
