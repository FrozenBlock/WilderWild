package net.frozenblock.wilderwild.misc;

import net.frozenblock.lib.mixin.server.BoneMealItemMixin;
import net.frozenblock.lib.replacements_and_lists.*;
import net.frozenblock.lib.sound.RegisterMovingSoundRestrictions;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.levelgen.Heightmap;

public class RegisterFrozenLib {

    public static void init() {
        RegisterMovingSoundRestrictions.register(WilderWild.id("ancient_horn"), (RegisterMovingSoundRestrictions.LoopPredicate<Player>) entity -> {
            if (entity instanceof Player player) {
                return player.getUseItem().is(RegisterItems.ANCIENT_HORN);
            }
            return false;
        });
        RegisterMovingSoundRestrictions.register(WilderWild.id("copper_horn"), (RegisterMovingSoundRestrictions.LoopPredicate<Player>) entity -> {
            if (entity instanceof Player player) {
                return player.getUseItem().is(RegisterItems.COPPER_HORN);
            }
            return false;
        });
        RegisterMovingSoundRestrictions.register(WilderWild.id("horn"), (RegisterMovingSoundRestrictions.LoopPredicate<Player>) entity -> {
            if (entity instanceof Player player) {
                return player.getUseItem().is(Items.GOAT_HORN);
            }
            return false;
        });
        RegisterMovingSoundRestrictions.register(WilderWild.id("nectar"), (RegisterMovingSoundRestrictions.LoopPredicate<Firefly>) entity -> {
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

        BlockScheduledTicks.ticks.put(Blocks.DIRT, (blockState, serverLevel, blockPos, randomSource) -> serverLevel.setBlock(blockPos, Blocks.MUD.defaultBlockState(), 3));
        HopperUntouchableList.blackListedTypes.add(RegisterBlockEntities.STONE_CHEST);
        SpawnRestrictionReplacements.spawnPlacementTypes.put(EntityType.SLIME, SpawnPlacements.Type.NO_RESTRICTIONS);
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/city_center/city_center_1"), WilderWild.id("ancient_city/city_center/city_center_1"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/city_center/city_center_2"), WilderWild.id("ancient_city/city_center/city_center_2"));

        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/barracks"), WilderWild.id("ancient_city/structures/barracks"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/chamber_1"), WilderWild.id("ancient_city/structures/chamber_1"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/chamber_2"), WilderWild.id("ancient_city/structures/chamber_2"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/chamber_3"), WilderWild.id("ancient_city/structures/chamber_3"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/sauna_1"), WilderWild.id("ancient_city/structures/sauna_1"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/tall_ruin_1"), WilderWild.id("ancient_city/structures/tall_ruin_1"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/tall_ruin_2"), WilderWild.id("ancient_city/structures/tall_ruin_2"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/tall_ruin_3"), WilderWild.id("ancient_city/structures/tall_ruin_3"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/tall_ruin_4"), WilderWild.id("ancient_city/structures/tall_ruin_4"));
        StructurePoolElementIdReplacements.resourceLocationReplacements.put(new ResourceLocation("ancient_city/structures/ice_box_1"), WilderWild.id("ancient_city/structures/ice_box_1"));
    }

}
