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
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public class FireflyBottleTrigger extends SimpleCriterionTrigger<FireflyBottleTrigger.TriggerInstance> {

	@Override
	@NotNull
	public TriggerInstance createInstance(@NotNull JsonObject jsonObject, @NotNull Optional<ContextAwarePredicate> contextAwarePredicate, @NotNull DeserializationContext deserializationContext) {
		return new TriggerInstance(contextAwarePredicate);
	}

	public void trigger(@NotNull ServerPlayer player) {
		this.trigger(player, conditions -> true);
	}

	public static class TriggerInstance
		extends AbstractCriterionTriggerInstance {

		public TriggerInstance(@NotNull Optional<ContextAwarePredicate> contextAwarePredicate) {
			super(contextAwarePredicate);
		}

		@NotNull
		public static Criterion<TriggerInstance> fireflyBottle() {
			return WWCriteria.FIREFLY_BOTTLE.createCriterion(new TriggerInstance(Optional.empty()));
		}
	}
}
