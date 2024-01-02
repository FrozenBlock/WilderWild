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
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FireflyBottleTrigger extends SimpleCriterionTrigger<FireflyBottleTrigger.TriggerInstance> {

	@Override
	@NotNull
	public TriggerInstance createInstance(@NotNull JsonObject jsonObject, @NotNull Optional<ContextAwarePredicate> contextAwarePredicate, @NotNull DeserializationContext deserializationContext) {
		Optional<ItemPredicate> itemPredicate = ItemPredicate.fromJson(jsonObject.get("item"));
		return new TriggerInstance(itemPredicate, contextAwarePredicate);
	}

	public void trigger(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
		this.trigger(player, conditions -> conditions.matches(stack));
	}

	public static class TriggerInstance
		extends AbstractCriterionTriggerInstance {
		private final Optional<ItemPredicate> item;

		public TriggerInstance(@NotNull Optional<ItemPredicate> item, @NotNull Optional<ContextAwarePredicate> contextAwarePredicate) {
			super(contextAwarePredicate);
			this.item = item;
		}

		@NotNull
		public static TriggerInstance fireflyBottle(@NotNull ItemPredicate item) {
			return new TriggerInstance(Optional.of(item), Optional.empty());
		}

		public boolean matches(@NotNull ItemStack stack) {
            return this.item.map(itemPredicate -> itemPredicate.matches(stack)).orElse(false);
        }

		@Override
		@NotNull
		public JsonObject serializeToJson() {
			JsonObject jsonObject = super.serializeToJson();
			this.item.ifPresent(itemPredicate -> jsonObject.add("item", itemPredicate.serializeToJson()));
			return jsonObject;
		}
	}
}
