package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.*;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.item.AlgaeItem;
import net.frozenblock.wilderwild.item.FloweredLilyPadItem;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.mixin.server.SignTypeAccessor;
import net.frozenblock.wilderwild.world.gen.sapling.BaobabSaplingGenerator;
import net.frozenblock.wilderwild.world.gen.sapling.CypressSaplingGenerator;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterItems.BAOBAB_SIGN;
import static net.frozenblock.wilderwild.registry.RegisterItems.CYPRESS_SIGN;

public class RegisterBlocks {
    private static final MapColor BAOBAB_PLANKS_COLOR = MapColor.ORANGE;
    private static final MapColor BAOBAB_BARK_COLOR = MapColor.BROWN;
    private static final MapColor CYPRESS_PLANKS_COLOR = MapColor.LIGHT_GRAY;
    private static final MapColor CYPRESS_BARK_COLOR = MapColor.STONE_GRAY;

    // OTHER (BUILDING BLOCKS)
    public static final Block CHISELED_MUD_BRICKS = new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS).strength(1.5F).requiresTool().sounds(BlockSoundGroup.MUD_BRICKS));

    public static void registerOtherBB() {
        // BB = Building Blocks
        registerBlock("chiseled_mud_bricks", CHISELED_MUD_BRICKS, ItemGroup.BUILDING_BLOCKS);
    }

    // WOOD
    public static final Block BAOBAB_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block BAOBAB_LOG = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_LOG = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block STRIPPED_BAOBAB_LOG = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_CYPRESS_LOG = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block STRIPPED_BAOBAB_WOOD = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_CYPRESS_WOOD = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block BAOBAB_WOOD = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_WOOD = new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block BAOBAB_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block BAOBAB_STAIRS = new StairsBlock(BAOBAB_PLANKS.getDefaultState(), FabricBlockSettings.copy(BAOBAB_PLANKS));
    public static final Block CYPRESS_STAIRS = new StairsBlock(CYPRESS_PLANKS.getDefaultState(), FabricBlockSettings.copy(CYPRESS_PLANKS));

    public static final Block BAOBAB_BUTTON = new WoodenButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON).mapColor(BAOBAB_PLANKS_COLOR));
    public static final Block CYPRESS_BUTTON = new WoodenButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON).mapColor(CYPRESS_PLANKS_COLOR));

    public static final Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));

    public static final Block BAOBAB_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());
    public static final Block CYPRESS_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block BAOBAB_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));
    public static final Block CYPRESS_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block BAOBAB_SAPLING = new SaplingBlock(new BaobabSaplingGenerator(), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block POTTED_BAOBAB_SAPLING = new FlowerPotBlock(RegisterBlocks.BAOBAB_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block CYPRESS_SAPLING = new WaterloggableSaplingBlock(new CypressSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    public static final Block POTTED_CYPRESS_SAPLING = new FlowerPotBlock(RegisterBlocks.CYPRESS_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static final Block BAOBAB_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.GREEN).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block CYPRESS_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.GREEN).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block CYPRESS_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

    public static final SignType BAOBAB_SIGN_TYPE = SignTypeAccessor.newSignType("baobab");
    public static final Block BAOBAB_SIGN_BLOCK = new WilderSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), BAOBAB_SIGN_TYPE);
    public static final Block BAOBAB_WALL_SIGN = new WilderWallSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(BAOBAB_SIGN_BLOCK), BAOBAB_SIGN_TYPE);

    public static final SignType CYPRESS_SIGN_TYPE = SignTypeAccessor.newSignType("cypress");
    public static final Block CYPRESS_SIGN_BLOCK = new WilderSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), CYPRESS_SIGN_TYPE);
    public static final Block CYPRESS_WALL_SIGN = new WilderWallSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.getDefaultMapColor()).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(CYPRESS_SIGN_BLOCK), CYPRESS_SIGN_TYPE);

    public static void registerWoods() {
        String baobab = "baobab";
        String cypress = "cypress";

        SignTypeAccessor.registerNew(BAOBAB_SIGN_TYPE);
        SignTypeAccessor.registerNew(CYPRESS_SIGN_TYPE);

        registerBlock(baobab + "_planks", BAOBAB_PLANKS, ItemGroup.BUILDING_BLOCKS);
        registerBlock(cypress + "_planks", CYPRESS_PLANKS, ItemGroup.BUILDING_BLOCKS);

        registerBlock(baobab + "_log", BAOBAB_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock(cypress + "_log", CYPRESS_LOG, ItemGroup.BUILDING_BLOCKS);

        registerBlock("stripped_" + baobab + "_log", STRIPPED_BAOBAB_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock("stripped_" + cypress + "_log", STRIPPED_CYPRESS_LOG, ItemGroup.BUILDING_BLOCKS);

        registerBlock("stripped_" + baobab + "_wood", STRIPPED_BAOBAB_WOOD, ItemGroup.BUILDING_BLOCKS);
        registerBlock("stripped_" + cypress + "_wood", STRIPPED_CYPRESS_WOOD, ItemGroup.BUILDING_BLOCKS);

        registerBlock(baobab + "_wood", BAOBAB_WOOD, ItemGroup.BUILDING_BLOCKS);
        registerBlock(cypress + "_wood", CYPRESS_WOOD, ItemGroup.BUILDING_BLOCKS);

        registerBlock(baobab + "_slab", BAOBAB_SLAB, ItemGroup.BUILDING_BLOCKS);
        registerBlock(cypress + "_slab", CYPRESS_SLAB, ItemGroup.BUILDING_BLOCKS);

        registerBlock(baobab + "_stairs", BAOBAB_STAIRS, ItemGroup.BUILDING_BLOCKS);
        registerBlock(cypress + "_stairs", CYPRESS_STAIRS, ItemGroup.BUILDING_BLOCKS);

        registerBlock(baobab + "_button", BAOBAB_BUTTON, ItemGroup.REDSTONE);
        registerBlock(cypress + "_button", CYPRESS_BUTTON, ItemGroup.REDSTONE);

        registerBlock(baobab + "_pressure_plate", BAOBAB_PRESSURE_PLATE, ItemGroup.REDSTONE);
        registerBlock(cypress + "_pressure_plate", CYPRESS_PRESSURE_PLATE, ItemGroup.REDSTONE);

        registerBlock(baobab + "_door", BAOBAB_DOOR, ItemGroup.REDSTONE);
        registerBlock(cypress + "_door", CYPRESS_DOOR, ItemGroup.REDSTONE);

        registerBlock(baobab + "_trapdoor", BAOBAB_TRAPDOOR, ItemGroup.REDSTONE);
        registerBlock(cypress + "_trapdoor", CYPRESS_TRAPDOOR, ItemGroup.REDSTONE);

        registerBlock(baobab + "_fence_gate", BAOBAB_FENCE_GATE, ItemGroup.REDSTONE);
        registerBlock(cypress + "_fence_gate", CYPRESS_FENCE_GATE, ItemGroup.REDSTONE);

        registerBlock(baobab + "_sapling", BAOBAB_SAPLING, ItemGroup.DECORATIONS);
        registerBlockWithoutBlockItem("potted_" + baobab + "_sapling", POTTED_BAOBAB_SAPLING);
        registerBlock(cypress + "_sapling", CYPRESS_SAPLING, ItemGroup.DECORATIONS);
        registerBlockWithoutBlockItem("potted_" + cypress + "_sapling", POTTED_CYPRESS_SAPLING);

        registerBlock(baobab + "_leaves", BAOBAB_LEAVES, ItemGroup.DECORATIONS);
        registerBlock(cypress + "_leaves", CYPRESS_LEAVES, ItemGroup.DECORATIONS);

        registerBlock(baobab + "_fence", BAOBAB_FENCE, ItemGroup.DECORATIONS);
        registerBlock(cypress + "_fence", CYPRESS_FENCE, ItemGroup.DECORATIONS);

        Registry.register(Registry.ITEM, WilderWild.id("baobab_sign"), BAOBAB_SIGN);
        registerBlockWithoutBlockItem(baobab + "_sign", BAOBAB_SIGN_BLOCK);
        registerBlockWithoutBlockItem(baobab + "_wall_sign", BAOBAB_WALL_SIGN);
        Registry.register(Registry.ITEM, WilderWild.id("cypress_sign"), CYPRESS_SIGN);
        registerBlockWithoutBlockItem(cypress + "_sign", CYPRESS_SIGN_BLOCK);
        registerBlockWithoutBlockItem(cypress + "_wall_sign", CYPRESS_WALL_SIGN);
    }

    // HOLLOWED LOGS
    public static final Block HOLLOWED_OAK_LOG = createHollowedLogBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN);
    public static final Block HOLLOWED_SPRUCE_LOG = createHollowedLogBlock(MapColor.SPRUCE_BROWN, MapColor.BROWN);
    public static final Block HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MapColor.PALE_YELLOW, MapColor.OFF_WHITE);
    public static final Block HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MapColor.DIRT_BROWN, MapColor.SPRUCE_BROWN);
    public static final Block HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MapColor.ORANGE, MapColor.STONE_GRAY);
    public static final Block HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MapColor.BROWN, MapColor.BROWN);
    public static final Block HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MapColor.RED, MapColor.SPRUCE_BROWN);
    public static final Block HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MapColor.ORANGE, MapColor.BROWN);
    public static final Block HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MapColor.LIGHT_GRAY, MapColor.STONE_GRAY);

    public static void registerHollowedLogs() {
        registerBlock("hollowed_oak_log", HOLLOWED_OAK_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_birch_log", HOLLOWED_BIRCH_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_acacia_log", HOLLOWED_ACACIA_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, ItemGroup.DECORATIONS);
        registerBlock("hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, ItemGroup.DECORATIONS);
    }

    // SCULK
    public static final Block SCULK_ECHOER = new SculkEchoerBlock(FabricBlockSettings.of(Material.SCULK, MapColor.CYAN).strength(3.0F, 3.0F).sounds(BlockSoundGroup.SCULK_CATALYST), 8);
    public static final Block SCULK_JAW = new SculkJawBlock(FabricBlockSettings.of(Material.SCULK).strength(0.6F).sounds(BlockSoundGroup.SCULK));
    public static final Block SCULK_STAIRS = new SculkStairsBlock(Blocks.SCULK.getDefaultState(), FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(BlockSoundGroup.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_SLAB = new SculkSlabBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(BlockSoundGroup.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_WALL = new SculkWallBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(BlockSoundGroup.SCULK).dropsLike(Blocks.SCULK));
    public static final Block OSSEOUS_SCULK = new OsseousSculkBlock(FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(2.0F).sounds(RegisterBlockSoundGroups.OSSEOUS_SCULK));
    public static final Block HANGING_TENDRIL = new HangingTendrilBlock(FabricBlockSettings.copyOf(Blocks.SCULK_SENSOR).strength(0.7F).collidable(false).luminance((state) -> 1)
            .sounds(RegisterBlockSoundGroups.HANGING_TENDRIL).emissiveLighting((state, world, pos) -> HangingTendrilBlock.shouldHavePogLighting(state)));
    public static final Block ECHO_GLASS = new EchoGlassBlock(FabricBlockSettings.of(Material.GLASS, MapColor.CYAN).strength(0.3F).nonOpaque().sounds(RegisterBlockSoundGroups.ECHO_GLASS));

    public static void registerDeepDark() {
        registerBlock("sculk_echoer", SCULK_ECHOER, ItemGroup.DECORATIONS);
        registerBlock("sculk_jaw", SCULK_JAW, ItemGroup.DECORATIONS);
        registerBlock("sculk_stairs", SCULK_STAIRS, ItemGroup.DECORATIONS);
        registerBlock("sculk_slab", SCULK_SLAB, ItemGroup.DECORATIONS);
        registerBlock("sculk_wall", SCULK_WALL, ItemGroup.DECORATIONS);
        registerBlock("osseous_sculk", OSSEOUS_SCULK, ItemGroup.DECORATIONS);
        registerBlock("hanging_tendril", HANGING_TENDRIL, ItemGroup.DECORATIONS);
        registerBlock("echo_glass", ECHO_GLASS, ItemGroup.DECORATIONS);
    }

    // MISC
    private static final Material ALGAE_MATERIAL = new FabricMaterialBuilder(MapColor.DARK_GREEN)
            .allowsMovement()
            .lightPassesThrough()
            .notSolid()
            .destroyedByPiston()
            .build();

    public static final Block TERMITE_MOUND = new TermiteMound(FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(0.3F).sounds(RegisterBlockSoundGroups.COARSEDIRT));

    // PLANTS
    public static final Block SEEDING_DANDELION = new SeedingDandelionBlock(StatusEffects.SLOW_FALLING, 12, FabricBlockSettings.copy(Blocks.DANDELION).sounds(BlockSoundGroup.SPORE_BLOSSOM).strength(0.0F).nonOpaque());
    public static final Block POTTED_SEEDING_DANDELION = new FlowerPotBlock(RegisterBlocks.SEEDING_DANDELION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block CARNATION = new FlowerBlock(StatusEffects.REGENERATION, 12, FabricBlockSettings.copy(Blocks.DANDELION).sounds(BlockSoundGroup.SPORE_BLOSSOM).strength(0.0F).nonOpaque());
    public static final Block POTTED_CARNATION = new FlowerPotBlock(RegisterBlocks.CARNATION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(FabricBlockSettings.copy(Blocks.DANDELION).sounds(BlockSoundGroup.SPORE_BLOSSOM).strength(0.0F).nonOpaque().ticksRandomly(), List.of(FlowerColors.BLUE, FlowerColors.PINK, FlowerColors.PURPLE, FlowerColors.WHITE));

    public static final Block WHITE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.OFF_WHITE).sounds(BlockSoundGroup.VINE));
    public static final Block PINK_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.DULL_PINK).sounds(BlockSoundGroup.VINE));
    public static final Block PURPLE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.VINE));
    public static final Block BLUE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.BLUE).sounds(BlockSoundGroup.VINE));

    public static final Block DATURA = new TallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0F).nonOpaque());
    public static final Block MILKWEED = new MilkweedBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0F).nonOpaque());

    public static final Block CATTAIL = new WaterloggableTallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).sounds(BlockSoundGroup.WET_GRASS).strength(0.0F).nonOpaque());
    public static final Block FLOWERING_LILY_PAD = new FloweringLilyPadBlock(FabricBlockSettings.copy(Blocks.LILY_PAD).sounds(RegisterBlockSoundGroups.LILYPAD));

    public static final Block ALGAE = new AlgaeBlock(FabricBlockSettings.of(ALGAE_MATERIAL).breakInstantly().velocityMultiplier(0.6F).nonOpaque().noCollision().sounds(BlockSoundGroup.SLIME));

    public static void registerPlants() {
        registerBlock("seeding_dandelion", SEEDING_DANDELION, ItemGroup.DECORATIONS);
        registerBlockWithoutBlockItem("potted_seeding_dandelion", POTTED_SEEDING_DANDELION);
        registerBlock("carnation", CARNATION, ItemGroup.DECORATIONS);
        registerBlockWithoutBlockItem("potted_carnation", POTTED_CARNATION);
        registerBlock("glory_of_the_snow", GLORY_OF_THE_SNOW, ItemGroup.DECORATIONS);
        registerBlock("blue_giant_glory_of_the_snow", BLUE_GLORY_OF_THE_SNOW, ItemGroup.DECORATIONS);
        registerBlock("pink_giant_glory_of_the_snow", PINK_GLORY_OF_THE_SNOW, ItemGroup.DECORATIONS);
        registerBlock("violet_beauty_glory_of_the_snow", PURPLE_GLORY_OF_THE_SNOW, ItemGroup.DECORATIONS);
        registerBlock("alba_glory_of_the_snow", WHITE_GLORY_OF_THE_SNOW, ItemGroup.DECORATIONS);
        registerBlock("datura", DATURA, ItemGroup.DECORATIONS);
        registerBlock("milkweed", MILKWEED, ItemGroup.DECORATIONS);
        registerBlock("cattail", CATTAIL, ItemGroup.DECORATIONS);

    }

    public static final Block BROWN_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance(1).collidable(false).nonOpaque().sounds(RegisterBlockSoundGroups.MUSHROOM));
    public static final Block RED_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).collidable(false).nonOpaque().sounds(RegisterBlockSoundGroups.MUSHROOM));
    public static final Block POLLEN_BLOCK = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(AbstractBlock.OffsetType.NONE).mapColor(MapColor.PALE_YELLOW).sounds(BlockSoundGroup.VINE));

    public static void registerNotSoPlants() {
        registerBlockWithoutBlockItem("pollen", POLLEN_BLOCK);
        registerBlock("brown_shelf_fungus", BROWN_SHELF_FUNGUS, ItemGroup.DECORATIONS);
        registerBlock("red_shelf_fungus", RED_SHELF_FUNGUS, ItemGroup.DECORATIONS);
    }

    // BLOCK FAMILIES
    public static final BlockFamily BAOBAB = BlockFamilies.register(BAOBAB_PLANKS)
            .button(BAOBAB_BUTTON)
            .slab(BAOBAB_SLAB)
            .stairs(BAOBAB_STAIRS)
            .fence(BAOBAB_FENCE)
            .fenceGate(BAOBAB_FENCE_GATE)
            .pressurePlate(BAOBAB_PRESSURE_PLATE)
            .sign(BAOBAB_SIGN_BLOCK, BAOBAB_WALL_SIGN)
            .door(BAOBAB_DOOR)
            .trapdoor(BAOBAB_TRAPDOOR)
            .group("wooden")
            .unlockCriterionName("has_planks")
            .build();


    public static final BlockFamily CYPRESS = BlockFamilies.register(CYPRESS_PLANKS)
            .button(CYPRESS_BUTTON)
            .slab(CYPRESS_SLAB)
            .stairs(CYPRESS_STAIRS)
            .fence(CYPRESS_FENCE)
            .fenceGate(CYPRESS_FENCE_GATE)
            .pressurePlate(CYPRESS_PRESSURE_PLATE)
            .sign(CYPRESS_SIGN_BLOCK, CYPRESS_WALL_SIGN)
            .door(CYPRESS_DOOR)
            .trapdoor(CYPRESS_TRAPDOOR)
            .group("wooden")
            .unlockCriterionName("has_planks")
            .build();

    // HELLO EVERYBODY
    // hi - treetrain
    public static void registerBlocks() {
        WilderWild.logWild("Registering Blocks for", WilderWild.UNSTABLE_LOGGING);

        registerOtherBB();
        registerWoods();
        registerHollowedLogs();
        registerDeepDark();
        registerBlock("termite_mound", TERMITE_MOUND, ItemGroup.DECORATIONS);
        registerPlants();
        Registry.register(Registry.BLOCK, WilderWild.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
        Registry.register(Registry.ITEM, WilderWild.id("flowering_lily_pad"), new FloweredLilyPadItem(FLOWERING_LILY_PAD, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.BLOCK, WilderWild.id("algae"), ALGAE);
        Registry.register(Registry.ITEM, WilderWild.id("algae"), new AlgaeItem(ALGAE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        registerNotSoPlants();

        registerComposting();
        registerFlammability();
        registerFuels();
    }

    private static void registerBlockWithoutBlockItem(String name, Block block) {
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlockItem(String name, Block block, ItemGroup group) {
        Registry.register(Registry.ITEM, WilderWild.id(name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    private static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new HollowedLogBlock(FabricBlockSettings.of(Material.WOOD,
                        (state) -> state.get(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0F).sounds(RegisterBlockSoundGroups.HOLLOWED_LOG));
    }

    public static void addBaobab() {
        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_LOG, HOLLOWED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_LOG, HOLLOWED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
    }

    protected static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    protected static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static void registerComposting() {
        CompostingChanceRegistry.INSTANCE.add(CARNATION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CATTAIL, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(DATURA, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(MILKWEED, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RegisterItems.MILKWEED_POD, 0.25F);
        CompostingChanceRegistry.INSTANCE.add(SEEDING_DANDELION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(FLOWERING_LILY_PAD, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BROWN_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RED_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_SAPLING, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BLUE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(WHITE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PINK_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PURPLE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ALGAE, 0.3F);
    }

    private static void registerFlammability() {
        WilderWild.logWild("Registering Flammability for", WilderWild.UNSTABLE_LOGGING);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.POLLEN_BLOCK, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.SEEDING_DANDELION, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CARNATION, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CATTAIL, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.DATURA, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.MILKWEED, 100, 60);

        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_BIRCH_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_OAK_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_ACACIA_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_JUNGLE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_MANGROVE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_SPRUCE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.STRIPPED_BAOBAB_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.STRIPPED_BAOBAB_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_TRAPDOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_LEAVES, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_SIGN_BLOCK, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.BAOBAB_WALL_SIGN, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.HOLLOWED_CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.STRIPPED_CYPRESS_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.STRIPPED_CYPRESS_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_TRAPDOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_LEAVES, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_BUTTON, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_SIGN_BLOCK, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(RegisterBlocks.CYPRESS_WALL_SIGN, 5, 20);
    }

    private static void registerFuels() {
        WilderWild.logWild("Registering Fuels for", WilderWild.UNSTABLE_LOGGING);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(BAOBAB_FENCE.asItem(), 300);
        registry.add(BAOBAB_FENCE_GATE.asItem(), 300);
        registry.add(CYPRESS_FENCE.asItem(), 300);
        registry.add(CYPRESS_FENCE_GATE.asItem(), 300);
    }
}

