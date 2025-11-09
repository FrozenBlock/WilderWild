/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MobBottleTrigger extends SimpleCriterionTrigger<MobBottleTrigger.TriggerInstance> {

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
		public static Criterion<TriggerInstance> mobBottle() {
			return mobBottle((ItemPredicate) null);
		}

		@NotNull
		public static Criterion<TriggerInstance> mobBottle(@NotNull ItemPredicate.Builder builder) {
			return mobBottle(builder.build());
		}

		@NotNull
		public static Criterion<TriggerInstance> mobBottle(@Nullable ItemPredicate item) {
			return WWCriteria.MOB_BOTTLE.createCriterion(new TriggerInstance(Optional.empty(), Optional.ofNullable(item)));
		}

		public boolean matches(ItemStack item) {
			return this.item.isEmpty() || this.item.get().test(item);
		}
	}
}
