package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class RegisterLootTables {

    public static void init() {
        //ANCIENT HORN
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BuiltInLootTables.ANCIENT_CITY.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(RegisterItems.ANCIENT_HORN).setWeight(1).setQuality(Rarity.EPIC.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-10.0F, 0.5F)));

                tableBuilder.withPool(pool);
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
