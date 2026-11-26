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
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.predicates.DistancePredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class FallAfterHarpoonGrappleFromGroundTrigger extends SimpleCriterionTrigger<FallAfterHarpoonGrappleFromGroundTrigger.TriggerInstance> {

	@Override
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(ServerPlayer player, Vec3 startPosition) {
		this.trigger(player, trigger -> trigger.matches(player.level(), startPosition, player.position()));
	}

	public record TriggerInstance(
		Optional<Holder<LootItemCondition>> player,
		Optional<LocationPredicate> startPosition,
		Optional<DistancePredicate> distance
	) implements SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
			LocationPredicate.CODEC.optionalFieldOf("start_position").forGetter(TriggerInstance::startPosition),
			DistancePredicate.CODEC.optionalFieldOf("distance").forGetter(TriggerInstance::distance)
		).apply(instance, TriggerInstance::new));

		public static Criterion<TriggerInstance> fallAfterHarpoonGrappleFromGround(DistancePredicate distance) {
			return WWCriteria.FALL_AFTER_HARPOON_GRAPPLE_FROM_GROUND.get().createCriterion(
				new TriggerInstance(
					Optional.empty(),
					Optional.empty(),
					Optional.of(distance)
				)
			);
		}

		public boolean matches(ServerLevel level, Vec3 enteredPosition, Vec3 playerPosition) {
			if (this.startPosition.isPresent()
				&& !this.startPosition.get().matches(level, enteredPosition.x, enteredPosition.y, enteredPosition.z)
			) return false;

			return this.distance.isPresent()
				&& this.distance.get().matches(enteredPosition.x, enteredPosition.y, enteredPosition.z, playerPosition.x, playerPosition.y, playerPosition.z);
		}
	}
}
