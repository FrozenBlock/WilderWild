package net.frozenblock.wilderwild.registry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.SculkSensorFrequencyRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.lib.block.api.FrozenSignBlock;
import net.frozenblock.lib.block.api.FrozenWallSignBlock;
import net.frozenblock.lib.block.api.FrozenWoodTypes;
import net.frozenblock.lib.impl.BonemealBehaviors;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.block.BaobabLeaves;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.FlowerLichenBlock;
import net.frozenblock.wilderwild.block.FloweringLilyPadBlock;
import net.frozenblock.wilderwild.block.GloryOfTheSnowBlock;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.block.MilkweedBlock;
import net.frozenblock.wilderwild.block.NematocystBlock;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.block.PollenBlock;
import net.frozenblock.wilderwild.block.SculkEchoerBlock;
import net.frozenblock.wilderwild.block.SculkJawBlock;
import net.frozenblock.wilderwild.block.SculkSlabBlock;
import net.frozenblock.wilderwild.block.SculkStairsBlock;
import net.frozenblock.wilderwild.block.SculkWallBlock;
import net.frozenblock.wilderwild.block.SeedingDandelionBlock;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMound;
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.item.AlgaeItem;
import net.frozenblock.wilderwild.item.FloweredLilyPadItem;
import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.gen.sapling.CypressSaplingGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class RegisterBlocks {
	private RegisterBlocks() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

    private static final MaterialColor BAOBAB_PLANKS_COLOR = MaterialColor.COLOR_ORANGE;
    private static final MaterialColor BAOBAB_BARK_COLOR = MaterialColor.COLOR_BROWN;
    private static final MaterialColor CYPRESS_PLANKS_COLOR = MaterialColor.COLOR_LIGHT_GRAY;
    private static final MaterialColor CYPRESS_BARK_COLOR = MaterialColor.STONE;

    // OTHER (BUILDING BLOCKS)
    public static final Block CHISELED_MUD_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(1.5F).requiresTool().sounds(SoundType.MUD_BRICKS));

    /**
     * Building Blocks
     */
    public static void registerOtherBB() {
        registerBlock("chiseled_mud_bricks", CHISELED_MUD_BRICKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    }

    // WOOD
    public static final Block BAOBAB_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block STRIPPED_BAOBAB_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_CYPRESS_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block STRIPPED_BAOBAB_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_CYPRESS_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_STAIRS = new StairBlock(BAOBAB_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(BAOBAB_PLANKS));
    public static final Block CYPRESS_STAIRS = new StairBlock(CYPRESS_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(CYPRESS_PLANKS));

    public static final Block BAOBAB_BUTTON = new WoodButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).color(BAOBAB_PLANKS_COLOR));
    public static final Block CYPRESS_BUTTON = new WoodButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).color(CYPRESS_PLANKS_COLOR));

    public static final Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).noCollision().strength(0.5F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).noCollision().strength(0.5F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque());
    public static final Block CYPRESS_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque());

    public static final Block BAOBAB_TRAPDOOR = new TrapDoorBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));
    public static final Block CYPRESS_TRAPDOOR = new TrapDoorBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_NUT = new BaobabNutBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().breakInstantly().sounds(RegisterBlockSoundGroups.BAOBAB_NUT).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final Block POTTED_BAOBAB_NUT = new FlowerPotBlock(RegisterBlocks.BAOBAB_NUT, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    //public static final Block PRICKLY_PEAR_CACTUS = new PricklyPearCactusBlock(FabricBlockSettings.of(Material.CACTUS).ticksRandomly().strength(0.4F).sounds(SoundType.SWEET_BERRY_BUSH).nonOpaque());
    public static final Block CYPRESS_SAPLING = new WaterloggableSaplingBlock(new CypressSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    public static final Block POTTED_CYPRESS_SAPLING = new FlowerPotBlock(RegisterBlocks.CYPRESS_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static final Block BAOBAB_LEAVES = new BaobabLeaves(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).ticksRandomly().sounds(SoundType.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block CYPRESS_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).ticksRandomly().sounds(SoundType.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final WoodType BAOBAB_WOOD_TYPE = FrozenWoodTypes.newType("wilderwildbaobab");
    public static final Block BAOBAB_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_sign"));
    public static final Block BAOBAB_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD).dropsLike(BAOBAB_SIGN_BLOCK), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_sign"));

    public static final WoodType CYPRESS_WOOD_TYPE = FrozenWoodTypes.newType("wilderwildcypress");
    public static final Block CYPRESS_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_sign"));
    public static final Block CYPRESS_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD).dropsLike(CYPRESS_SIGN_BLOCK), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_sign"));

    public static void registerWoods() {
        String baobab = "baobab";
        String cypress = "cypress";

        FrozenWoodTypes.register(BAOBAB_WOOD_TYPE);
        FrozenWoodTypes.register(CYPRESS_WOOD_TYPE);

        registerBlock(baobab + "_planks", BAOBAB_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_planks", CYPRESS_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_log", BAOBAB_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_log", CYPRESS_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock("stripped_" + baobab + "_log", STRIPPED_BAOBAB_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock("stripped_" + cypress + "_log", STRIPPED_CYPRESS_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock("stripped_" + baobab + "_wood", STRIPPED_BAOBAB_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock("stripped_" + cypress + "_wood", STRIPPED_CYPRESS_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_wood", BAOBAB_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_wood", CYPRESS_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_slab", BAOBAB_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_slab", CYPRESS_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_stairs", BAOBAB_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_stairs", CYPRESS_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_button", BAOBAB_BUTTON, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_button", CYPRESS_BUTTON, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_pressure_plate", BAOBAB_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_pressure_plate", CYPRESS_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_door", BAOBAB_DOOR, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_door", CYPRESS_DOOR, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_trapdoor", BAOBAB_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_trapdoor", CYPRESS_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_fence_gate", BAOBAB_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_fence_gate", CYPRESS_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);

        registerBlockWithoutBlockItem(baobab + "_nut", BAOBAB_NUT);
        registerBlockWithoutBlockItem("potted_" + baobab + "_nut", POTTED_BAOBAB_NUT);
        //registerBlockWithoutBlockItem("prickly_pear_cactus", PRICKLY_PEAR_CACTUS);
        registerBlock(cypress + "_sapling", CYPRESS_SAPLING, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_" + cypress + "_sapling", POTTED_CYPRESS_SAPLING);

        registerBlock(baobab + "_leaves", BAOBAB_LEAVES, CreativeModeTab.TAB_DECORATIONS);
        registerBlock(cypress + "_leaves", CYPRESS_LEAVES, CreativeModeTab.TAB_DECORATIONS);

        registerBlock(baobab + "_fence", BAOBAB_FENCE, CreativeModeTab.TAB_DECORATIONS);
        registerBlock(cypress + "_fence", CYPRESS_FENCE, CreativeModeTab.TAB_DECORATIONS);

        registerBlockWithoutBlockItem(baobab + "_sign", BAOBAB_SIGN_BLOCK);
        registerBlockWithoutBlockItem(baobab + "_wall_sign", BAOBAB_WALL_SIGN);
        registerBlockWithoutBlockItem(cypress + "_sign", CYPRESS_SIGN_BLOCK);
        registerBlockWithoutBlockItem(cypress + "_wall_sign", CYPRESS_WALL_SIGN);
    }

    // HOLLOWED LOGS

	public static final Map<Block, Block> HOLLOWED_LOGS = new LinkedHashMap<>();

    public static final Block HOLLOWED_OAK_LOG = createHollowedLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL);
    public static final Block HOLLOWED_SPRUCE_LOG = createHollowedLogBlock(MaterialColor.PODZOL, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MaterialColor.SAND, MaterialColor.QUARTZ);
    public static final Block HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MaterialColor.DIRT, MaterialColor.PODZOL);
    public static final Block HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.STONE);
    public static final Block HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MaterialColor.COLOR_RED, MaterialColor.PODZOL);
    public static final Block HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MaterialColor.COLOR_LIGHT_GRAY, MaterialColor.STONE);

    public static void registerHollowedLogs() {
        registerBlock("hollowed_oak_log", HOLLOWED_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_birch_log", HOLLOWED_BIRCH_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_acacia_log", HOLLOWED_ACACIA_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTab.TAB_DECORATIONS);
    }

    // SCULK
	public static final Block SCULK_ECHOER = new SculkEchoerBlock(FabricBlockSettings.of(Material.SCULK, MaterialColor.COLOR_CYAN).strength(3.0F, 3.0F).sounds(SoundType.SCULK_CATALYST), 8);
	public static final Block SCULK_JAW = new SculkJawBlock(FabricBlockSettings.of(Material.SCULK).strength(0.6F).sounds(SoundType.SCULK));
    public static final Block SCULK_STAIRS = new SculkStairsBlock(Blocks.SCULK.defaultBlockState(), FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_SLAB = new SculkSlabBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_WALL = new SculkWallBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block OSSEOUS_SCULK = new OsseousSculkBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.SAND).requiresTool().strength(2.0F).sounds(RegisterBlockSoundGroups.OSSEOUS_SCULK));
    public static final Block HANGING_TENDRIL = new HangingTendrilBlock(FabricBlockSettings.copyOf(Blocks.SCULK_SENSOR).strength(0.7F).collidable(false).luminance((state) -> 1)
            .sounds(RegisterBlockSoundGroups.HANGING_TENDRIL).emissiveLighting((state, level, pos) -> HangingTendrilBlock.shouldHavePogLighting(state)), 4);
    public static final Block ECHO_GLASS = new EchoGlassBlock(FabricBlockSettings.of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(0.3F).nonOpaque().sounds(RegisterBlockSoundGroups.ECHO_GLASS));

    public static void registerDeepDark() {
		registerBlock("sculk_echoer", SCULK_ECHOER, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("sculk_jaw", SCULK_JAW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("sculk_stairs", SCULK_STAIRS, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("sculk_slab", SCULK_SLAB, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("sculk_wall", SCULK_WALL, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("osseous_sculk", OSSEOUS_SCULK, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hanging_tendril", HANGING_TENDRIL, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("echo_glass", ECHO_GLASS, CreativeModeTab.TAB_DECORATIONS);
    }

    private static boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    public static final Material MESOGLEA_MATERIAL = new FabricMaterialBuilder(MaterialColor.CLAY)
            .lightPassesThrough()
            .notSolid()
            .destroyedByPiston()
            .build();

    public static final Material NEMATOCYST_MATERIAL = new FabricMaterialBuilder(MaterialColor.CLAY)
            .allowsMovement()
            .lightPassesThrough()
            .notSolid()
            .destroyedByPiston()
            .build();

    // Mesoglea
    public static final Block BLUE_PEARLESCENT_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.QUARTZ).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block PURPLE_PEARLESCENT_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_PURPLE).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block YELLOW_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_YELLOW).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block BLUE_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_LIGHT_BLUE).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block LIME_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_LIGHT_GREEN).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block RED_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_RED).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block PINK_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_PINK).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundGroups.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));

    public static final Block BLUE_PEARLESCENT_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.QUARTZ).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
    public static final Block PURPLE_PEARLESCENT_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_PURPLE).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
    public static final Block YELLOW_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_YELLOW).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
    public static final Block BLUE_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_LIGHT_BLUE).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
    public static final Block LIME_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_LIGHT_GREEN).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
    public static final Block RED_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_RED).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));
    public static final Block PINK_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_PINK).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundGroups.NEMATOCYST));

    // MISC
    private static final Material ALGAE_MATERIAL = new FabricMaterialBuilder(MaterialColor.PLANT)
            .allowsMovement()
            .lightPassesThrough()
            .notSolid()
            .destroyedByPiston()
            .build();

    public static final Block TERMITE_MOUND = new TermiteMound(FabricBlockSettings.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(0.3F).sounds(RegisterBlockSoundGroups.COARSEDIRT));
    public static final Block STONE_CHEST = new StoneChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST).sounds(SoundType.DEEPSLATE).strength(35.0F, 12.0F), () -> RegisterBlockEntities.STONE_CHEST);

    // PLANTS
    public static final Block SEEDING_DANDELION = new SeedingDandelionBlock(MobEffects.SLOW_FALLING, 12, FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(RegisterBlockSoundGroups.FLOWER).strength(0.0F).nonOpaque());
    public static final Block POTTED_SEEDING_DANDELION = new FlowerPotBlock(RegisterBlocks.SEEDING_DANDELION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block CARNATION = new FlowerBlock(MobEffects.REGENERATION, 12, FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(RegisterBlockSoundGroups.FLOWER).strength(0.0F).nonOpaque());
    public static final Block POTTED_CARNATION = new FlowerPotBlock(RegisterBlocks.CARNATION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(RegisterBlockSoundGroups.FLOWER).strength(0.0F).nonOpaque().ticksRandomly(), List.of(FlowerColor.BLUE, FlowerColor.PINK, FlowerColor.PURPLE, FlowerColor.WHITE));

    public static final Block WHITE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.QUARTZ).sound(SoundType.VINE));
    public static final Block PINK_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.CRIMSON_STEM).sound(SoundType.VINE));
    public static final Block PURPLE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_PURPLE).sound(SoundType.VINE));
    public static final Block BLUE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_BLUE).sound(SoundType.VINE));

    public static final Block DATURA = new TallFlowerBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final Block MILKWEED = new MilkweedBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));

    public static final Block CATTAIL = new WaterloggableTallFlowerBlock(FabricBlockSettings.copyOf(Blocks.ROSE_BUSH).sounds(SoundType.WET_GRASS).strength(0.0F).nonOpaque());
    public static final Block FLOWERING_LILY_PAD = new FloweringLilyPadBlock(FabricBlockSettings.copyOf(Blocks.LILY_PAD).sounds(RegisterBlockSoundGroups.LILYPAD));
    public static final Block ALGAE = new AlgaeBlock(FabricBlockSettings.of(ALGAE_MATERIAL).breakInstantly().nonOpaque().noCollision().sounds(SoundType.SLIME_BLOCK));

    public static final Block POTTED_BIG_DRIPLEAF = new FlowerPotBlock(Blocks.BIG_DRIPLEAF, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_SMALL_DRIPLEAF = new FlowerPotBlock(Blocks.SMALL_DRIPLEAF, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_GRASS = new FlowerPotBlock(Blocks.GRASS, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static void registerPlants() {
        registerBlockWithoutBlockItem("potted_big_dripleaf", POTTED_BIG_DRIPLEAF);
        registerBlockWithoutBlockItem("potted_small_dripleaf", POTTED_SMALL_DRIPLEAF);
        registerBlockWithoutBlockItem("potted_grass", POTTED_GRASS);
        registerBlock("seeding_dandelion", SEEDING_DANDELION, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_seeding_dandelion", POTTED_SEEDING_DANDELION);
        registerBlock("carnation", CARNATION, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_carnation", POTTED_CARNATION);
        registerBlock("glory_of_the_snow", GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("blue_giant_glory_of_the_snow", BLUE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("pink_giant_glory_of_the_snow", PINK_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("violet_beauty_glory_of_the_snow", PURPLE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("alba_glory_of_the_snow", WHITE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("datura", DATURA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("milkweed", MILKWEED, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("cattail", CATTAIL, CreativeModeTab.TAB_DECORATIONS);
    }

    public static final Block BROWN_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance(1).collidable(false).nonOpaque().sounds(RegisterBlockSoundGroups.MUSHROOM));
    public static final Block RED_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).collidable(false).nonOpaque().sounds(RegisterBlockSoundGroups.MUSHROOM));
    public static final Block POLLEN_BLOCK = new PollenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.SAND).sound(SoundType.VINE));

    public static void registerNotSoPlants() {
        registerBlockWithoutBlockItem("pollen", POLLEN_BLOCK);
        registerBlock("brown_shelf_fungus", BROWN_SHELF_FUNGUS, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("red_shelf_fungus", RED_SHELF_FUNGUS, CreativeModeTab.TAB_DECORATIONS);
        Registry.register(Registry.BLOCK, WilderSharedConstants.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
        Registry.register(Registry.ITEM, WilderSharedConstants.id("flowering_lily_pad"), new FloweredLilyPadItem(FLOWERING_LILY_PAD, new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS)));
        Registry.register(Registry.BLOCK, WilderSharedConstants.id("algae"), ALGAE);
        Registry.register(Registry.ITEM, WilderSharedConstants.id("algae"), new AlgaeItem(ALGAE, new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS)));
    }

    // BLOCK FAMILIES
    public static final BlockFamily BAOBAB = BlockFamilies.familyBuilder(BAOBAB_PLANKS)
            .button(BAOBAB_BUTTON)
            .slab(BAOBAB_SLAB)
            .stairs(BAOBAB_STAIRS)
            .fence(BAOBAB_FENCE)
            .fenceGate(BAOBAB_FENCE_GATE)
            .pressurePlate(BAOBAB_PRESSURE_PLATE)
            .sign(BAOBAB_SIGN_BLOCK, BAOBAB_WALL_SIGN)
            .door(BAOBAB_DOOR)
            .trapdoor(BAOBAB_TRAPDOOR)
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();


    public static final BlockFamily CYPRESS = BlockFamilies.familyBuilder(CYPRESS_PLANKS)
            .button(CYPRESS_BUTTON)
            .slab(CYPRESS_SLAB)
            .stairs(CYPRESS_STAIRS)
            .fence(CYPRESS_FENCE)
            .fenceGate(CYPRESS_FENCE_GATE)
            .pressurePlate(CYPRESS_PRESSURE_PLATE)
            .sign(CYPRESS_SIGN_BLOCK, CYPRESS_WALL_SIGN)
            .door(CYPRESS_DOOR)
            .trapdoor(CYPRESS_TRAPDOOR)
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();

    public static final Block NULL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE).sounds(RegisterBlockSoundGroups.NULL_BLOCK));

    public static final Block DISPLAY_LANTERN = new DisplayLanternBlock(FabricBlockSettings.of(Material.METAL).strength(3.5F).sounds(SoundType.LANTERN).luminance((state) -> state.getValue(RegisterProperties.DISPLAY_LIGHT)));

    public static void registerMisc() {
        registerBlock("termite_mound", TERMITE_MOUND, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("null_block", NULL_BLOCK, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("stone_chest", STONE_CHEST, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("display_lantern", DISPLAY_LANTERN, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("blue_pearlescent_mesoglea", BLUE_PEARLESCENT_MESOGLEA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("purple_pearlescent_mesoglea", PURPLE_PEARLESCENT_MESOGLEA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("blue_mesoglea", BLUE_MESOGLEA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("yellow_mesoglea", YELLOW_MESOGLEA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("lime_mesoglea", LIME_MESOGLEA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("red_mesoglea", RED_MESOGLEA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("pink_mesoglea", PINK_MESOGLEA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("blue_pearlescent_nematocyst", BLUE_PEARLESCENT_NEMATOCYST, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("purple_pearlescent_nematocyst", PURPLE_PEARLESCENT_NEMATOCYST, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("blue_nematocyst", BLUE_NEMATOCYST, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("yellow_nematocyst", YELLOW_NEMATOCYST, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("lime_nematocyst", LIME_NEMATOCYST, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("red_nematocyst", RED_NEMATOCYST, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("pink_nematocyst", PINK_NEMATOCYST, CreativeModeTab.TAB_DECORATIONS);
    }

    public static void registerBlocks() {
        WilderSharedConstants.logWild("Registering Blocks for", WilderSharedConstants.UNSTABLE_LOGGING);
		SculkSensorFrequencyRegistry.register(RegisterGameEvents.TENDRIL_EXTRACT_XP, 1);

        registerOtherBB();
        registerWoods();
        registerHollowedLogs();
        registerDeepDark();
        registerPlants();
        registerNotSoPlants();
        registerMisc();
        registerBlockProperties();
    }

    private static void registerBlockWithoutBlockItem(String name, Block block) {
        Registry.register(Registry.BLOCK, WilderSharedConstants.id(name), block);
    }

    private static void registerBlock(String name, Block block, CreativeModeTab tab) {
        registerBlockItem(name, block, tab);
        Registry.register(Registry.BLOCK, WilderSharedConstants.id(name), block);
    }

    private static void registerBlockItem(String name, Block block, CreativeModeTab tab) {
        Registry.register(Registry.ITEM, WilderSharedConstants.id(name), new BlockItem(block, new FabricItemSettings().tab(tab)));
    }

    private static HollowedLogBlock createHollowedLogBlock(MaterialColor topMapColor, MaterialColor sideMapColor) {
        return new HollowedLogBlock(FabricBlockSettings.of(Material.WOOD,
                        (state) -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0F).sound(RegisterBlockSoundGroups.HOLLOWED_LOG));
    }

    public static void registerBlockProperties() {
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_LOG, HOLLOWED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_LOG, HOLLOWED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
        registerStrippable();
        registerComposting();
        registerFlammability();
        registerFuels();
        registerBonemeal();
    }

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static void registerStrippable() {
        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
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
        CompostingChanceRegistry.INSTANCE.add(RegisterItems.BAOBAB_NUT, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BLUE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(WHITE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PINK_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PURPLE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ALGAE, 0.3F);
    }

    private static void registerFlammability() {
        WilderSharedConstants.logWild("Registering Flammability for", WilderSharedConstants.UNSTABLE_LOGGING);
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
        WilderSharedConstants.logWild("Registering Fuels for", WilderSharedConstants.UNSTABLE_LOGGING);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(BAOBAB_FENCE.asItem(), 300);
        registry.add(BAOBAB_FENCE_GATE.asItem(), 300);
        registry.add(CYPRESS_FENCE.asItem(), 300);
        registry.add(CYPRESS_FENCE_GATE.asItem(), 300);
    }

    private static void registerBonemeal() {
        BonemealBehaviors.BONEMEAL_BEHAVIORS.put(Blocks.LILY_PAD, (context, level, pos, state, face, horizontal) -> {
            WilderSharedConstants.log(Blocks.LILY_PAD, pos, "Bonemeal", WilderSharedConstants.DEV_LOGGING);
            if (!level.isClientSide) {
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                level.setBlockAndUpdate(pos, RegisterBlocks.FLOWERING_LILY_PAD.defaultBlockState());
                return true;
            }
            return false;
        });
		BonemealBehaviors.BONEMEAL_BEHAVIORS.put(BROWN_SHELF_FUNGUS, (context, world, pos, state, face, horizontal) -> {
			if (state.getValue(RegisterProperties.FUNGUS_STAGE) < 4) {
				WilderSharedConstants.log("Shelf Fungus Bonemealed @ " + pos + " with FungusStage of " + state.getValue(RegisterProperties.FUNGUS_STAGE), WilderSharedConstants.DEV_LOGGING);
				if (!world.isClientSide) {
					world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
					world.setBlockAndUpdate(pos, state.setValue(RegisterProperties.FUNGUS_STAGE, state.getValue(RegisterProperties.FUNGUS_STAGE) + 1));
					return true;
				}
			}
			return false;
		});
		BonemealBehaviors.BONEMEAL_BEHAVIORS.put(RED_SHELF_FUNGUS, (context, world, pos, state, face, horizontal) -> {
			if (state.getValue(RegisterProperties.FUNGUS_STAGE) < 4) {
				WilderSharedConstants.log("Shelf Fungus Bonemealed @ " + pos + " with FungusStage of " + state.getValue(RegisterProperties.FUNGUS_STAGE), WilderSharedConstants.DEV_LOGGING);
				if (!world.isClientSide) {
					world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
					world.setBlockAndUpdate(pos, state.setValue(RegisterProperties.FUNGUS_STAGE, state.getValue(RegisterProperties.FUNGUS_STAGE) + 1));
					return true;
				}
			}
			return false;
		});
		BonemealBehaviors.BONEMEAL_BEHAVIORS.put(ALGAE, (context, world, pos, state, face, horizontal) -> {
			WilderSharedConstants.log("Algae Bonemealed @ " + pos, WilderSharedConstants.DEV_LOGGING);
			if (!world.isClientSide) {
				for (Direction offset : AlgaeBlock.shuffleOffsets(world.getRandom())) {
					BlockPos blockPos = pos.relative(offset);
					if (world.getBlockState(blockPos).isAir() && state.getBlock().canSurvive(state, world, blockPos)) {
						world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
						world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 0);
						world.setBlockAndUpdate(blockPos, state);
						return true;
					}
				}
			}
			return false;
		});
    }

}
