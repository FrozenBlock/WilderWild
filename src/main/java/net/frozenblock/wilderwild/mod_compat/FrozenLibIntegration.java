/*
 * Copyright 2025 FrozenBlock
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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.block.api.dripstone.DripstoneDripApi;
import net.frozenblock.lib.block.api.friction.BlockFrictionAPI;
import net.frozenblock.lib.block.api.tick.BlockRandomTicks;
import net.frozenblock.lib.block.api.tick.BlockScheduledTicks;
import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.lib.block.storage.api.hopper.HopperApi;
import net.frozenblock.lib.entity.api.WolfVariantBiomeRegistry;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.item.api.ItemTooltipAdditionAPI;
import net.frozenblock.lib.item.api.removable.RemovableItemTags;
import net.frozenblock.lib.loot.api.FrozenLibLootTableEvents;
import net.frozenblock.lib.particle.api.VibrationParticleVisibilityApi;
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
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.block.FroglightGoopBlock;
import net.frozenblock.wilderwild.block.entity.GeyserBlockEntity;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
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
import net.minecraft.advancements.criterion.BredAnimalsTrigger;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.EffectsChangedTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.FilledBucketTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.WolfVariants;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.ChatFormatting;

public class FrozenLibIntegration extends ModIntegration {
	public static final Identifier INSTRUMENT_SOUND_PREDICATE = WWConstants.id("instrument");
	public static final Identifier ENDERMAN_ANGER_SOUND_PREDICATE = WWConstants.id("enderman_anger");
	public static final Identifier GEYSER_EFFECTIVE_WIND_DISTURBANCE = WWConstants.id("geyser_effective");
	public static final Identifier GEYSER_BASE_WIND_DISTURBANCE = WWConstants.id("geyser");

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

		SoundPredicate.register(INSTRUMENT_SOUND_PREDICATE, () -> new SoundPredicate.LoopPredicate<LivingEntity>() {

			private boolean firstCheck = true;
			private ItemStack lastStack;

			@Override
			public Boolean firstTickTest(LivingEntity entity) {
				return true;
			}

			@Override
			public boolean test(LivingEntity entity) {
				if (this.firstCheck) {
					this.firstCheck = false;
					InteractionHand hand = !entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() ? InteractionHand.MAIN_HAND : !entity.getItemInHand(InteractionHand.OFF_HAND).isEmpty() ? InteractionHand.OFF_HAND : null;
					if (hand == null) return false;

					ItemStack stack = entity.getItemInHand(hand);
					if (stack.getItem() instanceof InstrumentItem) {
						this.lastStack = stack;
						return true;
					}
					return false;
				}

				final ItemStack stack = entity.getUseItem();
				if (stack.getItem() instanceof InstrumentItem) {
					if (this.lastStack == null || ItemStack.matches(this.lastStack, stack)) {
						this.lastStack = stack;
						return true;
					}
					this.firstCheck = true;
				}
				return false;
			}
		});

		SoundPredicate.register(ENDERMAN_ANGER_SOUND_PREDICATE, () -> (SoundPredicate.LoopPredicate<EnderMan>) entity -> {
			if (entity.isSilent() || entity.isRemoved() || entity.isDeadOrDying()) return false;
			return entity.isCreepy() || entity.hasBeenStaredAt();
		});

		WindDisturbanceLogic.register(
			GEYSER_EFFECTIVE_WIND_DISTURBANCE,
                (WindDisturbanceLogic.DisturbanceLogic<GeyserBlockEntity>) (source, level, windOrigin, affectedArea, windTarget) -> {
				if (source.isPresent()) {
					BlockState state = level.getBlockState(source.get().getBlockPos());
					if (state.hasProperty(BlockStateProperties.FACING)) {
						Direction direction = state.getValue(BlockStateProperties.FACING);
						Vec3 movement = Vec3.atLowerCornerOf(direction.getUnitVec3i());
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
						Vec3 movement = Vec3.atLowerCornerOf(direction.getUnitVec3i());
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

		FrozenLibLootTableEvents.ON_ITEM_GENERATED_IN_CONTAINER.register((container, itemStack) -> {
			if (!(container instanceof StoneChestBlockEntity)) return;
			CustomData.update(DataComponents.CUSTOM_DATA, itemStack, compoundTag -> compoundTag.putBoolean("wilderwild_is_ancient", true));
		});
		RemovableItemTags.register("wilderwild_is_ancient", (level, entity, equipmentSlot) -> true, true);

		ItemTooltipAdditionAPI.addTooltip(
			Component.translatable("item.disabled.trailiertales").withStyle(ChatFormatting.RED),
			stack -> !FrozenBools.HAS_TRAILIERTALES && stack.getItem().requiredFeatures().contains(WWFeatureFlags.TRAILIER_TALES_COMPAT)
		);

		VibrationParticleVisibilityApi.registerVisibilityTest((data, user) -> !(user instanceof Crab.VibrationUser) && !(user instanceof IcicleBlockEntity.VibrationUser));
	}

	@Override
	public void init() {
		WWConstants.log("FrozenLib mod integration ran!", WWConstants.UNSTABLE_LOGGING);

		ServerWorldEvents.LOAD.register(
			(server, level) -> PlayerDamageTypeSounds.addDamageSound(
				level.damageSources().damageTypes.getValueOrThrow(DamageTypes.CACTUS),
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
			(state, level, pos, random) -> {
				if (DripstoneDripApi.getDripstoneFluid(level, pos) == Fluids.WATER) {
					level.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());
				}
			}
		);

		BlockRandomTicks.addToBlock(Blocks.PEARLESCENT_FROGLIGHT, FroglightGoopBlock::growFromFroglight);
		BlockRandomTicks.addToBlock(Blocks.VERDANT_FROGLIGHT, FroglightGoopBlock::growFromFroglight);
		BlockRandomTicks.addToBlock(Blocks.OCHRE_FROGLIGHT, FroglightGoopBlock::growFromFroglight);

		WindManager.addExtension(WWWindManager.TYPE);

		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_GRASS, WWSoundTypes.SHORT_GRASS, () -> WWBlockConfig.get().blockSounds.grassSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_FROZEN_GRASS, WWSoundTypes.FROZEN_GRASS, () -> WWBlockConfig.get().blockSounds.grassSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_DRY_GRASS, WWSoundTypes.DRY_GRASS, () -> WWBlockConfig.get().blockSounds.grassSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_FLOWER, SoundType.PINK_PETALS, () -> WWBlockConfig.get().blockSounds.flowerSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_LEAVES, SoundType.AZALEA_LEAVES, () -> WWBlockConfig.get().blockSounds.leafSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_CONIFER_LEAVES, WWSoundTypes.CONIFER_LEAVES, () -> WWBlockConfig.get().blockSounds.leafSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_CONIFER_LEAF_LITTER, WWSoundTypes.CONIFER_LEAF_LITTER, () -> WWBlockConfig.get().blockSounds.leafSounds);
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
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MELON_STEM, SoundType.CROP, () -> WWBlockConfig.get().blockSounds.melonSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_GRAVEL, WWSoundTypes.GRAVEL, () -> WWBlockConfig.get().blockSounds.gravelSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_CLAY, WWSoundTypes.CLAY, () -> WWBlockConfig.get().blockSounds.claySounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_DEAD_BUSH, SoundType.NETHER_SPROUTS, () -> WWBlockConfig.get().blockSounds.deadBushSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PODZOL, SoundType.ROOTED_DIRT, () -> WWBlockConfig.get().blockSounds.podzolSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_REINFORCED_DEEPSLATE, WWSoundTypes.REINFORCED_DEEPSLATE, () -> WWBlockConfig.get().blockSounds.reinforcedDeepslateSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_SUGAR_CANE, WWSoundTypes.SUGARCANE, () -> WWBlockConfig.get().blockSounds.sugarCaneSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_WITHER_ROSE, SoundType.SWEET_BERRY_BUSH, () -> WWBlockConfig.get().blockSounds.witherRoseSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MAGMA_BLOCK, WWSoundTypes.MAGMA, () -> WWBlockConfig.get().blockSounds.magmaSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_AUBURN_MOSS, WWSoundTypes.AUBURN_MOSS, () -> WWBlockConfig.get().blockSounds.mossSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_AUBURN_MOSS_CARPET, WWSoundTypes.AUBURN_MOSS_CARPET, () -> WWBlockConfig.get().blockSounds.mossSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_MOSS, WWSoundTypes.PALE_MOSS, () -> WWBlockConfig.get().blockSounds.mossSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_MOSS_CARPET, WWSoundTypes.PALE_MOSS_CARPET, () -> WWBlockConfig.get().blockSounds.mossSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_COCONUT, WWSoundTypes.COCONUT, () -> true);

		// PALE OAK
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_LEAVES, WWSoundTypes.PALE_OAK_LEAVES, () -> {
			final WWBlockConfig.BlockSoundsConfig soundConfig = WWBlockConfig.get().blockSounds;
			return soundConfig.paleOakSounds && soundConfig.leafSounds;
		});
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_LEAVES, SoundType.AZALEA_LEAVES, () -> {
			final WWBlockConfig.BlockSoundsConfig soundConfig = WWBlockConfig.get().blockSounds;
			return !soundConfig.paleOakSounds && soundConfig.leafSounds;
		});
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_LEAF_LITTER, WWSoundTypes.PALE_OAK_LEAF_LITTER, () -> {
			final WWBlockConfig.BlockSoundsConfig soundConfig = WWBlockConfig.get().blockSounds;
			return soundConfig.paleOakSounds && soundConfig.leafSounds;
		});
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_WOOD, WWSoundTypes.PALE_OAK_WOOD, () -> WWBlockConfig.get().blockSounds.paleOakSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_HOLLOWED_PALE_OAK_WOOD, WWSoundTypes.HOLLOWED_PALE_OAK_LOG, () -> WWBlockConfig.get().blockSounds.paleOakSounds);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_WOOD_HANGING_SIGN, WWSoundTypes.PALE_OAK_WOOD_HANGING_SIGN, () -> WWBlockConfig.get().blockSounds.paleOakSounds);

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

		if (WWWorldgenConfig.get().structure.decayTrailRuins) {
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

		if (WWWorldgenConfig.get().structure.newDesertVillages) {
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

		if (WWEntityConfig.get().scorched.scorchedInTrialChambers) {
			RandomPoolAliasApi.addTarget(
				WWConstants.vanillaId("trial_chambers/spawner/contents/small_melee"),
				WWConstants.id("trial_chambers/spawner/small_melee/scorched"),
				1
			);
		}

		AdvancementEvents.INIT.register((holder, registries) -> {
			HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
			HolderGetter<EntityType<?>> entities = registries.lookupOrThrow(Registries.ENTITY_TYPE);
			Advancement advancement = holder.value();
			if (!WWAmbienceAndMiscConfig.get().modifyAdvancements) return;
			switch (holder.id().toString()) {
				case "minecraft:adventure/adventuring_time" -> {
					final WWWorldgenConfig.BiomeGeneration biomeGenerationConfig = WWWorldgenConfig.get().biomeGeneration;
					if (biomeGenerationConfig.generateCypressWetlands) addBiomeRequirement(advancement, WWBiomes.CYPRESS_WETLANDS, registries);
					if (biomeGenerationConfig.generateOasis) addBiomeRequirement(advancement, WWBiomes.OASIS, registries);
					if (biomeGenerationConfig.generateWarmRiver) addBiomeRequirement(advancement, WWBiomes.WARM_RIVER, registries);
					if (biomeGenerationConfig.generateWarmBeach) addBiomeRequirement(advancement, WWBiomes.WARM_BEACH, registries);
					if (biomeGenerationConfig.generateFrozenCaves) addBiomeRequirement(advancement, WWBiomes.FROZEN_CAVES, registries);
					if (biomeGenerationConfig.generateMesogleaCaves) addBiomeRequirement(advancement, WWBiomes.MESOGLEA_CAVES, registries);
					if (biomeGenerationConfig.generateMagmaticCaves) addBiomeRequirement(advancement, WWBiomes.MAGMATIC_CAVES, registries);
					if (biomeGenerationConfig.generateAridForest) addBiomeRequirement(advancement, WWBiomes.ARID_FOREST, registries);
					if (biomeGenerationConfig.generateAridSavanna) addBiomeRequirement(advancement, WWBiomes.ARID_SAVANNA, registries);
					if (biomeGenerationConfig.generateParchedForest) addBiomeRequirement(advancement, WWBiomes.PARCHED_FOREST, registries);
					if (biomeGenerationConfig.generateBirchJungle) addBiomeRequirement(advancement, WWBiomes.BIRCH_JUNGLE, registries);
					if (biomeGenerationConfig.generateSparseBirchJungle) addBiomeRequirement(advancement, WWBiomes.SPARSE_BIRCH_JUNGLE, registries);
					if (biomeGenerationConfig.generateBirchTaiga) addBiomeRequirement(advancement, WWBiomes.BIRCH_TAIGA, registries);
					if (biomeGenerationConfig.generateSemiBirchForest) addBiomeRequirement(advancement, WWBiomes.SEMI_BIRCH_FOREST, registries);
					if (biomeGenerationConfig.generateDarkBirchForest) addBiomeRequirement(advancement, WWBiomes.DARK_BIRCH_FOREST, registries);
					if (biomeGenerationConfig.generateFlowerField) addBiomeRequirement(advancement, WWBiomes.FLOWER_FIELD, registries);
					if (biomeGenerationConfig.generateTemperateRainforest) addBiomeRequirement(advancement, WWBiomes.TEMPERATE_RAINFOREST, registries);
					if (biomeGenerationConfig.generateRainforest) addBiomeRequirement(advancement, WWBiomes.RAINFOREST, registries);
					if (biomeGenerationConfig.generateDarkTaiga) addBiomeRequirement(advancement, WWBiomes.DARK_TAIGA, registries);
					if (biomeGenerationConfig.generateOldGrowthBirchTaiga) addBiomeRequirement(advancement, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, registries);
					if (biomeGenerationConfig.generateOldGrowthDarkForest) addBiomeRequirement(advancement, WWBiomes.OLD_GROWTH_DARK_FOREST, registries);
					if (biomeGenerationConfig.generateOldGrowthSnowyTaiga) addBiomeRequirement(advancement, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, registries);
					if (biomeGenerationConfig.generateMixedForest) addBiomeRequirement(advancement, WWBiomes.MIXED_FOREST, registries);
					if (biomeGenerationConfig.generateDyingForest) addBiomeRequirement(advancement, WWBiomes.DYING_FOREST, registries);
					if (biomeGenerationConfig.generateSnowyDyingForest) addBiomeRequirement(advancement, WWBiomes.SNOWY_DYING_FOREST, registries);
					if (biomeGenerationConfig.generateDyingMixedForest) addBiomeRequirement(advancement, WWBiomes.DYING_MIXED_FOREST, registries);
					if (biomeGenerationConfig.generateSnowyDyingMixedForest) addBiomeRequirement(advancement, WWBiomes.SNOWY_DYING_MIXED_FOREST, registries);
					if (biomeGenerationConfig.generateMapleForest) addBiomeRequirement(advancement, WWBiomes.MAPLE_FOREST, registries);
					if (biomeGenerationConfig.generateSparseForest) addBiomeRequirement(advancement, WWBiomes.SPARSE_FOREST, registries);
					if (biomeGenerationConfig.generateTundra) addBiomeRequirement(advancement, WWBiomes.TUNDRA, registries);
				}
				case "minecraft:husbandry/balanced_diet" -> {
					final WWWorldgenConfig worldgenConfig = WWWorldgenConfig.get();
					final WWEntityConfig entityConfig = WWEntityConfig.get();

					if (worldgenConfig.treeGeneration.baobab) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:baobab_nut", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.BAOBAB_NUT)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:baobab_nut"))));
					}

					if (worldgenConfig.treeGeneration.palm) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:split_coconut", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.SPLIT_COCONUT)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:split_coconut"))));
					}

					if (worldgenConfig.vegetation.cactusGeneration) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:prickly_pear", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.PRICKLY_PEAR)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:prickly_pear"))));

						AdvancementAPI.addCriteria(advancement, "wilderwild:peeled_prickly_pear", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.PEELED_PRICKLY_PEAR)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:peeled_prickly_pear"))));
					}

					if (entityConfig.crab.spawnCrabs) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab_claw", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.CRAB_CLAW)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:crab_claw"))));

						AdvancementAPI.addCriteria(advancement, "wilderwild:cooked_crab_claw", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.COOKED_CRAB_CLAW)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:cooked_crab_claw"))));
					}

					if (entityConfig.scorched.spawnScorched || entityConfig.scorched.scorchedInTrialChambers) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched_eye", CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.SCORCHED_EYE)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:scorched_eye"))));
					}
				}
				case "minecraft:husbandry/bred_all_animals" -> {
					final WWEntityConfig entityConfig = WWEntityConfig.get();

					if (entityConfig.crab.spawnCrabs) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entities, WWEntityTypes.CRAB)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:crab"))));
					}

					if (entityConfig.ostrich.spawnOstriches) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:ostrich", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entities, WWEntityTypes.OSTRICH)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:ostrich"))));
					}

					if (entityConfig.moobloom.spawnMooblooms) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:moobloom", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entities, WWEntityTypes.MOOBLOOM)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:moobloom"))));
					}

					if (entityConfig.penguin.spawnPenguins) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:penguin", CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entities, WWEntityTypes.PENGUIN)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:penguin"))));
					}

				}
				case "minecraft:husbandry/tactical_fishing" -> {
					final WWEntityConfig entityConfig = WWEntityConfig.get();

					if (entityConfig.crab.spawnCrabs) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab_bucket", CriteriaTriggers.FILLED_BUCKET.createCriterion(
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(items, WWItems.CRAB_BUCKET)).triggerInstance())
						);
					}

					if (entityConfig.jellyfish.spawnJellyfish) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:jellyfish_bucket", CriteriaTriggers.FILLED_BUCKET.createCriterion(
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(items, WWItems.JELLYFISH_BUCKET)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement, List.of("wilderwild:crab_bucket", "wilderwild:jellyfish_bucket"));
					}
				}
				case "minecraft:nether/all_potions", "minecraft:nether/all_effects" -> {
					if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
						Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
						MobEffectsPredicate predicate = criterion.triggerInstance().effects.orElseThrow();
						Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap);

						final WWEntityConfig entityConfig = WWEntityConfig.get();
						if (entityConfig.crab.spawnCrabs || WWBlockConfig.get().reachBoostBeacon) {
							map.put(WWMobEffects.REACH_BOOST, new MobEffectsPredicate.MobEffectInstancePredicate());
						}

						if (entityConfig.scorched.spawnScorched || entityConfig.scorched.scorchedInTrialChambers) {
							map.put(WWMobEffects.SCORCHING, new MobEffectsPredicate.MobEffectInstancePredicate());
						}
						predicate.effectMap = map;
					}
				}
				case "minecraft:adventure/kill_a_mob" -> {
					final WWEntityConfig entityConfig = WWEntityConfig.get();
					if (entityConfig.scorched.spawnScorched || entityConfig.scorched.scorchedInTrialChambers) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entities, WWEntityTypes.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement, List.of("wilderwild:scorched"));
					}
				}
				case "minecraft:adventure/kill_all_mobs" -> {
					final WWEntityConfig entityConfig = WWEntityConfig.get();
					if (entityConfig.scorched.spawnScorched || entityConfig.scorched.scorchedInTrialChambers) {
						AdvancementAPI.addCriteria(advancement, "wilderwild:scorched", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entities, WWEntityTypes.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of("wilderwild:scorched"))));
					}
				}
				default -> {
				}
			}
		});

		if (WWBlockConfig.get().stoneChest.addStoneChests) {
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

	@Environment(EnvType.CLIENT)
	@Override
	public void clientInit() {
		ClientWindManager.addExtension(WWClientWindManager::new);
	}
}
