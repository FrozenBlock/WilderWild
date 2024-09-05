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

package net.frozenblock.wilderwild.mod_compat;

import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.block.api.dripstone.DripstoneDripWaterFrom;
import net.frozenblock.lib.block.api.dripstone.DripstoneUtils;
import net.frozenblock.lib.block.api.entity.BlockEntityWithoutLevelRendererRegistry;
import net.frozenblock.lib.block.api.tick.BlockScheduledTicks;
import net.frozenblock.lib.entity.api.WolfVariantBiomeRegistry;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.item.api.removable.RemovableItemTags;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlocks;
import net.frozenblock.lib.sound.api.damagesource.PlayerDamageSourceSounds;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.lib.spotting_icons.api.SpottingIconPredicate;
import net.frozenblock.lib.storage.api.HopperUntouchableList;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.lib.wind.api.WindDisturbance;
import net.frozenblock.lib.wind.api.WindDisturbanceLogic;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.lib.worldgen.structure.api.RandomPoolAliasApi;
import net.frozenblock.lib.worldgen.structure.api.StructureProcessorApi;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.GeyserBlockEntity;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.WWBlockEntities;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import static net.frozenblock.wilderwild.registry.WWBlocks.*;
import net.frozenblock.wilderwild.registry.WWEntities;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.*;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WWWorldgen;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.frozenblock.wilderwild.wind.WWWindManager;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import static net.minecraft.world.level.block.Blocks.CLAY;
import static net.minecraft.world.level.block.Blocks.FROSTED_ICE;
import static net.minecraft.world.level.block.Blocks.GRAVEL;
import static net.minecraft.world.level.block.Blocks.ICE;
import static net.minecraft.world.level.block.Blocks.SANDSTONE;
import static net.minecraft.world.level.block.Blocks.*;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FrozenLibIntegration extends ModIntegration {
	public static final ResourceLocation INSTRUMENT_SOUND_PREDICATE = WWConstants.id("instrument");
	public static final ResourceLocation NECTAR_SOUND_PREDICATE = WWConstants.id("nectar");
	public static final ResourceLocation ENDERMAN_ANGER_SOUND_PREDICATE = WWConstants.id("enderman_anger");
	public static final ResourceLocation GEYSER_EFFECTIVE_WIND_DISTURBANCE = WWConstants.id("geyser_effective");
	public static final ResourceLocation GEYSER_BASE_WIND_DISTURBANCE = WWConstants.id("geyser");

	public FrozenLibIntegration() {
		super("frozenlib");
	}

	private static void addBiomeRequirement(@NotNull Advancement advancement, @NotNull Holder<Biome> holder) {
		AdvancementAPI.addCriteria(advancement, holder.unwrapKey().orElseThrow().location().toString(), inBiome(holder));
		AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(holder.unwrapKey().orElseThrow().location().toString()))));
	}

	private static void addBiomeRequirement(@NotNull Advancement advancement, @NotNull ResourceKey<Biome> key, HolderLookup.Provider registries) {
		addBiomeRequirement(advancement, registries.lookupOrThrow(Registries.BIOME).getOrThrow(key));
	}

	@NotNull
	private static Criterion<PlayerTrigger.TriggerInstance> inBiome(Holder<Biome> holder) {
		return PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(holder));
	}

	@Override
	public void initPreFreeze() {
		WWConstants.log("FrozenLib pre-freeze mod integration ran!", WWConstants.UNSTABLE_LOGGING);
		SpottingIconPredicate.register(WWConstants.id("stella"), entity -> entity.hasCustomName() && entity.getCustomName().getString().equalsIgnoreCase("stella"));
		SoundPredicate.register(INSTRUMENT_SOUND_PREDICATE, () -> new SoundPredicate.LoopPredicate<LivingEntity>() {

			private boolean firstCheck = true;
			private ItemStack lastStack;

			@Override
			public Boolean firstTickTest(LivingEntity entity) {
				return true;
			}

			@Override
			public boolean test(LivingEntity entity) {
				if (firstCheck) {
					firstCheck = false;
					InteractionHand hand = !entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() ? InteractionHand.MAIN_HAND : !entity.getItemInHand(InteractionHand.OFF_HAND).isEmpty() ? InteractionHand.OFF_HAND : null;
					if (hand == null) return false;

					ItemStack stack = entity.getItemInHand(hand);
					if (stack.getItem() instanceof InstrumentItem) {
						this.lastStack = stack;
						return true;
					}
					return false;
				}
				var stack = entity.getUseItem();
				if (stack.getItem() instanceof InstrumentItem) {
					if (this.lastStack == null || ItemStack.matches(this.lastStack, stack)) {
						this.lastStack = stack;
						return true;
					} else {
						this.firstCheck = true;
					}
				}
				return false;
			}
		});

		SoundPredicate.register(NECTAR_SOUND_PREDICATE, () -> (SoundPredicate.LoopPredicate<Firefly>) entity ->
			!entity.isSilent() && entity.hasCustomName() && Objects.requireNonNull(entity.getCustomName()).getString().toLowerCase().contains("nectar")
		);

		SoundPredicate.register(ENDERMAN_ANGER_SOUND_PREDICATE, () -> (SoundPredicate.LoopPredicate<EnderMan>) entity -> {
			if (entity.isSilent() || entity.isRemoved() || entity.isDeadOrDying()) {
				return false;
			}
			return entity.isCreepy() || entity.hasBeenStaredAt();
		});

		WindDisturbanceLogic.register(
			GEYSER_EFFECTIVE_WIND_DISTURBANCE,
                (WindDisturbanceLogic.DisturbanceLogic<GeyserBlockEntity>) (source, level, windOrigin, affectedArea, windTarget) -> {
				if (source.isPresent()) {
					BlockState state = level.getBlockState(source.get().getBlockPos());
					if (state.hasProperty(BlockStateProperties.FACING)) {
						Direction direction = state.getValue(BlockStateProperties.FACING);
						Vec3 movement = Vec3.atLowerCornerOf(direction.getNormal());
						double strength = GeyserBlockEntity.ERUPTION_DISTANCE - Math.min(windTarget.distanceTo(windOrigin), GeyserBlockEntity.ERUPTION_DISTANCE);
						double intensity = strength / GeyserBlockEntity.ERUPTION_DISTANCE;
						return new WindDisturbance.DisturbanceResult(
							Mth.clamp(intensity * 2D, 0D, 1D),
							strength * 2D,
							movement.scale(intensity * GeyserBlockEntity.EFFECTIVE_ADDITIONAL_WIND_INTENSITY).scale(30D)
						);
					}
				}
				return null;
			}
        );

		WindDisturbanceLogic.register(
			GEYSER_BASE_WIND_DISTURBANCE,
			(WindDisturbanceLogic.DisturbanceLogic<GeyserBlockEntity>) (source, level, windOrigin, affectedArea, windTarget) -> {
				if (source.isPresent()) {
					BlockState state = level.getBlockState(source.get().getBlockPos());
					if (state.hasProperty(BlockStateProperties.FACING)) {
						Direction direction = state.getValue(BlockStateProperties.FACING);
						Vec3 movement = Vec3.atLowerCornerOf(direction.getNormal());
						double strength = GeyserBlockEntity.ERUPTION_DISTANCE - Math.min(windTarget.distanceTo(windOrigin), GeyserBlockEntity.ERUPTION_DISTANCE);
						double intensity = strength / GeyserBlockEntity.ERUPTION_DISTANCE;
						return new WindDisturbance.DisturbanceResult(
							Mth.clamp(intensity * 2D, 0D, 1D),
							strength * 2D,
							movement.scale(intensity * GeyserBlockEntity.BASE_WIND_INTENSITY).scale(30D)
						);
					}
				}
				return null;
			}
		);
	}

	@Override
	public void init() {
		WWConstants.log("FrozenLib mod integration ran!", WWConstants.UNSTABLE_LOGGING);

		ServerWorldEvents.LOAD.register((server, level) -> PlayerDamageSourceSounds.addDamageSound(
			level.damageSources().cactus(),
			WWSounds.PLAYER_HURT_CACTUS,
			WWConstants.id("cactus")
			)
		);

		HopperUntouchableList.BLACKLISTED_TYPES.add(WWBlockEntities.STONE_CHEST);

		FrozenBools.useNewDripstoneLiquid = true;
		DripstoneDripWaterFrom.ON_DRIP_BLOCK.put(Blocks.WET_SPONGE, (level, fluidInfo, blockPos) -> {
			BlockState blockState = Blocks.SPONGE.defaultBlockState();
			level.setBlockAndUpdate(fluidInfo.pos(), blockState);
			Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
			level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
			level.levelEvent(LevelEvent.DRIPSTONE_DRIP, blockPos, 0);
		});
		DripstoneDripWaterFrom.ON_DRIP_BLOCK.put(Blocks.MUD, (level, fluidInfo, blockPos) -> {
			BlockState blockState = CLAY.defaultBlockState();
			level.setBlockAndUpdate(fluidInfo.pos(), blockState);
			Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
			level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
			level.levelEvent(LevelEvent.DRIPSTONE_DRIP, blockPos, 0);
		});
		BlockScheduledTicks.TICKS.put(Blocks.DIRT, (blockState, serverLevel, blockPos, randomSource) -> {
			if (DripstoneUtils.getDripstoneFluid(serverLevel, blockPos) == Fluids.WATER) {
				serverLevel.setBlock(blockPos, Blocks.MUD.defaultBlockState(), 3);
			}
		});

		WindManager.addExtension(WWWindManager::new);
		RemovableItemTags.register("wilderwild_is_ancient", (level, entity, slot, selected) -> true, true);

		addBlocks(new Block[]{CACTUS, PRICKLY_PEAR_CACTUS}, CACTI, () -> WWBlockConfig.get().blockSounds.cactusSounds);
		addBlock(CLAY, WWSoundTypes.CLAY, () -> WWBlockConfig.get().blockSounds.claySounds);
		addBlock(COARSE_DIRT, COARSEDIRT, () -> WWBlockConfig.get().blockSounds.coarseDirtSounds);
		addBlock(DEAD_BUSH, SoundType.NETHER_SPROUTS, () -> WWBlockConfig.get().blockSounds.deadBushSounds);
		addBlocks(new Block[]{DANDELION,
			POPPY,
			BLUE_ORCHID,
			ALLIUM,
			AZURE_BLUET,
			RED_TULIP,
			ORANGE_TULIP,
			WHITE_TULIP,
			PINK_TULIP,
			OXEYE_DAISY,
			CORNFLOWER,
			LILY_OF_THE_VALLEY,
			SEEDING_DANDELION,
			CARNATION,
			MARIGOLD,
			GLORY_OF_THE_SNOW,
			DATURA,
			MILKWEED,
			SUNFLOWER,
			ROSE_BUSH,
			PEONY,
			LILAC,
			TORCHFLOWER,
			PINK_PETALS
		}, FLOWER, () -> WWBlockConfig.get().blockSounds.flowerSounds);
		addBlocks(new Block[]{ICE, BLUE_ICE, PACKED_ICE}, WWSoundTypes.ICE, () -> WWBlockConfig.get().blockSounds.iceSounds);
		addBlock(FROSTED_ICE, WWSoundTypes.FROSTED_ICE, () -> WWBlockConfig.get().blockSounds.frostedIceSounds);
		addBlock(GRAVEL, WWSoundTypes.GRAVEL, () -> WWBlockConfig.get().blockSounds.gravelSounds);
		addBlocks(new Block[]{
			ACACIA_SAPLING,
			BIRCH_SAPLING,
			DARK_OAK_SAPLING,
			JUNGLE_SAPLING,
			MANGROVE_PROPAGULE,
			OAK_SAPLING,
			SPRUCE_SAPLING,
			CYPRESS_SAPLING,
			MAPLE_SAPLING,
			BUSH
		}, SAPLING, () -> WWBlockConfig.get().blockSounds.saplingSounds);
		addBlocks(new Block[]{
			ACACIA_LEAVES,
			BIRCH_LEAVES,
			DARK_OAK_LEAVES,
			JUNGLE_LEAVES,
			MANGROVE_LEAVES,
			OAK_LEAVES,
			SPRUCE_LEAVES,
			BAOBAB_LEAVES,
			CYPRESS_LEAVES,
			PALM_FRONDS,
			MAPLE_LEAVES
		}, LEAVES, () -> WWBlockConfig.get().blockSounds.leafSounds);
		addBlocks(new Block[]{
			OAK_LEAF_LITTER,
			MAPLE_LEAF_LITTER
		}, LEAVES, () -> WWBlockConfig.get().blockSounds.leafSounds);
		addBlocks(new Block[]{LILY_PAD, FLOWERING_LILY_PAD}, LILYPAD, () -> WWBlockConfig.get().blockSounds.lilyPadSounds);
		addBlocks(new Block[]{RED_MUSHROOM, BROWN_MUSHROOM}, MUSHROOM, () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds);
		addBlocks(new Block[]{RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK, MUSHROOM_STEM}, MUSHROOM_BLOCK, () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds);
		addBlock(PODZOL, SoundType.ROOTED_DIRT, () -> WWBlockConfig.get().blockSounds.podzolSounds);
		addBlock(REINFORCED_DEEPSLATE, REINFORCEDDEEPSLATE, () -> WWBlockConfig.get().blockSounds.reinforcedDeepslateSounds);
		addBlocks(new Block[]{SANDSTONE, SANDSTONE_SLAB, SANDSTONE_STAIRS, SANDSTONE_WALL, CHISELED_SANDSTONE, CUT_SANDSTONE, SMOOTH_SANDSTONE, SMOOTH_SANDSTONE_SLAB, SMOOTH_SANDSTONE_STAIRS, RED_SANDSTONE, RED_SANDSTONE_SLAB, RED_SANDSTONE_STAIRS, RED_SANDSTONE_WALL, CHISELED_RED_SANDSTONE, CUT_RED_SANDSTONE, SMOOTH_RED_SANDSTONE, SMOOTH_RED_SANDSTONE_SLAB, SMOOTH_RED_SANDSTONE_STAIRS}, WWSoundTypes.SANDSTONE, () -> WWBlockConfig.get().blockSounds.sandstoneSounds);
		addBlock(SUGAR_CANE, SUGARCANE, () -> WWBlockConfig.get().blockSounds.sugarCaneSounds);
		addBlock(WITHER_ROSE, SoundType.SWEET_BERRY_BUSH, () -> WWBlockConfig.get().blockSounds.witherRoseSounds);
		addBlock(MAGMA_BLOCK, MAGMA, () -> WWBlockConfig.get().blockSounds.magmaSounds);

		WolfVariantBiomeRegistry.register(WWWorldgen.SNOWY_DYING_MIXED_FOREST, WolfVariants.ASHEN);
		WolfVariantBiomeRegistry.register(WWWorldgen.RAINFOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWWorldgen.SEMI_BIRCH_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWWorldgen.DYING_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWWorldgen.MIXED_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWWorldgen.PARCHED_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWWorldgen.OLD_GROWTH_BIRCH_TAIGA, WolfVariants.PALE);
		WolfVariantBiomeRegistry.register(WWWorldgen.BIRCH_TAIGA, WolfVariants.PALE);
		WolfVariantBiomeRegistry.register(WWWorldgen.DYING_MIXED_FOREST, WolfVariants.PALE);
		WolfVariantBiomeRegistry.register(WWWorldgen.DARK_TAIGA, WolfVariants.PALE);
		WolfVariantBiomeRegistry.register(WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA, WolfVariants.BLACK);
		WolfVariantBiomeRegistry.register(WWWorldgen.TEMPERATE_RAINFOREST, WolfVariants.CHESTNUT);

		BlockEntityWithoutLevelRendererRegistry.register(WWBlocks.STONE_CHEST, WWBlockEntities.STONE_CHEST);

		if (WWWorldgenConfig.get().decayTrailRuins) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.TRAIL_RUINS.location(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(MUD_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, CRACKED_MUD_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(MUD_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, MOSSY_MUD_BRICKS.defaultBlockState())
					)
				)
			);
			StructureProcessorApi.addProcessor(
				BuiltinStructures.TRAIL_RUINS.location(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(MUD_BRICK_STAIRS, 0.05F), AlwaysTrueTest.INSTANCE, MOSSY_MUD_BRICK_STAIRS),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, MOSSY_MUD_BRICK_SLAB),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, MOSSY_MUD_BRICK_WALL)
					)
				)
			);
		}

		if (WWWorldgenConfig.get().newDesertVillages) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.VILLAGE_DESERT.location(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_BUTTON), AlwaysTrueTest.INSTANCE, PALM_BUTTON),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_DOOR), AlwaysTrueTest.INSTANCE, PALM_DOOR),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_FENCE), AlwaysTrueTest.INSTANCE, PALM_FENCE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_FENCE_GATE), AlwaysTrueTest.INSTANCE, PALM_FENCE_GATE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_HANGING_SIGN), AlwaysTrueTest.INSTANCE, PALM_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_WALL_HANGING_SIGN), AlwaysTrueTest.INSTANCE, PALM_WALL_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_SIGN), AlwaysTrueTest.INSTANCE, PALM_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_HANGING_SIGN), AlwaysTrueTest.INSTANCE, PALM_WALL_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_LOG), AlwaysTrueTest.INSTANCE, PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_WOOD), AlwaysTrueTest.INSTANCE, PALM_WOOD),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(STRIPPED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, STRIPPED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(STRIPPED_JUNGLE_WOOD), AlwaysTrueTest.INSTANCE, STRIPPED_PALM_WOOD),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(HOLLOWED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, HOLLOWED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(STRIPPED_HOLLOWED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, STRIPPED_HOLLOWED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_PLANKS), AlwaysTrueTest.INSTANCE, PALM_PLANKS),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_PRESSURE_PLATE), AlwaysTrueTest.INSTANCE, PALM_PRESSURE_PLATE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_SLAB), AlwaysTrueTest.INSTANCE, PALM_SLAB),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_STAIRS), AlwaysTrueTest.INSTANCE, PALM_STAIRS),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_SAPLING), AlwaysTrueTest.INSTANCE, WWBlocks.COCONUT),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(JUNGLE_LEAVES), AlwaysTrueTest.INSTANCE, PALM_FRONDS)

					)
				)
			);
		}

		if (WWEntityConfig.get().scorched.scorchedInTrialChambers) {
			RandomPoolAliasApi.addTarget(
				WWConstants.vanillaId("trial_chambers/spawner/contents/small_melee"),
				WWConstants.id("trial_chambers/spawner/small_melee/scorched"),
				1
			);
		}

		AdvancementEvents.INIT.register((holder, registries) -> {
			Advancement advancement = holder.value();
			if (WWAmbienceAndMiscConfig.get().modifyAdvancements) {
				switch (holder.id().toString()) {
					case "minecraft:adventure/adventuring_time" -> {
						addBiomeRequirement(advancement, WWWorldgen.CYPRESS_WETLANDS, registries);
						addBiomeRequirement(advancement, WWWorldgen.MIXED_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.OASIS, registries);
						addBiomeRequirement(advancement, WWWorldgen.WARM_RIVER, registries);
						addBiomeRequirement(advancement, WWWorldgen.WARM_BEACH, registries);
						addBiomeRequirement(advancement, WWWorldgen.FROZEN_CAVES, registries);
						addBiomeRequirement(advancement, WWWorldgen.JELLYFISH_CAVES, registries);
						addBiomeRequirement(advancement, WWWorldgen.MAGMATIC_CAVES, registries);
						addBiomeRequirement(advancement, WWWorldgen.ARID_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.ARID_SAVANNA, registries);
						addBiomeRequirement(advancement, WWWorldgen.PARCHED_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.BIRCH_JUNGLE, registries);
						addBiomeRequirement(advancement, WWWorldgen.SPARSE_BIRCH_JUNGLE, registries);
						addBiomeRequirement(advancement, WWWorldgen.BIRCH_TAIGA, registries);
						addBiomeRequirement(advancement, WWWorldgen.SEMI_BIRCH_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.DARK_BIRCH_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.FLOWER_FIELD, registries);
						addBiomeRequirement(advancement, WWWorldgen.TEMPERATE_RAINFOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.RAINFOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.DARK_TAIGA, registries);
						addBiomeRequirement(advancement, WWWorldgen.OLD_GROWTH_BIRCH_TAIGA, registries);
						addBiomeRequirement(advancement, WWWorldgen.OLD_GROWTH_DARK_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA, registries);
						addBiomeRequirement(advancement, WWWorldgen.DYING_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.SNOWY_DYING_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.DYING_MIXED_FOREST, registries);
						addBiomeRequirement(advancement, WWWorldgen.SNOWY_DYING_MIXED_FOREST, registries);
					}
					case "minecraft:husbandry/balanced_diet" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:baobab_nut", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(WWItems.BAOBAB_NUT).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:split_coconut", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(WWItems.SPLIT_COCONUT).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab_claw", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(WWItems.CRAB_CLAW).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:cooked_crab_claw", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(WWItems.COOKED_CRAB_CLAW).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:prickly_pear", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(WWItems.PRICKLY_PEAR).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:peeled_prickly_pear", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(WWItems.PEELED_PRICKLY_PEAR).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched_eye", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(WWItems.SCORCHED_EYE).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									"wilderwild:baobab_nut"
								)
							))
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									"wilderwild:split_coconut"
								)
							))
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									"wilderwild:crab_claw"
								)
							))
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									"wilderwild:cooked_crab_claw"
								)
							))
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									"wilderwild:prickly_pear"
								)
							))
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									"wilderwild:peeled_prickly_pear"
								)
							))
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									"wilderwild:scorched_eye"
								)
							))
						);
					}
					case "minecraft:husbandry/bred_all_animals" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(WWEntities.CRAB)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new
								AdvancementRequirements(List.of(
								List.of(
									"wilderwild:crab"
								)
							))
						);
					}
					case "minecraft:husbandry/tactical_fishing" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab_bucket", CriteriaTriggers.FILLED_BUCKET.createCriterion(
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(WWItems.CRAB_BUCKET)).triggerInstance())
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:jellyfish_bucket", CriteriaTriggers.FILLED_BUCKET.createCriterion(
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(WWItems.JELLYFISH_BUCKET)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								"wilderwild:crab_bucket",
								"wilderwild:jellyfish_bucket"
							)
						);
					}
					case "minecraft:nether/all_potions", "minecraft:nether/all_effects" -> {
						if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
							Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
							MobEffectsPredicate predicate = criterion.triggerInstance().effects.orElseThrow();
							Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap);
							map.put(WWMobEffects.REACH_BOOST, new MobEffectsPredicate.MobEffectInstancePredicate());
							map.put(WWMobEffects.SCORCHING, new MobEffectsPredicate.MobEffectInstancePredicate());
							predicate.effectMap = map;
						}
					}
					case "minecraft:adventure/kill_a_mob" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(WWEntities.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								"wilderwild:scorched"
							)
						);
					}
					case "minecraft:adventure/kill_all_mobs" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(WWEntities.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement,
							new AdvancementRequirements(List.of(
								List.of(
									"wilderwild:scorched"
								)
							))
						);
					}
					default -> {
					}
				}

			}
		});

		if (WWWorldgenConfig.get().addStoneChests) {
			StructureProcessorApi.addProcessor(BuiltinStructures.ANCIENT_CITY.location(),
				new RuleProcessor(
					List.of(
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState()),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(WWBlockStateProperties.ANCIENT, true)
						),

						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.LEFT)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.LEFT).setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.LEFT)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.LEFT).setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.LEFT)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.LEFT).setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.LEFT)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.LEFT).setValue(WWBlockStateProperties.ANCIENT, true)
						),

						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.RIGHT)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.RIGHT).setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.RIGHT).setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.RIGHT).setValue(WWBlockStateProperties.ANCIENT, true)
						),
						new ProcessorRule(
							new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
							AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.RIGHT).setValue(WWBlockStateProperties.ANCIENT, true)
						)
					)
				)
			);
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void clientInit() {
		ClientWindManager.addExtension(WWClientWindManager::new);
	}
}
