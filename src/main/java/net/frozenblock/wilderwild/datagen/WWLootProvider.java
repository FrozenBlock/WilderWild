package net.frozenblock.wilderwild.datagen;

import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class WWLootProvider extends SimpleFabricLootTableProvider {

	public WWLootProvider(FabricDataOutput output) {
		super(output, LootContextParamSets.ENTITY);
	}

	@Override
	public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
		output.accept(
			RegisterEntities.CRAB.getDefaultLootTable(),
			LootTable.lootTable().withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1.0F))
					.add(LootItem.lootTableItem(RegisterItems.CRAB_CLAW)
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
					)
					.when(LootItemKilledByPlayerCondition.killedByPlayer())
			)
		);
	}
}
