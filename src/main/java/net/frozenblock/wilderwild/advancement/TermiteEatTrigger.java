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
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TermiteEatTrigger extends SimpleCriterionTrigger<TermiteEatTrigger.TriggerInstance> {
	public static final double TRIGGER_DISTANCE_FROM_PLAYER = Mth.square(10D);

	@Override
	@NotNull
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(@NotNull ServerPlayer player, ServerLevel level, @NotNull BlockPos pos, boolean playerPlaced) {
		this.trigger(player, conditions -> conditions.matches(level, pos, playerPlaced));
	}

	public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<BlockPredicate> block, boolean playerPlaced) implements SimpleInstance {

		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
				BlockPredicate.CODEC.optionalFieldOf("block").forGetter(TriggerInstance::block),
				Codec.BOOL.fieldOf("requires_player_placed_mound").forGetter(TriggerInstance::playerPlaced)
			).apply(instance, TriggerInstance::new)
		);

		@NotNull
		public static Criterion<TriggerInstance> termiteEat(boolean playerPlaced) {
			return termiteEat((BlockPredicate) null, playerPlaced);
		}

		@NotNull
		public static Criterion<TriggerInstance> termiteEat(@NotNull BlockPredicate.Builder builder, boolean playerPlaced) {
			return termiteEat(builder.build(), playerPlaced);
		}

		@NotNull
		public static Criterion<TriggerInstance> termiteEat(@Nullable BlockPredicate block, boolean playerPlaced) {
			return WWCriteria.TERMITE_EAT.createCriterion(new TriggerInstance(Optional.empty(), Optional.ofNullable(block), playerPlaced));
		}

		public boolean matches(ServerLevel level, BlockPos pos, boolean playerPlaced) {
			if (this.playerPlaced && !playerPlaced) return false;
			return this.block.isEmpty() || this.block.get().matches(level, pos);
		}
	}
}
