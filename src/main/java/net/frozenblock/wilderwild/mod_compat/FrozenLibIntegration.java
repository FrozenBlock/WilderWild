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

package net.frozenblock.wilderwild.mod_compat;

import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.block.api.dripstone.DripstoneDripApi;
import net.frozenblock.lib.block.api.entity.BlockEntityWithoutLevelRendererRegistry;
import net.frozenblock.lib.block.api.tick.BlockScheduledTicks;
import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.lib.block.storage.api.hopper.HopperApi;
import net.frozenblock.lib.entity.api.WolfVariantBiomeRegistry;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.item.api.removable.RemovableItemTags;
import net.frozenblock.lib.sound.api.damage.PlayerDamageTypeSounds;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.lib.spotting_icons.api.SpottingIconPredicate;
import net.frozenblock.lib.wind.api.WindDisturbance;
import net.frozenblock.lib.wind.api.WindDisturbanceLogic;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
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
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

	private static void addBiomeRequirement(@NotNull Advancement advancement, @NotNull ResourceKey<Biome> key, HolderLookup.@NotNull Provider registries) {
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

		ServerWorldEvents.LOAD.register(
			(server, level) -> PlayerDamageTypeSounds.addDamageSound(
				level.damageSources().damageTypes.getOrThrow(DamageTypes.CACTUS),
				WWSounds.PLAYER_HURT_CACTUS,
				WWConstants.id("cactus")
			)
		);

		HopperApi.addBlacklistedType(WWBlockEntityTypes.STONE_CHEST);

		DripstoneDripApi.addWaterDrip(
			Blocks.WET_SPONGE,
			(level, pos, fluidInfo) -> {
				BlockState blockState = Blocks.SPONGE.defaultBlockState();
				level.setBlockAndUpdate(fluidInfo.pos(), blockState);
				Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
				level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
				level.levelEvent(LevelEvent.DRIPSTONE_DRIP, pos, 0);
			});
		DripstoneDripApi.addWaterDrip(
			Blocks.MUD,
			(level, pos, fluidInfo) -> {
				BlockState blockState = Blocks.CLAY.defaultBlockState();
				level.setBlockAndUpdate(fluidInfo.pos(), blockState);
				Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
				level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
				level.levelEvent(LevelEvent.DRIPSTONE_DRIP, pos, 0);
			}
		);

		BlockScheduledTicks.addToBlock(
			Blocks.DIRT,
			(blockState, serverLevel, blockPos, randomSource) -> {
				if (DripstoneDripApi.getDripstoneFluid(serverLevel, blockPos) == Fluids.WATER) {
					serverLevel.setBlock(blockPos, Blocks.MUD.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		);

		WindManager.addExtension(WWWindManager::new);
		RemovableItemTags.register("wilderwild_is_ancient", (level, entity, slot, selected) -> true, true);

		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_FLOWER, WWSoundTypes.FLOWER, () -> WWBlockConfig.get().blockSounds.flowerSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_LEAVES, WWSoundTypes.LEAVES, () -> WWBlockConfig.get().blockSounds.leafSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_SAPLING, WWSoundTypes.SAPLING, () -> WWBlockConfig.get().blockSounds.saplingSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_CACTUS, WWSoundTypes.CACTUS, () -> WWBlockConfig.get().blockSounds.cactusSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_COARSE_DIRT, WWSoundTypes.COARSE_DIRT, () -> WWBlockConfig.get().blockSounds.coarseDirtSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_ICE, WWSoundTypes.ICE, () -> WWBlockConfig.get().blockSounds.iceSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_FROSTED_ICE, WWSoundTypes.FROSTED_ICE, () -> WWBlockConfig.get().blockSounds.frostedIceSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MUSHROOM, WWSoundTypes.MUSHROOM, () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MUSHROOM_BLOCK, WWSoundTypes.MUSHROOM_BLOCK, () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_SANDSTONE, WWSoundTypes.SANDSTONE, () -> WWBlockConfig.get().blockSounds.sandstoneSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_LILY_PAD, WWSoundTypes.LILY_PAD, () -> WWBlockConfig.get().blockSounds.lilyPadSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MELON, WWSoundTypes.MELON, () -> WWBlockConfig.get().blockSounds.melonSounds);

		BlockSoundTypeOverwrites.addBlock(Blocks.CLAY, WWSoundTypes.CLAY, () -> WWBlockConfig.get().blockSounds.claySounds);
		BlockSoundTypeOverwrites.addBlock(Blocks.DEAD_BUSH, SoundType.NETHER_SPROUTS, () -> WWBlockConfig.get().blockSounds.deadBushSounds);
		BlockSoundTypeOverwrites.addBlock(Blocks.GRAVEL, WWSoundTypes.GRAVEL, () -> WWBlockConfig.get().blockSounds.gravelSounds);
		BlockSoundTypeOverwrites.addBlock(Blocks.PODZOL, SoundType.ROOTED_DIRT, () -> WWBlockConfig.get().blockSounds.podzolSounds);
		BlockSoundTypeOverwrites.addBlock(Blocks.REINFORCED_DEEPSLATE, WWSoundTypes.REINFORCED_DEEPSLATE, () -> WWBlockConfig.get().blockSounds.reinforcedDeepslateSounds);
		BlockSoundTypeOverwrites.addBlock(Blocks.SUGAR_CANE, WWSoundTypes.SUGARCANE, () -> WWBlockConfig.get().blockSounds.sugarCaneSounds);
		BlockSoundTypeOverwrites.addBlock(Blocks.WITHER_ROSE, SoundType.SWEET_BERRY_BUSH, () -> WWBlockConfig.get().blockSounds.witherRoseSounds);
		BlockSoundTypeOverwrites.addBlock(Blocks.MAGMA_BLOCK, WWSoundTypes.MAGMA, () -> WWBlockConfig.get().blockSounds.magmaSounds);

		WolfVariantBiomeRegistry.register(WWBiomes.SNOWY_DYING_MIXED_FOREST, WolfVariants.ASHEN);
		WolfVariantBiomeRegistry.register(WWBiomes.RAINFOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWBiomes.SEMI_BIRCH_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWBiomes.DYING_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWBiomes.MIXED_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWBiomes.SPARSE_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWBiomes.PARCHED_FOREST, WolfVariants.WOODS);
		WolfVariantBiomeRegistry.register(WWBiomes.OLD_GROWTH_BIRCH_TAIGA, WolfVariants.PALE);
		WolfVariantBiomeRegistry.register(WWBiomes.BIRCH_TAIGA, WolfVariants.PALE);
		WolfVariantBiomeRegistry.register(WWBiomes.DYING_MIXED_FOREST, WolfVariants.PALE);
		WolfVariantBiomeRegistry.register(WWBiomes.DARK_TAIGA, WolfVariants.PALE);
		WolfVariantBiomeRegistry.register(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, WolfVariants.BLACK);
		WolfVariantBiomeRegistry.register(WWBiomes.TEMPERATE_RAINFOREST, WolfVariants.CHESTNUT);
		WolfVariantBiomeRegistry.register(WWBiomes.MAPLE_FOREST, WolfVariants.CHESTNUT);

		BlockEntityWithoutLevelRendererRegistry.register(WWBlocks.STONE_CHEST, WWBlockEntityTypes.STONE_CHEST);

		if (WWWorldgenConfig.get().decayTrailRuins) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.TRAIL_RUINS.location(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, WWBlocks.CRACKED_MUD_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICKS.defaultBlockState())
					)
				)
			);
			StructureProcessorApi.addProcessor(
				BuiltinStructures.TRAIL_RUINS.location(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_STAIRS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_STAIRS),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_SLAB),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_WALL)
					)
				)
			);
		}

		if (WWWorldgenConfig.get().newDesertVillages) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.VILLAGE_DESERT.location(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_BUTTON), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_BUTTON),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_DOOR), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_DOOR),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_FENCE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FENCE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_FENCE_GATE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FENCE_GATE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_WALL_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WALL_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_HANGING_SIGN), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WALL_HANGING_SIGN),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_WOOD), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_WOOD),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.STRIPPED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.STRIPPED_JUNGLE_WOOD), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_PALM_WOOD),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(WWBlocks.HOLLOWED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.HOLLOWED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG), AlwaysTrueTest.INSTANCE, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_PLANKS), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_PLANKS),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_PRESSURE_PLATE), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_PRESSURE_PLATE),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SLAB), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_SLAB),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_STAIRS), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_STAIRS),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_SAPLING), AlwaysTrueTest.INSTANCE, WWBlocks.COCONUT),
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.JUNGLE_LEAVES), AlwaysTrueTest.INSTANCE, WWBlocks.PALM_FRONDS)

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
						addBiomeRequirement(advancement, WWBiomes.CYPRESS_WETLANDS, registries);
						addBiomeRequirement(advancement, WWBiomes.MIXED_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.OASIS, registries);
						addBiomeRequirement(advancement, WWBiomes.WARM_RIVER, registries);
						addBiomeRequirement(advancement, WWBiomes.WARM_BEACH, registries);
						addBiomeRequirement(advancement, WWBiomes.FROZEN_CAVES, registries);
						addBiomeRequirement(advancement, WWBiomes.MESOGLEA_CAVES, registries);
						addBiomeRequirement(advancement, WWBiomes.MAGMATIC_CAVES, registries);
						addBiomeRequirement(advancement, WWBiomes.ARID_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.ARID_SAVANNA, registries);
						addBiomeRequirement(advancement, WWBiomes.PARCHED_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.BIRCH_JUNGLE, registries);
						addBiomeRequirement(advancement, WWBiomes.SPARSE_BIRCH_JUNGLE, registries);
						addBiomeRequirement(advancement, WWBiomes.BIRCH_TAIGA, registries);
						addBiomeRequirement(advancement, WWBiomes.SEMI_BIRCH_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.DARK_BIRCH_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.FLOWER_FIELD, registries);
						addBiomeRequirement(advancement, WWBiomes.TEMPERATE_RAINFOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.RAINFOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.DARK_TAIGA, registries);
						addBiomeRequirement(advancement, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, registries);
						addBiomeRequirement(advancement, WWBiomes.OLD_GROWTH_DARK_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, registries);
						addBiomeRequirement(advancement, WWBiomes.DYING_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.SNOWY_DYING_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.DYING_MIXED_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.SNOWY_DYING_MIXED_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.MAPLE_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.SPARSE_FOREST, registries);
						addBiomeRequirement(advancement, WWBiomes.TUNDRA, registries);
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
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(WWEntityTypes.CRAB)).triggerInstance())
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
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(WWEntityTypes.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								"wilderwild:scorched"
							)
						);
					}
					case "minecraft:adventure/kill_all_mobs" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(WWEntityTypes.SCORCHED)).triggerInstance())
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

		if (WWBlockConfig.get().stoneChest.addStoneChests) {
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
