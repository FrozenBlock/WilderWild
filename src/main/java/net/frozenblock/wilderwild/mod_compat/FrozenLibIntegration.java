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

package net.frozenblock.wilderwild.mod_compat;

import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.block.api.dripstone.DripstoneDripApi;
import net.frozenblock.lib.block.api.friction.BlockFrictionAPI;
import net.frozenblock.lib.block.api.tick.BlockRandomTicks;
import net.frozenblock.lib.block.api.tick.BlockScheduledTicks;
import net.frozenblock.lib.entity.api.WolfVariantBiomeRegistry;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.item.api.ItemTooltipAdditionAPI;
import net.frozenblock.lib.particle.api.VibrationParticleVisibilityApi;
import net.frozenblock.lib.sound.api.damage.PlayerDamageTypeSounds;
import net.frozenblock.lib.spotting_icons.api.SpottingIconPredicate;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.lib.worldgen.structure.api.RandomPoolAliasApi;
import net.frozenblock.lib.worldgen.structure.api.StructureProcessorApi;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.block.FroglightGoopBlock;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.BredAnimalsTrigger;
import net.minecraft.advancements.triggers.ConsumeItemTrigger;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.EffectsChangedTrigger;
import net.minecraft.advancements.triggers.FilledBucketTrigger;
import net.minecraft.advancements.triggers.KilledTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.wolf.WolfVariants;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireflyBushBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.ChatFormatting;

public class FrozenLibIntegration extends ModIntegration {

	public FrozenLibIntegration() {
		super("frozenlib");
	}

	private static void addBiomeRequirement(Advancement advancement, Holder<Biome> holder) {
		AdvancementAPI.addCriteria(advancement, holder.unwrapKey().orElseThrow().identifier().toString(), inBiome(holder));
		AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(holder.unwrapKey().orElseThrow().identifier().toString()))));
	}

	private static void addBiomeRequirement(Advancement advancement, ResourceKey<Biome> key, HolderLookup.Provider registries) {
		addBiomeRequirement(advancement, registries.lookupOrThrow(Registries.BIOME).getOrThrow(key));
	}

	private static Criterion<PlayerTrigger.TriggerInstance> inBiome(Holder<Biome> holder) {
		return PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(holder));
	}

	@Override
	public void initPreFreeze() {
		WWConstants.log("FrozenLib pre-freeze mod integration ran!", WWConstants.UNSTABLE_LOGGING);
		SpottingIconPredicate.register(
			WWConstants.id("stella"),
			entity -> entity.hasCustomName() && entity.getCustomName().getString().equalsIgnoreCase("stella")
		);

		ItemTooltipAdditionAPI.addTooltip(
			Component.translatable("item.disabled.trailiertales").withStyle(ChatFormatting.RED),
			stack -> !FrozenBools.HAS_TRAILIERTALES && stack.getItem().requiredFeatures().contains(WWFeatureFlags.TRAILIER_TALES_COMPAT)
		);

		VibrationParticleVisibilityApi.registerVisibilityTest((data, user) -> !(user instanceof Crab.VibrationUser) && !(user instanceof IcicleBlockEntity.VibrationUser));
	}

	@Override
	public void init() {
		WWConstants.log("FrozenLib mod integration ran!", WWConstants.UNSTABLE_LOGGING);

		ServerLevelEvents.LOAD.register(
			(server, level) -> PlayerDamageTypeSounds.addDamageSound(
				level.damageSources().damageTypes.getValueOrThrow(DamageTypes.CACTUS),
				WWSounds.PLAYER_HURT_CACTUS,
				WWConstants.id("cactus")
			)
		);

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
			(state, level, pos, random) -> {
				if (DripstoneDripApi.getDripstoneFluid(level, pos) == Fluids.WATER) {
					level.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());
				}
			}
		);

		BlockRandomTicks.addToBlock(Blocks.PEARLESCENT_FROGLIGHT, FroglightGoopBlock::growFromFroglight);
		BlockRandomTicks.addToBlock(Blocks.VERDANT_FROGLIGHT, FroglightGoopBlock::growFromFroglight);
		BlockRandomTicks.addToBlock(Blocks.OCHRE_FROGLIGHT, FroglightGoopBlock::growFromFroglight);

		BlockRandomTicks.addToBlock(
			Blocks.FIREFLY_BUSH,
			(state, level, pos, random) -> {
				if (!WWEntityConfig.FIREFLIES_NEED_BUSH.get() || !WWEntityConfig.SPAWN_FIREFLIES.get() || level.isClientSide()) return;
				if (level.getMaxLocalRawBrightness(pos) > FireflyBushBlock.FIREFLY_SPAWN_MAX_BRIGHTNESS_LEVEL) return;
				if (!level.hasNearbyAlivePlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 24D)) return;

				final Vec3 bushPos = pos.getCenter();
				final List<Firefly> fireflies = level.getEntitiesOfClass(
					Firefly.class,
					AABB.ofSize(bushPos, 16D, 16D, 16D),
					EntitySelector.LIVING_ENTITY_STILL_ALIVE.and(EntitySelector.NO_SPECTATORS)
				);
				if (!fireflies.isEmpty()) {
					if (fireflies.size() >= 16) return;
					if (fireflies.stream().filter(firefly -> firefly.position().distanceTo(bushPos) <= 4D).toList().size() >= 4) return;
				}

				for (int i = 0; i < random.nextInt(3, 6); i++) {
					SpawnUtil.trySpawnMob(
						WWEntityTypes.FIREFLY,
						EntitySpawnReason.TRIGGERED,
						level,
						pos,
						3,
						3,
						3,
						Firefly.FIREFLY_SPAWN_STRATEGY,
						false
					);
				}
			}
		);

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

		BlockFrictionAPI.MODIFICATIONS.register(ctx -> {
			if (ctx.entity instanceof Penguin && ctx.state.is(WWBlockTags.PENGUIN_IGNORE_FRICTION)) ctx.friction = 0.6F;
		});

		BlockFrictionAPI.MODIFICATIONS.register(ctx -> {
			if (ctx.entity instanceof Tumbleweed tumbleweed && tumbleweed.isCannonball()) ctx.friction = 0.965F;
		});

		if (WWWorldgenConfig.DECAYING_TRAIL_RUINS_GENERATION.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.TRAIL_RUINS.identifier(),
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, WWBlocks.CRACKED_MUD_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICKS.defaultBlockState())
					)
				)
			);
			StructureProcessorApi.addProcessor(
				BuiltinStructures.TRAIL_RUINS.identifier(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_STAIRS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_STAIRS),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_SLAB),
						new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_WALL)
					)
				)
			);
		}

		if (WWWorldgenConfig.NEW_DESERT_VILLAGE_GENERATION.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.VILLAGE_DESERT.identifier(),
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

		if (WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
			RandomPoolAliasApi.addTarget(
				WWConstants.vanillaId("trial_chambers/spawner/contents/small_melee"),
				WWConstants.id("trial_chambers/spawner/small_melee/scorched"),
				1
			);
		}

		AdvancementEvents.INIT.register((holder, registries) -> {
			final HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
			final HolderGetter<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
			final Advancement advancement = holder.value();
			if (!WWAmbienceAndMiscConfig.MODIFY_ADVANCEMENTS.get()) return;
			switch (holder.id().toString()) {
				case "minecraft:adventure/adventuring_time" -> {
					if (WWWorldgenConfig.CYPRESS_WETLANDS_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.CYPRESS_WETLANDS, registries);
					if (WWWorldgenConfig.OASIS_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.OASIS, registries);
					if (WWWorldgenConfig.WARM_RIVER_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.WARM_RIVER, registries);
					if (WWWorldgenConfig.WARM_BEACH_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.WARM_BEACH, registries);
					if (WWWorldgenConfig.FROZEN_CAVES_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.FROZEN_CAVES, registries);
					if (WWWorldgenConfig.MESOGLEA_CAVES_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.MESOGLEA_CAVES, registries);
					if (WWWorldgenConfig.MAGMATIC_CAVES_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.MAGMATIC_CAVES, registries);
					if (WWWorldgenConfig.ARID_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.ARID_FOREST, registries);
					if (WWWorldgenConfig.ARID_SAVANNA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.ARID_SAVANNA, registries);
					if (WWWorldgenConfig.PARCHED_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.PARCHED_FOREST, registries);
					if (WWWorldgenConfig.BIRCH_JUNGLE_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.BIRCH_JUNGLE, registries);
					if (WWWorldgenConfig.SPARSE_BIRCH_JUNGLE_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SPARSE_BIRCH_JUNGLE, registries);
					if (WWWorldgenConfig.BIRCH_TAIGA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.BIRCH_TAIGA, registries);
					if (WWWorldgenConfig.SEMI_BIRCH_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SEMI_BIRCH_FOREST, registries);
					if (WWWorldgenConfig.DARK_BIRCH_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.DARK_BIRCH_FOREST, registries);
					if (WWWorldgenConfig.FLOWER_FIELD_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.FLOWER_FIELD, registries);
					if (WWWorldgenConfig.TEMPERATE_RAINFOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.TEMPERATE_RAINFOREST, registries);
					if (WWWorldgenConfig.RAINFOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.RAINFOREST, registries);
					if (WWWorldgenConfig.DARK_TAIGA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.DARK_TAIGA, registries);
					if (WWWorldgenConfig.OLD_GROWTH_BIRCH_TAIGA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, registries);
					if (WWWorldgenConfig.OLD_GROWTH_DARK_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.OLD_GROWTH_DARK_FOREST, registries);
					if (WWWorldgenConfig.SNOWY_OLD_GROWTH_PINE_TAIGA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, registries);
					if (WWWorldgenConfig.MIXED_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.MIXED_FOREST, registries);
					if (WWWorldgenConfig.DYING_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.DYING_FOREST, registries);
					if (WWWorldgenConfig.SNOWY_DYING_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SNOWY_DYING_FOREST, registries);
					if (WWWorldgenConfig.DYING_MIXED_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.DYING_MIXED_FOREST, registries);
					if (WWWorldgenConfig.SNOWY_DYING_MIXED_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SNOWY_DYING_MIXED_FOREST, registries);
					if (WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.MAPLE_FOREST, registries);
					if (WWWorldgenConfig.SPARSE_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SPARSE_FOREST, registries);
					if (WWWorldgenConfig.TUNDRA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.TUNDRA, registries);
				}
				case "minecraft:husbandry/balanced_diet" -> {
					if (WWWorldgenConfig.BAOBAB_TREE_GENERATION.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:baobab_nut", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.BAOBAB_NUT)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:baobab_nut"))));
					}

					if (WWWorldgenConfig.PALM_TREE_GENERATION.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:split_coconut", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.SPLIT_COCONUT)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:split_coconut"))));
					}

					if (WWWorldgenConfig.CACTUS_GENERATION.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:prickly_pear", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.PRICKLY_PEAR)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:prickly_pear"))));

						AdvancementAPI.addCriteria(advancement, "wilderwild:peeled_prickly_pear", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.PEELED_PRICKLY_PEAR)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:peeled_prickly_pear"))));
					}

					if (WWEntityConfig.SPAWN_CRABS.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab_claw", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.CRAB_CLAW)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:crab_claw"))));

						AdvancementAPI.addCriteria(advancement, "wilderwild:cooked_crab_claw", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.COOKED_CRAB_CLAW)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:cooked_crab_claw"))));
					}

					if (WWEntityConfig.SPAWN_SCORCHED.get() || WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched_eye", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.SCORCHED_EYE)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:scorched_eye"))));
					}
				}
				case "minecraft:husbandry/bred_all_animals" -> {
					if (WWEntityConfig.SPAWN_CRABS.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.CRAB)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:crab"))));
					}

					if (WWEntityConfig.SPAWN_OSTRICHES.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:ostrich", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.OSTRICH)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:ostrich"))));
					}

					if (WWEntityConfig.SPAWN_MOOBLOOMS.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:moobloom", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.MOOBLOOM)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:moobloom"))));
					}

					if (WWEntityConfig.SPAWN_PENGUINS.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:penguin", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.PENGUIN)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:penguin"))));
					}

				}
				case "minecraft:husbandry/tactical_fishing" -> {
					if (WWEntityConfig.SPAWN_CRABS.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab_bucket", CriteriaTriggers.FILLED_BUCKET.createCriterion(
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(items, WWItems.CRAB_BUCKET)).triggerInstance())
						);
					}

					if (WWEntityConfig.SPAWN_JELLYFISH.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:jellyfish_bucket", CriteriaTriggers.FILLED_BUCKET.createCriterion(
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(items, WWItems.JELLYFISH_BUCKET)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement, List.of("wilderwild:crab_bucket", "wilderwild:jellyfish_bucket"));
					}
				}
				case "minecraft:nether/all_potions", "minecraft:nether/all_effects" -> {
					if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
						final Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
						final MobEffectsPredicate predicate = criterion.triggerInstance().effects.orElseThrow();
						final Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap);

						if (WWEntityConfig.SPAWN_CRABS.get() || WWBlockConfig.REACH_BOOST_BEACON.get()) {
							map.put(WWMobEffects.REACH_BOOST, new MobEffectsPredicate.MobEffectInstancePredicate());
						}

						if (WWEntityConfig.SPAWN_SCORCHED.get() || WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
							map.put(WWMobEffects.SCORCHING, new MobEffectsPredicate.MobEffectInstancePredicate());
						}
						predicate.effectMap = map;
					}
				}
				case "minecraft:adventure/kill_a_mob" -> {
					if (WWEntityConfig.SPAWN_SCORCHED.get() || WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement, List.of("wilderwild:scorched"));
					}

					if (WWEntityConfig.SPAWN_ZOMBIE_OSTRICHES.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:zombie_ostrich", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.ZOMBIE_OSTRICH)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement, List.of("wilderwild:zombie_ostrich"));
					}
				}
				case "minecraft:adventure/kill_all_mobs" -> {
					if (WWEntityConfig.SPAWN_SCORCHED.get() || WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:scorched"))));
					}
					if (WWEntityConfig.SPAWN_ZOMBIE_OSTRICHES.get()) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:zombie_ostrich", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.ZOMBIE_OSTRICH)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:zombie_ostrich"))));
					}
				}
				default -> {
				}
			}
		});

		if (WWBlockConfig.ADD_STONE_CHESTS.get()) {
			StructureProcessorApi.addProcessor(
				BuiltinStructures.ANCIENT_CITY.identifier(),
				new BlockStateRespectingRuleProcessor(
					ImmutableList.of(
						new BlockStateRespectingProcessorRule(new BlockMatchTest(Blocks.CHEST), AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST)
					)
				)
			);
		}
	}
}
