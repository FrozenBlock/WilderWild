/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.registry;

import java.util.List;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.lib.axe.api.AxeBehaviors;
import net.frozenblock.lib.block.api.FrozenCeilingHangingSignBlock;
import net.frozenblock.lib.block.api.FrozenSignBlock;
import net.frozenblock.lib.block.api.FrozenWallHangingSignBlock;
import net.frozenblock.lib.block.api.FrozenWallSignBlock;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.lib.item.api.bonemeal.BonemealBehaviors;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.block.BaobabLeaves;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
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
import net.frozenblock.wilderwild.block.PalmCrownBlock;
import net.frozenblock.wilderwild.block.PalmFrondsBlock;
import net.frozenblock.wilderwild.block.PollenBlock;
import net.frozenblock.wilderwild.block.PricklyPearCactusBlock;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.SculkSlabBlock;
import net.frozenblock.wilderwild.block.SculkStairsBlock;
import net.frozenblock.wilderwild.block.SculkWallBlock;
import net.frozenblock.wilderwild.block.SeedingDandelionBlock;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.block.SmallSpongeBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMound;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.block.WWBushBlock;
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.entity.TermiteManager;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.generation.sapling.CypressSaplingGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DispenserBlock;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class RegisterBlocks {
	private RegisterBlocks() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

    private static final MapColor BAOBAB_PLANKS_COLOR = MapColor.COLOR_ORANGE;
    private static final MapColor BAOBAB_BARK_COLOR = MapColor.COLOR_BROWN;
    private static final MapColor CYPRESS_PLANKS_COLOR = MapColor.COLOR_LIGHT_GRAY;
    private static final MapColor CYPRESS_BARK_COLOR = MapColor.STONE;
    private static final MapColor PALM_PLANKS_COLOR = MapColor.TERRACOTTA_WHITE;
    private static final MapColor PALM_BARK_COLOR = MapColor.COLOR_LIGHT_GRAY;

	public static final BlockSetType BAOBAB_SET = BlockSetTypeRegistry.registerWood(
			WilderSharedConstants.id("baobab")
	);

	public static final BlockSetType CYPRESS_SET = BlockSetTypeRegistry.registerWood(
			WilderSharedConstants.id("cypress")
	);

	public static final BlockSetType PALM_SET = BlockSetTypeRegistry.registerWood(
			WilderSharedConstants.id("palm")
	);

	public static final WoodType BAOBAB_WOOD_TYPE = WoodTypeRegistry.register(WilderSharedConstants.id("baobab"), BAOBAB_SET);

	public static final WoodType CYPRESS_WOOD_TYPE = WoodTypeRegistry.register(WilderSharedConstants.id("cypress"), CYPRESS_SET);

	public static final WoodType PALM_WOOD_TYPE = WoodTypeRegistry.register(WilderSharedConstants.id("palm"), PALM_SET);

    // OTHER (BUILDING BLOCKS)
    public static final Block CHISELED_MUD_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(1.5F).requiresTool().sounds(SoundType.MUD_BRICKS));
	public static final Block SCORCHED_SAND = new ScorchedBlock(FabricBlockSettings.of().mapColor(MapColor.SAND).strength(1.5F).sounds(RegisterBlockSoundTypes.SCORCHEDSAND).mapColor(MapColor.SAND).ticksRandomly(), Blocks.SAND.defaultBlockState(), true, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED);
	public static final Block SCORCHED_RED_SAND = new ScorchedBlock(FabricBlockSettings.of().mapColor(MapColor.SAND).strength(1.5F).sounds(RegisterBlockSoundTypes.SCORCHEDSAND).mapColor(MapColor.COLOR_ORANGE).ticksRandomly(), Blocks.RED_SAND.defaultBlockState(), true, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED);

    public static void registerOtherBB() {
        registerBlockAfter(Items.MUD_BRICKS, "chiseled_mud_bricks", CHISELED_MUD_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlock(false, "scorched_sand", SCORCHED_SAND);
		registerBlock(false, "scorched_red_sand", SCORCHED_RED_SAND);
    }

    // WOOD
    public static final Block BAOBAB_PLANKS = new Block(FabricBlockSettings.of().mapColor(BAOBAB_PLANKS_COLOR).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(SoundType.WOOD).burnable());
    public static final Block CYPRESS_PLANKS = new Block(FabricBlockSettings.of().mapColor(CYPRESS_PLANKS_COLOR).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(SoundType.WOOD).burnable());
    public static final Block PALM_PLANKS = new Block(FabricBlockSettings.of().mapColor(PALM_PLANKS_COLOR).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(SoundType.WOOD).burnable());

    public static final Block BAOBAB_LOG = new RotatedPillarBlock(log(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR));
    public static final Block CYPRESS_LOG = new RotatedPillarBlock(log(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR));
    public static final Block PALM_LOG = new RotatedPillarBlock(log(PALM_PLANKS_COLOR, PALM_BARK_COLOR));
	public static final Block PALM_CROWN = new PalmCrownBlock(log(PALM_PLANKS_COLOR, PALM_BARK_COLOR).sounds(RegisterBlockSoundTypes.PALM_CROWN));

    public static final Block STRIPPED_BAOBAB_LOG = new RotatedPillarBlock(log(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR));
    public static final Block STRIPPED_CYPRESS_LOG = new RotatedPillarBlock(log(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR));
    public static final Block STRIPPED_PALM_LOG = new RotatedPillarBlock(log(PALM_PLANKS_COLOR, PALM_BARK_COLOR));

    public static final Block STRIPPED_BAOBAB_WOOD = new RotatedPillarBlock(wood(BAOBAB_BARK_COLOR));
    public static final Block STRIPPED_CYPRESS_WOOD = new RotatedPillarBlock(wood(CYPRESS_BARK_COLOR));
    public static final Block STRIPPED_PALM_WOOD = new RotatedPillarBlock(wood(PALM_BARK_COLOR));

    public static final Block BAOBAB_WOOD = new RotatedPillarBlock(wood(BAOBAB_BARK_COLOR));
    public static final Block CYPRESS_WOOD = new RotatedPillarBlock(wood(CYPRESS_BARK_COLOR));
    public static final Block PALM_WOOD = new RotatedPillarBlock(wood(PALM_BARK_COLOR));

    public static final Block BAOBAB_SLAB = new SlabBlock(slab(BAOBAB_PLANKS_COLOR));
    public static final Block CYPRESS_SLAB = new SlabBlock(slab(CYPRESS_PLANKS_COLOR));
    public static final Block PALM_SLAB = new SlabBlock(slab(PALM_PLANKS_COLOR));

    public static final Block BAOBAB_STAIRS = new StairBlock(BAOBAB_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(BAOBAB_PLANKS).instrument(NoteBlockInstrument.BASS));
    public static final Block CYPRESS_STAIRS = new StairBlock(CYPRESS_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(CYPRESS_PLANKS).instrument(NoteBlockInstrument.BASS));
    public static final Block PALM_STAIRS = new StairBlock(PALM_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(PALM_PLANKS).instrument(NoteBlockInstrument.BASS));

    public static final Block BAOBAB_BUTTON = new ButtonBlock(
			FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).mapColor(BAOBAB_PLANKS_COLOR),
			BAOBAB_SET,
			30, true
	);
    public static final Block CYPRESS_BUTTON = new ButtonBlock(
			FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).mapColor(CYPRESS_PLANKS_COLOR),
			CYPRESS_SET,
			30, true
	);
	public static final Block PALM_BUTTON = new ButtonBlock(
			FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).mapColor(PALM_PLANKS_COLOR),
			PALM_SET,
			30, true
	);

    public static final Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(
			PressurePlateBlock.Sensitivity.EVERYTHING,
			FabricBlockSettings.of()
				.mapColor(BAOBAB_PLANKS_COLOR)
				.solid()
				.instrument(NoteBlockInstrument.BASS)
				.noCollision()
				.strength(0.5F)
				.burnable()
				.pistonBehavior(PushReaction.DESTROY),
			BAOBAB_SET
	);
    public static final Block CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(
			PressurePlateBlock.Sensitivity.EVERYTHING,
			FabricBlockSettings.of()
				.mapColor(CYPRESS_PLANKS_COLOR)
				.solid()
				.instrument(NoteBlockInstrument.BASS)
				.noCollision()
				.strength(0.5F)
				.burnable()
				.pistonBehavior(PushReaction.DESTROY),
			CYPRESS_SET
	);
	public static final Block PALM_PRESSURE_PLATE = new PressurePlateBlock(
			PressurePlateBlock.Sensitivity.EVERYTHING,
			FabricBlockSettings.of()
				.mapColor(PALM_PLANKS_COLOR)
				.solid()
				.instrument(NoteBlockInstrument.BASS)
				.noCollision()
				.strength(0.5F)
				.burnable()
				.pistonBehavior(PushReaction.DESTROY),
			PALM_SET
	);

    public static final Block BAOBAB_DOOR = new DoorBlock(
			FabricBlockSettings.of()
				.mapColor(BAOBAB_PLANKS_COLOR)
				.instrument(NoteBlockInstrument.BASS)
				.strength(3.0F)
				.nonOpaque()
				.burnable()
				.pistonBehavior(PushReaction.DESTROY),
			BAOBAB_SET
	);
    public static final Block CYPRESS_DOOR = new DoorBlock(
			FabricBlockSettings.of()
				.mapColor(CYPRESS_PLANKS_COLOR)
				.instrument(NoteBlockInstrument.BASS)
				.strength(3.0F)
				.nonOpaque()
				.burnable()
				.pistonBehavior(PushReaction.DESTROY),
			CYPRESS_SET
	);
	public static final Block PALM_DOOR = new DoorBlock(
			FabricBlockSettings.of()
				.mapColor(PALM_PLANKS_COLOR)
				.instrument(NoteBlockInstrument.BASS)
				.strength(3.0F)
				.nonOpaque()
				.burnable()
				.pistonBehavior(PushReaction.DESTROY),
			PALM_SET
	);

    public static final Block BAOBAB_TRAPDOOR = new TrapDoorBlock(
			FabricBlockSettings.of()
				.mapColor(BAOBAB_PLANKS_COLOR)
				.instrument(NoteBlockInstrument.BASS)
				.strength(3.0F)
				.nonOpaque()
				.allowsSpawning(RegisterBlocks::never)
				.burnable(),
			BAOBAB_SET
	);
    public static final Block CYPRESS_TRAPDOOR = new TrapDoorBlock(
			FabricBlockSettings.of()
				.mapColor(CYPRESS_PLANKS_COLOR)
				.instrument(NoteBlockInstrument.BASS)
				.strength(3.0F)
				.nonOpaque()
				.allowsSpawning(RegisterBlocks::never)
				.burnable(),
			CYPRESS_SET
	);
	public static final Block PALM_TRAPDOOR = new TrapDoorBlock(
			FabricBlockSettings.of()
				.mapColor(PALM_PLANKS_COLOR)
				.instrument(NoteBlockInstrument.BASS)
				.strength(3.0F)
				.nonOpaque()
				.allowsSpawning(RegisterBlocks::never)
				.burnable(),
			PALM_SET
	);

    public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(
			FabricBlockSettings.of()
				.mapColor(BAOBAB_PLANKS_COLOR)
				.solid()
				.instrument(NoteBlockInstrument.BASS)
				.strength(2.0F, 3.0F)
				.burnable(),
			BAOBAB_WOOD_TYPE
	);
    public static final Block CYPRESS_FENCE_GATE = new FenceGateBlock(
			FabricBlockSettings.of()
				.mapColor(CYPRESS_PLANKS_COLOR)
				.solid()
				.instrument(NoteBlockInstrument.BASS)
				.strength(2.0F, 3.0F)
				.burnable(),
			CYPRESS_WOOD_TYPE
	);
	public static final Block PALM_FENCE_GATE = new FenceGateBlock(
			FabricBlockSettings.of()
				.mapColor(PALM_PLANKS_COLOR)
				.solid()
				.instrument(NoteBlockInstrument.BASS)
				.strength(2.0F, 3.0F)
				.burnable(),
			PALM_WOOD_TYPE
	);

    public static final Block BAOBAB_NUT = new BaobabNutBlock(FabricBlockSettings.of().mapColor(MapColor.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(RegisterBlockSoundTypes.BAOBAB_NUT).offset(BlockBehaviour.OffsetType.XZ).dynamicBounds().pistonBehavior(PushReaction.DESTROY));
    public static final Block POTTED_BAOBAB_NUT = new FlowerPotBlock(RegisterBlocks.BAOBAB_NUT, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PushReaction.DESTROY));
	public static final Block PRICKLY_PEAR_CACTUS = new PricklyPearCactusBlock(FabricBlockSettings.of().mapColor(MapColor.PLANT).ticksRandomly().strength(0.4F).sounds(SoundType.WOOL).nonOpaque().pistonBehavior(PushReaction.DESTROY).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final Block CYPRESS_SAPLING = new WaterloggableSaplingBlock(new CypressSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    public static final Block POTTED_CYPRESS_SAPLING = new FlowerPotBlock(RegisterBlocks.CYPRESS_SAPLING, flowerPot());
    public static final Block COCONUT = new CoconutBlock(FabricBlockSettings.of().mapColor(MapColor.PLANT).noCollision().breakInstantly().ticksRandomly().sounds(RegisterBlockSoundTypes.COCONUT));
    public static final Block POTTED_COCONUT = new FlowerPotBlock(RegisterBlocks.COCONUT, flowerPot());

    public static final Block BAOBAB_LEAVES = new BaobabLeaves(leaves(SoundType.GRASS));
    public static final Block CYPRESS_LEAVES = new LeavesBlock(leaves(SoundType.GRASS));
    public static final Block PALM_FRONDS = new PalmFrondsBlock(leaves(SoundType.GRASS));

    public static final Block BAOBAB_FENCE = new FenceBlock(fence(BAOBAB_PLANKS_COLOR));
    public static final Block CYPRESS_FENCE = new FenceBlock(fence(CYPRESS_PLANKS_COLOR));
    public static final Block PALM_FENCE = new FenceBlock(fence(PALM_PLANKS_COLOR));

    public static final Block BAOBAB_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN).mapColor(BAOBAB_PLANKS_COLOR), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_sign"));
    public static final Block BAOBAB_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).mapColor(BAOBAB_PLANKS_COLOR).dropsLike(BAOBAB_SIGN_BLOCK), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_sign"));
	public static final Block BAOBAB_HANGING_SIGN = new FrozenCeilingHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).mapColor(BAOBAB_PLANKS_COLOR), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_hanging_sign"));
	public static final Block BAOBAB_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).mapColor(BAOBAB_PLANKS_COLOR).dropsLike(BAOBAB_HANGING_SIGN), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_hanging_sign"));

    public static final Block CYPRESS_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN).mapColor(CYPRESS_PLANKS_COLOR), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_sign"));
    public static final Block CYPRESS_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).mapColor(CYPRESS_PLANKS_COLOR).dropsLike(CYPRESS_SIGN_BLOCK), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_sign"));
	public static final Block CYPRESS_HANGING_SIGN = new FrozenCeilingHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).mapColor(CYPRESS_PLANKS_COLOR), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_hanging_sign"));
	public static final Block CYPRESS_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).mapColor(CYPRESS_PLANKS_COLOR).dropsLike(CYPRESS_HANGING_SIGN), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_hanging_sign"));

    public static final Block PALM_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN).mapColor(PALM_PLANKS_COLOR), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_sign"));
    public static final Block PALM_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).mapColor(PALM_PLANKS_COLOR).dropsLike(PALM_SIGN_BLOCK), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_sign"));
	public static final Block PALM_HANGING_SIGN = new FrozenCeilingHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).mapColor(PALM_LOG.defaultMapColor()), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_hanging_sign"));
	public static final Block PALM_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).mapColor(PALM_LOG.defaultMapColor()).dropsLike(PALM_HANGING_SIGN), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_hanging_sign"));

    public static void registerWoods() {
    	String baobab = "baobab";
    	String cypress = "cypress";
    	String palm = "palm";
		String wood = "baobab";
		//BAOBAB IN BUILDING BLOCKS
		registerBlockAfter(Items.MANGROVE_BUTTON,wood + "_log", BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_LOG,wood + "_wood", BAOBAB_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_WOOD,"stripped_" + wood + "_log", STRIPPED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_BAOBAB_LOG,"stripped_" + wood + "_wood", STRIPPED_BAOBAB_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_BAOBAB_WOOD, wood + "_planks", BAOBAB_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_PLANKS,wood + "_stairs", BAOBAB_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_STAIRS,wood + "_slab", BAOBAB_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_SLAB,wood + "_fence", BAOBAB_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_FENCE,wood + "_fence_gate", BAOBAB_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_FENCE_GATE,wood + "_door", BAOBAB_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_DOOR,wood + "_trapdoor", BAOBAB_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_TRAPDOOR,wood + "_pressure_plate", BAOBAB_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_PRESSURE_PLATE,wood + "_button", BAOBAB_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//BAOBAB IN NATURE
		registerBlockAfter(Items.MANGROVE_LOG,wood + "_log", BAOBAB_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.FLOWERING_AZALEA_LEAVES,wood + "_leaves", BAOBAB_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);

		wood = "cypress";
		//CYPRESS IN BUILDING BLOCKS
		registerBlockAfter(BAOBAB_BUTTON,wood + "_log", CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_LOG, wood + "_wood", CYPRESS_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_WOOD, "stripped_" + wood + "_log", STRIPPED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_CYPRESS_LOG, "stripped_" + wood + "_wood", STRIPPED_CYPRESS_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_CYPRESS_WOOD, wood + "_planks", CYPRESS_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_PLANKS,wood + "_stairs", CYPRESS_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_STAIRS,wood + "_slab", CYPRESS_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_SLAB,wood + "_fence", CYPRESS_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_FENCE,wood + "_fence_gate", CYPRESS_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_FENCE_GATE,wood + "_door", CYPRESS_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_DOOR,wood + "_trapdoor", CYPRESS_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_TRAPDOOR,wood + "_pressure_plate", CYPRESS_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_PRESSURE_PLATE,wood + "_button", CYPRESS_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//CYPRESS IN NATURE
		registerBlockAfter(BAOBAB_LOG,wood + "_log", CYPRESS_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(BAOBAB_LEAVES,wood + "_leaves", CYPRESS_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);

		wood = "palm";
		//PALM IN BUILDING BLOCKS
		registerBlockAfter(CYPRESS_BUTTON, wood + "_log", PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_LOG, wood + "_wood", PALM_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_WOOD, wood + "_crown", PALM_CROWN, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_CROWN, "stripped_" + wood + "_log", STRIPPED_PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_PALM_LOG, "stripped_" + wood + "_wood", STRIPPED_PALM_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_PALM_WOOD, wood + "_planks", PALM_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_PLANKS, wood + "_stairs", PALM_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_STAIRS, wood + "_slab", PALM_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_SLAB, wood + "_fence", PALM_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_FENCE, wood + "_fence_gate", PALM_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_FENCE_GATE, wood + "_door", PALM_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_DOOR, wood + "_trapdoor", PALM_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_TRAPDOOR, wood + "_pressure_plate", PALM_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_PRESSURE_PLATE, wood + "_button", PALM_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//PALM IN NATURE
		registerBlockAfter(CYPRESS_LOG, wood + "_log", PALM_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(PALM_LOG, wood + "_crown", PALM_CROWN, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(CYPRESS_LEAVES, wood + "_fronds", PALM_FRONDS, CreativeModeTabs.NATURAL_BLOCKS);

        registerBlock(false, baobab + "_nut", BAOBAB_NUT);
        registerBlock(false, "potted_" + baobab + "_nut", POTTED_BAOBAB_NUT);

		registerBlockAfter(Items.MANGROVE_PROPAGULE,cypress + "_sapling", CYPRESS_SAPLING, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlock(false, "potted_" + cypress + "_sapling", POTTED_CYPRESS_SAPLING);
		registerBlock(false, "coconut", COCONUT);
		registerBlock(false, "potted_coconut", POTTED_COCONUT);

        registerBlock(false, baobab + "_sign", BAOBAB_SIGN_BLOCK);
        registerBlock(false, baobab + "_wall_sign", BAOBAB_WALL_SIGN);
		registerBlock(false, baobab + "_hanging_sign", BAOBAB_HANGING_SIGN);
		registerBlock(false, baobab + "_wall_hanging_sign", BAOBAB_WALL_HANGING_SIGN);
        registerBlock(false, cypress + "_sign", CYPRESS_SIGN_BLOCK);
        registerBlock(false, cypress + "_wall_sign", CYPRESS_WALL_SIGN);
		registerBlock(false, cypress + "_hanging_sign", CYPRESS_HANGING_SIGN);
		registerBlock(false, cypress + "_wall_hanging_sign", CYPRESS_WALL_HANGING_SIGN);
		registerBlock(false, palm + "_sign", PALM_SIGN_BLOCK);
		registerBlock(false, palm + "_wall_sign", PALM_WALL_SIGN);
		registerBlock(false, palm + "_hanging_sign", PALM_HANGING_SIGN);
		registerBlock(false, palm + "_wall_hanging_sign", PALM_WALL_HANGING_SIGN);
    }

    // HOLLOWED LOGS

    public static final Block HOLLOWED_OAK_LOG = createHollowedLogBlock(MapColor.WOOD, MapColor.PODZOL);
    public static final Block HOLLOWED_SPRUCE_LOG = createHollowedLogBlock(MapColor.PODZOL, MapColor.COLOR_BROWN);
    public static final Block HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MapColor.SAND, MapColor.QUARTZ);
    public static final Block HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MapColor.DIRT, MapColor.PODZOL);
    public static final Block HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MapColor.COLOR_ORANGE, MapColor.STONE);
    public static final Block HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN);
    public static final Block HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MapColor.COLOR_RED, MapColor.PODZOL);
	public static final Block HOLLOWED_CHERRY_LOG = createHollowedLogBlock(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, RegisterBlockSoundTypes.HOLLOWED_CHERRY_LOG);
	public static final Block HOLLOWED_CRIMSON_STEM = createHollowedStemBlock(MapColor.CRIMSON_STEM);
	public static final Block HOLLOWED_WARPED_STEM = createHollowedStemBlock(MapColor.WARPED_STEM);
    public static final Block HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MapColor.COLOR_ORANGE, MapColor.COLOR_BROWN);
    public static final Block HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MapColor.COLOR_LIGHT_GRAY, MapColor.STONE);
	public static final Block HOLLOWED_PALM_LOG = createHollowedLogBlock(PALM_PLANKS_COLOR, PALM_BARK_COLOR);

	// STRIPPED HOLLOWED LOGS
	public static final Block STRIPPED_HOLLOWED_OAK_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_OAK_LOG.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_SPRUCE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_SPRUCE_LOG.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_BIRCH_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_BIRCH_LOG.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_CHERRY_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_CHERRY_LOG.defaultMapColor(), RegisterBlockSoundTypes.HOLLOWED_CHERRY_LOG);
	public static final Block STRIPPED_HOLLOWED_JUNGLE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_JUNGLE_LOG.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_ACACIA_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_ACACIA_LOG.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_DARK_OAK_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_DARK_OAK_LOG.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_MANGROVE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_MANGROVE_LOG.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_CRIMSON_STEM = createStrippedHollowedStemBlock(Blocks.STRIPPED_CRIMSON_STEM.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_WARPED_STEM = createStrippedHollowedStemBlock(Blocks.STRIPPED_WARPED_STEM.defaultMapColor());
	public static final Block STRIPPED_HOLLOWED_BAOBAB_LOG = createStrippedHollowedLogBlock(BAOBAB_PLANKS_COLOR);
	public static final Block STRIPPED_HOLLOWED_CYPRESS_LOG = createStrippedHollowedLogBlock(CYPRESS_PLANKS_COLOR);
	public static final Block STRIPPED_HOLLOWED_PALM_LOG = createStrippedHollowedLogBlock(PALM_PLANKS_COLOR);

    public static void registerHollowedLogs() {
		registerBlockAfter(Items.OAK_LOG, "hollowed_oak_log", HOLLOWED_OAK_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_OAK_LOG, "stripped_hollowed_oak_log", STRIPPED_HOLLOWED_OAK_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.SPRUCE_LOG, "hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_SPRUCE_LOG, "stripped_hollowed_spruce_log", STRIPPED_HOLLOWED_SPRUCE_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.BIRCH_LOG, "hollowed_birch_log", HOLLOWED_BIRCH_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_BIRCH_LOG, "stripped_hollowed_birch_log", STRIPPED_HOLLOWED_BIRCH_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.JUNGLE_LOG, "hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_JUNGLE_LOG, "stripped_hollowed_jungle_log", STRIPPED_HOLLOWED_JUNGLE_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.ACACIA_LOG, "hollowed_acacia_log", HOLLOWED_ACACIA_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_ACACIA_LOG, "stripped_hollowed_acacia_log", STRIPPED_HOLLOWED_ACACIA_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.DARK_OAK_LOG, "hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_DARK_OAK_LOG, "stripped_hollowed_dark_oak_log", STRIPPED_HOLLOWED_DARK_OAK_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.CRIMSON_STEM, "hollowed_crimson_stem", HOLLOWED_CRIMSON_STEM, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_CRIMSON_STEM, "stripped_hollowed_crimson_stem", STRIPPED_HOLLOWED_CRIMSON_STEM, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.WARPED_STEM, "hollowed_warped_stem", HOLLOWED_WARPED_STEM, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_WARPED_STEM, "stripped_hollowed_warped_stem", STRIPPED_HOLLOWED_WARPED_STEM, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.MANGROVE_LOG, "hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_MANGROVE_LOG, "stripped_hollowed_mangrove_log", STRIPPED_HOLLOWED_MANGROVE_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockAfter(Items.CHERRY_LOG, "hollowed_cherry_log", HOLLOWED_CHERRY_LOG, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_CHERRY_LOG, "stripped_hollowed_cherry_log", STRIPPED_HOLLOWED_CHERRY_LOG, CreativeModeTabs.BUILDING_BLOCKS);

		registerBlockBefore(BAOBAB_WOOD, "hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_BAOBAB_LOG, "stripped_hollowed_baobab_log", STRIPPED_HOLLOWED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_LOG, "hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockBefore(CYPRESS_WOOD, "hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_CYPRESS_LOG, "stripped_hollowed_cypress_log", STRIPPED_HOLLOWED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_LOG, "hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockBefore(PALM_WOOD, "hollowed_palm_log", HOLLOWED_PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(HOLLOWED_PALM_LOG, "stripped_hollowed_palm_log", STRIPPED_HOLLOWED_PALM_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(PALM_CROWN, "hollowed_palm_log", HOLLOWED_PALM_LOG, CreativeModeTabs.NATURAL_BLOCKS);
    }

    // SCULK
    public static final Block SCULK_STAIRS = new SculkStairsBlock(Blocks.SCULK.defaultBlockState(), FabricBlockSettings.copyOf(Blocks.SCULK));
    public static final Block SCULK_SLAB = new SculkSlabBlock(FabricBlockSettings.copyOf(Blocks.SCULK));
    public static final Block SCULK_WALL = new SculkWallBlock(FabricBlockSettings.copyOf(Blocks.SCULK));
    public static final Block OSSEOUS_SCULK = new OsseousSculkBlock(FabricBlockSettings.of().mapColor(MapColor.SAND).requiresTool().strength(2.0F).sounds(RegisterBlockSoundTypes.OSSEOUS_SCULK));
    public static final Block HANGING_TENDRIL = new HangingTendrilBlock(FabricBlockSettings.copyOf(Blocks.SCULK_SENSOR).strength(0.7F).collidable(false).ticksRandomly().luminance((state) -> 1)
            .sounds(RegisterBlockSoundTypes.HANGING_TENDRIL).emissiveLighting((state, level, pos) -> HangingTendrilBlock.shouldHavePogLighting(state)));
    public static final Block ECHO_GLASS = new EchoGlassBlock(FabricBlockSettings.of().mapColor(MapColor.COLOR_CYAN).strength(0.3F).nonOpaque().ticksRandomly().sounds(RegisterBlockSoundTypes.ECHO_GLASS));

    public static void registerDeepDark() {
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL,"sculk_wall", SCULK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL,"sculk_slab", SCULK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL,"sculk_stairs", SCULK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL,"osseous_sculk", OSSEOUS_SCULK, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.SCULK,"osseous_sculk", OSSEOUS_SCULK, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlockAfter(Items.SCULK_SENSOR,"hanging_tendril", HANGING_TENDRIL, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlock(false, "echo_glass", ECHO_GLASS);
    }

    private static boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    // Mesoglea
    public static final Block BLUE_PEARLESCENT_MESOGLEA = new MesogleaBlock(mesoglea(MapColor.QUARTZ), RegisterParticles.BLUE_PEARLESCENT_HANGING_MESOGLEA);
    public static final Block PURPLE_PEARLESCENT_MESOGLEA = new MesogleaBlock(mesoglea(MapColor.COLOR_PURPLE), RegisterParticles.PURPLE_PEARLESCENT_HANGING_MESOGLEA);
    public static final Block YELLOW_MESOGLEA = new MesogleaBlock(mesoglea(MapColor.COLOR_YELLOW), RegisterParticles.YELLOW_HANGING_MESOGLEA);
    public static final Block BLUE_MESOGLEA = new MesogleaBlock(mesoglea(MapColor.COLOR_LIGHT_BLUE), RegisterParticles.BLUE_HANGING_MESOGLEA);
    public static final Block LIME_MESOGLEA = new MesogleaBlock(mesoglea(MapColor.COLOR_LIGHT_GREEN), RegisterParticles.LIME_HANGING_MESOGLEA);
    public static final Block RED_MESOGLEA = new MesogleaBlock(mesoglea(MapColor.COLOR_RED), RegisterParticles.RED_HANGING_MESOGLEA);
    public static final Block PINK_MESOGLEA = new MesogleaBlock(mesoglea(MapColor.COLOR_PINK), RegisterParticles.PINK_HANGING_MESOGLEA);

    public static final Block BLUE_PEARLESCENT_NEMATOCYST = new NematocystBlock(nematocyst(MapColor.QUARTZ));
    public static final Block PURPLE_PEARLESCENT_NEMATOCYST = new NematocystBlock(nematocyst(MapColor.COLOR_PURPLE));
    public static final Block YELLOW_NEMATOCYST = new NematocystBlock(nematocyst(MapColor.COLOR_YELLOW));
    public static final Block BLUE_NEMATOCYST = new NematocystBlock(nematocyst(MapColor.COLOR_LIGHT_BLUE));
    public static final Block LIME_NEMATOCYST = new NematocystBlock(nematocyst(MapColor.COLOR_LIGHT_GREEN));
    public static final Block RED_NEMATOCYST = new NematocystBlock(nematocyst(MapColor.COLOR_RED));
    public static final Block PINK_NEMATOCYST = new NematocystBlock(nematocyst(MapColor.COLOR_PINK));

    // MISC

    public static final Block TERMITE_MOUND = new TermiteMound(FabricBlockSettings.of().mapColor(MapColor.COLOR_BROWN).strength(0.3F).sounds(RegisterBlockSoundTypes.TERMITEMOUND).ticksRandomly().burnable());
    public static final Block STONE_CHEST = new StoneChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST).sounds(SoundType.DEEPSLATE).strength(35.0F, 12.0F), () -> RegisterBlockEntities.STONE_CHEST);

    // PLANTS
    public static final Block SEEDING_DANDELION = new SeedingDandelionBlock(MobEffects.SLOW_FALLING, 12, FabricBlockSettings.of().noCollision().breakInstantly().sounds(SoundType.GRASS).offset(BlockBehaviour.OffsetType.XZ).pistonBehavior(PushReaction.DESTROY));
    public static final Block POTTED_SEEDING_DANDELION = new FlowerPotBlock(RegisterBlocks.SEEDING_DANDELION, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PushReaction.DESTROY));
    public static final Block CARNATION = new FlowerBlock(MobEffects.REGENERATION, 12, FabricBlockSettings.of().noCollision().breakInstantly().sounds(SoundType.GRASS).offset(BlockBehaviour.OffsetType.XZ).pistonBehavior(PushReaction.DESTROY));
    public static final Block POTTED_CARNATION = new FlowerPotBlock(RegisterBlocks.CARNATION, FabricBlockSettings.of().breakInstantly().nonOpaque().pistonBehavior(PushReaction.DESTROY));
    public static final Block GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(FabricBlockSettings.of().noCollision().breakInstantly().sounds(SoundType.GRASS).offset(BlockBehaviour.OffsetType.XZ).ticksRandomly().pistonBehavior(PushReaction.DESTROY), List.of(FlowerColor.BLUE, FlowerColor.PINK, FlowerColor.PURPLE, FlowerColor.WHITE));

    public static final Block WHITE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().offset(BlockBehaviour.OffsetType.NONE).mapColor(MapColor.QUARTZ).sound(SoundType.VINE));
    public static final Block PINK_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().offset(BlockBehaviour.OffsetType.NONE).mapColor(MapColor.CRIMSON_STEM).sound(SoundType.VINE));
    public static final Block PURPLE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().offset(BlockBehaviour.OffsetType.NONE).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.VINE));
    public static final Block BLUE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().offset(BlockBehaviour.OffsetType.NONE).mapColor(MapColor.COLOR_BLUE).sound(SoundType.VINE));

    public static final Block DATURA = new TallFlowerBlock(FabricBlockSettings.of().mapColor(MapColor.PLANT).noCollision().breakInstantly().sounds(SoundType.GRASS).burnable().pistonBehavior(PushReaction.DESTROY).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final Block MILKWEED = new MilkweedBlock(FabricBlockSettings.of().noCollision().breakInstantly().ticksRandomly().sounds(SoundType.GRASS).burnable().pistonBehavior(PushReaction.DESTROY).offsetType(BlockBehaviour.OffsetType.XZ));

    public static final Block CATTAIL = new WaterloggableTallFlowerBlock(FabricBlockSettings.copyOf(Blocks.ROSE_BUSH).sounds(SoundType.WET_GRASS).strength(0.0F).nonOpaque());
    public static final Block FLOWERING_LILY_PAD = new FloweringLilyPadBlock(FabricBlockSettings.copyOf(Blocks.LILY_PAD).sounds(SoundType.LILY_PAD));
    public static final Block ALGAE = new AlgaeBlock(FabricBlockSettings.of().mapColor(MapColor.PLANT).breakInstantly().nonOpaque().noCollision().sounds(RegisterBlockSoundTypes.ALGAE).pistonBehavior(PushReaction.DESTROY));
	public static final Block BUSH = new WWBushBlock(FabricBlockSettings.of().mapColor(MapColor.PLANT).breakInstantly().nonOpaque().noCollision().ticksRandomly().sounds(SoundType.GRASS).offset(BlockBehaviour.OffsetType.XZ).pistonBehavior(PushReaction.DESTROY));
	public static final Block POTTED_BUSH = new FlowerPotBlock(BUSH, flowerPot());
	public static final Block TUMBLEWEED_PLANT = new TumbleweedPlantBlock(FabricBlockSettings.of().mapColor(MapColor.PLANT).nonOpaque().sounds(RegisterBlockSoundTypes.TUMBLEWEED_PLANT).ticksRandomly().pistonBehavior(PushReaction.DESTROY));
	public static final Block POTTED_TUMBLEWEED_PLANT = new FlowerPotBlock(TUMBLEWEED_PLANT, flowerPot());
	public static final Block TUMBLEWEED = new TumbleweedBlock(FabricBlockSettings.of().mapColor(MapColor.PLANT).breakInstantly().nonOpaque().sounds(RegisterBlockSoundTypes.TUMBLEWEED_PLANT).ticksRandomly().pistonBehavior(PushReaction.DESTROY));
	public static final Block POTTED_TUMBLEWEED = new FlowerPotBlock(TUMBLEWEED, flowerPot());

    public static final Block POTTED_BIG_DRIPLEAF = new FlowerPotBlock(Blocks.BIG_DRIPLEAF, flowerPot());
    public static final Block POTTED_SMALL_DRIPLEAF = new FlowerPotBlock(Blocks.SMALL_DRIPLEAF, flowerPot());
    public static final Block POTTED_GRASS = new FlowerPotBlock(Blocks.GRASS, flowerPot());
	public static final Block POTTED_PRICKLY_PEAR = new FlowerPotBlock(PRICKLY_PEAR_CACTUS, flowerPot());

    public static void registerPlants() {
        registerBlock(false, "potted_big_dripleaf", POTTED_BIG_DRIPLEAF);
        registerBlock(false, "potted_small_dripleaf", POTTED_SMALL_DRIPLEAF);
        registerBlock(false, "potted_grass", POTTED_GRASS);
        registerBlockAfter(Items.DANDELION,"seeding_dandelion", SEEDING_DANDELION, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlock(false, "potted_seeding_dandelion", POTTED_SEEDING_DANDELION);
        registerBlockAfter(Items.CORNFLOWER, "carnation", CARNATION, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlock(false, "potted_carnation", POTTED_CARNATION);
		registerBlockBefore(Items.WITHER_ROSE, "glory_of_the_snow", GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE,"blue_giant_glory_of_the_snow", BLUE_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE,"pink_giant_glory_of_the_snow", PINK_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE,"violet_beauty_glory_of_the_snow", PURPLE_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE,"alba_glory_of_the_snow", WHITE_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.PEONY, "datura", DATURA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(DATURA, "milkweed", MILKWEED, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(MILKWEED, "cattail", CATTAIL, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.CACTUS, "tumbleweed_plant", TUMBLEWEED_PLANT, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock(false, "potted_tumbleweed_plant", POTTED_TUMBLEWEED_PLANT);
		registerBlockAfter(TUMBLEWEED_PLANT, "tumbleweed", TUMBLEWEED, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock(false, "potted_tumbleweed", POTTED_TUMBLEWEED);
		registerBlockAfter(TUMBLEWEED, "bush", BUSH, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock(false, "potted_bush", POTTED_BUSH);
		registerBlock(false, "prickly_pear", PRICKLY_PEAR_CACTUS);
		registerBlock(false, "potted_prickly_pear", POTTED_PRICKLY_PEAR);
    }

    public static final Block BROWN_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance(1).ticksRandomly().collidable(false).nonOpaque().sounds(RegisterBlockSoundTypes.MUSHROOM));
    public static final Block RED_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).ticksRandomly().collidable(false).nonOpaque().sounds(RegisterBlockSoundTypes.MUSHROOM));
    public static final Block POLLEN_BLOCK = new PollenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offset(BlockBehaviour.OffsetType.NONE).mapColor(MapColor.SAND).sounds(RegisterBlockSoundTypes.POLLEN));
	//TODO: Rename & Sponge Sounds
	public static final Block SMALL_SPONGE = new SmallSpongeBlock(FabricBlockSettings.copyOf(Blocks.SPONGE).strength(0.1F).collidable(false).nonOpaque().sounds(SoundType.WET_GRASS));

    public static void registerNotSoPlants() {
        registerBlock(false, "pollen", POLLEN_BLOCK);
		registerBlockAfter(Items.RED_MUSHROOM, "red_shelf_fungus", RED_SHELF_FUNGUS, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.RED_MUSHROOM, "brown_shelf_fungus", BROWN_SHELF_FUNGUS, CreativeModeTabs.NATURAL_BLOCKS);
		Registry.register(BuiltInRegistries.BLOCK, WilderSharedConstants.id("algae"), ALGAE);
		Registry.register(BuiltInRegistries.BLOCK, WilderSharedConstants.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
		registerBlockAfter(Items.WET_SPONGE, "small_sponge", SMALL_SPONGE, CreativeModeTabs.NATURAL_BLOCKS);
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

	public static final BlockFamily PALM = BlockFamilies.familyBuilder(PALM_PLANKS)
			.button(PALM_BUTTON)
			.slab(PALM_SLAB)
			.stairs(PALM_STAIRS)
			.fence(PALM_FENCE)
			.fenceGate(PALM_FENCE_GATE)
			.pressurePlate(PALM_PRESSURE_PLATE)
			.sign(PALM_SIGN_BLOCK, PALM_WALL_SIGN)
			.door(PALM_DOOR)
			.trapdoor(PALM_TRAPDOOR)
			.recipeGroupPrefix("wooden")
			.recipeUnlockedBy("has_planks")
			.getFamily();

    public static final Block NULL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE).sounds(RegisterBlockSoundTypes.NULL_BLOCK));

    public static final Block DISPLAY_LANTERN = new DisplayLanternBlock(FabricBlockSettings.of().strength(3.5f).sounds(SoundType.LANTERN).luminance((state) -> state.getValue(RegisterProperties.DISPLAY_LIGHT)).forceSolidOn().pushReaction(PushReaction.DESTROY));

    public static void registerMisc() {
        registerBlockBefore(Items.BEE_NEST, "termite_mound", TERMITE_MOUND, CreativeModeTabs.NATURAL_BLOCKS);
        registerBlockBefore(Items.GLASS, "null_block", NULL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS);
        registerBlockAfter(Items.CHEST, "stone_chest", STONE_CHEST, CreativeModeTabs.FUNCTIONAL_BLOCKS);
        registerBlockAfter(Items.SOUL_LANTERN, "display_lantern", DISPLAY_LANTERN, CreativeModeTabs.FUNCTIONAL_BLOCKS);

        registerBlockBefore(Items.SPONGE, "blue_pearlescent_mesoglea", BLUE_PEARLESCENT_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "purple_pearlescent_mesoglea", PURPLE_PEARLESCENT_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "blue_mesoglea", BLUE_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "pink_mesoglea", PINK_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "red_mesoglea", RED_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "yellow_mesoglea", YELLOW_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "lime_mesoglea", LIME_MESOGLEA, CreativeModeTabs.NATURAL_BLOCKS);

		registerBlockBefore(Items.SPONGE, "blue_pearlescent_nematocyst", BLUE_PEARLESCENT_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "purple_pearlescent_nematocyst", PURPLE_PEARLESCENT_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "blue_nematocyst", BLUE_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "pink_nematocyst", PINK_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "red_nematocyst", RED_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "yellow_nematocyst", YELLOW_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.SPONGE, "lime_nematocyst", LIME_NEMATOCYST, CreativeModeTabs.NATURAL_BLOCKS);
    }

    public static void registerBlocks() {
        WilderSharedConstants.logWild("Registering Blocks for", WilderSharedConstants.UNSTABLE_LOGGING);

        registerOtherBB();
        registerWoods();
        registerHollowedLogs();
        registerDeepDark();
        registerPlants();
        registerNotSoPlants();
        registerMisc();
    }

	public static void registerDispenses() {
		DispenserBlock.registerBehavior(RegisterItems.COCONUT, new AbstractProjectileDispenseBehavior() {
			protected Projectile getProjectile(@NotNull Level level, @NotNull Position position, @NotNull ItemStack stack) {
				return new CoconutProjectile(level, position.x(), position.y(), position.z());
			}
			protected float getUncertainty() {
				return 9.0F;
			}
			protected float getPower() {
				return 0.75F;
			}
		});
		DispenserBlock.registerBehavior(RegisterBlocks.TUMBLEWEED, new DefaultDispenseItemBehavior() {
			public ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
				Level level = source.getLevel();
				Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
				double d = source.x() + (double) direction.getStepX();
				double e = source.y() + (double) direction.getStepY();
				double f = source.z() + (double) direction.getStepZ();
				Position position = new PositionImpl(d, e, f);
				Tumbleweed tumbleweed = new Tumbleweed(RegisterEntities.TUMBLEWEED, level);
				Vec3 vec3 = (new Vec3(direction.getStepX(), direction.getStepY() + 0.1, direction.getStepZ())).normalize().add(level.random.triangle(0.0D, 0.0172275D * (double)6), level.random.triangle(0.0D, 0.0172275D * (double)6), level.random.triangle(0.0D, 0.0172275D * (double)6)).scale(1.1);
				tumbleweed.setDeltaMovement(vec3);
				tumbleweed.setPos(position.x(), position.y(), position.z());
				level.addFreshEntity(tumbleweed);
				stack.shrink(1);
				return stack;
			}
		});
	}

    private static void registerBlock(boolean registerBlockItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		if (registerBlockItem) {
			registerBlockItem(path, block, tabs);
		}
        actualRegisterBlock(path, block);
    }

	private static void registerBlockBefore(ItemLike comparedItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemBefore(comparedItem, path, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
		actualRegisterBlock(path, block);
	}

	private static void registerBlockAfter(ItemLike comparedItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemAfter(comparedItem, path, block, tabs);
		actualRegisterBlock(path, block);
	}

    private static void registerBlockItem(String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.add(block, tabs);
    }

	private static void registerBlockItemBefore(ItemLike comparedItem, String name, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemBefore(comparedItem, name, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	private static void registerBlockItemBefore(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility tabVisibility, ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addBefore(comparedItem, block, tabVisibility, tabs);
	}

	private static void registerBlockItemAfter(ItemLike comparedItem, String name, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemAfter(comparedItem, name, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	private static void registerBlockItemAfter(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility visibility, ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addAfter(comparedItem, block, visibility, tabs);
	}

	private static void actualRegisterBlock(String path, Block block) {
		if (BuiltInRegistries.BLOCK.getOptional(WilderSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.BLOCK, WilderSharedConstants.id(path), block);
		}
	}

	private static void actualRegisterBlockItem(String path, Block block) {
		if (BuiltInRegistries.ITEM.getOptional(WilderSharedConstants.id(path)).isEmpty()) {
			Registry.register(BuiltInRegistries.ITEM, WilderSharedConstants.id(path), new BlockItem(block, new FabricItemSettings()));
		}
	}

	private static FabricBlockSettings mesoglea(MapColor mapColor) {
		return FabricBlockSettings.of()
			.mapColor(mapColor)
			.notSolid()
			.strength(0.2F)
			.slipperiness(0.8F)
			.emissiveLighting(RegisterBlocks::always)
			.luminance((state) -> 7)
			.sounds(RegisterBlockSoundTypes.MESOGLEA)
			.suffocates(RegisterBlocks::never)
			.blockVision(RegisterBlocks::never)
			.dynamicBounds();
	}

	private static FabricBlockSettings nematocyst(MapColor mapColor) {
		return FabricBlockSettings.of()
			.mapColor(mapColor)
			.notSolid()
			.noCollision()
			.nonOpaque()
			.emissiveLighting(RegisterBlocks::always)
			.luminance((state) -> 4)
			.sounds(RegisterBlockSoundTypes.NEMATOCYST);
	}

	private static FabricBlockSettings log(MapColor mapColor, MapColor mapColor2) {
		return FabricBlockSettings.of()
			.mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? mapColor : mapColor2)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sounds(SoundType.WOOD)
			.burnable();
	}

	private static FabricBlockSettings wood(MapColor mapColor) {
		return FabricBlockSettings.of()
			.mapColor(mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sounds(SoundType.WOOD)
			.burnable();
	}

	private static FabricBlockSettings slab(MapColor mapColor) {
		return FabricBlockSettings.of()
			.mapColor(mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.sounds(SoundType.WOOD)
			.burnable();
	}

	private static FabricBlockSettings fence(MapColor mapColor) {
		return FabricBlockSettings.of()
			.mapColor(mapColor)
			.solid()
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.sounds(SoundType.WOOD)
			.burnable();
	}

	private static FabricBlockSettings flowerPot() {
		return FabricBlockSettings.of()
			.breakInstantly()
			.nonOpaque()
			.pistonBehavior(PushReaction.DESTROY);
	}

	private static FabricBlockSettings leaves(SoundType type) {
		return FabricBlockSettings.of()
			.mapColor(MapColor.PLANT)
			.strength(0.2F)
			.ticksRandomly()
			.sounds(type)
			.nonOpaque()
			.allowsSpawning(RegisterBlocks::ocelotOrParrot)
			.suffocates(RegisterBlocks::never)
			.blockVision(RegisterBlocks::never)
			.burnable()
			.pistonBehavior(PushReaction.DESTROY)
			.solidBlock(RegisterBlocks::never);
	}

    private static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor, SoundType soundType) {
        var settings = log(topMapColor, sideMapColor).sounds(soundType);

		return new HollowedLogBlock(settings);
    }

	private static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor) {
		return createHollowedLogBlock(topMapColor, sideMapColor, RegisterBlockSoundTypes.HOLLOWED_LOG);
	}

	private static HollowedLogBlock createHollowedStemBlock(MapColor mapColor) {
		return new HollowedLogBlock(wood(mapColor).sounds(RegisterBlockSoundTypes.HOLLOWED_STEM));
	}

	private static HollowedLogBlock createStrippedHollowedLogBlock(MapColor mapColor, SoundType soundType) {
		var settings = wood(mapColor).sounds(soundType);

		return new HollowedLogBlock(settings);
	}

	private static HollowedLogBlock createStrippedHollowedLogBlock(MapColor mapColor) {
		return createStrippedHollowedLogBlock(mapColor, RegisterBlockSoundTypes.HOLLOWED_LOG);
	}

	private static HollowedLogBlock createStrippedHollowedStemBlock(MapColor mapColor) {
		return new HollowedLogBlock(wood(mapColor).sounds(RegisterBlockSoundTypes.HOLLOWED_STEM));
	}

    public static void registerBlockProperties() {
        TermiteManager.Termite.addDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteManager.Termite.addDegradable(STRIPPED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
		TermiteManager.Termite.addDegradable(HOLLOWED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
        TermiteManager.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        TermiteManager.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteManager.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

		TermiteManager.Termite.addDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
		TermiteManager.Termite.addDegradable(STRIPPED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		TermiteManager.Termite.addDegradable(HOLLOWED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		TermiteManager.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

        TermiteManager.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        TermiteManager.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

		TermiteManager.Termite.addDegradable(PALM_CROWN, PALM_LOG);
		TermiteManager.Termite.addDegradable(PALM_LOG, STRIPPED_PALM_LOG);
		TermiteManager.Termite.addDegradable(STRIPPED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
		TermiteManager.Termite.addDegradable(HOLLOWED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
		TermiteManager.Termite.addDegradable(PALM_WOOD, STRIPPED_PALM_WOOD);

		TermiteManager.Termite.addNaturalDegradable(PALM_LOG, STRIPPED_PALM_LOG);
		TermiteManager.Termite.addNaturalDegradable(PALM_WOOD, STRIPPED_PALM_WOOD);

		TermiteManager.Termite.addDegradable(BUSH, Blocks.DEAD_BUSH);

        registerStrippable();
        registerComposting();
        registerFlammability();
        registerFuels();
        registerBonemeal();
		registerAxe();
    }

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static boolean ocelotOrParrot(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static void registerStrippable() {
        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
		StrippableBlockRegistry.register(PALM_LOG, STRIPPED_PALM_LOG);
		StrippableBlockRegistry.register(PALM_WOOD, STRIPPED_PALM_WOOD);

		StrippableBlockRegistry.register(HOLLOWED_ACACIA_LOG, STRIPPED_HOLLOWED_ACACIA_LOG);
		StrippableBlockRegistry.register(HOLLOWED_BIRCH_LOG, STRIPPED_HOLLOWED_BIRCH_LOG);
		StrippableBlockRegistry.register(HOLLOWED_CHERRY_LOG, STRIPPED_HOLLOWED_CHERRY_LOG);
		StrippableBlockRegistry.register(HOLLOWED_DARK_OAK_LOG, STRIPPED_HOLLOWED_DARK_OAK_LOG);
		StrippableBlockRegistry.register(HOLLOWED_OAK_LOG, STRIPPED_HOLLOWED_OAK_LOG);
		StrippableBlockRegistry.register(HOLLOWED_SPRUCE_LOG, STRIPPED_HOLLOWED_SPRUCE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_JUNGLE_LOG, STRIPPED_HOLLOWED_JUNGLE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_MANGROVE_LOG, STRIPPED_HOLLOWED_MANGROVE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_CRIMSON_STEM, STRIPPED_HOLLOWED_CRIMSON_STEM);
		StrippableBlockRegistry.register(HOLLOWED_WARPED_STEM, STRIPPED_HOLLOWED_WARPED_STEM);
		StrippableBlockRegistry.register(HOLLOWED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		StrippableBlockRegistry.register(HOLLOWED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
		StrippableBlockRegistry.register(HOLLOWED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
    }

    private static void registerComposting() {
        CompostingChanceRegistry.INSTANCE.add(CARNATION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CATTAIL, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(DATURA, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(MILKWEED, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(SEEDING_DANDELION, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(FLOWERING_LILY_PAD, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BROWN_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(RED_SHELF_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(BLUE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(WHITE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PINK_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(PURPLE_GLORY_OF_THE_SNOW, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ALGAE, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(BUSH, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED_PLANT, 0.5F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(RegisterItems.COCONUT, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(RegisterItems.SPLIT_COCONUT, 0.3F);
    }

    private static void registerFlammability() {
        WilderSharedConstants.logWild("Registering Flammability for", WilderSharedConstants.UNSTABLE_LOGGING);
		var flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
        flammableBlockRegistry.add(RegisterBlocks.POLLEN_BLOCK, 100, 60);
        flammableBlockRegistry.add(RegisterBlocks.SEEDING_DANDELION, 100, 60);
        flammableBlockRegistry.add(RegisterBlocks.CARNATION, 100, 60);
        flammableBlockRegistry.add(RegisterBlocks.CATTAIL, 100, 60);
        flammableBlockRegistry.add(RegisterBlocks.DATURA, 100, 60);
        flammableBlockRegistry.add(RegisterBlocks.MILKWEED, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.BLUE_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.PINK_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.TUMBLEWEED, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.TUMBLEWEED_PLANT, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.BUSH, 90, 40);

        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_BIRCH_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_CHERRY_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_OAK_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_ACACIA_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_JUNGLE_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_MANGROVE_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_CHERRY_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_SPRUCE_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, 5, 5);

        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.STRIPPED_BAOBAB_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_WOOD, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.STRIPPED_BAOBAB_WOOD, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_PLANKS, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_STAIRS, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_DOOR, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_FENCE, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_SLAB, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_FENCE_GATE, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_PRESSURE_PLATE, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_TRAPDOOR, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_LEAVES, 100, 60);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_BUTTON, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_SIGN_BLOCK, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.BAOBAB_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.BAOBAB_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.BAOBAB_WALL_HANGING_SIGN, 5, 20);

        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.STRIPPED_CYPRESS_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_WOOD, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.STRIPPED_CYPRESS_WOOD, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_PLANKS, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_STAIRS, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_DOOR, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_FENCE, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_SLAB, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_FENCE_GATE, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_PRESSURE_PLATE, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_TRAPDOOR, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_LEAVES, 100, 60);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_BUTTON, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_SIGN_BLOCK, 5, 20);
        flammableBlockRegistry.add(RegisterBlocks.CYPRESS_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.CYPRESS_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.CYPRESS_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.PALM_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.PALM_CROWN, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.PALM_WOOD, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_PALM_WOOD, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.PALM_PLANKS, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_STAIRS, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_DOOR, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_FENCE, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_SLAB, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_FRONDS, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.PALM_BUTTON, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_SIGN_BLOCK, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_WALL_HANGING_SIGN, 5, 20);
    }

    private static void registerFuels() {
        WilderSharedConstants.logWild("Registering Fuels for", WilderSharedConstants.UNSTABLE_LOGGING);
        FuelRegistry registry = FuelRegistry.INSTANCE;

		registry.add(RegisterItems.BAOBAB_BOAT_ITEM, 1200);
		registry.add(RegisterItems.BAOBAB_CHEST_BOAT_ITEM, 1200);
		registry.add(BAOBAB_LOG.asItem(), 300);
		registry.add(STRIPPED_BAOBAB_LOG.asItem(), 300);
		registry.add(BAOBAB_WOOD.asItem(), 300);
        registry.add(STRIPPED_BAOBAB_WOOD.asItem(), 300);
		registry.add(BAOBAB_PLANKS.asItem(), 300);
		registry.add(BAOBAB_SLAB.asItem(), 150);
		registry.add(BAOBAB_STAIRS.asItem(), 300);
		registry.add(BAOBAB_PRESSURE_PLATE.asItem(), 300);
		registry.add(BAOBAB_BUTTON.asItem(), 100);
		registry.add(BAOBAB_TRAPDOOR.asItem(), 300);
		registry.add(BAOBAB_FENCE_GATE.asItem(), 300);
		registry.add(BAOBAB_FENCE.asItem(), 300);
		registry.add(RegisterItems.BAOBAB_SIGN, 300);
		registry.add(RegisterItems.BAOBAB_NUT, 100);

		registry.add(RegisterItems.CYPRESS_BOAT_ITEM, 1200);
		registry.add(RegisterItems.CYPRESS_CHEST_BOAT_ITEM, 1200);
		registry.add(CYPRESS_LOG.asItem(), 300);
		registry.add(STRIPPED_CYPRESS_LOG.asItem(), 300);
		registry.add(CYPRESS_WOOD.asItem(), 300);
		registry.add(STRIPPED_CYPRESS_WOOD.asItem(), 300);
		registry.add(CYPRESS_PLANKS.asItem(), 300);
		registry.add(CYPRESS_SLAB.asItem(), 150);
		registry.add(CYPRESS_STAIRS.asItem(), 300);
		registry.add(CYPRESS_PRESSURE_PLATE.asItem(), 300);
		registry.add(CYPRESS_BUTTON.asItem(), 100);
		registry.add(CYPRESS_TRAPDOOR.asItem(), 300);
		registry.add(CYPRESS_FENCE_GATE.asItem(), 300);
		registry.add(CYPRESS_FENCE.asItem(), 300);
		registry.add(RegisterItems.CYPRESS_SIGN, 300);
		registry.add(CYPRESS_SAPLING.asItem(), 100);

		registry.add(RegisterItems.PALM_BOAT_ITEM, 1200);
		registry.add(RegisterItems.PALM_CHEST_BOAT_ITEM, 1200);
		registry.add(PALM_LOG.asItem(), 300);
		registry.add(STRIPPED_PALM_LOG.asItem(), 300);
		registry.add(PALM_WOOD.asItem(), 300);
		registry.add(STRIPPED_PALM_WOOD.asItem(), 300);
		registry.add(PALM_PLANKS.asItem(), 300);
		registry.add(PALM_SLAB.asItem(), 150);
		registry.add(PALM_STAIRS.asItem(), 300);
		registry.add(PALM_PRESSURE_PLATE.asItem(), 300);
		registry.add(PALM_BUTTON.asItem(), 100);
		registry.add(PALM_TRAPDOOR.asItem(), 300);
		registry.add(PALM_FENCE_GATE.asItem(), 300);
		registry.add(PALM_FENCE.asItem(), 300);
		registry.add(RegisterItems.PALM_SIGN, 300);
		registry.add(RegisterItems.COCONUT, 150); // COCONUT OIL IS KNOWN TO BE FLAMMABLE :)
		registry.add(RegisterItems.SPLIT_COCONUT, 75);

		registry.add(HOLLOWED_WARPED_STEM.asItem(), 300);
		registry.add(HOLLOWED_CRIMSON_STEM.asItem(), 300);
		registry.add(HOLLOWED_MANGROVE_LOG.asItem(), 300);
		registry.add(HOLLOWED_ACACIA_LOG.asItem(), 300);
		registry.add(HOLLOWED_JUNGLE_LOG.asItem(), 300);
		registry.add(HOLLOWED_DARK_OAK_LOG.asItem(), 300);
		registry.add(HOLLOWED_SPRUCE_LOG.asItem(), 300);
		registry.add(HOLLOWED_CHERRY_LOG.asItem(), 300);
		registry.add(HOLLOWED_BIRCH_LOG.asItem(), 300);
		registry.add(HOLLOWED_BAOBAB_LOG.asItem(), 300);
		registry.add(HOLLOWED_CYPRESS_LOG.asItem(), 300);
		registry.add(HOLLOWED_PALM_LOG.asItem(), 300);

		registry.add(STRIPPED_HOLLOWED_WARPED_STEM.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_CRIMSON_STEM.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_MANGROVE_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_ACACIA_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_JUNGLE_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_DARK_OAK_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_SPRUCE_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_CHERRY_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_BIRCH_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_BAOBAB_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_CYPRESS_LOG.asItem(), 300);
		registry.add(STRIPPED_HOLLOWED_PALM_LOG.asItem(), 300);

		registry.add(TUMBLEWEED.asItem(), 150);
		registry.add(TUMBLEWEED_PLANT.asItem(), 150);
    }

    private static void registerBonemeal() {
        BonemealBehaviors.BONEMEAL_BEHAVIORS.put(Blocks.LILY_PAD, (context, level, pos, state, face, horizontal) -> {
            if (!level.isClientSide) {
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                level.setBlockAndUpdate(pos, FLOWERING_LILY_PAD.defaultBlockState());
                return true;
            }
            return false;
        });
		BonemealBehaviors.BONEMEAL_BEHAVIORS.put(Blocks.DANDELION, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide) {
				level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
				level.setBlockAndUpdate(pos, RegisterBlocks.SEEDING_DANDELION.defaultBlockState());
				return true;
			}
			return false;
		});
    }

	private static void registerAxe() {
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.OAK_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_OAK_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.BIRCH_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_BIRCH_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.CHERRY_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_CHERRY_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.SPRUCE_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_SPRUCE_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.DARK_OAK_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_DARK_OAK_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.JUNGLE_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_JUNGLE_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.ACACIA_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_ACACIA_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.MANGROVE_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_MANGROVE_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.CRIMSON_STEM, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_CRIMSON_STEM, true));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.WARPED_STEM, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_WARPED_STEM, true));
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.BAOBAB_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_BAOBAB_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.CYPRESS_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_CYPRESS_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.PALM_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_PALM_LOG, false));
		//STRIPPED
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_OAK_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_BIRCH_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_CHERRY_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_SPRUCE_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_DARK_OAK_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_JUNGLE_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_ACACIA_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_MANGROVE_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_CRIMSON_STEM, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, true));
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_WARPED_STEM, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM, true));
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.STRIPPED_BAOBAB_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.STRIPPED_CYPRESS_LOG, (context, level, pos, state, face, horizontal) ->
		HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, false));
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.STRIPPED_PALM_LOG, (context, level, pos, state, face, horizontal) ->
				HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG, false));
	}

}
