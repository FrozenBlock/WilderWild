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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.ValidationContextSource;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class HarpoonReeledTrigger extends SimpleCriterionTrigger<HarpoonReeledTrigger.TriggerInstance> {

	@Override
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void triggerReel(ServerPlayer player, ItemStack crossbowItem, ItemStack harpoonItem, Harpoon harpoon) {
		final LootContext reelContext = EntityPredicate.createContext(player, harpoon);
		this.trigger(player, trigger -> trigger.matches(ReelType.REEL, crossbowItem, harpoonItem, reelContext));
	}

	public void triggerReelEntity(ServerPlayer player, ItemStack crossbowItem, ItemStack harpoonItem, Entity hookedIn) {
		final LootContext hookedInContext = EntityPredicate.createContext(player, hookedIn);
		this.trigger(player, trigger -> trigger.matches(ReelType.REEL_ENTITY, crossbowItem, harpoonItem, hookedInContext));
	}

	public void triggerGrapple(ServerPlayer player, ItemStack crossbowItem, ItemStack harpoonItem, Harpoon harpoon) {
		final LootContext hookedInContext = EntityPredicate.createContext(player, harpoon);
		this.trigger(player, trigger -> trigger.matches(ReelType.GRAPPLE, crossbowItem, harpoonItem, hookedInContext));
	}

	public record TriggerInstance(
		ReelType reelType,
		Optional<Holder<LootItemCondition>> player,
		Optional<ItemPredicate> crossbow,
		Optional<ItemPredicate> harpoon,
		Optional<Holder<LootItemCondition>> entity,
		Optional<BlockPredicate> block
	) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ReelType.CODEC.fieldOf("reel_type").forGetter(TriggerInstance::reelType),
			LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
			ItemPredicate.CODEC.optionalFieldOf("crossbow").forGetter(TriggerInstance::crossbow),
			ItemPredicate.CODEC.optionalFieldOf("harpoon").forGetter(TriggerInstance::harpoon),
			LootItemCondition.CODEC.optionalFieldOf("entity").forGetter(TriggerInstance::entity),
			BlockPredicate.CODEC.optionalFieldOf("block").forGetter(TriggerInstance::block)
		).apply(instance, TriggerInstance::new));

		public static Criterion<TriggerInstance> reeledEntity(
			Optional<ItemPredicate> crossbow,
			Optional<ItemPredicate> harpoon,
			Optional<EntityPredicate> entity
		) {
			return WWCriteria.HARPOON_REELED.get().createCriterion(
				new TriggerInstance(
					ReelType.REEL_ENTITY,
					Optional.empty(),
					crossbow,
					harpoon,
					EntityPredicate.wrap(entity),
					Optional.empty()
				)
			);
		}

		public static Criterion<TriggerInstance> grappled(
			Optional<ItemPredicate> crossbow,
			Optional<ItemPredicate> harpoon,
			Optional<EntityPredicate> entity,
			Optional<BlockPredicate> block
		) {
			return WWCriteria.HARPOON_REELED.get().createCriterion(
				new TriggerInstance(
					ReelType.GRAPPLE,
					Optional.empty(),
					crossbow,
					harpoon,
					EntityPredicate.wrap(entity),
					block
				)
			);
		}

		public boolean matches(ReelType reelType, ItemStack crossbow, ItemStack harpoon, LootContext context) {
			if (this.reelType != reelType) return false;
			if (this.crossbow.isPresent() && !this.crossbow.get().test(crossbow)) return false;
			if (this.harpoon.isPresent() && !this.harpoon.get().test(harpoon)) return false;
			if (this.entity.isPresent() && !this.entity.get().value().test(context)) return false;

			final Vec3 pos = context.getOptional(LootContextParams.ORIGIN);
			if (this.block.isPresent() && (pos == null || !this.block.get().matches(context.getLevel(), BlockPos.containing(pos)))) return false;

			return true;
		}

		@Override
		public void validate(ValidationContextSource validator) {
			SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
			Validatable.validateHolder(validator.entityContext(), "entity", this.entity);
		}
	}

	public enum ReelType implements StringRepresentable {
		REEL("reel"),
		REEL_ENTITY("reel_entity"),
		GRAPPLE("grapple");
		public static EnumCodec<ReelType> CODEC = StringRepresentable.fromEnum(ReelType::values);
		private final String name;

		ReelType(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
