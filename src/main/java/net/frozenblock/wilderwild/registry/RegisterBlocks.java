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
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.object.builder.v1.sign.SignTypeRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.frozenblock.lib.axe.api.AxeBehaviors;
import net.frozenblock.lib.block.api.FrozenSignBlock;
import net.frozenblock.lib.block.api.FrozenWallSignBlock;
import net.frozenblock.lib.item.api.bonemeal.BonemealBehaviors;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.block.BaobabLeaves;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.BushBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.block.ScorchedSandBlock;
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
import net.frozenblock.wilderwild.block.PalmLeavesBlock;
import net.frozenblock.wilderwild.block.PollenBlock;
import net.frozenblock.wilderwild.block.PricklyPearCactusBlock;
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
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
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
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class RegisterBlocks {
	private RegisterBlocks() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

    private static final MaterialColor BAOBAB_PLANKS_COLOR = MaterialColor.COLOR_ORANGE;
    private static final MaterialColor BAOBAB_BARK_COLOR = MaterialColor.COLOR_BROWN;
    private static final MaterialColor CYPRESS_PLANKS_COLOR = MaterialColor.COLOR_LIGHT_GRAY;
    private static final MaterialColor CYPRESS_BARK_COLOR = MaterialColor.STONE;
    private static final MaterialColor PALM_PLANKS_COLOR = MaterialColor.TERRACOTTA_WHITE;
    private static final MaterialColor PALM_BARK_COLOR = MaterialColor.COLOR_LIGHT_GRAY;

    // OTHER (BUILDING BLOCKS)
    public static final Block CHISELED_MUD_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(1.5F).requiresTool().sounds(SoundType.MUD_BRICKS));
	public static final Block SCORCHED_SAND = new ScorchedSandBlock(FabricBlockSettings.of(Material.SAND).strength(1.5F).requiresTool().sounds(SoundType.SAND), Blocks.SAND.defaultBlockState(), 14406560);

    /**
     * Building Blocks
     */
    public static void registerOtherBB() {
        registerBlock("chiseled_mud_bricks", CHISELED_MUD_BRICKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
		registerBlock("scorched_sand", SCORCHED_SAND, CreativeModeTab.TAB_BUILDING_BLOCKS);
    }

    // WOOD
    public static final Block BAOBAB_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block PALM_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block PALM_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
	public static final Block PALM_CROWN = new PalmCrownBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(RegisterBlockSoundTypes.PALM_CROWN));

    public static final Block STRIPPED_BAOBAB_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_CYPRESS_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_PALM_LOG = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block STRIPPED_BAOBAB_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_CYPRESS_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block STRIPPED_PALM_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block CYPRESS_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final Block PALM_WOOD = new RotatedPillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final Block BAOBAB_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block PALM_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_STAIRS = new StairBlock(BAOBAB_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(BAOBAB_PLANKS));
    public static final Block CYPRESS_STAIRS = new StairBlock(CYPRESS_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(CYPRESS_PLANKS));
    public static final Block PALM_STAIRS = new StairBlock(PALM_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(PALM_PLANKS));

    public static final Block BAOBAB_BUTTON = new WoodButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).color(BAOBAB_PLANKS_COLOR));
    public static final Block CYPRESS_BUTTON = new WoodButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).color(CYPRESS_PLANKS_COLOR));
    public static final Block PALM_BUTTON = new WoodButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).color(PALM_PLANKS_COLOR));

    public static final Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).noCollision().strength(0.5F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).noCollision().strength(0.5F).sounds(SoundType.WOOD));
    public static final Block PALM_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).noCollision().strength(0.5F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque());
    public static final Block CYPRESS_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque());
    public static final Block PALM_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque());

    public static final Block BAOBAB_TRAPDOOR = new TrapDoorBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));
    public static final Block CYPRESS_TRAPDOOR = new TrapDoorBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));
    public static final Block PALM_TRAPDOOR = new TrapDoorBlock(FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(3.0F).sounds(SoundType.WOOD).nonOpaque().allowsSpawning(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block PALM_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final Block BAOBAB_NUT = new BaobabNutBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().breakInstantly().sounds(RegisterBlockSoundTypes.BAOBAB_NUT).offsetType(BlockBehaviour.OffsetType.XZ).dynamicShape());
    public static final Block POTTED_BAOBAB_NUT = new FlowerPotBlock(RegisterBlocks.BAOBAB_NUT, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block PRICKLY_PEAR_CACTUS = new PricklyPearCactusBlock(FabricBlockSettings.of(Material.CACTUS).ticksRandomly().strength(0.4F).sounds(SoundType.WOOL).nonOpaque().offsetType(BlockBehaviour.OffsetType.XZ));
	public static final Block CYPRESS_SAPLING = new WaterloggableSaplingBlock(new CypressSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    public static final Block POTTED_CYPRESS_SAPLING = new FlowerPotBlock(RegisterBlocks.CYPRESS_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static final Block COCONUT = new CoconutBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().ticksRandomly().sounds(RegisterBlockSoundTypes.COCONUT));
    public static final Block POTTED_COCONUT = new FlowerPotBlock(RegisterBlocks.COCONUT, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static final Block BAOBAB_LEAVES = new BaobabLeaves(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).ticksRandomly().sounds(SoundType.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block CYPRESS_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).ticksRandomly().sounds(SoundType.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));
    public static final Block PALM_LEAVES = new PalmLeavesBlock(FabricBlockSettings.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).ticksRandomly().sounds(SoundType.GRASS).nonOpaque().allowsSpawning(RegisterBlocks::canSpawnOnLeaves).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never));

    public static final Block BAOBAB_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block CYPRESS_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));
    public static final Block PALM_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD, PALM_PLANKS_COLOR).strength(2.0F, 3.0F).sounds(SoundType.WOOD));

    public static final WoodType BAOBAB_WOOD_TYPE = SignTypeRegistry.registerSignType(WilderSharedConstants.id("baobab"));
    public static final Block BAOBAB_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_sign"));
    public static final Block BAOBAB_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD).dropsLike(BAOBAB_SIGN_BLOCK), BAOBAB_WOOD_TYPE, WilderSharedConstants.id("blocks/baobab_sign"));

    public static final WoodType CYPRESS_WOOD_TYPE = SignTypeRegistry.registerSignType(WilderSharedConstants.id("cypress"));
    public static final Block CYPRESS_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_sign"));
    public static final Block CYPRESS_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD).dropsLike(CYPRESS_SIGN_BLOCK), CYPRESS_WOOD_TYPE, WilderSharedConstants.id("blocks/cypress_sign"));

    public static final WoodType PALM_WOOD_TYPE = SignTypeRegistry.registerSignType(WilderSharedConstants.id("palm"));
    public static final Block PALM_SIGN_BLOCK = new FrozenSignBlock(FabricBlockSettings.of(Material.WOOD, PALM_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_sign"));
    public static final Block PALM_WALL_SIGN = new FrozenWallSignBlock(FabricBlockSettings.of(Material.WOOD, PALM_LOG.defaultMaterialColor()).noCollision().strength(1.0F).sounds(SoundType.WOOD).dropsLike(PALM_SIGN_BLOCK), PALM_WOOD_TYPE, WilderSharedConstants.id("blocks/palm_sign"));

    public static void registerWoods() {
    	String baobab = "baobab";
    	String cypress = "cypress";
    	String palm = "palm";

    	registerBlock(baobab + "_planks", BAOBAB_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(cypress + "_planks", CYPRESS_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(palm + "_planks", PALM_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);

    	registerBlock(baobab + "_log", BAOBAB_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(cypress + "_log", CYPRESS_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(palm + "_log", PALM_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

    	registerBlock("stripped_" + baobab + "_log", STRIPPED_BAOBAB_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock("stripped_" + cypress + "_log", STRIPPED_CYPRESS_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock("stripped_" + palm + "_log", STRIPPED_PALM_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

    	registerBlock("stripped_" + baobab + "_wood", STRIPPED_BAOBAB_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock("stripped_" + cypress + "_wood", STRIPPED_CYPRESS_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock("stripped_" + palm + "_wood", STRIPPED_PALM_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);

    	registerBlock(baobab + "_wood", BAOBAB_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(cypress + "_wood", CYPRESS_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(palm + "_wood", PALM_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);

    	registerBlock(baobab + "_slab", BAOBAB_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(cypress + "_slab", CYPRESS_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(palm + "_slab", PALM_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);

    	registerBlock(baobab + "_stairs", BAOBAB_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(cypress + "_stairs", CYPRESS_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(palm + "_stairs", PALM_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);

    	registerBlock(baobab + "_button", BAOBAB_BUTTON, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(cypress + "_button", CYPRESS_BUTTON, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(palm + "_button", PALM_BUTTON, CreativeModeTab.TAB_REDSTONE);

    	registerBlock(baobab + "_pressure_plate", BAOBAB_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(cypress + "_pressure_plate", CYPRESS_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(palm + "_pressure_plate", PALM_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);

    	registerBlock(baobab + "_door", BAOBAB_DOOR, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(cypress + "_door", CYPRESS_DOOR, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(palm + "_door", PALM_DOOR, CreativeModeTab.TAB_REDSTONE);

    	registerBlock(baobab + "_trapdoor", BAOBAB_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(cypress + "_trapdoor", CYPRESS_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(palm + "_trapdoor", PALM_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);

    	registerBlock(baobab + "_fence_gate", BAOBAB_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(cypress + "_fence_gate", CYPRESS_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);
    	registerBlock(palm + "_fence_gate", PALM_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);

    	registerBlockWithoutBlockItem(baobab + "_nut", BAOBAB_NUT);
    	registerBlockWithoutBlockItem("potted_" + baobab + "_nut", POTTED_BAOBAB_NUT);
    	registerBlock(cypress + "_sapling", CYPRESS_SAPLING, CreativeModeTab.TAB_DECORATIONS);
    	registerBlockWithoutBlockItem("potted_" + cypress + "_sapling", POTTED_CYPRESS_SAPLING);
		registerBlockWithoutBlockItem("coconut", COCONUT);
		Registry.register(Registry.ITEM, WilderSharedConstants.id("coconut"), RegisterItems.COCONUT);
    	registerBlockWithoutBlockItem("potted_coconut", POTTED_COCONUT);

    	registerBlock(baobab + "_leaves", BAOBAB_LEAVES, CreativeModeTab.TAB_DECORATIONS);
    	registerBlock(cypress + "_leaves", CYPRESS_LEAVES, CreativeModeTab.TAB_DECORATIONS);
		registerBlock(palm + "_crown", PALM_CROWN, CreativeModeTab.TAB_BUILDING_BLOCKS);
    	registerBlock(palm + "_leaves", PALM_LEAVES, CreativeModeTab.TAB_DECORATIONS);

    	registerBlock(baobab + "_fence", BAOBAB_FENCE, CreativeModeTab.TAB_DECORATIONS);
    	registerBlock(cypress + "_fence", CYPRESS_FENCE, CreativeModeTab.TAB_DECORATIONS);
    	registerBlock(palm + "_fence", PALM_FENCE, CreativeModeTab.TAB_DECORATIONS);

        registerBlockWithoutBlockItem(baobab + "_sign", BAOBAB_SIGN_BLOCK);
        registerBlockWithoutBlockItem(baobab + "_wall_sign", BAOBAB_WALL_SIGN);
        registerBlockWithoutBlockItem(cypress + "_sign", CYPRESS_SIGN_BLOCK);
        registerBlockWithoutBlockItem(cypress + "_wall_sign", CYPRESS_WALL_SIGN);
        registerBlockWithoutBlockItem(palm + "_sign", PALM_SIGN_BLOCK);
        registerBlockWithoutBlockItem(palm + "_wall_sign", PALM_WALL_SIGN);
    }

    // HOLLOWED LOGS
    public static final Block HOLLOWED_OAK_LOG = createHollowedLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL);
    public static final Block HOLLOWED_SPRUCE_LOG = createHollowedLogBlock(MaterialColor.PODZOL, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MaterialColor.SAND, MaterialColor.QUARTZ);
    public static final Block HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MaterialColor.DIRT, MaterialColor.PODZOL);
    public static final Block HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.STONE);
    public static final Block HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MaterialColor.COLOR_RED, MaterialColor.PODZOL);
	public static final Block HOLLOWED_CRIMSON_STEM = createHollowedStemBlock(MaterialColor.CRIMSON_STEM);
	public static final Block HOLLOWED_WARPED_STEM = createHollowedStemBlock(MaterialColor.WARPED_STEM);
    public static final Block HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.COLOR_BROWN);
    public static final Block HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MaterialColor.COLOR_LIGHT_GRAY, MaterialColor.STONE);
	public static final Block HOLLOWED_PALM_LOG = createHollowedLogBlock(PALM_PLANKS_COLOR, PALM_BARK_COLOR);

	// STRIPPED HOLLOWED LOGS
	public static final Block STRIPPED_HOLLOWED_OAK_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_OAK_LOG.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_SPRUCE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_SPRUCE_LOG.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_BIRCH_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_BIRCH_LOG.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_JUNGLE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_JUNGLE_LOG.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_ACACIA_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_ACACIA_LOG.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_DARK_OAK_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_DARK_OAK_LOG.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_MANGROVE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_MANGROVE_LOG.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_CRIMSON_STEM = createStrippedHollowedStemBlock(Blocks.STRIPPED_CRIMSON_STEM.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_WARPED_STEM = createStrippedHollowedStemBlock(Blocks.STRIPPED_WARPED_STEM.defaultMaterialColor());
	public static final Block STRIPPED_HOLLOWED_BAOBAB_LOG = createStrippedHollowedLogBlock(BAOBAB_PLANKS_COLOR);
	public static final Block STRIPPED_HOLLOWED_CYPRESS_LOG = createStrippedHollowedLogBlock(CYPRESS_PLANKS_COLOR);
	public static final Block STRIPPED_HOLLOWED_PALM_LOG = createStrippedHollowedLogBlock(PALM_PLANKS_COLOR);

    public static void registerHollowedLogs() {
        registerBlock("hollowed_oak_log", HOLLOWED_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_birch_log", HOLLOWED_BIRCH_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_acacia_log", HOLLOWED_ACACIA_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("hollowed_crimson_stem", HOLLOWED_CRIMSON_STEM, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("hollowed_warped_stem", HOLLOWED_WARPED_STEM, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("hollowed_palm_log", HOLLOWED_PALM_LOG, CreativeModeTab.TAB_DECORATIONS);

		registerBlock("stripped_hollowed_oak_log", STRIPPED_HOLLOWED_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_spruce_log", STRIPPED_HOLLOWED_SPRUCE_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_birch_log", STRIPPED_HOLLOWED_BIRCH_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_jungle_log", STRIPPED_HOLLOWED_JUNGLE_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_acacia_log", STRIPPED_HOLLOWED_ACACIA_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_dark_oak_log", STRIPPED_HOLLOWED_DARK_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_mangrove_log", STRIPPED_HOLLOWED_MANGROVE_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_crimson_stem", STRIPPED_HOLLOWED_CRIMSON_STEM, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_warped_stem", STRIPPED_HOLLOWED_WARPED_STEM, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_baobab_log", STRIPPED_HOLLOWED_BAOBAB_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_cypress_log", STRIPPED_HOLLOWED_CYPRESS_LOG, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("stripped_hollowed_palm_log", STRIPPED_HOLLOWED_PALM_LOG, CreativeModeTab.TAB_DECORATIONS);
    }

    // SCULK
    public static final Block SCULK_STAIRS = new SculkStairsBlock(Blocks.SCULK.defaultBlockState(), FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_SLAB = new SculkSlabBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block SCULK_WALL = new SculkWallBlock(FabricBlockSettings.of(Material.SCULK).strength(0.2F).sounds(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final Block OSSEOUS_SCULK = new OsseousSculkBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.SAND).requiresTool().strength(2.0F).sounds(RegisterBlockSoundTypes.OSSEOUS_SCULK));
    public static final Block HANGING_TENDRIL = new HangingTendrilBlock(FabricBlockSettings.copyOf(Blocks.SCULK_SENSOR).strength(0.7F).collidable(false).ticksRandomly().luminance((state) -> 1)
            .sounds(RegisterBlockSoundTypes.HANGING_TENDRIL).emissiveLighting((state, level, pos) -> HangingTendrilBlock.shouldHavePogLighting(state)), 4);
    public static final Block ECHO_GLASS = new EchoGlassBlock(FabricBlockSettings.of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(0.3F).nonOpaque().sounds(RegisterBlockSoundTypes.ECHO_GLASS));

    public static void registerDeepDark() {
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
    public static final Block BLUE_PEARLESCENT_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.QUARTZ).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundTypes.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never), RegisterParticles.BLUE_PEARLESCENT_HANGING_MESOGLEA);
    public static final Block PURPLE_PEARLESCENT_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_PURPLE).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundTypes.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never), RegisterParticles.PURPLE_PEARLESCENT_HANGING_MESOGLEA);
    public static final Block YELLOW_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_YELLOW).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundTypes.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never), RegisterParticles.YELLOW_HANGING_MESOGLEA);
    public static final Block BLUE_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_LIGHT_BLUE).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundTypes.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never), RegisterParticles.BLUE_HANGING_MESOGLEA);
    public static final Block LIME_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_LIGHT_GREEN).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundTypes.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never), RegisterParticles.LIME_HANGING_MESOGLEA);
    public static final Block RED_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_RED).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundTypes.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never), RegisterParticles.RED_HANGING_MESOGLEA);
    public static final Block PINK_MESOGLEA = new MesogleaBlock(FabricBlockSettings.of(MESOGLEA_MATERIAL, MaterialColor.COLOR_PINK).nonOpaque().strength(0.2F).slipperiness(0.8F).emissiveLighting(RegisterBlocks::always).luminance((state) -> 7).sounds(RegisterBlockSoundTypes.MESOGLEA).suffocates(RegisterBlocks::never).blockVision(RegisterBlocks::never), RegisterParticles.PINK_HANGING_MESOGLEA);

    public static final Block BLUE_PEARLESCENT_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.QUARTZ).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundTypes.NEMATOCYST));
    public static final Block PURPLE_PEARLESCENT_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_PURPLE).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundTypes.NEMATOCYST));
    public static final Block YELLOW_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_YELLOW).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundTypes.NEMATOCYST));
    public static final Block BLUE_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_LIGHT_BLUE).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundTypes.NEMATOCYST));
    public static final Block LIME_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_LIGHT_GREEN).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundTypes.NEMATOCYST));
    public static final Block RED_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_RED).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundTypes.NEMATOCYST));
    public static final Block PINK_NEMATOCYST = new NematocystBlock(FabricBlockSettings.of(NEMATOCYST_MATERIAL, MaterialColor.COLOR_PINK).noCollision().nonOpaque().emissiveLighting(RegisterBlocks::always).luminance((state) -> 4).sounds(RegisterBlockSoundTypes.NEMATOCYST));

    // MISC
    private static final Material ALGAE_MATERIAL = new FabricMaterialBuilder(MaterialColor.PLANT)
            .allowsMovement()
            .lightPassesThrough()
            .notSolid()
            .destroyedByPiston()
            .build();

    public static final Block TERMITE_MOUND = new TermiteMound(FabricBlockSettings.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(0.3F).sounds(RegisterBlockSoundTypes.COARSEDIRT).ticksRandomly());
    public static final Block STONE_CHEST = new StoneChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST).sounds(SoundType.DEEPSLATE).strength(35.0F, 12.0F), () -> RegisterBlockEntities.STONE_CHEST);

    // PLANTS
    public static final Block SEEDING_DANDELION = new SeedingDandelionBlock(MobEffects.SLOW_FALLING, 12, FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(SoundType.GRASS).strength(0.0F).nonOpaque());
    public static final Block POTTED_SEEDING_DANDELION = new FlowerPotBlock(RegisterBlocks.SEEDING_DANDELION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block CARNATION = new FlowerBlock(MobEffects.REGENERATION, 12, FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(SoundType.GRASS).strength(0.0F).nonOpaque());
    public static final Block POTTED_CARNATION = new FlowerPotBlock(RegisterBlocks.CARNATION, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(FabricBlockSettings.copyOf(Blocks.DANDELION).sounds(SoundType.GRASS).strength(0.0F).nonOpaque().ticksRandomly(), List.of(FlowerColor.BLUE, FlowerColor.PINK, FlowerColor.PURPLE, FlowerColor.WHITE));

    public static final Block WHITE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.QUARTZ).sound(SoundType.VINE));
    public static final Block PINK_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.CRIMSON_STEM).sound(SoundType.VINE));
    public static final Block PURPLE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_PURPLE).sound(SoundType.VINE));
    public static final Block BLUE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_BLUE).sound(SoundType.VINE));

    public static final Block DATURA = new TallFlowerBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
    public static final Block MILKWEED = new MilkweedBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().ticksRandomly().sounds(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));

    public static final Block CATTAIL = new WaterloggableTallFlowerBlock(FabricBlockSettings.copyOf(Blocks.ROSE_BUSH).sounds(SoundType.WET_GRASS).strength(0.0F).nonOpaque());
    public static final Block FLOWERING_LILY_PAD = new FloweringLilyPadBlock(FabricBlockSettings.copyOf(Blocks.LILY_PAD).sounds(SoundType.LILY_PAD));
    public static final Block ALGAE = new AlgaeBlock(FabricBlockSettings.of(ALGAE_MATERIAL).breakInstantly().nonOpaque().noCollision().sounds(RegisterBlockSoundTypes.ALGAE));
	public static final Block BUSH = new BushBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().nonOpaque().noCollision().sounds(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ));
	public static final Block TUMBLEWEED_PLANT = new TumbleweedPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().sounds(RegisterBlockSoundTypes.TUMBLEWEED_PLANT).randomTicks());
	public static final Block POTTED_TUMBLEWEED_PLANT = new FlowerPotBlock(TUMBLEWEED_PLANT, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block TUMBLEWEED = new TumbleweedBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().nonOpaque().sounds(RegisterBlockSoundTypes.TUMBLEWEED_PLANT).randomTicks());

    public static final Block POTTED_BIG_DRIPLEAF = new FlowerPotBlock(Blocks.BIG_DRIPLEAF, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_SMALL_DRIPLEAF = new FlowerPotBlock(Blocks.SMALL_DRIPLEAF, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_GRASS = new FlowerPotBlock(Blocks.GRASS, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block POTTED_PRICKLY_PEAR = new FlowerPotBlock(PRICKLY_PEAR_CACTUS, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

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
		registerBlock("bush", BUSH, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("tumbleweed_plant", TUMBLEWEED_PLANT, CreativeModeTab.TAB_DECORATIONS);
		registerBlock("tumbleweed", TUMBLEWEED, CreativeModeTab.TAB_DECORATIONS);
		registerBlockWithoutBlockItem("potted_tumbleweed_plant", POTTED_TUMBLEWEED_PLANT);
		registerBlockWithoutBlockItem("prickly_pear", PRICKLY_PEAR_CACTUS);
		registerBlockWithoutBlockItem("potted_prickly_pear", POTTED_PRICKLY_PEAR);
    }

    public static final Block BROWN_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance(1).collidable(false).nonOpaque().sounds(RegisterBlockSoundTypes.MUSHROOM));
    public static final Block RED_SHELF_FUNGUS = new ShelfFungusBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).collidable(false).nonOpaque().sounds(RegisterBlockSoundTypes.MUSHROOM));
    public static final Block POLLEN_BLOCK = new PollenBlock(FabricBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.SAND).sound(SoundType.VINE));
	//TODO: Rename & Sponge Sounds
	public static final Block SMALL_SPONGE = new SmallSpongeBlock(FabricBlockSettings.copyOf(Blocks.SPONGE).collidable(false).nonOpaque().sounds(SoundType.GRASS));

    public static void registerNotSoPlants() {
        registerBlockWithoutBlockItem("pollen", POLLEN_BLOCK);
        registerBlock("brown_shelf_fungus", BROWN_SHELF_FUNGUS, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("red_shelf_fungus", RED_SHELF_FUNGUS, CreativeModeTab.TAB_DECORATIONS);
        Registry.register(Registry.BLOCK, WilderSharedConstants.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
        Registry.register(Registry.ITEM, WilderSharedConstants.id("flowering_lily_pad"), new PlaceOnWaterBlockItem(FLOWERING_LILY_PAD, new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS)));
        Registry.register(Registry.BLOCK, WilderSharedConstants.id("algae"), ALGAE);
        Registry.register(Registry.ITEM, WilderSharedConstants.id("algae"), new PlaceOnWaterBlockItem(ALGAE, new FabricItemSettings().tab(CreativeModeTab.TAB_DECORATIONS)));
		registerBlock("small_sponge", SMALL_SPONGE, CreativeModeTab.TAB_DECORATIONS);
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

    public static final Block NULL_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE).sounds(RegisterBlockSoundTypes.NULL_BLOCK));

    public static final Block DISPLAY_LANTERN = new DisplayLanternBlock(FabricBlockSettings.of(Material.METAL).strength(3.5f).sounds(SoundType.LANTERN).luminance((state) -> state.getValue(RegisterProperties.DISPLAY_LIGHT)));

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

        registerOtherBB();
        registerWoods();
        registerHollowedLogs();
        registerDeepDark();
        registerPlants();
        registerNotSoPlants();
        registerMisc();
        registerBlockProperties();
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

    private static void registerBlockWithoutBlockItem(String name, Block block) {
        Registry.register(Registry.BLOCK, WilderSharedConstants.id(name), block);
    }

    private static void registerBlock(String name, Block block, CreativeModeTab tab) {
        registerBlockItem(name, block, tab);
        Registry.register(Registry.BLOCK, WilderSharedConstants.id(name), block);
    }

    private static void registerBlockItem(String name, Block block, CreativeModeTab tab) {
        Registry.register(Registry.ITEM, WilderSharedConstants.id(name),
				new BlockItem(block, new FabricItemSettings().tab(tab)));
    }

    private static HollowedLogBlock createHollowedLogBlock(MaterialColor topMapColor, MaterialColor sideMapColor) {
        return new HollowedLogBlock(FabricBlockSettings.of(Material.WOOD,
                        (state) -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0F).sound(RegisterBlockSoundTypes.HOLLOWED_LOG));
    }

	private static HollowedLogBlock createHollowedStemBlock(MaterialColor mapColor) {
		return new HollowedLogBlock(FabricBlockSettings.of(Material.NETHER_WOOD,
						(state) -> mapColor)
				.strength(2.0F).sound(RegisterBlockSoundTypes.HOLLOWED_STEM));
	}

	private static HollowedLogBlock createStrippedHollowedLogBlock(MaterialColor mapColor) {
		return new HollowedLogBlock(FabricBlockSettings.of(Material.WOOD,
						(state) -> mapColor)
				.strength(2.0F).sound(RegisterBlockSoundTypes.HOLLOWED_LOG));
	}

	private static HollowedLogBlock createStrippedHollowedStemBlock(MaterialColor mapColor) {
		return new HollowedLogBlock(FabricBlockSettings.of(Material.NETHER_WOOD,
						(state) -> mapColor)
				.strength(2.0F).sound(RegisterBlockSoundTypes.HOLLOWED_STEM));
	}

    public static void registerBlockProperties() {
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_HOLLOWED_BAOBAB_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_WOOD, Blocks.AIR);

        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

		TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_HOLLOWED_CYPRESS_LOG, Blocks.AIR);
		TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_WOOD, Blocks.AIR);

        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

		TermiteMoundBlockEntity.Termite.addDegradable(PALM_CROWN, PALM_LOG);
		TermiteMoundBlockEntity.Termite.addDegradable(PALM_LOG, STRIPPED_PALM_LOG);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_HOLLOWED_PALM_LOG, Blocks.AIR);
		TermiteMoundBlockEntity.Termite.addDegradable(PALM_WOOD, STRIPPED_PALM_WOOD);
		TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_PALM_WOOD, Blocks.AIR);

		TermiteMoundBlockEntity.Termite.addNaturalDegradable(PALM_LOG, STRIPPED_PALM_LOG);
		TermiteMoundBlockEntity.Termite.addNaturalDegradable(PALM_WOOD, STRIPPED_PALM_WOOD);
		TermiteMoundBlockEntity.Termite.addNaturalDegradable(PALM_CROWN, PALM_LOG);

		TermiteMoundBlockEntity.Termite.addDegradable(BUSH, Blocks.DEAD_BUSH);

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

    private static Boolean canSpawnOnLeaves(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> type) {
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
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED_PLANT, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED, 0.1F);
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
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_OAK_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_ACACIA_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_JUNGLE_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_MANGROVE_LOG, 5, 5);
        flammableBlockRegistry.add(RegisterBlocks.HOLLOWED_SPRUCE_LOG, 5, 5);
		flammableBlockRegistry.add(RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, 5, 5);
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
		flammableBlockRegistry.add(RegisterBlocks.PALM_LEAVES, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.PALM_BUTTON, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_SIGN_BLOCK, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_WALL_SIGN, 5, 20);
    }

    private static void registerFuels() {
        WilderSharedConstants.logWild("Registering Fuels for", WilderSharedConstants.UNSTABLE_LOGGING);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(BAOBAB_FENCE.asItem(), 300);
        registry.add(BAOBAB_FENCE_GATE.asItem(), 300);
        registry.add(CYPRESS_FENCE.asItem(), 300);
        registry.add(CYPRESS_FENCE_GATE.asItem(), 300);
		registry.add(PALM_FENCE.asItem(), 300);
		registry.add(PALM_FENCE_GATE.asItem(), 300);
		registry.add(TUMBLEWEED.asItem(), 150);
		registry.add(TUMBLEWEED_PLANT.asItem(), 150);
    }

    private static void registerBonemeal() {
        BonemealBehaviors.BONEMEAL_BEHAVIORS.put(Blocks.LILY_PAD, (context, level, pos, state, face, horizontal) -> {
            if (!level.isClientSide) {
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                level.setBlockAndUpdate(pos, RegisterBlocks.FLOWERING_LILY_PAD.defaultBlockState());
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
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.OAK_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_OAK_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.BIRCH_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_BIRCH_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.SPRUCE_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_SPRUCE_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.DARK_OAK_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_DARK_OAK_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.JUNGLE_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_JUNGLE_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.ACACIA_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_ACACIA_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.MANGROVE_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_MANGROVE_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.CRIMSON_STEM, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, true);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_CRIMSON_STEM.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.WARPED_STEM, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, true);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_WARPED_STEM.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.BAOBAB_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_BAOBAB_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.CYPRESS_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_CYPRESS_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.PALM_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.HOLLOWED_PALM_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		//STRIPPED
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_OAK_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_BIRCH_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_SPRUCE_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_DARK_OAK_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_JUNGLE_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_ACACIA_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_MANGROVE_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_CRIMSON_STEM, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, true);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(Blocks.STRIPPED_WARPED_STEM, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, true);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.STRIPPED_BAOBAB_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.STRIPPED_CYPRESS_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
		AxeBehaviors.AXE_BEHAVIORS.put(RegisterBlocks.STRIPPED_PALM_LOG, (context, level, pos, state, face, horizontal) -> {
			if (!level.isClientSide && face.getAxis().equals(state.getValue(BlockStateProperties.AXIS))) {
				HollowedLogBlock.hollowEffects(level, face, state, pos, false);
				level.setBlockAndUpdate(pos, RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				return true;
			}
			return false;
		});
	}

}
