/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.advancements.trigger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.ValidationContextSource;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jspecify.annotations.Nullable;

public class KilledByHarpoonTrigger extends SimpleCriterionTrigger<KilledByHarpoonTrigger.TriggerInstance> {

	@Override
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(ServerPlayer player, Collection<Entity> victims, @Nullable ItemStack firedByWeapon) {
		final List<LootContext> victimContexts = Lists.newArrayList();
		final Set<EntityType<?>> entityTypes = Sets.newHashSet();

		for (Entity victim : victims) {
			entityTypes.add(victim.getType());
			victimContexts.add(EntityPredicate.createContext(player, victim));
		}

		this.trigger(player, t -> t.matches(victimContexts, entityTypes.size(), firedByWeapon));
	}

	public record TriggerInstance(
		Optional<Holder<LootItemCondition>> player,
		List<Holder<LootItemCondition>> victims,
		MinMaxBounds.Ints uniqueEntityTypes,
		Optional<ItemPredicate> firedFromWeapon
	) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
			LootItemCondition.CODEC.listOf().optionalFieldOf("victims", List.of()).forGetter(TriggerInstance::victims),
			MinMaxBounds.Ints.CODEC
				.optionalFieldOf("unique_entity_types", MinMaxBounds.Ints.ANY)
				.forGetter(TriggerInstance::uniqueEntityTypes),
			ItemPredicate.CODEC.optionalFieldOf("fired_from_weapon").forGetter(TriggerInstance::firedFromWeapon)
		).apply(instance, TriggerInstance::new));

		public static Criterion<TriggerInstance> crossbowKilled(HolderGetter<Item> items, EntityPredicate.Builder... victims) {
			return WWCriteria.KILLED_BY_HARPOON.get().createCriterion(
				new TriggerInstance(
					Optional.empty(),
					EntityPredicate.wrap(victims),
					MinMaxBounds.Ints.ANY,
					Optional.of(ItemPredicate.Builder.item().of(items, Items.CROSSBOW).build())
				)
			);
		}

		public static Criterion<TriggerInstance> crossbowKilled(HolderGetter<Item> items, MinMaxBounds.Ints uniqueEntityTypes) {
			return WWCriteria.KILLED_BY_HARPOON.get().createCriterion(
				new TriggerInstance(
					Optional.empty(),
					List.of(),
					uniqueEntityTypes,
					Optional.of(ItemPredicate.Builder.item().of(items, Items.CROSSBOW).build())
				)
			);
		}

		public boolean matches(Collection<LootContext> victims, int uniqueEntityTypes, @Nullable ItemStack firedFromWeapon) {
			if (this.firedFromWeapon.isEmpty() || firedFromWeapon != null && this.firedFromWeapon.get().test(firedFromWeapon)) {
				if (!this.victims.isEmpty()) {
					final List<LootContext> victimsCopy = Lists.newArrayList(victims);

					for (Holder<LootItemCondition> predicate : this.victims) {
						boolean found = false;

						for (LootContext entity : victimsCopy) {
							if (predicate.value().test(entity)) {
								found = true;
								break;
							}
						}

						if (!found) return false;
					}
				}

				return this.uniqueEntityTypes.matches(uniqueEntityTypes);
			}

			return false;
		}

		@Override
		public void validate(ValidationContextSource validator) {
			SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
			Validatable.validateHolder(validator.entityContext(), "victims", this.victims);
		}
	}
}
