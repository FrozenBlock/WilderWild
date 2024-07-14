/*
 * Copyright 2023-2024 FrozenBlock
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
import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class FireflyBottleTrigger extends SimpleCriterionTrigger<FireflyBottleTrigger.TriggerInstance> {
	static final ResourceLocation ID = WilderConstants.id("firefly_bottle");

	@Override
	@NotNull
	public TriggerInstance createInstance(@NotNull JsonObject jsonObject, @NotNull ContextAwarePredicate contextAwarePredicate, @NotNull DeserializationContext deserializationContext) {
		return new TriggerInstance(contextAwarePredicate);
	}

	public void trigger(@NotNull ServerPlayer player) {
		this.trigger(player, conditions -> true);
	}

	@NotNull
	@Override
	public ResourceLocation getId() {
		return ID;
	}

	public static class TriggerInstance
		extends AbstractCriterionTriggerInstance {

		public TriggerInstance(ContextAwarePredicate player) {
			super(ID, player);
		}

		@NotNull
		public static TriggerInstance fireflyBottle() {
			return new TriggerInstance(ContextAwarePredicate.ANY);
		}
	}
}
