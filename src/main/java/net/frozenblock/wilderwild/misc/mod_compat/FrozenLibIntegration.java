/*
 * Copyright 2022-2023 FrozenBlock
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
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.interfaces.WilderEnderman;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.*;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.*;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import static net.minecraft.world.level.block.Blocks.CLAY;
import static net.minecraft.world.level.block.Blocks.GRAVEL;
import static net.minecraft.world.level.block.Blocks.SANDSTONE;
import static net.minecraft.world.level.block.Blocks.*;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class FrozenLibIntegration extends ModIntegration {
    public FrozenLibIntegration() {
        super("frozenlib");
    }

    @Override
    public void init() {
        WilderSharedConstants.log("FrozenLib mod integration ran!", WilderSharedConstants.UNSTABLE_LOGGING);
		SpottingIconPredicate.register(WilderSharedConstants.id("stella"), entity -> entity.hasCustomName() && entity.getCustomName().getString().equalsIgnoreCase("stella"));
        SoundPredicate.register(WilderSharedConstants.id("instrument"),(SoundPredicate.LoopPredicate<Player>) player ->
                (player.getUseItem().getItem() instanceof InstrumentItem)
        );
        SoundPredicate.register(WilderSharedConstants.id("nectar"), (SoundPredicate.LoopPredicate<Firefly>) entity ->
                !entity.isSilent() && entity.hasCustomName() && Objects.requireNonNull(entity.getCustomName()).getString().toLowerCase().contains("nectar")
        );
        SoundPredicate.register(WilderSharedConstants.id("enderman_anger"), new SoundPredicate.LoopPredicate<EnderMan>() {
            @Override
            public boolean test(EnderMan entity) {
                if (entity.isSilent() || !entity.isAlive() || entity.isRemoved()) {
                    return false;
                }
                return entity.isCreepy();
            }

            @Override
            public void onStart(@Nullable EnderMan entity) {
                if (entity != null) {
                    ((WilderEnderman) entity).wilderWild$setCanPlayLoopingSound(false);
                }
            }

            @Override
            public void onStop(@Nullable EnderMan entity) {
                if (entity != null) {
                    ((WilderEnderman) entity).wilderWild$setCanPlayLoopingSound(true);
                }
            }
        });

		ServerWorldEvents.LOAD.register((server, level) -> {
			PlayerDamageSourceSounds.addDamageSound(level.damageSources().cactus(), RegisterSounds.PLAYER_HURT_CACTUS, WilderSharedConstants.id("cactus"));
		});

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

		RemoveableItemTags.register("wilderwild_is_ancient", (level, entity, slot, selected) -> true, true);

        addBlocks(new Block[]{CACTUS, PRICKLY_PEAR_CACTUS}, CACTI, WilderSharedConstants.config()::cactusSounds);
        addBlock(CLAY, RegisterBlockSoundTypes.CLAY, WilderSharedConstants.config()::claySounds);
        addBlock(COARSE_DIRT, COARSEDIRT, WilderSharedConstants.config()::coarseDirtSounds);
        addBlock(COBWEB, WEB, WilderSharedConstants.config()::cobwebSounds);
        addBlock(DEAD_BUSH, SoundType.NETHER_SPROUTS, WilderSharedConstants.config()::deadBushSounds);
        addBlocks(new Block[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY, SEEDING_DANDELION, CARNATION, GLORY_OF_THE_SNOW}, FLOWER, WilderSharedConstants.config()::flowerSounds);
        addBlocks(new Block[]{FROSTED_ICE}, RegisterBlockSoundTypes.ICE, WilderSharedConstants.config()::frostedIceSounds);
        addBlock(GRAVEL, RegisterBlockSoundTypes.GRAVEL, WilderSharedConstants.config()::gravelSounds);
		addBlocks(new Block[]{ACACIA_SAPLING, BIRCH_SAPLING, DARK_OAK_SAPLING, JUNGLE_SAPLING, MANGROVE_PROPAGULE, OAK_SAPLING, SPRUCE_SAPLING, CYPRESS_SAPLING, BUSH}, SAPLING, WilderSharedConstants.config()::saplingSounds);
        addBlocks(new Block[]{ACACIA_LEAVES, BIRCH_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, MANGROVE_LEAVES, OAK_LEAVES, SPRUCE_LEAVES, BAOBAB_LEAVES, CYPRESS_LEAVES, PALM_FRONDS}, LEAVES, WilderSharedConstants.config()::leafSounds);
        addBlocks(new Block[]{LILY_PAD, FLOWERING_LILY_PAD}, LILYPAD, WilderSharedConstants.config()::lilyPadSounds);
        addBlocks(new Block[]{RED_MUSHROOM, BROWN_MUSHROOM}, MUSHROOM, WilderSharedConstants.config()::mushroomBlockSounds);
        addBlocks(new Block[]{RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK, MUSHROOM_STEM}, MUSHROOM_BLOCK, WilderSharedConstants.config()::mushroomBlockSounds);
        addBlock(PODZOL, SoundType.ROOTED_DIRT, WilderSharedConstants.config()::podzolSounds);
        addBlock(REINFORCED_DEEPSLATE, REINFORCEDDEEPSLATE, WilderSharedConstants.config()::reinforcedDeepslateSounds);
		addBlocks(new Block[]{SANDSTONE, SANDSTONE_SLAB, SANDSTONE_STAIRS, SANDSTONE_WALL, CHISELED_SANDSTONE, CUT_SANDSTONE, SMOOTH_SANDSTONE, SMOOTH_SANDSTONE_SLAB, SMOOTH_SANDSTONE_STAIRS, RED_SANDSTONE, RED_SANDSTONE_SLAB, RED_SANDSTONE_STAIRS, RED_SANDSTONE_WALL, CHISELED_RED_SANDSTONE, CUT_RED_SANDSTONE, SMOOTH_RED_SANDSTONE, SMOOTH_RED_SANDSTONE_SLAB, SMOOTH_RED_SANDSTONE_STAIRS}, RegisterBlockSoundTypes.SANDSTONE, WilderSharedConstants.config()::sandstoneSounds);
        addBlock(SUGAR_CANE, SUGARCANE, WilderSharedConstants.config()::sugarCaneSounds);
        addBlock(WITHER_ROSE, SoundType.SWEET_BERRY_BUSH, WilderSharedConstants.config()::witherRoseSounds);
    }

	public static Fluid getDripstoneFluid(ServerLevel level, BlockPos pos) {
		BlockPos blockPos = PointedDripstoneBlock.findStalactiteTipAboveCauldron(level, pos);
		if (blockPos == null) {
			return Fluids.EMPTY;
		}
		return PointedDripstoneBlock.getCauldronFillFluidType(level, blockPos);
	}
}
