package net.frozenblock.wilderwild.datagen;

import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.frozenblock.wilderwild.advancement.FireflyBottleTrigger;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.network.chat.Component;

public class WWAdvancementProvider extends FabricAdvancementProvider {
	protected WWAdvancementProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateAdvancement(Consumer<AdvancementHolder> writer) {
		Advancement.Builder.advancement()
			.parent(WilderSharedConstants.vanillaId("husbandry/root"))
			.display(
				RegisterItems.FIREFLY_BOTTLE,
				Component.translatable("wilderwild.advancements.husbandry.firefly_in_a_bottle.title"),
				Component.translatable("wilderwild.advancements.husbandry.firefly_in_a_bottle.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("firefly_bottled", FireflyBottleTrigger.TriggerInstance.fireflyBottle())
			.save(writer, WilderSharedConstants.string("husbandry/firefly_in_a_bottle"));
	}
}
