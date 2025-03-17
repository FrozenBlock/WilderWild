/*
 * Copyright 2023-2025 FrozenBlock
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
import net.frozenblock.wilderwild.block.state.properties.GeyserType;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeyserPushMobTrigger extends SimpleCriterionTrigger<GeyserPushMobTrigger.TriggerInstance> {
	public static final double TRIGGER_DISTANCE_FROM_PLAYER = Mth.square(16D);

	@Override
	@NotNull
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(@NotNull ServerPlayer player, Entity entity, boolean playerPlaced, GeyserType geyserType) {
		LootContext pushedMob = EntityPredicate.createContext(player, entity);
		this.trigger(player, conditions -> conditions.matches(pushedMob, playerPlaced, geyserType));
	}

	public record TriggerInstance(
		Optional<ContextAwarePredicate> player,
		Optional<ContextAwarePredicate> pushedMob,
		boolean playerPlaced,
		Optional<GeyserType> geyserType
	) implements SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("pushed_mob").forGetter(TriggerInstance::pushedMob),
				Codec.BOOL.fieldOf("requires_player_placed_geyser").forGetter(TriggerInstance::playerPlaced),
				GeyserType.CODEC.optionalFieldOf("geyser_type").forGetter(TriggerInstance::geyserType)
			).apply(instance, TriggerInstance::new)
		);

		@NotNull
		public static Criterion<TriggerInstance> geyserPushMob(Optional<EntityPredicate> pushedMob, boolean playerPlaced, @Nullable GeyserType geyserType) {
			return WWCriteria.GEYSER_PUSH_MOB_TRIGGER.createCriterion(
				new TriggerInstance(Optional.empty(), EntityPredicate.wrap(pushedMob), playerPlaced, Optional.ofNullable(geyserType))
			);
		}

		public boolean matches(LootContext pushedMob, boolean playerPlaced, GeyserType geyserType) {
			if (this.playerPlaced && !playerPlaced) return false;
			if (this.geyserType.isPresent() && this.geyserType.get() != geyserType) return false;
			return this.pushedMob.isEmpty() || this.pushedMob.get().matches(pushedMob);
		}
	}
}
