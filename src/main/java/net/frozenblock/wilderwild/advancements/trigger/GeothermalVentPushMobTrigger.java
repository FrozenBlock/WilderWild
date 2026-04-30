/*
 * Copyright 2025-2026 FrozenBlock
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

import net.frozenblock.wilderwild.block.state.properties.GeothermalVentType;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

public class GeothermalVentPushMobTrigger extends SimpleCriterionTrigger<GeothermalVentPushMobTrigger.TriggerInstance> {
	public static final double TRIGGER_DISTANCE_FROM_PLAYER = Mth.square(16D);

	@Override
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(ServerPlayer player, Entity entity, boolean playerPlaced, GeothermalVentType geothermalVentType) {
		LootContext pushedMob = EntityPredicate.createContext(player, entity);
		this.trigger(player, conditions -> conditions.matches(pushedMob, playerPlaced, geothermalVentType));
	}

	public record TriggerInstance(
		Optional<ContextAwarePredicate> player,
		Optional<ContextAwarePredicate> pushedMob,
		boolean playerPlaced,
		Optional<GeothermalVentType> geothermalVentType
	) implements SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("pushed_mob").forGetter(TriggerInstance::pushedMob),
				Codec.BOOL.fieldOf("requires_player_placed_geothermal_vent").forGetter(TriggerInstance::playerPlaced),
				GeothermalVentType.CODEC.optionalFieldOf("geothermal_vent_type").forGetter(TriggerInstance::geothermalVentType)
			).apply(instance, TriggerInstance::new)
		);

		public static Criterion<TriggerInstance> geothermalVentPushMob(Optional<EntityPredicate> pushedMob, boolean playerPlaced, @Nullable GeothermalVentType geothermalVentType) {
			return WWCriteria.GEOTHERMAL_VENT_PUSH_MOB_TRIGGER.createCriterion(
				new TriggerInstance(Optional.empty(), EntityPredicate.wrap(pushedMob), playerPlaced, Optional.ofNullable(geothermalVentType))
			);
		}

		public boolean matches(LootContext pushedMob, boolean playerPlaced, GeothermalVentType geothermalVentType) {
			if (this.playerPlaced && !playerPlaced) return false;
			if (this.geothermalVentType.isPresent() && this.geothermalVentType.get() != geothermalVentType) return false;
			return this.pushedMob.isEmpty() || this.pushedMob.get().matches(pushedMob);
		}
	}
}
