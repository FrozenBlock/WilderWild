/*
 * Copyright 2023 FrozenBlock
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

import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.block.api.dripstone.DripstoneDripWaterFrom;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.item.api.RemoveableItemTags;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlocks;
import net.frozenblock.lib.sound.api.damagesource.PlayerDamageSourceSounds;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.lib.spotting_icons.api.SpottingIconPredicate;
import net.frozenblock.lib.storage.api.HopperUntouchableList;
import net.frozenblock.lib.tick.api.BlockScheduledTicks;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.wind.WilderClientWindManager;
import net.frozenblock.wilderwild.misc.wind.WilderWindManager;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.*;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.*;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import static net.minecraft.world.level.block.Blocks.CLAY;
import static net.minecraft.world.level.block.Blocks.FROSTED_ICE;
import static net.minecraft.world.level.block.Blocks.GRAVEL;
import static net.minecraft.world.level.block.Blocks.ICE;
import static net.minecraft.world.level.block.Blocks.SANDSTONE;
import static net.minecraft.world.level.block.Blocks.*;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class FrozenLibIntegration extends ModIntegration {
	public FrozenLibIntegration() {
		super("frozenlib");
	}

	public static Fluid getDripstoneFluid(ServerLevel level, BlockPos pos) {
		BlockPos blockPos = PointedDripstoneBlock.findStalactiteTipAboveCauldron(level, pos);
		if (blockPos == null) {
			return Fluids.EMPTY;
		}
		return PointedDripstoneBlock.getCauldronFillFluidType(level, blockPos);
	}

	@Override
	public void init() {
		WilderSharedConstants.log("FrozenLib mod integration ran!", WilderSharedConstants.UNSTABLE_LOGGING);
		SpottingIconPredicate.register(WilderSharedConstants.id("stella"), entity -> entity.hasCustomName() && entity.getCustomName().getString().equalsIgnoreCase("stella"));
		SoundPredicate.register(WilderSharedConstants.id("instrument"), (SoundPredicate.LoopPredicate<LivingEntity>) entity ->
			(entity.getUseItem().getItem() instanceof InstrumentItem)
		);
		SoundPredicate.register(WilderSharedConstants.id("nectar"), (SoundPredicate.LoopPredicate<Firefly>) entity ->
			!entity.isSilent() && entity.hasCustomName() && Objects.requireNonNull(entity.getCustomName()).getString().toLowerCase().contains("nectar")
		);
		SoundPredicate.register(WilderSharedConstants.id("enderman_anger"), (SoundPredicate.LoopPredicate<EnderMan>) entity -> {
			if (entity.isSilent() || entity.isRemoved() || entity.isDeadOrDying()) {
				return false;
			}
			return entity.isCreepy() || entity.hasBeenStaredAt();
		});

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
			if (getDripstoneFluid(serverLevel, blockPos) == Fluids.WATER) {
				serverLevel.setBlock(blockPos, Blocks.MUD.defaultBlockState(), 3);
			}
		});

		WindManager.addExtension(WilderWindManager::new);
		RemoveableItemTags.register("wilderwild_is_ancient", (level, entity, slot, selected) -> true, true);

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
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void clientInit() {
		ClientWindManager.addExtension(WilderClientWindManager::new);
	}
}
