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

package net.frozenblock.wilderwild.misc.mod_compat;

import java.util.HashMap;
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
import net.frozenblock.lib.block.api.tick.BlockScheduledTicks;
import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlocks;
import net.frozenblock.lib.item.api.removable.RemovableItemTags;
import net.frozenblock.lib.sound.api.damagesource.PlayerDamageSourceSounds;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.lib.spotting_icons.api.SpottingIconPredicate;
import net.frozenblock.lib.storage.api.HopperUntouchableList;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.config.MiscConfig;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.wind.WilderClientWindManager;
import net.frozenblock.wilderwild.misc.wind.CloudWindManager;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.*;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.*;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterMobEffects;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class FrozenLibIntegration extends ModIntegration {

	public FrozenLibIntegration() {
		super("frozenlib");
	}

	@Override
	public void initPreFreeze() {
		WilderSharedConstants.log("FrozenLib pre-freeze mod integration ran!", WilderSharedConstants.UNSTABLE_LOGGING);
		SpottingIconPredicate.register(WilderSharedConstants.id("stella"), entity -> entity.hasCustomName() && entity.getCustomName().getString().equalsIgnoreCase("stella"));
		SoundPredicate.register(WilderSharedConstants.id("instrument"), new SoundPredicate.LoopPredicate<LivingEntity>() {

			private boolean firstCheck = true;

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
					return stack.getItem() instanceof InstrumentItem;
				}
				return entity.getUseItem().getItem() instanceof InstrumentItem;
			}
		});
		SoundPredicate.register(WilderSharedConstants.id("nectar"), (SoundPredicate.LoopPredicate<Firefly>) entity ->
			!entity.isSilent() && entity.hasCustomName() && Objects.requireNonNull(entity.getCustomName()).getString().toLowerCase().contains("nectar")
		);
		SoundPredicate.register(WilderSharedConstants.id("enderman_anger"), (SoundPredicate.LoopPredicate<EnderMan>) entity -> {
			if (entity.isSilent() || entity.isRemoved() || entity.isDeadOrDying()) {
				return false;
			}
			return entity.isCreepy() || entity.hasBeenStaredAt();
		});
	}

	@Override
	public void init() {
		WilderSharedConstants.log("FrozenLib mod integration ran!", WilderSharedConstants.UNSTABLE_LOGGING);

		ServerWorldEvents.LOAD.register((server, level) -> PlayerDamageSourceSounds.addDamageSound(level.damageSources().cactus(), RegisterSounds.PLAYER_HURT_CACTUS, WilderSharedConstants.id("cactus")));

		HopperUntouchableList.BLACKLISTED_TYPES.add(RegisterBlockEntities.STONE_CHEST);
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

		WindManager.addExtension(CloudWindManager::new);
		RemovableItemTags.register("wilderwild_is_ancient", (level, entity, slot, selected) -> true, true);

		addBlocks(new Block[]{CACTUS, PRICKLY_PEAR_CACTUS}, CACTI, () -> BlockConfig.get().blockSounds.cactusSounds);
		addBlock(CLAY, RegisterBlockSoundTypes.CLAY, () -> BlockConfig.get().blockSounds.claySounds);
		addBlock(COARSE_DIRT, COARSEDIRT, () -> BlockConfig.get().blockSounds.coarseDirtSounds);
		addBlock(COBWEB, WEB, () -> BlockConfig.get().blockSounds.cobwebSounds);
		addBlock(DEAD_BUSH, SoundType.NETHER_SPROUTS, () -> BlockConfig.get().blockSounds.deadBushSounds);
		addBlocks(new Block[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY, SEEDING_DANDELION, CARNATION, GLORY_OF_THE_SNOW, DATURA, MILKWEED, SUNFLOWER, ROSE_BUSH, PEONY, LILAC}, FLOWER, () -> BlockConfig.get().blockSounds.flowerSounds);
		addBlocks(new Block[]{ICE, BLUE_ICE, PACKED_ICE}, RegisterBlockSoundTypes.ICE, () -> BlockConfig.get().blockSounds.iceSounds);
		addBlock(FROSTED_ICE, RegisterBlockSoundTypes.FROSTED_ICE, () -> BlockConfig.get().blockSounds.frostedIceSounds);
		addBlock(GRAVEL, RegisterBlockSoundTypes.GRAVEL, () -> BlockConfig.get().blockSounds.gravelSounds);
		addBlocks(new Block[]{ACACIA_SAPLING, BIRCH_SAPLING, DARK_OAK_SAPLING, JUNGLE_SAPLING, MANGROVE_PROPAGULE, OAK_SAPLING, SPRUCE_SAPLING, CYPRESS_SAPLING, BUSH}, SAPLING, () -> BlockConfig.get().blockSounds.saplingSounds);
		addBlocks(new Block[]{ACACIA_LEAVES, BIRCH_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, MANGROVE_LEAVES, OAK_LEAVES, SPRUCE_LEAVES, BAOBAB_LEAVES, CYPRESS_LEAVES, PALM_FRONDS}, LEAVES, () -> BlockConfig.get().blockSounds.leafSounds);
		addBlocks(new Block[]{LILY_PAD, FLOWERING_LILY_PAD}, LILYPAD, () -> BlockConfig.get().blockSounds.lilyPadSounds);
		addBlocks(new Block[]{RED_MUSHROOM, BROWN_MUSHROOM}, MUSHROOM, () -> BlockConfig.get().blockSounds.mushroomBlockSounds);
		addBlocks(new Block[]{RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK, MUSHROOM_STEM}, MUSHROOM_BLOCK, () -> BlockConfig.get().blockSounds.mushroomBlockSounds);
		addBlock(PODZOL, SoundType.ROOTED_DIRT, () -> BlockConfig.get().blockSounds.podzolSounds);
		addBlock(REINFORCED_DEEPSLATE, REINFORCEDDEEPSLATE, () -> BlockConfig.get().blockSounds.reinforcedDeepslateSounds);
		addBlocks(new Block[]{SANDSTONE, SANDSTONE_SLAB, SANDSTONE_STAIRS, SANDSTONE_WALL, CHISELED_SANDSTONE, CUT_SANDSTONE, SMOOTH_SANDSTONE, SMOOTH_SANDSTONE_SLAB, SMOOTH_SANDSTONE_STAIRS, RED_SANDSTONE, RED_SANDSTONE_SLAB, RED_SANDSTONE_STAIRS, RED_SANDSTONE_WALL, CHISELED_RED_SANDSTONE, CUT_RED_SANDSTONE, SMOOTH_RED_SANDSTONE, SMOOTH_RED_SANDSTONE_SLAB, SMOOTH_RED_SANDSTONE_STAIRS}, RegisterBlockSoundTypes.SANDSTONE, () -> BlockConfig.get().blockSounds.sandstoneSounds);
		addBlock(SUGAR_CANE, SUGARCANE, () -> BlockConfig.get().blockSounds.sugarCaneSounds);
		addBlock(WITHER_ROSE, SoundType.SWEET_BERRY_BUSH, () -> BlockConfig.get().blockSounds.witherRoseSounds);

		AdvancementEvents.INIT.register(holder -> {
			Advancement advancement = holder.value();
			if (MiscConfig.get().modifyAdvancements) {
				switch (holder.id().toString()) {
					case "minecraft:adventure/adventuring_time" -> {
						addBiomeRequirement(advancement, RegisterWorldgen.CYPRESS_WETLANDS);
						addBiomeRequirement(advancement, RegisterWorldgen.MIXED_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.OASIS);
						addBiomeRequirement(advancement, RegisterWorldgen.WARM_RIVER);
						addBiomeRequirement(advancement, RegisterWorldgen.WARM_BEACH);
						addBiomeRequirement(advancement, RegisterWorldgen.JELLYFISH_CAVES);
						addBiomeRequirement(advancement, RegisterWorldgen.ARID_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.ARID_SAVANNA);
						addBiomeRequirement(advancement, RegisterWorldgen.PARCHED_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.BIRCH_JUNGLE);
						addBiomeRequirement(advancement, RegisterWorldgen.SPARSE_BIRCH_JUNGLE);
						addBiomeRequirement(advancement, RegisterWorldgen.BIRCH_TAIGA);
						addBiomeRequirement(advancement, RegisterWorldgen.SEMI_BIRCH_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.DARK_BIRCH_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.FLOWER_FIELD);
						addBiomeRequirement(advancement, RegisterWorldgen.TEMPERATE_RAINFOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.RAINFOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.DARK_TAIGA);
						addBiomeRequirement(advancement, RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA);
						addBiomeRequirement(advancement, RegisterWorldgen.OLD_GROWTH_DARK_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA);
						addBiomeRequirement(advancement, RegisterWorldgen.DYING_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.SNOWY_DYING_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.DYING_MIXED_FOREST);
						addBiomeRequirement(advancement, RegisterWorldgen.SNOWY_DYING_MIXED_FOREST);
					}
					case "minecraft:husbandry/balanced_diet" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:baobab_nut",
							ConsumeItemTrigger.TriggerInstance.usedItem(RegisterItems.BAOBAB_NUT)
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:split_coconut",
							ConsumeItemTrigger.TriggerInstance.usedItem(RegisterItems.SPLIT_COCONUT)
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab_claw",
							ConsumeItemTrigger.TriggerInstance.usedItem(RegisterItems.CRAB_CLAW)
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:cooked_crab_claw",
							ConsumeItemTrigger.TriggerInstance.usedItem(RegisterItems.COOKED_CRAB_CLAW)
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:prickly_pear",
							ConsumeItemTrigger.TriggerInstance.usedItem(RegisterItems.PRICKLY_PEAR)
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:peeled_prickly_pear",
							ConsumeItemTrigger.TriggerInstance.usedItem(RegisterItems.PEELED_PRICKLY_PEAR)
						);
						AdvancementAPI.addRequirements(advancement,
							new String[][]{{
								"wilderwild:baobab_nut",
								"wilderwild:split_coconut",
								"wilderwild:crab_claw",
								"wilderwild:cooked_crab_claw",
								"wilderwild:prickly_pear",
								"wilderwild:peeled_prickly_pear"
							}}
						);
					}
					case "minecraft:husbandry/bred_all_animals" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab",
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(RegisterEntities.CRAB))
						);
						AdvancementAPI.addRequirements(advancement,
								new String[][]{{
								"wilderwild:crab"
							}}
						);
					}
					case "minecraft:husbandry/tactical_fishing" -> {
						AdvancementAPI.addCriteria(advancement, "wilderwild:crab_bucket",
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(RegisterItems.CRAB_BUCKET).build())
						);
						AdvancementAPI.addCriteria(advancement, "wilderwild:jellyfish_bucket",
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(RegisterItems.JELLYFISH_BUCKET).build())
						);
						AdvancementAPI.addRequirements(advancement,
								new String[][]{{
								"wilderwild:crab_bucket",
								"wilderwild:jellyfish_bucket"
							}}
						);
					}
					case "minecraft:nether/all_potions", "minecraft:nether/all_effects" -> {
						if (advancement.getCriteria().get("all_effects") != null && advancement.getCriteria().get("all_effects").getTrigger() instanceof EffectsChangedTrigger.TriggerInstance effectsChangedTrigger) {
							MobEffectsPredicate predicate = MobEffectsPredicate.effects();
							Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effects);
							map.put(RegisterMobEffects.REACH, new MobEffectsPredicate.MobEffectInstancePredicate());
							predicate.effects = map;
						}
					}
					default -> {
					}
				}

			}
		});
	}

	private static void addBiomeRequirement(@NotNull Advancement advancement, @NotNull ResourceKey<Biome> key) {
		AdvancementAPI.addCriteria(advancement, key.location().toString(), inBiome(key));
		AdvancementAPI.addRequirements(advancement, new String[][]{{key.location().toString()}});
	}

	@NotNull
	private static Criterion inBiome(ResourceKey<Biome> key) {
		return new Criterion(PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setBiome(key).build()));
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void clientInit() {
		ClientWindManager.addExtension(WilderClientWindManager::new);
	}
}
