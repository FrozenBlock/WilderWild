package net.frozenblock.wilderwild.advancement;

import com.google.gson.JsonObject;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class FireflyBottleTrigger extends SimpleCriterionTrigger<FireflyBottleTrigger.TriggerInstance> {

	public static final ResourceLocation ID = WilderSharedConstants.id("firefly_bottle");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate contextAwarePredicate, DeserializationContext deserializationContext) {
		ItemPredicate itemPredicate = ItemPredicate.fromJson(jsonObject.get("item"));
		return new TriggerInstance(itemPredicate, contextAwarePredicate);
	}

	public void trigger(ServerPlayer player, ItemStack stack) {
		this.trigger(player, (conditions) -> conditions.matches(stack));
	}

	public static class TriggerInstance
		extends AbstractCriterionTriggerInstance {
		private final ItemPredicate item;

		public TriggerInstance(ItemPredicate item, ContextAwarePredicate contextAwarePredicate) {
			super(ID, contextAwarePredicate);
			this.item = item;
		}

		public static TriggerInstance fireflyBottle(ItemPredicate item) {
			return new TriggerInstance(item, ContextAwarePredicate.ANY);
		}

		public boolean matches(ItemStack stack) {
		return this.item.matches(stack);
	}

		@Override
		public JsonObject serializeToJson(SerializationContext context) {
			JsonObject jsonObject = super.serializeToJson(context);
			jsonObject.add("item", this.item.serializeToJson());
			return jsonObject;
		}
	}
}
