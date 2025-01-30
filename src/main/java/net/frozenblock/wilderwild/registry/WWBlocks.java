/*
 * Copyright 2023-2025 FrozenBlock
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

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.item.api.bonemeal.BonemealBehaviors;
import net.frozenblock.lib.storage.api.NoInteractionStorage;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.block.BaobabLeavesBlock;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.CattailBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.FloweringWaterlilyBlock;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.block.HugePaleMushroomBlock;
import net.frozenblock.wilderwild.block.LeafLitterBlock;
import net.frozenblock.wilderwild.block.LeavesWithLitterBlock;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.block.MilkweedBlock;
import net.frozenblock.wilderwild.block.MyceliumGrowthBlock;
import net.frozenblock.wilderwild.block.NematocystBlock;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.block.OstrichEggBlock;
import net.frozenblock.wilderwild.block.PaleMushroomBlock;
import net.frozenblock.wilderwild.block.PaleShelfFungiBlock;
import net.frozenblock.wilderwild.block.PalmFrondsBlock;
import net.frozenblock.wilderwild.block.PenguinEggBlock;
import net.frozenblock.wilderwild.block.PollenBlock;
import net.frozenblock.wilderwild.block.PricklyPearCactusBlock;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.SculkSlabBlock;
import net.frozenblock.wilderwild.block.SculkStairBlock;
import net.frozenblock.wilderwild.block.SculkWallBlock;
import net.frozenblock.wilderwild.block.SeedingFlowerBlock;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMoundBlock;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WideFlowerBlock;
import net.frozenblock.wilderwild.block.WilderBushBlock;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.worldgen.feature.configured.WWTreeConfigured;
import net.frozenblock.wilderwild.worldgen.feature.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.worldgen.impl.sapling.WWTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class WWBlocks {
	public static final BlockSetType BAOBAB_SET = BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA).register(WWConstants.id("baobab"));
	public static final BlockSetType WILLOW_SET = BlockSetTypeBuilder.copyOf(BlockSetType.SPRUCE).register(WWConstants.id("willow"));
	public static final BlockSetType CYPRESS_SET = BlockSetTypeBuilder.copyOf(BlockSetType.BIRCH).register(WWConstants.id("cypress"));
	public static final BlockSetType PALM_SET = BlockSetTypeBuilder.copyOf(BlockSetType.JUNGLE).register(WWConstants.id("palm"));
	public static final BlockSetType MAPLE_SET = BlockSetTypeBuilder.copyOf(BlockSetType.SPRUCE).register(WWConstants.id("maple"));
	public static final WoodType BAOBAB_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.ACACIA).register(WWConstants.id("baobab"), BAOBAB_SET);
	public static final WoodType WILLOW_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.SPRUCE).register(WWConstants.id("willow"), WILLOW_SET);
	public static final WoodType CYPRESS_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.BIRCH).register(WWConstants.id("cypress"), CYPRESS_SET);
	public static final WoodType PALM_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.JUNGLE).register(WWConstants.id("palm"), PALM_SET);
	public static final WoodType MAPLE_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.SPRUCE).register(WWConstants.id("maple"), MAPLE_SET);
	private static final MapColor BAOBAB_PLANKS_COLOR = MapColor.COLOR_ORANGE;
	private static final MapColor BAOBAB_BARK_COLOR = MapColor.COLOR_BROWN;
	private static final MapColor WILLOW_PLANKS_COLOR = MapColor.TERRACOTTA_LIGHT_GREEN;
	private static final MapColor WILLOW_BARK_COLOR = MapColor.COLOR_BROWN;
	private static final MapColor CYPRESS_PLANKS_COLOR = MapColor.SAND;
	private static final MapColor CYPRESS_BARK_COLOR = MapColor.CLAY;
	private static final MapColor PALM_BARK_COLOR = MapColor.TERRACOTTA_ORANGE;
	private static final MapColor PALM_PLANKS_COLOR = MapColor.COLOR_YELLOW;
	private static final MapColor MAPLE_PLANKS_COLOR = MapColor.COLOR_BROWN;
	private static final MapColor MAPLE_BARK_COLOR = MapColor.COLOR_BROWN;

	// OTHER (BUILDING BLOCKS)

	public static final Block CHISELED_MUD_BRICKS = register("chiseled_mud_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.MUD_BRICKS)
	);
	public static final Block CRACKED_MUD_BRICKS = register("cracked_mud_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.MUD_BRICKS)
	);
	public static final Block MOSSY_MUD_BRICKS = register("mossy_mud_bricks",
		Block::new,
		Properties.ofFullCopy(Blocks.MUD_BRICKS)
	);
	public static final Block MOSSY_MUD_BRICK_STAIRS = register("mossy_mud_brick_stairs",
		properties -> new StairBlock(MOSSY_MUD_BRICKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MOSSY_MUD_BRICKS)
	);
	public static final Block MOSSY_MUD_BRICK_SLAB = register("mossy_mud_brick_slab",
		SlabBlock::new,
		Properties.ofFullCopy(MOSSY_MUD_BRICKS)
	);
	public static final Block MOSSY_MUD_BRICK_WALL = register("mossy_mud_brick_wall",
		WallBlock::new,
		Properties.ofFullCopy(MOSSY_MUD_BRICKS)
	);
	public static final BlockFamily FAMILY_MOSSY_MUD_BRICK = BlockFamilies.familyBuilder(MOSSY_MUD_BRICKS)
		.stairs(MOSSY_MUD_BRICK_STAIRS)
		.slab(MOSSY_MUD_BRICK_SLAB)
		.wall(MOSSY_MUD_BRICK_WALL)
		.getFamily();

	public static final ScorchedBlock SCORCHED_SAND = registerWithoutItem("scorched_sand",
		properties -> new ScorchedBlock(
			Blocks.SAND.defaultBlockState(),
			true,
			SoundEvents.BRUSH_SAND,
			SoundEvents.BRUSH_SAND_COMPLETED,
			properties
		),
		Properties.of()
			.strength(1.5F)
			.sound(WWSoundTypes.SCORCHED_SAND)
			.mapColor(MapColor.SAND)
			.randomTicks()
	);

	public static final ScorchedBlock SCORCHED_RED_SAND = registerWithoutItem("scorched_red_sand",
		properties -> new ScorchedBlock(
			Blocks.RED_SAND.defaultBlockState(),
			true,
			SoundEvents.BRUSH_SAND,
			SoundEvents.BRUSH_SAND_COMPLETED,
			properties
		),
		Properties.of()
			.strength(1.5F)
			.sound(WWSoundTypes.SCORCHED_SAND)
			.mapColor(MapColor.COLOR_ORANGE)
			.randomTicks()
	);

	public static final BaobabNutBlock BAOBAB_NUT = registerWithoutItem("baobab_nut",
		properties -> new BaobabNutBlock(WWTreeGrowers.BAOBAB, properties),
		Properties.ofFullCopy(Blocks.BAMBOO)
			.sound(WWSoundTypes.BAOBAB_NUT)
	);
	public static final Block POTTED_BAOBAB_NUT = registerWithoutItem("potted_baobab_nut",
		properties -> new FlowerPotBlock(BAOBAB_NUT, properties),
		Blocks.flowerPotProperties()
	);

	public static final PricklyPearCactusBlock PRICKLY_PEAR_CACTUS = registerWithoutItem("prickly_pear",
		PricklyPearCactusBlock::new,
		Properties.ofFullCopy(Blocks.CACTUS)
			.noCollission()
			.offsetType(BlockBehaviour.OffsetType.XZ)
	);

	public static final WaterloggableSaplingBlock WILLOW_SAPLING = register("willow_sapling",
		properties -> new WaterloggableSaplingBlock(WWTreeGrowers.WILLOW, properties),
		Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_WILLOW_SAPLING = registerWithoutItem("potted_willow_sapling",
		properties -> new FlowerPotBlock(WILLOW_SAPLING, properties),
		Blocks.flowerPotProperties()
	);

	public static final WaterloggableSaplingBlock CYPRESS_SAPLING = register("cypress_sapling",
		properties -> new WaterloggableSaplingBlock(WWTreeGrowers.CYPRESS, properties),
		Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_CYPRESS_SAPLING = registerWithoutItem("potted_cypress_sapling",
		properties -> new FlowerPotBlock(CYPRESS_SAPLING, properties),
		Blocks.flowerPotProperties()
	);

	public static final CoconutBlock COCONUT = registerWithoutItem("coconut",
		properties -> new CoconutBlock(WWTreeGrowers.PALM, properties),
		Properties.of().instabreak().randomTicks().sound(WWSoundTypes.COCONUT)
	);
	public static final Block POTTED_COCONUT = registerWithoutItem("potted_coconut",
		properties -> new FlowerPotBlock(COCONUT, properties),
		Blocks.flowerPotProperties()
	);

	public static final SaplingBlock MAPLE_SAPLING = register("maple_sapling",
		properties -> new SaplingBlock(WWTreeGrowers.MAPLE, properties),
		Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_MAPLE_SAPLING = registerWithoutItem("potted_maple_sapling",
		properties -> new FlowerPotBlock(MAPLE_SAPLING, properties),
		Blocks.flowerPotProperties()
	);

	public static final Block BAOBAB_LEAVES = register("baobab_leaves",
		BaobabLeavesBlock::new,
		Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final Block WILLOW_LEAVES = register("willow_leaves",
		LeavesBlock::new,
		Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final Block CYPRESS_LEAVES = register("cypress_leaves",
		LeavesBlock::new,
		Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final PalmFrondsBlock PALM_FRONDS = register("palm_fronds",
		PalmFrondsBlock::new,
		Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final Block YELLOW_MAPLE_LEAVES = register("yellow_maple_leaves",
		LeavesWithLitterBlock::new,
		Blocks.leavesProperties(SoundType.GRASS).mapColor(MapColor.COLOR_YELLOW)
	);
	public static final Block ORANGE_MAPLE_LEAVES = register("orange_maple_leaves",
		LeavesWithLitterBlock::new,
		Blocks.leavesProperties(SoundType.GRASS).mapColor(MapColor.COLOR_ORANGE)
	);
	public static final Block RED_MAPLE_LEAVES = register("red_maple_leaves",
		LeavesWithLitterBlock::new,
		Blocks.leavesProperties(SoundType.GRASS).mapColor(MapColor.COLOR_RED)
	);

	public static final HollowedLogBlock HOLLOWED_OAK_LOG = register("hollowed_oak_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.WOOD, MapColor.PODZOL)
	);
	public static final HollowedLogBlock HOLLOWED_SPRUCE_LOG = register("hollowed_spruce_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.PODZOL, MapColor.COLOR_BROWN)
	);
	public static final HollowedLogBlock HOLLOWED_BIRCH_LOG = register("hollowed_birch_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.SAND, MapColor.QUARTZ)
	);
	public static final HollowedLogBlock HOLLOWED_JUNGLE_LOG = register("hollowed_jungle_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.DIRT, MapColor.PODZOL)
	);
	public static final HollowedLogBlock HOLLOWED_ACACIA_LOG = register("hollowed_acacia_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.COLOR_ORANGE, MapColor.STONE)
	);
	public static final HollowedLogBlock HOLLOWED_DARK_OAK_LOG = register("hollowed_dark_oak_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN)
	);
	public static final HollowedLogBlock HOLLOWED_MANGROVE_LOG = register("hollowed_mangrove_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.COLOR_RED, MapColor.PODZOL)
	);
	public static final HollowedLogBlock HOLLOWED_CHERRY_LOG = register("hollowed_cherry_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, WWSoundTypes.HOLLOWED_CHERRY_LOG)
	);
	public static final HollowedLogBlock HOLLOWED_PALE_OAK_LOG = register("hollowed_pale_oak_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MapColor.QUARTZ, MapColor.STONE)
	);
	public static final HollowedLogBlock HOLLOWED_CRIMSON_STEM = register("hollowed_crimson_stem",
		HollowedLogBlock::new,
		hollowedStemProperties(MapColor.CRIMSON_STEM)
	);
	public static final HollowedLogBlock HOLLOWED_WARPED_STEM = register("hollowed_warped_stem",
		HollowedLogBlock::new,
		hollowedStemProperties(MapColor.WARPED_STEM)
	);
	public static final HollowedLogBlock HOLLOWED_BAOBAB_LOG = register("hollowed_baobab_log",
		HollowedLogBlock::new,
		hollowedLogProperties(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR)
	);
	public static final HollowedLogBlock HOLLOWED_WILLOW_LOG = register("hollowed_willow_log",
		HollowedLogBlock::new,
		hollowedLogProperties(WILLOW_PLANKS_COLOR, WILLOW_BARK_COLOR)
	);
	public static final HollowedLogBlock HOLLOWED_CYPRESS_LOG = register("hollowed_cypress_log",
		HollowedLogBlock::new,
		hollowedLogProperties(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR)
	);
	public static final HollowedLogBlock HOLLOWED_PALM_LOG = register("hollowed_palm_log",
		HollowedLogBlock::new,
		hollowedLogProperties(PALM_PLANKS_COLOR, PALM_BARK_COLOR)
	);
	public static final HollowedLogBlock HOLLOWED_MAPLE_LOG = register("hollowed_maple_log",
		HollowedLogBlock::new,
		hollowedLogProperties(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR)
	);

	// STRIPPED HOLLOWED LOGS
	public static final HollowedLogBlock STRIPPED_HOLLOWED_OAK_LOG = register("stripped_hollowed_oak_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_OAK_LOG.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_SPRUCE_LOG = register("stripped_hollowed_spruce_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_SPRUCE_LOG.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_BIRCH_LOG = register("stripped_hollowed_birch_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_BIRCH_LOG.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_CHERRY_LOG = register("stripped_hollowed_cherry_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_CHERRY_LOG.defaultMapColor(), WWSoundTypes.HOLLOWED_CHERRY_LOG)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_JUNGLE_LOG = register("stripped_hollowed_jungle_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_JUNGLE_LOG.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_ACACIA_LOG = register("stripped_hollowed_acacia_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_ACACIA_LOG.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_DARK_OAK_LOG = register("stripped_hollowed_dark_oak_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_DARK_OAK_LOG.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_MANGROVE_LOG = register("stripped_hollowed_mangrove_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_MANGROVE_LOG.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_PALE_OAK_LOG = register("stripped_hollowed_pale_oak_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(Blocks.STRIPPED_PALE_OAK_LOG.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_CRIMSON_STEM = register("stripped_hollowed_crimson_stem",
		HollowedLogBlock::new,
		strippedHollowedStemProperties(Blocks.STRIPPED_CRIMSON_STEM.defaultMapColor())
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_WARPED_STEM = register("stripped_hollowed_warped_stem",
		HollowedLogBlock::new,
		strippedHollowedStemProperties(Blocks.STRIPPED_WARPED_STEM.defaultMapColor())
	);

	// LEAF LITTER
	public static final LeafLitterBlock YELLOW_MAPLE_LEAF_LITTER = leafLitter("yellow_maple_leaf_litter",
		YELLOW_MAPLE_LEAVES,
		WWParticleTypes.YELLOW_MAPLE_LEAVES,
		0.04F,
		() -> WWAmbienceAndMiscConfig.Client.MAPLE_LEAF_FREQUENCY,
		5
	);
	public static final LeafLitterBlock ORANGE_MAPLE_LEAF_LITTER = leafLitter("orange_maple_leaf_litter",
		ORANGE_MAPLE_LEAVES,
		WWParticleTypes.ORANGE_MAPLE_LEAVES,
		0.04F,
		() -> WWAmbienceAndMiscConfig.Client.MAPLE_LEAF_FREQUENCY,
		5
	);
	public static final LeafLitterBlock RED_MAPLE_LEAF_LITTER = leafLitter("red_maple_leaf_litter",
		RED_MAPLE_LEAVES,
		WWParticleTypes.RED_MAPLE_LEAVES,
		0.04F,
		() -> WWAmbienceAndMiscConfig.Client.MAPLE_LEAF_FREQUENCY,
		5
	);

	// SCULK
	public static final SculkStairBlock SCULK_STAIRS = register("sculk_stairs",
		properties -> new SculkStairBlock(Blocks.SCULK.defaultBlockState(), properties),
		Properties.ofFullCopy(Blocks.SCULK)
	);

	public static final SculkSlabBlock SCULK_SLAB = register("sculk_slab",
		SculkSlabBlock::new,
		Properties.ofFullCopy(Blocks.SCULK)
	);

	public static final SculkWallBlock SCULK_WALL = register("sculk_wall",
		SculkWallBlock::new,
		Properties.ofFullCopy(Blocks.SCULK)
	);

	public static final OsseousSculkBlock OSSEOUS_SCULK = register("osseous_sculk",
		OsseousSculkBlock::new,
		Properties.of()
			.mapColor(MapColor.SAND)
			.strength(2.0F)
			.sound(WWSoundTypes.OSSEOUS_SCULK)
	);

	public static final HangingTendrilBlock HANGING_TENDRIL = register("hanging_tendril",
		HangingTendrilBlock::new,
		Properties.ofFullCopy(Blocks.SCULK_SENSOR)
			.strength(0.7F)
			.noCollission()
			.noOcclusion()
			.randomTicks()
			.lightLevel(state -> 1)
			.sound(WWSoundTypes.HANGING_TENDRIL)
			.emissiveRendering((state, level, pos) -> HangingTendrilBlock.shouldHavePogLighting(state))
	);

	public static final EchoGlassBlock ECHO_GLASS = registerWithoutItem("echo_glass",
		EchoGlassBlock::new,
		Properties.ofFullCopy(Blocks.TINTED_GLASS)
			.strength(1F)
			.mapColor(MapColor.COLOR_CYAN)
			.noOcclusion()
			.randomTicks()
			.sound(WWSoundTypes.ECHO_GLASS)
	);

	// Mesoglea
	public static final MesogleaBlock BLUE_PEARLESCENT_MESOGLEA = mesoglea("blue_pearlescent_mesoglea",
		MapColor.QUARTZ,
		WWParticleTypes.BLUE_PEARLESCENT_HANGING_MESOGLEA,
		true
	);
	public static final MesogleaBlock PURPLE_PEARLESCENT_MESOGLEA = mesoglea("purple_pearlescent_mesoglea",
		MapColor.COLOR_PURPLE,
		WWParticleTypes.PURPLE_PEARLESCENT_HANGING_MESOGLEA,
		true
	);
	public static final MesogleaBlock YELLOW_MESOGLEA = mesoglea("yellow_mesoglea",
		MapColor.COLOR_YELLOW,
		WWParticleTypes.YELLOW_HANGING_MESOGLEA,
		false
	);
	public static final MesogleaBlock BLUE_MESOGLEA = mesoglea("blue_mesoglea",
		MapColor.COLOR_LIGHT_BLUE,
		WWParticleTypes.BLUE_HANGING_MESOGLEA,
		false
	);
	public static final MesogleaBlock LIME_MESOGLEA = mesoglea("lime_mesoglea",
		MapColor.COLOR_LIGHT_GREEN,
		WWParticleTypes.LIME_HANGING_MESOGLEA,
		false
	);
	public static final MesogleaBlock RED_MESOGLEA = mesoglea("red_mesoglea",
		MapColor.COLOR_RED,
		WWParticleTypes.RED_HANGING_MESOGLEA,
		false
	);
	public static final MesogleaBlock PINK_MESOGLEA = mesoglea("pink_mesoglea",
		MapColor.COLOR_PINK,
		WWParticleTypes.PINK_HANGING_MESOGLEA,
		false
	);

	public static final NematocystBlock BLUE_PEARLESCENT_NEMATOCYST = register("blue_pearlescent_nematocyst",
		NematocystBlock::new,
		nematocystProperties(MapColor.QUARTZ)
	);
	public static final NematocystBlock PURPLE_PEARLESCENT_NEMATOCYST = register("purple_pearlescent_nematocyst",
		NematocystBlock::new,
		nematocystProperties(MapColor.COLOR_PURPLE)
	);
	public static final NematocystBlock YELLOW_NEMATOCYST = register("yellow_nematocyst",
		NematocystBlock::new,
		nematocystProperties(MapColor.COLOR_YELLOW)
	);
	public static final NematocystBlock BLUE_NEMATOCYST = register("blue_nematocyst",
		NematocystBlock::new,
		nematocystProperties(MapColor.COLOR_BLUE)
	);
	public static final NematocystBlock LIME_NEMATOCYST = register("lime_nematocyst",
		NematocystBlock::new,
		nematocystProperties(MapColor.COLOR_LIGHT_GREEN)
	);
	public static final NematocystBlock RED_NEMATOCYST = register("red_nematocyst",
		NematocystBlock::new,
		nematocystProperties(MapColor.COLOR_RED)
	);
	public static final NematocystBlock PINK_NEMATOCYST = register("pink_nematocyst",
		NematocystBlock::new,
		nematocystProperties(MapColor.COLOR_PINK)
	);

	// MISC

	public static final TermiteMoundBlock TERMITE_MOUND = register("termite_mound",
		TermiteMoundBlock::new,
		Properties.of()
			.mapColor(MapColor.COLOR_BROWN)
			.strength(0.3F)
			.sound(WWSoundTypes.TERMITE_MOUND)
			.hasPostProcess(Blocks::always)
			.randomTicks()
	);

	public static final StoneChestBlock STONE_CHEST = register("stone_chest",
		properties -> new StoneChestBlock(() -> WWBlockEntityTypes.STONE_CHEST, properties),
		Properties.ofFullCopy(Blocks.CHEST)
			.mapColor(MapColor.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(2.5F)
			.requiresCorrectToolForDrops()
			.sound(SoundType.DEEPSLATE)
			.strength(35F, 12F)
	);

	// PLANTS

	public static final SeedingFlowerBlock SEEDING_DANDELION = register("seeding_dandelion",
		properties -> new SeedingFlowerBlock(MobEffects.SLOW_FALLING, 12, Blocks.DANDELION, properties),
		Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_SEEDING_DANDELION = registerWithoutItem("potted_seeding_dandelion",
		properties -> new FlowerPotBlock(SEEDING_DANDELION, properties),
		Blocks.flowerPotProperties()
	);

	public static final FlowerBlock CARNATION = register("carnation",
		properties -> new FlowerBlock(MobEffects.REGENERATION, 12, properties),
		Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_CARNATION = registerWithoutItem("potted_carnation",
		properties -> new FlowerPotBlock(CARNATION, properties),
		Blocks.flowerPotProperties()
	);

	public static final FlowerBlock MARIGOLD = register("marigold",
		properties -> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 8, properties),
		Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_MARIGOLD = registerWithoutItem("potted_marigold",
		properties -> new FlowerPotBlock(MARIGOLD, properties),
		Blocks.flowerPotProperties()
	);

	public static final FlowerBlock PASQUEFLOWER = register("pasqueflower",
		properties -> new FlowerBlock(
			MobEffects.NIGHT_VISION,
			8,
			properties
		),
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_PASQUEFLOWER = registerWithoutItem("potted_pasqueflower",
		properties -> new FlowerPotBlock(PASQUEFLOWER, properties),
		Blocks.flowerPotProperties()
	);

	public static final MyceliumGrowthBlock MYCELIUM_GROWTH = register("mycelium_growth",
		MyceliumGrowthBlock::new,
		BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.NETHER_SPROUTS)
	);
	public static final Block POTTED_MYCELIUM_GROWTH = registerWithoutItem("potted_mycelium_growth",
		properties -> new FlowerPotBlock(MYCELIUM_GROWTH, properties),
		Blocks.flowerPotProperties()
	);


	public static final FlowerBlock YELLOW_HIBISCUS = register("yellow_hibiscus",
		properties -> new FlowerBlock(
			MobEffects.HUNGER,
			8,
			properties
		),
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_YELLOW_HIBISCUS = registerWithoutItem("potted_yellow_hibiscus",
		properties -> new FlowerPotBlock(YELLOW_HIBISCUS, properties),
		Blocks.flowerPotProperties()
	);

	public static final FlowerBlock WHITE_HIBISCUS = register("white_hibiscus",
		properties -> new FlowerBlock(
			MobEffects.HUNGER,
			8,
			properties
		),
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_WHITE_HIBISCUS = registerWithoutItem("potted_white_hibiscus",
		properties -> new FlowerPotBlock(WHITE_HIBISCUS, properties),
		Blocks.flowerPotProperties()
	);

	public static final FlowerBlock PINK_HIBISCUS = register("pink_hibiscus",
		properties -> new FlowerBlock(
			MobEffects.HUNGER,
			8,
			properties
		),
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_PINK_HIBISCUS = registerWithoutItem("potted_pink_hibiscus",
		properties -> new FlowerPotBlock(PINK_HIBISCUS, properties),
		Blocks.flowerPotProperties()
	);

	public static final FlowerBlock PURPLE_HIBISCUS = register("purple_hibiscus",
		properties -> new FlowerBlock(
			MobEffects.HUNGER,
			8,
			properties
		),
		BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_PURPLE_HIBISCUS = registerWithoutItem("potted_purple_hibiscus",
		properties -> new FlowerPotBlock(PURPLE_HIBISCUS, properties),
		Blocks.flowerPotProperties()
	);

	public static final TallFlowerBlock DATURA = register("datura",
		TallFlowerBlock::new,
		Properties.ofFullCopy(Blocks.SUNFLOWER)
	);

	public static final MilkweedBlock MILKWEED = register("milkweed",
		MilkweedBlock::new,
		Properties.ofFullCopy(Blocks.SUNFLOWER)
			.randomTicks()
	);

	public static final Block CATTAIL = register("cattail",
		CattailBlock::new,
		Properties.ofFullCopy(Blocks.ROSE_BUSH)
			.sound(SoundType.WET_GRASS)
			.strength(0F)
			.noOcclusion()
	);

	public static final FloweringWaterlilyBlock FLOWERING_LILY_PAD = registerWithoutItem("flowering_lily_pad",
		(properties -> new FloweringWaterlilyBlock(Blocks.LILY_PAD, properties)),
		Properties.ofFullCopy(Blocks.LILY_PAD)
	);

	public static final AlgaeBlock ALGAE = registerWithoutItem("algae",
		AlgaeBlock::new,
		Properties.ofFullCopy(Blocks.FROGSPAWN)
			.mapColor(MapColor.PLANT)
			.sound(WWSoundTypes.ALGAE)
	);

	public static final WilderBushBlock BUSH = register("bush",
		WilderBushBlock::new,
		Properties.ofFullCopy(Blocks.DEAD_BUSH)
			.mapColor(MapColor.PLANT)
			.noOcclusion()
			.randomTicks()
			.offsetType(BlockBehaviour.OffsetType.XZ)
	);
	public static final Block POTTED_BUSH = registerWithoutItem("potted_bush",
		properties -> new FlowerPotBlock(BUSH, properties),
		Blocks.flowerPotProperties()
	);

	public static final TumbleweedPlantBlock TUMBLEWEED_PLANT = register("tumbleweed_plant",
		TumbleweedPlantBlock::new,
		Properties.of()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final Block POTTED_TUMBLEWEED_PLANT = registerWithoutItem("potted_tumbleweed_plant",
		properties -> new FlowerPotBlock(TUMBLEWEED_PLANT, properties),
		Blocks.flowerPotProperties()
	);
	public static final TumbleweedBlock TUMBLEWEED = register("tumbleweed",
		TumbleweedBlock::new,
		Properties.of()
			.instabreak()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final Block POTTED_TUMBLEWEED = registerWithoutItem("potted_tumbleweed",
		properties -> new FlowerPotBlock(TUMBLEWEED, properties),
		Blocks.flowerPotProperties()
	);

	public static final Block POTTED_BIG_DRIPLEAF = registerWithoutItem("potted_big_dripleaf",
		properties -> new FlowerPotBlock(Blocks.BIG_DRIPLEAF, properties),
		Blocks.flowerPotProperties()
	);
	public static final Block POTTED_SMALL_DRIPLEAF = registerWithoutItem("potted_small_dripleaf",
		properties -> new FlowerPotBlock(Blocks.SMALL_DRIPLEAF, properties),
		Blocks.flowerPotProperties()
	);

	public static final Block POTTED_SHORT_GRASS = registerWithoutItem("potted_short_grass",
		properties -> new FlowerPotBlock(Blocks.SHORT_GRASS, properties),
		Blocks.flowerPotProperties()
	);

	public static final Block POTTED_PRICKLY_PEAR = registerWithoutItem("potted_prickly_pear",
		properties -> new FlowerPotBlock(PRICKLY_PEAR_CACTUS, properties),
		Blocks.flowerPotProperties()
	);

	public static final ShelfFungiBlock BROWN_SHELF_FUNGI = register("brown_shelf_fungi",
		ShelfFungiBlock::new,
		Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK)
			.lightLevel(state -> 1)
			.randomTicks()
			.noCollission()
			.noOcclusion()
			.sound(WWSoundTypes.MUSHROOM)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final ShelfFungiBlock RED_SHELF_FUNGI = register("red_shelf_fungi",
		ShelfFungiBlock::new,
		Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK)
			.randomTicks()
			.noCollission()
			.noOcclusion()
			.sound(WWSoundTypes.MUSHROOM)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final ShelfFungiBlock CRIMSON_SHELF_FUNGI = register("crimson_shelf_fungi",
		ShelfFungiBlock::new,
		Properties.of()
			.mapColor(MapColor.NETHER)
			.strength(0.2F)
			.randomTicks()
			.noCollission()
			.noOcclusion()
			.sound(SoundType.FUNGUS)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final ShelfFungiBlock WARPED_SHELF_FUNGI = register("warped_shelf_fungi",
		ShelfFungiBlock::new,
		Properties.of()
			.mapColor(MapColor.NETHER)
			.strength(0.2F)
			.randomTicks()
			.noCollission()
			.noOcclusion()
			.sound(SoundType.FUNGUS)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final HugePaleMushroomBlock PALE_MUSHROOM_BLOCK = register("pale_mushroom_block",
		HugePaleMushroomBlock::new,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_GRAY)
			.instrument(NoteBlockInstrument.BASS)
			.strength(0.2F)
			.sound(SoundType.WOOD)
			.ignitedByLava()
	);
	public static final PaleMushroomBlock PALE_MUSHROOM = register("pale_mushroom",
		properties -> new PaleMushroomBlock(WWTreeConfigured.HUGE_PALE_MUSHROOM.getKey(), properties),
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_GRAY)
			.noCollission()
			.randomTicks()
			.instabreak()
			.sound(SoundType.GRASS)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block POTTED_PALE_MUSHROOM = register("potted_pale_mushroom",
		properties -> new FlowerPotBlock(PALE_MUSHROOM, properties),
		Blocks.flowerPotProperties()
	);
	public static final PaleShelfFungiBlock PALE_SHELF_FUNGI = register("pale_shelf_fungi",
		PaleShelfFungiBlock::new,
		Properties.ofFullCopy(PALE_MUSHROOM_BLOCK)
			.randomTicks()
			.noCollission()
			.noOcclusion()
			.sound(WWSoundTypes.MUSHROOM)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final PollenBlock POLLEN = registerWithoutItem("pollen",
		PollenBlock::new,
		Properties.ofFullCopy(Blocks.SHORT_GRASS)
			.mapColor(MapColor.SAND)
			.sound(WWSoundTypes.POLLEN)
			.offsetType(BlockBehaviour.OffsetType.NONE)
	);

	public static final SpongeBudBlock SPONGE_BUD = register("sponge_bud",
		SpongeBudBlock::new,
		Properties.ofFullCopy(Blocks.SPONGE)
			.strength(0.1F)
			.noCollission()
			.noOcclusion()
			.sound(SoundType.SPONGE)
	);

	public static final OstrichEggBlock OSTRICH_EGG = register("ostrich_egg",
		OstrichEggBlock::new,
		Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);

	public static final PenguinEggBlock PENGUIN_EGG = register("penguin_egg",
		PenguinEggBlock::new,
		Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);

	public static final Block NULL_BLOCK = register("null_block",
		Block::new,
		Properties.ofFullCopy(Blocks.STONE)
			.sound(WWSoundTypes.NULL_BLOCK)
	);

	public static final DisplayLanternBlock DISPLAY_LANTERN = registerWithoutItem("display_lantern",
		DisplayLanternBlock::new,
		Properties.of().mapColor(MapColor.METAL).forceSolidOn().strength(3.5F).sound(SoundType.LANTERN)
			.lightLevel(state -> state.getValue(WWBlockStateProperties.DISPLAY_LIGHT))
	);

	public static final GeyserBlock GEYSER = register("geyser",
		GeyserBlock::new,
		Properties.of().mapColor(MapColor.TERRACOTTA_BROWN)
			.sound(WWSoundTypes.GEYSER)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.lightLevel(blockState -> 2)
			.strength(3F)
			.isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false)
			.hasPostProcess(Blocks::always)
			.emissiveRendering(Blocks::always)
	);

	// WOOD

	public static final Block BAOBAB_PLANKS = register("baobab_planks",
		Block::new,
		Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final StairBlock BAOBAB_STAIRS = register("baobab_stairs",
		properties -> new StairBlock(BAOBAB_PLANKS.defaultBlockState(), properties),
		Properties.ofFullCopy(BAOBAB_PLANKS)
	);
	public static final Block BAOBAB_FENCE_GATE = register("baobab_fence_gate",
		properties -> new FenceGateBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_SLAB = register("baobab_slab",
		SlabBlock::new,
		Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final PressurePlateBlock BAOBAB_PRESSURE_PLATE = register("baobab_pressure_plate",
		properties -> new PressurePlateBlock(BAOBAB_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_BUTTON = register("baobab_button",
		properties -> new ButtonBlock(BAOBAB_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final DoorBlock BAOBAB_DOOR = register("baobab_door",
		properties -> new DoorBlock(BAOBAB_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final TrapDoorBlock BAOBAB_TRAPDOOR = register("baobab_trapdoor",
		properties -> new TrapDoorBlock(BAOBAB_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final FenceBlock BAOBAB_FENCE = register("baobab_fence",
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_BAOBAB_LOG = register("stripped_hollowed_baobab_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(BAOBAB_PLANKS_COLOR)
	);

	public static final Block BAOBAB_LOG = register("baobab_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR, SoundType.WOOD)
	);
	public static final SignBlock BAOBAB_SIGN = registerWithoutItem("baobab_sign",
		properties -> new StandingSignBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
	);
	public static final SignBlock BAOBAB_WALL_SIGN = registerWithoutItem("baobab_wall_sign",
		properties -> new WallSignBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
			.overrideDescription(BAOBAB_SIGN.getDescriptionId())
			.overrideLootTable(BAOBAB_SIGN.getLootTable())
	);

	public static final BlockFamily FAMILY_BAOBAB = BlockFamilies.familyBuilder(BAOBAB_PLANKS)
		.button(BAOBAB_BUTTON)
		.slab(BAOBAB_SLAB)
		.stairs(BAOBAB_STAIRS)
		.fence(BAOBAB_FENCE)
		.fenceGate(BAOBAB_FENCE_GATE)
		.pressurePlate(BAOBAB_PRESSURE_PLATE)
		.sign(BAOBAB_SIGN, BAOBAB_WALL_SIGN)
		.door(BAOBAB_DOOR)
		.trapdoor(BAOBAB_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final CeilingHangingSignBlock BAOBAB_HANGING_SIGN = registerWithoutItem("baobab_hanging_sign",
		properties -> new CeilingHangingSignBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
	);
	public static final WallHangingSignBlock BAOBAB_WALL_HANGING_SIGN = registerWithoutItem("baobab_wall_hanging_sign",
		properties -> new WallHangingSignBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
			.overrideDescription(BAOBAB_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(BAOBAB_HANGING_SIGN.getLootTable())
	);
	public static final Block STRIPPED_BAOBAB_LOG = register("stripped_baobab_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(BAOBAB_PLANKS_COLOR, BAOBAB_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final RotatedPillarBlock STRIPPED_BAOBAB_WOOD = register("stripped_baobab_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock BAOBAB_WOOD = register("baobab_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(BAOBAB_BARK_COLOR)
	);

	public static final Block WILLOW_PLANKS = register("willow_planks",
		Block::new,
		Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final StairBlock WILLOW_STAIRS = register("willow_stairs",
		properties -> new StairBlock(WILLOW_PLANKS.defaultBlockState(), properties),
		Properties.ofFullCopy(WILLOW_PLANKS)
	);
	public static final Block WILLOW_FENCE_GATE = register("willow_fence_gate",
		properties -> new FenceGateBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final SlabBlock WILLOW_SLAB = register("willow_slab",
		SlabBlock::new,
		Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final Block WILLOW_BUTTON = register("willow_button",
		properties -> new ButtonBlock(WILLOW_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final PressurePlateBlock WILLOW_PRESSURE_PLATE = register("willow_pressure_plate",
		properties -> new PressurePlateBlock(WILLOW_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final DoorBlock WILLOW_DOOR = register("willow_door",
		properties -> new DoorBlock(WILLOW_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR)
			.mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final TrapDoorBlock WILLOW_TRAPDOOR = register("willow_trapdoor",
		properties -> new TrapDoorBlock(WILLOW_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
			.mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final FenceBlock WILLOW_FENCE = register("willow_fence",
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_WILLOW_LOG = register("stripped_hollowed_willow_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(WILLOW_PLANKS_COLOR)
	);

	public static final Block WILLOW_LOG = register("willow_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(WILLOW_PLANKS_COLOR, WILLOW_BARK_COLOR, SoundType.WOOD)
	);
	public static final SignBlock WILLOW_SIGN = registerWithoutItem("willow_sign",
		properties -> new StandingSignBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(WILLOW_LOG.defaultMapColor())
	);
	public static final SignBlock WILLOW_WALL_SIGN = registerWithoutItem("willow_wall_sign",
		properties -> new WallSignBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(WILLOW_LOG.defaultMapColor())
			.overrideDescription(WILLOW_SIGN.getDescriptionId())
			.overrideLootTable(WILLOW_SIGN.getLootTable())
	);

	public static final BlockFamily FAMILY_WILLOW = BlockFamilies.familyBuilder(WILLOW_PLANKS)
		.button(WILLOW_BUTTON)
		.slab(WILLOW_SLAB)
		.stairs(WILLOW_STAIRS)
		.fence(WILLOW_FENCE)
		.fenceGate(WILLOW_FENCE_GATE)
		.pressurePlate(WILLOW_PRESSURE_PLATE)
		.sign(WILLOW_SIGN, WILLOW_WALL_SIGN)
		.door(WILLOW_DOOR)
		.trapdoor(WILLOW_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final CeilingHangingSignBlock WILLOW_HANGING_SIGN = registerWithoutItem("willow_hanging_sign",
		properties -> new CeilingHangingSignBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(WILLOW_LOG.defaultMapColor())
	);
	public static final WallHangingSignBlock WILLOW_WALL_HANGING_SIGN = registerWithoutItem("willow_wall_hanging_sign",
		properties -> new WallHangingSignBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(WILLOW_LOG.defaultMapColor())
			.overrideDescription(WILLOW_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(WILLOW_HANGING_SIGN.getLootTable())
	);

	public static final Block STRIPPED_WILLOW_LOG = register("stripped_willow_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(WILLOW_PLANKS_COLOR, WILLOW_BARK_COLOR, SoundType.WOOD)
	);
	public static final RotatedPillarBlock STRIPPED_WILLOW_WOOD = register("stripped_willow_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock WILLOW_WOOD = register("willow_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(WILLOW_BARK_COLOR)
	);

	public static final Block CYPRESS_PLANKS = register("cypress_planks",
		Block::new,
		Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final StairBlock CYPRESS_STAIRS = register("cypress_stairs",
		properties -> new StairBlock(CYPRESS_PLANKS.defaultBlockState(), properties),
		Properties.ofFullCopy(CYPRESS_PLANKS)
	);
	public static final Block CYPRESS_FENCE_GATE = register("cypress_fence_gate",
		properties -> new FenceGateBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final SlabBlock CYPRESS_SLAB = register("cypress_slab",
		SlabBlock::new,
		Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_BUTTON = register("cypress_button",
		properties -> new ButtonBlock(CYPRESS_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final PressurePlateBlock CYPRESS_PRESSURE_PLATE = register("cypress_pressure_plate",
		properties -> new PressurePlateBlock(CYPRESS_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final DoorBlock CYPRESS_DOOR = register("cypress_door",
		properties -> new DoorBlock(CYPRESS_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final TrapDoorBlock CYPRESS_TRAPDOOR = register("cypress_trapdoor",
		properties -> new TrapDoorBlock(CYPRESS_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final FenceBlock CYPRESS_FENCE = register("cypress_fence",
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_CYPRESS_LOG = register("stripped_hollowed_cypress_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(CYPRESS_PLANKS_COLOR)
	);

	public static final Block CYPRESS_LOG = register("cypress_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR, SoundType.WOOD)
	);
	public static final SignBlock CYPRESS_SIGN = registerWithoutItem("cypress_sign",
		properties -> new StandingSignBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
	);
	public static final SignBlock CYPRESS_WALL_SIGN = registerWithoutItem("cypress_wall_sign",
		properties -> new WallSignBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
			.overrideDescription(CYPRESS_SIGN.getDescriptionId())
			.overrideLootTable(CYPRESS_SIGN.getLootTable())
	);

	public static final BlockFamily FAMILY_CYPRESS = BlockFamilies.familyBuilder(CYPRESS_PLANKS)
		.button(CYPRESS_BUTTON)
		.slab(CYPRESS_SLAB)
		.stairs(CYPRESS_STAIRS)
		.fence(CYPRESS_FENCE)
		.fenceGate(CYPRESS_FENCE_GATE)
		.pressurePlate(CYPRESS_PRESSURE_PLATE)
		.sign(CYPRESS_SIGN, CYPRESS_WALL_SIGN)
		.door(CYPRESS_DOOR)
		.trapdoor(CYPRESS_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final CeilingHangingSignBlock CYPRESS_HANGING_SIGN = registerWithoutItem("cypress_hanging_sign",
		properties -> new CeilingHangingSignBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
	);
	public static final WallHangingSignBlock CYPRESS_WALL_HANGING_SIGN = registerWithoutItem("cypress_wall_hanging_sign",
		properties -> new WallHangingSignBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
			.overrideDescription(CYPRESS_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(CYPRESS_HANGING_SIGN.getLootTable())
	);

	public static final Block STRIPPED_CYPRESS_LOG = register("stripped_cypress_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR, SoundType.WOOD)
	);
	public static final RotatedPillarBlock STRIPPED_CYPRESS_WOOD = register("stripped_cypress_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock CYPRESS_WOOD = register("cypress_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(CYPRESS_BARK_COLOR)
	);

	public static final Block PALM_PLANKS = register("palm_planks",
		Block::new,
		Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final StairBlock PALM_STAIRS = register("palm_stairs",
		properties -> new StairBlock(PALM_PLANKS.defaultBlockState(), properties),
		Properties.ofFullCopy(PALM_PLANKS)
	);
	public static final Block PALM_FENCE_GATE = register("palm_fence_gate",
		properties -> new FenceGateBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(PALM_PLANKS.defaultMapColor())
	);
	public static final SlabBlock PALM_SLAB = register("palm_slab",
		SlabBlock::new,
		Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_BUTTON = register("palm_button",
		properties -> new ButtonBlock(PALM_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final PressurePlateBlock PALM_PRESSURE_PLATE = register("palm_pressure_plate",
		properties -> new PressurePlateBlock(PALM_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final DoorBlock PALM_DOOR = register("palm_door",
		properties -> new DoorBlock(PALM_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final TrapDoorBlock PALM_TRAPDOOR = register("palm_trapdoor",
		properties -> new TrapDoorBlock(PALM_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final FenceBlock PALM_FENCE = register("palm_fence",
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_PALM_LOG = register("stripped_hollowed_palm_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(PALM_PLANKS_COLOR)
	);

	public static final Block PALM_LOG = register("palm_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(PALM_PLANKS_COLOR, PALM_BARK_COLOR, SoundType.WOOD)
	);
	public static final SignBlock PALM_SIGN = registerWithoutItem("palm_sign",
		properties -> new StandingSignBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
	);
	public static final SignBlock PALM_WALL_SIGN = registerWithoutItem("palm_wall_sign",
		properties -> new WallSignBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
			.overrideDescription(PALM_SIGN.getDescriptionId())
			.overrideLootTable(PALM_SIGN.getLootTable())
	);

	public static final BlockFamily FAMILY_PALM = BlockFamilies.familyBuilder(PALM_PLANKS)
		.button(PALM_BUTTON)
		.slab(PALM_SLAB)
		.stairs(PALM_STAIRS)
		.fence(PALM_FENCE)
		.fenceGate(PALM_FENCE_GATE)
		.pressurePlate(PALM_PRESSURE_PLATE)
		.sign(PALM_SIGN, PALM_WALL_SIGN)
		.door(PALM_DOOR)
		.trapdoor(PALM_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final CeilingHangingSignBlock PALM_HANGING_SIGN = registerWithoutItem("palm_hanging_sign",
		properties -> new CeilingHangingSignBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
	);
	public static final WallHangingSignBlock PALM_WALL_HANGING_SIGN = registerWithoutItem("palm_wall_hanging_sign",
		properties -> new WallHangingSignBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
			.overrideDescription(PALM_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(PALM_HANGING_SIGN.getLootTable())
	);
	public static final Block STRIPPED_PALM_LOG = register("stripped_palm_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(PALM_PLANKS_COLOR, PALM_BARK_COLOR, SoundType.WOOD)
	);
	public static final RotatedPillarBlock STRIPPED_PALM_WOOD = register("stripped_palm_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(PALM_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock PALM_WOOD = register("palm_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(PALM_BARK_COLOR)
	);

	// MAPLE

	public static final Block MAPLE_PLANKS = register("maple_planks",
		Block::new,
		Properties.ofFullCopy(Blocks.OAK_PLANKS)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final StairBlock MAPLE_STAIRS = register("maple_stairs",
		properties -> new StairBlock(MAPLE_PLANKS.defaultBlockState(), properties),
		Properties.ofFullCopy(MAPLE_PLANKS)
	);
	public static final Block MAPLE_FENCE_GATE = register("maple_fence_gate",
		properties -> new FenceGateBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final SlabBlock MAPLE_SLAB = register("maple_slab",
		SlabBlock::new,
		Properties.ofFullCopy(Blocks.OAK_SLAB)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_BUTTON = register("maple_button",
		properties -> new ButtonBlock(MAPLE_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final PressurePlateBlock MAPLE_PRESSURE_PLATE = register("maple_pressure_plate",
		properties -> new PressurePlateBlock(MAPLE_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final DoorBlock MAPLE_DOOR = register("maple_door",
		properties -> new DoorBlock(MAPLE_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final TrapDoorBlock MAPLE_TRAPDOOR = register("maple_trapdoor",
		properties -> new TrapDoorBlock(MAPLE_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final FenceBlock MAPLE_FENCE = register("maple_fence",
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_MAPLE_LOG = register("stripped_hollowed_maple_log",
		HollowedLogBlock::new,
		strippedHollowedLogProperties(MAPLE_PLANKS_COLOR)
	);

	public static final Block MAPLE_LOG = register("maple_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR, SoundType.WOOD)
	);
	public static final SignBlock MAPLE_SIGN = registerWithoutItem("maple_sign",
		properties -> new StandingSignBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
	);
	public static final WallSignBlock MAPLE_WALL_SIGN = registerWithoutItem("maple_wall_sign",
		properties -> new WallSignBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
			.overrideDescription(MAPLE_SIGN.getDescriptionId())
			.overrideLootTable(MAPLE_SIGN.getLootTable())
	);

	public static final BlockFamily FAMILY_MAPLE = BlockFamilies.familyBuilder(MAPLE_PLANKS)
		.button(MAPLE_BUTTON)
		.slab(MAPLE_SLAB)
		.stairs(MAPLE_STAIRS)
		.fence(MAPLE_FENCE)
		.fenceGate(MAPLE_FENCE_GATE)
		.pressurePlate(MAPLE_PRESSURE_PLATE)
		.sign(MAPLE_SIGN, MAPLE_WALL_SIGN)
		.door(MAPLE_DOOR)
		.trapdoor(MAPLE_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	public static final CeilingHangingSignBlock MAPLE_HANGING_SIGN = registerWithoutItem("maple_hanging_sign",
		properties -> new CeilingHangingSignBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
	);
	public static final WallHangingSignBlock MAPLE_WALL_HANGING_SIGN = registerWithoutItem("maple_wall_hanging_sign",
		properties -> new WallHangingSignBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
			.overrideDescription(MAPLE_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(MAPLE_HANGING_SIGN.getLootTable())
	);

	public static final Block STRIPPED_MAPLE_LOG = register("stripped_maple_log",
		RotatedPillarBlock::new,
		Blocks.logProperties(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR, SoundType.WOOD)
	);
	public static final RotatedPillarBlock STRIPPED_MAPLE_WOOD = register("stripped_maple_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final RotatedPillarBlock MAPLE_WOOD = register("maple_wood",
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD)
			.mapColor(MAPLE_BARK_COLOR)
	);

	private WWBlocks() {
		throw new UnsupportedOperationException("WWBlocks contains only static declarations.");
	}

	public static void registerBlocks() {
		WWConstants.logWithModId("Registering Blocks for", WWConstants.UNSTABLE_LOGGING);
	}
	private static <T extends Block> T registerWithoutItem(String path, Function<Properties, T> block, Properties properties) {
		ResourceLocation id = WWConstants.id(path);
		return doRegister(id, makeBlock(block, properties, id));
	}

	private static <T extends Block> T register(String path, Function<Properties, T> block, Properties properties) {
		T registered = registerWithoutItem(path, block, properties);
		Items.registerBlock(registered);
		return registered;
	}

	private static <T extends Block> T doRegister(ResourceLocation id, T block) {
		if (BuiltInRegistries.BLOCK.getOptional(id).isEmpty()) {
			return Registry.register(BuiltInRegistries.BLOCK, id, block);
		}
		throw new IllegalArgumentException("Block with id " + id + " is already in the block registry.");
	}

	private static <T extends Block> T makeBlock(Function<Properties, T> function, Properties properties, ResourceLocation id) {
		return function.apply(properties.setId(ResourceKey.create(Registries.BLOCK, id)));
	}

	@NotNull
	public static Properties hollowedLogProperties(MapColor topMapColor, MapColor sideMapColor, SoundType soundType) {
		return Properties.of()
			.mapColor(state -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(soundType)
			.ignitedByLava();
	}

	@NotNull
	public static Properties hollowedLogProperties(MapColor topMapColor, MapColor sideMapColor) {
		return hollowedLogProperties(topMapColor, sideMapColor, WWSoundTypes.HOLLOWED_LOG);
	}

	@NotNull
	public static Properties hollowedStemProperties(MapColor mapColor) {
		return Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(WWSoundTypes.HOLLOWED_STEM);
	}

	@NotNull
	public static Properties strippedHollowedLogProperties(MapColor mapColor, SoundType soundType) {
		return Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(soundType)
			.ignitedByLava();
	}

	@NotNull
	public static Properties strippedHollowedLogProperties(MapColor mapColor) {
		return strippedHollowedLogProperties(mapColor, WWSoundTypes.HOLLOWED_LOG);
	}

	@NotNull
	public static Properties strippedHollowedStemProperties(MapColor mapColor) {
		return Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(WWSoundTypes.HOLLOWED_STEM);
	}

	@NotNull
	public static LeafLitterBlock leafLitter(
		String id,
		Block sourceBlock,
		@NotNull ParticleType<WWFallingLeavesParticleOptions> particleType,
		float litterChance,
		Supplier<Double> frequencyModifier,
		int textureSize
	) {
		return leafLitter(
			id, sourceBlock, particleType, litterChance, 0.0225F, frequencyModifier, textureSize, 3F, 10F, true, true
		);
	}

	@NotNull
	public static LeafLitterBlock leafLitter(
		String id,
		Block sourceBlock,
		@NotNull ParticleType<WWFallingLeavesParticleOptions> particleType,
		float litterChance,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		boolean flowAway,
		boolean swirl
	) {
		LeafLitterBlock leafLitterBlock = createLeafLitter(id, sourceBlock, particleType);
		FallingLeafUtil.registerFallingLeafWithLitter(
			sourceBlock,
			leafLitterBlock,
			litterChance,
			particleType,
			particleChance,
			frequencyModifier,
			textureSize,
			particleGravityScale,
			windScale,
			flowAway,
			swirl
		);
		return leafLitterBlock;
	}

	private static @NotNull LeafLitterBlock createLeafLitter(String id, Block sourceBlock, @NotNull ParticleType<WWFallingLeavesParticleOptions> particleType) {
		Properties properties = Properties.ofFullCopy(sourceBlock)
			.randomTicks()
			.noCollission()
			.instabreak()
			.replaceable()
			.pushReaction(PushReaction.DESTROY);

		LeafLitterBlock leafLitterBlock = register(id, properties1 -> new LeafLitterBlock(sourceBlock, properties1), properties);
		LeafLitterBlock.LeafLitterParticleRegistry.registerLeafParticle(leafLitterBlock, particleType);
		return leafLitterBlock;
	}

	@NotNull
	public static MesogleaBlock mesoglea(String id, @NotNull MapColor mapColor, @NotNull ParticleOptions particleType, boolean pearlescent) {
		MesogleaBlock mesogleaBlock = register(id,
			properties -> new MesogleaBlock(pearlescent, properties),
			Properties.of()
				.mapColor(mapColor)
				.noOcclusion()
				.strength(0.2F)
				.friction(0.8F)
				.emissiveRendering(Blocks::always)
				.lightLevel(state -> 7)
				.sound(WWSoundTypes.MESOGLEA)
				.isSuffocating(Blocks::never)
				.isViewBlocking(Blocks::never)
				.dynamicShape()
		);
		MesogleaBlock.MesogleaParticleRegistry.registerDripParticle(mesogleaBlock, particleType);
		return mesogleaBlock;
	}

	@NotNull
	public static Properties nematocystProperties(@NotNull MapColor mapColor) {
		return Properties.of()
			.mapColor(mapColor)
			.noCollission()
			.noOcclusion()
			.emissiveRendering(Blocks::always)
			.lightLevel(state -> 4)
			.sound(WWSoundTypes.NEMATOCYST)
			.pushReaction(PushReaction.DESTROY);
	}

	public static void registerBlockProperties() {
		registerDispenses();

		var sign = (FabricBlockEntityType) BlockEntityType.SIGN;
		var hangingSign = (FabricBlockEntityType) BlockEntityType.HANGING_SIGN;

		sign.addSupportedBlock(BAOBAB_SIGN);
		sign.addSupportedBlock(BAOBAB_WALL_SIGN);
		sign.addSupportedBlock(WILLOW_SIGN);
		sign.addSupportedBlock(WILLOW_WALL_SIGN);
		sign.addSupportedBlock(CYPRESS_SIGN);
		sign.addSupportedBlock(CYPRESS_WALL_SIGN);
		sign.addSupportedBlock(PALM_SIGN);
		sign.addSupportedBlock(PALM_WALL_SIGN);
		sign.addSupportedBlock(MAPLE_SIGN);
		sign.addSupportedBlock(MAPLE_WALL_SIGN);

		hangingSign.addSupportedBlock(BAOBAB_HANGING_SIGN);
		hangingSign.addSupportedBlock(BAOBAB_WALL_HANGING_SIGN);
		hangingSign.addSupportedBlock(WILLOW_HANGING_SIGN);
		hangingSign.addSupportedBlock(WILLOW_WALL_HANGING_SIGN);
		hangingSign.addSupportedBlock(CYPRESS_HANGING_SIGN);
		hangingSign.addSupportedBlock(CYPRESS_WALL_HANGING_SIGN);
		hangingSign.addSupportedBlock(PALM_HANGING_SIGN);
		hangingSign.addSupportedBlock(PALM_WALL_HANGING_SIGN);
		hangingSign.addSupportedBlock(MAPLE_HANGING_SIGN);
		hangingSign.addSupportedBlock(MAPLE_WALL_HANGING_SIGN);

		registerStrippable();
		registerComposting();
		registerFlammability();
		registerFuels();
		registerBonemeal();
		registerAxe();
		registerInventories();
	}

	private static void registerDispenses() {
		DispenserBlock.registerBehavior(TUMBLEWEED, new DefaultDispenseItemBehavior() {
			@Override
			@NotNull
			public ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
				Level level = source.level();
				Direction direction = source.state().getValue(DispenserBlock.FACING);
				Vec3 position = source.center().add(direction.getStepX(), direction.getStepY(), direction.getStepZ());
				Tumbleweed tumbleweed = new Tumbleweed(WWEntityTypes.TUMBLEWEED, level);
				Vec3 vec3 = new Vec3(direction.getStepX(), direction.getStepY() + 0.1D, direction.getStepZ()).normalize().add(level.random.triangle(0D, 0.0172275D * 6D), level.random.triangle(0D, 0.0172275D * 6D), level.random.triangle(0D, 0.0172275D * 6D)).scale(1.1D);
				tumbleweed.setDeltaMovement(vec3);
				tumbleweed.setPos(position);
				level.addFreshEntity(tumbleweed);
				stack.shrink(1);
				return stack;
			}
		});
	}

	private static void registerStrippable() {
		StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
		StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
		StrippableBlockRegistry.register(WILLOW_LOG, STRIPPED_WILLOW_LOG);
		StrippableBlockRegistry.register(WILLOW_WOOD, STRIPPED_WILLOW_WOOD);
		StrippableBlockRegistry.register(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
		StrippableBlockRegistry.register(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
		StrippableBlockRegistry.register(PALM_LOG, STRIPPED_PALM_LOG);
		StrippableBlockRegistry.register(PALM_WOOD, STRIPPED_PALM_WOOD);
		StrippableBlockRegistry.register(MAPLE_LOG, STRIPPED_MAPLE_LOG);
		StrippableBlockRegistry.register(MAPLE_WOOD, STRIPPED_MAPLE_WOOD);

		StrippableBlockRegistry.register(HOLLOWED_ACACIA_LOG, STRIPPED_HOLLOWED_ACACIA_LOG);
		StrippableBlockRegistry.register(HOLLOWED_BIRCH_LOG, STRIPPED_HOLLOWED_BIRCH_LOG);
		StrippableBlockRegistry.register(HOLLOWED_CHERRY_LOG, STRIPPED_HOLLOWED_CHERRY_LOG);
		StrippableBlockRegistry.register(HOLLOWED_DARK_OAK_LOG, STRIPPED_HOLLOWED_DARK_OAK_LOG);
		StrippableBlockRegistry.register(HOLLOWED_OAK_LOG, STRIPPED_HOLLOWED_OAK_LOG);
		StrippableBlockRegistry.register(HOLLOWED_SPRUCE_LOG, STRIPPED_HOLLOWED_SPRUCE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_JUNGLE_LOG, STRIPPED_HOLLOWED_JUNGLE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_MANGROVE_LOG, STRIPPED_HOLLOWED_MANGROVE_LOG);
		StrippableBlockRegistry.register(HOLLOWED_PALE_OAK_LOG, STRIPPED_HOLLOWED_PALE_OAK_LOG);
		StrippableBlockRegistry.register(HOLLOWED_CRIMSON_STEM, STRIPPED_HOLLOWED_CRIMSON_STEM);
		StrippableBlockRegistry.register(HOLLOWED_WARPED_STEM, STRIPPED_HOLLOWED_WARPED_STEM);
		StrippableBlockRegistry.register(HOLLOWED_WILLOW_LOG, STRIPPED_HOLLOWED_WILLOW_LOG);
		StrippableBlockRegistry.register(HOLLOWED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		StrippableBlockRegistry.register(HOLLOWED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
		StrippableBlockRegistry.register(HOLLOWED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
		StrippableBlockRegistry.register(HOLLOWED_MAPLE_LOG, STRIPPED_HOLLOWED_MAPLE_LOG);
	}

	private static void registerComposting() {
		CompostingChanceRegistry.INSTANCE.add(CARNATION, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(CATTAIL, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(DATURA, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(MILKWEED, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(MARIGOLD, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(SEEDING_DANDELION, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(FLOWERING_LILY_PAD, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(BROWN_SHELF_FUNGI, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(RED_SHELF_FUNGI, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(WILLOW_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(PALM_FRONDS, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(YELLOW_MAPLE_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ORANGE_MAPLE_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(RED_MAPLE_LEAVES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(WILLOW_SAPLING, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(BAOBAB_NUT, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(MAPLE_SAPLING, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(WWItems.COCONUT, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(WWItems.SPLIT_COCONUT, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(YELLOW_HIBISCUS, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(WHITE_HIBISCUS, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(PINK_HIBISCUS, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(PURPLE_HIBISCUS, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(ALGAE, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(MYCELIUM_GROWTH, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(BUSH, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED_PLANT, 0.5F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(WWItems.PRICKLY_PEAR, 0.5F);
		CompostingChanceRegistry.INSTANCE.add(WWItems.PEELED_PRICKLY_PEAR, 0.5F);
		CompostingChanceRegistry.INSTANCE.add(YELLOW_MAPLE_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(ORANGE_MAPLE_LEAF_LITTER, 0.1F);
		CompostingChanceRegistry.INSTANCE.add(RED_MAPLE_LEAF_LITTER, 0.1F);
	}

	private static void registerFlammability() {
		WWConstants.logWithModId("Registering Flammability for", WWConstants.UNSTABLE_LOGGING);
		var flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableBlockRegistry.add(POLLEN, 200, 60);
		flammableBlockRegistry.add(SEEDING_DANDELION, 100, 60);
		flammableBlockRegistry.add(CARNATION, 100, 60);
		flammableBlockRegistry.add(CATTAIL, 100, 60);
		flammableBlockRegistry.add(DATURA, 100, 60);
		flammableBlockRegistry.add(MILKWEED, 100, 60);
		flammableBlockRegistry.add(MARIGOLD, 100, 60);
		flammableBlockRegistry.add(YELLOW_HIBISCUS, 100, 60);
		flammableBlockRegistry.add(WHITE_HIBISCUS, 100, 60);
		flammableBlockRegistry.add(PINK_HIBISCUS, 100, 60);
		flammableBlockRegistry.add(PURPLE_HIBISCUS, 100, 60);
		flammableBlockRegistry.add(TUMBLEWEED, 100, 60);
		flammableBlockRegistry.add(TUMBLEWEED_PLANT, 100, 60);
		flammableBlockRegistry.add(BUSH, 90, 40);
		flammableBlockRegistry.add(MYCELIUM_GROWTH, 100, 60);

		flammableBlockRegistry.add(HOLLOWED_BIRCH_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_CHERRY_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_ACACIA_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_JUNGLE_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_DARK_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_MANGROVE_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_CHERRY_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_SPRUCE_LOG, 5, 5);
		flammableBlockRegistry.add(HOLLOWED_PALE_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_BIRCH_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_CHERRY_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_ACACIA_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_JUNGLE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_DARK_OAK_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_MANGROVE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_SPRUCE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_PALE_OAK_LOG, 5, 5);

		flammableBlockRegistry.add(HOLLOWED_BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_BAOBAB_LOG, 5, 5);
		flammableBlockRegistry.add(BAOBAB_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_BAOBAB_WOOD, 5, 5);
		flammableBlockRegistry.add(BAOBAB_PLANKS, 5, 20);
		flammableBlockRegistry.add(BAOBAB_STAIRS, 5, 20);
		flammableBlockRegistry.add(BAOBAB_DOOR, 5, 20);
		flammableBlockRegistry.add(BAOBAB_FENCE, 5, 20);
		flammableBlockRegistry.add(BAOBAB_SLAB, 5, 20);
		flammableBlockRegistry.add(BAOBAB_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(BAOBAB_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(BAOBAB_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(BAOBAB_LEAVES, 100, 60);
		flammableBlockRegistry.add(BAOBAB_BUTTON, 5, 20);
		flammableBlockRegistry.add(BAOBAB_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(HOLLOWED_WILLOW_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_WILLOW_LOG, 5, 5);
		flammableBlockRegistry.add(WILLOW_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_WILLOW_LOG, 5, 5);
		flammableBlockRegistry.add(WILLOW_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_WILLOW_WOOD, 5, 5);
		flammableBlockRegistry.add(WILLOW_PLANKS, 5, 20);
		flammableBlockRegistry.add(WILLOW_STAIRS, 5, 20);
		flammableBlockRegistry.add(WILLOW_DOOR, 5, 20);
		flammableBlockRegistry.add(WILLOW_FENCE, 5, 20);
		flammableBlockRegistry.add(WILLOW_SLAB, 5, 20);
		flammableBlockRegistry.add(WILLOW_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(WILLOW_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(WILLOW_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(WILLOW_LEAVES, 100, 60);
		flammableBlockRegistry.add(WILLOW_BUTTON, 5, 20);
		flammableBlockRegistry.add(WILLOW_SIGN, 5, 20);
		flammableBlockRegistry.add(WILLOW_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(WILLOW_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(WILLOW_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(HOLLOWED_CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_CYPRESS_LOG, 5, 5);
		flammableBlockRegistry.add(CYPRESS_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_CYPRESS_WOOD, 5, 5);
		flammableBlockRegistry.add(CYPRESS_PLANKS, 5, 20);
		flammableBlockRegistry.add(CYPRESS_STAIRS, 5, 20);
		flammableBlockRegistry.add(CYPRESS_DOOR, 5, 20);
		flammableBlockRegistry.add(CYPRESS_FENCE, 5, 20);
		flammableBlockRegistry.add(CYPRESS_SLAB, 5, 20);
		flammableBlockRegistry.add(CYPRESS_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(CYPRESS_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(CYPRESS_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(CYPRESS_LEAVES, 100, 60);
		flammableBlockRegistry.add(CYPRESS_BUTTON, 5, 20);
		flammableBlockRegistry.add(CYPRESS_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(HOLLOWED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(PALM_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_PALM_LOG, 5, 5);
		flammableBlockRegistry.add(PALM_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_PALM_WOOD, 5, 5);
		flammableBlockRegistry.add(PALM_PLANKS, 5, 20);
		flammableBlockRegistry.add(PALM_STAIRS, 5, 20);
		flammableBlockRegistry.add(PALM_DOOR, 5, 20);
		flammableBlockRegistry.add(PALM_FENCE, 5, 20);
		flammableBlockRegistry.add(PALM_SLAB, 5, 20);
		flammableBlockRegistry.add(PALM_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(PALM_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(PALM_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(PALM_FRONDS, 100, 60);
		flammableBlockRegistry.add(PALM_BUTTON, 5, 20);
		flammableBlockRegistry.add(PALM_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_WALL_HANGING_SIGN, 5, 20);

		flammableBlockRegistry.add(HOLLOWED_MAPLE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_MAPLE_LOG, 5, 5);
		flammableBlockRegistry.add(MAPLE_LOG, 5, 5);
		flammableBlockRegistry.add(STRIPPED_MAPLE_LOG, 5, 5);
		flammableBlockRegistry.add(MAPLE_WOOD, 5, 5);
		flammableBlockRegistry.add(STRIPPED_MAPLE_WOOD, 5, 5);
		flammableBlockRegistry.add(MAPLE_PLANKS, 5, 20);
		flammableBlockRegistry.add(MAPLE_STAIRS, 5, 20);
		flammableBlockRegistry.add(MAPLE_DOOR, 5, 20);
		flammableBlockRegistry.add(MAPLE_FENCE, 5, 20);
		flammableBlockRegistry.add(MAPLE_SLAB, 5, 20);
		flammableBlockRegistry.add(MAPLE_FENCE_GATE, 5, 20);
		flammableBlockRegistry.add(MAPLE_PRESSURE_PLATE, 5, 20);
		flammableBlockRegistry.add(MAPLE_TRAPDOOR, 5, 20);
		flammableBlockRegistry.add(YELLOW_MAPLE_LEAVES, 100, 60);
		flammableBlockRegistry.add(ORANGE_MAPLE_LEAVES, 100, 60);
		flammableBlockRegistry.add(RED_MAPLE_LEAVES, 100, 60);
		flammableBlockRegistry.add(YELLOW_MAPLE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(ORANGE_MAPLE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(RED_MAPLE_LEAF_LITTER, 200, 60);
		flammableBlockRegistry.add(MAPLE_BUTTON, 5, 20);
		flammableBlockRegistry.add(MAPLE_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_WALL_HANGING_SIGN, 5, 20);
	}

	private static void registerFuels() {
		WWConstants.logWithModId("Registering Fuels for", WWConstants.UNSTABLE_LOGGING);

		FuelRegistryEvents.BUILD.register((builder, context) -> {
			builder.add(WWItems.BAOBAB_BOAT, 1200);
			builder.add(WWItems.BAOBAB_CHEST_BOAT, 1200);
			builder.add(BAOBAB_LOG.asItem(), 300);
			builder.add(STRIPPED_BAOBAB_LOG.asItem(), 300);
			builder.add(BAOBAB_WOOD.asItem(), 300);
			builder.add(STRIPPED_BAOBAB_WOOD.asItem(), 300);
			builder.add(BAOBAB_PLANKS.asItem(), 300);
			builder.add(BAOBAB_SLAB.asItem(), 150);
			builder.add(BAOBAB_STAIRS.asItem(), 300);
			builder.add(BAOBAB_PRESSURE_PLATE.asItem(), 300);
			builder.add(BAOBAB_BUTTON.asItem(), 100);
			builder.add(BAOBAB_TRAPDOOR.asItem(), 300);
			builder.add(BAOBAB_FENCE_GATE.asItem(), 300);
			builder.add(BAOBAB_FENCE.asItem(), 300);
			builder.add(WWItems.BAOBAB_SIGN, 300);
			builder.add(WWItems.BAOBAB_HANGING_SIGN, 800);
			builder.add(WWItems.BAOBAB_NUT, 100);

			builder.add(WWItems.WILLOW_BOAT, 1200);
			builder.add(WWItems.WILLOW_CHEST_BOAT, 1200);
			builder.add(WILLOW_LOG.asItem(), 300);
			builder.add(STRIPPED_WILLOW_LOG.asItem(), 300);
			builder.add(WILLOW_WOOD.asItem(), 300);
			builder.add(STRIPPED_WILLOW_WOOD.asItem(), 300);
			builder.add(WILLOW_PLANKS.asItem(), 300);
			builder.add(WILLOW_SLAB.asItem(), 150);
			builder.add(WILLOW_STAIRS.asItem(), 300);
			builder.add(WILLOW_PRESSURE_PLATE.asItem(), 300);
			builder.add(WILLOW_BUTTON.asItem(), 100);
			builder.add(WILLOW_TRAPDOOR.asItem(), 300);
			builder.add(WILLOW_FENCE_GATE.asItem(), 300);
			builder.add(WILLOW_FENCE.asItem(), 300);
			builder.add(WWItems.WILLOW_SIGN, 300);
			builder.add(WWItems.WILLOW_HANGING_SIGN, 800);
			builder.add(WILLOW_SAPLING.asItem(), 100);

			builder.add(WWItems.CYPRESS_BOAT, 1200);
			builder.add(WWItems.CYPRESS_CHEST_BOAT, 1200);
			builder.add(CYPRESS_LOG.asItem(), 300);
			builder.add(STRIPPED_CYPRESS_LOG.asItem(), 300);
			builder.add(CYPRESS_WOOD.asItem(), 300);
			builder.add(STRIPPED_CYPRESS_WOOD.asItem(), 300);
			builder.add(CYPRESS_PLANKS.asItem(), 300);
			builder.add(CYPRESS_SLAB.asItem(), 150);
			builder.add(CYPRESS_STAIRS.asItem(), 300);
			builder.add(CYPRESS_PRESSURE_PLATE.asItem(), 300);
			builder.add(CYPRESS_BUTTON.asItem(), 100);
			builder.add(CYPRESS_TRAPDOOR.asItem(), 300);
			builder.add(CYPRESS_FENCE_GATE.asItem(), 300);
			builder.add(CYPRESS_FENCE.asItem(), 300);
			builder.add(WWItems.CYPRESS_SIGN, 300);
			builder.add(WWItems.CYPRESS_HANGING_SIGN, 800);
			builder.add(CYPRESS_SAPLING.asItem(), 100);

			builder.add(WWItems.PALM_BOAT, 1200);
			builder.add(WWItems.PALM_CHEST_BOAT, 1200);
			builder.add(PALM_LOG.asItem(), 300);
			builder.add(STRIPPED_PALM_LOG.asItem(), 300);
			builder.add(PALM_WOOD.asItem(), 300);
			builder.add(STRIPPED_PALM_WOOD.asItem(), 300);
			builder.add(PALM_PLANKS.asItem(), 300);
			builder.add(PALM_SLAB.asItem(), 150);
			builder.add(PALM_STAIRS.asItem(), 300);
			builder.add(PALM_PRESSURE_PLATE.asItem(), 300);
			builder.add(PALM_BUTTON.asItem(), 100);
			builder.add(PALM_TRAPDOOR.asItem(), 300);
			builder.add(PALM_FENCE_GATE.asItem(), 300);
			builder.add(PALM_FENCE.asItem(), 300);
			builder.add(WWItems.PALM_SIGN, 300);
			builder.add(WWItems.PALM_HANGING_SIGN, 800);
			builder.add(WWItems.COCONUT, 150); // COCONUT OIL IS KNOWN TO BE FLAMMABLE :)
			builder.add(WWItems.SPLIT_COCONUT, 75);

			builder.add(WWItems.MAPLE_BOAT, 1200);
			builder.add(WWItems.MAPLE_CHEST_BOAT, 1200);
			builder.add(MAPLE_LOG.asItem(), 300);
			builder.add(STRIPPED_MAPLE_LOG.asItem(), 300);
			builder.add(MAPLE_WOOD.asItem(), 300);
			builder.add(STRIPPED_MAPLE_WOOD.asItem(), 300);
			builder.add(MAPLE_PLANKS.asItem(), 300);
			builder.add(MAPLE_SLAB.asItem(), 150);
			builder.add(MAPLE_STAIRS.asItem(), 300);
			builder.add(MAPLE_PRESSURE_PLATE.asItem(), 300);
			builder.add(MAPLE_BUTTON.asItem(), 100);
			builder.add(MAPLE_TRAPDOOR.asItem(), 300);
			builder.add(MAPLE_FENCE_GATE.asItem(), 300);
			builder.add(MAPLE_FENCE.asItem(), 300);
			builder.add(WWItems.MAPLE_SIGN, 300);
			builder.add(WWItems.MAPLE_HANGING_SIGN, 800);
			builder.add(MAPLE_SAPLING.asItem(), 100);

			builder.add(HOLLOWED_WARPED_STEM.asItem(), 300);
			builder.add(HOLLOWED_CRIMSON_STEM.asItem(), 300);
			builder.add(HOLLOWED_MANGROVE_LOG.asItem(), 300);
			builder.add(HOLLOWED_ACACIA_LOG.asItem(), 300);
			builder.add(HOLLOWED_JUNGLE_LOG.asItem(), 300);
			builder.add(HOLLOWED_DARK_OAK_LOG.asItem(), 300);
			builder.add(HOLLOWED_SPRUCE_LOG.asItem(), 300);
			builder.add(HOLLOWED_CHERRY_LOG.asItem(), 300);
			builder.add(HOLLOWED_BIRCH_LOG.asItem(), 300);
			builder.add(HOLLOWED_PALE_OAK_LOG.asItem(), 300);
			builder.add(HOLLOWED_BAOBAB_LOG.asItem(), 300);
			builder.add(HOLLOWED_WILLOW_LOG.asItem(), 300);
			builder.add(HOLLOWED_CYPRESS_LOG.asItem(), 300);
			builder.add(HOLLOWED_PALM_LOG.asItem(), 300);
			builder.add(HOLLOWED_MAPLE_LOG.asItem(), 300);

			builder.add(STRIPPED_HOLLOWED_WARPED_STEM.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_CRIMSON_STEM.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_MANGROVE_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_ACACIA_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_JUNGLE_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_DARK_OAK_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_SPRUCE_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_CHERRY_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_BIRCH_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_PALE_OAK_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_BAOBAB_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_WILLOW_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_CYPRESS_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_PALM_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_MAPLE_LOG.asItem(), 300);

			builder.add(TUMBLEWEED.asItem(), 150);
			builder.add(TUMBLEWEED_PLANT.asItem(), 150);
		});
	}

	private static void registerBonemeal() {
		BonemealBehaviors.register(
			Blocks.LILY_PAD,
			new BonemealBehaviors.BonemealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return WWBlockConfig.get().flower.bonemealLilypads;
				}

				@Override
				public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					level.setBlock(pos, FLOWERING_LILY_PAD.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		);

		BonemealBehaviors.register(
			Blocks.DANDELION,
			new BonemealBehaviors.BonemealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return WWBlockConfig.get().flower.bonemealDandelions;
				}

				@Override
				public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					level.setBlock(pos, SEEDING_DANDELION.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		);

		BonemealBehaviors.register(
			Blocks.MYCELIUM,
			new BonemealBehaviors.BonemealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return level.getBlockState(pos.above()).isAir();
				}

				@Override
				public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					BlockPos blockPos = pos.above();
					Optional<Holder.Reference<PlacedFeature>> optional = level.registryAccess()
						.lookupOrThrow(Registries.PLACED_FEATURE)
						.get(WWMiscPlaced.MYCELIUM_GROWTH_BONEMEAL.getKey());

					masterLoop:
					for (int i = 0; i < 128; i++) {
						BlockPos blockPos2 = blockPos;

						for (int j = 0; j < i / 16; j++) {
							blockPos2 = blockPos2.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
							if (!level.getBlockState(blockPos2.below()).is(Blocks.MYCELIUM) || level.getBlockState(blockPos2).isCollisionShapeFullBlock(level, blockPos2)) {
								continue masterLoop;
							}
						}

						BlockState blockState2 = level.getBlockState(blockPos2);
						if (blockState2.isAir()) {
							if (optional.isEmpty()) continue;
							optional.get().value().place(level, level.getChunkSource().getGenerator(), random, blockPos2);
						}
					}
				}

				@Override
				public BlockPos getParticlePos(BlockState state, BlockPos pos) {
					return pos.above();
				}

				@Override
				public boolean isNeighborSpreader() {
					return true;
				}
			}
		);
	}

	private static void registerAxe() {
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.OAK_LOG, HOLLOWED_OAK_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.BIRCH_LOG, HOLLOWED_BIRCH_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.CHERRY_LOG, HOLLOWED_CHERRY_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.SPRUCE_LOG, HOLLOWED_SPRUCE_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.DARK_OAK_LOG, HOLLOWED_DARK_OAK_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.JUNGLE_LOG, HOLLOWED_OAK_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.ACACIA_LOG, HOLLOWED_ACACIA_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.MANGROVE_LOG, HOLLOWED_MANGROVE_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.PALE_OAK_LOG, HOLLOWED_PALE_OAK_LOG);
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.CRIMSON_STEM, HOLLOWED_CRIMSON_STEM);
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.WARPED_STEM, HOLLOWED_WARPED_STEM);
		HollowedLogBlock.registerAxeHollowBehavior(BAOBAB_LOG, HOLLOWED_BAOBAB_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(WILLOW_LOG, HOLLOWED_WILLOW_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(CYPRESS_LOG, HOLLOWED_CYPRESS_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(PALM_LOG, HOLLOWED_PALM_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(MAPLE_LOG, HOLLOWED_MAPLE_LOG);
		//STRIPPED
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_OAK_LOG, STRIPPED_HOLLOWED_OAK_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_BIRCH_LOG, STRIPPED_HOLLOWED_BIRCH_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_CHERRY_LOG, STRIPPED_HOLLOWED_CHERRY_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_SPRUCE_LOG, STRIPPED_HOLLOWED_SPRUCE_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_DARK_OAK_LOG, STRIPPED_HOLLOWED_DARK_OAK_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_JUNGLE_LOG, STRIPPED_HOLLOWED_OAK_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_ACACIA_LOG, STRIPPED_HOLLOWED_ACACIA_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_MANGROVE_LOG, STRIPPED_HOLLOWED_MANGROVE_LOG);
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.STRIPPED_PALE_OAK_LOG, STRIPPED_HOLLOWED_PALE_OAK_LOG);
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.STRIPPED_CRIMSON_STEM, STRIPPED_HOLLOWED_CRIMSON_STEM);
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.STRIPPED_WARPED_STEM, STRIPPED_HOLLOWED_WARPED_STEM);
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_BAOBAB_LOG, STRIPPED_HOLLOWED_BAOBAB_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_WILLOW_LOG, STRIPPED_HOLLOWED_WILLOW_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_CYPRESS_LOG, STRIPPED_HOLLOWED_CYPRESS_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_PALM_LOG, STRIPPED_HOLLOWED_PALM_LOG);
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_MAPLE_LOG, STRIPPED_HOLLOWED_MAPLE_LOG);
	}

	private static void registerInventories() {
		ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(), STONE_CHEST);
	}
}
