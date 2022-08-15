package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Rarity;

public final class RegisterLootTables {

    public static void init() {
        //ANCIENT HORN
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (LootTables.ANCIENT_CITY_CHEST.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(RegisterItems.ANCIENT_HORN).weight(1).quality(Rarity.EPIC.ordinal() + 1)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-10.0F, 0.5F)));

                tableBuilder.pool(pool);
            }
        });
        //FLOATING MOSS
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (LootTables.SHIPWRECK_SUPPLY_CHEST.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(RegisterBlocks.ALGAE.asItem()).weight(5).quality(Rarity.COMMON.ordinal() + 1)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0F, 1.0F)));

                tableBuilder.pool(pool);
            }
        });
        //BAOBAB SAPLING
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (LootTables.VILLAGE_SAVANNA_HOUSE_CHEST.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(RegisterBlocks.BAOBAB_SAPLING.asItem()).weight(2).quality(Rarity.COMMON.ordinal() + 1)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0F, 1.0F)));

                tableBuilder.pool(pool);
            }
        });
        //BAOBAB LOG
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (LootTables.VILLAGE_SAVANNA_HOUSE_CHEST.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(RegisterBlocks.BAOBAB_LOG.asItem()).weight(2).quality(Rarity.COMMON.ordinal() + 1)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0F, 1.0F)));

                tableBuilder.pool(pool);
            }
        });
    }
}
