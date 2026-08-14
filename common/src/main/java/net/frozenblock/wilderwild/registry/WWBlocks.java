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

import com.google.common.base.Suppliers;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.frozenblock.lib.block.api.blockentity.BlockEntityTypeExtension;
import net.frozenblock.lib.block.api.fire.FlammableBlockRegistry;
import net.frozenblock.lib.block.api.registry.BlockSetTypeBuilder;
import net.frozenblock.lib.block.api.registry.WoodTypeBuilder;
import net.frozenblock.lib.block.api.storage.hopper.HopperApi;
import net.frozenblock.lib.item.api.bonemeal.BoneMealApi;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredBlock;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
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
import net.frozenblock.wilderwild.block.GeothermalVentBlock;
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
import net.frozenblock.wilderwild.block.ShrubBlock;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMoundBlock;
import net.frozenblock.wilderwild.block.TubeWormsBlock;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.block.WaterloggableSaplingBlock;
import net.frozenblock.wilderwild.block.WideFlowerBlock;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.block.impl.PoplarCollection;
import net.frozenblock.wilderwild.block.state.properties.FroglightType;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.data.worldgen.feature.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.levelgen.grower.WWTreeGrowers;
import net.frozenblock.wilderwild.references.WWBlockIds;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemId;
import net.minecraft.references.BlockItemIds;
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
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
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
	private static final FrozenDeferredRegister.Blocks REGISTER = FrozenDeferredRegister.createBlocks(WWConstants.MOD_ID);

	// BLOCK SET TYPES
	public static final BlockSetType BAOBAB_SET = BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA).register(WWConstants.id("baobab"));
	public static final BlockSetType WILLOW_SET = BlockSetTypeBuilder.copyOf(BlockSetType.SPRUCE).register(WWConstants.id("willow"));
	public static final BlockSetType CYPRESS_SET = BlockSetTypeBuilder.copyOf(BlockSetType.BIRCH).register(WWConstants.id("cypress"));
	public static final BlockSetType PALM_SET = BlockSetTypeBuilder.copyOf(BlockSetType.JUNGLE).register(WWConstants.id("palm"));

	// deferred so that WWSounds isn't init
	public static final Supplier<BlockSetType> MAPLE_SET = Suppliers.memoize(() -> BlockSetTypeBuilder.copyOf(BlockSetType.SPRUCE)
		.soundType(WWSoundTypes.MAPLE_WOOD)
		.doorCloseSound(WWSounds.BLOCK_MAPLE_WOOD_DOOR_CLOSE.get()).doorOpenSound(WWSounds.BLOCK_MAPLE_WOOD_DOOR_OPEN.get())
		.trapdoorCloseSound(WWSounds.BLOCK_MAPLE_WOOD_TRAPDOOR_CLOSE.get()).trapdoorOpenSound(WWSounds.BLOCK_MAPLE_WOOD_TRAPDOOR_OPEN.get())
		.pressurePlateClickOnSound(WWSounds.BLOCK_MAPLE_WOOD_PRESSURE_PLATE_CLICK_ON.get()).pressurePlateClickOffSound(WWSounds.BLOCK_MAPLE_WOOD_PRESSURE_PLATE_CLICK_OFF.get())
		.buttonClickOnSound(WWSounds.BLOCK_MAPLE_BUTTON_CLICK_ON.get()).buttonClickOffSound(WWSounds.BLOCK_MAPLE_BUTTON_CLICK_OFF.get())
		.register(WWConstants.id("maple")));
	// WOOD TYPES
	public static final WoodType BAOBAB_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.ACACIA).register(WWConstants.id("baobab"), BAOBAB_SET);
	public static final WoodType WILLOW_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.SPRUCE).register(WWConstants.id("willow"), WILLOW_SET);
	public static final WoodType CYPRESS_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.BIRCH).register(WWConstants.id("cypress"), CYPRESS_SET);
	public static final WoodType PALM_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.JUNGLE).register(WWConstants.id("palm"), PALM_SET);
	// deferred so that WWSounds isn't init
	public static final Supplier<WoodType> MAPLE_WOOD_TYPE = Suppliers.memoize(() -> WoodTypeBuilder.copyOf(WoodType.SPRUCE)
		.soundType(WWSoundTypes.MAPLE_WOOD)
		.fenceGateCloseSound(WWSounds.BLOCK_MAPLE_WOOD_FENCE_GATE_CLOSE.get()).fenceGateOpenSound(WWSounds.BLOCK_MAPLE_WOOD_FENCE_GATE_OPEN.get())
		.hangingSignSoundType(WWSoundTypes.MAPLE_WOOD_HANGING_SIGN)
		.register(WWConstants.id("maple"), MAPLE_SET.get()));
	// WOOD COLORS
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
	public static final FrozenDeferredBlock<Block> CHISELED_MUD_BRICKS = REGISTER.registerSimpleBlock(WWBlockItemIds.CHISELED_MUD_BRICKS.block(), () -> Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final FrozenDeferredBlock<Block> CRACKED_MUD_BRICKS = REGISTER.registerSimpleBlock(WWBlockItemIds.CRACKED_MUD_BRICKS.block(), () -> Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final FrozenDeferredBlock<Block> MOSSY_MUD_BRICKS = REGISTER.registerSimpleBlock(WWBlockItemIds.MOSSY_MUD_BRICKS.block(), () -> Properties.ofFullCopy(Blocks.MUD_BRICKS));
	public static final FrozenDeferredBlock<StairBlock> MOSSY_MUD_BRICK_STAIRS = REGISTER.registerStair(WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS, MOSSY_MUD_BRICKS);
	public static final FrozenDeferredBlock<SlabBlock> MOSSY_MUD_BRICK_SLAB = REGISTER.registerSlab(WWBlockItemIds.MOSSY_MUD_BRICK_SLAB, MOSSY_MUD_BRICKS);
	public static final FrozenDeferredBlock<WallBlock> MOSSY_MUD_BRICK_WALL = REGISTER.registerWall(WWBlockItemIds.MOSSY_MUD_BRICK_WALL, MOSSY_MUD_BRICKS);

	// SAND
	public static final FrozenDeferredBlock<ScorchedBlock> SCORCHED_SAND = registerScorchedSand(WWBlockItemIds.SCORCHED_SAND, Blocks.SAND);
	public static final FrozenDeferredBlock<ScorchedBlock> SCORCHED_RED_SAND = registerScorchedSand(WWBlockItemIds.SCORCHED_RED_SAND, Blocks.RED_SAND);

	public static FrozenDeferredBlock<ScorchedBlock> registerScorchedSand(BlockItemId id, Block base) {
		return REGISTER.registerBlock(
			id.block(),
			properties -> new ScorchedBlock(
				base.defaultBlockState(),
				true,
				SoundEvents.BRUSH_SAND,
				SoundEvents.BRUSH_SAND_COMPLETED,
				true,
				properties
			),
			() -> Properties.of()
				.strength(1.5F)
				.sound(WWSoundTypes.SCORCHED_SAND)
				.mapColor(base.defaultMapColor())
				.randomTicks()
		);
	}

	// SAPLINGS
	public static final FrozenDeferredBlock<BaobabNutBlock> BAOBAB_NUT = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_NUT.block(),
		properties -> new BaobabNutBlock(WWTreeGrowers.BAOBAB, properties),
		() -> Properties.ofFullCopy(Blocks.BAMBOO).sound(WWSoundTypes.BAOBAB_NUT)
	);
	public static final FrozenDeferredBlock<Block> POTTED_BAOBAB_NUT = registerFlowerPot(WWBlockIds.POTTED_BAOBAB_NUT, BAOBAB_NUT);

	public static final FrozenDeferredBlock<WaterloggableSaplingBlock> WILLOW_SAPLING = REGISTER.registerBlock(WWBlockItemIds.WILLOW_SAPLING.block(),
		properties -> new WaterloggableSaplingBlock(WWTreeGrowers.WILLOW, properties),
		() -> Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final FrozenDeferredBlock<Block> POTTED_WILLOW_SAPLING = registerFlowerPot(WWBlockIds.POTTED_WILLOW_SAPLING, WILLOW_SAPLING);

	public static final FrozenDeferredBlock<WaterloggableSaplingBlock> CYPRESS_SAPLING = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_SAPLING.block(),
		properties -> new WaterloggableSaplingBlock(WWTreeGrowers.CYPRESS, properties),
		() -> Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
	);
	public static final FrozenDeferredBlock<Block> POTTED_CYPRESS_SAPLING = registerFlowerPot(WWBlockIds.POTTED_CYPRESS_SAPLING, CYPRESS_SAPLING);

	public static final FrozenDeferredBlock<CoconutBlock> COCONUT = REGISTER.registerBlock(WWBlockItemIds.COCONUT.block(),
		properties -> new CoconutBlock(WWTreeGrowers.PALM, properties),
		() -> Properties.of().instabreak().randomTicks().sound(SoundType.STONE)
	);
	public static final FrozenDeferredBlock<Block> POTTED_COCONUT = registerFlowerPot(WWBlockIds.POTTED_COCONUT, COCONUT);

	public static final MapleCollection<FrozenDeferredBlock<SaplingBlock>> MAPLE_SAPLING = MapleCollection.zipMap(WWBlockItemIds.MAPLE_SAPLING, WWTreeGrowers.MAPLE,
		(id, treeGrower) -> REGISTER.registerBlock(id.block(),
			properties -> new SaplingBlock(treeGrower, properties),
			() -> Properties.ofFullCopy(Blocks.BIRCH_SAPLING)
		)
	);
	public static final MapleCollection<FrozenDeferredBlock<Block>> POTTED_MAPLE_SAPLING = MapleCollection.zipMap(WWBlockIds.POTTED_MAPLE_SAPLING, MAPLE_SAPLING,
		WWBlocks::registerFlowerPot
	);

	public static final PoplarCollection<Supplier<Block>> POPLAR_SAPLING = PoplarCollection.zipMap(WWBlockItemIds.POPLAR_SAPLING, WWTreeGrowers.POPLAR,
		(id, treeGrower) -> {
			if (id.equals(BlockItemIds.POPLAR_SAPLING)) return () -> Blocks.POPLAR_SAPLING;
			return REGISTER.registerBlock(id.block(),
				properties -> new SaplingBlock(treeGrower, properties),
				() -> Properties.ofFullCopy(Blocks.POPLAR_SAPLING)
			);
		}
	);
	public static final PoplarCollection<Supplier<Block>> POTTED_POPLAR_SAPLING = PoplarCollection.zipMap(WWBlockIds.POTTED_POPLAR_SAPLING, POPLAR_SAPLING,
		(id, sapling) -> {
			if (id.equals(BlockIds.POTTED_POPLAR_SAPLING)) return () -> Blocks.POTTED_POPLAR_SAPLING;
			return registerFlowerPot(id, sapling);
		}
	);

	// LEAVES
	public static final FrozenDeferredBlock<BaobabLeavesBlock> BAOBAB_LEAVES = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_LEAVES.block(),
		properties -> new BaobabLeavesBlock(0.01F, properties),
		() -> Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final FrozenDeferredBlock<TintedParticleLeavesBlock> WILLOW_LEAVES = REGISTER.registerBlock(WWBlockItemIds.WILLOW_LEAVES.block(),
		properties -> new TintedParticleLeavesBlock(0.01F, properties),
		() -> Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final FrozenDeferredBlock<TintedParticleLeavesBlock> CYPRESS_LEAVES = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_LEAVES.block(),
		properties -> new TintedParticleLeavesBlock(0.01F, properties),
		() -> Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final FrozenDeferredBlock<PalmFrondsBlock> PALM_FRONDS = REGISTER.registerBlock(WWBlockItemIds.PALM_FRONDS.block(),
		properties -> new PalmFrondsBlock(0.005F, properties),
		() -> Blocks.leavesProperties(SoundType.GRASS)
	);
	public static final MapleCollection<FrozenDeferredBlock<LeavesWithLitterBlock>> MAPLE_LEAVES = MapleCollection.zipMap(WWBlockItemIds.MAPLE_LEAVES, MapleCollection.MAP_COLORS,
		(id, mapColor) -> REGISTER.registerBlock(id.block(),
			properties -> new LeavesWithLitterBlock(AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties),
			() -> Blocks.leavesProperties(WWSoundTypes.MAPLE_LEAVES).mapColor(mapColor)
		)
	);

	// HOLLOWED LOGS
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_OAK_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_OAK_LOG,
		() -> hollowedLogProperties(MapColor.WOOD, MapColor.PODZOL)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_SPRUCE_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_SPRUCE_LOG,
		() -> hollowedLogProperties(MapColor.PODZOL, MapColor.COLOR_BROWN)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_BIRCH_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_BIRCH_LOG,
		() -> hollowedLogProperties(MapColor.SAND, MapColor.QUARTZ)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_JUNGLE_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_JUNGLE_LOG,
		() -> hollowedLogProperties(MapColor.DIRT, MapColor.PODZOL)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_ACACIA_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_ACACIA_LOG,
		() -> hollowedLogProperties(MapColor.COLOR_ORANGE, MapColor.STONE)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_DARK_OAK_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_DARK_OAK_LOG,
		() -> hollowedLogProperties(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_MANGROVE_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_MANGROVE_LOG,
		() -> hollowedLogProperties(MapColor.COLOR_RED, MapColor.PODZOL)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_CHERRY_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_CHERRY_LOG,
		() -> hollowedLogProperties(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, WWSoundTypes.HOLLOWED_CHERRY_LOG)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_PALE_OAK_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_PALE_OAK_LOG,
		() -> hollowedLogProperties(MapColor.QUARTZ, MapColor.STONE)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_POPLAR_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_POPLAR_LOG,
		() -> hollowedLogProperties(MapColor.COLOR_LIGHT_GRAY, MapColor.PODZOL)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_CRIMSON_STEM = registerHollowedLog(WWBlockItemIds.HOLLOWED_CRIMSON_STEM,
		() -> hollowedStemProperties(MapColor.CRIMSON_STEM)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_WARPED_STEM = registerHollowedLog(WWBlockItemIds.HOLLOWED_WARPED_STEM,
		() -> hollowedStemProperties(MapColor.WARPED_STEM)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_BAOBAB_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_BAOBAB_LOG,
		() -> hollowedLogProperties(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_WILLOW_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_WILLOW_LOG,
		() -> hollowedLogProperties(WILLOW_PLANKS_COLOR, WILLOW_BARK_COLOR)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_CYPRESS_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_CYPRESS_LOG,
		() -> hollowedLogProperties(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_PALM_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_PALM_LOG,
		() -> hollowedLogProperties(PALM_PLANKS_COLOR, PALM_BARK_COLOR)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> HOLLOWED_MAPLE_LOG = registerHollowedLog(WWBlockItemIds.HOLLOWED_MAPLE_LOG,
		() -> hollowedLogProperties(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR, WWSoundTypes.HOLLOWED_MAPLE_LOG)
	);

	// STRIPPED HOLLOWED LOGS
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_OAK_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_OAK_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_SPRUCE_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_SPRUCE_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_BIRCH_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_BIRCH_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_JUNGLE_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_JUNGLE_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_ACACIA_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_ACACIA_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_DARK_OAK_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_DARK_OAK_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_MANGROVE_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_MANGROVE_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_CHERRY_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_CHERRY_LOG.defaultMapColor(), WWSoundTypes.HOLLOWED_CHERRY_LOG)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_PALE_OAK_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_PALE_OAK_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_POPLAR_LOG = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_POPLAR_LOG,
		() -> strippedHollowedLogProperties(Blocks.STRIPPED_POPLAR_LOG.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_CRIMSON_STEM = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM,
		() -> strippedHollowedStemProperties(Blocks.STRIPPED_CRIMSON_STEM.defaultMapColor())
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_WARPED_STEM = registerHollowedLog(WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM,
		() -> strippedHollowedStemProperties(Blocks.STRIPPED_WARPED_STEM.defaultMapColor())
	);

	public static FrozenDeferredBlock<HollowedLogBlock> registerHollowedLog(BlockItemId id, Supplier<Properties> properties) {
		return REGISTER.registerBlock(id.block(), HollowedLogBlock::new, properties);
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
	public static final FrozenDeferredBlock<Block> ACACIA_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.ACACIA_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> AZALEA_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.AZALEA_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> BAOBAB_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.BAOBAB_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> BIRCH_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.BIRCH_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> CHERRY_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.CHERRY_LEAF_LITTER, () -> WWSoundTypes.CHERRY_LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> CYPRESS_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.CYPRESS_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> DARK_OAK_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.DARK_OAK_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> JUNGLE_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.JUNGLE_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> MANGROVE_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.MANGROVE_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> PALE_OAK_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.PALE_OAK_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> PALM_FROND_LITTER = registerLeafLitter(WWBlockItemIds.PALM_FROND_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> SPRUCE_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.SPRUCE_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final FrozenDeferredBlock<Block> WILLOW_LEAF_LITTER = registerLeafLitter(WWBlockItemIds.WILLOW_LEAF_LITTER, () -> SoundType.LEAF_LITTER);
	public static final MapleCollection<FrozenDeferredBlock<Block>> MAPLE_LEAF_LITTER = WWBlockItemIds.MAPLE_LEAF_LITTER.map(id -> registerLeafLitter(id, () -> WWSoundTypes.MAPLE_LEAF_LITTER));

	private static FrozenDeferredBlock<Block> registerLeafLitter(BlockItemId id, Supplier<SoundType> soundType) {
		return registerLeafLitter(id, soundType, null);
	}

	private static FrozenDeferredBlock<Block> registerLeafLitter(BlockItemId id, Supplier<SoundType> soundType, Consumer<Block> also) {
		return REGISTER.registerBlock(id.block(), LeafLitterBlock::new, () -> Properties.ofFullCopy(Blocks.LEAF_LITTER).sound(soundType.get()), also);
	}

	// SCULK
	public static final FrozenDeferredBlock<SculkStairBlock> SCULK_STAIRS = REGISTER.registerBlock(WWBlockItemIds.SCULK_STAIRS.block(),
		properties -> new SculkStairBlock(Blocks.SCULK.defaultBlockState(), properties),
		() -> Properties.ofFullCopy(Blocks.SCULK)
	);
	public static final FrozenDeferredBlock<SculkSlabBlock> SCULK_SLAB = REGISTER.registerBlock(WWBlockItemIds.SCULK_SLAB.block(), SculkSlabBlock::new, () -> Properties.ofFullCopy(Blocks.SCULK));
	public static final FrozenDeferredBlock<SculkWallBlock> SCULK_WALL = REGISTER.registerBlock(WWBlockItemIds.SCULK_WALL.block(), SculkWallBlock::new, () -> Properties.ofFullCopy(Blocks.SCULK));
	public static final FrozenDeferredBlock<OsseousSculkBlock> OSSEOUS_SCULK = REGISTER.registerBlock(WWBlockItemIds.OSSEOUS_SCULK.block(),
		OsseousSculkBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.SAND)
			.strength(2F)
			.sound(WWSoundTypes.OSSEOUS_SCULK)
	);
	public static final FrozenDeferredBlock<HangingTendrilBlock> HANGING_TENDRIL = REGISTER.registerBlock(WWBlockItemIds.HANGING_TENDRIL.block(),
		HangingTendrilBlock::new,
		() -> Properties.ofFullCopy(Blocks.SCULK_SENSOR)
			.strength(0.7F)
			.noCollision()
			.noOcclusion()
			.randomTicks()
			.lightLevel(state -> 1)
			.sound(WWSoundTypes.HANGING_TENDRIL)
			.emissiveRendering(HangingTendrilBlock::shouldHavePogLighting)
	);
	public static final FrozenDeferredBlock<EchoGlassBlock> ECHO_GLASS = REGISTER.registerBlock(WWBlockItemIds.ECHO_GLASS.block(),
		EchoGlassBlock::new,
		() -> Properties.ofFullCopy(Blocks.TINTED_GLASS)
			.strength(1F)
			.mapColor(MapColor.COLOR_CYAN)
			.noOcclusion()
			.randomTicks()
			.sound(WWSoundTypes.ECHO_GLASS)
	);

	// MESOGLEA
	public static final FrozenDeferredBlock<MesogleaBlock> PEARLESCENT_BLUE_MESOGLEA = registerMesoglea(WWBlockItemIds.PEARLESCENT_BLUE_MESOGLEA,
		MapColor.QUARTZ,
		() -> WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_BLUE.get(),
		() -> WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_BLUE.get(),
		() -> WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_BLUE.get(),
		() -> WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_BLUE.get(),
		() -> WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_BLUE.get(),
		true,
		Integer.parseInt("B9DAED", 16)
	);
	public static final FrozenDeferredBlock<MesogleaBlock> PEARLESCENT_PURPLE_MESOGLEA = registerMesoglea(WWBlockItemIds.PEARLESCENT_PURPLE_MESOGLEA,
		MapColor.COLOR_PURPLE,
		() -> WWParticleTypes.HANGING_MESOGLEA_PEARLESCENT_PURPLE.get(),
		() -> WWParticleTypes.MESOGLEA_BUBBLE_PEARLESCENT_PURPLE.get(),
		() -> WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PEARLESCENT_PURPLE.get(),
		() -> WWParticleTypes.CURRENT_DOWN_MESOGLEA_PEARLESCENT_PURPLE.get(),
		() -> WWParticleTypes.MESOGLEA_SPLASH_PEARLESCENT_PURPLE.get(),
		true,
		Integer.parseInt("C6B2F4", 16)
	);
	public static final FrozenDeferredBlock<MesogleaBlock> YELLOW_MESOGLEA = registerMesoglea(WWBlockItemIds.YELLOW_MESOGLEA,
		MapColor.COLOR_YELLOW,
		() -> WWParticleTypes.HANGING_MESOGLEA_YELLOW.get(),
		() -> WWParticleTypes.MESOGLEA_BUBBLE_YELLOW.get(),
		() -> WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_YELLOW.get(),
		() -> WWParticleTypes.CURRENT_DOWN_MESOGLEA_YELLOW.get(),
		() -> WWParticleTypes.MESOGLEA_SPLASH_YELLOW.get(),
		false,
		Integer.parseInt("FFC958", 16)
	);
	public static final FrozenDeferredBlock<MesogleaBlock> BLUE_MESOGLEA = registerMesoglea(WWBlockItemIds.BLUE_MESOGLEA,
		MapColor.COLOR_LIGHT_BLUE,
		() -> WWParticleTypes.HANGING_MESOGLEA_BLUE.get(),
		() -> WWParticleTypes.MESOGLEA_BUBBLE_BLUE.get(),
		() -> WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_BLUE.get(),
		() -> WWParticleTypes.CURRENT_DOWN_MESOGLEA_BLUE.get(),
		() -> WWParticleTypes.MESOGLEA_SPLASH_BLUE.get(),
		false,
		Integer.parseInt("596BFF", 16)
	);
	public static final FrozenDeferredBlock<MesogleaBlock> LIME_MESOGLEA = registerMesoglea(WWBlockItemIds.LIME_MESOGLEA,
		MapColor.COLOR_LIGHT_GREEN,
		() -> WWParticleTypes.HANGING_MESOGLEA_LIME.get(),
		() -> WWParticleTypes.MESOGLEA_BUBBLE_LIME.get(),
		() -> WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_LIME.get(),
		() -> WWParticleTypes.CURRENT_DOWN_MESOGLEA_LIME.get(),
		() -> WWParticleTypes.MESOGLEA_SPLASH_LIME.get(),
		false,
		Integer.parseInt("55EF1B", 16)
	);
	public static final FrozenDeferredBlock<MesogleaBlock> RED_MESOGLEA = registerMesoglea(WWBlockItemIds.RED_MESOGLEA,
		MapColor.COLOR_RED,
		() -> WWParticleTypes.HANGING_MESOGLEA_RED.get(),
		() -> WWParticleTypes.MESOGLEA_BUBBLE_RED.get(),
		() -> WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_RED.get(),
		() -> WWParticleTypes.CURRENT_DOWN_MESOGLEA_RED.get(),
		() -> WWParticleTypes.MESOGLEA_SPLASH_RED.get(),
		false,
		Integer.parseInt("FD3420", 16)
	);
	public static final FrozenDeferredBlock<MesogleaBlock> PINK_MESOGLEA = registerMesoglea(WWBlockItemIds.PINK_MESOGLEA,
		MapColor.COLOR_PINK,
		() -> WWParticleTypes.HANGING_MESOGLEA_PINK.get(),
		() -> WWParticleTypes.MESOGLEA_BUBBLE_PINK.get(),
		() -> WWParticleTypes.BUBBLE_COLUMN_UP_MESOGLEA_PINK.get(),
		() -> WWParticleTypes.CURRENT_DOWN_MESOGLEA_PINK.get(),
		() -> WWParticleTypes.MESOGLEA_SPLASH_PINK.get(),
		false,
		Integer.parseInt("ED87D1", 16)
	);

	public static FrozenDeferredBlock<MesogleaBlock> registerMesoglea(
		BlockItemId id,
		MapColor mapColor,
		Supplier<ParticleOptions> dripParticle,
		Supplier<ParticleOptions> bubbleParticle,
		Supplier<ParticleOptions> bubbleColumnUpParticle,
		Supplier<ParticleOptions> currentDownParticle,
		Supplier<ParticleOptions> splashParticle,
		boolean pearlescent,
		int waterFogColor
	) {
		return REGISTER.registerBlock(
			id.block(),
			properties -> new MesogleaBlock(
				pearlescent,
				new ColorRGBA(waterFogColor),
				dripParticle.get(),
				bubbleParticle.get(),
				bubbleColumnUpParticle.get(),
				currentDownParticle.get(),
				splashParticle.get(),
				properties
			),
			() -> Properties.of()
				.mapColor(mapColor)
				.noOcclusion()
				.strength(0.2F)
				.friction(0.8F)
				.lightLevel(state -> 7)
				.sound(WWSoundTypes.MESOGLEA)
				.isSuffocating(Blocks::never)
				.dynamicShape()
				.pushReaction(PushReaction.POPPED)
		);
	}

	// NEMATOCYST
	public static final FrozenDeferredBlock<NematocystBlock> PEARLESCENT_BLUE_NEMATOCYST = registerNematocyst(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST, MapColor.QUARTZ);
	public static final FrozenDeferredBlock<NematocystBlock> PEARLESCENT_PURPLE_NEMATOCYST = registerNematocyst(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST, MapColor.COLOR_PURPLE);
	public static final FrozenDeferredBlock<NematocystBlock> YELLOW_NEMATOCYST = registerNematocyst(WWBlockItemIds.YELLOW_NEMATOCYST, MapColor.COLOR_YELLOW);
	public static final FrozenDeferredBlock<NematocystBlock> BLUE_NEMATOCYST = registerNematocyst(WWBlockItemIds.BLUE_NEMATOCYST, MapColor.COLOR_BLUE);
	public static final FrozenDeferredBlock<NematocystBlock> LIME_NEMATOCYST = registerNematocyst(WWBlockItemIds.LIME_NEMATOCYST, MapColor.COLOR_LIGHT_GREEN);
	public static final FrozenDeferredBlock<NematocystBlock> RED_NEMATOCYST = registerNematocyst(WWBlockItemIds.RED_NEMATOCYST, MapColor.COLOR_RED);
	public static final FrozenDeferredBlock<NematocystBlock> PINK_NEMATOCYST = registerNematocyst(WWBlockItemIds.PINK_NEMATOCYST, MapColor.COLOR_PINK);

	public static FrozenDeferredBlock<NematocystBlock> registerNematocyst(BlockItemId id, MapColor mapColor) {
		return REGISTER.registerBlock(
			id.block(),
			NematocystBlock::new,
			() -> Properties.of()
				.mapColor(mapColor)
				.noCollision()
				.noOcclusion()
				.sound(WWSoundTypes.NEMATOCYST)
				.pushReaction(PushReaction.POPPED)
		);
	}

	// MISC
	public static final FrozenDeferredBlock<TermiteMoundBlock> TERMITE_MOUND = REGISTER.registerBlock(WWBlockItemIds.TERMITE_MOUND.block(),
		TermiteMoundBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.COLOR_BROWN)
			.strength(0.3F)
			.sound(WWSoundTypes.TERMITE_MOUND)
			.postProcess(Blocks::postProcessSelf)
			.randomTicks()
	);
	public static final FrozenDeferredBlock<StoneChestBlock> STONE_CHEST = REGISTER.registerBlock(WWBlockItemIds.STONE_CHEST.block(),
		properties -> new StoneChestBlock(() -> WWBlockEntityTypes.STONE_CHEST.get(), properties),
		() -> Properties.ofFullCopy(Blocks.CHEST)
			.mapColor(MapColor.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(2.5F)
			.requiresCorrectToolForDrops()
			.sound(SoundType.DEEPSLATE)
			.strength(35F, 12F)
	);
	public static final FrozenDeferredBlock<Block> NULL_BLOCK = REGISTER.registerSimpleBlock(WWBlockItemIds.NULL_BLOCK.block(), () -> Properties.ofFullCopy(Blocks.STONE).sound(WWSoundTypes.NULL_BLOCK));
	public static final FrozenDeferredBlock<DisplayLanternBlock> DISPLAY_LANTERN = REGISTER.registerBlock(WWBlockItemIds.DISPLAY_LANTERN.block(),
		DisplayLanternBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.METAL)
			.forceSolidOn()
			.strength(3.5F)
			.pushReaction(PushReaction.POPPED)
			.sound(SoundType.LANTERN)
			.lightLevel(state -> state.getValue(WWBlockStateProperties.DISPLAY_LIGHT))
	);

	// FLOWERS
	public static final FrozenDeferredBlock<Block> POTTED_CACTUS_FLOWER = registerFlowerPot(WWBlockIds.POTTED_CACTUS_FLOWER, () -> Blocks.CACTUS_FLOWER);

	public static final FrozenDeferredBlock<SeedingFlowerBlock> SEEDING_DANDELION = REGISTER.registerBlock(WWBlockItemIds.SEEDING_DANDELION.block(),
		properties -> new SeedingFlowerBlock(MobEffects.SLOW_FALLING, 12, Blocks.DANDELION, properties),
		() -> Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final FrozenDeferredBlock<Block> POTTED_SEEDING_DANDELION = registerFlowerPot(WWBlockIds.POTTED_SEEDING_DANDELION, SEEDING_DANDELION);

	public static final FrozenDeferredBlock<FlowerBlock> CARNATION = registerFlower(WWBlockItemIds.CARNATION, MobEffects.REGENERATION, 12F);
	public static final FrozenDeferredBlock<Block> POTTED_CARNATION = registerFlowerPot(WWBlockIds.POTTED_CARNATION, CARNATION);

	public static final FrozenDeferredBlock<FlowerBlock> MARIGOLD = registerFlower(WWBlockItemIds.MARIGOLD, MobEffects.RESISTANCE, 8F);
	public static final FrozenDeferredBlock<Block> POTTED_MARIGOLD = registerFlowerPot(WWBlockIds.POTTED_MARIGOLD, MARIGOLD);

	public static final FrozenDeferredBlock<FlowerBlock> PASQUEFLOWER = registerFlower(WWBlockItemIds.PASQUEFLOWER, MobEffects.NIGHT_VISION, 8F);
	public static final FrozenDeferredBlock<Block> POTTED_PASQUEFLOWER = registerFlowerPot(WWBlockIds.POTTED_PASQUEFLOWER, PASQUEFLOWER);

	public static final FrozenDeferredBlock<WideFlowerBlock> RED_HIBISCUS = registerHibiscus(WWBlockItemIds.RED_HIBISCUS);
	public static final FrozenDeferredBlock<Block> POTTED_RED_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_RED_HIBISCUS, RED_HIBISCUS);

	public static final FrozenDeferredBlock<WideFlowerBlock> YELLOW_HIBISCUS = registerHibiscus(WWBlockItemIds.YELLOW_HIBISCUS);
	public static final FrozenDeferredBlock<Block> POTTED_YELLOW_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_YELLOW_HIBISCUS, YELLOW_HIBISCUS);

	public static final FrozenDeferredBlock<WideFlowerBlock> WHITE_HIBISCUS = registerHibiscus(WWBlockItemIds.WHITE_HIBISCUS);
	public static final FrozenDeferredBlock<Block> POTTED_WHITE_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_WHITE_HIBISCUS, WHITE_HIBISCUS);

	public static final FrozenDeferredBlock<WideFlowerBlock> PINK_HIBISCUS = registerHibiscus(WWBlockItemIds.PINK_HIBISCUS);
	public static final FrozenDeferredBlock<Block> POTTED_PINK_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_PINK_HIBISCUS, PINK_HIBISCUS);

	public static final FrozenDeferredBlock<WideFlowerBlock> PURPLE_HIBISCUS = registerHibiscus(WWBlockItemIds.PURPLE_HIBISCUS);
	public static final FrozenDeferredBlock<Block> POTTED_PURPLE_HIBISCUS = registerFlowerPot(WWBlockIds.POTTED_PURPLE_HIBISCUS, PURPLE_HIBISCUS);

	public static FrozenDeferredBlock<FlowerBlock> registerFlower(BlockItemId id, Holder<MobEffect> suspiciousStewEffect, float effectSeconds) {
		return REGISTER.registerBlock(id.block(), properties -> new FlowerBlock(suspiciousStewEffect, effectSeconds, properties), () -> Properties.ofFullCopy(Blocks.DANDELION));
	}

	public static FrozenDeferredBlock<WideFlowerBlock> registerHibiscus(BlockItemId id) {
		return REGISTER.registerBlock(id.block(), properties -> new WideFlowerBlock(MobEffects.HUNGER, 8F, properties), () -> Properties.ofFullCopy(Blocks.DANDELION));
	}

	// FLOWERBEDS
	public static final FrozenDeferredBlock<Block> POTTED_PINK_PETALS = registerFlowerPot(WWBlockIds.POTTED_PINK_PETALS, () -> Blocks.PINK_PETALS);
	public static final FrozenDeferredBlock<Block> POTTED_WILDFLOWERS = registerFlowerPot(WWBlockIds.POTTED_WILDFLOWERS, () -> Blocks.WILDFLOWERS);

	public static final FrozenDeferredBlock<FlowerBedBlock> PHLOX = REGISTER.registerBlock(WWBlockItemIds.PHLOX.block(),
		properties -> new FlowerBedBlock(properties, 3),
		() -> Properties.ofFullCopy(Blocks.PINK_PETALS)
	);
	public static final FrozenDeferredBlock<Block> POTTED_PHLOX = registerFlowerPot(WWBlockIds.POTTED_PHLOX, PHLOX);

	public static final FrozenDeferredBlock<FlowerBedBlock> LANTANAS = REGISTER.registerBlock(WWBlockItemIds.LANTANAS,
		properties -> new FlowerBedBlock(properties, 3),
		() -> Properties.ofFullCopy(Blocks.PINK_PETALS)
	);
	public static final FrozenDeferredBlock<Block> POTTED_LANTANAS = registerFlowerPot(WWBlockIds.POTTED_LANTANAS, LANTANAS);

	public static final FrozenDeferredBlock<FlowerBedBlock> CLOVERS = REGISTER.registerBlock(WWBlockItemIds.CLOVERS.block(),
		properties -> new FlowerBedBlock(properties, 3),
		() -> Properties.ofFullCopy(Blocks.PINK_PETALS).sound(SoundType.GRASS).instabreak()
	);
	public static final FrozenDeferredBlock<Block> POTTED_CLOVERS = registerFlowerPot(WWBlockIds.POTTED_CLOVERS, CLOVERS);

	// TALL FLOWERS
	public static final FrozenDeferredBlock<TallFlowerBlock> DATURA = REGISTER.registerBlock(WWBlockItemIds.DATURA.block(), TallFlowerBlock::new, () -> Properties.ofFullCopy(Blocks.SUNFLOWER));
	public static final FrozenDeferredBlock<MilkweedBlock> MILKWEED = REGISTER.registerBlock(WWBlockItemIds.MILKWEED.block(), MilkweedBlock::new, () -> Properties.ofFullCopy(Blocks.SUNFLOWER).randomTicks());

	// VEGETATION
	public static final FrozenDeferredBlock<Block> POTTED_SHORT_GRASS = registerFlowerPot(WWBlockIds.POTTED_SHORT_GRASS, () -> Blocks.SHORT_GRASS);
	public static final FrozenDeferredBlock<Block> POTTED_BUSH = registerFlowerPot(WWBlockIds.POTTED_BUSH, () -> Blocks.BUSH);
	public static final FrozenDeferredBlock<Block> POTTED_FIREFLY_BUSH = registerFlowerPot(WWBlockIds.POTTED_FIREFLY_BUSH, () -> Blocks.FIREFLY_BUSH);
	public static final FrozenDeferredBlock<Block> POTTED_SHORT_DRY_GRASS = registerFlowerPot(WWBlockIds.POTTED_SHORT_DRY_GRASS, () -> Blocks.SHORT_DRY_GRASS);
	public static final FrozenDeferredBlock<Block> POTTED_TALL_DRY_GRASS = registerFlowerPot(WWBlockIds.POTTED_TALL_DRY_GRASS, () -> Blocks.TALL_DRY_GRASS);
	public static final FrozenDeferredBlock<Block> POTTED_BIG_DRIPLEAF = registerFlowerPot(WWBlockIds.POTTED_BIG_DRIPLEAF, () -> Blocks.BIG_DRIPLEAF);
	public static final FrozenDeferredBlock<Block> POTTED_SMALL_DRIPLEAF = registerFlowerPot(WWBlockIds.POTTED_SMALL_DRIPLEAF, () -> Blocks.SMALL_DRIPLEAF);

	public static final FrozenDeferredBlock<PollenBlock> POLLEN = REGISTER.registerBlock(WWBlockItemIds.POLLEN.block(),
		PollenBlock::new,
		() -> Properties.ofFullCopy(Blocks.SHORT_GRASS)
			.mapColor(MapColor.SAND)
			.sound(WWSoundTypes.POLLEN)
			.offsetType(BlockBehaviour.OffsetType.NONE)
	);

	public static final FrozenDeferredBlock<PricklyPearCactusBlock> PRICKLY_PEAR = REGISTER.registerBlock(WWBlockItemIds.PRICKLY_PEAR.block(),
		PricklyPearCactusBlock::new,
		() -> Properties.ofFullCopy(Blocks.CACTUS).noCollision().offsetType(BlockBehaviour.OffsetType.XZ)
	);
	public static final FrozenDeferredBlock<Block> POTTED_PRICKLY_PEAR = registerFlowerPot(WWBlockIds.POTTED_PRICKLY_PEAR, PRICKLY_PEAR);

	public static final FrozenDeferredBlock<ShrubBlock> SHRUB = REGISTER.registerBlock(WWBlockItemIds.SHRUB.block(),
		ShrubBlock::new,
		() -> Properties.ofFullCopy(Blocks.DEAD_BUSH)
			.mapColor(MapColor.PLANT)
			.noOcclusion()
			.randomTicks()
			.sound(SoundType.RED_SHRUB)
			.offsetType(BlockBehaviour.OffsetType.XZ)
	);
	public static final FrozenDeferredBlock<Block> POTTED_SHRUB = registerFlowerPot(WWBlockIds.POTTED_SHRUB, SHRUB);

	public static final FrozenDeferredBlock<TumbleweedPlantBlock> TUMBLEWEED_PLANT = REGISTER.registerBlock(WWBlockItemIds.TUMBLEWEED_PLANT.block(),
		TumbleweedPlantBlock::new,
		() -> Properties.of()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final FrozenDeferredBlock<Block> POTTED_TUMBLEWEED_PLANT = registerFlowerPot(WWBlockIds.POTTED_TUMBLEWEED_PLANT, TUMBLEWEED_PLANT);

	public static final FrozenDeferredBlock<TumbleweedBlock> TUMBLEWEED = REGISTER.registerBlock(WWBlockItemIds.TUMBLEWEED.block(),
		TumbleweedBlock::new,
		() -> Properties.of()
			.instabreak()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final FrozenDeferredBlock<Block> POTTED_TUMBLEWEED = registerFlowerPot(WWBlockIds.POTTED_TUMBLEWEED, TUMBLEWEED);

	public static final FrozenDeferredBlock<MyceliumGrowthBlock> MYCELIUM_GROWTH = REGISTER.registerBlock(WWBlockItemIds.MYCELIUM_GROWTH.block(),
		MyceliumGrowthBlock::new,
		() -> Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.NETHER_SPROUTS)
	);
	public static final FrozenDeferredBlock<Block> POTTED_MYCELIUM_GROWTH = registerFlowerPot(WWBlockIds.POTTED_MYCELIUM_GROWTH, MYCELIUM_GROWTH);

	public static final FrozenDeferredBlock<FrozenTallGrassBlock> FROZEN_SHORT_GRASS = REGISTER.registerBlock(WWBlockItemIds.FROZEN_SHORT_GRASS.block(), FrozenTallGrassBlock::new, () -> Properties.ofFullCopy(Blocks.SHORT_GRASS));
	public static final FrozenDeferredBlock<Block> POTTED_FROZEN_SHORT_GRASS = registerFlowerPot(WWBlockIds.POTTED_FROZEN_SHORT_GRASS, FROZEN_SHORT_GRASS);
	public static final FrozenDeferredBlock<FrozenDoublePlantBlock> FROZEN_TALL_GRASS = REGISTER.registerBlock(WWBlockItemIds.FROZEN_TALL_GRASS.block(), FrozenDoublePlantBlock::new, () -> Properties.ofFullCopy(Blocks.TALL_GRASS));

	public static final FrozenDeferredBlock<FrozenTallGrassBlock> FROZEN_FERN = REGISTER.registerBlock(WWBlockItemIds.FROZEN_FERN.block(), FrozenTallGrassBlock::new, () -> Properties.ofFullCopy(Blocks.FERN));
	public static final FrozenDeferredBlock<Block> POTTED_FROZEN_FERN = registerFlowerPot(WWBlockIds.POTTED_FROZEN_FERN, FROZEN_FERN);

	public static final FrozenDeferredBlock<FrozenDoublePlantBlock> FROZEN_LARGE_FERN = REGISTER.registerBlock(WWBlockItemIds.FROZEN_LARGE_FERN.block(), FrozenDoublePlantBlock::new, () -> Properties.ofFullCopy(Blocks.LARGE_FERN));

	public static final FrozenDeferredBlock<FrozenBushBlock> FROZEN_BUSH = REGISTER.registerBlock(WWBlockItemIds.FROZEN_BUSH.block(), FrozenBushBlock::new, () -> Properties.ofFullCopy(Blocks.BUSH));
	public static final FrozenDeferredBlock<Block> POTTED_FROZEN_BUSH = registerFlowerPot(WWBlockIds.POTTED_FROZEN_BUSH, FROZEN_BUSH);

	// MUSHROOMS
	public static final FrozenDeferredBlock<HugePaleMushroomBlock> PALE_MUSHROOM_BLOCK = REGISTER.registerBlock(WWBlockItemIds.PALE_MUSHROOM_BLOCK.block(),
		HugePaleMushroomBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.COLOR_GRAY)
			.instrument(NoteBlockInstrument.BASS)
			.strength(0.2F)
			.sound(SoundType.WOOD)
			.ignitedByLava()
	);
	public static final FrozenDeferredBlock<PaleMushroomBlock> PALE_MUSHROOM = REGISTER.registerBlock(WWBlockItemIds.PALE_MUSHROOM.block(),
		properties -> new PaleMushroomBlock(ResourceKey.create(Registries.FEATURE, WWConstants.id("huge_pale_mushroom")), properties),
		() -> Properties.of()
			.mapColor(MapColor.COLOR_GRAY)
			.noCollision()
			.randomTicks()
			.instabreak()
			.sound(SoundType.GRASS)
			.postProcess(Blocks::postProcessSelf)
			.pushReaction(PushReaction.POPPED)
	);
	public static final FrozenDeferredBlock<Block> POTTED_PALE_MUSHROOM = registerFlowerPot(WWBlockIds.POTTED_PALE_MUSHROOM, PALE_MUSHROOM);

	// MOSS
	public static final FrozenDeferredBlock<AuburnMossBlock> AUBURN_MOSS_BLOCK = REGISTER.registerBlock(WWBlockItemIds.AUBURN_MOSS_BLOCK.block(),
		AuburnMossBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.TERRACOTTA_ORANGE)
			.strength(0.1F)
			.sound(SoundType.MOSS)
			.pushReaction(PushReaction.POPPED)
	);
	public static final FrozenDeferredBlock<AuburnMossCarpetBlock> AUBURN_MOSS_CARPET = REGISTER.registerBlock(WWBlockItemIds.AUBURN_MOSS_CARPET.block(),
		AuburnMossCarpetBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.TERRACOTTA_ORANGE)
			.strength(0.1F)
			.sound(SoundType.MOSS_CARPET)
			.pushReaction(PushReaction.POPPED)
	);
	public static final FrozenDeferredBlock<AuburnCreepingMossBlock> AUBURN_CREEPING_MOSS = REGISTER.registerBlock(WWBlockItemIds.AUBURN_CREEPING_MOSS.block(),
		AuburnCreepingMossBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.TERRACOTTA_ORANGE)
			.forceSolidOn()
			.noCollision()
			.strength(0.1F)
			.sound(SoundType.MOSS_CARPET)
			.pushReaction(PushReaction.POPPED)
	);

	// AQUATIC
	public static final FrozenDeferredBlock<CattailBlock> CATTAIL = REGISTER.registerBlock(WWBlockItemIds.CATTAIL.block(), CattailBlock::new, () -> Properties.ofFullCopy(Blocks.ROSE_BUSH).sound(SoundType.WET_GRASS));
	public static final FrozenDeferredBlock<FloweringWaterlilyBlock> FLOWERING_LILY_PAD = REGISTER.registerBlock(WWBlockItemIds.FLOWERING_LILY_PAD.block(),
		properties -> new FloweringWaterlilyBlock(Blocks.LILY_PAD, properties),
		() -> Properties.ofFullCopy(Blocks.LILY_PAD)
	);
	public static final FrozenDeferredBlock<AlgaeBlock> ALGAE = REGISTER.registerBlock(WWBlockItemIds.ALGAE.block(),
		AlgaeBlock::new,
		() -> Properties.ofFullCopy(Blocks.FROGSPAWN).mapColor(MapColor.PLANT).sound(WWSoundTypes.ALGAE)
	);
	public static final FrozenDeferredBlock<PlanktonBlock> PLANKTON = REGISTER.registerBlock(WWBlockItemIds.PLANKTON.block(),
		PlanktonBlock::new,
		() -> Properties.ofFullCopy(Blocks.FROGSPAWN)
			.mapColor(MapColor.COLOR_LIGHT_BLUE)
			.randomTicks()
			.requiresCorrectToolForDrops()
			.lightLevel(state -> PlanktonBlock.isGlowing(state) ? PlanktonBlock.LIGHT_LEVEL : 0)
			.sound(WWSoundTypes.ALGAE)
	);
	public static final FrozenDeferredBlock<SpongeBudBlock> SPONGE_BUD = REGISTER.registerBlock(WWBlockItemIds.SPONGE_BUD.block(),
		SpongeBudBlock::new,
		() -> Properties.ofFullCopy(Blocks.SPONGE)
			.strength(0.1F)
			.noCollision()
			.noOcclusion()
			.sound(SoundType.SPONGE)
	);
	public static final FrozenDeferredBlock<BarnaclesBlock> BARNACLES = REGISTER.registerBlock(WWBlockItemIds.BARNACLES.block(),
		BarnaclesBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.forceSolidOn()
			.noCollision()
			.sound(WWSoundTypes.BARNACLES)
			.pushReaction(PushReaction.POPPED)
	);
	public static final FrozenDeferredBlock<SeaAnemoneBlock> SEA_ANEMONE = REGISTER.registerBlock(WWBlockItemIds.SEA_ANEMONE.block(),
		SeaAnemoneBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.WATER)
			.instabreak()
			.noCollision()
			.lightLevel(state -> SeaAnemoneBlock.isGlowing(state) ? SeaAnemoneBlock.LIGHT_LEVEL : 0)
			.randomTicks()
			.sound(WWSoundTypes.SEA_ANEMONE)
			.pushReaction(PushReaction.POPPED)
	);
	public static final FrozenDeferredBlock<SeaWhipBlock> SEA_WHIP = REGISTER.registerBlock(WWBlockItemIds.SEA_WHIP.block(),
		SeaWhipBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.WATER)
			.instabreak()
			.noCollision()
			.sound(SoundType.WET_GRASS)
			.pushReaction(PushReaction.POPPED)
	);
	public static final FrozenDeferredBlock<TubeWormsBlock> TUBE_WORMS = REGISTER.registerBlock(WWBlockItemIds.TUBE_WORMS.block(),
		TubeWormsBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.WATER)
			.strength(0.2F)
			.noCollision()
			.randomTicks()
			.sound(WWSoundTypes.TUBE_WORMS)
			.pushReaction(PushReaction.POPPED)
	);

	// EGGS
	public static final FrozenDeferredBlock<OstrichEggBlock> OSTRICH_EGG = REGISTER.registerBlock(WWBlockItemIds.OSTRICH_EGG.block(),
		OstrichEggBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);
	public static final FrozenDeferredBlock<PenguinEggBlock> PENGUIN_EGG = REGISTER.registerBlock(WWBlockItemIds.PENGUIN_EGG.block(),
		PenguinEggBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);

	// GABBRO
	public static final FrozenDeferredBlock<Block> GABBRO = REGISTER.registerSimpleBlock(WWBlockItemIds.GABBRO.block(),
		() -> Properties.of().mapColor(MapColor.TERRACOTTA_BROWN)
			.sound(WWSoundTypes.GABBRO)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(4.5F)
	);
	public static final FrozenDeferredBlock<StairBlock> GABBRO_STAIRS = REGISTER.registerBlock(WWBlockItemIds.GABBRO_STAIRS.block(),
		properties -> new StairBlock(WWBlocks.GABBRO.get().defaultBlockState(), properties),
		() -> Properties.ofFullCopy(WWBlocks.GABBRO.get()).requiredFeatures(WWFeatureFlags.TRAILIER_TALES_COMPAT)
	);
	public static final FrozenDeferredBlock<SlabBlock> GABBRO_SLAB = REGISTER.registerBlock(WWBlockItemIds.GABBRO_SLAB.block(),
		SlabBlock::new,
		() -> Properties.ofFullCopy(WWBlocks.GABBRO.get()).requiredFeatures(WWFeatureFlags.TRAILIER_TALES_COMPAT)
	);
	public static final FrozenDeferredBlock<WallBlock> GABBRO_WALL = REGISTER.registerBlock(WWBlockItemIds.GABBRO_WALL.block(),
		WallBlock::new,
		() -> Properties.ofFullCopy(WWBlocks.GABBRO.get()).requiredFeatures(WWFeatureFlags.TRAILIER_TALES_COMPAT)
	);

	public static final FrozenDeferredBlock<GeothermalVentBlock> GEOTHERMAL_VENT = REGISTER.registerBlock(WWBlockItemIds.GEOTHERMAL_VENT.block(),
		GeothermalVentBlock::new,
		() -> Properties.ofFullCopy(WWBlocks.GABBRO.get())
			.sound(WWSoundTypes.GEOTHERMAL_VENT)
			.strength(8F)
			.isValidSpawn((state, level, pos, entityType) -> false)
			.postProcess(Blocks::postProcessSelf)
	);

	public static final FrozenDeferredBlock<Block> POLISHED_GABBRO = REGISTER.registerSimpleBlock(WWBlockItemIds.POLISHED_GABBRO.block(), () -> Properties.ofFullCopy(WWBlocks.GABBRO.get()));
	public static final FrozenDeferredBlock<StairBlock> POLISHED_GABBRO_STAIRS = REGISTER.registerStair(WWBlockItemIds.POLISHED_GABBRO_STAIRS, POLISHED_GABBRO);
	public static final FrozenDeferredBlock<SlabBlock> POLISHED_GABBRO_SLAB = REGISTER.registerSlab(WWBlockItemIds.POLISHED_GABBRO_SLAB, POLISHED_GABBRO);
	public static final FrozenDeferredBlock<WallBlock> POLISHED_GABBRO_WALL = REGISTER.registerWall(WWBlockItemIds.POLISHED_GABBRO_WALL, POLISHED_GABBRO);

	public static final FrozenDeferredBlock<Block> GABBRO_BRICKS = REGISTER.registerSimpleBlock(WWBlockItemIds.GABBRO_BRICKS.block(), () -> Properties.ofFullCopy(WWBlocks.GABBRO.get()).sound(WWSoundTypes.GABBRO_BRICKS));
	public static final FrozenDeferredBlock<StairBlock> GABBRO_BRICK_STAIRS = REGISTER.registerStair(WWBlockItemIds.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICKS);
	public static final FrozenDeferredBlock<SlabBlock> GABBRO_BRICK_SLAB = REGISTER.registerSlab(WWBlockItemIds.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICKS);
	public static final FrozenDeferredBlock<WallBlock> GABBRO_BRICK_WALL = REGISTER.registerWall(WWBlockItemIds.GABBRO_BRICK_WALL, WWBlocks.GABBRO_BRICKS);
	public static final FrozenDeferredBlock<Block> CRACKED_GABBRO_BRICKS = REGISTER.registerSimpleBlock(WWBlockItemIds.CRACKED_GABBRO_BRICKS.block(), () -> Properties.ofFullCopy(WWBlocks.GABBRO_BRICKS.get()));
	public static final FrozenDeferredBlock<Block> CHISELED_GABBRO_BRICKS = REGISTER.registerSimpleBlock(WWBlockItemIds.CHISELED_GABBRO_BRICKS.block(), () -> Properties.ofFullCopy(WWBlocks.GABBRO_BRICKS.get()));

	public static final FrozenDeferredBlock<Block> MOSSY_GABBRO_BRICKS = REGISTER.registerSimpleBlock(WWBlockItemIds.MOSSY_GABBRO_BRICKS.block(),
		() -> Properties.ofFullCopy(WWBlocks.GABBRO_BRICKS.get()).requiredFeatures(WWFeatureFlags.TRAILIER_TALES_COMPAT)
	);
	public static final FrozenDeferredBlock<StairBlock> MOSSY_GABBRO_BRICK_STAIRS = REGISTER.registerStair(WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICKS);
	public static final FrozenDeferredBlock<SlabBlock> MOSSY_GABBRO_BRICK_SLAB = REGISTER.registerSlab(WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICKS);
	public static final FrozenDeferredBlock<WallBlock> MOSSY_GABBRO_BRICK_WALL = REGISTER.registerWall(WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL, WWBlocks.MOSSY_GABBRO_BRICKS);

	// BAOBAB
	public static final FrozenDeferredBlock<Block> BAOBAB_PLANKS = REGISTER.registerSimpleBlock(WWBlockItemIds.BAOBAB_PLANKS.block(), () -> Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(BAOBAB_PLANKS_COLOR));

	public static final FrozenDeferredBlock<StairBlock> BAOBAB_STAIRS = REGISTER.registerStair(WWBlockItemIds.BAOBAB_STAIRS, BAOBAB_PLANKS);
	public static final FrozenDeferredBlock<FenceGateBlock> BAOBAB_FENCE_GATE = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_FENCE_GATE.block(),
		properties -> new FenceGateBlock(BAOBAB_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<SlabBlock> BAOBAB_SLAB = REGISTER.registerSlab(WWBlockItemIds.BAOBAB_SLAB, BAOBAB_PLANKS);
	public static final FrozenDeferredBlock<PressurePlateBlock> BAOBAB_PRESSURE_PLATE = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_PRESSURE_PLATE.block(),
		properties -> new PressurePlateBlock(BAOBAB_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<ButtonBlock> BAOBAB_BUTTON = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_BUTTON.block(),
		properties -> new ButtonBlock(BAOBAB_SET, 30, properties),
		Blocks::buttonProperties
	);
	public static final FrozenDeferredBlock<DoorBlock> BAOBAB_DOOR = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_DOOR.block(),
		properties -> new DoorBlock(BAOBAB_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<TrapDoorBlock> BAOBAB_TRAPDOOR = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_TRAPDOOR.block(),
		properties -> new TrapDoorBlock(BAOBAB_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<FenceBlock> BAOBAB_FENCE = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_FENCE.block(),
		FenceBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> BAOBAB_LOG = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(BAOBAB_PLANKS_COLOR, BAOBAB_BARK_COLOR, SoundType.WOOD)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_BAOBAB_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_BAOBAB_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(BAOBAB_PLANKS_COLOR, BAOBAB_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_BAOBAB_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG.block(),
		HollowedLogBlock::new,
		() -> strippedHollowedLogProperties(BAOBAB_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> BAOBAB_WOOD = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(BAOBAB_BARK_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_BAOBAB_WOOD = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_BAOBAB_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(BAOBAB_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<StandingSignBlock> BAOBAB_SIGN = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_SIGN.block(),
		properties -> new StandingSignBlock(BAOBAB_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(BAOBAB_LOG.get().defaultMapColor())
	);
	public static final FrozenDeferredBlock<WallSignBlock> BAOBAB_WALL_SIGN = REGISTER.registerBlock(WWBlockIds.BAOBAB_WALL_SIGN,
		properties -> new WallSignBlock(BAOBAB_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(BAOBAB_LOG.get().defaultMapColor())
			.overrideDescription(BAOBAB_SIGN.get().getDescriptionId())
			.overrideLootTable(BAOBAB_SIGN.get().getLootTable())
	);
	public static final FrozenDeferredBlock<CeilingHangingSignBlock> BAOBAB_HANGING_SIGN = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_HANGING_SIGN.block(),
		properties -> new CeilingHangingSignBlock(BAOBAB_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.get().defaultMapColor())
	);
	public static final FrozenDeferredBlock<WallHangingSignBlock> BAOBAB_WALL_HANGING_SIGN = REGISTER.registerBlock(WWBlockIds.BAOBAB_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(BAOBAB_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(BAOBAB_LOG.get().defaultMapColor())
			.overrideDescription(BAOBAB_HANGING_SIGN.get().getDescriptionId())
			.overrideLootTable(BAOBAB_HANGING_SIGN.get().getLootTable())
	);
	public static final FrozenDeferredBlock<ShelfBlock> BAOBAB_SHELF = REGISTER.registerBlock(WWBlockItemIds.BAOBAB_SHELF.block(),
		ShelfBlock::new,
		() -> Properties.of()
			.mapColor(BAOBAB_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
	);

	// WILLOW
	public static final FrozenDeferredBlock<Block> WILLOW_PLANKS = REGISTER.registerSimpleBlock(WWBlockItemIds.WILLOW_PLANKS.block(), () -> Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(WILLOW_PLANKS_COLOR));
	public static final FrozenDeferredBlock<StairBlock> WILLOW_STAIRS = REGISTER.registerStair(WWBlockItemIds.WILLOW_STAIRS, WILLOW_PLANKS);
	public static final FrozenDeferredBlock<FenceGateBlock> WILLOW_FENCE_GATE = REGISTER.registerBlock(WWBlockItemIds.WILLOW_FENCE_GATE.block(),
		properties -> new FenceGateBlock(WILLOW_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<SlabBlock> WILLOW_SLAB = REGISTER.registerSlab(WWBlockItemIds.WILLOW_SLAB, WILLOW_PLANKS);
	public static final FrozenDeferredBlock<PressurePlateBlock> WILLOW_PRESSURE_PLATE = REGISTER.registerBlock(WWBlockItemIds.WILLOW_PRESSURE_PLATE.block(),
		properties -> new PressurePlateBlock(WILLOW_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<ButtonBlock> WILLOW_BUTTON = REGISTER.registerBlock(WWBlockItemIds.WILLOW_BUTTON.block(),
		properties -> new ButtonBlock(WILLOW_SET, 30, properties),
		Blocks::buttonProperties
	);
	public static final FrozenDeferredBlock<DoorBlock> WILLOW_DOOR = REGISTER.registerBlock(WWBlockItemIds.WILLOW_DOOR.block(),
		properties -> new DoorBlock(WILLOW_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<TrapDoorBlock> WILLOW_TRAPDOOR = REGISTER.registerBlock(WWBlockItemIds.WILLOW_TRAPDOOR.block(),
		properties -> new TrapDoorBlock(WILLOW_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<FenceBlock> WILLOW_FENCE = REGISTER.registerBlock(WWBlockItemIds.WILLOW_FENCE.block(),
		FenceBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> WILLOW_LOG = REGISTER.registerBlock(WWBlockItemIds.WILLOW_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(WILLOW_PLANKS_COLOR, WILLOW_BARK_COLOR, SoundType.WOOD)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_WILLOW_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_WILLOW_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(WILLOW_PLANKS_COLOR, WILLOW_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_WILLOW_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG.block(),
		HollowedLogBlock::new,
		() -> strippedHollowedLogProperties(WILLOW_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> WILLOW_WOOD = REGISTER.registerBlock(WWBlockItemIds.WILLOW_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(WILLOW_BARK_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_WILLOW_WOOD = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_WILLOW_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(WILLOW_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<StandingSignBlock> WILLOW_SIGN = REGISTER.registerBlock(WWBlockItemIds.WILLOW_SIGN.block(),
		properties -> new StandingSignBlock(WILLOW_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(WILLOW_LOG.get().defaultMapColor())
	);
	public static final FrozenDeferredBlock<WallSignBlock> WILLOW_WALL_SIGN = REGISTER.registerBlock(WWBlockIds.WILLOW_WALL_SIGN,
		properties -> new WallSignBlock(WILLOW_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(WILLOW_LOG.get().defaultMapColor())
			.overrideDescription(WILLOW_SIGN.get().getDescriptionId())
			.overrideLootTable(WILLOW_SIGN.get().getLootTable())
	);
	public static final FrozenDeferredBlock<CeilingHangingSignBlock> WILLOW_HANGING_SIGN = REGISTER.registerBlock(WWBlockItemIds.WILLOW_HANGING_SIGN.block(),
		properties -> new CeilingHangingSignBlock(WILLOW_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(WILLOW_LOG.get().defaultMapColor())
	);
	public static final FrozenDeferredBlock<WallHangingSignBlock> WILLOW_WALL_HANGING_SIGN = REGISTER.registerBlock(WWBlockIds.WILLOW_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(WILLOW_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(WILLOW_LOG.get().defaultMapColor())
			.overrideDescription(WILLOW_HANGING_SIGN.get().getDescriptionId())
			.overrideLootTable(WILLOW_HANGING_SIGN.get().getLootTable())
	);
	public static final FrozenDeferredBlock<ShelfBlock> WILLOW_SHELF = REGISTER.registerBlock(WWBlockItemIds.WILLOW_SHELF.block(),
		ShelfBlock::new,
		() -> Properties.of()
			.mapColor(WILLOW_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
	);

	// CYPRESS
	public static final FrozenDeferredBlock<Block> CYPRESS_PLANKS = REGISTER.registerSimpleBlock(WWBlockItemIds.CYPRESS_PLANKS.block(), () -> Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(CYPRESS_PLANKS_COLOR));
	public static final FrozenDeferredBlock<StairBlock> CYPRESS_STAIRS = REGISTER.registerStair(WWBlockItemIds.CYPRESS_STAIRS, CYPRESS_PLANKS);
	public static final FrozenDeferredBlock<FenceGateBlock> CYPRESS_FENCE_GATE = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_FENCE_GATE.block(),
		properties -> new FenceGateBlock(CYPRESS_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<SlabBlock> CYPRESS_SLAB = REGISTER.registerSlab(WWBlockItemIds.CYPRESS_SLAB, CYPRESS_PLANKS);
	public static final FrozenDeferredBlock<PressurePlateBlock> CYPRESS_PRESSURE_PLATE = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_PRESSURE_PLATE.block(),
		properties -> new PressurePlateBlock(CYPRESS_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<ButtonBlock> CYPRESS_BUTTON = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_BUTTON.block(),
		properties -> new ButtonBlock(CYPRESS_SET, 30, properties),
		Blocks::buttonProperties
	);
	public static final FrozenDeferredBlock<DoorBlock> CYPRESS_DOOR = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_DOOR.block(),
		properties -> new DoorBlock(CYPRESS_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<TrapDoorBlock> CYPRESS_TRAPDOOR = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_TRAPDOOR.block(),
		properties -> new TrapDoorBlock(CYPRESS_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<FenceBlock> CYPRESS_FENCE = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_FENCE.block(),
		FenceBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> CYPRESS_LOG = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(CYPRESS_PLANKS_COLOR, CYPRESS_BARK_COLOR, SoundType.WOOD)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_CYPRESS_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_CYPRESS_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(CYPRESS_PLANKS_COLOR, CYPRESS_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_CYPRESS_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG.block(),
		HollowedLogBlock::new,
		() -> strippedHollowedLogProperties(CYPRESS_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> CYPRESS_WOOD = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(CYPRESS_BARK_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_CYPRESS_WOOD = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_CYPRESS_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(CYPRESS_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<StandingSignBlock> CYPRESS_SIGN = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_SIGN.block(),
		properties -> new StandingSignBlock(CYPRESS_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(CYPRESS_LOG.get().defaultMapColor())
	);
	public static final FrozenDeferredBlock<WallSignBlock> CYPRESS_WALL_SIGN = REGISTER.registerBlock(WWBlockIds.CYPRESS_WALL_SIGN,
		properties -> new WallSignBlock(CYPRESS_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(CYPRESS_LOG.get().defaultMapColor())
			.overrideDescription(CYPRESS_SIGN.get().getDescriptionId())
			.overrideLootTable(CYPRESS_SIGN.get().getLootTable())
	);
	public static final FrozenDeferredBlock<CeilingHangingSignBlock> CYPRESS_HANGING_SIGN = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_HANGING_SIGN.block(),
		properties -> new CeilingHangingSignBlock(CYPRESS_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.get().defaultMapColor())
	);
	public static final FrozenDeferredBlock<WallHangingSignBlock> CYPRESS_WALL_HANGING_SIGN = REGISTER.registerBlock(WWBlockIds.CYPRESS_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(CYPRESS_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(CYPRESS_LOG.get().defaultMapColor())
			.overrideDescription(CYPRESS_HANGING_SIGN.get().getDescriptionId())
			.overrideLootTable(CYPRESS_HANGING_SIGN.get().getLootTable())
	);
	public static final FrozenDeferredBlock<ShelfBlock> CYPRESS_SHELF = REGISTER.registerBlock(WWBlockItemIds.CYPRESS_SHELF.block(),
		ShelfBlock::new,
		() -> Properties.of()
			.mapColor(CYPRESS_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
	);

	// PALM
	public static final FrozenDeferredBlock<Block> PALM_PLANKS = REGISTER.registerSimpleBlock(WWBlockItemIds.PALM_PLANKS.block(), () -> Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(PALM_PLANKS_COLOR));
	public static final FrozenDeferredBlock<StairBlock> PALM_STAIRS = REGISTER.registerStair(WWBlockItemIds.PALM_STAIRS, PALM_PLANKS);
	public static final FrozenDeferredBlock<FenceGateBlock> PALM_FENCE_GATE = REGISTER.registerBlock(WWBlockItemIds.PALM_FENCE_GATE.block(),
		properties -> new FenceGateBlock(PALM_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(PALM_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<SlabBlock> PALM_SLAB = REGISTER.registerSlab(WWBlockItemIds.PALM_SLAB, PALM_PLANKS);
	public static final FrozenDeferredBlock<PressurePlateBlock> PALM_PRESSURE_PLATE = REGISTER.registerBlock(WWBlockItemIds.PALM_PRESSURE_PLATE.block(),
		properties -> new PressurePlateBlock(PALM_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(PALM_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<ButtonBlock> PALM_BUTTON = REGISTER.registerBlock(WWBlockItemIds.PALM_BUTTON.block(),
		properties -> new ButtonBlock(PALM_SET, 30, properties),
		Blocks::buttonProperties
	);
	public static final FrozenDeferredBlock<DoorBlock> PALM_DOOR = REGISTER.registerBlock(WWBlockItemIds.PALM_DOOR.block(),
		properties -> new DoorBlock(PALM_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(PALM_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<TrapDoorBlock> PALM_TRAPDOOR = REGISTER.registerBlock(WWBlockItemIds.PALM_TRAPDOOR.block(),
		properties -> new TrapDoorBlock(PALM_SET, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(PALM_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<FenceBlock> PALM_FENCE = REGISTER.registerBlock(WWBlockItemIds.PALM_FENCE.block(),
		FenceBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(PALM_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> PALM_LOG = REGISTER.registerBlock(WWBlockItemIds.PALM_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(PALM_PLANKS_COLOR, PALM_BARK_COLOR, SoundType.WOOD)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_PALM_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_PALM_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(PALM_PLANKS_COLOR, PALM_PLANKS_COLOR, SoundType.WOOD)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_PALM_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG.block(),
		HollowedLogBlock::new,
		() -> strippedHollowedLogProperties(PALM_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> PALM_WOOD = REGISTER.registerBlock(WWBlockItemIds.PALM_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(PALM_BARK_COLOR)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_PALM_WOOD = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_PALM_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(PALM_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<StandingSignBlock> PALM_SIGN = REGISTER.registerBlock(WWBlockItemIds.PALM_SIGN.block(),
		properties -> new StandingSignBlock(PALM_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(PALM_LOG.get().defaultMapColor())
	);
	public static final FrozenDeferredBlock<WallSignBlock> PALM_WALL_SIGN = REGISTER.registerBlock(WWBlockIds.PALM_WALL_SIGN,
		properties -> new WallSignBlock(PALM_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(PALM_LOG.get().defaultMapColor())
			.overrideDescription(PALM_SIGN.get().getDescriptionId())
			.overrideLootTable(PALM_SIGN.get().getLootTable())
	);
	public static final FrozenDeferredBlock<CeilingHangingSignBlock> PALM_HANGING_SIGN = REGISTER.registerBlock(WWBlockItemIds.PALM_HANGING_SIGN.block(),
		properties -> new CeilingHangingSignBlock(PALM_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(PALM_LOG.get().defaultMapColor())
	);
	public static final FrozenDeferredBlock<WallHangingSignBlock> PALM_WALL_HANGING_SIGN = REGISTER.registerBlock(WWBlockIds.PALM_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(PALM_WOOD_TYPE, properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(PALM_LOG.get().defaultMapColor())
			.overrideDescription(PALM_HANGING_SIGN.get().getDescriptionId())
			.overrideLootTable(PALM_HANGING_SIGN.get().getLootTable())
	);
	public static final FrozenDeferredBlock<ShelfBlock> PALM_SHELF = REGISTER.registerBlock(WWBlockItemIds.PALM_SHELF.block(),
		ShelfBlock::new,
		() -> Properties.of()
			.mapColor(PALM_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
	);

	// MAPLE
	public static final FrozenDeferredBlock<Block> MAPLE_PLANKS = REGISTER.registerSimpleBlock(WWBlockItemIds.MAPLE_PLANKS.block(),
		() -> Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MAPLE_PLANKS_COLOR).sound(WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<StairBlock> MAPLE_STAIRS = REGISTER.registerStair(WWBlockItemIds.MAPLE_STAIRS, MAPLE_PLANKS);
	public static final FrozenDeferredBlock<FenceGateBlock> MAPLE_FENCE_GATE = REGISTER.registerBlock(WWBlockItemIds.MAPLE_FENCE_GATE.block(),
		properties -> new FenceGateBlock(MAPLE_WOOD_TYPE.get(), properties),
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MAPLE_PLANKS_COLOR).sound(WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<SlabBlock> MAPLE_SLAB = REGISTER.registerSlab(WWBlockItemIds.MAPLE_SLAB, MAPLE_PLANKS);
	public static final FrozenDeferredBlock<PressurePlateBlock> MAPLE_PRESSURE_PLATE = REGISTER.registerBlock(WWBlockItemIds.MAPLE_PRESSURE_PLATE.block(),
		properties -> new PressurePlateBlock(MAPLE_SET.get(), properties),
		() -> Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<ButtonBlock> MAPLE_BUTTON = REGISTER.registerBlock(WWBlockItemIds.MAPLE_BUTTON.block(),
		properties -> new ButtonBlock(MAPLE_SET.get(), 30, properties),
		Blocks::buttonProperties
	);
	public static final FrozenDeferredBlock<DoorBlock> MAPLE_DOOR = REGISTER.registerBlock(WWBlockItemIds.MAPLE_DOOR.block(),
		properties -> new DoorBlock(MAPLE_SET.get(), properties),
		() -> Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<TrapDoorBlock> MAPLE_TRAPDOOR = REGISTER.registerBlock(WWBlockItemIds.MAPLE_TRAPDOOR.block(),
		properties -> new TrapDoorBlock(MAPLE_SET.get(), properties),
		() -> Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MAPLE_PLANKS_COLOR)
	);
	public static final FrozenDeferredBlock<FenceBlock> MAPLE_FENCE = REGISTER.registerBlock(WWBlockItemIds.MAPLE_FENCE.block(),
		FenceBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MAPLE_PLANKS_COLOR).sound(WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> MAPLE_LOG = REGISTER.registerBlock(WWBlockItemIds.MAPLE_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(MAPLE_PLANKS_COLOR, MAPLE_BARK_COLOR, WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_MAPLE_LOG.block(),
		RotatedPillarBlock::new,
		() -> Blocks.logProperties(MAPLE_PLANKS_COLOR, MAPLE_PLANKS_COLOR, WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<HollowedLogBlock> STRIPPED_HOLLOWED_MAPLE_LOG = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG.block(),
		HollowedLogBlock::new,
		() -> strippedHollowedLogProperties(MAPLE_PLANKS_COLOR).sound(WWSoundTypes.HOLLOWED_MAPLE_LOG)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> MAPLE_WOOD = REGISTER.registerBlock(WWBlockItemIds.MAPLE_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MAPLE_BARK_COLOR).sound(WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = REGISTER.registerBlock(WWBlockItemIds.STRIPPED_MAPLE_WOOD.block(),
		RotatedPillarBlock::new,
		() -> Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MAPLE_PLANKS_COLOR).sound(WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<StandingSignBlock> MAPLE_SIGN = REGISTER.registerBlock(WWBlockItemIds.MAPLE_SIGN.block(),
		properties -> new StandingSignBlock(MAPLE_WOOD_TYPE.get(), properties),
		() -> Properties.ofFullCopy(Blocks.OAK_SIGN)
			.mapColor(MAPLE_LOG.get().defaultMapColor())
			.sound(WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<WallSignBlock> MAPLE_WALL_SIGN = REGISTER.registerBlock(WWBlockIds.MAPLE_WALL_SIGN,
		properties -> new WallSignBlock(MAPLE_WOOD_TYPE.get(), properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)
			.mapColor(MAPLE_LOG.get().defaultMapColor())
			.overrideDescription(MAPLE_SIGN.get().getDescriptionId())
			.overrideLootTable(MAPLE_SIGN.get().getLootTable())
			.sound(WWSoundTypes.MAPLE_WOOD)
	);
	public static final FrozenDeferredBlock<CeilingHangingSignBlock> MAPLE_HANGING_SIGN = REGISTER.registerBlock(WWBlockItemIds.MAPLE_HANGING_SIGN.block(),
		properties -> new CeilingHangingSignBlock(MAPLE_WOOD_TYPE.get(), properties),
		() -> Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
			.mapColor(MAPLE_LOG.get().defaultMapColor())
			.sound(WWSoundTypes.MAPLE_WOOD_HANGING_SIGN)
	);
	public static final FrozenDeferredBlock<WallHangingSignBlock> MAPLE_WALL_HANGING_SIGN = REGISTER.registerBlock(WWBlockIds.MAPLE_WALL_HANGING_SIGN,
		properties -> new WallHangingSignBlock(MAPLE_WOOD_TYPE.get(), properties),
		() -> Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)
			.mapColor(MAPLE_LOG.get().defaultMapColor())
			.overrideDescription(MAPLE_HANGING_SIGN.get().getDescriptionId())
			.overrideLootTable(MAPLE_HANGING_SIGN.get().getLootTable())
			.sound(WWSoundTypes.MAPLE_WOOD_HANGING_SIGN)
	);
	public static final FrozenDeferredBlock<ShelfBlock> MAPLE_SHELF = REGISTER.registerBlock(WWBlockItemIds.MAPLE_SHELF.block(),
		ShelfBlock::new,
		() -> Properties.of()
			.mapColor(MAPLE_PLANKS_COLOR)
			.instrument(NoteBlockInstrument.BASS)
			.sound(SoundType.SHELF)
			.ignitedByLava()
			.strength(2F, 3F)
	);

	// ICE
	public static final FrozenDeferredBlock<FragileIceBlock> FRAGILE_ICE = REGISTER.registerBlock(WWBlockItemIds.FRAGILE_ICE.block(),
		FragileIceBlock::new,
		() -> Properties.ofFullCopy(Blocks.ICE).strength(0.2F).pushReaction(PushReaction.POPPED)
	);
	public static final FrozenDeferredBlock<IcicleBlock> ICICLE = REGISTER.registerBlock(WWBlockItemIds.ICICLE.block(),
		properties -> new IcicleBlock(WWBlocks.FRAGILE_ICE.get().defaultBlockState(), properties),
		() -> Properties.of().mapColor(MapColor.ICE)
			.forceSolidOn()
			.friction(0.98F)
			.randomTicks()
			.strength(0.2F)
			.sound(SoundType.GLASS)
			.noOcclusion()
			.dynamicShape()
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.POPPED)
			.isRedstoneConductor(Blocks::never)
	);

	// FROGLIGHT GOOP
	public static final FrozenDeferredBlock<FroglightGoopBodyBlock> OCHRE_FROGLIGHT_GOOP_BODY = registerFroglightGoopBody(WWBlockIds.OCHRE_FROGLIGHT_GOOP_BODY, FroglightType.OCHRE, Blocks.OCHRE_FROGLIGHT);
	public static final FrozenDeferredBlock<FroglightGoopBlock> OCHRE_FROGLIGHT_GOOP = registerFroglightGoop(WWBlockItemIds.OCHRE_FROGLIGHT_GOOP, FroglightType.OCHRE, Blocks.OCHRE_FROGLIGHT);
	public static final FrozenDeferredBlock<FroglightGoopBodyBlock> VERDANT_FROGLIGHT_GOOP_BODY = registerFroglightGoopBody(WWBlockIds.VERDANT_FROGLIGHT_GOOP_BODY, FroglightType.VERDANT, Blocks.VERDANT_FROGLIGHT);
	public static final FrozenDeferredBlock<FroglightGoopBlock> VERDANT_FROGLIGHT_GOOP = registerFroglightGoop(WWBlockItemIds.VERDANT_FROGLIGHT_GOOP, FroglightType.VERDANT, Blocks.VERDANT_FROGLIGHT);
	public static final FrozenDeferredBlock<FroglightGoopBodyBlock> PEARLESCENT_FROGLIGHT_GOOP_BODY = registerFroglightGoopBody(WWBlockIds.PEARLESCENT_FROGLIGHT_GOOP_BODY, FroglightType.PEARLESCENT, Blocks.PEARLESCENT_FROGLIGHT);
	public static final FrozenDeferredBlock<FroglightGoopBlock> PEARLESCENT_FROGLIGHT_GOOP = registerFroglightGoop(WWBlockItemIds.PEARLESCENT_FROGLIGHT_GOOP, FroglightType.PEARLESCENT, Blocks.PEARLESCENT_FROGLIGHT);

	static {
		// Fabric: register all the blocks
		// NeoForge: add the RegisterEvent listener
		REGISTER.register();
	}

	public static FrozenDeferredBlock<FroglightGoopBodyBlock> registerFroglightGoopBody(ResourceKey<Block> id, FroglightType froglightType, Block froglightBlock) {
		return REGISTER.registerBlock(id, properties -> new FroglightGoopBodyBlock(froglightType, properties), () -> froglightGoopProperties(froglightBlock));
	}

	public static FrozenDeferredBlock<FroglightGoopBlock> registerFroglightGoop(BlockItemId id, FroglightType froglightType, Block froglightBlock) {
		return REGISTER.registerBlock(id.block(), properties -> new FroglightGoopBlock(froglightType, properties), () -> froglightGoopProperties(froglightBlock));
	}

	private static BlockBehaviour.Properties froglightGoopProperties(Block froglightBlock) {
		return BlockBehaviour.Properties.of()
			.mapColor(froglightBlock.defaultMapColor())
			.randomTicks()
			.instabreak()
			.noCollision()
			.sound(SoundType.FROGLIGHT)
			.lightLevel(state -> 5)
			.pushReaction(PushReaction.POPPED);
	}

	public static void init() {}

	public static FrozenDeferredBlock<Block> registerFlowerPot(ResourceKey<Block> id, Supplier<? extends Block> potted) {
		return REGISTER.registerBlock(id, properties -> new FlowerPotBlock(potted.get(), properties), Blocks::flowerPotProperties);
	}

	public static void setupBlockProperties() {
		registerDispenses();

		var sign = (BlockEntityTypeExtension) BlockEntityTypes.SIGN;
		sign.frozenLib$addValidBlock(BAOBAB_SIGN.get());
		sign.frozenLib$addValidBlock(BAOBAB_WALL_SIGN.get());
		sign.frozenLib$addValidBlock(WILLOW_SIGN.get());
		sign.frozenLib$addValidBlock(WILLOW_WALL_SIGN.get());
		sign.frozenLib$addValidBlock(CYPRESS_SIGN.get());
		sign.frozenLib$addValidBlock(CYPRESS_WALL_SIGN.get());
		sign.frozenLib$addValidBlock(PALM_SIGN.get());
		sign.frozenLib$addValidBlock(PALM_WALL_SIGN.get());
		sign.frozenLib$addValidBlock(MAPLE_SIGN.get());
		sign.frozenLib$addValidBlock(MAPLE_WALL_SIGN.get());

		var hangingSign = (BlockEntityTypeExtension) BlockEntityTypes.HANGING_SIGN;
		hangingSign.frozenLib$addValidBlock(BAOBAB_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(BAOBAB_WALL_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(WILLOW_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(WILLOW_WALL_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(CYPRESS_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(CYPRESS_WALL_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(PALM_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(PALM_WALL_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(MAPLE_HANGING_SIGN.get());
		hangingSign.frozenLib$addValidBlock(MAPLE_WALL_HANGING_SIGN.get());

		var shelf = (BlockEntityTypeExtension) BlockEntityTypes.SHELF;
		shelf.frozenLib$addValidBlock(BAOBAB_SHELF.get());
		shelf.frozenLib$addValidBlock(WILLOW_SHELF.get());
		shelf.frozenLib$addValidBlock(CYPRESS_SHELF.get());
		shelf.frozenLib$addValidBlock(PALM_SHELF.get());
		shelf.frozenLib$addValidBlock(MAPLE_SHELF.get());

		registerFlammability();
		registerBonemeal();
		registerInventories();
		WWFrictionModifications.init();
		WWBlockTicks.setup();
	}

	private static void registerDispenses() {
		DispenserBlock.registerBehavior(TUMBLEWEED, new DefaultDispenseItemBehavior() {
			@Override
			public ItemStack execute(BlockSource source, ItemStack stack) {
				final Level level = source.level();
				final Direction direction = source.state().getValue(DispenserBlock.FACING);
				final Vec3 position = source.center().add(direction.getStepX(), direction.getStepY(), direction.getStepZ());
				final Tumbleweed tumbleweed = new Tumbleweed(WWEntityTypes.TUMBLEWEED.get(), level);
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

	private static void registerFlammability() {
		FlammableBlockRegistry.register(POLLEN.get(), 60, 100);
		FlammableBlockRegistry.register(SEEDING_DANDELION.get(), 60, 100);
		FlammableBlockRegistry.register(CARNATION.get(), 60, 100);
		FlammableBlockRegistry.register(CATTAIL.get(), 60, 100);
		FlammableBlockRegistry.register(DATURA.get(), 60, 100);
		FlammableBlockRegistry.register(MILKWEED.get(), 60, 100);
		FlammableBlockRegistry.register(MARIGOLD.get(), 60, 100);
		FlammableBlockRegistry.register(RED_HIBISCUS.get(), 60, 100);
		FlammableBlockRegistry.register(YELLOW_HIBISCUS.get(), 60, 100);
		FlammableBlockRegistry.register(WHITE_HIBISCUS.get(), 60, 100);
		FlammableBlockRegistry.register(PINK_HIBISCUS.get(), 60, 100);
		FlammableBlockRegistry.register(PURPLE_HIBISCUS.get(), 60, 100);
		FlammableBlockRegistry.register(TUMBLEWEED.get(), 60, 100);
		FlammableBlockRegistry.register(TUMBLEWEED_PLANT.get(), 60, 100);
		FlammableBlockRegistry.register(SHRUB.get(), 40, 90);
		FlammableBlockRegistry.register(MYCELIUM_GROWTH.get(), 60, 100);
		FlammableBlockRegistry.register(LANTANAS.get(), 60, 100);
		FlammableBlockRegistry.register(PHLOX.get(), 60, 100);
		FlammableBlockRegistry.register(CLOVERS.get(), 60, 100);

		FlammableBlockRegistry.register(FROZEN_SHORT_GRASS.get(), 60, 100);
		FlammableBlockRegistry.register(FROZEN_TALL_GRASS.get(), 60, 100);
		FlammableBlockRegistry.register(FROZEN_FERN.get(), 60, 100);
		FlammableBlockRegistry.register(FROZEN_LARGE_FERN.get(), 60, 100);
		FlammableBlockRegistry.register(FROZEN_BUSH.get(), 60, 100);

		FlammableBlockRegistry.register(HOLLOWED_BIRCH_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_CHERRY_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_OAK_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_ACACIA_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_JUNGLE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_DARK_OAK_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_MANGROVE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_CHERRY_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_SPRUCE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_PALE_OAK_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(HOLLOWED_POPLAR_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_BIRCH_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_CHERRY_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_OAK_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_ACACIA_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_JUNGLE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_DARK_OAK_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_MANGROVE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_SPRUCE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_PALE_OAK_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_POPLAR_LOG.get(), 5, 5);

		FlammableBlockRegistry.register(HOLLOWED_BAOBAB_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_BAOBAB_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(BAOBAB_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_BAOBAB_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(BAOBAB_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_BAOBAB_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(BAOBAB_PLANKS.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_STAIRS.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_DOOR.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_FENCE.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_SLAB.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_FENCE_GATE.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_PRESSURE_PLATE.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_TRAPDOOR.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_LEAVES.get(), 30, 60);
		FlammableBlockRegistry.register(BAOBAB_BUTTON.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_WALL_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_WALL_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(BAOBAB_SHELF.get(), 30, 20);

		FlammableBlockRegistry.register(HOLLOWED_WILLOW_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_WILLOW_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(WILLOW_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_WILLOW_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(WILLOW_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_WILLOW_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(WILLOW_PLANKS.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_STAIRS.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_DOOR.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_FENCE.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_SLAB.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_FENCE_GATE.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_PRESSURE_PLATE.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_TRAPDOOR.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_LEAVES.get(), 30, 60);
		FlammableBlockRegistry.register(WILLOW_BUTTON.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_WALL_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_WALL_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(WILLOW_SHELF.get(), 30, 20);

		FlammableBlockRegistry.register(HOLLOWED_CYPRESS_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_CYPRESS_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(CYPRESS_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_CYPRESS_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(CYPRESS_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_CYPRESS_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(CYPRESS_PLANKS.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_STAIRS.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_DOOR.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_FENCE.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_SLAB.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_FENCE_GATE.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_PRESSURE_PLATE.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_TRAPDOOR.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_LEAVES.get(), 30, 60);
		FlammableBlockRegistry.register(CYPRESS_BUTTON.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_WALL_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_WALL_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(CYPRESS_SHELF.get(), 30, 20);

		FlammableBlockRegistry.register(HOLLOWED_PALM_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_PALM_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(PALM_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_PALM_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(PALM_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_PALM_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(PALM_PLANKS.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_STAIRS.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_DOOR.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_FENCE.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_SLAB.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_FENCE_GATE.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_PRESSURE_PLATE.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_TRAPDOOR.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_FRONDS.get(), 30, 60);
		FlammableBlockRegistry.register(PALM_BUTTON.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_WALL_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_WALL_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(PALM_SHELF.get(), 30, 20);

		FlammableBlockRegistry.register(HOLLOWED_MAPLE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_HOLLOWED_MAPLE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(MAPLE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_MAPLE_LOG.get(), 5, 5);
		FlammableBlockRegistry.register(MAPLE_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(STRIPPED_MAPLE_WOOD.get(), 5, 5);
		FlammableBlockRegistry.register(MAPLE_PLANKS.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_STAIRS.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_DOOR.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_FENCE.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_SLAB.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_FENCE_GATE.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_PRESSURE_PLATE.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_TRAPDOOR.get(), 5, 20);
		MAPLE_LEAVES.forEach(leaves -> FlammableBlockRegistry.register(leaves.get(), 30, 60));
		FlammableBlockRegistry.register(MAPLE_BUTTON.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_WALL_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_WALL_HANGING_SIGN.get(), 5, 20);
		FlammableBlockRegistry.register(MAPLE_SHELF.get(), 30, 20);

		FlammableBlockRegistry.register(ACACIA_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(AZALEA_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(BAOBAB_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(BIRCH_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(CHERRY_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(CYPRESS_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(DARK_OAK_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(JUNGLE_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(MANGROVE_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(PALE_OAK_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(PALM_FROND_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(SPRUCE_LEAF_LITTER.get(), 60, 100);
		FlammableBlockRegistry.register(WILLOW_LEAF_LITTER.get(), 60, 100);
		MAPLE_LEAF_LITTER.forEach(leafLitter -> FlammableBlockRegistry.register(leafLitter.get(), 60, 100));
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
					level.setBlock(pos, FLOWERING_LILY_PAD.get().defaultBlockState(), Block.UPDATE_CLIENTS);
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
					level.setBlock(pos, SEEDING_DANDELION.get().defaultBlockState(), Block.UPDATE_CLIENTS);
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

	private static void registerInventories() {
		HopperApi.addBlacklistedType(WWBlockEntityTypes.STONE_CHEST.get());
	}
}
