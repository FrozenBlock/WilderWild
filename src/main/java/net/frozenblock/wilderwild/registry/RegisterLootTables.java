package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class RegisterLootTables {

    // allows for other mods to add their items to the loot table without breaking anything
    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BuiltInLootTables.ANCIENT_CITY.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(RegisterItems.ANCIENT_HORN).setWeight(1).setQuality(Rarity.EPIC.ordinal() + 1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-10.0F, 0.5F)));

                tableBuilder.withPool(pool);
            }
        });
    }
}
