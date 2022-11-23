package net.frozenblock.wilderwild;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.entrypoints.FrozenMainEntrypoint;
import net.frozenblock.lib.impl.BlockScheduledTicks;
import net.frozenblock.lib.impl.DripstoneDripWaterFrom;
import net.frozenblock.lib.impl.HopperUntouchableList;
import net.frozenblock.lib.impl.PlayerDamageSourceSounds;
import net.frozenblock.lib.impl.StructurePoolElementIdReplacements;
import net.frozenblock.lib.item.api.HeavyItemDamageRegistry;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.WilderEnderman;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public final class FrozenLibIntegration implements FrozenMainEntrypoint {

    @Override
    public void init() {
        WilderWild.log("FrozenLib Main Entrypoint for WilderWild loaded.", WilderWild.UNSTABLE_LOGGING);
		SoundPredicate.register(WilderWild.id("instrument"),(SoundPredicate.LoopPredicate<Player>) player -> {
			return (player.getUseItem().getItem() instanceof InstrumentItem);
        });
        SoundPredicate.register(WilderWild.id("nectar"), (SoundPredicate.LoopPredicate<Firefly>) entity -> {
            if (entity.isSilent()) {
                return false;
            }
            if (entity.hasCustomName()) {
                Component name = entity.getCustomName();
                if (name != null) {
                    return name.getString().toLowerCase().contains("nectar");
                }
            }
            return false;
        });
		SoundPredicate.register(WilderWild.id("enderman_anger"), new SoundPredicate.LoopPredicate<EnderMan>() {
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

		PlayerDamageSourceSounds.addDamageSound(DamageSource.CACTUS, RegisterSounds.PLAYER_HURT_CACTUS, WilderWild.id("cactus"));
        BlockScheduledTicks.TICKS.put(Blocks.DIRT, (blockState, serverLevel, blockPos, randomSource) -> serverLevel.setBlock(blockPos, Blocks.MUD.defaultBlockState(), 3));
        HopperUntouchableList.BLACKLISTED_TYPES.add(RegisterBlockEntities.STONE_CHEST);
        //StructurePoolElementIdReplacements.resourceLocationReplacements.put(WilderWild.vanillaId("ancient_city/city_center/city_center_1"), WilderWild.id("ancient_city/city_center/city_center_1"));
        //StructurePoolElementIdReplacements.resourceLocationReplacements.put(WilderWild.vanillaId("ancient_city/city_center/city_center_2"), WilderWild.id("ancient_city/city_center/city_center_2"));
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

		HeavyItemDamageRegistry.register(Registry.ITEM.get(WilderWild.id("coconut")), 0.1F, 6);

        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/barracks"), WilderWild.id("ancient_city/structures/barracks"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/chamber_1"), WilderWild.id("ancient_city/structures/chamber_1"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/chamber_2"), WilderWild.id("ancient_city/structures/chamber_2"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/chamber_3"), WilderWild.id("ancient_city/structures/chamber_3"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/sauna_1"), WilderWild.id("ancient_city/structures/sauna_1"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/tall_ruin_1"), WilderWild.id("ancient_city/structures/tall_ruin_1"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/tall_ruin_2"), WilderWild.id("ancient_city/structures/tall_ruin_2"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/tall_ruin_3"), WilderWild.id("ancient_city/structures/tall_ruin_3"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/tall_ruin_4"), WilderWild.id("ancient_city/structures/tall_ruin_4"));
        StructurePoolElementIdReplacements.RESOURCE_LOCATION_REPLACEMENTS.put(WilderWild.vanillaId("ancient_city/structures/ice_box_1"), WilderWild.id("ancient_city/structures/ice_box_1"));
    }

    @Override
    public void initDevOnly() {

    }
}
