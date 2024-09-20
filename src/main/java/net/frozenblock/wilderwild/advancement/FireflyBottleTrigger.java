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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireflyBottleTrigger extends SimpleCriterionTrigger<FireflyBottleTrigger.TriggerInstance> {

	@Override
	@NotNull
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
		this.trigger(player, conditions -> conditions.matches(stack));
	}

	public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleInstance {

		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
				ItemPredicate.CODEC.optionalFieldOf("item").forGetter(TriggerInstance::item)
			).apply(instance, TriggerInstance::new)
		);

		@NotNull
		public static Criterion<TriggerInstance> fireflyBottle() {
			return fireflyBottle((ItemPredicate) null);
		}

		@NotNull
		public static Criterion<TriggerInstance> fireflyBottle(@NotNull ItemPredicate.Builder builder) {
			return fireflyBottle(builder.build());
		}

		@NotNull
		public static Criterion<TriggerInstance> fireflyBottle(@Nullable ItemPredicate item) {
			return WWCriteria.FIREFLY_BOTTLE.createCriterion(new TriggerInstance(Optional.empty(), Optional.ofNullable(item)));
		}

		public boolean matches(ItemStack item) {
			return this.item.isEmpty() || this.item.get().test(item);
		}
	}
}
