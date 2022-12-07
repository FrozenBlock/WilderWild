package net.frozenblock.wilderwild;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.entrypoint.api.FrozenMainEntrypoint;
import net.frozenblock.lib.impl.BlockScheduledTicks;
import net.frozenblock.lib.impl.DripstoneDripWaterFrom;
import net.frozenblock.lib.impl.HopperUntouchableList;
import net.frozenblock.lib.impl.PlayerDamageSourceSounds;
import net.frozenblock.lib.impl.StructurePoolElementIdReplacements;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.WilderEnderman;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.*;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.*;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.BAOBAB_LEAVES;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.CYPRESS_LEAVES;
import static net.minecraft.world.level.block.Blocks.*;
import static net.minecraft.world.level.block.Blocks.WITHER_ROSE;

public final class FrozenLibIntegration implements FrozenMainEntrypoint {

    @Override
    public void init() {
        WilderSharedConstants.log("FrozenLib Main Entrypoint for WilderWild loaded.", WilderSharedConstants.UNSTABLE_LOGGING);
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
					((WilderEnderman) entity).setCanPlayLoopingSound(false);
				}
			}

			@Override
			public void onStop(@Nullable EnderMan entity) {
				if (entity != null) {
					((WilderEnderman) entity).setCanPlayLoopingSound(true);
				}
			}
		});

		PlayerDamageSourceSounds.addDamageSound(DamageSource.CACTUS, RegisterSounds.PLAYER_HURT_CACTUS, WilderSharedConstants.id("cactus"));
        BlockScheduledTicks.TICKS.put(Blocks.DIRT, (blockState, serverLevel, blockPos, randomSource) -> serverLevel.setBlock(blockPos, Blocks.MUD.defaultBlockState(), 3));
        HopperUntouchableList.BLACKLISTED_TYPES.add(RegisterBlockEntities.STONE_CHEST);
        //StructurePoolElementIdReplacements.resourceLocationReplacements.put(WilderSharedConstants.vanillaId("ancient_city/city_center/city_center_1"), WilderSharedConstants.id("ancient_city/city_center/city_center_1"));
        //StructurePoolElementIdReplacements.resourceLocationReplacements.put(WilderSharedConstants.vanillaId("ancient_city/city_center/city_center_2"), WilderSharedConstants.id("ancient_city/city_center/city_center_2"));
        FrozenBools.useNewDripstoneLiquid = true;
        DripstoneDripWaterFrom.ON_DRIP_BLOCK.put(Blocks.WET_SPONGE, (level, fluidInfo, blockPos) -> {
            BlockState blockState = Blocks.SPONGE.defaultBlockState();
            level.setBlockAndUpdate(fluidInfo.pos(), blockState);
            Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
            level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
            level.levelEvent(LevelEvent.DRIPSTONE_DRIP, blockPos, 0);
        });
        DripstoneDripWaterFrom.ON_DRIP_BLOCK.put(Blocks.MUD, (level, fluidInfo, blockPos) -> {
            BlockState blockState = Blocks.CLAY.defaultBlockState();
            level.setBlockAndUpdate(fluidInfo.pos(), blockState);
            Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
            level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
            level.levelEvent(LevelEvent.DRIPSTONE_DRIP, blockPos, 0);
        });

        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/barracks"), WilderSharedConstants.id("ancient_city/structures/barracks"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/chamber_1"), WilderSharedConstants.id("ancient_city/structures/chamber_1"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/chamber_2"), WilderSharedConstants.id("ancient_city/structures/chamber_2"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/chamber_3"), WilderSharedConstants.id("ancient_city/structures/chamber_3"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/sauna_1"), WilderSharedConstants.id("ancient_city/structures/sauna_1"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/tall_ruin_1"), WilderSharedConstants.id("ancient_city/structures/tall_ruin_1"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/tall_ruin_2"), WilderSharedConstants.id("ancient_city/structures/tall_ruin_2"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/tall_ruin_3"), WilderSharedConstants.id("ancient_city/structures/tall_ruin_3"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/tall_ruin_4"), WilderSharedConstants.id("ancient_city/structures/tall_ruin_4"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderSharedConstants.vanillaId("ancient_city/structures/ice_box_1"), WilderSharedConstants.id("ancient_city/structures/ice_box_1"));

		if (ClothConfigInteractionHandler.cactusSounds()) {
			addBlock(CACTUS, SoundType.SWEET_BERRY_BUSH);
		}
		if (ClothConfigInteractionHandler.claySounds()) {
			addBlock(CLAY, CLAY_BLOCK);
		}
		if (ClothConfigInteractionHandler.coarseDirtSounds()) {
			addBlock(COARSE_DIRT, COARSEDIRT);
		}
		if (ClothConfigInteractionHandler.cobwebSounds()) {
			addBlock(COBWEB, WEB);
		}
		if (ClothConfigInteractionHandler.deadBushSounds()) {
			addBlock(DEAD_BUSH, SoundType.NETHER_SPROUTS);
		}
		if (ClothConfigInteractionHandler.flowerSounds()) {
			addBlocks(new Block[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY}, FLOWER);
		}
		if (ClothConfigInteractionHandler.frostedIceSounds()) {
			addBlocks(new Block[]{FROSTED_ICE}, ICE_BLOCKS);
		}
		if (ClothConfigInteractionHandler.gravelSounds()) {
			addBlock(GRAVEL, GRAVELSOUNDS);
		}
		if (ClothConfigInteractionHandler.leafSounds()) {
			addBlockTag(BlockTags.LEAVES, LEAVES);
			addBlocks(new Block[]{ACACIA_LEAVES, BIRCH_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, MANGROVE_LEAVES, OAK_LEAVES, SPRUCE_LEAVES, BAOBAB_LEAVES, CYPRESS_LEAVES}, LEAVES);
			if (FabricLoader.getInstance().isModLoaded("betternether")) {
				addBlock("betternether", "willow_leaves", LEAVES);
				addBlock("betternether", "rubeous_leaves", LEAVES);
				addBlock("betternether", "anchor_tree_leaves", LEAVES);
				addBlock("betternether", "nether_sakura_leaves", LEAVES);
			}

			if (FabricLoader.getInstance().isModLoaded("betterend")) {
				addBlock("betterend", "pythadendron_leaves", LEAVES);
				addBlock("betterend", "lacugrove_leaves", LEAVES);
				addBlock("betterend", "dragon_tree_leaves", LEAVES);
				addBlock("betterend", "tenanea_leaves", LEAVES);
				addBlock("betterend", "helix_tree_leaves", LEAVES);
				addBlock("betterend", "lucernia_leaves", LEAVES);
			}

			if (FabricLoader.getInstance().isModLoaded("blockus")) {
				addBlock("blockus", "white_oak_leaves", LEAVES);
				addBlock("blockus", "legacy_leaves", LEAVES);
			}

			if (FabricLoader.getInstance().isModLoaded("edenring")) {
				addBlock("edenring", "auritis_leaves", LEAVES);
			}

			if (FabricLoader.getInstance().isModLoaded("techreborn")) {
				addBlock("techreborn", "rubber_leaves", LEAVES);
			}
		}
		if (ClothConfigInteractionHandler.lilyPadSounds()) {
			addBlock(LILY_PAD, LILYPAD);
		}
		if (ClothConfigInteractionHandler.mushroomBlockSounds()) {
			addBlocks(new Block[]{RED_MUSHROOM, BROWN_MUSHROOM}, MUSHROOM);
			addBlocks(new Block[]{RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK, MUSHROOM_STEM}, MUSHROOM_BLOCK);
		}
		if (ClothConfigInteractionHandler.podzolSounds()) {
			addBlock(PODZOL, SoundType.ROOTED_DIRT);
		}
		if (ClothConfigInteractionHandler.reinforcedDeepslateSounds()) {
			addBlock(REINFORCED_DEEPSLATE, REINFORCEDDEEPSLATE);
		}
		if (ClothConfigInteractionHandler.sugarCaneSounds()) {
			addBlock(SUGAR_CANE, SUGARCANE);
		}
		if (ClothConfigInteractionHandler.witherRoseSounds()) {
			addBlock(WITHER_ROSE, SoundType.SWEET_BERRY_BUSH);
		}
    }

    @Override
    public void initDevOnly() {

    }
}
