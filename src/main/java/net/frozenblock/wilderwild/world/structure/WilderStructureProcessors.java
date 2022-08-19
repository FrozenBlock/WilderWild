package net.frozenblock.wilderwild.world.structure;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

public class WilderStructureProcessors {

    public static final Holder<StructureProcessorList> ABANDONED_CABIN_PROCESSOR = registerProcessorList(
            "abandoned_cabin",
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

    public static final Holder<StructureProcessorList> CHEST_TO_STONE = registerProcessorList("chests_to_stone", ImmutableList.of(new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)),
            new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)),
            new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)),
            new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST))))));

    public static final Holder<StructureProcessorList> CHEST_TO_STONE_GENERIC = registerProcessorList("chests_to_stone_generic", ImmutableList.of(new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)),
            new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)),
            new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)),
            new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)),
            new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()),
                    new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()),
                    new ProcessorRule(new RandomBlockMatchTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()))),
            new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));

    public static final Holder<StructureProcessorList> CHEST_TO_STONE_WALLS = registerProcessorList("chests_to_stone_walls", ImmutableList.of(new RuleProcessor(ImmutableList.of(
                    new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)),
                    new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)),
                    new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)),
                    new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)))),
            new BlockRotProcessor(BlockTags.ANCIENT_CITY_REPLACEABLE, 0.95f), new RuleProcessor(ImmutableList.of(new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()), new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()), new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3f), AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()), new ProcessorRule(new RandomBlockMatchTest(Blocks.SOUL_LANTERN, 0.05f),
                    AlwaysTrueTest.INSTANCE, Blocks.AIR.defaultBlockState()))), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));

    public static final Holder<StructureProcessorList> CHEST_TO_STONE_START = registerProcessorList("chests_to_stone_start", ImmutableList.of(new RuleProcessor(ImmutableList.of(
                    new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)),
                    new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)),
                    new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)),
                    new ProcessorRule(new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)), AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)),
            new ProcessorRule(new BlockMatchTest(Blocks.COAL_ORE), AlwaysTrueTest.INSTANCE, Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState()))), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));

    private static Holder<StructureProcessorList> registerProcessorList(String id, ImmutableList<StructureProcessor> processorList) {
        ResourceLocation identifier = WilderWild.id(id);
        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);
        return BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, identifier, structureProcessorList);
    }

    public static void init() {
    }
}
