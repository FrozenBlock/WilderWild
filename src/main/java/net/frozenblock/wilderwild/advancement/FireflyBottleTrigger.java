package net.frozenblock.wilderwild.advancement;

import com.google.gson.JsonObject;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FireflyBottleTrigger extends SimpleCriterionTrigger<FireflyBottleTrigger.TriggerInstance> {

	public static final ResourceLocation ID = WilderSharedConstants.id("firefly_bottle");

	@Override
	@NotNull
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	@NotNull
	public TriggerInstance createInstance(@NotNull JsonObject jsonObject, @NotNull ContextAwarePredicate contextAwarePredicate, @NotNull DeserializationContext deserializationContext) {
		ItemPredicate itemPredicate = ItemPredicate.fromJson(jsonObject.get("item"));
		return new TriggerInstance(itemPredicate, contextAwarePredicate);
	}

	public void trigger(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
		this.trigger(player, (conditions) -> conditions.matches(stack));
	}

	public static class TriggerInstance
		extends AbstractCriterionTriggerInstance {
		private final ItemPredicate item;

		public TriggerInstance(@NotNull ItemPredicate item, @NotNull ContextAwarePredicate contextAwarePredicate) {
			super(ID, contextAwarePredicate);
			this.item = item;
		}

		@NotNull
		public static TriggerInstance fireflyBottle(@NotNull ItemPredicate item) {
			return new TriggerInstance(item, ContextAwarePredicate.ANY);
		}

		public boolean matches(@NotNull ItemStack stack) {
			return this.item.matches(stack);
		}

		@Override
		@NotNull
		public JsonObject serializeToJson(@NotNull SerializationContext context) {
			JsonObject jsonObject = super.serializeToJson(context);
			jsonObject.add("item", this.item.serializeToJson());
			return jsonObject;
		}
	}
}
