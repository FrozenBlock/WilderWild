/*
 * Copyright 2023-2024 FrozenBlock
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

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.axe.api.AxeBehaviors;
import net.frozenblock.lib.block.api.FrozenCeilingHangingSignBlock;
import net.frozenblock.lib.block.api.FrozenSignBlock;
import net.frozenblock.lib.block.api.FrozenWallHangingSignBlock;
import net.frozenblock.lib.block.api.FrozenWallSignBlock;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.lib.item.api.bonemeal.BonemealBehaviors;
import net.frozenblock.lib.storage.api.NoInteractionStorage;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.block.BaobabLeavesBlock;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.FlowerLichenBlock;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.GloryOfTheSnowBlock;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.block.MilkweedBlock;
import net.frozenblock.wilderwild.block.NematocystBlock;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.block.OstrichEggBlock;
import net.frozenblock.wilderwild.block.PalmFrondsBlock;
import net.frozenblock.wilderwild.block.PollenBlock;
import net.frozenblock.wilderwild.block.PricklyPearCactusBlock;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.SculkSlabBlock;
import net.frozenblock.wilderwild.block.SculkStairBlock;
import net.frozenblock.wilderwild.block.SculkWallBlock;
import net.frozenblock.wilderwild.block.SeedingFlowerBlock;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.block.SmallSpongeBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMoundBlock;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.WilderBushBlock;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.entity.ai.TermiteManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.impl.sapling.BaobabSaplingGenerator;
import net.frozenblock.wilderwild.world.impl.sapling.CypressSaplingGenerator;
import net.frozenblock.wilderwild.world.impl.sapling.PalmSaplingGenerator;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class RegisterBlocks {
	public static final BlockSetType BAOBAB_SET = BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA).register(WilderSharedConstants.id("baobab"));
	public static final BlockSetType CYPRESS_SET = BlockSetTypeBuilder.copyOf(BlockSetType.BIRCH).register(WilderSharedConstants.id("cypress"));
	public static final BlockSetType PALM_SET = BlockSetTypeBuilder.copyOf(BlockSetType.JUNGLE).register(WilderSharedConstants.id("palm"));
	public static final WoodType BAOBAB_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.ACACIA).register(WilderSharedConstants.id("baobab"), BAOBAB_SET);
	public static final WoodType CYPRESS_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.BIRCH).register(WilderSharedConstants.id("cypress"), CYPRESS_SET);
	public static final WoodType PALM_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.JUNGLE).register(WilderSharedConstants.id("palm"), PALM_SET);

	// OTHER (BUILDING BLOCKS)

	public static final Block CHISELED_MUD_BRICKS = new Block(
		FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS)
			.strength(1.5F)
			.requiresTool()
			.sounds(SoundType.MUD_BRICKS)
	);

	public static final ScorchedBlock SCORCHED_SAND = new ScorchedBlock(
		Blocks.SAND.defaultBlockState(),
		true,
		SoundEvents.BRUSH_SAND,
		SoundEvents.BRUSH_SAND_COMPLETED,
		FabricBlockSettings.create()
			.strength(1.5F)
			.sounds(RegisterBlockSoundTypes.SCORCHEDSAND)
			.mapColor(MapColor.SAND)
			.ticksRandomly()
	);

	public static final ScorchedBlock SCORCHED_RED_SAND = new ScorchedBlock(
		Blocks.RED_SAND.defaultBlockState(),
		true,
		SoundEvents.BRUSH_SAND,
		SoundEvents.BRUSH_SAND_COMPLETED,
		FabricBlockSettings.create()
			.strength(1.5F)
			.sounds(RegisterBlockSoundTypes.SCORCHEDSAND)
			.mapColor(MapColor.COLOR_ORANGE)
			.ticksRandomly()
	);

	public static final BaobabNutBlock BAOBAB_NUT = new BaobabNutBlock(
		new BaobabSaplingGenerator(),
		FabricBlockSettings.copyOf(Blocks.BAMBOO)
			.sounds(RegisterBlockSoundTypes.BAOBAB_NUT)
	);

	public static final FlowerPotBlock POTTED_BAOBAB_NUT = Blocks.flowerPot(BAOBAB_NUT);
	public static final PricklyPearCactusBlock PRICKLY_PEAR_CACTUS = new PricklyPearCactusBlock(
		FabricBlockSettings.copyOf(Blocks.CACTUS)
			.noCollision()
			.offsetType(BlockBehaviour.OffsetType.XZ)
	);
	public static final WaterloggableSaplingBlock CYPRESS_SAPLING = new WaterloggableSaplingBlock(
		new CypressSaplingGenerator(),
		FabricBlockSettings.copyOf(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_CYPRESS_SAPLING = Blocks.flowerPot(CYPRESS_SAPLING);
	public static final CoconutBlock COCONUT = new CoconutBlock(
		new PalmSaplingGenerator(),
		FabricBlockSettings.create().breakInstantly().ticksRandomly().sounds(RegisterBlockSoundTypes.COCONUT)
	);
	public static final Block POTTED_COCONUT = Blocks.flowerPot(COCONUT);
	public static final Block CYPRESS_LEAVES = Blocks.leaves(SoundType.GRASS); // in front so the other leaves can have a copy of its settings
	public static final Block BAOBAB_LEAVES = new BaobabLeavesBlock(FabricBlockSettings.copyOf(CYPRESS_LEAVES));
	public static final PalmFrondsBlock PALM_FRONDS = new PalmFrondsBlock(FabricBlockSettings.copyOf(CYPRESS_LEAVES));
	public static final HollowedLogBlock HOLLOWED_OAK_LOG = createHollowedLogBlock(MapColor.WOOD, MapColor.PODZOL);
	public static final HollowedLogBlock HOLLOWED_SPRUCE_LOG =  createHollowedLogBlock(MapColor.PODZOL, MapColor.COLOR_BROWN);
	public static final HollowedLogBlock HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MapColor.SAND, MapColor.QUARTZ);
	public static final HollowedLogBlock HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MapColor.DIRT, MapColor.PODZOL);
	public static final HollowedLogBlock HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MapColor.COLOR_ORANGE, MapColor.STONE);
	public static final HollowedLogBlock HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN);
	public static final HollowedLogBlock HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MapColor.COLOR_RED, MapColor.PODZOL);
	public static final HollowedLogBlock HOLLOWED_CHERRY_LOG = createHollowedLogBlock(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, RegisterBlockSoundTypes.HOLLOWED_CHERRY_LOG);
	public static final HollowedLogBlock HOLLOWED_CRIMSON_STEM = createHollowedStemBlock(MapColor.CRIMSON_STEM);
	public static final HollowedLogBlock HOLLOWED_WARPED_STEM = createHollowedStemBlock(MapColor.WARPED_STEM);
	public static final HollowedLogBlock HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MapColor.COLOR_ORANGE, MapColor.COLOR_BROWN);
	public static final HollowedLogBlock HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MapColor.COLOR_LIGHT_GRAY, MapColor.STONE);
	// STRIPPED HOLLOWED LOGS
	public static final HollowedLogBlock STRIPPED_HOLLOWED_OAK_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_OAK_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_SPRUCE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_SPRUCE_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_BIRCH_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_BIRCH_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_CHERRY_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_CHERRY_LOG.defaultMapColor(), RegisterBlockSoundTypes.HOLLOWED_CHERRY_LOG);
	public static final HollowedLogBlock STRIPPED_HOLLOWED_JUNGLE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_JUNGLE_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_ACACIA_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_ACACIA_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_DARK_OAK_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_DARK_OAK_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_MANGROVE_LOG = createStrippedHollowedLogBlock(Blocks.STRIPPED_MANGROVE_LOG.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_CRIMSON_STEM = createStrippedHollowedStemBlock(Blocks.STRIPPED_CRIMSON_STEM.defaultMapColor());
	public static final HollowedLogBlock STRIPPED_HOLLOWED_WARPED_STEM = createStrippedHollowedStemBlock(Blocks.STRIPPED_WARPED_STEM.defaultMapColor());
	// SCULK
	public static final SculkStairBlock SCULK_STAIRS = new SculkStairBlock(
		Blocks.SCULK.defaultBlockState(),
		FabricBlockSettings.create()
			.mapColor(MapColor.COLOR_BLACK)
			.strength(0.2F)
			.sounds(SoundType.SCULK)
	);

	public static final SculkSlabBlock SCULK_SLAB = new SculkSlabBlock(
		FabricBlockSettings.create()
			.mapColor(MapColor.COLOR_BLACK)
			.strength(0.2F)
			.sounds(SoundType.SCULK));

	public static final SculkWallBlock SCULK_WALL = new SculkWallBlock(
		FabricBlockSettings.create()
			.mapColor(MapColor.COLOR_BLACK)
			.strength(0.2F)
			.sounds(SoundType.SCULK)
	);

	public static final OsseousSculkBlock OSSEOUS_SCULK = new OsseousSculkBlock(
		FabricBlockSettings.create()
			.mapColor(MapColor.SAND)
			.strength(2.0F)
			.sounds(RegisterBlockSoundTypes.OSSEOUS_SCULK)
	);

	public static final HangingTendrilBlock HANGING_TENDRIL = new HangingTendrilBlock(
		FabricBlockSettings.copyOf(Blocks.SCULK_SENSOR)
			.strength(0.7F)
			.noCollision()
			.nonOpaque()
			.ticksRandomly()
			.luminance(state -> 1)
			.sounds(RegisterBlockSoundTypes.HANGING_TENDRIL)
			.emissiveLighting((state, level, pos) -> HangingTendrilBlock.shouldHavePogLighting(state))
	);

	public static final EchoGlassBlock ECHO_GLASS = new EchoGlassBlock(
		FabricBlockSettings.copyOf(Blocks.TINTED_GLASS)
			.mapColor(MapColor.COLOR_CYAN)
			.nonOpaque()
			.ticksRandomly()
			.sounds(RegisterBlockSoundTypes.ECHO_GLASS)
			.instrument(NoteBlockInstrument.HAT)
	);

	// Mesoglea
	public static final MesogleaBlock BLUE_PEARLESCENT_MESOGLEA = mesoglea(
		MapColor.QUARTZ,
		RegisterParticles.BLUE_PEARLESCENT_HANGING_MESOGLEA,
		true
	);

	public static final MesogleaBlock PURPLE_PEARLESCENT_MESOGLEA = mesoglea(
		MapColor.COLOR_PURPLE,
		RegisterParticles.PURPLE_PEARLESCENT_HANGING_MESOGLEA,
		true
	);

	public static final MesogleaBlock YELLOW_MESOGLEA = mesoglea(
		MapColor.COLOR_YELLOW,
		RegisterParticles.YELLOW_HANGING_MESOGLEA,
		false
	);

	public static final MesogleaBlock BLUE_MESOGLEA = mesoglea(
		MapColor.COLOR_LIGHT_BLUE,
		RegisterParticles.BLUE_HANGING_MESOGLEA,
		false
	);

	public static final MesogleaBlock LIME_MESOGLEA = mesoglea(
		MapColor.COLOR_LIGHT_GREEN,
		RegisterParticles.LIME_HANGING_MESOGLEA,
		false
	);

	public static final MesogleaBlock RED_MESOGLEA = mesoglea(
		MapColor.COLOR_RED,
		RegisterParticles.RED_HANGING_MESOGLEA,
		false
	);

	public static final MesogleaBlock PINK_MESOGLEA = mesoglea(
		MapColor.COLOR_PINK,
		RegisterParticles.PINK_HANGING_MESOGLEA,
		false
	);

	public static final NematocystBlock BLUE_PEARLESCENT_NEMATOCYST = nematocyst(MapColor.QUARTZ);

	public static final NematocystBlock PURPLE_PEARLESCENT_NEMATOCYST = nematocyst(MapColor.COLOR_PURPLE);

	public static final NematocystBlock YELLOW_NEMATOCYST = nematocyst(MapColor.COLOR_YELLOW);

	public static final NematocystBlock BLUE_NEMATOCYST = nematocyst(MapColor.COLOR_BLUE);

	public static final NematocystBlock LIME_NEMATOCYST = nematocyst(MapColor.COLOR_LIGHT_GREEN);

	public static final NematocystBlock RED_NEMATOCYST = nematocyst(MapColor.COLOR_RED);

	public static final NematocystBlock PINK_NEMATOCYST = nematocyst(MapColor.COLOR_PINK);

	// MISC

	public static final TermiteMoundBlock TERMITE_MOUND = new TermiteMoundBlock(
		FabricBlockSettings.create()
			.mapColor(MapColor.COLOR_BROWN)
			.strength(0.3F)
			.sound(RegisterBlockSoundTypes.TERMITEMOUND)
			.hasPostProcess(Blocks::always)
			.randomTicks()
	);

	public static final StoneChestBlock STONE_CHEST = new StoneChestBlock(
		FabricBlockSettings.copyOf(Blocks.CHEST)
			.sounds(SoundType.DEEPSLATE)
			.strength(35.0F, 12.0F),
		() -> RegisterBlockEntities.STONE_CHEST
	);

	// PLANTS

	public static final SeedingFlowerBlock SEEDING_DANDELION = new SeedingFlowerBlock(
		MobEffects.SLOW_FALLING,
		12,
		FabricBlockSettings.copyOf(Blocks.DANDELION)
	);

	public static final FlowerPotBlock POTTED_SEEDING_DANDELION = Blocks.flowerPot(SEEDING_DANDELION);

	public static final FlowerBlock CARNATION = new FlowerBlock(
		MobEffects.REGENERATION,
		12,
		FabricBlockSettings.copyOf(Blocks.DANDELION)
	);

	public static final FlowerPotBlock POTTED_CARNATION = Blocks.flowerPot(CARNATION);

	public static final GloryOfTheSnowBlock GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(
		FabricBlockSettings.copyOf(Blocks.DANDELION)
			.ticksRandomly()
	);

	public static final FlowerLichenBlock ALBA_GLORY_OF_THE_SNOW = new FlowerLichenBlock(
		FabricBlockSettings.copyOf(Blocks.GRASS)
			.mapColor(MapColor.QUARTZ)
			.sounds(SoundType.VINE)
			.noCollision()
			.offset(BlockBehaviour.OffsetType.NONE)
	);

	public static final FlowerLichenBlock PINK_GIANT_GLORY_OF_THE_SNOW = new FlowerLichenBlock(
		FabricBlockSettings.copyOf(ALBA_GLORY_OF_THE_SNOW)
			.mapColor(MapColor.CRIMSON_STEM)
	);

	public static final FlowerLichenBlock VIOLET_BEAUTY_GLORY_OF_THE_SNOW = new FlowerLichenBlock(
		FabricBlockSettings.copyOf(ALBA_GLORY_OF_THE_SNOW)
			.mapColor(MapColor.COLOR_PURPLE)
	);

	public static final FlowerLichenBlock BLUE_GIANT_GLORY_OF_THE_SNOW = new FlowerLichenBlock(
		FabricBlockSettings.copyOf(ALBA_GLORY_OF_THE_SNOW)
			.mapColor(MapColor.COLOR_BLUE)
	);

	public static final TallFlowerBlock DATURA = new TallFlowerBlock(FabricBlockSettings.copyOf(Blocks.SUNFLOWER));

	public static final MilkweedBlock MILKWEED = new MilkweedBlock(
		FabricBlockSettings.copyOf(Blocks.SUNFLOWER)
			.ticksRandomly()
	);

	public static final Block CATTAIL = new WaterloggableTallFlowerBlock(
		FabricBlockSettings.copyOf(Blocks.ROSE_BUSH)
			.sounds(SoundType.WET_GRASS)
			.strength(0.0F)
			.nonOpaque()
	);

	public static final WaterlilyBlock FLOWERING_LILY_PAD = new WaterlilyBlock(
		FabricBlockSettings.copyOf(Blocks.LILY_PAD)
	);

	public static final AlgaeBlock ALGAE = new AlgaeBlock(
		FabricBlockSettings.copyOf(Blocks.FROGSPAWN)
			.mapColor(MapColor.PLANT)
			.sounds(RegisterBlockSoundTypes.ALGAE)
	);

	public static final WilderBushBlock BUSH = new WilderBushBlock(
		FabricBlockSettings.copyOf(Blocks.DEAD_BUSH)
			.mapColor(MapColor.PLANT)
			.nonOpaque()
			.ticksRandomly()
			.offsetType(BlockBehaviour.OffsetType.XZ)
	);

	public static final FlowerPotBlock POTTED_BUSH = Blocks.flowerPot(BUSH);

	public static final TumbleweedPlantBlock TUMBLEWEED_PLANT = new TumbleweedPlantBlock(
		FabricBlockSettings.create()
			.nonOpaque()
			.sounds(RegisterBlockSoundTypes.TUMBLEWEED_PLANT)
			.ticksRandomly()
	);

	public static final FlowerPotBlock POTTED_TUMBLEWEED_PLANT = Blocks.flowerPot(TUMBLEWEED_PLANT);

	public static final TumbleweedBlock TUMBLEWEED = new TumbleweedBlock(
		FabricBlockSettings.create()
			.breakInstantly()
			.nonOpaque()
			.sounds(RegisterBlockSoundTypes.TUMBLEWEED_PLANT)
			.ticksRandomly()
	);

	public static final FlowerPotBlock POTTED_TUMBLEWEED = Blocks.flowerPot(TUMBLEWEED);

	public static final FlowerPotBlock POTTED_BIG_DRIPLEAF = Blocks.flowerPot(Blocks.BIG_DRIPLEAF);

	public static final FlowerPotBlock POTTED_SMALL_DRIPLEAF = Blocks.flowerPot(Blocks.SMALL_DRIPLEAF);

	public static final FlowerPotBlock POTTED_GRASS = Blocks.flowerPot(Blocks.GRASS);

	public static final FlowerPotBlock POTTED_PRICKLY_PEAR = Blocks.flowerPot(PRICKLY_PEAR_CACTUS);

	public static final ShelfFungusBlock BROWN_SHELF_FUNGUS = new ShelfFungusBlock(
		FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)
			.luminance(1)
			.ticksRandomly()
			.noCollision()
			.nonOpaque()
			.sounds(RegisterBlockSoundTypes.MUSHROOM)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final ShelfFungusBlock RED_SHELF_FUNGUS = new ShelfFungusBlock(
		FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK)
			.luminance(1)
			.ticksRandomly()
			.noCollision()
			.nonOpaque()
			.sounds(RegisterBlockSoundTypes.MUSHROOM)
			.hasPostProcess(Blocks::always)
			.pushReaction(PushReaction.DESTROY)
	);

	public static final PollenBlock POLLEN = new PollenBlock(
		FabricBlockSettings.copyOf(Blocks.GRASS)
			.mapColor(MapColor.SAND)
			.sounds(RegisterBlockSoundTypes.POLLEN)
			.offset(BlockBehaviour.OffsetType.NONE)
	);

	public static final SmallSpongeBlock SMALL_SPONGE = new SmallSpongeBlock(
		FabricBlockSettings.copyOf(Blocks.SPONGE)
			.strength(0.1F)
			.noCollision()
			.nonOpaque()
			.sounds(SoundType.SPONGE)
	);

	public static final Block OSTRICH_EGG = new OstrichEggBlock(
		FabricBlockSettings.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);

	public static final Block NULL_BLOCK = new Block(
		FabricBlockSettings.copyOf(Blocks.STONE)
			.sounds(RegisterBlockSoundTypes.NULL_BLOCK)
	);

	public static final DisplayLanternBlock DISPLAY_LANTERN = new DisplayLanternBlock(
		FabricBlockSettings.create().mapColor(MapColor.METAL).forceSolidOn().strength(3.5F).sound(SoundType.LANTERN)
			.lightLevel(state -> state.getValue(RegisterProperties.DISPLAY_LIGHT))
	);

	public static final GeyserBlock GEYSER = new GeyserBlock(
		FabricBlockSettings.create().mapColor(MapColor.TERRACOTTA_BROWN)
			.sound(RegisterBlockSoundTypes.GEYSER)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.lightLevel(blockState -> 2)
			.strength(0.85F)
			.isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false)
			.hasPostProcess(Blocks::always)
			.emissiveRendering(Blocks::always)
	);

	private static final MapColor BAOBAB_PLANKS_COLOR = MapColor.COLOR_ORANGE;

	// WOOD

	public static final Block BAOBAB_PLANKS = new Block(
		FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);

	public static final StairBlock BAOBAB_STAIRS = new StairBlock(
		BAOBAB_PLANKS.defaultBlockState(),
		FabricBlockSettings.copyOf(BAOBAB_PLANKS)
	);

	public static final FenceGateBlock BAOBAB_FENCE_GATE = new FenceGateBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE)
			.mapColor(BAOBAB_PLANKS_COLOR),
		BAOBAB_WOOD_TYPE
	);

	public static final SlabBlock BAOBAB_SLAB = new SlabBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_SLAB)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);

	public static final ButtonBlock BAOBAB_BUTTON = new ButtonBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).mapColor(BAOBAB_PLANKS_COLOR),
		BAOBAB_SET,
		30, true
	);

	public static final PressurePlateBlock BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(
		PressurePlateBlock.Sensitivity.EVERYTHING,
		FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE).mapColor(BAOBAB_PLANKS_COLOR),
		BAOBAB_SET
	);

	public static final DoorBlock BAOBAB_DOOR = new DoorBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_DOOR).mapColor(BAOBAB_PLANKS_COLOR),
		BAOBAB_SET
	);

	public static final TrapDoorBlock BAOBAB_TRAPDOOR = new TrapDoorBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).mapColor(BAOBAB_PLANKS_COLOR),
		BAOBAB_SET
	);

	public static final FenceBlock BAOBAB_FENCE = new FenceBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_FENCE)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);

	public static final HollowedLogBlock STRIPPED_HOLLOWED_BAOBAB_LOG = createStrippedHollowedLogBlock(BAOBAB_PLANKS_COLOR);

	private static final MapColor BAOBAB_BARK_COLOR = MapColor.COLOR_BROWN;

	public static final Block BAOBAB_LOG = Blocks.log(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR);

	public static final FrozenSignBlock BAOBAB_SIGN = new FrozenSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor()),
		BAOBAB_WOOD_TYPE,
		WilderSharedConstants.id("blocks/baobab_sign")
	);

	public static final FrozenWallSignBlock BAOBAB_WALL_SIGN = new FrozenWallSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
			.dropsLike(BAOBAB_SIGN),
		BAOBAB_WOOD_TYPE,
		WilderSharedConstants.id("blocks/baobab_sign")
	);

	public static final BlockFamily BAOBAB = BlockFamilies.familyBuilder(BAOBAB_PLANKS)
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

	public static final FrozenCeilingHangingSignBlock BAOBAB_HANGING_SIGN = new FrozenCeilingHangingSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor()),
		BAOBAB_WOOD_TYPE,
		WilderSharedConstants.id("blocks/baobab_hanging_sign")
	);

	public static final FrozenWallHangingSignBlock BAOBAB_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
			.dropsLike(BAOBAB_HANGING_SIGN),
		BAOBAB_WOOD_TYPE,
		WilderSharedConstants.id("blocks/baobab_hanging_sign")
	);

	public static final RotatedPillarBlock STRIPPED_BAOBAB_LOG = Blocks.log(BAOBAB_PLANKS_COLOR, BAOBAB_PLANKS_COLOR);

	public static final RotatedPillarBlock STRIPPED_BAOBAB_WOOD = new RotatedPillarBlock(
		FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(BAOBAB_PLANKS_COLOR)
	);

	public static final RotatedPillarBlock BAOBAB_WOOD = new RotatedPillarBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WOOD)
			.mapColor(BAOBAB_BARK_COLOR)
	);

	private static final MapColor CYPRESS_PLANKS_COLOR = MapColor.COLOR_LIGHT_GRAY;

	public static final Block CYPRESS_PLANKS = new Block(
		FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);

	public static final StairBlock CYPRESS_STAIRS = new StairBlock(
		CYPRESS_PLANKS.defaultBlockState(),
		FabricBlockSettings.copyOf(CYPRESS_PLANKS)
	);

	public static final FenceGateBlock CYPRESS_FENCE_GATE = new FenceGateBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE)
			.mapColor(CYPRESS_PLANKS_COLOR),
		CYPRESS_WOOD_TYPE
	);

	public static final SlabBlock CYPRESS_SLAB = new SlabBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_SLAB)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);

	public static final ButtonBlock CYPRESS_BUTTON = new ButtonBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_BUTTON)
			.mapColor(CYPRESS_PLANKS_COLOR),
		CYPRESS_SET,
		30, true
	);

	public static final PressurePlateBlock CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(
		PressurePlateBlock.Sensitivity.EVERYTHING,
		FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(CYPRESS_PLANKS_COLOR),
		CYPRESS_SET
	);

	public static final DoorBlock CYPRESS_DOOR = new DoorBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_DOOR)
			.mapColor(CYPRESS_PLANKS_COLOR),
		CYPRESS_SET
	);

	public static final TrapDoorBlock CYPRESS_TRAPDOOR = new TrapDoorBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR)
			.mapColor(CYPRESS_PLANKS_COLOR),
		CYPRESS_SET
	);

	public static final FenceBlock CYPRESS_FENCE = new FenceBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_FENCE)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);

	public static final HollowedLogBlock STRIPPED_HOLLOWED_CYPRESS_LOG = createStrippedHollowedLogBlock(CYPRESS_PLANKS_COLOR);

	private static final MapColor CYPRESS_BARK_COLOR = MapColor.STONE;

	public static final RotatedPillarBlock CYPRESS_LOG = Blocks.log(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR);

	public static final FrozenSignBlock CYPRESS_SIGN = new FrozenSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor()),
		CYPRESS_WOOD_TYPE,
		WilderSharedConstants.id("blocks/cypress_sign")
	);

	public static final FrozenWallSignBlock CYPRESS_WALL_SIGN = new FrozenWallSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
			.dropsLike(CYPRESS_SIGN),
		CYPRESS_WOOD_TYPE,
		WilderSharedConstants.id("blocks/cypress_sign")
	);

	public static final BlockFamily CYPRESS = BlockFamilies.familyBuilder(CYPRESS_PLANKS)
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

	public static final FrozenCeilingHangingSignBlock CYPRESS_HANGING_SIGN = new FrozenCeilingHangingSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor()),
		CYPRESS_WOOD_TYPE,
		WilderSharedConstants.id("blocks/cypress_hanging_sign")
	);

	public static final FrozenWallHangingSignBlock CYPRESS_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
			.dropsLike(CYPRESS_HANGING_SIGN),
		CYPRESS_WOOD_TYPE,
		WilderSharedConstants.id("blocks/cypress_hanging_sign")
	);

	public static final RotatedPillarBlock STRIPPED_CYPRESS_LOG = Blocks.log(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR);

	public static final RotatedPillarBlock STRIPPED_CYPRESS_WOOD = new RotatedPillarBlock(
		FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(CYPRESS_PLANKS_COLOR)
	);

	public static final RotatedPillarBlock CYPRESS_WOOD = new RotatedPillarBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WOOD)
			.mapColor(CYPRESS_BARK_COLOR)
	);

	private static final MapColor PALM_PLANKS_COLOR = MapColor.TERRACOTTA_WHITE;

	public static final Block PALM_PLANKS = new Block(
		FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
			.mapColor(PALM_PLANKS_COLOR)
	);

	public static final StairBlock PALM_STAIRS = new StairBlock(
		PALM_PLANKS.defaultBlockState(),
		FabricBlockSettings.copyOf(PALM_PLANKS)
	);

	public static final FenceGateBlock PALM_FENCE_GATE = new FenceGateBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE)
			.mapColor(PALM_PLANKS.defaultMapColor()),
		PALM_WOOD_TYPE
	);

	public static final SlabBlock PALM_SLAB = new SlabBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_SLAB)
			.mapColor(PALM_PLANKS_COLOR)
	);

	public static final ButtonBlock PALM_BUTTON = new ButtonBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_BUTTON)
			.mapColor(PALM_PLANKS_COLOR),
		PALM_SET,
		30, true
	);

	public static final PressurePlateBlock PALM_PRESSURE_PLATE = new PressurePlateBlock(
		PressurePlateBlock.Sensitivity.EVERYTHING,
		FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE)
			.mapColor(PALM_PLANKS_COLOR),
		PALM_SET
	);

	public static final DoorBlock PALM_DOOR = new DoorBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_DOOR)
			.mapColor(PALM_PLANKS_COLOR),
		PALM_SET
	);

	public static final TrapDoorBlock PALM_TRAPDOOR = new TrapDoorBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR)
			.mapColor(PALM_PLANKS_COLOR),
		PALM_SET
	);

	public static final FenceBlock PALM_FENCE = new FenceBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_FENCE)
			.mapColor(PALM_PLANKS_COLOR)
	);

	public static final HollowedLogBlock STRIPPED_HOLLOWED_PALM_LOG = createStrippedHollowedLogBlock(PALM_PLANKS_COLOR);

	private static final MapColor PALM_BARK_COLOR = MapColor.COLOR_LIGHT_GRAY;

	public static final RotatedPillarBlock PALM_LOG = Blocks.log(PALM_PLANKS_COLOR, PALM_BARK_COLOR);

	public static final FrozenSignBlock PALM_SIGN = new FrozenSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_SIGN)
			.mapColor(PALM_LOG.defaultMapColor()),
		PALM_WOOD_TYPE,
		WilderSharedConstants.id("blocks/palm_sign")
	);

	public static final FrozenWallSignBlock PALM_WALL_SIGN = new FrozenWallSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
			.dropsLike(PALM_SIGN),
		PALM_WOOD_TYPE,
		WilderSharedConstants.id("blocks/palm_sign")
	);

	public static final BlockFamily PALM = BlockFamilies.familyBuilder(PALM_PLANKS)
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

	public static final FrozenCeilingHangingSignBlock PALM_HANGING_SIGN = new FrozenCeilingHangingSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN)
			.mapColor(PALM_LOG.defaultMapColor()),
		PALM_WOOD_TYPE,
		WilderSharedConstants.id("blocks/palm_hanging_sign")
	);

	public static final FrozenWallHangingSignBlock PALM_WALL_HANGING_SIGN = new FrozenWallHangingSignBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
			.dropsLike(PALM_HANGING_SIGN),
		PALM_WOOD_TYPE,
		WilderSharedConstants.id("blocks/palm_hanging_sign")
	);

	public static final RotatedPillarBlock PALM_CROWN = new RotatedPillarBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_LOG)
			.mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? PALM_PLANKS_COLOR : PALM_BARK_COLOR)
			.sounds(RegisterBlockSoundTypes.PALM_CROWN)
	);

	public static final RotatedPillarBlock STRIPPED_PALM_LOG = Blocks.log(PALM_PLANKS_COLOR, PALM_BARK_COLOR);

	public static final RotatedPillarBlock STRIPPED_PALM_WOOD = new RotatedPillarBlock(
		FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD)
			.mapColor(PALM_PLANKS_COLOR)
	);

	public static final RotatedPillarBlock PALM_WOOD = new RotatedPillarBlock(
		FabricBlockSettings.copyOf(Blocks.OAK_WOOD)
			.mapColor(PALM_BARK_COLOR)
	);

	public static final HollowedLogBlock HOLLOWED_PALM_LOG = createHollowedLogBlock(PALM_PLANKS_COLOR, PALM_BARK_COLOR);

	private RegisterBlocks() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

	public static void registerOtherBB() {
		registerBlockAfter(Items.MUD_BRICKS, "chiseled_mud_bricks", CHISELED_MUD_BRICKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlock("scorched_sand", SCORCHED_SAND);
		registerBlock("scorched_red_sand", SCORCHED_RED_SAND);
	}

	public static void registerWoods() {
		String baobab = "baobab";
		String cypress = "cypress";
		String palm = "palm";
		String wood = baobab;
		//BAOBAB IN BUILDING BLOCKS
		registerBlockAfter(Items.MANGROVE_BUTTON, wood + "_log", BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_LOG, wood + "_wood", BAOBAB_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_WOOD, "stripped_" + wood + "_log", STRIPPED_BAOBAB_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_BAOBAB_LOG, "stripped_" + wood + "_wood", STRIPPED_BAOBAB_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_BAOBAB_WOOD, wood + "_planks", BAOBAB_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_PLANKS, wood + "_stairs", BAOBAB_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_STAIRS, wood + "_slab", BAOBAB_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_SLAB, wood + "_fence", BAOBAB_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_FENCE, wood + "_fence_gate", BAOBAB_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_FENCE_GATE, wood + "_door", BAOBAB_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_DOOR, wood + "_trapdoor", BAOBAB_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_TRAPDOOR, wood + "_pressure_plate", BAOBAB_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(BAOBAB_PRESSURE_PLATE, wood + "_button", BAOBAB_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//BAOBAB IN NATURE
		registerBlockAfter(Items.MANGROVE_LOG, wood + "_log", BAOBAB_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.FLOWERING_AZALEA_LEAVES, wood + "_leaves", BAOBAB_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);

		wood = cypress;
		//CYPRESS IN BUILDING BLOCKS
		registerBlockAfter(BAOBAB_BUTTON, wood + "_log", CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_LOG, wood + "_wood", CYPRESS_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_WOOD, "stripped_" + wood + "_log", STRIPPED_CYPRESS_LOG, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_CYPRESS_LOG, "stripped_" + wood + "_wood", STRIPPED_CYPRESS_WOOD, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(STRIPPED_CYPRESS_WOOD, wood + "_planks", CYPRESS_PLANKS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_PLANKS, wood + "_stairs", CYPRESS_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_STAIRS, wood + "_slab", CYPRESS_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_SLAB, wood + "_fence", CYPRESS_FENCE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_FENCE, wood + "_fence_gate", CYPRESS_FENCE_GATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_FENCE_GATE, wood + "_door", CYPRESS_DOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_DOOR, wood + "_trapdoor", CYPRESS_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_TRAPDOOR, wood + "_pressure_plate", CYPRESS_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(CYPRESS_PRESSURE_PLATE, wood + "_button", CYPRESS_BUTTON, CreativeModeTabs.BUILDING_BLOCKS);
		//CYPRESS IN NATURE
		registerBlockAfter(BAOBAB_LOG, wood + "_log", CYPRESS_LOG, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(BAOBAB_LEAVES, wood + "_leaves", CYPRESS_LEAVES, CreativeModeTabs.NATURAL_BLOCKS);

		wood = palm;
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

		registerBlock(baobab + "_nut", BAOBAB_NUT);
		registerBlock("potted_" + baobab + "_nut", POTTED_BAOBAB_NUT);

		registerBlockAfter(Items.MANGROVE_PROPAGULE, cypress + "_sapling", CYPRESS_SAPLING, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_" + cypress + "_sapling", POTTED_CYPRESS_SAPLING);
		registerBlock("coconut", COCONUT);
		registerBlock("potted_coconut", POTTED_COCONUT);

		registerBlock(baobab + "_sign", BAOBAB_SIGN);
		registerBlock(baobab + "_wall_sign", BAOBAB_WALL_SIGN);
		registerBlock(baobab + "_hanging_sign", BAOBAB_HANGING_SIGN);
		registerBlock(baobab + "_wall_hanging_sign", BAOBAB_WALL_HANGING_SIGN);
		registerBlock(cypress + "_sign", CYPRESS_SIGN);
		registerBlock(cypress + "_wall_sign", CYPRESS_WALL_SIGN);
		registerBlock(cypress + "_hanging_sign", CYPRESS_HANGING_SIGN);
		registerBlock(cypress + "_wall_hanging_sign", CYPRESS_WALL_HANGING_SIGN);
		registerBlock(palm + "_sign", PALM_SIGN);
		registerBlock(palm + "_wall_sign", PALM_WALL_SIGN);
		registerBlock(palm + "_hanging_sign", PALM_HANGING_SIGN);
		registerBlock(palm + "_wall_hanging_sign", PALM_WALL_HANGING_SIGN);
	}

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

	public static void registerDeepDark() {
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL, "sculk_wall", SCULK_WALL, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL, "sculk_slab", SCULK_SLAB, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL, "sculk_stairs", SCULK_STAIRS, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.DEEPSLATE_TILE_WALL, "osseous_sculk", OSSEOUS_SCULK, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.SCULK, "osseous_sculk", OSSEOUS_SCULK, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.SCULK_SENSOR, "hanging_tendril", HANGING_TENDRIL, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("echo_glass", ECHO_GLASS);
	}

	public static void registerPlants() {
		registerBlock("potted_big_dripleaf", POTTED_BIG_DRIPLEAF);
		registerBlock("potted_small_dripleaf", POTTED_SMALL_DRIPLEAF);
		registerBlock("potted_grass", POTTED_GRASS);
		registerBlockAfter(Items.DANDELION, "seeding_dandelion", SEEDING_DANDELION, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_seeding_dandelion", POTTED_SEEDING_DANDELION);
		registerBlockAfter(Items.CORNFLOWER, "carnation", CARNATION, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_carnation", POTTED_CARNATION);
		registerBlockBefore(Items.WITHER_ROSE, "glory_of_the_snow", GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE, "blue_giant_glory_of_the_snow", BLUE_GIANT_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE, "pink_giant_glory_of_the_snow", PINK_GIANT_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE, "violet_beauty_glory_of_the_snow", VIOLET_BEAUTY_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.WITHER_ROSE, "alba_glory_of_the_snow", ALBA_GLORY_OF_THE_SNOW, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.PEONY, "datura", DATURA, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(DATURA, "milkweed", MILKWEED, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(MILKWEED, "cattail", CATTAIL, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.CACTUS, "tumbleweed_plant", TUMBLEWEED_PLANT, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_tumbleweed_plant", POTTED_TUMBLEWEED_PLANT);
		registerBlockAfter(TUMBLEWEED_PLANT, "tumbleweed", TUMBLEWEED, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_tumbleweed", POTTED_TUMBLEWEED);
		registerBlockAfter(TUMBLEWEED, "bush", BUSH, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlock("potted_bush", POTTED_BUSH);
		registerBlock("prickly_pear", PRICKLY_PEAR_CACTUS);
		registerBlock("potted_prickly_pear", POTTED_PRICKLY_PEAR);
	}

	public static void registerNotSoPlants() {
		registerBlock("pollen", POLLEN);
		registerBlockAfter(Items.RED_MUSHROOM, "red_shelf_fungus", RED_SHELF_FUNGUS, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.RED_MUSHROOM, "brown_shelf_fungus", BROWN_SHELF_FUNGUS, CreativeModeTabs.NATURAL_BLOCKS);
		Registry.register(BuiltInRegistries.BLOCK, WilderSharedConstants.id("algae"), ALGAE);
		Registry.register(BuiltInRegistries.BLOCK, WilderSharedConstants.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
		registerBlockAfter(Items.WET_SPONGE, "small_sponge", SMALL_SPONGE, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockAfter(Items.SNIFFER_EGG, "ostrich_egg", OSTRICH_EGG, CreativeModeTabs.NATURAL_BLOCKS);
	}

	public static void registerMisc() {
		registerBlockBefore(Items.BEE_NEST, "termite_mound", TERMITE_MOUND, CreativeModeTabs.NATURAL_BLOCKS);
		registerBlockBefore(Items.GLASS, "null_block", NULL_BLOCK, CreativeModeTabs.BUILDING_BLOCKS);
		registerBlockAfter(Items.CHEST, "stone_chest", STONE_CHEST, CreativeModeTabs.FUNCTIONAL_BLOCKS);
		registerBlock("display_lantern", DISPLAY_LANTERN);

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

		registerBlockAfter(Items.MAGMA_BLOCK, "geyser", GEYSER, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	public static void registerBlocks() {
		WilderSharedConstants.logWithModId("Registering Blocks for", WilderSharedConstants.UNSTABLE_LOGGING);

		registerOtherBB();
		registerWoods();
		registerHollowedLogs();
		registerDeepDark();
		registerPlants();
		registerNotSoPlants();
		registerMisc();
	}

	private static void registerBlock(String path, Block block) {
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockBefore(ItemLike comparedItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemBefore(comparedItem, path, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockAfter(ItemLike comparedItem, String path, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemAfter(comparedItem, path, block, tabs);
		actualRegisterBlock(path, block);
	}

	@SafeVarargs
	private static void registerBlockItemBefore(ItemLike comparedItem, String path, Block block, CreativeModeTab.TabVisibility tabVisibility, ResourceKey<CreativeModeTab>... tabs) {
		actualRegisterBlockItem(path, block);
		FrozenCreativeTabs.addBefore(comparedItem, block, tabVisibility, tabs);
	}

	@SafeVarargs
	private static void registerBlockItemAfter(ItemLike comparedItem, String name, Block block, ResourceKey<CreativeModeTab>... tabs) {
		registerBlockItemAfter(comparedItem, name, block, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}

	@SafeVarargs
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

	@NotNull
	public static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor, SoundType soundType) {
		var settings = FabricBlockSettings.create()
			.mapColor(state -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sounds(soundType)
			.burnable();

		return new HollowedLogBlock(settings);
	}

	@NotNull
	public static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor) {
		return createHollowedLogBlock(topMapColor, sideMapColor, RegisterBlockSoundTypes.HOLLOWED_LOG);
	}

	@NotNull
	public static HollowedLogBlock createHollowedStemBlock(MapColor mapColor) {
		return new HollowedLogBlock(FabricBlockSettings.create()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sounds(RegisterBlockSoundTypes.HOLLOWED_STEM)
		);
	}

	@NotNull
	public static HollowedLogBlock createStrippedHollowedLogBlock(MapColor mapColor, SoundType soundType) {
		var settings = FabricBlockSettings.create()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sounds(soundType)
			.burnable();

		return new HollowedLogBlock(settings);
	}

	@NotNull
	public static HollowedLogBlock createStrippedHollowedLogBlock(MapColor mapColor) {
		return createStrippedHollowedLogBlock(mapColor, RegisterBlockSoundTypes.HOLLOWED_LOG);
	}

	@NotNull
	public static HollowedLogBlock createStrippedHollowedStemBlock(MapColor mapColor) {
		return new HollowedLogBlock(FabricBlockSettings.create()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sounds(RegisterBlockSoundTypes.HOLLOWED_STEM)
		);
	}

	@NotNull
	public static MesogleaBlock mesoglea(@NotNull MapColor mapColor, @NotNull ParticleOptions particleType, boolean pearlescent) {
		MesogleaBlock mesogleaBlock = new MesogleaBlock(
			pearlescent,
			FabricBlockSettings.create()
				.mapColor(mapColor)
				.nonOpaque()
				.strength(0.2F)
				.slipperiness(0.8F)
				.emissiveLighting(Blocks::always)
				.luminance(state -> 7)
				.sounds(RegisterBlockSoundTypes.MESOGLEA)
				.suffocates(Blocks::never)
				.blockVision(Blocks::never)
				.dynamicBounds()
		);
		MesogleaBlock.MesogleaParticleRegistry.registerDripParticle(mesogleaBlock, particleType);
		return mesogleaBlock;
	}

	@NotNull
	public static NematocystBlock nematocyst(@NotNull MapColor mapColor) {
		return new NematocystBlock(
			FabricBlockSettings.create()
				.mapColor(mapColor)
				.noCollision()
				.nonOpaque()
				.emissiveLighting(Blocks::always)
				.luminance(state -> 4)
				.sounds(RegisterBlockSoundTypes.NEMATOCYST)
				.pistonBehavior(PushReaction.DESTROY)
		);
	}

	public static void registerBlockProperties() {
		registerDispenses();
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
		registerInventories();
	}

	private static void registerDispenses() {
		DispenserBlock.registerBehavior(RegisterItems.COCONUT, new AbstractProjectileDispenseBehavior() {
			@Override
			@NotNull
			protected Projectile getProjectile(@NotNull Level level, @NotNull Position position, @NotNull ItemStack stack) {
				return new CoconutProjectile(level, position.x(), position.y(), position.z());
			}

			@Override
			protected float getUncertainty() {
				return 9F;
			}

			@Override
			protected float getPower() {
				return 0.75F;
			}
		});
		DispenserBlock.registerBehavior(RegisterBlocks.TUMBLEWEED, new DefaultDispenseItemBehavior() {
			@Override
			@NotNull
			public ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
				Level level = source.level();
				Direction direction = source.state().getValue(DispenserBlock.FACING);
				Vec3 position = source.center().add(direction.getStepX(), direction.getStepY(), direction.getStepZ());
				Tumbleweed tumbleweed = new Tumbleweed(RegisterEntities.TUMBLEWEED, level);
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
		CompostingChanceRegistry.INSTANCE.add(BLUE_GIANT_GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(ALBA_GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(PINK_GIANT_GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(VIOLET_BEAUTY_GLORY_OF_THE_SNOW, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(ALGAE, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(BUSH, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED_PLANT, 0.5F);
		CompostingChanceRegistry.INSTANCE.add(TUMBLEWEED, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(RegisterItems.COCONUT, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(RegisterItems.SPLIT_COCONUT, 0.3F);
	}

	private static void registerFlammability() {
		WilderSharedConstants.logWithModId("Registering Flammability for", WilderSharedConstants.UNSTABLE_LOGGING);
		var flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableBlockRegistry.add(RegisterBlocks.POLLEN, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.SEEDING_DANDELION, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.CARNATION, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.CATTAIL, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.DATURA, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.MILKWEED, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.PINK_GIANT_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW, 100, 60);
		flammableBlockRegistry.add(RegisterBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW, 100, 60);
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
		flammableBlockRegistry.add(RegisterBlocks.BAOBAB_SIGN, 5, 20);
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
		flammableBlockRegistry.add(RegisterBlocks.CYPRESS_SIGN, 5, 20);
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
		flammableBlockRegistry.add(RegisterBlocks.PALM_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(RegisterBlocks.PALM_WALL_HANGING_SIGN, 5, 20);
	}

	private static void registerFuels() {
		WilderSharedConstants.logWithModId("Registering Fuels for", WilderSharedConstants.UNSTABLE_LOGGING);
		FuelRegistry registry = FuelRegistry.INSTANCE;

		registry.add(RegisterItems.BAOBAB_BOAT, 1200);
		registry.add(RegisterItems.BAOBAB_CHEST_BOAT, 1200);
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

		registry.add(RegisterItems.CYPRESS_BOAT, 1200);
		registry.add(RegisterItems.CYPRESS_CHEST_BOAT, 1200);
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

		registry.add(RegisterItems.PALM_BOAT, 1200);
		registry.add(RegisterItems.PALM_CHEST_BOAT, 1200);
		registry.add(PALM_LOG.asItem(), 300);
		registry.add(PALM_CROWN.asItem(), 300);
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
		AxeBehaviors.register(Blocks.OAK_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_OAK_LOG, false));
		AxeBehaviors.register(Blocks.BIRCH_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_BIRCH_LOG, false));
		AxeBehaviors.register(Blocks.CHERRY_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_CHERRY_LOG, false));
		AxeBehaviors.register(Blocks.SPRUCE_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_SPRUCE_LOG, false));
		AxeBehaviors.register(Blocks.DARK_OAK_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_DARK_OAK_LOG, false));
		AxeBehaviors.register(Blocks.JUNGLE_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_JUNGLE_LOG, false));
		AxeBehaviors.register(Blocks.ACACIA_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_ACACIA_LOG, false));
		AxeBehaviors.register(Blocks.MANGROVE_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_MANGROVE_LOG, false));
		AxeBehaviors.register(Blocks.CRIMSON_STEM, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_CRIMSON_STEM, true));
		AxeBehaviors.register(Blocks.WARPED_STEM, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_WARPED_STEM, true));
		AxeBehaviors.register(RegisterBlocks.BAOBAB_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_BAOBAB_LOG, false));
		AxeBehaviors.register(RegisterBlocks.CYPRESS_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_CYPRESS_LOG, false));
		AxeBehaviors.register(RegisterBlocks.PALM_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.HOLLOWED_PALM_LOG, false));
		//STRIPPED
		AxeBehaviors.register(Blocks.STRIPPED_OAK_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_BIRCH_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_CHERRY_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_SPRUCE_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_DARK_OAK_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_JUNGLE_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_ACACIA_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_MANGROVE_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, false));
		AxeBehaviors.register(Blocks.STRIPPED_CRIMSON_STEM, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, true));
		AxeBehaviors.register(Blocks.STRIPPED_WARPED_STEM, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM, true));
		AxeBehaviors.register(RegisterBlocks.STRIPPED_BAOBAB_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, false));
		AxeBehaviors.register(RegisterBlocks.STRIPPED_CYPRESS_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, false));
		AxeBehaviors.register(RegisterBlocks.STRIPPED_PALM_LOG, (context, level, pos, state, face, horizontal) ->
			HollowedLogBlock.hollow(level, pos, state, face, RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG, false));
	}

	private static void registerInventories() {
		ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(), STONE_CHEST);
	}
}
