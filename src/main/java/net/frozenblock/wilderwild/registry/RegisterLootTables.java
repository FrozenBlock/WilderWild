package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Rarity;

public class RegisterLootTables {

    // allows for other mods to add their items to the loot table without breaking anything
    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (LootTables.ANCIENT_CITY_CHEST.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(RegisterItems.ANCIENT_HORN).weight(1).quality(Rarity.EPIC.ordinal() + 1)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0F, 1.0F)));

                tableBuilder.pool(pool);
            }
        });
    }
}
