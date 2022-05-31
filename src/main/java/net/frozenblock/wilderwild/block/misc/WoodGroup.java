package net.frozenblock.wilderwild.block.misc;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.mixin.SignTypeAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class WoodGroup {

    public Block LOG;
    public Block STRIPPED_LOG;
    public Block WOOD;
    public Block STRIPPED_WOOD;
    public Block HOLLOWED_LOG;
    public Block PLANKS;
    public Block SLAB;
    public Block STAIRS;
    public Block DOOR;
    public Block TRAPDOOR;
    public Block FENCE;
    public Block GATE;
    public Block PLATE;
    public Block LEAVES;
    public Block BUTTON;
    public Block SIGN;
    public Block WALL_SIGN;

    public final SignType SIGN_TYPE;

    public WoodGroup(Identifier id, MapColor planks, MapColor bark, MapColor leaves) {
        String name = id.getPath();
        this.SIGN_TYPE = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType(name));
        LOG = registerBlock(name+"_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? planks : bark).strength(2.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.BUILDING_BLOCKS);
        STRIPPED_LOG = registerBlock("stripped_"+name+"_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? planks : bark).strength(2.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.BUILDING_BLOCKS);
        WOOD = registerBlock(name+"_wood", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? planks : bark).strength(2.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.BUILDING_BLOCKS);
        STRIPPED_WOOD = registerBlock("stripped_"+name+"_wood", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? planks : bark).strength(2.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.BUILDING_BLOCKS);
        HOLLOWED_LOG = registerBlock("hollowed_"+name+"_log", createHollowedLogBlock(planks, bark), ItemGroup.BUILDING_BLOCKS);
        PLANKS = registerBlock(name+"_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, planks).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.BUILDING_BLOCKS);
        SLAB = registerBlock(name+"_slab", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, planks).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.BUILDING_BLOCKS);
        STAIRS = registerBlock(name+"_stairs", new StairsBlock(PLANKS.getDefaultState(), AbstractBlock.Settings.copy(PLANKS)), ItemGroup.BUILDING_BLOCKS);
        DOOR = registerBlock(name+"_door", new DoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()), ItemGroup.REDSTONE);
        TRAPDOOR = registerBlock("name"+"_trapdoor", new TrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(WoodGroup::never)), ItemGroup.REDSTONE);
        FENCE = registerBlock(name+"_fence", new FenceBlock(AbstractBlock.Settings.of(Material.WOOD, planks).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.BUILDING_BLOCKS);
        GATE = registerBlock(name+"_fence_gate", new FenceGateBlock(AbstractBlock.Settings.of(Material.WOOD, planks).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.REDSTONE);
        PLATE = registerBlock(name+"_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, planks).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), ItemGroup.REDSTONE);
        LEAVES = registerBlock(name+"_leaves", new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES, leaves).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(WoodGroup::canSpawnOnLeaves).suffocates(WoodGroup::never).blockVision(WoodGroup::never)), ItemGroup.BUILDING_BLOCKS);
        BUTTON = registerBlock(name+"_button", new WoodenButtonBlock(AbstractBlock.Settings.copy(Blocks.OAK_BUTTON).mapColor(planks)), ItemGroup.REDSTONE);
        SIGN = registerBlock(name+"_sign", new SignBlock(AbstractBlock.Settings.of(Material.WOOD, bark).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), SIGN_TYPE), ItemGroup.BUILDING_BLOCKS);
        WALL_SIGN = registerBlockWithoutBlockItem(name+"_wall_sign", new SignBlock(AbstractBlock.Settings.of(Material.WOOD, bark).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(SIGN), SIGN_TYPE));

        StrippableBlockRegistry.register(LOG, STRIPPED_LOG);
        StrippableBlockRegistry.register(WOOD, STRIPPED_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(WOOD,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_WOOD,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(PLANKS,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(STAIRS,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(DOOR,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(FENCE,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(SLAB,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(GATE,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(PLATE,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(TRAPDOOR,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(LEAVES,100,60);
        FlammableBlockRegistry.getDefaultInstance().add(BUTTON,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(SIGN,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(WALL_SIGN,5,20);
    }

    private static Block registerBlockWithoutBlockItem(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, id), block);
    }

    private static Block registerBlock(String id, Block block, ItemGroup group) {
        registerBlockItem(id, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, id), block);
    }

    private static BlockItem registerBlockItem(String id, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, id),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    private static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new HollowedLogBlock(AbstractBlock.Settings.of(Material.WOOD,
                        (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0F).sounds(BlockSoundGroup.WOOD));
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
}
