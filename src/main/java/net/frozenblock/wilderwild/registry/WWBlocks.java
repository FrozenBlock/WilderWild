/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.registry;

import java.util.Optional;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.storage.api.NoInteractionStorage;
import net.frozenblock.lib.item.api.bone_meal.BoneMealApi;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.frozenblock.wilderwild.block.AuburnCreepingMossBlock;
import net.frozenblock.wilderwild.block.AuburnMossBlock;
import net.frozenblock.wilderwild.block.AuburnMossCarpetBlock;
import net.frozenblock.wilderwild.block.BaobabLeavesBlock;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.block.BarnaclesBlock;
import net.frozenblock.wilderwild.block.CattailBlock;
import net.frozenblock.wilderwild.block.CoconutBlock;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.FloweringWaterlilyBlock;
import net.frozenblock.wilderwild.block.FragileIceBlock;
import net.frozenblock.wilderwild.block.FroglightGoopBlock;
import net.frozenblock.wilderwild.block.FroglightGoopBodyBlock;
import net.frozenblock.wilderwild.block.FrozenBushBlock;
import net.frozenblock.wilderwild.block.FrozenDoublePlantBlock;
import net.frozenblock.wilderwild.block.FrozenTallGrassBlock;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.block.HugePaleMushroomBlock;
import net.frozenblock.wilderwild.block.IcicleBlock;
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
import net.frozenblock.wilderwild.block.PlanktonBlock;
import net.frozenblock.wilderwild.block.PollenBlock;
import net.frozenblock.wilderwild.block.PricklyPearCactusBlock;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.SculkSlabBlock;
import net.frozenblock.wilderwild.block.SculkStairBlock;
import net.frozenblock.wilderwild.block.SculkWallBlock;
import net.frozenblock.wilderwild.block.SeaAnemoneBlock;
import net.frozenblock.wilderwild.block.SeaWhipBlock;
import net.frozenblock.wilderwild.block.SeedingFlowerBlock;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.block.ShrubBlock;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMoundBlock;
import net.frozenblock.wilderwild.block.TubeWormsBlock;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WideFlowerBlock;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.block.state.properties.FroglightType;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.references.WWBlockIds;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.worldgen.features.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.worldgen.impl.sapling.WWTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.ShelfBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
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

public final class WWBlocks {
	public static final BlockSetType BAOBAB_SET = BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA).register(WWConstants.id("baobab"));
	public static final BlockSetType WILLOW_SET = BlockSetTypeBuilder.copyOf(BlockSetType.SPRUCE).register(WWConstants.id("willow"));
	public static final BlockSetType CYPRESS_SET = BlockSetTypeBuilder.copyOf(BlockSetType.BIRCH).register(WWConstants.id("cypress"));
	public static final BlockSetType PALM_SET = BlockSetTypeBuilder.copyOf(BlockSetType.JUNGLE).register(WWConstants.id("palm"));
	public static final BlockSetType MAPLE_SET = BlockSetTypeBuilder.copyOf(BlockSetType.SPRUCE)
		.soundType(WWSoundTypes.MAPLE_WOOD)
		.doorCloseSound(WWSounds.BLOCK_MAPLE_WOOD_DOOR_CLOSE).doorOpenSound(WWSounds.BLOCK_MAPLE_WOOD_DOOR_OPEN)
		.trapdoorCloseSound(WWSounds.BLOCK_MAPLE_WOOD_TRAPDOOR_CLOSE).trapdoorOpenSound(WWSounds.BLOCK_MAPLE_WOOD_TRAPDOOR_OPEN)
		.pressurePlateClickOnSound(WWSounds.BLOCK_MAPLE_WOOD_PRESSURE_PLATE_CLICK_ON).pressurePlateClickOffSound(WWSounds.BLOCK_MAPLE_WOOD_PRESSURE_PLATE_CLICK_OFF)
		.buttonClickOnSound(WWSounds.BLOCK_MAPLE_BUTTON_CLICK_ON).buttonClickOffSound(WWSounds.BLOCK_MAPLE_BUTTON_CLICK_OFF)
		.register(WWConstants.id("maple"));
	public static final WoodType BAOBAB_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.ACACIA).register(WWConstants.id("baobab"), BAOBAB_SET);
	public static final WoodType WILLOW_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.SPRUCE).register(WWConstants.id("willow"), WILLOW_SET);
	public static final WoodType CYPRESS_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.BIRCH).register(WWConstants.id("cypress"), CYPRESS_SET);
	public static final WoodType PALM_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.JUNGLE).register(WWConstants.id("palm"), PALM_SET);
	public static final WoodType MAPLE_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.SPRUCE)
		.soundType(WWSoundTypes.MAPLE_WOOD)
		.fenceGateCloseSound(WWSounds.BLOCK_MAPLE_WOOD_FENCE_GATE_CLOSE).fenceGateOpenSound(WWSounds.BLOCK_MAPLE_WOOD_FENCE_GATE_OPEN)
		.hangingSignSoundType(WWSoundTypes.MAPLE_WOOD_HANGING_SIGN)
		.register(WWConstants.id("maple"), MAPLE_SET);
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

	// MUD
	public static final Block CHISELED_MUD_BRICKS = Blocks.register(WWBlockItemIds.CHISELED_MUD_BRICKS.block(), Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final Block CRACKED_MUD_BRICKS = Blocks.register(WWBlockItemIds.CRACKED_MUD_BRICKS.block(), Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final Block MOSSY_MUD_BRICKS = Blocks.register(WWBlockItemIds.MOSSY_MUD_BRICKS.block(), Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final Block MOSSY_MUD_BRICK_STAIRS = Blocks.registerStair(WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS, MOSSY_MUD_BRICKS);
	public static final Block MOSSY_MUD_BRICK_SLAB = Blocks.registerSlab(WWBlockItemIds.MOSSY_MUD_BRICK_SLAB, MOSSY_MUD_BRICKS);
	public static final Block MOSSY_MUD_BRICK_WALL = Blocks.registerWall(WWBlockItemIds.MOSSY_MUD_BRICK_WALL, MOSSY_MUD_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_MUD_BRICK = BlockFamilies.familyBuilder(MOSSY_MUD_BRICKS)
		.stairs(MOSSY_MUD_BRICK_STAIRS)
		.slab(MOSSY_MUD_BRICK_SLAB)
		.wall(MOSSY_MUD_BRICK_WALL)
		.getFamily();

	// SAND
	public static final Block SCORCHED_SAND = registerScorchedSand(WWBlockItemIds.SCORCHED_SAND, Blocks.SAND);
	public static final Block SCORCHED_RED_SAND = registerScorchedSand(WWBlockItemIds.SCORCHED_RED_SAND, Blocks.RED_SAND);

	public static Block registerScorchedSand(BlockItemId id, Block base) {
		return Blocks.register(
			id.block(),
			properties -> new ScorchedBlock(
				base.defaultBlockState(),
				true,
				SoundEvents.BRUSH_SAND,
				SoundEvents.BRUSH_SAND_COMPLETED,
				true,
				properties
			),
			Properties.of()
				.strength(1.5F)
				.sound(WWSoundTypes.SCORCHED_SAND)
				.mapColor(base.defaultMapColor())
				.randomTicks()
		);
	}

	// SAPLINGS
	public static final Block BAOBAB_NUT = Blocks.register(WWBlockItemIds.BAOBAB_NUT,
		properties -> new BaobabNutBlock(WWTreeGrowers.BAOBAB, properties),
		Properties.ofFullCopy(Blocks.BAMBOO).sound(WWSoundTypes.BAOBAB_NUT)
	);
	public static final Block POTTED_BAOBAB_NUT = registerFlowerPot(WWBlockIds.POTTED_BAOBAB_NUT, BAOBAB_NUT);

	public static final Block WILLOW_SAPLING = Blocks.register(WWBlockItemIds.WILLOW_SAPLING,
		properties -> new WaterloggableSaplingBlock(WWTreeGrowers.WILLOW, properties),
		Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_WILLOW_SAPLING = registerFlowerPot(WWBlockIds.POTTED_WILLOW_SAPLING, WILLOW_SAPLING);

	public static final Block CYPRESS_SAPLING = Blocks.register(WWBlockItemIds.CYPRESS_SAPLING,
		properties -> new WaterloggableSaplingBlock(WWTreeGrowers.CYPRESS, properties),
		Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_CYPRESS_SAPLING = registerFlowerPot(WWBlockIds.POTTED_CYPRESS_SAPLING, CYPRESS_SAPLING);

	public static final Block COCONUT = Blocks.register(WWBlockItemIds.COCONUT,
		properties -> new CoconutBlock(WWTreeGrowers.PALM, properties),
		Properties.of().instabreak().randomTicks().sound(SoundType.STONE)
	);
	public static final Block POTTED_COCONUT = registerFlowerPot(WWBlockIds.POTTED_COCONUT, COCONUT);

	public static final Block YELLOW_MAPLE_SAPLING = Blocks.register(WWBlockItemIds.YELLOW_MAPLE_SAPLING,
		properties -> new SaplingBlock(WWTreeGrowers.YELLOW_MAPLE, properties),
		Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_YELLOW_MAPLE_SAPLING = registerFlowerPot(WWBlockIds.POTTED_YELLOW_MAPLE_SAPLING, YELLOW_MAPLE_SAPLING);

	public static final Block ORANGE_MAPLE_SAPLING = Blocks.register(WWBlockItemIds.ORANGE_MAPLE_SAPLING,
		properties -> new SaplingBlock(WWTreeGrowers.ORANGE_MAPLE, properties),
		Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_ORANGE_MAPLE_SAPLING = registerFlowerPot(WWBlockIds.POTTED_ORANGE_MAPLE_SAPLING, ORANGE_MAPLE_SAPLING);

	public static final Block RED_MAPLE_SAPLING = Blocks.register(WWBlockItemIds.RED_MAPLE_SAPLING,
		properties -> new SaplingBlock(WWTreeGrowers.RED_MAPLE, properties),
		Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final Block POTTED_RED_MAPLE_SAPLING = registerFlowerPot(WWBlockIds.POTTED_RED_MAPLE_SAPLING, RED_MAPLE_SAPLING);

	// LEAVES
	public static final Block BAOBAB_LEAVES = Blocks.register(WWBlockItemIds.BAOBAB_LEAVES,
		properties -> new BaobabLeavesBlock(0.01F, properties),
		Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final Block WILLOW_LEAVES = Blocks.register(WWBlockItemIds.WILLOW_LEAVES,
		properties -> new TintedParticleLeavesBlock(0.01F, properties),
		Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final Block CYPRESS_LEAVES = Blocks.register(WWBlockItemIds.CYPRESS_LEAVES,
		properties -> new TintedParticleLeavesBlock(0.01F, properties),
		Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final Block PALM_FRONDS = Blocks.register(WWBlockItemIds.PALM_FRONDS,
		properties -> new PalmFrondsBlock(0.005F, properties),
		Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final Block YELLOW_MAPLE_LEAVES = Blocks.register(WWBlockItemIds.YELLOW_MAPLE_LEAVES,
		LeavesWithLitterBlock::new,
		Blocks.leavesProperties(WWSoundTypes.MAPLE_LEAVES).mapColor(MapColor.COLOR_YELLOW)
	);
	public static final Block ORANGE_MAPLE_LEAVES = Blocks.register(WWBlockItemIds.ORANGE_MAPLE_LEAVES,
		LeavesWithLitterBlock::new,
		Blocks.leavesProperties(WWSoundTypes.MAPLE_LEAVES).mapColor(MapColor.COLOR_ORANGE)
	);
	public static final Block RED_MAPLE_LEAVES = Blocks.register(WWBlockItemIds.RED_MAPLE_LEAVES,
		LeavesWithLitterBlock::new,
		Blocks.leavesProperties(WWSoundTypes.MAPLE_LEAVES).mapColor(MapColor.COLOR_RED)
	);

	// HOLLOWED LOGS
	public static final Block HOLLOWED_OAK_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_OAK_LOG,
		hollowedLogProperties(MapColor.WOOD, MapColor.PODZOL)
	);
	public static final Block HOLLOWED_SPRUCE_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_SPRUCE_LOG,
		hollowedLogProperties(MapColor.PODZOL, MapColor.COLOR_BROWN)
	);
	public static final Block HOLLOWED_BIRCH_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_BIRCH_LOG,
		hollowedLogProperties(MapColor.SAND, MapColor.QUARTZ)
	);
	public static final Block HOLLOWED_JUNGLE_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_JUNGLE_LOG,
		hollowedLogProperties(MapColor.DIRT, MapColor.PODZOL)
	);
	public static final Block HOLLOWED_ACACIA_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_ACACIA_LOG,
		hollowedLogProperties(MapColor.COLOR_ORANGE, MapColor.STONE)
	);
	public static final Block HOLLOWED_DARK_OAK_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_DARK_OAK_LOG,
		hollowedLogProperties(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN)
	);
	public static final Block HOLLOWED_MANGROVE_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_MANGROVE_LOG,
		hollowedLogProperties(MapColor.COLOR_RED, MapColor.PODZOL)
	);
	public static final Block HOLLOWED_CHERRY_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_CHERRY_LOG,
		hollowedLogProperties(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, WWSoundTypes.HOLLOWED_CHERRY_LOG)
	);
	public static final Block HOLLOWED_PALE_OAK_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_PALE_OAK_LOG,
		hollowedLogProperties(MapColor.QUARTZ, MapColor.STONE)
	);
	public static final Block HOLLOWED_CRIMSON_STEM = registerHollowedLog(WWBlockItemIds.HOLLOWED_CRIMSON_STEM,
		hollowedStemProperties(MapColor.CRIMSON_STEM)
	);
	public static final Block HOLLOWED_WARPED_STEM = registerHollowedLog(WWBlockItemIds.HOLLOWED_WARPED_STEM,
		hollowedStemProperties(MapColor.WARPED_STEM)
	);
	public static final Block HOLLOWED_BAOBAB_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_BAOBAB_LOG,
		hollowedLogProperties(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR)
	);
	public static final Block HOLLOWED_WILLOW_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_WILLOW_LOG,
		hollowedLogProperties(WILLOW_PLANKS_COLOR, WILLOW_BARK_COLOR)
	);
	public static final Block HOLLOWED_CYPRESS_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_CYPRESS_LOG,
		hollowedLogProperties(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR)
	);
	public static final Block HOLLOWED_PALM_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_PALM_LOG,
		hollowedLogProperties(PALM_PLANKS_COLOR, PALM_BARK_COLOR)
	);
	public static final Block HOLLOWED_MAPLE_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_MAPLE_LOG,
		hollowedLogProperties(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR, WWSoundTypes.HOLLOWED_MAPLE_LOG)
	);

	// STRIPPED HOLLOWED LOGS
	public static final Block STRIPPED_HOLLOWED_OAK_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_OAK_LOG.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_SPRUCE_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_SPRUCE_LOG.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_BIRCH_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_BIRCH_LOG.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_JUNGLE_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_JUNGLE_LOG.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_ACACIA_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_ACACIA_LOG.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_DARK_OAK_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_DARK_OAK_LOG.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_MANGROVE_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_MANGROVE_LOG.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_CHERRY_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_CHERRY_LOG.defaultMapColor(), WWSoundTypes.HOLLOWED_CHERRY_LOG)
	);
	public static final Block STRIPPED_HOLLOWED_PALE_OAK_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG,
		strippedHollowedLogProperties(Blocks.STRIPPED_PALE_OAK_LOG.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_CRIMSON_STEM = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM,
		strippedHollowedStemProperties(Blocks.STRIPPED_CRIMSON_STEM.defaultMapColor())
	);
	public static final Block STRIPPED_HOLLOWED_WARPED_STEM = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM,
		strippedHollowedStemProperties(Blocks.STRIPPED_WARPED_STEM.defaultMapColor())
	);

	public static Block registerHollowedLog(BlockItemId id, Properties properties) {
		return Blocks.register(id, HollowedLogBlock::new, properties);
	}

	public static Properties hollowedLogProperties(MapColor topMapColor, MapColor sideMapColor, SoundType soundType) {
		return Properties.of()
			.mapColor(state -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(soundType)
			.ignitedByLava();
	}

	public static Properties hollowedLogProperties(MapColor topMapColor, MapColor sideMapColor) {
		return hollowedLogProperties(topMapColor, sideMapColor, WWSoundTypes.HOLLOWED_LOG);
	}

	public static Properties hollowedStemProperties(MapColor mapColor) {
		return Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(WWSoundTypes.HOLLOWED_STEM);
	}

	public static Properties strippedHollowedLogProperties(MapColor mapColor, SoundType soundType) {
		return Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(soundType)
			.ignitedByLava();
	}

	public static Properties strippedHollowedLogProperties(MapColor mapColor) {
		return strippedHollowedLogProperties(mapColor, WWSoundTypes.HOLLOWED_LOG);
	}

	public static Properties strippedHollowedStemProperties(MapColor mapColor) {
		return Properties.of()
			.mapColor(state -> mapColor)
			.instrument(NoteBlockInstrument.BASS)
			.strength(2F)
			.sound(WWSoundTypes.HOLLOWED_STEM);
	}

	// LEAF LITTER
	public static final Block ACACIA_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.ACACIA_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block AZALEA_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.AZALEA_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block BAOBAB_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.BAOBAB_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block BIRCH_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.BIRCH_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block CHERRY_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.CHERRY_LEAF_LITTER, WWSoundTypes.CHERRY_LEAF_LITTER);
	public static final Block CYPRESS_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.CYPRESS_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block DARK_OAK_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.DARK_OAK_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block JUNGLE_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.JUNGLE_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block MANGROVE_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.MANGROVE_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block PALE_OAK_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.PALE_OAK_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block PALM_FROND_LITTER = registerLeafLitter(WWBlockItemIds.PALM_FROND_LITTER, SoundType.LEAF_LITTER);
	public static final Block SPRUCE_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.SPRUCE_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block WILLOW_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.WILLOW_LEAF_LITTER, SoundType.LEAF_LITTER);
	public static final Block YELLOW_MAPLE_LEAF_LITTER = registerMapleLeafLitter(WWBlockItemIds.YELLOW_MAPLE_LEAF_LITTER,
		YELLOW_MAPLE_LEAVES,
		WWParticleTypes.YELLOW_MAPLE_LEAVES
	);
	public static final Block ORANGE_MAPLE_LEAF_LITTER = registerMapleLeafLitter(WWBlockItemIds.ORANGE_MAPLE_LEAF_LITTER,
		ORANGE_MAPLE_LEAVES,
		WWParticleTypes.ORANGE_MAPLE_LEAVES
	);
	public static final Block RED_MAPLE_LEAF_LITTER = registerMapleLeafLitter(WWBlockItemIds.RED_MAPLE_LEAF_LITTER,
		RED_MAPLE_LEAVES,
		WWParticleTypes.RED_MAPLE_LEAVES
	);

	public static Block registerMapleLeafLitter(BlockItemId id, Block sourceBlock, ParticleType<WWFallingLeavesParticleOptions> particleType) {
		return registerLeafLitter(
			id,
			sourceBlock,
			particleType,
			0.04F,
			() -> WWAmbienceAndMiscConfig.MAPLE_LEAF_FREQUENCY.get() * 0.01D,
			5,
			FallingLeafUtil.LeafMovementType.SWIRL,
			WWSoundTypes.MAPLE_LEAF_LITTER
		);
	}

	public static Block registerLeafLitter(
		BlockItemId id,
		Block sourceBlock,
		ParticleType<WWFallingLeavesParticleOptions> particleType,
		float litterChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		FallingLeafUtil.LeafMovementType leafMovementType,
		SoundType soundType
	) {
		return registerLeafLitter(
			id, sourceBlock, particleType, litterChance, 0.0225F, frequencyModifier, textureSize, 3F, 10F, leafMovementType, soundType
		);
	}

	public static Block registerLeafLitter(
		BlockItemId id,
		Block sourceBlock,
		ParticleType<WWFallingLeavesParticleOptions> particleType,
		float litterChance,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		FallingLeafUtil.LeafMovementType leafMovementType,
		SoundType soundType
	) {
		final Block block = registerLeafLitter(id, soundType);
		FallingLeafUtil.registerLeavesWithLitter(
			sourceBlock,
			block,
			litterChance,
			particleType,
			particleChance,
			frequencyModifier,
			textureSize,
			particleGravityScale,
			windScale,
			leafMovementType
		);
		return block;
	}

	private static Block registerLeafLitter(BlockItemId id, SoundType soundType) {
		return Blocks.register(id, LeafLitterBlock::new, Properties.ofFullCopy(Blocks.LEAF_LITTER).sound(soundType));
	}

	// SCULK
	public static final Block SCULK_STAIRS = Blocks.register(WWBlockItemIds.SCULK_STAIRS,
		properties -> new SculkStairBlock(Blocks.SCULK.defaultBlockState(), properties),
		Properties.ofFullCopy(Blocks.SCULK)
	);
	public static final Block SCULK_SLAB = Blocks.register(WWBlockItemIds.SCULK_SLAB, SculkSlabBlock::new, Properties.ofFullCopy(Blocks.SCULK));
	public static final Block SCULK_WALL = Blocks.register(WWBlockItemIds.SCULK_WALL, SculkWallBlock::new, Properties.ofFullCopy(Blocks.SCULK));
	public static final Block OSSEOUS_SCULK = Blocks.register(WWBlockItemIds.OSSEOUS_SCULK,
		OsseousSculkBlock::new,
		Properties.of()
			.mapColor(MapColor.SAND)
			.strength(2F)
			.sound(WWSoundTypes.OSSEOUS_SCULK)
	);
	public static final Block HANGING_TENDRIL = Blocks.register(WWBlockItemIds.HANGING_TENDRIL,
		HangingTendrilBlock::new,
		Properties.ofFullCopy(Blocks.SCULK_SENSOR)
			.strength(0.7F)
			.noCollision()
			.noOcclusion()
			.randomTicks()
			.lightLevel(state -> 1)
			.sound(WWSoundTypes.HANGING_TENDRIL)
			.emissiveRendering(HangingTendrilBlock::shouldHavePogLighting)
	);
	public static final Block ECHO_GLASS = Blocks.register(WWBlockItemIds.ECHO_GLASS,
		EchoGlassBlock::new,
		Properties.ofFullCopy(Blocks.TINTED_GLASS)
			.strength(1F)
			.mapColor(MapColor.COLOR_CYAN)
			.noOcclusion()
			.randomTicks()
			.sound(WWSoundTypes.ECHO_GLASS)
	);

	// MESOGLEA
	public static final Block PEARLESCENT_BLUE_MESOGLEA = registerMesoglea(WWBlockItemIds.PEARLESCENT_BLUE_MESOGLEA,
		MapColor.QUARTZ,
		WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_BLUE,
		WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_BLUE,
		WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_BLUE,
		WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_BLUE,
		WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_BLUE,
		true,
		Integer.parseInt("B9DAED", 16)
	);
	public static final Block PEARLESCENT_PURPLE_MESOGLEA = registerMesoglea(WWBlockItemIds.PEARLESCENT_PURPLE_MESOGLEA,
		MapColor.COLOR_PURPLE,
		WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_PURPLE,
		WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_PURPLE,
		WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_PURPLE,
		WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_PURPLE,
		WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_PURPLE,
		true,
		Integer.parseInt("C6B2F4", 16)
	);
	public static final Block YELLOW_MESOGLEA = registerMesoglea(WWBlockItemIds.YELLOW_MESOGLEA,
		MapColor.COLOR_YELLOW,
		WWParticleTypes.HANGING_MESOGLEA_YELLOW,
		WWParticleTypes.MESOGLEA_BUBBLE_YELLOW,
		WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_YELLOW,
		WWParticleTypes.CURRENT_DOWN_MESOGLEA_YELLOW,
		WWParticleTypes.MESOGLEA_SPLASH_YELLOW,
		false,
		Integer.parseInt("FFC958", 16)
	);
	public static final Block BLUE_MESOGLEA = registerMesoglea(WWBlockItemIds.BLUE_MESOGLEA,
		MapColor.COLOR_LIGHT_BLUE,
		WWParticleTypes.HANGING_MESOGLEA_BLUE,
		WWParticleTypes.MESOGLEA_BUBBLE_BLUE,
		WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_BLUE,
		WWParticleTypes.CURRENT_DOWN_MESOGLEA_BLUE,
		WWParticleTypes.MESOGLEA_SPLASH_BLUE,
		false,
		Integer.parseInt("596BFF", 16)
	);
	public static final Block LIME_MESOGLEA = registerMesoglea(WWBlockItemIds.LIME_MESOGLEA,
		MapColor.COLOR_LIGHT_GREEN,
		WWParticleTypes.HANGING_MESOGLEA_LIME,
		WWParticleTypes.MESOGLEA_BUBBLE_LIME,
		WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_LIME,
		WWParticleTypes.CURRENT_DOWN_MESOGLEA_LIME,
		WWParticleTypes.MESOGLEA_SPLASH_LIME,
		false,
		Integer.parseInt("55EF1B", 16)
	);
	public static final Block RED_MESOGLEA = registerMesoglea(WWBlockItemIds.RED_MESOGLEA,
		MapColor.COLOR_RED,
		WWParticleTypes.HANGING_MESOGLEA_RED,
		WWParticleTypes.MESOGLEA_BUBBLE_RED,
		WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_RED,
		WWParticleTypes.CURRENT_DOWN_MESOGLEA_RED,
		WWParticleTypes.MESOGLEA_SPLASH_RED,
		false,
		Integer.parseInt("FD3420", 16)
	);
	public static final Block PINK_MESOGLEA = registerMesoglea(WWBlockItemIds.PINK_MESOGLEA,
		MapColor.COLOR_PINK,
		WWParticleTypes.HANGING_MESOGLEA_PINK,
		WWParticleTypes.MESOGLEA_BUBBLE_PINK,
		WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PINK,
		WWParticleTypes.CURRENT_DOWN_MESOGLEA_PINK,
		WWParticleTypes.MESOGLEA_SPLASH_PINK,
		false,
		Integer.parseInt("ED87D1", 16)
	);

	public static Block registerMesoglea(
		BlockItemId id,
		MapColor mapColor,
		ParticleOptions dripParticle,
		ParticleOptions bubbleParticle,
		ParticleOptions bubbleColumnUpParticle,
		ParticleOptions currentDownParticle,
		ParticleOptions splashParticle,
		boolean pearlescent,
		int waterFogColor
	) {
		return Blocks.register(
			id,
			properties -> new MesogleaBlock(
				pearlescent,
				new ColorRGBA(waterFogColor),
				dripParticle,
				bubbleParticle,
				bubbleColumnUpParticle,
				currentDownParticle,
				splashParticle,
				properties),
			Properties.of()
				.mapColor(mapColor)
				.noOcclusion()
				.strength(0.2F)
				.friction(0.8F)
				.lightLevel(state -> 7)
				.sound(WWSoundTypes.MESOGLEA)
				.isSuffocating(Blocks::never)
				.isViewBlocking(Blocks::never)
				.dynamicShape()
				.pushReaction(PushReaction.DESTROY)
		);
	}

	// NEMATOCYST
	public static final Block PEARLESCENT_BLUE_NEMATOCYST = registerNematocyst(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST, MapColor.QUARTZ);
	public static final Block PEARLESCENT_PURPLE_NEMATOCYST = registerNematocyst(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST, MapColor.COLOR_PURPLE);
	public static final Block YELLOW_NEMATOCYST = registerNematocyst(WWBlockItemIds.YELLOW_NEMATOCYST, MapColor.COLOR_YELLOW);
	public static final Block BLUE_NEMATOCYST = registerNematocyst(WWBlockItemIds.BLUE_NEMATOCYST, MapColor.COLOR_BLUE);
	public static final Block LIME_NEMATOCYST = registerNematocyst(WWBlockItemIds.LIME_NEMATOCYST, MapColor.COLOR_LIGHT_GREEN);
	public static final Block RED_NEMATOCYST = registerNematocyst(WWBlockItemIds.RED_NEMATOCYST, MapColor.COLOR_RED);
	public static final Block PINK_NEMATOCYST = registerNematocyst(WWBlockItemIds.PINK_NEMATOCYST, MapColor.COLOR_PINK);

	public static Block registerNematocyst(BlockItemId id, MapColor mapColor) {
		return Blocks.register(
			id,
			NematocystBlock::new,
			Properties.of()
				.mapColor(mapColor)
				.noCollision()
				.noOcclusion()
				.sound(WWSoundTypes.NEMATOCYST)
				.pushReaction(PushReaction.DESTROY)
		);
	}

	// MISC
	public static final Block TERMITE_MOUND = Blocks.register(WWBlockItemIds.TERMITE_MOUND,
		TermiteMoundBlock::new,
		Properties.of()
			.mapColor(MapColor.COLOR_BROWN)
			.strength(0.3F)
			.sound(WWSoundTypes.TERMITE_MOUND)
			.postProcess(Blocks::postProcessSelf)
			.randomTicks()
	);
	public static final Block STONE_CHEST = Blocks.register(WWBlockItemIds.STONE_CHEST,
		properties -> new StoneChestBlock(() -> WWBlockEntityTypes.STONE_CHEST, properties),
		Properties.ofFullCopy(Blocks.CHEST)
			.mapColor(MapColor.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(2.5F)
			.requiresCorrectToolForDrops()
			.sound(SoundType.DEEPSLATE)
			.strength(35F, 12F)
	);
	public static final Block NULL_BLOCK = Blocks.register(WWBlockItemIds.NULL_BLOCK, Properties.ofFullCopy(Blocks.STONE).sound(WWSoundTypes.NULL_BLOCK));
	public static final Block DISPLAY_LANTERN = Blocks.register(WWBlockItemIds.DISPLAY_LANTERN,
		DisplayLanternBlock::new,
		Properties.of()
			.mapColor(MapColor.METAL)
			.forceSolidOn()
			.strength(3.5F)
			.pushReaction(PushReaction.DESTROY)
			.sound(SoundType.LANTERN)
			.lightLevel(state -> state.getValue(WWBlockStateProperties.DISPLAY_LIGHT))
	);

	// FLOWERS
	public static final Block POTTED_CACTUS_FLOWER = registerFlowerPot(WWBlockIds.POTTED_CACTUS_FLOWER, Blocks.CACTUS_FLOWER);

	public static final Block SEEDING_DANDELION = Blocks.register(WWBlockItemIds.SEEDING_DANDELION,
		properties -> new SeedingFlowerBlock(MobEffects.SLOW_FALLING, 12, Blocks.DANDELION, properties),
		Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final Block POTTED_SEEDING_DANDELION = registerFlowerPot(WWBlockIds.POTTED_SEEDING_DANDELION, SEEDING_DANDELION);

	public static final Block CARNATION = registerFlower(WWBlockItemIds.CARNATION, MobEffects.REGENERATION, 12F);
	public static final Block POTTED_CARNATION = registerFlowerPot(WWBlockIds.POTTED_CARNATION, CARNATION);

	public static final Block MARIGOLD = registerFlower(WWBlockItemIds.MARIGOLD, MobEffects.RESISTANCE, 8F);
	public static final Block POTTED_MARIGOLD = registerFlowerPot(WWBlockIds.POTTED_MARIGOLD, MARIGOLD);

	public static final Block PASQUEFLOWER = registerFlower(WWBlockItemIds.PASQUEFLOWER, MobEffects.NIGHT_VISION, 8F);
	public static final Block POTTED_PASQUEFLOWER = registerFlowerPot(WWBlockIds.POTTED_PASQUEFLOWER, PASQUEFLOWER);

	public static final Block RED_HIBISCUS = registerHibiscus(WWBlockItemIds.RED_HIBISCUS);
	public static final Block POTTED_RED_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_RED_HIBISCUS, RED_HIBISCUS);

	public static final Block YELLOW_HIBISCUS = registerHibiscus(WWBlockItemIds.YELLOW_HIBISCUS);
	public static final Block POTTED_YELLOW_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_YELLOW_HIBISCUS, YELLOW_HIBISCUS);

	public static final Block WHITE_HIBISCUS = registerHibiscus(WWBlockItemIds.WHITE_HIBISCUS);
	public static final Block POTTED_WHITE_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_WHITE_HIBISCUS, WHITE_HIBISCUS);

	public static final Block PINK_HIBISCUS = registerHibiscus(WWBlockItemIds.PINK_HIBISCUS);
	public static final Block POTTED_PINK_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_PINK_HIBISCUS, PINK_HIBISCUS);

	public static final Block PURPLE_HIBISCUS = registerHibiscus(WWBlockItemIds.PURPLE_HIBISCUS);
	public static final Block POTTED_PURPLE_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_PURPLE_HIBISCUS, PURPLE_HIBISCUS);

	public static Block registerFlower(BlockItemId id, Holder<MobEffect> suspiciousStewEffect, float effectSeconds) {
		return Blocks.register(id, properties -> new FlowerBlock(suspiciousStewEffect, effectSeconds, properties), Properties.ofFullCopy(Blocks.DANDELION));
	}

	public static Block registerHibiscus(BlockItemId id) {
		return Blocks.register(id, properties -> new WideFlowerBlock(MobEffects.HUNGER, 8F, properties), Properties.ofFullCopy(Blocks.DANDELION));
	}

	// FLOWERBEDS
	public static final Block POTTED_PINK_PETALS = registerFlowerPot(WWBlockIds.POTTED_PINK_PETALS, Blocks.PINK_PETALS);
	public static final Block POTTED_WILDFLOWERS = registerFlowerPot(WWBlockIds.POTTED_WILDFLOWERS, Blocks.WILDFLOWERS);

	public static final Block PHLOX = Blocks.register(WWBlockItemIds.PHLOX, FlowerBedBlock::new, Properties.ofFullCopy(Blocks.PINK_PETALS));
	public static final Block POTTED_PHLOX = registerFlowerPot(WWBlockIds.POTTED_PHLOX, PHLOX);

	public static final Block LANTANAS = Blocks.register(WWBlockItemIds.LANTANAS, FlowerBedBlock::new, Properties.ofFullCopy(Blocks.PINK_PETALS));
	public static final Block POTTED_LANTANAS = registerFlowerPot(WWBlockIds.POTTED_LANTANAS, LANTANAS);

	public static final Block CLOVERS = Blocks.register(WWBlockItemIds.CLOVERS,
		FlowerBedBlock::new,
		Properties.ofFullCopy(Blocks.PINK_PETALS).sound(SoundType.GRASS).instabreak()
	);
	public static final Block POTTED_CLOVERS = registerFlowerPot(WWBlockIds.POTTED_CLOVERS, CLOVERS);

	// TALL FLOWERS
	public static final Block DATURA = Blocks.register(WWBlockItemIds.DATURA, TallFlowerBlock::new, Properties.ofFullCopy(Blocks.SUNFLOWER));
	public static final Block MILKWEED = Blocks.register(WWBlockItemIds.MILKWEED, MilkweedBlock::new, Properties.ofFullCopy(Blocks.SUNFLOWER).randomTicks());

	// VEGETATION
	public static final Block POTTED_SHORT_GRASS = registerFlowerPot(WWBlockIds.POTTED_SHORT_GRASS, Blocks.SHORT_GRASS);
	public static final Block POTTED_BUSH = registerFlowerPot(WWBlockIds.POTTED_BUSH, Blocks.BUSH);
	public static final Block POTTED_FIREFLY_BUSH = registerFlowerPot(WWBlockIds.POTTED_FIREFLY_BUSH, Blocks.FIREFLY_BUSH);
	public static final Block POTTED_SHORT_DRY_GRASS = registerFlowerPot(WWBlockIds.POTTED_SHORT_DRY_GRASS, Blocks.SHORT_DRY_GRASS);
	public static final Block POTTED_TALL_DRY_GRASS = registerFlowerPot(WWBlockIds.POTTED_TALL_DRY_GRASS, Blocks.TALL_DRY_GRASS);
	public static final Block POTTED_BIG_DRIPLEAF = registerFlowerPot(WWBlockIds.POTTED_BIG_DRIPLEAF, Blocks.BIG_DRIPLEAF);
	public static final Block POTTED_SMALL_DRIPLEAF = registerFlowerPot(WWBlockIds.POTTED_SMALL_DRIPLEAF, Blocks.SMALL_DRIPLEAF);

	public static final Block POLLEN = Blocks.register(WWBlockItemIds.POLLEN,
		PollenBlock::new,
		Properties.ofFullCopy(Blocks.SHORT_GRASS)
			.mapColor(MapColor.SAND)
			.sound(WWSoundTypes.POLLEN)
			.offsetType(BlockBehaviour.OffsetType.NONE)
	);

	public static final Block PRICKLY_PEAR = Blocks.register(WWBlockItemIds.PRICKLY_PEAR,
		PricklyPearCactusBlock::new,
		Properties.ofFullCopy(Blocks.CACTUS).noCollision().offsetType(BlockBehaviour.OffsetType.XZ)
	);
	public static final Block POTTED_PRICKLY_PEAR = registerFlowerPot(WWBlockIds.POTTED_PRICKLY_PEAR, PRICKLY_PEAR);

	public static final Block SHRUB = Blocks.register(WWBlockItemIds.SHRUB,
		ShrubBlock::new,
		Properties.ofFullCopy(Blocks.DEAD_BUSH)
			.mapColor(MapColor.PLANT)
			.noOcclusion()
			.randomTicks()
			.offsetType(BlockBehaviour.OffsetType.XZ)
	);
	public static final Block POTTED_SHRUB = registerFlowerPot(WWBlockIds.POTTED_SHRUB, SHRUB);

	public static final Block TUMBLEWEED_PLANT = Blocks.register(WWBlockItemIds.TUMBLEWEED_PLANT,
		TumbleweedPlantBlock::new,
		Properties.of()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final Block POTTED_TUMBLEWEED_PLANT = registerFlowerPot(WWBlockIds.POTTED_TUMBLEWEED_PLANT, TUMBLEWEED_PLANT);

	public static final Block TUMBLEWEED = Blocks.register(WWBlockItemIds.TUMBLEWEED,
		TumbleweedBlock::new,
		Properties.of()
			.instabreak()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final Block POTTED_TUMBLEWEED = registerFlowerPot(WWBlockIds.POTTED_TUMBLEWEED, TUMBLEWEED);

	public static final Block MYCELIUM_GROWTH = Blocks.register(WWBlockItemIds.MYCELIUM_GROWTH,
		MyceliumGrowthBlock::new,
		Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.NETHER_SPROUTS)
	);
	public static final Block POTTED_MYCELIUM_GROWTH = registerFlowerPot(WWBlockIds.POTTED_MYCELIUM_GROWTH, MYCELIUM_GROWTH);

	public static final Block FROZEN_SHORT_GRASS = Blocks.register(WWBlockItemIds.FROZEN_SHORT_GRASS, FrozenTallGrassBlock::new, Properties.ofFullCopy(Blocks.SHORT_GRASS));
	public static final Block POTTED_FROZEN_SHORT_GRASS = registerFlowerPot(WWBlockIds.POTTED_FROZEN_SHORT_GRASS, FROZEN_SHORT_GRASS);
	public static final Block FROZEN_TALL_GRASS = Blocks.register(WWBlockItemIds.FROZEN_TALL_GRASS, FrozenDoublePlantBlock::new, Properties.ofFullCopy(Blocks.TALL_GRASS));

	public static final Block FROZEN_FERN = Blocks.register(WWBlockItemIds.FROZEN_FERN, FrozenTallGrassBlock::new, Properties.ofFullCopy(Blocks.FERN));
	public static final Block POTTED_FROZEN_FERN = registerFlowerPot(WWBlockIds.POTTED_FROZEN_FERN, FROZEN_FERN);

	public static final Block FROZEN_LARGE_FERN = Blocks.register(WWBlockItemIds.FROZEN_LARGE_FERN, FrozenDoublePlantBlock::new, Properties.ofFullCopy(Blocks.LARGE_FERN));

	public static final Block FROZEN_BUSH = Blocks.register(WWBlockItemIds.FROZEN_BUSH, FrozenBushBlock::new, Properties.ofFullCopy(Blocks.BUSH));
	public static final Block POTTED_FROZEN_BUSH = registerFlowerPot(WWBlockIds.POTTED_FROZEN_BUSH, FROZEN_BUSH);

	// MUSHROOMS
	public static final Block BROWN_SHELF_FUNGI = Blocks.register(WWBlockItemIds.BROWN_SHELF_FUNGI,
		properties -> new ShelfFungiBlock(WWLootTables.SHEAR_BROWN_SHELF_FUNGI, properties),
		shelfFungiProperties(MapColor.COLOR_BROWN, WWSoundTypes.MUSHROOM).lightLevel(state -> 1)
	);
	public static final Block RED_SHELF_FUNGI = Blocks.register(WWBlockItemIds.RED_SHELF_FUNGI,
		properties -> new ShelfFungiBlock(WWLootTables.SHEAR_RED_SHELF_FUNGI, properties),
		shelfFungiProperties(MapColor.COLOR_RED, WWSoundTypes.MUSHROOM)
	);
	public static final Block CRIMSON_SHELF_FUNGI = Blocks.register(WWBlockItemIds.CRIMSON_SHELF_FUNGI,
		properties -> new ShelfFungiBlock(WWLootTables.SHEAR_CRIMSON_SHELF_FUNGI, properties),
		shelfFungiProperties(MapColor.NETHER, SoundType.FUNGUS)
	);
	public static final Block WARPED_SHELF_FUNGI = Blocks.register(WWBlockItemIds.WARPED_SHELF_FUNGI,
		properties -> new ShelfFungiBlock(WWLootTables.SHEAR_WARPED_SHELF_FUNGI, properties),
		shelfFungiProperties(MapColor.NETHER, SoundType.FUNGUS)
	);

	public static final Block PALE_MUSHROOM_BLOCK = Blocks.register(WWBlockItemIds.PALE_MUSHROOM_BLOCK,
		HugePaleMushroomBlock::new,
		Properties.of()
			.mapColor(MapColor.COLOR_GRAY)
			.instrument(NoteBlockInstrument.BASS)
			.strength(0.2F)
			.sound(SoundType.WOOD)
			.ignitedByLava()
	);
	public static final Block PALE_MUSHROOM = Blocks.register(WWBlockItemIds.PALE_MUSHROOM,
		properties -> new PaleMushroomBlock(ResourceKey.create(Registries.CONFIGURED_FEATURE, WWConstants.id("huge_pale_mushroom")), properties),
		Properties.of()
			.mapColor(MapColor.COLOR_GRAY)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.GRASS)
			.postProcess(Blocks::postProcessSelf)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block POTTED_PALE_MUSHROOM = registerFlowerPot(WWBlockIds.POTTED_PALE_MUSHROOM, PALE_MUSHROOM);
	public static final Block PALE_SHELF_FUNGI = Blocks.register(WWBlockItemIds.PALE_SHELF_FUNGI,
		properties -> new PaleShelfFungiBlock(WWLootTables.SHEAR_PALE_SHELF_FUNGI, properties),
		shelfFungiProperties(MapColor.COLOR_GRAY, WWSoundTypes.MUSHROOM)
	);

	public static Properties shelfFungiProperties(MapColor mapColor, SoundType soundType) {
		return Properties.of()
			.mapColor(mapColor)
			.strength(0.2F)
			.randomTicks()
			.noCollision()
			.noOcclusion()
			.sound(soundType)
			.postProcess(Blocks::postProcessSelf)
			.pushReaction(PushReaction.DESTROY);
	}

	// MOSS
	public static final Block AUBURN_MOSS_BLOCK = Blocks.register(WWBlockItemIds.AUBURN_MOSS_BLOCK,
		AuburnMossBlock::new,
		Properties.of()
			.mapColor(MapColor.TERRACOTTA_ORANGE)
			.strength(0.1F)
			.sound(SoundType.MOSS)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block AUBURN_MOSS_CARPET = Blocks.register(WWBlockItemIds.AUBURN_MOSS_CARPET,
		AuburnMossCarpetBlock::new,
		Properties.of()
			.mapColor(MapColor.TERRACOTTA_ORANGE)
			.strength(0.1F)
			.sound(SoundType.MOSS_CARPET)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block AUBURN_CREEPING_MOSS = Blocks.register(WWBlockItemIds.AUBURN_CREEPING_MOSS,
		AuburnCreepingMossBlock::new,
		Properties.of()
			.mapColor(MapColor.TERRACOTTA_ORANGE)
			.forceSolidOn()
			.noCollision()
			.strength(0.1F)
			.sound(SoundType.MOSS_CARPET)
			.pushReaction(PushReaction.DESTROY)
	);

	// AQUATIC
	public static final Block CATTAIL = Blocks.register(WWBlockItemIds.CATTAIL, CattailBlock::new, Properties.ofFullCopy(Blocks.ROSE_BUSH).sound(SoundType.WET_GRASS));
	public static final Block FLOWERING_LILY_PAD = Blocks.register(WWBlockItemIds.FLOWERING_LILY_PAD,
		properties -> new FloweringWaterlilyBlock(Blocks.LILY_PAD, properties),
		Properties.ofFullCopy(Blocks.LILY_PAD)
	);
	public static final Block ALGAE = Blocks.register(WWBlockItemIds.ALGAE,
		AlgaeBlock::new,
		Properties.ofFullCopy(Blocks.FROGSPAWN).mapColor(MapColor.PLANT).sound(WWSoundTypes.ALGAE)
	);
	public static final Block PLANKTON = Blocks.register(WWBlockItemIds.PLANKTON,
		PlanktonBlock::new,
		Properties.ofFullCopy(Blocks.FROGSPAWN)
			.mapColor(MapColor.COLOR_LIGHT_BLUE)
			.randomTicks()
			.requiresCorrectToolForDrops()
			.lightLevel(state -> PlanktonBlock.isGlowing(state) ? PlanktonBlock.LIGHT_LEVEL : 0)
			.sound(WWSoundTypes.ALGAE)
	);
	public static final Block SPONGE_BUD = Blocks.register(WWBlockItemIds.SPONGE_BUD,
		SpongeBudBlock::new,
		Properties.ofFullCopy(Blocks.SPONGE)
			.strength(0.1F)
			.noCollision()
			.noOcclusion()
			.sound(SoundType.SPONGE)
	);
	public static final Block BARNACLES = Blocks.register(WWBlockItemIds.BARNACLES,
		BarnaclesBlock::new,
		Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.forceSolidOn()
			.noCollision()
			.sound(WWSoundTypes.BARNACLES)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block SEA_ANEMONE = Blocks.register(WWBlockItemIds.SEA_ANEMONE,
		SeaAnemoneBlock::new,
		Properties.of()
			.mapColor(MapColor.WATER)
			.instabreak()
			.noCollision()
			.lightLevel(state -> SeaAnemoneBlock.isGlowing(state) ? SeaAnemoneBlock.LIGHT_LEVEL : 0)
			.randomTicks()
			.sound(WWSoundTypes.SEA_ANEMONE)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block SEA_WHIP = Blocks.register(WWBlockItemIds.SEA_WHIP,
		SeaWhipBlock::new,
		Properties.of()
			.mapColor(MapColor.WATER)
			.instabreak()
			.noCollision()
			.sound(SoundType.WET_GRASS)
			.pushReaction(PushReaction.DESTROY)
	);
	public static final Block TUBE_WORMS = Blocks.register(WWBlockItemIds.TUBE_WORMS,
		TubeWormsBlock::new,
		Properties.of()
			.mapColor(MapColor.WATER)
			.strength(0.2F)
			.noCollision()
			.randomTicks()
			.sound(WWSoundTypes.TUBE_WORMS)
			.pushReaction(PushReaction.DESTROY)
	);

	// EGGS
	public static final Block OSTRICH_EGG = Blocks.register(WWBlockItemIds.OSTRICH_EGG,
		OstrichEggBlock::new,
		Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);
	public static final Block PENGUIN_EGG = Blocks.register(WWBlockItemIds.PENGUIN_EGG,
		PenguinEggBlock::new,
		Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);

	// GABBRO
	public static final Block GABBRO = Blocks.register(WWBlockItemIds.GABBRO,
		Properties.of().mapColor(MapColor.TERRACOTTA_BROWN)
			.sound(WWSoundTypes.GABBRO)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(4.5F)
	);
	public static final Block GABBRO_STAIRS = Blocks.register(WWBlockItemIds.GABBRO_STAIRS,
		properties -> new StairBlock(GABBRO.defaultBlockState(), properties),
		Properties.ofFullCopy(WWBlocks.GABBRO).requiredFeatures(WWFeatureFlags.TRAILIER_TALES_COMPAT)
	);
	public static final Block GABBRO_SLAB = Blocks.register(WWBlockItemIds.GABBRO_SLAB,
		SlabBlock::new,
		Properties.ofFullCopy(WWBlocks.GABBRO).requiredFeatures(WWFeatureFlags.TRAILIER_TALES_COMPAT)
	);
	public static final Block GABBRO_WALL = Blocks.register(WWBlockItemIds.GABBRO_WALL,
		WallBlock::new,
		Properties.ofFullCopy(WWBlocks.GABBRO).requiredFeatures(WWFeatureFlags.TRAILIER_TALES_COMPAT)
	);

	public static final Block GEYSER = Blocks.register(WWBlockItemIds.GEYSER,
		GeyserBlock::new,
		Properties.ofFullCopy(GABBRO)
			.sound(WWSoundTypes.GEYSER)
			.strength(8F)
			.isValidSpawn((state, level, pos, entityType) -> false)
			.postProcess(Blocks::postProcessSelf)
	);

	public static final Block POLISHED_GABBRO = Blocks.register(WWBlockItemIds.POLISHED_GABBRO, Properties.ofFullCopy(GABBRO));
	public static final Block POLISHED_GABBRO_STAIRS = Blocks.registerStair(WWBlockItemIds.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO);
	public static final Block POLISHED_GABBRO_SLAB = Blocks.registerSlab(WWBlockItemIds.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO);
	public static final Block POLISHED_GABBRO_WALL = Blocks.registerWall(WWBlockItemIds.POLISHED_GABBRO_WALL, WWBlocks.POLISHED_GABBRO);
	public static final BlockFamily FAMILY_GABBRO = BlockFamilies.familyBuilder(WWBlocks.GABBRO)
		.stairs(GABBRO_STAIRS)
		.slab(GABBRO_SLAB)
		.wall(GABBRO_WALL)
		.polished(POLISHED_GABBRO)
		.dontGenerateModel()
		.getFamily();
	public static final BlockFamily FAMILY_POLISHED_GABBRO = BlockFamilies.familyBuilder(POLISHED_GABBRO)
		.stairs(POLISHED_GABBRO_STAIRS)
		.slab(POLISHED_GABBRO_SLAB)
		.wall(POLISHED_GABBRO_WALL)
		.getFamily();

	public static final Block GABBRO_BRICKS = Blocks.register(WWBlockItemIds.GABBRO_BRICKS, Properties.ofFullCopy(GABBRO).sound(WWSoundTypes.GABBRO_BRICKS));
	public static final Block GABBRO_BRICK_STAIRS = Blocks.registerStair(WWBlockItemIds.GABBRO_BRICK_STAIRS, GABBRO_BRICKS);
	public static final Block GABBRO_BRICK_SLAB = Blocks.registerSlab(WWBlockItemIds.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICKS);
	public static final Block GABBRO_BRICK_WALL = Blocks.registerWall(WWBlockItemIds.GABBRO_BRICK_WALL, WWBlocks.GABBRO_BRICKS);
	public static final Block CRACKED_GABBRO_BRICKS = Blocks.register(WWBlockItemIds.CRACKED_GABBRO_BRICKS, Properties.ofFullCopy(WWBlocks.GABBRO_BRICKS));
	public static final Block CHISELED_GABBRO_BRICKS = Blocks.register(WWBlockItemIds.CHISELED_GABBRO_BRICKS, Properties.ofFullCopy(WWBlocks.GABBRO_BRICKS));
	public static final BlockFamily FAMILY_GABBRO_BRICK = BlockFamilies.familyBuilder(GABBRO_BRICKS)
		.stairs(GABBRO_BRICK_STAIRS)
		.slab(GABBRO_BRICK_SLAB)
		.wall(GABBRO_BRICK_WALL)
		.cracked(CRACKED_GABBRO_BRICKS)
		.chiseled(CHISELED_GABBRO_BRICKS)
		.getFamily();

	public static final Block MOSSY_GABBRO_BRICKS = Blocks.register(WWBlockItemIds.MOSSY_GABBRO_BRICKS,
		Properties.ofFullCopy(GABBRO_BRICKS).requiredFeatures(WWFeatureFlags.TRAILIER_TALES_COMPAT)
	);
	public static final Block MOSSY_GABBRO_BRICK_STAIRS = Blocks.registerStair(WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICKS);
	public static final Block MOSSY_GABBRO_BRICK_SLAB = Blocks.registerSlab(WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICKS);
	public static final Block MOSSY_GABBRO_BRICK_WALL = Blocks.registerWall(WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL, WWBlocks.MOSSY_GABBRO_BRICKS);
	public static final BlockFamily FAMILY_MOSSY_GABBRO_BRICK = BlockFamilies.familyBuilder(MOSSY_GABBRO_BRICKS)
		.stairs(MOSSY_GABBRO_BRICK_STAIRS)
		.slab(MOSSY_GABBRO_BRICK_SLAB)
		.wall(MOSSY_GABBRO_BRICK_WALL)
		.getFamily();

	// BAOBAB
	public static final Block BAOBAB_PLANKS = Blocks.register(WWBlockItemIds.BAOBAB_PLANKS, Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(BAOBAB_PLANKS_COLOR));
	public static final Block BAOBAB_STAIRS = Blocks.registerStair(WWBlockItemIds.BAOBAB_STAIRS, BAOBAB_PLANKS);
	public static final Block BAOBAB_FENCE_GATE = Blocks.register(WWBlockItemIds.BAOBAB_FENCE_GATE,
		properties -> new FenceGateBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_SLAB = Blocks.registerSlab(WWBlockItemIds.BAOBAB_SLAB, BAOBAB_PLANKS);
	public static final Block BAOBAB_PRESSURE_PLATE = Blocks.register(WWBlockItemIds.BAOBAB_PRESSURE_PLATE,
		properties -> new PressurePlateBlock(BAOBAB_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_BUTTON = Blocks.register(WWBlockItemIds.BAOBAB_BUTTON,
		properties -> new ButtonBlock(BAOBAB_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final Block BAOBAB_DOOR = Blocks.register(WWBlockItemIds.BAOBAB_DOOR,
		properties -> new DoorBlock(BAOBAB_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_TRAPDOOR = Blocks.register(WWBlockItemIds.BAOBAB_TRAPDOOR,
		properties -> new TrapDoorBlock(BAOBAB_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_FENCE = Blocks.register(WWBlockItemIds.BAOBAB_FENCE,
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_LOG = Blocks.register(WWBlockItemIds.BAOBAB_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_BAOBAB_LOG = Blocks.register(WWBlockItemIds.STRIPPED_BAOBAB_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(BAOBAB_PLANKS_COLOR, BAOBAB_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_HOLLOWED_BAOBAB_LOG = Blocks.register(WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG,
		HollowedLogBlock::new,
		strippedHollowedLogProperties(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_WOOD = Blocks.register(WWBlockItemIds.BAOBAB_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(BAOBAB_BARK_COLOR)
	);
	public static final Block STRIPPED_BAOBAB_WOOD = Blocks.register(WWBlockItemIds.STRIPPED_BAOBAB_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final Block BAOBAB_SIGN = Blocks.register(WWBlockItemIds.BAOBAB_SIGN,
		properties -> new StandingSignBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
	);
	public static final Block BAOBAB_WALL_SIGN = Blocks.register(WWBlockIds.BAOBAB_WALL_SIGN,
		properties -> new WallSignBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
			.overrideDescription(BAOBAB_SIGN.getDescriptionId())
			.overrideLootTable(BAOBAB_SIGN.getLootTable())
	);
	public static final Block BAOBAB_HANGING_SIGN = Blocks.register(WWBlockItemIds.BAOBAB_HANGING_SIGN,
		properties -> new CeilingHangingSignBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
	);
	public static final Block BAOBAB_WALL_HANGING_SIGN = Blocks.register(WWBlockIds.BAOBAB_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(BAOBAB_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.defaultMapColor())
			.overrideDescription(BAOBAB_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(BAOBAB_HANGING_SIGN.getLootTable())
	);
	public static final Block BAOBAB_SHELF = Blocks.register(WWBlockItemIds.BAOBAB_SHELF,
		ShelfBlock::new,
		Properties.of()
			.mapColor(BAOBAB_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
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

	// WILLOW
	public static final Block WILLOW_PLANKS = Blocks.register(WWBlockItemIds.WILLOW_PLANKS, Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(WILLOW_PLANKS_COLOR));
	public static final Block WILLOW_STAIRS = Blocks.registerStair(WWBlockItemIds.WILLOW_STAIRS, WILLOW_PLANKS);
	public static final Block WILLOW_FENCE_GATE = Blocks.register(WWBlockItemIds.WILLOW_FENCE_GATE,
		properties -> new FenceGateBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final Block WILLOW_SLAB = Blocks.registerSlab(WWBlockItemIds.WILLOW_SLAB, WILLOW_PLANKS);
	public static final Block WILLOW_PRESSURE_PLATE = Blocks.register(WWBlockItemIds.WILLOW_PRESSURE_PLATE,
		properties -> new PressurePlateBlock(WILLOW_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final Block WILLOW_BUTTON = Blocks.register(WWBlockItemIds.WILLOW_BUTTON,
		properties -> new ButtonBlock(WILLOW_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final Block WILLOW_DOOR = Blocks.register(WWBlockItemIds.WILLOW_DOOR,
		properties -> new DoorBlock(WILLOW_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final Block WILLOW_TRAPDOOR = Blocks.register(WWBlockItemIds.WILLOW_TRAPDOOR,
		properties -> new TrapDoorBlock(WILLOW_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final Block WILLOW_FENCE = Blocks.register(WWBlockItemIds.WILLOW_FENCE,
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final Block WILLOW_LOG = Blocks.register(WWBlockItemIds.WILLOW_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(WILLOW_PLANKS_COLOR, WILLOW_BARK_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_WILLOW_LOG = Blocks.register(WWBlockItemIds.STRIPPED_WILLOW_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(WILLOW_PLANKS_COLOR, WILLOW_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_HOLLOWED_WILLOW_LOG = Blocks.register(WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG,
		HollowedLogBlock::new,
		strippedHollowedLogProperties(WILLOW_PLANKS_COLOR)
	);
	public static final Block WILLOW_WOOD = Blocks.register(WWBlockItemIds.WILLOW_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(WILLOW_BARK_COLOR)
	);
	public static final Block STRIPPED_WILLOW_WOOD = Blocks.register(WWBlockItemIds.STRIPPED_WILLOW_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final Block WILLOW_SIGN = Blocks.register(WWBlockItemIds.WILLOW_SIGN,
		properties -> new StandingSignBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(WILLOW_LOG.defaultMapColor())
	);
	public static final Block WILLOW_WALL_SIGN = Blocks.register(WWBlockIds.WILLOW_WALL_SIGN,
		properties -> new WallSignBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(WILLOW_LOG.defaultMapColor())
			.overrideDescription(WILLOW_SIGN.getDescriptionId())
			.overrideLootTable(WILLOW_SIGN.getLootTable())
	);
	public static final Block WILLOW_HANGING_SIGN = Blocks.register(WWBlockItemIds.WILLOW_HANGING_SIGN,
		properties -> new CeilingHangingSignBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(WILLOW_LOG.defaultMapColor())
	);
	public static final Block WILLOW_WALL_HANGING_SIGN = Blocks.register(WWBlockIds.WILLOW_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(WILLOW_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(WILLOW_LOG.defaultMapColor())
			.overrideDescription(WILLOW_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(WILLOW_HANGING_SIGN.getLootTable())
	);
	public static final Block WILLOW_SHELF = Blocks.register(WWBlockItemIds.WILLOW_SHELF,
		ShelfBlock::new,
		Properties.of()
			.mapColor(WILLOW_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
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

	// CYPRESS
	public static final Block CYPRESS_PLANKS = Blocks.register(WWBlockItemIds.CYPRESS_PLANKS, Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(CYPRESS_PLANKS_COLOR));
	public static final Block CYPRESS_STAIRS = Blocks.registerStair(WWBlockItemIds.CYPRESS_STAIRS, CYPRESS_PLANKS);
	public static final Block CYPRESS_FENCE_GATE = Blocks.register(WWBlockItemIds.CYPRESS_FENCE_GATE,
		properties -> new FenceGateBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_SLAB = Blocks.registerSlab(WWBlockItemIds.CYPRESS_SLAB, CYPRESS_PLANKS);
	public static final Block CYPRESS_PRESSURE_PLATE = Blocks.register(WWBlockItemIds.CYPRESS_PRESSURE_PLATE,
		properties -> new PressurePlateBlock(CYPRESS_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_BUTTON = Blocks.register(WWBlockItemIds.CYPRESS_BUTTON,
		properties -> new ButtonBlock(CYPRESS_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final Block CYPRESS_DOOR = Blocks.register(WWBlockItemIds.CYPRESS_DOOR,
		properties -> new DoorBlock(CYPRESS_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_TRAPDOOR = Blocks.register(WWBlockItemIds.CYPRESS_TRAPDOOR,
		properties -> new TrapDoorBlock(CYPRESS_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_FENCE = Blocks.register(WWBlockItemIds.CYPRESS_FENCE,
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_LOG = Blocks.register(WWBlockItemIds.CYPRESS_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_CYPRESS_LOG = Blocks.register(WWBlockItemIds.STRIPPED_CYPRESS_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(CYPRESS_PLANKS_COLOR, CYPRESS_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_HOLLOWED_CYPRESS_LOG = Blocks.register(WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG,
		HollowedLogBlock::new,
		strippedHollowedLogProperties(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_WOOD = Blocks.register(WWBlockItemIds.CYPRESS_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(CYPRESS_BARK_COLOR)
	);
	public static final Block STRIPPED_CYPRESS_WOOD = Blocks.register(WWBlockItemIds.STRIPPED_CYPRESS_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final Block CYPRESS_SIGN = Blocks.register(WWBlockItemIds.CYPRESS_SIGN,
		properties -> new StandingSignBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
	);
	public static final Block CYPRESS_WALL_SIGN = Blocks.register(WWBlockIds.CYPRESS_WALL_SIGN,
		properties -> new WallSignBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
			.overrideDescription(CYPRESS_SIGN.getDescriptionId())
			.overrideLootTable(CYPRESS_SIGN.getLootTable())
	);
	public static final Block CYPRESS_HANGING_SIGN = Blocks.register(WWBlockItemIds.CYPRESS_HANGING_SIGN,
		properties -> new CeilingHangingSignBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
	);
	public static final Block CYPRESS_WALL_HANGING_SIGN = Blocks.register(WWBlockIds.CYPRESS_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(CYPRESS_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.defaultMapColor())
			.overrideDescription(CYPRESS_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(CYPRESS_HANGING_SIGN.getLootTable())
	);
	public static final Block CYPRESS_SHELF = Blocks.register(WWBlockItemIds.CYPRESS_SHELF,
		ShelfBlock::new,
		Properties.of()
			.mapColor(CYPRESS_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
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

	// PALM
	public static final Block PALM_PLANKS = Blocks.register(WWBlockItemIds.PALM_PLANKS, Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(PALM_PLANKS_COLOR));
	public static final Block PALM_STAIRS = Blocks.registerStair(WWBlockItemIds.PALM_STAIRS, PALM_PLANKS);
	public static final Block PALM_FENCE_GATE = Blocks.register(WWBlockItemIds.PALM_FENCE_GATE,
		properties -> new FenceGateBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_SLAB = Blocks.registerSlab(WWBlockItemIds.PALM_SLAB, PALM_PLANKS);
	public static final Block PALM_PRESSURE_PLATE = Blocks.register(WWBlockItemIds.PALM_PRESSURE_PLATE,
		properties -> new PressurePlateBlock(PALM_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_BUTTON = Blocks.register(WWBlockItemIds.PALM_BUTTON,
		properties -> new ButtonBlock(PALM_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final Block PALM_DOOR = Blocks.register(WWBlockItemIds.PALM_DOOR,
		properties -> new DoorBlock(PALM_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_TRAPDOOR = Blocks.register(WWBlockItemIds.PALM_TRAPDOOR,
		properties -> new TrapDoorBlock(PALM_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_FENCE = Blocks.register(WWBlockItemIds.PALM_FENCE,
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_LOG = Blocks.register(WWBlockItemIds.PALM_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(PALM_PLANKS_COLOR, PALM_BARK_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_PALM_LOG = Blocks.register(WWBlockItemIds.STRIPPED_PALM_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(PALM_PLANKS_COLOR, PALM_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_HOLLOWED_PALM_LOG = Blocks.register(WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG,
		HollowedLogBlock::new,
		strippedHollowedLogProperties(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_WOOD = Blocks.register(WWBlockItemIds.PALM_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(PALM_BARK_COLOR)
	);
	public static final Block STRIPPED_PALM_WOOD = Blocks.register(WWBlockItemIds.STRIPPED_PALM_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(PALM_PLANKS_COLOR)
	);
	public static final Block PALM_SIGN = Blocks.register(WWBlockItemIds.PALM_SIGN,
		properties -> new StandingSignBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
	);
	public static final Block PALM_WALL_SIGN = Blocks.register(WWBlockIds.PALM_WALL_SIGN,
		properties -> new WallSignBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
			.overrideDescription(PALM_SIGN.getDescriptionId())
			.overrideLootTable(PALM_SIGN.getLootTable())
	);
	public static final Block PALM_HANGING_SIGN = Blocks.register(WWBlockItemIds.PALM_HANGING_SIGN,
		properties -> new CeilingHangingSignBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
	);
	public static final Block PALM_WALL_HANGING_SIGN = Blocks.register(WWBlockIds.PALM_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(PALM_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(PALM_LOG.defaultMapColor())
			.overrideDescription(PALM_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(PALM_HANGING_SIGN.getLootTable())
	);
	public static final Block PALM_SHELF = Blocks.register(WWBlockItemIds.PALM_SHELF,
		ShelfBlock::new,
		Properties.of()
			.mapColor(PALM_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
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

	// MAPLE
	public static final Block MAPLE_PLANKS = Blocks.register(WWBlockItemIds.MAPLE_PLANKS, Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MAPLE_PLANKS_COLOR));
	public static final Block MAPLE_STAIRS = Blocks.registerStair(WWBlockItemIds.MAPLE_STAIRS, MAPLE_PLANKS);
	public static final Block MAPLE_FENCE_GATE = Blocks.register(WWBlockItemIds.MAPLE_FENCE_GATE,
		properties -> new FenceGateBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_SLAB = Blocks.registerSlab(WWBlockItemIds.MAPLE_SLAB, MAPLE_PLANKS);
	public static final Block MAPLE_PRESSURE_PLATE = Blocks.register(WWBlockItemIds.MAPLE_PRESSURE_PLATE,
		properties -> new PressurePlateBlock(MAPLE_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_BUTTON = Blocks.register(WWBlockItemIds.MAPLE_BUTTON,
		properties -> new ButtonBlock(MAPLE_SET, 30, properties),
		Blocks.buttonProperties()
	);
	public static final Block MAPLE_DOOR = Blocks.register(WWBlockItemIds.MAPLE_DOOR,
		properties -> new DoorBlock(MAPLE_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_TRAPDOOR = Blocks.register(WWBlockItemIds.MAPLE_TRAPDOOR,
		properties -> new TrapDoorBlock(MAPLE_SET, properties),
		Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_FENCE = Blocks.register(WWBlockItemIds.MAPLE_FENCE,
		FenceBlock::new,
		Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_LOG = Blocks.register(WWBlockItemIds.MAPLE_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_MAPLE_LOG = Blocks.register(WWBlockItemIds.STRIPPED_MAPLE_LOG,
		RotatedPillarBlock::new,
		Blocks.logProperties(MAPLE_PLANKS_COLOR, MAPLE_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final Block STRIPPED_HOLLOWED_MAPLE_LOG = Blocks.register(WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG,
		HollowedLogBlock::new,
		strippedHollowedLogProperties(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_WOOD = Blocks.register(WWBlockItemIds.MAPLE_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MAPLE_BARK_COLOR)
	);
	public static final Block STRIPPED_MAPLE_WOOD = Blocks.register(WWBlockItemIds.STRIPPED_MAPLE_WOOD,
		RotatedPillarBlock::new,
		Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final Block MAPLE_SIGN = Blocks.register(WWBlockItemIds.MAPLE_SIGN,
		properties -> new StandingSignBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
	);
	public static final Block MAPLE_WALL_SIGN = Blocks.register(WWBlockIds.MAPLE_WALL_SIGN,
		properties -> new WallSignBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
			.overrideDescription(MAPLE_SIGN.getDescriptionId())
			.overrideLootTable(MAPLE_SIGN.getLootTable())
	);
	public static final Block MAPLE_HANGING_SIGN = Blocks.register(WWBlockItemIds.MAPLE_HANGING_SIGN,
		properties -> new CeilingHangingSignBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
	);
	public static final Block MAPLE_WALL_HANGING_SIGN = Blocks.register(WWBlockIds.MAPLE_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(MAPLE_WOOD_TYPE, properties),
		Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(MAPLE_LOG.defaultMapColor())
			.overrideDescription(MAPLE_HANGING_SIGN.getDescriptionId())
			.overrideLootTable(MAPLE_HANGING_SIGN.getLootTable())
	);
	public static final Block MAPLE_SHELF = Blocks.register(WWBlockItemIds.MAPLE_SHELF,
		ShelfBlock::new,
		Properties.of()
			.mapColor(MAPLE_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
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

	// ICE
	public static final Block FRAGILE_ICE = Blocks.register(WWBlockItemIds.FRAGILE_ICE,
		FragileIceBlock::new,
		Properties.ofFullCopy(Blocks.ICE).strength(0.2F).pushReaction(PushReaction.DESTROY)
	);
	public static final Block ICICLE = Blocks.register(WWBlockItemIds.ICICLE,
		properties -> new IcicleBlock(FRAGILE_ICE.defaultBlockState(), properties),
		Properties.of().mapColor(MapColor.ICE)
			.forceSolidOn()
			.friction(0.98F)
			.randomTicks()
			.strength(0.2F)
			.sound(SoundType.GLASS)
			.noOcclusion()
			.dynamicShape()
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY)
			.isRedstoneConductor(Blocks::never)
	);

	// FROGLIGHT GOOP
	public static final Block OCHRE_FROGLIGHT_GOOP_BODY = registerFroglightGoopBody(WWBlockIds.OCHRE_FROGLIGHT_GOOP_BODY, FroglightType.OCHRE, Blocks.OCHRE_FROGLIGHT);
	public static final Block OCHRE_FROGLIGHT_GOOP = registerFroglightGoop(WWBlockItemIds.OCHRE_FROGLIGHT_GOOP, FroglightType.OCHRE, Blocks.OCHRE_FROGLIGHT);
	public static final Block VERDANT_FROGLIGHT_GOOP_BODY = registerFroglightGoopBody(WWBlockIds.VERDANT_FROGLIGHT_GOOP_BODY, FroglightType.VERDANT, Blocks.VERDANT_FROGLIGHT);
	public static final Block VERDANT_FROGLIGHT_GOOP = registerFroglightGoop(WWBlockItemIds.VERDANT_FROGLIGHT_GOOP, FroglightType.VERDANT, Blocks.VERDANT_FROGLIGHT);
	public static final Block PEARLESCENT_FROGLIGHT_GOOP_BODY = registerFroglightGoopBody(WWBlockIds.PEARLESCENT_FROGLIGHT_GOOP_BODY, FroglightType.PEARLESCENT, Blocks.PEARLESCENT_FROGLIGHT);
	public static final Block PEARLESCENT_FROGLIGHT_GOOP = registerFroglightGoop(WWBlockItemIds.PEARLESCENT_FROGLIGHT_GOOP, FroglightType.PEARLESCENT, Blocks.PEARLESCENT_FROGLIGHT);

	public static Block registerFroglightGoopBody(ResourceKey<Block> id, FroglightType froglightType, Block froglightBlock) {
		return Blocks.register(id, properties -> new FroglightGoopBodyBlock(froglightType, properties), froglightGoopProperties(froglightBlock));
	}

	public static Block registerFroglightGoop(BlockItemId id, FroglightType froglightType, Block froglightBlock) {
		return Blocks.register(id, properties -> new FroglightGoopBlock(froglightType, properties), froglightGoopProperties(froglightBlock));
	}

	private static BlockBehaviour.Properties froglightGoopProperties(Block froglightBlock) {
		return BlockBehaviour.Properties.of()
			.mapColor(froglightBlock.defaultMapColor())
			.randomTicks()
			.instabreak()
			.noCollision()
			.sound(SoundType.FROGLIGHT)
			.lightLevel(state -> 5)
			.pushReaction(PushReaction.DESTROY);
	}

	private WWBlocks() {
		throw new UnsupportedOperationException("WWBlocks contains only static declarations.");
	}

	public static void init() {}

	public static Block registerFlowerPot(ResourceKey<Block> id, Block potted) {
		return Blocks.register(id, properties -> new FlowerPotBlock(potted, properties), Blocks.flowerPotProperties());
	}

	public static void registerBlockProperties() {
		registerDispenses();

		var sign = BlockEntityTypes.SIGN;
		sign.addValidBlock(BAOBAB_SIGN);
		sign.addValidBlock(BAOBAB_WALL_SIGN);
		sign.addValidBlock(WILLOW_SIGN);
		sign.addValidBlock(WILLOW_WALL_SIGN);
		sign.addValidBlock(CYPRESS_SIGN);
		sign.addValidBlock(CYPRESS_WALL_SIGN);
		sign.addValidBlock(PALM_SIGN);
		sign.addValidBlock(PALM_WALL_SIGN);
		sign.addValidBlock(MAPLE_SIGN);
		sign.addValidBlock(MAPLE_WALL_SIGN);

		var hangingSign = BlockEntityTypes.HANGING_SIGN;
		hangingSign.addValidBlock(BAOBAB_HANGING_SIGN);
		hangingSign.addValidBlock(BAOBAB_WALL_HANGING_SIGN);
		hangingSign.addValidBlock(WILLOW_HANGING_SIGN);
		hangingSign.addValidBlock(WILLOW_WALL_HANGING_SIGN);
		hangingSign.addValidBlock(CYPRESS_HANGING_SIGN);
		hangingSign.addValidBlock(CYPRESS_WALL_HANGING_SIGN);
		hangingSign.addValidBlock(PALM_HANGING_SIGN);
		hangingSign.addValidBlock(PALM_WALL_HANGING_SIGN);
		hangingSign.addValidBlock(MAPLE_HANGING_SIGN);
		hangingSign.addValidBlock(MAPLE_WALL_HANGING_SIGN);

		var shelf = (FabricBlockEntityType) BlockEntityTypes.SHELF;
		shelf.addValidBlock(BAOBAB_SHELF);
		shelf.addValidBlock(WILLOW_SHELF);
		shelf.addValidBlock(CYPRESS_SHELF);
		shelf.addValidBlock(PALM_SHELF);
		shelf.addValidBlock(MAPLE_SHELF);

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
			public ItemStack execute(BlockSource source, ItemStack stack) {
				final Level level = source.level();
				final Direction direction = source.state().getValue(DispenserBlock.FACING);
				final Vec3 position = source.center().add(direction.getStepX(), direction.getStepY(), direction.getStepZ());
				final Tumbleweed tumbleweed = new Tumbleweed(WWEntityTypes.TUMBLEWEED, level);
				final Vec3 vec3 = new Vec3(direction.getStepX(), direction.getStepY() + 0.1D, direction.getStepZ())
					.normalize()
					.add(
						level.getRandom().triangle(0D, 0.0172275D * 6D),
						level.getRandom().triangle(0D, 0.0172275D * 6D),
						level.getRandom().triangle(0D, 0.0172275D * 6D)
					).scale(1.1D);
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
		CompostableRegistry.INSTANCE.add(CARNATION, 0.65F);
		CompostableRegistry.INSTANCE.add(CATTAIL, 0.65F);
		CompostableRegistry.INSTANCE.add(DATURA, 0.65F);
		CompostableRegistry.INSTANCE.add(MILKWEED, 0.65F);
		CompostableRegistry.INSTANCE.add(MARIGOLD, 0.3F);
		CompostableRegistry.INSTANCE.add(LANTANAS, 0.3F);
		CompostableRegistry.INSTANCE.add(PHLOX, 0.3F);
		CompostableRegistry.INSTANCE.add(SEEDING_DANDELION, 0.65F);
		CompostableRegistry.INSTANCE.add(FLOWERING_LILY_PAD, 0.65F);
		CompostableRegistry.INSTANCE.add(BROWN_SHELF_FUNGI, 0.65F);
		CompostableRegistry.INSTANCE.add(RED_SHELF_FUNGI, 0.65F);
		CompostableRegistry.INSTANCE.add(WILLOW_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(PALM_FRONDS, 0.3F);
		CompostableRegistry.INSTANCE.add(YELLOW_MAPLE_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(ORANGE_MAPLE_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(RED_MAPLE_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(WILLOW_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(BAOBAB_NUT, 0.3F);
		CompostableRegistry.INSTANCE.add(YELLOW_MAPLE_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(ORANGE_MAPLE_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(RED_MAPLE_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(WWItems.COCONUT, 0.65F);
		CompostableRegistry.INSTANCE.add(WWItems.SPLIT_COCONUT, 0.3F);
		CompostableRegistry.INSTANCE.add(RED_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(YELLOW_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(WHITE_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(PINK_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(PURPLE_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(ALGAE, 0.3F);
		CompostableRegistry.INSTANCE.add(PLANKTON, 0.3F);
		CompostableRegistry.INSTANCE.add(MYCELIUM_GROWTH, 0.3F);
		CompostableRegistry.INSTANCE.add(SHRUB, 0.65F);
		CompostableRegistry.INSTANCE.add(TUMBLEWEED_PLANT, 0.5F);
		CompostableRegistry.INSTANCE.add(TUMBLEWEED, 0.3F);
		CompostableRegistry.INSTANCE.add(WWItems.PRICKLY_PEAR, 0.5F);
		CompostableRegistry.INSTANCE.add(WWItems.PEELED_PRICKLY_PEAR, 0.5F);
		CompostableRegistry.INSTANCE.add(ACACIA_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(AZALEA_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(BAOBAB_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(BIRCH_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(CHERRY_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(CYPRESS_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(DARK_OAK_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(JUNGLE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(MANGROVE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(PALE_OAK_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(PALM_FROND_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(SPRUCE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(WILLOW_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(YELLOW_MAPLE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(ORANGE_MAPLE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(RED_MAPLE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(CLOVERS, 0.3F);
		CompostableRegistry.INSTANCE.add(FROZEN_SHORT_GRASS, 0.3F);
		CompostableRegistry.INSTANCE.add(FROZEN_TALL_GRASS, 0.5F);
		CompostableRegistry.INSTANCE.add(FROZEN_FERN, 0.65F);
		CompostableRegistry.INSTANCE.add(FROZEN_LARGE_FERN, 0.65F);
		CompostableRegistry.INSTANCE.add(FROZEN_BUSH, 0.3F);
		CompostableRegistry.INSTANCE.add(AUBURN_MOSS_BLOCK, 0.65F);
		CompostableRegistry.INSTANCE.add(AUBURN_MOSS_CARPET, 0.3F);
		CompostableRegistry.INSTANCE.add(AUBURN_CREEPING_MOSS, 0.3F);
	}

	private static void registerFlammability() {
		WWConstants.logWithModId("Registering Flammability for", WWConstants.UNSTABLE_LOGGING);
		final var flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableBlockRegistry.add(POLLEN, 60, 100);
		flammableBlockRegistry.add(SEEDING_DANDELION, 60, 100);
		flammableBlockRegistry.add(CARNATION, 60, 100);
		flammableBlockRegistry.add(CATTAIL, 60, 100);
		flammableBlockRegistry.add(DATURA, 60, 100);
		flammableBlockRegistry.add(MILKWEED, 60, 100);
		flammableBlockRegistry.add(MARIGOLD, 60, 100);
		flammableBlockRegistry.add(RED_HIBISCUS, 60, 100);
		flammableBlockRegistry.add(YELLOW_HIBISCUS, 60, 100);
		flammableBlockRegistry.add(WHITE_HIBISCUS, 60, 100);
		flammableBlockRegistry.add(PINK_HIBISCUS, 60, 100);
		flammableBlockRegistry.add(PURPLE_HIBISCUS, 60, 100);
		flammableBlockRegistry.add(TUMBLEWEED, 60, 100);
		flammableBlockRegistry.add(TUMBLEWEED_PLANT, 60, 100);
		flammableBlockRegistry.add(SHRUB, 40, 90);
		flammableBlockRegistry.add(MYCELIUM_GROWTH, 60, 100);
		flammableBlockRegistry.add(LANTANAS, 60, 100);
		flammableBlockRegistry.add(PHLOX, 60, 100);
		flammableBlockRegistry.add(CLOVERS, 60, 100);

		flammableBlockRegistry.add(FROZEN_SHORT_GRASS, 60, 100);
		flammableBlockRegistry.add(FROZEN_TALL_GRASS, 60, 100);
		flammableBlockRegistry.add(FROZEN_FERN, 60, 100);
		flammableBlockRegistry.add(FROZEN_LARGE_FERN, 60, 100);
		flammableBlockRegistry.add(FROZEN_BUSH, 60, 100);

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
		flammableBlockRegistry.add(BAOBAB_LEAVES, 30, 60);
		flammableBlockRegistry.add(BAOBAB_BUTTON, 5, 20);
		flammableBlockRegistry.add(BAOBAB_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_WALL_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(BAOBAB_SHELF, 30, 20);

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
		flammableBlockRegistry.add(WILLOW_LEAVES, 30, 60);
		flammableBlockRegistry.add(WILLOW_BUTTON, 5, 20);
		flammableBlockRegistry.add(WILLOW_SIGN, 5, 20);
		flammableBlockRegistry.add(WILLOW_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(WILLOW_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(WILLOW_WALL_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(WILLOW_SHELF, 30, 20);

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
		flammableBlockRegistry.add(CYPRESS_LEAVES, 30, 60);
		flammableBlockRegistry.add(CYPRESS_BUTTON, 5, 20);
		flammableBlockRegistry.add(CYPRESS_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_WALL_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(CYPRESS_SHELF, 30, 20);

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
		flammableBlockRegistry.add(PALM_FRONDS, 30, 60);
		flammableBlockRegistry.add(PALM_BUTTON, 5, 20);
		flammableBlockRegistry.add(PALM_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_WALL_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(PALM_SHELF, 30, 20);

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
		flammableBlockRegistry.add(YELLOW_MAPLE_LEAVES, 30, 60);
		flammableBlockRegistry.add(ORANGE_MAPLE_LEAVES, 30, 60);
		flammableBlockRegistry.add(RED_MAPLE_LEAVES, 30, 60);
		flammableBlockRegistry.add(YELLOW_MAPLE_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(ORANGE_MAPLE_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(RED_MAPLE_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(MAPLE_BUTTON, 5, 20);
		flammableBlockRegistry.add(MAPLE_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_WALL_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_WALL_HANGING_SIGN, 5, 20);
		flammableBlockRegistry.add(MAPLE_SHELF, 30, 20);

		flammableBlockRegistry.add(ACACIA_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(AZALEA_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(BAOBAB_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(BIRCH_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(CHERRY_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(CYPRESS_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(DARK_OAK_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(JUNGLE_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(MANGROVE_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(PALE_OAK_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(PALM_FROND_LITTER, 60, 100);
		flammableBlockRegistry.add(SPRUCE_LEAF_LITTER, 60, 100);
		flammableBlockRegistry.add(WILLOW_LEAF_LITTER, 60, 100);
	}

	private static void registerFuels() {
		WWConstants.logWithModId("Registering Fuels for", WWConstants.UNSTABLE_LOGGING);

		FuelValueEvents.BUILD.register((builder, context) -> {
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
			builder.add(YELLOW_MAPLE_SAPLING.asItem(), 100);
			builder.add(ORANGE_MAPLE_SAPLING.asItem(), 100);
			builder.add(RED_MAPLE_SAPLING.asItem(), 100);

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

			builder.add(ACACIA_LEAF_LITTER, 100);
			builder.add(AZALEA_LEAF_LITTER, 100);
			builder.add(BAOBAB_LEAF_LITTER, 100);
			builder.add(BIRCH_LEAF_LITTER, 100);
			builder.add(CHERRY_LEAF_LITTER, 100);
			builder.add(CYPRESS_LEAF_LITTER, 100);
			builder.add(DARK_OAK_LEAF_LITTER, 100);
			builder.add(JUNGLE_LEAF_LITTER, 100);
			builder.add(MANGROVE_LEAF_LITTER, 100);
			builder.add(PALE_OAK_LEAF_LITTER, 100);
			builder.add(PALM_FROND_LITTER, 100);
			builder.add(SPRUCE_LEAF_LITTER, 100);
			builder.add(WILLOW_LEAF_LITTER, 100);

			builder.add(TUMBLEWEED.asItem(), 150);
			builder.add(TUMBLEWEED_PLANT.asItem(), 150);

			builder.add(SHRUB.asItem(), 150);
		});
	}

	private static void registerBonemeal() {
		BoneMealApi.register(
			Blocks.LILY_PAD,
			new BoneMealApi.BoneMealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return WWBlockConfig.BONE_MEAL_LILY_PADS.get();
				}

				@Override
				public void performBoneMeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					level.setBlock(pos, FLOWERING_LILY_PAD.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		);

		BoneMealApi.register(
			Blocks.DANDELION,
			new BoneMealApi.BoneMealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return WWBlockConfig.BONE_MEAL_DANDELIONS.get();
				}

				@Override
				public void performBoneMeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					level.setBlock(pos, SEEDING_DANDELION.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		);

		BoneMealApi.register(
			Blocks.MYCELIUM,
			new BoneMealApi.BoneMealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return level.getBlockState(pos.above()).isAir();
				}

				@Override
				public void performBoneMeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					final BlockPos blockPos = pos.above();
					final Optional<Holder.Reference<PlacedFeature>> optional = level.registryAccess()
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
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.JUNGLE_LOG, HOLLOWED_JUNGLE_LOG);
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
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_JUNGLE_LOG, STRIPPED_HOLLOWED_JUNGLE_LOG);
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
