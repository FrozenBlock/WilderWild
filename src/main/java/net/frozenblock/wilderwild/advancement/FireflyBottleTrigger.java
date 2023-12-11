/*
 * Copyright 2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.advancement;

import com.google.gson.JsonObject;
import java.util.Optional;
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
