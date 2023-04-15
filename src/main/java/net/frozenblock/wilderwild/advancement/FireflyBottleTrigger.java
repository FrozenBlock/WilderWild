package net.frozenblock.wilderwild.advancement;

import com.google.gson.JsonObject;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class FireflyBottleTrigger extends SimpleCriterionTrigger<FireflyBottleTrigger.TriggerInstance> {
static final ResourceLocation ID = WilderSharedConstants.id("firefly_bottle");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext conditionsParser) {
		ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
		return new TriggerInstance(entityPredicate, itemPredicate);
	}

	public void trigger(ServerPlayer player, ItemStack stack) {
		this.trigger(player, (conditions) -> conditions.matches(stack));
	}

	public static class TriggerInstance
		extends AbstractCriterionTriggerInstance {
		private final ItemPredicate item;

		public TriggerInstance(EntityPredicate.Composite player, ItemPredicate item) {
			super(ID, player);
			this.item = item;
		}

		public static TriggerInstance fireflyBottle(ItemPredicate item) {
			return new TriggerInstance(EntityPredicate.Composite.ANY, item);
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
